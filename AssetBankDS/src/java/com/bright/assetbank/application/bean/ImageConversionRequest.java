/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ 
/*     */ public class ImageConversionRequest extends QueuedItem
/*     */ {
/*  29 */   private ImageAsset m_image = null;
/*  30 */   private ImageConversionInfo m_conversionInfo = null;
/*  31 */   private String m_sRelativeTempDownloadFilePath = null;
/*  32 */   private int m_iHeight = 0;
/*  33 */   private int m_iWidth = 0;
/*  34 */   private boolean m_bAlreadyWatermarked = false;
/*  35 */   private String m_sOriginalFilenameFullPath = null;
/*     */ 
/*     */   public ImageConversionRequest(ImageAsset a_sImage, ImageConversionInfo a_sConversionInfo, String a_sRelativeTempDownloadFilePath, int a_sHeight, int a_sWidth, boolean a_sAlreadyWatermarked, String a_sOriginalFilenameFullPath)
/*     */   {
/*  51 */     this.m_image = a_sImage;
/*  52 */     this.m_conversionInfo = a_sConversionInfo;
/*  53 */     this.m_sRelativeTempDownloadFilePath = a_sRelativeTempDownloadFilePath;
/*  54 */     this.m_iHeight = a_sHeight;
/*  55 */     this.m_iWidth = a_sWidth;
/*  56 */     this.m_bAlreadyWatermarked = a_sAlreadyWatermarked;
/*  57 */     this.m_sOriginalFilenameFullPath = a_sOriginalFilenameFullPath;
/*     */   }
/*     */ 
/*     */   public boolean isAlreadyWatermarked()
/*     */   {
/*  62 */     return this.m_bAlreadyWatermarked;
/*     */   }
/*     */ 
/*     */   public void setAlreadyWatermarked(boolean a_sAlreadyWatermarked) {
/*  66 */     this.m_bAlreadyWatermarked = a_sAlreadyWatermarked;
/*     */   }
/*     */ 
/*     */   public ImageConversionInfo getConversionInfo() {
/*  70 */     return this.m_conversionInfo;
/*     */   }
/*     */ 
/*     */   public void setConversionInfo(ImageConversionInfo a_sConversionInfo) {
/*  74 */     this.m_conversionInfo = a_sConversionInfo;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/*  78 */     return this.m_iHeight;
/*     */   }
/*     */ 
/*     */   public void setHeight(int a_sHeight) {
/*  82 */     this.m_iHeight = a_sHeight;
/*     */   }
/*     */ 
/*     */   public ImageAsset getImage() {
/*  86 */     return this.m_image;
/*     */   }
/*     */ 
/*     */   public void setImage(ImageAsset a_sImage) {
/*  90 */     this.m_image = a_sImage;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/*  94 */     return this.m_iWidth;
/*     */   }
/*     */ 
/*     */   public void setWidth(int a_sWidth) {
/*  98 */     this.m_iWidth = a_sWidth;
/*     */   }
/*     */ 
/*     */   public String getRelativeTempDownloadFilePath() {
/* 102 */     return this.m_sRelativeTempDownloadFilePath;
/*     */   }
/*     */ 
/*     */   public String getOriginalFilenameFullPath() {
/* 106 */     return this.m_sOriginalFilenameFullPath;
/*     */   }
/*     */ 
/*     */   public void setOriginalFilenameFullPath(String a_sOriginalFilenameFullPath) {
/* 110 */     this.m_sOriginalFilenameFullPath = a_sOriginalFilenameFullPath;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.ImageConversionRequest
 * JD-Core Version:    0.6.0
 */