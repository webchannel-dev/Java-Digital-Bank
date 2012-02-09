/*     */ package com.bright.assetbank.search.service;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.externalfilter.service.ExternalFilterManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.search.bean.IndexableDocument;
/*     */ import com.bright.framework.search.bean.IterableIndexableDocument;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.search.service.AnalyzerFactory;
/*     */ import com.bright.framework.search.service.ManualReindexQueueManager;
/*     */ import com.bright.framework.search.service.SortFieldSpecifier;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class MultiLanguageSearchManager extends ManualReindexQueueManager
/*     */ {
/*     */   private static final String c_ksClassName = "MultiLanguageSearchManager";
/*  82 */   private AssetManager m_assetManager = null;
/*  83 */   private SortFieldSpecifier m_SortFieldSpecifier = null;
/*  84 */   private Map<String, AssetSearchManager> m_searchManagers = null;
/*  85 */   private String m_sIndexRootFilepath = null;
/*  86 */   private LanguageManager m_languageManager = null;
/*  87 */   private DBTransactionManager m_transactionManager = null;
/*  88 */   private ScheduleManager m_scheduleManager = null;
/*  89 */   private ExternalFilterManager m_externalFilterManager = null;
/*  90 */   private AttributeSearchManager m_attributeSearchManager = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  97 */     super.startup();
/*     */ 
/*  99 */     this.m_sIndexRootFilepath = AssetBankSettings.getIndexDirectory();
/*     */ 
/* 101 */     if (AssetBankSettings.getUseRelativeDirectories())
/*     */     {
/* 103 */       this.m_sIndexRootFilepath = (GlobalSettings.getApplicationPath() + "/" + this.m_sIndexRootFilepath);
/*     */     }
/*     */ 
/* 106 */     DBTransaction transaction = null;
/* 107 */     this.m_searchManagers = new Hashtable();
/*     */ 
/* 109 */     if (AssetBankSettings.getSupportMultiLanguage())
/*     */     {
/*     */       try
/*     */       {
/* 113 */         transaction = this.m_transactionManager.getNewTransaction();
/*     */ 
/* 116 */         List languages = this.m_languageManager.getLanguages(transaction, true);
/* 117 */         Iterator itLanguages = languages.iterator();
/* 118 */         while (itLanguages.hasNext())
/*     */         {
/* 120 */           Language language = (Language)itLanguages.next();
/* 121 */           addLanguage(language, true);
/*     */         }
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/* 127 */         if (transaction != null)
/*     */         {
/*     */           try
/*     */           {
/* 131 */             transaction.commit();
/*     */           }
/*     */           catch (SQLException sqle)
/*     */           {
/* 135 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 142 */       addLanguage(LanguageConstants.k_defaultLanguage, false);
/*     */     }
/*     */ 
/* 146 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/* 152 */           MultiLanguageSearchManager.this.optimiseIndices();
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/* 156 */           MultiLanguageSearchManager.this.m_logger.error("SingleIndexSearchManager: Bn2Exception whilst optimising indices : " + bn2e);
/*     */         }
/*     */       }
/*     */     };
/* 161 */     int iHourOfDay = FrameworkSettings.getoptimiseIndexHourOfDay();
/* 162 */     this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, false);
/*     */   }
/*     */ 
/*     */   public void addLanguage(Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 172 */     addLanguage(a_language, true);
/*     */   }
/*     */ 
/*     */   private void addLanguage(Language a_language, boolean a_bMultiLanguage)
/*     */     throws Bn2Exception
/*     */   {
/* 183 */     AssetSearchManager searchManager = new AssetSearchManager("Assets (" + a_language.getCode() + ")");
/*     */     String sIndexDirectory=null;
/* 187 */     if (a_bMultiLanguage)
/*     */     {
/* 189 */       sIndexDirectory = this.m_sIndexRootFilepath + "/" + a_language.getCode();
/* 190 */       searchManager.setIndexLanguage(a_language);
/*     */     }
/*     */     else
/*     */     {
/* 195 */       sIndexDirectory = this.m_sIndexRootFilepath;
/*     */     }
/*     */ 
/* 198 */     AnalyzerFactory analyzerFactory = this.m_attributeSearchManager.analyzerFactoryForLanguage(a_language.getCode());
/* 199 */     AssetIndexManager assetIndexManager = new AssetIndexManager(sIndexDirectory, analyzerFactory);
/* 200 */     searchManager.setSortFieldSpecifier(this.m_SortFieldSpecifier);
/* 201 */     searchManager.setIndexManager(assetIndexManager);
/* 202 */     searchManager.setAssetManager(this.m_assetManager);
/* 203 */     searchManager.setExternalFilterManager(this.m_externalFilterManager);
/*     */ 
/* 206 */     searchManager.initialiseIndex();
/* 207 */     this.m_searchManagers.put(a_language.getCode(), searchManager);
/*     */   }
/*     */ 
/*     */   public void removeLanguage(String a_sLanguageCode)
/*     */   {
/* 212 */     AssetSearchManager searchManager = (AssetSearchManager)this.m_searchManagers.remove(a_sLanguageCode);
/* 213 */     if (searchManager != null)
/*     */     {
/* 215 */       searchManager.deleteIndex();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void rebuildIndicesAsynchronously()
/*     */   {
/* 229 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 234 */           MultiLanguageSearchManager.this.rebuildIndices(false);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 238 */           MultiLanguageSearchManager.this.m_logger.error("MultiLanguageSearchManager.rebuildIndexAsynchronously() : Exception caught while rebuilding indices : " + e, e);
/*     */         }
/*     */       }
/*     */     }
/* 229 */     .start();
/*     */   }
/*     */ 
/*     */   private Vector<Asset> loadIndexableDocuments(int a_iStartId, int a_iBatchSize, Vector<Long> a_vecIds)
/*     */     throws Bn2Exception
/*     */   {
/* 255 */     return this.m_assetManager.getAssetsByIdAndBatchSize(null, a_vecIds, null, a_iStartId, a_iBatchSize, true, true, false);
/*     */   }
/*     */ 
/*     */   public SearchResults<LightweightAsset> searchByPageIndex(SearchQuery a_searchQuery, int a_iPageIndex, int a_iPageSize, String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/* 264 */     AssetSearchManager manager = assetSearchManagerForLanguage(a_sLanguageCode);
/*     */ 
/* 266 */     int iStartIndex = (a_iPageSize >= 0) && (a_iPageIndex >= 0) ? a_iPageIndex * a_iPageSize : 0;
/*     */ 
/* 268 */     return manager.search(a_searchQuery, iStartIndex, a_iPageSize);
/*     */   }
/*     */ 
/*     */   public SearchResults<LightweightAsset> searchByResultIndex(SearchQuery a_searchQuery, int a_iStartIndex, int a_iNumResults, String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/* 277 */     AssetSearchManager manager = assetSearchManagerForLanguage(a_sLanguageCode);
/* 278 */     return manager.search(a_searchQuery, a_iStartIndex, a_iNumResults);
/*     */   }
/*     */ 
/*     */   public SearchResults<LightweightAsset> searchByResultIndex(SearchQuery a_searchQuery, int a_iStartIndex, int a_iNumResults)
/*     */     throws Bn2Exception
/*     */   {
/* 287 */     return searchByResultIndex(a_searchQuery, a_iStartIndex, a_iNumResults, "en");
/*     */   }
/*     */ 
/*     */   public SearchResults<LightweightAsset> search(SearchQuery a_searchQuery, String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/* 296 */     return searchByPageIndex(a_searchQuery, -1, -1, a_sLanguageCode);
/*     */   }
/*     */ 
/*     */   public SearchResults<LightweightAsset> search(SearchQuery a_searchQuery)
/*     */     throws Bn2Exception
/*     */   {
/* 305 */     return searchByPageIndex(a_searchQuery, -1, -1, "en");
/*     */   }
/*     */ 
/*     */   public int getHitCount(SearchQuery a_searchQuery, String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/* 319 */     AssetSearchManager manager = assetSearchManagerForLanguage(a_sLanguageCode);
/*     */ 
/* 321 */     return manager.getHitCount(a_searchQuery);
/*     */   }
/*     */ 
/*     */   public int getHitCount(SearchQuery a_searchQuery)
/*     */     throws Bn2Exception
/*     */   {
/* 335 */     return getHitCount(a_searchQuery, "en");
/*     */   }
/*     */ 
/*     */   private AssetSearchManager assetSearchManagerForLanguage(String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/* 341 */     AssetSearchManager manager = (AssetSearchManager)this.m_searchManagers.get(a_sLanguageCode);
/*     */ 
/* 343 */     if (manager == null)
/*     */     {
/* 345 */       throw new Bn2Exception("MultiLanguageSearchManager.search() : No index found for language code '" + a_sLanguageCode + "'");
/*     */     }
/* 347 */     return manager;
/*     */   }
/*     */ 
/*     */   public void rebuildIndices(boolean a_bQuick)
/*     */     throws Bn2Exception
/*     */   {
/* 353 */     rebuildIndex(a_bQuick, -1L, null);
/*     */   }
/*     */ 
/*     */   public void rebuildIndex(boolean a_bQuick, long a_lUserId, Vector<Long> a_vecDocIds)
/*     */     throws Bn2Exception
/*     */   {
/* 363 */     Vector vIndexableDocuments = null;
/*     */ 
/* 365 */     int iNumDocumentsAtATime = FrameworkSettings.getIndexAllBatchSize();
/* 366 */     int iStartId = 1;
/* 367 */     int iTotalIndexed = 0;
/*     */ 
/* 371 */     if ((a_vecDocIds != null) && (a_vecDocIds.size() > 0))
/*     */     {
/* 373 */       a_bQuick = true;
/*     */     }
/*     */ 
/* 376 */     this.m_logger.debug("Starting reindex of all languages at " + new Date());
/* 377 */     if (a_lUserId > 0L)
/*     */     {
/* 379 */       String sStart = "Starting";
/* 380 */       if (a_bQuick)
/*     */       {
/* 382 */         sStart = sStart + " quick";
/*     */       }
/*     */ 
/* 385 */       addMessage(a_lUserId, sStart + " reindex");
/*     */     }
/*     */ 
/* 389 */     Iterator itSearchManagers = this.m_searchManagers.values().iterator();
/* 390 */     if (!a_bQuick)
/*     */     {
/* 392 */       while (itSearchManagers.hasNext())
/*     */       {
/* 394 */         AssetSearchManager manager = (AssetSearchManager)itSearchManagers.next();
/* 395 */         manager.clearIndex();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*     */     do
/*     */     {
/* 402 */       vIndexableDocuments = loadIndexableDocuments(iStartId, iNumDocumentsAtATime, a_vecDocIds);
/*     */ 
/* 404 */       if ((vIndexableDocuments != null) && (vIndexableDocuments.size() > 0))
/*     */       {
/* 407 */         int iSize = vIndexableDocuments.size();
/* 408 */         int iMaxId = new Long(((Asset)vIndexableDocuments.get(iSize - 1)).getId()).intValue();
/* 409 */         iStartId = iMaxId + 1;
/*     */ 
/* 412 */         Iterator itLangCodes = this.m_searchManagers.keySet().iterator();
/*     */ 
/* 414 */         while (itLangCodes.hasNext())
/*     */         {
/* 416 */           String sCode = (String)itLangCodes.next();
/* 417 */           AssetSearchManager manager = (AssetSearchManager)this.m_searchManagers.get(sCode);
/* 418 */           manager.indexDocuments(vIndexableDocuments, false, a_bQuick, false);
/*     */         }
/*     */ 
/* 421 */         if (a_lUserId > 0L)
/*     */         {
/* 423 */           addMessage(a_lUserId, iSize + " assets indexed");
/* 424 */           iTotalIndexed += iSize;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 429 */         addMessage(a_lUserId, "No indexable documents found while rebuilding indices");
/* 430 */         this.m_logger.debug("No indexable documents found while rebuilding indices");
/*     */       }
/*     */     }
/* 433 */     while ((vIndexableDocuments != null) && (vIndexableDocuments.size() >= iNumDocumentsAtATime));
/*     */ 
/* 436 */     if (a_lUserId > 0L)
/*     */     {
/* 438 */       addMessage(a_lUserId, "Optimising index");
/*     */     }
/* 440 */     itSearchManagers = this.m_searchManagers.values().iterator();
/* 441 */     while (itSearchManagers.hasNext())
/*     */     {
/* 443 */       AssetSearchManager manager = (AssetSearchManager)itSearchManagers.next();
/* 444 */       manager.optimiseIndex();
/*     */     }
/*     */ 
/* 447 */     if (a_lUserId > 0L)
/*     */     {
/* 449 */       addMessage(a_lUserId, "Reindexed " + iTotalIndexed + " assets in total");
/* 450 */       addMessage(a_lUserId, "Finished reindexing");
/*     */     }
/*     */ 
/* 453 */     this.m_logger.debug("Finished reindexing all languages at " + new Date());
/*     */   }
/*     */ 
/*     */   public void optimiseIndices()
/*     */     throws Bn2Exception
/*     */   {
/* 462 */     Iterator itSearchManagers = this.m_searchManagers.values().iterator();
/* 463 */     while (itSearchManagers.hasNext())
/*     */     {
/* 465 */       AssetSearchManager manager = (AssetSearchManager)itSearchManagers.next();
/* 466 */       manager.optimiseIndex();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void indexDocument(IndexableDocument a_doc, boolean a_bReindex)
/*     */     throws Bn2Exception
/*     */   {
/* 478 */     Set keys = this.m_searchManagers.keySet();
/*     */ 
/* 480 */     Iterator itKeys = keys.iterator();
/* 481 */     while (itKeys.hasNext())
/*     */     {
/* 483 */       String sLanguageCode = (String)itKeys.next();
/* 484 */       AssetSearchManager manager = (AssetSearchManager)this.m_searchManagers.get(sLanguageCode);
/* 485 */       manager.indexDocument(a_doc, a_bReindex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void indexDocumentsAsynchronously(final Vector a_vecDocuments)
/*     */   {
/* 500 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 505 */           MultiLanguageSearchManager.this.indexDocuments(null, a_vecDocuments, true);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 509 */           MultiLanguageSearchManager.this.m_logger.error("MultiLanguageSearchManager.rebuildIndexAsynchronously() : Exception caught while rebuilding indices : " + e, e);
/*     */         }
/*     */       }
/*     */     }
/* 500 */     .start();
/*     */   }
/*     */ 
/*     */   public void indexDocuments(DBTransaction a_dbTransaction, Vector a_vecDocs, boolean a_bReindex)
/*     */     throws Bn2Exception
/*     */   {
/* 524 */     indexDocuments(a_dbTransaction, a_vecDocs, a_bReindex, false);
/*     */   }
/*     */ 
/*     */   public void indexDocuments(DBTransaction a_dbTransaction, Vector a_vecDocs, boolean a_bReindex, boolean a_bQuick)
/*     */     throws Bn2Exception
/*     */   {
/* 535 */     indexDocuments(a_dbTransaction, a_vecDocs, a_bReindex, a_bQuick, false);
/*     */   }
/*     */ 
/*     */   public void indexDocuments(DBTransaction a_dbTransaction, Vector a_vecDocs, boolean a_bReindex, boolean a_bQuick, boolean a_bOnlyUsageChanged)
/*     */     throws Bn2Exception
/*     */   {
/* 548 */     Iterator itLangCodes = this.m_searchManagers.keySet().iterator();
/*     */ 
/* 550 */     while (itLangCodes.hasNext())
/*     */     {
/* 552 */       String sCode = (String)itLangCodes.next();
/* 553 */       AssetSearchManager manager = (AssetSearchManager)this.m_searchManagers.get(sCode);
/* 554 */       manager.indexDocuments(a_vecDocs, a_bReindex, a_bQuick, a_bOnlyUsageChanged);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeDocument(IterableIndexableDocument a_doc)
/*     */     throws Bn2Exception
/*     */   {
/* 565 */     Iterator itSearchManagers = this.m_searchManagers.values().iterator();
/* 566 */     while (itSearchManagers.hasNext())
/*     */     {
/* 568 */       AssetSearchManager manager = (AssetSearchManager)itSearchManagers.next();
/* 569 */       manager.removeDocument(a_doc);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 575 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public SortFieldSpecifier getSortFieldSpecifier()
/*     */   {
/* 580 */     return this.m_SortFieldSpecifier;
/*     */   }
/*     */ 
/*     */   public void setSortFieldSpecifier(SortFieldSpecifier a_sSortFieldSpecifier)
/*     */   {
/* 585 */     this.m_SortFieldSpecifier = a_sSortFieldSpecifier;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 590 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 595 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager scheduleManager)
/*     */   {
/* 600 */     this.m_scheduleManager = scheduleManager;
/*     */   }
/*     */ 
/*     */   public void setExternalFilterManager(ExternalFilterManager aManager)
/*     */   {
/* 605 */     this.m_externalFilterManager = aManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeSearchManager(AttributeSearchManager a_attributeSearchManager)
/*     */   {
/* 610 */     this.m_attributeSearchManager = a_attributeSearchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.service.MultiLanguageSearchManager
 * JD-Core Version:    0.6.0
 */