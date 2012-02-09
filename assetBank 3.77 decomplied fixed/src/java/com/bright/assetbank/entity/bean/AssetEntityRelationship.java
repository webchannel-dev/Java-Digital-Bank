/*     */ package com.bright.assetbank.entity.bean;
/*     */ 
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.Translatable;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class AssetEntityRelationship extends BaseAssetEntityRelationship
/*     */   implements Translatable
/*     */ {
/*  35 */   private long m_lRelatesToAssetEntityId = 0L;
/*     */ 
/*  38 */   private Vector m_vTranslations = null;
/*     */ 
/*     */   public long getRelatesToAssetEntityId()
/*     */   {
/*  42 */     return this.m_lRelatesToAssetEntityId;
/*     */   }
/*     */ 
/*     */   public void setRelatesToAssetEntityId(long a_lRelatesToAssetEntityId)
/*     */   {
/*  47 */     this.m_lRelatesToAssetEntityId = a_lRelatesToAssetEntityId;
/*     */   }
/*     */ 
/*     */   public boolean getCanRelateToAssetEntity(long a_lRelatesToAssetEntityId)
/*     */   {
/*  52 */     return (this.m_lRelatesToAssetEntityId <= 0L) || (this.m_lRelatesToAssetEntityId == a_lRelatesToAssetEntityId);
/*     */   }
/*     */ 
/*     */   public String getFromName()
/*     */   {
/*  57 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/*  59 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  60 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getFromName())))
/*     */       {
/*  62 */         return translation.getFromName();
/*     */       }
/*     */     }
/*  65 */     return super.getFromName();
/*     */   }
/*     */ 
/*     */   public String getFromNamePlural()
/*     */   {
/*  70 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/*  72 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  73 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getFromNamePlural())))
/*     */       {
/*  75 */         return translation.getFromNamePlural();
/*     */       }
/*     */     }
/*  78 */     return super.getFromNamePlural();
/*     */   }
/*     */ 
/*     */   public String getToName()
/*     */   {
/*  83 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/*  85 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  86 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getToName())))
/*     */       {
/*  88 */         return translation.getToName();
/*     */       }
/*     */     }
/*  91 */     return super.getToName();
/*     */   }
/*     */ 
/*     */   public String getToNamePlural()
/*     */   {
/*  96 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/*  98 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  99 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getToNamePlural())))
/*     */       {
/* 101 */         return translation.getToNamePlural();
/*     */       }
/*     */     }
/* 104 */     return super.getToNamePlural();
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/* 109 */     if (this.m_vTranslations == null)
/*     */     {
/* 111 */       this.m_vTranslations = new Vector();
/*     */     }
/* 113 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_vTranslations)
/*     */   {
/* 118 */     this.m_vTranslations = a_vTranslations;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 123 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseAssetEntityRelationship
/*     */     implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 134 */       this.m_language = a_language;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.bean.AssetEntityRelationship
 * JD-Core Version:    0.6.0
 */