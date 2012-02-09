/*    */ package com.bright.assetbank.autocomplete.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class ACRebuildIndexJob extends ACIndexJob
/*    */ {
/*    */   private long m_lUserId;
/*    */ 
/*    */   public ACRebuildIndexJob(long a_lUserId)
/*    */   {
/* 35 */     this.m_lUserId = a_lUserId;
/*    */   }
/*    */ 
/*    */   public void perform(AutoCompleteUpdateManager a_autoCompleteUpdateManager)
/*    */     throws Bn2Exception
/*    */   {
/* 41 */     a_autoCompleteUpdateManager.rebuildIndex(this.m_lUserId);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.ACRebuildIndexJob
 * JD-Core Version:    0.6.0
 */