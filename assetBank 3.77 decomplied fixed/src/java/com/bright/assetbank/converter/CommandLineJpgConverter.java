/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.bright.assetbank.converter.exception.AssetConversionException;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ 
/*    */ public abstract class CommandLineJpgConverter extends CommandLineConverter
/*    */ {
/*    */   public String convert(String a_sBaseSourcePath, String a_sBaseDestinationPath, String a_sRelativeSourcePath, String a_sRelativeDestinationPath)
/*    */     throws AssetConversionException
/*    */   {
/* 37 */     String sDestinationFilepath = null;
/*    */ 
/* 40 */     sDestinationFilepath = FileUtil.getFilepathWithoutSuffix(a_sRelativeSourcePath);
/* 41 */     sDestinationFilepath = sDestinationFilepath + ".jpg";
/*    */ 
/* 44 */     super.convert(a_sBaseSourcePath, a_sBaseDestinationPath, a_sRelativeSourcePath, sDestinationFilepath, (String[])null);
/*    */ 
/* 46 */     return sDestinationFilepath;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.CommandLineJpgConverter
 * JD-Core Version:    0.6.0
 */