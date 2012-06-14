/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.RefDataItem;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public class BaseUsageTypeFormat extends RefDataItem
/*    */ {
/* 30 */   protected Language m_language = null;
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 34 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language language)
/*    */   {
/* 39 */     this.m_language = language;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.BaseUsageTypeFormat
 * JD-Core Version:    0.6.0
 */