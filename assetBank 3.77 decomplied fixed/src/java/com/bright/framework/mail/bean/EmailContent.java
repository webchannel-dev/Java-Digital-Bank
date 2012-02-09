/*     */ package com.bright.framework.mail.bean;
/*     */ 
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class EmailContent extends BaseEmailContent
/*     */   implements TranslatableWithLanguage
/*     */ {
/*     */   private Vector m_vTranslations;
/*     */ 
/*     */   public EmailContent()
/*     */   {
/*  37 */     this.m_vTranslations = null;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language) {
/*  41 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/*  46 */     if (this.m_vTranslations == null)
/*     */     {
/*  48 */       this.m_vTranslations = new Vector();
/*     */     }
/*  50 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_translations)
/*     */   {
/*  55 */     this.m_vTranslations = a_translations;
/*     */   }
/*     */ 
/*     */   public String getBody(Language a_language)
/*     */   {
/*  60 */     if (!LanguageConstants.k_defaultLanguage.equals(a_language))
/*     */     {
/*  62 */       Translation translation = (Translation)LanguageUtils.getTranslation(a_language, getTranslations());
/*  63 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getBody())))
/*     */       {
/*  65 */         return translation.getBody();
/*     */       }
/*     */     }
/*  68 */     return getBody();
/*     */   }
/*     */ 
/*     */   public String getBodyForLanguage(Language a_language)
/*     */   {
/*  73 */     if (LanguageConstants.k_defaultLanguage.equals(a_language))
/*     */     {
/*  75 */       return getBody();
/*     */     }
/*  77 */     Translation translation = (Translation)LanguageUtils.getTranslation(a_language, getTranslations());
/*  78 */     if (translation != null)
/*     */     {
/*  80 */       return translation.getBody();
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   public String getSubject(Language a_language)
/*     */   {
/*  87 */     if (!LanguageConstants.k_defaultLanguage.equals(a_language))
/*     */     {
/*  89 */       Translation translation = (Translation)LanguageUtils.getTranslation(a_language, getTranslations());
/*  90 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getSubject())))
/*     */       {
/*  92 */         return translation.getSubject();
/*     */       }
/*     */     }
/*  95 */     return getSubject();
/*     */   }
/*     */ 
/*     */   public String getSubjectForLanguage(Language a_language)
/*     */   {
/* 100 */     if (LanguageConstants.k_defaultLanguage.equals(a_language))
/*     */     {
/* 102 */       return getSubject();
/*     */     }
/* 104 */     Translation translation = (Translation)LanguageUtils.getTranslation(a_language, getTranslations());
/* 105 */     if (translation != null)
/*     */     {
/* 107 */       return translation.getSubject();
/*     */     }
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseEmailContent
/*     */     implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 121 */       this.m_language = a_language;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.bean.EmailContent
 * JD-Core Version:    0.6.0
 */