/*     */ package com.bright.framework.queue.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.framework.queue.bean.QueueThread;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract class QueueManager extends Bn2Manager
/*     */ {
/*  36 */   protected QueueThread[] m_aQueueThread = null;
/*  37 */   protected Vector<QueuedItem> m_vecQueue = new Vector();
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  54 */     super.startup();
/*     */ 
/*  57 */     this.m_aQueueThread = new QueueThread[getNumberOfServicingThreads()];
/*     */ 
/*  60 */     for (int i = 0; i < getNumberOfServicingThreads(); i++)
/*     */     {
/*  62 */       this.m_aQueueThread[i] = new QueueThread(this);
/*  63 */       this.m_aQueueThread[i].start();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*  80 */     super.dispose();
/*     */ 
/*  82 */     for (int i = 0; (this.m_aQueueThread != null) && (i < this.m_aQueueThread.length); i++)
/*     */     {
/*  84 */       if (this.m_aQueueThread[i] == null)
/*     */         continue;
/*  86 */       this.m_aQueueThread[i].stopProcessing();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int queueItem(QueuedItem a_queuedItem)
/*     */   {
/* 106 */     this.m_vecQueue.add(a_queuedItem);
/*     */ 
/* 109 */     for (int i = 0; (this.m_aQueueThread != null) && (i < this.m_aQueueThread.length); i++)
/*     */     {
/* 111 */       this.m_aQueueThread[i].alertToQueueChange();
/*     */     }
/*     */ 
/* 114 */     return getItemCount();
/*     */   }
/*     */ 
/*     */   public boolean isThreadActive(Thread a_thread)
/*     */   {
/* 124 */     for (int i = 0; (this.m_aQueueThread != null) && (i < this.m_aQueueThread.length); i++)
/*     */     {
/* 126 */       if (a_thread == this.m_aQueueThread[i])
/*     */       {
/* 128 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   public int getItemCount()
/*     */   {
/* 148 */     return this.m_vecQueue.size();
/*     */   }
/*     */ 
/*     */   public QueuedItem getNextItem()
/*     */   {
/* 163 */     QueuedItem item = null;
/*     */ 
/* 165 */     synchronized (this.m_vecQueue)
/*     */     {
/* 167 */       if (this.m_vecQueue.size() > 0)
/*     */       {
/* 169 */         item = (QueuedItem)this.m_vecQueue.remove(0);
/*     */       }
/*     */     }
/*     */ 
/* 173 */     return item;
/*     */   }
/*     */ 
/*     */   protected int getNumberOfServicingThreads()
/*     */   {
/* 183 */     return 1;
/*     */   }
/*     */ 
/*     */   public abstract void processQueueItem(QueuedItem paramQueuedItem)
/*     */     throws Bn2Exception;
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.queue.service.QueueManager
 * JD-Core Version:    0.6.0
 */