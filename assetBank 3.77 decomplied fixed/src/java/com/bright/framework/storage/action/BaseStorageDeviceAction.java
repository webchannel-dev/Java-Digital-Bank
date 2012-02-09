/*    */ package com.bright.framework.storage.action;
/*    */ 
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.storage.service.StorageDeviceManager;
/*    */ 
/*    */ public abstract class BaseStorageDeviceAction extends BTransactionAction
/*    */ {
/* 29 */   private StorageDeviceManager m_storageDeviceManager = null;
/*    */ 
/*    */   public StorageDeviceManager getStorageDeviceManager()
/*    */   {
/* 33 */     return this.m_storageDeviceManager;
/*    */   }
/*    */ 
/*    */   public void setStorageDeviceManager(StorageDeviceManager a_deviceManager)
/*    */   {
/* 38 */     this.m_storageDeviceManager = a_deviceManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.action.BaseStorageDeviceAction
 * JD-Core Version:    0.6.0
 */