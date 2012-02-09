/*     */ package com.bright.assetbank.search.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.externalfilter.service.ExternalFilterManager;
/*     */ import com.bright.assetbank.report.service.SearchReportManager;
/*     */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*     */ import com.bright.assetbank.search.form.BaseSearchForm;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.search.constant.SearchConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.ParseException;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class BaseSearchAction extends BTransactionAction
/*     */   implements ImageConstants, AssetBankConstants, MessageConstants, AssetBankSearchConstants, AttributeConstants, SearchConstants
/*     */ {
/*  71 */   protected IAssetManager m_assetManager = null;
/*  72 */   protected MultiLanguageSearchManager m_searchManager = null;
/*  73 */   protected SearchReportManager m_searchReportManager = null;
/*  74 */   protected AttributeManager m_attributeManager = null;
/*  75 */   protected AssetEntityManager m_assetEntityManager = null;
/*  76 */   protected ListManager m_listManager = null;
/*  77 */   protected ABUserManager m_userManager = null;
/*     */   protected ExternalFilterManager m_externalFilterManager;
/*     */ 
/*     */   public void setExternalFilterManager(ExternalFilterManager a_externalFilterManager)
/*     */   {
/*  82 */     this.m_externalFilterManager = a_externalFilterManager;
/*     */   }
/*     */ 
/*     */   protected abstract SearchQuery getNewSearchCriteria(DBTransaction paramDBTransaction, BaseSearchForm paramBaseSearchForm, HttpServletRequest paramHttpServletRequest)
/*     */     throws ParseException, Bn2Exception;
/*     */ 
/*     */   protected abstract boolean searchCriteriaSpecified(BaseSearchForm paramBaseSearchForm, SearchQuery paramSearchQuery);
/*     */ 
/*     */   protected abstract void populateForm(SearchQuery paramSearchQuery, BaseSearchForm paramBaseSearchForm);
/*     */ 
/*     */   protected abstract void prepareSearchCriteriaBeforeSearch(HttpServletRequest paramHttpServletRequest, SearchQuery paramSearchQuery);
/*     */ 
/*     */   protected abstract void validateForm(BaseSearchForm paramBaseSearchForm, HttpServletRequest paramHttpServletRequest)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 110 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 111 */     BaseSearchForm searchForm = (BaseSearchForm)a_form;
/* 112 */     ActionForward afForward = null;
/* 113 */     SearchQuery searchQuery = null;
/* 114 */     SearchResults searchResults = null;
/*     */ 
/* 116 */     boolean bUseCache = getIntParameter(a_request, "cachedCriteria") == 1;
/* 117 */     if ((!bUseCache) || (userProfile.getSearchCriteria() == null))
/*     */     {
/*     */       try
/*     */       {
/* 122 */         searchQuery = getNewSearchCriteria(a_dbTransaction, searchForm, a_request);
/*     */ 
/* 125 */         this.m_externalFilterManager.populateSearchCriteria(a_request, searchQuery, searchForm.getErrors());
/*     */ 
/* 128 */         userProfile.setSearchCriteria(searchQuery);
/*     */ 
/* 130 */         if (Boolean.parseBoolean(a_request.getParameter("newSearch")))
/*     */         {
/* 132 */           SearchUtil.resetSelectAssetSessionAttributes(a_request);
/*     */         }
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/* 137 */         searchForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", userProfile.getCurrentLanguage()).getBody());
/* 138 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 144 */       searchQuery = userProfile.getSearchCriteria();
/*     */ 
/* 146 */       populateForm(searchQuery, searchForm);
/*     */ 
/* 149 */       if (a_request.getParameter("sortAttributeId") == null)
/*     */       {
/* 151 */         searchForm.setSortAttributeId(searchQuery.getSortAttributeId());
/* 152 */         searchForm.setSortDescending(searchQuery.isSortDescending());
/*     */       }
/*     */ 
/* 155 */       if (!Boolean.parseBoolean(a_request.getParameter("newSearch")))
/*     */       {
/* 158 */         SearchUtil.populateSelectAssetFormData(searchForm, a_request);
/* 159 */         searchForm.setEntityPreSelected(searchForm.getEntityName() != null);
/*     */       }
/*     */       else
/*     */       {
/* 163 */         SearchUtil.resetSelectAssetSessionAttributes(a_request);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 168 */     searchForm.setCustomSortFieldsDefined(this.m_attributeManager.getCustomSortOrderDefined(a_dbTransaction, 1L));
/*     */ 
/* 171 */     if ((!bUseCache) || (userProfile.getSearchCriteria() == null) || ((AssetBankSettings.getUserDrivenSortingEnabled()) && ((searchQuery.getSortAttributeId() != searchForm.getSortAttributeId()) || (searchQuery.isSortDescending() != searchForm.isSortDescending()))))
/*     */     {
/* 177 */       if (a_request.getParameter("sortAttributeId") != null)
/*     */       {
/* 179 */         searchQuery.setSortAttributeId(searchForm.getSortAttributeId());
/* 180 */         searchQuery.setSortDescending(searchForm.isSortDescending());
/*     */       }
/*     */       else
/*     */       {
/* 186 */         searchQuery.setSortDescending(!searchForm.isCustomSortFieldsDefined());
/* 187 */         searchForm.setSortDescending(!searchForm.isCustomSortFieldsDefined());
/*     */       }
/*     */ 
/* 190 */       SearchUtil.populateSortFieldCriteria(a_dbTransaction, this.m_attributeManager, searchQuery, 1L);
/*     */     }
/*     */ 
/* 194 */     validateForm(searchForm, a_request);
/*     */ 
/* 197 */     if ((!searchForm.getHasErrors()) && (!bUseCache) && (!searchCriteriaSpecified(searchForm, searchQuery)))
/*     */     {
/* 200 */       searchForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationSearchCriteria", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 204 */     if (searchForm.getHasErrors())
/*     */     {
/* 206 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 210 */     int iPageIndex = getIntParameter(a_request, "page");
/* 211 */     int iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/* 214 */     if (iPageIndex < 0)
/*     */     {
/* 217 */       if (bUseCache)
/*     */       {
/* 220 */         iPageIndex = searchQuery.getPageIndex();
/*     */       }
/*     */       else
/*     */       {
/* 224 */         iPageIndex = 0;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 229 */     if (iPageSize <= 0)
/*     */     {
/* 232 */       if (userProfile.getSearchResultsPerPage() > 0)
/*     */       {
/* 234 */         iPageSize = userProfile.getSearchResultsPerPage();
/*     */       }
/*     */       else
/*     */       {
/* 238 */         iPageSize = AssetBankSettings.getDefaultNumResultsPerPage();
/*     */       }
/*     */     }
/*     */ 
/* 242 */     if ((userProfile.getIsLoggedIn()) && (userProfile.getSearchResultsPerPage() != iPageSize))
/*     */     {
/* 245 */       this.m_userManager.updateSearchResultsPerPage(a_dbTransaction, userProfile.getUser().getId(), iPageSize);
/*     */     }
/*     */ 
/* 250 */     userProfile.setSearchResultsPerPage(iPageSize);
/*     */ 
/* 253 */     searchForm.setPageSize(iPageSize);
/*     */ 
/* 256 */     searchForm.setPageSizeOptions(StringUtil.convertToVector(AssetBankSettings.getOptionsNumSearchResults(), ","));
/*     */ 
/* 259 */     prepareSearchCriteriaBeforeSearch(a_request, searchQuery);
/*     */ 
/* 262 */     String sLangCode = userProfile.getCurrentLanguage().getCode();
/* 263 */     if (StringUtils.isNotEmpty(searchQuery.getLanguageCode()))
/*     */     {
/* 265 */       sLangCode = searchQuery.getLanguageCode();
/*     */     }
/*     */ 
/*     */     do
/*     */     {
/* 274 */       searchResults = this.m_searchManager.searchByPageIndex(searchQuery, iPageIndex, iPageSize, sLangCode);
/*     */     }
/* 276 */     while ((searchResults.getNumResults() > 0) && (searchResults.getNumResultsPopulated() == 0) && (iPageIndex-- > 0));
/*     */ 
/* 279 */     if ((AssetBankSettings.getRecordSearches()) && (iPageIndex == 0))
/*     */     {
/* 281 */       boolean bSuccess = false;
/* 282 */       if (!searchResults.getSearchResults().isEmpty())
/*     */       {
/* 284 */         bSuccess = true;
/*     */       }
/* 286 */       this.m_searchReportManager.addSearchToQueue(searchQuery, bSuccess);
/*     */     }
/*     */ 
/* 290 */     for (int i = 0; i < searchResults.getSearchResults().size(); i++)
/*     */     {
/* 292 */       LightweightAsset asset = (LightweightAsset)searchResults.getSearchResults().get(i);
/* 293 */       asset.setInAssetBox(userProfile.getAssetBox().containsAsset(asset.getId()));
/*     */ 
/* 296 */       if ((userProfile.getIsAdmin()) || (!AssetBankSettings.getCanRestrictAssets()) || (!AssetBankSettings.getHideRestrictedImages()))
/*     */         continue;
/* 298 */       asset.setOverrideRestriction(this.m_assetManager.canViewRestrictedAsset(userProfile, asset));
/*     */     }
/*     */ 
/* 303 */     searchForm.setSearchResults(searchResults);
/*     */ 
/* 305 */     searchForm.setAssetIdFieldLabel(this.m_attributeManager.getAttribute(a_dbTransaction, "assetId").getLabel());
/*     */ 
/* 308 */     searchQuery.setPageIndex(iPageIndex);
/* 309 */     searchQuery.setPageSize(iPageSize);
/*     */ 
/* 312 */     if (!bUseCache)
/*     */     {
/* 314 */       userProfile.addRecentSearch(searchQuery, a_request.getQueryString());
/*     */ 
/* 316 */       a_request.getSession().setAttribute("searchPage", a_request.getParameter("searchPage"));
/*     */     }
/* 318 */     else if (AssetBankSettings.getUserDrivenSortingEnabled())
/*     */     {
/* 321 */       userProfile.updateCurrentSearchSortAttribute();
/*     */     }
/*     */ 
/* 325 */     if (AssetBankSettings.getUserDrivenSortingEnabled())
/*     */     {
/* 327 */       Vector vSortAtts = this.m_attributeManager.getUserSortAttributes(a_dbTransaction, userProfile.getIsAdmin() ? null : userProfile.getVisibleAttributeIds(), false);
/* 328 */       LanguageUtils.setLanguageOnAll(vSortAtts, userProfile.getCurrentLanguage());
/* 329 */       AttributeUtil.sortAttributesByLabel(vSortAtts);
/* 330 */       searchForm.setSortAttributes(vSortAtts);
/*     */     }
/*     */ 
/* 334 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 336 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 341 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setSearchReportManager(SearchReportManager a_searchReportManager)
/*     */   {
/* 346 */     this.m_searchReportManager = a_searchReportManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 351 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 357 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 363 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 369 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 374 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager manager)
/*     */   {
/* 379 */     this.m_userManager = manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.BaseSearchAction
 * JD-Core Version:    0.6.0
 */