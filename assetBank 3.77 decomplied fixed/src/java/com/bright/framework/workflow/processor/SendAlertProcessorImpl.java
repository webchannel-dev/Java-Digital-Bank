/*     */ package com.bright.framework.workflow.processor;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.workflow.bean.AlertInfo;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract class SendAlertProcessorImpl extends Bn2Manager
/*     */   implements SendAlertProcessor
/*     */ {
/*  38 */   Map m_mapBatches = Collections.synchronizedMap(new HashMap());
/*     */ 
/*  41 */   protected EmailManager m_emailManager = null;
/*     */ 
/*  47 */   protected AssetWorkflowManager m_assetWorkflowManager = null;
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/*  44 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWorkflowManager)
/*     */   {
/*  50 */     this.m_assetWorkflowManager = a_assetWorkflowManager;
/*     */   }
/*     */ 
/*     */   public abstract void sendAlert(DBTransaction paramDBTransaction, AlertInfo paramAlertInfo)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public void startBatch(long a_lBatchId)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     this.m_mapBatches.put(new Long(a_lBatchId), new Vector());
/*     */   }
/*     */ 
/*     */   public void endBatch(long a_lBatchId)
/*     */   {
/*  89 */     this.m_mapBatches.remove(new Long(a_lBatchId));
/*     */   }
/*     */ 
/*     */   public void addToBatch(AlertInfo a_alert, long a_lBatchId)
/*     */     throws Bn2Exception
/*     */   {
/* 102 */     Long olKey = new Long(a_lBatchId);
/*     */ 
/* 105 */     if (!this.m_mapBatches.containsKey(olKey))
/*     */     {
/* 107 */       startBatch(a_lBatchId);
/*     */     }
/*     */ 
/* 111 */     ((Vector)this.m_mapBatches.get(olKey)).add(a_alert);
/*     */   }
/*     */ 
/*     */   public void sendBatch(DBTransaction a_dbTransaction, long a_lBatchId)
/*     */     throws Bn2Exception
/*     */   {
/* 125 */     Vector vecAlerts = (Vector)this.m_mapBatches.get(new Long(a_lBatchId));
/*     */ 
/* 127 */     if (vecAlerts != null)
/*     */     {
/* 129 */       sendBatchAlert(a_dbTransaction, vecAlerts);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract void sendBatchAlert(DBTransaction paramDBTransaction, Vector paramVector)
/*     */     throws Bn2Exception;
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.processor.SendAlertProcessorImpl
 * JD-Core Version:    0.6.0
 */