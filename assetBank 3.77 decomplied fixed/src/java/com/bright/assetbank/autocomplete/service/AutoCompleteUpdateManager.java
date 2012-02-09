/*     */ package com.bright.assetbank.autocomplete.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetDeleteContext;
/*     */ import com.bright.assetbank.application.service.AssetDeleteParticipant;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.AssetSaveContext;
/*     */ import com.bright.assetbank.application.service.AssetSaveParticipant;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.AttributeSearchManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.queue.service.MessagingQueueManager;
/*     */ import com.bright.framework.search.service.IndexManager;
/*     */ import com.bright.framework.search.util.LuceneUtil;
/*     */ import com.bright.framework.util.UnaryFunction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AutoCompleteUpdateManager extends MessagingQueueManager
/*     */   implements AssetSaveParticipant, AssetDeleteParticipant
/*     */ {
/*     */   public static final String c_ksClassName = "AutoCompleteUpdateManager";
/*     */   private static final String c_ksAttributeName_AutoCompleteActive = "autoCompleteActive";
/*     */   private AssetManager m_assetManager;
/*     */   private AttributeManager m_attributeManager;
/*     */   private AttributeSearchManager m_attributeSearchManager;
/*     */   private AutoCompleteIndexManagers m_autoCompleteIndexManagers;
/*     */   private LanguageManager m_languageManager;
/*     */   private ScheduleManager m_scheduleManager;
/*     */   private MultiLanguageSearchManager m_searchManager;
/*     */ 
/*     */   public AutoCompleteUpdateManager()
/*     */   {
/*  89 */     this.m_scheduleManager = null;
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  96 */     super.startup();
/*     */ 
/*  99 */     this.m_assetManager.registerAssetSaveParticipant(this);
/* 100 */     this.m_assetManager.registerAssetDeleteParticipant(this);
/*     */ 
/* 103 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/* 109 */           if (AutoCompleteUpdateManager.this.autoCompleteActive())
/*     */           {
/* 111 */             AutoCompleteUpdateManager.this.optimiseIndices();
/*     */           }
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/* 116 */           AutoCompleteUpdateManager.this.m_logger.error("SingleIndexSearchManager: Bn2Exception whilst optimising indices : " + bn2e);
/*     */         }
/*     */       }
/*     */     };
/* 121 */     int iHourOfDay = FrameworkSettings.getoptimiseIndexHourOfDay();
/* 122 */     this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, false);
/*     */   }
/*     */ 
/*     */   public void initAssetSave(AssetSaveContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/* 132 */     boolean autoCompleteActive = autoCompleteActive();
/*     */ 
/* 134 */     if (autoCompleteActive)
/*     */     {
/* 136 */       a_context.needOriginalAsset();
/*     */     }
/*     */ 
/* 139 */     a_context.setAttribute("AutoCompleteUpdateManager", "autoCompleteActive", Boolean.valueOf(autoCompleteActive));
/*     */   }
/*     */ 
/*     */   public void save(AssetSaveContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public void assetWasSaved(AssetSaveContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/* 150 */     boolean autoCompleteActive = ((Boolean)a_context.getAttribute("AutoCompleteUpdateManager", "autoCompleteActive")).booleanValue();
/*     */ 
/* 152 */     if (autoCompleteActive)
/*     */     {
/* 154 */       Asset asset = a_context.getAsset();
/*     */       Asset originalAsset;
/*     */      // Asset originalAsset;
/* 156 */       if (a_context.isNew())
/*     */       {
/* 158 */         originalAsset = null;
/*     */       }
/*     */       else
/*     */       {
/* 162 */         originalAsset = a_context.getOriginalAsset();
/*     */       }
/*     */ 
/* 165 */       ACIndexAssetKeywordsJob indexJob = new ACIndexAssetKeywordsJob(originalAsset, asset);
/* 166 */       queueItem(indexJob);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void assetWillBeDeleted(AssetDeleteContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public void assetWasDeleted(AssetDeleteContext a_context)
/*     */     throws Bn2Exception
/*     */   {
/* 179 */     if (autoCompleteActive())
/*     */     {
/* 181 */       queueItem(new ACIndexAssetKeywordsJob(a_context.getOriginalAsset(), null));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processQueueItem(QueuedItem a_item)
/*     */     throws Bn2Exception
/*     */   {
/* 190 */     ACIndexJob indexJob = (ACIndexJob)a_item;
/* 191 */     indexJob.perform(this);
/*     */   }
/*     */ 
/*     */   public void queueRebuildIndex(long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 204 */     queueItem(new ACRebuildIndexJob(a_lUserId));
/*     */   }
/*     */ 
/*     */   public void addLanguage(Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 217 */     IndexManager newIndexManager = this.m_autoCompleteIndexManagers.addLanguage(a_language);
/* 218 */     IndexManager defaultIndexManager = this.m_autoCompleteIndexManagers.indexManagerForLanguage("en");
/* 219 */     defaultIndexManager.copyIndexDirectoryTo(newIndexManager);
/*     */   }
/*     */ 
/*     */   public void removeLanguage(String a_sLanguageCode) throws Bn2Exception
/*     */   {
/* 224 */     this.m_autoCompleteIndexManagers.removeLanguage(a_sLanguageCode);
/*     */   }
/*     */ 
/*     */   public void renameLanguage(Language a_old, Language a_new)
/*     */     throws Bn2Exception
/*     */   {
/* 232 */     IndexManager oldIndexManager = this.m_autoCompleteIndexManagers.indexManagerForLanguage(a_old.getCode());
/* 233 */     IndexManager newIndexManager = this.m_autoCompleteIndexManagers.addLanguage(a_new);
/* 234 */     if (oldIndexManager.indexExists())
/*     */     {
/* 236 */       oldIndexManager.renameIndexDirectory(newIndexManager);
/*     */     }
/* 238 */     this.m_autoCompleteIndexManagers.removeLanguage(a_old.getCode());
/*     */   }
/*     */ 
/*     */   void rebuildIndex(long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 250 */     String ksMethodName = "rebuildIndex";
/*     */ 
/* 252 */     startBatchProcess(a_lUserId);
/*     */ 
/* 254 */     this.m_logger.info("AutoCompleteUpdateManager: rebuilding index.  Initiating user: " + a_lUserId);
/*     */ 
/* 257 */     Exception error = null;
/*     */     try
/*     */     {
/* 262 */       Set autoCompleteAttributeIds = this.m_attributeManager.getAttributeIdsForAutoCompleteIndex();
/*     */ 
/* 264 */       List languages = this.m_languageManager.getLanguages(null);
/*     */ 
/* 269 */       if (a_lUserId > 0L) addMessage(a_lUserId, "Collecting attribute values from assets"); AffectedKeywords affectedKeywords = new AffectedKeywords(this.m_attributeSearchManager);
/*     */ 
/* 272 */       int iNumDocumentsAtATime = FrameworkSettings.getIndexAllBatchSize();
/* 273 */       int iStartId = 1;
/*     */ 
/* 275 */       long loadAssetsTotalTime = 0L; long populateKeywordsTotalTime = 0L;
/*     */       List<Asset> assets;
/*     */       do { long loadAssetsStart = System.currentTimeMillis();
/*     */ 
/* 284 */         assets = null;
/* 285 */         assets = loadAssets(iStartId, iNumDocumentsAtATime);
/* 286 */         long loadAssetsEnd = System.currentTimeMillis();
/* 287 */         long loadAssetsTime = loadAssetsEnd - loadAssetsStart;
/* 288 */         loadAssetsTotalTime += loadAssetsTime;
/* 289 */         this.m_logger.debug("loadAssets() took " + loadAssetsTime + "ms, total " + loadAssetsTotalTime);
/*     */ 
/* 291 */         if ((assets != null) && (assets.size() > 0))
/*     */         {
/* 302 */           int iSize = assets.size();
/* 303 */           int iMaxId = (int)((Asset)assets.get(iSize - 1)).getId();
/* 304 */           iStartId = iMaxId + 1;
/*     */ 
/* 306 */           long populateKeywordsStart = System.currentTimeMillis();
/* 307 */           for (Asset asset : assets)
/*     */           {
/* 309 */             populateAffectedKeywordsFromAsset(languages, autoCompleteAttributeIds, affectedKeywords, asset);
/*     */           }
/*     */ 
/* 312 */           long populateKeywordsEnd = System.currentTimeMillis();
/* 313 */           long populateKeywordsTime = populateKeywordsEnd - populateKeywordsStart;
/* 314 */           populateKeywordsTotalTime += populateKeywordsTime;
/* 315 */           this.m_logger.debug("populate keywords took " + populateKeywordsTime + "ms, total " + populateKeywordsTotalTime);
/*     */ 
/* 317 */           if (a_lUserId > 0L)
/*     */           {
/* 319 */             addMessage(a_lUserId, iSize + " assets read");
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 324 */           addMessage(a_lUserId, "No indexable documents found while rebuilding indices");
/* 325 */           this.m_logger.debug("No indexable documents found while rebuilding indices");
/*     */         }
/*     */       }
/* 328 */       while ((assets != null) && (assets.size() >= iNumDocumentsAtATime));
/*     */ 
/* 332 */       if (a_lUserId > 0L) addMessage(a_lUserId, "Counting search hits for keywords");
/* 333 */       ACIndexEntries indexEntries = countHits(affectedKeywords);
/*     */ 
/* 336 */       if (a_lUserId > 0L) addMessage(a_lUserId, "Deleting auto complete index");
/*     */ 
/* 338 */       this.m_autoCompleteIndexManagers.forEachIndexManager(new UnaryFunction()
/*     */       {
/*     */         public Object execute(Object a_arg) throws Bn2Exception
/*     */         {
                    //IndexManager
                    
/* 342 */           ((IndexManager)a_arg).deleteIndex();
/* 343 */           return null;
/*     */         }
/*     */       });
/* 348 */       if (a_lUserId > 0L) addMessage(a_lUserId, "Adding auto complete index entries");
/* 349 */       addIndexEntries(indexEntries.getIndexEntriesToSaveByLanguage());
/*     */ 
/* 351 */       if (a_lUserId > 0L) addMessage(a_lUserId, "Finished reindexing");
/* 352 */       this.m_logger.info("AutoCompleteUpdateManager: finished rebuilding.  Initiating user: " + a_lUserId);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 356 */       error = e;
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 360 */       error = e;
/*     */     }
/*     */     finally
/*     */     {
/* 364 */       if (error != null)
/*     */       {
/* 366 */         this.m_logger.error("Exception in AutoCompleteUpdateManager.rebuildIndex", error);
/* 367 */         if (a_lUserId > 0L) addMessage(a_lUserId, "An internal error occurred during the reindex: " + error);
/*     */       }
/* 369 */       endBatchProcess(a_lUserId);
/*     */     }
/*     */   }
/*     */ 
/*     */   private List<Asset> loadAssets(int a_iStartId, int a_iBatchSize)
/*     */     throws Bn2Exception
/*     */   {
/* 376 */     return this.m_assetManager.getAssetsByIdAndBatchSize(null, null, null, a_iStartId, a_iBatchSize, false, false, false);
/*     */   }
/*     */ 
/*     */   private boolean autoCompleteActive()
/*     */     throws Bn2Exception
/*     */   {
/* 388 */     return AssetBankSettings.getAutoCompleteEnabled();
/*     */   }
/*     */ 
/*     */   void indexAssetKeywords(Asset a_originalAsset, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 407 */     Set autoCompleteAttributeIds = this.m_attributeManager.getAttributeIdsForAutoCompleteIndex();
/*     */ 
/* 409 */     AffectedKeywords affectedKeywords = new AffectedKeywords(this.m_attributeSearchManager);
/*     */ 
/* 411 */     if (a_originalAsset != null)
/*     */     {
/* 413 */       populateAffectedKeywordsFromAsset(autoCompleteAttributeIds, affectedKeywords, a_originalAsset);
/*     */     }
/*     */ 
/* 416 */     if (a_asset != null)
/*     */     {
/* 418 */       populateAffectedKeywordsFromAsset(autoCompleteAttributeIds, affectedKeywords, a_asset);
/*     */     }
/*     */ 
/* 422 */     countHitsAndUpdateACIndices(affectedKeywords);
/*     */   }
/*     */ 
/*     */   private void populateAffectedKeywordsFromAsset(Set<Long> a_attributeIds, AffectedKeywords a_affectedKeywords, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 433 */     List allLanguages = this.m_languageManager.getLanguages(null);
/* 434 */     populateAffectedKeywordsFromAsset(allLanguages, a_attributeIds, a_affectedKeywords, a_asset);
/*     */   }
/*     */ 
/*     */   private void populateAffectedKeywordsFromAsset(Collection<Language> a_languages, Set<Long> a_attributeIds, AffectedKeywords a_affectedKeywords, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 445 */     for (Long attributeId : a_attributeIds)
/*     */     {
            /* 447 */
            long lAttributeId = attributeId.longValue();
            /* 448 */
            AttributeValue attVal = a_asset.getAttributeValue(lAttributeId);
/*     */ 
/* 452 */       if (attVal != null)
/*     */       {
/* 454 */         for (Language language : a_languages)
/*     */         {
/* 456 */           if (attVal.getAttribute().getIsKeywordPicker())
/*     */           {
/* 458 */             Collection keywords = AttributeUtil.getKeywordsCollectionForAttributeValue(attVal, language);
/* 459 */             a_affectedKeywords.addTokenisedFieldValue(language.getCode(), new AttributeField(lAttributeId, attVal.getAttribute().isAutoComplete()), keywords);
/*     */           }
/*     */           else
/*     */           {
/* 465 */             String sAttributeValue = attVal.getValueForIndex(language);
/*     */ 
/* 468 */             if ((StringUtils.isEmpty(attVal.getAttribute().getTokenDelimiterRegex())) && (!attVal.getAttribute().getIsTextarea()) && (attVal.getAttribute().isAutoComplete()))
/*     */             {
/* 473 */               Collection keywords = new ArrayList();
/* 474 */               keywords.add(sAttributeValue);
/* 475 */               a_affectedKeywords.addTokenisedFieldValue(language.getCode(), new AttributeField(lAttributeId, attVal.getAttribute().isAutoComplete()), keywords);
/*     */             }
/*     */             else
/*     */             {
/* 481 */               a_affectedKeywords.addAttributeValue(language.getCode(), new AttributeField(lAttributeId, attVal.getAttribute().isAutoComplete()), sAttributeValue);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     long lAttributeId;
/*     */     AttributeValue attVal;
/* 496 */     for (Language language : a_languages)
/*     */     {
/* 499 */       if (AssetBankSettings.includeCategoryNamesInKeywordSearch())
/*     */       {
/* 501 */         a_affectedKeywords.addTokenisedFieldValue(language.getCode(), new KeywordField("cat"), a_asset.getDescriptiveAndPermissionCategoryNamesIncludingAncestors(language));
/*     */       }
/*     */ 
/* 508 */       a_affectedKeywords.addTokenisedFieldValue(language.getCode(), new KeywordField("fn"), Collections.singleton(a_asset.getOriginalFilename()));
/*     */ 
/* 515 */       String sKeywords = a_asset.getKeywordsAndSynonyms(language);
/* 516 */       if (AssetBankSettings.getAutoCompleteIncludeFileKeywords())
/*     */       {
/* 518 */         String sFileKeywords = a_asset.getFileKeywords();
/* 519 */         if (sFileKeywords.length() > 0)
/*     */         {
/* 521 */           sKeywords = sKeywords + " " + sFileKeywords;
/*     */         }
/*     */       }
/* 524 */       a_affectedKeywords.addFieldValue(language.getCode(), new KeywordField("kw"), sKeywords);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void countHitsAndUpdateACIndices(AffectedKeywords a_affectedKeywords)
/*     */     throws Bn2Exception
/*     */   {
/* 535 */     ACIndexEntries indexEntries = countHits(a_affectedKeywords);
/*     */ 
/* 539 */     deleteIndexEntries(indexEntries.getIndexEntriesToDeleteByLanguage());
/*     */ 
/* 542 */     addIndexEntries(indexEntries.getIndexEntriesToSaveByLanguage());
/*     */   }
/*     */ 
/*     */   private void deleteIndexEntries(Map<String, List<ACIndexEntry>> a_indexEntriesToDeleteByLanguage)
/*     */     throws Bn2Exception
/*     */   {
/* 548 */     for (Map.Entry entry : a_indexEntriesToDeleteByLanguage.entrySet())
/*     */     {
/* 550 */       String sLanguageCode = (String)entry.getKey();
/* 551 */       List indexEntriesToDelete = (List)entry.getValue();
/* 552 */       if (!indexEntriesToDelete.isEmpty())
/*     */       {
/* 554 */         this.m_autoCompleteIndexManagers.indexManagerForLanguage(sLanguageCode).deleteDocuments(indexEntriesToDelete);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addIndexEntries(Map<String, List<ACIndexEntry>> a_indexEntriesToSaveByLanguage)
/*     */     throws Bn2Exception
/*     */   {
/* 562 */     for (Map.Entry entry : a_indexEntriesToSaveByLanguage.entrySet())
/*     */     {
/* 564 */       String sLanguageCode = (String)entry.getKey();
/* 565 */       List indexEntriesToSave = (List)entry.getValue();
/* 566 */       if (!indexEntriesToSave.isEmpty())
/*     */       {
/* 568 */         IndexManager indexManager = this.m_autoCompleteIndexManagers.indexManagerForLanguage(sLanguageCode);
/*     */ 
/* 570 */         indexManager.indexDocuments(indexEntriesToSave, null);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private ACIndexEntries countHits(AffectedKeywords a_affectedKeywords)
/*     */     throws Bn2Exception
/*     */   {
/* 578 */     ACIndexEntries indexEntries = new ACIndexEntries();
/*     */ 
/* 580 */     Iterator keywordSetsByFieldByLanguageIterator = a_affectedKeywords.keywordSetsByFieldByLanguageIterator();
/*     */     String sLanguageCode;
/*     */     List indexEntriesToSave;
/*     */     List indexEntriesToDelete;
/*     */     ACField field;
/* 581 */     while (keywordSetsByFieldByLanguageIterator.hasNext())
/*     */     {
/* 583 */       Map.Entry keywordSetsByFieldByLanguageEntry = (Map.Entry)keywordSetsByFieldByLanguageIterator.next();
/* 584 */       sLanguageCode = (String)keywordSetsByFieldByLanguageEntry.getKey();
/* 585 */       Map keywordSetsByField = (Map)keywordSetsByFieldByLanguageEntry.getValue();
/*     */ 
/* 587 */       indexEntriesToSave = (List)indexEntries.getIndexEntriesToSaveByLanguage().get(sLanguageCode);
/* 588 */       indexEntriesToDelete = (List)indexEntries.getIndexEntriesToDeleteByLanguage().get(sLanguageCode);
/*     */       Set keywordSetsByFieldSet = keywordSetsByField.entrySet();
/* 590 */      //for (Map.Entry keywordsByFieldEntry : keywordSetsByField.entrySet())
                for(Iterator it=keywordSetsByFieldSet.iterator();it.hasNext();)
/*     */       {
                    
/* 592 */         //field = (ACField)keywordsByFieldEntry.getKey();
                    Map.Entry keywordsByFieldEntry =(Map.Entry) it.next();
                    field = (ACField)keywordsByFieldEntry.getKey();
/* 593 */         Set<String> keywords = (Set)keywordsByFieldEntry.getValue();
/*     */ 
/* 595 */         for (String keyword : keywords)
/*     */         {
/* 598 */           int hitCount = field.countHits(sLanguageCode, keyword);
/*     */ 
/* 600 */           ACIndexEntry acIndexEntry = new ACIndexEntry(field, keyword, hitCount);
/*     */ 
/* 602 */           if (hitCount == 0)
/*     */           {
/* 605 */             indexEntriesToDelete.add(acIndexEntry);
/*     */           }
/*     */           else
/*     */           {
/* 610 */             indexEntriesToSave.add(acIndexEntry);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 616 */     return indexEntries;
/*     */   }
/*     */ 
/*     */   private void optimiseIndices() throws Bn2Exception
/*     */   {
/* 621 */     if (autoCompleteActive())
/*     */     {
/* 623 */       this.m_logger.info("Optimising auto complete indices");
/* 624 */       this.m_autoCompleteIndexManagers.forEachIndexManager(new UnaryFunction()
/*     */       {
/*     */         public Object execute(Object a_indexManager) throws Bn2Exception
/*     */         {
/* 628 */           ((IndexManager)a_indexManager).optimizeIndex();
/* 629 */           return null;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addVisibleToMostPeopleCriteria(SearchCriteria a_criteria)
/*     */   {
/* 638 */     a_criteria.addApprovalStatus(3);
/*     */   }
/*     */ 
/*     */   private int countVisibleHits(SearchCriteria a_criteria, String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/* 649 */     addVisibleToMostPeopleCriteria(a_criteria);
/*     */ 
/* 655 */     return this.m_searchManager.getHitCount(a_criteria, a_sLanguageCode);
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 803 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 808 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeSearchManager(AttributeSearchManager a_attributeSearchManager)
/*     */   {
/* 813 */     this.m_attributeSearchManager = a_attributeSearchManager;
/*     */   }
/*     */ 
/*     */   public void setAutoCompleteIndexManagers(AutoCompleteIndexManagers a_autoCompleteIndexManagers)
/*     */   {
/* 818 */     this.m_autoCompleteIndexManagers = a_autoCompleteIndexManagers;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 823 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_scheduleManager)
/*     */   {
/* 828 */     this.m_scheduleManager = a_scheduleManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 833 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   class KeywordField
/*     */     implements AutoCompleteUpdateManager.ACField
/*     */   {
/*     */     private String m_sFieldId;
/*     */ 
/*     */     KeywordField(String a_sFieldId)
/*     */     {
/* 756 */       this.m_sFieldId = a_sFieldId;
/*     */     }
/*     */ 
/*     */     public int countHits(String a_sLanguageCode, String a_sValue)
/*     */       throws Bn2Exception
/*     */     {
/* 762 */       SearchCriteria criteria = new SearchCriteria();
/*     */ 
/* 766 */       criteria.setKeywords(LuceneUtil.escape(a_sValue));
/*     */ 
/* 768 */       return AutoCompleteUpdateManager.this.countVisibleHits(criteria, a_sLanguageCode);
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o)
/*     */     {
/* 774 */       if (this == o) return true;
/* 775 */       if ((o == null) || (getClass() != o.getClass())) return false;
/*     */ 
/* 777 */       KeywordField that = (KeywordField)o;
/*     */ 
/* 779 */       return this.m_sFieldId.equals(that.m_sFieldId);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 785 */       return this.m_sFieldId.hashCode();
/*     */     }
/*     */ 
/*     */     public String getFieldId()
/*     */     {
/* 790 */       return this.m_sFieldId;
/*     */     }
/*     */ 
/*     */     public boolean isAutoCompleteOnForField()
/*     */     {
/* 795 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   class AttributeField
/*     */     implements AutoCompleteUpdateManager.ACField
/*     */   {
/*     */     private long m_attributeId;
/*     */     private boolean m_bAutoCompleteOnForField;
/*     */ 
/*     */     public AttributeField(long a_attributeId, boolean a_bAutoCompleteOnForField)
/*     */     {
/* 675 */       this.m_attributeId = a_attributeId;
/* 676 */       this.m_bAutoCompleteOnForField = a_bAutoCompleteOnForField;
/*     */     }
/*     */ 
/*     */     public int countHits(String a_sLanguageCode, String a_sValue) throws Bn2Exception
/*     */     {
/* 681 */       Attribute attribute = AutoCompleteUpdateManager.this.m_attributeManager.getAttribute(null, this.m_attributeId);
/*     */ 
/* 684 */       SearchCriteria criteria = new SearchCriteria();
/*     */ 
/* 686 */       if (attribute.isAutoComplete())
/*     */       {
/* 689 */         Vector attributeSearches = new Vector();
/*     */ 
/* 691 */         AttributeValue attVal = new AttributeValue();
/* 692 */         attVal.setAttribute(attribute);
/*     */ 
/* 696 */         attVal.setValue(LuceneUtil.escape(a_sValue));
/*     */ 
/* 698 */         attributeSearches.add(attVal);
/*     */ 
/* 701 */         criteria.setAttributeSearches(attributeSearches);
/*     */       }
/*     */       else
/*     */       {
/* 707 */         criteria.setKeywords(LuceneUtil.escape(a_sValue));
/*     */       }
/*     */ 
/* 711 */       criteria.setAttributeSearchesAreEscaped(true);
/*     */ 
/* 713 */       return AutoCompleteUpdateManager.this.countVisibleHits(criteria, a_sLanguageCode);
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o)
/*     */     {
/* 719 */       if (this == o) return true;
/* 720 */       if ((o == null) || (getClass() != o.getClass())) return false;
/*     */ 
/* 722 */       AttributeField that = (AttributeField)o;
/*     */ 
/* 724 */       return this.m_attributeId == that.m_attributeId;
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 731 */       return (int)this.m_attributeId;
/*     */     }
/*     */ 
/*     */     public String getFieldId()
/*     */     {
/* 736 */       return InternalACUtil.fieldIdFromAttributeId(this.m_attributeId);
/*     */     }
/*     */ 
/*     */     public long getAttributeId()
/*     */     {
/* 741 */       return this.m_attributeId;
/*     */     }
/*     */ 
/*     */     public boolean isAutoCompleteOnForField()
/*     */     {
/* 746 */       return this.m_bAutoCompleteOnForField;
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract interface ACField
/*     */   {
/*     */     public abstract String getFieldId();
/*     */ 
/*     */     public abstract boolean isAutoCompleteOnForField();
/*     */ 
/*     */     public abstract int countHits(String paramString1, String paramString2)
/*     */       throws Bn2Exception;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.AutoCompleteUpdateManager
 * JD-Core Version:    0.6.0
 */