/*     */ package com.bright.assetbank.attribute.bean;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseAttribute extends DataBean
/*     */   implements Serializable
/*     */ {
/*  33 */   protected String m_sLabel = null;
/*  34 */   protected String m_sDefaultValue = null;
/*  35 */   protected String m_sValueIfNotVisible = null;
/*  36 */   protected String m_sHelpText = null;
/*  37 */   protected String m_sDisplayName = null;
/*  38 */   protected String m_sAltText = null;
/*  39 */   protected String m_sInputMask = null;
/*     */ 
/*  41 */   protected Language m_language = (Language)LanguageConstants.k_defaultLanguage.clone();
/*     */ 
/*     */   public void setLabel(String a_sLabel)
/*     */   {
/*  45 */     this.m_sLabel = a_sLabel;
/*     */   }
/*     */ 
/*     */   public String getLabel()
/*     */   {
/*  50 */     return this.m_sLabel;
/*     */   }
/*     */ 
/*     */   public String getDefaultValue()
/*     */   {
/*  56 */     return this.m_sDefaultValue;
/*     */   }
/*     */ 
/*     */   public void setDefaultValue(String a_sDefaultValue)
/*     */   {
/*  62 */     this.m_sDefaultValue = a_sDefaultValue;
/*     */   }
/*     */ 
/*     */   public String getValueIfNotVisible()
/*     */   {
/*  68 */     return this.m_sValueIfNotVisible;
/*     */   }
/*     */ 
/*     */   public void setValueIfNotVisible(String a_sValueIfNotVisible)
/*     */   {
/*  74 */     this.m_sValueIfNotVisible = a_sValueIfNotVisible;
/*     */   }
/*     */ 
/*     */   public String getHelpText()
/*     */   {
/*  79 */     return this.m_sHelpText;
/*     */   }
/*     */ 
/*     */   public void setHelpText(String a_sHelpText)
/*     */   {
/*  84 */     this.m_sHelpText = a_sHelpText;
/*     */   }
/*     */ 
/*     */   public String getDisplayName()
/*     */   {
/*  89 */     return this.m_sDisplayName;
/*     */   }
/*     */ 
/*     */   public void setDisplayName(String a_sDisplayName)
/*     */   {
/*  94 */     this.m_sDisplayName = a_sDisplayName;
/*     */   }
/*     */ 
/*     */   public String getAltText()
/*     */   {
/*  99 */     return this.m_sAltText;
/*     */   }
/*     */ 
/*     */   public void setAltText(String a_sAltText)
/*     */   {
/* 104 */     this.m_sAltText = a_sAltText;
/*     */   }
/*     */ 
/*     */   public void setLanguage(Language a_displayLanguage)
/*     */   {
/* 116 */     this.m_language = a_displayLanguage;
/*     */   }
/*     */ 
/*     */   public Language getLanguage()
/*     */   {
/* 121 */     return this.m_language;
/*     */   }
/*     */ 
/*     */   public void setInputMask(String a_sInputMask)
/*     */   {
/* 126 */     this.m_sInputMask = a_sInputMask;
/*     */   }
/*     */ 
/*     */   public String getInputMask()
/*     */   {
/* 131 */     return this.m_sInputMask;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.BaseAttribute
 * JD-Core Version:    0.6.0
 */