/*     */ package com.bright.framework.image.util;
/*     */ 
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ 
/*     */ public class ImageUtil
/*     */   implements ImageConstants
/*     */ {
/*     */   public static boolean isWebImageFile(String a_sFilename)
/*     */   {
/*  58 */     String sSuffix = null;
/*  59 */     boolean bResult = false;
/*     */ 
/*  61 */     sSuffix = FileUtil.getSuffix(a_sFilename);
/*     */ 
/*  63 */     if (sSuffix != null)
/*     */     {
/*  65 */       sSuffix = sSuffix.toLowerCase();
/*     */ 
/*  67 */       if ((sSuffix.equals("jpg")) || (sSuffix.equals("jpeg")) || (sSuffix.equals("jpe")) || (sSuffix.equals("gif")) || (sSuffix.equals("png")))
/*     */       {
/*  73 */         bResult = true;
/*     */       }
/*     */     }
/*     */ 
/*  77 */     return bResult;
/*     */   }
/*     */ 
/*     */   public static boolean canReadEmbeddedData(String a_sSuffix)
/*     */   {
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean supportsMultiLayers(String a_sFilename, boolean a_bSuffixOnly)
/*     */   {
/* 140 */     String sSuffix = a_sFilename;
/*     */ 
/* 142 */     if (!a_bSuffixOnly)
/*     */     {
/* 144 */       sSuffix = FileUtil.getSuffix(a_sFilename);
/*     */     }
/*     */ 
/* 147 */     if (sSuffix != null)
/*     */     {
/* 149 */       sSuffix = sSuffix.toLowerCase();
/*     */ 
/* 151 */       if ((sSuffix.equals("tif")) || (sSuffix.equals("tiff")) || (sSuffix.equals("pdf")))
/*     */       {
/* 155 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean supportsMultiLayers(long a_lFormatId)
/*     */   {
/* 164 */     return (a_lFormatId == 107L) || (a_lFormatId == 7L) || (a_lFormatId == 6L);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.util.ImageUtil
 * JD-Core Version:    0.6.0
 */