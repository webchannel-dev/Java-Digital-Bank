/*    */ package com.bright.assetbank.batch.extract.bean;
/*    */ 
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ManualExtractItem extends QueuedItem
/*    */ {
/* 31 */   private long m_lUserId = -1L;
/* 32 */   private Vector m_vecAssetIds = null;
/*    */ 
/*    */   public void setUserId(long a_lUserId)
/*    */   {
/* 36 */     this.m_lUserId = a_lUserId;
/*    */   }
/*    */ 
/*    */   public long getUserId()
/*    */   {
/* 41 */     return this.m_lUserId;
/*    */   }
/*    */ 
/*    */   public Vector getAssetIds()
/*    */   {
/* 46 */     return this.m_vecAssetIds;
/*    */   }
/*    */ 
/*    */   public void setAssetIds(Vector a_sVecAssetIds)
/*    */   {
/* 51 */     this.m_vecAssetIds = a_sVecAssetIds;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.extract.bean.ManualExtractItem
 * JD-Core Version:    0.6.0
 */