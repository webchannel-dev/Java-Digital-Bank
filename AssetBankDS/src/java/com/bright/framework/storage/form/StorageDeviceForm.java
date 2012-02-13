/*    */ package com.bright.framework.storage.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.storage.bean.RemoteStorageDevice;
/*    */ import com.bright.framework.storage.bean.StorageDevice;
/*    */ import com.bright.framework.storage.service.StorageDeviceFactory;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class StorageDeviceForm extends Bn2Form
/*    */ {
/* 34 */   private StorageDevice m_device = null;
/* 35 */   private String m_sFactoryClassName = null;
/* 36 */   private Vector<StorageDevice> m_devices = null;
/*    */   private boolean m_bRequiredForStorage;
/*    */   private List<StorageDeviceFactory<StorageDevice>> m_factories;
/*    */ 
/*    */   public StorageDevice getDevice()
/*    */   {
/* 42 */     return this.m_device;
/*    */   }
/*    */ 
/*    */   public void setDevice(StorageDevice m_device)
/*    */   {
/* 47 */     this.m_device = m_device;
/*    */   }
/*    */ 
/*    */   public Vector<StorageDevice> getDevices()
/*    */   {
/* 52 */     return this.m_devices;
/*    */   }
/*    */ 
/*    */   public void setDevices(Vector<StorageDevice> m_devices)
/*    */   {
/* 57 */     this.m_devices = m_devices;
/*    */   }
/*    */ 
/*    */   public boolean isRequiredForStorage()
/*    */   {
/* 62 */     return this.m_bRequiredForStorage;
/*    */   }
/*    */ 
/*    */   public void setRequiredForStorage(boolean a_bRequiredForStorage)
/*    */   {
/* 67 */     this.m_bRequiredForStorage = a_bRequiredForStorage;
/*    */   }
/*    */ 
/*    */   public String getFactoryClassName()
/*    */   {
/* 72 */     return this.m_sFactoryClassName;
/*    */   }
/*    */ 
/*    */   public void setFactoryClassName(String a_sFactoryClassName)
/*    */   {
/* 77 */     this.m_sFactoryClassName = a_sFactoryClassName;
/*    */   }
/*    */ 
/*    */   public List<StorageDeviceFactory<StorageDevice>> getFactories()
/*    */   {
/* 82 */     return this.m_factories;
/*    */   }
/*    */ 
/*    */   public void setFactories(List<StorageDeviceFactory<StorageDevice>> a_factories)
/*    */   {
/* 87 */     this.m_factories = a_factories;
/*    */   }
/*    */ 
/*    */   public boolean isStorageDeviceRemote()
/*    */   {
/* 92 */     return (this.m_device != null) && ((this.m_device instanceof RemoteStorageDevice));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.form.StorageDeviceForm
 * JD-Core Version:    0.6.0
 */