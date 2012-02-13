/*    */ package com.bright.assetbank.application.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class AssetFileReadException extends Bn2Exception
/*    */ {
/*    */   public AssetFileReadException(String a_sMessage)
/*    */   {
/* 31 */     super(a_sMessage);
/*    */   }
/*    */ 
/*    */   public AssetFileReadException(String a_sMessage, Exception a_cause)
/*    */   {
/* 36 */     super(a_sMessage, a_cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.exception.AssetFileReadException
 * JD-Core Version:    0.6.0
 */