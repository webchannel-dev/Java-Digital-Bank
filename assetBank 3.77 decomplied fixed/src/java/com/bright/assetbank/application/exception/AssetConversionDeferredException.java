/*    */ package com.bright.assetbank.application.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class AssetConversionDeferredException extends Bn2Exception
/*    */ {
/*    */   private String m_sFilePath;
/*    */ 
/*    */   public AssetConversionDeferredException(String a_sFilePath)
/*    */   {
/* 42 */     this.m_sFilePath = a_sFilePath;
/*    */   }
/*    */ 
/*    */   public String getFilePath()
/*    */   {
/* 51 */     return this.m_sFilePath;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.exception.AssetConversionDeferredException
 * JD-Core Version:    0.6.0
 */