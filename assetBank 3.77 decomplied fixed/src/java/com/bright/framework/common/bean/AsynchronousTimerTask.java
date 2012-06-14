/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ import java.util.TimerTask;
/*    */ 
/*    */ public class AsynchronousTimerTask extends TimerTask
/*    */ {
/* 19 */   private TimerTask m_actualTask = null;
/*    */ 
/*    */   public AsynchronousTimerTask(TimerTask a_actualTask)
/*    */   {
/* 23 */     this.m_actualTask = a_actualTask;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 31 */     Thread thread = new Thread()
/*    */     {
/*    */       public void run()
/*    */       {
/* 35 */         AsynchronousTimerTask.this.runAsynchronously();
/*    */       }
/*    */     };
/* 40 */     thread.start();
/*    */   }
/*    */ 
/*    */   protected synchronized void runAsynchronously()
/*    */   {
/* 47 */     this.m_actualTask.run();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.AsynchronousTimerTask
 * JD-Core Version:    0.6.0
 */