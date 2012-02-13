/*     */ package com.bright.assetbank.converter;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*     */ import com.bright.assetbank.converter.exception.AssetConversionException;
/*     */ import com.bright.framework.image.util.ImageMagick;
/*     */ import com.bright.framework.image.util.ImageMagickOptionList;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.commandline.CommandLineExec;
/*     */ import java.io.File;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class RawToImageConverter
/*     */   implements AssetConverter
/*     */ {
/*     */   public String convert(String a_sBaseSourcePath, String a_sBaseDestinationPath, String a_sRelativeSourcePath, String a_sRelativeDestinationPath)
/*     */     throws AssetConversionException
/*     */   {
/*  44 */     String sDestinationFileId = null;
/*     */ 
/*  47 */     if ((ConverterSettings.getDcrawPath() == null) || (ConverterSettings.getDcrawPath().length() == 0))
/*     */     {
/*  50 */       throw new AssetConversionException("RawToImageConverter: dcraw path is not specified in the settings file");
/*     */     }
/*     */ 
/*  53 */     StringBuffer sbOut = new StringBuffer();
/*     */ 
/*  56 */     String[] aCmd = new String[2];
/*     */ 
/*  59 */     aCmd[0] = ConverterSettings.getDcrawPath();
/*     */ 
/*  62 */     aCmd[1] = (a_sBaseSourcePath + "/" + a_sRelativeSourcePath);
/*     */ 
/*  65 */     String sCommandForLog = aCmd[0] + " " + aCmd[1];
/*  66 */     GlobalApplication.getInstance().getLogger().debug("RawToImageConverter: about to run command " + sCommandForLog);
/*     */ 
/*  69 */     int iCode = -1;
/*     */     try
/*     */     {
/*  72 */       StringBuffer sbErrors = new StringBuffer();
/*  73 */       iCode = CommandLineExec.execute(aCmd, sbOut, sbErrors);
/*     */ 
/*  75 */       if (iCode != 0)
/*     */       {
/*  77 */         throw new Exception("Returned code: " + iCode + ", error output: " + sbErrors.toString());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  82 */       throw new AssetConversionException("RawToImageConverter: " + sCommandForLog + " : " + e.getMessage());
/*     */     }
/*     */ 
/*  86 */     String sPPMFilepath = a_sBaseSourcePath + "/" + FileUtil.getFilepathWithoutSuffix(a_sRelativeSourcePath) + "." + "ppm";
/*     */ 
/*  89 */     sDestinationFileId = FileUtil.getFilepathWithoutSuffix(a_sRelativeDestinationPath) + ".jpg";
/*  90 */     String sFullDestinationPath = a_sBaseDestinationPath + "/" + sDestinationFileId;
/*     */ 
/*  93 */     ImageMagickOptionList options = new ImageMagickOptionList();
/*     */ 
/*  95 */     options.addInputFilename(sPPMFilepath);
/*  96 */     options.addOutputFilename(sFullDestinationPath);
/*     */     try
/*     */     {
/* 101 */       ImageMagick.convert(options);
/*     */ 
/* 104 */       File fCheck = new File(sFullDestinationPath);
/*     */ 
/* 106 */       if (!fCheck.exists())
/*     */       {
/* 108 */         throw new AssetConversionException("RawToImageConverter.convert: could not create destination jpg file.");
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 113 */       throw new AssetConversionException("IMToJpgConverter.convert: exception: ", e);
/*     */     }
/*     */     finally
/*     */     {
/* 118 */       File fToDelete = new File(sPPMFilepath);
/* 119 */       fToDelete.delete();
/* 120 */       FileUtil.logFileDeletion(fToDelete);
/*     */     }
/*     */ 
/* 123 */     return sDestinationFileId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.RawToImageConverter
 * JD-Core Version:    0.6.0
 */