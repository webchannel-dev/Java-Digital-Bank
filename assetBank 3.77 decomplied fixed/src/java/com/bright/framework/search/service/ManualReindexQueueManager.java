/*    */ package com.bright.framework.search.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import com.bright.framework.queue.service.MessagingQueueManager;
/*    */ import com.bright.framework.search.bean.ManualIndexItem;
/*    */ import java.util.Vector;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public abstract class ManualReindexQueueManager extends MessagingQueueManager
/*    */ {
/* 37 */   protected CategoryCountCacheManager m_catCountManager = null;
/*    */ 
/*    */   public abstract void rebuildIndex(boolean paramBoolean, long paramLong, Vector<Long> paramVector) throws Bn2Exception;
/*    */ 
/* 40 */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_sManager) { this.m_catCountManager = a_sManager;
/*    */   }
/*    */ 
/*    */   public void queueRebuildIndex(boolean a_bQuick, long a_lUserId)
/*    */   {
/* 45 */     queueRebuildIndex(a_bQuick, a_lUserId, null);
/*    */   }
/*    */ 
/*    */   public void queueRebuildIndex(boolean a_bQuick, long a_lUserId, Vector<Long> a_lIdsToReindex)
/*    */   {
/* 54 */     ManualIndexItem item = new ManualIndexItem();
/* 55 */     item.setIsQuickIndex(a_bQuick);
/* 56 */     item.setUserId(a_lUserId);
/* 57 */     item.setIdsToReindex(a_lIdsToReindex);
/* 58 */     queueItem(item);
/*    */   }
/*    */ 
/*    */   public void processQueueItem(QueuedItem a_queuedItem)
/*    */     throws Bn2Exception
/*    */   {
/* 68 */     ManualIndexItem item = (ManualIndexItem)a_queuedItem;
/*    */     try
/*    */     {
/* 72 */       startBatchProcess(item.getUserId());
/* 73 */       rebuildIndex(item.getIsQuickIndex(), item.getUserId(), item.getIdsToReindex());
/*    */ 
/* 76 */       this.m_catCountManager.rebuildCacheNow();
/*    */     }
/*    */     catch (Throwable th)
/*    */     {
/* 80 */       this.m_logger.error("An error occurred during a manual reindex", th);
/* 81 */       addMessage(item.getUserId(), "An error occurred: " + th.getMessage());
/*    */     }
/*    */     finally
/*    */     {
/* 85 */       endBatchProcess(item.getUserId());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.service.ManualReindexQueueManager
 * JD-Core Version:    0.6.0
 */