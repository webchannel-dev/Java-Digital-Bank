/*    */ package com.bright.framework.search.bean;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*    */ import java.io.PrintStream;
/*    */ import java.util.TimerTask;
/*    */ 
/*    */ public class ReindexTask extends TimerTask
/*    */ {
/* 32 */   private MultiLanguageSearchManager m_searchManager = null;
/*    */ 
/*    */   public ReindexTask(MultiLanguageSearchManager a_searchManager)
/*    */   {
/* 47 */     this.m_searchManager = a_searchManager;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 63 */       this.m_searchManager.rebuildIndices(false);
/*    */     }
/*    */     catch (Bn2Exception e)
/*    */     {
/* 68 */       System.out.println(e.getMessage());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.bean.ReindexTask
 * JD-Core Version:    0.6.0
 */