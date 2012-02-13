/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.util.VideoUtil;
/*    */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*    */ import com.bright.assetbank.converter.exception.AssetConversionException;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ 
/*    */ public abstract class VideoConverter
/*    */   implements AssetConverter
/*    */ {
/*    */   public String convert(String a_sBaseSourcePath, String a_sBaseDestinationPath, String a_sRelativeSourcePath, String a_sRelativeDestinationPath)
/*    */     throws AssetConversionException
/*    */   {
/* 52 */     String sDestinationFilepath = null;
/*    */ 
/* 55 */     sDestinationFilepath = FileUtil.getFilepathWithoutSuffix(a_sRelativeSourcePath);
/* 56 */     sDestinationFilepath = sDestinationFilepath + "." + getFileExtension();
/*    */ 
/* 58 */     if ((ConverterSettings.getFfmpegPath() == null) || (ConverterSettings.getFfmpegPath().length() == 0))
/*    */     {
/* 60 */       throw new AssetConversionException("VideoToPngConverter.convert: ffmpeg path is null in ApplicationSettings.properties");
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 65 */       VideoUtil.createPreviewImage(a_sBaseSourcePath + "/" + a_sRelativeSourcePath, a_sBaseDestinationPath + "/" + sDestinationFilepath, 0);
/*    */     }
/*    */     catch (Bn2Exception e)
/*    */     {
/* 69 */       throw new AssetConversionException(getClass().getSimpleName() + ": Error: ", e);
/*    */     }
/*    */ 
/* 72 */     return sDestinationFilepath;
/*    */   }
/*    */ 
/*    */   protected abstract String getFileExtension();
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.VideoConverter
 * JD-Core Version:    0.6.0
 */