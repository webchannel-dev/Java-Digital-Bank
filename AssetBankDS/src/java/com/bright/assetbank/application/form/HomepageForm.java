/*     */ package com.bright.assetbank.application.form;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class HomepageForm extends AssetForm
/*     */   implements ImageConstants
/*     */ {
/*  39 */   private Vector m_vecDescriptiveCategories = null;
/*  40 */   private Vector m_vecPermissionCategories = null;
/*     */ 
/*  42 */   private Vector m_vecRecentImages = null;
/*  43 */   private LightweightAsset[] m_aPromotedAssets = null;
/*     */ 
/*  45 */   private LightweightAsset m_featuredAsset = null;
/*  46 */   private boolean m_bDescriptiveCategoriesHaveImages = false;
/*  47 */   private boolean m_bPermissionCategoriesHaveImages = false;
/*     */ 
/*  49 */   private long m_browseCategoryId = 0L;
/*  50 */   private long m_browseCategoryTreeId = 0L;
/*     */ 
/*  52 */   private Vector m_vecUserPromotedSearches = null;
/*  53 */   private Vector m_vecGlobalPromotedSearches = null;
/*     */ 
/*  55 */   private long m_lNumUnsubmittedAssets = 0L;
/*  56 */   private long m_lNumUnsubmittedWorkflowedAssets = 0L;
/*  57 */   private Vector m_keywordList = null;
/*     */ 
/*  59 */   private String m_sCustomWelcomeText = null;
/*     */ 
/*     */   public Vector getDescriptiveCategories()
/*     */   {
/*  63 */     return this.m_vecDescriptiveCategories;
/*     */   }
/*     */ 
/*     */   public void setDescriptiveCategories(Vector a_vecCategories) {
/*  67 */     this.m_vecDescriptiveCategories = a_vecCategories;
/*     */   }
/*     */ 
/*     */   public Vector getPermissionCategories()
/*     */   {
/*  72 */     return this.m_vecPermissionCategories;
/*     */   }
/*     */ 
/*     */   public void setPermissionCategories(Vector a_sVecPermissionCategories) {
/*  76 */     this.m_vecPermissionCategories = a_sVecPermissionCategories;
/*     */   }
/*     */ 
/*     */   public int getNumDescriptiveCategories()
/*     */   {
/*  81 */     if (this.m_vecDescriptiveCategories == null)
/*     */     {
/*  83 */       return 0;
/*     */     }
/*  85 */     return this.m_vecDescriptiveCategories.size();
/*     */   }
/*     */ 
/*     */   public int getNumPermissionCategories()
/*     */   {
/*  90 */     if (this.m_vecPermissionCategories == null)
/*     */     {
/*  92 */       return 0;
/*     */     }
/*  94 */     return this.m_vecPermissionCategories.size();
/*     */   }
/*     */ 
/*     */   public Vector getRecentImages()
/*     */   {
/*  99 */     return this.m_vecRecentImages;
/*     */   }
/*     */ 
/*     */   public void setRecentImages(Vector a_vecRecentImages)
/*     */   {
/* 104 */     this.m_vecRecentImages = a_vecRecentImages;
/*     */   }
/*     */ 
/*     */   public LightweightAsset[] getPromotedAssets()
/*     */   {
/* 109 */     return this.m_aPromotedAssets;
/*     */   }
/*     */ 
/*     */   public void setPromotedAssets(LightweightAsset[] a_aPromotedAssets)
/*     */   {
/* 114 */     this.m_aPromotedAssets = a_aPromotedAssets;
/*     */   }
/*     */ 
/*     */   public boolean getDescriptiveCategoriesHaveImages()
/*     */   {
/* 119 */     return this.m_bDescriptiveCategoriesHaveImages;
/*     */   }
/*     */ 
/*     */   public void setDescriptiveCategoriesHaveImages(boolean a_sDescriptiveCategoriesHaveImages) {
/* 123 */     this.m_bDescriptiveCategoriesHaveImages = a_sDescriptiveCategoriesHaveImages;
/*     */   }
/*     */ 
/*     */   public boolean getPermissionCategoriesHaveImages()
/*     */   {
/* 128 */     return this.m_bPermissionCategoriesHaveImages;
/*     */   }
/*     */ 
/*     */   public void setPermissionCategoriesHaveImages(boolean a_sPermissionCategoriesHaveImages) {
/* 132 */     this.m_bPermissionCategoriesHaveImages = a_sPermissionCategoriesHaveImages;
/*     */   }
/*     */ 
/*     */   public LightweightAsset getFeaturedAsset()
/*     */   {
/* 137 */     return this.m_featuredAsset;
/*     */   }
/*     */ 
/*     */   public void setFeaturedAsset(LightweightAsset a_sFeaturedAsset) {
/* 141 */     this.m_featuredAsset = a_sFeaturedAsset;
/*     */   }
/*     */ 
/*     */   public long getBrowseCategoryId() {
/* 145 */     return this.m_browseCategoryId;
/*     */   }
/*     */ 
/*     */   public void setBrowseCategoryId(long a_sBrowseCategoryId) {
/* 149 */     this.m_browseCategoryId = a_sBrowseCategoryId;
/*     */   }
/*     */ 
/*     */   public long getBrowseCategoryTreeId() {
/* 153 */     return this.m_browseCategoryTreeId;
/*     */   }
/*     */ 
/*     */   public void setBrowseCategoryTreeId(long a_sBrowseCategoryTreeId) {
/* 157 */     this.m_browseCategoryTreeId = a_sBrowseCategoryTreeId;
/*     */   }
/*     */ 
/*     */   public void setUserPromotedSearches(Vector a_vecUserPromotedSearches)
/*     */   {
/* 162 */     this.m_vecUserPromotedSearches = a_vecUserPromotedSearches;
/*     */   }
/*     */ 
/*     */   public Vector getUserPromotedSearches()
/*     */   {
/* 167 */     return this.m_vecUserPromotedSearches;
/*     */   }
/*     */ 
/*     */   public void setGlobalPromotedSearches(Vector a_vecGlobalPromotedSearches)
/*     */   {
/* 172 */     this.m_vecGlobalPromotedSearches = a_vecGlobalPromotedSearches;
/*     */   }
/*     */ 
/*     */   public Vector getGlobalPromotedSearches()
/*     */   {
/* 177 */     return this.m_vecGlobalPromotedSearches;
/*     */   }
/*     */ 
/*     */   public int getGlobalPromotedSearchCount()
/*     */   {
/* 182 */     if (getGlobalPromotedSearches() != null)
/*     */     {
/* 184 */       return getGlobalPromotedSearches().size();
/*     */     }
/* 186 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getUserPromotedSearchCount()
/*     */   {
/* 191 */     if (getUserPromotedSearches() != null)
/*     */     {
/* 193 */       return getUserPromotedSearches().size();
/*     */     }
/* 195 */     return 0;
/*     */   }
/*     */ 
/*     */   public long getNumUnsubmittedAssets()
/*     */   {
/* 200 */     return this.m_lNumUnsubmittedAssets;
/*     */   }
/*     */ 
/*     */   public void setNumUnsubmittedAssets(long a_sNumUnsubmittedAssets) {
/* 204 */     this.m_lNumUnsubmittedAssets = a_sNumUnsubmittedAssets;
/*     */   }
/*     */ 
/*     */   public long getNumUnsubmittedWorkflowedAssets() {
/* 208 */     return this.m_lNumUnsubmittedWorkflowedAssets;
/*     */   }
/*     */ 
/*     */   public void setNumUnsubmittedWorkflowedAssets(long a_sNumUnsubmittedWorkflowedAssets) {
/* 212 */     this.m_lNumUnsubmittedWorkflowedAssets = a_sNumUnsubmittedWorkflowedAssets;
/*     */   }
/*     */ 
/*     */   public String getCustomWelcomeText()
/*     */   {
/* 217 */     return this.m_sCustomWelcomeText;
/*     */   }
/*     */ 
/*     */   public void setCustomWelcomeText(String a_sCustomWelcomeText)
/*     */   {
/* 222 */     this.m_sCustomWelcomeText = a_sCustomWelcomeText;
/*     */   }
/*     */ 
/*     */   public Vector getKeywordList()
/*     */   {
/* 227 */     return this.m_keywordList;
/*     */   }
/*     */ 
/*     */   public void setKeywordList(Vector a_sKeywordList) {
/* 231 */     this.m_keywordList = a_sKeywordList;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.HomepageForm
 * JD-Core Version:    0.6.0
 */