/*    */ package com.bright.framework.mail.bean;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public class BaseEmailContent
/*    */ {
/* 28 */   protected Language m_language = null;
/* 29 */   private String m_sSubject = null;
/* 30 */   private String m_sBody = null;
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
/*    */ 
/*    */   public void setSubject(String a_sSubject)
/*    */   {
/* 44 */     this.m_sSubject = a_sSubject;
/*    */   }
/*    */ 
/*    */   public String getSubject()
/*    */   {
/* 49 */     return this.m_sSubject;
/*    */   }
/*    */ 
/*    */   public void setBody(String a_sBody)
/*    */   {
/* 54 */     this.m_sBody = a_sBody;
/*    */   }
/*    */ 
/*    */   public String getBody()
/*    */   {
/* 59 */     return this.m_sBody;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.bean.BaseEmailContent
 * JD-Core Version:    0.6.0
 */