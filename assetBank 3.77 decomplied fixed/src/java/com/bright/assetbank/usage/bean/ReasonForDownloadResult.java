/*     */ package com.bright.assetbank.usage.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class ReasonForDownloadResult extends ReportEntity
/*     */ {
/*  38 */   private long m_lAssetId = 0L;
/*  39 */   private String m_sPaddedAssetId = null;
/*  40 */   private Date m_dtDownloadTime = null;
/*  41 */   private String m_sReasonForDownload = null;
/*  42 */   private long m_lUserId = 0L;
/*  43 */   private String m_sUserFullname = null;
/*  44 */   private String m_sUsername = null;
/*  45 */   private String m_sOriginalFilename = null;
/*  46 */   private String m_sThumbnailPath = null;
/*  47 */   private String m_sCountryName = null;
/*  48 */   private String m_sOrganisation = null;
/*  49 */   private long m_lAssetUseId = 0L;
/*  50 */   private Vector<String> m_vecSecondaryUsageTypes = null;
/*     */ 
/*     */   public Date getDownloadTime()
/*     */   {
/*  56 */     return this.m_dtDownloadTime;
/*     */   }
/*     */ 
/*     */   public void setDownloadTime(Date a_dtDownloadTime)
/*     */   {
/*  62 */     this.m_dtDownloadTime = a_dtDownloadTime;
/*     */   }
/*     */ 
/*     */   public String getAssetId()
/*     */   {
/*  68 */     if (this.m_sPaddedAssetId == null)
/*     */     {
/*  70 */       this.m_sPaddedAssetId = Asset.getPaddedId(this.m_lAssetId);
/*     */     }
/*  72 */     return this.m_sPaddedAssetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_lAssetId)
/*     */   {
/*  78 */     this.m_lAssetId = a_lAssetId;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/*  84 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_lUserId)
/*     */   {
/*  90 */     this.m_lUserId = a_lUserId;
/*     */   }
/*     */ 
/*     */   public String getReasonForDownload()
/*     */   {
/*  96 */     return this.m_sReasonForDownload;
/*     */   }
/*     */ 
/*     */   public void setReasonForDownload(String a_sReasonForDownload)
/*     */   {
/* 102 */     this.m_sReasonForDownload = a_sReasonForDownload;
/*     */   }
/*     */ 
/*     */   public void setUserFullname(String a_sUserFullname)
/*     */   {
/* 107 */     this.m_sUserFullname = a_sUserFullname;
/*     */   }
/*     */ 
/*     */   public String getUserFullname()
/*     */   {
/* 112 */     return this.m_sUserFullname;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 117 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername)
/*     */   {
/* 122 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public String getOriginalFilename() {
/* 126 */     return this.m_sOriginalFilename;
/*     */   }
/*     */ 
/*     */   public void setOriginalFilename(String a_sOriginalFilename) {
/* 130 */     this.m_sOriginalFilename = a_sOriginalFilename;
/*     */   }
/*     */ 
/*     */   public void setCountryName(String a_sCountryName)
/*     */   {
/* 135 */     this.m_sCountryName = a_sCountryName;
/*     */   }
/*     */ 
/*     */   public String getCountryName()
/*     */   {
/* 140 */     return this.m_sCountryName;
/*     */   }
/*     */ 
/*     */   public void setOrganisation(String a_sOrganisation)
/*     */   {
/* 145 */     this.m_sOrganisation = a_sOrganisation;
/*     */   }
/*     */ 
/*     */   public String getOrganisation()
/*     */   {
/* 150 */     return this.m_sOrganisation;
/*     */   }
/*     */ 
/*     */   public String getThumbnailPath()
/*     */   {
/* 155 */     return this.m_sThumbnailPath;
/*     */   }
/*     */ 
/*     */   public String getRc4EncryptedThumbnailPath()
/*     */   {
/* 160 */     return FileUtil.encryptFilepath(this.m_sThumbnailPath);
/*     */   }
/*     */ 
/*     */   public void setThumbnailPath(String a_sThumbnailPath)
/*     */   {
/* 165 */     this.m_sThumbnailPath = a_sThumbnailPath;
/*     */   }
/*     */ 
/*     */   public boolean getHasIcon()
/*     */   {
/* 170 */     return (this.m_sThumbnailPath != null) && (this.m_sThumbnailPath.startsWith(StoredFileType.SHARED_THUMBNAIL.getDirectoryName()));
/*     */   }
/*     */ 
/*     */   public long getAssetUseId()
/*     */   {
/* 175 */     return this.m_lAssetUseId;
/*     */   }
/*     */ 
/*     */   public void setAssetUseId(long a_lAssetUseId)
/*     */   {
/* 180 */     this.m_lAssetUseId = a_lAssetUseId;
/*     */   }
/*     */ 
/*     */   public void setSecondaryUsageTypes(Vector<String> a_vecSecondaryUsageTypes)
/*     */   {
/* 185 */     this.m_vecSecondaryUsageTypes = a_vecSecondaryUsageTypes;
/*     */   }
/*     */ 
/*     */   public Vector<String> getSecondaryUsageTypes()
/*     */   {
/* 190 */     if (this.m_vecSecondaryUsageTypes == null)
/*     */     {
/* 192 */       this.m_vecSecondaryUsageTypes = new Vector();
/*     */     }
/*     */ 
/* 195 */     return this.m_vecSecondaryUsageTypes;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.ReasonForDownloadResult
 * JD-Core Version:    0.6.0
 */