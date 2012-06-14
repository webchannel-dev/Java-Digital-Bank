/*     */ package com.bright.framework.search.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.search.constant.SearchConstants;
/*     */ import com.bright.framework.search.exception.SearchCriteriaException;
/*     */ import com.bright.framework.search.form.SearchForm;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class SearchAction extends Bn2Action
/*     */   implements CommonConstants, SearchConstants, FrameworkConstants
/*     */ {
/*  50 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   protected abstract SearchQuery getSearchCriteria(HttpServletRequest paramHttpServletRequest)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  85 */     int iPageIndex = 0;
/*  86 */     int iPageSize = 0;
/*  87 */     SearchResults results = null;
/*  88 */     SearchQuery searchCriteria = null;
/*  89 */     SearchForm searchForm = null;
/*  90 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  92 */     searchForm = (SearchForm)a_form;
/*     */     try
/*     */     {
/*  96 */       this.m_logger.debug("In SearchAction : execute method");
/*     */       try
/*     */       {
/* 101 */         searchCriteria = getSearchCriteria(a_request);
/*     */       }
/*     */       catch (SearchCriteriaException sce)
/*     */       {
/* 105 */         this.m_logger.error("Error whilst getting search criteria in SearchAction.execute()");
/* 106 */         return a_mapping.findForward("SearchCriteriaError");
/*     */       }
/*     */ 
/* 110 */       iPageIndex = getIntParameter(a_request, "page");
/*     */ 
/* 113 */       if (iPageIndex < 0)
/*     */       {
/* 115 */         iPageIndex = 0;
/*     */       }
/*     */ 
/* 119 */       iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/* 122 */       if (iPageSize <= 0)
/*     */       {
/* 124 */         iPageSize = AssetBankSettings.getDefaultNumResultsPerPage();
/*     */       }
/*     */ 
/*     */       do
/*     */       {
/* 130 */         results = this.m_searchManager.searchByPageIndex(searchCriteria, iPageIndex, iPageSize, userProfile.getCurrentLanguage().getCode());
/*     */       }
/* 132 */       while ((results.getNumResults() > 0) && (results.getNumResultsPopulated() == 0) && (iPageIndex-- > 0));
/*     */ 
/* 134 */       searchForm.setResults(results);
/* 135 */       searchForm.setCriteria(searchCriteria);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 139 */       this.m_logger.error("Error in SearchAction.execute: ", e);
/*     */ 
/* 142 */       throw e;
/*     */     }
/*     */ 
/* 145 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 161 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.action.SearchAction
 * JD-Core Version:    0.6.0
 */