/*     */ package com.bright.framework.queue.service;
/*     */ 
/*     */ import com.bright.framework.queue.bean.MessageBatchMonitor;
/*     */ import com.bright.framework.queue.bean.MessageBatchMonitor.Status;
/*     */ import com.bright.framework.queue.bean.QueueThread;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class MessagingQueueManager extends QueueManager
/*     */ {
/*  36 */   protected MessageBatchMonitor m_batchMonitor = new MessageBatchMonitor();
/*     */ 
/*     */   public Vector<String> getMessages(long a_id)
/*     */   {
/*  50 */     Vector vMessages = this.m_batchMonitor.getMessages(a_id);
/*  51 */     if (vMessages != null)
/*     */     {
/*  53 */       vMessages = new Vector(vMessages);
/*     */     }
/*  55 */     return vMessages;
/*     */   }
/*     */ 
/*     */   public boolean isBatchInProgress(long a_id)
/*     */   {
/*  70 */     return this.m_batchMonitor.isBatchInProgress(a_id);
/*     */   }
/*     */ 
/*     */   public MessageBatchMonitor.Status getStatus(long a_id)
/*     */   {
/*  81 */     return this.m_batchMonitor.getStatus(a_id);
/*     */   }
/*     */ 
/*     */   public int getInProgressCount()
/*     */   {
/*  90 */     return this.m_batchMonitor.getInProgressCount();
/*     */   }
/*     */ 
/*     */   protected void addMessage(long a_id, String a_sMessage)
/*     */   {
/* 109 */     if (isThreadActive(Thread.currentThread()))
/*     */     {
/* 111 */       this.m_batchMonitor.addMessage(a_id, a_sMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clearMessages(long a_id)
/*     */   {
/* 118 */     this.m_batchMonitor.clearMessages(a_id);
/*     */   }
/*     */ 
/*     */   public void startBatchProcess(long a_id)
/*     */   {
/* 134 */     this.m_batchMonitor.startBatchProcess(a_id);
/*     */   }
/*     */ 
/*     */   protected void endBatchProcess(long a_id)
/*     */   {
/* 151 */     if (isThreadActive(Thread.currentThread()))
/*     */     {
/* 153 */       this.m_batchMonitor.endBatchProcess(a_id);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void failBatchProcess(long a_id)
/*     */   {
/* 171 */     if (isThreadActive(Thread.currentThread()))
/*     */     {
/* 173 */       this.m_batchMonitor.failBatchProcess(a_id);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void cancelJobs(long a_lJobId, boolean a_bImmediately)
/*     */   {
/* 185 */     synchronized (this.m_vecQueue)
/*     */     {
/* 188 */       for (int i = 0; (this.m_aQueueThread != null) && (i < this.m_aQueueThread.length); i++)
/*     */       {
/* 190 */         QueuedItem item = this.m_aQueueThread[i].getCurrentItem();
/*     */ 
/* 192 */         if ((item == null) || (item.getJobId() != a_lJobId))
/*     */           continue;
/* 194 */         this.m_logger.debug("MessagingQueueManager.cancelJobs: cancelling job " + a_lJobId);
/*     */ 
/* 197 */         this.m_aQueueThread[i].stopProcessing();
/*     */ 
/* 200 */         this.m_batchMonitor.endBatchProcess(a_lJobId);
/*     */ 
/* 202 */         if (!a_bImmediately)
/*     */           continue;
/* 204 */         this.m_aQueueThread[i] = new QueueThread(this);
/* 205 */         this.m_aQueueThread[i].start();
/*     */       }
/*     */ 
/* 211 */       Iterator itQueue = this.m_vecQueue.iterator();
/* 212 */       while (itQueue.hasNext())
/*     */       {
/* 214 */         QueuedItem item = (QueuedItem)itQueue.next();
/* 215 */         if (item.getJobId() == a_lJobId)
/*     */         {
/* 217 */           itQueue.remove();
/* 218 */           this.m_batchMonitor.endBatchProcess(a_lJobId);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getFreeServicingThreadCount()
/*     */   {
/* 237 */     return this.m_aQueueThread.length - this.m_batchMonitor.getInProgressCount();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.queue.service.MessagingQueueManager
 * JD-Core Version:    0.6.0
 */