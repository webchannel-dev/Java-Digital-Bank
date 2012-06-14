/*     */ package com.bright.framework.queue.bean;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.queue.service.QueueManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class QueueThread extends Thread
/*     */ {
/*  31 */   private static int c_threadCounter = 0;
/*     */ 
/*  33 */   private QueueManager m_queueManager = null;
/*  34 */   private QueuedItem m_currentItem = null;
/*  35 */   private boolean m_bFinished = false;
/*  36 */   private volatile boolean m_bSuspended = false;
/*     */ 
/*     */   public QueueThread(QueueManager a_queueManager)
/*     */   {
/*  44 */     super("QueueThread " + ++c_threadCounter);
/*     */ 
/*  46 */     this.m_queueManager = a_queueManager;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/*  62 */       while (!this.m_bFinished)
/*     */       {
/*     */         try
/*     */         {
/*  69 */           synchronized (this)
/*     */           {
/*  72 */             if (this.m_queueManager.getItemCount() == 0)
/*     */             {
/*  74 */               wait();
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (InterruptedException ie)
/*     */         {
/*     */         }
/*     */ 
/*  84 */         this.m_queueManager.checkIsRunning();
/*     */ 
/*  89 */         synchronized (this)
/*     */         {
/*  91 */           while (this.m_bSuspended)
/*     */           {
/*     */             try
/*     */             {
/*  95 */               wait();
/*     */             }
/*     */             catch (InterruptedException ie)
/*     */             {
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 104 */           if (this.m_bFinished)
/*     */           {
/* 106 */             return;
/*     */           }
/*     */ 
/* 109 */           this.m_currentItem = this.m_queueManager.getNextItem();
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 115 */           if (this.m_currentItem != null)
/*     */           {
/* 117 */             this.m_queueManager.processQueueItem(this.m_currentItem);
/*     */           }
/*     */         }
/*     */         catch (Throwable bn2e)
/*     */         {
/* 122 */           GlobalApplication.getInstance().getLogger().error("QueueThread.start: exception ", bn2e);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IllegalStateException ise)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void stopProcessing()
/*     */   {
/* 145 */     this.m_bFinished = true;
/* 146 */     interrupt();
/*     */   }
/*     */ 
/*     */   public synchronized void alertToQueueChange()
/*     */   {
/* 160 */     notifyAll();
/*     */   }
/*     */ 
/*     */   public QueuedItem getCurrentItem()
/*     */   {
/* 172 */     return this.m_currentItem;
/*     */   }
/*     */ 
/*     */   public void suspendProcessing()
/*     */   {
/* 182 */     synchronized (this)
/*     */     {
/* 184 */       this.m_bSuspended = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resumeProcessing()
/*     */   {
/* 193 */     if (this.m_bSuspended)
/*     */     {
/* 195 */       this.m_bSuspended = false;
/*     */       try
/*     */       {
/* 198 */         notify();
/*     */       }
/*     */       catch (IllegalMonitorStateException imse)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.queue.bean.QueueThread
 * JD-Core Version:    0.6.0
 */