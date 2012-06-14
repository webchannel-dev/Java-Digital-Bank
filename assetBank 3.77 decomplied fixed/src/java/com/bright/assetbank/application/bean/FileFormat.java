/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.converter.AssetConverter;
/*     */ import com.bright.assetbank.converter.AssetToTextConverter;
/*     */ import com.bright.assetbank.converter.ConverterFactory;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ 
/*     */ public class FileFormat extends DataBean
/*     */   implements AssetBankConstants
/*     */ {
/*  39 */   private long m_lAssetTypeId = 0L;
/*  40 */   private String m_sFileExtension = null;
/*  41 */   private String m_sName = null;
/*  42 */   private String m_sDescription = null;
/*  43 */   private boolean m_bIsIndexable = false;
/*  44 */   private boolean m_bIsConvertable = false;
/*  45 */   private boolean m_bIsConversionTarget = false;
/*  46 */   private String m_sThumbnailImageLocation = null;
/*  47 */   private String m_sContentType = null;
/*  48 */   private String m_sConverterClass = null;
/*  49 */   private String m_sAssetToTextConverterClass = null;
/*  50 */   private AssetConverter m_converter = null;
/*  51 */   private AssetToTextConverter m_assetToTextConverter = null;
/*  52 */   private String m_sViewFileInclude = null;
/*  53 */   private String m_sPreviewInclude = null;
/*  54 */   private int m_iPreviewHeight = 0;
/*  55 */   private int m_iPreviewWidth = 0;
/*  56 */   private boolean m_bCanConvertIndividualLayers = false;
/*  57 */   private boolean m_bCanViewOriginal = false;
/*     */ 
/*  60 */   public static final FileFormat s_unknownFileFormat = new FileFormat();
/*  61 */   public static final FileFormat s_noFileFormat = new FileFormat();
/*     */ 
/*     */   public String getFileExtension()
/*     */   {
/*  83 */     return this.m_sFileExtension;
/*     */   }
/*     */ 
/*     */   public void setFileExtension(String a_sFileExtension)
/*     */   {
/*  91 */     this.m_sFileExtension = a_sFileExtension;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  99 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/* 107 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 115 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/* 123 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public long getAssetTypeId()
/*     */   {
/* 129 */     return this.m_lAssetTypeId;
/*     */   }
/*     */ 
/*     */   public void setAssetTypeId(long a_sAssetTypeId)
/*     */   {
/* 135 */     this.m_lAssetTypeId = a_sAssetTypeId;
/*     */   }
/*     */ 
/*     */   public boolean getIsIndexable()
/*     */   {
/* 141 */     return this.m_bIsIndexable;
/*     */   }
/*     */ 
/*     */   public void setIndexable(boolean a_sIsIndexable)
/*     */   {
/* 147 */     this.m_bIsIndexable = a_sIsIndexable;
/*     */   }
/*     */ 
/*     */   public boolean getIsConvertable()
/*     */   {
/* 153 */     return this.m_bIsConvertable;
/*     */   }
/*     */ 
/*     */   public void setConvertable(boolean a_sIsConvertable)
/*     */   {
/* 159 */     this.m_bIsConvertable = a_sIsConvertable;
/*     */   }
/*     */ 
/*     */   public boolean getIsConversionTarget()
/*     */   {
/* 165 */     return this.m_bIsConversionTarget;
/*     */   }
/*     */ 
/*     */   public void setConversionTarget(boolean a_sIsConversionTarget)
/*     */   {
/* 171 */     this.m_bIsConversionTarget = a_sIsConversionTarget;
/*     */   }
/*     */ 
/*     */   public String getThumbnailImageLocation()
/*     */   {
/* 177 */     return this.m_sThumbnailImageLocation;
/*     */   }
/*     */ 
/*     */   public void setThumbnailImageLocation(String a_sThumbnailImageLocation)
/*     */   {
/* 183 */     this.m_sThumbnailImageLocation = a_sThumbnailImageLocation;
/*     */   }
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 189 */     return this.m_sContentType;
/*     */   }
/*     */ 
/*     */   public void setContentType(String a_sContentType)
/*     */   {
/* 195 */     this.m_sContentType = a_sContentType;
/*     */   }
/*     */ 
/*     */   public String getConverterClass()
/*     */   {
/* 200 */     return this.m_sConverterClass;
/*     */   }
/*     */ 
/*     */   public void setConverterClass(String a_sConverterClass)
/*     */   {
/* 205 */     this.m_sConverterClass = a_sConverterClass;
/*     */   }
/*     */ 
/*     */   public AssetConverter getConverterInstance()
/*     */     throws Bn2Exception
/*     */   {
/* 222 */     if (this.m_converter == null)
/*     */     {
/* 224 */       if (getConverterClass() != null)
/*     */       {
/* 226 */         this.m_converter = ConverterFactory.getConverterInstance(getConverterClass());
/*     */       }
/*     */     }
/*     */ 
/* 230 */     return this.m_converter;
/*     */   }
/*     */ 
/*     */   public AssetToTextConverter getAssetToTextConverterInstance()
/*     */     throws Bn2Exception
/*     */   {
/* 247 */     if (this.m_assetToTextConverter == null)
/*     */     {
/* 249 */       if (getAssetToTextConverterClass() != null)
/*     */       {
/* 251 */         this.m_assetToTextConverter = ConverterFactory.getFileAssetConverterInstance(getAssetToTextConverterClass());
/*     */       }
/*     */     }
/*     */ 
/* 255 */     return this.m_assetToTextConverter;
/*     */   }
/*     */ 
/*     */   public String getAssetToTextConverterClass()
/*     */   {
/* 262 */     return this.m_sAssetToTextConverterClass;
/*     */   }
/*     */ 
/*     */   public void setAssetToTextConverterClass(String a_sAssetToTextConverterClass)
/*     */   {
/* 267 */     this.m_sAssetToTextConverterClass = a_sAssetToTextConverterClass;
/*     */   }
/*     */ 
/*     */   public String getPreviewInclude()
/*     */   {
/* 272 */     return this.m_sPreviewInclude;
/*     */   }
/*     */ 
/*     */   public void setPreviewInclude(String previewInclude)
/*     */   {
/* 277 */     this.m_sPreviewInclude = previewInclude;
/*     */   }
/*     */ 
/*     */   public int getPreviewHeight()
/*     */   {
/* 282 */     return this.m_iPreviewHeight;
/*     */   }
/*     */ 
/*     */   public void setPreviewHeight(int a_iPreviewHeight)
/*     */   {
/* 287 */     this.m_iPreviewHeight = a_iPreviewHeight;
/*     */   }
/*     */ 
/*     */   public int getPreviewWidth()
/*     */   {
/* 292 */     return this.m_iPreviewWidth;
/*     */   }
/*     */ 
/*     */   public void setPreviewWidth(int a_iPreviewWidth)
/*     */   {
/* 297 */     this.m_iPreviewWidth = a_iPreviewWidth;
/*     */   }
/*     */ 
/*     */   public boolean getCanConvertIndividualLayers()
/*     */   {
/* 302 */     return this.m_bCanConvertIndividualLayers;
/*     */   }
/*     */ 
/*     */   public void setCanConvertIndividualLayers(boolean a_bCanConvertIndividualLayers)
/*     */   {
/* 307 */     this.m_bCanConvertIndividualLayers = a_bCanConvertIndividualLayers;
/*     */   }
/*     */ 
/*     */   public boolean getCanViewOriginal()
/*     */   {
/* 312 */     return this.m_bCanViewOriginal;
/*     */   }
/*     */ 
/*     */   public void setCanViewOriginal(boolean canViewOriginal)
/*     */   {
/* 317 */     this.m_bCanViewOriginal = canViewOriginal;
/*     */   }
/*     */ 
/*     */   public String getViewFileInclude()
/*     */   {
/* 327 */     return this.m_sViewFileInclude;
/*     */   }
/*     */ 
/*     */   public void setViewFileInclude(String viewFileInclude)
/*     */   {
/* 332 */     this.m_sViewFileInclude = viewFileInclude;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  66 */     s_unknownFileFormat.m_lAssetTypeId = 1L;
/*  67 */     s_unknownFileFormat.m_sName = "unknown";
/*  68 */     s_unknownFileFormat.m_sDescription = "unknown";
/*  69 */     s_unknownFileFormat.m_sThumbnailImageLocation = AssetBankSettings.getUnknownFileThumbnail();
/*  70 */     s_unknownFileFormat.m_sContentType = "application/unknown";
/*     */ 
/*  72 */     s_noFileFormat.m_lAssetTypeId = 0L;
/*  73 */     s_noFileFormat.m_sName = "no file";
/*  74 */     s_noFileFormat.m_sDescription = "no file";
/*  75 */     s_noFileFormat.m_sThumbnailImageLocation = AssetBankSettings.getNoFileThumbnail();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.FileFormat
 * JD-Core Version:    0.6.0
 */