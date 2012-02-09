/*     */ package com.bright.assetbank.commercialoption.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class CommercialOption extends BaseCommercialOption
/*     */   implements TranslatableWithLanguage
/*     */ {
/*  38 */   private BrightMoney m_price = null;
/*  39 */   private boolean isDisabled = false;
/*     */ 
/*  41 */   private Vector m_vTranslations = null;
/*     */ 
/*     */   public CommercialOption()
/*     */   {
/*  45 */     this.m_price = new BrightMoney();
/*     */   }
/*     */ 
/*     */   public BrightMoney getPrice()
/*     */   {
/*  53 */     if (this.m_price == null)
/*     */     {
/*  55 */       this.m_price = new BrightMoney();
/*     */     }
/*  57 */     return this.m_price;
/*     */   }
/*     */ 
/*     */   public void setPrice(BrightMoney a_price)
/*     */   {
/*  66 */     this.m_price = a_price;
/*     */   }
/*     */ 
/*     */   public boolean isDisabled()
/*     */   {
/*  75 */     return this.isDisabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(boolean a_sIsDisabled)
/*     */   {
/*  84 */     this.isDisabled = a_sIsDisabled;
/*     */   }
/*     */ 
/*     */   public void copyFrom(CommercialOption a_toCopy)
/*     */   {
/* 102 */     setId(a_toCopy.getId());
/* 103 */     setName(a_toCopy.getName());
/* 104 */     setDescription(a_toCopy.getDescription());
/* 105 */     setTerms(a_toCopy.getTerms());
/* 106 */     getPrice().setAmount(a_toCopy.getPrice().getAmount());
/*     */ 
/* 108 */     setTranslations((Vector)a_toCopy.getTranslations().clone());
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 114 */     if ((this.m_language != null) && (!LanguageConstants.k_defaultLanguage.equals(this.m_language)))
/*     */     {
/* 116 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, getTranslations());
/* 117 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getDescription())))
/*     */       {
/* 119 */         return translation.getDescription();
/*     */       }
/*     */     }
/* 122 */     return super.getDescription();
/*     */   }
/*     */ 
/*     */   public String getTerms()
/*     */   {
/* 128 */     if ((this.m_language != null) && (!LanguageConstants.k_defaultLanguage.equals(this.m_language)))
/*     */     {
/* 130 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, getTranslations());
/* 131 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getTerms())))
/*     */       {
/* 133 */         return translation.getTerms();
/*     */       }
/*     */     }
/* 136 */     return super.getTerms();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 142 */     if ((this.m_language != null) && (!LanguageConstants.k_defaultLanguage.equals(this.m_language)))
/*     */     {
/* 144 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, getTranslations());
/* 145 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getName())))
/*     */       {
/* 147 */         return translation.getName();
/*     */       }
/*     */     }
/* 150 */     return super.getName();
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/* 156 */     if (this.m_vTranslations == null)
/*     */     {
/* 158 */       this.m_vTranslations = new Vector();
/*     */     }
/* 160 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_vTranslations)
/*     */   {
/* 166 */     this.m_vTranslations = a_vTranslations;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 172 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseCommercialOption
/*     */     implements com.bright.framework.language.bean.Translation 
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 182 */       setLanguage(a_language);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.commercialoption.bean.CommercialOption
 * JD-Core Version:    0.6.0
 */