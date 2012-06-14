/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.assetbank.usage.bean.ColorSpace;
/*     */ import com.bright.framework.image.bean.Colour;
/*     */ import java.awt.Rectangle;
/*     */ 
/*     */ public class ImageConversionInfo extends MediaAssetConversionInfo
/*     */ {
/*  40 */   private int m_iRotationAngle = 0;
/*  41 */   private int m_iCropX = 0;
/*  42 */   private int m_iCropY = 0;
/*  43 */   private int m_iCropHeight = 0;
/*  44 */   private int m_iCropWidth = 0;
/*     */ 
/*  49 */   private long m_cropMaskId = 0L;
/*     */   private Colour m_cropMaskColour;
/*  56 */   private float m_fJpegQuality = 0.0F;
/*  57 */   private boolean m_bApplyStrip = false;
/*  58 */   private String m_sCreditText = null;
/*  59 */   private int m_iLayerToConvert = 1;
/*  60 */   private String m_sTint = "";
/*     */ 
/*  62 */   private ColorSpace m_convertToColorSpace = null;
/*  63 */   private ColorSpace m_currentColorSpace = null;
/*     */ 
/*     */   public ImageConversionInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ImageConversionInfo(ImageConversionInfo a_copyFrom)
/*     */   {
/*  72 */     super(a_copyFrom);
/*  73 */     this.m_bApplyStrip = a_copyFrom.m_bApplyStrip;
/*  74 */     this.m_fJpegQuality = a_copyFrom.m_fJpegQuality;
/*  75 */     this.m_iCropHeight = a_copyFrom.m_iCropHeight;
/*  76 */     this.m_iCropWidth = a_copyFrom.m_iCropWidth;
/*  77 */     this.m_iCropX = a_copyFrom.m_iCropX;
/*  78 */     this.m_iCropY = a_copyFrom.m_iCropY;
/*  79 */     this.m_cropMaskId = a_copyFrom.m_cropMaskId;
/*  80 */     this.m_cropMaskColour = a_copyFrom.m_cropMaskColour;
/*  81 */     this.m_iLayerToConvert = a_copyFrom.m_iLayerToConvert;
/*  82 */     this.m_iRotationAngle = a_copyFrom.m_iRotationAngle;
/*  83 */     this.m_sCreditText = a_copyFrom.m_sCreditText;
/*  84 */     this.m_sTint = a_copyFrom.m_sTint;
/*  85 */     this.m_convertToColorSpace = a_copyFrom.m_convertToColorSpace;
/*  86 */     this.m_currentColorSpace = a_copyFrom.m_currentColorSpace;
/*     */   }
/*     */ 
/*     */   public void setCropRectangle(Rectangle a_rect)
/*     */   {
/*  91 */     setCropX(a_rect.x);
/*  92 */     setCropY(a_rect.y);
/*  93 */     setCropWidth(a_rect.width);
/*  94 */     setCropHeight(a_rect.height);
/*     */   }
/*     */ 
/*     */   public int getRotationAngle()
/*     */   {
/* 100 */     return this.m_iRotationAngle;
/*     */   }
/*     */ 
/*     */   public void setRotationAngle(int a_iRotationAngle)
/*     */   {
/* 105 */     this.m_iRotationAngle = a_iRotationAngle;
/*     */   }
/*     */ 
/*     */   public float getJpegQuality()
/*     */   {
/* 110 */     return this.m_fJpegQuality;
/*     */   }
/*     */ 
/*     */   public void setJpegQuality(float a_fJpegQuality) {
/* 114 */     this.m_fJpegQuality = a_fJpegQuality;
/*     */   }
/*     */ 
/*     */   public void setCropX(int a_iCropX) {
/* 118 */     this.m_iCropX = a_iCropX;
/*     */   }
/*     */ 
/*     */   public int getCropX() {
/* 122 */     return this.m_iCropX;
/*     */   }
/*     */ 
/*     */   public void setCropY(int a_iCropY) {
/* 126 */     this.m_iCropY = a_iCropY;
/*     */   }
/*     */ 
/*     */   public int getCropY() {
/* 130 */     return this.m_iCropY;
/*     */   }
/*     */ 
/*     */   public void setCropHeight(int a_iCropHeight) {
/* 134 */     this.m_iCropHeight = a_iCropHeight;
/*     */   }
/*     */ 
/*     */   public int getCropHeight() {
/* 138 */     return this.m_iCropHeight;
/*     */   }
/*     */ 
/*     */   public void setCropWidth(int a_iCropWidth) {
/* 142 */     this.m_iCropWidth = a_iCropWidth;
/*     */   }
/*     */ 
/*     */   public int getCropWidth() {
/* 146 */     return this.m_iCropWidth;
/*     */   }
/*     */ 
/*     */   public long getCropMaskId() {
/* 150 */     return this.m_cropMaskId;
/*     */   }
/*     */ 
/*     */   public void setCropMaskId(long a_cropMaskId) {
/* 154 */     this.m_cropMaskId = a_cropMaskId;
/*     */   }
/*     */ 
/*     */   public Colour getCropMaskColour() {
/* 158 */     return this.m_cropMaskColour;
/*     */   }
/*     */ 
/*     */   public void setCropMaskColour(Colour a_cropMaskColour) {
/* 162 */     this.m_cropMaskColour = a_cropMaskColour;
/*     */   }
/*     */ 
/*     */   public boolean getApplyStrip() {
/* 166 */     return this.m_bApplyStrip;
/*     */   }
/*     */ 
/*     */   public void setApplyStrip(boolean a_bApplyStrip) {
/* 170 */     this.m_bApplyStrip = a_bApplyStrip;
/*     */   }
/*     */ 
/*     */   public String getCreditText() {
/* 174 */     return this.m_sCreditText;
/*     */   }
/*     */ 
/*     */   public void setCreditText(String a_sCreditText) {
/* 178 */     this.m_sCreditText = a_sCreditText;
/*     */   }
/*     */ 
/*     */   public int getLayerToConvert() {
/* 182 */     return this.m_iLayerToConvert;
/*     */   }
/*     */ 
/*     */   public void setLayerToConvert(int layerToConvert) {
/* 186 */     if (layerToConvert < 0)
/*     */     {
/* 188 */       throw new IllegalArgumentException("Cannot specify a layer below 0");
/*     */     }
/* 190 */     this.m_iLayerToConvert = layerToConvert;
/*     */   }
/*     */ 
/*     */   public String getTint()
/*     */   {
/* 195 */     return this.m_sTint;
/*     */   }
/*     */ 
/*     */   public void setTint(String a_sTint) {
/* 199 */     this.m_sTint = a_sTint;
/*     */   }
/*     */ 
/*     */   public ColorSpace getConvertToColorSpace()
/*     */   {
/* 204 */     return this.m_convertToColorSpace;
/*     */   }
/*     */ 
/*     */   public void setConvertToColorSpace(ColorSpace a_convertToColorSpace) {
/* 208 */     this.m_convertToColorSpace = a_convertToColorSpace;
/*     */   }
/*     */ 
/*     */   public ColorSpace getCurrentColorSpace()
/*     */   {
/* 213 */     return this.m_currentColorSpace;
/*     */   }
/*     */ 
/*     */   public void setCurrentColorSpace(ColorSpace a_currentColorSpace) {
/* 217 */     this.m_currentColorSpace = a_currentColorSpace;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.ImageConversionInfo
 * JD-Core Version:    0.6.0
 */