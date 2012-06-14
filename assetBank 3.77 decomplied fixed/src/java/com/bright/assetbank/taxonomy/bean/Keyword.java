/*     */ package com.bright.assetbank.taxonomy.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.CategoryImpl;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class Keyword extends CategoryImpl
/*     */   implements Category, TranslatableWithLanguage
/*     */ {
/*     */   private boolean m_bInMasterList;
/*  40 */   Vector m_keywordsForThisSynonym = null;
/*     */   Language m_language;
/*     */ 
/*     */   public Keyword()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Keyword(Category a_cat)
/*     */   {
/*  52 */     super(a_cat);
/*     */   }
/*     */ 
/*     */   public void setLanguage(Language a_language)
/*     */   {
/*  57 */     this.m_language = a_language;
/*     */   }
/*     */ 
/*     */   protected Language getLanguage()
/*     */   {
/*  63 */     return this.m_language;
/*     */   }
/*     */ 
/*     */   public String getSynonymsForDisplay()
/*     */   {
/*  73 */     String sDelim = AssetBankSettings.getKeywordDelimiter();
/*  74 */     String sSynonymsForDisplay = "";
/*     */ 
/*  76 */     if (StringUtil.stringIsPopulated(getSynonyms()))
/*     */     {
/*  78 */       sSynonymsForDisplay = getSynonyms().replaceAll(sDelim, sDelim + " ");
/*     */     }
/*     */ 
/*  81 */     return sSynonymsForDisplay;
/*     */   }
/*     */ 
/*     */   public String getSynonyms()
/*     */   {
/*  87 */     return getDescription();
/*     */   }
/*     */ 
/*     */   public void setSynonyms(String a_sSynonyms) {
/*  91 */     setDescription(a_sSynonyms);
/*     */   }
/*     */ 
/*     */   public boolean getInMasterList()
/*     */   {
/*  97 */     return this.m_bInMasterList;
/*     */   }
/*     */ 
/*     */   public void setInMasterList(boolean a_sInMasterList) {
/* 101 */     this.m_bInMasterList = a_sInMasterList;
/*     */   }
/*     */ 
/*     */   public Vector getKeywordsForThisSynonym()
/*     */   {
/* 106 */     return this.m_keywordsForThisSynonym;
/*     */   }
/*     */ 
/*     */   public void setKeywordsForThisSynonym(Vector a_sKeywordsForThisSynonym) {
/* 110 */     this.m_keywordsForThisSynonym = a_sKeywordsForThisSynonym;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.bean.Keyword
 * JD-Core Version:    0.6.0
 */