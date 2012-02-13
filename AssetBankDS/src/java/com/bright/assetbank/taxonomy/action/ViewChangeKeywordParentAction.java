/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.ChangeCategoryParentForm;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewChangeKeywordParentAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants
/*     */ {
/*     */   private static final String c_ksClassName = "ViewChangeKeywordParentAction";
/*  49 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     String ksMethodName = "execute";
/*  73 */     ActionForward afForward = null;
/*  74 */     ChangeCategoryParentForm form = (ChangeCategoryParentForm)a_form;
/*     */ 
/*  77 */     long lTreeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/*  79 */     if (lTreeId <= 0L)
/*     */     {
/*  81 */       this.m_logger.error("ViewChangeKeywordParentAction.execute : No tree id supplied");
/*  82 */       throw new Bn2Exception("ViewChangeKeywordParentAction.execute : No tree id supplied");
/*     */     }
/*     */ 
/*  86 */     Category category = this.m_categoryManager.getCategory(a_dbTransaction, lTreeId, form.getCategoryIdToMove());
/*     */ 
/*  89 */     form.setCategory(category);
/*     */ 
/*  92 */     FlatCategoryList flatCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, lTreeId);
/*     */ 
/*  94 */     form.setFlatCategoryList(flatCategoryList);
/*     */ 
/*  96 */     afForward = a_mapping.findForward("Success");
/*  97 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/* 102 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.ViewChangeKeywordParentAction
 * JD-Core Version:    0.6.0
 */