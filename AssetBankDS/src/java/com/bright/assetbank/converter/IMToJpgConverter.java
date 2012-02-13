/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.bright.assetbank.application.util.ABImageMagickOptionList;
/*    */ import com.bright.assetbank.converter.exception.AssetConversionException;
/*    */ import com.bright.framework.image.util.ImageMagick;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ 
/*    */ public class IMToJpgConverter
/*    */   implements AssetConverter
/*    */ {
/*    */   public String convert(String a_sBaseSourcePath, String a_sBaseDestinationPath, String a_sRelativeSourcePath, String a_sRelativeDestinationPath)
/*    */     throws AssetConversionException
/*    */   {
/* 46 */     String sDestinationFilepth = null;
/*    */ 
/* 49 */     sDestinationFilepth = FileUtil.getFilepathWithoutSuffix(a_sRelativeSourcePath);
/* 50 */     sDestinationFilepth = sDestinationFilepth + ".jpg";
/*    */ 
/* 52 */     String sFullDestPath = a_sBaseDestinationPath + "/" + sDestinationFilepth;
/* 53 */     String sFullSourcePath = a_sBaseSourcePath + "/" + a_sRelativeSourcePath;
/*    */ 
/* 56 */     ABImageMagickOptionList options = new ABImageMagickOptionList();
/*    */ 
/* 58 */     options.addInputFilename(sFullSourcePath);
/* 59 */     options.addFormatSpecificOptions(sFullSourcePath, sFullDestPath);
/* 60 */     options.addOutputFilename(sFullDestPath);
/*    */     try
/*    */     {
/* 65 */       ImageMagick.convert(options);
/*    */ 
/* 68 */       File fCheck = new File(sFullDestPath);
/*    */ 
/* 70 */       if (!fCheck.exists())
/*    */       {
/* 72 */         throw new AssetConversionException("IMToJpgConverter.convert: could not create destination file: " + sFullDestPath);
/*    */       }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 77 */       throw new AssetConversionException("IMToJpgConverter.convert: exception: " + e.getLocalizedMessage(), e);
/*    */     }
/* 79 */     return sDestinationFilepth;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.IMToJpgConverter
 * JD-Core Version:    0.6.0
 */