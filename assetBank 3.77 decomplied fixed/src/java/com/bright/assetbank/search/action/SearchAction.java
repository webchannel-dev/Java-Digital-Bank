/*     */ package com.bright.assetbank.search.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.externalfilter.service.ExternalFilterManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.form.BaseSearchForm;
/*     */ import com.bright.assetbank.search.form.SearchForm;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.BaseSearchQuery;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class SearchAction extends BaseSearchAction
/*     */   implements UserConstants
/*     */ {
/*     */   protected SearchQuery getNewSearchCriteria(DBTransaction a_dbTransaction, BaseSearchForm a_form, HttpServletRequest a_request)
/*     */     throws ParseException, Bn2Exception
/*     */   {
/*  99 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 100 */     SearchForm searchForm = (SearchForm)a_form;
/* 101 */     SearchCriteria searchCriteria = null;
/*     */ 
/* 104 */     searchCriteria = searchForm.populateSearchCriteria(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/* 107 */     searchCriteria.setMaxNoOfResults(FrameworkSettings.getMaxNoOfSearchResults());
/*     */ 
/* 110 */     String sDefaultOperator = AssetBankSettings.getDefaultSearchOperator();
/* 111 */     if (StringUtil.stringIsPopulated(sDefaultOperator))
/*     */     {
/* 113 */       searchCriteria.setDefaultOperator(sDefaultOperator);
/*     */     }
/*     */ 
/* 118 */     searchCriteria.setupPermissions(userProfile);
/*     */ 
/* 121 */     Enumeration params = a_request.getParameterNames();
/* 122 */     while (params.hasMoreElements())
/*     */     {
/* 124 */       String sName = (String)params.nextElement();
/* 125 */       if (sName.startsWith("approvalStatus"))
/*     */       {
/* 128 */         String sStatus = a_request.getParameter(sName);
/* 129 */         String[] aStatus = sStatus.split(",");
/* 130 */         if ((aStatus != null) && (aStatus.length > 0))
/*     */         {
/* 132 */           for (int i = 0; i < aStatus.length; i++)
/*     */           {
/* 134 */             searchCriteria.addApprovalStatus(Integer.parseInt(aStatus[i]));
/* 135 */             searchCriteria.setPerformingApprovalSearch(true);
/*     */           }
/*     */         }
/*     */       }
/* 139 */       else if (sName.startsWith("visibleToGroup"))
/*     */       {
/* 141 */         long lId = Long.parseLong(a_request.getParameter(sName));
/* 142 */         searchCriteria.addGroupRestriction(lId);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 149 */     if ((searchCriteria.getGroupRestrictions().size() > 0) && ((searchCriteria.getGroupRestrictions().size() != 1) || (!searchCriteria.getContainsGroupRestriction(2L, false))) && (!searchCriteria.getContainsGroupRestriction(1L, false)))
/*     */     {
/* 153 */       searchCriteria.addImplicitGroupRestriction(1L);
/*     */     }
/*     */ 
/* 156 */     if ((searchCriteria.getGroupRestrictions().size() > 0) && (!searchCriteria.getContainsGroupRestriction(2L, false)))
/*     */     {
/* 159 */       searchCriteria.addImplicitGroupRestriction(2L);
/*     */     }
/*     */ 
/* 164 */     if ((!searchCriteria.getPerformingApprovalSearch().booleanValue()) && (userProfile.getIsAdmin()))
/*     */     {
/* 166 */       searchCriteria.setApprovalStatuses(null);
/*     */     }
/*     */ 
/* 170 */     if (searchForm.getCompleteness() > 0)
/*     */     {
/* 173 */       if (searchForm.getCompleteness() == 2L)
/*     */       {
/* 175 */         searchCriteria.setIsComplete(Boolean.TRUE);
/*     */       }
/* 177 */       else if (searchForm.getCompleteness() == 3L)
/*     */       {
/* 179 */         searchCriteria.setIsComplete(Boolean.FALSE);
/*     */       }
/* 181 */       if (searchForm.getCompleteness() == 1L)
/*     */       {
/* 184 */         searchCriteria.setIsComplete(null);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 189 */     Vector allAttributes = null;
/*     */ 
/* 192 */     if ((searchForm.getSelectedFilter() != null) && (searchForm.getSelectedFilter().getId() > 0L))
/*     */     {
/* 194 */       allAttributes = this.m_assetManager.getAssetAttributes(a_dbTransaction, null);
/*     */     }
/* 197 */     else if (userProfile.getIsAdmin())
/*     */     {
/* 199 */       allAttributes = this.m_assetManager.getAssetAttributes(a_dbTransaction, null);
/*     */     }
/*     */     else
/*     */     {
/* 204 */       allAttributes = this.m_assetManager.getAssetAttributes(a_dbTransaction, userProfile.getVisibleAttributeIds());
/*     */     }
/*     */ 
/* 207 */     SearchUtil.getAndSetAttributeSearches(allAttributes, a_request, searchCriteria);
/*     */ 
/* 209 */     searchCriteria.setBulkUpload(searchForm.getBulkUpload());
/*     */ 
/* 212 */     int iAdvancedSearch = getIntParameter(a_request, "advancedSearch");
/* 213 */     if (iAdvancedSearch <= 0)
/*     */     {
/* 215 */       searchCriteria.setSelectedFilters(userProfile.getSelectedFilters());
/*     */     }
/*     */ 
/* 218 */     searchCriteria.setIncludePreviousVersions(searchForm.getIncludePreviousVersions());
/*     */ 
/* 220 */     if ((AssetBankSettings.getAssetEntitiesEnabled()) && (!searchCriteria.assetEntityIncludedInSearch(-999L)))
/*     */     {
/* 222 */       searchCriteria.setAssetEntityIdsToExclude(this.m_assetEntityManager.getEntityIdsExcludedFromSearch(a_dbTransaction, a_request.getParameter("quickSearch") != null));
/*     */     }
/*     */ 
/* 226 */     if (searchForm.getAverageRating() >= 0.0D)
/*     */     {
/* 228 */       searchCriteria.setAverageRating(searchForm.getAverageRating());
/*     */     }
/*     */ 
/* 231 */     if ((searchForm.getMaximumVotes() > 0) || (searchForm.getMinimumVotes() > 0))
/*     */     {
/* 233 */       searchCriteria.setMaximumVotes(searchForm.getMaximumVotes());
/* 234 */       searchCriteria.setMinimumVotes(searchForm.getMinimumVotes());
/*     */     }
/*     */ 
/* 237 */     return searchCriteria;
/*     */   }
/*     */ 
/*     */   protected void populateForm(SearchQuery a_query, BaseSearchForm a_form)
/*     */   {
/* 242 */     BaseSearchQuery criteria = (BaseSearchQuery)a_query;
/* 243 */     a_form.setKeywords(criteria.getKeywords());
/*     */   }
/*     */ 
/*     */   protected void prepareSearchCriteriaBeforeSearch(HttpServletRequest a_request, SearchQuery a_searchQuery)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected boolean searchCriteriaSpecified(BaseSearchForm a_searchForm, SearchQuery a_searchQuery)
/*     */   {
/* 270 */     SearchCriteria criteria = (SearchCriteria)a_searchQuery;
/* 271 */     SearchForm searchForm = (SearchForm)a_searchForm;
/*     */ 
/* 310 */     return (StringUtil.stringIsPopulated(searchForm.getKeywords())) || (StringUtil.stringIsPopulated(searchForm.getAssetIds())) || (StringUtil.stringIsPopulated(searchForm.getTitle())) || (StringUtil.stringIsPopulated(searchForm.getFilename())) || (StringUtil.stringIsPopulated(searchForm.getAllCategoryIds())) || (StringUtil.stringIsPopulated(searchForm.getAddedBy())) || (StringUtil.stringIsPopulated(searchForm.getDateAddedLower())) || (StringUtil.stringIsPopulated(searchForm.getDateAddedUpper())) || (StringUtil.stringIsPopulated(searchForm.getDateModLower())) || (StringUtil.stringIsPopulated(searchForm.getDateModUpper())) || (StringUtil.stringIsPopulated(searchForm.getDateDownloadedUpper())) || (StringUtil.stringIsPopulated(searchForm.getDateDownloadedLower())) || ((searchForm.getSelectedEntities() != null) && (searchForm.getSelectedEntities().length > 0)) || (searchForm.getOrientation() > 0) || (searchForm.getEmptyFileStatus() > 0) || (searchForm.getBulkUpload() > 0L) || (searchForm.getPriceLower().getIsFormAmountEntered()) || (searchForm.getPriceUpper().getIsFormAmountEntered()) || (searchForm.getCompleteness() > 1L) || ((criteria.getAttributeSearches() != null) && (criteria.getAttributeSearches().size() != 0)) || (criteria.hasDateRanges()) || (criteria.hasNumberRanges()) || (criteria.getWithoutCategory()) || (criteria.getIsSensitive() != null) || ((criteria.getApprovalStatuses() != null) && (criteria.getApprovalStatuses().size() > 0)) || (criteria.getAgreementType() > 0L) || (criteria.getAverageRating() >= 0.0D) || (criteria.getMaximumVotes() > 0) || (criteria.getMinimumVotes() > 0) || ((criteria.getGroupRestrictions() != null) && (criteria.getGroupRestrictions().size() > 0)) || (StringUtil.stringIsPopulated(searchForm.getAgreementText())) || (searchForm.getFilesizeLower() != null) || (searchForm.getFilesizeUpper() != null) || (criteria.getAddedByUserId() > 0L) || (!this.m_externalFilterManager.emptySearchCriteria(criteria));
/*     */   }
/*     */ 
/*     */   protected void validateForm(BaseSearchForm a_form, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.SearchAction
 * JD-Core Version:    0.6.0
 */