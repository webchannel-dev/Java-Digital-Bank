/*    */ package com.bright.framework.database.bean;
/*    */ 
/*    */ public abstract class TransactionListener
/*    */ {
/*    */   public void doAfterRollback()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doAfterCommit()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doOnFinalizerCommit()
/*    */   {
/* 41 */     doAfterCommit();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.bean.TransactionListener
 * JD-Core Version:    0.6.0
 */