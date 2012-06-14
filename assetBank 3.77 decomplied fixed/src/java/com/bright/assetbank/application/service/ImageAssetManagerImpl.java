/*      */ package com.bright.assetbank.application.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*      */ import com.bright.assetbank.application.bean.AssetFileSource;
/*      */ import com.bright.assetbank.application.bean.FileFormat;
/*      */ import com.bright.assetbank.application.bean.ImageAsset;
/*      */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*      */ import com.bright.assetbank.application.bean.ImageConversionRequest;
/*      */ import com.bright.assetbank.application.bean.ImageFileInfo;
/*      */ import com.bright.assetbank.application.bean.Tint;
/*      */ import com.bright.assetbank.application.bean.UploadedFileInfo;
/*      */ import com.bright.assetbank.application.bean.UploadedImageInfo;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.constant.GeneratedImageVersion;
/*      */ import com.bright.assetbank.application.exception.AssetConversionDeferredException;
/*      */ import com.bright.assetbank.application.exception.AssetFileReadException;
/*      */ import com.bright.assetbank.application.util.ABImageMagick;
/*      */ import com.bright.assetbank.application.util.ABImageMagickOptionList;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*      */ import com.bright.assetbank.usage.bean.ColorSpace;
/*      */ import com.bright.assetbank.usage.bean.Mask;
/*      */ import com.bright.assetbank.usage.service.MaskManager;
/*      */ import com.bright.assetbank.usage.service.UsageManager;
/*      */ import com.bright.assetbank.usage.util.MaskUtil;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.image.bean.ImageFile;
/*      */ import com.bright.framework.image.constant.ImageConstants;
/*      */ import com.bright.framework.image.exception.ImageException;
/*      */ import com.bright.framework.image.util.ImageMagick;
/*      */ import com.bright.framework.image.util.ImageMagickOptionList;
/*      */ import com.bright.framework.image.util.ImageUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.storage.constant.StoredFileType;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class ImageAssetManagerImpl extends FileAssetManagerImpl
/*      */   implements IAssetManagerImpl, AssetBankConstants, ImageConstants
/*      */ {
/*  124 */   private DownloadImageQueueManager m_downloadQueueManager = null;
/*      */   private MaskManager m_maskManager;
/*  126 */   private Vector m_vecTints = new Vector();
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  144 */     super.startup();
/*      */ 
/*  147 */     ImageMagick.setPath(AssetBankSettings.getImageMagickPath());
/*      */ 
/*  150 */     generateTints();
/*      */   }
/*      */ 
/*      */   public long getAssetTypeId()
/*      */   {
/*  167 */     return 2L;
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lId, Asset a_asset, Vector a_vVisibleAttributeIds, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  183 */     return getAsset(a_dbTransaction, a_lId, a_asset, a_vVisibleAttributeIds, LanguageConstants.k_defaultLanguage, a_bGetFeedback);
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lId, Asset a_asset, Vector a_vVisibleAttributeIds, Language a_language, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  208 */     ImageAsset image = null;
/*      */ 
/*  210 */     if (a_asset == null)
/*      */     {
/*  212 */       image = new ImageAsset();
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*  218 */         image = (ImageAsset)a_asset;
/*      */       }
/*      */       catch (ClassCastException cce)
/*      */       {
/*  222 */         throw new Bn2Exception("ImageAssetManagerImpl.getAsset() : Passed asset object is not of type ImageAsset", cce);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  228 */     DBTransaction transaction = a_dbTransaction;
/*  229 */     if (transaction == null)
/*      */     {
/*  231 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*  233 */     Connection con = null;
/*  234 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  238 */       con = transaction.getConnection();
/*      */ 
/*  240 */       String sSQL = "SELECT ia.Height,ia.Width, ia.ColorSpace, ia.NumLayers, ia.LargeFileLocation, ia.UnwatermarkedLargeFileLocation, ia.FeaturedFileLocation FROM ImageAsset ia WHERE ia.AssetId = ?";
/*      */ 
/*  252 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  254 */       psql.setLong(1, a_lId);
/*      */ 
/*  256 */       rs = psql.executeQuery();
/*      */ 
/*  258 */       if (rs.next())
/*      */       {
/*  260 */         image.setHeight(rs.getInt("Height"));
/*  261 */         image.setWidth(rs.getInt("Width"));
/*  262 */         image.setColorSpace(rs.getInt("ColorSpace"));
/*  263 */         image.setNumPages(rs.getInt("NumLayers"));
/*      */ 
/*  265 */         if ((rs.getString("LargeFileLocation") != null) && (rs.getString("LargeFileLocation").trim().length() > 0))
/*      */         {
/*  268 */           image.setLargeImageFile(new ImageFile(rs.getString("LargeFileLocation")));
/*      */         }
/*      */ 
/*  271 */         if ((rs.getString("FeaturedFileLocation") != null) && (rs.getString("FeaturedFileLocation").trim().length() > 0))
/*      */         {
/*  274 */           image.setFeaturedImageFile(new ImageFile(rs.getString("FeaturedFileLocation")));
/*      */         }
/*      */ 
/*  277 */         if ((rs.getString("UnwatermarkedLargeFileLocation") != null) && (rs.getString("UnwatermarkedLargeFileLocation").trim().length() > 0))
/*      */         {
/*  280 */           image.setUnwatermarkedLargeImageFile(new ImageFile(rs.getString("UnwatermarkedLargeFileLocation")));
/*      */         }
/*      */       }
/*      */ 
/*  284 */       psql.close();
/*      */ 
/*  293 */       image = (ImageAsset)super.getAsset(transaction, a_lId, image, a_vVisibleAttributeIds, a_language, a_bGetFeedback);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  297 */       throw new Bn2Exception("SQL Exception whilst getting an image asset from the database : ", e);
/*      */     }
/*      */     finally
/*      */     {
/*  302 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  306 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  310 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  315 */     return image;
/*      */   }
/*      */ 
/*      */   public String getDownloadableAssetPath(Asset a_asset, String a_sConvertToFileExtension, AssetConversionInfo a_conversionInfo)
/*      */     throws Bn2Exception
/*      */   {
/*  342 */     ImageAsset image = null;
/*  343 */     String sPath = null;
/*      */ 
/*  346 */     if ((a_asset == null) || (!(a_asset instanceof ImageAsset)))
/*      */     {
/*  348 */       throw new Bn2Exception("ImageAssetManagerImpl.getDownloadableAssetPath() : Passed asset object is not a valid ImageAsset");
/*      */     }
/*      */ 
/*  351 */     image = (ImageAsset)a_asset;
/*  352 */     ImageConversionInfo imageConverstionInfo = (ImageConversionInfo)a_conversionInfo;
/*      */ 
/*  355 */     boolean bDownloadOriginal = false;
/*      */ 
/*  358 */     if ((a_sConvertToFileExtension == null) || ((imageConverstionInfo.getUseOriginal()) && (imageConverstionInfo.getRotationAngle() == 0) && (!imageConverstionInfo.getAddWatermark()) && (!StringUtil.stringIsPopulated(imageConverstionInfo.getTint()))))
/*      */     {
/*  364 */       bDownloadOriginal = true;
/*      */     }
/*      */ 
/*  367 */     if (bDownloadOriginal)
/*      */     {
/*  370 */       if ((image.getOriginalFileLocation() != null) && (image.getOriginalFileLocation().length() > 0))
/*      */       {
/*  372 */         sPath = image.getOriginalFileLocation();
/*      */       }
/*      */       else
/*      */       {
/*  376 */         sPath = image.getFileLocation();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  381 */       imageConverstionInfo.setUseOriginal(false);
/*      */ 
/*  383 */       if ((imageConverstionInfo.getConvertToColorSpace() != null) && (imageConverstionInfo.getCurrentColorSpace() == null))
/*      */       {
/*  386 */         ColorSpace currentColorSpace = this.m_usageManager.getCurrentColorSpace(image, null);
/*  387 */         imageConverstionInfo.setCurrentColorSpace(currentColorSpace);
/*      */       }
/*      */ 
/*  392 */       sPath = prepareImageForDownload(image, a_sConvertToFileExtension, imageConverstionInfo);
/*      */     }
/*      */ 
/*  397 */     return sPath;
/*      */   }
/*      */ 
/*      */   private String prepareImageForDownload(ImageAsset a_image, String a_sRequestedImageFormatExt, ImageConversionInfo a_conversionInfo)
/*      */     throws Bn2Exception
/*      */   {
/*  433 */     String sUrlOfOriginalImage = null;
/*      */ 
/*  436 */     int iHeight = 0;
/*  437 */     int iWidth = 0;
/*      */ 
/*  440 */     ImageConversionInfo conversionInfo = new ImageConversionInfo(a_conversionInfo);
/*      */ 
/*  443 */     if (((conversionInfo.getMaxHeight() <= 0) && (conversionInfo.getMaxWidth() <= 0)) || ((!conversionInfo.getScaleUp()) && (conversionInfo.getMaxHeight() > a_image.getHeight()) && (conversionInfo.getMaxWidth() > a_image.getWidth())))
/*      */     {
/*  449 */       iHeight = a_image.getHeight();
/*  450 */       iWidth = a_image.getWidth();
/*      */     }
/*      */     else
/*      */     {
/*  455 */       iHeight = conversionInfo.getMaxHeight();
/*  456 */       iWidth = conversionInfo.getMaxWidth();
/*      */     }
/*      */ 
/*  460 */     boolean bAlreadyWatermarked = false;
/*  461 */     if ((a_image.getLargeImageFile() != null) && (a_image.getLargeImageFile().getPath().length() > 0) && (iHeight <= AssetBankSettings.getLargeImageSize()) && (iWidth <= AssetBankSettings.getLargeImageSize()) && (AssetBankSettings.getWatermarkFullSize() == conversionInfo.getAddWatermark()) && (AssetBankSettings.getJpgConversionQuality() >= conversionInfo.getJpegQuality()) && (conversionInfo.getCropWidth() == 0) && (conversionInfo.getCropHeight() == 0) && (conversionInfo.getTint() == "") && (conversionInfo.getLayerToConvert() < 2) && ((!ABImageMagick.getIsCMYK(a_image.getColorSpace())) || (conversionInfo.getConvertToColorSpace().getId() == 1L)))
/*      */     {
/*  473 */       sUrlOfOriginalImage = a_image.getLargeImageFile().getPath();
/*      */ 
/*  475 */       bAlreadyWatermarked = AssetBankSettings.getWatermarkFullSize();
/*      */ 
/*  479 */       if ((conversionInfo.getConvertToColorSpace() != null) && (conversionInfo.getConvertToColorSpace().getId() == 1L))
/*      */       {
/*  482 */         conversionInfo.setConvertToColorSpace(null);
/*  483 */         conversionInfo.setCurrentColorSpace(null);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  490 */       sUrlOfOriginalImage = a_image.getFileLocation();
/*      */     }
/*      */ 
/*  493 */     String sOriginalFilenameFullPath = this.m_fileStoreManager.getAbsolutePath(sUrlOfOriginalImage);
/*  494 */     String sRelativePath = this.m_fileStoreManager.getRealRelativePath(sUrlOfOriginalImage);
/*      */ 
/*  497 */     String sFilenameWithoutSuffix = FileUtil.getFilenameWithoutSuffix(FileUtil.getFilename(sRelativePath.replaceAll("/", "_")));
/*      */ 
/*  501 */     String sRelativeTempDownloadFilePath = sFilenameWithoutSuffix + "." + a_sRequestedImageFormatExt;
/*      */ 
/*  504 */     sRelativeTempDownloadFilePath = this.m_fileStoreManager.getUniqueFilepath(sRelativeTempDownloadFilePath, StoredFileType.TEMP);
/*      */ 
/*  508 */     if ((AssetBankSettings.getCreditStripEnabled()) && (!conversionInfo.getAddingAsNewAsset()))
/*      */     {
/*  511 */       AttributeValue avCreditAttribute = a_image.getAttributeValue(AssetBankSettings.getCreditAttributeId());
/*      */ 
/*  514 */       if (avCreditAttribute == null)
/*      */       {
/*  516 */         this.m_logger.error("The credit attribute does not exist, no text has been appended to standard copyright strip");
/*      */ 
/*  518 */         conversionInfo.setCreditText("");
/*      */       }
/*  523 */       else if (StringUtil.stringIsPopulated(avCreditAttribute.getValue()))
/*      */       {
/*  526 */         String sCreditText = "";
/*      */ 
/*  529 */         if ((avCreditAttribute.getAttribute().getIsDropdownList()) && (StringUtil.stringIsPopulated(avCreditAttribute.getAdditionalValue())))
/*      */         {
/*  532 */           sCreditText = avCreditAttribute.getAdditionalValue();
/*      */         }
/*      */         else
/*      */         {
/*  536 */           sCreditText = avCreditAttribute.getValue();
/*      */         }
/*      */ 
/*  539 */         conversionInfo.setCreditText(sCreditText);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  545 */     ImageConversionRequest conversion = new ImageConversionRequest(a_image, conversionInfo, sRelativeTempDownloadFilePath, iHeight, iWidth, bAlreadyWatermarked, sOriginalFilenameFullPath);
/*      */ 
/*  553 */     if (conversionInfo.isDeferAllowed())
/*      */     {
/*  555 */       int iMaxSize = AssetBankSettings.getDeferredConversionImageAreaThreshold();
/*      */ 
/*  557 */       if (conversion.getWidth() * conversion.getHeight() > iMaxSize)
/*      */       {
/*  559 */         this.m_downloadQueueManager.queueItem(conversion);
/*  560 */         throw new AssetConversionDeferredException(sRelativeTempDownloadFilePath);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  566 */     doConversion(conversion);
/*      */ 
/*  568 */     return sRelativeTempDownloadFilePath;
/*      */   }
/*      */ 
/*      */   public void doConversion(ImageConversionRequest a_conversion)
/*      */     throws ImageException, Bn2Exception
/*      */   {
/*  595 */     String sWorkingDownloadPath = this.m_fileStoreManager.getAbsolutePath(a_conversion.getRelativeTempDownloadFilePath());
/*  596 */     File fTempWorkingFile = null;
/*      */     try
/*      */     {
/*  602 */       if (FileUtil.getSuffix(sWorkingDownloadPath).equalsIgnoreCase("pdf"))
/*      */       {
/*  604 */         fTempWorkingFile = File.createTempFile(FileUtil.getFilenameWithoutSuffix(sWorkingDownloadPath, true), ".tif");
/*  605 */         sWorkingDownloadPath = fTempWorkingFile.getAbsolutePath();
/*      */       }
/*      */ 
/*  609 */       ImageConversionInfo info = a_conversion.getConversionInfo();
/*      */ 
/*  611 */       String sOriginalFilenameFullPath = a_conversion.getOriginalFilenameFullPath();
/*      */ 
/*  613 */       int iLayer = getInputLayerToUse(a_conversion);
/*      */ 
/*  617 */       Mask mask = null;
/*      */ 
/*  620 */       if ((info.getCropWidth() > 0) && (info.getCropHeight() > 0))
/*      */       {
/*  622 */         ABImageMagickOptionList crOpt = new ABImageMagickOptionList();
/*      */ 
/*  624 */         crOpt.addInputFilename(sOriginalFilenameFullPath);
/*      */ 
/*  626 */         crOpt.addFormatSpecificOptions(sOriginalFilenameFullPath, sWorkingDownloadPath);
/*      */ 
/*  630 */         crOpt.specifyInputLayer(iLayer);
/*      */ 
/*  632 */         crOpt.addCrop(info.getCropWidth(), info.getCropHeight(), info.getCropX(), info.getCropY());
/*      */ 
/*  638 */         if ((info.getCropMaskId() > 0L) && (MaskUtil.masksAllowedBySettings()))
/*      */         {
/*  642 */           mask = this.m_maskManager.getMaskById(null, info.getCropMaskId());
/*      */ 
/*  647 */           ImageMagickOptionList maskStack = new ImageMagickOptionList();
/*  648 */           maskStack.addAuxiliaryInputFilename(mask.getImageFullPath());
/*  649 */           maskStack.addResize(info.getCropWidth(), info.getCropHeight());
/*  650 */           if (info.getCropMaskColour() != null)
/*      */           {
/*  652 */             maskStack.addSetColour(info.getCropMaskColour());
/*      */           }
/*      */ 
/*  655 */           crOpt.addImageStack(maskStack);
/*  656 */           crOpt.addComposite();
/*      */         }
/*      */ 
/*  659 */         crOpt.addOutputFilename(sWorkingDownloadPath);
/*      */ 
/*  661 */         sOriginalFilenameFullPath = sWorkingDownloadPath;
/*      */ 
/*  663 */         ImageMagick.convert(crOpt);
/*      */       }
/*      */ 
/*  667 */       ABImageMagickOptionList options = new ABImageMagickOptionList();
/*      */ 
/*  670 */       options.addInputFilename(sOriginalFilenameFullPath);
/*      */ 
/*  673 */       if (info.getConvertToColorSpace() != null)
/*      */       {
/*  675 */         options.convertColorSpace(info.getCurrentColorSpace().getFileLocation(), info.getConvertToColorSpace().getFileLocation(), hasEmbeddedColorProfile(sOriginalFilenameFullPath));
/*      */       }
/*      */ 
/*  679 */       options.addQuality(info.getJpegQuality());
/*      */ 
/*  681 */       if (info.getDensity() > 0)
/*      */       {
/*  683 */         options.addDensity(info.getDensity());
/*      */       }
/*      */ 
/*  687 */       if (info.getApplyStrip())
/*      */       {
/*  689 */         options.addStrip();
/*      */       }
/*      */ 
/*  693 */       options.addFormatSpecificOptions(sOriginalFilenameFullPath, sWorkingDownloadPath);
/*      */ 
/*  697 */       options.specifyInputLayer(iLayer);
/*      */ 
/*  699 */       int[] aiNewSize = null;
/*  700 */       if (info.getMaintainAspectRatio())
/*      */       {
/*      */         int heightAspect;
/*      */         int widthAspect;
/*      */         //int heightAspect;
/*  704 */         if (mask == null)
/*      */         {
/*  706 */           widthAspect = a_conversion.getImage().getWidth();
/*  707 */           heightAspect = a_conversion.getImage().getHeight();
/*      */         }
/*      */         else
/*      */         {
/*  711 */           widthAspect = mask.getWidth();
/*  712 */           heightAspect = mask.getHeight();
/*      */         }
/*      */ 
/*  715 */         aiNewSize = options.addResize(widthAspect, heightAspect, a_conversion.getWidth(), a_conversion.getHeight(), null);
/*      */       }
/*      */       else
/*      */       {
/*  724 */         aiNewSize = options.addResize(a_conversion.getWidth(), a_conversion.getHeight());
/*      */       }
/*      */ 
/*  728 */       if (info.getRotationAngle() > 0)
/*      */       {
/*  730 */         options.addRotateClockwise(info.getRotationAngle());
/*      */       }
/*      */ 
/*  734 */       options.addOutputFilename(sWorkingDownloadPath);
/*      */ 
/*  737 */       ImageMagick.convert(options);
/*      */ 
/*  739 */       String sInputFileIdentifier = a_conversion.getOriginalFilenameFullPath() + (iLayer >= 0 ? "[" + iLayer + "]" : "");
/*      */ 
/*  742 */       if (StringUtil.stringIsPopulated(info.getTint()))
/*      */       {
/*  744 */         Tint tint = null;
/*      */ 
/*  747 */         Iterator itTints = this.m_vecTints.iterator();
/*      */ 
/*  749 */         while (itTints.hasNext())
/*      */         {
/*  751 */           Tint currentTint = (Tint)itTints.next();
/*      */ 
/*  753 */           if (currentTint.getName().equals(info.getTint()))
/*      */           {
/*  755 */             tint = currentTint;
/*  756 */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  761 */         ABImageMagick.addTint(sWorkingDownloadPath, sWorkingDownloadPath, tint.getColour(), tint.getPercentage(), sInputFileIdentifier);
/*      */       }
/*      */ 
/*  773 */       if (((info.getAddWatermark()) && (!a_conversion.isAlreadyWatermarked())) || ((info.getAddDownloadWatermark()) && (!a_conversion.getConversionInfo().getAddingAsNewAsset())))
/*      */       {
/*  775 */         ABImageMagick.addWatermark(sWorkingDownloadPath, sWorkingDownloadPath, aiNewSize[0], aiNewSize[1], sInputFileIdentifier, true);
/*      */ 
/*  785 */         sInputFileIdentifier = sInputFileIdentifier + "_wm";
/*      */       }
/*      */ 
/*  790 */       if (AssetBankSettings.getCreditStripEnabled())
/*      */       {
/*  792 */         if (StringUtil.stringIsPopulated(info.getCreditText()))
/*      */         {
/*  794 */           ABImageMagick.addCreditStrip(sWorkingDownloadPath, sWorkingDownloadPath, info.getCreditText(), sInputFileIdentifier);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  807 */       if (fTempWorkingFile != null)
/*      */       {
/*  809 */         options = new ABImageMagickOptionList();
/*  810 */         options.addInputFilename(sWorkingDownloadPath);
/*  811 */         options.addOutputFilename(sWorkingDownloadPath);
/*      */ 
/*  814 */         if ((a_conversion.getImage().getNumPages() > 1) && (!a_conversion.getImage().getFormat().getCanConvertIndividualLayers()) && (info.getLayerToConvert() > 0))
/*      */         {
/*  816 */           options.specifyInputLayer(info.getLayerToConvert() - 1);
/*      */         }
/*      */ 
/*  819 */         ImageMagick.convert(options);
/*      */ 
/*  822 */         fTempWorkingFile.delete();
/*  823 */         FileUtil.logFileDeletion(fTempWorkingFile);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*  831 */       throw new ImageException("IOException in ImageAssetManagerImpl.doConversion: " + ioe.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   private int getInputLayerToUse(ImageConversionRequest a_conversion)
/*      */   {
/*  843 */     int iLayer = -1;
/*      */ 
/*  847 */     if ((a_conversion.getImage().getNumPages() > 1) && ((!ImageUtil.supportsMultiLayers(a_conversion.getRelativeTempDownloadFilePath(), false)) || (a_conversion.getConversionInfo().getLayerToConvert() >= 1) || (!a_conversion.getImage().getFormat().getCanConvertIndividualLayers())))
/*      */     {
/*  852 */       iLayer = a_conversion.getConversionInfo().getLayerToConvert() - 1;
/*      */     }
/*      */ 
/*  855 */     return iLayer;
/*      */   }
/*      */ 
/*      */   public boolean canStoreFileFormat(FileFormat a_format)
/*      */   {
/*  876 */     return (a_format != null) && (a_format.getAssetTypeId() == getAssetTypeId()) && (a_format.getIsConvertable());
/*      */   }
/*      */ 
/*      */   public boolean canConvertToFileFormat(Asset a_asset, FileFormat a_format)
/*      */     throws Bn2Exception
/*      */   {
/*  899 */     return (a_format != null) && (a_format.getAssetTypeId() == getAssetTypeId()) && (a_format.getIsConversionTarget()) && (a_asset.getFormat().getIsConvertable());
/*      */   }
/*      */ 
/*      */   public Asset saveAsset(DBTransaction a_dbTransaction, Asset a_asset, AssetFileSource a_source, long a_lUserId, AssetConversionInfo a_conversionInfo, AssetFileSource a_thumbnailSource, boolean a_bForceThumbnailRegeneration, boolean a_bForcePreviewRegeneration, int a_iSaveTypeId)
/*      */     throws Bn2Exception, AssetFileReadException
/*      */   {
/*  931 */     boolean bChangedImageFileInfo = false;
/*  932 */     boolean bImageFileUpdated = false;
/*  933 */     ImageAsset image = null;
/*      */ 
/*  936 */     if ((a_asset instanceof ImageAsset))
/*      */     {
/*  938 */       image = (ImageAsset)a_asset;
/*      */     }
/*      */     else
/*      */     {
/*  942 */       image = new ImageAsset(a_asset);
/*      */     }
/*      */ 
/*  945 */     if ((a_source != null) && (a_source.isValid()))
/*      */     {
/*  947 */       if (!canStoreFileFormat(a_asset.getFormat()))
/*      */       {
/*  949 */         throw new Bn2Exception("Cannot add image file '" + a_source.getFilename() + "' because file type cannot be processed as an image.");
/*      */       }
/*      */ 
/*  952 */       bImageFileUpdated = a_asset.getId() > 0L;
/*  953 */       bChangedImageFileInfo = true;
/*      */     }
/*      */ 
/*  957 */     if ((image.getIsImage()) && (a_thumbnailSource != null))
/*      */     {
/*  959 */       image.setHasSubstituteFile(((image.getHasSubstituteFile()) && (!a_thumbnailSource.getRemove())) || (a_thumbnailSource.isValid()));
/*      */     }
/*      */ 
/*  963 */     if (((a_thumbnailSource != null) && ((a_thumbnailSource.getRegenerate()) || (a_thumbnailSource.isValid()) || (a_thumbnailSource.getRemove()))) || ((a_asset.getId() <= 0L) && (StringUtils.isNotEmpty(a_asset.getFileLocation()))))
/*      */     {
/*  966 */       bImageFileUpdated = a_asset.getId() > 0L;
/*  967 */       bChangedImageFileInfo = true;
/*      */     }
/*      */ 
/*  970 */     boolean bIsNew = false;
/*      */ 
/*  972 */     if (a_asset.getId() <= 0L)
/*      */     {
/*  974 */       bIsNew = true;
/*      */     }
/*      */ 
/*  978 */     image = (ImageAsset)super.saveAsset(a_dbTransaction, image, a_source, a_lUserId, a_conversionInfo, a_thumbnailSource, a_bForceThumbnailRegeneration, a_bForcePreviewRegeneration, a_iSaveTypeId);
/*      */     try
/*      */     {
/*  991 */       ImageConversionInfo imageConversionInfo = null;
/*      */ 
/*  993 */       if (a_conversionInfo != null)
/*      */       {
/*  995 */         imageConversionInfo = (ImageConversionInfo)a_conversionInfo;
/*      */       }
/*      */ 
/*  999 */       if (((imageConversionInfo == null) || (!imageConversionInfo.getDeferThumbnailGeneration())) && ((bChangedImageFileInfo) || ((imageConversionInfo != null) && (imageConversionInfo.getRotationAngle() != 0))))
/*      */       {
/* 1003 */         String sFilenameFullPath = this.m_fileStoreManager.getAbsolutePath(image.getFileLocation());
/*      */ 
/* 1005 */         ImageFileInfo imageInfo = ABImageMagick.getInfo(sFilenameFullPath);
/*      */ 
/* 1007 */         image.setWidth(imageInfo.getWidth());
/* 1008 */         image.setHeight(imageInfo.getHeight());
/* 1009 */         image.setColorSpace(imageInfo.getColorSpace());
/* 1010 */         image.setNumPages(imageInfo.getNumberOfLayers());
/*      */ 
/* 1013 */         bChangedImageFileInfo = true;
/*      */       }
/*      */ 
/* 1017 */       saveImageToDatabase(a_dbTransaction, image, bChangedImageFileInfo);
/*      */ 
/* 1020 */       if (AssetBankSettings.getAssetRepurposingEnabled())
/*      */       {
/* 1022 */         if (image.getIsRestricted())
/*      */         {
/* 1024 */           this.m_assetRepurposingManager.removeRepurposedImages(a_dbTransaction, image.getId());
/*      */         }
/* 1026 */         else if (bImageFileUpdated)
/*      */         {
/* 1028 */           this.m_assetRepurposingManager.warnOfImageUpdate(a_dbTransaction, image.getId());
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/* 1035 */       if (bIsNew)
/*      */       {
/* 1037 */         deleteAsset(a_dbTransaction, image.getId());
/* 1038 */         throw new Bn2Exception("ImageAssetManagerImpl.saveAsset: Deleting Asset with Id: " + image.getId(), t);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1043 */     if ((AssetBankSettings.getReembedMetadataOnSave()) && ((a_conversionInfo == null) || (!a_conversionInfo.getDoNotReEmbedMetadata())))
/*      */     {
/* 1045 */       String sLocation = null;
/*      */       try
/*      */       {
/* 1050 */         if (StringUtil.stringIsPopulated(image.getOriginalFileLocation()))
/*      */         {
/* 1052 */           sLocation = image.getOriginalFileLocation();
/*      */         }
/*      */         else
/*      */         {
/* 1056 */           sLocation = image.getFileLocation();
/*      */         }
/*      */ 
/* 1060 */         String sSourceFullPath = this.m_fileStoreManager.getAbsolutePath(sLocation);
/*      */ 
/* 1063 */         this.m_attributeManager.embedMetadataValues(sSourceFullPath, image, 1L, null);
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/* 1068 */         this.m_logger.error("ImageAssetManagerImpl.saveAsset: Error ignored when embedding metadata in source file: " + sLocation + ": " + e);
/*      */       }
/*      */     }
/*      */ 
/* 1072 */     return image;
/*      */   }
/*      */ 
/*      */   protected boolean getNeedToGenerateThumbnails(AssetFileSource a_source, AssetFileSource a_thumbnailSource, AssetConversionInfo a_conversionInfo)
/*      */   {
/* 1095 */     ImageConversionInfo imageConversionInfo = null;
/*      */ 
/* 1097 */     if (a_conversionInfo != null)
/*      */     {
/* 1099 */       imageConversionInfo = (ImageConversionInfo)a_conversionInfo;
/*      */     }
/*      */ 
/* 1108 */     return ((a_thumbnailSource != null) && ((a_thumbnailSource.isValid()) || (a_thumbnailSource.getRemove()))) || ((a_source != null) && (a_source.isValid()) && ((a_thumbnailSource == null) || (!a_thumbnailSource.isValid()))) || ((imageConversionInfo != null) && (imageConversionInfo.getRotationAngle() != 0));
/*      */   }
/*      */ 
/*      */   public void createImageVersions(Asset a_asset, String a_sThumbnailFileId, AssetConversionInfo a_conversionInfo, int a_iSaveTypeId, Set<GeneratedImageVersion> a_versionsToCreate)
/*      */     throws Bn2Exception
/*      */   {
/* 1138 */     String sSubstituteFileId = a_sThumbnailFileId;
/*      */ 
/* 1141 */     if (sSubstituteFileId != null)
/*      */     {
/* 1143 */       if (StringUtils.isEmpty(a_asset.getOriginalFileLocation()))
/*      */       {
/* 1145 */         a_asset.setOriginalFileLocation(a_asset.getFileLocation());
/*      */       }
/* 1147 */       else if (!a_asset.getOriginalFileLocation().equals(a_asset.getFileLocation()))
/*      */       {
/* 1149 */         this.m_fileStoreManager.deleteFile(a_asset.getFileLocation());
/*      */       }
/*      */ 
/* 1152 */       File tmpFile = new File(this.m_fileStoreManager.getAbsolutePath(sSubstituteFileId));
/* 1153 */       String sFileLocation = this.m_fileStoreManager.addFile(tmpFile, StoredFileType.PREVIEW_OR_THUMBNAIL);
/* 1154 */       a_asset.setFileLocation(sFileLocation);
/*      */ 
/* 1156 */       this.m_fileStoreManager.storeFile(sFileLocation);
/*      */     }
/*      */ 
/* 1159 */     ImageConversionInfo imageConversionInfo = null;
/* 1160 */     ImageAsset imageAsset = (ImageAsset)a_asset;
/*      */ 
/* 1162 */     if (a_conversionInfo != null)
/*      */     {
/* 1164 */       imageConversionInfo = (ImageConversionInfo)a_conversionInfo;
/*      */     }
/*      */ 
/* 1168 */     if ((a_conversionInfo != null) && (imageConversionInfo.getRotationAngle() != 0))
/*      */     {
/* 1172 */       String sSourceFullPath = this.m_fileStoreManager.getAbsolutePath(a_asset.getFileLocation());
/* 1173 */       String sOldFileToDelete = null;
/*      */ 
/* 1176 */       if ((a_asset.getOriginalFileLocation() != null) && (a_asset.getOriginalFileLocation().length() > 0))
/*      */       {
/* 1179 */         sOldFileToDelete = a_asset.getFileLocation();
/*      */       }
/*      */       else
/*      */       {
/* 1184 */         a_asset.setOriginalFileLocation(a_asset.getFileLocation());
/*      */       }
/*      */ 
/* 1188 */       String sFilenameWithoutSuffix = FileUtil.getFilepathWithoutSuffix(a_asset.getFileLocation());
/* 1189 */       String sProposedFilename = FileUtil.getFilename(sFilenameWithoutSuffix) + "." + AssetBankSettings.getRotatedSourceFormat();
/*      */ 
/* 1191 */       String sNewLocation = this.m_fileStoreManager.getUniqueFilepath(sProposedFilename, StoredFileType.ASSET);
/*      */ 
/* 1193 */       a_asset.setFileLocation(sNewLocation);
/*      */ 
/* 1196 */       String sDestFullPath = this.m_fileStoreManager.getAbsolutePath(sNewLocation);
/*      */ 
/* 1199 */       ABImageMagickOptionList options = new ABImageMagickOptionList();
/* 1200 */       options.addInputFilename(sSourceFullPath);
/* 1201 */       options.addFormatSpecificOptions(sSourceFullPath, sDestFullPath);
/* 1202 */       options.addRotateClockwise(imageConversionInfo.getRotationAngle());
/* 1203 */       options.addQuality(1.0F);
/* 1204 */       options.addOutputFilename(sDestFullPath);
/*      */ 
/* 1206 */       if (imageAsset.getNumPages() > 1)
/*      */       {
/* 1208 */         options.specifyInputLayer(0);
/*      */       }
/*      */ 
/* 1211 */       ABImageMagick.convert(options);
/*      */ 
/* 1213 */       this.m_fileStoreManager.storeFile(sNewLocation);
/*      */ 
/* 1216 */       if (sOldFileToDelete != null)
/*      */       {
/* 1218 */         this.m_fileStoreManager.deleteFile(sOldFileToDelete);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1223 */     if (((AssetBankSettings.getCacheLargeImage()) || (AssetBankSettings.getCacheUnwatermarkedLargeImage())) && ((imageAsset.getLargeImageFile() == null) || (a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.LARGE_WATERMARKED)) || (a_versionsToCreate.contains(GeneratedImageVersion.LARGE_UNWATERMARKED))))
/*      */     {
/* 1230 */       createLargeImageFiles(imageAsset, a_versionsToCreate);
/*      */     }
/*      */ 
/* 1234 */     if (sSubstituteFileId == null)
/*      */     {
/* 1237 */       if ((imageAsset.getLargeImageFile() != null) && ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.LARGE_WATERMARKED))))
/*      */       {
/* 1240 */         sSubstituteFileId = imageAsset.getLargeImageFile().getPath();
/*      */       }
/* 1242 */       else if (imageAsset.getUnwatermarkedLargeImageFile() != null)
/*      */       {
/* 1244 */         sSubstituteFileId = imageAsset.getUnwatermarkedLargeImageFile().getPath();
/*      */       }
/*      */       else
/*      */       {
/* 1249 */         sSubstituteFileId = a_asset.getFileLocation();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1254 */     super.createImageVersions(a_asset, sSubstituteFileId, a_conversionInfo, a_iSaveTypeId, a_versionsToCreate);
/*      */ 
/* 1257 */     if ((imageAsset.getLargeImageFile() != null) && (AssetBankSettings.getWatermarkFullSize()) && ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.LARGE_WATERMARKED))))
/*      */     {
/* 1261 */       ImageFile largeImage = imageAsset.getLargeImageFile();
/* 1262 */       String sLargeSource = this.m_fileStoreManager.getAbsolutePath(largeImage.getPath());
/*      */ 
/* 1264 */       ABImageMagick.addWatermark(sLargeSource, sLargeSource, largeImage.getPixelWidth(), largeImage.getPixelHeight(), null, false);
/*      */ 
/* 1272 */       this.m_logger.trace("Added watermark to large image for asset id=" + a_asset.getId());
/*      */ 
/* 1274 */       this.m_fileStoreManager.storeFile(largeImage.getPath());
/*      */     }
/*      */ 
/* 1278 */     if ((a_asset.getIsFeatured()) && ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.FEATURED))))
/*      */     {
/* 1281 */       createFeaturedImageFile((ImageAsset)a_asset);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void createLargeImageFiles(ImageAsset a_asset, Set<GeneratedImageVersion> a_versionsToCreate)
/*      */     throws Bn2Exception
/*      */   {
/* 1302 */     String sSource = null;
/* 1303 */     ImageFileInfo imageInfo = null;
/* 1304 */     int[] iaLargeSize = null;
/* 1305 */     int[] iaUnwatermarkedLargeSize = null;
/*      */ 
/* 1308 */     String sLegacyThumbnail = getLegacyThumbnailFile(a_asset);
/*      */ 
/* 1311 */     if (sLegacyThumbnail != null)
/*      */     {
/* 1313 */       sSource = sLegacyThumbnail;
/* 1314 */       this.m_logger.debug("Using legacy thumbnail image " + sLegacyThumbnail + " for uploaded file: " + a_asset.getOriginalFilename());
/*      */     }
/*      */     else
/*      */     {
/* 1318 */       sSource = this.m_fileStoreManager.getAbsolutePath(a_asset.getFileLocation());
/*      */     }
/*      */ 
/* 1322 */     imageInfo = ABImageMagick.getInfo(sSource);
/* 1323 */     int iSourceWidth = imageInfo.getWidth();
/* 1324 */     int iSourceHeight = imageInfo.getHeight();
/* 1325 */     boolean bIsSourceCMYK = ABImageMagick.getIsCMYK(imageInfo.getColorSpace());
/*      */ 
/* 1327 */     String sLargeFileId = null;
/* 1328 */     String sUnwatermarkedLargeFileId = null;
/*      */ 
/* 1330 */     boolean bGenerateLargeVersion = (AssetBankSettings.getCacheLargeImage()) && ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.LARGE_WATERMARKED)));
/*      */ 
/* 1333 */     boolean bGenerateUnwatermarkedVersion = (AssetBankSettings.getCacheUnwatermarkedLargeImage()) && ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.LARGE_UNWATERMARKED)));
/*      */ 
/* 1337 */     String sRgbColorProfile = this.m_usageManager.getColorSpace(null, 1).getFileLocation();
/* 1338 */     String sCmykColorProfile = this.m_usageManager.getColorSpace(null, 2).getFileLocation();
/*      */ 
/* 1341 */     if ((sLegacyThumbnail == null) || (!AssetBankSettings.getLegacyThumbnailUseForLarge()))
/*      */     {
/* 1344 */       int iSize = getLargeImageSize(AssetBankSettings.getLargeImageSize(), imageInfo.getWidth(), imageInfo.getHeight());
/* 1345 */       int iUWSize = getLargeImageSize(AssetBankSettings.getUnwatermarkedLargeImageSize(), imageInfo.getWidth(), imageInfo.getHeight());
/*      */ 
/* 1348 */       if ((bGenerateUnwatermarkedVersion) && (iUWSize >= iSize))
/*      */       {
/* 1350 */         sUnwatermarkedLargeFileId = getLargeImageFilename(a_asset.getUnwatermarkedLargeImageFile(), a_asset.getFileLocation(), "-u.jpg");
/*      */ 
/* 1352 */         iaUnwatermarkedLargeSize = resizeImage(sSource, iSourceWidth, iSourceHeight, bIsSourceCMYK, imageInfo.getNumberOfLayers(), sUnwatermarkedLargeFileId, iUWSize, sRgbColorProfile, sCmykColorProfile);
/*      */ 
/* 1354 */         this.m_logger.trace("Generated large unwatermarked image for asset id=" + a_asset.getId());
/*      */ 
/* 1357 */         sSource = this.m_fileStoreManager.getAbsolutePath(sUnwatermarkedLargeFileId);
/* 1358 */         iSourceWidth = iaUnwatermarkedLargeSize[0];
/* 1359 */         iSourceHeight = iaUnwatermarkedLargeSize[1];
/* 1360 */         bIsSourceCMYK = false;
/*      */       }
/*      */ 
/* 1364 */       if (bGenerateLargeVersion)
/*      */       {
/* 1366 */         sLargeFileId = getLargeImageFilename(a_asset.getLargeImageFile(), a_asset.getFileLocation(), "-l.jpg");
/*      */ 
/* 1368 */         iaLargeSize = resizeImage(sSource, iSourceWidth, iSourceHeight, bIsSourceCMYK, imageInfo.getNumberOfLayers(), sLargeFileId, iSize, sRgbColorProfile, sCmykColorProfile);
/*      */ 
/* 1370 */         this.m_logger.trace("Generated large watermarked image for asset id=" + a_asset.getId());
/*      */ 
/* 1373 */         sSource = this.m_fileStoreManager.getAbsolutePath(sLargeFileId);
/* 1374 */         iSourceWidth = iaLargeSize[0];
/* 1375 */         iSourceHeight = iaLargeSize[1];
/* 1376 */         bIsSourceCMYK = false;
/*      */       }
/*      */ 
/* 1380 */       if ((bGenerateUnwatermarkedVersion) && (iUWSize < iSize))
/*      */       {
/* 1382 */         sUnwatermarkedLargeFileId = getLargeImageFilename(a_asset.getUnwatermarkedLargeImageFile(), a_asset.getFileLocation(), "-u.jpg");
/*      */ 
/* 1385 */         iaUnwatermarkedLargeSize = resizeImage(sSource, iSourceWidth, iSourceHeight, bIsSourceCMYK, imageInfo.getNumberOfLayers(), sUnwatermarkedLargeFileId, iUWSize, sRgbColorProfile, sCmykColorProfile);
/*      */ 
/* 1387 */         this.m_logger.trace("Generated large unwatermarked image for asset id=" + a_asset.getId());
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1392 */       sLargeFileId = getLargeImageFilename(a_asset.getLargeImageFile(), a_asset.getFileLocation(), "-l.jpg");
/*      */ 
/* 1395 */       this.m_fileStoreManager.moveFileToExistingLocation(new File(sLegacyThumbnail), sLargeFileId);
/*      */ 
/* 1398 */       iaLargeSize = new int[2];
/* 1399 */       iaLargeSize[0] = imageInfo.getWidth();
/* 1400 */       iaLargeSize[1] = imageInfo.getHeight();
/*      */     }
/*      */ 
/* 1405 */     if (bGenerateLargeVersion)
/*      */     {
/* 1407 */       ImageFile imageFile = new ImageFile(sLargeFileId);
/* 1408 */       imageFile.setPixelWidth(iaLargeSize[0]);
/* 1409 */       imageFile.setPixelHeight(iaLargeSize[1]);
/* 1410 */       a_asset.setLargeImageFile(imageFile);
/*      */     }
/*      */ 
/* 1413 */     if (bGenerateUnwatermarkedVersion)
/*      */     {
/* 1415 */       ImageFile uwImageFile = new ImageFile(sUnwatermarkedLargeFileId);
/* 1416 */       uwImageFile.setPixelWidth(iaUnwatermarkedLargeSize[0]);
/* 1417 */       uwImageFile.setPixelHeight(iaUnwatermarkedLargeSize[1]);
/* 1418 */       a_asset.setUnwatermarkedLargeImageFile(uwImageFile);
/*      */     }
/*      */   }
/*      */ 
/*      */   private int[] resizeImage(String a_sSourcePath, int a_iSourceWidth, int a_iSourceHeight, boolean a_bSourceIsCMYK, int iNumLayers, String a_sDestFileId, int a_iNewSize, String a_sRgbColorProfile, String a_sCmykColorProfile)
/*      */     throws Bn2Exception, ImageException
/*      */   {
/* 1445 */     String sDest = this.m_fileStoreManager.getAbsolutePath(a_sDestFileId);
/* 1446 */     int[] iaSize = ABImageMagick.resizeToJpg(a_sSourcePath, sDest, a_iSourceWidth, a_iSourceHeight, a_iNewSize, a_iNewSize, a_bSourceIsCMYK, a_sRgbColorProfile, a_sCmykColorProfile, iNumLayers);
/*      */ 
/* 1457 */     this.m_fileStoreManager.storeFile(a_sDestFileId);
/*      */ 
/* 1459 */     return iaSize;
/*      */   }
/*      */ 
/*      */   private String getLargeImageFilename(ImageFile a_imageFile, String a_sFileLocation, String a_sExtension)
/*      */     throws Bn2Exception
/*      */   {
/* 1472 */     return this.m_fileStoreManager.getUniqueFilenameForRelatedFile(a_sFileLocation, a_sExtension, StoredFileType.PREVIEW_OR_THUMBNAIL);
/*      */   }
/*      */ 
/*      */   private int getLargeImageSize(int a_iSettingSize, int a_iSourceWidth, int a_iSourceHeight)
/*      */   {
/* 1479 */     int iSize = a_iSettingSize;
/*      */ 
/* 1482 */     int iImageLargestDim = a_iSourceWidth;
/*      */ 
/* 1484 */     if (a_iSourceHeight > iImageLargestDim)
/*      */     {
/* 1486 */       iImageLargestDim = a_iSourceHeight;
/*      */     }
/*      */ 
/* 1490 */     if (iSize > iImageLargestDim)
/*      */     {
/* 1492 */       iSize = iImageLargestDim;
/*      */     }
/*      */ 
/* 1495 */     return iSize;
/*      */   }
/*      */ 
/*      */   public void createFeaturedImageFile(ImageAsset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 1513 */     String sSource = this.m_fileStoreManager.getAbsolutePath(a_asset.getFileLocation());
/*      */ 
/* 1516 */     String sFeaturedFileId = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(a_asset.getFileLocation(), "-f.jpg", StoredFileType.PREVIEW_OR_THUMBNAIL);
/*      */ 
/* 1520 */     String sDest = this.m_fileStoreManager.getAbsolutePath(sFeaturedFileId);
/*      */ 
/* 1523 */     ImageFileInfo imageInfo = ABImageMagick.getInfo(sSource);
/*      */ 
/* 1525 */     int iSourceWidth = imageInfo.getWidth();
/* 1526 */     int iSourceHeight = imageInfo.getHeight();
/* 1527 */     int iColorSpace = imageInfo.getColorSpace();
/*      */ 
/* 1529 */     boolean bIsCMYK = ABImageMagick.getIsCMYK(iColorSpace);
/*      */ 
/* 1532 */     String sRgbColorProfile = this.m_usageManager.getColorSpace(null, 1).getFileLocation();
/* 1533 */     String sCmykColorProfile = this.m_usageManager.getColorSpace(null, 2).getFileLocation();
/*      */ 
/* 1536 */     int iNewWidth = AssetBankSettings.getFeaturedImageWidth();
/* 1537 */     int iNewHeight = AssetBankSettings.getFeaturedImageHeight();
/*      */ 
/* 1539 */     ABImageMagick.resizeAndCropToJpg(sSource, sDest, iSourceWidth, iSourceHeight, iNewWidth, iNewHeight, bIsCMYK, sRgbColorProfile, sCmykColorProfile, a_asset.getNumPages());
/*      */ 
/* 1551 */     this.m_logger.trace("Generated featured image version for asset id=" + a_asset.getId());
/*      */ 
/* 1553 */     a_asset.setFeaturedImageFile(new ImageFile(sFeaturedFileId));
/*      */ 
/* 1555 */     this.m_fileStoreManager.storeFile(sFeaturedFileId);
/*      */   }
/*      */ 
/*      */   protected void deleteGeneratedImages(Asset a_asset, Set<GeneratedImageVersion> a_versionsToDelete)
/*      */   {
/* 1567 */     if (((a_asset instanceof ImageAsset)) && ((a_versionsToDelete == null) || (a_versionsToDelete.contains(GeneratedImageVersion.FEATURED))))
/*      */     {
/* 1569 */       deleteFeaturedImageFile((ImageAsset)a_asset);
/*      */     }
/*      */ 
/* 1572 */     super.deleteGeneratedImages(a_asset, a_versionsToDelete);
/*      */   }
/*      */ 
/*      */   public void deleteFeaturedImageFile(ImageAsset a_asset)
/*      */   {
/* 1587 */     if (StringUtil.stringIsPopulated(a_asset.getFeaturedImageFile().getPath()))
/*      */     {
/* 1589 */       this.m_fileStoreManager.deleteFile(a_asset.getFeaturedImageFile().getPath());
/* 1590 */       a_asset.setFeaturedImageFile(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void saveImageToDatabase(DBTransaction a_dbTransaction, ImageAsset a_image, boolean a_bUpdateFileInfo)
/*      */     throws Bn2Exception
/*      */   {
/* 1618 */     Connection con = null;
/* 1619 */     ResultSet rs = null;
/* 1620 */     String sSQL = null;
/* 1621 */     int iCol = 1;
/* 1622 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1624 */     if (transaction == null)
/*      */     {
/* 1626 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1631 */       con = transaction.getConnection();
/*      */ 
/* 1633 */       if (a_bUpdateFileInfo)
/*      */       {
/* 1637 */         sSQL = "SELECT AssetId FROM ImageAsset WHERE AssetId=?";
/*      */ 
/* 1640 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 1641 */         psql.setLong(1, a_image.getId());
/* 1642 */         rs = psql.executeQuery();
/*      */ 
/* 1644 */         if (rs.next())
/*      */         {
/* 1646 */           sSQL = "UPDATE ImageAsset SET Height=?, Width=?, ColorSpace=?, NumLayers=?, LargeFileLocation=?, UnwatermarkedLargeFileLocation=?, FeaturedFileLocation=? WHERE AssetId=?";
/*      */         }
/*      */         else
/*      */         {
/* 1658 */           sSQL = "INSERT INTO ImageAsset (Height, Width, ColorSpace, NumLayers, LargeFileLocation, UnwatermarkedLargeFileLocation, FeaturedFileLocation, AssetId ) VALUES (?,?,?,?,?,?,?,?)";
/*      */         }
/*      */ 
/* 1670 */         psql.close();
/*      */ 
/* 1673 */         psql = con.prepareStatement(sSQL);
/*      */ 
/* 1676 */         psql.setInt(iCol++, a_image.getHeight());
/* 1677 */         psql.setInt(iCol++, a_image.getWidth());
/* 1678 */         psql.setInt(iCol++, a_image.getColorSpace());
/* 1679 */         psql.setInt(iCol++, a_image.getNumPages());
/* 1680 */         psql.setString(iCol++, a_image.getLargeImageFile() == null ? "" : a_image.getLargeImageFile().getPath());
/* 1681 */         psql.setString(iCol++, a_image.getUnwatermarkedLargeImageFile() == null ? "" : a_image.getUnwatermarkedLargeImageFile().getPath());
/* 1682 */         psql.setString(iCol++, a_image.getFeaturedImageFile().getPath());
/* 1683 */         psql.setLong(iCol++, a_image.getId());
/*      */ 
/* 1685 */         psql.executeUpdate();
/* 1686 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1691 */       this.m_logger.error("SQL Exception whilst saving image to the database : " + e);
/* 1692 */       throw new Bn2Exception("SQL Exception whilst saving image to the database : ", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1697 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1701 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1705 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteAsset(DBTransaction a_dbTransaction, long a_lImageId, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 1728 */     ImageAsset image = null;
/* 1729 */     Connection con = null;
/* 1730 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1732 */     if (transaction == null)
/*      */     {
/* 1734 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/* 1738 */     if (a_asset != null)
/*      */     {
/* 1740 */       image = (ImageAsset)a_asset;
/*      */     }
/*      */     else
/*      */     {
/* 1744 */       image = (ImageAsset)getAsset(transaction, a_lImageId, null, null, false);
/* 1745 */       a_asset = image;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1750 */       con = transaction.getConnection();
/*      */ 
/* 1753 */       if (AssetBankSettings.getAssetRepurposingEnabled())
/*      */       {
/* 1755 */         this.m_assetRepurposingManager.removeRepurposedImages(transaction, image.getId());
/*      */       }
/*      */ 
/* 1758 */       String sSQL = "DELETE FROM ImageAsset WHERE AssetId=?";
/* 1759 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1760 */       psql.setLong(1, a_lImageId);
/*      */ 
/* 1762 */       psql.executeUpdate();
/* 1763 */       psql.close();
/*      */ 
/* 1766 */       super.deleteAsset(transaction, a_lImageId, a_asset);
/*      */ 
/* 1769 */       if ((image.getPreviewImageFile() != null) && (StringUtils.isNotEmpty(image.getPreviewImageFile().getPath())))
/*      */       {
/* 1771 */         this.m_fileStoreManager.deleteFile(image.getPreviewImageFile().getPath());
/*      */       }
/*      */ 
/* 1777 */       if (image.getHomogenizedImageFile() != null)
/*      */       {
/* 1779 */         this.m_fileStoreManager.deleteFile(image.getHomogenizedImageFile().getPath());
/*      */       }
/* 1781 */       if (image.getLargeImageFile() != null)
/*      */       {
/* 1783 */         this.m_fileStoreManager.deleteFile(image.getLargeImageFile().getPath());
/*      */       }
/* 1785 */       if (StringUtils.isNotEmpty(image.getFeaturedImageFile().getPath()))
/*      */       {
/* 1787 */         this.m_fileStoreManager.deleteFile(image.getFeaturedImageFile().getPath());
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1792 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1796 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1800 */           this.m_logger.error("SQL Exception whilst trying to roll back transaction " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1804 */       this.m_logger.error("Exception whilst deleting image from the database : " + e);
/* 1805 */       throw new Bn2Exception("Exception whilst deleting image from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1810 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1814 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1818 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getSupportedImageFormats(DBTransaction a_dbTransaction, boolean a_bWritableOnly)
/*      */     throws Bn2Exception
/*      */   {
/* 1840 */     return super.getSupportedFileFormats(a_dbTransaction, 2L, a_bWritableOnly);
/*      */   }
/*      */ 
/*      */   protected UploadedFileInfo getAdditionalFileInfo(UploadedFileInfo fileInfo, String a_sLocation)
/*      */   {
/* 1858 */     UploadedImageInfo imageInfo = new UploadedImageInfo(fileInfo);
/*      */ 
/* 1861 */     String sImageLocation = a_sLocation;
/*      */ 
/* 1864 */     if ((fileInfo.getConvertedLocation() != null) && (fileInfo.getConvertedLocation().length() > 0))
/*      */     {
/* 1866 */       sImageLocation = fileInfo.getConvertedLocation();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1871 */       ImageFileInfo info = ABImageMagick.getInfo(this.m_fileStoreManager.getAbsolutePath(sImageLocation));
/*      */ 
/* 1873 */       imageInfo.setImageWidth(info.getWidth());
/* 1874 */       imageInfo.setImageHeight(info.getHeight());
/* 1875 */       imageInfo.setNumLayers(info.getNumberOfLayers());
/*      */     }
/*      */     catch (ImageException ie)
/*      */     {
/* 1880 */       this.m_logger.warn("ImageAssetManagerImpl.getUploadedFileInfo had a problem reading dimensions of uploaded image file: " + a_sLocation + ": " + ie.getMessage());
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1885 */       this.m_logger.warn("ImageAssetManagerImpl.getUploadedFileInfo had a problem reading dimensions of uploaded image file: " + a_sLocation + ": " + e.getMessage(), e);
/*      */     }
/*      */ 
/* 1888 */     return imageInfo;
/*      */   }
/*      */ 
/*      */   public Vector getDownloadableImageFormats()
/*      */     throws Bn2Exception
/*      */   {
/* 1905 */     return getSupportedImageFormats(null, true);
/*      */   }
/*      */ 
/*      */   public boolean hasEmbeddedColorProfile(String a_sImagePath)
/*      */     throws Bn2Exception
/*      */   {
/* 1922 */     String sFilename = FileUtil.getFilenameWithoutSuffix(a_sImagePath, true) + ".icc";
/* 1923 */     String sTempFilePath = this.m_fileStoreManager.getAbsolutePath(this.m_fileStoreManager.getUniqueFilepath(sFilename, StoredFileType.TEMP));
/*      */ 
/* 1926 */     ImageMagickOptionList opts = new ImageMagickOptionList();
/* 1927 */     opts.addInputFilename(a_sImagePath);
/* 1928 */     opts.addOutputFilename(sTempFilePath);
/*      */ 
/* 1930 */     boolean bHasProfile = false;
/*      */     try
/*      */     {
/* 1933 */       ImageMagick.convert(opts);
/*      */ 
/* 1936 */       bHasProfile = true;
/*      */     }
/*      */     catch (ImageException ie)
/*      */     {
/* 1941 */       bHasProfile = false;
/*      */     }
/*      */ 
/* 1945 */     File fTemp = new File(sTempFilePath);
/* 1946 */     if (fTemp.exists())
/*      */     {
/* 1948 */       fTemp.delete();
/* 1949 */       FileUtil.logFileDeletion(fTemp);
/*      */     }
/*      */ 
/* 1952 */     return bHasProfile;
/*      */   }
/*      */ 
/*      */   public void generateTints()
/*      */   {
/* 1969 */     String sDownloadTints = AssetBankSettings.getDownloadTints();
/*      */ 
/* 1971 */     if (StringUtil.stringIsPopulated(sDownloadTints))
/*      */     {
/* 1974 */       String[] saDownloadTints = sDownloadTints.split(";");
/*      */ 
/* 1976 */       for (int x = 0; x < saDownloadTints.length; x++)
/*      */       {
/* 1978 */         String sTint = saDownloadTints[x];
/*      */ 
/* 1980 */         Tint tint = new Tint();
/* 1981 */         String[] asName = sTint.split(":");
/* 1982 */         tint.setName(asName[0]);
/*      */ 
/* 1984 */         String[] asColour = asName[1].split(",");
/* 1985 */         tint.setColour(asColour[0]);
/* 1986 */         tint.setPercentage(asColour[1]);
/*      */ 
/* 1988 */         this.m_vecTints.add(tint);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getTints()
/*      */   {
/* 2006 */     return this.m_vecTints;
/*      */   }
/*      */ 
/*      */   private String getLegacyThumbnailFile(Asset a_asset)
/*      */   {
/* 2017 */     if (!StringUtil.stringIsPopulated(AssetBankSettings.getLegacyThumbnailDirectory()))
/*      */     {
/* 2019 */       return null;
/*      */     }
/*      */ 
/* 2023 */     String[] saThumbnailDirs = AssetBankSettings.getLegacyThumbnailDirectory().split(";");
/* 2024 */     String sThumbnailFile = null;
/*      */ 
/* 2027 */     for (int i = 0; i < saThumbnailDirs.length; i++)
/*      */     {
/* 2029 */       if (saThumbnailDirs[i] == null)
/*      */         continue;
/* 2031 */       sThumbnailFile = saThumbnailDirs[i] + "/" + FileUtil.getFilenameWithoutSuffix(a_asset.getOriginalFilename(), true) + AssetBankSettings.getLegacyThumbnailSuffix();
/*      */ 
/* 2035 */       if (new File(sThumbnailFile).exists())
/*      */       {
/* 2038 */         return sThumbnailFile;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2043 */     return null;
/*      */   }
/*      */ 
/*      */   public void setDownloadQueueManager(DownloadImageQueueManager a_sDownloadQueueManager)
/*      */   {
/* 2049 */     this.m_downloadQueueManager = a_sDownloadQueueManager;
/*      */   }
/*      */ 
/*      */   public MaskManager getMaskManager()
/*      */   {
/* 2054 */     return this.m_maskManager;
/*      */   }
/*      */ 
/*      */   public void setMaskManager(MaskManager a_maskManager)
/*      */   {
/* 2059 */     this.m_maskManager = a_maskManager;
/*      */   }
/*      */ 
/*      */   public void setUsageManager(UsageManager a_sUsageManager)
/*      */   {
/* 2064 */     this.m_usageManager = a_sUsageManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.ImageAssetManagerImpl
 * JD-Core Version:    0.6.0
 */