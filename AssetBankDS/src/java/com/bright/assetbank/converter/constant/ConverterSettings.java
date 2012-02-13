/*    */ package com.bright.assetbank.converter.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class ConverterSettings extends GlobalSettings
/*    */ {
/*    */   public static String getPptToJpgCommand()
/*    */   {
/* 32 */     return getInstance().getStringSetting("ppt-jpg-command");
/*    */   }
/*    */ 
/*    */   public static String getPptMergeCommand()
/*    */   {
/* 37 */     return getInstance().getStringSetting("ppt-merge-command");
/*    */   }
/*    */ 
/*    */   public static String getSwfToImageCommand()
/*    */   {
/* 42 */     return getInstance().getStringSetting("swf-image-command");
/*    */   }
/*    */ 
/*    */   public static String getSwfToImageDestinationExtension()
/*    */   {
/* 47 */     return getInstance().getStringSetting("swf-image-destination-extension");
/*    */   }
/*    */ 
/*    */   public static String getFfmpegPath()
/*    */   {
/* 52 */     return getInstance().getStringSetting("ffmpeg-path");
/*    */   }
/*    */ 
/*    */   public static String getRawToPpmCommand()
/*    */   {
/* 57 */     return getInstance().getStringSetting("raw-ppm-command");
/*    */   }
/*    */ 
/*    */   public static String getDcrawPath()
/*    */   {
/* 62 */     return getInstance().getStringSetting("dcraw-path");
/*    */   }
/*    */ 
/*    */   public static String getHtmlToolsPath()
/*    */   {
/* 67 */     return getInstance().getStringSetting("htmltools-path");
/*    */   }
/*    */ 
/*    */   public static String getDocToImageConverterPath()
/*    */   {
/* 72 */     return getInstance().getStringSetting("doc-to-image-converter-path");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.constant.ConverterSettings
 * JD-Core Version:    0.6.0
 */