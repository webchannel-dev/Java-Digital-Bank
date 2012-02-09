/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.bean.MediaAssetConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.ABImageMagick;
/*     */ import com.bright.assetbank.application.util.DownloadUtil;
/*     */ import com.bright.assetbank.usage.bean.ColorSpace;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */ import com.bright.assetbank.usage.service.MaskManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.image.util.ImageUtil;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DownloadImageAction extends DownloadMediaAssetAction
/*     */ {
/*     */   private MaskManager m_maskManager;
/*     */ 
/*     */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vecVisibleAttributeIds)
/*     */     throws AssetNotFoundException, Bn2Exception
/*     */   {
/*  83 */     ImageAsset image = (ImageAsset)this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, a_vecVisibleAttributeIds, false, false);
/*  84 */     return image;
/*     */   }
/*     */ 
/*     */   public void validateImplementationValues(DBTransaction a_dbTransaction, DownloadForm a_form, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     if ((a_form.getJpegQuality() < 0) || (a_form.getJpegQuality() > 100))
/*     */     {
/*  93 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationInvalidJpegQuality", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  97 */     DownloadUtil.checkGroupDownloadSize(a_userProfile, a_form.getHeight(), a_form.getWidth(), this.m_listManager, a_dbTransaction, a_form);
/*     */   }
/*     */ 
/*     */   public void validateUsageAndConditions(DownloadForm a_form, ABUserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 103 */     super.validateUsageAndConditions(a_form, a_userProfile, a_dbTransaction, a_listManager, a_asset);
/*     */ 
/* 107 */     if (!a_form.getHasErrors())
/*     */     {
/* 109 */       if ((a_form.getAdvanced()) && (a_form.getImageFormat() != null) && (a_form.getLayerToDownload() == 0) && (!ImageUtil.supportsMultiLayers(a_form.getImageFormat(), true)))
/*     */       {
/* 111 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoLayerSupport", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/* 113 */       else if ((!a_form.getAdvanced()) && (a_form.getUsageTypeFormatId() > 0L))
/*     */       {
/* 115 */         UsageTypeFormat utf = this.m_usageManager.getUsageTypeFormat(a_dbTransaction, a_form.getUsageTypeFormatId());
/*     */ 
/* 117 */         if (a_form.getLayerToDownload() == 0)
/*     */         {
/* 120 */           if ((utf.getPreserveFormatList() != null) && (a_asset != null) && (a_asset.getFormat() != null) && (a_asset.getFormat().getFileExtension() != null))
/*     */           {
/* 122 */             String[] preserveList = utf.getPreserveFormatList().split(";");
/* 123 */             String sExt = a_asset.getFormat().getFileExtension().toLowerCase();
/* 124 */             Arrays.sort(preserveList);
/* 125 */             if (Arrays.binarySearch(preserveList, sExt) >= 0)
/*     */             {
/* 127 */               return;
/*     */             }
/*     */           }
/*     */ 
/* 131 */           if (!ImageUtil.supportsMultiLayers(utf.getFormatId()))
/*     */           {
/* 133 */             a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoLayerSupport", a_userProfile.getCurrentLanguage()).getBody());
/*     */           }
/*     */         }
/*     */ 
/* 137 */         ImageAsset image = (ImageAsset)a_asset;
/* 138 */         if ((!utf.getScaleUp()) && (utf.getHeight() > image.getHeight()) && (utf.getWidth() > image.getWidth()))
/*     */         {
/* 140 */           DownloadUtil.checkGroupDownloadSize(a_userProfile, image.getHeight(), image.getWidth(), this.m_listManager, a_dbTransaction, a_form);
/*     */         }
/*     */         else
/*     */         {
/* 144 */           DownloadUtil.checkGroupDownloadSize(a_userProfile, utf.getHeight(), utf.getWidth(), this.m_listManager, a_dbTransaction, a_form);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public MediaAssetConversionInfo getConversionInfo(DBTransaction a_dbTransaction, HttpServletRequest a_request, DownloadForm a_form, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 154 */     ImageConversionInfo conversionInfo = new ImageConversionInfo();
/* 155 */     ImageAsset image = (ImageAsset)a_asset;
/*     */ 
/* 158 */     if ((a_form.getCropHeight() > 0) && (a_form.getCropWidth() > 0) && ((a_form.getCropHeight() != image.getHeight()) || (a_form.getCropWidth() != image.getWidth())))
/*     */     {
/* 161 */       conversionInfo.setCropHeight(a_form.getCropHeight());
/* 162 */       conversionInfo.setCropWidth(a_form.getCropWidth());
/* 163 */       conversionInfo.setCropX(a_form.getCropX());
/* 164 */       conversionInfo.setCropY(a_form.getCropY());
/*     */ 
/* 166 */       a_form.copyMaskAndColourToConversionInfo(conversionInfo);
/*     */     }
/*     */ 
/* 170 */     if ((AssetBankSettings.getSimpleConvertOptionsForDownload()) && (!a_form.getAdvanced()))
/*     */     {
/* 173 */       UsageTypeFormat utf = this.m_usageManager.getUsageTypeFormat(a_dbTransaction, a_form.getUsageTypeFormatId());
/* 174 */       DownloadUtil.populateSimpleConversionInfo(this.m_maskManager, a_dbTransaction, conversionInfo, utf, a_form, image);
/*     */ 
/* 177 */       a_form.setWidth(utf.getWidth());
/* 178 */       a_form.setHeight(utf.getHeight());
/*     */     }
/*     */     else
/*     */     {
/* 183 */       if (a_request.getParameter("aspect") == null)
/*     */       {
/* 186 */         conversionInfo.setMaintainAspectRatio(false);
/*     */       }
/*     */       else
/*     */       {
/* 190 */         conversionInfo.setMaintainAspectRatio(true);
/*     */       }
/*     */ 
/* 193 */       conversionInfo.setDensity(a_form.getDensity());
/* 194 */       conversionInfo.setJpegQuality(a_form.getJpegQuality() / 100.0F);
/* 195 */       conversionInfo.setScaleUp(true);
/* 196 */       conversionInfo.setApplyStrip(a_form.getApplyStrip());
/* 197 */       conversionInfo.setMaxHeight(a_form.getHeight());
/* 198 */       conversionInfo.setMaxWidth(a_form.getWidth());
/*     */     }
/*     */ 
/* 203 */     if ((a_form.getWatermarkImageOption()) || (AssetBankSettings.getWatermarkDownload()))
/*     */     {
/* 205 */       conversionInfo.setAddDownloadWatermark(true);
/*     */     }
/* 207 */     else if (((a_request.getParameter("b_preview") != null) && (AssetBankSettings.getWatermarkPreviewDownload())) || ((a_request.getParameter("b_download") != null) && (AssetBankSettings.getWatermarkDownload())))
/*     */     {
/* 210 */       conversionInfo.setAddWatermark(true);
/*     */     }
/*     */ 
/* 213 */     conversionInfo.setRotationAngle(a_form.getRotationAngle());
/*     */ 
/* 216 */     int iColorSpaceId = 0;
/*     */ 
/* 218 */     if ((ABImageMagick.getIsCMYK(image.getColorSpace())) && ((a_request.getParameter("b_preview") != null) || (a_form.isRepurpose())))
/*     */     {
/* 221 */       iColorSpaceId = 1;
/*     */     }
/* 224 */     else if ((a_form.getSelectedColorSpaceId() == 2) && (a_request.getParameter("b_preview") == null))
/*     */     {
/* 226 */       iColorSpaceId = 2;
/*     */     }
/*     */     else
/*     */     {
/* 231 */       iColorSpaceId = a_form.getSelectedColorSpaceId();
/*     */     }
/*     */ 
/* 235 */     if (iColorSpaceId > 0)
/*     */     {
/* 238 */       ColorSpace destinationColorSpace = this.m_usageManager.getColorSpace(a_dbTransaction, iColorSpaceId);
/* 239 */       ColorSpace currentColorSpace = this.m_usageManager.getCurrentColorSpace(image, a_dbTransaction);
/*     */ 
/* 241 */       conversionInfo.setConvertToColorSpace(destinationColorSpace);
/* 242 */       conversionInfo.setCurrentColorSpace(currentColorSpace);
/*     */     }
/*     */ 
/* 247 */     conversionInfo.setDeferAllowed(a_request.getParameter("b_download") != null);
/*     */ 
/* 249 */     if (a_form.getLayerToDownload() >= 0)
/*     */     {
/* 251 */       conversionInfo.setLayerToConvert(a_form.getLayerToDownload());
/*     */     }
/*     */ 
/* 255 */     conversionInfo.setTint(a_form.getTint());
/*     */ 
/* 257 */     return conversionInfo;
/*     */   }
/*     */ 
/*     */   public String getFormatExt(DBTransaction a_dbTransaction, DownloadForm a_form, String a_sOriginalFormatExt)
/*     */     throws Bn2Exception
/*     */   {
/* 264 */     String sFormatExt = null;
/*     */ 
/* 267 */     if ((AssetBankSettings.getSimpleConvertOptionsForDownload()) && (!a_form.getAdvanced()))
/*     */     {
/* 269 */       UsageTypeFormat utf = this.m_usageManager.getUsageTypeFormat(a_dbTransaction, a_form.getUsageTypeFormatId());
/*     */ 
/* 272 */       if (utf.preserveExtension(a_sOriginalFormatExt))
/*     */       {
/* 275 */         sFormatExt = a_sOriginalFormatExt;
/*     */       }
/*     */       else
/*     */       {
/* 280 */         long lFormatId = utf.getFormatId();
/* 281 */         FileFormat format = this.m_assetManager.getFileFormat(a_dbTransaction, lFormatId);
/* 282 */         sFormatExt = format.getFileExtension();
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 288 */       sFormatExt = a_form.getImageFormat();
/*     */     }
/* 290 */     return sFormatExt;
/*     */   }
/*     */ 
/*     */   public void validateImplementationDownload(DBTransaction a_dbTransaction, HttpServletRequest a_request, DownloadForm a_form, ABUserProfile a_userProfile, String a_sFormatExt, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 304 */     if ((a_request.getParameter("b_downloadOriginal") == null) && (a_asset.getThumbnailImageFile().getPath() == null))
/*     */     {
/* 307 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "thumbnailsNotYetGenerated", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 310 */     if ((a_form.isRepurpose()) && ((a_form.getHeight() > AssetBankSettings.getMaxImageRepurposeDimension()) || (a_form.getWidth() > AssetBankSettings.getMaxImageRepurposeDimension())))
/*     */     {
/* 312 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationMaxRepurposedImageSize", a_userProfile.getCurrentLanguage()).getBody() + AssetBankSettings.getMaxImageRepurposeDimension() + " pixels");
/*     */     }
/* 314 */     else if ((a_form.getHeight() > AssetBankSettings.getMaxImageDownloadDimension()) || (a_form.getWidth() > AssetBankSettings.getMaxImageDownloadDimension()))
/*     */     {
/* 316 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "imageErrorMax", a_userProfile.getCurrentLanguage()).getBody() + AssetBankSettings.getMaxImageDownloadDimension() + " pixels");
/*     */     }
/*     */ 
/* 320 */     if (a_request.getParameter("b_preview") != null)
/*     */     {
/* 322 */       if (!ImageUtil.isWebImageFile("dummy." + a_sFormatExt))
/*     */       {
/* 324 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "fileErrorPreview", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ActionForward getForward(ActionMapping a_mapping, HttpServletRequest a_request, DownloadForm a_form)
/*     */   {
/* 333 */     if (a_form.getEmail())
/*     */     {
/* 335 */       return a_mapping.findForward("DownloadEmailSuccess");
/*     */     }
/* 337 */     if (a_form.isRepurpose())
/*     */     {
/* 339 */       return a_mapping.findForward("DownloadRepurposeSuccess");
/*     */     }
/*     */ 
/* 343 */     return a_mapping.findForward("DownloadSuccess");
/*     */   }
/*     */ 
/*     */   public ActionForward doPreviewWork(DownloadForm a_form, ActionMapping a_mapping, String a_sDownloadPath)
/*     */   {
/* 351 */     a_form.setEncryptedDownloadPath(FileUtil.encryptFilepath(a_sDownloadPath));
/* 352 */     return a_mapping.findForward("PreviewSuccess");
/*     */   }
/*     */ 
/*     */   public MaskManager getMaskManager()
/*     */   {
/* 357 */     return this.m_maskManager;
/*     */   }
/*     */ 
/*     */   public void setMaskManager(MaskManager a_maskManager)
/*     */   {
/* 363 */     this.m_maskManager = a_maskManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DownloadImageAction
 * JD-Core Version:    0.6.0
 */