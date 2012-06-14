/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public abstract class BaseTranslatableStringDataBean extends StringDataBean
/*    */ {
/*    */   protected Language m_language;
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 32 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language language)
/*    */   {
/* 37 */     this.m_language = language;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.BaseTranslatableStringDataBean
 * JD-Core Version:    0.6.0
 */