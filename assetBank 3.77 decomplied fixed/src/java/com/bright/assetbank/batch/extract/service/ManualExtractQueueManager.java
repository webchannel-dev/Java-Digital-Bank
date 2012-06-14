/*     */ package com.bright.assetbank.batch.extract.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.batch.extract.bean.ManualExtractItem;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.queue.service.MessagingQueueManager;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ManualExtractQueueManager extends MessagingQueueManager
/*     */ {
/*  40 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*  46 */   private IAssetManager m_assetManager = null;
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_sAttributeManager)
/*     */   {
/*  43 */     this.m_attributeManager = a_sAttributeManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/*  49 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   private void extractMetadata(long a_lUserId, Vector a_vecAssetIds)
/*     */     throws Bn2Exception
/*     */   {
/*  55 */     int iSize = a_vecAssetIds.size();
/*     */ 
/*  57 */     if (a_lUserId > 0L)
/*     */     {
/*  59 */       addMessage(a_lUserId, "Starting job for extracting embedded metadata for " + iSize + " assets...");
/*     */     }
/*     */ 
/*  63 */     Vector allAttributes = this.m_assetManager.getAssetAttributes(null, null);
/*     */ 
/*  67 */     Iterator itAssets = a_vecAssetIds.iterator();
/*  68 */     while (itAssets.hasNext())
/*     */     {
/*  70 */       Long olId = (Long)itAssets.next();
/*     */ 
/*  72 */       if ((a_lUserId > 0L) && (iSize < 1000))
/*     */       {
/*  74 */         addMessage(a_lUserId, "Extracting embedded metadata for asset with Id: " + olId);
/*     */       }
/*     */ 
/*  77 */       Vector vecTempAttributes = AttributeUtil.copyAttributeVector(allAttributes);
/*     */       try
/*     */       {
/*  80 */         this.m_attributeManager.populateAssetFromEmbeddedDataMappings(a_lUserId, olId.longValue(), vecTempAttributes);
/*     */       }
/*     */       catch (AssetNotFoundException anfe)
/*     */       {
/*  84 */         addMessage(a_lUserId, "Could not embedded metadata for asset with Id: " + olId + " - asset does not exist");
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/*  89 */         this.m_logger.error("Error in ManualExtractQueueManager.extractMetadata: ", e);
/*  90 */         addMessage(a_lUserId, "ould not embedded metadata for asset with Id: " + olId + " : " + e.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  95 */     if (a_lUserId > 0L)
/*     */     {
/*  97 */       addMessage(a_lUserId, "Finished metadata extraction. Remember to reindex the assets.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void queueExtractJob(long a_lUserId, Vector a_vecAssets)
/*     */   {
/* 104 */     ManualExtractItem item = new ManualExtractItem();
/* 105 */     item.setUserId(a_lUserId);
/* 106 */     item.setAssetIds(a_vecAssets);
/* 107 */     queueItem(item);
/*     */   }
/*     */ 
/*     */   public void processQueueItem(QueuedItem a_item)
/*     */     throws Bn2Exception
/*     */   {
/* 114 */     ManualExtractItem item = (ManualExtractItem)a_item;
/*     */     try
/*     */     {
/* 118 */       startBatchProcess(item.getUserId());
/* 119 */       extractMetadata(item.getUserId(), item.getAssetIds());
/*     */     }
/*     */     catch (Throwable th)
/*     */     {
/* 123 */       this.m_logger.error("ManualExtractQueueManager: An error occurred during a manual re-extract", th);
/* 124 */       addMessage(item.getUserId(), "ManualExtractQueueManager: An error occurred: " + th.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/* 128 */       endBatchProcess(item.getUserId());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.extract.service.ManualExtractQueueManager
 * JD-Core Version:    0.6.0
 */