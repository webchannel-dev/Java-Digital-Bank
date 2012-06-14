/*    */ package com.bright.framework.storage.bean;
/*    */ 
/*    */ public abstract class BaseRemoteStorageDevice extends BaseStorageDevice
/*    */   implements RemoteStorageDevice
/*    */ {
/*    */   private String m_sAuthenticationId;
/*    */   private String m_sAuthenticationKey;
/*    */ 
/*    */   public BaseRemoteStorageDevice(String a_sFactoryClassName, String a_sFactoryDisplayName)
/*    */   {
/* 28 */     super(a_sFactoryClassName, a_sFactoryDisplayName);
/*    */   }
/*    */ 
/*    */   public String getAuthenticationId()
/*    */   {
/* 33 */     return this.m_sAuthenticationId;
/*    */   }
/*    */ 
/*    */   public void setAuthenticationId(String a_sId)
/*    */   {
/* 38 */     this.m_sAuthenticationId = a_sId;
/*    */   }
/*    */ 
/*    */   public String getAuthenticationKey()
/*    */   {
/* 43 */     return this.m_sAuthenticationKey;
/*    */   }
/*    */ 
/*    */   public void setAuthenticationKey(String a_sKey)
/*    */   {
/* 48 */     this.m_sAuthenticationKey = a_sKey;
/*    */   }
/*    */ 
/*    */   public boolean isLocalFileStorage()
/*    */   {
/* 53 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.bean.BaseRemoteStorageDevice
 * JD-Core Version:    0.6.0
 */