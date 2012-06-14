/*    */ package com.bright.framework.storage.service;
/*    */ 
/*    */ import com.bright.framework.database.service.DBTransactionManager;
/*    */ import com.bright.framework.storage.bean.DatabaseStorageDevice;
/*    */ import com.bright.framework.storage.constant.StorageDeviceType;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DatabaseStorageDeviceFactory extends BaseStorageDeviceFactory<DatabaseStorageDevice>
/*    */   implements TransactionStorageDeviceFactory<DatabaseStorageDevice>
/*    */ {
/*    */   public static final String k_sName = "Database";
/*    */   private DBTransactionManager m_transactionManager;
/*    */ 
/*    */   public DatabaseStorageDevice createNewStorageDevice()
/*    */   {
/* 33 */     return new DatabaseStorageDevice(getClass().getName(), getDisplayName(), this.m_transactionManager);
/*    */   }
/*    */ 
/*    */   public String getDisplayName()
/*    */   {
/* 38 */     return "Database";
/*    */   }
/*    */ 
/*    */   public List<StorageDeviceType> getSupportedDeviceTypes()
/*    */   {
/* 43 */     return Arrays.asList(new StorageDeviceType[] { StorageDeviceType.ASSETS, StorageDeviceType.ASSETS_AND_THUMBNAILS, StorageDeviceType.THUMBNAILS });
/*    */   }
/*    */ 
/*    */   public void setTransactionManager(DBTransactionManager manager)
/*    */   {
/* 48 */     this.m_transactionManager = manager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.service.DatabaseStorageDeviceFactory
 * JD-Core Version:    0.6.0
 */