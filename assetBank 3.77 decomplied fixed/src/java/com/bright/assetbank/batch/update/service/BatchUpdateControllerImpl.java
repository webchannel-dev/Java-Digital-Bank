/*     */ package com.bright.assetbank.batch.update.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.batch.update.bean.Batch;
/*     */ import com.bright.assetbank.batch.update.bean.BatchUpdateBatch;
/*     */ import com.bright.framework.workflow.bean.State;
/*     */ import com.bright.framework.workflow.bean.Workflow;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class BatchUpdateControllerImpl extends BatchUpdateController
/*     */ {
/*     */   public static final String k_sTypeUnsubmitted = "UNSUBMITTED";
/*     */   public static final String k_sTypeApproval = "APPROVAL";
/*     */   public static final String k_sTypeOwner = "OWNER";
/*     */   public static final String k_sTypeBatch = "BATCH";
/*  45 */   private boolean m_bIsApproval = false;
/*  46 */   private boolean m_bIsUnsubmitted = false;
/*  47 */   private boolean m_bIsOwner = false;
/*     */ 
/*  50 */   private Vector m_vecWorkflowTransitions = null;
/*  51 */   private State m_state = null;
/*  52 */   private Workflow m_workflow = null;
/*     */ 
/*     */   public String getType()
/*     */   {
/*  56 */     return "BATCHUPDATE";
/*     */   }
/*     */ 
/*     */   public boolean getShowAlertPanel()
/*     */   {
/*  69 */     return (!this.m_bIsApproval) && (!this.m_bIsUnsubmitted);
/*     */   }
/*     */ 
/*     */   public String getBatchType()
/*     */   {
/*  80 */     if (this.m_bIsUnsubmitted)
/*     */     {
/*  82 */       return "UNSUBMITTED";
/*     */     }
/*     */ 
/*  85 */     if (this.m_bIsApproval)
/*     */     {
/*  87 */       if (this.m_bIsOwner)
/*     */       {
/*  89 */         return "OWNER";
/*     */       }
/*     */ 
/*  92 */       return "APPROVAL";
/*     */     }
/*     */ 
/*  95 */     return "BATCH";
/*     */   }
/*     */ 
/*     */   BatchUpdateControllerImpl(UpdateManager a_updateManager)
/*     */   {
/* 107 */     this.m_updateManager = a_updateManager;
/*     */   }
/*     */ 
/*     */   public boolean startNewBatchUpdateWithoutLocks(Vector a_vecAssets, long a_lUserId)
/*     */   {
/* 121 */     return startNewBatchUpdate(a_vecAssets, a_lUserId, false);
/*     */   }
/*     */ 
/*     */   public boolean startNewBatchUpdate(Vector a_vecAssets, long a_lUserId)
/*     */   {
/* 132 */     return startNewBatchUpdate(a_vecAssets, a_lUserId, true);
/*     */   }
/*     */ 
/*     */   private boolean startNewBatchUpdate(Vector a_vecAssets, long a_lUserId, boolean a_bLockAssets)
/*     */   {
/* 147 */     if (this.m_currentUpdate != null)
/*     */     {
/* 149 */       this.m_updateManager.cancelBatchUpdate(this.m_currentUpdate);
/*     */     }
/*     */ 
/* 152 */     this.m_currentUpdate = this.m_updateManager.startNewBatchUpdate(a_vecAssets, a_lUserId, a_bLockAssets);
/*     */ 
/* 155 */     return this.m_currentUpdate.getExistLockedAssetsInBatch();
/*     */   }
/*     */ 
/*     */   public boolean addToBatchUpdate(Vector a_vecAssets)
/*     */     throws Bn2Exception
/*     */   {
/* 168 */     if (this.m_currentUpdate == null)
/*     */     {
/* 170 */       throw new Bn2Exception("BatchUpdateController.addToBatchUpdate: there is not current batch");
/*     */     }
/*     */ 
/* 174 */     return this.m_updateManager.addToBatchUpdate((BatchUpdateBatch)this.m_currentUpdate, a_vecAssets, true);
/*     */   }
/*     */ 
/*     */   public long getNextAsset()
/*     */     throws Bn2Exception
/*     */   {
/* 187 */     String k_sMethodName = "getNextAsset";
/*     */ 
/* 189 */     if (this.m_currentUpdate == null)
/*     */     {
/* 191 */       throw new Bn2Exception("BatchUpdateController.getNextAsset - the batch update has not been started");
/*     */     }
/*     */ 
/* 194 */     BatchUpdateBatch batch = (BatchUpdateBatch)this.m_currentUpdate;
/*     */ 
/* 197 */     long lCurrentAsset = batch.getCurrent();
/*     */ 
/* 199 */     if (lCurrentAsset != 0L)
/*     */     {
/* 202 */       this.m_updateManager.freeAsset(lCurrentAsset);
/*     */     }
/*     */ 
/* 206 */     return batch.getNext();
/*     */   }
/*     */ 
/*     */   public long getCurrentAsset()
/*     */     throws Bn2Exception
/*     */   {
/* 218 */     String k_sMethodName = "getCurrentAsset";
/*     */ 
/* 220 */     if (this.m_currentUpdate == null)
/*     */     {
/* 222 */       throw new Bn2Exception("BatchUpdateController.getCurrentAsset - the batch update has not been started");
/*     */     }
/*     */ 
/* 225 */     BatchUpdateBatch batch = (BatchUpdateBatch)this.m_currentUpdate;
/*     */ 
/* 228 */     return batch.getCurrent();
/*     */   }
/*     */ 
/*     */   public boolean getHasNext()
/*     */   {
/* 240 */     if (this.m_currentUpdate == null)
/*     */     {
/* 242 */       return false;
/*     */     }
/*     */ 
/* 245 */     BatchUpdateBatch batch = (BatchUpdateBatch)this.m_currentUpdate;
/*     */ 
/* 247 */     return batch.getHasNext();
/*     */   }
/*     */ 
/*     */   public int getNumberToGo()
/*     */   {
/* 259 */     if (this.m_currentUpdate == null)
/*     */     {
/* 261 */       return 0;
/*     */     }
/*     */ 
/* 264 */     BatchUpdateBatch batch = (BatchUpdateBatch)this.m_currentUpdate;
/*     */ 
/* 266 */     return batch.getNumberToGo();
/*     */   }
/*     */ 
/*     */   public boolean getIsApproval()
/*     */   {
/* 271 */     return this.m_bIsApproval;
/*     */   }
/*     */ 
/*     */   public void setApproval(boolean a_bIsApproval) {
/* 275 */     this.m_bIsApproval = a_bIsApproval;
/*     */   }
/*     */ 
/*     */   public boolean getIsUnsubmitted()
/*     */   {
/* 280 */     return this.m_bIsUnsubmitted;
/*     */   }
/*     */ 
/*     */   public void setUnsubmitted(boolean a_sIsUnsubmitted) {
/* 284 */     this.m_bIsUnsubmitted = a_sIsUnsubmitted;
/*     */   }
/*     */ 
/*     */   public boolean getIsOwner()
/*     */   {
/* 289 */     return this.m_bIsOwner;
/*     */   }
/*     */ 
/*     */   public void setOwner(boolean a_sIsOwner) {
/* 293 */     this.m_bIsOwner = a_sIsOwner;
/*     */   }
/*     */ 
/*     */   public Vector getWorkflowTransitions()
/*     */   {
/* 298 */     return this.m_vecWorkflowTransitions;
/*     */   }
/*     */ 
/*     */   public void setWorkflowTransitions(Vector a_vecWorkflowTransitions) {
/* 302 */     this.m_vecWorkflowTransitions = a_vecWorkflowTransitions;
/*     */   }
/*     */ 
/*     */   public State getState()
/*     */   {
/* 307 */     return this.m_state;
/*     */   }
/*     */ 
/*     */   public void setState(State a_sState) {
/* 311 */     this.m_state = a_sState;
/*     */   }
/*     */ 
/*     */   public void setWorkflow(Workflow a_workflow)
/*     */   {
/* 316 */     this.m_workflow = a_workflow;
/*     */   }
/*     */ 
/*     */   public Workflow getWorkflow()
/*     */   {
/* 321 */     return this.m_workflow;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl
 * JD-Core Version:    0.6.0
 */