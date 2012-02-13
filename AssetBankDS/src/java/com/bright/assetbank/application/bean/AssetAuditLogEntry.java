/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class AssetAuditLogEntry
/*     */ {
/*     */   private long m_lId;
/*     */   private String m_sIdentifier;
/*     */   private Date m_date;
/*     */   private String m_sUsername;
/*     */   private String m_sIpAddress;
/*     */   private String m_sType;
/*     */   private String m_sLog;
/*     */   private long m_lAssetId;
/*     */   private String m_sThumbnailPath;
/*     */   private Date m_dtLoginTime;
/*     */   private Date m_dtSessionStartTime;
/*     */ 
/*     */   public AssetAuditLogEntry()
/*     */   {
/*  25 */     this.m_lId = 0L;
/*  26 */     this.m_sIdentifier = null;
/*  27 */     this.m_date = null;
/*  28 */     this.m_sUsername = null;
/*  29 */     this.m_sIpAddress = null;
/*  30 */     this.m_sType = null;
/*  31 */     this.m_sLog = null;
/*  32 */     this.m_lAssetId = 0L;
/*  33 */     this.m_sThumbnailPath = null;
/*  34 */     this.m_dtLoginTime = null;
/*  35 */     this.m_dtSessionStartTime = null;
/*     */   }
/*     */ 
/*     */   public Date getDate()
/*     */   {
/*  40 */     return this.m_date;
/*     */   }
/*     */ 
/*     */   public void setDate(Date a_sDate) {
/*  44 */     this.m_date = a_sDate;
/*     */   }
/*     */ 
/*     */   public String getIdentifier()
/*     */   {
/*  49 */     return this.m_sIdentifier;
/*     */   }
/*     */ 
/*     */   public void setIdentifier(String a_sIdentifier) {
/*  53 */     this.m_sIdentifier = a_sIdentifier;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  58 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername) {
/*  62 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public String getIpAddress()
/*     */   {
/*  67 */     return this.m_sIpAddress;
/*     */   }
/*     */ 
/*     */   public void setIpAddress(String a_sIpAddress) {
/*  71 */     this.m_sIpAddress = a_sIpAddress;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  76 */     return this.m_sType;
/*     */   }
/*     */ 
/*     */   public void setType(String a_sType) {
/*  80 */     this.m_sType = a_sType;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/*  85 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public void setId(long a_lId) {
/*  89 */     this.m_lId = a_lId;
/*     */   }
/*     */ 
/*     */   public String getLog()
/*     */   {
/*  94 */     return this.m_sLog;
/*     */   }
/*     */ 
/*     */   public void setLog(String a_sLog) {
/*  98 */     this.m_sLog = a_sLog;
/*     */   }
/*     */ 
/*     */   public long getAssetId()
/*     */   {
/* 103 */     return this.m_lAssetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_lAssetId) {
/* 107 */     this.m_lAssetId = a_lAssetId;
/*     */   }
/*     */ 
/*     */   public Date getLoginTime()
/*     */   {
/* 125 */     return this.m_dtLoginTime;
/*     */   }
/*     */ 
/*     */   public void setLoginTime(Date a_loginTime) {
/* 129 */     this.m_dtLoginTime = a_loginTime;
/*     */   }
/*     */ 
/*     */   public Date getSessionStartTime() {
/* 133 */     return this.m_dtSessionStartTime;
/*     */   }
/*     */ 
/*     */   public void setSessionStartTime(Date a_sessionStartTime) {
/* 137 */     this.m_dtSessionStartTime = a_sessionStartTime;
/*     */   }
/*     */ 
/*     */   public String getThumbnailPath()
/*     */   {
/* 142 */     return this.m_sThumbnailPath;
/*     */   }
/*     */ 
/*     */   public String getRc4EncryptedThumbnailPath()
/*     */   {
/* 147 */     return FileUtil.encryptFilepath(this.m_sThumbnailPath);
/*     */   }
/*     */ 
/*     */   public void setThumbnailPath(String a_sThumbnailPath)
/*     */   {
/* 152 */     this.m_sThumbnailPath = a_sThumbnailPath;
/*     */   }
/*     */ 
/*     */   public boolean getHasIcon()
/*     */   {
/* 157 */     return (this.m_sThumbnailPath != null) && (this.m_sThumbnailPath.startsWith(StoredFileType.SHARED_THUMBNAIL.getDirectoryName()));
/*     */   }
/*     */ 
/*     */   public static class DateComparatorAsc
/*     */     implements Comparator
/*     */   {
/*     */     public int compare(Object o1, Object o2)
/*     */     {
/* 114 */       if (((o1 instanceof AssetAuditLogEntry)) && ((o2 instanceof AssetAuditLogEntry)))
/*     */       {
/* 116 */         return ((AssetAuditLogEntry)o2).getDate().compareTo(((AssetAuditLogEntry)o1).getDate());
/*     */       }
/*     */ 
/* 119 */       return 0;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.AssetAuditLogEntry
 * JD-Core Version:    0.6.0
 */