/*     */ package com.bright.assetbank.category.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.category.bean.Panel;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.common.bean.KeyValueBean;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class BrowseItemsForm extends Bn2Form
/*     */   implements AssetBankConstants
/*     */ {
/*  46 */   private SearchResults m_searchResults = null;
/*  47 */   private Category m_category = null;
/*  48 */   private String m_sRootCategoryName = null;
/*  49 */   private Vector m_vecSubCategoryList = null;
/*  50 */   private Vector m_vecBrowseableCategories = null;
/*  51 */   private boolean m_bCategoriesHaveImages = false;
/*  52 */   private boolean m_bAllCats = false;
/*     */ 
/*  54 */   private long m_lSortAttributeId = 0L;
/*  55 */   private boolean m_bSortDescending = false;
/*  56 */   private Vector m_vSortAttributes = null;
/*  57 */   private Vector m_vecFilters = null;
/*  58 */   private boolean m_bCustomSortFieldsDefined = false;
/*  59 */   private Filter m_selectedFilter = null;
/*  60 */   private List<Panel> m_panels = null;
/*  61 */   private LightweightAsset m_extensionAsset = null;
/*     */ 
/*     */   public void setExtensionAsset(LightweightAsset a_extensionAsset)
/*     */   {
/*  65 */     this.m_extensionAsset = a_extensionAsset;
/*     */   }
/*     */ 
/*     */   public LightweightAsset getExtensionAsset()
/*     */   {
/*  70 */     return this.m_extensionAsset;
/*     */   }
/*     */ 
/*     */   public Vector getSubCategoryList()
/*     */   {
/*  75 */     return this.m_vecSubCategoryList;
/*     */   }
/*     */ 
/*     */   public void setSubCategoryList(Vector a_vecSubCategoryList)
/*     */   {
/*  80 */     this.m_vecSubCategoryList = a_vecSubCategoryList;
/*     */   }
/*     */ 
/*     */   public boolean getSubCategoryListIsEmpty()
/*     */   {
/*  85 */     return (this.m_vecSubCategoryList == null) || (this.m_vecSubCategoryList.isEmpty());
/*     */   }
/*     */ 
/*     */   public void setCategory(Category a_category)
/*     */   {
/*  90 */     this.m_category = a_category;
/*     */   }
/*     */ 
/*     */   public Category getCategory()
/*     */   {
/*  95 */     return this.m_category;
/*     */   }
/*     */ 
/*     */   public long getTopLevelCategoryId()
/*     */   {
/* 100 */     return -1L;
/*     */   }
/*     */ 
/*     */   public SearchResults getSearchResults()
/*     */   {
/* 105 */     if (this.m_searchResults == null)
/*     */     {
/* 107 */       this.m_searchResults = new SearchResults();
/*     */     }
/* 109 */     return this.m_searchResults;
/*     */   }
/*     */ 
/*     */   public void setSearchResults(SearchResults a_vecSearchResults)
/*     */   {
/* 114 */     this.m_searchResults = a_vecSearchResults;
/*     */   }
/*     */ 
/*     */   public Vector getBrowseableCategoriesForAdmin()
/*     */   {
/* 119 */     return getBrowseableCategories(1);
/*     */   }
/*     */ 
/*     */   public Vector getBrowseableCategoriesForNonAdmin()
/*     */   {
/* 124 */     return getBrowseableCategories(0);
/*     */   }
/*     */ 
/*     */   private Vector getBrowseableCategories(int a_iIsAdmin)
/*     */   {
/* 129 */     if (this.m_vecBrowseableCategories == null)
/*     */     {
/* 131 */       if (a_iIsAdmin > 0)
/*     */       {
/* 133 */         this.m_vecBrowseableCategories = getCategory().getChildCategories();
/*     */       }
/*     */       else
/*     */       {
/* 137 */         this.m_vecBrowseableCategories = new Vector();
/* 138 */         Category category = null;
/*     */ 
/* 140 */         int i = 0;
/* 141 */         for (; (getCategory().getChildCategories() != null) && (i < getCategory().getChildCategories().size()); i++)
/*     */         {
/* 143 */           category = (Category)getCategory().getChildCategories().elementAt(i);
/*     */ 
/* 145 */           if (!category.getIsBrowsable())
/*     */             continue;
/* 147 */           this.m_vecBrowseableCategories.add(getCategory().getChildCategories().elementAt(i));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 153 */     return this.m_vecBrowseableCategories;
/*     */   }
/*     */ 
/*     */   public void setBrowseableCategories(Vector a_browseableCategories)
/*     */   {
/* 158 */     this.m_vecBrowseableCategories = a_browseableCategories;
/*     */   }
/*     */ 
/*     */   public int getNoOfBrowseableCategories()
/*     */   {
/* 163 */     return this.m_vecBrowseableCategories.size();
/*     */   }
/*     */ 
/*     */   public double getColLength()
/*     */   {
/* 168 */     return Math.ceil(getNoOfBrowseableCategories() / 3.0D);
/*     */   }
/*     */ 
/*     */   public double getFilterColLength()
/*     */   {
/* 173 */     return Math.ceil(getNoOfFilters() / 3.0D);
/*     */   }
/*     */ 
/*     */   public void setAllCats(boolean a_bAllCats)
/*     */   {
/* 178 */     this.m_bAllCats = a_bAllCats;
/*     */   }
/*     */ 
/*     */   public boolean getAllCats()
/*     */   {
/* 183 */     return this.m_bAllCats;
/*     */   }
/*     */ 
/*     */   public Vector<KeyValueBean> getBreadcrumbTrail()
/*     */   {
/* 200 */     Vector vecTrail = new Vector();
/*     */ 
/* 202 */     String sCategoryTypeParam = "&categoryTypeId=" + getCategory().getCategoryTypeId();
/* 203 */     String sPageParams = "&page=" + getSearchResults().getPageIndex() + "&pageSize=" + getSearchResults().getPageSize();
/* 204 */     String sSortParams = "&sortAttributeId=" + getSortAttributeId() + "&sortDescending=" + isSortDescending();
/*     */ 
/* 206 */     if (getAllCats())
/*     */     {
/* 208 */       sCategoryTypeParam = sCategoryTypeParam + "&allCats=1";
/*     */     }
/*     */ 
/* 216 */     if (getCategory().getDepth() != 0)
/*     */     {
/*     */       String sLabel;
/*     */       String sLink;
/*     */       //String sLabel;
/* 218 */       if (getAllCats())
/*     */       {
/* 220 */         sLink = "viewAlphabeticCategories";
/* 221 */         sLabel = "All categories";
/*     */       }
/*     */       else
/*     */       {
/* 225 */         sLink = "browseItems?categoryId=" + getTopLevelCategoryId() + sCategoryTypeParam;
/* 226 */         sLabel = getRootCategoryName();
/*     */       }
/*     */ 
/* 229 */       KeyValueBean entry = new KeyValueBean(sLabel, sLink);
/* 230 */       vecTrail.add(entry);
/*     */     }
/*     */ 
/* 234 */     if (!getCategory().getAncestorCategoryListIsEmpty())
/*     */     {
/* 236 */       Iterator it = getCategory().getAncestors().iterator();
/* 237 */       while (it.hasNext())
/*     */       {
/* 239 */         Category cat = (Category)it.next();
/* 240 */         String sLink = "browseItems?categoryId=" + cat.getId() + sCategoryTypeParam;
/* 241 */         String sLabel = cat.getName();
/*     */ 
/* 243 */         KeyValueBean entry = new KeyValueBean(sLabel, sLink);
/* 244 */         vecTrail.add(entry);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 249 */     String sLink = "browseItems?categoryId=" + getCategory().getId() + sCategoryTypeParam + sPageParams + sSortParams;
/*     */     String sLabel;
/*     */     //String sLabel;
/* 251 */     if (getCategory().getId() == -1L)
/*     */     {
/* 253 */       sLabel = getRootCategoryName();
/*     */     }
/*     */     else
/*     */     {
/* 257 */       sLabel = getCategory().getName();
/*     */     }
/*     */ 
/* 260 */     KeyValueBean entry = new KeyValueBean(sLabel, sLink);
/* 261 */     vecTrail.add(entry);
/*     */ 
/* 264 */     if ((getSelectedFilter() != null) && (getSelectedFilter().getId() > 0L))
/*     */     {
/* 266 */       sLink = sLink + "&filterId=" + getSelectedFilter().getId();
/* 267 */       sLabel = getSelectedFilter().getName();
/* 268 */       entry = new KeyValueBean(sLabel, sLink);
/* 269 */       vecTrail.add(entry);
/*     */     }
/*     */ 
/* 272 */     vecTrail = getAdjustedTrail(vecTrail);
/*     */ 
/* 274 */     return vecTrail;
/*     */   }
/*     */ 
/*     */   private Vector<KeyValueBean> getAdjustedTrail(Vector<KeyValueBean> a_vecTrail)
/*     */   {
/* 280 */     Vector vecNew = null;
/* 281 */     long lTop = CategoryUtil.getDescriptiveBrowseTopLevelCatId();
/* 282 */     boolean bFound = false;
/*     */ 
/* 284 */     if (lTop != -1L)
/*     */     {
/* 286 */       for (int i = 0; i < a_vecTrail.size(); i++)
/*     */       {
/* 288 */         KeyValueBean b = (KeyValueBean)a_vecTrail.elementAt(i);
/* 289 */         if (b.getValue().contains("?categoryId=" + lTop + "&"))
/*     */         {
/* 291 */           b.setKey(getRootCategoryName());
/* 292 */           bFound = true;
/*     */         }
/*     */ 
/* 296 */         if (!bFound)
/*     */           continue;
/* 298 */         if (vecNew == null)
/*     */         {
/* 300 */           vecNew = new Vector();
/*     */         }
/* 302 */         vecNew.add(b);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 307 */     if (vecNew != null)
/*     */     {
/* 309 */       return vecNew;
/*     */     }
/* 311 */     return a_vecTrail;
/*     */   }
/*     */ 
/*     */   public boolean getContainsImages()
/*     */   {
/* 316 */     return SearchUtil.getSearchResultsContainImages(getSearchResults());
/*     */   }
/*     */ 
/*     */   public boolean getCategoriesHaveImages()
/*     */   {
/* 321 */     return this.m_bCategoriesHaveImages;
/*     */   }
/*     */ 
/*     */   public void setCategoriesHaveImages(boolean a_sCategoriesHaveImages) {
/* 325 */     this.m_bCategoriesHaveImages = a_sCategoriesHaveImages;
/*     */   }
/*     */ 
/*     */   public String getRootCategoryName()
/*     */   {
/* 330 */     return this.m_sRootCategoryName;
/*     */   }
/*     */ 
/*     */   public void setRootCategoryName(String rootCategoryName)
/*     */   {
/* 335 */     this.m_sRootCategoryName = rootCategoryName;
/*     */   }
/*     */ 
/*     */   public long getSortAttributeId()
/*     */   {
/* 340 */     return this.m_lSortAttributeId;
/*     */   }
/*     */ 
/*     */   public void setSortAttributeId(long a_iSortAttributeId)
/*     */   {
/* 345 */     this.m_lSortAttributeId = a_iSortAttributeId;
/*     */   }
/*     */ 
/*     */   public Vector getSortAttributes()
/*     */   {
/* 350 */     return this.m_vSortAttributes;
/*     */   }
/*     */ 
/*     */   public void setSortAttributes(Vector a_iSortAttributes)
/*     */   {
/* 355 */     this.m_vSortAttributes = a_iSortAttributes;
/*     */   }
/*     */ 
/*     */   public boolean isSortDescending()
/*     */   {
/* 360 */     return this.m_bSortDescending;
/*     */   }
/*     */ 
/*     */   public void setSortDescending(boolean a_iSortDescending)
/*     */   {
/* 365 */     this.m_bSortDescending = a_iSortDescending;
/*     */   }
/*     */ 
/*     */   public boolean isCustomSortFieldsDefined()
/*     */   {
/* 370 */     return this.m_bCustomSortFieldsDefined;
/*     */   }
/*     */ 
/*     */   public void setCustomSortFieldsDefined(boolean a_iCustomSortFieldsDefined)
/*     */   {
/* 375 */     this.m_bCustomSortFieldsDefined = a_iCustomSortFieldsDefined;
/*     */   }
/*     */ 
/*     */   public void setFilters(Vector a_vecFilters)
/*     */   {
/* 380 */     this.m_vecFilters = a_vecFilters;
/*     */   }
/*     */ 
/*     */   public Vector getFilters()
/*     */   {
/* 385 */     return this.m_vecFilters;
/*     */   }
/*     */ 
/*     */   public int getNoOfFilters()
/*     */   {
/* 390 */     if (getFilters() != null)
/*     */     {
/* 392 */       return getFilters().size();
/*     */     }
/* 394 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setSelectedFilter(Filter a_selectedFilter)
/*     */   {
/* 399 */     this.m_selectedFilter = a_selectedFilter;
/*     */   }
/*     */ 
/*     */   public Filter getSelectedFilter()
/*     */   {
/* 404 */     return this.m_selectedFilter;
/*     */   }
/*     */ 
/*     */   public void setPanels(List<Panel> a_panels)
/*     */   {
/* 409 */     this.m_panels = a_panels;
/*     */   }
/*     */ 
/*     */   public List<Panel> getPanels()
/*     */   {
/* 414 */     return this.m_panels;
/*     */   }
/*     */ 
/*     */   public boolean getShowGlobalAddItemLink()
/*     */   {
/* 426 */     if (this.m_panels != null)
/*     */     {
/* 428 */       int iVisibleCanAddItemNoHeaderCount = 0;
/* 429 */       int iInvisibleCanAddItemCount = 0;
/* 430 */       for (Panel panel : this.m_panels)
/*     */       {
/* 432 */         if ((panel.getCanAddItem()) && (!StringUtil.stringIsPopulated(panel.getHeader())) && (panel.getVisibilityStatus() == 3))
/*     */         {
/* 436 */           iVisibleCanAddItemNoHeaderCount++;
/*     */         }
/* 438 */         else if ((panel.getCanAddItem()) && (panel.getVisibilityStatus() == 1))
/*     */         {
/* 441 */           iInvisibleCanAddItemCount++;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 446 */       if (((iVisibleCanAddItemNoHeaderCount == 1) && (iInvisibleCanAddItemCount <= 0)) || (iInvisibleCanAddItemCount > 0))
/*     */       {
/* 449 */         return true;
/*     */       }
/*     */     }
/* 452 */     return false;
/*     */   }
/*     */ 
/*     */   public long getExtensionEntityId()
/*     */   {
/* 457 */     if ((this.m_extensionAsset != null) && (this.m_extensionAsset.getEntity() != null))
/*     */     {
/* 459 */       return this.m_extensionAsset.getEntity().getId();
/*     */     }
/* 461 */     return -1L;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.form.BrowseItemsForm
 * JD-Core Version:    0.6.0
 */