/*     */ package com.bright.assetbank.attribute.filter.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class FilterForm extends Bn2Form
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  52 */   private Vector m_vecFilters = null;
/*  53 */   private Filter m_defaultFilter = new Filter();
/*     */   private Filter m_filter;
/*  55 */   private Vector m_vecAvailableAttributes = null;
/*  56 */   private long m_lDefaultFilterId = -1L;
/*     */ 
/*  58 */   private int m_iType = 0;
/*  59 */   private Vector m_vecFilterGroups = null;
/*  60 */   private Vector m_vecUserGroups = null;
/*  61 */   private FlatCategoryList m_categories = null;
/*  62 */   private FlatCategoryList m_accessLevels = null;
/*     */ 
/*  64 */   private LinkedHashMap<Category, List<Filter>> m_categoryFilters = null;
/*  65 */   private LinkedHashMap<Category, List<Filter>> m_accessLevelFilters = null;
/*  66 */   private Vector m_vecAllLinkedFilters = null;
/*     */ 
/*     */   public void setFilters(Vector a_vecFilters)
/*     */   {
/*  70 */     this.m_vecFilters = a_vecFilters;
/*     */   }
/*     */ 
/*     */   public Vector getFilters()
/*     */   {
/*  75 */     return this.m_vecFilters;
/*     */   }
/*     */ 
/*     */   public int getFiltersCount()
/*     */   {
/*  80 */     if (getFilters() != null)
/*     */     {
/*  82 */       return getFilters().size();
/*     */     }
/*  84 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setDefaultFilter(Filter a_defaultFilter)
/*     */   {
/*  89 */     this.m_defaultFilter = a_defaultFilter;
/*     */   }
/*     */ 
/*     */   public Filter getDefaultFilter()
/*     */   {
/*  94 */     return this.m_defaultFilter;
/*     */   }
/*     */ 
/*     */   public void setFilterGroups(Vector a_vecFilterGroups)
/*     */   {
/*  99 */     this.m_vecFilterGroups = a_vecFilterGroups;
/*     */   }
/*     */ 
/*     */   public Vector getFilterGroups()
/*     */   {
/* 104 */     return this.m_vecFilterGroups;
/*     */   }
/*     */ 
/*     */   public void setFilter(Filter a_filter)
/*     */   {
/* 109 */     this.m_filter = a_filter;
/*     */   }
/*     */ 
/*     */   public Filter getFilter()
/*     */   {
/* 114 */     if (this.m_filter == null)
/*     */     {
/* 116 */       this.m_filter = new Filter();
/*     */ 
/* 119 */       if (FrameworkSettings.getSupportMultiLanguage())
/*     */       {
/* 121 */         LanguageUtils.createEmptyTranslations(this.m_filter, 20);
/*     */       }
/*     */     }
/* 124 */     return this.m_filter;
/*     */   }
/*     */ 
/*     */   public void setAvailableAttributes(Vector a_vecAvailableAttributes)
/*     */   {
/* 129 */     this.m_vecAvailableAttributes = a_vecAvailableAttributes;
/*     */   }
/*     */ 
/*     */   public Vector getAvailableAttributes()
/*     */   {
/* 134 */     return this.m_vecAvailableAttributes;
/*     */   }
/*     */ 
/*     */   public void setDefaultFilterId(long a_lDefaultFilterId)
/*     */   {
/* 139 */     this.m_lDefaultFilterId = a_lDefaultFilterId;
/*     */   }
/*     */ 
/*     */   public long getDefaultFilterId()
/*     */   {
/* 144 */     return this.m_lDefaultFilterId;
/*     */   }
/*     */ 
/*     */   public void setType(int a_iType)
/*     */   {
/* 149 */     this.m_iType = a_iType;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 154 */     return this.m_iType;
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager) throws Bn2Exception
/*     */   {
/* 159 */     if (!StringUtil.stringIsPopulated(getFilter().getName()))
/*     */     {
/* 161 */       addError(a_listManager.getListItem(a_dbTransaction, "noFilterName", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setUserGroups(Vector a_vecUserGroups)
/*     */   {
/* 167 */     this.m_vecUserGroups = a_vecUserGroups;
/*     */   }
/*     */ 
/*     */   public Vector getUserGroups()
/*     */   {
/* 172 */     return this.m_vecUserGroups;
/*     */   }
/*     */ 
/*     */   public void setCategories(FlatCategoryList a_categories)
/*     */   {
/* 177 */     this.m_categories = a_categories;
/*     */   }
/*     */ 
/*     */   public FlatCategoryList getCategories()
/*     */   {
/* 182 */     return this.m_categories;
/*     */   }
/*     */ 
/*     */   public void setAccessLevels(FlatCategoryList a_accessLevels)
/*     */   {
/* 187 */     this.m_accessLevels = a_accessLevels;
/*     */   }
/*     */ 
/*     */   public FlatCategoryList getAccessLevels()
/*     */   {
/* 192 */     return this.m_accessLevels;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap<Category, List<Filter>> getCategoryFilters()
/*     */   {
/* 197 */     return this.m_categoryFilters;
/*     */   }
/*     */ 
/*     */   public void setCategoryFilters(LinkedHashMap<Category, List<Filter>> a_categoryFilters)
/*     */   {
/* 202 */     this.m_categoryFilters = a_categoryFilters;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap<Category, List<Filter>> getAccessLevelFilters()
/*     */   {
/* 207 */     return this.m_accessLevelFilters;
/*     */   }
/*     */ 
/*     */   public void setAccessLevelFilters(LinkedHashMap<Category, List<Filter>> a_accessLevelFilters)
/*     */   {
/* 212 */     this.m_accessLevelFilters = a_accessLevelFilters;
/*     */   }
/*     */ 
/*     */   public void setAllLinkedFilters(Vector a_vecAllLinkedFilters)
/*     */   {
/* 217 */     this.m_vecAllLinkedFilters = a_vecAllLinkedFilters;
/*     */   }
/*     */ 
/*     */   public Vector getAllLinkedFilters()
/*     */   {
/* 222 */     return this.m_vecAllLinkedFilters;
/*     */   }
/*     */ 
/*     */   public int getAllLinkedFiltersCount()
/*     */   {
/* 227 */     if (getAllLinkedFilters() != null)
/*     */     {
/* 229 */       return getAllLinkedFilters().size();
/*     */     }
/* 231 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.form.FilterForm
 * JD-Core Version:    0.6.0
 */