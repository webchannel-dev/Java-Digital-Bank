/*    */ package com.bright.framework.language.util;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.language.bean.Translatable;
/*    */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*    */ import com.bright.framework.language.bean.Translation;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class LanguageUtils
/*    */ {
/*    */   public static Translation getTranslation(Language a_language, List a_translations)
/*    */   {
/* 45 */     if (a_translations != null)
/*    */     {
/* 47 */       Iterator itTransaltions = a_translations.iterator();
/* 48 */       while (itTransaltions.hasNext())
/*    */       {
/* 50 */         Translation translation = (Translation)itTransaltions.next();
/* 51 */         if (translation.getLanguage().equals(a_language))
/*    */         {
/* 53 */           return translation;
/*    */         }
/*    */       }
/*    */     }
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */   public static void setLanguageOnAll(List<? extends TranslatableWithLanguage> a_translatables, Language a_Language)
/*    */   {
/* 65 */     if (a_translatables != null)
/*    */     {
/* 67 */       for (TranslatableWithLanguage translatable : a_translatables)
/*    */       {
/* 69 */         translatable.setLanguage(a_Language);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void createEmptyTranslations(Translatable a_translatable, int a_iNumToCreate)
/*    */   {
/* 80 */     Vector translastions = a_translatable.getTranslations();
/* 81 */     for (int i = 0; i < a_iNumToCreate; i++)
/*    */     {
/* 83 */       translastions.add(a_translatable.createTranslation(new Language()));
/*    */     }
/*    */   }
/*    */ 
/*    */   public static boolean shouldUseStemming(String a_sLanguageCode)
/*    */   {
/* 93 */     return "en".equals(a_sLanguageCode);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.language.util.LanguageUtils
 * JD-Core Version:    0.6.0
 */