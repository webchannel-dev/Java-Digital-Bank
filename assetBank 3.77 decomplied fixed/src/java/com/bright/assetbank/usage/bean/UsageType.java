/*     */ package com.bright.assetbank.usage.bean;
/*     */ 
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class UsageType extends BaseUsageType
/*     */   implements TranslatableWithLanguage
/*     */ {
/*     */   private boolean m_bIsEditable;
/*     */   private long m_lParentId;
/*     */   private int m_iDownloadTabId;
/*     */   private long m_lAssetTypeId;
/*     */   private boolean m_bCanEnterDetails;
/*     */   private boolean m_bDetailsMandatory;
/*     */   private boolean m_bDownloadOriginal;
/*     */   private boolean m_bHighResolution;
/*     */   private boolean m_bSelected;
/*     */   private Vector m_vTranslations;
/*     */   private Vector<UsageType> m_vChildren;
/*     */ 
/*     */   public UsageType()
/*     */   {
/*  43 */     this.m_bIsEditable = false;
/*  44 */     this.m_lParentId = 0L;
/*  45 */     this.m_iDownloadTabId = 3;
/*  46 */     this.m_lAssetTypeId = 0L;
/*  47 */     this.m_bCanEnterDetails = false;
/*  48 */     this.m_bDetailsMandatory = false;
/*  49 */     this.m_bDownloadOriginal = false;
/*  50 */     this.m_bHighResolution = false;
/*  51 */     this.m_bSelected = false;
/*     */ 
/*  54 */     this.m_vTranslations = null;
/*     */ 
/*  56 */     this.m_vChildren = null;
/*     */   }
/*     */ 
/*     */   public boolean isEditable()
/*     */   {
/*  61 */     return this.m_bIsEditable;
/*     */   }
/*     */ 
/*     */   public void setEditable(boolean a_sIsEditable) {
/*  65 */     this.m_bIsEditable = a_sIsEditable;
/*     */   }
/*     */ 
/*     */   public long getParentId()
/*     */   {
/*  70 */     return this.m_lParentId;
/*     */   }
/*     */ 
/*     */   public void setParentId(long a_sParentId) {
/*  74 */     this.m_lParentId = a_sParentId;
/*     */   }
/*     */ 
/*     */   public int getDownloadTabId()
/*     */   {
/*  79 */     return this.m_iDownloadTabId;
/*     */   }
/*     */ 
/*     */   public void setDownloadTabId(int a_iDownloadTabId)
/*     */   {
/*  84 */     this.m_iDownloadTabId = a_iDownloadTabId;
/*     */   }
/*     */ 
/*     */   public boolean getCanEnterDetails()
/*     */   {
/*  89 */     return this.m_bCanEnterDetails;
/*     */   }
/*     */ 
/*     */   public void setCanEnterDetails(boolean a_bCanEnterDetails) {
/*  93 */     this.m_bCanEnterDetails = a_bCanEnterDetails;
/*     */   }
/*     */ 
/*     */   public boolean getDetailsMandatory()
/*     */   {
/*  98 */     return this.m_bDetailsMandatory;
/*     */   }
/*     */ 
/*     */   public void setDetailsMandatory(boolean a_bDetailsMandatory) {
/* 102 */     this.m_bDetailsMandatory = a_bDetailsMandatory;
/*     */   }
/*     */ 
/*     */   public long getAssetTypeId()
/*     */   {
/* 107 */     return this.m_lAssetTypeId;
/*     */   }
/*     */ 
/*     */   public void setAssetTypeId(long a_lAssetTypeId) {
/* 111 */     this.m_lAssetTypeId = a_lAssetTypeId;
/*     */   }
/*     */ 
/*     */   public boolean getDownloadOriginal() {
/* 115 */     return this.m_bDownloadOriginal;
/*     */   }
/*     */ 
/*     */   public void setDownloadOriginal(boolean a_sDownloadOriginal) {
/* 119 */     this.m_bDownloadOriginal = a_sDownloadOriginal;
/*     */   }
/*     */ 
/*     */   public boolean getHighResolution()
/*     */   {
/* 124 */     return this.m_bHighResolution;
/*     */   }
/*     */ 
/*     */   public void setHighResolution(boolean a_bHighResolution) {
/* 128 */     this.m_bHighResolution = a_bHighResolution;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 134 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 136 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 137 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getDescription())))
/*     */       {
/* 139 */         return translation.getDescription();
/*     */       }
/*     */     }
/* 142 */     return super.getDescription();
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 147 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 149 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 150 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getMessage())))
/*     */       {
/* 152 */         return translation.getMessage();
/*     */       }
/*     */     }
/* 155 */     return super.getMessage();
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/* 160 */     if (this.m_vTranslations == null)
/*     */     {
/* 162 */       this.m_vTranslations = new Vector();
/*     */     }
/* 164 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_vTranslations)
/*     */   {
/* 169 */     this.m_vTranslations = a_vTranslations;
/*     */   }
/*     */ 
/*     */   public Vector<UsageType> getChildren()
/*     */   {
/* 174 */     if (this.m_vChildren == null)
/*     */     {
/* 176 */       this.m_vChildren = new Vector();
/*     */     }
/* 178 */     return this.m_vChildren;
/*     */   }
/*     */ 
/*     */   public void setChildren(Vector<UsageType> a_vChildren)
/*     */   {
/* 183 */     this.m_vChildren = a_vChildren;
/*     */   }
/*     */ 
/*     */   public boolean getSelected()
/*     */   {
/* 188 */     return this.m_bSelected;
/*     */   }
/*     */ 
/*     */   public void setSelected(boolean a_bSelected) {
/* 192 */     this.m_bSelected = a_bSelected;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 199 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseUsageType
/*     */     implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 210 */       this.m_language = a_language;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.UsageType
 * JD-Core Version:    0.6.0
 */