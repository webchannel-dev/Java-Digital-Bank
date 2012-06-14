/*     */ package com.bright.assetbank.marketing.bean;
/*     */ 
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class MarketingGroup extends BaseMarketingGroup
/*     */   implements TranslatableWithLanguage
/*     */ {
/*     */   private String m_sPurpose;
/*     */   private int m_iNumUsers;
/*     */   private List m_users;
/*     */   private boolean m_bHiddenInDefaultLanguage;
/*     */   private Vector m_vTranslations;
/*     */ 
/*     */   public MarketingGroup()
/*     */   {
/*  31 */     this.m_sPurpose = null;
/*  32 */     this.m_iNumUsers = 0;
/*  33 */     this.m_users = null;
/*  34 */     this.m_bHiddenInDefaultLanguage = false;
/*     */ 
/*  37 */     this.m_vTranslations = null;
/*     */   }
/*     */ 
/*     */   public String getPurpose() {
/*  41 */     return this.m_sPurpose;
/*     */   }
/*     */ 
/*     */   public void setPurpose(String purpose) {
/*  45 */     this.m_sPurpose = purpose;
/*     */   }
/*     */ 
/*     */   public int getNumUsers() {
/*  49 */     return this.m_iNumUsers;
/*     */   }
/*     */ 
/*     */   public void setNumUsers(int numUsers) {
/*  53 */     this.m_iNumUsers = numUsers;
/*     */   }
/*     */ 
/*     */   public List getUsers() {
/*  57 */     return this.m_users;
/*     */   }
/*     */ 
/*     */   public void setUsers(List users) {
/*  61 */     this.m_users = users;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  66 */     if (!this.m_language.equals(LanguageConstants.k_defaultLanguage))
/*     */     {
/*  68 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  69 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getDescription())))
/*     */       {
/*  71 */         return translation.getDescription();
/*     */       }
/*     */     }
/*  74 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  79 */     if (!this.m_language.equals(LanguageConstants.k_defaultLanguage))
/*     */     {
/*  81 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  82 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getName())))
/*     */       {
/*  84 */         return translation.getName();
/*     */       }
/*     */     }
/*  87 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/*  94 */     if (this.m_vTranslations == null)
/*     */     {
/*  96 */       this.m_vTranslations = new Vector();
/*     */     }
/*  98 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector translations)
/*     */   {
/* 103 */     this.m_vTranslations = translations;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 108 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public boolean isHiddenInDefaultLanguage()
/*     */   {
/* 130 */     return this.m_bHiddenInDefaultLanguage;
/*     */   }
/*     */ 
/*     */   public void setHiddenInDefaultLanguage(boolean hiddenInDefaultLanguage)
/*     */   {
/* 135 */     this.m_bHiddenInDefaultLanguage = hiddenInDefaultLanguage;
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseMarketingGroup
/*     */     implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 119 */       this.m_language = a_language;
/*     */     }
/*     */ 
/*     */     public long getMarketingGroupId()
/*     */     {
/* 124 */       return MarketingGroup.this.getId();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.bean.MarketingGroup
 * JD-Core Version:    0.6.0
 */