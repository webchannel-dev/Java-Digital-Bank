/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ public class MediaAssetConversionInfo extends AssetConversionInfo
/*     */ {
/*  29 */   private boolean m_bAddWatermark = false;
/*  30 */   private boolean m_bAddDownloadWatermark = false;
/*  31 */   private int m_iMaxHeight = 0;
/*  32 */   private int m_iMaxWidth = 0;
/*  33 */   private boolean m_bMaintainAspectRatio = false;
/*  34 */   private boolean m_bUseOriginal = false;
/*  35 */   private boolean m_bAddingAsNewAsset = false;
/*  36 */   private int m_iDensity = 0;
/*     */ 
/*  58 */   private boolean m_bScaleUp = false;
/*     */ 
/*     */   public MediaAssetConversionInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MediaAssetConversionInfo(MediaAssetConversionInfo a_copyFrom)
/*     */   {
/*  45 */     super(a_copyFrom);
/*  46 */     this.m_bAddDownloadWatermark = a_copyFrom.m_bAddDownloadWatermark;
/*  47 */     this.m_bAddWatermark = a_copyFrom.m_bAddWatermark;
/*  48 */     this.m_bAddingAsNewAsset = a_copyFrom.m_bAddingAsNewAsset;
/*  49 */     this.m_bMaintainAspectRatio = a_copyFrom.m_bMaintainAspectRatio;
/*  50 */     this.m_bScaleUp = a_copyFrom.m_bScaleUp;
/*  51 */     this.m_bUseOriginal = a_copyFrom.m_bUseOriginal;
/*  52 */     this.m_iDensity = a_copyFrom.m_iDensity;
/*  53 */     this.m_iMaxHeight = a_copyFrom.m_iMaxHeight;
/*  54 */     this.m_iMaxWidth = a_copyFrom.m_iMaxWidth;
/*     */   }
/*     */ 
/*     */   public boolean getAddWatermark()
/*     */   {
/*  63 */     return this.m_bAddWatermark;
/*     */   }
/*     */ 
/*     */   public void setAddWatermark(boolean a_bAddWatermark)
/*     */   {
/*  68 */     this.m_bAddWatermark = a_bAddWatermark;
/*     */   }
/*     */ 
/*     */   public boolean getAddDownloadWatermark()
/*     */   {
/*  73 */     return this.m_bAddDownloadWatermark;
/*     */   }
/*     */ 
/*     */   public void setAddDownloadWatermark(boolean a_bAddDownloadWatermark) {
/*  77 */     this.m_bAddDownloadWatermark = a_bAddDownloadWatermark;
/*     */   }
/*     */ 
/*     */   public int getMaxHeight()
/*     */   {
/*  83 */     return this.m_iMaxHeight;
/*     */   }
/*     */ 
/*     */   public void setMaxHeight(int a_iMaxHeight)
/*     */   {
/*  88 */     this.m_iMaxHeight = a_iMaxHeight;
/*     */   }
/*     */ 
/*     */   public int getMaxWidth()
/*     */   {
/*  93 */     return this.m_iMaxWidth;
/*     */   }
/*     */ 
/*     */   public void setMaxWidth(int a_iMaxWidth)
/*     */   {
/*  98 */     this.m_iMaxWidth = a_iMaxWidth;
/*     */   }
/*     */ 
/*     */   public boolean getMaintainAspectRatio()
/*     */   {
/* 104 */     return this.m_bMaintainAspectRatio;
/*     */   }
/*     */ 
/*     */   public void setMaintainAspectRatio(boolean a_bMaintainAspectRatio) {
/* 108 */     this.m_bMaintainAspectRatio = a_bMaintainAspectRatio;
/*     */   }
/*     */ 
/*     */   public boolean getScaleUp() {
/* 112 */     return this.m_bScaleUp;
/*     */   }
/*     */ 
/*     */   public void setScaleUp(boolean a_bScaleUp) {
/* 116 */     this.m_bScaleUp = a_bScaleUp;
/*     */   }
/*     */ 
/*     */   public boolean getUseOriginal() {
/* 120 */     return this.m_bUseOriginal;
/*     */   }
/*     */ 
/*     */   public void setUseOriginal(boolean a_bUseOriginal) {
/* 124 */     this.m_bUseOriginal = a_bUseOriginal;
/*     */   }
/*     */ 
/*     */   public boolean getAddingAsNewAsset() {
/* 128 */     return this.m_bAddingAsNewAsset;
/*     */   }
/*     */ 
/*     */   public void setAddingAsNewAsset(boolean a_bAddingAsNewAsset) {
/* 132 */     this.m_bAddingAsNewAsset = a_bAddingAsNewAsset;
/*     */   }
/*     */ 
/*     */   public int getDensity() {
/* 136 */     return this.m_iDensity;
/*     */   }
/*     */ 
/*     */   public void setDensity(int a_iDensity) {
/* 140 */     this.m_iDensity = a_iDensity;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.MediaAssetConversionInfo
 * JD-Core Version:    0.6.0
 */