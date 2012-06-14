/*     */ package com.bright.assetbank.search.form;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class BaseSearchForm extends AssetForm
/*     */ {
/*  41 */   private boolean m_bOrCategories = true;
/*  42 */   private SearchResults m_searchResults = null;
/*  43 */   protected BrightMoney m_priceLower = null;
/*  44 */   protected BrightMoney m_priceUpper = null;
/*  45 */   private Vector m_vecBulkUploads = null;
/*  46 */   private long m_lBulkUpload = 0L;
/*  47 */   private boolean m_bWithoutCategory = false;
/*  48 */   private Filter m_selectedFilter = new Filter();
/*  49 */   private Vector m_vecFilters = null;
/*  50 */   private boolean m_bRefineSearch = false;
/*  51 */   private boolean m_bSearchingCategory = false;
/*  52 */   protected String m_sLanguage = null;
/*  53 */   private List m_languages = null;
/*  54 */   private boolean m_bIncludePreviousVersions = false;
/*  55 */   private Vector m_entities = null;
/*  56 */   private String m_entityName = null;
/*  57 */   private boolean m_bEntityPreSelected = false;
/*  58 */   private String m_sSelectAssetUrl = null;
/*  59 */   private String[] m_sSelectAssetParamNames = null;
/*  60 */   private String[] m_sSelectAssetParamValues = null;
/*  61 */   private String m_sRelationName = null;
/*  62 */   private String m_sAssetIdFieldLabel = null;
/*  63 */   private String m_sKeywords = null;
/*  64 */   private long m_lSortAttributeId = 0L;
/*  65 */   private boolean m_bSortDescending = false;
/*  66 */   private Vector m_vSortAttributes = null;
/*  67 */   private boolean m_bCustomSortFieldsDefined = false;
/*  68 */   private boolean m_bIncludeImplicitCategoryMembers = false;
/*  69 */   private int m_iPageSize = 0;
/*  70 */   private Vector m_vPageSizeOptions = null;
/*     */   protected long[] m_aSelectedEntities;
/*  75 */   private HashMap<String, Attribute> m_hmExternalFieldToAttributeMap = null;
/*     */ 
/*     */   public boolean getOrCategories()
/*     */   {
/*  82 */     return this.m_bOrCategories;
/*     */   }
/*     */ 
/*     */   public void setOrCategories(boolean a_bOrCategories)
/*     */   {
/*  90 */     this.m_bOrCategories = a_bOrCategories;
/*     */   }
/*     */ 
/*     */   public int getMaxNumberOfResults()
/*     */   {
/* 105 */     return AssetBankSettings.getDefaultNumResultsPerPage();
/*     */   }
/*     */ 
/*     */   public void setSearchResults(SearchResults a_searchResults)
/*     */   {
/* 110 */     this.m_searchResults = a_searchResults;
/*     */   }
/*     */ 
/*     */   public SearchResults getSearchResults()
/*     */   {
/* 115 */     if (this.m_searchResults == null)
/*     */     {
/* 117 */       this.m_searchResults = new SearchResults();
/*     */     }
/* 119 */     return this.m_searchResults;
/*     */   }
/*     */ 
/*     */   public BrightMoney getPriceLower()
/*     */   {
/* 124 */     return this.m_priceLower;
/*     */   }
/*     */ 
/*     */   public void setPriceLower(BrightMoney a_sPriceLower)
/*     */   {
/* 129 */     this.m_priceLower = a_sPriceLower;
/*     */   }
/*     */ 
/*     */   public BrightMoney getPriceUpper()
/*     */   {
/* 134 */     return this.m_priceUpper;
/*     */   }
/*     */ 
/*     */   public void setPriceUpper(BrightMoney a_sPriceUpper)
/*     */   {
/* 139 */     this.m_priceUpper = a_sPriceUpper;
/*     */   }
/*     */ 
/*     */   public boolean getWithoutCategory()
/*     */   {
/* 144 */     return this.m_bWithoutCategory;
/*     */   }
/*     */ 
/*     */   public void setWithoutCategory(boolean a_sWithoutCategory)
/*     */   {
/* 149 */     this.m_bWithoutCategory = a_sWithoutCategory;
/*     */   }
/*     */ 
/*     */   public void setBulkUploads(Vector a_vecBulkUploads)
/*     */   {
/* 154 */     this.m_vecBulkUploads = a_vecBulkUploads;
/*     */   }
/*     */ 
/*     */   public Vector getBulkUploads()
/*     */   {
/* 159 */     return this.m_vecBulkUploads;
/*     */   }
/*     */ 
/*     */   public long getBulkUpload()
/*     */   {
/* 165 */     return this.m_lBulkUpload;
/*     */   }
/*     */ 
/*     */   public void setBulkUpload(long a_sBulkUpload)
/*     */   {
/* 171 */     this.m_lBulkUpload = a_sBulkUpload;
/*     */   }
/*     */ 
/*     */   public void setSelectedFilter(Filter a_selectedFilter)
/*     */   {
/* 176 */     this.m_selectedFilter = a_selectedFilter;
/*     */   }
/*     */ 
/*     */   public Filter getSelectedFilter()
/*     */   {
/* 181 */     return this.m_selectedFilter;
/*     */   }
/*     */ 
/*     */   public void setFilters(Vector a_vecFilters)
/*     */   {
/* 186 */     this.m_vecFilters = a_vecFilters;
/*     */   }
/*     */ 
/*     */   public Vector getFilters()
/*     */   {
/* 191 */     return this.m_vecFilters;
/*     */   }
/*     */ 
/*     */   public void setRefineSearch(boolean a_bRefineSearch)
/*     */   {
/* 196 */     this.m_bRefineSearch = a_bRefineSearch;
/*     */   }
/*     */ 
/*     */   public boolean getRefineSearch()
/*     */   {
/* 201 */     return this.m_bRefineSearch;
/*     */   }
/*     */ 
/*     */   public void setSearchingCategory(boolean a_bSearchingCategory)
/*     */   {
/* 206 */     this.m_bSearchingCategory = a_bSearchingCategory;
/*     */   }
/*     */ 
/*     */   public boolean getSearchingCategory()
/*     */   {
/* 211 */     return this.m_bSearchingCategory;
/*     */   }
/*     */ 
/*     */   public List getLanguages()
/*     */   {
/* 216 */     return this.m_languages;
/*     */   }
/*     */ 
/*     */   public void setLanguages(List languages)
/*     */   {
/* 221 */     this.m_languages = languages;
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/* 226 */     return this.m_sLanguage;
/*     */   }
/*     */ 
/*     */   public void setLanguage(String a_sLanguage)
/*     */   {
/* 231 */     this.m_sLanguage = a_sLanguage;
/*     */   }
/*     */ 
/*     */   public boolean getIncludePreviousVersions()
/*     */   {
/* 236 */     return this.m_bIncludePreviousVersions;
/*     */   }
/*     */ 
/*     */   public void setIncludePreviousVersions(boolean includePreviousVersions)
/*     */   {
/* 241 */     this.m_bIncludePreviousVersions = includePreviousVersions;
/*     */   }
/*     */ 
/*     */   public Vector getEntities()
/*     */   {
/* 246 */     return this.m_entities;
/*     */   }
/*     */ 
/*     */   public void setEntities(Vector a_entities)
/*     */   {
/* 251 */     this.m_entities = a_entities;
/*     */   }
/*     */ 
/*     */   public String[] getSelectAssetParamNames()
/*     */   {
/* 256 */     if (this.m_sSelectAssetParamNames == null)
/*     */     {
/* 258 */       this.m_sSelectAssetParamNames = new String[20];
/*     */     }
/* 260 */     return this.m_sSelectAssetParamNames;
/*     */   }
/*     */ 
/*     */   public void setSelectAssetParamNames(String[] a_selectAssetQueryString)
/*     */   {
/* 265 */     this.m_sSelectAssetParamNames = a_selectAssetQueryString;
/*     */   }
/*     */ 
/*     */   public String getSelectAssetUrl()
/*     */   {
/* 270 */     return this.m_sSelectAssetUrl;
/*     */   }
/*     */ 
/*     */   public void setSelectAssetUrl(String a_selectAssetUrl)
/*     */   {
/* 275 */     this.m_sSelectAssetUrl = a_selectAssetUrl;
/*     */   }
/*     */ 
/*     */   public String[] getSelectAssetParamValues()
/*     */   {
/* 280 */     if (this.m_sSelectAssetParamValues == null)
/*     */     {
/* 282 */       this.m_sSelectAssetParamValues = new String[20];
/*     */     }
/* 284 */     return this.m_sSelectAssetParamValues;
/*     */   }
/*     */ 
/*     */   public void setSelectAssetParamValues(String[] a_selectAssetParamValues)
/*     */   {
/* 289 */     this.m_sSelectAssetParamValues = a_selectAssetParamValues;
/*     */   }
/*     */ 
/*     */   public boolean isEntityPreSelected()
/*     */   {
/* 294 */     return this.m_bEntityPreSelected;
/*     */   }
/*     */ 
/*     */   public void setEntityPreSelected(boolean a_entityPreSelected)
/*     */   {
/* 299 */     this.m_bEntityPreSelected = a_entityPreSelected;
/*     */   }
/*     */ 
/*     */   public String getEntityName()
/*     */   {
/* 304 */     return this.m_entityName;
/*     */   }
/*     */ 
/*     */   public void setEntityName(String a_entityName)
/*     */   {
/* 309 */     this.m_entityName = a_entityName;
/*     */   }
/*     */ 
/*     */   public String getRelationName()
/*     */   {
/* 314 */     return this.m_sRelationName;
/*     */   }
/*     */ 
/*     */   public void setRelationName(String a_relationName)
/*     */   {
/* 319 */     this.m_sRelationName = a_relationName;
/*     */   }
/*     */ 
/*     */   public void setSelectedEntities(long[] a_aSelectedEntities)
/*     */   {
/* 324 */     this.m_aSelectedEntities = a_aSelectedEntities;
/*     */   }
/*     */ 
/*     */   public long[] getSelectedEntities()
/*     */   {
/* 329 */     return this.m_aSelectedEntities;
/*     */   }
/*     */ 
/*     */   public String getAssetIdFieldLabel()
/*     */   {
/* 334 */     return this.m_sAssetIdFieldLabel;
/*     */   }
/*     */ 
/*     */   public void setAssetIdFieldLabel(String a_sAssetIdFieldLabel)
/*     */   {
/* 339 */     this.m_sAssetIdFieldLabel = a_sAssetIdFieldLabel;
/*     */   }
/*     */ 
/*     */   public void setKeywords(String a_sKeywords)
/*     */   {
/* 344 */     this.m_sKeywords = a_sKeywords;
/*     */   }
/*     */ 
/*     */   public String getKeywords()
/*     */   {
/* 349 */     return this.m_sKeywords;
/*     */   }
/*     */ 
/*     */   public long getSortAttributeId()
/*     */   {
/* 354 */     return this.m_lSortAttributeId;
/*     */   }
/*     */ 
/*     */   public void setSortAttributeId(long a_iSortAttributeId)
/*     */   {
/* 359 */     this.m_lSortAttributeId = a_iSortAttributeId;
/*     */   }
/*     */ 
/*     */   public Vector getSortAttributes()
/*     */   {
/* 364 */     return this.m_vSortAttributes;
/*     */   }
/*     */ 
/*     */   public void setSortAttributes(Vector a_iSortAttributes)
/*     */   {
/* 369 */     this.m_vSortAttributes = a_iSortAttributes;
/*     */   }
/*     */ 
/*     */   public boolean isSortDescending()
/*     */   {
/* 374 */     return this.m_bSortDescending;
/*     */   }
/*     */ 
/*     */   public void setSortDescending(boolean a_iSortDescending)
/*     */   {
/* 379 */     this.m_bSortDescending = a_iSortDescending;
/*     */   }
/*     */ 
/*     */   public boolean isCustomSortFieldsDefined()
/*     */   {
/* 384 */     return this.m_bCustomSortFieldsDefined;
/*     */   }
/*     */ 
/*     */   public void setCustomSortFieldsDefined(boolean a_iCustomSortFieldsDefined)
/*     */   {
/* 389 */     this.m_bCustomSortFieldsDefined = a_iCustomSortFieldsDefined;
/*     */   }
/*     */ 
/*     */   public boolean getIncludeImplicitCategoryMembers()
/*     */   {
/* 395 */     return this.m_bIncludeImplicitCategoryMembers;
/*     */   }
/*     */ 
/*     */   public void setIncludeImplicitCategoryMembers(boolean a_sIncludeImplicitCategoryMembers)
/*     */   {
/* 401 */     this.m_bIncludeImplicitCategoryMembers = a_sIncludeImplicitCategoryMembers;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Attribute> getExternalFieldToAttributeMap()
/*     */   {
/* 406 */     return this.m_hmExternalFieldToAttributeMap;
/*     */   }
/*     */ 
/*     */   public void setExternalFieldToAttributeMap(HashMap<String, Attribute> a_sHmExternalFieldToAttributeMap)
/*     */   {
/* 411 */     this.m_hmExternalFieldToAttributeMap = a_sHmExternalFieldToAttributeMap;
/*     */   }
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/* 416 */     return this.m_iPageSize;
/*     */   }
/*     */ 
/*     */   public void setPageSize(int a_iPageSize)
/*     */   {
/* 421 */     this.m_iPageSize = a_iPageSize;
/*     */   }
/*     */ 
/*     */   public Vector getPageSizeOptions()
/*     */   {
/* 426 */     return this.m_vPageSizeOptions;
/*     */   }
/*     */ 
/*     */   public void setPageSizeOptions(Vector a_iPageSizeOptions)
/*     */   {
/* 431 */     this.m_vPageSizeOptions = a_iPageSizeOptions;
/*     */   }
/*     */ 
/*     */   public boolean getContainsImages()
/*     */   {
/* 436 */     return SearchUtil.getSearchResultsContainImages(getSearchResults());
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.form.BaseSearchForm
 * JD-Core Version:    0.6.0
 */