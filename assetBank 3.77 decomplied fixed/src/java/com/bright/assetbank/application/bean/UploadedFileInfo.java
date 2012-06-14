/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ 
/*     */ public class UploadedFileInfo
/*     */ {
/*  33 */   private String m_sFileLocation = null;
/*  34 */   private String m_sConvertedLocation = null;
/*  35 */   private ImageFile m_ifPreviewImage = null;
/*  36 */   private long m_lTimeUploaded = 0L;
/*  37 */   private long m_lAssetTypeId = 0L;
/*  38 */   private long m_sFileSizeInBytes = 0L;
/*     */ 
/*     */   public UploadedFileInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UploadedFileInfo(UploadedFileInfo a_toCopy)
/*     */   {
/*  54 */     setFileLocation(a_toCopy.getFileLocation());
/*  55 */     setAssetTypeId(a_toCopy.getAssetTypeId());
/*  56 */     setConvertedLocation(a_toCopy.getConvertedLocation());
/*  57 */     setPreviewImage(a_toCopy.getPreviewImage());
/*  58 */     setTimeUploaded(a_toCopy.getTimeUploaded());
/*  59 */     setFileSizeInBytes(a_toCopy.getFileSizeInBytes());
/*     */   }
/*     */ 
/*     */   public ImageFile getPreviewImage()
/*     */   {
/*  65 */     return this.m_ifPreviewImage;
/*     */   }
/*     */ 
/*     */   public void setPreviewImage(ImageFile a_ifPreviewImage)
/*     */   {
/*  70 */     this.m_ifPreviewImage = a_ifPreviewImage;
/*     */   }
/*     */ 
/*     */   public long getTimeUploaded()
/*     */   {
/*  75 */     return this.m_lTimeUploaded;
/*     */   }
/*     */ 
/*     */   public void setTimeUploaded(long a_lTimeUploaded)
/*     */   {
/*  80 */     this.m_lTimeUploaded = a_lTimeUploaded;
/*     */   }
/*     */ 
/*     */   public String getFileLocation()
/*     */   {
/*  85 */     return this.m_sFileLocation;
/*     */   }
/*     */ 
/*     */   public void setFileLocation(String a_sFileLocation)
/*     */   {
/*  90 */     this.m_sFileLocation = a_sFileLocation;
/*     */   }
/*     */ 
/*     */   public long getAssetTypeId() {
/*  94 */     return this.m_lAssetTypeId;
/*     */   }
/*     */ 
/*     */   public void setAssetTypeId(long a_lAssetTypeId) {
/*  98 */     this.m_lAssetTypeId = a_lAssetTypeId;
/*     */   }
/*     */ 
/*     */   public String getConvertedLocation() {
/* 102 */     return this.m_sConvertedLocation;
/*     */   }
/*     */ 
/*     */   public void setConvertedLocation(String a_sConvertedLocation) {
/* 106 */     this.m_sConvertedLocation = a_sConvertedLocation;
/*     */   }
/*     */ 
/*     */   public long getFileSizeInBytes()
/*     */   {
/* 111 */     return this.m_sFileSizeInBytes;
/*     */   }
/*     */ 
/*     */   public void setFileSizeInBytes(long a_sFileSizeInBytes)
/*     */   {
/* 116 */     this.m_sFileSizeInBytes = a_sFileSizeInBytes;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.UploadedFileInfo
 * JD-Core Version:    0.6.0
 */