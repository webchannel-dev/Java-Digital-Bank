/*     */ package com.bright.assetbank.repurposing.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class RepurposedSlideshow extends RepurposedVersion
/*     */ {
/*  37 */   private int m_iDisplayTime = Integer.parseInt("5");
/*  38 */   private boolean m_bInfoBar = false;
/*  39 */   private boolean m_bFullScreen = false;
/*  40 */   private String m_sFilename = null;
/*  41 */   private String m_sDirectory = null;
/*  42 */   private SearchQuery m_criteria = null;
/*  43 */   private String m_sCriteriaFile = null;
/*  44 */   private boolean m_bIncludeLabels = false;
/*  45 */   private boolean m_bMaintainAspectRatio = true;
/*  46 */   private Collection<String> m_captionIds = null;
/*  47 */   private String m_sCaptionIds = "";
/*  48 */   private int m_iIntJpgConversionQuality = -1;
/*  49 */   private String m_sLanguageCode = null;
/*  50 */   private int m_iImageHeight = -1;
/*  51 */   private int m_iImageWidth = -1;
/*  52 */   private String m_sDescription = "";
/*     */ 
/*  54 */   private Collection<Asset> m_assets = null;
/*  55 */   private ImageConversionInfo m_conversionInfo = null;
/*     */ 
/*  57 */   private long m_lDisplayTypeId = 1L;
/*  58 */   private String m_sDisplayType = "";
/*  59 */   private boolean m_bShowInListOnHomepage = false;
/*  60 */   private boolean m_bDefaultOnHomepage = false;
/*  61 */   private long m_lSequence = 0L;
/*  62 */   private long m_lCaptionAttributeId = 0L;
/*  63 */   private long m_lCreditAttributeId = 0L;
/*     */ 
/*     */   public void setDisplayTime(int a_iDisplayTime)
/*     */   {
/*  67 */     this.m_iDisplayTime = a_iDisplayTime;
/*     */   }
/*     */ 
/*     */   public int getDisplayTime()
/*     */   {
/*  72 */     return this.m_iDisplayTime;
/*     */   }
/*     */ 
/*     */   public void setInfoBar(boolean a_bInfoBar)
/*     */   {
/*  77 */     this.m_bInfoBar = a_bInfoBar;
/*     */   }
/*     */ 
/*     */   public boolean getInfoBar()
/*     */   {
/*  82 */     return this.m_bInfoBar;
/*     */   }
/*     */ 
/*     */   public boolean getFullScreen()
/*     */   {
/*  87 */     return this.m_bFullScreen;
/*     */   }
/*     */ 
/*     */   public void setFullScreen(boolean a_bFullScreen)
/*     */   {
/*  92 */     this.m_bFullScreen = a_bFullScreen;
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/*  97 */     if (this.m_sFilename == null)
/*     */     {
/*  99 */       this.m_sFilename = FileUtil.getFilename(getUrl());
/*     */     }
/* 101 */     return this.m_sFilename;
/*     */   }
/*     */ 
/*     */   public String getDirectory()
/*     */   {
/* 106 */     if (this.m_sDirectory == null)
/*     */     {
/* 108 */       this.m_sDirectory = FileUtil.getParentDirectoryPath(getUrl());
/*     */     }
/* 110 */     return this.m_sDirectory;
/*     */   }
/*     */ 
/*     */   public void setSearchCriteria(SearchQuery a_criteria)
/*     */   {
/* 115 */     this.m_criteria = a_criteria;
/*     */   }
/*     */ 
/*     */   public SearchQuery getSearchCriteria()
/*     */   {
/* 120 */     return this.m_criteria;
/*     */   }
/*     */ 
/*     */   public void setCriteriaFile(String a_sCriteriaFile)
/*     */   {
/* 125 */     this.m_sCriteriaFile = a_sCriteriaFile;
/*     */   }
/*     */ 
/*     */   public String getCriteriaFile()
/*     */   {
/* 130 */     return this.m_sCriteriaFile;
/*     */   }
/*     */ 
/*     */   public void setIncludeLabels(boolean a_bIncludeLabels)
/*     */   {
/* 135 */     this.m_bIncludeLabels = a_bIncludeLabels;
/*     */   }
/*     */ 
/*     */   public boolean getIncludeLabels()
/*     */   {
/* 140 */     return this.m_bIncludeLabels;
/*     */   }
/*     */ 
/*     */   public void setMaintainAspectRatio(boolean a_bMaintainAspectRatio)
/*     */   {
/* 145 */     this.m_bMaintainAspectRatio = a_bMaintainAspectRatio;
/*     */   }
/*     */ 
/*     */   public boolean getMaintainAspectRatio()
/*     */   {
/* 150 */     return this.m_bMaintainAspectRatio;
/*     */   }
/*     */ 
/*     */   public void setIntJpgConversionQuality(int a_iIntJpgConversionQuality)
/*     */   {
/* 155 */     this.m_iIntJpgConversionQuality = a_iIntJpgConversionQuality;
/*     */   }
/*     */ 
/*     */   public int getIntJpgConversionQuality()
/*     */   {
/* 160 */     return this.m_iIntJpgConversionQuality;
/*     */   }
/*     */ 
/*     */   public void setAssets(Collection<Asset> a_assets)
/*     */   {
/* 165 */     this.m_assets = a_assets;
/*     */   }
/*     */ 
/*     */   public Collection<Asset> getAssets()
/*     */   {
/* 170 */     return this.m_assets;
/*     */   }
/*     */ 
/*     */   public void setConversionInfo(ImageConversionInfo a_conversionInfo)
/*     */   {
/* 175 */     this.m_conversionInfo = a_conversionInfo;
/*     */   }
/*     */ 
/*     */   public ImageConversionInfo getConversionInfo()
/*     */   {
/* 180 */     return this.m_conversionInfo;
/*     */   }
/*     */ 
/*     */   public void setCaptionIds(Collection<String> a_captionIds)
/*     */   {
/* 185 */     this.m_captionIds = a_captionIds;
/*     */   }
/*     */ 
/*     */   public Collection<String> getCaptionIds()
/*     */   {
/* 190 */     return this.m_captionIds;
/*     */   }
/*     */ 
/*     */   public String getCaptionIdsString()
/*     */   {
/* 195 */     if (!StringUtil.stringIsPopulated(this.m_sCaptionIds))
/*     */     {
/* 197 */       if (getCaptionIds() != null)
/*     */       {
/* 199 */         for (String sId : getCaptionIds())
/*     */         {
/* 201 */           this.m_sCaptionIds = (this.m_sCaptionIds + sId + ",");
/*     */         }
/*     */       }
/*     */     }
/* 205 */     return this.m_sCaptionIds;
/*     */   }
/*     */ 
/*     */   public void setLanguageCode(String a_sLanguageCode)
/*     */   {
/* 210 */     this.m_sLanguageCode = a_sLanguageCode;
/*     */   }
/*     */ 
/*     */   public String getLanguageCode()
/*     */   {
/* 215 */     return this.m_sLanguageCode;
/*     */   }
/*     */ 
/*     */   public void setImageHeight(int a_iImageHeight)
/*     */   {
/* 220 */     this.m_iImageHeight = a_iImageHeight;
/*     */   }
/*     */ 
/*     */   public int getImageHeight()
/*     */   {
/* 225 */     return this.m_iImageHeight;
/*     */   }
/*     */ 
/*     */   public void setImageWidth(int a_iImageWidth)
/*     */   {
/* 230 */     this.m_iImageWidth = a_iImageWidth;
/*     */   }
/*     */ 
/*     */   public int getImageWidth()
/*     */   {
/* 235 */     return this.m_iImageWidth;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/* 240 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 245 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public long getDisplayTypeId()
/*     */   {
/* 250 */     return this.m_lDisplayTypeId;
/*     */   }
/*     */ 
/*     */   public void setDisplayTypeId(long a_lDisplayTypeId) {
/* 254 */     this.m_lDisplayTypeId = a_lDisplayTypeId;
/*     */   }
/*     */ 
/*     */   public void setDisplayType(String a_sDisplayType)
/*     */   {
/* 259 */     this.m_sDisplayType = a_sDisplayType;
/*     */   }
/*     */ 
/*     */   public String getDisplayType() {
/* 263 */     return this.m_sDisplayType;
/*     */   }
/*     */ 
/*     */   public void setShowInListOnHomepage(boolean a_bShowInListOnHomepage)
/*     */   {
/* 268 */     this.m_bShowInListOnHomepage = a_bShowInListOnHomepage;
/*     */   }
/*     */ 
/*     */   public boolean getShowInListOnHomepage() {
/* 272 */     return this.m_bShowInListOnHomepage;
/*     */   }
/*     */ 
/*     */   public void setDefaultOnHomepage(boolean a_bDefaultOnHomepage)
/*     */   {
/* 277 */     this.m_bDefaultOnHomepage = a_bDefaultOnHomepage;
/*     */   }
/*     */ 
/*     */   public boolean getDefaultOnHomepage() {
/* 281 */     return this.m_bDefaultOnHomepage;
/*     */   }
/*     */ 
/*     */   public void setSequence(long a_lSequence)
/*     */   {
/* 286 */     this.m_lSequence = a_lSequence;
/*     */   }
/*     */ 
/*     */   public long getSequence() {
/* 290 */     return this.m_lSequence;
/*     */   }
/*     */ 
/*     */   public void setCaptionAttributeId(long a_lCaptionAttributeId)
/*     */   {
/* 295 */     this.m_lCaptionAttributeId = a_lCaptionAttributeId;
/*     */   }
/*     */ 
/*     */   public long getCaptionAttributeId() {
/* 299 */     return this.m_lCaptionAttributeId;
/*     */   }
/*     */ 
/*     */   public void setCreditAttributeId(long a_lCreditAttributeId)
/*     */   {
/* 304 */     this.m_lCreditAttributeId = a_lCreditAttributeId;
/*     */   }
/*     */ 
/*     */   public long getCreditAttributeId() {
/* 308 */     return this.m_lCreditAttributeId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.bean.RepurposedSlideshow
 * JD-Core Version:    0.6.0
 */