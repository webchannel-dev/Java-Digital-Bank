/*     */ package com.bright.assetbank.presentation.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class PresentationMergingConverter
/*     */ {
/*     */   private static final String c_ksClassName = "PresentationMergingConverter";
/*     */ 
/*     */   public synchronized void merge(FileStoreManager a_fileStoreManager, String a_sDestinationFullPath, AssetInList[] a_aAssets)
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     String sArguments = "";
/*  63 */     int iSize = a_aAssets.length;
/*  64 */     for (int i = 0; i < iSize; i++)
/*     */     {
/*  66 */       AssetInList ail = a_aAssets[i];
/*     */ 
/*  68 */       if (ail == null)
/*     */         continue;
/*  70 */       Asset asset = ail.getAsset();
/*     */ 
/*  73 */       String sSource = asset.getOriginalFileLocation();
/*     */ 
/*  76 */       if ((sSource == null) || (asset.getFormat().getFileExtension().compareToIgnoreCase("ppt") != 0))
/*     */         continue;
/*  78 */       if (sArguments.length() > 0)
/*     */       {
/*  80 */         sArguments = sArguments + " ";
/*     */       }
/*     */ 
/*  83 */       sArguments = sArguments + a_fileStoreManager.getAbsolutePath(sSource);
/*     */     }
/*     */ 
/*  88 */     Runtime rt = Runtime.getRuntime();
/*     */ 
/*  91 */     String sCommand = getCommand();
/*     */ 
/*  94 */     sCommand = StringUtil.replaceFirst(sCommand, "?", a_sDestinationFullPath);
/*  95 */     sCommand = StringUtil.replaceFirst(sCommand, "?", sArguments);
/*     */ 
/*  97 */     GlobalApplication.getInstance().getLogger().debug("PresentationMergingConverter: command = " + sCommand);
/*     */ 
/*  99 */     Process process = null;
/*     */     try
/*     */     {
/* 104 */       process = rt.exec(sCommand);
/*     */ 
/* 106 */       InputStream stderr = process.getErrorStream();
/* 107 */       String sLine = null;
/*     */ 
/* 109 */       InputStreamReader isr = new InputStreamReader(stderr);
/* 110 */       BufferedReader br = new BufferedReader(isr);
/*     */ 
/* 112 */       while ((sLine = br.readLine()) != null)
/*     */       {
/* 114 */         GlobalApplication.getInstance().getLogger().error("PresentationMergingConverter: Error output:" + sLine);
/*     */       }
/*     */     }
/*     */     catch (Exception ioe)
/*     */     {
/* 119 */       throw new Bn2Exception("PresentationMergingConverter: Exception: " + ioe.getMessage(), ioe);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 125 */       process.waitFor();
/*     */     }
/*     */     catch (Exception ie)
/*     */     {
/* 129 */       throw new Bn2Exception("PresentationMergingConverter: Exception: " + ie.getMessage(), ie);
/*     */     }
/*     */ 
/* 132 */     int iResult = process.exitValue();
/*     */ 
/* 135 */     if (iResult != 0)
/*     */     {
/* 137 */       throw new Bn2Exception("PresentationMergingConverter: error code: " + iResult);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getCommand()
/*     */   {
/* 156 */     return ConverterSettings.getPptMergeCommand();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.presentation.service.PresentationMergingConverter
 * JD-Core Version:    0.6.0
 */