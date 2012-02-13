/*    */ package com.bright.assetbank.attribute.bean;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.language.constant.LanguageConstants;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class BaseAttributeValue
/*    */   implements Serializable
/*    */ {
/* 32 */   protected String m_sValue = null;
/* 33 */   protected String m_sAdditionalValue = null;
/* 34 */   protected Language m_language = (Language)LanguageConstants.k_defaultLanguage.clone();
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 38 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language displayLanguage)
/*    */   {
/* 43 */     this.m_language = displayLanguage;
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 48 */     return this.m_sValue;
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 53 */     this.m_sValue = value;
/*    */   }
/*    */ 
/*    */   public String getAdditionalValue()
/*    */   {
/* 68 */     return this.m_sAdditionalValue;
/*    */   }
/*    */ 
/*    */   public void setAdditionalValue(String a_sAdditionalValue)
/*    */   {
/* 76 */     this.m_sAdditionalValue = a_sAdditionalValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.BaseAttributeValue
 * JD-Core Version:    0.6.0
 */