/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AudioAsset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class AssetManagerUtil
/*     */   implements AssetBankConstants
/*     */ {
/*     */   public static Asset createAssetFromResultSet(ResultSet a_rs)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/*  50 */     long lAssetTypeId = a_rs.getLong("assAssetTypeId");
/*  51 */     Asset asset = createAssetOfType(lAssetTypeId);
/*     */ 
/*  55 */     asset.setId(a_rs.getLong("assId"));
/*  56 */     asset.setTypeId(a_rs.getLong("assAssetTypeId"));
/*  57 */     asset.setCode(a_rs.getString("Code"));
/*  58 */     asset.setFileLocation(a_rs.getString("FileLocation"));
/*  59 */     asset.setOriginalFileLocation(a_rs.getString("OriginalFileLocation"));
/*  60 */     asset.setOriginalFilename(a_rs.getString("OriginalFilename"));
/*  61 */     asset.setFileSizeInBytes(a_rs.getLong("FileSizeInBytes"));
/*  62 */     asset.getPrice().setAmount(a_rs.getLong("Price"));
/*  63 */     asset.setImportedAssetId(a_rs.getString("ImportedAssetId"));
/*  64 */     asset.setIsRestricted(a_rs.getBoolean("IsPreviewRestricted"));
/*  65 */     asset.setIsSensitive(a_rs.getBoolean("IsSensitive"));
/*  66 */     asset.setSynchronised(a_rs.getBoolean("Synchronised"));
/*     */ 
/*  68 */     if (a_rs.getLong("ffId") > 0L)
/*     */     {
/*  70 */       FileFormat format = new FileFormat();
/*  71 */       format.setId(a_rs.getLong("ffId"));
/*  72 */       format.setAssetTypeId(a_rs.getLong("ffAssetTypeId"));
/*  73 */       format.setName(a_rs.getString("ffName"));
/*  74 */       format.setDescription(a_rs.getString("ffDescription"));
/*  75 */       format.setFileExtension(a_rs.getString("FileExtension"));
/*  76 */       format.setIndexable(a_rs.getBoolean("IsIndexable"));
/*  77 */       format.setConvertable(a_rs.getBoolean("IsConvertable"));
/*  78 */       format.setConversionTarget(a_rs.getBoolean("IsConversionTarget"));
/*  79 */       format.setThumbnailImageLocation(a_rs.getString("ffThumbnailFileLocation"));
/*  80 */       format.setContentType(a_rs.getString("ContentType"));
/*  81 */       format.setConverterClass(a_rs.getString("ConverterClass"));
/*  82 */       format.setAssetToTextConverterClass(a_rs.getString("ToTextConverterClass"));
/*  83 */       format.setViewFileInclude(a_rs.getString("ViewFileInclude"));
/*  84 */       format.setPreviewInclude(a_rs.getString("PreviewInclude"));
/*  85 */       format.setPreviewHeight(a_rs.getInt("PreviewHeight"));
/*  86 */       format.setPreviewWidth(a_rs.getInt("PreviewWidth"));
/*  87 */       format.setCanConvertIndividualLayers(a_rs.getBoolean("ConvertIndividualLayers"));
/*  88 */       format.setCanViewOriginal(a_rs.getBoolean("CanViewOriginal"));
/*     */ 
/*  90 */       asset.setFormat(format);
/*     */     }
/*     */     else
/*     */     {
/*  94 */       asset.setFormat(FileFormat.s_unknownFileFormat);
/*     */     }
/*     */ 
/*  99 */     if ((a_rs.getString("assThumbnailFileLocation") == null) || (a_rs.getString("assThumbnailFileLocation").trim().length() == 0))
/*     */     {
/* 102 */       asset.setThumbnailImageFile(new ImageFile(asset.getFormat().getThumbnailImageLocation()));
/*     */     }
/*     */     else
/*     */     {
/* 106 */       asset.setThumbnailImageFile(new ImageFile(a_rs.getString("assThumbnailFileLocation")));
/*     */     }
/*     */ 
/* 110 */     if ((a_rs.getString("assSmallFileLocation") != null) && (a_rs.getString("assSmallFileLocation").trim().length() > 0))
/*     */     {
/* 113 */       asset.setHomogenizedImageFile(new ImageFile(a_rs.getString("assSmallFileLocation")));
/*     */     }
/*     */ 
/* 117 */     if ((a_rs.getString("assMediumFileLocation") != null) && (a_rs.getString("assMediumFileLocation").trim().length() > 0))
/*     */     {
/* 120 */       asset.setPreviewImageFile(new ImageFile(a_rs.getString("assMediumFileLocation")));
/*     */     }
/*     */ 
/* 125 */     if (lAssetTypeId == 2L)
/*     */     {
/* 127 */       ImageAsset image = (ImageAsset)asset;
/* 128 */       image.setHeight(a_rs.getInt("Height"));
/* 129 */       image.setWidth(a_rs.getInt("Width"));
/* 130 */       image.setColorSpace(a_rs.getInt("ColorSpace"));
/* 131 */       image.setNumPages(a_rs.getInt("NumLayers"));
/*     */     }
/*     */ 
/* 134 */     return asset;
/*     */   }
/*     */ 
/*     */   public static Asset createAssetOfType(long a_lAssetTypeId)
/*     */     throws Bn2Exception
/*     */   {
/*     */     Asset asset;
/*     */    // Asset asset;
/* 155 */     if (a_lAssetTypeId == 2L)
/*     */     {
/* 157 */       asset = new ImageAsset();
/*     */     }
/*     */     else
/*     */     {
/*     */       //Asset asset;
/* 159 */       if (a_lAssetTypeId == 3L)
/*     */       {
/* 161 */         asset = new VideoAsset();
/*     */       }
/*     */       else
/*     */       {
/*     */        // Asset asset;
/* 163 */         if (a_lAssetTypeId == 4L)
/*     */         {
/* 165 */           asset = new AudioAsset();
/*     */         }
/*     */         else
/*     */         {
/*     */           //Asset asset;
/* 167 */           if (a_lAssetTypeId == 1L)
/*     */           {
/* 169 */             asset = new Asset();
/*     */           }
/*     */           else
/*     */           {
/* 173 */             asset = new Asset();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 176 */     return asset;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetManagerUtil
 * JD-Core Version:    0.6.0
 */