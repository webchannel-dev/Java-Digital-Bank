/*    */ package com.bright.assetbank.application.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class AssetConversionFailedException extends Bn2Exception
/*    */ {
/*    */   public AssetConversionFailedException(Throwable a_cause)
/*    */   {
/* 31 */     super("Asset conversion failed", a_cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.exception.AssetConversionFailedException
 * JD-Core Version:    0.6.0
 */