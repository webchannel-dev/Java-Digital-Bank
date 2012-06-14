/*     */ package com.bright.assetbank.category.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.util.CategoryUtil;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class CategoryCountCacheManager extends Bn2Manager
/*     */ {
/*  59 */   private MultiLanguageSearchManager m_searchManager = null;
/*  60 */   private AssetCategoryManager m_categoryManager = null;
/*  61 */   private DBTransactionManager m_transactionManager = null;
/*  62 */   private ScheduleManager m_scheduleManager = null;
/*     */ 
/*  64 */   private Map m_mapCache = new HashMap();
/*     */ 
/*  66 */   private Map m_mapSearchCriteriaForCount = new HashMap();
/*  67 */   private Map m_mapCatIdKeyMap = Collections.synchronizedMap(new HashMap());
/*  68 */   private Set m_setDirtyCategoryIds = new HashSet();
/*  69 */   private boolean m_bCacheInvalidated = false;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     super.startup();
/*     */ 
/*  76 */     int iSyncPeriod = AssetBankSettings.getCategoryCountRefreshPeriod();
/*     */ 
/*  79 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run()
/*     */       {
/*  83 */         CategoryCountCacheManager.this.refreshCache();
/*     */       }
/*     */     };
/*  88 */     this.m_logger.info("Scheduling category count cache refresh task to run every " + iSyncPeriod + " minutes");
/*     */ 
/*  90 */     this.m_scheduleManager.schedule(task, 60000L, iSyncPeriod * 60L * 1000L, true);
/*     */   }
/*     */ 
/*     */   public Integer getItemCountForSubCategories(Category a_cat, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/*  97 */     int iCount = 0;
/*  98 */     if (a_cat.getChildCategories() != null)
/*     */     {
/* 100 */       Enumeration e = a_cat.getChildCategories().elements();
/* 101 */       while (e.hasMoreElements())
/*     */       {
/* 103 */         Category cat = (Category)e.nextElement();
/* 104 */         if (cat.getChildCategories() != null)
/*     */         {
/* 106 */           iCount += getItemCountForSubCategories(cat, a_userProfile).intValue();
/*     */         }
/* 108 */         iCount += getItemCount(cat.getId(), cat.getCategoryTypeId(), a_userProfile).intValue();
/*     */       }
/*     */     }
/* 111 */     return Integer.valueOf(iCount);
/*     */   }
/*     */ 
/*     */   public Integer getItemCount(long a_lCategoryId, long a_lCategoryTypeId, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 129 */     String sKey = null;
/*     */ 
/* 132 */     sKey = "" + a_lCategoryId + "-" + a_userProfile.getPermissionsKeyForCaching();
/*     */ 
/* 137 */     Integer intCount = (Integer)this.m_mapCache.get(sKey);
/*     */ 
/* 139 */     if (intCount == null)
/*     */     {
/* 142 */       SearchCriteria searchCriteria = new SearchCriteria();
/*     */ 
/* 145 */       searchCriteria.setIncludeImplicitCategoryMembers(true);
/*     */ 
/* 148 */       searchCriteria.setCategoryIds(Long.toString(a_lCategoryId));
/*     */ 
/* 151 */       searchCriteria.setExtensionTypeToExclude(a_lCategoryTypeId);
/*     */ 
/* 154 */       searchCriteria.setupPermissions(a_userProfile);
/*     */ 
/* 157 */       if (a_userProfile.getSelectedFilters() != null)
/*     */       {
/* 159 */         searchCriteria.setSelectedFilters((Vector)a_userProfile.getSelectedFilters().clone());
/*     */       }
/*     */ 
/* 162 */       synchronized (this.m_mapCache)
/*     */       {
/* 165 */         if ((intCount = (Integer)this.m_mapCache.get(sKey)) == null)
/*     */         {
/* 168 */           intCount = Integer.valueOf(this.m_searchManager.getHitCount(searchCriteria, a_userProfile.getCurrentLanguage().getCode()));
/*     */ 
/* 171 */           this.m_mapCache.put(sKey, intCount);
/*     */ 
/* 174 */           searchCriteria.setLanguageCode(a_userProfile.getCurrentLanguage().getCode());
/* 175 */           this.m_mapSearchCriteriaForCount.put(sKey, searchCriteria);
/*     */ 
/* 178 */           Set setKeysForCatId = null;
/* 179 */           if (this.m_mapCatIdKeyMap.containsKey(Long.valueOf(a_lCategoryId)))
/*     */           {
/* 181 */             setKeysForCatId = (Set)this.m_mapCatIdKeyMap.get(Long.valueOf(a_lCategoryId));
/*     */           }
/*     */           else
/*     */           {
/* 185 */             setKeysForCatId = new HashSet();
/* 186 */             this.m_mapCatIdKeyMap.put(Long.valueOf(a_lCategoryId), setKeysForCatId);
/*     */           }
/* 188 */           setKeysForCatId.add(sKey);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 194 */     return intCount;
/*     */   }
/*     */ 
/*     */   public void invalidateCache()
/*     */   {
/* 209 */     this.m_bCacheInvalidated = true;
/*     */   }
/*     */ 
/*     */   public void invalidateCache(Vector a_vIds, long a_lTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 223 */     if (a_vIds != null)
/*     */     {
/* 225 */       long[] ids = new long[a_vIds.size()];
/*     */ 
/* 227 */       for (int i = 0; i < ids.length; i++)
/*     */       {
/* 229 */         ids[i] = ((Long)a_vIds.get(i)).longValue();
/*     */       }
/*     */ 
/* 232 */       invalidateCache(null, ids, a_lTreeId);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void invalidateCache(DBTransaction a_dbTransaction, long[] a_alIds, long a_lTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 246 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 248 */     if (a_alIds != null)
/*     */     {
/* 250 */       if (transaction == null)
/*     */       {
/* 252 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 257 */         for (int i = 0; i < a_alIds.length; i++)
/*     */         {
/* 259 */           invalidateCache(transaction, a_alIds[i], a_lTreeId);
/*     */         }
/*     */       }
/*     */       catch (Bn2Exception sqle)
/*     */       {
/* 264 */         if ((transaction != null) && (a_dbTransaction == null))
/*     */         {
/*     */           try
/*     */           {
/* 268 */             transaction.rollback();
/*     */           }
/*     */           catch (SQLException sqle1)
/*     */           {
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 277 */         invalidateCache();
/*     */ /* 279 */  this.m_logger.error("CategoryCountCacheManager.invalidateCache() : Bn2Exception whilst calling m_categoryManager.getAncestors() - invalidating whole cache!", sqle);
/*     */       }
/*     */       finally
/*     */       {
/* 283 */         if ((transaction != null) && (a_dbTransaction == null))
/*     */         {
/*     */           try
/*     */           {
/* 287 */             transaction.commit();
/*     */           }
/*     */           catch (SQLException sqle)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void invalidateCache(DBTransaction a_dbTransaction, long a_lId, long a_lTreeId)
/*     */     throws Bn2Exception
/*     */   {
/* 308 */     int iMinRefreshableCountSize = AssetBankSettings.getMinimumCategoryCountRefreshSize();
/*     */ 
/* 311 */     if (this.m_setDirtyCategoryIds.contains(Long.valueOf(a_lId)))
/*     */     {
/* 313 */       return;
/*     */     }
/*     */ 
/* 317 */     Vector vAncestors = this.m_categoryManager.getAncestors(a_dbTransaction, a_lTreeId, a_lId, false);
/*     */ 
/* 320 */     if (vAncestors == null)
/*     */     {
/* 322 */       return;
/*     */     }
/*     */ 
/* 325 */     Vector<Long> vCatIds = CategoryUtil.getCategoryIdVector(vAncestors);
/* 326 */     vCatIds.add(Long.valueOf(a_lId));
/*     */ 
/* 329 */     boolean bInCache = false;
/* 330 */     for (Long lId : vCatIds)
/*     */     {
/* 332 */       if (this.m_mapCatIdKeyMap.containsKey(lId))
/*     */       {
/* 334 */         bInCache = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 339 */     if (!bInCache)
/*     */     {
/* 341 */       return;
/*     */     }
        Iterator iteratorKeys;
/*     */  Long lId;
/* 344 */     for (Iterator iterator = vCatIds.iterator(); iterator.hasNext(); )
/*     */     {
/* 346 */       lId = (Long)iterator.next();
/*     */ 
/* 349 */       Set setKeys = (Set)this.m_mapCatIdKeyMap.get(lId);
/*     */ 
/* 351 */       if ((setKeys != null) && (setKeys.iterator() != null))
/*     */       {
/* 355 */         for (iteratorKeys = setKeys.iterator(); iteratorKeys.hasNext(); )
/*     */         {
/* 357 */           String sKey = (String)iteratorKeys.next();
/*     */ 
/* 359 */           synchronized (this.m_mapCache)
/*     */           {
/* 361 */             if ((this.m_mapCache.containsKey(sKey)) && (((Integer)this.m_mapCache.get(sKey)).intValue() < iMinRefreshableCountSize))
/*     */             {
/* 363 */               this.m_mapCache.remove(sKey);
/* 364 */               this.m_mapSearchCriteriaForCount.remove(sKey);
/* 365 */               iteratorKeys.remove();
/*     */             }
/*     */             else
/*     */             {
/* 369 */               synchronized (this.m_setDirtyCategoryIds)
/*     */               {
/* 372 */                 this.m_setDirtyCategoryIds.add(lId);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */    
/*     */     //Iterator iteratorKeys;
/*     */   }
/*     */ 
/*     */   public void rebuildCacheNow()
/*     */   {
/* 387 */     this.m_bCacheInvalidated = true;
/* 388 */     refreshCache();
/*     */   }
/*     */ 
/*     */   private void refreshCache()
/*     */   {
/* 396 */     Map mapUpdatedCache = null;
/* 397 */     Collection colDirtyIds = null;
/* 398 */     int iCacheEntryCount = 0;
/*     */ 
/* 400 */     synchronized (this.m_setDirtyCategoryIds)
/*     */     {
/* 402 */       if ((!this.m_bCacheInvalidated) && (this.m_setDirtyCategoryIds.size() == 0))
/*     */       {
/* 404 */         return;
/*     */       }
/*     */ 
/* 407 */       mapUpdatedCache = new HashMap();
/*     */ 
/* 409 */       if (this.m_bCacheInvalidated)
/*     */       {
/* 411 */         colDirtyIds = new ArrayList(this.m_mapCatIdKeyMap.keySet());
/*     */       }
/*     */       else
/*     */       {
/* 415 */         colDirtyIds = new ArrayList(this.m_setDirtyCategoryIds);
/*     */       }
/*     */ 
/* 419 */       this.m_setDirtyCategoryIds.clear();
/*     */     }
/*     */ 
/* 422 */     int iErrorCount = 0;
        Iterator iteratorKeys;
/*     */ 
/* 424 */     for (Iterator iterator = colDirtyIds.iterator(); iterator.hasNext(); )
/*     */     {
/* 426 */       Long lDirtyCatId = (Long)iterator.next();
/*     */ 
/* 428 */       if (this.m_mapCatIdKeyMap.containsKey(lDirtyCatId))
/*     */       {
/* 431 */         Set vKeys = (Set)this.m_mapCatIdKeyMap.get(lDirtyCatId);
/*     */ 
/* 433 */         iCacheEntryCount += vKeys.size();
/*     */ 
/* 436 */         for (iteratorKeys = vKeys.iterator(); iteratorKeys.hasNext(); )
/*     */         {
/* 438 */           String sKey = (String)iteratorKeys.next();
/*     */ 
/* 440 */           if (this.m_mapSearchCriteriaForCount.containsKey(sKey))
/*     */           {
/* 442 */             SearchCriteria searchCriteria = (SearchCriteria)this.m_mapSearchCriteriaForCount.get(sKey);
/*     */             try
/*     */             {
/* 447 */               Integer intCount = Integer.valueOf(this.m_searchManager.getHitCount(searchCriteria, searchCriteria.getLanguageCode()));
/*     */ 
/* 450 */               mapUpdatedCache.put(sKey, intCount);
/*     */             }
/*     */             catch (Bn2Exception e)
/*     */             {
/* 454 */               iErrorCount++; if (iErrorCount <= 1)
/*     */               {
/* 456 */                 this.m_logger.error("CategoryCountCacheManager.refreshCache() : Bn2Exception whilst getting count for category with key=" + sKey + " : " + e.getLocalizedMessage(), e);
/*     */               }
/*     */               else
/*     */               {
/* 460 */                 this.m_logger.error("CategoryCountCacheManager.refreshCache() : Bn2Exception whilst getting count for category with key=" + sKey + " (repeated error: stack trace suppressed)");
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 469 */     synchronized (this.m_mapCache)
/*     */     {
/*     */       //Iterator iteratorKeys;
/* 472 */       if (this.m_bCacheInvalidated)
/*     */       {
/* 474 */         this.m_mapCache.clear();
/*     */       }
/*     */ 
/* 478 */       this.m_mapCache.putAll(mapUpdatedCache);
/* 479 */       this.m_bCacheInvalidated = false;
/*     */     }
/*     */ 
/* 482 */     this.m_logger.debug("Successfully refreshed " + colDirtyIds.size() + " categories in the category count cache, with a total of " + iCacheEntryCount + " entries replaced");
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 487 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_manager)
/*     */   {
/* 492 */     this.m_categoryManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*     */   {
/* 497 */     this.m_transactionManager = a_sTransactionManager;
/*     */   }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_manager)
/*     */   {
/* 502 */     this.m_scheduleManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.service.CategoryCountCacheManager
 * JD-Core Version:    0.6.0
 */