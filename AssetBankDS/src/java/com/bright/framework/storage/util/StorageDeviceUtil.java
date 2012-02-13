/*     */ package com.bright.framework.storage.util;
/*     */ 
/*     */ import com.bright.framework.storage.bean.StorageDevice;
/*     */ 
/*     */ public class StorageDeviceUtil
/*     */ {
/*     */   public static String removeDevicePrefix(String a_sRelativePath)
/*     */   {
/*  30 */     int iDelimiterIndex = a_sRelativePath.indexOf(':');
/*     */ 
/*  32 */     return a_sRelativePath.substring(iDelimiterIndex + 1);
/*     */   }
/*     */ 
/*     */   public static String getStorageDirectory(String a_sRelativePath)
/*     */   {
/*  43 */     if (!hasStorageDirectory(a_sRelativePath))
/*     */     {
/*  45 */       return null;
/*     */     }
/*     */ 
/*  48 */     int iDelimiterIndex = a_sRelativePath.indexOf(':');
/*     */ 
/*  50 */     return a_sRelativePath.substring(iDelimiterIndex + 1, a_sRelativePath.indexOf("/"));
/*     */   }
/*     */ 
/*     */   public static boolean hasStorageDirectory(String a_sPath)
/*     */   {
/*  60 */     return (a_sPath != null) && (a_sPath.length() > 1) && (a_sPath.indexOf('/', 1) > 0);
/*     */   }
/*     */ 
/*     */   public static String getRelativePathForDevice(String a_sRelativePath, long a_lDeviceId)
/*     */   {
/*  72 */     if (a_lDeviceId != 1L)
/*     */     {
/*  75 */       return String.valueOf(a_lDeviceId) + ':' + a_sRelativePath;
/*     */     }
/*  77 */     return a_sRelativePath;
/*     */   }
/*     */ 
/*     */   public static String getRelativePathForFile(String a_sRelativePath, StorageDevice a_device)
/*     */   {
/*  88 */     return getRelativePathForDevice(a_sRelativePath, a_device.getId());
/*     */   }
/*     */ 
/*     */   public static String getRealRelativePath(String a_sRelativePath)
/*     */   {
/*  99 */     return removeDevicePrefix(a_sRelativePath);
/*     */   }
/*     */ 
/*     */   public static long getDeviceIdForRelativePath(String a_sRelativePath)
/*     */   {
/* 110 */     long lDeviceId = 1L;
/*     */ 
/* 112 */     if (a_sRelativePath != null)
/*     */     {
/* 114 */       int iDelimiterIndex = a_sRelativePath.indexOf(':');
/*     */ 
/* 116 */       if (iDelimiterIndex > 0)
/*     */       {
/* 118 */         lDeviceId = Long.parseLong(a_sRelativePath.substring(0, iDelimiterIndex));
/*     */       }
/*     */     }
/*     */ 
/* 122 */     return lDeviceId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.util.StorageDeviceUtil
 * JD-Core Version:    0.6.0
 */