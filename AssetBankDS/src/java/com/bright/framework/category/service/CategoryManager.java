/*      */ package com.bright.framework.category.service;
/*      */ 
/*      */ //import J;
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.batch.service.BatchQueueManager;
/*      */ import com.bright.framework.category.bean.AlphabeticCategoryComparator;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.bean.Category.Translation;
/*      */ import com.bright.framework.category.bean.CategoryImpl;
/*      */ import com.bright.framework.category.bean.CategoryWithLanguage;
/*      */ import com.bright.framework.category.bean.FlatCategoryList;
/*      */ import com.bright.framework.category.bean.Item;
/*      */ import com.bright.framework.category.constant.CategoryConstants;
/*      */ import com.bright.framework.category.constant.CategorySettings;
/*      */ import com.bright.framework.category.util.CategoryUtil;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.bean.TransactionListener;
/*      */ import com.bright.framework.database.exception.SQLStatementException;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.language.service.LanguageManager;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.File;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public abstract class CategoryManager extends BatchQueueManager
/*      */   implements CategoryConstants
/*      */ {
/*      */   private static final String c_ksClassName = "CategoryManager";
/*  152 */   public static int k_iNORMAL = 0;
/*      */ 
/*  154 */   protected DBTransactionManager m_transactionManager = null;
/*  155 */   protected LanguageManager m_languageManager = null;
/*  156 */   private CategoryTypeCache m_catTypeCache = new CategoryTypeCache();
/*  157 */   protected FileStoreManager m_fileStoreManager = null;
/*      */ 
/*  164 */   private long m_lCacheTimestamp = 0L;
/*      */ 
/*      */   public abstract Item getItem(long paramLong)
/*      */     throws Bn2Exception;
/*      */ 
/*      */   public abstract String getItemTableName();
/*      */ 
/*      */   public abstract String getCategoryTypeName(long paramLong);
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  182 */     super.startup();
/*      */     try
/*      */     {
/*  188 */       for (Long lTypeId : getAllCategoryTypeIds(null))
/*      */       {
/*  190 */         CategoryCache cache = this.m_catTypeCache.getCache(lTypeId.longValue());
/*  191 */         if (cache.needsBuilding())
/*      */         {
/*  193 */           rebuildCategoryCache(null, lTypeId.longValue());
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/*  199 */       this.m_logger.error("UpgradeManager.startup() : Exception whist setting asset access stats in Asset table", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getCacheTimestamp()
/*      */   {
/*  217 */     return this.m_lCacheTimestamp;
/*      */   }
/*      */ 
/*      */   public FlatCategoryList getFlatCategoryList(DBTransaction a_dbTransaction, String a_sTreeId)
/*      */     throws Bn2Exception
/*      */   {
/*  238 */     return getFlatCategoryList(a_dbTransaction, Long.parseLong(a_sTreeId));
/*      */   }
/*      */ 
/*      */   public FlatCategoryList getFlatCategoryList(DBTransaction a_dbTransaction, long a_lTreeId)
/*      */     throws Bn2Exception
/*      */   {
/*  263 */     FlatCategoryList flatCatList = getCacheFlatCategoryList(a_dbTransaction, a_lTreeId);
/*      */ 
/*  265 */     return flatCatList;
/*      */   }
/*      */ 
/*      */   public FlatCategoryList getFlatCategoryList(DBTransaction a_dbTransaction, long a_lTreeId, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  284 */     FlatCategoryList cats = getFlatCategoryList(a_dbTransaction, a_lTreeId);
/*      */ 
/*  286 */     if (!a_language.isDefault())
/*      */     {
/*  288 */       FlatCategoryList catListClone = new FlatCategoryList();
/*  289 */       catListClone.setDepth(cats.getDepth());
/*  290 */       catListClone.setCategories(CategoryUtil.createTranslations(cats.getCategories(), a_language));
/*  291 */       cats = catListClone;
/*      */     }
/*      */ 
/*  294 */     return cats;
/*      */   }
/*      */ 
/*      */   public Vector getCategories(DBTransaction a_dbTransaction, long a_lTypeId, long a_lCategoryId, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  313 */     Vector vCats = getCategories(a_dbTransaction, a_lTypeId, a_lCategoryId);
/*      */ 
/*  315 */     if ((a_language != null) && (!a_language.isDefault()))
/*      */     {
/*  317 */       return CategoryUtil.createTranslations(vCats, a_language);
/*      */     }
/*  319 */     return vCats;
/*      */   }
/*      */ 
/*      */   public Vector<Category> getCategories(DBTransaction a_dbTransaction, long a_lTypeId, long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/*  344 */     Vector vecCategories = getCacheChildCategories(a_dbTransaction, a_lTypeId, a_lCategoryId);
/*      */ 
/*  346 */     return vecCategories;
/*      */   }
/*      */ 
/*      */   public Vector getCategories(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  352 */     return getCategories(a_dbTransaction, 1L, -1L);
/*      */   }
/*      */ 
/*      */   public Vector getAccessLevels(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  359 */     return getCategories(a_dbTransaction, 2L, -1L);
/*      */   }
/*      */ 
/*      */   public Vector<Category> getAncestors(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  382 */     return getAncestors(a_dbTransaction, a_lTreeId, a_lCatId, true);
/*      */   }
/*      */ 
/*      */   public Vector<Category> getAncestors(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCatId, boolean a_bErrorIfNotFound)
/*      */     throws Bn2Exception
/*      */   {
/*  394 */     Category cat = getCacheCategory(a_dbTransaction, a_lTreeId, a_lCatId);
/*      */ 
/*  396 */     if ((!a_bErrorIfNotFound) && (cat == null))
/*      */     {
/*  398 */       return null;
/*      */     }
/*      */ 
/*  401 */     return cat.getAncestors();
/*      */   }
/*      */ 
/*      */   public long[] getDescendantIds(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  423 */     Category cat = getCacheCategory(a_dbTransaction, a_lTreeId, a_lCatId);
/*      */ 
/*  426 */     long[] laDecendantIds = cat.getAllDescendantsIds();
/*      */ 
/*  429 */     return laDecendantIds;
/*      */   }
/*      */ 
/*      */   protected long[] getCategoryAndDescendantIds(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  452 */     long[] alDescendentIds = getDescendantIds(a_dbTransaction, a_lTreeId, a_lCatId);
/*  453 */     long[] alCategoryIds = null;
/*      */ 
/*  456 */     if (alDescendentIds != null)
/*      */     {
/*  458 */       alCategoryIds = new long[alDescendentIds.length + 1];
/*      */ 
/*  461 */       for (int i = 0; i < alDescendentIds.length; i++)
/*      */       {
/*  463 */         alCategoryIds[i] = alDescendentIds[i];
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  468 */       alCategoryIds = new long[1];
/*      */     }
/*      */ 
/*  472 */     alCategoryIds[(alCategoryIds.length - 1)] = a_lCatId;
/*      */ 
/*  474 */     return alCategoryIds;
/*      */   }
/*      */ 
/*      */   public Category getCategory(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  493 */     Category cat = getCacheCategory(a_dbTransaction, a_lTreeId, a_lCatId);
/*  494 */     return cat;
/*      */   }
/*      */ 
/*      */   public boolean categoryExists(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  503 */     return getCategory(a_dbTransaction, a_lTreeId, a_lCatId) != null;
/*      */   }
/*      */ 
/*      */   public Category getCategory(DBTransaction a_dbTransaction, long a_lTreeId, String a_sName, boolean a_bIsFullName)
/*      */     throws Bn2Exception
/*      */   {
/*  523 */     Vector vecItems = getCategoriesByName(a_dbTransaction, a_lTreeId, a_sName, a_bIsFullName);
/*      */ 
/*  525 */     if ((vecItems != null) && (vecItems.size() > 0))
/*      */     {
/*  527 */       return (Category)vecItems.firstElement();
/*      */     }
/*  529 */     return null;
/*      */   }
/*      */ 
/*      */   public Vector getCategoriesByName(DBTransaction a_dbTransaction, long a_lTreeId, String a_sName, boolean a_bIsFullName)
/*      */     throws Bn2Exception
/*      */   {
/*  548 */     if (a_sName == null)
/*      */     {
/*  550 */       return null;
/*      */     }
/*      */ 
/*  554 */     String[] aSubCats = a_sName.split("/");
/*      */ 
/*  556 */     String sNameTidied = null;
/*  557 */     for (int i = 0; i < aSubCats.length; i++)
/*      */     {
/*  559 */       if (sNameTidied == null)
/*      */       {
/*  561 */         sNameTidied = "";
/*      */       }
/*      */       else
/*      */       {
/*  565 */         sNameTidied = sNameTidied + "/";
/*      */       }
/*      */ 
/*  568 */       sNameTidied = sNameTidied + aSubCats[i].trim();
/*      */     }
/*      */ 
/*  571 */     Category catItem = null;
/*  572 */     Vector vecItems = null;
/*  573 */     Vector vecFullList = getFlatCategoryList(a_dbTransaction, a_lTreeId).getCategories();
/*      */ 
/*  576 */     for (int i = 0; i < vecFullList.size(); i++)
/*      */     {
/*  578 */       catItem = (Category)vecFullList.get(i);
/*      */ 
/*  581 */       if (((!a_bIsFullName) || (!CategoryUtil.namesMatch(catItem.getFullName(), sNameTidied))) && ((a_bIsFullName) || (!CategoryUtil.namesMatch(catItem.getName(), sNameTidied)))) {
/*      */         continue;
/*      */       }
/*  584 */       if (vecItems == null)
/*      */       {
/*  586 */         vecItems = new Vector();
/*      */       }
/*  588 */       vecItems.add(catItem);
/*      */     }
/*      */ 
/*  592 */     return vecItems;
/*      */   }
/*      */ 
/*      */   public Vector findCategoriesByName(DBTransaction a_transaction, long a_lTreeId, String a_sCatName)
/*      */     throws Bn2Exception
/*      */   {
/*  619 */     Vector vecCategories = getCategoriesByName(a_transaction, a_lTreeId, a_sCatName, true);
/*      */ 
/*  626 */     if ((vecCategories == null) || (vecCategories.size() == 0))
/*      */     {
/*  629 */       vecCategories = getCategoriesByName(a_transaction, a_lTreeId, a_sCatName, false);
/*      */     }
/*      */ 
/*  636 */     return vecCategories;
/*      */   }
/*      */ 
/*      */   public Category ensureCategoryExists(DBTransaction a_dbTransaction, long a_lTreeId, String a_sName, boolean a_bIsRetrictive)
/*      */     throws Bn2Exception
/*      */   {
/*  654 */     this.m_logger.debug("CategoryManager.ensureCategoryExists: Checking category name:" + a_sName);
/*      */ 
/*  656 */     Category cat = getCategory(a_dbTransaction, a_lTreeId, a_sName, true);
/*      */ 
/*  658 */     if (cat != null)
/*      */     {
/*  660 */       this.m_logger.debug("CategoryManager.ensureCategoryExists: Category does exist ID:" + cat.getId());
/*  661 */       return cat;
/*      */     }
/*      */ 
/*  664 */     long lParentId = -1L;
/*      */ 
/*  667 */     if (a_sName.indexOf("/") >= 0)
/*      */     {
/*  669 */       String sParent = a_sName.substring(0, a_sName.lastIndexOf("/"));
/*  670 */       Category parent = ensureCategoryExists(a_dbTransaction, a_lTreeId, sParent, a_bIsRetrictive);
/*  671 */       if (parent != null)
/*      */       {
/*  673 */         lParentId = parent.getId();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  678 */     cat = new CategoryImpl();
/*  679 */     cat.setName(a_sName.substring(a_sName.lastIndexOf("/") + 1).trim());
/*  680 */     cat.setIsBrowsable(true);
/*  681 */     cat.setIsRestrictive(a_bIsRetrictive);
/*      */ 
/*  684 */     this.m_languageManager.createEmptyTranslations(a_dbTransaction, cat);
/*      */ 
/*  687 */     addCategory(a_dbTransaction, a_lTreeId, cat, lParentId);
/*      */ 
/*  689 */     this.m_logger.debug("CategoryManager.ensureCategoryExists: Category did not exist, new category added:" + cat.getId());
/*      */ 
/*  691 */     return cat;
/*      */   }
/*      */ 
/*      */   public Vector<Long> getCategoryIdsForAllItems(DBTransaction a_dbTransaction, long a_lTreeId)
/*      */     throws Bn2Exception
/*      */   {
/*  702 */     return getCategoryIdsForItem(a_dbTransaction, a_lTreeId, -1L);
/*      */   }
/*      */ 
/*      */   public Vector<Long> getCategoryIdsForItem(DBTransaction a_dbTransaction, long a_lTreeId, long a_lItemId)
/*      */     throws Bn2Exception
/*      */   {
/*  718 */     String ksMethodName = "getCategoryIdsForItem";
/*      */ 
/*  720 */     Vector vecIds = new Vector();
/*  721 */     String sSql = null;
/*      */ 
/*  723 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  725 */     if (transaction == null)
/*      */     {
/*  727 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*  732 */     sSql = "SELECT DISTINCT IIC.CategoryId FROM CM_ItemInCategory IIC INNER JOIN CM_Category C ON C.Id=IIC.CategoryId WHERE C.CategoryTypeId = " + a_lTreeId;
/*      */ 
/*  740 */     if (a_lItemId > 0L)
/*      */     {
/*  742 */       sSql = sSql + " AND ItemId = " + a_lItemId;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  747 */       Connection con = transaction.getConnection();
/*  748 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/*  750 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  752 */       while (rs.next())
/*      */       {
/*  754 */         vecIds.add(Long.valueOf(rs.getLong("CategoryId")));
/*      */       }
/*      */ 
/*  757 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  761 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/*  765 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  773 */       this.m_logger.error("CategoryManager.getCategoryIdsForItem : SQL Exception : " + e);
/*  774 */       throw new Bn2Exception("CategoryManager.getCategoryIdsForItem : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  779 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  783 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  787 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  792 */     return vecIds;
/*      */   }
/*      */ 
/*      */   public Vector<Category> getCategoriesForItem(DBTransaction a_dbTransaction, long a_lTreeId, long a_lItemId)
/*      */     throws Bn2Exception
/*      */   {
/*  801 */     return getCategoriesForItem(a_dbTransaction, a_lTreeId, a_lItemId, null);
/*      */   }
/*      */ 
/*      */   public Vector<Category> getCategoriesForItem(DBTransaction a_dbTransaction, long a_lTreeId, long a_lItemId, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  824 */     DBTransaction dbTransaction = null;
/*  825 */     Vector vecCategories = new Vector();
/*      */     try
/*      */     {
/*  829 */       if (a_dbTransaction != null)
/*      */       {
/*  831 */         dbTransaction = a_dbTransaction;
/*      */       }
/*      */       else
/*      */       {
/*  835 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  840 */       String sSql = "SELECT C.Id FROM CM_Category C JOIN CM_ItemInCategory IIC ON C.Id = IIC.CategoryId WHERE C.CategoryTypeId = " + a_lTreeId + " " + "AND IIC.ItemId = " + a_lItemId;
/*      */ 
/*  849 */       Connection con = dbTransaction.getConnection();
/*  850 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/*  852 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  855 */       while (rs.next())
/*      */       {
/*  857 */         Category category = getCacheCategory(dbTransaction, a_lTreeId, rs.getLong("Id"));
/*      */ 
/*  862 */         if ((a_language != null) && (!a_language.isDefault()))
/*      */         {
/*  864 */           category = new CategoryWithLanguage(category, a_language);
/*      */         }
/*      */ 
/*  868 */         vecCategories.add(category);
/*      */       }
/*      */ 
/*  871 */       psql.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       throw new Bn2Exception("Exception in getCategoriesForItem : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  886 */       if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  890 */           dbTransaction.commit();
/*      */         } catch (Exception ex) {
/*      */         }
/*      */       }
/*      */     }
/*  895 */     return vecCategories;
/*      */   }
/*      */ 
/*      */   public Map<Long, Boolean> getApprovalStatusesForItem(DBTransaction a_dbTransaction, long a_lItemId)
/*      */     throws Bn2Exception
/*      */   {
/*  901 */     DBTransaction dbTransaction = a_dbTransaction;
/*  902 */     if (dbTransaction == null)
/*      */     {
/*  904 */       dbTransaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*  907 */     String sSQL = "SELECT C.Id, IIC.IsApproved FROM CM_Category C JOIN CM_ItemInCategory IIC ON C.Id = IIC.CategoryId WHERE C.CategoryTypeId = 2 AND IIC.ItemId = " + a_lItemId;
               long catId;
/*      */     try
/*      */     {
/*  915 */       Map approvalStatusByCatId = new HashMap();
/*      */ 
/*  917 */       Connection con = dbTransaction.getConnection();
/*  918 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  920 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  922 */       while (rs.next())
/*      */       {
/*  924 */         catId = rs.getLong("Id");
/*  925 */         boolean approved = rs.getBoolean("IsApproved");
/*      */ 
/*  927 */         approvalStatusByCatId.put(Long.valueOf(catId), Boolean.valueOf(approved));
/*      */       }
/*      */ 
/*  930 */       psql.close();
/*      */ 
/*  932 */       //catId = approvalStatusByCatId;
/*      */      // return catId;
                 return approvalStatusByCatId;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  936 */       throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */     finally
/*      */     {
/*  940 */       if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  944 */           dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  948 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
                     throw new Bn2Exception(sqle.getMessage());
/*      */         }
/*      */       }
/*  949 */     }//throw localObject;
/*      */   }
/*      */ 
/*      */   protected void deleteCategoryFromDatabase(DBTransaction a_dbTransaction, final long a_lTreeId, final long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/*  982 */     Connection con = null;
/*      */ 
/*  984 */     String sCategoryIds = "";
/*  985 */     PreparedStatement psql = null;
/*  986 */     String sSql = null;
/*      */ 
/*  988 */     long[] alCategoryIdsToDelete = getCategoryAndDescendantIds(a_dbTransaction, a_lTreeId, a_lCategoryId);
/*      */ 
/*  992 */     sCategoryIds = StringUtil.convertNumbersToString(alCategoryIdsToDelete, ", ");
/*      */     try
/*      */     {
/*  996 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  999 */       sSql = "DELETE FROM PublishAction WHERE CategoryId IN (" + sCategoryIds + ")";
/* 1000 */       psql = con.prepareStatement(sSql);
/* 1001 */       psql.executeUpdate();
/* 1002 */       psql.close();
/*      */ 
/* 1005 */       sSql = "DELETE FROM CM_ItemInCategory WHERE CategoryId IN (" + sCategoryIds + ")";
/* 1006 */       psql = con.prepareStatement(sSql);
/* 1007 */       psql.executeUpdate();
/* 1008 */       psql.close();
/*      */ 
/* 1010 */       sSql = "DELETE FROM TranslatedCategory WHERE CategoryId IN (" + sCategoryIds + ")";
/* 1011 */       psql = con.prepareStatement(sSql);
/* 1012 */       psql.executeUpdate();
/* 1013 */       psql.close();
/*      */ 
/* 1016 */       sSql = "UPDATE CM_Category SET ParentId = NULL WHERE Id IN (" + sCategoryIds + ")";
/* 1017 */       psql = con.prepareStatement(sSql);
/* 1018 */       psql.executeUpdate();
/* 1019 */       psql.close();
/*      */ 
/* 1021 */       sSql = "DELETE FROM CM_Category WHERE Id IN (" + sCategoryIds + ")";
/* 1022 */       psql = con.prepareStatement(sSql);
/* 1023 */       psql.executeUpdate();
/* 1024 */       psql.close();
/*      */ 
/* 1027 */       a_dbTransaction.addListener(new TransactionListener()
/*      */       {
/*      */         public void doAfterCommit()
/*      */         {
/*      */           try {
/* 1032 */             CategoryManager.this.removeCategoryImage(a_lTreeId, a_lCategoryId);
/*      */           }
/*      */           catch (Bn2Exception e)
/*      */           {
/*      */           }
/*      */ 
/* 1038 */           CategoryManager.this.cacheDeleteCategory(a_lTreeId, a_lCategoryId);
/*      */         }
/*      */       });
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1045 */       throw new Bn2Exception("CatMngr.deleteCategory: SQL Exception whilst deleting categories: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateCategory(DBTransaction a_dbTransaction, final long a_lTreeId, Category a_objCategory, boolean a_bInvalidateCaches)
/*      */     throws Bn2Exception
/*      */   {
/* 1058 */     String ksMethodName = "updateCategory";
/* 1059 */     Connection con = null;
/*      */     try
/*      */     {
/* 1063 */       con = a_dbTransaction.getConnection();
/* 1064 */       String sSql = "UPDATE CM_Category SET Name = ?, Summary = ?, Description=?, IsBrowsable = ?, IsRestrictive = ?, Synchronised = ?, SelectedOnLoad=?, ExtensionAssetId=?, AllowAdvancedOptions=?, IsExpired=?, WorkflowName=?, CanAssignIfNotLeaf=?  WHERE Id =?";
/*      */ 
/* 1079 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 1081 */       int iCol = 1;
/* 1082 */       psql.setString(iCol++, a_objCategory.getName());
/* 1083 */       psql.setString(iCol++, a_objCategory.getSummary());
/* 1084 */       psql.setString(iCol++, a_objCategory.getDescription());
/* 1085 */       psql.setBoolean(iCol++, a_objCategory.getIsBrowsable());
/* 1086 */       psql.setBoolean(iCol++, a_objCategory.getIsRestrictive());
/* 1087 */       psql.setBoolean(iCol++, a_objCategory.isSynchronised());
/* 1088 */       psql.setBoolean(iCol++, a_objCategory.getSelectedOnLoad());
/* 1089 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_objCategory.getExtensionAssetId());
/* 1090 */       psql.setBoolean(iCol++, a_objCategory.getAllowAdvancedOptions());
/* 1091 */       psql.setBoolean(iCol++, a_objCategory.isExpired());
/* 1092 */       psql.setString(iCol++, a_objCategory.getWorkflowName());
/* 1093 */       psql.setBoolean(iCol++, a_objCategory.getCanAssignIfNotLeaf());
/* 1094 */       psql.setLong(iCol++, a_objCategory.getId());
/*      */ 
/* 1096 */       psql.executeUpdate();
/* 1097 */       psql.close();
/*      */ 
/* 1099 */       if (a_objCategory.getId() > 0L)
/*      */       {
/* 1101 */         sSql = "DELETE FROM TranslatedCategory WHERE CategoryId=?";
/* 1102 */         psql = con.prepareStatement(sSql);
/* 1103 */         psql.setLong(1, a_objCategory.getId());
/* 1104 */         psql.executeUpdate();
/* 1105 */         psql.close();
/*      */ 
/* 1107 */         insertCategoryTranslations(a_objCategory, con);
/*      */       }
/*      */ 
/* 1110 */       Category cat = getCacheCategory(a_dbTransaction, a_lTreeId, a_objCategory.getId());
/* 1111 */       final Category catClone = cat.clone();
/*      */ 
/* 1113 */       boolean bRestrictivenessChanged = a_objCategory.getIsRestrictive() != cat.getIsRestrictive();
/*      */ 
/* 1116 */       if ((a_objCategory.getIsRestrictive()) && (bRestrictivenessChanged))
/*      */       {
/* 1118 */         makeAncestorsRestrictive(a_dbTransaction, cat);
/*      */       }
/* 1121 */       else if ((!a_objCategory.getIsRestrictive()) && (bRestrictivenessChanged))
/*      */       {
/* 1124 */         if (cat.getParent() != null)
/*      */         {
/* 1126 */           a_objCategory.setWorkflowName(cat.getParent().getWorkflowName());
/*      */         }
/*      */ 
/* 1129 */         adjustDescendants(a_dbTransaction, cat, cat.getParent().getWorkflowName());
/*      */ 
/* 1132 */         sSql = "DELETE FROM CategoryVisibleToGroup WHERE CategoryId IN (SELECT Id FROM CM_Category WHERE IsRestrictive=?)";
/* 1133 */         psql = con.prepareStatement(sSql);
/* 1134 */         psql.setBoolean(1, false);
/* 1135 */         psql.executeUpdate();
/* 1136 */         psql.close();
/*      */       }
/*      */ 
/* 1139 */       if (a_bInvalidateCaches)
/*      */       {
/* 1141 */         if ((cat != null) && (a_dbTransaction != null))
/*      */         {
/* 1143 */           a_dbTransaction.addListener(new TransactionListener()
/*      */           {
/*      */             public void doAfterRollback() {
/* 1146 */               CategoryManager.this.cacheUpdateCategory(a_lTreeId, catClone);
/* 1147 */               CategoryManager.this.m_logger.debug("Category " + catClone.getId() + " reinstated in cache from DB on rollback after update/delete in CategoryManager");
/*      */             } } );
/*      */         }
/* 1151 */         cacheUpdateCategory(a_lTreeId, a_objCategory);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1157 */       throw new Bn2Exception("CategoryManager.updateCategory: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertCategoryTranslations(Category a_objCategory, Connection con)
/*      */     throws SQLException
/*      */   {
/* 1172 */     Iterator itTranslations = a_objCategory.getTranslations().iterator();
/* 1173 */     while (itTranslations.hasNext())
/*      */     {
/* 1175 */       Category.Translation translation = (Category.Translation)itTranslations.next();
/*      */ 
/* 1177 */       if (translation.getLanguage().getId() > 0L)
/*      */       {
/* 1179 */         String sSql = "INSERT INTO TranslatedCategory (CategoryId,LanguageId,Name,Description,Summary) VALUES (?,?,?,?,?)";
/* 1180 */         PreparedStatement psql = con.prepareStatement(sSql);
/* 1181 */         psql.setLong(1, a_objCategory.getId());
/* 1182 */         psql.setLong(2, translation.getLanguage().getId());
/* 1183 */         psql.setString(3, translation.getName());
/* 1184 */         psql.setString(4, translation.getDescription());
/* 1185 */         psql.setString(5, translation.getSummary());
/* 1186 */         psql.executeUpdate();
/* 1187 */         psql.close();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void alphabetiseSubCategories(DBTransaction a_dbTransaction, long a_lTypeId, long a_lParentCategoryId, boolean a_bRoot)
/*      */     throws Bn2Exception
/*      */   {
/* 1211 */     String ksMethodName = "alphabetiseSubCategories";
/*      */     try
/*      */     {
/* 1215 */       Connection con = a_dbTransaction.getConnection();
/* 1216 */       ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*      */ 
/* 1222 */       String sSql = "SELECT * FROM CM_Category WHERE ";
/* 1223 */       String sEnd = " ORDER BY Name";
/*      */ 
/* 1225 */       if (a_bRoot)
/*      */       {
/* 1227 */         sSql = sSql + sqlGenerator.getNullCheckStatement("ParentId");
/*      */       }
/*      */       else
/*      */       {
/* 1231 */         sSql = sSql + "ParentId=" + a_lParentCategoryId;
/*      */       }
/* 1233 */       sSql = sSql + sEnd;
/*      */ 
/* 1235 */       PreparedStatement selectPsql = con.prepareStatement(sSql);
/* 1236 */       ResultSet rs = selectPsql.executeQuery();
/*      */ 
/* 1239 */       sSql = "UPDATE CM_Category SET SequenceNumber=? WHERE Id=?";
/* 1240 */       int iSequence = 1;
/*      */ 
/* 1242 */       while (rs.next())
/*      */       {
/* 1244 */         PreparedStatement updatePsql = con.prepareStatement(sSql);
/* 1245 */         updatePsql.setInt(1, iSequence);
/* 1246 */         updatePsql.setLong(2, rs.getLong("Id"));
/* 1247 */         updatePsql.executeUpdate();
/* 1248 */         updatePsql.close();
/* 1249 */         iSequence++;
/*      */       }
/* 1251 */       selectPsql.close();
/*      */ 
/* 1254 */       rebuildCategoryCache(a_dbTransaction, a_lTypeId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1259 */       throw new Bn2Exception("CategoryManager.alphabetiseSubCategories: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean changeCategoryParent(DBTransaction a_dbTransaction, long a_lTreeId, Category a_category, long a_lNewParentId)
/*      */     throws Bn2Exception
/*      */   {
/* 1286 */     String ksMethodName = "changeCategoryParent";
/* 1287 */     boolean bSuccess = false;
/*      */ 
/* 1290 */     if (!existsCategoryWithNameAlready(a_dbTransaction, a_lTreeId, a_category.getId(), a_lNewParentId, a_category.getName()))
/*      */     {
/* 1296 */       boolean bIsRestrictive = false;
/*      */ 
/* 1299 */       if (a_lTreeId == 2L)
/*      */       {
/* 1302 */         if (a_lNewParentId <= 0L)
/*      */         {
/* 1305 */           bIsRestrictive = true;
/*      */         }
/*      */         else
/*      */         {
/* 1310 */           Category thisCat = getCategory(a_dbTransaction, a_lTreeId, a_category.getId());
/* 1311 */           Category parentCat = getCategory(a_dbTransaction, a_lTreeId, a_lNewParentId);
/*      */ 
/* 1314 */           if (parentCat.getIsRestrictive())
/*      */           {
/* 1317 */             bIsRestrictive = thisCat.getIsRestrictive();
/*      */           }
/*      */           else
/*      */           {
/* 1322 */             bIsRestrictive = false;
/* 1323 */             adjustDescendants(a_dbTransaction, thisCat, parentCat.getWorkflowName());
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1330 */         Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1336 */         String sSql = "SELECT MAX(SequenceNumber)+1 NextSequence FROM CM_Category WHERE ParentId=?";
/* 1337 */         PreparedStatement psql = con.prepareStatement(sSql);
/* 1338 */         DBUtil.setFieldIdOrNull(psql, 1, a_lNewParentId);
/* 1339 */         ResultSet rs = psql.executeQuery();
/* 1340 */         int iSequence = 0;
/*      */ 
/* 1342 */         if (rs.next())
/*      */         {
/* 1344 */           iSequence = rs.getInt("NextSequence");
/*      */         }
/* 1346 */         psql.close();
/*      */ 
/* 1349 */         sSql = "UPDATE CM_Category SET ParentId = ?, SequenceNumber=?, IsRestrictive=? WHERE Id =?";
/* 1350 */         psql = con.prepareStatement(sSql);
/* 1351 */         DBUtil.setFieldIdOrNull(psql, 1, a_lNewParentId);
/* 1352 */         psql.setInt(2, iSequence);
/* 1353 */         psql.setBoolean(3, bIsRestrictive);
/* 1354 */         psql.setLong(4, a_category.getId());
/* 1355 */         psql.executeUpdate();
/* 1356 */         psql.close();
/*      */ 
/* 1359 */         rebuildCategoryCache(a_dbTransaction, a_lTreeId);
/*      */ 
/* 1361 */         bSuccess = true;
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 1367 */         throw new Bn2Exception("CategoryManager.changeCategoryParent: Exception occurred: " + e, e);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1372 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public boolean updateCategoryDetails(DBTransaction a_dbTransaction, long a_lTreeId, Category a_category)
/*      */     throws Bn2Exception
/*      */   {
/* 1395 */     DBTransaction transaction = a_dbTransaction;
/* 1396 */     boolean bSuccess = false;
/*      */     try
/*      */     {
/* 1400 */       if (transaction == null)
/*      */       {
/* 1402 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/*      */ 
/* 1406 */       long lCatId = a_category.getId();
/* 1407 */       String sProposedName = a_category.getName();
/*      */ 
/* 1410 */       Category cat = getCategory(transaction, a_lTreeId, lCatId);
/*      */ 
/* 1412 */       cat = cat.clone();
/*      */ 
/* 1415 */       if (!existsCategoryWithNameAlready(transaction, a_lTreeId, lCatId, cat.getParentId(), sProposedName))
/*      */       {
/* 1417 */         bSuccess = true;
/*      */ 
/* 1420 */         cat.setName(sProposedName);
/* 1421 */         cat.setDescription(a_category.getDescription());
/* 1422 */         cat.setIsBrowsable(a_category.getIsBrowsable());
/* 1423 */         cat.setSynchronised(a_category.isSynchronised());
/* 1424 */         cat.setSelectedOnLoad(a_category.getSelectedOnLoad());
/* 1425 */         cat.setExtensionAssetId(a_category.getExtensionAssetId());
/* 1426 */         cat.setTranslations(a_category.getTranslations());
/* 1427 */         cat.setAllowAdvancedOptions(a_category.getAllowAdvancedOptions());
/* 1428 */         cat.setExpired(a_category.isExpired());
/* 1429 */         cat.setWorkflowName(a_category.getWorkflowName());
/* 1430 */         cat.setCanAssignIfNotLeaf(a_category.getCanAssignIfNotLeaf());
/* 1431 */         cat.setIsRestrictive(a_category.getIsRestrictive());
/* 1432 */         updateCategory(transaction, a_lTreeId, cat, true);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       throw new Bn2Exception(getClass().getSimpleName() + ": SQL Exception: ", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1445 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */         try {
/* 1447 */           transaction.commit();
/*      */         } catch (SQLException ex) {
/*      */         }
/*      */     }
/* 1451 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   private void makeAncestorsRestrictive(DBTransaction a_dbTransaction, Category a_category)
/*      */     throws Bn2Exception
/*      */   {
/* 1464 */     if ((a_category.getParent() == null) || (a_category.getParent().getId() == -1L))
/*      */     {
/* 1466 */       return;
/*      */     }
/*      */ 
/* 1469 */     Category parent = a_category.getParent();
/*      */ 
/* 1471 */     if (!parent.getIsRestrictive())
/*      */     {
/*      */       try
/*      */       {
/* 1476 */         Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1478 */         String sSql = "UPDATE CM_Category SET IsRestrictive=? WHERE Id=?";
/*      */ 
/* 1480 */         PreparedStatement psql = con.prepareStatement(sSql);
/* 1481 */         psql.setBoolean(1, true);
/* 1482 */         psql.setLong(2, parent.getId());
/*      */ 
/* 1484 */         psql.execute();
/*      */ 
/* 1486 */         psql.close();
/*      */ 
/* 1488 */         parent.setImmutable(false);
/* 1489 */         parent.setIsRestrictive(true);
/* 1490 */         parent.setImmutable(true);
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 1495 */         throw new Bn2Exception("CategoryManager.makeParentsRestrictive() : Exception occurred: " + e, e);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1500 */     makeAncestorsRestrictive(a_dbTransaction, parent);
/*      */   }
/*      */ 
/*      */   private void adjustDescendants(DBTransaction a_dbTransaction, Category a_category, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/* 1516 */     if ((a_category.getChildCategories() == null) || (a_category.getChildCategories().isEmpty()))
/*      */     {
/* 1518 */       return;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1524 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1526 */       String sSql = "UPDATE CM_Category SET IsRestrictive=?, WorkflowName=? WHERE ParentId=?";
/* 1527 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 1528 */       psql.setBoolean(1, false);
/* 1529 */       psql.setString(2, a_sWorkflowName);
/* 1530 */       psql.setLong(3, a_category.getId());
/* 1531 */       psql.executeUpdate();
/* 1532 */       psql.close();
/*      */ 
/* 1536 */       for (Category child : a_category.getChildCategories())
/*      */       {
/* 1538 */         child.setImmutable(false);
/* 1539 */         child.setIsRestrictive(false);
/* 1540 */         child.setWorkflowName(a_sWorkflowName);
/* 1541 */         child.setImmutable(true);
/* 1542 */         adjustDescendants(a_dbTransaction, child, a_sWorkflowName);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1548 */       throw new Bn2Exception("CategoryManager.adjustDescendants() : Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean existsCategoryWithNameAlready(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCatId, long a_lParentId, String a_sName) throws Bn2Exception {
/* 1573 */     boolean bNameGloballyUnique = false;
/* 1574 */     DBTransaction transaction = a_dbTransaction;
/*      */     boolean bExists;
/*      */     try {
/* 1578 */       if (transaction == null)
/*      */       {
/* 1580 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/* 1582 */       Connection con = transaction.getConnection();
/* 1583 */       bNameGloballyUnique = isNameGloballyUnique(a_lTreeId, con);
/*      */      // boolean bExists;
/* 1586 */       if (bNameGloballyUnique)
/*      */       {
/* 1588 */         bExists = existsCategoryWithNameInSubcategory(transaction, a_lTreeId, a_lCatId, 0L, a_sName);
/*      */       }
/*      */       else
/*      */       {
/* 1592 */         bExists = existsCategoryWithNameInSubcategory(transaction, a_lTreeId, a_lCatId, a_lParentId, a_sName);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       throw new Bn2Exception(getClass().getSimpleName() + ": SQL Exception: ", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1605 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */         try {
/* 1607 */           transaction.commit();
/*      */         } catch (SQLException ex) {
/*      */         }
/*      */     }
/* 1611 */     return bExists;
/*      */   }
/*      */ 
/*      */   private boolean existsCategoryWithNameInSubcategory(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCatId, long a_lParentId, String a_sName)
/*      */     throws Bn2Exception
/*      */   {
/* 1636 */     String ksMethodName = "existsCategoryWithNameInSubcategory";
/* 1637 */     Connection con = null;
/* 1638 */     boolean bExists = false;
/*      */     try
/*      */     {
/* 1643 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1646 */       ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*      */ 
/* 1649 */       String sSql = null;
/* 1650 */       String sParentIdClause = null;
/*      */ 
/* 1652 */       PreparedStatement psql = null;
/*      */ 
/* 1654 */       if (a_lParentId == 0L)
/*      */       {
/* 1656 */         sParentIdClause = "";
/*      */       }
/* 1660 */       else if (a_lParentId == -1L)
/*      */       {
/* 1662 */         sParentIdClause = "AND ParentId IS NULL";
/*      */       }
/*      */       else
/*      */       {
/* 1666 */         sParentIdClause = "AND ParentId = " + a_lParentId;
/*      */       }
/*      */ 
/* 1670 */       sSql = "SELECT DISTINCT 1 FROM CM_Category WHERE UPPER(" + sqlGenerator.getTrimFunction("?") + ") = UPPER(" + sqlGenerator.getTrimFunction("Name") + ") " + "AND CategoryTypeId = ? " + "AND Id <> ? " + sParentIdClause;
/*      */ 
/* 1676 */       psql = con.prepareStatement(sSql);
/* 1677 */       psql.setString(1, a_sName);
/* 1678 */       psql.setLong(2, a_lTreeId);
/* 1679 */       psql.setLong(3, a_lCatId);
/*      */ 
/* 1681 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1684 */       if (rs.next())
/*      */       {
/* 1686 */         bExists = true;
/*      */       }
/*      */ 
/* 1689 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1694 */       throw new Bn2Exception("CategoryManager.existsCategoryWithNameInSubcategory: Exception occurred: " + e, e);
/*      */     }
/* 1696 */     return bExists;
/*      */   }
/*      */ 
/*      */   protected boolean addCategory(DBTransaction a_dbTransaction, final long a_lTreeId, Category a_newCategory, long a_lParentId)
/*      */     throws Bn2Exception
/*      */   {
/* 1726 */     boolean bSuccess = false;
/* 1727 */     long lNewId = 0L;
/*      */ 
/* 1729 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1731 */     if (transaction == null)
/*      */     {
/* 1733 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/* 1736 */     String sSql = null;
/*      */     try
/*      */     {
/* 1739 */       if (!existsCategoryWithNameAlready(transaction, a_lTreeId, 0L, a_lParentId, a_newCategory.getName()))
/*      */       {
/* 1741 */         Connection con = transaction.getConnection();
/*      */ 
/* 1743 */         String sParentIdClause = null;
/* 1744 */         PreparedStatement psql = null;
/*      */ 
/* 1746 */         if (a_lParentId == -1L)
/*      */         {
/* 1748 */           sParentIdClause = "IS NULL";
/*      */         }
/*      */         else
/*      */         {
/* 1752 */           sParentIdClause = "= " + a_lParentId;
/*      */         }
/*      */ 
/* 1756 */         sSql = "SELECT MAX(SequenceNumber) maxSeq FROM CM_Category WHERE CategoryTypeId = ? AND ParentId " + sParentIdClause;
/*      */ 
/* 1760 */         psql = con.prepareStatement(sSql);
/* 1761 */         psql.setLong(1, a_lTreeId);
/*      */ 
/* 1763 */         ResultSet rsNextSeqNo = psql.executeQuery();
/*      */         long lNextSeqNo;
/*      */         //long lNextSeqNo;
/* 1765 */         if (rsNextSeqNo.next()) {
/* 1766 */           lNextSeqNo = rsNextSeqNo.getLong("maxSeq") + 1L;
/*      */         }
/*      */         else {
/* 1769 */           lNextSeqNo = 1L;
/*      */         }
/*      */ 
/* 1772 */         psql.close();
/*      */ 
/* 1774 */         ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*      */ 
/* 1776 */         sSql = "INSERT INTO CM_Category (";
/*      */ 
/* 1778 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1780 */           lNewId = sqlGenerator.getUniqueId(con, "CategorySequence");
/* 1781 */           sSql = sSql + "Id, ";
/*      */         }
/*      */ 
/* 1784 */         sSql = sSql + "Name, Summary, Description, CategoryTypeId, SequenceNumber, IsRestrictive, IsBrowsable, IsListboxCategory, CannotBeDeleted, Synchronised, SelectedOnLoad, ExtensionAssetId, AllowAdvancedOptions, IsExpired, WorkflowName, CanAssignIfNotLeaf ";
/*      */ 
/* 1786 */         if (a_lParentId != -1L)
/*      */         {
/* 1788 */           sSql = sSql + ", ParentId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
/*      */         }
/*      */         else
/*      */         {
/* 1792 */           sSql = sSql + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
/*      */         }
/*      */ 
/* 1795 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1797 */           sSql = sSql + ",?";
/*      */         }
/*      */ 
/* 1800 */         sSql = sSql + ")";
/*      */ 
/* 1802 */         psql = con.prepareStatement(sSql);
/* 1803 */         int iCol = 1;
/*      */ 
/* 1805 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1807 */           psql.setLong(iCol++, lNewId);
/*      */         }
/*      */ 
/* 1810 */         psql.setString(iCol++, a_newCategory.getName());
/* 1811 */         psql.setString(iCol++, a_newCategory.getSummary());
/* 1812 */         psql.setString(iCol++, a_newCategory.getDescription());
/* 1813 */         psql.setLong(iCol++, a_lTreeId);
/* 1814 */         psql.setLong(iCol++, lNextSeqNo);
/* 1815 */         psql.setBoolean(iCol++, a_newCategory.getIsRestrictive());
/* 1816 */         psql.setBoolean(iCol++, a_newCategory.getIsBrowsable());
/* 1817 */         psql.setBoolean(iCol++, a_newCategory.getIsListboxCategory());
/* 1818 */         psql.setBoolean(iCol++, a_newCategory.getCannotBeDeleted());
/* 1819 */         psql.setBoolean(iCol++, a_newCategory.isSynchronised());
/* 1820 */         psql.setBoolean(iCol++, a_newCategory.getSelectedOnLoad());
/* 1821 */         DBUtil.setFieldIdOrNull(psql, iCol++, a_newCategory.getExtensionAssetId());
/* 1822 */         psql.setBoolean(iCol++, a_newCategory.getAllowAdvancedOptions());
/* 1823 */         psql.setBoolean(iCol++, a_newCategory.isExpired());
/* 1824 */         psql.setString(iCol++, a_newCategory.getWorkflowName());
/* 1825 */         psql.setBoolean(iCol++, a_newCategory.getCanAssignIfNotLeaf());
/*      */ 
/* 1827 */         if (a_lParentId != -1L)
/*      */         {
/* 1829 */           psql.setLong(iCol++, a_lParentId);
/*      */         }
/*      */ 
/* 1832 */         psql.executeUpdate();
/* 1833 */         psql.close();
/*      */ 
/* 1835 */         if (sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1837 */           lNewId = sqlGenerator.getUniqueId(con, "CM_Category");
/*      */         }
/* 1839 */         a_newCategory.setId(lNewId);
/* 1840 */         a_newCategory.setCategoryTypeId(a_lTreeId);
/*      */ 
/* 1842 */         insertCategoryTranslations(a_newCategory, con);
/*      */ 
/* 1845 */         cacheAddCategory(a_lTreeId, a_newCategory, a_lParentId);
/*      */ 
/* 1848 */         if (a_dbTransaction != null)
/*      */         {
/* 1850 */           final long lAddedCatId = a_newCategory.getId();
/* 1851 */           a_dbTransaction.addListener(new TransactionListener()
/*      */           {
/*      */             public void doAfterRollback() {
/* 1854 */               CategoryManager.this.cacheDeleteCategory(a_lTreeId, lAddedCatId);
/* 1855 */               CategoryManager.this.m_logger.debug("Category deleted from cache on rollback after CategoryManager.addCategory()");
/*      */             }
/*      */           });
/*      */         }
/*      */ 
/* 1861 */         if ((a_lTreeId == 2L) && (a_newCategory.getIsRestrictive()))
/*      */         {
/* 1863 */           Category cat = getCacheCategory(a_dbTransaction, a_lTreeId, lNewId);
/* 1864 */           makeAncestorsRestrictive(a_dbTransaction, cat);
/*      */         }
/*      */ 
/* 1868 */         bSuccess = true;
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1873 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 1877 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1885 */       throw new SQLStatementException(sSql, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1890 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1894 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1898 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1903 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public long addCategoryTree(DBTransaction a_dbTransaction, String a_sDescription, boolean a_bAlphabetic, boolean a_bNameGloballyUnique)
/*      */     throws Bn2Exception
/*      */   {
/* 1926 */     String ksMethodName = "addCategoryTree";
/* 1927 */     long lNewId = -1L;
/*      */     try
/*      */     {
/* 1931 */       ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/* 1932 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1935 */       String sSQL = "INSERT INTO CM_CategoryType (Description, IsAlphabeticOrder, IsNameGloballyUnique";
/* 1936 */       String sValues = ") VALUES (?,?,?";
/*      */ 
/* 1938 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1940 */         lNewId = sqlGenerator.getUniqueId(con, "CategoryTypeSequence");
/* 1941 */         sSQL = sSQL + ", Id";
/* 1942 */         sValues = sValues + ",?";
/*      */       }
/*      */ 
/* 1945 */       sSQL = sSQL + sValues + ")";
/*      */ 
/* 1947 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1948 */       int iCol = 1;
/* 1949 */       psql.setString(iCol++, a_sDescription);
/* 1950 */       psql.setBoolean(iCol++, a_bAlphabetic);
/* 1951 */       psql.setBoolean(iCol++, a_bNameGloballyUnique);
/*      */ 
/* 1953 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1955 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*      */ 
/* 1958 */       psql.executeUpdate();
/*      */ 
/* 1961 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1963 */         lNewId = sqlGenerator.getUniqueId(con, "CM_CategoryType");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1969 */       throw new Bn2Exception("CategoryManager.addCategoryTree: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1972 */     return lNewId;
/*      */   }
/*      */ 
/*      */   public void deleteCategoryTree(DBTransaction a_dbTransaction, long a_lTreeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1993 */     String ksMethodName = "deleteCategoryTree";
/*      */     try
/*      */     {
/* 1997 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 2000 */       Vector vecCategories = getCategories(a_dbTransaction, a_lTreeId, -1L);
/*      */ 
/* 2003 */       for (int i = 0; i < vecCategories.size(); i++)
/*      */       {
/* 2005 */         Category cat = (Category)vecCategories.elementAt(i);
/* 2006 */         deleteCategoryFromDatabase(a_dbTransaction, a_lTreeId, cat.getId());
/*      */       }
/*      */ 
/* 2010 */       String sSQL = "DELETE FROM CM_CategoryType WHERE Id=?";
/* 2011 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 2012 */       psql.setLong(1, a_lTreeId);
/* 2013 */       psql.executeUpdate();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2018 */       throw new Bn2Exception("CategoryManager.deleteCategoryTree: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteItemFromCategories(DBTransaction a_dbTransaction, long a_lTreeId, long a_lItemId)
/*      */     throws Bn2Exception
/*      */   {
/* 2041 */     Connection con = null;
/*      */     try
/*      */     {
/* 2045 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2047 */       String sSql = "SELECT Id FROM CM_ItemInCategory iic INNER JOIN CM_Category cat ON iic.CategoryId = cat.Id WHERE iic.ItemId = ? AND cat.CategoryTypeId = ? ";
/*      */ 
/* 2053 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 2054 */       psql.setLong(1, a_lItemId);
/* 2055 */       psql.setLong(2, a_lTreeId);
/* 2056 */       ResultSet rsCats = psql.executeQuery();
/*      */ 
/* 2058 */       while (rsCats.next())
/*      */       {
/* 2060 */         long lCatId = rsCats.getLong("Id");
/* 2061 */         deleteItemFromCategory(a_dbTransaction, a_lItemId, lCatId, a_lTreeId);
/*      */       }
/*      */ 
/* 2064 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2068 */       throw new Bn2Exception("SQL Exception whilst trying to delete item from categories : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteItemFromCategory(DBTransaction a_dbTransaction, long a_lItemId, long a_lCatId, long a_lCatTreeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2090 */     Connection con = null;
/*      */     try
/*      */     {
/* 2094 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2096 */       String sSql = "DELETE FROM CM_ItemInCategory WHERE ItemId=? AND CategoryId=?";
/*      */ 
/* 2098 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 2100 */       psql.setLong(1, a_lItemId);
/* 2101 */       psql.setLong(2, a_lCatId);
/* 2102 */       psql.executeUpdate();
/* 2103 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2107 */       this.m_logger.error("SQL Exception whilst trying to delete item from category : " + e);
/* 2108 */       throw new Bn2Exception("SQL Exception whilst trying to delete item from category : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addItemToCategory(DBTransaction a_dbTransaction, long a_lItemId, long a_lCatId, long a_lCatTreeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2128 */     Connection con = null;
/*      */     try
/*      */     {
/* 2132 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2135 */       PreparedStatement psql = null;
/*      */ 
/* 2138 */       String sSql = "SELECT 1 FROM CM_ItemInCategory WHERE ItemId=? AND CategoryId=? ";
/* 2139 */       psql = con.prepareStatement(sSql);
/* 2140 */       psql.setLong(1, a_lItemId);
/* 2141 */       psql.setLong(2, a_lCatId);
/* 2142 */       ResultSet rsCats = psql.executeQuery();
/*      */ 
/* 2144 */       boolean bExists = rsCats.next();
/* 2145 */       psql.close();
/*      */ 
/* 2147 */       if (!bExists)
/*      */       {
/* 2149 */         sSql = "INSERT INTO CM_ItemInCategory (ItemId, CategoryId) VALUES (?,?)";
/* 2150 */         psql = con.prepareStatement(sSql);
/* 2151 */         psql.setLong(1, a_lItemId);
/* 2152 */         psql.setLong(2, a_lCatId);
/* 2153 */         psql.executeUpdate();
/* 2154 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2159 */       throw new Bn2Exception("SQL Exception whilst trying to adding item to category : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void moveCategoryInSequence(DBTransaction a_dbTransaction, long a_lCategoryTypeId, long a_lCategoryId, boolean a_bMoveUp)
/*      */     throws Bn2Exception
/*      */   {
/* 2180 */     Connection con = null;
/* 2181 */     String sSql = null;
/* 2182 */     PreparedStatement psql = null;
/* 2183 */     ResultSet rs = null;
/* 2184 */     int iCurrentSN = 0;
/* 2185 */     String sParentIdClause = "";
/* 2186 */     int iNewSN = 0;
/*      */     try
/*      */     {
/* 2190 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2194 */       sSql = "SELECT SequenceNumber, ParentId  FROM CM_Category WHERE Id = " + a_lCategoryId + " AND CategoryTypeId = " + a_lCategoryTypeId;
/*      */ 
/* 2199 */       psql = con.prepareStatement(sSql);
/* 2200 */       rs = psql.executeQuery();
/*      */ 
/* 2202 */       if (rs.next())
/*      */       {
/* 2204 */         iCurrentSN = rs.getInt("SequenceNumber");
/* 2205 */         if (rs.getLong("ParentId") < 1L) {
/* 2206 */           sParentIdClause = "IS NULL";
/*      */         }
/*      */         else {
/* 2209 */           sParentIdClause = "= " + rs.getLong("ParentId");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2214 */       psql.close();
/*      */ 
/* 2217 */       if (a_bMoveUp)
/*      */       {
/* 2219 */         sSql = "SELECT MAX(SequenceNumber) maxSeq  FROM CM_Category WHERE ParentId " + sParentIdClause + " AND " + "SequenceNumber < " + iCurrentSN + " AND CategoryTypeId = " + a_lCategoryTypeId;
/*      */ 
/* 2226 */         this.m_logger.debug("WE ARE IN MOVE SEQUENCE ITEM:  sql:" + sSql);
/*      */ 
/* 2228 */         psql = con.prepareStatement(sSql);
/* 2229 */         rs = psql.executeQuery();
/*      */ 
/* 2231 */         if (rs.next())
/*      */         {
/* 2233 */           iNewSN = rs.getInt("maxSeq");
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2240 */         sSql = "SELECT MIN(SequenceNumber) minSeq  FROM CM_Category WHERE ParentId " + sParentIdClause + " AND " + "SequenceNumber > " + iCurrentSN + " AND CategoryTypeId = " + a_lCategoryTypeId;
/*      */ 
/* 2247 */         psql = con.prepareStatement(sSql);
/* 2248 */         rs = psql.executeQuery();
/*      */ 
/* 2250 */         if (rs.next())
/*      */         {
/* 2252 */           iNewSN = rs.getInt("minSeq");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2258 */       boolean bWasNext = !rs.wasNull();
/* 2259 */       psql.close();
/*      */ 
/* 2261 */       if (bWasNext)
/*      */       {
/* 2263 */         sSql = "UPDATE CM_Category SET SequenceNumber = " + iCurrentSN + " " + " WHERE " + "ParentId " + sParentIdClause + " AND " + "SequenceNumber = " + iNewSN + " AND CategoryTypeId = " + a_lCategoryTypeId;
/*      */ 
/* 2270 */         psql = con.prepareStatement(sSql);
/* 2271 */         psql.executeUpdate();
/* 2272 */         psql.close();
/*      */ 
/* 2274 */         sSql = "UPDATE CM_Category SET SequenceNumber = " + iNewSN + " WHERE Id = " + a_lCategoryId + " AND CategoryTypeId = " + a_lCategoryTypeId;
/*      */ 
/* 2279 */         psql = con.prepareStatement(sSql);
/* 2280 */         psql.executeUpdate();
/* 2281 */         psql.close();
/*      */ 
/* 2283 */         cacheMoveCategory(a_lCategoryTypeId, a_lCategoryId, a_bMoveUp);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2288 */       throw new Bn2Exception("SQL Exception in CategoryManager.moveCategoryInSequence() : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void moveItemInSequence(DBTransaction a_dbTransaction, long a_lItemId, boolean a_bMoveUp)
/*      */     throws Bn2Exception
/*      */   {
/* 2309 */     Connection con = null;
/* 2310 */     String sSql = null;
/* 2311 */     PreparedStatement psql = null;
/* 2312 */     ResultSet rs = null;
/* 2313 */     int iCurrentSN = 0;
/* 2314 */     long lCatId = 0L;
/* 2315 */     int iNewSN = 0;
/*      */     try
/*      */     {
/* 2319 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2323 */       sSql = "SELECT SequenceNumber, CategoryId FROM CM_ItemInCategory c WHERE ItemID = " + a_lItemId;
/*      */ 
/* 2325 */       psql = con.prepareStatement(sSql);
/* 2326 */       rs = psql.executeQuery();
/*      */ 
/* 2328 */       if (rs.next())
/*      */       {
/* 2330 */         iCurrentSN = rs.getInt("SequenceNumber");
/* 2331 */         lCatId = rs.getLong("CategoryId");
/*      */       }
/*      */ 
/* 2334 */       psql.close();
/*      */ 
/* 2337 */       if (a_bMoveUp)
/*      */       {
/* 2339 */         sSql = "SELECT MAX(SequenceNumber)  FROM CM_ItemInCategory c WHERE CategoryId = " + lCatId + " AND SequenceNumber < " + iCurrentSN;
/*      */ 
/* 2344 */         psql = con.prepareStatement(sSql);
/* 2345 */         rs = psql.executeQuery();
/*      */ 
/* 2347 */         if (rs.next())
/*      */         {
/* 2349 */           iNewSN = rs.getInt("MAX(SequenceNumber)");
/*      */         }
/*      */ 
/* 2352 */         psql.close();
/*      */       }
/*      */       else
/*      */       {
/* 2357 */         sSql = "SELECT MIN(SequenceNumber) FROM CM_ItemInCategory c WHERE CategoryId = " + lCatId + " AND SequenceNumber > " + iCurrentSN;
/*      */ 
/* 2361 */         psql = con.prepareStatement(sSql);
/* 2362 */         rs = psql.executeQuery();
/*      */ 
/* 2364 */         if (rs.next())
/*      */         {
/* 2366 */           iNewSN = rs.getInt("MIN(SequenceNumber)");
/*      */         }
/*      */ 
/* 2369 */         psql.close();
/*      */       }
/*      */ 
/* 2373 */       if (!rs.wasNull())
/*      */       {
/* 2375 */         sSql = "UPDATE CM_ItemInCategory SET SequenceNumber = " + iCurrentSN + " WHERE CategoryId  = " + lCatId + " AND SequenceNumber = " + iNewSN;
/*      */ 
/* 2380 */         psql = con.prepareStatement(sSql);
/* 2381 */         psql.executeUpdate();
/* 2382 */         psql.close();
/*      */ 
/* 2384 */         sSql = "UPDATE CM_ItemInCategory SET SequenceNumber = " + iNewSN + " WHERE ItemId = " + a_lItemId;
/*      */ 
/* 2388 */         psql = con.prepareStatement(sSql);
/* 2389 */         psql.executeUpdate();
/* 2390 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2395 */       throw new Bn2Exception("SQL Exception in CategoryManager.moveItemInSequence() : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void moveItems(DBTransaction a_dbTransaction, long a_lTreeId, long a_lFromCategoryId, long a_lToCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/* 2420 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 2423 */       String sSql = "INSERT INTO CM_ItemInCategory (CategoryId, ItemId)  SELECT ?, iic1.ItemId FROM CM_ItemInCategory iic1 LEFT JOIN CM_ItemInCategory iic2  ON iic1.ItemId = iic2.ItemId AND iic2.CategoryId = ?  WHERE iic1.CategoryId=? AND iic2.CategoryId IS NULL";
/*      */ 
/* 2428 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 2429 */       psql.setLong(1, a_lToCategoryId);
/* 2430 */       psql.setLong(2, a_lToCategoryId);
/* 2431 */       psql.setLong(3, a_lFromCategoryId);
/* 2432 */       psql.executeUpdate();
/* 2433 */       psql.close();
/*      */ 
/* 2436 */       sSql = "DELETE FROM CM_ItemInCategory WHERE CategoryId=?";
/*      */ 
/* 2438 */       psql = con.prepareStatement(sSql);
/* 2439 */       psql.setLong(1, a_lFromCategoryId);
/* 2440 */       psql.executeUpdate();
/* 2441 */       psql.close();
/*      */ 
/* 2444 */       rebuildCategoryCache(a_dbTransaction, a_lTreeId);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2448 */       this.m_logger.error("CategoryManager.moveItems: SQL Exception: " + sqle);
/* 2449 */       throw new Bn2Exception("CategoryManager.moveItems: " + sqle, sqle);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void clearAllCategoryCaches()
/*      */   {
/* 2464 */     Set typeIds = this.m_catTypeCache.getCategoryTypeIds();
/*      */ 
/* 2466 */     Iterator itTypes = typeIds.iterator();
/* 2467 */     while (itTypes.hasNext())
/*      */     {
/* 2469 */       Long id = (Long)itTypes.next();
/* 2470 */       if (id != null)
/*      */       {
/* 2473 */         CategoryCache categoryCache = this.m_catTypeCache.getCache(id.longValue());
/*      */ 
/* 2475 */         if (categoryCache != null)
/*      */         {
/* 2477 */           categoryCache.clearCache();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getRootCategoryName(DBTransaction a_transaction, Language a_lang, long a_lTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2497 */     return "Root";
/*      */   }
/*      */ 
/*      */   private static boolean isNameGloballyUnique(long a_lTypeId, Connection a_con)
/*      */     throws SQLException
/*      */   {
/* 2515 */     PreparedStatement psql = null;
/* 2516 */     ResultSet rs = null;
/* 2517 */     String sSql = null;
/*      */ 
/* 2520 */     boolean bGloballyUnique = false;
/*      */ 
/* 2522 */     sSql = "SELECT IsNameGloballyUnique FROM CM_CategoryType WHERE Id=" + a_lTypeId;
/* 2523 */     psql = a_con.prepareStatement(sSql);
/* 2524 */     rs = psql.executeQuery();
/* 2525 */     if (rs.next())
/*      */     {
/* 2527 */       bGloballyUnique = rs.getBoolean("IsNameGloballyUnique");
/*      */     }
/* 2529 */     psql.close();
/*      */ 
/* 2531 */     return bGloballyUnique;
/*      */   }
/*      */ 
/*      */   protected static String getCategoryImageUrl(long a_lCategoryId, boolean bOnlyIfExists, boolean a_bCreateLogo)
/*      */   {
/* 2550 */     String sRelativeUrl = CategorySettings.getCategoryImagesDir() + "/" + a_lCategoryId;
/*      */ 
/* 2552 */     if (a_bCreateLogo)
/*      */     {
/* 2554 */       sRelativeUrl = sRelativeUrl + "logo";
/*      */     }
/*      */ 
/* 2557 */     sRelativeUrl = sRelativeUrl + "." + CategorySettings.getCategoryImageFormat();
/*      */ 
/* 2559 */     File fImage = new File(CategorySettings.getApplicationPath() + "/" + sRelativeUrl);
/*      */ 
/* 2562 */     if ((bOnlyIfExists) && (!fImage.exists()))
/*      */     {
/* 2565 */       sRelativeUrl = null;
/*      */     }
/*      */ 
/* 2568 */     return sRelativeUrl;
/*      */   }
/*      */ 
/*      */   public void removeCategoryImage(long a_lCategoryTypeId, long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/* 2585 */     String sFullPath = CategorySettings.getApplicationPath() + "/" + getCategoryImageUrl(a_lCategoryId, true, false);
/*      */ 
/* 2588 */     Category cat = getCacheCategory(null, a_lCategoryTypeId, a_lCategoryId);
/*      */ 
/* 2590 */     cat.setImmutable(false);
/* 2591 */     cat.setImageUrl(null);
/* 2592 */     cat.setImmutable(true);
/*      */ 
/* 2594 */     cacheUpdateCategory(a_lCategoryTypeId, cat);
/*      */ 
/* 2597 */     if (sFullPath != null)
/*      */     {
/* 2599 */       File fFile = new File(sFullPath);
/* 2600 */       fFile.delete();
/* 2601 */       FileUtil.logFileDeletion(fFile);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Category[] getAlphabeticFlatCategoryList(DBTransaction a_dbTransaction, long a_lTreeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2609 */     FlatCategoryList list = getFlatCategoryList(a_dbTransaction, a_lTreeId);
/*      */ 
/* 2611 */     String sRootName = getRootCategoryName(a_dbTransaction, LanguageConstants.k_defaultLanguage, a_lTreeId);
/*      */ 
/* 2614 */     Vector vecCategories = list.getCategories();
/*      */ 
/* 2617 */     for (int i = 0; i < vecCategories.size(); i++)
/*      */     {
/* 2619 */       Category cat = (Category)vecCategories.elementAt(i);
/* 2620 */       if (!cat.getName().equals(sRootName))
/*      */         continue;
/* 2622 */       vecCategories.removeElementAt(i);
/* 2623 */       break;
/*      */     }
/*      */ 
/* 2627 */     Category[] aCategories = new Category[vecCategories.size()];
/*      */ 
/* 2629 */     for (int i = 0; i < vecCategories.size(); i++)
/*      */     {
/* 2631 */       aCategories[i] = ((Category)vecCategories.elementAt(i));
/*      */     }
/*      */ 
/* 2635 */     AlphabeticCategoryComparator comparator = new AlphabeticCategoryComparator();
/* 2636 */     Arrays.sort(aCategories, comparator);
/*      */ 
/* 2638 */     return aCategories;
/*      */   }
/*      */ 
/*      */   public DBTransactionManager getTransactionManager()
/*      */   {
/* 2646 */     return this.m_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 2652 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setLanguageManager(LanguageManager languageManager) {
/* 2656 */     this.m_languageManager = languageManager;
/*      */   }
/*      */ 
/*      */   public void addAncestorsOfCategories(Collection<Category> a_vCategories, HashSet a_hsAncestorCategoryIds, long a_lTreeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2667 */     addAncestorsOfCategoryIds(CategoryUtil.uniqueIdsFromCategories(a_vCategories), a_hsAncestorCategoryIds, a_lTreeId);
/*      */   }
/*      */ 
/*      */   public void addAncestorsOfCategoryIds(Collection<Long> a_categoryIds, HashSet<Long> a_hsAncestorCategoryIds, long a_lTreeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2679 */     if (a_categoryIds == a_hsAncestorCategoryIds)
/*      */     {
/* 2681 */       this.m_logger.error("a_categoryIds and a_hsCategories should not be the same object");
/*      */     }
/*      */ 
/* 2684 */     DBTransaction tmpTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 2686 */     for (Iterator i$ = a_categoryIds.iterator(); i$.hasNext(); ) { long id = ((Long)i$.next()).longValue();
/*      */ 
/* 2688 */       Vector vAncestors = getAncestors(tmpTransaction, a_lTreeId, id, false);
/* 2689 */       if (vAncestors != null)
/*      */       {
/* 2691 */         for (int j = 0; j < vAncestors.size(); j++)
/*      */         {
/* 2693 */           a_hsAncestorCategoryIds.add(Long.valueOf(((Category)vAncestors.get(j)).getId()));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2700 */       tmpTransaction.commit();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2704 */       throw new Bn2Exception("AssetCategoryManager.addAncestors() : Could not commit transation : " + e.getMessage(), e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<Category> getCategories(DBTransaction a_dbTransaction, long a_lTreeId, Vector<Long> a_vecIds)
/*      */     throws Bn2Exception
/*      */   {
/* 2711 */     return getCategories(a_dbTransaction, a_lTreeId, a_vecIds, null);
/*      */   }
/*      */ 
/*      */   public Vector<Category> getCategories(DBTransaction a_dbTransaction, long a_lTreeId, Collection<Long> a_ids, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/* 2717 */     if (a_ids != null)
/*      */     {
/* 2719 */       Vector vecCats = new Vector(a_ids.size());
/*      */ 
/* 2721 */       for (Long id : a_ids)
/*      */       {
/* 2723 */         vecCats.add(getCategory(a_dbTransaction, a_lTreeId, id.longValue()));
/*      */       }
/*      */ 
/* 2726 */       if ((a_language != null) && (!a_language.isDefault()))
/*      */       {
/* 2728 */         return CategoryUtil.createTranslations(vecCats, a_language);
/*      */       }
/*      */ 
/* 2731 */       return vecCats;
/*      */     }
/* 2733 */     return null;
/*      */   }
/*      */ 
/*      */   public Vector<Category> getPopulatedCategories(DBTransaction a_dbTransaction, Item a_item, long a_lTreeId, Vector<Long> a_vecNewCategories)
/*      */     throws Bn2Exception
/*      */   {
/* 2755 */     Vector vecExistingCategories = getCategoriesForItem(a_dbTransaction, a_lTreeId, a_item.getId());
/* 2756 */     Vector vecCatObjs = new Vector();
/*      */ 
/* 2758 */     for (int i = 0; i < a_vecNewCategories.size(); i++)
/*      */     {
/* 2760 */       boolean bFound = false;
/* 2761 */       long lId = ((Long)a_vecNewCategories.elementAt(i)).longValue();
/* 2762 */       for (int x = 0; x < vecExistingCategories.size(); x++)
/*      */       {
/* 2764 */         Category cat = (Category)vecExistingCategories.elementAt(x);
/* 2765 */         if (cat.getId() != lId)
/*      */           continue;
/* 2767 */         vecCatObjs.add(cat);
/* 2768 */         bFound = true;
/* 2769 */         break;
/*      */       }
/*      */ 
/* 2772 */       if (bFound) {
/*      */         continue;
/*      */       }
/* 2775 */       Category cat = getCategory(a_dbTransaction, a_lTreeId, lId);
/* 2776 */       vecCatObjs.add(cat);
/*      */     }
/*      */ 
/* 2780 */     return vecCatObjs;
/*      */   }
/*      */ 
/*      */   public void clearCache(long a_lTypeId)
/*      */   {
/* 2793 */     CategoryCache cache = this.m_catTypeCache.getCache(a_lTypeId);
/* 2794 */     synchronized (cache)
/*      */     {
/* 2796 */       cache.clearCache();
/*      */     }
/*      */   }
/*      */ 
/*      */   private FlatCategoryList getCacheFlatCategoryList(DBTransaction a_transaction, long a_lTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2810 */     FlatCategoryList flatCatList = null;
/*      */ 
/* 2812 */     CategoryCache cache = this.m_catTypeCache.getCache(a_lTypeId);
/* 2813 */     if (cache.needsBuilding())
/*      */     {
/* 2815 */       rebuildCategoryCache(a_transaction, a_lTypeId);
/*      */     }
/*      */ 
/* 2818 */     synchronized (cache)
/*      */     {
/* 2820 */       FlatCategoryList cacheCopy = cache.getFlatCategoryList();
/* 2821 */       flatCatList = cacheCopy.clone();
/*      */     }
/*      */ 
/* 2825 */     if (flatCatList == null)
/*      */     {
/* 2827 */       throw new Bn2Exception("CategoryManager.getCacheFlatCategoryList: returned null");
/*      */     }
/*      */ 
/* 2830 */     return flatCatList;
/*      */   }
/*      */ 
/*      */   private Category getCacheCategory(DBTransaction a_transaction, long a_lTypeId, long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/* 2841 */     Category cacheCat = null;
/* 2842 */     boolean bRebuiltCache = false;
/*      */ 
/* 2844 */     CategoryCache cache = this.m_catTypeCache.getCache(a_lTypeId);
/* 2845 */     if (cache.needsBuilding())
/*      */     {
/* 2847 */       rebuildCategoryCache(a_transaction, a_lTypeId);
/* 2848 */       bRebuiltCache = true;
/*      */     }
/*      */ 
/* 2851 */     synchronized (cache)
/*      */     {
/* 2853 */       cacheCat = cache.getCategory(a_lCategoryId);
/*      */     }
/*      */ 
/* 2857 */     if (cacheCat == null)
/*      */     {
/* 2866 */       boolean bFoundInOtherCache = false;
/* 2867 */       for (CategoryCache otherCache : this.m_catTypeCache.getAllCaches())
/*      */       {
/* 2869 */         if (otherCache.getCategory(a_lCategoryId) != null)
/*      */         {
/* 2871 */           bFoundInOtherCache = true;
/* 2872 */           break;
/*      */         }
/*      */       }
/*      */ 
/* 2876 */       if ((!bFoundInOtherCache) && (!bRebuiltCache))
/*      */       {
/* 2878 */         rebuildCategoryCache(a_transaction, a_lTypeId);
/*      */ 
/* 2881 */         synchronized (cache)
/*      */         {
/* 2883 */           cacheCat = cache.getCategory(a_lCategoryId);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2888 */     return (Category)cacheCat;
/*      */   }
/*      */ 
/*      */   private Vector<Category> getCacheChildCategories(DBTransaction a_transaction, long a_lTypeId, long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/* 2902 */     Vector vec = null;
/*      */ 
/* 2905 */     Category cat = getCacheCategory(a_transaction, a_lTypeId, a_lCategoryId);
/* 2906 */     vec = cat.getChildCategories();
/*      */ 
/* 2908 */     if (vec == null)
/*      */     {
/* 2910 */       throw new Bn2Exception("CategoryManager.getCacheChildCategories: returned null");
/*      */     }
/*      */ 
/* 2913 */     return vec;
/*      */   }
/*      */ 
/*      */   public void rebuildCategoryCache(DBTransaction a_dbTransaction, long a_lTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2926 */     this.m_logger.info("Building category cache for type id = " + a_lTypeId);
/*      */ 
/* 2928 */     FlatCategoryList flatCatList = null;
/*      */ 
/* 2931 */     DBTransaction transaction = a_dbTransaction;
/* 2932 */     if (transaction == null)
/*      */     {
/* 2934 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/* 2937 */     Connection con = null;
/*      */     try
/*      */     {
/* 2941 */       int iDepth = 0;
/* 2942 */       Vector vecCats = new Vector();
/*      */ 
/* 2945 */       con = transaction.getConnection();
/*      */ 
/* 2948 */       Category rootCat = CategoryUtil.createRootCategory(transaction, a_lTypeId, true);
/*      */ 
/* 2951 */       Vector vecAllDescendants = new Vector();
/*      */ 
/* 2954 */       Vector vecAncestors = new Vector();
/*      */ 
/* 2957 */       iDepth = populateFlatCategoryList(a_lTypeId, rootCat, vecAllDescendants, vecAncestors, vecCats, 0, con, null);
/*      */ 
/* 2967 */       long[] laDescendantIds = new long[vecAllDescendants.size()];
/*      */ 
/* 2970 */       for (int i = 0; i < vecAllDescendants.size(); i++)
/*      */       {
/* 2973 */         Long lID = (Long)(Long)vecAllDescendants.get(i);
/* 2974 */         laDescendantIds[i] = lID.longValue();
/*      */       }
/*      */ 
/* 2978 */       rootCat.setAllDescendantsIds(laDescendantIds);
/*      */ 
/* 2981 */       rootCat.setImmutable(true);
/*      */ 
/* 2984 */       flatCatList = new FlatCategoryList();
/* 2985 */       flatCatList.setCategories(vecCats);
/* 2986 */       flatCatList.setDepth(iDepth);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       throw new Bn2Exception("SQL Exception in CategoryManager.getCategoryCache : ", e);
/*      */     }
/*      */     finally
/*      */     {
/* 3008 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 3013 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException e2)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3023 */     CategoryCache categoryCache = this.m_catTypeCache.getCache(a_lTypeId);
/* 3024 */     synchronized (categoryCache)
/*      */     {
/* 3026 */       categoryCache.populateCache(flatCatList);
/* 3027 */       this.m_lCacheTimestamp = System.currentTimeMillis();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void rebuildAllCategoryCaches(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
                Set<Long> ids = this.m_catTypeCache.getCategoryTypeIds();
/* 3040 */     for (Long lTypeId : ids)
/*      */     {
/* 3042 */       rebuildCategoryCache(a_transaction, lTypeId.longValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void cacheAddCategory(long a_lTreeId, Category a_cat, long a_lParentId)
/*      */   {
/* 3056 */     CategoryCache categoryCache = this.m_catTypeCache.getCache(a_lTreeId);
/* 3057 */     synchronized (categoryCache)
/*      */     {
/* 3060 */       if (categoryCache.needsBuilding())
/*      */       {
/* 3062 */         return;
/*      */       }
/*      */ 
/* 3080 */       a_cat = a_cat.clone();
/*      */ 
/* 3082 */       FlatCategoryList fcl = categoryCache.getFlatCategoryList();
/* 3083 */       Vector vecCats = fcl.getCategories();
/*      */ 
/* 3086 */       int iPos = CategoryUtil.getCategoryPosition(vecCats, a_lParentId);
/* 3087 */       if (iPos < 0)
/*      */       {
/* 3091 */         this.m_logger.error("CategoryManager.cacheAddCategory failed: could not find the parent in cache, consider rebuild cache: " + a_lParentId);
/* 3092 */         return;
/*      */       }
/*      */ 
/* 3095 */       Category parentCat = (Category)vecCats.get(iPos);
/*      */ 
/* 3098 */       a_cat.setChildCategories(new Vector());
/* 3099 */       a_cat.setAllDescendantsIds(new long[0]);
/*      */ 
/* 3102 */       a_cat.setDepth(parentCat.getDepth() + 1);
/* 3103 */       Vector vecAncestors = (Vector)parentCat.getAncestors().clone();
/*      */ 
/* 3108 */       if (parentCat.getId() > 0L)
/*      */       {
/* 3110 */         vecAncestors.add(parentCat);
/*      */       }
/* 3112 */       a_cat.setAncestors(vecAncestors);
/*      */ 
/* 3118 */       long[] listDescendants = parentCat.getAllDescendantsIds();
/*      */ 
/* 3120 */       if ((listDescendants != null) && (listDescendants.length > 0))
/*      */       {
/* 3123 */         long[] listSortedDescendants = (long[])listDescendants.clone();
/* 3124 */         Arrays.sort(listSortedDescendants);
/*      */ 
/* 3127 */         while (iPos < vecCats.size())
/*      */         {
/* 3130 */           if (iPos == vecCats.size() - 1)
/*      */           {
/*      */             break;
/*      */           }
/*      */ 
/* 3136 */           Category temp = (Category)vecCats.get(iPos + 1);
/* 3137 */           if (Arrays.binarySearch(listSortedDescendants, temp.getId()) < 0)
/*      */           {
/*      */             break;
/*      */           }
/*      */ 
/* 3144 */           iPos++;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3150 */       if (iPos >= vecCats.size() - 1)
/*      */       {
/* 3152 */         vecCats.add(a_cat);
/*      */       }
/*      */       else
/*      */       {
/* 3156 */         vecCats.add(iPos + 1, a_cat);
/*      */       }
/*      */ 
/* 3160 */       parentCat.getChildCategories().add(a_cat);
/*      */ 
/* 3164 */       Iterator itAncestors = vecAncestors.iterator();
/* 3165 */       while (itAncestors.hasNext())
/*      */       {
/* 3167 */         Category catAnc = (Category)itAncestors.next();
/* 3168 */         addToDescendants(catAnc, a_cat.getId());
/*      */       }
/*      */ 
/* 3173 */       Category rootCat = fcl.getCategoryById(-1L);
/* 3174 */       if (rootCat != null)
/*      */       {
/* 3176 */         addToDescendants(rootCat, a_cat.getId());
/*      */       }
/*      */ 
/* 3181 */       if (a_cat.getDepth() + 1 > fcl.getDepth())
/*      */       {
/* 3183 */         fcl.setDepth(a_cat.getDepth() + 1);
/*      */       }
/*      */ 
/* 3187 */       a_cat.setImmutable(true);
/*      */ 
/* 3190 */       this.m_logger.debug("CategoryManager.cacheAddCategory: added category to cache: " + a_cat.getId());
/*      */ 
/* 3193 */       categoryCache.populateCache(fcl);
/* 3194 */       this.m_lCacheTimestamp = System.currentTimeMillis();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void cacheUpdateCategory(long a_lTreeId, Category a_cat)
/*      */   {
/* 3207 */     CategoryCache categoryCache = this.m_catTypeCache.getCache(a_lTreeId);
/* 3208 */     synchronized (categoryCache)
/*      */     {
/* 3211 */       if (categoryCache.needsBuilding())
/*      */       {
/* 3213 */         return;
/*      */       }
/*      */ 
/* 3216 */       FlatCategoryList fcl = categoryCache.getFlatCategoryList();
/* 3217 */       Vector vecCats = fcl.getCategories();
/*      */ 
/* 3220 */       long lId = a_cat.getId();
/* 3221 */       int iPos = CategoryUtil.getCategoryPosition(vecCats, lId);
/* 3222 */       if (iPos < 0)
/*      */       {
/* 3226 */         this.m_logger.error("CategoryManager.cacheUpdateCategory failed: could not find the category to update: " + lId);
/* 3227 */         return;
/*      */       }
/*      */ 
/* 3231 */       Category currCat = (Category)vecCats.get(iPos);
/*      */ 
/* 3233 */       currCat.setImmutable(false);
/* 3234 */       a_cat.copyUpdateFieldsTo(currCat);
/* 3235 */       currCat.setImmutable(true);
/*      */ 
/* 3238 */       this.m_logger.debug("CategoryManager.cacheUpdateCategory: updated category to cache: " + a_cat.getId());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void cacheMoveCategory(long a_lTreeId, long a_lCategoryId, boolean a_bMoveUp)
/*      */     throws Bn2Exception
/*      */   {
/* 3254 */     CategoryCache categoryCache = this.m_catTypeCache.getCache(a_lTreeId);
/* 3255 */     synchronized (categoryCache)
/*      */     {
/* 3258 */       if (categoryCache.needsBuilding())
/*      */       {
/* 3260 */         return;
/*      */       }
/*      */ 
/* 3263 */       FlatCategoryList fcl = categoryCache.getFlatCategoryList();
/* 3264 */       Vector vecCats = fcl.getCategories();
/*      */ 
/* 3267 */       int iPos = CategoryUtil.getCategoryPosition(vecCats, a_lCategoryId);
/* 3268 */       if (iPos < 0)
/*      */       {
/* 3272 */         this.m_logger.error("CategoryManager.cacheMoveCategory failed: could not find the category to move: " + a_lCategoryId);
/* 3273 */         return;
/*      */       }
/*      */ 
/* 3277 */       int iInc = a_bMoveUp ? -1 : 1;
/*      */ 
/* 3280 */       Category cat = getCacheCategory(null, a_lTreeId, a_lCategoryId);
/* 3281 */       Category parent = getCacheCategory(null, a_lTreeId, cat.getParentId());
/* 3282 */       Vector siblings = parent.getChildCategories();
/*      */ 
/* 3285 */       int iPosInSiblings = CategoryUtil.getCategoryPosition(siblings, a_lCategoryId);
/*      */ 
/* 3288 */       int iSwapPos = iPosInSiblings + iInc;
/*      */ 
/* 3291 */       Category temp = (Category)siblings.get(iPosInSiblings);
/* 3292 */       siblings.set(iPosInSiblings, siblings.get(iSwapPos));
/* 3293 */       siblings.set(iSwapPos, temp);
/*      */ 
/* 3298 */       List removedSource = new ArrayList();
/*      */       do
/*      */       {
/* 3301 */         removedSource.add(vecCats.remove(iPos));
/*      */       }
/* 3303 */       while ((iPos < vecCats.size()) && (((Category)vecCats.get(iPos)).isDescendedFrom(cat)));
/*      */ 
/* 3307 */       int iDestPos = iPos - (a_bMoveUp ? 1 : 0);
/* 3308 */       while ((iDestPos >= 0) && (iDestPos < vecCats.size()) && (((Category)vecCats.get(iDestPos)).getDepth() != cat.getDepth()))
/*      */       {
/* 3310 */         iDestPos += iInc;
/*      */       }
/*      */ 
/* 3315 */       if ((!a_bMoveUp) && (iDestPos < vecCats.size()))
/*      */       {
/* 3317 */         Category dest = (Category)vecCats.get(iDestPos);
/*      */         do
/*      */         {
/* 3320 */           iDestPos++;
/*      */         }
/* 3322 */         while ((iDestPos < vecCats.size()) && (((Category)vecCats.get(iDestPos)).isDescendedFrom(dest)));
/*      */       }
/*      */ 
/* 3327 */       if (iDestPos < vecCats.size())
/*      */       {
/* 3329 */         for (int i = 0; i < removedSource.size(); i++)
/*      */         {
/* 3331 */           vecCats.add(iDestPos + i, removedSource.get(i));
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 3336 */         vecCats.addAll(removedSource);
/*      */       }
/*      */ 
/* 3340 */       this.m_logger.debug("CategoryManager.cacheMoveCategory: moved category in cache: " + a_lCategoryId);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Category cacheDeleteCategory(long a_lTreeId, long a_lCategoryId)
/*      */   {
/* 3352 */     Category removedCat = null;
/* 3353 */     CategoryCache categoryCache = this.m_catTypeCache.getCache(a_lTreeId);
/* 3354 */     synchronized (categoryCache)
/*      */     {
/* 3357 */       if (categoryCache.needsBuilding())
/*      */       {
/* 3359 */         return null;
/*      */       }
/*      */ 
/* 3362 */       FlatCategoryList fcl = categoryCache.getFlatCategoryList();
/* 3363 */       Vector vecCats = fcl.getCategories();
/*      */ 
/* 3366 */       int iPos = CategoryUtil.getCategoryPosition(vecCats, a_lCategoryId);
/* 3367 */       if (iPos < 0)
/*      */       {
/* 3371 */         this.m_logger.error("CategoryManager.cacheDeleteCategory failed: could not find the cat in cache: " + a_lCategoryId);
/* 3372 */         return null;
/*      */       }
/*      */ 
/* 3376 */       Category delCat = (Category)vecCats.get(iPos);
/* 3377 */       long lDelId = delCat.getId();
/* 3378 */       int iDelPos = iPos;
/* 3379 */       int iDelDepth = delCat.getDepth();
/*      */ 
/* 3382 */       if (lDelId > 0L)
/*      */       {
/* 3385 */         long[] alDelDescendents = delCat.getAllDescendantsIds();
/*      */ 
/* 3388 */         if (!delCat.getAncestorCategoryListIsEmpty())
/*      */         {
/* 3391 */           Category parentCat = delCat.getParent();
/* 3392 */           Vector vecParentChildren = parentCat.getChildCategories();
/* 3393 */           if ((vecParentChildren != null) && (vecParentChildren.size() > 0))
/*      */           {
/* 3395 */             for (int i = 0; i < vecParentChildren.size(); i++)
/*      */             {
/* 3397 */               Category child = (Category)vecParentChildren.get(i);
/* 3398 */               if (child.getId() != lDelId)
/*      */                 continue;
/* 3400 */               vecParentChildren.remove(i);
/* 3401 */               break;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 3407 */           Vector vecAncestors = delCat.getAncestors();
/* 3408 */           Iterator itAncestors = vecAncestors.iterator();
/* 3409 */           while (itAncestors.hasNext())
/*      */           {
/* 3411 */             Category catAnc = (Category)itAncestors.next();
/* 3412 */             removeFromDescendants(catAnc, lDelId);
/* 3413 */             removeFromDescendants(catAnc, alDelDescendents);
/*      */           }
/*      */         }
/* 3416 */         else if (delCat.getParent() == null)
/*      */         {
/* 3418 */           int iPosInTopLevel = CategoryUtil.getCategoryPosition(((Category)vecCats.get(0)).getChildCategories(), lDelId);
/* 3419 */           ((Category)vecCats.get(0)).getChildCategories().remove(iPosInTopLevel);
/*      */         }
/*      */ 
/* 3425 */         Category rootCat = fcl.getCategoryById(-1L);
/* 3426 */         if (rootCat != null)
/*      */         {
/* 3428 */           removeFromDescendants(rootCat, lDelId);
/* 3429 */           removeFromDescendants(rootCat, alDelDescendents);
/*      */         }
/*      */ 
/* 3433 */         removedCat = (Category)vecCats.remove(iDelPos);
/*      */ 
/* 3436 */         for (int i = 0; i < alDelDescendents.length; i++)
/*      */         {
/* 3438 */           long lDescId = alDelDescendents[i];
/* 3439 */           int iDescPos = CategoryUtil.getCategoryPosition(vecCats, lDescId);
/* 3440 */           if (iDescPos < 0)
/*      */             continue;
/* 3442 */           Category descCat = (Category)vecCats.get(iDescPos);
/* 3443 */           int iDescDelDepth = descCat.getDepth();
/*      */ 
/* 3446 */           if (iDescDelDepth > iDelDepth)
/*      */           {
/* 3448 */             iDelDepth = iDescDelDepth;
/*      */           }
/*      */ 
/* 3451 */           vecCats.remove(iDescPos);
/*      */         }
/*      */ 
/* 3457 */         if (fcl.getDepth() <= iDelDepth + 1)
/*      */         {
/* 3460 */           int iMaxDepth = 0;
/* 3461 */           Iterator it = vecCats.iterator();
/* 3462 */           while (it.hasNext())
/*      */           {
/* 3464 */             Category cat = (Category)it.next();
/* 3465 */             if (cat.getDepth() > iMaxDepth)
/*      */             {
/* 3467 */               iMaxDepth = cat.getDepth();
/*      */             }
/*      */           }
/*      */ 
/* 3471 */           fcl.setDepth(iMaxDepth + 1);
/*      */         }
/*      */ 
/* 3475 */         this.m_logger.debug("CategoryManager.cacheDeleteCategory: deleted category from cache: " + lDelId);
/*      */ 
/* 3478 */         categoryCache.populateCache(fcl);
/* 3479 */         this.m_lCacheTimestamp = System.currentTimeMillis();
/*      */       }
/*      */     }
/* 3482 */     return removedCat;
/*      */   }
/*      */ 
/*      */   private static void addToDescendants(Category a_cat, long a_lNewId)
/*      */   {
/* 3493 */     long[] alCurrentDescIds = a_cat.getAllDescendantsIds();
/* 3494 */     long[] alNewDescIds = new long[alCurrentDescIds.length + 1];
/*      */ 
/* 3496 */     for (int i = 0; i < alCurrentDescIds.length; i++)
/*      */     {
/* 3498 */       alNewDescIds[i] = alCurrentDescIds[i];
/*      */     }
/*      */ 
/* 3501 */     alNewDescIds[alCurrentDescIds.length] = a_lNewId;
/*      */ 
/* 3503 */     a_cat.setAllDescendantsIds(alNewDescIds);
/*      */   }
/*      */ 
/*      */   private static void removeFromDescendants(Category a_cat, long a_lDelId)
/*      */   {
/* 3515 */     long[] alCurrentDescIds = a_cat.getAllDescendantsIds();
/*      */ 
/* 3518 */     boolean bFound = false;
/* 3519 */     Vector vecNewList = new Vector();
/*      */ 
/* 3521 */     for (int i = 0; i < alCurrentDescIds.length; i++)
/*      */     {
/* 3523 */       if (alCurrentDescIds[i] != a_lDelId)
/*      */       {
/* 3525 */         vecNewList.add(Long.valueOf(alCurrentDescIds[i]));
/*      */       }
/*      */       else
/*      */       {
/* 3529 */         bFound = true;
/*      */       }
/*      */     }
/*      */ 
/* 3533 */     if (!bFound)
/*      */     {
/* 3536 */       GlobalApplication.getInstance().getLogger().error("CategoryManager.removeFromDescendants: could not find the deleted category in parent descendants list: parent=" + a_cat.getId() + " deleted=" + a_lDelId);
/*      */     }
/*      */     else
/*      */     {
/* 3541 */       long[] alNewDescIds = new long[vecNewList.size()];
/* 3542 */       int index = 0;
/* 3543 */       for (Iterator i$ = vecNewList.iterator(); i$.hasNext(); ) { long lDescId = ((Long)i$.next()).longValue();
/*      */ 
/* 3545 */         alNewDescIds[index] = lDescId;
/* 3546 */         index++;
/*      */       }
/*      */ 
/* 3549 */       a_cat.setAllDescendantsIds(alNewDescIds);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void removeFromDescendants(Category a_cat, long[] a_alDelIds)
/*      */   {
/* 3563 */     for (int i = 0; i < a_alDelIds.length; i++)
/*      */     {
/* 3565 */       long lId = a_alDelIds[i];
/* 3566 */       removeFromDescendants(a_cat, lId);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static int populateFlatCategoryList(long a_lTypeId, Category a_cat, Vector a_vecCatDescendantIds, Vector a_vecCatAncestors, Vector a_vecAllCategories, int a_iDepthSoFar, Connection a_con)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 3594 */     return populateFlatCategoryList(a_lTypeId, a_cat, a_vecCatDescendantIds, a_vecCatAncestors, a_vecAllCategories, a_iDepthSoFar, a_con, null);
/*      */   }
/*      */ 
/*      */   private static int populateFlatCategoryList(long a_lTypeId, Category a_cat, Vector a_vecCatDescendantIds, Vector a_vecCatAncestors, Vector a_vecAllCategories, int a_iDepthSoFar, Connection a_con, String a_sCatIds)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 3638 */     PreparedStatement psql = null;
/* 3639 */     ResultSet rs = null;
/* 3640 */     String sSql = null;
/*      */ 
/* 3643 */     String sSQLOrderBy = "ORDER BY cat.SequenceNumber ";
/* 3644 */     boolean bAlpha = isAlphabeticCategory(a_lTypeId, a_con);
/* 3645 */     if (bAlpha)
/*      */     {
/* 3647 */       sSQLOrderBy = "ORDER BY cat.Name ";
/*      */     }
/*      */ 
/* 3651 */     int iThisDepth = a_iDepthSoFar + 1;
/*      */ 
/* 3653 */     int iMaxDepth = iThisDepth;
/*      */ 
/* 3657 */     int iIndexOfParent = -1;
/*      */ 
/* 3659 */     sSql = "SELECT cat.Id catId, cat.Name catName,cat.Summary catSummary,cat.Description catDescription, cat.IsBrowsable catIsBrowsable, cat.IsRestrictive catIsRestrictive, cat.IsListboxCategory catIsListboxCategory, cat.CannotBeDeleted catCannotBeDeleted, cat.CategoryTypeId catCategoryTypeId, cat.SequenceNumber catSequenceNumber, cat.Synchronised catSynchronised, cat.SelectedOnLoad catSelectedOnLoad, cat.AllowAdvancedOptions catAllowAdvancedOptions, cat.IsExpired catIsExpired, cat.WorkflowName catWorkflowName, cat.CanAssignIfNotLeaf catCanAssignIfNotLeaf, cat.ExtensionAssetId catExtensionAssetId, tc.Name tcName, tc.Description tcDescription, tc.Summary tcSummary, l.Id lId, l.Code lCode, l.Name lName FROM CM_Category cat LEFT JOIN TranslatedCategory tc ON tc.CategoryId = cat.Id LEFT JOIN Language l ON l.Id = tc.LanguageId AND l.IsDefault=0 WHERE cat.CategoryTypeId = " + a_lTypeId + " ";
/*      */ 
/* 3691 */     if (a_cat.getId() == -1L)
/*      */     {
/* 3693 */       sSql = sSql + " AND cat.ParentId IS NULL ";
/*      */     }
/*      */     else
/*      */     {
/* 3698 */       sSql = sSql + " AND cat.ParentId = " + a_cat.getId() + " ";
/*      */     }
/*      */ 
/* 3701 */     if (a_sCatIds != null)
/*      */     {
/* 3703 */       sSql = sSql + "AND cat.Id IN (" + a_sCatIds + ") ";
/*      */     }
/*      */ 
/* 3706 */     sSql = sSql + sSQLOrderBy;
/*      */ 
/* 3708 */     if (a_cat.getId() != -1L)
/*      */     {
/* 3711 */       iIndexOfParent = a_vecAllCategories.size();
/*      */     }
/*      */ 
/* 3714 */     psql = a_con.prepareStatement(sSql);
/*      */ 
/* 3716 */     rs = psql.executeQuery();
/*      */ 
/* 3719 */     Vector vecChildCategories = new Vector();
/* 3720 */     long lLastCatId = 0L;
/* 3721 */     Category childCategory = null;
/* 3722 */     boolean bAtLeastOneChildHasImage = false;
/*      */ 
/* 3725 */     while (rs.next())
/*      */     {
/* 3727 */       if (lLastCatId != rs.getLong("catId"))
/*      */       {
/* 3729 */         childCategory = new CategoryImpl();
/* 3730 */         childCategory.setId(rs.getLong("catId"));
/* 3731 */         childCategory.setName(rs.getString("catName"));
/* 3732 */         childCategory.setSummary(rs.getString("catSummary"));
/* 3733 */         childCategory.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "catDescription"));
/* 3734 */         childCategory.setIsBrowsable(rs.getBoolean("catIsBrowsable"));
/* 3735 */         childCategory.setIsRestrictive(rs.getBoolean("catIsRestrictive"));
/* 3736 */         childCategory.setIsListboxCategory(rs.getBoolean("catIsListboxCategory"));
/* 3737 */         childCategory.setCannotBeDeleted(rs.getBoolean("catCannotBeDeleted"));
/* 3738 */         childCategory.setCategoryTypeId(rs.getLong("catCategoryTypeId"));
/* 3739 */         childCategory.setDepth(iThisDepth);
/* 3740 */         childCategory.setSynchronised(rs.getBoolean("catSynchronised"));
/* 3741 */         childCategory.setSelectedOnLoad(rs.getBoolean("catSelectedOnLoad"));
/* 3742 */         childCategory.setAllowAdvancedOptions(rs.getBoolean("catAllowAdvancedOptions"));
/* 3743 */         childCategory.setExpired(rs.getBoolean("catIsExpired"));
/* 3744 */         childCategory.setWorkflowName(rs.getString("catWorkflowName"));
/* 3745 */         childCategory.setCanAssignIfNotLeaf(rs.getBoolean("catCanAssignIfNotLeaf"));
/* 3746 */         childCategory.setExtensionAssetId(rs.getLong("catExtensionAssetId"));
/*      */ 
/* 3749 */         childCategory.setParentIndex(iIndexOfParent);
/*      */ 
/* 3752 */         childCategory.setImageUrl(getCategoryImageUrl(childCategory.getId(), true, false));
/*      */ 
/* 3754 */         if (StringUtil.stringIsPopulated(childCategory.getImageUrl()))
/*      */         {
/* 3756 */           bAtLeastOneChildHasImage = true;
/*      */         }
/*      */ 
/* 3761 */         vecChildCategories.add(childCategory);
/*      */ 
/* 3763 */         lLastCatId = childCategory.getId();
/*      */       }
/*      */ 
/* 3766 */       if ((rs.getLong("lId") <= 0L) || (rs.getLong("lId") == 1L))
/*      */         continue;
/* 3768 */       Category.Translation translation = childCategory.createTranslation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/* 3769 */       translation.setName(rs.getString("tcName"));
/* 3770 */       translation.setDescription(rs.getString("tcDescription"));
/* 3771 */       translation.setSummary(rs.getString("tcSummary"));
/* 3772 */       childCategory.getTranslations().add(translation);
/*      */     }
/*      */ 
/* 3776 */     psql.close();
/*      */ 
/* 3779 */     a_cat.setChildCategories(vecChildCategories);
/*      */ 
/* 3782 */     a_cat.setAtLeastOneChildHasImage(bAtLeastOneChildHasImage);
/*      */ 
/* 3785 */     a_cat.setAncestors(a_vecCatAncestors);
/*      */ 
/* 3788 */     a_vecAllCategories.add(a_cat);
/*      */ 
/* 3791 */     Vector vecChildrensAncestors = new Vector();
/*      */ 
/* 3795 */     vecChildrensAncestors = (Vector)a_vecCatAncestors.clone();
/*      */ 
/* 3798 */     if (a_cat.getId() > 0L)
/*      */     {
/* 3800 */       vecChildrensAncestors.add(a_cat);
/*      */     }
/*      */ 
/* 3804 */     for (int i = 0; i < vecChildCategories.size(); i++)
/*      */     {
/* 3806 */       Category catToTraverse = (Category)vecChildCategories.get(i);
/*      */ 
/* 3809 */       a_vecCatDescendantIds.add(new Long(catToTraverse.getId()));
/*      */ 
/* 3812 */       Vector vecThisCategoriesDescendantIds = new Vector();
/*      */ 
/* 3815 */       int iDepthReached = populateFlatCategoryList(a_lTypeId, catToTraverse, vecThisCategoriesDescendantIds, vecChildrensAncestors, a_vecAllCategories, iThisDepth, a_con);
/*      */ 
/* 3819 */       long[] laDescendantIds = new long[vecThisCategoriesDescendantIds.size()];
/*      */ 
/* 3822 */       for (int iDescendantIndex = 0; iDescendantIndex < vecThisCategoriesDescendantIds.size(); iDescendantIndex++)
/*      */       {
/* 3825 */         Long lID = (Long)(Long)vecThisCategoriesDescendantIds.get(iDescendantIndex);
/* 3826 */         laDescendantIds[iDescendantIndex] = lID.longValue();
/*      */ 
/* 3829 */         a_vecCatDescendantIds.add(lID);
/*      */       }
/*      */ 
/* 3833 */       catToTraverse.setAllDescendantsIds(laDescendantIds);
/*      */ 
/* 3836 */       if (iDepthReached > iMaxDepth)
/*      */       {
/* 3838 */         iMaxDepth = iDepthReached;
/*      */       }
/*      */ 
/* 3842 */       catToTraverse.setImmutable(true);
/*      */     }
/*      */ 
/* 3845 */     return iMaxDepth;
/*      */   }
/*      */ 
/*      */   private static boolean isAlphabeticCategory(long a_lTypeId, Connection a_con)
/*      */     throws SQLException
/*      */   {
/* 3863 */     PreparedStatement psql = null;
/* 3864 */     ResultSet rs = null;
/* 3865 */     String sSql = null;
/*      */ 
/* 3868 */     boolean bAlpha = false;
/*      */ 
/* 3870 */     sSql = "SELECT IsAlphabeticOrder FROM CM_CategoryType WHERE Id=" + a_lTypeId;
/* 3871 */     psql = a_con.prepareStatement(sSql);
/* 3872 */     rs = psql.executeQuery();
/* 3873 */     if (rs.next())
/*      */     {
/* 3875 */       bAlpha = rs.getBoolean("IsAlphabeticOrder");
/*      */     }
/* 3877 */     psql.close();
/*      */ 
/* 3879 */     return bAlpha;
/*      */   }
/*      */ 
/*      */   private Collection<Long> getAllCategoryTypeIds(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 3892 */     ArrayList catTypeIds = null;
/* 3893 */     DBTransaction transaction = a_dbTransaction;
/* 3894 */     Connection con = null;
/* 3895 */     if (transaction == null)
/*      */     {
/* 3897 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3903 */       con = transaction.getConnection();
/*      */ 
/* 3905 */       PreparedStatement psql = null;
/* 3906 */       ResultSet rs = null;
/* 3907 */       catTypeIds = new ArrayList();
/* 3908 */       psql = con.prepareStatement("SELECT Id FROM CM_CategoryType");
/* 3909 */       rs = psql.executeQuery();
/* 3910 */       while (rs.next())
/*      */       {
/* 3912 */         catTypeIds.add(Long.valueOf(rs.getLong("Id")));
/*      */       }
/* 3914 */       psql.close();
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/* 3918 */       throw new Bn2Exception("Exception in CategoryManager.getAllCategoryTypeIds. ", t);
/*      */     }
/*      */     finally
/*      */     {
/* 3923 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 3928 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException e2)
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3937 */     return catTypeIds;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*      */   {
/* 3942 */     this.m_fileStoreManager = a_fileStoreManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.service.CategoryManager
 * JD-Core Version:    0.6.0
 */