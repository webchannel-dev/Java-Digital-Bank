/*    */ package com.bright.framework.cache.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.Bn2Manager;
/*    */ import com.bright.framework.cache.FileCache;
/*    */ import com.bright.framework.storage.bean.StorageDevice;
/*    */ import com.bright.framework.storage.constant.StorageDeviceType;
/*    */ import com.bright.framework.storage.constant.StoredFileType;
/*    */ import com.bright.framework.storage.service.StorageDeviceManager;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class FileCacheManager extends Bn2Manager
/*    */ {
/* 35 */   private HashMap hm_FileCaches = new HashMap();
/*    */ 
/* 37 */   private StorageDeviceManager m_storageDeviceManager = null;
/*    */ 
/*    */   public FileCache getFileCache(String a_sClassName, int a_iMaxSize)
/*    */     throws Bn2Exception
/*    */   {
/* 48 */     FileCache cache = null;
/*    */ 
/* 50 */     if (!this.hm_FileCaches.containsKey(a_sClassName))
/*    */     {
/* 53 */       StorageDevice device = this.m_storageDeviceManager.getDeviceForNewFile(null, StorageDeviceType.getTypeFor(StoredFileType.FILE_CACHE));
/* 54 */       String sBasePath = device.getFullLocalBasePath(StoredFileType.FILE_CACHE);
/* 55 */       cache = new FileCache(sBasePath + '/' + a_sClassName, a_iMaxSize);
/* 56 */       this.hm_FileCaches.put(a_sClassName, cache);
/*    */     }
/*    */     else
/*    */     {
/* 60 */       cache = (FileCache)this.hm_FileCaches.get(a_sClassName);
/*    */     }
/*    */ 
/* 63 */     return cache;
/*    */   }
/*    */ 
/*    */   public void setStorageDeviceManager(StorageDeviceManager a_deviceManager)
/*    */   {
/* 68 */     this.m_storageDeviceManager = a_deviceManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.cache.service.FileCacheManager
 * JD-Core Version:    0.6.0
 */