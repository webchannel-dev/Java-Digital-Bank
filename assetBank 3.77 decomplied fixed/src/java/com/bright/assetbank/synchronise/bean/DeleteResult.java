/*    */ package com.bright.assetbank.synchronise.bean;
/*    */ 
/*    */ public class DeleteResult
/*    */ {
/* 26 */   private int m_iNumDeleted = 0;
/* 27 */   private int m_iNumErrors = 0;
/*    */ 
/*    */   public int getNumDeleted()
/*    */   {
/* 31 */     return this.m_iNumDeleted;
/*    */   }
/*    */ 
/*    */   public void setNumDeleted(int numDeleted) {
/* 35 */     this.m_iNumDeleted = numDeleted;
/*    */   }
/*    */ 
/*    */   public int getNumErrors() {
/* 39 */     return this.m_iNumErrors;
/*    */   }
/*    */ 
/*    */   public void setNumErrors(int numErrors) {
/* 43 */     this.m_iNumErrors = numErrors;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.DeleteResult
 * JD-Core Version:    0.6.0
 */