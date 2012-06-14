/*    */ package com.bright.framework.storage.service;
/*    */ 
/*    */ import com.bright.framework.storage.bean.FileSystemStorageDevice;
/*    */ import com.bright.framework.storage.constant.StorageDeviceType;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FileSystemStorageDeviceFactory extends BaseStorageDeviceFactory<FileSystemStorageDevice>
/*    */ {
/*    */   public static final String k_sName = "File System";
/*    */ 
/*    */   public FileSystemStorageDevice createNewStorageDevice()
/*    */   {
/* 30 */     return new FileSystemStorageDevice(getClass().getName(), getDisplayName());
/*    */   }
/*    */ 
/*    */   public String getDisplayName()
/*    */   {
/* 35 */     return "File System";
/*    */   }
/*    */ 
/*    */   public List<StorageDeviceType> getSupportedDeviceTypes()
/*    */   {
/* 40 */     return Arrays.asList(StorageDeviceType.values());
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.service.FileSystemStorageDeviceFactory
 * JD-Core Version:    0.6.0
 */