/*    */ package com.bright.assetbank.search.exception;
/*    */ 
/*    */ public class MaxSearchesReachedException extends Exception
/*    */ {
/*    */   public MaxSearchesReachedException(int a_iMaxAllowed)
/*    */   {
/* 28 */     super("The maximum number of saved searches (" + a_iMaxAllowed + ") has been reached");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.exception.MaxSearchesReachedException
 * JD-Core Version:    0.6.0
 */