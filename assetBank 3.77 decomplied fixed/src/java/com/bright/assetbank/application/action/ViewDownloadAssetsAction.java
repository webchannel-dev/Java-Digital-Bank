/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.ABImageMagick;
/*     */ import com.bright.assetbank.approval.bean.AssetApprovalSearchCriteria;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxDownloadForm;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.TreeMap;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class ViewDownloadAssetsAction extends ViewDownloadImageAssetAction
/*     */   implements AssetBoxConstants, AssetBankConstants, FrameworkConstants
/*     */ {
/*  79 */   private AssetApprovalManager m_approvalManager = null;
/*     */ 
/*  85 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   protected abstract Vector<AssetInList> getAssetsToDownload(DBTransaction paramDBTransaction, ABUserProfile paramABUserProfile, AssetBoxDownloadForm paramAssetBoxDownloadForm, HttpServletRequest paramHttpServletRequest)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/*  82 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  88 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  98 */     ActionForward afForward = null;
/*  99 */     AssetBoxDownloadForm form = (AssetBoxDownloadForm)a_form;
/* 100 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 102 */     int iSize = 0;
/* 103 */     String sFileTypeCounts = "";
/* 104 */     HashMap hmFileExtensionCounts = new HashMap(10);
/* 105 */     TreeMap tmFileExtensions = new TreeMap();
/* 106 */     boolean bCanDownloadAllAssets = true;
/* 107 */     boolean bCanDownloadAnyAssets = false;
/*     */ 
/* 110 */     boolean bCanDownloadOriginal = true;
/* 111 */     boolean bCanDownloadOriginalAny = false;
/*     */ 
/* 114 */     boolean bCanDownloadAdvanced = true;
/* 115 */     boolean bCanDownloadAdvancedAny = false;
/*     */ 
/* 118 */     boolean bAnAgreementApplies = false;
/*     */ 
/* 121 */     boolean bRequireHighResApproval = false;
/*     */ 
/* 123 */     Vector vecHighResApprovals = new Vector();
/*     */ 
/* 129 */     Vector vecApprovedAssets = new Vector();
/*     */ 
/* 131 */     Vector<AssetInList> candidateAssets = getAssetsToDownload(a_dbTransaction, userProfile, form, a_request);
/*     */ 
/* 136 */     if (candidateAssets.size() == 0)
/*     */     {
/* 138 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "noAssetFilesToDownload", userProfile.getCurrentLanguage()).getBody());
/* 139 */       return a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 142 */     Iterator itBox = candidateAssets.iterator();
/* 143 */     int iDownloadOriginalCount = 0;
/* 144 */     String sExcludedIds = "";
/* 145 */     while (itBox.hasNext())
/*     */     {
/* 147 */       AssetInList ail = (AssetInList)itBox.next();
/* 148 */       Asset asset = ail.getAsset();
/*     */ 
/* 151 */       if (asset.getSurrogateAssetId() > 0L)
/*     */       {
/* 153 */         asset = this.m_assetManager.getAsset(a_dbTransaction, asset.getSurrogateAssetId(), null, false, false);
/*     */       }
/*     */ 
/* 156 */       if (ail.getIsApprovalApproved())
/*     */       {
/* 158 */         vecApprovedAssets.add(new Long(ail.getAsset().getId()));
/*     */       }
/*     */ 
/* 162 */       if (asset.getAgreementTypeId() == 2L)
/*     */       {
/* 164 */         bAnAgreementApplies = true;
/*     */       }
/*     */ 
/* 168 */       if (ail.getIsDownloadableNow())
/*     */       {
/* 170 */         bCanDownloadAnyAssets = true;
/*     */ 
/* 172 */         String sFileLoc = asset.getOriginalFileLocation();
/* 173 */         if (StringUtils.isEmpty(sFileLoc))
/*     */         {
/* 175 */           sFileLoc = asset.getFileLocation();
/*     */         }
/*     */ 
/* 178 */         String sExt = FileUtil.getSuffix(sFileLoc).toLowerCase();
/*     */ 
/* 180 */         if (hmFileExtensionCounts.containsKey(sExt))
/*     */         {
/* 183 */           hmFileExtensionCounts.put(sExt, new Long(((Long)hmFileExtensionCounts.get(sExt)).longValue() + 1L));
/*     */         }
/*     */         else
/*     */         {
/* 187 */           hmFileExtensionCounts.put(sExt, new Long(1L));
/* 188 */           tmFileExtensions.put(sExt, sExt);
/*     */         }
/*     */ 
/* 191 */         iSize = (int)(iSize + asset.getFileSizeInBytes());
/*     */ 
/* 194 */         if (asset.getTypeId() == 2L)
/*     */         {
/* 196 */           if (ABImageMagick.getIsCMYK(((ImageAsset)asset).getColorSpace()))
/*     */           {
/* 198 */             form.setNotAllRGB(true);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 203 */         if (ail.getUserCanDownloadOriginal())
/*     */         {
/* 205 */           bCanDownloadOriginalAny = true;
/* 206 */           iDownloadOriginalCount++;
/*     */         }
/*     */         else
/*     */         {
/* 210 */           sExcludedIds = sExcludedIds + "," + asset.getId();
/* 211 */           bCanDownloadOriginal = false;
/*     */         }
/*     */ 
/* 214 */         if (ail.getUserCanDownloadAdvanced())
/*     */         {
/* 216 */           bCanDownloadAdvancedAny = true;
/*     */         }
/*     */         else
/*     */         {
/* 220 */           bCanDownloadAdvanced = false;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 225 */         bCanDownloadAllAssets = false;
/*     */       }
/*     */     }
/* 228 */     sExcludedIds = sExcludedIds + ",";
/*     */ 
/* 231 */     if (bAnAgreementApplies)
/*     */     {
/* 233 */       form.setAssetsHaveAgreement(true);
/*     */     }
/*     */ 
/* 237 */     if (!bCanDownloadAnyAssets)
/*     */     {
/* 239 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetBoxErrorPermission", userProfile.getCurrentLanguage()).getBody());
/* 240 */       return a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 243 */     Iterator it = tmFileExtensions.values().iterator();
/*     */ 
/* 246 */     while (it.hasNext())
/*     */     {
/* 248 */       String sExt = (String)it.next();
/*     */ 
/* 250 */       sFileTypeCounts = sFileTypeCounts + sExt.toUpperCase() + " x " + hmFileExtensionCounts.get(sExt);
/*     */ 
/* 252 */       if (it.hasNext())
/*     */       {
/* 254 */         sFileTypeCounts = sFileTypeCounts + "; ";
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 260 */     if ((!userProfile.getIsAdmin()) && (userProfile.getUser() != null) && (userProfile.getUser().getId() > 0L) && (candidateAssets != null) && (candidateAssets.size() > 0))
/*     */     {
/* 267 */       AssetApprovalSearchCriteria query = new AssetApprovalSearchCriteria();
/* 268 */       query.setUserId(userProfile.getUser().getId());
/* 269 */       query.setApprovalStatusId(2L);
/* 270 */       itBox = candidateAssets.iterator();
/* 271 */       while (itBox.hasNext())
/*     */       {
/* 273 */         AssetInList ail = (AssetInList)itBox.next();
/* 274 */         query.addAssetId(ail.getAsset().getId());
/*     */ 
/* 277 */         if ((!bRequireHighResApproval) && (this.m_assetManager.userMustRequestApprovalForHighRes(userProfile, this.m_assetManager.getAsset(a_dbTransaction, ail.getAsset().getId(), null, false, false))))
/*     */         {
/* 279 */           bRequireHighResApproval = true;
/*     */         }
/*     */       }
/* 282 */       Vector vecApprovals = this.m_approvalManager.getAssetApprovalList(a_dbTransaction, query, userProfile.getCurrentLanguage());
/* 283 */       form.setAssetApprovals(vecApprovals);
/*     */ 
/* 285 */       query.setUserId(userProfile.getUser().getId());
/* 286 */       query.setApprovalStatusId(4L);
/* 287 */       vecHighResApprovals = this.m_approvalManager.getAssetApprovalList(a_dbTransaction, query, userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 290 */     form.setAssets(candidateAssets);
/* 291 */     form.setTotalFileSize(iSize);
/* 292 */     form.setFileFormats(sFileTypeCounts);
/* 293 */     form.setCanDownloadAllAssets(bCanDownloadAllAssets);
/* 294 */     form.setUserCanDownloadOriginal(bCanDownloadOriginal);
/* 295 */     form.setUserCanDownloadAdvanced(bCanDownloadAdvanced);
/* 296 */     form.setUserCanDownloadOriginalAny(bCanDownloadOriginalAny);
/* 297 */     form.setUserCanDownloadAdvancedAny(bCanDownloadAdvancedAny);
/* 298 */     form.setDownloadOriginalCount(iDownloadOriginalCount);
/* 299 */     form.setExcludedIds(sExcludedIds);
/* 300 */     form.setUserMustRequestApprovalForHighRes(bRequireHighResApproval);
/*     */ 
/* 302 */     boolean bAdvanced = Boolean.parseBoolean(a_request.getParameter("advanced"));
/* 303 */     form.setAdvanced(bAdvanced);
/*     */ 
/* 307 */     setUsageSelectorInfo(a_dbTransaction, form, userProfile.getUsageExclusions(), vecApprovedAssets, userProfile);
/*     */ 
/* 310 */     setUsageFormats(a_dbTransaction, form, userProfile);
/*     */ 
/* 313 */     if ((form.getUsageTypeFormats() != null) && (!form.getUsageTypeFormats().isEmpty()) && (!form.getConvertToRGB()))
/*     */     {
/* 315 */       UsageTypeFormat firstUtf = (UsageTypeFormat)form.getUsageTypeFormats().firstElement();
/*     */ 
/* 317 */       form.setConvertToRGB(false);
/*     */ 
/* 319 */       if ((firstUtf != null) && (firstUtf.getColorSpace() > 0L))
/*     */       {
/* 321 */         long lSelectedColorSpace = firstUtf.getColorSpace();
/* 322 */         if (lSelectedColorSpace == 1L)
/*     */         {
/* 324 */           form.setConvertToRGB(true);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 331 */     if (this.m_attributeManager.downloadAttributesExist())
/*     */     {
/* 333 */       int iMaxDownloadAttributes = 0;
/* 334 */       for (AssetInList ail : candidateAssets)
/*     */       {
/* 336 */         Asset asset = ail.getAsset();
/* 337 */         for (AttributeValue attVal : asset.getAttributeValues())
/*     */         {
/* 339 */           if (attVal.getAttribute().getShowOnDownload())
/*     */           {
/* 341 */             ail.getDownloadAttributes().add(attVal);
/*     */           }
/*     */         }
/* 344 */         iMaxDownloadAttributes = Math.max(iMaxDownloadAttributes, ail.getDownloadAttributes().size());
/*     */       }
/* 346 */       form.setMaxDownloadAttributes(iMaxDownloadAttributes);
/*     */     }
/*     */ 
/* 350 */     if (candidateAssets.size() == vecHighResApprovals.size())
/*     */     {
/* 352 */       form.setHighResDirectDownload(true);
/* 353 */       afForward = a_mapping.findForward("HighResDownload");
/*     */     }
/*     */     else
/*     */     {
/* 357 */       if (vecHighResApprovals.size() > 0)
/*     */       {
/* 359 */         form.setSomeAssetsRequireHighResApproval(true);
/*     */       }
/* 361 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 365 */     form.setColorSpaces(this.m_usageManager.getColorSpaces(a_dbTransaction, -1, true));
/*     */ 
/* 367 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewDownloadAssetsAction
 * JD-Core Version:    0.6.0
 */