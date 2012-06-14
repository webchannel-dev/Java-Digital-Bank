/*     */ package com.bright.assetbank.batch.update.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.batch.update.bean.Batch;
/*     */ import com.bright.assetbank.batch.update.bean.BulkUpdateBatch;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class BulkUpdateControllerImpl extends BatchUpdateController
/*     */ {
/*     */   private int m_numberUpdatePermissionDenied;
/*     */ 
/*     */   public int getNumberUpdatePermissionDenied()
/*     */   {
/*  38 */     return this.m_numberUpdatePermissionDenied;
/*     */   }
/*     */ 
/*     */   public void setNumberUpdatePermissionDenied(int a_numberUpdatePermissionDenied)
/*     */   {
/*  43 */     this.m_numberUpdatePermissionDenied = a_numberUpdatePermissionDenied;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  48 */     return "BULKUPDATE";
/*     */   }
/*     */ 
/*     */   public String getBatchType()
/*     */   {
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean getShowAlertPanel()
/*     */   {
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */   BulkUpdateControllerImpl(UpdateManager a_updateManager)
/*     */   {
/*  77 */     this.m_updateManager = a_updateManager;
/*     */   }
/*     */ 
/*     */   public boolean startNewBatchUpdate(Vector a_vecAssets, long a_lUserId)
/*     */   {
/*  90 */     if (this.m_currentUpdate != null)
/*     */     {
/*  92 */       this.m_updateManager.cancelBatchUpdate(this.m_currentUpdate);
/*     */     }
/*     */ 
/*  96 */     this.m_currentUpdate = this.m_updateManager.startNewBulkUpdate(a_vecAssets, a_lUserId);
/*     */ 
/*  99 */     boolean bLockedAssets = this.m_currentUpdate.getExistLockedAssetsInBatch();
/* 100 */     if (bLockedAssets)
/*     */     {
/* 102 */       this.m_updateManager.cancelBatchUpdate(this.m_currentUpdate);
/*     */     }
/*     */ 
/* 105 */     return bLockedAssets;
/*     */   }
/*     */ 
/*     */   public boolean addToBatchUpdate(Vector a_vecAssets)
/*     */     throws Bn2Exception
/*     */   {
/* 117 */     if (this.m_currentUpdate == null)
/*     */     {
/* 119 */       throw new Bn2Exception("BatchUpdateController.addToBatchUpdate: there is not current batch");
/*     */     }
/*     */ 
/* 123 */     boolean bLockedAssets = this.m_updateManager.addToBulkUpdate((BulkUpdateBatch)this.m_currentUpdate, a_vecAssets);
/* 124 */     if (bLockedAssets)
/*     */     {
/* 126 */       this.m_updateManager.cancelBatchUpdate(this.m_currentUpdate);
/*     */     }
/*     */ 
/* 129 */     return bLockedAssets;
/*     */   }
/*     */ 
/*     */   public long getNumberAlreadyUpdated()
/*     */   {
/* 139 */     BulkUpdateBatch batch = (BulkUpdateBatch)this.m_currentUpdate;
/* 140 */     if (batch == null)
/*     */     {
/* 142 */       return 0L;
/*     */     }
/*     */ 
/* 145 */     long lSize = batch.getAssetsAlreadyUpdated().size();
/*     */ 
/* 147 */     return lSize;
/*     */   }
/*     */ 
/*     */   public long getNumberSelectedForUpdate()
/*     */   {
/* 157 */     BulkUpdateBatch batch = (BulkUpdateBatch)this.m_currentUpdate;
/* 158 */     if (batch == null)
/*     */     {
/* 160 */       return 0L;
/*     */     }
/*     */ 
/* 163 */     long lSize = batch.getAssetsToUpdate().size();
/*     */ 
/* 165 */     return lSize;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.service.BulkUpdateControllerImpl
 * JD-Core Version:    0.6.0
 */