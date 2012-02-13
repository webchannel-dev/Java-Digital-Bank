/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.DeleteCategoryForm;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewDeleteCategoryAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants
/*     */ {
/*  51 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  73 */     long lParentCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  75 */     List ancestors = null;
/*     */ 
/*  78 */     if (lParentCatId != -1L)
/*     */     {
/*  80 */       ancestors = this.m_categoryManager.getAncestors(a_dbTransaction, 1L, lParentCatId);
/*     */     }
/*     */ 
/*  84 */     if ((!userProfile.getIsLoggedIn()) || (!userProfile.getCanEditSubcategories((int)lParentCatId, ancestors)))
/*     */     {
/*  86 */       this.m_logger.error("ViewDeleteCategoryAction.execute : User does not have admin permission : " + userProfile);
/*  87 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  90 */     DeleteCategoryForm form = (DeleteCategoryForm)a_form;
/*     */ 
/*  93 */     Category category = this.m_categoryManager.getCategory(a_dbTransaction, 1L, form.getCategoryIdToDelete());
/*  94 */     form.setCategory(category);
/*     */ 
/*  97 */     if (category.getNumChildCategories() <= 0)
/*     */     {
/* 100 */       FlatCategoryList flatCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 1L);
/* 101 */       form.setFlatCategoryList(flatCategoryList);
/*     */     }
/*     */ 
/* 104 */     afForward = a_mapping.findForward("Success");
/* 105 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 110 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.ViewDeleteCategoryAction
 * JD-Core Version:    0.6.0
 */