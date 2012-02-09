/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MovePermissionCategoryAction extends BTransactionAction
/*     */   implements OrgUnitConstants, CategoryConstants
/*     */ {
/*  46 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager) {
/*  49 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  79 */       this.m_logger.error("MovePermissionCategoryAction.execute : User not logged in.");
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  82 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  86 */     long lOrgUnitId = getLongParameter(a_request, "ouid");
/*  87 */     long lCategoryId = getIntParameter(a_request, "catIdToMove");
/*  88 */     String sDirection = a_request.getParameter("direction");
/*     */ 
/*  90 */     if (lOrgUnitId > 0L)
/*     */     {
/*  92 */       if ((!userProfile.getIsAdmin()) && ((!userProfile.getIsOrgUnitAdmin()) || (!user.getIsAdminOfOrgUnit(lOrgUnitId))))
/*     */       {
/*  94 */         this.m_logger.error("ViewPermissionCategoriesAction.execute : User does not have admin permission for the given org unit: " + lOrgUnitId + " : " + userProfile);
/*  95 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/* 101 */     else if (!userProfile.getIsAdmin())
/*     */     {
/* 103 */       this.m_logger.error("ViewPermissionCategoriesAction.execute : User does not have admin permission to browse all permissions categories: " + userProfile);
/* 104 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 109 */     this.m_categoryManager.moveCategoryInSequence(a_dbTransaction, 2L, lCategoryId, !"down".equalsIgnoreCase(sDirection));
/*     */ 
/* 111 */     String sQueryString = "ouid=" + lOrgUnitId;
/* 112 */     long lParentCatId = getLongParameter(a_request, "categoryId");
/* 113 */     if (lParentCatId > 0L)
/*     */     {
/* 115 */       sQueryString = sQueryString + "&categoryId=" + lParentCatId;
/*     */     }
/* 117 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 119 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.MovePermissionCategoryAction
 * JD-Core Version:    0.6.0
 */