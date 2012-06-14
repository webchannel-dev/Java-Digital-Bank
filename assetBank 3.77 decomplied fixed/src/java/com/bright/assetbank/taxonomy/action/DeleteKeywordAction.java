/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.DeleteCategoryForm;
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
/*     */ public class DeleteKeywordAction extends BTransactionAction
/*     */   implements CategoryConstants, AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "DeleteKeywordAction";
/*  50 */   private TaxonomyManager m_taxonomyManager = null;
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager) {
/*  53 */     this.m_taxonomyManager = a_taxonomyManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     String ksMethodName = "execute";
/*  78 */     ActionForward afForward = null;
/*  79 */     DeleteCategoryForm deleteCategoryForm = (DeleteCategoryForm)a_form;
/*     */ 
/*  81 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  84 */     long lTreeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/*  87 */     String sFilter = a_request.getParameter("filter");
/*     */ 
/*  89 */     if (lTreeId <= 0L)
/*     */     {
/*  91 */       this.m_logger.error("DeleteKeywordAction.execute : No tree id supplied");
/*  92 */       throw new Bn2Exception("DeleteKeywordAction.execute : No tree id supplied");
/*     */     }
/*     */ 
/*  95 */     this.m_taxonomyManager.deleteKeyword(a_dbTransaction, deleteCategoryForm.getCategoryIdToDelete(), lTreeId, userProfile.getUser().getId());
/*     */ 
/*  97 */     String sQueryString = "categoryTypeId=" + lTreeId + "&" + "filter" + "=" + sFilter;
/*  98 */     long lParentCatId = getLongParameter(a_request, "categoryId");
/*  99 */     if (lParentCatId > 0L)
/*     */     {
/* 101 */       sQueryString = sQueryString + "&categoryId=" + lParentCatId;
/*     */     }
/* 103 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 104 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.DeleteKeywordAction
 * JD-Core Version:    0.6.0
 */