/*    */ package com.bright.assetbank.commercialoption.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public abstract class BaseCommercialOption extends StringDataBean
/*    */ {
/*    */   protected Language m_language;
/* 30 */   private String m_sDescription = null;
/* 31 */   private String m_sTerms = null;
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 35 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language language)
/*    */   {
/* 40 */     this.m_language = language;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 48 */     return this.m_sDescription;
/*    */   }
/*    */ 
/*    */   public void setDescription(String a_sDescription)
/*    */   {
/* 56 */     this.m_sDescription = a_sDescription;
/*    */   }
/*    */ 
/*    */   public String getTerms()
/*    */   {
/* 64 */     return this.m_sTerms;
/*    */   }
/*    */ 
/*    */   public void setTerms(String a_sTerms)
/*    */   {
/* 72 */     this.m_sTerms = a_sTerms;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.commercialoption.bean.BaseCommercialOption
 * JD-Core Version:    0.6.0
 */