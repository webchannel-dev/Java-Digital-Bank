/*     */ package com.bright.assetbank.batch.upload.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.batch.upload.service.ImportManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ImportThread extends Thread
/*     */ {
/*  31 */   private ImportManager m_importManager = null;
/*  32 */   private boolean m_bFinished = false;
/*     */ 
/*     */   public ImportThread(ImportManager a_importManager)
/*     */   {
/*  45 */     this.m_importManager = a_importManager;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/*  61 */       while (!this.m_bFinished)
/*     */       {
/*     */         try
/*     */         {
/*  68 */           synchronized (this)
/*     */           {
/*  70 */             wait();
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (InterruptedException ie)
/*     */         {
/*     */         }
/*     */ 
/*  79 */         this.m_importManager.checkIsRunning();
/*     */         try
/*     */         {
/*  84 */           this.m_importManager.processQueueItem(this.m_importManager.getNextItem());
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/*  88 */           GlobalApplication.getInstance().getLogger().error("ImportThread.start: exception ", bn2e);
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
/* 110 */     this.m_bFinished = true;
/* 111 */     interrupt();
/*     */   }
/*     */ 
/*     */   public void alertToQueueChange()
/*     */   {
/* 125 */     interrupt();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.bean.ImportThread
 * JD-Core Version:    0.6.0
 */