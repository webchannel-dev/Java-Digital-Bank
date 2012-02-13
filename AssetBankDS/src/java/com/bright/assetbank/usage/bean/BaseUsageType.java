/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import com.bright.assetbank.user.bean.GroupExcludable;
/*    */ import com.bright.framework.common.bean.RefDataItem;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public class BaseUsageType extends RefDataItem
/*    */   implements GroupExcludable
/*    */ {
/* 31 */   private String m_sAssetTypeDescription = null;
/* 32 */   private String m_sMessage = null;
/* 33 */   protected Language m_language = null;
/*    */ 
/*    */   public String getAssetTypeDescription()
/*    */   {
/* 37 */     return this.m_sAssetTypeDescription;
/*    */   }
/*    */ 
/*    */   public void setAssetTypeDescription(String a_sAssetTypeDescription)
/*    */   {
/* 42 */     this.m_sAssetTypeDescription = a_sAssetTypeDescription;
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 47 */     return this.m_sMessage;
/*    */   }
/*    */ 
/*    */   public void setMessage(String a_sMessage)
/*    */   {
/* 52 */     this.m_sMessage = a_sMessage;
/*    */   }
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 57 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language language)
/*    */   {
/* 62 */     this.m_language = language;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.BaseUsageType
 * JD-Core Version:    0.6.0
 */