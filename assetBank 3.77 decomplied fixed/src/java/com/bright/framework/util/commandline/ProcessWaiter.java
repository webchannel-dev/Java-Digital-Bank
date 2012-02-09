/*    */ package com.bright.framework.util.commandline;
/*    */ 
/*    */ class ProcessWaiter extends Thread
/*    */ {
/* 29 */   private Process m_process = null;
/* 30 */   private boolean m_bFinished = false;
/* 31 */   private int m_iExitValue = 0;
/*    */ 
/*    */   public ProcessWaiter(Process a_process)
/*    */   {
/* 44 */     this.m_process = a_process;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 62 */       this.m_iExitValue = this.m_process.waitFor();
/* 63 */       setFinished(true);
/*    */     }
/*    */     catch (InterruptedException ioe)
/*    */     {
/* 67 */       ioe.printStackTrace();
/* 68 */       setFinished(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   public synchronized boolean hasFinished()
/*    */   {
/* 75 */     return this.m_bFinished;
/*    */   }
/*    */ 
/*    */   public synchronized void setFinished(boolean a_sFinished) {
/* 79 */     this.m_bFinished = a_sFinished;
/*    */   }
/*    */ 
/*    */   public int getExitValue() {
/* 83 */     return this.m_iExitValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.commandline.ProcessWaiter
 * JD-Core Version:    0.6.0
 */