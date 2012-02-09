/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*    */ 
/*    */ public class HTMLToJpgConverter extends CommandLineJpgConverter
/*    */ {
/*    */   protected String getCommand()
/*    */   {
/* 38 */     return ConverterSettings.getHtmlToolsPath();
/*    */   }
/*    */ 
/*    */   protected boolean getQuotePaths()
/*    */   {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   protected boolean getUniformSlashes()
/*    */   {
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */   protected int getSuccessCode()
/*    */   {
/* 53 */     return 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.HTMLToJpgConverter
 * JD-Core Version:    0.6.0
 */