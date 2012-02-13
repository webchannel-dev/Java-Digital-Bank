/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*    */ import com.bright.framework.language.bean.Translation;
/*    */ import com.bright.framework.language.constant.LanguageConstants;
/*    */ import com.bright.framework.language.util.LanguageUtils;
/*    */ import java.util.Vector;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class TranslatableStringDataBean extends BaseTranslatableStringDataBean
/*    */   implements TranslatableWithLanguage
/*    */ {
/* 34 */   private Vector m_vTranslations = null;
/*    */ 
/*    */   public TranslatableStringDataBean(long a_lId, String a_sName)
/*    */   {
/* 44 */     setId(a_lId);
/* 45 */     setName(a_sName);
/*    */   }
/*    */ 
/*    */   public TranslatableStringDataBean()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 60 */     if ((this.m_language != null) && (!LanguageConstants.k_defaultLanguage.equals(this.m_language)))
/*    */     {
/* 62 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, getTranslations());
/* 63 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getName())))
/*    */       {
/* 65 */         return translation.getName();
/*    */       }
/*    */     }
/* 68 */     return super.getName();
/*    */   }
/*    */ 
/*    */   public Translation createTranslation(Language a_language)
/*    */   {
/* 73 */     return new Translation(a_language);
/*    */   }
/*    */ 
/*    */   public Vector getTranslations()
/*    */   {
/* 78 */     if (this.m_vTranslations == null)
/*    */     {
/* 80 */       this.m_vTranslations = new Vector();
/*    */     }
/* 82 */     return this.m_vTranslations;
/*    */   }
/*    */ 
/*    */   public void setTranslations(Vector a_vTranslations)
/*    */   {
/* 87 */     this.m_vTranslations = a_vTranslations;
/*    */   }
/*    */ 
/*    */   public class Translation extends BaseTranslatableStringDataBean implements com.bright.framework.language.bean.Translation
/*    */   {
/*    */     public Translation(Language a_language)
/*    */     {
/* 94 */       setLanguage(a_language);
/*    */     }
/*    */ 
/*    */     public long getStringDataBeanId()
/*    */     {
/* 99 */       return TranslatableStringDataBean.this.m_lId;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.TranslatableStringDataBean
 * JD-Core Version:    0.6.0
 */