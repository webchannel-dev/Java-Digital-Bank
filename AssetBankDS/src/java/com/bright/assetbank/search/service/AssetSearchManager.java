/*     */ package com.bright.assetbank.search.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.IndexableDocument;
/*     */ import com.bright.framework.search.bean.IterableIndexableDocument;
/*     */ import com.bright.framework.search.service.SingleIndexSearchManager;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.lucene.document.Document;
/*     */ 
/*     */ public class AssetSearchManager extends SingleIndexSearchManager<LightweightAsset>
/*     */ {
/*  62 */   private IAssetManager m_assetManager = null;
/*  63 */   protected Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */   private String m_sIndexName;
/*     */ 
/*     */   public AssetSearchManager(String a_sIndexName)
/*     */   {
/*  71 */     this.m_sIndexName = a_sIndexName;
/*     */   }
/*     */ 
/*     */   public String getIndexName()
/*     */   {
/*  76 */     return this.m_sIndexName;
/*     */   }
/*     */ 
/*     */   protected int getIdSortFieldType()
/*     */   {
/*  81 */     return 6;
/*     */   }
/*     */ 
/*     */   protected LightweightAsset getSearchResultFromDoc(Document a_doc)
/*     */   {
/*  96 */     LightweightAsset asset = null;
/*     */     try
/*     */     {
/* 100 */       asset = new LightweightAsset();
/* 101 */       asset.populateFromLuceneDocument(a_doc);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/* 105 */       this.m_logger.error("AssetSearchManager.getSearchResultFromDoc() : could not create asset from type id " + a_doc.get("f_typeid"), nfe);
/*     */     }
/*     */ 
/* 109 */     return asset;
/*     */   }
/*     */ 
/*     */   protected Vector<? extends IterableIndexableDocument> loadIndexableDocuments(long a_lStartId, int a_iBatchSize, Vector<Long> a_vecIds)
/*     */     throws Bn2Exception
/*     */   {
/* 123 */     return this.m_assetManager.getAssetsByIdAndBatchSize(null, a_vecIds, null, a_lStartId, a_iBatchSize, true, true, false);
/*     */   }
/*     */ 
/*     */   public void indexDocument(IndexableDocument a_doc, boolean a_bReindex)
/*     */     throws Bn2Exception
/*     */   {
/* 133 */     super.indexDocument(a_doc, a_bReindex);
/*     */   }
/*     */ 
/*     */   public void indexDocuments(DBTransaction a_dbTransaction, Vector a_vecDocs, boolean a_bReindex, boolean a_bQuick, boolean a_bOnlyUsageChanged)
/*     */     throws Bn2Exception
/*     */   {
/* 142 */     super.indexDocuments(a_vecDocs, a_bReindex, a_bQuick, a_bOnlyUsageChanged);
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 148 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 154 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.service.AssetSearchManager
 * JD-Core Version:    0.6.0
 */