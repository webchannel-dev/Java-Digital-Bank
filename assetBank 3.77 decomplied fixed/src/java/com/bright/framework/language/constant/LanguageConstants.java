/*    */ package com.bright.framework.language.constant;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public abstract interface LanguageConstants
/*    */ {
/*    */   public static final long k_lDefaultLanguageId = 1L;
/*    */   public static final String k_sDefaultLanguageName = "English";
/*    */   public static final String k_sDefaultLanguageCode = "en";
/* 32 */   public static final Language k_defaultLanguage = new Language(1L, "English", "en", true, true);
/*    */   public static final String k_sAttributeLanguage = "currentLanguage";
/*    */   public static final String k_sParamNameLanguageId = "languageId";
/*    */   public static final String k_sParamNameLanguage = "lang";
/*    */   public static final String k_sMessageSetting_LanguageNameRequired = "languageNameRequired";
/*    */   public static final String k_sMessageSetting_LanguageCodeRequired = "languageCodeRequired";
/*    */   public static final String k_sMessageSetting_LanguageNameNotUnique = "languageNameNotUnique";
/*    */   public static final String k_sMessageSetting_LanguageCodeNotUnique = "languageCodeNotUnique";
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.language.constant.LanguageConstants
 * JD-Core Version:    0.6.0
 */