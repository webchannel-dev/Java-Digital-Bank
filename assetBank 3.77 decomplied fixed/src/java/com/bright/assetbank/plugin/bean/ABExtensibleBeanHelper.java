/*    */ package com.bright.assetbank.plugin.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class ABExtensibleBeanHelper
/*    */   implements Serializable
/*    */ {
/* 25 */   private Map<String, Serializable> m_extensionData = new HashMap();
/*    */ 
/*    */   public void setExtensionData(String a_sExtensionKey, Serializable a_extensionData)
/*    */   {
/* 37 */     this.m_extensionData.put(a_sExtensionKey, a_extensionData);
/*    */   }
/*    */ 
/*    */   public Serializable getExtensionData(String a_sExtensionKey)
/*    */   {
/* 47 */     return (Serializable)this.m_extensionData.get(a_sExtensionKey);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.bean.ABExtensibleBeanHelper
 * JD-Core Version:    0.6.0
 */