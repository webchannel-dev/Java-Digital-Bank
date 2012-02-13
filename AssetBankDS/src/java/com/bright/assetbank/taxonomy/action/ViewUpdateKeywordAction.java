/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.category.form.ABCategoryForm;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.framework.category.action.ViewUpdateCategoryAction;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.Category.Translation;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUpdateKeywordAction extends ViewUpdateCategoryAction
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "ViewUpdateKeywordAction";
/*  49 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     String ksMethodName = "execute";
/*  71 */     ActionForward afForward = null;
/*     */ 
/*  73 */     ABCategoryForm form = (ABCategoryForm)a_form;
/*  74 */     long lCatId = getLongParameter(a_request, "categoryId");
/*  75 */     long lTreeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/*  77 */     if (lTreeId <= 0L)
/*     */     {
/*  79 */       this.m_logger.error("ViewUpdateKeywordAction.execute : No tree id supplied");
/*  80 */       throw new Bn2Exception("ViewUpdateKeywordAction.execute : No tree id supplied");
/*     */     }
/*     */ 
/*  83 */     super.getCategory(form, a_dbTransaction, lTreeId, lCatId);
/*     */ 
/*  86 */     String sSynonyms = TaxonomyManager.topAndTailDelimiters(form.getCategory().getDescription());
/*  87 */     form.getCategory().setDescription(sSynonyms);
/*  88 */     Iterator itTranslations = form.getCategory().getTranslations().iterator();
/*  89 */     while (itTranslations.hasNext())
/*     */     {
/*  91 */       Category.Translation translation = (Category.Translation)itTranslations.next();
/*  92 */       translation.setDescription(TaxonomyManager.topAndTailDelimiters(translation.getDescription()));
/*     */     }
/*     */ 
/*  96 */     this.m_languageManager.ensureTranslations(a_dbTransaction, form.getCategory());
/*     */ 
/*  98 */     afForward = a_mapping.findForward("Success");
/*  99 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 104 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.ViewUpdateKeywordAction
 * JD-Core Version:    0.6.0
 */