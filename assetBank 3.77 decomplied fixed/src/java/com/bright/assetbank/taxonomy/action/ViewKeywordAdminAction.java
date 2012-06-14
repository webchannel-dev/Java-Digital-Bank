/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.taxonomy.constant.KeywordConstants;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.action.ViewCategoryAdminAction;
/*     */ import com.bright.framework.category.form.CategoryAdminForm;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewKeywordAdminAction extends ViewCategoryAdminAction
/*     */   implements AssetBankConstants, KeywordConstants
/*     */ {
/*     */   private static final String c_ksClassName = "ViewKeywordAdminAction";
/*  54 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     String ksMethodName = "execute";
/*  76 */     ActionForward afForward = null;
/*  77 */     CategoryAdminForm form = (CategoryAdminForm)a_form;
/*  78 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  81 */     String sFilter = a_request.getParameter("filter");
/*     */ 
/*  84 */     if (!StringUtil.stringIsPopulated(sFilter))
/*     */     {
/*  86 */       sFilter = "a";
/*     */     }
/*  88 */     if ((sFilter.compareToIgnoreCase("all") == 0) || (!userProfile.getCurrentLanguage().getUsesLatinAlphabet()))
/*     */     {
/*  90 */       sFilter = "";
/*     */     }
/*     */ 
/*  94 */     long lCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  97 */     long lTreeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/*  99 */     if (lTreeId <= 0L)
/*     */     {
/* 102 */       if (a_request.getAttribute("categoryTypeId") != null)
/*     */       {
/* 104 */         lTreeId = ((Long)a_request.getAttribute("categoryTypeId")).longValue();
/*     */       }
/*     */ 
/* 107 */       if (lTreeId <= 0L)
/*     */       {
/* 109 */         this.m_logger.error("ViewKeywordAdminAction.execute : No tree id supplied");
/* 110 */         throw new Bn2Exception("ViewKeywordAdminAction.execute : No tree id supplied");
/*     */       }
/*     */     }
/*     */ 
/* 114 */     super.getCategories(form, a_dbTransaction, lTreeId, lCatId, null);
/*     */ 
/* 117 */     if (StringUtil.stringIsPopulated(sFilter))
/*     */     {
/* 119 */       Vector vecFiltered = super.filterCategories(form.getSubCategoryList(), sFilter);
/* 120 */       form.setSubCategoryList(vecFiltered);
/*     */     }
/*     */ 
/* 124 */     if (AssetBankSettings.getSupportMultiLanguage())
/*     */     {
/* 126 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getNewCategory());
/*     */     }
/*     */ 
/* 129 */     afForward = a_mapping.findForward("Success");
/* 130 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 135 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.ViewKeywordAdminAction
 * JD-Core Version:    0.6.0
 */