/*     */ package com.bright.assetbank.batch.update.bean;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class BatchUpdateBatch extends Batch
/*     */ {
/*     */   public static final String k_sTypeBatchUpdate = "BATCHUPDATE";
/*  32 */   private int m_iPosition = 0;
/*     */ 
/*     */   public synchronized long getNext()
/*     */   {
/*  40 */     long lId = 0L;
/*     */ 
/*  42 */     if ((this.m_vecImages != null) && (this.m_iPosition < this.m_vecImages.size()))
/*     */     {
/*  44 */       Long olId = (Long)this.m_vecImages.get(this.m_iPosition);
/*  45 */       lId = olId.longValue();
/*  46 */       this.m_iPosition += 1;
/*     */     }
/*     */ 
/*  49 */     return lId;
/*     */   }
/*     */ 
/*     */   public synchronized long getCurrent()
/*     */   {
/*  57 */     long lId = 0L;
/*     */ 
/*  60 */     if (this.m_iPosition > 0)
/*     */     {
/*  62 */       this.m_iPosition -= 1;
/*  63 */       lId = getNext();
/*     */     }
/*     */ 
/*  66 */     return lId;
/*     */   }
/*     */ 
/*     */   public synchronized boolean getHasNext()
/*     */   {
/*  74 */     boolean bRetVal = false;
/*     */ 
/*  76 */     if ((this.m_vecImages != null) && (this.m_iPosition < this.m_vecImages.size()))
/*     */     {
/*  78 */       bRetVal = true;
/*     */     }
/*     */ 
/*  81 */     return bRetVal;
/*     */   }
/*     */ 
/*     */   public synchronized int getNumberToGo()
/*     */   {
/*  89 */     int iToGo = 0;
/*     */ 
/*  91 */     if (this.m_vecImages != null)
/*     */     {
/*  93 */       iToGo = this.m_vecImages.size() - this.m_iPosition;
/*     */     }
/*     */ 
/*  96 */     return iToGo;
/*     */   }
/*     */ 
/*     */   public void setPosition(int a_iPosition)
/*     */   {
/* 101 */     this.m_iPosition = a_iPosition;
/*     */   }
/*     */ 
/*     */   public int getPosition()
/*     */   {
/* 106 */     return this.m_iPosition;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 112 */     return "BATCHUPDATE";
/*     */   }
/*     */ 
/*     */   public long getIdFromBatchObject(Object a_obj)
/*     */   {
/* 119 */     Long ol = (Long)a_obj;
/* 120 */     return ol.longValue();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.bean.BatchUpdateBatch
 * JD-Core Version:    0.6.0
 */