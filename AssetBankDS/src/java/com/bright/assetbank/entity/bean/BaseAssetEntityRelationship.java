/*    */ package com.bright.assetbank.entity.bean;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public abstract class BaseAssetEntityRelationship
/*    */ {
/* 28 */   protected String m_sToName = null;
/* 29 */   protected String m_sFromName = null;
/* 30 */   protected String m_sToNamePlural = null;
/* 31 */   protected String m_sFromNamePlural = null;
/* 32 */   protected Language m_language = null;
/*    */ 
/*    */   public String getFromName()
/*    */   {
/* 36 */     return this.m_sFromName;
/*    */   }
/*    */ 
/*    */   public void setFromName(String fromName) {
/* 40 */     this.m_sFromName = fromName;
/*    */   }
/*    */ 
/*    */   public String getToName() {
/* 44 */     return this.m_sToName;
/*    */   }
/*    */ 
/*    */   public void setToName(String toName) {
/* 48 */     this.m_sToName = toName;
/*    */   }
/*    */ 
/*    */   public Language getLanguage() {
/* 52 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language language) {
/* 56 */     this.m_language = language;
/*    */   }
/*    */ 
/*    */   public String getFromNamePlural() {
/* 60 */     return this.m_sFromNamePlural;
/*    */   }
/*    */ 
/*    */   public void setFromNamePlural(String fromNamePlural) {
/* 64 */     this.m_sFromNamePlural = fromNamePlural;
/*    */   }
/*    */ 
/*    */   public String getToNamePlural() {
/* 68 */     return this.m_sToNamePlural;
/*    */   }
/*    */ 
/*    */   public void setToNamePlural(String toNamePlural) {
/* 72 */     this.m_sToNamePlural = toNamePlural;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.bean.BaseAssetEntityRelationship
 * JD-Core Version:    0.6.0
 */