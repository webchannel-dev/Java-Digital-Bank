/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnitMetadata;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.bean.GroupCategoryPermission;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.form.GroupForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveGroupPermissionsAction extends UserAction
/*     */   implements FrameworkConstants, UserConstants, CategoryConstants
/*     */ {
/*  60 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  62 */   private AssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     ActionForward afForward = null;
/*  84 */     GroupForm form = (GroupForm)a_form;
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  88 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  90 */       this.m_logger.error("SaveGroupAction.execute : User is not an admin.");
/*  91 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  93 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  96 */     long lGroupId = form.getGroup().getId();
/*     */ 
/*  98 */     if (lGroupId > 0L)
/*     */     {
/* 100 */       OrgUnit ouGroup = this.m_orgUnitManager.getOrgUnitForGroup(a_dbTransaction, lGroupId);
/* 101 */       if ((!userProfile.getIsAdmin()) && ((!userProfile.getIsOrgUnitAdmin()) || (!user.getIsAdminOfOrgUnit(ouGroup.getId()))))
/*     */       {
/* 103 */         this.m_logger.error("SaveGroupAction.execute : User is not an admin of group: " + lGroupId);
/* 104 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 109 */     Vector vecPermissions = getPermissions(a_request);
/* 110 */     form.setSelectedPermissions(vecPermissions);
/*     */ 
/* 114 */     if ((lGroupId > 0L) && (!userProfile.getIsAdmin()))
/*     */     {
/* 116 */       addInvisiblePermissions(lGroupId, vecPermissions);
/*     */     }
/*     */ 
/* 120 */     long lTreeId = getLongParameter(a_request, "treeId");
/*     */ 
/* 122 */     getUserManager().saveGroupPermissions(a_dbTransaction, form.getGroup(), vecPermissions, lTreeId);
/*     */ 
/* 125 */     lGroupId = form.getGroup().getId();
/*     */ 
/* 128 */     long lOrgUnitId = form.getGroup().getOrgUnitReference().getId();
/* 129 */     if (lOrgUnitId > 0L)
/*     */     {
/* 131 */       this.m_orgUnitManager.addGroupToOrgUnit(a_dbTransaction, lOrgUnitId, lGroupId);
/*     */     }
/*     */ 
/* 136 */     this.m_assetManager.clearAssetCaches();
/*     */ 
/* 138 */     afForward = createRedirectingForward(RequestUtil.getAsQueryParameter(a_request, "name") + "&" + RequestUtil.getAsQueryParameter(a_request, "page") + "&" + RequestUtil.getAsQueryParameter(a_request, "pageSize"), a_mapping, "Success");
/*     */ 
/* 144 */     return afForward;
/*     */   }
/*     */ 
/*     */   public Vector getPermissions(HttpServletRequest a_request)
/*     */   {
/* 162 */     Vector vecPermissions = new Vector();
/*     */ 
/* 164 */     Enumeration e = a_request.getParameterNames();
/* 165 */     String sName = null;
/*     */ 
/* 167 */     while (e.hasMoreElements())
/*     */     {
/* 169 */       sName = (String)e.nextElement();
/*     */ 
/* 172 */       if (!sName.startsWith("catPermission")) {
/*     */         continue;
/*     */       }
/* 175 */       String sValue = a_request.getParameter(sName);
/*     */ 
/* 178 */       String[] aVals = sValue.split(":");
/* 179 */       String[] aIds = aVals[0].split("!");
/*     */       try
/*     */       {
/* 184 */         long lCatId = new Long(aIds[0]).longValue();
/*     */ 
/* 187 */         int iDownloadPermissionLevel = new Integer(aVals[1]).intValue();
/*     */ 
/* 190 */         long lParentId = 0L;
/*     */         try
/*     */         {
/* 194 */           lParentId = Long.parseLong(aIds[1]);
/*     */         }
/*     */         catch (NumberFormatException ne)
/*     */         {
/* 199 */           this.m_logger.error("SaveGroupAction.getPermissions : Unable to parse parent category id : " + aIds[1]);
/*     */         }
/*     */ 
/* 203 */         int iUploadPermissionLevel = 0;
/* 204 */         String sUploadValue = a_request.getParameter("catUploadPermission" + lCatId);
/* 205 */         if (sUploadValue != null)
/*     */         {
/* 207 */           iUploadPermissionLevel = Integer.parseInt(sUploadValue);
/*     */         }
/*     */ 
/* 211 */         boolean bCanDownloadOriginal = false;
/* 212 */         boolean bCanDownloadAdvanced = false;
/* 213 */         boolean bCanReviewAssets = false;
/* 214 */         boolean bCanViewRestrictedAssets = false;
/* 215 */         boolean bRequiresApprovalForHighRes = false;
/*     */ 
/* 218 */         String sAdvancedPermissions = a_request.getParameter("advanced_permissions");
/* 219 */         if (!StringUtil.stringIsPopulated(sAdvancedPermissions))
/*     */         {
/* 221 */           bCanDownloadOriginal = true;
/* 222 */           bCanDownloadAdvanced = true;
/* 223 */           bCanReviewAssets = true;
/* 224 */           bCanViewRestrictedAssets = true;
/*     */         }
/*     */         else
/*     */         {
/* 228 */           String sDownloadOriginal = a_request.getParameter("catDownloadOriginal" + lCatId);
/* 229 */           if (StringUtil.stringIsPopulated(sDownloadOriginal))
/*     */           {
/* 231 */             bCanDownloadOriginal = true;
/*     */           }
/* 233 */           String sDownloadAdvanced = a_request.getParameter("catDownloadAdvanced" + lCatId);
/* 234 */           if (StringUtil.stringIsPopulated(sDownloadAdvanced))
/*     */           {
/* 236 */             bCanDownloadAdvanced = true;
/*     */           }
/* 238 */           String sReviewAssets = a_request.getParameter("catReviewAssets" + lCatId);
/* 239 */           if (StringUtil.stringIsPopulated(sReviewAssets))
/*     */           {
/* 241 */             bCanReviewAssets = true;
/*     */           }
/*     */ 
/* 244 */           String sViewRestrictedAssets = a_request.getParameter("catViewRestrictedAssets" + lCatId);
/* 245 */           if (StringUtil.stringIsPopulated(sViewRestrictedAssets))
/*     */           {
/* 247 */             bCanViewRestrictedAssets = true;
/*     */           }
/*     */ 
/* 250 */           String sApprovalForHighRes = a_request.getParameter("catHighResApproval" + lCatId);
/* 251 */           if (StringUtil.stringIsPopulated(sApprovalForHighRes))
/*     */           {
/* 253 */             bRequiresApprovalForHighRes = true;
/*     */           }
/*     */         }
/*     */ 
/* 257 */         boolean bCanEditSubcategories = false;
/* 258 */         String sDownloadAdvanced = a_request.getParameter("catEditSubcategories" + lCatId);
/* 259 */         if (StringUtil.stringIsPopulated(sDownloadAdvanced))
/*     */         {
/* 261 */           bCanEditSubcategories = true;
/*     */         }
/*     */ 
/* 264 */         GroupCategoryPermission gcp = new GroupCategoryPermission();
/* 265 */         gcp.getCategory().setId(lCatId);
/* 266 */         gcp.setParentCategoryId(lParentId);
/* 267 */         gcp.setDownloadPermissionLevel(iDownloadPermissionLevel);
/* 268 */         gcp.setUploadPermissionLevel(iUploadPermissionLevel);
/* 269 */         gcp.setCanDownloadOriginal(bCanDownloadOriginal);
/* 270 */         gcp.setCanDownloadAdvanced(bCanDownloadAdvanced);
/* 271 */         gcp.setCanEditSubcategories(bCanEditSubcategories);
/* 272 */         gcp.setCanReviewAssets(bCanReviewAssets);
/* 273 */         gcp.setCanViewRestrictedAssets(bCanViewRestrictedAssets);
/* 274 */         gcp.setApprovalRequiredForHighRes(bRequiresApprovalForHighRes);
/*     */ 
/* 276 */         vecPermissions.add(gcp);
/*     */       }
/*     */       catch (NumberFormatException ex)
/*     */       {
/* 281 */         this.m_logger.error("SaveGroupAction.getPermissions : Unable to parse permission value");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 288 */     updatePermissions(vecPermissions);
/*     */ 
/* 290 */     return vecPermissions;
/*     */   }
/*     */ 
/*     */   public void updatePermissions(Vector a_vecCurrentPermissions)
/*     */   {
/* 308 */     GroupCategoryPermission gcp = null;
/*     */ 
/* 311 */     for (int i = 0; i < a_vecCurrentPermissions.size(); i++)
/*     */     {
/* 313 */       gcp = (GroupCategoryPermission)(GroupCategoryPermission)a_vecCurrentPermissions.elementAt(i);
/*     */ 
/* 315 */       if (gcp.getParentCategoryId() <= 0L)
/*     */         continue;
/* 317 */       if (gcp.getDownloadPermissionLevel() == 0)
/*     */         continue;
/* 319 */       fixPermission(gcp.getParentCategoryId(), gcp.getDownloadPermissionLevel(), a_vecCurrentPermissions);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void fixPermission(long a_lCatId, int a_iPermissionLevel, Vector a_vecPermissions)
/*     */   {
/* 349 */     GroupCategoryPermission gcp = null;
/*     */ 
/* 351 */     for (int i = 0; i < a_vecPermissions.size(); i++)
/*     */     {
/* 353 */       gcp = (GroupCategoryPermission)(GroupCategoryPermission)a_vecPermissions.elementAt(i);
/*     */ 
/* 355 */       if (gcp.getCategory().getId() != a_lCatId)
/*     */         continue;
/* 357 */       if (isParentPermissionLevelSufficient(gcp.getDownloadPermissionLevel(), a_iPermissionLevel)) {
/*     */         break;
/*     */       }
/* 360 */       gcp.setDownloadPermissionLevel(1);
/* 361 */       a_vecPermissions.setElementAt(gcp, i);
/*     */ 
/* 363 */       if (gcp.getParentCategoryId() <= 0L)
/*     */         break;
/* 365 */       fixPermission(gcp.getParentCategoryId(), a_iPermissionLevel, a_vecPermissions); break;
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isParentPermissionLevelSufficient(int a_iParentPermission, int a_iPermission)
/*     */   {
/* 394 */     return (a_iPermission == 0) || (a_iParentPermission != 0);
/*     */   }
/*     */ 
/*     */   private void addInvisiblePermissions(long a_lGroupId, Vector a_vecPermissions)
/*     */     throws Bn2Exception
/*     */   {
/* 411 */     Group oldGroup = getUserManager().getGroup(a_lGroupId);
/*     */ 
/* 414 */     Vector vecOldPermissions = oldGroup.getCategoryPermissions();
/*     */ 
/* 416 */     if (vecOldPermissions != null)
/*     */     {
/* 418 */       Iterator itOld = vecOldPermissions.iterator();
/* 419 */       while (itOld.hasNext())
/*     */       {
/* 421 */         GroupCategoryPermission oldGCP = (GroupCategoryPermission)itOld.next();
/*     */ 
/* 424 */         boolean bInList = false;
/* 425 */         Iterator itNew = a_vecPermissions.iterator();
/* 426 */         while (itNew.hasNext())
/*     */         {
/* 428 */           GroupCategoryPermission newGCP = (GroupCategoryPermission)itNew.next();
/*     */ 
/* 430 */           if (oldGCP.getCategory().getId() == newGCP.getCategory().getId())
/*     */           {
/* 433 */             bInList = true;
/* 434 */             break;
/*     */           }
/*     */         }
/*     */ 
/* 438 */         if (!bInList)
/*     */         {
/* 441 */           a_vecPermissions.add(oldGCP);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/* 449 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 454 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.SaveGroupPermissionsAction
 * JD-Core Version:    0.6.0
 */