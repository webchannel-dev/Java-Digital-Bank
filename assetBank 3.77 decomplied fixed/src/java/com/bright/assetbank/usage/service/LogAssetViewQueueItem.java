/*    */ package com.bright.assetbank.usage.service;
/*    */ 
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ 
/*    */ class LogAssetViewQueueItem extends QueuedItem
/*    */ {
/*    */   protected long m_lAssetId;
/*    */   protected long m_lUserId;
/*    */   protected long m_lLoginSessionId;
/*    */ 
/*    */   public LogAssetViewQueueItem(long assetId, long userId, long loginSessionId)
/*    */   {
/* 34 */     this.m_lAssetId = assetId;
/* 35 */     this.m_lUserId = userId;
/* 36 */     this.m_lLoginSessionId = loginSessionId;
/*    */   }
/*    */ 
/*    */   public long getAssetId()
/*    */   {
/* 41 */     return this.m_lAssetId;
/*    */   }
/*    */ 
/*    */   public long getLoginSessionId()
/*    */   {
/* 46 */     return this.m_lLoginSessionId;
/*    */   }
/*    */ 
/*    */   public long getUserId()
/*    */   {
/* 51 */     return this.m_lUserId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.service.LogAssetViewQueueItem
 * JD-Core Version:    0.6.0
 */