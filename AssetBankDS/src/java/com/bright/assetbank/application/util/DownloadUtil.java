/*     */ package com.bright.assetbank.application.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.bean.MediaAssetConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.usage.bean.Mask;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */ import com.bright.assetbank.usage.service.MaskManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.util.ExifTool;
/*     */ import com.bright.framework.image.util.GeometryUtil;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class DownloadUtil
/*     */ {
/*     */   public static String getDownloadFilename(Asset a_asset, String a_sExtension)
/*     */   {
/*  70 */     String sExtension = a_sExtension;
/*     */ 
/*  72 */     if (sExtension == null)
/*     */     {
/*  74 */       if (a_asset.getFormat() != FileFormat.s_unknownFileFormat)
/*     */       {
/*  76 */         sExtension = a_asset.getFormat().getFileExtension();
/*     */       }
/*     */       else
/*     */       {
/*  80 */         sExtension = FileUtil.getSuffix(a_asset.getFileLocation());
/*     */       }
/*     */     }
/*     */ 
/*  84 */     return getClientFilename(a_asset, sExtension);
/*     */   }
/*     */ 
/*     */   public static String getClientFilename(Asset a_asset, String a_sExtension)
/*     */   {
/*  97 */     String sCandidateFilename = String.valueOf(a_asset.getId());
/*     */ 
/*  99 */     if (!AssetBankSettings.getUseIdForDownloadFilenames())
/*     */     {
/* 101 */       if (AssetBankSettings.getDownloadFilenameUseOriginal())
/*     */       {
/* 103 */         sCandidateFilename = FileUtil.getFilepathWithoutSuffix(a_asset.getFileName());
/*     */       }
/* 105 */       else if (a_asset.getName() != null)
/*     */       {
/* 107 */         sCandidateFilename = FileUtil.getFilepathWithoutSuffix(a_asset.getName());
/*     */       }
/*     */     }
/*     */ 
/* 111 */     if (a_sExtension != null)
/*     */     {
/* 113 */       sCandidateFilename = sCandidateFilename + "." + a_sExtension;
/*     */     }
/*     */ 
/* 116 */     String sClientFilename = FileUtil.getSafeFilename(sCandidateFilename, false);
/* 117 */     return sClientFilename;
/*     */   }
/*     */ 
/*     */   public static void checkGroupDownloadSize(ABUserProfile a_userProfile, int a_iHeight, int a_iWidth, ListManager a_listManager, DBTransaction a_transaction, Bn2Form a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 132 */     if (!a_userProfile.getIsAdmin())
/*     */     {
/* 135 */       int iMaxWidth = a_userProfile.getMaxGroupDownloadWidth();
/* 136 */       int iMaxHeight = a_userProfile.getMaxGroupDownloadHeight();
/*     */ 
/* 138 */       if (((iMaxHeight > 0) && (a_iHeight > iMaxHeight)) || ((iMaxWidth > 0) && (a_iWidth > iMaxWidth)))
/*     */       {
/* 141 */         a_form.addError(a_listManager.getListItem(a_transaction, "failedValidationGroupSize", a_userProfile.getCurrentLanguage()).getBody() + " (" + iMaxWidth + "px x " + iMaxHeight + "px)");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void populateSimpleConversionInfo(MaskManager a_maskManager, DBTransaction a_dbTransaction, ImageConversionInfo a_conversionInfo, UsageTypeFormat utf, DownloadForm a_form, ImageAsset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 159 */     a_conversionInfo.setMaxWidth(utf.getWidth());
/* 160 */     a_conversionInfo.setMaxHeight(utf.getHeight());
/* 161 */     if (!utf.getCropToFit())
/*     */     {
/* 163 */       a_conversionInfo.setMaintainAspectRatio(true);
/*     */     }
/*     */ 
/* 173 */     if ((a_form.getCropWidth() <= 0) || (a_form.getCropHeight() <= 0))
/*     */     {
/* 175 */       if (utf.getCropToFit())
/*     */       {
/* 182 */         Dimension aspectRatio = new Dimension(utf.getWidth(), utf.getHeight());
/* 183 */         Dimension boundingBox = new Dimension(a_asset.getWidth(), a_asset.getHeight());
/* 184 */         Rectangle centred = GeometryUtil.fitAndCentre(aspectRatio, boundingBox);
/*     */ 
/* 187 */         a_conversionInfo.setCropRectangle(centred);
/*     */       }
/* 189 */       else if ((a_form.getCropMask()) && (a_form.getCropMaskId() > 0L))
/*     */       {
/* 191 */         a_form.copyMaskAndColourToConversionInfo(a_conversionInfo);
/*     */ 
/* 193 */         Mask mask = a_maskManager.getMaskById(a_dbTransaction, a_form.getCropMaskId());
/* 194 */         Dimension maskAspectRatio = mask.getImageSize();
/* 195 */         Dimension boundingBox = new Dimension(a_asset.getWidth(), a_asset.getHeight());
/* 196 */         Rectangle centred = GeometryUtil.fitAndCentre(maskAspectRatio, boundingBox);
/*     */ 
/* 199 */         a_conversionInfo.setCropRectangle(centred);
/*     */       }
/*     */     }
/*     */ 
/* 203 */     a_conversionInfo.setScaleUp(utf.getScaleUp());
/* 204 */     a_conversionInfo.setDensity(utf.getDensity());
/* 205 */     a_conversionInfo.setJpegQuality(utf.getJpegQuality() / 100.0F);
/* 206 */     a_conversionInfo.setApplyStrip(utf.getApplyStrip());
/* 207 */     a_conversionInfo.setAddWatermark(utf.getWatermark());
/*     */   }
/*     */ 
/*     */   public static String embedMetadata(String a_sRelativeDownloadPath, Asset a_asset, boolean a_bMakeCopyIfChanging, boolean a_bDontEmbedMetadata, MediaAssetConversionInfo conversionInfo, AttributeManager a_attributeManager, FileStoreManager a_fileStoreManager)
/*     */   {
/* 220 */     String sPath = null;
/*     */     try
/*     */     {
/* 224 */       HashMap vecExtraFields = null;
/*     */ 
/* 227 */       if ((conversionInfo != null) && (conversionInfo.getDensity() > 0))
/*     */       {
/* 229 */         vecExtraFields = new HashMap();
/*     */ 
/* 232 */         vecExtraFields.put(ExifTool.c_sXMPXResolution, "" + conversionInfo.getDensity());
/* 233 */         vecExtraFields.put(ExifTool.c_sXMPYResolution, "" + conversionInfo.getDensity());
/*     */       }
/*     */       else
/*     */       {
/* 238 */         Vector vecMappings = a_attributeManager.getEmbeddedDataMappings(null, -1L, 2L);
/*     */ 
/* 242 */         if ((vecMappings == null) || (vecMappings.size() == 0))
/*     */         {
/* 245 */           return a_sRelativeDownloadPath;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 250 */       if ((a_bMakeCopyIfChanging) && (!a_bDontEmbedMetadata))
/*     */       {
/* 253 */         sPath = a_fileStoreManager.getUniqueFilepath(FileUtil.getFilename(a_sRelativeDownloadPath), StoredFileType.TEMP);
/*     */ 
/* 256 */         FileUtil.copyFile(a_fileStoreManager.getAbsolutePath(a_sRelativeDownloadPath), a_fileStoreManager.getAbsolutePath(sPath));
/*     */       }
/*     */       else
/*     */       {
/* 262 */         sPath = a_sRelativeDownloadPath;
/*     */       }
/*     */ 
/* 266 */       if (!a_bDontEmbedMetadata)
/*     */       {
/* 268 */         a_attributeManager.embedMetadataValues(a_fileStoreManager.getAbsolutePath(sPath), a_asset, 2L, vecExtraFields);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Bn2Exception bn2)
/*     */     {
/* 275 */       GlobalApplication.getInstance().getLogger().error("DownloadImageAction: Exception while trying to embed metadata:", bn2);
/*     */     }
/*     */ 
/* 279 */     return sPath;
/*     */   }
/*     */ 
/*     */   public static boolean treatFileAsDocumentForDownload(String a_sFileExtension)
/*     */   {
/* 285 */     String sExtensions = AssetBankSettings.getDownloadImageFileAsDocumentExtensions();
/*     */ 
/* 287 */     if ((a_sFileExtension == null) || (StringUtils.isEmpty(sExtensions)))
/*     */     {
/* 289 */       return false;
/*     */     }
/*     */ 
/* 292 */     return new HashSet(Arrays.asList(sExtensions.toLowerCase().split("[, ]+"))).contains(a_sFileExtension.toLowerCase());
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.util.DownloadUtil
 * JD-Core Version:    0.6.0
 */