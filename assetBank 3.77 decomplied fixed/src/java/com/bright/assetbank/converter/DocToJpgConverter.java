/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*    */ 
/*    */ public class DocToJpgConverter extends CommandLineJpgConverter
/*    */ {
/*    */   protected String getCommand()
/*    */   {
/* 32 */     return ConverterSettings.getDocToImageConverterPath();
/*    */   }
/*    */ 
/*    */   protected boolean getQuotePaths()
/*    */   {
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   protected boolean getUniformSlashes()
/*    */   {
/* 42 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.DocToJpgConverter
 * JD-Core Version:    0.6.0
 */