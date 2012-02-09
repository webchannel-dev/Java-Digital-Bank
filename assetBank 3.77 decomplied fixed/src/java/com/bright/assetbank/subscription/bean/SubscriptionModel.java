/*     */ package com.bright.assetbank.subscription.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class SubscriptionModel extends BaseSubscriptionModel
/*     */   implements TranslatableWithLanguage
/*     */ {
/*  42 */   private boolean m_bInactive = false;
/*  43 */   private BrightMoney m_price = null;
/*  44 */   private BrightNaturalNumber m_iNoOfDownloads = null;
/*  45 */   private BrightNaturalNumber m_iDuration = null;
/*     */ 
/*  48 */   private BrightMoney m_bestPrice = null;
/*     */   private Vector m_groups;
/*     */   private Vector m_upgrades;
/*  57 */   private boolean m_bHasActiveSubscriptions = false;
/*     */ 
/*  59 */   private Vector m_vTranslations = null;
/*     */ 
/*     */   public SubscriptionModel()
/*     */   {
/*  63 */     this.m_price = new BrightMoney();
/*  64 */     this.m_bestPrice = new BrightMoney();
/*  65 */     this.m_iNoOfDownloads = new BrightNaturalNumber();
/*  66 */     this.m_iDuration = new BrightNaturalNumber();
/*     */   }
/*     */ 
/*     */   public boolean getIsInGroup(int a_iGroupId)
/*     */   {
/*  83 */     Long olGroupId = null;
/*     */ 
/*  85 */     if (getGroups() != null)
/*     */     {
/*  87 */       for (int i = 0; i < getGroups().size(); i++)
/*     */       {
/*  89 */         olGroupId = (Long)(Long)getGroups().elementAt(i);
/*     */ 
/*  91 */         if (olGroupId.intValue() == a_iGroupId)
/*     */         {
/*  93 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getInactive()
/*     */   {
/* 108 */     return this.m_bInactive;
/*     */   }
/*     */ 
/*     */   public void setInactive(boolean a_bNotActive) {
/* 112 */     this.m_bInactive = a_bNotActive;
/*     */   }
/*     */ 
/*     */   public BrightMoney getPrice()
/*     */   {
/* 117 */     return this.m_price;
/*     */   }
/*     */ 
/*     */   public void setPrice(BrightMoney a_price) {
/* 121 */     this.m_price = a_price;
/*     */   }
/*     */ 
/*     */   public BrightNaturalNumber getDuration()
/*     */   {
/* 126 */     return this.m_iDuration;
/*     */   }
/*     */ 
/*     */   public void setDuration(BrightNaturalNumber a_sDuration) {
/* 130 */     this.m_iDuration = a_sDuration;
/*     */   }
/*     */ 
/*     */   public BrightNaturalNumber getNoOfDownloads()
/*     */   {
/* 135 */     return this.m_iNoOfDownloads;
/*     */   }
/*     */ 
/*     */   public void setNoOfDownloads(BrightNaturalNumber a_sNoOfDownloads) {
/* 139 */     this.m_iNoOfDownloads = a_sNoOfDownloads;
/*     */   }
/*     */ 
/*     */   public Vector getGroups()
/*     */   {
/* 144 */     if (this.m_groups == null)
/*     */     {
/* 146 */       this.m_groups = new Vector();
/*     */     }
/* 148 */     return this.m_groups;
/*     */   }
/*     */ 
/*     */   public void setGroups(Vector a_sGroups) {
/* 152 */     this.m_groups = a_sGroups;
/*     */   }
/*     */ 
/*     */   public Vector getUpgrades()
/*     */   {
/* 157 */     if (this.m_upgrades == null)
/*     */     {
/* 159 */       this.m_upgrades = new Vector();
/*     */     }
/* 161 */     return this.m_upgrades;
/*     */   }
/*     */ 
/*     */   public void setUpgrades(Vector a_sUpgrades) {
/* 165 */     this.m_upgrades = a_sUpgrades;
/*     */   }
/*     */ 
/*     */   public boolean getHasActiveSubscriptions()
/*     */   {
/* 170 */     return this.m_bHasActiveSubscriptions;
/*     */   }
/*     */ 
/*     */   public void setHasActiveSubscriptions(boolean a_sHasActiveSubscriptions) {
/* 174 */     this.m_bHasActiveSubscriptions = a_sHasActiveSubscriptions;
/*     */   }
/*     */ 
/*     */   public BrightMoney getBestPrice()
/*     */   {
/* 179 */     return this.m_bestPrice;
/*     */   }
/*     */ 
/*     */   public void setBestPrice(BrightMoney a_sBestPrice) {
/* 183 */     this.m_bestPrice = a_sBestPrice;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 188 */     if ((this.m_language != null) && (!LanguageConstants.k_defaultLanguage.equals(this.m_language)))
/*     */     {
/* 190 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, getTranslations());
/* 191 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getDescription())))
/*     */       {
/* 193 */         return translation.getDescription();
/*     */       }
/*     */     }
/* 196 */     return super.getDescription();
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 201 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/* 206 */     if (this.m_vTranslations == null)
/*     */     {
/* 208 */       this.m_vTranslations = new Vector();
/*     */     }
/* 210 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_vTranslations)
/*     */   {
/* 215 */     this.m_vTranslations = a_vTranslations;
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseSubscriptionModel implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 222 */       setLanguage(a_language);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.bean.SubscriptionModel
 * JD-Core Version:    0.6.0
 */