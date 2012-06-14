/*    */ package com.bright.assetbank.workflow.util;
/*    */ 
/*    */ public class SubmitOptionLeaveInCurrentStatePredicate
/*    */   implements SubmitOptionPredicate
/*    */ {
/*    */   public boolean submitOptionMatches(int a_iOption)
/*    */   {
/* 27 */     return (a_iOption == 5) || (a_iOption == 6);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.util.SubmitOptionLeaveInCurrentStatePredicate
 * JD-Core Version:    0.6.0
 */