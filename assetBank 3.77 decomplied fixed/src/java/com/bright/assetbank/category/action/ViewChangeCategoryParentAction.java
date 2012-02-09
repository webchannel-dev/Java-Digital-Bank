/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.ChangeCategoryParentForm;
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
/*     */ public class ViewChangeCategoryParentAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants
/*     */ {
/*  48 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager) {
/*  51 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     ActionForward afForward = null;
/*  81 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  82 */     ChangeCategoryParentForm form = (ChangeCategoryParentForm)a_form;
/*     */ 
/*  85 */     long lCategoryTypeId = form.getCategoryTypeId();
/*     */ 
/*  87 */     if (lCategoryTypeId <= 0L)
/*     */     {
/*  90 */       lCategoryTypeId = 1L;
/*     */     }
/*     */ 
/*  94 */     if ((!userProfile.getIsLoggedIn()) || (!userProfile.getIsAdmin()))
/*     */     {
/*  96 */       this.m_logger.error("ViewChangeCategoryParentAction.execute : User does not have admin permission : " + userProfile);
/*  97 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 101 */     Category category = this.m_categoryManager.getCategory(a_dbTransaction, lCategoryTypeId, form.getCategoryIdToMove());
/* 102 */     form.setCategory(category);
/*     */ 
/* 105 */     FlatCategoryList flatCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, lCategoryTypeId);
/* 106 */     form.setFlatCategoryList(flatCategoryList);
/*     */ 
/* 108 */     afForward = a_mapping.findForward("Success");
/* 109 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.ViewChangeCategoryParentAction
 * JD-Core Version:    0.6.0
 */