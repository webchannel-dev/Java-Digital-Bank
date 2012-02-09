/*     */ package com.bright.framework.news.bean;
/*     */ 
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class NewsItem extends BaseNewsItem
/*     */   implements TranslatableWithLanguage
/*     */ {
/*     */   private boolean m_bIsPublished;
/*     */   private Date m_dtCreatedDate;
/*     */   private Vector m_vTranslations;
/*     */   private boolean m_bIsTruncated;
/*     */ 
/*     */   public NewsItem()
/*     */   {
/*  36 */     this.m_bIsPublished = false;
/*  37 */     this.m_dtCreatedDate = null;
/*  38 */     this.m_vTranslations = null;
/*  39 */     this.m_bIsTruncated = false;
/*     */   }
/*     */ 
/*     */   public boolean isPublished() {
/*  43 */     return this.m_bIsPublished;
/*     */   }
/*     */ 
/*     */   public void setPublished(boolean a_bIsPublished)
/*     */   {
/*  48 */     this.m_bIsPublished = a_bIsPublished;
/*     */   }
/*     */ 
/*     */   public Date getCreatedDate()
/*     */   {
/*  53 */     return this.m_dtCreatedDate;
/*     */   }
/*     */ 
/*     */   public void setCreatedDate(Date a_createdDate)
/*     */   {
/*  58 */     this.m_dtCreatedDate = a_createdDate;
/*     */   }
/*     */ 
/*     */   public boolean getIsTruncated()
/*     */   {
/*  63 */     return this.m_bIsTruncated;
/*     */   }
/*     */ 
/*     */   public void setIsTruncated(boolean a_bIsTruncated)
/*     */   {
/*  68 */     this.m_bIsTruncated = a_bIsTruncated;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/*  73 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/*  78 */     if (this.m_vTranslations == null)
/*     */     {
/*  80 */       this.m_vTranslations = new Vector();
/*     */     }
/*  82 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_vTranslations)
/*     */   {
/*  87 */     this.m_vTranslations = a_vTranslations;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/*  95 */     if ((this.m_language != null) && (!LanguageConstants.k_defaultLanguage.equals(this.m_language)))
/*     */     {
/*  97 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, getTranslations());
/*  98 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getContent())))
/*     */       {
/* 100 */         return translation.getContent();
/*     */       }
/*     */     }
/* 103 */     return super.getContent();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 111 */     if ((this.m_language != null) && (!LanguageConstants.k_defaultLanguage.equals(this.m_language)))
/*     */     {
/* 113 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, getTranslations());
/* 114 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getName())))
/*     */       {
/* 116 */         return translation.getName();
/*     */       }
/*     */     }
/* 119 */     return super.getName();
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseNewsItem implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 126 */       setLanguage(a_language);
/*     */     }
/*     */ 
/*     */     public long getStringDataBeanId()
/*     */     {
/* 131 */       return NewsItem.this.m_lId;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.news.bean.NewsItem
 * JD-Core Version:    0.6.0
 */