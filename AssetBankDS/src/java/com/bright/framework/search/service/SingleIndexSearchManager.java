/*     */ package com.bright.framework.search.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.CreateLuceneDocumentFromAssetParameters;
/*     */ import com.bright.assetbank.externalfilter.service.ExternalFilterManager;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.search.bean.FilterArrayIterator;
/*     */ import com.bright.framework.search.bean.IndexableDocument;
/*     */ import com.bright.framework.search.bean.IterableIndexableDocument;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResult;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.search.constant.SearchConstants;
/*     */ import com.bright.framework.search.lucene.BTopDocs;
/*     */ import com.bright.framework.search.lucene.SearchFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.misc.ChainedFilter;
/*     */ import org.apache.lucene.search.Filter;
/*     */ import org.apache.lucene.search.ScoreDoc;
/*     */ import org.apache.lucene.search.SortField;
/*     */ import org.apache.lucene.search.TopDocs;
/*     */ 
/*     */ public abstract class SingleIndexSearchManager<T extends SearchResult> extends ManualReindexQueueManager
/*     */   implements SearchConstants, Runnable
/*     */ {
/*     */   public static final String c_ksClassName = "SingleIndexSearchManager";
/*  90 */   private IndexManager m_indexManager = null;
/*  91 */   private ScheduleManager m_scheduleManager = null;
/*     */ 
/*  93 */   private Language m_indexLanguage = LanguageConstants.k_defaultLanguage;
/*     */   private SortFieldSpecifier m_sortFieldSpecifier;
/*  96 */   private ExternalFilterManager m_externalFilterManager = null;
/*     */ 
/*     */   protected abstract Vector<? extends IterableIndexableDocument> loadIndexableDocuments(long paramLong, int paramInt, Vector<Long> paramVector)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   protected abstract T getSearchResultFromDoc(Document paramDocument);
/*     */ 
/*     */   protected abstract String getIndexName();
/*     */ 
/*     */   protected int getIdSortFieldType()
/*     */   {
/* 123 */     return 3;
/*     */   }
/*     */ 
/*     */   public void initialiseIndex()
/*     */     throws Bn2Exception
/*     */   {
/* 135 */     if (!this.m_indexManager.indexExists())
/*     */     {
/* 140 */       this.m_logger.warn("Initialising index for the first time");
/* 141 */       rebuildIndex(false);
/* 142 */       this.m_indexManager.optimizeIndex();
/*     */     }
/*     */     else
/*     */     {
/* 147 */       this.m_indexManager.releaseLockFileIfExists();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteIndex()
/*     */   {
/*     */     try
/*     */     {
/* 158 */       this.m_indexManager.deleteIndex();
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 162 */       this.m_logger.error("deleteIndex() failed", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public SearchResults<T> search(SearchQuery a_searchQuery, int a_iStartIndex, int a_iPageSize)
/*     */     throws Bn2Exception
/*     */   {
/* 193 */     boolean bNoLimit = (a_iStartIndex < 0) || (a_iPageSize < 0);
/* 194 */     int iEndIndex = Math.max(a_iStartIndex, 0) + a_iPageSize - 1;
/*     */ 
/* 197 */     String sQuery = a_searchQuery.getLuceneQuery();
/*     */ 
/* 200 */     Collection filterArray = null;
/*     */ 
/* 203 */     if ((a_searchQuery.getExternalFilterCriteria() != null) && (!a_searchQuery.getExternalFilterCriteria().isEmpty()))
/*     */     {
/* 206 */       filterArray = this.m_externalFilterManager.externalSearch(a_searchQuery);
/*     */     }
/*     */ 
/* 212 */     SortField[] aSortFields = null;
/* 213 */     if (a_searchQuery.getSortFields() != null)
/*     */     {
/* 215 */       aSortFields = new SortField[a_searchQuery.getSortFields().length + 1];
/* 216 */       System.arraycopy(a_searchQuery.getSortFields(), 0, aSortFields, 0, a_searchQuery.getSortFields().length);
/*     */ 
/* 220 */       aSortFields[(aSortFields.length - 1)] = new SortField("f_id_sort", getIdSortFieldType());
/*     */     }
/*     */ 
/* 224 */     SearchResults results = new SearchResults();
/* 225 */     results.setPageIndex(a_iStartIndex >= 0 ? a_iStartIndex / a_iPageSize : -1);
/* 226 */     results.setPageSize(a_iPageSize);
/*     */ 
/* 229 */     long lStartTime = System.currentTimeMillis();
/*     */ 
/* 231 */     if ((sQuery != null) && (sQuery.trim().length() > 0))
/*     */     {
/* 233 */       BTopDocs bhits = null;
/* 234 */       Filter luceneFilter = null;
/*     */ 
/* 237 */       if (a_searchQuery.getSearchFilters() != null)
/*     */       {
/* 239 */         List filters = new ArrayList();
/*     */ 
/* 241 */         for (int i = 0; i < a_searchQuery.getSearchFilters().length; i++)
/*     */         {
/* 243 */           Filter filter = a_searchQuery.getSearchFilters()[i].getAsLuceneFilter();
/* 244 */           if (filter == null)
/*     */             continue;
/* 246 */           filters.add(filter);
/*     */         }
/*     */ 
/* 250 */         if (!filters.isEmpty())
/*     */         {
/* 252 */           luceneFilter = new ChainedFilter((Filter[])(Filter[])filters.toArray(new Filter[filters.size()]), 1);
/*     */         }
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 258 */         if (filterArray != null)
/*     */         {
/* 264 */           FilterArrayIterator itExternalFilter = new FilterArrayIterator(filterArray);
/* 265 */           int iHitIndex = 0;
/*     */ 
/* 267 */           while (itExternalFilter.getHasMoreBatches())
/*     */           {
/* 270 */             String sFilter = itExternalFilter.getNextBatchQuery();
/* 271 */             String sFilteredQuery = "(" + sQuery + ") AND (" + sFilter + ")";
/*     */ 
/* 274 */             bhits = this.m_indexManager.getDocuments(sFilteredQuery, aSortFields, a_searchQuery.getDefaultOperator(), luceneFilter, a_searchQuery.getMaxNoOfResults());
/*     */ 
/* 282 */             if (bhits != null)
/*     */             {
/* 284 */               iHitIndex = buildSearchResults(bhits, results, a_searchQuery.getMaxNoOfResults(), bNoLimit, a_iStartIndex, iEndIndex, iHitIndex);
/*     */ 
/* 287 */               if ((a_searchQuery.getMaxNoOfResults() > 0) && (iHitIndex >= a_searchQuery.getMaxNoOfResults()))
/*     */               {
/* 289 */                 results.setMaxResultsExceeded(true);
/* 290 */                 break;
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 295 */               this.m_logger.debug("SingleIndexSearchManager.search: Search returned no results, probably due to parse error.");
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 300 */           results.setNumResults(iHitIndex);
/*     */         }
/*     */         else
/*     */         {
/* 306 */           int iMaxDocuments = iEndIndex >= 0 ? Math.min(iEndIndex + 1, a_searchQuery.getMaxNoOfResults()) : a_searchQuery.getMaxNoOfResults();
/*     */ 
/* 309 */           bhits = this.m_indexManager.getDocuments(sQuery, aSortFields, a_searchQuery.getDefaultOperator(), luceneFilter, iMaxDocuments);
/*     */ 
/* 317 */           if (bhits != null)
/*     */           {
/* 319 */             buildSearchResults(bhits, results, a_searchQuery.getMaxNoOfResults(), bNoLimit, a_iStartIndex, iEndIndex, 0);
/*     */ 
/* 322 */             results.setMaxResultsExceeded(bhits.getHits().totalHits > a_searchQuery.getMaxNoOfResults());
/*     */ 
/* 325 */             if (a_searchQuery.getMaxNoOfResults() > 0)
/*     */             {
/* 328 */               results.setNumResults(Math.min(a_searchQuery.getMaxNoOfResults(), bhits.getHits().totalHits));
/*     */             }
/*     */             else
/*     */             {
/* 333 */               results.setNumResults(bhits.getHits().totalHits);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 338 */             this.m_logger.debug("Search returned no results, probably due to parse error.");
/*     */           }
/*     */         }
/*     */ 
/* 342 */         if ((bhits != null) && (bhits.getHits() != null))
/*     */         {
/* 344 */           results.setTotalHits(bhits.getHits().totalHits);
/*     */         }
/*     */         else
/*     */         {
/* 348 */           results.setTotalHits(0);
/*     */         }
/*     */ 
/* 352 */         this.m_logger.debug("Post-search retrieve & filter ran in " + (System.currentTimeMillis() - lStartTime) + "ms");
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/* 357 */         throw new Bn2Exception("Exception in DocumentManager.getDocuments: ", ioe);
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 361 */         throw new Bn2Exception("NumberFormatException in SingleIndexSearchManager. This is most likely because a sort field is set to 'Numeric' instead of 'Decimal'. Please advise an administrator of this error.", nfe);
/*     */       }
/*     */       finally
/*     */       {
/* 366 */         if (bhits != null)
/*     */         {
/*     */           try
/*     */           {
/* 370 */             bhits.close();
/*     */           }
/*     */           catch (IOException e)
/*     */           {
/* 374 */             this.m_logger.error("Exception closing BHits", e);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 380 */     return results;
/*     */   }
/*     */ 
/*     */   private int buildSearchResults(BTopDocs a_bhits, SearchResults<T> a_results, int a_iMaxResults, boolean a_bNoLimit, int a_iStartIndex, int a_iEndIndex, int a_iHitIndex)
/*     */     throws IOException
/*     */   {
/* 409 */     TopDocs hits = a_bhits.getHits();
/*     */ 
/* 412 */     for (int i = 0; i < hits.scoreDocs.length; i++)
/*     */     {
/* 415 */       if ((a_bNoLimit) || ((a_iHitIndex >= a_iStartIndex) && (a_iHitIndex <= a_iEndIndex)))
/*     */       {
/* 418 */         Document doc = a_bhits.getDocumentByHitIndex(i);
/*     */ 
/* 420 */         SearchResult result = getSearchResultFromDoc(doc);
/*     */ 
/* 423 */         result.setScore(hits.scoreDocs[i].score);
/*     */ 
/* 426 */         result.setPosition(a_iHitIndex);
/*     */ 
/* 428 */         a_results.getSearchResults().add((T)result);
/*     */       }
/*     */ 
/* 431 */       a_iHitIndex++;
/*     */ 
/* 434 */       if ((a_iMaxResults > 0) && (a_iHitIndex >= a_iMaxResults))
/*     */       {
/*     */         break;
/*     */       }
/*     */     }
/*     */ 
/* 440 */     return a_iHitIndex;
/*     */   }
/*     */ 
/*     */   public int getHitCount(SearchQuery a_searchQuery)
/*     */     throws Bn2Exception
/*     */   {
/* 453 */     int iOldMax = a_searchQuery.getMaxNoOfResults();
/* 454 */     a_searchQuery.setMaxNoOfResults(0);
/*     */ 
/* 456 */     SearchResults results = search(a_searchQuery, 0, 1);
/*     */ 
/* 459 */     a_searchQuery.setMaxNoOfResults(iOldMax);
/*     */ 
/* 461 */     return results.getTotalHits();
/*     */   }
/*     */ 
/*     */   public void clearIndex()
/*     */     throws Bn2Exception
/*     */   {
/* 470 */     this.m_indexManager.deleteAllDocuments();
/* 471 */     this.m_externalFilterManager.clearIndex();
/*     */   }
/*     */ 
/*     */   public void rebuildIndex(boolean a_bQuick) throws Bn2Exception
/*     */   {
/* 476 */     rebuildIndex(a_bQuick, -1L, null);
/*     */   }
/*     */ 
/*     */   public void rebuildIndex(boolean a_bQuick, long a_lUserId, Vector<Long> a_vecIds)
/*     */     throws Bn2Exception
/*     */   {
/* 487 */     rebuildIndex(a_bQuick, a_lUserId, true, a_vecIds);
/*     */   }
/*     */ 
/*     */   private void rebuildIndex(boolean a_bQuick, long a_lUserId, boolean a_bUseStemming, Vector<Long> a_vecIds)
/*     */     throws Bn2Exception
/*     */   {
/* 512 */     int iNumDocumentsAtATime = FrameworkSettings.getIndexAllBatchSize();
/* 513 */     long iStartId = 1L;
/* 514 */     int iTotalIndexed = 0;
/*     */ 
/* 518 */     if ((a_vecIds != null) && (a_vecIds.size() > 0))
/*     */     {
/* 520 */       a_bQuick = true;
/*     */     }
/*     */ 
/* 523 */     if (a_lUserId > 0L)
/*     */     {
/* 525 */       addMessage(a_lUserId, "Starting asset reindex");
/*     */     }
/*     */ 
/* 528 */     this.m_logger.debug("Starting reindex of index: " + getIndexName() + " at " + new Date());
/*     */ 
/* 531 */     if (!a_bQuick)
/*     */     {
/* 533 */       clearIndex();
/*     */     }
/*     */     Vector vIndexableDocuments;
/*     */     do
/*     */     {
/* 539 */       vIndexableDocuments = loadIndexableDocuments(iStartId, iNumDocumentsAtATime, a_vecIds);
/*     */ 
/* 542 */       if (vIndexableDocuments != null)
/*     */       {
/* 545 */         int iSize = vIndexableDocuments.size();
/* 546 */         if (iSize > 0)
/*     */         {
/* 549 */           long iMaxId = ((IterableIndexableDocument)vIndexableDocuments.get(iSize - 1)).getId();
/* 550 */           iStartId = iMaxId + 1L;
/*     */         }
/*     */ 
/* 554 */         indexDocuments(vIndexableDocuments, false, a_bQuick, false);
/*     */ 
/* 556 */         if (a_lUserId > 0L)
/*     */         {
/* 558 */           addMessage(a_lUserId, iSize + " assets indexed");
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 563 */         this.m_logger.debug("No indexable documents found while rebuilding index: " + getIndexName());
/*     */       }
/*     */     }
/* 566 */     while ((vIndexableDocuments != null) && (vIndexableDocuments.size() >= iNumDocumentsAtATime));
/*     */ 
/* 568 */     if (a_lUserId > 0L)
/*     */     {
/* 570 */       addMessage(a_lUserId, "Reindexed " + iTotalIndexed + " assets in total");
/* 571 */       addMessage(a_lUserId, "Finished asset reindex");
/*     */     }
/*     */ 
/* 574 */     this.m_logger.debug("Finished reindexing index: " + getIndexName() + " at " + new Date());
/*     */   }
/*     */ 
/*     */   public void optimiseIndex()
/*     */     throws Bn2Exception
/*     */   {
/* 583 */     this.m_indexManager.optimizeIndex();
/*     */   }
/*     */ 
/*     */   public void indexDocument(IndexableDocument a_doc, boolean a_bReindex)
/*     */     throws Bn2Exception
/*     */   {
/* 594 */     Vector vDoc = new Vector(1);
/* 595 */     vDoc.add(a_doc);
/* 596 */     this.m_indexManager.indexDocuments(vDoc, getCreateLuceneDocumentFromAssetParameters());
/*     */ 
/* 598 */     this.m_externalFilterManager.indexDocument(a_doc, a_bReindex);
/*     */   }
/*     */ 
/*     */   public void indexDocuments(Vector a_vecDocs, boolean a_bReindex, boolean a_bQuick, boolean a_bOnlyUsageChanged)
/*     */     throws Bn2Exception
/*     */   {
/* 622 */     this.m_indexManager.indexDocuments(a_vecDocs, getCreateLuceneDocumentFromAssetParameters());
/*     */ 
/* 624 */     this.m_externalFilterManager.indexDocuments(a_vecDocs, a_bReindex, a_bQuick, a_bOnlyUsageChanged);
/*     */   }
/*     */ 
/*     */   private CreateLuceneDocumentFromAssetParameters getCreateLuceneDocumentFromAssetParameters()
/*     */     throws Bn2Exception
/*     */   {
/* 631 */     String[] asSortFieldNames = null;
/*     */ 
/* 633 */     if (this.m_sortFieldSpecifier != null)
/*     */     {
/* 635 */       asSortFieldNames = this.m_sortFieldSpecifier.getSortFieldsNames();
/*     */     }
/*     */ 
/* 638 */     return new CreateLuceneDocumentFromAssetParameters(asSortFieldNames, this.m_indexLanguage);
/*     */   }
/*     */ 
/*     */   public void removeDocument(IterableIndexableDocument a_doc)
/*     */     throws Bn2Exception
/*     */   {
/* 655 */     this.m_indexManager.deleteDocument(a_doc.getIndexableDocId());
/*     */ 
/* 657 */     this.m_externalFilterManager.removeDocument(a_doc);
/*     */   }
/*     */ 
/*     */   public void rebuildIndexAsynchronously()
/*     */   {
/* 670 */     Thread reindexThread = new Thread(this);
/*     */ 
/* 672 */     reindexThread.start();
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/* 687 */       rebuildIndex(false);
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 691 */       this.m_logger.error("Exception caught whilst reindexing asynchronously in " + getClass().getName() + " : " + bn2e);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected boolean isSearchResultVisible(SearchQuery a_searchCriteria, SearchResult a_result)
/*     */   {
/* 713 */     return true;
/*     */   }
/*     */ 
/*     */   public Language getIndexLanguage()
/*     */   {
/* 718 */     return this.m_indexLanguage;
/*     */   }
/*     */ 
/*     */   public void setIndexLanguage(Language indexLanguage)
/*     */   {
/* 723 */     this.m_indexLanguage = indexLanguage;
/*     */   }
/*     */ 
/*     */   public SortFieldSpecifier getSortFieldSpecifier()
/*     */   {
/* 728 */     return this.m_sortFieldSpecifier;
/*     */   }
/*     */ 
/*     */   public void setSortFieldSpecifier(SortFieldSpecifier a_sortFieldSpecifier)
/*     */   {
/* 733 */     this.m_sortFieldSpecifier = a_sortFieldSpecifier;
/*     */   }
/*     */ 
/*     */   public void setIndexManager(IndexManager a_indexManager)
/*     */   {
/* 749 */     this.m_indexManager = a_indexManager;
/*     */   }
/*     */ 
/*     */   public ScheduleManager getScheduleManager()
/*     */   {
/* 755 */     return this.m_scheduleManager;
/*     */   }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*     */   {
/* 761 */     this.m_scheduleManager = a_sScheduleManager;
/*     */   }
/*     */ 
/*     */   public void setExternalFilterManager(ExternalFilterManager aManager)
/*     */   {
/* 766 */     this.m_externalFilterManager = aManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.service.SingleIndexSearchManager
 * JD-Core Version:    0.6.0
 */