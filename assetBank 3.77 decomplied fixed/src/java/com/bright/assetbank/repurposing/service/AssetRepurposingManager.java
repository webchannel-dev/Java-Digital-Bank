/*      */ package com.bright.assetbank.repurposing.service;
/*      */ 
/*      */ import com.bn2web.common.constant.GlobalSettings;
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.FileFormat;
/*      */ import com.bright.assetbank.application.bean.ImageAsset;
/*      */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*      */ import com.bright.assetbank.application.bean.ImageFileInfo;
/*      */ import com.bright.assetbank.application.bean.VideoAsset;
/*      */ import com.bright.assetbank.application.bean.VideoInfo;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.service.AssetManager;
/*      */ import com.bright.assetbank.application.util.ABImageMagick;
/*      */ import com.bright.assetbank.application.util.AssetUtil;
/*      */ import com.bright.assetbank.application.util.VideoUtil;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.repurposing.bean.RepurposedAsset;
/*      */ import com.bright.assetbank.repurposing.bean.RepurposedSlideshow;
/*      */ import com.bright.assetbank.repurposing.bean.RepurposedVersion;
/*      */ import com.bright.assetbank.repurposing.util.RepurposingUtil;
/*      */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*      */ import com.bright.assetbank.search.util.SearchUtil;
/*      */ import com.bright.assetbank.usage.bean.ColorSpace;
/*      */ import com.bright.assetbank.usage.service.UsageManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.image.bean.ImageFile;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.util.BrightDateFormat;
/*      */ import com.bright.framework.util.CollectionUtil;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.ServletUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import com.bright.framework.util.XMLUtil;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AssetRepurposingManager extends Bn2Manager
/*      */ {
/*      */   private static final int k_iVideoHeightMargin = 24;
/*      */   private static final String k_sListItemId_ImageHtmlSnippet = "technical-embeddable-image-html";
/*      */   private static final String k_sListItemId_VideoHtmlSnippet = "technical-embeddable-video-html";
/*      */   private static final String k_sListItemId_AudioHtmlSnippet = "technical-embeddable-audio-html";
/*      */   private static final String k_sListItemId_EmbeddedSlideshowHtmlSnippet = "technical-embeddable-slideshow-html";
/*      */   private static final String k_sListItemId_EmbeddedSlideshowDownloadHtmlHeader = "technical-embeddable-slideshow-download-html-header";
/*      */   private static final String k_sListItemId_EmbeddedSlideshowDownloadHtmlFooter = "technical-embeddable-slideshow-download-html-footer";
/*      */   private static final String k_sRepurposedVersionColumns = "rv.Id raId, rv.Url raUrl, rv.CreatedByUserId raCreatedByUserId, rv.CreatedDate raCreatedDate, rv.Height raHeight, rv.Width raWidth, u.Forename uForename, u.Surname uSurname, u.Username uUsername, u.emailAddress uemailAddress";
/*      */   private static final String k_sRepurposedVersionTables = "RepurposedVersion rv LEFT JOIN AssetBankUser u ON u.Id=rv.CreatedByUserId";
/*      */   private static final String c_ksClassName = "AssetRepurposingManager";
/*      */   private static final String k_sListItemVariable_Width = "width";
/*      */   private static final String k_sListItemVariable_Height = "height";
/*      */   private static final String k_sListItemVariable_Url = "url";
/*      */   private static final String k_sListItemVariable_UrlStub = "urlStub";
/*      */   private static final String k_sListItemVariable_CommonUrlStub = "commonUrlStub";
/*      */   private static final String k_sListItemVariable_Id = "id";
/*      */   private static final String k_sListItemVariable_FlashPlayerBaseUrl = "flvPlayerUrl";
/*      */   private static final String k_sListItemVariable_PreviewFileLocation = "previewFileLocation";
/*      */   private static final String k_sListItemVariable_Duration = "duration";
/*      */   private static final double k_dNumAssetsPerDir = 100.0D;
/*      */   private static final String k_sListItemVariable_DisplayTime = "displayTime";
/*      */   private static final String k_sListItemVariable_InfoBar = "infoBar";
/*      */   private static final String k_sListItemVariable_SlideshowSize = "slideshowSize";
/*      */   private static final String k_sListItemVariable_OnExternalSite = "onExternalSite";
/*      */   private static final String k_sListItemVariable_Type = "type";
/*      */   private static final String k_sListItemVariable_Title = "title";
/*  135 */   private FileStoreManager m_fileStoreManager = null;
/*  136 */   private EmailManager m_emailManager = null;
/*  137 */   private ListManager m_listManager = null;
/*  138 */   private DBTransactionManager m_transactionManager = null;
/*  139 */   private AssetManager m_assetManager = null;
/*  140 */   private ScheduleManager m_scheduleManager = null;
/*  141 */   private UsageManager m_usageManager = null;
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  150 */     super.startup();
/*  151 */     int iRepurposedSlideshowRefreshPeriod = AssetBankSettings.getRepurposedSlideshowRefreshPeriod() * 1000 * 60 * 60;
/*      */ 
/*  153 */     if (iRepurposedSlideshowRefreshPeriod > 0)
/*      */     {
/*  156 */       TimerTask task = new TimerTask()
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           try
/*      */           {
/*  162 */             AssetRepurposingManager.this.refreshRepurposedSlideshows(null);
/*      */           }
/*      */           catch (Bn2Exception e)
/*      */           {
/*  166 */             AssetRepurposingManager.this.m_logger.error("Scheduled task to refresh repurposed slideshow encountered error: " + e.getLocalizedMessage(), e);
/*      */           }
/*      */         }
/*      */       };
/*  171 */       this.m_scheduleManager.schedule(task, 0L, iRepurposedSlideshowRefreshPeriod, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public RepurposedAsset addRepurposedAsset(DBTransaction a_transaction, Asset a_asset, long a_lUserId, String a_sTempFileLocation, String a_sBaseUrl)
/*      */     throws Bn2Exception
/*      */   {
/*  192 */     String sBasePath = AssetBankSettings.getRepurposedFileBasePath();
/*  193 */     String sUrlDir = getDirectoryForAsset(a_asset.getId(), sBasePath);
/*  194 */     String sPath = sBasePath + "/" + sUrlDir;
/*  195 */     FileUtil.ensureDirectoryExists(new File(sPath));
/*      */ 
/*  198 */     String sFileLocation = sPath + "/" + FileUtil.getUniqueFilename(sPath, FileUtil.getFilename(a_sTempFileLocation));
/*  199 */     FileUtil.copyFile(this.m_fileStoreManager.getAbsolutePath(a_sTempFileLocation), sFileLocation);
/*      */ 
/*  202 */     String sFileUrl = sUrlDir + "/" + FileUtil.getFilename(sFileLocation);
/*      */ 
/*  205 */     FileFormat format = a_asset.getFormat();
/*  206 */     String sPreviewFileUrl = null;
/*  207 */     VideoInfo vidInfo = null;
/*      */ 
/*  209 */     if ((format.getAssetTypeId() == 3L) && (AssetBankSettings.getRepurposedVideoMaintainPreview()))
/*      */     {
/*  211 */       VideoAsset video = (VideoAsset)a_asset;
/*      */ 
/*  214 */       String sPrevFileLocation = sPath + "/" + FileUtil.getUniqueFilename(sPath, FileUtil.getFilename(video.getPreviewImageFile().getPath()));
/*  215 */       String sOriginalPreview = this.m_fileStoreManager.getAbsolutePath(video.getPreviewImageFile().getPath());
/*      */ 
/*  218 */       ImageFileInfo imageInfo = ABImageMagick.getInfo(sOriginalPreview);
/*  219 */       vidInfo = VideoUtil.getVideoInfo(sFileLocation);
/*      */ 
/*  221 */       if ((vidInfo.getWidth() < imageInfo.getWidth()) && (vidInfo.getHeight() < imageInfo.getHeight()))
/*      */       {
/*  223 */         int iColorSpace = imageInfo.getColorSpace();
/*  224 */         boolean bIsCMYK = ABImageMagick.getIsCMYK(iColorSpace);
/*      */ 
/*  227 */         String sRgbColorProfile = this.m_usageManager.getColorSpace(a_transaction, 1).getFileLocation();
/*  228 */         String sCmykColorProfile = this.m_usageManager.getColorSpace(a_transaction, 2).getFileLocation();
/*      */ 
/*  231 */         ABImageMagick.resizeAndCropToJpg(sOriginalPreview, sPrevFileLocation, imageInfo.getWidth(), imageInfo.getHeight(), vidInfo.getWidth(), vidInfo.getHeight(), bIsCMYK, sRgbColorProfile, sCmykColorProfile, imageInfo.getNumberOfLayers());
/*      */       }
/*      */       else
/*      */       {
/*  246 */         FileUtil.copyFile(sOriginalPreview, sPrevFileLocation);
/*      */       }
/*      */ 
/*  249 */       sPreviewFileUrl = sUrlDir + "/" + FileUtil.getFilename(sPrevFileLocation);
/*      */     }
/*      */ 
/*  253 */     RepurposedAsset repAsset = createRepurposedAsset(a_transaction, a_asset.getId(), a_lUserId, sFileUrl, sFileLocation, a_sBaseUrl, sPreviewFileUrl, format, vidInfo);
/*      */ 
/*  255 */     return saveRepurposedAsset(a_transaction, repAsset);
/*      */   }
/*      */ 
/*      */   private RepurposedAsset createRepurposedAsset(DBTransaction a_transaction, long a_lAssetId, long a_lUserId, String sFileUrl, String a_sFileLocation, String a_sBaseUrl, String a_sPreviewFileLocation, FileFormat a_format, VideoInfo a_vidInfo)
/*      */     throws Bn2Exception
/*      */   {
/*  280 */     RepurposedAsset repAsset = new RepurposedAsset();
/*      */ 
/*  282 */     repAsset.setUrl(sFileUrl);
/*  283 */     repAsset.setAssetId(a_lAssetId);
/*  284 */     repAsset.setFileFormatId(a_format.getId());
/*  285 */     repAsset.setCreatedDate(new Date(System.currentTimeMillis()));
/*  286 */     repAsset.setCreatedByUser(new ABUser(a_lUserId));
/*      */ 
/*  288 */     String sHtmlSnippet = null;
/*      */ 
/*  290 */     if (a_format.getAssetTypeId() == 2L)
/*      */     {
/*  293 */       ImageFileInfo info = ABImageMagick.getInfo(a_sFileLocation);
/*  294 */       repAsset.setHeight(String.valueOf(info.getHeight()));
/*  295 */       repAsset.setWidth(String.valueOf(info.getWidth()));
/*  296 */       sHtmlSnippet = this.m_listManager.getListItem(a_transaction, "technical-embeddable-image-html", LanguageConstants.k_defaultLanguage).getBody();
/*      */     }
/*  298 */     else if (a_format.getAssetTypeId() == 3L)
/*      */     {
/*  300 */       VideoInfo info = a_vidInfo;
/*  301 */       if (info == null)
/*      */       {
/*  303 */         info = VideoUtil.getVideoInfo(a_sFileLocation);
/*      */       }
/*      */ 
/*  306 */       repAsset.setHeight(String.valueOf(info.getDisplayHeight()));
/*  307 */       repAsset.setWidth(String.valueOf(info.getWidth()));
/*  308 */       repAsset.setDuration(info.getDuration());
/*      */ 
/*  311 */       if (StringUtil.stringIsPopulated(a_sPreviewFileLocation))
/*      */       {
/*  313 */         repAsset.setPreviewFileLocation(a_sPreviewFileLocation);
/*      */       }
/*      */ 
/*  316 */       sHtmlSnippet = this.m_listManager.getListItem(a_transaction, "technical-embeddable-video-html", LanguageConstants.k_defaultLanguage).getBody();
/*      */     }
/*  318 */     else if (a_format.getAssetTypeId() == 4L)
/*      */     {
/*  320 */       VideoInfo info = VideoUtil.getVideoInfo(a_sFileLocation);
/*  321 */       repAsset.setDuration(info.getDuration());
/*  322 */       sHtmlSnippet = this.m_listManager.getListItem(a_transaction, "technical-embeddable-audio-html", LanguageConstants.k_defaultLanguage).getBody();
/*      */     }
/*      */ 
/*  325 */     if (sHtmlSnippet != null)
/*      */     {
/*  327 */       populateEmbeddableHtmlForAsset(a_sBaseUrl, repAsset, sHtmlSnippet, a_format.getAssetTypeId());
/*      */     }
/*      */ 
/*  330 */     return repAsset;
/*      */   }
/*      */ 
/*      */   private String getDirectoryForAsset(long a_lAssetId, String a_sBasePath)
/*      */     throws Bn2Exception
/*      */   {
/*  343 */     long lDirNo = (long)Math.ceil(a_lAssetId / 100.0D) - 1L;
/*  344 */     String sUrlDir = AssetBankSettings.getRepurposedFileUrlPath() + "/" + (lDirNo * 100L + 1L) + "-" + (lDirNo + 1L) * 100L;
/*  345 */     FileUtil.ensureDirectoryExists(new File(a_sBasePath + "/" + sUrlDir));
/*  346 */     sUrlDir = sUrlDir + "/" + a_lAssetId;
/*  347 */     return sUrlDir;
/*      */   }
/*      */ 
/*      */   private RepurposedVersion saveRepurposedVersion(DBTransaction a_transaction, RepurposedVersion a_version)
/*      */     throws Bn2Exception
/*      */   {
/*  362 */     String ksMethodName = "saveRepurposedVersion";
/*      */ 
/*  364 */     Connection con = null;
/*      */     try
/*      */     {
/*  368 */       con = a_transaction.getConnection();
/*      */ 
/*  370 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*  371 */       long lNewId = 0L;
/*      */ 
/*  373 */       String sSql = "INSERT INTO RepurposedVersion (";
/*      */ 
/*  375 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  377 */         lNewId = sqlGenerator.getUniqueId(con, "RepurposedAssetSequence");
/*  378 */         sSql = sSql + "Id,";
/*      */       }
/*      */ 
/*  381 */       sSql = sSql + "Url,CreatedByUserId,CreatedDate,Height,Width) VALUES (?,?,?,?,?";
/*      */ 
/*  383 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  385 */         sSql = sSql + ",?";
/*      */       }
/*      */ 
/*  388 */       sSql = sSql + ")";
/*      */ 
/*  390 */       int iCol = 1;
/*  391 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/*  393 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  395 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*      */ 
/*  398 */       psql.setString(iCol++, a_version.getUrl());
/*  399 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_version.getCreatedByUser());
/*  400 */       psql.setTimestamp(iCol++, new Timestamp(a_version.getCreatedDate().getTime()));
/*  401 */       psql.setString(iCol++, a_version.getHeight());
/*  402 */       psql.setString(iCol++, a_version.getWidth());
/*  403 */       psql.executeUpdate();
/*      */ 
/*  406 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  408 */         lNewId = sqlGenerator.getUniqueId(con, "RepurposedAsset");
/*      */       }
/*      */ 
/*  411 */       a_version.setId(lNewId);
/*      */ 
/*  413 */       psql.close();
/*      */ 
/*  415 */       return a_version;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  419 */       this.m_logger.error("AssetRepurposingManager.saveRepurposedVersion : SQL Exception : " + e, e);
/*  420 */     throw new Bn2Exception("AssetRepurposingManager.saveRepurposedVersion : SQL Exception : " + e, e);}
/*      */   }
/*      */ 
/*      */   private RepurposedAsset saveRepurposedAsset(DBTransaction a_transaction, RepurposedAsset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/*  436 */     String ksMethodName = "saveRepurposedAsset";
/*      */ 
/*  438 */     Connection con = null;
/*      */     try
/*      */     {
/*  443 */       a_asset = (RepurposedAsset)saveRepurposedVersion(a_transaction, a_asset);
/*  444 */       con = a_transaction.getConnection();
/*      */ 
/*  447 */       String sSql = "INSERT INTO RepurposedAsset (RepurposedVersionId,AssetId,FileFormatId,Duration,PreviewFileLocation) VALUES (?,?,?,?,?)";
/*      */ 
/*  454 */       int iCol = 1;
/*  455 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  456 */       psql.setLong(iCol++, a_asset.getId());
/*  457 */       psql.setLong(iCol++, a_asset.getAssetId());
/*  458 */       psql.setLong(iCol++, a_asset.getFileFormatId());
/*  459 */       psql.setLong(iCol++, a_asset.getDuration());
/*  460 */       psql.setString(iCol++, a_asset.getPreviewFileLocation());
/*  461 */       psql.executeUpdate();
/*  462 */       psql.close();
/*      */ 
/*  464 */       return a_asset;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  468 */       this.m_logger.error("AssetRepurposingManager.saveRepurposedAsset : SQL Exception : " + e, e);
/*  469 */     throw new Bn2Exception("AssetRepurposingManager.saveRepurposedAsset : SQL Exception : " + e, e);}
/*      */   }
/*      */ 
/*      */   private void saveRepurposedSlideshow(DBTransaction a_transaction, RepurposedSlideshow a_slideshow)
/*      */     throws Bn2Exception
/*      */   {
/*  483 */     String ksMethodName = "saveRepurposedSlideshow";
/*      */ 
/*  485 */     Connection con = null;
/*      */     try
/*      */     {
/*  489 */       con = a_transaction.getConnection();
/*      */ 
/*  492 */       if (a_slideshow.getDefaultOnHomepage())
/*      */       {
/*  494 */         String sSql = "UPDATE RepurposedSlideshow SET IsDefaultOnHomepage=?";
/*  495 */         PreparedStatement psql = con.prepareStatement(sSql);
/*  496 */         psql.setBoolean(1, false);
/*  497 */         psql.executeUpdate();
/*  498 */         psql.close();
/*      */       }
/*      */ 
/*  502 */       int iMaxSequenceNumber = 0;
/*  503 */       String sSql = "SELECT MAX(SequenceNumber) as maxSequenceNumber FROM RepurposedSlideshow";
/*  504 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  505 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  507 */       if (rs.next())
/*      */       {
/*  509 */         iMaxSequenceNumber = rs.getInt("maxSequenceNumber");
/*      */       }
/*      */ 
/*  513 */       if (a_slideshow.getSearchCriteria() != null)
/*      */       {
/*  515 */         a_slideshow.setCriteriaFile(SearchUtil.serializeSearch(this.m_fileStoreManager, a_slideshow.getSearchCriteria()));
/*      */       }
/*  517 */       psql.close();
/*      */ 
/*  522 */       sSql = "UPDATE RepurposedVersion SET Url=? WHERE Id=?";
/*  523 */       psql = con.prepareStatement(sSql);
/*  524 */       psql.setString(1, a_slideshow.getUrl());
/*  525 */       psql.setLong(2, a_slideshow.getId());
/*  526 */       psql.executeUpdate();
/*  527 */       psql.close();
/*      */ 
/*  530 */       sSql = "INSERT INTO RepurposedSlideshow (RepurposedVersionId,DisplayTime,InfoBar, SearchCriteriaFile, MaintainAspectRatio, IncludeLabels, CaptionIds, JpgConversionQuality, LanguageCode, ImageHeight, ImageWidth, Description, ShowOnHomepage, IsDefaultOnHomepage, SequenceNumber, SlideShowTypeId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/*  548 */       int iCol = 1;
/*  549 */       psql = con.prepareStatement(sSql);
/*  550 */       psql.setLong(iCol++, a_slideshow.getId());
/*  551 */       psql.setInt(iCol++, a_slideshow.getDisplayTime());
/*  552 */       psql.setBoolean(iCol++, a_slideshow.getInfoBar());
/*  553 */       psql.setString(iCol++, a_slideshow.getCriteriaFile());
/*  554 */       psql.setBoolean(iCol++, a_slideshow.getMaintainAspectRatio());
/*  555 */       psql.setBoolean(iCol++, a_slideshow.getIncludeLabels());
/*  556 */       psql.setString(iCol++, a_slideshow.getCaptionIdsString());
/*  557 */       psql.setInt(iCol++, a_slideshow.getIntJpgConversionQuality());
/*  558 */       psql.setString(iCol++, a_slideshow.getLanguageCode());
/*  559 */       psql.setInt(iCol++, a_slideshow.getImageHeight());
/*  560 */       psql.setInt(iCol++, a_slideshow.getImageWidth());
/*  561 */       psql.setString(iCol++, a_slideshow.getDescription());
/*  562 */       psql.setBoolean(iCol++, a_slideshow.getShowInListOnHomepage());
/*  563 */       psql.setBoolean(iCol++, a_slideshow.getDefaultOnHomepage());
/*  564 */       iMaxSequenceNumber++; psql.setLong(iCol++, iMaxSequenceNumber);
/*  565 */       psql.setLong(iCol++, a_slideshow.getDisplayTypeId());
/*  566 */       psql.executeUpdate();
/*  567 */       psql.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  573 */       this.m_logger.error("AssetRepurposingManager.saveRepurposedSlideshow : SQL Exception : " + e, e);
/*  574 */       throw new Bn2Exception("AssetRepurposingManager.saveRepurposedSlideshow : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateRepurposedSlideshow(DBTransaction a_transaction, RepurposedSlideshow a_slideshow)
/*      */     throws Bn2Exception
/*      */   {
/*  584 */     String ksMethodName = "saveRepurposedSlideshow";
/*      */ 
/*  586 */     Connection con = null;
/*      */     try
/*      */     {
/*  592 */       con = a_transaction.getConnection();
/*      */ 
/*  595 */       if (a_slideshow.getDefaultOnHomepage())
/*      */       {
/*  597 */         String sSql = "UPDATE RepurposedSlideshow SET IsDefaultOnHomepage=?";
/*  598 */         PreparedStatement psql = con.prepareStatement(sSql);
/*  599 */         psql.setBoolean(1, false);
/*  600 */         psql.executeUpdate();
/*  601 */         psql.close();
/*      */       }
/*      */ 
/*  604 */       String sSql = "UPDATE RepurposedSlideshow SET Description=?, ShowOnHomepage=?, IsDefaultOnHomepage=? WHERE RepurposedVersionId=?";
/*  605 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  606 */       psql.setString(1, a_slideshow.getDescription());
/*  607 */       psql.setBoolean(2, a_slideshow.getShowInListOnHomepage());
/*  608 */       psql.setBoolean(3, a_slideshow.getDefaultOnHomepage());
/*  609 */       psql.setLong(4, a_slideshow.getId());
/*  610 */       psql.executeUpdate();
/*  611 */       psql.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  616 */       this.m_logger.error("AssetRepurposingManager.saveRepurposedSlideshow : SQL Exception : " + e, e);
/*  617 */       throw new Bn2Exception("AssetRepurposingManager.saveRepurposedSlideshow : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void populateEmbeddableHtmlForAsset(String a_sBaseUrl, RepurposedAsset a_asset, String a_sHtmlSnippet, long a_lTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  625 */     String sHtml = a_sHtmlSnippet.replaceAll("\\?url\\?", a_sBaseUrl + "/" + a_asset.getUrl());
/*      */ 
/*  627 */     if (a_lTypeId != 4L)
/*      */     {
/*  629 */       sHtml = sHtml.replaceAll("\\?height\\?", String.valueOf(Integer.parseInt(a_asset.getHeight()) + (a_lTypeId == 3L ? 24 : 0)));
/*  630 */       sHtml = sHtml.replaceAll("\\?width\\?", String.valueOf(a_asset.getWidth()));
/*      */     }
/*      */ 
/*  633 */     sHtml = sHtml.replaceAll("\\?id\\?", String.valueOf(a_asset.getAssetId()));
/*      */ 
/*  635 */     if ((a_lTypeId == 3L) || (a_lTypeId == 4L))
/*      */     {
/*  637 */       String sPlayerBaseUrl = AssetBankSettings.getRepurposedVideoFlashPlayerBaseUrl();
/*      */ 
/*  639 */       if (StringUtils.isEmpty(sPlayerBaseUrl))
/*      */       {
/*  641 */         sPlayerBaseUrl = a_sBaseUrl + "/" + AssetBankSettings.getFlashPlayerBaseUrl();
/*      */       }
/*      */ 
/*  644 */       sHtml = sHtml.replaceAll("\\?flvPlayerUrl\\?", sPlayerBaseUrl);
/*      */ 
/*  647 */       if ((a_lTypeId == 3L) && (StringUtil.stringIsPopulated(a_asset.getPreviewFileLocation())))
/*      */       {
/*  649 */         sHtml = sHtml.replaceAll("\\?previewFileLocation\\?", a_sBaseUrl + "/" + a_asset.getPreviewFileLocation());
/*      */       }
/*      */ 
/*  653 */       sHtml = sHtml.replaceAll("\\?duration\\?", String.valueOf(a_asset.getDuration() / 1000L));
/*      */     }
/*      */ 
/*  656 */     a_asset.setEmbeddableHtml(sHtml);
/*      */   }
/*      */ 
/*      */   private void populateEmbeddableHtmlForSlideshow(DBTransaction a_transaction, RepurposedSlideshow a_slideshow, String a_sUrlStub, String a_sCommonUrlStub, String a_sFilename)
/*      */     throws Bn2Exception
/*      */   {
/*  665 */     String sHtml = this.m_listManager.getListItem(a_transaction, "technical-embeddable-slideshow-html", LanguageConstants.k_defaultLanguage).getBody();
/*      */ 
/*  667 */     String sSlideShowType = "";
/*      */ 
/*  670 */     if (a_slideshow.getDisplayTypeId() == 2L)
/*      */     {
/*  672 */       sSlideShowType = "photoEssay";
/*      */     }
/*      */     else
/*      */     {
/*  677 */       sSlideShowType = "slideshow";
/*      */     }
/*      */ 
/*  681 */     sHtml = sHtml.replaceAll("\\?urlStub\\?", a_sUrlStub);
/*  682 */     sHtml = sHtml.replaceAll("\\?commonUrlStub\\?", a_sCommonUrlStub);
/*  683 */     sHtml = sHtml.replaceAll("\\?url\\?", a_sFilename);
/*  684 */     sHtml = sHtml.replaceAll("\\?displayTime\\?", String.valueOf(a_slideshow.getDisplayTime()));
/*  685 */     sHtml = sHtml.replaceAll("\\?width\\?", a_slideshow.getWidth());
/*  686 */     sHtml = sHtml.replaceAll("\\?height\\?", a_slideshow.getHeight());
/*  687 */     sHtml = sHtml.replaceAll("\\?onExternalSite\\?", "on");
/*  688 */     sHtml = sHtml.replaceAll("\\?type\\?", sSlideShowType);
/*  689 */     sHtml = sHtml.replaceAll("\\?title\\?", a_slideshow.getDescription());
/*  690 */     a_slideshow.setEmbeddableHtml(sHtml);
/*      */ 
/*  693 */     String sHomepageHtml = this.m_listManager.getListItem(a_transaction, "technical-embeddable-slideshow-html", LanguageConstants.k_defaultLanguage).getBody();
/*  694 */     sHomepageHtml = sHomepageHtml.replaceAll("\\?urlStub\\?", a_sUrlStub);
/*  695 */     sHomepageHtml = sHomepageHtml.replaceAll("\\?commonUrlStub\\?", a_sCommonUrlStub);
/*  696 */     sHomepageHtml = sHomepageHtml.replaceAll("\\?url\\?", a_sFilename);
/*  697 */     sHomepageHtml = sHomepageHtml.replaceAll("\\?displayTime\\?", String.valueOf(a_slideshow.getDisplayTime()));
/*  698 */     sHomepageHtml = sHomepageHtml.replaceAll("\\?width\\?", "100%");
/*  699 */     sHomepageHtml = sHomepageHtml.replaceAll("\\?height\\?", "100%");
/*  700 */     sHomepageHtml = sHomepageHtml.replaceAll("\\?onExternalSite\\?", "off");
/*  701 */     sHomepageHtml = sHomepageHtml.replaceAll("\\?type\\?", sSlideShowType);
/*  702 */     sHomepageHtml = sHomepageHtml.replaceAll("\\?title\\?", a_slideshow.getDescription());
/*  703 */     a_slideshow.setHomepageEmbeddableHtml(sHomepageHtml);
/*      */   }
/*      */ 
/*      */   public RepurposedAsset addRepurposedAsset(DBTransaction a_transaction, Asset a_asset, long a_lUserId, String a_sTempFileLocation)
/*      */     throws Bn2Exception
/*      */   {
/*  721 */     return addRepurposedAsset(a_transaction, a_asset, a_lUserId, a_sTempFileLocation, "");
/*      */   }
/*      */ 
/*      */   public boolean getHasRepurposedAssets(DBTransaction a_transaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  734 */     String ksMethodName = "getHasRepurposedAssets";
/*      */ 
/*  736 */     PreparedStatement psql = null;
/*      */ 
/*  738 */     Connection con = null;
/*      */     try
/*      */     {
/*  742 */       con = a_transaction.getConnection();
/*      */ 
/*  745 */       String sSql = "SELECT ra.RepurposedVersionId raId FROM RepurposedAsset ra WHERE ra.AssetId = ? ";
/*  746 */       psql = con.prepareStatement(sSql);
/*  747 */       psql.setLong(1, a_lAssetId);
/*  748 */       ResultSet rs = psql.executeQuery();
/*  749 */       boolean bool = rs.next();
/*      */       return bool;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  753 */       this.m_logger.error("AssetRepurposingManager.getHasRepurposedAssets : SQL Exception : " + e, e);
/*  754 */       throw new Bn2Exception("AssetRepurposingManager.getHasRepurposedAssets : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  758 */       if (psql != null)
/*      */       {
/*      */         try
/*      */         {
/*  762 */           psql.close(); } catch (SQLException e) {
                                            throw new Bn2Exception (e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*  764 */     //throw localObject;
/*      */   }
/*      */ 
/*      */   public ArrayList<RepurposedAsset> getRepurposedImages(DBTransaction a_transaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  779 */     return getRepurposedAssets(a_transaction, a_lAssetId, "");
/*      */   }
/*      */ 
/*      */   public ArrayList<RepurposedAsset> getRepurposedAssets(DBTransaction a_transaction, long a_lAssetId, String a_sBaseUrl)
/*      */     throws Bn2Exception
/*      */   {
/*  792 */     String ksMethodName = "getRepurposedAssets";
/*      */ 
/*  794 */     ArrayList alAssets = new ArrayList();
/*  795 */     RepurposedAsset repAsset = null;
/*      */ 
/*  797 */     Connection con = null;
/*      */     try
/*      */     {
/*  801 */       con = a_transaction.getConnection();
/*      */ 
/*  804 */       String sSql = "SELECT rv.Id raId, rv.Url raUrl, rv.CreatedByUserId raCreatedByUserId, rv.CreatedDate raCreatedDate, rv.Height raHeight, rv.Width raWidth, u.Forename uForename, u.Surname uSurname, u.Username uUsername, u.emailAddress uemailAddress, ra.AssetId raAssetId, ra.FileFormatId raFileFormatId, ra.Duration raDuration, ra.PreviewFileLocation, a.AssetTypeId aAssetTypeId FROM RepurposedVersion rv LEFT JOIN AssetBankUser u ON u.Id=rv.CreatedByUserId LEFT JOIN RepurposedAsset ra ON rv.Id=ra.RepurposedVersionId INNER JOIN Asset a ON a.Id = ra.AssetId WHERE ra.AssetId = ? ORDER BY rv.Width, rv.CreatedDate DESC ";
/*      */ 
/*  816 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  817 */       psql.setLong(1, a_lAssetId);
/*  818 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  820 */       while (rs.next())
/*      */       {
/*  822 */         repAsset = new RepurposedAsset();
/*  823 */         populateRepurposedAsset(a_transaction, repAsset, rs, a_sBaseUrl);
/*  824 */         alAssets.add(repAsset);
/*      */       }
/*      */ 
/*  827 */       psql.close();
/*  828 */       return alAssets;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  832 */       this.m_logger.error("AssetRepurposingManager.getRepurposedAssets : SQL Exception : " + e, e);
/*  833 */     throw new Bn2Exception("AssetRepurposingManager.getRepurposedAssets : SQL Exception : " + e, e);}
/*      */   }
/*      */ 
/*      */   public ArrayList<RepurposedSlideshow> getRepurposedSlideshows(DBTransaction a_transaction, boolean a_bRefreshingOnly, boolean a_bHomepageOnly, HttpServletRequest a_request)
/*      */     throws Bn2Exception
/*      */   {
/*  845 */     String ksMethodName = "getRepurposedSlideshows";
/*      */ 
/*  847 */     ArrayList alSlideshows = new ArrayList();
/*  848 */     RepurposedSlideshow repSlideshow = null;
/*  849 */     String sUrlStub = AssetBankSettings.getRepurposedFileBaseUrl(a_request) + "/" + AssetBankSettings.getRepurposedSlideshowPath();
/*      */ 
/*  851 */     Connection con = null;
/*      */     try
/*      */     {
/*  855 */       con = a_transaction.getConnection();
/*  856 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/*  858 */       String sSql = "SELECT rv.Id raId, rv.Url raUrl, rv.CreatedByUserId raCreatedByUserId, rv.CreatedDate raCreatedDate, rv.Height raHeight, rv.Width raWidth, u.Forename uForename, u.Surname uSurname, u.Username uUsername, u.emailAddress uemailAddress, rs.DisplayTime, rs.InfoBar, rs.SearchCriteriaFile, rs.MaintainAspectRatio, rs.IncludeLabels, rs.CaptionIds, rs.JpgConversionQuality, rs.LanguageCode, rs.ImageHeight, rs.ImageWidth, rs.Description, rs.ShowOnHomepage, rs.IsDefaultOnHomepage, rs.SequenceNumber, rs.SlideShowTypeId, sst.Type as SlideShowDisplayType FROM RepurposedVersion rv LEFT JOIN AssetBankUser u ON u.Id=rv.CreatedByUserId JOIN RepurposedSlideshow rs ON rv.Id=rs.RepurposedVersionId LEFT JOIN SlideShowType sst ON sst.Id=rs.SlideShowTypeId WHERE 1=1 ";
/*      */ 
/*  880 */       if (a_bRefreshingOnly)
/*      */       {
/*  882 */         sSql = sSql + "AND NOT " + sqlGenerator.getNullCheckStatement("rs.SearchCriteriaFile") + " ";
/*      */       }
/*      */ 
/*  885 */       if (a_bHomepageOnly)
/*      */       {
/*  887 */         sSql = sSql + "AND ShowOnHomepage=? ";
/*  888 */         sSql = sSql + "ORDER BY rs.SequenceNumber ";
/*      */       }
/*      */       else
/*      */       {
/*  892 */         sSql = sSql + "ORDER BY rv.CreatedDate DESC ";
/*      */       }
/*      */ 
/*  895 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/*  897 */       if (a_bHomepageOnly)
/*      */       {
/*  899 */         psql.setBoolean(1, true);
/*      */       }
/*      */ 
/*  902 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  904 */       while (rs.next())
/*      */       {
/*  906 */         repSlideshow = new RepurposedSlideshow();
/*  907 */         populateRepurposedSlideshow(a_transaction, repSlideshow, rs, sUrlStub);
/*  908 */         alSlideshows.add(repSlideshow);
/*      */       }
/*      */ 
/*  911 */       psql.close();
/*  912 */       return alSlideshows;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  916 */       this.m_logger.error("AssetRepurposingManager.getRepurposedSlideshows : SQL Exception : " + e, e);
/*  917 */     throw new Bn2Exception("AssetRepurposingManager.getRepurposedSlideshows : SQL Exception : " + e, e);
/*      */  } }
/*      */ 
/*      */   public RepurposedSlideshow getRepurposedSlideshow(DBTransaction a_transaction, long a_lId, HttpServletRequest a_request)
/*      */     throws Bn2Exception
/*      */   {
/*  928 */     String ksMethodName = "getRepurposedSlideshow";
/*  929 */     RepurposedSlideshow repSlideshow = null;
/*  930 */     String sUrlStub = AssetBankSettings.getRepurposedFileBaseUrl(a_request) + "/" + AssetBankSettings.getRepurposedSlideshowPath();
/*      */ 
/*  932 */     Connection con = null;
/*      */     try
/*      */     {
/*  936 */       con = a_transaction.getConnection();
/*      */ 
/*  939 */       String sSql = "SELECT rv.Id raId, rv.Url raUrl, rv.CreatedByUserId raCreatedByUserId, rv.CreatedDate raCreatedDate, rv.Height raHeight, rv.Width raWidth, u.Forename uForename, u.Surname uSurname, u.Username uUsername, u.emailAddress uemailAddress, rs.DisplayTime, rs.InfoBar, rs.SearchCriteriaFile, rs.MaintainAspectRatio, rs.IncludeLabels, rs.CaptionIds, rs.JpgConversionQuality, rs.LanguageCode, rs.ImageHeight, rs.ImageWidth, rs.Description, rs.ShowOnHomepage, rs.IsDefaultOnHomepage, rs.SequenceNumber, rs.SlideShowTypeId, sst.Type as SlideShowDisplayType FROM RepurposedVersion rv LEFT JOIN AssetBankUser u ON u.Id=rv.CreatedByUserId LEFT JOIN RepurposedSlideshow rs ON rv.Id=rs.RepurposedVersionId LEFT JOIN SlideShowType sst ON sst.Id=rs.SlideShowTypeId WHERE rv.Id=?";
/*      */ 
/*  961 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  962 */       psql.setLong(1, a_lId);
/*  963 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  965 */       if (rs.next())
/*      */       {
/*  967 */         repSlideshow = new RepurposedSlideshow();
/*  968 */         populateRepurposedSlideshow(a_transaction, repSlideshow, rs, sUrlStub);
/*      */       }
/*      */ 
/*  971 */       return repSlideshow;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  975 */       this.m_logger.error("AssetRepurposingManager.getRepurposedSlideshow : SQL Exception : " + e, e);
/*  976 */     throw new Bn2Exception("AssetRepurposingManager.getRepurposedSlideshow : SQL Exception : " + e, e);
/*      */   }}
/*      */ 
/*      */   public String downloadRepurposedSlideshow(DBTransaction a_transaction, long a_lId, HttpServletRequest a_request)
/*      */     throws Bn2Exception
/*      */   {
/*  988 */     String ksMethodName = "downloadRepurposedSlideshow";
/*  989 */     String sLink = null;
/*      */     try
/*      */     {
/*  993 */       String sFileStub = GlobalSettings.getApplicationPath() + "/" + AssetBankSettings.getRepurposedSlideshowPath() + "/";
/*  994 */       String sFilenameNoExtension = "slideshow" + a_lId;
/*  995 */       String sZipFilename = sFilenameNoExtension + ".zip";
/*  996 */       String sPath = sFileStub + sFilenameNoExtension + "/" + sZipFilename;
/*  997 */       String sUrlStub = ServletUtil.getApplicationUrl(a_request) + "/" + AssetBankSettings.getRepurposedSlideshowPath() + "/" + sFilenameNoExtension + "/";
/*  998 */       sLink = sUrlStub + sZipFilename;
/*      */ 
/* 1001 */       File file = new File(sPath);
/* 1002 */       if (!file.exists())
/*      */       {
/* 1004 */         RepurposedSlideshow slideshow = getRepurposedSlideshow(a_transaction, a_lId, a_request);
/*      */ 
/* 1006 */         String sHtmlFile = sFileStub + sFilenameNoExtension + "/" + "index.html";
/* 1007 */         File htmlFile = new File(sHtmlFile);
/* 1008 */         if (!htmlFile.exists())
/*      */         {
/* 1012 */           String sHtml = this.m_listManager.getListItem(a_transaction, "technical-embeddable-slideshow-download-html-header", LanguageConstants.k_defaultLanguage).getBody();
/*      */ 
/* 1014 */           populateEmbeddableHtmlForSlideshow(a_transaction, slideshow, "", "", "slideshow_d.xml");
/* 1015 */           sHtml = sHtml + slideshow.getEmbeddableHtml();
/* 1016 */           sHtml = sHtml + this.m_listManager.getListItem(a_transaction, "technical-embeddable-slideshow-download-html-footer", LanguageConstants.k_defaultLanguage).getBody();
/*      */ 
/* 1019 */           FileWriter fstream = new FileWriter(htmlFile);
/* 1020 */           BufferedWriter out = new BufferedWriter(fstream);
/* 1021 */           out.write(sHtml);
/* 1022 */           out.close();
/*      */         }
/*      */ 
/* 1026 */         String sXMLFile = sFileStub + sFilenameNoExtension + "/" + "slideshow_d.xml";
/* 1027 */         File xmlFile = new File(sXMLFile);
/* 1028 */         if (!xmlFile.exists())
/*      */         {
/* 1031 */           String sOriginalXMLFile = sFileStub + sFilenameNoExtension + "/" + "slideshow.xml";
/* 1032 */           File originalXmlFile = new File(sOriginalXMLFile);
/* 1033 */           FileUtil.copyFile(originalXmlFile, xmlFile);
/* 1034 */           FileUtil.replaceStringInFile(xmlFile, sUrlStub, "");
/*      */         }
/*      */ 
/* 1038 */         String sFileLoc = sFileStub + sFilenameNoExtension;
/* 1039 */         File directory = new File(sFileLoc);
/* 1040 */         File[] files = directory.listFiles();
/* 1041 */         File[][] fileArrays = new File[2][];
/* 1042 */         fileArrays[0] = files;
/*      */ 
/* 1045 */         sFileLoc = sFileStub + "common";
/* 1046 */         directory = new File(sFileLoc);
/* 1047 */         File[] commonfiles = directory.listFiles();
/* 1048 */         fileArrays[1] = commonfiles;
/* 1049 */        // File[] fullList = (File[])CollectionUtil.convertToSingleArray(fileArrays, (java.io.File.class));
/*      */         File[] fullList = (File[])CollectionUtil.convertToSingleArray(fileArrays,File[].class);
                   
/* 1051 */         ArrayList alExclusions = new ArrayList();
/* 1052 */         alExclusions.add("slideshow.xml");
/*      */ 
/* 1054 */         FileUtil.createZipFile(file, fullList, alExclusions);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1059 */       this.m_logger.error("AssetRepurposingManager.downloadRepurposedSlideshow : Error preparing embeddable slideshow download: ", e);
/* 1060 */       throw new Bn2Exception("AssetRepurposingManager.downloadRepurposedSlideshow : Error preparing embeddable slideshow download: ", e);
/*      */     }
/* 1062 */     return sLink;
/*      */   }
/*      */ 
/*      */   public RepurposedAsset getRepurposedAsset(DBTransaction a_transaction, long a_lRepurposedAssetId, String a_sBaseUrl)
/*      */     throws Bn2Exception
/*      */   {
/* 1076 */     return getRepurposedAsset(a_transaction, a_lRepurposedAssetId, a_sBaseUrl, 0, 0);
/*      */   }
/*      */ 
/*      */   public RepurposedAsset getRepurposedAsset(DBTransaction a_transaction, long a_lRepurposedAssetId, String a_sBaseUrl, int a_iHeight, int a_iWidth)
/*      */     throws Bn2Exception
/*      */   {
/* 1089 */     String ksMethodName = "getRepurposedAsset";
/*      */ 
/* 1091 */     RepurposedAsset repAsset = null;
/*      */ 
/* 1093 */     Connection con = null;
/*      */     try
/*      */     {
/* 1097 */       con = a_transaction.getConnection();
/*      */ 
/* 1100 */       String sSql = "SELECT rv.Id raId, rv.Url raUrl, rv.CreatedByUserId raCreatedByUserId, rv.CreatedDate raCreatedDate, rv.Height raHeight, rv.Width raWidth, u.Forename uForename, u.Surname uSurname, u.Username uUsername, u.emailAddress uemailAddress, ra.AssetId raAssetId, ra.FileFormatId raFileFormatId, ra.Duration raDuration, ra.PreviewFileLocation, a.AssetTypeId aAssetTypeId FROM RepurposedVersion rv LEFT JOIN AssetBankUser u ON u.Id=rv.CreatedByUserId LEFT JOIN RepurposedAsset ra ON rv.Id=ra.RepurposedVersionId INNER JOIN Asset a ON a.Id = ra.AssetId WHERE ra.RepurposedVersionId = ? ";
/*      */ 
/* 1111 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 1112 */       psql.setLong(1, a_lRepurposedAssetId);
/* 1113 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1115 */       if (rs.next())
/*      */       {
/* 1117 */         repAsset = new RepurposedAsset();
/* 1118 */         populateRepurposedAsset(a_transaction, repAsset, rs, a_sBaseUrl, a_iHeight, a_iWidth);
/*      */       }
/*      */ 
/* 1121 */       psql.close();
/*      */ 
/* 1123 */       return repAsset;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1127 */       this.m_logger.error("AssetRepurposingManager.getRepurposedAsset : SQL Exception : " + e, e);
/* 1128 */     throw new Bn2Exception("AssetRepurposingManager.getRepurposedAsset : SQL Exception : " + e, e);
/*      */   }}
/*      */ 
/*      */   private String getHtmlSnippetForAsset(DBTransaction a_transaction, ResultSet a_rs)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1134 */     String sHtmlSnippet = null;
/* 1135 */     if (a_rs.getLong("aAssetTypeId") == 2L)
/*      */     {
/* 1137 */       sHtmlSnippet = this.m_listManager.getListItem(a_transaction, "technical-embeddable-image-html", LanguageConstants.k_defaultLanguage).getBody();
/*      */     }
/* 1139 */     else if (a_rs.getLong("aAssetTypeId") == 3L)
/*      */     {
/* 1141 */       sHtmlSnippet = this.m_listManager.getListItem(a_transaction, "technical-embeddable-video-html", LanguageConstants.k_defaultLanguage).getBody();
/*      */     }
/* 1143 */     else if (a_rs.getLong("aAssetTypeId") == 4L)
/*      */     {
/* 1145 */       sHtmlSnippet = this.m_listManager.getListItem(a_transaction, "technical-embeddable-audio-html", LanguageConstants.k_defaultLanguage).getBody();
/*      */     }
/*      */ 
/* 1148 */     return sHtmlSnippet;
/*      */   }
/*      */ 
/*      */   public void removeRepurposedImages(DBTransaction a_transaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1160 */     String ksMethodName = "removeRepurposedImages";
/* 1161 */     DBTransaction transaction = a_transaction;
/*      */ 
/* 1163 */     if (transaction == null)
/*      */     {
/* 1165 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1171 */       String sBasePath = AssetBankSettings.getRepurposedFileBasePath();
/*      */ 
/* 1173 */       ArrayList<RepurposedAsset> assets = getRepurposedImages(transaction, a_lAssetId);
/*      */ 
/* 1175 */       if (assets.size() > 0)
/*      */       {
/* 1177 */         for (RepurposedAsset image : assets)
/*      */         {
/* 1179 */           File version = new File(sBasePath + "/" + image.getUrl());
/*      */ 
/* 1181 */           if ((!version.delete()) && (version.exists()))
/*      */           {
/* 1183 */             this.m_logger.error("AssetRepurposingManager.removeRepurposedImages : Could not delete repurposed file: " + sBasePath + "/" + image.getUrl());
/*      */           }
/*      */           else
/*      */           {
/* 1187 */             FileUtil.logFileDeletion(version);
/*      */           }
/*      */         }
/*      */ 
/* 1191 */         performDeletion(transaction, a_lAssetId, -1L);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1196 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1200 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1204 */           this.m_logger.error("AssetRepurposingManager.removeRepurposedImages : SQL Exception while rolling back : " + e, e);
/*      */         }
/*      */       }
/* 1207 */       this.m_logger.error("AssetRepurposingManager.removeRepurposedImages : SQL Exception : " + e, e);
/* 1208 */       throw new Bn2Exception("AssetRepurposingManager.removeRepurposedImages : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1213 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1217 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1221 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage(), sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeRepurposedAsset(DBTransaction a_transaction, long a_lRepurposedAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1235 */     String ksMethodName = "removeRepurposedVersion";
/* 1236 */     String sBasePath = AssetBankSettings.getRepurposedFileBasePath();
/*      */ 
/* 1238 */     RepurposedAsset repAsset = getRepurposedAsset(a_transaction, a_lRepurposedAssetId, "");
/*      */ 
/* 1240 */     File file = new File(sBasePath + "/" + repAsset.getUrl());
/* 1241 */     file.delete();
/* 1242 */     FileUtil.logFileDeletion(file);
/*      */ 
/* 1245 */     if (StringUtil.stringIsPopulated(repAsset.getPreviewFileLocation()))
/*      */     {
/* 1247 */       file = new File(sBasePath + "/" + repAsset.getPreviewFileLocation());
/* 1248 */       file.delete();
/* 1249 */       FileUtil.logFileDeletion(file);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1254 */       performDeletion(a_transaction, -1L, a_lRepurposedAssetId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1258 */       this.m_logger.error("AssetRepurposingManager.removeRepurposedVersion : SQL Exception : " + e, e);
/* 1259 */       throw new Bn2Exception("AssetRepurposingManager.removeRepurposedVersion : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeRepurposedSlideshow(DBTransaction a_transaction, long a_lRepurposedSlideshowId)
/*      */     throws Bn2Exception
/*      */   {
/* 1273 */     String ksMethodName = "removeRepurposedSlideshow";
/* 1274 */     String sDir = "/slideshow" + a_lRepurposedSlideshowId;
/* 1275 */     String sPath = AssetBankSettings.getRepurposedFileBasePath() + "/" + AssetBankSettings.getRepurposedSlideshowPath() + sDir;
/*      */ 
/* 1277 */     File file = new File(sPath);
/* 1278 */     if (file.isDirectory())
/*      */     {
/* 1281 */       File[] files = file.listFiles();
/*      */ 
/* 1283 */       for (File tempFile : files)
/*      */       {
/* 1285 */         tempFile.delete();
/* 1286 */         FileUtil.logFileDeletion(file);
/*      */       }
/*      */ 
/* 1290 */       file.delete();
/* 1291 */       FileUtil.logFileDeletion(file);
/*      */     }
/*      */ 
/* 1295 */     Connection con = null;
/*      */     try
/*      */     {
/* 1298 */       con = a_transaction.getConnection();
/* 1299 */       String sSql = "DELETE FROM RepurposedSlideshow WHERE RepurposedVersionId=?";
/* 1300 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 1301 */       psql.setLong(1, a_lRepurposedSlideshowId);
/* 1302 */       psql.executeUpdate();
/* 1303 */       psql.close();
/*      */ 
/* 1305 */       sSql = "DELETE FROM RepurposedVersion WHERE Id=?";
/* 1306 */       psql = con.prepareStatement(sSql);
/* 1307 */       psql.setLong(1, a_lRepurposedSlideshowId);
/* 1308 */       psql.executeUpdate();
/* 1309 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1313 */       this.m_logger.error("AssetRepurposingManager.removeRepurposedSlideshow : SQL Exception : ", e);
/* 1314 */       throw new Bn2Exception("AssetRepurposingManager.removeRepurposedSlideshow : SQL Exception : ", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void warnOfImageUpdate(DBTransaction a_transaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1328 */     String ksMethodName = "warnOfImageUpdate";
/* 1329 */     Connection con = null;
/* 1330 */     String sSql = null;
/* 1331 */     PreparedStatement psql = null;
/* 1332 */     DBTransaction transaction = a_transaction;
/*      */ 
/* 1334 */     if (transaction == null)
/*      */     {
/* 1336 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/* 1339 */     String sBasePath = AssetBankSettings.getRepurposedFileBaseUrl(null);
/*      */ 
/* 1341 */     String sAssetUrl = AssetBankSettings.getApplicationUrl() + "/action/" + "viewAsset" + "?" + "id" + "=" + a_lAssetId;
/*      */     try
/*      */     {
/* 1346 */       con = transaction.getConnection();
/*      */ 
/* 1348 */       sSql = "SELECT rv.Id raId, rv.Url raUrl, ra.AssetId raAssetId, rv.CreatedByUserId raCreatedByUserId, rv.CreatedDate raCreatedDate, u.Title uTitle, u.Forename uForename, u.Surname uSurname, u.Username uUsername, u.emailAddress uemailAddress, u.LanguageId uLanguageId FROM RepurposedVersion rv LEFT JOIN RepurposedAsset ra ON rv.Id=ra.RepurposedVersionId LEFT JOIN AssetBankUser u ON u.Id=rv.CreatedByUserId WHERE ra.AssetId = ? ";
/*      */ 
/* 1366 */       psql = con.prepareStatement(sSql);
/* 1367 */       psql.setLong(1, a_lAssetId);
/*      */ 
/* 1369 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1371 */       HashMap params = new HashMap();
/* 1372 */       params.put("template", "repurposed_image_updated");
/* 1373 */       params.put("assetUrl", sAssetUrl);
/*      */ 
/* 1375 */       while (rs.next())
/*      */       {
/* 1377 */         if ((rs.getLong("raCreatedByUserId") <= 0L) || (!StringUtils.isNotEmpty(rs.getString("uemailAddress"))))
/*      */           continue;
/* 1379 */         ABUser user = new ABUser();
/* 1380 */         user.setTitle(rs.getString("uTitle"));
/* 1381 */         user.setForename(rs.getString("uForename"));
/* 1382 */         user.setSurname(rs.getString("uSurname"));
/*      */ 
/* 1384 */         params.put("recipients", rs.getString("uemailAddress"));
/* 1385 */         params.put("name", user.getFullName());
/* 1386 */         params.put("username", rs.getString("uUsername"));
/* 1387 */         params.put("versionUrl", sBasePath + "/" + rs.getString("raUrl"));
/* 1388 */         params.put("date", AssetBankSettings.getStandardDateFormat().format(rs.getDate("raCreatedDate")));
/*      */ 
/* 1390 */         this.m_emailManager.sendTemplatedEmail(params, new Language(rs.getLong("uLanguageId")));
/*      */       }
/*      */ 
/* 1394 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1398 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1402 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1406 */           this.m_logger.error("AssetRepurposingManager.warnOfImageUpdate : SQL Exception while rolling back : " + e, e);
/*      */         }
/*      */       }
/* 1409 */       this.m_logger.error("AssetRepurposingManager.warnOfImageUpdate : SQL Exception : " + e, e);
/* 1410 */       throw new Bn2Exception("AssetRepurposingManager.warnOfImageUpdate : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1415 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1419 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1423 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage(), sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateAssetEmbedStatus(DBTransaction a_transaction, long a_lAssetId, boolean a_bCanEmbed)
/*      */     throws Bn2Exception
/*      */   {
/* 1438 */     String ksMethodName = "updateAssetEmbedStatus";
/* 1439 */     Connection con = null;
/* 1440 */     String sSql = null;
/* 1441 */     PreparedStatement psql = null;
/* 1442 */     DBTransaction transaction = a_transaction;
/*      */ 
/* 1444 */     if (transaction == null)
/*      */     {
/* 1446 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1451 */       con = transaction.getConnection();
/*      */ 
/* 1453 */       sSql = "UPDATE Asset SET CanEmbedFile=? WHERE Id=?";
/*      */ 
/* 1455 */       psql = con.prepareStatement(sSql);
/* 1456 */       psql.setBoolean(1, a_bCanEmbed);
/* 1457 */       psql.setLong(2, a_lAssetId);
/*      */ 
/* 1459 */       psql.executeUpdate();
/*      */ 
/* 1461 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1465 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1469 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1473 */           this.m_logger.error("AssetRepurposingManager.updateAssetEmbedStatus : SQL Exception while rolling back : " + e, e);
/*      */         }
/*      */       }
/* 1476 */       this.m_logger.error("AssetRepurposingManager.updateAssetEmbedStatus : SQL Exception : " + e, e);
/* 1477 */       throw new Bn2Exception("AssetRepurposingManager.updateAssetEmbedStatus : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1482 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1486 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1490 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage(), sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public RepurposedSlideshow addRepurposedSlideshow(DBTransaction a_transaction, RepurposedSlideshow a_rs, String a_sProposedFormat, HttpServletRequest a_request) throws Bn2Exception
/*      */   {
/* 1506 */     String ksMethodName = "addRepurposedSlideshow";
/*      */     String sMessage;
/*      */     try
/*      */     {
/* 1511 */       a_rs = (RepurposedSlideshow)saveRepurposedVersion(a_transaction, a_rs);
/* 1512 */       generateSlideshowFiles(a_rs, a_sProposedFormat, a_request);
/* 1513 */       String sUrlStub = AssetBankSettings.getRepurposedFileBaseUrl(a_request) + "/" + AssetBankSettings.getRepurposedSlideshowPath();
/* 1514 */       String sDir = "/slideshow" + a_rs.getId();
/*      */ 
/* 1517 */       populateEmbeddableHtmlForSlideshow(a_transaction, a_rs, sUrlStub + sDir + "/", sUrlStub + "/" + "common" + "/", "slideshow.xml");
/*      */ 
/* 1520 */       saveRepurposedSlideshow(a_transaction, a_rs);
/*      */ 
/* 1522 */       return a_rs;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1526 */       sMessage = "AssetRepurposingManager.addRepurposedSlideshow: Error repurposing slideshow ";
/* 1527 */       this.m_logger.error(sMessage, e);
/* 1528 */     throw new Bn2Exception(sMessage, e);}
/*      */   }
/*      */ 
/*      */   private void regenerateSlideshowFiles(RepurposedSlideshow a_rs, String a_sProposedFormat, HttpServletRequest a_request)
/*      */     throws Bn2Exception, IOException
/*      */   {
/* 1540 */     String sPath = AssetBankSettings.getRepurposedFileBasePath() + "/" + AssetBankSettings.getRepurposedSlideshowPath() + "/" + "slideshow" + a_rs.getId();
/* 1541 */     File directory = new File(sPath);
/* 1542 */     if (directory.exists())
/*      */     {
/* 1544 */       for (File file : directory.listFiles())
/*      */       {
/* 1546 */         file.delete();
/* 1547 */         FileUtil.logFileDeletion(file);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1552 */     generateSlideshowFiles(a_rs, a_sProposedFormat, a_request);
/*      */   }
/*      */ 
/*      */   private void generateSlideshowFiles(RepurposedSlideshow a_rs, String a_sProposedFormat, HttpServletRequest a_request)
/*      */     throws Bn2Exception, IOException
/*      */   {
/* 1563 */     HashMap hmSubFiles = new HashMap();
/*      */ 
/* 1565 */     HashMap hmSubThumbnailFiles = new HashMap();
/* 1566 */     String sUrlStub = AssetBankSettings.getRepurposedFileBaseUrl(a_request) + "/" + AssetBankSettings.getRepurposedSlideshowPath();
/* 1567 */     String sDir = "/slideshow" + a_rs.getId();
/* 1568 */     String sPath = AssetBankSettings.getRepurposedFileBasePath() + "/" + AssetBankSettings.getRepurposedSlideshowPath() + sDir;
/* 1569 */     for (Asset asset : a_rs.getAssets())
/*      */     {
/* 1571 */       String sFileName = null;
/* 1572 */       String sThumbnailFileName = null;
/* 1573 */       FileUtil.ensureDirectoryExists(new File(sPath));
/* 1574 */       if (a_rs.getConversionInfo() != null)
/*      */       {
/* 1577 */         String sTempFileLocation = this.m_assetManager.getDownloadableAssetPath(asset, a_sProposedFormat, a_rs.getConversionInfo());
/* 1578 */         String sFileLocation = sPath + "/" + FileUtil.getUniqueFilename(sPath, FileUtil.getFilename(sTempFileLocation));
/* 1579 */         FileUtil.copyFile(this.m_fileStoreManager.getAbsolutePath(sTempFileLocation), sFileLocation);
/*      */ 
/* 1581 */         sFileName = sUrlStub + sDir + "/" + FileUtil.getFilename(sFileLocation);
/*      */       }
/*      */       else
/*      */       {
/* 1585 */         String sTempFilename = null;
/* 1586 */         String sAbsolutePath = null;
/*      */ 
/* 1589 */         ImageFile imFile = ((ImageAsset)asset).getUnwatermarkedLargeImageFile();
/* 1590 */         if ((imFile != null) && (imFile.getPath() != null))
/*      */         {
/* 1592 */           sAbsolutePath = this.m_fileStoreManager.getAbsolutePath(imFile.getPath());
/* 1593 */           sTempFilename = FileUtil.getFilename(sAbsolutePath);
/*      */         }
/*      */         else
/*      */         {
/* 1598 */           sAbsolutePath = this.m_fileStoreManager.getAbsolutePath(this.m_assetManager.getTemporaryLargeFile((ImageAsset)asset, AssetBankSettings.getUnwatermarkedLargeImageSize(), -1, true));
/* 1599 */           sTempFilename = FileUtil.getFilename(sAbsolutePath);
/*      */         }
/* 1601 */         FileUtil.copyFile(sAbsolutePath, sPath + "/" + sTempFilename);
/* 1602 */         sFileName = sUrlStub + sDir + "/" + sTempFilename;
/*      */       }
/*      */ 
/* 1607 */       hmSubFiles.put(new Long(asset.getId()), sFileName);
/*      */ 
/* 1611 */       if (a_rs.getDisplayTypeId() == 2L)
/*      */       {
/* 1613 */         ImageConversionInfo conversionInfo = new ImageConversionInfo();
/* 1614 */         conversionInfo.setJpegQuality(AssetBankSettings.getJpgConversionQuality());
/* 1615 */         conversionInfo.setScaleUp(true);
/* 1616 */         conversionInfo.setMaxHeight(40);
/* 1617 */         conversionInfo.setMaxWidth(1000);
/* 1618 */         conversionInfo.setMaintainAspectRatio(true);
/*      */ 
/* 1621 */         String sTempFileLocation = this.m_assetManager.getDownloadableAssetPath(asset, a_sProposedFormat, conversionInfo);
/* 1622 */         String sFileLocation = sPath + "/" + FileUtil.getUniqueFilename(sPath, FileUtil.getFilename(sTempFileLocation));
/* 1623 */         FileUtil.copyFile(this.m_fileStoreManager.getAbsolutePath(sTempFileLocation), sFileLocation);
/*      */ 
/* 1625 */         sThumbnailFileName = sUrlStub + sDir + "/" + FileUtil.getFilename(sFileLocation);
/*      */ 
/* 1627 */         hmSubThumbnailFiles.put(new Long(asset.getId()), sThumbnailFileName);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1633 */     boolean bGetAllAttributes = true;
/*      */ 
/* 1635 */     if (a_rs.getDisplayTypeId() == 2L)
/*      */     {
/* 1637 */       bGetAllAttributes = false;
/*      */     }
/*      */ 
/* 1641 */     String sXML = AssetUtil.getAssetsAsXML(a_rs.getAssets(), bGetAllAttributes, a_rs.getCaptionIds(), a_rs.getIncludeLabels(), null, false, false, null, hmSubFiles, hmSubThumbnailFiles, a_request, a_rs.getCaptionAttributeId(), a_rs.getCreditAttributeId());
/* 1642 */     String sXSLTFilename = AssetBankSettings.getSearchResultsTransformationDirectory() + "/" + "slideshow.xslt";
/* 1643 */     sXSLTFilename = GlobalSettings.getApplicationPath() + "/" + sXSLTFilename;
/* 1644 */     File xsltFile = new File(sXSLTFilename);
/*      */ 
/* 1647 */     String sSlideshowXML = XMLUtil.transformXML(sXML, xsltFile);
/*      */ 
/* 1650 */     sSlideshowXML = sSlideshowXML.replaceFirst("<gallery>", "<gallery slideshowId='" + a_rs.getId() + "' baseUrl='" + ServletUtil.getApplicationUrl(a_request) + "/'>");
/*      */ 
/* 1652 */     String sFilename = "slideshow.xml";
/* 1653 */     String sXMLUrl = sPath + "/" + sFilename;
/* 1654 */     File sXMLFile = new File(sXMLUrl);
/* 1655 */     BufferedWriter out = new BufferedWriter(new FileWriter(sXMLFile));
/* 1656 */     out.write(sSlideshowXML);
/* 1657 */     out.close();
/*      */ 
/* 1660 */     a_rs.setInfoBar((a_rs.getCaptionIds() != null) && (a_rs.getCaptionIds().size() > 0));
/* 1661 */     a_rs.setUrl(sDir + "/" + sFilename);
/*      */   }
/*      */ 
/*      */   public void refreshRepurposedSlideshows(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1672 */     DBTransaction transaction = a_transaction;
/* 1673 */     String ksMethodName = "refreshRepurposedSlideshows";
/*      */     try
/*      */     {
/* 1677 */       if (transaction == null)
/*      */       {
/* 1679 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/* 1683 */       ArrayList<RepurposedSlideshow> alSlideshows = getRepurposedSlideshows(transaction, true, false, null);
/*      */ 
/* 1685 */       if ((alSlideshows != null) && (alSlideshows.size() > 0))
/*      */       {
/* 1688 */         for (RepurposedSlideshow rs : alSlideshows)
/*      */         {
/* 1691 */           Vector vecAssets = SearchUtil.getAssetsFromSearchCriteria(rs.getSearchCriteria(), rs.getLanguageCode());
/* 1692 */           rs.setAssets(vecAssets);
/*      */ 
/* 1695 */           if (rs.getIntJpgConversionQuality() > 0)
/*      */           {
/* 1698 */             ImageConversionInfo conversionInfo = RepurposingUtil.getSlideshowConversion(rs.getImageWidth(), rs.getImageHeight(), rs.getIntJpgConversionQuality() / 100.0F, rs.getMaintainAspectRatio());
/* 1699 */             rs.setConversionInfo(conversionInfo);
/*      */           }
/*      */ 
/* 1703 */           regenerateSlideshowFiles(rs, "jpg", null);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1709 */       if ((transaction != null) && (a_transaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 1713 */           transaction.rollback(); } catch (Exception ex) {
/*      */         }
/*      */       }
/* 1716 */       String sMessage = "AssetRepurposingManager.refreshRepurposedSlideshows: Error refreshing slideshows: ";
/* 1717 */       this.m_logger.error(sMessage, e);
/* 1718 */       throw new Bn2Exception(sMessage, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1722 */       if ((transaction != null) && (a_transaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 1726 */           transaction.commit();
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void performDeletion(DBTransaction a_transaction, long a_lAssetId, long a_lRepurposedAssetId)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1739 */     Connection con = a_transaction.getConnection();
/* 1740 */     String sIdCheck = "AssetId";
/* 1741 */     String sIds = "";
/* 1742 */     long lIdValue = a_lAssetId;
/* 1743 */     String sSql = null;
/* 1744 */     PreparedStatement psql = null;
/*      */ 
/* 1746 */     if ((a_lAssetId <= 0L) && (a_lRepurposedAssetId > 0L))
/*      */     {
/* 1748 */       sIdCheck = "RepurposedVersionId";
/* 1749 */       lIdValue = a_lRepurposedAssetId;
/* 1750 */       sIds = String.valueOf(a_lRepurposedAssetId);
/*      */     }
/*      */     else
/*      */     {
/* 1755 */       sSql = "SELECT RepurposedVersionId FROM RepurposedAsset WHERE AssetId=?";
/* 1756 */       psql = con.prepareStatement(sSql);
/* 1757 */       psql.setLong(1, lIdValue);
/* 1758 */       ResultSet rs = psql.executeQuery();
/* 1759 */       while (rs.next())
/*      */       {
/* 1761 */         sIds = sIds + rs.getLong("RepurposedVersionId");
/* 1762 */         sIds = sIds + ",";
/*      */       }
/* 1764 */       sIds = sIds.substring(0, sIds.length() - 1);
/* 1765 */       psql.close();
/*      */     }
/*      */ 
/* 1769 */     sSql = "DELETE FROM RepurposedAsset WHERE " + sIdCheck + "=?";
/* 1770 */     psql = con.prepareStatement(sSql);
/* 1771 */     psql.setLong(1, lIdValue);
/* 1772 */     psql.executeUpdate();
/* 1773 */     psql.close();
/*      */ 
/* 1776 */     if (StringUtil.stringIsPopulated(sIds))
/*      */     {
/* 1778 */       sSql = "DELETE FROM RepurposedVersion WHERE Id IN (" + sIds + ")";
/* 1779 */       psql = con.prepareStatement(sSql);
/* 1780 */       psql.executeUpdate();
/* 1781 */       psql.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void populateRepurposedVersion(RepurposedVersion a_version, ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 1793 */     a_version.setId(a_rs.getLong("raId"));
/* 1794 */     a_version.setUrl(a_rs.getString("raUrl"));
/* 1795 */     a_version.setHeight(a_rs.getString("raHeight"));
/* 1796 */     a_version.setWidth(a_rs.getString("raWidth"));
/* 1797 */     a_version.setCreatedDate(a_rs.getTimestamp("raCreatedDate"));
/*      */ 
/* 1799 */     if (a_rs.getLong("raCreatedByUserId") > 0L)
/*      */     {
/* 1801 */       ABUser user = new ABUser(a_rs.getLong("raCreatedByUserId"));
/* 1802 */       user.setForename(a_rs.getString("uForename"));
/* 1803 */       user.setSurname(a_rs.getString("uSurname"));
/* 1804 */       user.setUsername(a_rs.getString("uUsername"));
/* 1805 */       user.setEmailAddress(a_rs.getString("uEmailAddress"));
/* 1806 */       a_version.setCreatedByUser(user);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void populateRepurposedAsset(DBTransaction a_transaction, RepurposedAsset a_repAsset, ResultSet a_rs, String a_sBaseUrl)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1813 */     populateRepurposedAsset(a_transaction, a_repAsset, a_rs, a_sBaseUrl, -1, -1);
/*      */   }
/*      */ 
/*      */   private void populateRepurposedAsset(DBTransaction a_transaction, RepurposedAsset a_repAsset, ResultSet a_rs, String a_sBaseUrl, int a_iHeight, int a_iWidth)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1820 */     populateRepurposedVersion(a_repAsset, a_rs);
/*      */ 
/* 1823 */     long lDuration = a_rs.getLong("raDuration");
/* 1824 */     a_repAsset.setAssetId(a_rs.getLong("raAssetId"));
/* 1825 */     a_repAsset.setFileFormatId(a_rs.getLong("raFileFormatId"));
/* 1826 */     a_repAsset.setDuration(lDuration);
/* 1827 */     a_repAsset.setPreviewFileLocation(a_rs.getString("PreviewFileLocation"));
/*      */ 
/* 1832 */     if (a_iHeight > 0)
/*      */     {
/* 1834 */       a_repAsset.setHeight(String.valueOf(a_iHeight));
/*      */     }
/* 1836 */     if (a_iWidth > 0)
/*      */     {
/* 1838 */       a_repAsset.setWidth(String.valueOf(a_iWidth));
/*      */     }
/*      */ 
/* 1841 */     String sHtmlSnippet = getHtmlSnippetForAsset(a_transaction, a_rs);
/* 1842 */     populateEmbeddableHtmlForAsset(a_sBaseUrl, a_repAsset, sHtmlSnippet, a_rs.getLong("aAssetTypeId"));
/*      */   }
/*      */ 
/*      */   private void populateRepurposedSlideshow(DBTransaction a_transaction, RepurposedSlideshow a_repSlideshow, ResultSet a_rs, String a_sBaseUrl)
/*      */     throws SQLException, Bn2Exception, IOException, ClassNotFoundException
/*      */   {
/* 1851 */     populateRepurposedVersion(a_repSlideshow, a_rs);
/*      */ 
/* 1854 */     a_repSlideshow.setDisplayTime(a_rs.getInt("DisplayTime"));
/* 1855 */     a_repSlideshow.setInfoBar(a_rs.getBoolean("InfoBar"));
/* 1856 */     a_repSlideshow.setIncludeLabels(a_rs.getBoolean("IncludeLabels"));
/* 1857 */     a_repSlideshow.setMaintainAspectRatio(a_rs.getBoolean("MaintainAspectRatio"));
/* 1858 */     String sCaptionIds = a_rs.getString("CaptionIds");
/* 1859 */     if (StringUtil.stringIsPopulated(sCaptionIds))
/*      */     {
/* 1861 */       a_repSlideshow.setCaptionIds(StringUtil.convertToList(sCaptionIds, ","));
/*      */     }
/* 1863 */     a_repSlideshow.setIntJpgConversionQuality(a_rs.getInt("JpgConversionQuality"));
/* 1864 */     a_repSlideshow.setLanguageCode(a_rs.getString("LanguageCode"));
/* 1865 */     a_repSlideshow.setImageHeight(a_rs.getInt("ImageHeight"));
/* 1866 */     a_repSlideshow.setImageWidth(a_rs.getInt("ImageWidth"));
/* 1867 */     a_repSlideshow.setDescription(a_rs.getString("Description"));
/*      */ 
/* 1869 */     a_repSlideshow.setShowInListOnHomepage(a_rs.getBoolean("ShowOnHomepage"));
/* 1870 */     a_repSlideshow.setDefaultOnHomepage(a_rs.getBoolean("IsDefaultOnHomepage"));
/* 1871 */     a_repSlideshow.setSequence(a_rs.getInt("SequenceNumber"));
/* 1872 */     a_repSlideshow.setDisplayTypeId(a_rs.getLong("SlideShowTypeId"));
/* 1873 */     a_repSlideshow.setDisplayType(a_rs.getString("SlideShowDisplayType"));
/*      */ 
/* 1875 */     a_repSlideshow.setId(a_rs.getLong("raId"));
/*      */ 
/* 1877 */     populateEmbeddableHtmlForSlideshow(a_transaction, a_repSlideshow, a_sBaseUrl + a_repSlideshow.getDirectory() + "/", a_sBaseUrl + "/" + "common" + "/", a_repSlideshow.getFilename());
/*      */ 
/* 1880 */     String sSearchCriteriaFile = a_rs.getString("SearchCriteriaFile");
/* 1881 */     if (StringUtil.stringIsPopulated(sSearchCriteriaFile))
/*      */     {
/* 1883 */       String sPath = this.m_fileStoreManager.getAbsolutePath(sSearchCriteriaFile);
/* 1884 */       BaseSearchQuery query = SearchUtil.getCriteria(sPath);
/* 1885 */       a_repSlideshow.setSearchCriteria(query);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void moveSlideshowOnHomepage(DBTransaction a_dbTransaction, long a_lSlideshowId, long a_lSlideshowInfrontOfId)
/*      */     throws Bn2Exception
/*      */   {
/* 1898 */     Connection con = null;
/* 1899 */     PreparedStatement psql1 = null;
/* 1900 */     String sSQL = null;
/* 1901 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1905 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1913 */       if (a_lSlideshowInfrontOfId <= 0L)
/*      */       {
/* 1915 */         int iMaxSequenceNumber = 0;
/*      */ 
/* 1917 */         sSQL = "SELECT MAX(SequenceNumber) as maxSequenceNumber FROM RepurposedSlideshow";
/* 1918 */         psql1 = con.prepareStatement(sSQL);
/* 1919 */         rs = psql1.executeQuery();
/*      */ 
/* 1921 */         if (rs.next())
/*      */         {
/* 1923 */           iMaxSequenceNumber = rs.getInt("maxSequenceNumber");
/*      */ 
/* 1925 */           int iNewSequenceNumber = iMaxSequenceNumber + 1;
/*      */ 
/* 1927 */           sSQL = "UPDATE RepurposedSlideshow SET SequenceNumber=? WHERE RepurposedVersionId=?";
/* 1928 */           PreparedStatement psql2 = con.prepareStatement(sSQL);
/* 1929 */           psql2.setInt(1, iNewSequenceNumber);
/* 1930 */           psql2.setLong(2, a_lSlideshowId);
/* 1931 */           psql2.executeUpdate();
/* 1932 */           psql2.close();
/*      */         }
/*      */ 
/* 1935 */         psql1.close();
/*      */       }
/*      */       else
/*      */       {
/* 1939 */         sSQL = "SELECT SequenceNumber FROM RepurposedSlideshow WHERE RepurposedVersionId=?";
/* 1940 */         psql1 = con.prepareStatement(sSQL);
/* 1941 */         psql1.setLong(1, a_lSlideshowInfrontOfId);
/* 1942 */         rs = psql1.executeQuery();
/*      */ 
/* 1944 */         if (rs.next())
/*      */         {
/* 1946 */           int iAssetInFrontOfSequenceNumberIncrement = rs.getInt("SequenceNumber");
/* 1947 */           int iAssetInFrontOfSequenceNumber = rs.getInt("SequenceNumber");
/*      */ 
/* 1951 */           sSQL = "SELECT RepurposedVersionId, SequenceNumber FROM RepurposedSlideshow WHERE SequenceNumber>=? ORDER BY SequenceNumber ASC";
/*      */ 
/* 1956 */           PreparedStatement psql = con.prepareStatement(sSQL);
/* 1957 */           psql.setInt(1, iAssetInFrontOfSequenceNumberIncrement);
/* 1958 */           rs = psql.executeQuery();
/*      */ 
/* 1961 */           while (rs.next())
/*      */           {
/* 1963 */             sSQL = "UPDATE RepurposedSlideshow SET SequenceNumber=? WHERE RepurposedVersionId=?";
/* 1964 */             PreparedStatement psql3 = con.prepareStatement(sSQL);
/* 1965 */             iAssetInFrontOfSequenceNumberIncrement++; psql3.setInt(1, iAssetInFrontOfSequenceNumberIncrement);
/* 1966 */             psql3.setLong(2, rs.getLong("RepurposedVersionId"));
/* 1967 */             psql3.executeUpdate();
/* 1968 */             psql3.close();
/*      */           }
/*      */ 
/* 1973 */           sSQL = "UPDATE RepurposedSlideshow SET SequenceNumber=? WHERE RepurposedVersionId=?";
/* 1974 */           PreparedStatement psql3 = con.prepareStatement(sSQL);
/* 1975 */           psql3.setInt(1, iAssetInFrontOfSequenceNumber);
/* 1976 */           psql3.setLong(2, a_lSlideshowId);
/* 1977 */           psql3.executeUpdate();
/* 1978 */           psql3.close();
/*      */ 
/* 1981 */           psql.close();
/*      */         }
/*      */ 
/* 1984 */         psql1.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1989 */       throw new Bn2Exception("SQL Exception whilst changing order of slideshows: ", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void warnOfImageRemoval(DBTransaction a_transaction, long a_lVersionId)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setListManager(ListManager a_manager)
/*      */   {
/* 2007 */     this.m_listManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_manager)
/*      */   {
/* 2012 */     this.m_transactionManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setEmailManager(EmailManager a_manager)
/*      */   {
/* 2017 */     this.m_emailManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager a_manager)
/*      */   {
/* 2022 */     this.m_fileStoreManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(AssetManager a_assetManager)
/*      */   {
/* 2027 */     this.m_assetManager = a_assetManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_scheduleManager)
/*      */   {
/* 2032 */     this.m_scheduleManager = a_scheduleManager;
/*      */   }
/*      */ 
/*      */   public void setUsageManager(UsageManager a_sUsageManager)
/*      */   {
/* 2037 */     this.m_usageManager = a_sUsageManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.service.AssetRepurposingManager
 * JD-Core Version:    0.6.0
 */