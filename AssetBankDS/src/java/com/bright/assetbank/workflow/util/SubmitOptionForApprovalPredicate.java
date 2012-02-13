/*    */ package com.bright.assetbank.workflow.util;
/*    */ 
/*    */ public class SubmitOptionForApprovalPredicate
/*    */   implements SubmitOptionPredicate
/*    */ {
/*    */   public boolean submitOptionMatches(int a_iOption)
/*    */   {
/* 27 */     return (a_iOption == 1) || (a_iOption == 3);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.util.SubmitOptionForApprovalPredicate
 * JD-Core Version:    0.6.0
 */