/*     */ package com.bright.framework.category.bean;
/*     */ 
/*     */ import com.bright.framework.category.util.CategoryUtil;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ 
/*     */ public class CategoryWithLanguage extends CategoryWrapper<CategoryWithLanguage>
/*     */   implements Category, TranslatableWithLanguage
/*     */ {
/*     */   private Language m_language;
/*     */   private String m_sJSUnicodeName;
/*     */ 
/*     */   public CategoryWithLanguage(Category a_category, Language a_language)
/*     */   {
/*  33 */     super(a_category);
/*  34 */     this.m_language = a_language;
/*     */   }
/*     */ 
/*     */   public Language getLanguage()
/*     */   {
/*  39 */     return this.m_language;
/*     */   }
/*     */ 
/*     */   public void setLanguage(Language a_language)
/*     */   {
/*  44 */     this.m_language = a_language;
/*  45 */     this.m_sJSUnicodeName = null;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/*  51 */     super.setName(a_sName);
/*  52 */     this.m_sJSUnicodeName = null;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  62 */     return getName(this.m_language);
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  72 */     return getDescription(this.m_language);
/*     */   }
/*     */ 
/*     */   public String getSummary()
/*     */   {
/*  82 */     return getSummary(this.m_language);
/*     */   }
/*     */ 
/*     */   public int compareTo(Object obj)
/*     */   {
/*  91 */     return CategoryUtil.compareTo(this, obj);
/*     */   }
/*     */ 
/*     */   public String getFullName()
/*     */   {
/*  97 */     return CategoryUtil.getFullName(this);
/*     */   }
/*     */ 
/*     */   public String getJavaScriptEncodedName()
/*     */   {
/* 103 */     return StringUtil.getJavascriptLiteralString(getName());
/*     */   }
/*     */ 
/*     */   public String getJSUnicodeName()
/*     */   {
/* 112 */     if (this.m_sJSUnicodeName == null)
/*     */     {
/* 115 */       this.m_sJSUnicodeName = CategoryUtil.getJSUnicodeName(getName());
/*     */     }
/*     */ 
/* 118 */     return this.m_sJSUnicodeName;
/*     */   }
/*     */ 
/*     */   public String getJSUnicodeFullName()
/*     */   {
/* 130 */     return CategoryUtil.getJSUnicodeFullName(this);
/*     */   }
/*     */ 
/*     */   public String getNameWithEscapedQuotes()
/*     */   {
/* 140 */     return CategoryUtil.getNameWithEscapedQuotes(this);
/*     */   }
/*     */ 
/*     */   protected CategoryWithLanguage doWrapCategory(Category a_catToBeWrapped)
/*     */   {
/* 154 */     if ((a_catToBeWrapped instanceof CategoryWithLanguage))
/*     */     {
/* 160 */       CategoryWithLanguage cwlToBeWrapped = (CategoryWithLanguage)a_catToBeWrapped;
/* 161 */       return new CategoryWithLanguage(cwlToBeWrapped.getWrappedCategory(), this.m_language);
/*     */     }
/*     */ 
/* 165 */     return new CategoryWithLanguage(a_catToBeWrapped, this.m_language);
/*     */   }
/*     */ 
/*     */   public CategoryWithLanguage clone()
/*     */   {
/* 172 */     return new CategoryWithLanguage(getWrappedCategory().clone(), this.m_language);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.CategoryWithLanguage
 * JD-Core Version:    0.6.0
 */