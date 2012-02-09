/*     */ package com.bright.assetbank.priceband.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean.Translation;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ //import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class PriceBand extends TranslatableStringDataBean
/*     */ {
/*  37 */   private TranslatableStringDataBean m_priceBandType = null;
/*  38 */   private String m_sDescription = null;
/*  39 */   private BrightMoney m_basePrice = null;
/*  40 */   private BrightMoney m_unitPrice = null;
/*  41 */   private StringDataBean m_assetType = null;
/*     */ 
/*     */   protected PriceBand()
/*     */   {
/*  48 */     this.m_priceBandType = new TranslatableStringDataBean();
/*  49 */     this.m_basePrice = new BrightMoney();
/*  50 */     this.m_unitPrice = new BrightMoney();
/*     */   }
/*     */ 
/*     */   public BrightMoney getFirstUnitPrice()
/*     */   {
/*  61 */     BrightMoney firstUnitPrice = new BrightMoney();
/*  62 */     firstUnitPrice.setAmount(getBasePrice().getAmount() + getUnitPrice().getAmount());
/*     */ 
/*  64 */     return firstUnitPrice;
/*     */   }
/*     */ 
/*     */   public BrightMoney getBasePrice()
/*     */   {
/*  70 */     return this.m_basePrice;
/*     */   }
/*     */ 
/*     */   public void setBasePrice(BrightMoney a_sBasePrice) {
/*  74 */     this.m_basePrice = a_sBasePrice;
/*     */   }
/*     */ 
/*     */   public TranslatableStringDataBean getPriceBandType() {
/*  78 */     return this.m_priceBandType;
/*     */   }
/*     */ 
/*     */   public void setPriceBandType(TranslatableStringDataBean a_sPriceBandType) {
/*  82 */     this.m_priceBandType = a_sPriceBandType;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  87 */     if ((this.m_language != null) && (!LanguageConstants.k_defaultLanguage.equals(this.m_language)))
/*     */     {
/*  89 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, getTranslations());
/*  90 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getDescription())))
/*     */       {
/*  92 */         return translation.getDescription();
/*     */       }
/*     */     }
/*  95 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/* 100 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public BrightMoney getUnitPrice() {
/* 104 */     return this.m_unitPrice;
/*     */   }
/*     */ 
/*     */   public void setUnitPrice(BrightMoney a_sUnitPrice) {
/* 108 */     this.m_unitPrice = a_sUnitPrice;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 113 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public void setLanguage(Language language)
/*     */   {
/* 118 */     super.setLanguage(language);
/*     */ 
/* 120 */     if (this.m_priceBandType != null)
/*     */     {
/* 122 */       this.m_priceBandType.setLanguage(language);
/*     */     }
/*     */   }
/*     */ 
/*     */   public StringDataBean getAssetType()
/*     */   {
/* 153 */     if (this.m_assetType == null)
/*     */     {
/* 155 */       this.m_assetType = new StringDataBean();
/*     */     }
/* 157 */     return this.m_assetType;
/*     */   }
/*     */ 
/*     */   public void setAssetType(StringDataBean assetType)
/*     */   {
/* 163 */     this.m_assetType = assetType;
/*     */   }
/*     */ 
/*     */   public class Translation extends TranslatableStringDataBean.Translation
/*     */   {
/* 132 */     private String m_sDescription = null;
/*     */ 
/*     */     public Translation(Language a_language)
/*     */     {
/* 136 */       super(a_language);
/*     */     }
/*     */ 
/*     */     public String getDescription()
/*     */     {
/* 141 */       return this.m_sDescription;
/*     */     }
/*     */ 
/*     */     public void setDescription(String description)
/*     */     {
/* 146 */       this.m_sDescription = description;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.bean.PriceBand
 * JD-Core Version:    0.6.0
 */