/*      */ package com.bright.assetbank.search.bean;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*      */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*      */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*      */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*      */ import com.bright.assetbank.search.lucene.CategoryApprovalFilter;
/*      */ import com.bright.assetbank.search.lucene.PermissionCategoryFilter;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.user.bean.Group;
/*      */ import com.bright.assetbank.user.bean.GroupCategoryPermission;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.search.constant.SearchConstants;
/*      */ import com.bright.framework.search.lucene.FieldDateRange;
/*      */ import com.bright.framework.search.lucene.FieldNumericRange;
/*      */ import com.bright.framework.search.lucene.SearchFilter;
/*      */ import com.bright.framework.util.CollectionUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.avalon.framework.component.ComponentException;
/*      */ import org.apache.avalon.framework.component.ComponentManager;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public abstract class BaseSearchQuery extends com.bright.framework.search.bean.BaseSearchQuery
/*      */   implements Serializable, SearchConstants, AttributeConstants, AssetBankSearchConstants
/*      */ {
/*      */   private static final long serialVersionUID = -4320122430262867714L;
/*   79 */   protected static Log c_logger = GlobalApplication.getInstance().getLogger();
/*      */ 
/*   81 */   private String m_sCategoryIds = null;
/*   82 */   private boolean m_bOrCategories = false;
/*   83 */   private boolean m_bIncludeImplicitCategoryMembers = true;
/*   84 */   private boolean m_bWithoutCategory = false;
/*   85 */   private BrightMoney m_priceLower = null;
/*   86 */   private BrightMoney m_priceUpper = null;
/*   87 */   private boolean m_bIncludePreviousVersions = false;
/*   88 */   private Set<Long> m_requiredRestrictiveCategories = null;
/*      */ 
/*   94 */   private Vector m_vAttributeExclusions = null;
/*      */ 
/*   96 */   private int m_iPageIndex = 0;
/*   97 */   private int m_iPageSize = 0;
/*   98 */   private long m_lBulkUpload = 0L;
/*   99 */   private Vector m_vecSelectedFilters = null;
/*  100 */   private Vector m_vecPermissionCategoriesToRefine = new Vector();
/*  101 */   private Vector m_vecDescriptiveCategoriesToRefine = new Vector();
/*  102 */   private String m_sLanguageCode = null;
/*  103 */   private Vector<Long> m_vAssetEntityIdsToExclude = null;
/*  104 */   private Vector<Long> m_vAssetEntityIdsToInclude = null;
/*  105 */   private long m_lSortAttributeId = 0L;
/*  106 */   private boolean m_bSortDescending = false;
/*  107 */   private boolean m_bAdvancedViewing = false;
/*  108 */   private Boolean m_boolIsComplete = null;
/*  109 */   private boolean m_bPerformingApprovalSearch = false;
/*  110 */   private CategoryManager m_categoryManager = null;
/*      */   private Map<String, String> m_externalFilterCriteria;
/*      */ 
/*      */   private CategoryManager getCategoryManager()
/*      */     throws Bn2Exception
/*      */   {
/*  114 */     if (this.m_categoryManager == null)
/*      */     {
/*      */       try
/*      */       {
/*  118 */         this.m_categoryManager = ((CategoryManager)GlobalApplication.getInstance().getComponentManager().lookup("CategoryManager"));
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  122 */         throw new Bn2Exception("CategoryApprovalFilter.getCategoryManager: Error looking up category manager: " + e.getMessage(), e);
/*      */       }
/*      */     }
/*  125 */     return this.m_categoryManager;
/*      */   }
/*      */ 
/*      */   public boolean getOrCategories()
/*      */   {
/*  141 */     return this.m_bOrCategories;
/*      */   }
/*      */ 
/*      */   public void setOrCategories(boolean a_bOrCategories)
/*      */   {
/*  149 */     this.m_bOrCategories = a_bOrCategories;
/*      */   }
/*      */ 
/*      */   public String getCategoryIds()
/*      */   {
/*  154 */     return this.m_sCategoryIds;
/*      */   }
/*      */ 
/*      */   public void setCategoryIds(String a_sCategoryIds)
/*      */   {
/*  159 */     this.m_sCategoryIds = a_sCategoryIds;
/*      */   }
/*      */ 
/*      */   public Vector getAttributeExclusions()
/*      */   {
/*  165 */     return this.m_vAttributeExclusions;
/*      */   }
/*      */ 
/*      */   public void setAttributeExclusions(Vector a_sAttributeExclusions)
/*      */   {
/*  171 */     this.m_vAttributeExclusions = a_sAttributeExclusions;
/*      */   }
/*      */ 
/*      */   public boolean getIncludeImplicitCategoryMembers()
/*      */   {
/*  177 */     return this.m_bIncludeImplicitCategoryMembers;
/*      */   }
/*      */ 
/*      */   public void setIncludeImplicitCategoryMembers(boolean a_sIncludeImplicitCategoryMembers)
/*      */   {
/*  183 */     this.m_bIncludeImplicitCategoryMembers = a_sIncludeImplicitCategoryMembers;
/*      */   }
/*      */ 
/*      */   public int getPageIndex()
/*      */   {
/*  189 */     return this.m_iPageIndex;
/*      */   }
/*      */ 
/*      */   public void setPageIndex(int a_iPageIndex)
/*      */   {
/*  195 */     this.m_iPageIndex = a_iPageIndex;
/*      */   }
/*      */ 
/*      */   public int getPageSize()
/*      */   {
/*  201 */     return this.m_iPageSize;
/*      */   }
/*      */ 
/*      */   public void setPageSize(int a_iPageSize)
/*      */   {
/*  207 */     this.m_iPageSize = a_iPageSize;
/*      */   }
/*      */ 
/*      */   public BrightMoney getPriceLower()
/*      */   {
/*  212 */     return this.m_priceLower;
/*      */   }
/*      */ 
/*      */   public void setPriceLower(BrightMoney a_sPriceLower)
/*      */   {
/*  217 */     this.m_priceLower = a_sPriceLower;
/*      */   }
/*      */ 
/*      */   public BrightMoney getPriceUpper()
/*      */   {
/*  222 */     return this.m_priceUpper;
/*      */   }
/*      */ 
/*      */   public void setPriceUpper(BrightMoney a_sPriceUpper)
/*      */   {
/*  227 */     this.m_priceUpper = a_sPriceUpper;
/*      */   }
/*      */ 
/*      */   public boolean getWithoutCategory()
/*      */   {
/*  232 */     return this.m_bWithoutCategory;
/*      */   }
/*      */ 
/*      */   public void setWithoutCategory(boolean a_sWithoutCategory)
/*      */   {
/*  237 */     this.m_bWithoutCategory = a_sWithoutCategory;
/*      */   }
/*      */ 
/*      */   public void setSelectedFilters(Vector a_vecSelectedFilters)
/*      */   {
/*  242 */     this.m_vecSelectedFilters = a_vecSelectedFilters;
/*      */   }
/*      */ 
/*      */   public void addSelectedFilter(Filter a_filter)
/*      */   {
/*  247 */     if (getSelectedFilters() == null)
/*      */     {
/*  249 */       setSelectedFilters(new Vector());
/*      */     }
/*  251 */     getSelectedFilters().add(a_filter);
/*      */   }
/*      */ 
/*      */   public void addSelectedFilters(Vector a_vecFilters)
/*      */   {
/*  256 */     if (getSelectedFilters() == null)
/*      */     {
/*  258 */       setSelectedFilters(new Vector());
/*      */     }
/*  260 */     getSelectedFilters().addAll(a_vecFilters);
/*      */   }
/*      */ 
/*      */   public Vector getSelectedFilters()
/*      */   {
/*  265 */     return this.m_vecSelectedFilters;
/*      */   }
/*      */ 
/*      */   public void setPermissionCategoriesToRefine(Vector a_vecPermissionCategoriesToRefine)
/*      */   {
/*  270 */     this.m_vecPermissionCategoriesToRefine = a_vecPermissionCategoriesToRefine;
/*      */   }
/*      */ 
/*      */   public Vector getPermissionCategoriesToRefine()
/*      */   {
/*  275 */     return this.m_vecPermissionCategoriesToRefine;
/*      */   }
/*      */ 
/*      */   public void setDescriptiveCategoriesToRefine(Vector a_vecDescriptiveCategoriesToRefine)
/*      */   {
/*  280 */     this.m_vecDescriptiveCategoriesToRefine = a_vecDescriptiveCategoriesToRefine;
/*      */   }
/*      */ 
/*      */   public Vector getDescriptiveCategoriesToRefine()
/*      */   {
/*  285 */     return this.m_vecDescriptiveCategoriesToRefine;
/*      */   }
/*      */ 
/*      */   public boolean getCatgeoryToBeRefined(String a_sCategoryId)
/*      */   {
/*      */     try
/*      */     {
/*  294 */       if (getPermissionCategoriesToRefine() != null)
/*      */       {
/*  296 */         for (int i = 0; i < getPermissionCategoriesToRefine().size(); i++)
/*      */         {
/*  298 */           if (getPermissionCategoriesToRefine().elementAt(i).equals(a_sCategoryId))
/*      */           {
/*  300 */             return true;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  306 */       if (getDescriptiveCategoriesToRefine() != null)
/*      */       {
/*  308 */         for (int i = 0; i < getDescriptiveCategoriesToRefine().size(); i++)
/*      */         {
/*  310 */           if (getDescriptiveCategoriesToRefine().elementAt(i).equals(a_sCategoryId))
/*      */           {
/*  312 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  319 */       GlobalApplication.getInstance().getLogger().error("SearchCriteria.getCategoryToBeRefined: error checking category refinement: " + e.getMessage());
/*      */     }
/*      */ 
/*  322 */     return false;
/*      */   }
/*      */ 
/*      */   public String getLanguageCode()
/*      */   {
/*  327 */     return this.m_sLanguageCode;
/*      */   }
/*      */ 
/*      */   public void setLanguageCode(String languageCode)
/*      */   {
/*  332 */     this.m_sLanguageCode = languageCode;
/*      */   }
/*      */ 
/*      */   public boolean getIncludePreviousVersions()
/*      */   {
/*  337 */     return this.m_bIncludePreviousVersions;
/*      */   }
/*      */ 
/*      */   public void setIncludePreviousVersions(boolean includePreviousVersions)
/*      */   {
/*  342 */     this.m_bIncludePreviousVersions = includePreviousVersions;
/*      */   }
/*      */ 
/*      */   protected void addDateQuery(Vector a_vecDateRanges, String a_sFieldName, Date a_dtLower, Date a_dtUpper)
/*      */   {
/*  365 */     if ((a_dtLower == null) && (a_dtUpper == null))
/*      */     {
/*  367 */       return;
/*      */     }
/*      */ 
/*  370 */     FieldDateRange fieldRange = new FieldDateRange();
/*      */ 
/*  373 */     fieldRange.setLower(-9223372036854775808L);
/*  374 */     fieldRange.setUpper(9223372036854775807L);
/*      */ 
/*  377 */     if (a_dtLower != null)
/*      */     {
/*  379 */       fieldRange.setLower(a_dtLower.getTime());
/*      */     }
/*      */ 
/*  382 */     if (a_dtUpper != null)
/*      */     {
/*  384 */       fieldRange.setUpper(a_dtUpper.getTime());
/*      */     }
/*      */ 
/*  388 */     fieldRange.setFieldName(a_sFieldName);
/*      */ 
/*  391 */     a_vecDateRanges.add(fieldRange);
/*      */   }
/*      */ 
/*      */   protected void addNumericQuery(Vector a_vecNumericRanges, String a_sFieldName, Number a_dLower, Number a_dUpper)
/*      */   {
/*  411 */     FieldNumericRange fieldRange = new FieldNumericRange();
/*      */ 
/*  414 */     fieldRange.setLower(Double.valueOf(-1.797693134862316E+307D));
/*  415 */     fieldRange.setUpper(Double.valueOf(1.7976931348623157E+308D));
/*      */ 
/*  417 */     if (a_dLower != null)
/*      */     {
/*  419 */       fieldRange.setLower(a_dLower);
/*      */     }
/*      */ 
/*  422 */     if (a_dUpper != null)
/*      */     {
/*  424 */       fieldRange.setUpper(a_dUpper);
/*      */     }
/*      */ 
/*  427 */     fieldRange.setFieldName(a_sFieldName);
/*      */ 
/*  430 */     a_vecNumericRanges.add(fieldRange);
/*      */   }
/*      */ 
/*      */   protected String addCategorySearch(String a_sQuery, Vector a_vecFilterIds)
/*      */   {
/*  437 */     if (this.m_bWithoutCategory)
/*      */     {
/*  439 */       if (a_sQuery.length() > 0)
/*      */       {
/*  441 */         a_sQuery = "(" + a_sQuery + ") AND ";
/*      */       }
/*  443 */       a_sQuery = a_sQuery + "f_catsEmpty:(0) ";
/*      */     }
/*      */     else
/*      */     {
/*  448 */       if ((this.m_sCategoryIds != null) && (this.m_sCategoryIds.trim().length() > 0))
/*      */       {
/*      */         String sCategoryIds;
/*      */         //String sCategoryIds;
/*  450 */         if (this.m_bOrCategories)
/*      */         {
/*  453 */           sCategoryIds = this.m_sCategoryIds.replaceAll(",", " OR ");
/*      */         }
/*      */         else
/*      */         {
/*  458 */           sCategoryIds = this.m_sCategoryIds.replaceAll(",", " AND ");
/*      */         }
/*      */ 
/*  461 */         if (a_sQuery.length() > 0)
/*      */         {
/*  463 */           a_sQuery = "(" + a_sQuery + ") AND ";
/*      */         }
/*      */ 
/*  466 */         if (this.m_bIncludeImplicitCategoryMembers)
/*      */         {
/*  468 */           a_sQuery = a_sQuery + "f_allCatIds:(" + sCategoryIds + ") ";
/*      */         }
/*      */         else
/*      */         {
/*  472 */           a_sQuery = a_sQuery + "f_expCatIds:(" + sCategoryIds + ") ";
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  477 */       if ((a_vecFilterIds != null) && (a_vecFilterIds.size() > 0))
/*      */       {
/*  479 */         String sFilterCats = null;
/*      */ 
/*  481 */         for (int w = 0; w < a_vecFilterIds.size(); w++)
/*      */         {
/*  483 */           String sTempId = (String)a_vecFilterIds.elementAt(w);
/*  484 */           if (!StringUtil.stringIsPopulated(sTempId))
/*      */             continue;
/*  486 */           if (sFilterCats == null)
/*      */           {
/*  488 */             sFilterCats = sTempId;
/*      */           }
/*      */           else
/*      */           {
/*  492 */             sFilterCats = sFilterCats + " AND " + sTempId;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  497 */         if (StringUtil.stringIsPopulated(sFilterCats))
/*      */         {
/*  499 */           if (a_sQuery.length() > 0)
/*      */           {
/*  501 */             a_sQuery = "(" + a_sQuery + ") AND ";
/*      */           }
/*  503 */           a_sQuery = a_sQuery + "f_allCatIds:(" + sFilterCats + ") ";
/*      */         }
/*      */       }
/*      */     }
/*  507 */     return a_sQuery;
/*      */   }
/*      */ 
/*      */   protected String addPreviousVersionSearch(String a_sQuery)
/*      */   {
/*  512 */     if (!this.m_bIncludePreviousVersions)
/*      */     {
/*  514 */       if (a_sQuery.length() > 0)
/*      */       {
/*  516 */         a_sQuery = "(" + a_sQuery + ") AND ";
/*      */       }
/*      */ 
/*  519 */       a_sQuery = a_sQuery + "f_leafVersion:(1)";
/*      */     }
/*  521 */     return a_sQuery;
/*      */   }
/*      */ 
/*      */   protected String addEntitySearch(String a_sQuery)
/*      */   {
/*  527 */     if ((this.m_vAssetEntityIdsToInclude != null) && (!this.m_vAssetEntityIdsToInclude.contains(new Long(-999L))))
/*      */     {
/*  529 */       a_sQuery = addIdListToQuery(a_sQuery, CollectionUtil.getLongArray(this.m_vAssetEntityIdsToInclude), "f_entityId", false);
/*      */     }
/*      */ 
/*  533 */     a_sQuery = addIdListToQuery(a_sQuery, CollectionUtil.getLongArray(this.m_vAssetEntityIdsToExclude), "f_entityId", true);
/*      */ 
/*  535 */     return a_sQuery;
/*      */   }
/*      */ 
/*      */   protected String addAttributeExclusions(String sQuery)
/*      */   {
/*  541 */     if ((sQuery.length() > 0) && (getAttributeExclusions() != null) && (getAttributeExclusions().size() > 0))
/*      */     {
/*  543 */       sQuery = "(" + sQuery + ")";
/*      */ 
/*  546 */       for (int i = 0; i < getAttributeExclusions().size(); i++)
/*      */       {
/*  548 */         AttributeValue attVal = (AttributeValue)getAttributeExclusions().get(i);
/*      */ 
/*  550 */         sQuery = sQuery + " NOT f_att_" + attVal.getAttribute().getId() + ":\"" + attVal.getValue() + "\" ";
/*      */       }
/*      */     }
/*      */ 
/*  554 */     return sQuery;
/*      */   }
/*      */ 
/*      */   protected String addIdListToQuery(String a_sQuery, long[] a_alIds, String a_sFieldname, boolean a_bNegative)
/*      */   {
/*  560 */     String sIds = "";
/*      */ 
/*  562 */     if (a_alIds != null)
/*      */     {
/*  564 */       for (int i = 0; i < a_alIds.length; i++)
/*      */       {
/*  566 */         sIds = sIds + a_alIds[i];
/*      */ 
/*  568 */         if (i >= a_alIds.length - 1)
/*      */           continue;
/*  570 */         sIds = sIds + " OR ";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  575 */     if (StringUtils.isNotEmpty(sIds))
/*      */     {
/*  577 */       if (a_sQuery.length() > 0)
/*      */       {
/*  579 */         a_sQuery = "(" + a_sQuery + ") AND ";
/*      */       }
/*      */ 
/*  582 */       if (a_bNegative)
/*      */       {
/*  584 */         a_sQuery = a_sQuery + " NOT ";
/*      */       }
/*      */ 
/*  587 */       a_sQuery = a_sQuery + a_sFieldname + ":(" + sIds + ")";
/*      */     }
/*  589 */     return a_sQuery;
/*      */   }
/*      */ 
/*      */   public long getSortAttributeId()
/*      */   {
/*  594 */     return this.m_lSortAttributeId;
/*      */   }
/*      */ 
/*      */   public void setSortAttributeId(long a_iSortAttributeId)
/*      */   {
/*  599 */     this.m_lSortAttributeId = a_iSortAttributeId;
/*      */   }
/*      */ 
/*      */   public boolean isSortDescending()
/*      */   {
/*  604 */     return this.m_bSortDescending;
/*      */   }
/*      */ 
/*      */   public void setSortDescending(boolean a_iSortDescending)
/*      */   {
/*  609 */     this.m_bSortDescending = a_iSortDescending;
/*      */   }
/*      */ 
/*      */   public long getBulkUpload()
/*      */   {
/*  615 */     return this.m_lBulkUpload;
/*      */   }
/*      */ 
/*      */   public void setBulkUpload(long a_sBulkUpload)
/*      */   {
/*  621 */     this.m_lBulkUpload = a_sBulkUpload;
/*      */   }
/*      */ 
/*      */   public Vector<Long> getAssetEntityIdsToExclude()
/*      */   {
/*  626 */     return this.m_vAssetEntityIdsToExclude;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityIdsToExclude(Vector<Long> assetEntityIdsToExclude)
/*      */   {
/*  631 */     this.m_vAssetEntityIdsToExclude = assetEntityIdsToExclude;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityIdsToInclude(Vector<Long> assetEntityIdsToInclude)
/*      */   {
/*  636 */     this.m_vAssetEntityIdsToInclude = assetEntityIdsToInclude;
/*      */   }
/*      */ 
/*      */   public Vector<Long> getAssetEntityIdsToInclude()
/*      */   {
/*  641 */     return this.m_vAssetEntityIdsToInclude;
/*      */   }
/*      */ 
/*      */   public void addAssetEntityIdToInclude(long a_lId)
/*      */   {
/*  646 */     if (this.m_vAssetEntityIdsToInclude == null)
/*      */     {
/*  648 */       this.m_vAssetEntityIdsToInclude = new Vector();
/*      */     }
/*  650 */     this.m_vAssetEntityIdsToInclude.add(new Long(a_lId));
/*      */   }
/*      */ 
/*      */   public boolean assetEntityIncludedInSearch(long a_lId)
/*      */   {
/*  655 */     if (this.m_vAssetEntityIdsToInclude != null)
/*      */     {
/*  657 */       return this.m_vAssetEntityIdsToInclude.contains(new Long(a_lId));
/*      */     }
/*  659 */     return false;
/*      */   }
/*      */ 
/*      */   public Map<String, String> getExternalFilterCriteria()
/*      */   {
/*  664 */     return this.m_externalFilterCriteria;
/*      */   }
/*      */ 
/*      */   public void setExternalFilterCriteria(Map<String, String> a_externalFilterCriteria)
/*      */   {
/*  669 */     this.m_externalFilterCriteria = a_externalFilterCriteria;
/*      */   }
/*      */ 
/*      */   protected String addAttributeSearches(String a_sQuery, Vector a_vecAttributeVals, boolean a_bAttributeSearchesAreEscaped)
/*      */   {
/*  684 */     if ((a_vecAttributeVals != null) && (a_vecAttributeVals.size() > 0))
/*      */     {
/*  686 */       if (a_sQuery.length() > 0)
/*      */       {
/*  688 */         a_sQuery = "(" + a_sQuery + ")";
/*      */       }
/*      */ 
/*  691 */       for (int i = 0; i < a_vecAttributeVals.size(); i++)
/*      */       {
/*  693 */         AttributeValue attVal = (AttributeValue)a_vecAttributeVals.get(i);
/*  694 */         Attribute attribute = attVal.getAttribute();
/*  695 */         String sValue = attVal.getValue();
/*      */ 
/*  697 */         if (!a_bAttributeSearchesAreEscaped)
/*      */         {
/*  699 */           sValue = mungeAttributeValueForLuceneQuery(attribute, sValue);
/*      */         }
/*      */ 
/*  702 */         if (a_sQuery.length() > 0)
/*      */         {
/*  704 */           a_sQuery = a_sQuery + " AND ";
/*      */         }
/*      */ 
/*  707 */         if (!attVal.getRange())
/*      */         {
/*  709 */           sValue = "(" + sValue + ")";
/*      */         }
/*      */ 
/*  712 */         String sFieldName = AttributeUtil.getFlexibleAttributeIndexFieldName(attribute);
/*  713 */         a_sQuery = a_sQuery + " " + sFieldName + ":" + sValue + " ";
/*      */       }
/*      */     }
/*  716 */     return a_sQuery;
/*      */   }
/*      */ 
/*      */   protected String mungeAttributeValueForLuceneQuery(Attribute a_attribute, String a_sValue)
/*      */   {
/*  721 */     boolean a_bIsListAttribute = a_attribute.getIsList();
/*  722 */     boolean a_bDelimiterIsSpace = a_attribute.getDelimiterIsSpace();
/*  723 */     String a_sTokenDelimiterRegex = a_attribute.getTokenDelimiterRegex();
/*  724 */     return mungeAttributeValueForLuceneQuery(a_sValue, a_bIsListAttribute, a_bDelimiterIsSpace, a_sTokenDelimiterRegex);
/*      */   }
/*      */ 
/*      */   protected String mungeAttributeValueForLuceneQuery(String a_sValue, boolean a_bIsListAttribute, boolean a_bDelimiterIsSpace, String a_sTokenDelimiterRegex)
/*      */   {
/*  730 */     if (a_bIsListAttribute)
/*      */     {
/*  732 */       a_sValue = "\"" + a_sValue + "\"";
/*      */     }
/*  736 */     else if ((!a_bDelimiterIsSpace) && (a_sValue != null))
/*      */     {
/*  742 */       String[] tokens = a_sValue.split(a_sTokenDelimiterRegex);
/*      */ 
/*  744 */       StringBuilder sbValue = new StringBuilder("(");
/*  745 */       for (int i = 0; i < tokens.length; i++)
/*      */       {
/*  747 */         String token = tokens[i];
/*  748 */         if (token.length() <= 0)
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  756 */         sbValue.append(token.replace(" ", "\\ "));
/*  757 */         sbValue.append(" ");
/*      */       }
/*      */ 
/*  760 */       sbValue.append(")");
/*  761 */       a_sValue = sbValue.toString();
/*      */     }
/*      */ 
/*  764 */     return a_sValue;
/*      */   }
/*      */ 
/*      */   protected Vector addFilterCriteria(Vector a_vAttributeVals, Vector a_vCatIds)
/*      */   {
/*  775 */     if (getSelectedFilters() != null)
/*      */     {
/*  777 */       Enumeration e = getSelectedFilters().elements();
/*      */ 
/*  779 */       while (e.hasMoreElements())
/*      */       {
/*  781 */         Filter currentFilter = (Filter)e.nextElement();
/*      */ 
/*  783 */         if ((currentFilter != null) && (currentFilter.getId() > 0L))
/*      */         {
/*  785 */           for (int i = 0; i < currentFilter.getAttributeValues().size(); i++)
/*      */           {
/*  787 */             if (a_vAttributeVals == null)
/*      */             {
/*  789 */               a_vAttributeVals = new Vector();
/*      */             }
/*  791 */             AttributeValue value = (AttributeValue)currentFilter.getAttributeValues().elementAt(i);
/*      */ 
/*  793 */             boolean bIsDate = false;
/*  794 */             String sField = value.getAttribute().getFieldName();
/*  795 */             if ((value.getAttribute().getIsDatepicker()) || ((sField != null) && ((sField.equals("dateAdded")) || (sField.equals("dateLastModified")) || (sField.equals("dateLastDownloaded")))))
/*      */             {
/*  801 */               bIsDate = true;
/*      */             }
/*      */ 
/*  804 */             if (bIsDate)
/*      */               continue;
/*  806 */             if (!StringUtil.stringIsPopulated(value.getValue()))
/*      */               continue;
/*  808 */             a_vAttributeVals.add(value);
/*      */           }
/*      */ 
/*  814 */           if (StringUtil.stringIsPopulated(currentFilter.getCategoryIds()))
/*      */           {
/*  816 */             String[] aCats = currentFilter.getCategoryIds().split(",");
/*  817 */             a_vCatIds.addAll(Arrays.asList(aCats));
/*      */           }
/*  819 */           if (StringUtil.stringIsPopulated(currentFilter.getAccessLevelIds()))
/*      */           {
/*  821 */             String[] aCats = currentFilter.getAccessLevelIds().split(",");
/*  822 */             a_vCatIds.addAll(Arrays.asList(aCats));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  827 */     return a_vAttributeVals;
/*      */   }
/*      */ 
/*      */   public void setupPermissions(ABUserProfile a_userProfile)
/*      */   {
/*  838 */     if (!a_userProfile.getIsAdmin())
/*      */     {
/*  840 */       setAttributeExclusions(a_userProfile.getAttributeExclusions());
/*  841 */       setRequiredRestrictiveCategories(a_userProfile.getVisiblePermissionCategories());
/*  842 */       if ((AssetBankSettings.getAdvancedViewingEnabled()) && (a_userProfile.hasAdvancedViewing()))
/*      */       {
/*  845 */         setAdvancedViewing(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public SearchFilter[] getSearchFilters()
/*      */     throws Bn2Exception
/*      */   {
/*  858 */     SearchFilter catApprovalFilter = getCategoryApprovalFilter();
/*      */ 
/*  860 */     SearchFilter permissionCatFilter = getPermissionCategoryFilter();
/*      */ 
/*  862 */     int iNumFilters = 0;
/*      */ 
/*  864 */     if (catApprovalFilter != null)
/*      */     {
/*  866 */       iNumFilters++;
/*      */     }
/*  868 */     if (permissionCatFilter != null)
/*      */     {
/*  870 */       iNumFilters++;
/*      */     }
/*      */ 
/*  873 */     SearchFilter[] filters = new SearchFilter[iNumFilters];
/*      */ 
/*  877 */     if (permissionCatFilter != null)
/*      */     {
/*  879 */       filters[0] = permissionCatFilter;
/*      */     }
/*  881 */     if (catApprovalFilter != null)
/*      */     {
/*  883 */       filters[(iNumFilters - 1)] = catApprovalFilter;
/*      */     }
/*      */ 
/*  886 */     return filters;
/*      */   }
/*      */ 
/*      */   public Boolean getPerformingApprovalSearch()
/*      */   {
/*  891 */     return Boolean.valueOf(this.m_bPerformingApprovalSearch);
/*      */   }
/*      */ 
/*      */   public void setPerformingApprovalSearch(boolean a_bPerformingApprovalSearch)
/*      */   {
/*  897 */     this.m_bPerformingApprovalSearch = a_bPerformingApprovalSearch;
/*      */   }
/*      */ 
/*      */   public Set<Long> getRequiredRestrictiveCategories()
/*      */   {
/*  902 */     return this.m_requiredRestrictiveCategories;
/*      */   }
/*      */ 
/*      */   public void setRequiredRestrictiveCategories(Set<Long> a_categories)
/*      */   {
/*  907 */     this.m_requiredRestrictiveCategories = a_categories;
/*      */   }
/*      */ 
/*      */   public void setAdvancedViewing(boolean a_bAdvancedViewing)
/*      */   {
/*  912 */     this.m_bAdvancedViewing = a_bAdvancedViewing;
/*      */   }
/*      */ 
/*      */   public boolean getAdvancedViewing()
/*      */   {
/*  917 */     return this.m_bAdvancedViewing;
/*      */   }
/*      */ 
/*      */   public Boolean getIsComplete()
/*      */   {
/*  922 */     return this.m_boolIsComplete;
/*      */   }
/*      */ 
/*      */   public void setIsComplete(Boolean a_boolIsComplete)
/*      */   {
/*  927 */     this.m_boolIsComplete = a_boolIsComplete;
/*      */   }
/*      */ 
/*      */   public SearchFilter getPermissionCategoryFilter()
/*      */   {
/*  932 */     if ((this.m_requiredRestrictiveCategories == null) || (this.m_requiredRestrictiveCategories.isEmpty()))
/*      */     {
/*  934 */       return null;
/*      */     }
/*      */ 
/*  937 */     PermissionCategoryFilter permCatFilter = new PermissionCategoryFilter();
/*  938 */     permCatFilter.setPermissionCategories(this.m_requiredRestrictiveCategories);
/*  939 */     return permCatFilter;
/*      */   }
/*      */ 
/*      */   protected CategoryApprovalFilter buildCategoryApprovalFilter()
/*      */     throws Bn2Exception
/*      */   {
/*  950 */     CategoryApprovalFilter filter = null;
/*  951 */     if (!getPerformingApprovalSearch().booleanValue())
/*      */     {
/*  955 */       filter = new CategoryApprovalFilter();
/*  956 */       filter.setAdvancedViewing(getAdvancedViewing());
/*  957 */       filter.setPermissionCategories(getRequiredRestrictiveCategories());
/*      */ 
/*  963 */       if (StringUtil.stringIsPopulated(getCategoryIds()))
/*      */       {
/*  965 */         Set givenCatIds = StringUtil.convertToSetOfLongs(getCategoryIds(), ",");
/*  966 */         Set restrictiveCatIds = new HashSet();
/*      */ 
/*  968 */         if (!givenCatIds.isEmpty())
/*      */         {
/*  971 */           for (Iterator iterator = givenCatIds.iterator(); iterator.hasNext(); )
/*      */           {
/*  973 */             Long lId = (Long)iterator.next();
/*  974 */             Category cat = getCategoryManager().getCategory(null, 2L, lId.longValue());
/*  975 */             if ((cat != null) && (cat.getId() > 0L))
/*      */             {
/*  978 */               Category restrictiveCat = cat.getClosestRestrictiveAncestor();
/*      */ 
/*  980 */               if (restrictiveCat != null)
/*      */               {
/*  982 */                 restrictiveCatIds.add(Long.valueOf(restrictiveCat.getId()));
/*      */               }
/*      */               else
/*      */               {
/*  986 */                 c_logger.warn("Category '" + cat.getFullName() + "' with type id " + cat.getCategoryTypeId() + " has no restrictive ancestor.");
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  992 */           if (!restrictiveCatIds.isEmpty())
/*      */           {
/*  994 */             filter.setSearchCategories(restrictiveCatIds);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  999 */     return filter;
/*      */   }
/*      */ 
/*      */   public void addCategoryRestrictions(long a_lGroupId)
/*      */     throws Bn2Exception, ComponentException
/*      */   {
/* 1006 */     ABUserManager userManager = (ABUserManager)(ABUserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager");
/* 1007 */     Group group = userManager.getGroup(a_lGroupId);
/*      */ 
/* 1010 */     Vector<GroupCategoryPermission> vecPerms = group.getCategoryPermissions();
/* 1011 */     Set hsCategoryIds = new HashSet();
/* 1012 */     if (getRequiredRestrictiveCategories() == null)
/*      */     {
/* 1014 */       setRequiredRestrictiveCategories(hsCategoryIds);
/*      */     }
/*      */ 
/* 1017 */     if (vecPerms != null)
/*      */     {
/* 1019 */       for (GroupCategoryPermission gcp : vecPerms)
/*      */       {
/* 1021 */         if (gcp.getDownloadPermissionLevel() >= 1)
/*      */         {
/* 1023 */           getRequiredRestrictiveCategories().add(new Long(gcp.getCategory().getId()));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1031 */       getRequiredRestrictiveCategories().add(new Long(-1L));
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getGroupRestrictionString()
/*      */   {
/* 1041 */     return "";
/*      */   }
/*      */ 
/*      */   protected abstract CategoryApprovalFilter getCategoryApprovalFilter()
/*      */     throws Bn2Exception;
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.bean.BaseSearchQuery
 * JD-Core Version:    0.6.0
 */