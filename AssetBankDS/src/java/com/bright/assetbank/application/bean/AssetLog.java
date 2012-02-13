/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetLog extends DataBean
/*     */ {
/*  32 */   private String m_sType = "";
/*  33 */   private String m_sNewValue = "";
/*  34 */   private String m_sOldValue = "";
/*  35 */   private String m_sName = "";
/*  36 */   private long m_lId = 0L;
/*  37 */   private boolean m_bAdded = false;
/*  38 */   private long m_lLanguageId = 0L;
/*  39 */   private String m_sLanguageName = "";
/*  40 */   private Vector m_vTranslations = null;
/*     */ 
/*     */   public void setType(String a_sType)
/*     */   {
/*  44 */     this.m_sType = a_sType;
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  48 */     return this.m_sType;
/*     */   }
/*     */ 
/*     */   public void setNewValue(String a_sNewValue)
/*     */   {
/*  53 */     this.m_sNewValue = a_sNewValue;
/*     */   }
/*     */ 
/*     */   public String getNewValue() {
/*  57 */     return this.m_sNewValue;
/*     */   }
/*     */ 
/*     */   public void setOldValue(String a_sOldValue)
/*     */   {
/*  62 */     this.m_sOldValue = a_sOldValue;
/*     */   }
/*     */ 
/*     */   public String getOldValue() {
/*  66 */     return this.m_sOldValue;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/*  71 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  75 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setId(long a_lId)
/*     */   {
/*  80 */     this.m_lId = a_lId;
/*     */   }
/*     */ 
/*     */   public long getId() {
/*  84 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_vTranslations)
/*     */   {
/*  89 */     this.m_vTranslations = a_vTranslations;
/*     */   }
/*     */ 
/*     */   public Vector getTranslations() {
/*  93 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setLanguageId(long a_lLanguageId)
/*     */   {
/*  98 */     this.m_lLanguageId = a_lLanguageId;
/*     */   }
/*     */ 
/*     */   public long getLanguageId() {
/* 102 */     return this.m_lLanguageId;
/*     */   }
/*     */ 
/*     */   public void setLanguageName(String a_sLanguageName) {
/* 106 */     this.m_sLanguageName = a_sLanguageName;
/*     */   }
/*     */ 
/*     */   public String getLanguageName() {
/* 110 */     return this.m_sLanguageName;
/*     */   }
/*     */ 
/*     */   public void setIsAdded(boolean a_bAdded)
/*     */   {
/* 115 */     this.m_bAdded = a_bAdded;
/*     */   }
/*     */ 
/*     */   public boolean getIsAdded() {
/* 119 */     return this.m_bAdded;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.AssetLog
 * JD-Core Version:    0.6.0
 */