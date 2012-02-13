/*    */ package com.bright.framework.database.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class TransactionException extends Bn2Exception
/*    */ {
/*    */   public TransactionException(String a_sMessage)
/*    */   {
/* 38 */     super(a_sMessage);
/*    */   }
/*    */ 
/*    */   public TransactionException(String a_sMessage, Throwable a_thrCause)
/*    */   {
/* 53 */     super(a_sMessage, a_thrCause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.exception.TransactionException
 * JD-Core Version:    0.6.0
 */