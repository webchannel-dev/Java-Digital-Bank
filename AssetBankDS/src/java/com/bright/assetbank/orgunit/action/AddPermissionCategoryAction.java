/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.category.action.AddExtendedCategoryAction;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.form.OrgUnitCategoryAdminForm;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddPermissionCategoryAction extends AddExtendedCategoryAction
/*     */   implements OrgUnitConstants
/*     */ {
/*  53 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  59 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  56 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  62 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     ActionForward afForward = null;
/*  73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  74 */     OrgUnitCategoryAdminForm form = (OrgUnitCategoryAdminForm)a_form;
/*     */ 
/*  77 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  79 */       this.m_logger.error("AddPermissionCategoryAction.execute : User not logged in.");
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  82 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  86 */     long lOrgUnitId = getLongParameter(a_request, "ouid");
/*  87 */     long lParentCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  89 */     if (lOrgUnitId > 0L)
/*     */     {
/*  92 */       if ((userProfile.getIsAdmin()) || ((userProfile.getIsOrgUnitAdmin()) && (user.getIsAdminOfOrgUnit(lOrgUnitId))))
/*     */       {
/*  95 */         OrgUnit ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, lOrgUnitId);
/*  96 */         form.setOrgUnit(ou);
/*     */ 
/*  98 */         if (lParentCatId > 0L)
/*     */         {
/* 101 */           if (!ou.containsCategory(lParentCatId))
/*     */           {
/* 103 */             this.m_logger.error("AddPermissionCategoryAction.execute : Category not in the given org unit.");
/* 104 */             return a_mapping.findForward("NoPermission");
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 110 */           lParentCatId = ou.getCategory().getId();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 116 */         this.m_logger.error("AddPermissionCategoryAction.execute : User does not have admin permission for the given org unit: " + lOrgUnitId + " : " + userProfile);
/* 117 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/* 123 */     else if (!userProfile.getIsAdmin())
/*     */     {
/* 125 */       this.m_logger.error("AddPermissionCategoryAction.execute : User does not have admin permission to browse all permissions categories: " + userProfile);
/* 126 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 131 */     Category newCategory = form.getNewCategory();
/*     */ 
/* 134 */     if (lParentCatId > 0L)
/*     */     {
/* 136 */       Category parent = getCategoryManager().getCategory(a_dbTransaction, 2L, lParentCatId);
/* 137 */       if ((parent != null) && (StringUtil.stringIsPopulated(parent.getWorkflowName())))
/*     */       {
/* 139 */         newCategory.setWorkflowName(parent.getWorkflowName());
/*     */       }
/*     */     }
/*     */ 
/* 143 */     boolean bValid = saveCategory(a_dbTransaction, a_request, newCategory, form, userProfile, lParentCatId, 2L);
/*     */ 
/* 145 */     if (bValid)
/*     */     {
/* 150 */       if (!commitTransaction(a_dbTransaction))
/*     */       {
/* 153 */         return a_mapping.findForward("SystemFailure");
/*     */       }
/*     */ 
/* 157 */       a_dbTransaction = getNewTransaction();
/*     */       try
/*     */       {
/* 163 */         this.m_orgUnitManager.setDefaultPermissions(a_dbTransaction, newCategory.getId(), lParentCatId);
/*     */ 
/* 166 */         this.m_userManager.setCategoryPermissions(a_dbTransaction.getConnection(), userProfile, userProfile.getUser().getId());
/*     */       }
/*     */       finally
/*     */       {
/* 173 */         commitTransaction(a_dbTransaction);
/*     */       }
/*     */     }
/*     */ 
/* 177 */     String sQueryString = "ouid=" + lOrgUnitId;
/* 178 */     if ((!form.getExtendedCategory()) || (!bValid))
/*     */     {
/* 180 */       if (lParentCatId > 0L)
/*     */       {
/* 182 */         sQueryString = sQueryString + "&categoryId=" + lParentCatId;
/*     */       }
/*     */ 
/* 186 */       sQueryString = sQueryString + "&categoryTypeId=2";
/*     */ 
/* 188 */       if (!bValid)
/*     */       {
/* 190 */         afForward = createForward(sQueryString, a_mapping, "Failure");
/*     */       }
/*     */       else
/*     */       {
/* 194 */         afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 199 */       afForward = getExtendedCategoryForward(null, a_request, a_mapping, newCategory, sQueryString, lParentCatId);
/*     */     }
/*     */ 
/* 202 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.AddPermissionCategoryAction
 * JD-Core Version:    0.6.0
 */