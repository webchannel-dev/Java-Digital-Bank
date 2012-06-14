/*    */ package com.bright.assetbank.marketing.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.language.constant.LanguageConstants;
/*    */ 
/*    */ public class BaseMarketingGroup extends DataBean
/*    */ {
/* 24 */   protected String m_sName = null;
/* 25 */   protected String m_sDescription = null;
/* 26 */   protected Language m_language = (Language)LanguageConstants.k_defaultLanguage.clone();
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 30 */     return this.m_sDescription;
/*    */   }
/*    */ 
/*    */   public void setDescription(String description) {
/* 34 */     this.m_sDescription = description;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 38 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 42 */     this.m_sName = name;
/*    */   }
/*    */ 
/*    */   public Language getLanguage() {
/* 46 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language language) {
/* 50 */     this.m_language = language;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.bean.BaseMarketingGroup
 * JD-Core Version:    0.6.0
 */