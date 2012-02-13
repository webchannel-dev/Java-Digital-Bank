/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*    */ import com.bright.assetbank.converter.exception.AssetConversionException;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ 
/*    */ public class SwfToImageConverter extends CommandLineConverter
/*    */ {
/*    */   public String convert(String a_sBaseSourcePath, String a_sBaseDestinationPath, String a_sRelativeSourcePath, String a_sRelativeDestinationPath)
/*    */     throws AssetConversionException
/*    */   {
/* 38 */     String sDestinationFilepath = null;
/*    */ 
/* 41 */     sDestinationFilepath = FileUtil.getFilepathWithoutSuffix(a_sRelativeSourcePath);
/* 42 */     sDestinationFilepath = sDestinationFilepath + "." + ConverterSettings.getSwfToImageDestinationExtension();
/*    */ 
/* 45 */     super.convert(a_sBaseSourcePath, a_sBaseDestinationPath, a_sRelativeSourcePath, sDestinationFilepath, (String[])null);
/*    */ 
/* 47 */     return sDestinationFilepath;
/*    */   }
/*    */ 
/*    */   protected String getCommand()
/*    */   {
/* 64 */     return ConverterSettings.getSwfToImageCommand();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.SwfToImageConverter
 * JD-Core Version:    0.6.0
 */