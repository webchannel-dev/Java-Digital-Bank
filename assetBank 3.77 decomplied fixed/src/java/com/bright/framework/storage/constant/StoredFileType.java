/*    */ package com.bright.framework.storage.constant;
/*    */ 
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import com.bright.framework.storage.util.StorageDeviceUtil;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public enum StoredFileType
/*    */ {
/* 24 */   TEMP(FrameworkSettings.getTemporaryDirectory()), 
/* 25 */   SHARED(FrameworkSettings.getShareDirectory()), 
/* 26 */   EXPORT(FrameworkSettings.getExportDirectory()), 
/* 27 */   IMPORT(FrameworkSettings.getImportDirectory()), 
/* 28 */   SEARCH(FrameworkSettings.getSavedSearchCriteriaDir()), 
/* 29 */   FILE_CACHE(FrameworkSettings.getFileCacheDirectory()), 
/* 30 */   PREVIEW_OR_THUMBNAIL, 
/* 31 */   ASSET, 
/* 32 */   SHARED_THUMBNAIL("thumbnails"), 
/* 33 */   REPURPOSED;
/*    */ 
/*    */   private String m_sDirectoryName;
/*    */ 
/*    */   private StoredFileType()
/*    */   {
/*    */   }
/*    */ 
/*    */   private StoredFileType(String a_sDirectoryName)
/*    */   {
/* 44 */     this.m_sDirectoryName = a_sDirectoryName;
/*    */   }
/*    */ 
/*    */   public String getDirectoryName()
/*    */   {
/* 49 */     return this.m_sDirectoryName;
/*    */   }
/*    */ 
/*    */   public static StoredFileType getTypeForPath(String a_sPath)
/*    */   {
/* 59 */     a_sPath = StorageDeviceUtil.removeDevicePrefix(a_sPath);
/*    */ 
/* 61 */     for (StoredFileType type : values())
/*    */     {
/* 63 */       if ((StringUtils.isNotEmpty(type.getDirectoryName())) && (a_sPath.startsWith(type.getDirectoryName())))
/*    */       {
/* 65 */         return type;
/*    */       }
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   public static boolean isTransientFileType(String a_sPath)
/*    */   {
/* 73 */     StoredFileType type = getTypeForPath(a_sPath);
/* 74 */     return (type != null) && ((type.equals(TEMP)) || (type.equals(SHARED)) || (type.equals(IMPORT)) || (type.equals(EXPORT)));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.constant.StoredFileType
 * JD-Core Version:    0.6.0
 */