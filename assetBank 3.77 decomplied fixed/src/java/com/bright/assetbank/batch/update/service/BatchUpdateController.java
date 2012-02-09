/*     */ package com.bright.assetbank.batch.update.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.batch.update.bean.Batch;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract class BatchUpdateController
/*     */ {
/*  37 */   public final String k_sClassName = "BatchUpdateController";
/*     */ 
/*  39 */   protected UpdateManager m_updateManager = null;
/*  40 */   protected Batch m_currentUpdate = null;
/*  41 */   private int m_iNumberMatchingSearch = 0;
/*  42 */   private String m_sFinishedUrl = null;
/*  43 */   private String m_sCancelUrl = null;
/*  44 */   private boolean m_bDelete = false;
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*     */     try
/*     */     {
/*  59 */       cancelCurrentBatchUpdate();
/*     */     }
/*     */     finally
/*     */     {
/*  63 */       super.finalize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract String getType();
/*     */ 
/*     */   public abstract boolean getShowAlertPanel();
/*     */ 
/*     */   public abstract String getBatchType();
/*     */ 
/*     */   public abstract boolean startNewBatchUpdate(Vector paramVector, long paramLong);
/*     */ 
/*     */   public abstract boolean addToBatchUpdate(Vector paramVector)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public void cancelCurrentBatchUpdate()
/*     */   {
/* 118 */     if (this.m_currentUpdate != null)
/*     */     {
/* 120 */       this.m_updateManager.cancelBatchUpdate(this.m_currentUpdate);
/* 121 */       this.m_currentUpdate = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getNumberInBatch()
/*     */   {
/* 139 */     if (this.m_currentUpdate == null)
/*     */     {
/* 141 */       return 0;
/*     */     }
/*     */ 
/* 144 */     return this.m_currentUpdate.getImages().size();
/*     */   }
/*     */ 
/*     */   public int getNumberMatchingSearch()
/*     */   {
/* 150 */     return this.m_iNumberMatchingSearch;
/*     */   }
/*     */ 
/*     */   public void setNumberMatchingSearch(int a_iNumberMatchingSearch)
/*     */   {
/* 155 */     this.m_iNumberMatchingSearch = a_iNumberMatchingSearch;
/*     */   }
/*     */ 
/*     */   public String getCancelUrl()
/*     */   {
/* 160 */     return this.m_sCancelUrl;
/*     */   }
/*     */ 
/*     */   public void setCancelUrl(String a_sCancelUrl)
/*     */   {
/* 165 */     this.m_sCancelUrl = a_sCancelUrl;
/*     */   }
/*     */ 
/*     */   public String getFinishedUrl()
/*     */   {
/* 170 */     return this.m_sFinishedUrl;
/*     */   }
/*     */ 
/*     */   public void setFinishedUrl(String a_sFinishedUrl)
/*     */   {
/* 175 */     this.m_sFinishedUrl = a_sFinishedUrl;
/*     */   }
/*     */ 
/*     */   public Batch getBatchUpdate()
/*     */   {
/* 182 */     return this.m_currentUpdate;
/*     */   }
/*     */ 
/*     */   public boolean getDelete()
/*     */   {
/* 187 */     return this.m_bDelete;
/*     */   }
/*     */ 
/*     */   public void setDelete(boolean a_bDelete) {
/* 191 */     this.m_bDelete = a_bDelete;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.service.BatchUpdateController
 * JD-Core Version:    0.6.0
 */