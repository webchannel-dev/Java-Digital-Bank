/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.form.DeletePermissionCategoryForm;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DeletePermissionCategoryAction extends BTransactionAction
/*     */   implements OrgUnitConstants, CategoryConstants
/*     */ {
/*  49 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager) {
/*  52 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     ActionForward afForward = null;
/*  63 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  64 */     DeletePermissionCategoryForm deletePermissionCategoryForm = (DeletePermissionCategoryForm)a_form;
/*     */ 
/*  67 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  69 */       this.m_logger.error("MovePermissionCategoryAction.execute : User not logged in.");
/*  70 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  72 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  76 */     long lOrgUnitId = getLongParameter(a_request, "ouid");
/*     */ 
/*  78 */     if (lOrgUnitId > 0L)
/*     */     {
/*  80 */       if ((!userProfile.getIsAdmin()) && ((!userProfile.getIsOrgUnitAdmin()) || (!user.getIsAdminOfOrgUnit(lOrgUnitId))))
/*     */       {
/*  82 */         this.m_logger.error("ViewPermissionCategoriesAction.execute : User does not have admin permission for the given org unit: " + lOrgUnitId + " : " + userProfile);
/*  83 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/*  89 */     else if (!userProfile.getIsAdmin())
/*     */     {
/*  91 */       this.m_logger.error("ViewPermissionCategoriesAction.execute : User does not have admin permission to browse all permissions categories: " + userProfile);
/*  92 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  98 */     this.m_categoryManager.deleteCategory(a_dbTransaction, 2L, deletePermissionCategoryForm.getCategoryIdToDelete(), deletePermissionCategoryForm.getCategoryIdToMoveTo(), userProfile.getUser().getId());
/*     */ 
/* 104 */     String sQueryString = "ouid=" + lOrgUnitId;
/* 105 */     long lParentCatId = getLongParameter(a_request, "categoryId");
/* 106 */     if (lParentCatId > 0L)
/*     */     {
/* 108 */       sQueryString = sQueryString + "&categoryId=" + lParentCatId;
/*     */     }
/* 110 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 112 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.DeletePermissionCategoryAction
 * JD-Core Version:    0.6.0
 */