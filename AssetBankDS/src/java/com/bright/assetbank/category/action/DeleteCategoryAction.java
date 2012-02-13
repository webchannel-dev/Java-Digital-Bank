/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.DeleteCategoryForm;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DeleteCategoryAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants
/*     */ {
/*  54 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager) {
/*  57 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  81 */     ActionForward afForward = null;
/*  82 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  83 */     DeleteCategoryForm deleteCategoryForm = (DeleteCategoryForm)a_form;
/*  84 */     long lParentCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  86 */     List ancestors = null;
/*     */ 
/*  89 */     if (lParentCatId != -1L)
/*     */     {
/*  91 */       ancestors = this.m_categoryManager.getAncestors(a_dbTransaction, 1L, lParentCatId);
/*     */     }
/*     */ 
/*  95 */     if ((!userProfile.getIsLoggedIn()) || (!userProfile.getCanEditSubcategories((int)lParentCatId, ancestors)))
/*     */     {
/*  97 */       this.m_logger.error("DeleteCategoryAction.execute : User does not have admin permission : " + userProfile);
/*  98 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 102 */     this.m_categoryManager.deleteCategory(a_dbTransaction, 1L, deleteCategoryForm.getCategoryIdToDelete(), deleteCategoryForm.getCategoryIdToMoveTo(), userProfile.getUser().getId());
/*     */ 
/* 108 */     String sQueryString = "";
/* 109 */     if (lParentCatId > 0L)
/*     */     {
/* 111 */       sQueryString = sQueryString + "categoryId=" + lParentCatId;
/*     */     }
/*     */ 
/* 114 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 115 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.DeleteCategoryAction
 * JD-Core Version:    0.6.0
 */