/*    */ package com.bright.assetbank.marketing.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public abstract class BaseMarketingEmail extends StringDataBean
/*    */ {
/* 29 */   private String m_sIntroduction = null;
/* 30 */   private Language m_language = null;
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 34 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language language) {
/* 38 */     this.m_language = language;
/*    */   }
/*    */ 
/*    */   public String getIntroduction() {
/* 42 */     return this.m_sIntroduction;
/*    */   }
/*    */ 
/*    */   public void setIntroduction(String introduction) {
/* 46 */     this.m_sIntroduction = introduction;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.bean.BaseMarketingEmail
 * JD-Core Version:    0.6.0
 */