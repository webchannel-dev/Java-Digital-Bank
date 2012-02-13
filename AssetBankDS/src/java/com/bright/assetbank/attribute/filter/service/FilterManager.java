/*      */ package com.bright.assetbank.attribute.filter.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*      */ import com.bright.assetbank.attribute.filter.bean.Filter.Translation;
/*      */ import com.bright.assetbank.attribute.filter.bean.FilterGroup;
/*      */ import com.bright.assetbank.attribute.util.AttributeDBUtil;
/*      */ import com.bright.assetbank.attribute.util.AttributeValueSequenceComparator;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*      */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.bean.FlatCategoryList;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.BrightDateTime;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.bean.LanguageAwareComponent;
/*      */ import com.bright.framework.language.util.LanguageUtils;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.util.CollectionUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class FilterManager extends Bn2Manager
/*      */   implements AssetBankSearchConstants, LanguageAwareComponent
/*      */ {
/*      */   private static final String c_ksClassName = "FilterManager";
/*   92 */   private DBTransactionManager m_transactionManager = null;
/*   93 */   private CategoryManager m_categoryManager = null;
/*      */ 
/*   95 */   private Object m_objDefaultFilterLock = new Object();
/*   96 */   private Filter m_defaultFilter = null;
/*      */ 
/*   98 */   private Object m_objFiltersLock = new Object();
/*   99 */   private Vector m_vecAllFilters = null;
/*      */ 
/*  101 */   private HashMap m_hmFiltersByGroupByType = new HashMap();
/*  102 */   private Object m_objFiltersByGroupLock = new Object();
/*      */ 
/*  104 */   private Object m_objCategoryFiltersLock = new Object();
/*  105 */   private HashMap m_hmCategoryFilterCache = new HashMap();
/*  106 */   private HashMap m_hmAccessLevelFilterCache = new HashMap();
/*      */ 
/*      */   public Filter getFilter(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  121 */     if (this.m_vecAllFilters != null)
/*      */     {
/*  123 */       for (int i = 0; i < this.m_vecAllFilters.size(); i++)
/*      */       {
/*  125 */         Filter filter = (Filter)this.m_vecAllFilters.elementAt(i);
/*  126 */         if (filter.getId() == a_lId)
/*      */         {
/*  128 */           return filter;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  134 */     return getFilterFromDatabase(a_dbTransaction, a_lId, false);
/*      */   }
/*      */ 
/*      */   private Filter getFilterFromDatabase(DBTransaction a_dbTransaction, long a_lId, boolean a_bDefault)
/*      */     throws Bn2Exception
/*      */   {
/*  155 */     String ksMethodName = "getFilter";
/*  156 */     Filter filter = null;
/*  157 */     Connection con = null;
/*  158 */     ResultSet rs = null;
/*  159 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*  163 */       if (transaction == null)
/*      */       {
/*  165 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*  167 */       con = transaction.getConnection();
/*      */ 
/*  169 */       String sSQL = "SELECT f.Id fId, f.Name fName, f.FilterTypeId, f.IsDefault fIsDefault, f.CategoryIds, f.AccessLevelIds, f.SequenceNumber, tf.Name tfName, l.Id lId, l.Code lCode, l.Name lName FROM Filter f LEFT JOIN TranslatedFilter tf ON tf.FilterId=f.Id LEFT JOIN Language l ON l.Id=tf.LanguageId WHERE ";
/*      */ 
/*  185 */       if (a_bDefault)
/*      */       {
/*  187 */         sSQL = sSQL + "f.IsDefault=1";
/*      */       }
/*      */       else
/*      */       {
/*  191 */         sSQL = sSQL + "f.Id=?";
/*      */       }
/*      */ 
/*  195 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  197 */       if (!a_bDefault)
/*      */       {
/*  199 */         psql.setLong(1, a_lId);
/*      */       }
/*      */ 
/*  202 */       rs = psql.executeQuery();
/*  203 */       long lLastFilterId = 0L;
/*      */ 
/*  205 */       while (rs.next())
/*      */       {
/*  207 */         if (rs.getLong("fId") == lLastFilterId)
/*      */           continue;
/*  209 */         filter = buildFilter(rs, transaction);
/*  210 */         lLastFilterId = filter.getId();
/*      */       }
/*      */ 
/*  214 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  218 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/*  222 */           transaction.rollback(); } catch (Exception ex) {
/*      */         }
/*      */       }
/*  225 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter from the database : " + e);
/*  226 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  230 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/*  234 */           transaction.commit();
/*      */         } catch (Exception ex) {
/*      */         }
/*      */       }
/*      */     }
/*  239 */     return filter;
/*      */   }
/*      */ 
/*      */   private Vector getFilterGroupsForFilter(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  257 */     String ksMethodName = "getFilterGroupsForFilter";
/*  258 */     Vector vecFilterGroups = null;
/*  259 */     Connection con = null;
/*  260 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  264 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  266 */       String sSQL = "SELECT fg.Id fgId, fg.FilterTypeId, fg.Name fgName FROM FilterGroup fg LEFT JOIN FilterInGroup fig ON fg.Id=fig.GroupId WHERE fig.FilterId=?";
/*      */ 
/*  275 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  276 */       psql.setLong(1, a_lId);
/*  277 */       rs = psql.executeQuery();
/*      */ 
/*  279 */       while (rs.next())
/*      */       {
/*  281 */         FilterGroup fg = buildFilterGroup(rs);
/*  282 */         if (vecFilterGroups == null)
/*      */         {
/*  284 */           vecFilterGroups = new Vector();
/*      */         }
/*  286 */         vecFilterGroups.add(fg);
/*      */       }
/*      */ 
/*  289 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  293 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter groups from the database : " + e);
/*  294 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter groups from the database : " + e, e);
/*      */     }
/*      */ 
/*  297 */     return vecFilterGroups;
/*      */   }
/*      */ 
/*      */   public Vector getFilterGroups(DBTransaction a_dbTransaction, long a_lTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  316 */     String ksMethodName = "getFilterGroups";
/*  317 */     Vector vecFilterGroups = null;
/*  318 */     Connection con = null;
/*  319 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  323 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  325 */       String sSQL = "SELECT fg.Id fgId, fg.FilterTypeId, fg.Name fgName FROM FilterGroup fg";
/*      */ 
/*  327 */       if (a_lTypeId > 0L)
/*      */       {
/*  329 */         sSQL = sSQL + " WHERE fg.FilterTypeId=?";
/*      */       }
/*      */ 
/*  333 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  335 */       if (a_lTypeId > 0L)
/*      */       {
/*  337 */         psql.setLong(1, a_lTypeId);
/*      */       }
/*      */ 
/*  340 */       rs = psql.executeQuery();
/*      */ 
/*  342 */       while (rs.next())
/*      */       {
/*  344 */         FilterGroup fg = buildFilterGroup(rs);
/*  345 */         if (vecFilterGroups == null)
/*      */         {
/*  347 */           vecFilterGroups = new Vector();
/*      */         }
/*  349 */         vecFilterGroups.add(fg);
/*      */       }
/*      */ 
/*  352 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  356 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter groups from the database : " + e);
/*  357 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter groups from the database : " + e, e);
/*      */     }
/*      */ 
/*  360 */     return vecFilterGroups;
/*      */   }
/*      */ 
/*      */   public Vector getFiltersByGroupFilters(DBTransaction a_dbTransaction, Language a_lang, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  378 */     Vector vecGroupedFilters = CollectionUtil.deepCloneVectorOfDataBeans(getFiltersByGroup(a_dbTransaction, 1));
/*  379 */     boolean bFoundFilters = false;
/*      */ 
/*  381 */     if ((vecGroupedFilters != null) && (vecGroupedFilters.size() > 0))
/*      */     {
/*  383 */       for (int i = 0; i < vecGroupedFilters.size(); i++)
/*      */       {
/*  385 */         FilterGroup temp = (FilterGroup)vecGroupedFilters.elementAt(i);
/*  386 */         temp.setFilters(getCorrectedFilterList(temp.getFilters(), a_lang, a_userProfile.getFilterExclusions()));
/*  387 */         if ((temp.getFilters() == null) || (temp.getFilters().size() <= 0))
/*      */           continue;
/*  389 */         bFoundFilters = true;
/*      */       }
/*      */     }
/*      */ 
/*  393 */     if (bFoundFilters)
/*      */     {
/*  395 */       return vecGroupedFilters;
/*      */     }
/*  397 */     return null;
/*      */   }
/*      */ 
/*      */   private Vector getFiltersByGroup(DBTransaction a_dbTransaction, int a_iTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  415 */     String ksMethodName = "getFiltersByGroup";
/*      */ 
/*  417 */     if ((this.m_hmFiltersByGroupByType != null) && (this.m_hmFiltersByGroupByType.get(new Integer(a_iTypeId)) != null))
/*      */     {
/*  420 */       return (Vector)this.m_hmFiltersByGroupByType.get(new Integer(a_iTypeId));
/*      */     }
/*      */ 
/*  424 */     Connection con = null;
/*  425 */     ResultSet rs = null;
/*  426 */     Vector vecFiltersByGroup = null;
/*      */     try
/*      */     {
/*  430 */       con = a_dbTransaction.getConnection();
/*  431 */       String sSQL = "SELECT f.Id fId, f.UserId, f.Name fName, f.IsDefault fIsDefault, f.FilterTypeId, f.CategoryIds, f.AccessLevelIds, f.SequenceNumber, fg.Id fgId, fg.Name fgName FROM Filter f LEFT JOIN FilterInGroup fig ON f.Id=fig.FilterId LEFT JOIN FilterGroup fg ON fig.GroupId=fg.Id WHERE f.FilterTypeId=? AND f.Id NOT IN (SELECT DISTINCT FilterId FROM FilterForCategory) ORDER BY fg.Id, f.SequenceNumber";
/*      */ 
/*  449 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  450 */       psql.setInt(1, a_iTypeId);
/*  451 */       rs = psql.executeQuery();
/*  452 */       long lLastGroupId = -10L;
/*  453 */       Vector vecFilters = null;
/*  454 */       FilterGroup group = null;
/*      */ 
/*  456 */       while (rs.next())
/*      */       {
/*  458 */         if (rs.getLong("fgId") != lLastGroupId)
/*      */         {
/*  460 */           if (group != null)
/*      */           {
/*  462 */             group.setFilters(vecFilters);
/*      */ 
/*  464 */             if (vecFiltersByGroup == null)
/*      */             {
/*  466 */               vecFiltersByGroup = new Vector();
/*      */             }
/*      */ 
/*  469 */             vecFiltersByGroup.add(group);
/*      */           }
/*  471 */           group = buildFilterGroup(rs);
/*  472 */           vecFilters = new Vector();
/*  473 */           lLastGroupId = group.getId();
/*      */         }
/*  475 */         Filter filter = buildFilter(rs, a_dbTransaction);
/*  476 */         vecFilters.add(filter);
/*      */       }
/*      */ 
/*  480 */       if (group != null)
/*      */       {
/*  482 */         if (vecFiltersByGroup == null)
/*      */         {
/*  484 */           vecFiltersByGroup = new Vector();
/*      */         }
/*      */ 
/*  487 */         group.setFilters(vecFilters);
/*  488 */         vecFiltersByGroup.add(group);
/*      */       }
/*      */ 
/*  491 */       psql.close();
/*      */ 
/*  494 */       synchronized (this.m_objFiltersByGroupLock)
/*      */       {
/*  496 */         this.m_hmFiltersByGroupByType.put(new Integer(a_iTypeId), vecFiltersByGroup);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  501 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter groups from the database : " + e);
/*  502 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter groups from the database : " + e, e);
/*      */     }
/*      */ 
/*  505 */     return vecFiltersByGroup;
/*      */   }
/*      */ 
/*      */   public FilterGroup getFilterGroup(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  525 */     String ksMethodName = "getFilterGroup";
/*  526 */     FilterGroup group = null;
/*  527 */     Connection con = null;
/*  528 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  532 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  534 */       String sSQL = "SELECT fg.Id fgId, fg.FilterTypeId, fg.Name fgName FROM FilterGroup fg WHERE fg.Id=?";
/*      */ 
/*  537 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  538 */       psql.setLong(1, a_lId);
/*      */ 
/*  540 */       rs = psql.executeQuery();
/*      */ 
/*  542 */       if (rs.next())
/*      */       {
/*  544 */         group = buildFilterGroup(rs);
/*      */       }
/*      */ 
/*  547 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  551 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter group from the database : " + e);
/*  552 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst getting filter group from the database : " + e, e);
/*      */     }
/*      */ 
/*  555 */     return group;
/*      */   }
/*      */ 
/*      */   public Filter getDefaultFilter(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  575 */     if (this.m_defaultFilter != null)
/*      */     {
/*  577 */       return this.m_defaultFilter;
/*      */     }
/*      */ 
/*  581 */     Filter defaultFilter = getFilterFromDatabase(a_dbTransaction, -1L, true);
/*      */ 
/*  584 */     synchronized (this.m_objDefaultFilterLock)
/*      */     {
/*  586 */       this.m_defaultFilter = defaultFilter;
/*      */     }
/*      */ 
/*  589 */     return defaultFilter;
/*      */   }
/*      */ 
/*      */   public Vector getGlobalFilters(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  611 */     Vector vFilters = getFilters(a_dbTransaction, 1, -1L, -1L);
/*  612 */     Vector vGlobalFilters = new Vector();
/*      */ 
/*  614 */     for (int i = 0; i < vFilters.size(); i++)
/*      */     {
/*  616 */       Filter filter = (Filter)vFilters.elementAt(i);
/*  617 */       if ((filter.getLinkedToCategories() != null) && (filter.getLinkedToCategories().size() > 0))
/*      */         continue;
/*  619 */       vGlobalFilters.add(filter);
/*      */     }
/*      */ 
/*  623 */     return getCorrectedFilterList(vGlobalFilters, a_userProfile.getCurrentLanguage(), a_userProfile.getFilterExclusions());
/*      */   }
/*      */ 
/*      */   public Vector getAllFilters(DBTransaction a_dbTransaction, Language a_language, Vector a_vecExclusions)
/*      */     throws Bn2Exception
/*      */   {
/*  642 */     Vector vFilters = getFilters(a_dbTransaction, 1, -1L, -1L);
/*  643 */     return getCorrectedFilterList(vFilters, a_language, a_vecExclusions);
/*      */   }
/*      */ 
/*      */   public Vector getTemplates(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  663 */     Vector vFilters = getFilters(a_dbTransaction, 2, a_userProfile.getUser().getId(), -1L);
/*  664 */     return getCorrectedFilterList(vFilters, a_userProfile.getCurrentLanguage(), a_userProfile.getFilterExclusions());
/*      */   }
/*      */ 
/*      */   public Vector getFiltersForCategory(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/*  685 */     Vector vFilters = getFilters(a_dbTransaction, 1, -1L, a_lCategoryId);
/*  686 */     return getCorrectedFilterList(vFilters, a_userProfile.getCurrentLanguage(), a_userProfile.getFilterExclusions());
/*      */   }
/*      */ 
/*      */   public Vector getAllCategoryFilters(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  706 */     Vector vFilters = getFilters(a_dbTransaction, 1, -1L, -1L);
/*  707 */     Vector vCategoryFilters = new Vector();
/*      */ 
/*  709 */     for (int i = 0; i < vFilters.size(); i++)
/*      */     {
/*  711 */       Filter filter = (Filter)vFilters.elementAt(i);
/*  712 */       if ((filter.getLinkedToCategories() == null) || (filter.getLinkedToCategories().size() <= 0))
/*      */         continue;
/*  714 */       vCategoryFilters.add(filter);
/*      */     }
/*      */ 
/*  718 */     return getCorrectedFilterList(vCategoryFilters, a_userProfile.getCurrentLanguage(), a_userProfile.getFilterExclusions());
/*      */   }
/*      */ 
/*      */   private Vector getCorrectedFilterList(Vector a_vecFilters, Language a_language, Vector a_vecExclusions)
/*      */   {
/*  723 */     Vector vecTempFilters = CollectionUtil.deepCloneVectorOfDataBeans(a_vecFilters);
/*      */ 
/*  725 */     if (a_language != null)
/*      */     {
/*  727 */       LanguageUtils.setLanguageOnAll(vecTempFilters, a_language);
/*      */     }
/*      */ 
/*  731 */     Vector vecReturnFilters = vecTempFilters;
/*  732 */     if ((a_vecExclusions != null) && (a_vecExclusions.size() > 0) && (vecTempFilters != null) && (vecTempFilters.size() > 0))
/*      */     {
/*  735 */       Enumeration e = vecTempFilters.elements();
/*  736 */       vecReturnFilters = new Vector();
/*  737 */       while (e.hasMoreElements())
/*      */       {
/*  739 */         Filter possibleFilter = (Filter)e.nextElement();
/*  740 */         boolean bAdd = true;
/*  741 */         for (int i = 0; i < a_vecExclusions.size(); i++)
/*      */         {
/*  743 */           Filter excludedFilter = (Filter)a_vecExclusions.elementAt(i);
/*  744 */           if (excludedFilter.getId() != possibleFilter.getId())
/*      */             continue;
/*  746 */           bAdd = false;
/*  747 */           break;
/*      */         }
/*      */ 
/*  752 */         if (bAdd)
/*      */         {
/*  754 */           vecReturnFilters.add(possibleFilter);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  759 */     return vecReturnFilters;
/*      */   }
/*      */ 
/*      */   public void fixAllCategoryFilterOrdering(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  772 */     List<Category> categories = new ArrayList();
/*  773 */     categories.addAll(getFiltersByCategory(a_dbTransaction, 1L).keySet());
/*  774 */     categories.addAll(getFiltersByCategory(a_dbTransaction, 2L).keySet());
/*      */ 
/*  776 */     for (Category category : categories)
/*      */     {
/*  778 */       Category cat = category;
/*  779 */       fixFilterOrdering(a_dbTransaction, cat.getId(), false);
/*      */     }
/*      */   }
/*      */ 
/*      */   private LinkedHashMap<Category, List<Filter>> getFiltersByCategory(DBTransaction a_dbTransaction, long a_lCategoryTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  795 */     String ksMethodName = "getFiltersByCategory";
/*      */ 
/*  798 */     LinkedHashMap filtersByCategory = new LinkedHashMap();
/*      */     try
/*      */     {
/*  801 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/*  803 */       String sSQL = "SELECT ffc.CategoryId FROM FilterForCategory ffc LEFT JOIN CM_Category c ON ffc.CategoryId=c.Id WHERE c.CategoryTypeId=?";
/*      */ 
/*  808 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  809 */       psql.setLong(1, a_lCategoryTypeId);
/*  810 */       ResultSet rs = psql.executeQuery();
/*  811 */       Vector vecApplicableCategories = new Vector();
/*      */ 
/*  813 */       while (rs.next())
/*      */       {
/*  815 */         vecApplicableCategories.add(new Long(rs.getLong("CategoryId")));
/*      */       }
/*      */ 
/*  819 */       FlatCategoryList list = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, a_lCategoryTypeId);
/*      */ 
/*  822 */       if ((list != null) && (list.getCategories() != null))
/*      */       {
/*  824 */         for (int i = 0; i < list.getCategories().size(); i++)
/*      */         {
/*  826 */           Category cat = (Category)list.getCategories().elementAt(i);
/*  827 */           if ((!vecApplicableCategories.contains(new Long(cat.getId()))) || (filtersByCategory.containsKey(cat))) {
/*      */             continue;
/*      */           }
/*  830 */           Vector vecFilters = getFilters(a_dbTransaction, 1, -1L, cat.getId());
/*  831 */           filtersByCategory.put(cat, vecFilters);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  838 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception : " + e);
/*  839 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception : " + e, e);
/*      */     }
/*      */ 
/*  842 */     return filtersByCategory;
/*      */   }
/*      */ 
/*      */   public LinkedHashMap<Category, List<Filter>> getFiltersByCategoryOfTypeCategory(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  849 */     return getFiltersByCategory(a_dbTransaction, 1L);
/*      */   }
/*      */ 
/*      */   public LinkedHashMap<Category, List<Filter>> getFiltersByCategoryOfTypeAccessLevel(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  855 */     return getFiltersByCategory(a_dbTransaction, 2L);
/*      */   }
/*      */ 
/*      */   private Vector getFilters(DBTransaction a_dbTransaction, int a_iType, long a_lUserId, long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/*  877 */     String ksMethodName = "getFilters";
/*      */ 
/*  880 */     if (a_iType == 1)
/*      */     {
/*  882 */       if (a_lCategoryId > 0L)
/*      */       {
/*  884 */         if (this.m_hmCategoryFilterCache.containsKey(new Long(a_lCategoryId)))
/*      */         {
/*  886 */           return (Vector)this.m_hmCategoryFilterCache.get(new Long(a_lCategoryId));
/*      */         }
/*  888 */         if (this.m_hmAccessLevelFilterCache.containsKey(new Long(a_lCategoryId)))
/*      */         {
/*  890 */           return (Vector)this.m_hmAccessLevelFilterCache.get(new Long(a_lCategoryId));
/*      */         }
/*      */       }
/*  893 */       else if (this.m_vecAllFilters != null)
/*      */       {
/*  895 */         return this.m_vecAllFilters;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  900 */     Vector vecFilters = null;
/*  901 */     Connection con = null;
/*  902 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  906 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  908 */       String sSQL = "SELECT f.Id fId, f.Name fName, f.IsDefault fIsDefault, f.CategoryIds, f.AccessLevelIds, f.SequenceNumber, f.FilterTypeId FROM Filter f ";
/*      */ 
/*  917 */       String sWhere = " WHERE f.FilterTypeId = ?";
/*  918 */       String sOrderBy = "f.SequenceNumber";
/*      */ 
/*  920 */       if (a_lCategoryId > 0L)
/*      */       {
/*  922 */         sSQL = sSQL + " LEFT JOIN FilterForCategory ffc ON ffc.FilterId=f.Id";
/*  923 */         sWhere = sWhere + " AND ffc.CategoryId=?";
/*  924 */         sOrderBy = "ffc.SequenceNumber";
/*      */       }
/*      */ 
/*  927 */       if (a_iType == 2)
/*      */       {
/*  929 */         sWhere = sWhere + " AND f.UserId = ?";
/*      */       }
/*      */ 
/*  932 */       sSQL = sSQL + sWhere + " ORDER BY " + sOrderBy;
/*  933 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  934 */       int iCol = 1;
/*      */ 
/*  936 */       psql.setLong(iCol++, a_iType);
/*      */ 
/*  938 */       if (a_lCategoryId > 0L)
/*      */       {
/*  940 */         psql.setLong(iCol++, a_lCategoryId);
/*      */       }
/*      */ 
/*  943 */       if (a_iType == 2)
/*      */       {
/*  945 */         psql.setLong(iCol++, a_lUserId);
/*      */       }
/*      */ 
/*  948 */       rs = psql.executeQuery();
/*  949 */       vecFilters = new Vector();
/*  950 */       Filter filter = null;
/*  951 */       long lLastFilterId = 0L;
/*      */ 
/*  953 */       while (rs.next())
/*      */       {
/*  956 */         if (rs.getLong("fId") == lLastFilterId)
/*      */           continue;
/*  958 */         filter = buildFilter(rs, a_dbTransaction);
/*  959 */         vecFilters.add(filter);
/*  960 */         lLastFilterId = filter.getId();
/*      */       }
/*      */ 
/*  964 */       psql.close();
/*      */ 
/*  967 */       if (a_iType == 1)
/*      */       {
/*  969 */         cacheFilters(a_dbTransaction, vecFilters, a_lCategoryId);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  974 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst getting filters from the database : " + e);
/*  975 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst getting filters from the database : " + e, e);
/*      */     }
/*      */ 
/*  978 */     return vecFilters;
/*      */   }
/*      */ 
/*      */   private void setFilterTranslations(DBTransaction a_dbTransaction, Filter a_filter)
/*      */     throws Bn2Exception
/*      */   {
/*  984 */     String ksMethodName = "setFilterTranslations";
/*      */ 
/*  987 */     Connection con = null;
/*  988 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  992 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  994 */       String sSQL = "SELECT tf.Name tfName, l.Id lId, l.Code lCode, l.Name lName FROM TranslatedFilter tf LEFT JOIN Language l ON l.Id=tf.LanguageId WHERE tf.FilterId=?";
/*      */ 
/* 1002 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1003 */       psql.setLong(1, a_filter.getId());
/* 1004 */       rs = psql.executeQuery();
/*      */ 
/* 1006 */       while (rs.next())
/*      */       {
/* 1008 */         if ((rs.getLong("lId") <= 0L) || (rs.getLong("lId") == 1L))
/*      */           continue;
/*      */         //Filter tmp94_93 = a_filter; 
                    //tmp94_93.getClass();
                    Filter.Translation translation = a_filter.new Translation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/*      */ 
/* 1013 */         translation.setName(rs.getString("tfName"));
/* 1014 */         a_filter.getTranslations().add(translation);
/*      */       }
/*      */ 
/* 1017 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1021 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception : " + e);
/* 1022 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void cacheFilters(DBTransaction a_dbTransaction, Vector a_vecFilters, long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/* 1030 */     if (a_lCategoryId > 0L)
/*      */     {
/* 1033 */       synchronized (this.m_objCategoryFiltersLock)
/*      */       {
/* 1035 */         Category cat = this.m_categoryManager.getCategory(a_dbTransaction, 1L, a_lCategoryId);
/* 1036 */         if (cat == null)
/*      */         {
/* 1039 */           this.m_hmAccessLevelFilterCache.put(new Long(a_lCategoryId), a_vecFilters);
/*      */         }
/*      */         else
/*      */         {
/* 1043 */           this.m_hmCategoryFilterCache.put(new Long(a_lCategoryId), a_vecFilters);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1050 */       synchronized (this.m_objFiltersLock)
/*      */       {
/* 1052 */         this.m_vecAllFilters = a_vecFilters;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getAttributesForFilter(DBTransaction a_dbTransaction, long a_lFilterId)
/*      */     throws Bn2Exception
/*      */   {
/* 1074 */     String ksMethodName = "getAttributesForFilter";
/* 1075 */     Connection con = null;
/* 1076 */     PreparedStatement psql = null;
/* 1077 */     ResultSet rs = null;
/* 1078 */     String sSQL = null;
/* 1079 */     Vector vAttributes = null;
/*      */     try
/*      */     {
/* 1083 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1086 */       sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden , at.Id atId, faav.Value, faav.DateValue, faav.DateTimeValue FROM FilterAssetAttributeValue faav LEFT JOIN Attribute a ON faav.AttributeId=a.Id LEFT JOIN AttributeType at ON a.AttributeTypeId = at.Id WHERE faav.FilterId = ? ORDER BY a.SequenceNumber, a.Id";
/*      */ 
/* 1100 */       psql = con.prepareStatement(sSQL);
/* 1101 */       psql.setLong(1, a_lFilterId);
/* 1102 */       rs = psql.executeQuery();
/* 1103 */       vAttributes = getAttributeValuesForItemFromResultSet(rs, a_dbTransaction, null, null, a_lFilterId);
/* 1104 */       psql.close();
/*      */ 
/* 1107 */       sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden , at.Id atId, lav.Id avId, lav.Value Value FROM ListAttributeValue lav JOIN FilterListAttributeValue flav ON lav.Id = flav.ListAttributeValueId LEFT JOIN Attribute a ON lav.AttributeId=a.Id LEFT JOIN AttributeType at ON a.AttributeTypeId = at.Id WHERE flav.FilterId = ? ORDER BY a.SequenceNumber, a.Id, lav.SequenceNumber";
/*      */ 
/* 1122 */       psql = con.prepareStatement(sSQL);
/* 1123 */       psql.setLong(1, a_lFilterId);
/* 1124 */       rs = psql.executeQuery();
/* 1125 */       vAttributes.addAll(getAttributeValuesForItemFromResultSet(rs, a_dbTransaction, null, null, a_lFilterId));
/* 1126 */       psql.close();
/*      */ 
/* 1128 */       Collections.sort(vAttributes, AttributeValueSequenceComparator.getInstance());
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1132 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst getting visible attribute ids for filter : " + e);
/* 1133 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst getting visible attribute ids for filter : " + e, e);
/*      */     }
/*      */ 
/* 1136 */     return vAttributes;
/*      */   }
/*      */ 
/*      */   public Vector getAttributeValuesForItemFromResultSet(ResultSet a_rs, DBTransaction a_transaction, Vector a_vValidFlexibleAttributeIds, Vector a_vVisibleAttributeIds, long a_lItemId)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1146 */     long lLastAttributeId = 0L;
/* 1147 */     Vector vAttributes = new Vector();
/* 1148 */     Attribute attribute = null;
/*      */ 
/* 1150 */     while (a_rs.next())
/*      */     {
/* 1152 */       if (lLastAttributeId != a_rs.getLong("aId"))
/*      */       {
/* 1155 */         attribute = AttributeDBUtil.buildAttribute(a_rs);
/*      */ 
/* 1158 */         if ((a_vVisibleAttributeIds == null) || (a_vVisibleAttributeIds.contains(Long.valueOf(attribute.getId()))))
/*      */         {
/* 1160 */           attribute.setIsVisible(true);
/*      */         }
/*      */ 
/* 1163 */         lLastAttributeId = attribute.getId();
/*      */       }
/*      */ 
/* 1166 */       Vector vecOptionalStaticAttributes = new Vector(AssetEntityConstants.k_aOptionalStaticAttributes.length);
/* 1167 */       vecOptionalStaticAttributes.addAll(Arrays.asList(AssetEntityConstants.k_aOptionalStaticAttributes));
/*      */ 
/* 1169 */       if (((attribute.getStatic()) && (!vecOptionalStaticAttributes.contains(attribute.getFieldName()))) || (a_vValidFlexibleAttributeIds == null) || (a_vValidFlexibleAttributeIds.contains(Long.valueOf(attribute.getId()))))
/*      */       {
/* 1173 */         AttributeValue aValue = buildAttributeValue(a_transaction, a_rs, attribute, a_lItemId);
/* 1174 */         vAttributes.add(aValue);
/*      */       }
/*      */     }
/*      */ 
/* 1178 */     return vAttributes;
/*      */   }
/*      */ 
/*      */   private AttributeValue buildAttributeValue(DBTransaction a_transaction, ResultSet a_rs, Attribute a_attribute, long a_lItemId)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1194 */     AttributeValue aValue = new AttributeValue();
/* 1195 */     Date dateValue = null;
/*      */ 
/* 1197 */     aValue.setAttribute(a_attribute);
/* 1198 */     aValue.setValue("");
/*      */ 
/* 1200 */     String sValue = SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "Value");
/*      */ 
/* 1202 */     if (sValue != null)
/*      */     {
/* 1205 */       aValue.setValue(sValue);
/*      */     }
/*      */ 
/* 1209 */     if ((a_attribute.getTypeId() == 5L) || (a_attribute.getTypeId() == 6L))
/*      */     {
/* 1212 */       if (a_rs.getLong("avId") > 0L)
/*      */       {
/* 1214 */         aValue.setId(a_rs.getLong("avId"));
/*      */       }
/* 1216 */       a_attribute.getListOptionValues().add(aValue);
/*      */     }
/* 1218 */     else if (a_attribute.getTypeId() == 4L)
/*      */     {
/* 1220 */       if (a_rs.getLong("avId") > 0L)
/*      */       {
/* 1222 */         aValue.setId(a_rs.getLong("avId"));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1227 */     if ((a_attribute.getIsDatepicker()) && (!StringUtil.stringIsPopulated(aValue.getValue())))
/*      */     {
/* 1229 */       dateValue = a_rs.getDate("DateValue");
/*      */ 
/* 1231 */       if (dateValue != null)
/*      */       {
/* 1234 */         BrightDate bd = new BrightDate(dateValue);
/* 1235 */         String sDateValue = bd.getFormDate();
/* 1236 */         aValue.setValue(sDateValue);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1241 */     if ((a_attribute.getIsDateTime()) && (!StringUtil.stringIsPopulated(aValue.getValue())))
/*      */     {
/* 1243 */       dateValue = a_rs.getDate("DateTimeValue");
/*      */ 
/* 1245 */       if (dateValue != null)
/*      */       {
/* 1248 */         BrightDateTime bd = new BrightDateTime(dateValue);
/* 1249 */         String sDateValue = bd.getFormDateTime();
/* 1250 */         aValue.setValue(sDateValue);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1255 */     if (a_attribute.getId() == 2L)
/*      */     {
/* 1257 */       aValue.setValue(String.valueOf(a_lItemId));
/*      */     }
/*      */ 
/* 1260 */     return aValue;
/*      */   }
/*      */ 
/*      */   public void saveFilter(DBTransaction a_dbTransaction, Filter a_filter, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1283 */     String ksMethodName = "saveFilter";
/* 1284 */     Connection con = null;
/*      */ 
/* 1286 */     String sSQL = "";
/*      */     try
/*      */     {
/* 1289 */       con = a_dbTransaction.getConnection();
/* 1290 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 1291 */       boolean bSetId = true;
/* 1292 */       boolean bUpdate = false;
/*      */ 
/* 1295 */       if (a_filter.getId() > 0L)
/*      */       {
/* 1297 */         sSQL = "UPDATE Filter SET Name=?, IsDefault=?, CategoryIds=?, AccessLevelIds=?, FilterTypeId=?, SequenceNumber=? WHERE Id=?";
/* 1298 */         bUpdate = true;
/*      */       }
/*      */       else
/*      */       {
/* 1303 */         sSQL = "INSERT INTO Filter (Name, IsDefault, CategoryIds, AccessLevelIds, FilterTypeId, SequenceNumber";
/*      */ 
/* 1305 */         if (a_filter.getType() == 2)
/*      */         {
/* 1307 */           sSQL = sSQL + ", UserId";
/*      */         }
/*      */ 
/* 1310 */         String sValues = ") VALUES (?,?,?,?,?,?";
/*      */ 
/* 1312 */         if (a_filter.getType() == 2)
/*      */         {
/* 1314 */           sValues = sValues + ", ?";
/*      */         }
/*      */ 
/* 1317 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1319 */           a_filter.setId(sqlGenerator.getUniqueId(con, "AttributeSequence"));
/* 1320 */           sSQL = sSQL + ", Id";
/* 1321 */           sValues = sValues + ",?";
/*      */         }
/*      */         else
/*      */         {
/* 1325 */           bSetId = false;
/*      */         }
/*      */ 
/* 1328 */         sSQL = sSQL + sValues + ")";
/*      */       }
/*      */ 
/* 1331 */       int iCol = 1;
/*      */ 
/* 1333 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1334 */       psql.setString(iCol++, a_filter.getName());
/* 1335 */       psql.setBoolean(iCol++, a_filter.getIsDefault());
/* 1336 */       psql.setString(iCol++, a_filter.getCategoryIds());
/* 1337 */       psql.setString(iCol++, a_filter.getAccessLevelIds());
/* 1338 */       psql.setInt(iCol++, a_filter.getType());
/* 1339 */       if ((a_filter.getLinkedToCategories() != null) && (a_filter.getLinkedToCategories().size() > 0))
/*      */       {
/* 1341 */         psql.setNull(iCol++, 4);
/*      */       }
/*      */       else
/*      */       {
/* 1345 */         psql.setInt(iCol++, 0);
/*      */       }
/*      */ 
/* 1348 */       if ((a_filter.getType() == 2) && (!bUpdate))
/*      */       {
/* 1350 */         psql.setLong(iCol++, a_lUserId);
/*      */       }
/*      */ 
/* 1353 */       if (bSetId)
/*      */       {
/* 1355 */         psql.setLong(iCol++, a_filter.getId());
/*      */       }
/* 1357 */       psql.executeUpdate();
/* 1358 */       psql.close();
/*      */ 
/* 1360 */       if (!bSetId)
/*      */       {
/* 1363 */         a_filter.setId(sqlGenerator.getUniqueId(con, null));
/*      */       }
/*      */ 
/* 1367 */       sSQL = "DELETE FROM TranslatedFilter WHERE FilterId=?";
/* 1368 */       psql = con.prepareStatement(sSQL);
/* 1369 */       psql.setLong(1, a_filter.getId());
/* 1370 */       psql.executeUpdate();
/*      */ 
/* 1372 */       Iterator itTranslations = a_filter.getTranslations().iterator();
/* 1373 */       while (itTranslations.hasNext())
/*      */       {
/* 1375 */         Filter.Translation translation = (Filter.Translation)itTranslations.next();
/*      */ 
/* 1377 */         if (translation.getLanguage().getId() > 0L)
/*      */         {
/* 1379 */           sSQL = "INSERT INTO TranslatedFilter (FilterId,LanguageId,Name) VALUES (?,?,?)";
/* 1380 */           psql = con.prepareStatement(sSQL);
/* 1381 */           psql.setLong(1, a_filter.getId());
/* 1382 */           psql.setLong(2, translation.getLanguage().getId());
/* 1383 */           psql.setString(3, translation.getName());
/* 1384 */           psql.executeUpdate();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1389 */       Vector vAttributes = a_filter.getAttributeValues();
/* 1390 */       deleteFilterAttributeValues(con, a_filter.getId());
/*      */ 
/* 1392 */       if ((vAttributes != null) && (vAttributes.size() > 0))
/*      */       {
/* 1394 */         for (int i = 0; i < vAttributes.size(); i++)
/*      */         {
/* 1396 */           AttributeValue attVal = (AttributeValue)vAttributes.get(i);
/*      */ 
/* 1402 */           if ((attVal.getAttribute().getIsDropdownList()) || (attVal.getAttribute().getIsCheckList()) || (attVal.getAttribute().getIsOptionList()))
/*      */           {
/* 1406 */             if (attVal.getId() <= 0L)
/*      */               continue;
/* 1408 */             sSQL = "INSERT INTO FilterListAttributeValue (FilterId,ListAttributeValueId) VALUES (?,?)";
/* 1409 */             psql = con.prepareStatement(sSQL);
/* 1410 */             psql.setLong(1, a_filter.getId());
/* 1411 */             psql.setLong(2, attVal.getId());
/* 1412 */             psql.executeUpdate();
/* 1413 */             psql.close();
/*      */           }
/*      */           else
/*      */           {
/* 1422 */             sSQL = "INSERT INTO FilterAssetAttributeValue (FilterId,AttributeId,Value,DateValue,DateTimeValue) VALUES (";
/*      */ 
/* 1424 */             sSQL = sSQL + "?,?,?,?,?)";
/*      */ 
/* 1426 */             psql = con.prepareStatement(sSQL);
/* 1427 */             iCol = 1;
/*      */ 
/* 1429 */             psql.setLong(iCol++, a_filter.getId());
/* 1430 */             psql.setLong(iCol++, attVal.getAttribute().getId());
/* 1431 */             psql.setString(iCol++, attVal.getValue());
/* 1432 */             DBUtil.setFieldDateOrNull(psql, iCol++, attVal.getDateValue().getDate());
/* 1433 */             DBUtil.setFieldTimestampOrNull(psql, iCol++, attVal.getDateTimeValue().getDate());
/* 1434 */             psql.executeUpdate();
/* 1435 */             psql.close();
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1441 */       sSQL = "DELETE FROM FilterForCategory WHERE FilterId=?";
/* 1442 */       psql = con.prepareStatement(sSQL);
/* 1443 */       psql.setLong(1, a_filter.getId());
/* 1444 */       psql.executeUpdate();
/* 1445 */       psql.close();
/*      */ 
/* 1447 */       if ((a_filter.getLinkedToCategories() != null) && (a_filter.getLinkedToCategories().size() > 0))
/*      */       {
/* 1449 */         sSQL = "INSERT INTO FilterForCategory (FilterId, CategoryId, SequenceNumber) VALUES (?,?,?)";
/* 1450 */         psql = con.prepareStatement(sSQL);
/*      */ 
/* 1452 */         for (int i = 0; i < a_filter.getLinkedToCategories().size(); i++)
/*      */         {
/* 1454 */           long lCatId = ((Long)a_filter.getLinkedToCategories().elementAt(i)).longValue();
/* 1455 */           psql.setLong(1, a_filter.getId());
/* 1456 */           psql.setLong(2, lCatId);
/* 1457 */           psql.setInt(3, 0);
/* 1458 */           psql.executeUpdate();
/*      */         }
/*      */ 
/* 1461 */         psql.close();
/*      */       }
/*      */ 
/* 1465 */       if (!bUpdate)
/*      */       {
/* 1467 */         if ((a_filter.getLinkedToCategories() != null) && (a_filter.getLinkedToCategories().size() > 0))
/*      */         {
/* 1469 */           for (int i = 0; i < a_filter.getLinkedToCategories().size(); i++)
/*      */           {
/* 1471 */             fixFilterOrdering(a_dbTransaction, ((Long)a_filter.getLinkedToCategories().elementAt(i)).longValue(), false);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1476 */           fixFilterOrdering(a_dbTransaction, -1L, false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1481 */       invalidateCaches();
/* 1482 */       invalidateFiltersByGroupCache(a_filter.getType());
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1487 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst saving filter to the database.  SQL = " + sSQL, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveFilterGroupExclusions(DBTransaction a_dbTransaction, long a_lFilterId, Vector a_vecGroupExclusions)
/*      */     throws Bn2Exception
/*      */   {
/* 1508 */     String ksMethodName = "saveFilterGroupExclusions";
/* 1509 */     Connection con = null;
/*      */     try
/*      */     {
/* 1513 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1516 */       String sSQL = "DELETE FROM GroupFilterExclusion WHERE FilterId=?";
/* 1517 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1518 */       psql.setLong(1, a_lFilterId);
/* 1519 */       psql.executeUpdate();
/* 1520 */       psql.close();
/*      */ 
/* 1522 */       if ((a_vecGroupExclusions != null) && (a_vecGroupExclusions.size() > 0))
/*      */       {
/* 1524 */         sSQL = "INSERT INTO GroupFilterExclusion (FilterId, UserGroupId) VALUES (?,?)";
/* 1525 */         psql = con.prepareStatement(sSQL);
/*      */ 
/* 1527 */         for (int i = 0; i < a_vecGroupExclusions.size(); i++)
/*      */         {
/* 1529 */           psql.setLong(1, a_lFilterId);
/* 1530 */           psql.setLong(2, ((Long)a_vecGroupExclusions.elementAt(i)).longValue());
/* 1531 */           psql.executeUpdate();
/*      */         }
/*      */ 
/* 1534 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1539 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception : " + e);
/* 1540 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveFilterGroup(DBTransaction a_dbTransaction, FilterGroup a_filterGroup)
/*      */     throws Bn2Exception
/*      */   {
/* 1562 */     String ksMethodName = "saveFilterGroup";
/* 1563 */     Connection con = null;
/*      */     try
/*      */     {
/* 1567 */       con = a_dbTransaction.getConnection();
/* 1568 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 1569 */       String sSQL = "";
/* 1570 */       boolean bSetId = true;
/*      */ 
/* 1573 */       if (a_filterGroup.getId() > 0L)
/*      */       {
/* 1575 */         sSQL = "UPDATE FilterGroup SET Name=?, FilterTypeId=? WHERE Id=?";
/*      */       }
/*      */       else
/*      */       {
/* 1579 */         sSQL = "INSERT INTO FilterGroup (Name, FilterTypeId";
/* 1580 */         String sValues = ") VALUES (?,?";
/*      */ 
/* 1582 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1584 */           a_filterGroup.setId(sqlGenerator.getUniqueId(con, "FilterGroupSequence"));
/* 1585 */           sSQL = sSQL + ", Id";
/* 1586 */           sValues = sValues + ",?";
/*      */         }
/*      */         else
/*      */         {
/* 1590 */           bSetId = false;
/*      */         }
/*      */ 
/* 1593 */         sSQL = sSQL + sValues + ")";
/*      */       }
/*      */ 
/* 1596 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1597 */       psql.setString(1, a_filterGroup.getName());
/* 1598 */       psql.setLong(2, a_filterGroup.getFilterTypeId());
/*      */ 
/* 1600 */       if (bSetId)
/*      */       {
/* 1602 */         psql.setLong(3, a_filterGroup.getId());
/*      */       }
/* 1604 */       psql.executeUpdate();
/* 1605 */       psql.close();
/*      */ 
/* 1607 */       if (!bSetId)
/*      */       {
/* 1610 */         a_filterGroup.setId(sqlGenerator.getUniqueId(con, null));
/*      */       }
/*      */ 
/* 1613 */       invalidateCaches();
/* 1614 */       invalidateFiltersByGroupCache(a_filterGroup.getFilterTypeId());
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1618 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst saving filter group to database : " + e);
/* 1619 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst saving filter group to the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateFilterGroups(DBTransaction a_dbTransaction, long a_lFilterId, long[] a_aGroupIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1639 */     String ksMethodName = "updateFilterGroups";
/* 1640 */     Connection con = null;
/*      */     try
/*      */     {
/* 1644 */       con = a_dbTransaction.getConnection();
/* 1645 */       String sSQL = "DELETE FROM FilterInGroup WHERE FilterId=?";
/* 1646 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1647 */       psql.setLong(1, a_lFilterId);
/* 1648 */       psql.executeUpdate();
/* 1649 */       psql.close();
/*      */ 
/* 1651 */       sSQL = "INSERT INTO FilterInGroup (FilterId, GroupId) VALUES (?,?)";
/*      */ 
/* 1653 */       for (int i = 0; i < a_aGroupIds.length; i++)
/*      */       {
/* 1655 */         psql = con.prepareStatement(sSQL);
/* 1656 */         psql.setLong(1, a_lFilterId);
/* 1657 */         psql.setLong(2, a_aGroupIds[i]);
/* 1658 */         psql.executeUpdate();
/* 1659 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1664 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst saving filter groups to database : " + e);
/* 1665 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst saving filter groups to the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteFilterGroup(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 1685 */     String ksMethodName = "deleteFilterGroup";
/* 1686 */     Connection con = null;
/*      */     try
/*      */     {
/* 1690 */       con = a_dbTransaction.getConnection();
/* 1691 */       String sSQL = "SELECT FilterTypeId FROM FilterGroup WHERE Id=?";
/* 1692 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1693 */       psql.setLong(1, a_lId);
/* 1694 */       ResultSet rs = psql.executeQuery();
/* 1695 */       rs.next();
/* 1696 */       int iTypeId = rs.getInt("FilterTypeId");
/* 1697 */       psql.close();
/*      */ 
/* 1699 */       String[] aSQL = { "DELETE FROM FilterInGroup WHERE GroupId=?", "DELETE FROM FilterGroup WHERE Id=?" };
/*      */ 
/* 1702 */       for (int i = 0; i < aSQL.length; i++)
/*      */       {
/* 1704 */         psql = con.prepareStatement(aSQL[i]);
/* 1705 */         psql.setLong(1, a_lId);
/* 1706 */         psql.executeUpdate();
/* 1707 */         psql.close();
/*      */       }
/*      */ 
/* 1710 */       invalidateCaches();
/* 1711 */       invalidateFiltersByGroupCache(iTypeId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1715 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst deleting filter group to database : " + e);
/* 1716 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst deleting filter group to the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setDefaultFilter(DBTransaction a_dbTransaction, long a_lFilterId)
/*      */     throws Bn2Exception
/*      */   {
/* 1735 */     String ksMethodName = "setDefaultFilter";
/* 1736 */     Connection con = null;
/*      */     try
/*      */     {
/* 1740 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1743 */       String sSQL = "UPDATE Filter SET IsDefault=?";
/* 1744 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1745 */       psql.setBoolean(1, false);
/* 1746 */       psql.executeUpdate();
/* 1747 */       psql.close();
/*      */ 
/* 1751 */       if (a_lFilterId > 0L) {
/* 1752 */         sSQL = "UPDATE Filter SET IsDefault=? WHERE Id=?";
/* 1753 */         psql = con.prepareStatement(sSQL);
/* 1754 */         psql.setBoolean(1, true);
/* 1755 */         psql.setLong(2, a_lFilterId);
/* 1756 */         psql.executeUpdate();
/* 1757 */         psql.close();
/*      */       }
/*      */ 
/* 1761 */       invalidateCaches();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1765 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst setting default filter to database : " + e);
/* 1766 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst setting default filter to the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteFilter(DBTransaction a_dbTransaction, long a_lFilterId)
/*      */     throws Bn2Exception
/*      */   {
/* 1785 */     String ksMethodName = "deleteFilter";
/* 1786 */     Connection con = null;
/*      */     try
/*      */     {
/* 1790 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1792 */       String sSQL = "SELECT FilterTypeId FROM Filter WHERE Id=?";
/* 1793 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1794 */       psql.setLong(1, a_lFilterId);
/* 1795 */       ResultSet rs = psql.executeQuery();
/* 1796 */       rs.next();
/* 1797 */       int iTypeId = rs.getInt("FilterTypeId");
/* 1798 */       psql.close();
/*      */ 
/* 1801 */       deleteFilterAttributeValues(con, a_lFilterId);
/*      */ 
/* 1803 */       String[] aSQL = { "DELETE FROM TranslatedFilter WHERE FilterId=?", "DELETE FROM FilterInGroup WHERE FilterId=?", "DELETE FROM FilterForCategory WHERE FilterId=?", "DELETE FROM GroupFilterExclusion WHERE FilterId=?", "DELETE FROM Filter WHERE Id=?" };
/*      */ 
/* 1809 */       for (int i = 0; i < aSQL.length; i++)
/*      */       {
/* 1811 */         psql = con.prepareStatement(aSQL[i]);
/* 1812 */         psql.setLong(1, a_lFilterId);
/* 1813 */         psql.executeUpdate();
/* 1814 */         psql.close();
/*      */       }
/*      */ 
/* 1817 */       invalidateCaches();
/* 1818 */       invalidateFiltersByGroupCache(iTypeId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1822 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception whilst setting default filter to database : " + e);
/* 1823 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception whilst setting default filter to the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deleteFilterAttributeValues(Connection a_con, long a_lFilterId)
/*      */     throws SQLException
/*      */   {
/* 1843 */     String sSQL = "DELETE FROM FilterListAttributeValue WHERE FilterId=?";
/* 1844 */     PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 1845 */     psql.setLong(1, a_lFilterId);
/* 1846 */     psql.executeUpdate();
/* 1847 */     psql.close();
/*      */ 
/* 1849 */     sSQL = "DELETE FROM FilterAssetAttributeValue WHERE FilterId=?";
/* 1850 */     psql = a_con.prepareStatement(sSQL);
/* 1851 */     psql.setLong(1, a_lFilterId);
/* 1852 */     psql.executeUpdate();
/* 1853 */     psql.close();
/*      */   }
/*      */ 
/*      */   public void moveFilter(DBTransaction a_dbTransaction, long a_lId, long a_lCatId, boolean a_bUp)
/*      */     throws Bn2Exception
/*      */   {
/* 1863 */     Connection con = null;
/* 1864 */     PreparedStatement psql1 = null;
/* 1865 */     String sSQL = null;
/* 1866 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1870 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1875 */       sSQL = "SELECT SequenceNumber FROM Filter WHERE Id=?";
/* 1876 */       if (a_lCatId > 0L)
/*      */       {
/* 1878 */         sSQL = "SELECT SequenceNumber FROM FilterForCategory WHERE FilterId=? AND CategoryId=?";
/*      */       }
/* 1880 */       psql1 = con.prepareStatement(sSQL);
/* 1881 */       psql1.setLong(1, a_lId);
/* 1882 */       if (a_lCatId > 0L)
/*      */       {
/* 1884 */         psql1.setLong(2, a_lCatId);
/*      */       }
/* 1886 */       rs = psql1.executeQuery();
/*      */ 
/* 1888 */       if (rs.next())
/*      */       {
/* 1890 */         int iOldSequenceNumber = rs.getInt("SequenceNumber");
/*      */ 
/* 1895 */         String sSequencer = "f.SequenceNumber";
/* 1896 */         String sTable = "Filter";
/* 1897 */         String sIdentifier = "Id=?";
/* 1898 */         if (a_lCatId > 0L)
/*      */         {
/* 1900 */           sTable = "FilterForCategory";
/* 1901 */           sIdentifier = "FilterId=? AND CategoryId=?";
/* 1902 */           sSequencer = "ffc.SequenceNumber";
/*      */         }
/*      */ 
/* 1905 */         sSQL = "SELECT f.Id," + sSequencer + " FROM Filter f";
/*      */ 
/* 1907 */         if (a_lCatId > 0L)
/*      */         {
/* 1909 */           sSQL = sSQL + " LEFT JOIN FilterForCategory ffc ON ffc.FilterId=f.Id";
/* 1910 */           sSQL = sSQL + " WHERE ffc.CategoryId=" + a_lCatId;
/*      */         }
/*      */         else
/*      */         {
/* 1914 */           sSQL = sSQL + " WHERE 1=1";
/*      */         }
/*      */ 
/* 1917 */         if (!a_bUp)
/*      */         {
/* 1919 */           sSQL = sSQL + " AND " + sSequencer + ">? ORDER BY " + sSequencer + " ASC";
/*      */         }
/*      */         else
/*      */         {
/* 1923 */           sSQL = sSQL + " AND " + sSequencer + "<? ORDER BY " + sSequencer + " DESC";
/*      */         }
/*      */ 
/* 1926 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 1927 */         psql.setInt(1, iOldSequenceNumber);
/* 1928 */         rs = psql.executeQuery();
/*      */ 
/* 1931 */         if (rs.next())
/*      */         {
/* 1933 */           sSQL = "UPDATE " + sTable + " SET SequenceNumber=? WHERE " + sIdentifier;
/* 1934 */           PreparedStatement psql3 = con.prepareStatement(sSQL);
/* 1935 */           psql3.setInt(1, iOldSequenceNumber);
/* 1936 */           psql3.setLong(2, rs.getLong("Id"));
/* 1937 */           if (a_lCatId > 0L)
/*      */           {
/* 1939 */             psql3.setLong(3, a_lCatId);
/*      */           }
/* 1941 */           psql3.executeUpdate();
/* 1942 */           psql3.close();
/*      */ 
/* 1944 */           sSQL = "UPDATE " + sTable + " SET SequenceNumber=? WHERE " + sIdentifier;
/* 1945 */           psql3 = con.prepareStatement(sSQL);
/* 1946 */           psql3.setInt(1, rs.getInt("SequenceNumber"));
/* 1947 */           psql3.setLong(2, a_lId);
/* 1948 */           if (a_lCatId > 0L)
/*      */           {
/* 1950 */             psql3.setLong(3, a_lCatId);
/*      */           }
/* 1952 */           psql3.executeUpdate();
/* 1953 */           psql3.close();
/*      */         }
/*      */ 
/* 1956 */         psql.close();
/*      */       }
/*      */ 
/* 1959 */       psql1.close();
/*      */ 
/* 1962 */       invalidateCaches();
/* 1963 */       invalidateFiltersByGroupCache(1);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1967 */       throw new Bn2Exception("SQL Exception whilst moving a usage type value in the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fixFilterOrdering(DBTransaction a_dbTransaction, long a_lCatId, boolean a_bAlphabetise)
/*      */     throws Bn2Exception
/*      */   {
/* 1981 */     Connection con = null;
/* 1982 */     PreparedStatement psql = null;
/* 1983 */     String sSQL = null;
/* 1984 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1988 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1991 */       Vector vecOrderedValues = new Vector();
/*      */ 
/* 1993 */       String sSQLStart = "SELECT f.Id FROM Filter f";
/* 1994 */       String sSwitcher = "";
/*      */ 
/* 1996 */       if (a_lCatId > 0L)
/*      */       {
/* 1998 */         sSQLStart = sSQLStart + " LEFT JOIN FilterForCategory ffc ON ffc.FilterId=f.Id WHERE ffc.CategoryId=" + a_lCatId;
/* 1999 */         sSwitcher = "ffc.";
/*      */       }
/*      */       else
/*      */       {
/* 2003 */         sSQLStart = sSQLStart + " WHERE f.Id NOT IN (SELECT FilterId FROM FilterForCategory)";
/*      */       }
/*      */ 
/* 2006 */       sSQLStart = sSQLStart + " AND f.FilterTypeId=1 ";
/*      */ 
/* 2008 */       if (!a_bAlphabetise)
/*      */       {
/* 2010 */         sSQL = sSQLStart + "AND NOT " + sSwitcher + "SequenceNumber=0 ORDER BY " + sSwitcher + "SequenceNumber ASC";
/*      */       }
/*      */       else
/*      */       {
/* 2014 */         sSQL = sSQLStart + "ORDER BY " + SQLGenerator.getInstance().getOrderByForLargeTextField("f.Name", 100) + " ASC";
/*      */       }
/* 2016 */       psql = con.prepareStatement(sSQL);
/* 2017 */       rs = psql.executeQuery();
/*      */ 
/* 2019 */       while (rs.next())
/*      */       {
/* 2021 */         long lFilterId = rs.getLong("Id");
/* 2022 */         vecOrderedValues.add(new Long(lFilterId));
/*      */       }
/* 2024 */       psql.close();
/*      */ 
/* 2027 */       if (!a_bAlphabetise)
/*      */       {
/* 2029 */         sSQL = sSQLStart + "AND " + sSwitcher + "SequenceNumber=0 ORDER BY " + SQLGenerator.getInstance().getOrderByForLargeTextField("f.Name", 100) + " ASC";
/* 2030 */         psql = con.prepareStatement(sSQL);
/* 2031 */         rs = psql.executeQuery();
/* 2032 */         while (rs.next())
/*      */         {
/* 2034 */           long lFilterId = rs.getLong("Id");
/* 2035 */           vecOrderedValues.add(new Long(lFilterId));
/*      */         }
/* 2037 */         psql.close();
/*      */       }
/*      */ 
/* 2041 */       for (int i = 0; i < vecOrderedValues.size(); i++)
/*      */       {
/* 2043 */         long lFilterId = ((Long)vecOrderedValues.get(i)).longValue();
/*      */ 
/* 2045 */         sSQL = "UPDATE Filter SET SequenceNumber=? WHERE Id=?";
/* 2046 */         if (a_lCatId > 0L)
/*      */         {
/* 2048 */           sSQL = "UPDATE FilterForCategory SET SequenceNumber=? WHERE FilterId=? AND CategoryId=?";
/*      */         }
/*      */ 
/* 2051 */         psql = con.prepareStatement(sSQL);
/* 2052 */         psql.setLong(1, i + 1);
/* 2053 */         psql.setLong(2, lFilterId);
/* 2054 */         if (a_lCatId > 0L)
/*      */         {
/* 2056 */           psql.setLong(3, a_lCatId);
/*      */         }
/* 2058 */         psql.executeUpdate();
/* 2059 */         psql.close();
/*      */       }
/*      */ 
/* 2063 */       invalidateCaches();
/* 2064 */       invalidateFiltersByGroupCache(1);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2068 */       this.m_logger.error("SQL Exception whilst sorting attribute values : " + e);
/* 2069 */       throw new Bn2Exception("SQL Exception whilst sorting attribute values : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void invalidateCaches()
/*      */   {
/* 2075 */     synchronized (this.m_objCategoryFiltersLock)
/*      */     {
/* 2077 */       this.m_hmAccessLevelFilterCache.clear();
/* 2078 */       this.m_hmCategoryFilterCache.clear();
/*      */     }
/*      */ 
/* 2081 */     synchronized (this.m_objFiltersLock)
/*      */     {
/* 2083 */       this.m_vecAllFilters = null;
/*      */     }
/*      */ 
/* 2086 */     synchronized (this.m_objDefaultFilterLock)
/*      */     {
/* 2088 */       this.m_defaultFilter = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void invalidateFiltersByGroupCache(int a_iTypeId)
/*      */   {
/* 2094 */     synchronized (this.m_objFiltersByGroupLock)
/*      */     {
/* 2096 */       this.m_hmFiltersByGroupByType.remove(new Integer(a_iTypeId));
/*      */     }
/*      */   }
/*      */ 
/*      */   private Filter buildFilter(ResultSet a_rs, DBTransaction a_dbTransaction)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 2103 */     Filter filter = new Filter();
/* 2104 */     filter.setId(a_rs.getLong("fId"));
/* 2105 */     filter.setName(a_rs.getString("fName"));
/* 2106 */     filter.setIsDefault(a_rs.getBoolean("fIsDefault"));
/* 2107 */     filter.setAttributeValues(getAttributesForFilter(a_dbTransaction, filter.getId()));
/* 2108 */     filter.setCategoryIds(a_rs.getString("CategoryIds"));
/* 2109 */     filter.setAccessLevelIds(a_rs.getString("AccessLevelIds"));
/* 2110 */     filter.setSequence(a_rs.getInt("SequenceNumber"));
/* 2111 */     filter.setGroups(getFilterGroupsForFilter(a_dbTransaction, filter.getId()));
/* 2112 */     filter.setUserGroupExclusions(getUserGroupExclusionsForFilter(a_dbTransaction, filter.getId()));
/* 2113 */     filter.setLinkedToCategories(getLinkedCategoriesForFilter(a_dbTransaction, filter.getId()));
/* 2114 */     filter.setType(a_rs.getInt("FilterTypeId"));
/* 2115 */     setFilterTranslations(a_dbTransaction, filter);
/*      */ 
/* 2117 */     return filter;
/*      */   }
/*      */ 
/*      */   public Vector getUserGroupExclusionsForFilter(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 2136 */     String ksMethodName = "getUserGroupExclusionsForFilter";
/* 2137 */     Vector vecExcludeFromGroups = null;
/* 2138 */     Connection con = null;
/* 2139 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2143 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2145 */       String sSQL = "SELECT UserGroupId FROM GroupFilterExclusion WHERE FilterId=?";
/*      */ 
/* 2148 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 2149 */       psql.setLong(1, a_lId);
/* 2150 */       rs = psql.executeQuery();
/*      */ 
/* 2152 */       while (rs.next())
/*      */       {
/* 2154 */         if (vecExcludeFromGroups == null)
/*      */         {
/* 2156 */           vecExcludeFromGroups = new Vector();
/*      */         }
/* 2158 */         vecExcludeFromGroups.add(new Long(rs.getLong("UserGroupId")));
/*      */       }
/*      */ 
/* 2161 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2165 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception : " + e);
/* 2166 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception : " + e, e);
/*      */     }
/*      */ 
/* 2169 */     return vecExcludeFromGroups;
/*      */   }
/*      */ 
/*      */   public Vector getLinkedCategoriesForFilter(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 2187 */     String ksMethodName = "getLinkedCategoriesForFilter";
/* 2188 */     Vector vecApplicableCategories = null;
/* 2189 */     Connection con = null;
/* 2190 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2194 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2196 */       String sSQL = "SELECT CategoryId FROM FilterForCategory WHERE FilterId=?";
/*      */ 
/* 2199 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 2200 */       psql.setLong(1, a_lId);
/* 2201 */       rs = psql.executeQuery();
/*      */ 
/* 2203 */       while (rs.next())
/*      */       {
/* 2205 */         if (vecApplicableCategories == null)
/*      */         {
/* 2207 */           vecApplicableCategories = new Vector();
/*      */         }
/* 2209 */         vecApplicableCategories.add(new Long(rs.getLong("CategoryId")));
/*      */       }
/*      */ 
/* 2212 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2216 */       this.m_logger.error("FilterManager." + ksMethodName + ": SQL Exception : " + e);
/* 2217 */       throw new Bn2Exception("FilterManager." + ksMethodName + ": SQL Exception : " + e, e);
/*      */     }
/*      */ 
/* 2220 */     return vecApplicableCategories;
/*      */   }
/*      */ 
/*      */   private FilterGroup buildFilterGroup(ResultSet a_rs)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 2226 */     FilterGroup fg = new FilterGroup();
/* 2227 */     fg.setId(a_rs.getLong("fgId"));
/* 2228 */     fg.setFilterTypeId(a_rs.getInt("FilterTypeId"));
/* 2229 */     fg.setName(a_rs.getString("fgName"));
/*      */ 
/* 2231 */     return fg;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/* 2236 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(CategoryManager a_categoryManager)
/*      */   {
/* 2241 */     this.m_categoryManager = a_categoryManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.service.FilterManager
 * JD-Core Version:    0.6.0
 */