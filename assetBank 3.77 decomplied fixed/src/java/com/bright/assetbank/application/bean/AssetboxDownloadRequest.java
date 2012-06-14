/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.assetbank.usage.bean.AssetUse;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class AssetboxDownloadRequest extends QueuedItem
/*     */ {
/*  40 */   private Vector m_vecAssets = null;
/*  41 */   private String m_sFilename = null;
/*  42 */   private FileFormat m_format = null;
/*  43 */   private AssetUse m_assetUse = null;
/*  44 */   private long m_lUserId = 0L;
/*  45 */   private ImageConversionInfo m_conversionInfo = null;
/*  46 */   private HttpSession m_session = null;
/*     */   private Language m_language;
/*  49 */   private String m_sPreserveFormatList = null;
/*  50 */   private Set<Long> m_secondaryUsageTypes = null;
/*     */ 
/*     */   public AssetboxDownloadRequest(Vector a_sVecAssets, String a_sFilename, FileFormat a_sFormat, AssetUse a_sAssetUse, long a_sUserId, long a_lIdForSession, ImageConversionInfo a_sConversionInfo, String a_sPreserveFormatList, HttpSession a_session, Set<Long> a_secondaryUsageTypes)
/*     */   {
/*  73 */     this.m_vecAssets = a_sVecAssets;
/*  74 */     this.m_sFilename = a_sFilename;
/*  75 */     this.m_format = a_sFormat;
/*  76 */     this.m_assetUse = a_sAssetUse;
/*  77 */     this.m_lUserId = a_sUserId;
/*  78 */     this.m_conversionInfo = a_sConversionInfo;
/*  79 */     this.m_sPreserveFormatList = a_sPreserveFormatList;
/*  80 */     this.m_session = a_session;
/*  81 */     this.m_language = UserProfile.getUserProfile(a_session).getCurrentLanguage();
/*  82 */     setJobId(a_lIdForSession);
/*  83 */     this.m_secondaryUsageTypes = a_secondaryUsageTypes;
/*     */   }
/*     */ 
/*     */   public void processDownloadComplete(List a_files)
/*     */   {
/*  95 */     if (this.m_session != null)
/*     */     {
/*     */       try
/*     */       {
/*  99 */         this.m_session.setAttribute("assetboxDownloadFiles", a_files);
/*     */       }
/*     */       catch (IllegalStateException ise)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public AssetUse getAssetUse()
/*     */   {
/* 110 */     return this.m_assetUse;
/*     */   }
/*     */ 
/*     */   public void setAssetUse(AssetUse a_sAssetUse)
/*     */   {
/* 115 */     this.m_assetUse = a_sAssetUse;
/*     */   }
/*     */ 
/*     */   public ImageConversionInfo getConversionInfo()
/*     */   {
/* 120 */     return this.m_conversionInfo;
/*     */   }
/*     */ 
/*     */   public void setConversionInfo(ImageConversionInfo a_sConversionInfo)
/*     */   {
/* 125 */     this.m_conversionInfo = a_sConversionInfo;
/*     */   }
/*     */ 
/*     */   public FileFormat getFormat()
/*     */   {
/* 130 */     return this.m_format;
/*     */   }
/*     */ 
/*     */   public void setFormat(FileFormat a_sFormat)
/*     */   {
/* 135 */     this.m_format = a_sFormat;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/* 140 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_sUserId)
/*     */   {
/* 145 */     this.m_lUserId = a_sUserId;
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/* 150 */     return this.m_sFilename;
/*     */   }
/*     */ 
/*     */   public void setFilename(String a_sFilename)
/*     */   {
/* 155 */     this.m_sFilename = a_sFilename;
/*     */   }
/*     */ 
/*     */   public Language getLanguage()
/*     */   {
/* 160 */     return this.m_language;
/*     */   }
/*     */ 
/*     */   public Vector getVecAssets()
/*     */   {
/* 165 */     return this.m_vecAssets;
/*     */   }
/*     */ 
/*     */   public void setVecAssets(Vector a_sVecAssets)
/*     */   {
/* 170 */     this.m_vecAssets = a_sVecAssets;
/*     */   }
/*     */ 
/*     */   public String getPreserveFormatList()
/*     */   {
/* 175 */     return this.m_sPreserveFormatList;
/*     */   }
/*     */ 
/*     */   public void setPreserveFormatList(String a_sPreserveFormatList) {
/* 179 */     this.m_sPreserveFormatList = a_sPreserveFormatList;
/*     */   }
/*     */ 
/*     */   public HttpSession getSession()
/*     */   {
/* 184 */     return this.m_session;
/*     */   }
/*     */ 
/*     */   public Set<Long> getSecondaryUsageTypes()
/*     */   {
/* 189 */     return this.m_secondaryUsageTypes;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.AssetboxDownloadRequest
 * JD-Core Version:    0.6.0
 */