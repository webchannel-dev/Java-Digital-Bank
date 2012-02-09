/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
          import com.bright.assetbank.application.service.AssetManager;
/*     */ //import com.bright.assetbank.application.service.AssetManager1;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.category.bean.Panel;
/*     */ import com.bright.assetbank.category.constant.CategoryConstants;
/*     */ import com.bright.assetbank.category.form.BrowseItemsForm;
/*     */ import com.bright.assetbank.category.service.BrowseAssetsPaneller;
/*     */ import com.bright.assetbank.category.service.DefaultBrowseAssetsPaneller;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.plugin.service.PluginManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.CategoryWithLanguage;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class BrowseItemsAction extends BTransactionAction
/*     */   implements CommonConstants, CategoryConstants, AssetBankSearchConstants, MessageConstants, AssetBankConstants, AttributeConstants
/*     */ {
/*     */   private static final String k_sForwardNoDescriptiveCategories = "noDescriptiveCategories";
/*  94 */   protected AssetCategoryManager m_categoryManager = null;
/*  95 */   protected MultiLanguageSearchManager m_searchManager = null;
/*  96 */   protected AttributeManager m_attributeManager = null;
/*  97 */   protected ListManager m_listManager = null;
/*  98 */   protected AssetManager m_assetManager = null;
/*  99 */   protected FilterManager m_filterManager = null;
/* 100 */   protected PluginManager m_pluginManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 110 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 112 */     boolean bError = false;
/*     */ 
/* 115 */     ActionForward afForward = null;
/*     */ 
/* 117 */     BrowseItemsForm browseItemsForm = (BrowseItemsForm)a_form;
/*     */ 
/* 119 */     long lCatId = getLongParameter(a_request, "categoryId");
/* 120 */     long lCatTreeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/* 123 */     if ((lCatTreeId == 1L) && (lCatId == -1L))
/*     */     {
/* 125 */       lCatId = CategoryUtil.getDescriptiveBrowseTopLevelCatId();
/*     */     }
/*     */ 
/* 129 */     if (((lCatId <= 0L) && (lCatId != -1L)) || (lCatTreeId <= 0L))
/*     */     {
/* 131 */       browseItemsForm.addError(this.m_listManager.getListItem(a_dbTransaction, "categoryErrorMissingId", userProfile.getCurrentLanguage()).getBody());
/* 132 */       bError = true;
/*     */     }
/*     */ 
/* 135 */     if (!bError)
/*     */     {
/* 138 */       if (lCatTreeId == 1L)
/*     */       {
/* 140 */         userProfile.setSelectedCategoryId(lCatId);
/* 141 */         browseItemsForm.setRootCategoryName(this.m_listManager.getListItem("category-root", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */       else
/*     */       {
/* 145 */         userProfile.setSelectedAccessLevelId(lCatId);
/* 146 */         browseItemsForm.setRootCategoryName(this.m_listManager.getListItem("access-level-root", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */ 
/* 150 */       BrowseAssetsPaneller paneller = null;
/* 151 */       if (setupPanels())
/*     */       {
/* 153 */         paneller = this.m_pluginManager.getBrowseAssetsPaneller();
/* 154 */         if (paneller == null)
/*     */         {
/* 156 */           paneller = new DefaultBrowseAssetsPaneller();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 161 */       int iPageIndex = -1;
/* 162 */       int iPageSize = -1;
/* 163 */       if ((paneller == null) || (paneller.requiresPaging()))
/*     */       {
/* 166 */         iPageIndex = getIntParameter(a_request, "page");
/*     */ 
/* 169 */         if (iPageIndex < 0)
/*     */         {
/* 171 */           iPageIndex = 0;
/*     */         }
/*     */ 
/* 175 */         iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/* 178 */         if (iPageSize <= 0)
/*     */         {
/* 180 */           iPageSize = AssetBankSettings.getDefaultNumImagesPerBrowsePage();
/*     */         }
/*     */ 
/* 183 */         userProfile.setSelectedPage(iPageIndex);
/* 184 */         userProfile.setSelectedPageSize(iPageSize);
/*     */       }
/*     */ 
/* 187 */       Category cat = this.m_categoryManager.getCategory(a_dbTransaction, lCatTreeId, lCatId);
/*     */ 
/* 190 */       if (cat.getExtensionAssetId() > 0L)
/*     */       {
/* 192 */         LightweightAsset asset = this.m_categoryManager.findExtensionAssetForCategory(userProfile, cat.getExtensionAssetId());
/* 193 */         browseItemsForm.setExtensionAsset(asset);
/*     */       }
/*     */ 
/* 197 */       if (!userProfile.getCurrentLanguage().isDefault())
/*     */       {
/* 199 */         cat = new CategoryWithLanguage(cat, userProfile.getCurrentLanguage());
/*     */       }
/*     */ 
/* 204 */       if ((!userProfile.getIsAdmin()) && (lCatTreeId == 2L))
/*     */       {
/* 206 */         cat = filterAccessLevelAncestorsAndDescendents(userProfile.getPermissionCategoryIds(1), cat);
/*     */       }
/*     */ 
/* 210 */       if (getIntParameter(a_request, "allCats") == 1)
/*     */       {
/* 212 */         browseItemsForm.setAllCats(true);
/*     */       }
/*     */ 
/* 216 */       browseItemsForm.setCategory(cat);
/*     */       Vector subCats;
/*     */      // Vector subCats;
/* 221 */       if (userProfile.getIsAdmin())
/*     */       {
/* 223 */         subCats = browseItemsForm.getBrowseableCategoriesForAdmin();
/*     */       }
/*     */       else
/*     */       {
/* 227 */         subCats = browseItemsForm.getBrowseableCategoriesForNonAdmin();
/*     */       }
/*     */ 
/* 230 */       if ((!userProfile.getIsAdmin()) && (!AssetBankSettings.getShowEmptyCategories()) && (lCatTreeId == 1L))
/*     */       {
/* 232 */         subCats = this.m_categoryManager.removeEmptyCategories(subCats, userProfile);
/* 233 */         browseItemsForm.setBrowseableCategories(subCats);
/*     */       }
/*     */ 
/* 238 */       if ((lCatTreeId == 1L) && (lCatId == -1L) && ((subCats == null) || (subCats.size() == 0)))
/*     */       {
/* 241 */         String sQueryString = "categoryTypeId=2";
/* 242 */         afForward = createRedirectingForward(sQueryString, a_mapping, "noDescriptiveCategories");
/* 243 */         return afForward;
/*     */       }
/*     */ 
/* 247 */       if ((lCatTreeId == 2L) && (lCatId == -1L) && (subCats.size() == 1))
/*     */       {
/* 249 */         Category subCat = (Category)subCats.get(0);
/* 250 */         String sQueryString = "categoryTypeId=2&categoryId=" + subCat.getId();
/*     */ 
/* 252 */         afForward = createRedirectingForward(sQueryString, a_mapping, "noDescriptiveCategories");
/* 253 */         return afForward;
/*     */       }
/*     */ 
/* 258 */       if ((AssetBankSettings.getAutoBrowseIntoSingleCategories()) && (lCatTreeId == 1L) && (subCats.size() == 1))
/*     */       {
/* 260 */         Category subCat = (Category)subCats.get(0);
/* 261 */         String sQueryString = "categoryTypeId=1&categoryId=" + subCat.getId();
/*     */ 
/* 263 */         afForward = createRedirectingForward(sQueryString, a_mapping, "noDescriptiveCategories");
/* 264 */         return afForward;
/*     */       }
/*     */ 
/* 268 */       Category subCat = null;
/*     */ 
/* 270 */       for (int i = 0; i < subCats.size(); i++)
/*     */       {
/* 272 */         subCat = (Category)subCats.get(i);
/*     */ 
/* 274 */         if (subCat.getImageUrl() == null)
/*     */           continue;
/* 276 */         browseItemsForm.setCategoriesHaveImages(true);
/* 277 */         break;
/*     */       }
/*     */ 
/* 282 */       browseItemsForm.setCustomSortFieldsDefined(this.m_attributeManager.getCustomSortOrderDefined(a_dbTransaction, 2L));
/*     */ 
/* 284 */       SearchCriteria searchCriteria = new SearchCriteria();
/*     */ 
/* 286 */       long lSortOrder = getIntParameter(a_request, "sortAttributeId");
/*     */ 
/* 289 */       if (lSortOrder > 0L)
/*     */       {
/* 291 */         searchCriteria.setSortAttributeId(browseItemsForm.getSortAttributeId());
/* 292 */         searchCriteria.setSortDescending(browseItemsForm.isSortDescending());
/*     */       }
/*     */       else
/*     */       {
/* 298 */         searchCriteria.setSortDescending(!browseItemsForm.isCustomSortFieldsDefined());
/* 299 */         browseItemsForm.setSortDescending(!browseItemsForm.isCustomSortFieldsDefined());
/*     */       }
/*     */ 
/* 303 */       long lFilterId = getLongParameter(a_request, "filterId");
/* 304 */       if (lFilterId > 0L)
/*     */       {
/* 306 */         Filter filter = this.m_filterManager.getFilter(a_dbTransaction, lFilterId);
/* 307 */         searchCriteria.addSelectedFilter(filter);
/* 308 */         browseItemsForm.setSelectedFilter(filter);
/*     */       }
/*     */ 
/* 312 */       userProfile.setBrowseCriteria(searchCriteria);
/* 313 */       SearchResults results = getResults(searchCriteria, lCatId, lCatTreeId, userProfile, iPageIndex, iPageSize, a_dbTransaction);
/*     */ 
/* 316 */       if ((!userProfile.getIsAdmin()) && (AssetBankSettings.getCanRestrictAssets()) && (AssetBankSettings.getHideRestrictedImages()))
/*     */       {
/* 318 */         for (int i = 0; i < results.getSearchResults().size(); i++)
/*     */         {
/* 320 */           LightweightAsset asset = (LightweightAsset)results.getSearchResults().get(i);
/* 321 */           asset.setOverrideRestriction(this.m_assetManager.canViewRestrictedAsset(userProfile, asset));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 326 */       userProfile.setInAssetBoxFlagOnAll(results.getSearchResults());
/*     */ 
/* 329 */       browseItemsForm.setSearchResults(results);
/*     */ 
/* 332 */       if (setupPanels())
/*     */       {
/* 334 */         List extraPanels = getExtraPanels(a_dbTransaction, userProfile, lCatId, lCatTreeId);
/* 335 */         browseItemsForm.setPanels(paneller.getPanels(userProfile, results.getSearchResults(), extraPanels, cat, userProfile.getListView()));
/*     */       }
/*     */ 
/* 339 */       if (lCatId > 0L)
/*     */       {
/* 341 */         browseItemsForm.setFilters(this.m_filterManager.getFiltersForCategory(a_dbTransaction, userProfile, lCatId));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 346 */     if (AssetBankSettings.getUserDrivenBrowseSortingEnabled())
/*     */     {
/* 348 */       Vector vSortAtts = this.m_attributeManager.getUserSortAttributes(a_dbTransaction, userProfile.getIsAdmin() ? null : userProfile.getVisibleAttributeIds(), false);
/* 349 */       LanguageUtils.setLanguageOnAll(vSortAtts, userProfile.getCurrentLanguage());
/* 350 */       AttributeUtil.sortAttributesByLabel(vSortAtts);
/* 351 */       browseItemsForm.setSortAttributes(vSortAtts);
/*     */     }
/*     */ 
/* 355 */     if (AssetBankSettings.getUseCategoryExplorer())
/*     */     {
/* 358 */       a_request.getSession().setAttribute("lastGetRequestUri", "/action/viewHome");
/* 359 */       a_request.getSession().setAttribute("manualOveride", new Boolean(true));
/*     */     }
/*     */ 
/* 363 */     if (bError)
/*     */     {
/* 365 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 369 */       afForward = a_mapping.findForward("Success");
/*     */     }
/* 371 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected SearchResults<LightweightAsset> getResults(SearchCriteria a_searchCriteria, long a_lCatId, long a_lCatTreeId, ABUserProfile a_userProfile, int a_iPageIndex, int a_iPageSize, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 401 */     boolean bShowImplicitMembers = false;
/* 402 */     if (a_lCatTreeId == 2L)
/*     */     {
/* 404 */       bShowImplicitMembers = AssetBankSettings.getShowImplicitAssetsInAccessLevelBrowser();
/*     */     }
/*     */     else
/*     */     {
/* 408 */       bShowImplicitMembers = AssetBankSettings.getShowImplicitAssetsInDescriptiveCategoryBrowser();
/*     */     }
/* 410 */     a_searchCriteria.setIncludeImplicitCategoryMembers(bShowImplicitMembers);
/*     */ 
/* 415 */     if ((a_lCatId > 0L) || (!bShowImplicitMembers))
/*     */     {
/* 417 */       a_searchCriteria.setCategoryIds(Long.toString(a_lCatId));
/*     */     }
/*     */ 
/* 421 */     a_searchCriteria.setExtensionTypeToExclude(a_lCatTreeId);
/*     */ 
/* 424 */     a_searchCriteria.setupPermissions(a_userProfile);
/*     */ 
/* 427 */     SearchUtil.populateSortFieldCriteria(a_dbTransaction, this.m_attributeManager, a_searchCriteria, 2L);
/*     */ 
/* 429 */     a_searchCriteria.addSelectedFilters(a_userProfile.getSelectedFilters());
/*     */ 
/* 431 */     SearchResults results = this.m_searchManager.searchByPageIndex(a_searchCriteria, a_iPageIndex, a_iPageSize, a_userProfile.getCurrentLanguage().getCode());
/*     */ 
/* 433 */     return results;
/*     */   }
/*     */ 
/*     */   private static Category filterAccessLevelAncestorsAndDescendents(Set a_categoryIds, Category a_category)
/*     */   {
/* 443 */     Category cat = a_category.clone();
/*     */ 
/* 445 */     if (a_categoryIds.size() <= 0)
/*     */     {
/* 448 */       cat.getAncestors().clear();
/* 449 */       cat.getChildCategories().clear();
/*     */     }
/* 451 */     else if (a_category != null)
/*     */     {
/* 454 */       cat.setAncestors(CategoryUtil.filterCategoryVectorByCategoryIds(a_categoryIds, a_category.getAncestors(), false));
/* 455 */       cat.setChildCategories(CategoryUtil.filterCategoryVectorByCategoryIds(a_categoryIds, a_category.getChildCategories(), false));
/*     */     }
/*     */ 
/* 458 */     return cat;
/*     */   }
/*     */ 
/*     */   private List<Panel> getExtraPanels(DBTransaction a_transaction, ABUserProfile a_userProfile, long a_lCatId, long a_lCatTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 477 */     ArrayList extraPanels = new ArrayList();
/*     */ 
/* 480 */     Vector sodAssets = this.m_assetManager.getShowOnDescendantAssets(a_transaction, a_userProfile, a_lCatId, a_lCatTreeId);
/* 481 */     String sHeader = this.m_listManager.getListItem(a_transaction, "subhead-associated-assets", a_userProfile.getCurrentLanguage(), null);
/* 482 */     Panel panel = new Panel(true, sHeader);
/* 483 */     panel.setVisibilityStatus(1);
/* 484 */     panel.setCanAddItem(false);
/* 485 */     panel.setAssets(sodAssets);
/* 486 */     panel.setListView(a_userProfile.getListView());
/*     */ 
/* 488 */     extraPanels.add(panel);
/* 489 */     return extraPanels;
/*     */   }
/*     */ 
/*     */   protected boolean setupPanels()
/*     */   {
/* 495 */     return true;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 500 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_sCategoryManager)
/*     */   {
/* 505 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager) {
/* 509 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 514 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager assetManager)
/*     */   {
/* 519 */     this.m_assetManager = assetManager;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 524 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ 
/*     */   public void setPluginManager(PluginManager a_pluginManager)
/*     */   {
/* 529 */     this.m_pluginManager = a_pluginManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.BrowseItemsAction
 * JD-Core Version:    0.6.0
 */