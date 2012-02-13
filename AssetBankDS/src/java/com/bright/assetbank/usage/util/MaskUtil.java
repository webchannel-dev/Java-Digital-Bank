/*     */ package com.bright.assetbank.usage.util;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import java.io.File;
/*     */ 
/*     */ public class MaskUtil
/*     */ {
/*     */   private static final String c_ksThumbnailSuffix = "t";
/*     */   private static final String c_ksThumbnailExtension = "png";
/*     */   private static final String c_ksThumbnailSuffixAndExtension = "t.png";
/*     */   private static final String c_ksWebappRelativeMaskDir = "images/masks";
/*     */ 
/*     */   public static boolean masksAllowedBySettings()
/*     */   {
/*  51 */     return !AssetBankSettings.getCreditStripEnabled();
/*     */   }
/*     */ 
/*     */   public static String getMaskDirPath()
/*     */   {
/*  59 */     return AssetBankSettings.getApplicationPath() + "/" + "images/masks";
/*     */   }
/*     */ 
/*     */   public static File getMaskDirPathAsFile()
/*     */   {
/*  65 */     return new File(getMaskDirPath());
/*     */   }
/*     */ 
/*     */   public static String getWebappRelativeMaskPath(String a_sFilename)
/*     */   {
/*  74 */     return "images/masks/" + a_sFilename;
/*     */   }
/*     */ 
/*     */   public static String getMaskFullPath(String a_sFilename)
/*     */   {
/*  83 */     return getMaskFullPathAsFile(a_sFilename).getPath();
/*     */   }
/*     */ 
/*     */   public static File getMaskFullPathAsFile(String a_sFilename)
/*     */   {
/*  92 */     return new File(getMaskDirPathAsFile(), a_sFilename);
/*     */   }
/*     */ 
/*     */   public static String getThumbnailPath(long a_lMaskId)
/*     */   {
/*  97 */     return getThumbnailPathAsFile(a_lMaskId).getPath();
/*     */   }
/*     */ 
/*     */   public static File getThumbnailPathAsFile(long a_lMaskId)
/*     */   {
/* 102 */     return new File(getMaskDirPathAsFile(), getThumbnailFilename(a_lMaskId));
/*     */   }
/*     */ 
/*     */   public static String getWebappRelativeThumbnailPath(long a_lMaskId)
/*     */   {
/* 107 */     return "images/masks/" + getThumbnailFilename(a_lMaskId);
/*     */   }
/*     */ 
/*     */   private static String getThumbnailFilename(long a_lMaskId)
/*     */   {
/* 112 */     return a_lMaskId + "t.png";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.util.MaskUtil
 * JD-Core Version:    0.6.0
 */