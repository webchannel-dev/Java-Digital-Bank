/*    */ package com.bright.framework.storage.bean;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.storage.constant.StoredFileType;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class FileSystemStorageDevice extends BaseStorageDevice
/*    */ {
/*    */   public FileSystemStorageDevice(String a_sFactoryClassName, String a_sFactoryDisplayName)
/*    */   {
/* 37 */     super(a_sFactoryClassName, a_sFactoryDisplayName);
/*    */   }
/*    */ 
/*    */   public boolean isLocalFileStorage()
/*    */   {
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   protected String generateUniqueFilename(String a_sDirectory, String a_sFilename, StoredFileType a_type)
/*    */     throws Bn2Exception
/*    */   {
/* 63 */     String sDirectory = getFullLocalBasePath() + "/" + a_sDirectory;
/* 64 */     String sFilename = FileUtil.getUniqueFilename(sDirectory, a_sFilename);
/*    */ 
/* 67 */     File file = new File(sDirectory + "/" + sFilename);
/* 68 */     if (!file.exists())
/*    */     {
/*    */       try
/*    */       {
/* 72 */         file.createNewFile();
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 76 */         this.m_logger.error("FileStoreManager.generateUniqueFilename() : Could not create empty file: " + file.getAbsolutePath());
/*    */       }
/*    */     }
/*    */ 
/* 80 */     return sFilename;
/*    */   }
/*    */ 
/*    */   public void doRunUsageScan()
/*    */     throws Bn2Exception
/*    */   {
/* 90 */     setUsedSpaceInBytes(getUsedLocalSpaceInBytes());
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.bean.FileSystemStorageDevice
 * JD-Core Version:    0.6.0
 */