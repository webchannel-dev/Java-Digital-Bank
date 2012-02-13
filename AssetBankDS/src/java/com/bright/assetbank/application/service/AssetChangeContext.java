/*    */ package com.bright.assetbank.application.service;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class AssetChangeContext
/*    */ {
/* 31 */   private Map<String, Object> m_mapAttributes = new HashMap();
/*    */ 
/*    */   public void setAttribute(String a_sParticipantName, String a_sName, Object a_value)
/*    */   {
/* 53 */     this.m_mapAttributes.put(a_sParticipantName + "_" + a_sName, a_value);
/*    */   }
/*    */ 
/*    */   public Object getAttribute(String a_sParticipantName, String a_sName)
/*    */   {
/* 61 */     return this.m_mapAttributes.get(a_sParticipantName + "_" + a_sName);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetChangeContext
 * JD-Core Version:    0.6.0
 */