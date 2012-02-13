/*    */ package com.bright.framework.language.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import java.util.List;
/*    */ 
/*    */ public class LanguageForm extends Bn2Form
/*    */ {
/* 31 */   private List m_languages = null;
/* 32 */   private Language m_language = null;
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 36 */     if (this.m_language == null)
/*    */     {
/* 38 */       this.m_language = new Language();
/*    */     }
/* 40 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language a_language)
/*    */   {
/* 45 */     this.m_language = a_language;
/*    */   }
/*    */ 
/*    */   public List getLanguages()
/*    */   {
/* 50 */     return this.m_languages;
/*    */   }
/*    */ 
/*    */   public void setLanguages(List a_languages)
/*    */   {
/* 55 */     this.m_languages = a_languages;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.language.form.LanguageForm
 * JD-Core Version:    0.6.0
 */