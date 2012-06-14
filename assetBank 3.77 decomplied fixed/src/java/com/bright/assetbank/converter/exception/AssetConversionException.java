/*    */ package com.bright.assetbank.converter.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class AssetConversionException extends Bn2Exception
/*    */ {
/*    */   public AssetConversionException(String a_sMessage)
/*    */   {
/* 31 */     super(a_sMessage);
/*    */   }
/*    */ 
/*    */   public AssetConversionException(String a_sMessage, Exception a_cause)
/*    */   {
/* 36 */     super(a_sMessage, a_cause);
/*    */   }
/*    */ 
/*    */   public AssetConversionException(String a_sMessage, Throwable a_cause)
/*    */   {
/* 41 */     super(a_sMessage, a_cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.exception.AssetConversionException
 * JD-Core Version:    0.6.0
 */