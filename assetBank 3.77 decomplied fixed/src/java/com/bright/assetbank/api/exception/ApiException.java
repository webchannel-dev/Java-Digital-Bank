/*    */ package com.bright.assetbank.api.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class ApiException extends Bn2Exception
/*    */ {
/*    */   public ApiException(String a_sMessage)
/*    */   {
/* 31 */     super(a_sMessage);
/*    */   }
/*    */ 
/*    */   public ApiException(String a_sMessage, Exception a_cause)
/*    */   {
/* 36 */     super(a_sMessage, a_cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.api.exception.ApiException
 * JD-Core Version:    0.6.0
 */