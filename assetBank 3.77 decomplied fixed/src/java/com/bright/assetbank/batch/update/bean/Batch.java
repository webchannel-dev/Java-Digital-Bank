/*    */ package com.bright.assetbank.batch.update.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public abstract class Batch
/*    */ {
/* 32 */   protected Vector m_vecImages = null;
/* 33 */   private long m_lUserId = 0L;
/* 34 */   private int m_iNumLockedAssets = 0;
/*    */ 
/*    */   public abstract String getType();
/*    */ 
/*    */   public abstract long getIdFromBatchObject(Object paramObject);
/*    */ 
/*    */   public Vector getImages()
/*    */   {
/* 55 */     return this.m_vecImages;
/*    */   }
/*    */ 
/*    */   public void setImages(Vector a_vecImages)
/*    */   {
/* 60 */     this.m_vecImages = a_vecImages;
/*    */   }
/*    */ 
/*    */   public long getUserId()
/*    */   {
/* 65 */     return this.m_lUserId;
/*    */   }
/*    */ 
/*    */   public void setUserId(long a_sUserId) {
/* 69 */     this.m_lUserId = a_sUserId;
/*    */   }
/*    */ 
/*    */   public int getNumLockedAssetsInBatch()
/*    */   {
/* 74 */     return this.m_iNumLockedAssets;
/*    */   }
/*    */ 
/*    */   public void setNumLockedAssetsInBatch(int a_iNumLockedAssets)
/*    */   {
/* 79 */     this.m_iNumLockedAssets = a_iNumLockedAssets;
/*    */   }
/*    */ 
/*    */   public boolean getExistLockedAssetsInBatch()
/*    */   {
/* 84 */     return this.m_iNumLockedAssets > 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.bean.Batch
 * JD-Core Version:    0.6.0
 */