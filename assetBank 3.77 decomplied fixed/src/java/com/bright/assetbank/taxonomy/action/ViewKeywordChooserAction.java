/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.taxonomy.constant.KeywordConstants;
/*     */ import com.bright.assetbank.taxonomy.form.KeywordChooserForm;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
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
/*     */ public class ViewKeywordChooserAction extends BTransactionAction
/*     */   implements KeywordConstants, AssetBankConstants, CategoryConstants
/*     */ {
/*     */   private static final String c_ksClassName = "ViewKeywordChooserAction";
/*  59 */   protected TaxonomyManager m_taxonomyManager = null;
/*  60 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     String ksMethodName = "execute";
/*  83 */     ActionForward afForward = null;
/*  84 */     KeywordChooserForm form = (KeywordChooserForm)a_form;
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  88 */     String sFilter = getFilter(a_request);
/*  89 */     form.setFilter(sFilter);
/*     */ 
/*  92 */     if (!StringUtil.stringIsPopulated(sFilter))
/*     */     {
/*  94 */       sFilter = "a";
/*  95 */       form.setFilter(sFilter);
/*     */     }
/*  97 */     if ((sFilter.compareToIgnoreCase("all") == 0) || (!userProfile.getCurrentLanguage().getUsesLatinAlphabet()))
/*     */     {
/*  99 */       sFilter = "";
/*     */     }
/*     */ 
/* 103 */     boolean bBrowsableOnly = false;
/* 104 */     String sBrowsableOnly = a_request.getParameter("browse");
/* 105 */     if (StringUtil.stringIsPopulated(sBrowsableOnly))
/*     */     {
/* 107 */       bBrowsableOnly = true;
/*     */     }
/*     */ 
/* 110 */     boolean bIncludeExpired = false;
/* 111 */     String sIncludeExpired = a_request.getParameter("expired");
/* 112 */     if (StringUtil.stringIsPopulated(sIncludeExpired))
/*     */     {
/* 114 */       bIncludeExpired = true;
/*     */     }
/*     */ 
/* 118 */     long lTreeId = getTreeId(a_request);
/*     */ 
/* 120 */     if (lTreeId <= 0L)
/*     */     {
/* 122 */       this.m_logger.error("ViewKeywordChooserAction.execute : No tree id supplied");
/* 123 */       throw new Bn2Exception("ViewKeywordChooserAction.execute : No tree id supplied");
/*     */     }
/* 125 */     form.setCategoryTreeId(lTreeId);
/*     */ 
/* 128 */     Vector vKeywords = this.m_taxonomyManager.getKeywordsForFilter(a_dbTransaction, sFilter, lTreeId, bBrowsableOnly, bIncludeExpired, userProfile.getCurrentLanguage());
/* 129 */     form.setBrowserName(this.m_taxonomyManager.getAttributeNameForKeywordTree(a_dbTransaction, String.valueOf(lTreeId)));
/*     */ 
/* 132 */     if (AssetBankSettings.getSupportMultiLanguage())
/*     */     {
/* 134 */       LanguageUtils.setLanguageOnAll(vKeywords, userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 137 */     form.setKeywordList(vKeywords);
/*     */ 
/* 139 */     form.setRootCategoryName(this.m_listManager.getListItem("keyword-root", userProfile.getCurrentLanguage()).getBody());
/*     */ 
/* 141 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 143 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected long getTreeId(HttpServletRequest a_request)
/*     */   {
/* 148 */     return getLongParameter(a_request, "categoryTypeId");
/*     */   }
/*     */ 
/*     */   protected String getFilter(HttpServletRequest a_request)
/*     */   {
/* 153 */     return a_request.getParameter("filter");
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 158 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager taxonomyManager)
/*     */   {
/* 163 */     this.m_taxonomyManager = taxonomyManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.ViewKeywordChooserAction
 * JD-Core Version:    0.6.0
 */