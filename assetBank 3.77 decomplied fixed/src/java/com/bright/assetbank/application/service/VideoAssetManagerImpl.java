/*      */ package com.bright.assetbank.application.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*      */ import com.bright.assetbank.application.bean.AssetFileSource;
/*      */ import com.bright.assetbank.application.bean.FileFormat;
/*      */ import com.bright.assetbank.application.bean.UploadedFileInfo;
/*      */ import com.bright.assetbank.application.bean.VideoAsset;
/*      */ import com.bright.assetbank.application.bean.VideoConversionInfo;
/*      */ import com.bright.assetbank.application.bean.VideoConversionResult;
/*      */ import com.bright.assetbank.application.bean.VideoInfo;
/*      */ import com.bright.assetbank.application.bean.VideoPreviewQueueItem;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.exception.AssetConversionFailedException;
/*      */ import com.bright.assetbank.application.exception.AssetFileReadException;
/*      */ import com.bright.assetbank.application.util.VideoUtil;
/*      */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*      */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.framework.common.service.RefDataManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.storage.constant.StoredFileType;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import com.bright.framework.util.commandline.CommandLineExec;
/*      */ import java.io.File;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class VideoAssetManagerImpl extends ImageAssetManagerImpl
/*      */ {
/*   85 */   protected RefDataManager m_refDataManager = null;
/*   86 */   protected DBTransactionManager m_transactionManager = null;
/*   87 */   protected VideoPreviewManager m_videoPreviewManager = null;
/*   88 */   protected HashMap<Long, VideoConversionResult> m_hmInProgressConversions = new HashMap();
/*   89 */   protected HashMap<Long, VideoConversionResult> m_hmCompletedConversions = new HashMap();
/*   90 */   protected HashMap<Long, Exception> m_hmConversionErrors = new HashMap();
/*   91 */   protected Object m_oKeyLock = new Object();
/*      */ 
/*      */   public long getAssetTypeId()
/*      */   {
/*  107 */     return 3L;
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  116 */     for (VideoAsset videoAsset : getAssetsRequiringPreviewGeneration())
/*      */     {
/*  119 */       String sourcePath = this.m_fileStoreManager.getAbsolutePath(videoAsset.getOriginalFileLocation());
/*  120 */       VideoInfo videoInfo = VideoUtil.getVideoInfo(sourcePath);
/*  121 */       int startPreviewAtFrame = 0;
/*      */ 
/*  123 */       VideoPreviewQueueItem videoPreviewQueueItem = new VideoPreviewQueueItem(videoAsset, videoInfo, sourcePath, startPreviewAtFrame);
/*      */ 
/*  129 */       this.m_videoPreviewManager.queueItem(videoPreviewQueueItem);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Asset saveAsset(DBTransaction a_dbTransaction, Asset a_asset, AssetFileSource a_source, long a_lUserId, AssetConversionInfo a_conversionInfo, AssetFileSource a_thumbnailSource, boolean a_bForceThumbnailRegeneration, boolean a_bForcePreviewRegeneration, int a_iSaveTypeId)
/*      */     throws Bn2Exception, AssetFileReadException
/*      */   {
/*  159 */     return saveAsset(a_dbTransaction, a_asset, a_source, a_lUserId, a_conversionInfo, a_thumbnailSource, a_bForceThumbnailRegeneration, a_bForcePreviewRegeneration, a_iSaveTypeId, false);
/*      */   }
/*      */ 
/*      */   public Asset saveAsset(DBTransaction a_dbTransaction, Asset a_asset, AssetFileSource a_source, long a_lUserId, AssetConversionInfo a_conversionInfo, AssetFileSource a_thumbnailSource, boolean a_bForceThumbnailRegeneration, boolean a_bForcePreviewRegeneration, int a_iSaveTypeId, boolean a_skipVideoThumbnails)
/*      */     throws Bn2Exception, AssetFileReadException
/*      */   {
/*  194 */     DBTransaction transaction = a_dbTransaction;
/*  195 */     VideoAsset video = null;
/*  196 */     boolean bIsNew = false;
/*      */ 
/*  198 */     if ((a_asset.getId() <= 0L) || ((a_source != null) && (a_source.getIsNewWithFixedId())))
/*      */     {
/*  200 */       bIsNew = true;
/*      */     }
/*      */ 
/*  204 */     if ((a_asset instanceof VideoAsset))
/*      */     {
/*  206 */       video = (VideoAsset)a_asset;
/*      */     }
/*      */     else
/*      */     {
/*  210 */       video = new VideoAsset(a_asset);
/*      */     }
/*      */ 
/*  213 */     boolean bRegeneratePreview = false;
/*  214 */     if ((a_source != null) && (a_source.isValid()))
/*      */     {
/*  216 */       bRegeneratePreview = true;
/*      */     }
/*      */ 
/*  220 */     if (!a_skipVideoThumbnails) {
/*  221 */       super.saveAsset(a_dbTransaction, video, a_source, a_lUserId, a_conversionInfo, a_thumbnailSource, a_bForceThumbnailRegeneration, a_bForcePreviewRegeneration, a_iSaveTypeId);
/*      */     }
/*      */ 
/*  234 */     int iStartFrame = 0;
/*  235 */     if ((a_conversionInfo instanceof VideoConversionInfo))
/*      */     {
/*  237 */       iStartFrame = ((VideoConversionInfo)a_conversionInfo).getPreviewStartFrame();
/*  238 */       bRegeneratePreview = true;
/*      */     }
/*  240 */     saveVideo(transaction, video, (bRegeneratePreview) || (a_bForcePreviewRegeneration), bIsNew, iStartFrame);
/*      */ 
/*  245 */     return video;
/*      */   }
/*      */ 
/*      */   public void saveVideo(DBTransaction a_dbTransaction, VideoAsset a_video, boolean a_bRegeneratePreview, boolean a_bIsNew, int a_iStartPreviewAtFrame)
/*      */     throws Bn2Exception
/*      */   {
/*  272 */     DBTransaction transaction = a_dbTransaction;
/*  273 */     String sourcePath = this.m_fileStoreManager.getAbsolutePath(a_video.getOriginalFileLocation());
/*  274 */     VideoInfo videoInfo = VideoUtil.getVideoInfo(sourcePath);
/*      */     try
/*      */     {
/*  278 */       if (a_bRegeneratePreview)
/*      */       {
/*  281 */         a_video.setPreviewClipBeingGenerated(true);
/*      */       }
/*      */ 
/*  284 */       if ((a_bIsNew) && (StringUtils.isNotEmpty(a_video.getFileLocation())))
/*      */       {
/*  287 */         a_video.setPreviewClipLocation(copyFile(a_video.getPreviewClipLocation(), StoredFileType.PREVIEW_OR_THUMBNAIL));
/*  288 */         a_video.setEmbeddedPreviewClipLocation(copyFile(a_video.getEmbeddedPreviewClipLocation(), StoredFileType.PREVIEW_OR_THUMBNAIL));
/*      */       }
/*      */ 
/*  292 */       if (a_bIsNew)
/*      */       {
/*      */         try
/*      */         {
/*  296 */           if (a_dbTransaction == null)
/*      */           {
/*  298 */             transaction = getTransactionManager().getNewTransaction();
/*      */           }
/*      */ 
/*  301 */           Connection con = transaction.getConnection();
/*  302 */           String sSQL = null;
/*      */ 
/*  304 */           sSQL = "INSERT INTO VideoAsset (PreviewClipLocation, EmbeddedPreviewClipLocation, Duration, Width, Height, PAR, PreviewClipBeingGenerated, AssetId)  VALUES (?,?,?,?,?,?,?,?) ";
/*      */ 
/*  308 */           int iCol = 1;
/*  309 */           PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  312 */           psql.setString(iCol++, a_video.getPreviewClipLocation());
/*  313 */           psql.setString(iCol++, a_video.getEmbeddedPreviewClipLocation());
/*  314 */           DBUtil.setFieldLongOrNull(psql, iCol++, videoInfo.getDuration());
/*  315 */           DBUtil.setFieldIntOrNull(psql, iCol++, videoInfo.getWidth());
/*  316 */           DBUtil.setFieldIntOrNull(psql, iCol++, videoInfo.getHeight());
/*  317 */           psql.setFloat(iCol++, videoInfo.getPAR());
/*  318 */           psql.setBoolean(iCol++, a_video.getPreviewClipBeingGenerated());
/*  319 */           psql.setLong(iCol++, a_video.getId());
/*      */ 
/*  321 */           psql.executeUpdate();
/*  322 */           psql.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  327 */           if ((a_dbTransaction == null) && (transaction != null))
/*      */           {
/*      */             try
/*      */             {
/*  331 */               transaction.rollback();
/*      */             }
/*      */             catch (SQLException sqle)
/*      */             {
/*  335 */               this.m_logger.error("Exception whilst trying to roll back connection " + sqle.getMessage());
/*      */             }
/*      */           }
/*      */ 
/*  339 */           this.m_logger.error("Exception whilst saving video : " + e);
/*  340 */           throw new Bn2Exception("Exception whilst saving video : ", e);
/*      */         }
/*      */         finally
/*      */         {
/*  345 */           if ((a_dbTransaction == null) && (transaction != null))
/*      */           {
/*      */             try
/*      */             {
/*  349 */               transaction.commit();
/*      */             }
/*      */             catch (SQLException sqle)
/*      */             {
/*  353 */               this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*  362 */       if (a_bIsNew)
/*      */       {
/*  364 */         deleteAsset(a_dbTransaction, a_video.getId());
/*  365 */         throw new Bn2Exception("Error in VideoAssetManagerImpl.saveAsset:", t);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  370 */     if (a_bRegeneratePreview)
/*      */     {
/*  372 */       VideoPreviewQueueItem videoPreviewQueueItem = new VideoPreviewQueueItem(a_video, videoInfo, sourcePath, a_iStartPreviewAtFrame);
/*      */ 
/*  376 */       this.m_videoPreviewManager.queueItem(videoPreviewQueueItem);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lId, Asset a_asset, Vector a_vVisibleAttributeIds, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  401 */     return getAsset(a_dbTransaction, a_lId, a_asset, a_vVisibleAttributeIds, LanguageConstants.k_defaultLanguage, a_bGetFeedback);
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lId, Asset a_asset, Vector a_vVisibleAttributeIds, Language a_language, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  432 */     VideoAsset video = null;
/*  433 */     Connection con = null;
/*  434 */     ResultSet rs = null;
/*  435 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  437 */     if (transaction == null)
/*      */     {
/*  439 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*  442 */     if (a_asset == null)
/*      */     {
/*  444 */       video = new VideoAsset();
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*  450 */         video = (VideoAsset)a_asset;
/*      */       }
/*      */       catch (ClassCastException cce)
/*      */       {
/*  454 */         throw new Bn2Exception("VideoAssetManagerImpl.getAsset() : Passed asset object is not of type VideoAsset", cce);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  459 */     video = (VideoAsset)super.getAsset(a_dbTransaction, a_lId, video, a_vVisibleAttributeIds, a_language, a_bGetFeedback);
/*      */     try
/*      */     {
/*  463 */       con = transaction.getConnection();
/*      */ 
/*  465 */       String sSQL = "SELECT PreviewClipLocation, EmbeddedPreviewClipLocation, Duration, Width, Height, PAR, PreviewClipBeingGenerated FROM VideoAsset WHERE AssetId = ?";
/*      */ 
/*  469 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  470 */       psql.setLong(1, a_lId);
/*      */ 
/*  472 */       rs = psql.executeQuery();
/*      */ 
/*  474 */       if (rs.next())
/*      */       {
/*  476 */         video.setPreviewClipLocation(rs.getString("PreviewClipLocation"));
/*  477 */         video.setEmbeddedPreviewClipLocation(rs.getString("EmbeddedPreviewClipLocation"));
/*  478 */         video.setDuration(rs.getLong("Duration"));
/*  479 */         video.setPAR(rs.getFloat("PAR"));
/*  480 */         video.setPreviewClipBeingGenerated(rs.getBoolean("PreviewClipBeingGenerated"));
/*      */ 
/*  482 */         if (rs.getInt("Width") > 0)
/*      */         {
/*  484 */           video.setWidth(rs.getInt("Width"));
/*      */         }
/*  486 */         if (rs.getInt("Height") > 0)
/*      */         {
/*  488 */           video.setHeight(rs.getInt("Height"));
/*      */         }
/*      */       }
/*      */ 
/*  492 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  496 */       this.m_logger.error("SQL Exception whilst getting an video asset from the database : " + e);
/*  497 */       throw new Bn2Exception("SQL Exception whilst getting an video asset from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  502 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  506 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  510 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  515 */     return video;
/*      */   }
/*      */ 
/*      */   public List<VideoAsset> getAssetsRequiringPreviewGeneration()
/*      */     throws Bn2Exception
/*      */   {
/*  530 */     DBTransaction transaction = getTransactionManager().getNewTransaction();
/*  531 */     List videoAssets = new ArrayList();
/*      */     try
/*      */     {
/*  538 */       Connection con = transaction.getConnection();
/*      */ 
/*  540 */       String sSQL = "SELECT AssetId, PreviewClipLocation, EmbeddedPreviewClipLocation, Duration, Width, Height, PAR FROM VideoAsset WHERE PreviewClipBeingGenerated = 1";
/*      */ 
/*  544 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  546 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  548 */       while (rs.next())
/*      */       {
/*  550 */         Long assetId = Long.valueOf(rs.getLong("AssetId"));
/*      */ 
/*  552 */         VideoAsset videoAsset = new VideoAsset();
/*  553 */         videoAsset.setId(assetId.longValue());
/*  554 */         super.getAsset(transaction, assetId.longValue(), videoAsset, null, LanguageConstants.k_defaultLanguage, false);
/*      */ 
/*  556 */         videoAsset.setPreviewClipLocation(rs.getString("PreviewClipLocation"));
/*  557 */         videoAsset.setEmbeddedPreviewClipLocation(rs.getString("EmbeddedPreviewClipLocation"));
/*  558 */         videoAsset.setDuration(rs.getLong("Duration"));
/*  559 */         videoAsset.setPAR(rs.getFloat("PAR"));
/*  560 */         videoAsset.setPreviewClipBeingGenerated(true);
/*      */ 
/*  562 */         if (rs.getInt("Width") > 0)
/*      */         {
/*  564 */           videoAsset.setWidth(rs.getInt("Width"));
/*      */         }
/*  566 */         if (rs.getInt("Height") > 0)
/*      */         {
/*  568 */           videoAsset.setHeight(rs.getInt("Height"));
/*      */         }
/*      */ 
/*  571 */         videoAssets.add(videoAsset);
/*      */       }
/*      */ 
/*  574 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  578 */       this.m_logger.error("SQL Exception whilst getting an video asset from the database : " + e);
/*  579 */       throw new Bn2Exception("SQL Exception whilst getting an video asset from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  584 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  588 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  592 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  597 */     return videoAssets;
/*      */   }
/*      */ 
/*      */   public void deleteAsset(DBTransaction a_dbTransaction, long a_lId, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/*  620 */     VideoAsset video = null;
/*  621 */     Connection con = null;
/*  622 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  624 */     if (transaction == null)
/*      */     {
/*  626 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*  629 */     video = (VideoAsset)getAsset(a_dbTransaction, a_lId, null, null, false);
/*      */     try
/*      */     {
/*  633 */       con = transaction.getConnection();
/*      */ 
/*  636 */       if (AssetBankSettings.getAssetRepurposingEnabled())
/*      */       {
/*  638 */         this.m_assetRepurposingManager.removeRepurposedImages(transaction, video.getId());
/*      */       }
/*      */ 
/*  641 */       String sSQL = "DELETE FROM VideoAsset WHERE AssetId=?";
/*  642 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  643 */       psql.setLong(1, a_lId);
/*      */ 
/*  645 */       psql.executeUpdate();
/*  646 */       psql.close();
/*      */ 
/*  649 */       super.deleteAsset(transaction, a_lId, video);
/*      */ 
/*  654 */       if ((video.getPreviewClipLocation() != null) && (video.getPreviewClipLocation().length() > 0))
/*      */       {
/*  656 */         this.m_fileStoreManager.deleteFile(video.getPreviewClipLocation());
/*      */       }
/*      */ 
/*  659 */       if ((video.getEmbeddedPreviewClipLocation() != null) && (video.getEmbeddedPreviewClipLocation().length() > 0))
/*      */       {
/*  661 */         this.m_fileStoreManager.deleteFile(video.getEmbeddedPreviewClipLocation());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  667 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  671 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  675 */           this.m_logger.error("SQL Exception whilst trying to roll back transaction " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  679 */       this.m_logger.error("Exception whilst deleting image from the database : " + e);
/*  680 */       throw new Bn2Exception("Exception whilst deleting image from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  685 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  689 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  693 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<FileFormat> getDownloadableVideoFormats()
/*      */     throws Bn2Exception
/*      */   {
/*  713 */     return getSupportedVideoFormats(null, true);
/*      */   }
/*      */ 
/*      */   public Vector<FileFormat> getSupportedVideoFormats(DBTransaction a_dbTransaction, boolean a_bWritableOnly)
/*      */     throws Bn2Exception
/*      */   {
/*  733 */     return super.getSupportedFileFormats(a_dbTransaction, 3L, a_bWritableOnly);
/*      */   }
/*      */ 
/*      */   public String getDownloadableAssetPath(Asset a_asset, String a_sConvertToFileExtension, AssetConversionInfo a_conversionInfo)
/*      */     throws Bn2Exception
/*      */   {
/*  760 */     String sPath = null;
/*      */ 
/*  763 */     if ((a_asset == null) || (!(a_asset instanceof VideoAsset)))
/*      */     {
/*  765 */       throw new Bn2Exception("VideoAssetManagerImpl.getDownloadableAssetPath() : Passed asset object is not a valid VideoAsset");
/*      */     }
/*      */ 
/*  768 */     VideoConversionInfo converstionInfo = (VideoConversionInfo)a_conversionInfo;
/*      */ 
/*  771 */     boolean bDownloadOriginal = false;
/*      */ 
/*  774 */     if ((a_sConvertToFileExtension == null) || ((converstionInfo.getUseOriginal()) && (!converstionInfo.getAddWatermark())))
/*      */     {
/*  778 */       bDownloadOriginal = true;
/*      */     }
/*      */ 
/*  781 */     if (bDownloadOriginal)
/*      */     {
/*  784 */       if ((a_asset.getOriginalFileLocation() != null) && (a_asset.getOriginalFileLocation().length() > 0))
/*      */       {
/*  786 */         sPath = a_asset.getOriginalFileLocation();
/*      */       }
/*      */       else
/*      */       {
/*  790 */         sPath = a_asset.getFileLocation();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  795 */       converstionInfo.setUseOriginal(false);
/*      */ 
/*  798 */       sPath = prepareVideoForDownload((VideoAsset)a_asset, a_sConvertToFileExtension, converstionInfo);
/*      */     }
/*      */ 
/*  803 */     return sPath;
/*      */   }
/*      */ 
/*      */   public String prepareVideoForDownload(VideoAsset a_asset, String a_sConvertToFileExtension, VideoConversionInfo a_conversionOptions)
/*      */     throws Bn2Exception
/*      */   {
/*  829 */     String sExtension = ".";
/*      */ 
/*  831 */     if (a_conversionOptions.getPreview())
/*      */     {
/*  833 */       sExtension = "-vp.";
/*      */     }
/*      */ 
/*  836 */     sExtension = sExtension + a_sConvertToFileExtension;
/*  837 */     String sConvertedFilename = a_asset.getOriginalFileLocation().substring(a_asset.getOriginalFileLocation().lastIndexOf("/"), a_asset.getOriginalFileLocation().lastIndexOf("."));
/*  838 */     sConvertedFilename = sConvertedFilename + sExtension;
/*      */ 
/*  841 */     sConvertedFilename = this.m_fileStoreManager.getUniqueFilepath(sConvertedFilename, StoredFileType.TEMP);
/*      */ 
/*  844 */     String sSourcePath = this.m_fileStoreManager.getAbsolutePath(a_asset.getOriginalFileLocation());
/*  845 */     String sDestPath = this.m_fileStoreManager.getAbsolutePath(sConvertedFilename);
/*      */ 
/*  848 */     Vector vecCommand = new Vector();
/*  849 */     Vector vecTempCommand = null;
/*  850 */     vecCommand.add(ConverterSettings.getFfmpegPath());
/*      */ 
/*  852 */     if ((a_conversionOptions.getStartOffset() > 0.0D) && (sSourcePath.endsWith(".wmv")))
/*      */     {
/*  856 */       String sWavFilename = a_asset.getOriginalFileLocation().substring(a_asset.getOriginalFileLocation().lastIndexOf("/"), a_asset.getOriginalFileLocation().lastIndexOf("."));
/*  857 */       sWavFilename = sWavFilename + "." + "wav";
/*  858 */       sWavFilename = this.m_fileStoreManager.getUniqueFilepath(sWavFilename, StoredFileType.TEMP);
/*  859 */       String sWavPath = this.m_fileStoreManager.getAbsolutePath(sWavFilename);
/*  860 */       vecTempCommand = new Vector();
/*  861 */       vecTempCommand.add(ConverterSettings.getFfmpegPath());
/*  862 */       vecTempCommand.add("-i");
/*  863 */       vecTempCommand.add(sSourcePath);
/*  864 */       vecTempCommand.add("-y");
/*  865 */       vecTempCommand.add(sWavPath);
/*      */ 
/*  867 */       vecCommand.add("-ss");
/*  868 */       vecCommand.add(String.valueOf(a_conversionOptions.getStartOffset()));
/*      */ 
/*  871 */       vecCommand.add("-i");
/*  872 */       vecCommand.add(sWavPath);
/*      */ 
/*  874 */       vecCommand.add("-ss");
/*  875 */       vecCommand.add(String.valueOf(a_conversionOptions.getStartOffset()));
/*      */     }
/*      */ 
/*  878 */     if ((StringUtils.isNotBlank(AssetBankSettings.getWmvAudioCodec())) && (sDestPath.endsWith(".wmv")))
/*      */     {
/*  880 */       vecCommand.add("-acodec");
/*  881 */       vecCommand.add(AssetBankSettings.getWmvAudioCodec());
/*      */     }
/*      */ 
/*  885 */     vecCommand.add("-i");
/*  886 */     vecCommand.add(sSourcePath);
/*      */ 
/*  889 */     if ((a_conversionOptions.getMaxWidth() > 0) && (a_conversionOptions.getMaxHeight() > 0))
/*      */     {
/*  891 */       vecCommand.add("-s");
/*  892 */       vecCommand.add(a_conversionOptions.getMaxWidth() + "x" + a_conversionOptions.getMaxHeight());
/*      */     }
/*      */ 
/*  896 */     if (a_conversionOptions.getDuration() > 0.0D)
/*      */     {
/*  898 */       vecCommand.add("-t");
/*  899 */       vecCommand.add(String.valueOf(a_conversionOptions.getDuration()));
/*      */     }
/*      */ 
/*  902 */     if (a_conversionOptions.getFrameRate() > 0)
/*      */     {
/*  904 */       vecCommand.add("-r");
/*  905 */       vecCommand.add(String.valueOf(a_conversionOptions.getFrameRate()));
/*      */     }
/*  907 */     if ((a_conversionOptions.getStartOffset() > 0.0D) && (!sSourcePath.endsWith(".wmv")))
/*      */     {
/*  909 */       vecCommand.add("-ss");
/*  910 */       vecCommand.add(String.valueOf(a_conversionOptions.getStartOffset()));
/*      */     }
/*      */ 
/*  918 */     if (StringUtil.stringIsPopulated(a_conversionOptions.getVideoBitrate()))
/*      */     {
/*  920 */       vecCommand.add("-b");
/*  921 */       vecCommand.add(a_conversionOptions.getVideoBitrate());
/*      */     }
/*      */ 
/*  924 */     if (a_conversionOptions.getAudioBitrate() > 0L)
/*      */     {
/*  926 */       vecCommand.add("-ab");
/*  927 */       vecCommand.add(String.valueOf(a_conversionOptions.getAudioBitrate()));
/*      */     }
/*      */ 
/*  931 */     vecCommand.add("-y");
/*      */ 
/*  934 */     vecCommand.add(sDestPath);
/*      */ 
/*  936 */     if (a_conversionOptions.getPreview())
/*      */     {
/*  938 */       if (vecTempCommand != null)
/*      */       {
/*  940 */         runVideoConversion(vecTempCommand, -1L);
/*      */       }
/*  942 */       runVideoConversion(vecCommand, -1L);
/*      */     }
/*      */     else
/*      */     {
/*  947 */       runAsynchronousVideoConversion(vecCommand, vecTempCommand, sConvertedFilename, a_conversionOptions, a_asset);
/*      */     }
/*      */ 
/*  951 */     return sConvertedFilename;
/*      */   }
/*      */ 
/*      */   private void runAsynchronousVideoConversion(Vector vecCommand, Vector vecFirstStepCommand, String a_sConvertedFilename, VideoConversionInfo a_conversionOptions, VideoAsset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/*  964 */     String[] saCommand = new String[vecCommand.size()];
/*  965 */     for (int i = 0; i < vecCommand.size(); i++)
/*      */     {
/*  967 */       saCommand[i] = ((String)vecCommand.elementAt(i));
/*      */     }
/*      */ 
/*  970 */     String[] saFirstStepCommand = null;
/*  971 */     if (vecFirstStepCommand != null)
/*      */     {
/*  973 */       saFirstStepCommand = new String[vecFirstStepCommand.size()];
/*      */ 
/*  975 */       for (int i = 0; i < vecFirstStepCommand.size(); i++)
/*      */       {
/*  977 */         saFirstStepCommand[i] = ((String)vecFirstStepCommand.elementAt(i));
/*      */       }
/*      */     }
/*      */ 
/*  981 */     runAsynchronousVideoConversion(saCommand, saFirstStepCommand, a_sConvertedFilename, a_conversionOptions, a_asset);
/*      */   }
/*      */ 
/*      */   private void runAsynchronousVideoConversion(final String[] a_saCommand, final String[] a_saFirstStepCommand, String a_sConvertedFilename, VideoConversionInfo a_conversionOptions, VideoAsset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/*  994 */     VideoConversionResult result = new VideoConversionResult();
/*  995 */     result.setConvertedFilename(a_sConvertedFilename);
/*  996 */     result.setUsedOriginal(a_conversionOptions.getUseOriginal());
/*  997 */     result.setCompress(a_conversionOptions.getCompressFile());
/*  998 */     result.setConvertedAsset(a_asset);
/*      */ 
/* 1001 */     final long lDownloadId = getUniqueDownloadId();
/* 1002 */     a_conversionOptions.getUserProfile().setDownloadId(lDownloadId);
/* 1003 */     this.m_hmInProgressConversions.put(new Long(lDownloadId), result);
/*      */ 
/* 1005 */     Thread runner = new Thread(new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/*      */         try
/*      */         {
/* 1011 */           if (a_saFirstStepCommand != null)
/*      */           {
/* 1013 */             VideoAssetManagerImpl.this.runVideoConversion(a_saFirstStepCommand, lDownloadId, false);
/*      */           }
/* 1015 */           VideoAssetManagerImpl.this.runVideoConversion(a_saCommand, lDownloadId, true);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1019 */           VideoAssetManagerImpl.this.m_logger.error("VideoAssetManagerImpl.runAsynchronousVideoConversion() : Exception caught : " + e, e);
/*      */         }
/*      */       }
/*      */     });
/* 1025 */     runner.start();
/*      */   }
/*      */ 
/*      */   private void runVideoConversion(Vector vecCommand, long a_lDownloadId)
/*      */   {
/* 1036 */     String[] saCommand = new String[vecCommand.size()];
/* 1037 */     for (int i = 0; i < vecCommand.size(); i++)
/*      */     {
/* 1039 */       saCommand[i] = ((String)vecCommand.elementAt(i));
/*      */     }
/* 1041 */     runVideoConversion(saCommand, a_lDownloadId, true);
/*      */   }
/*      */ 
/*      */   private void runVideoConversion(String[] a_saCommand, long a_lDownloadId, boolean a_bClearProgress)
/*      */   {
/*      */     try
/*      */     {
/*      */       try
/*      */       {
/* 1057 */         CommandLineExec.execute(a_saCommand);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1062 */         if ((!e.getMessage().contains("output buffer too small")) || (!e.getMessage().contains("Audio encoding failed")))
/*      */         {
/* 1065 */           throw e;
/*      */         }
/*      */       }
/*      */ 
/* 1069 */       if (a_lDownloadId > 0L)
/*      */       {
/* 1072 */         VideoConversionResult result = (VideoConversionResult)this.m_hmInProgressConversions.get(new Long(a_lDownloadId));
/* 1073 */         if (a_bClearProgress)
/*      */         {
/* 1075 */           this.m_hmInProgressConversions.remove(new Long(a_lDownloadId));
/* 1076 */           this.m_hmCompletedConversions.put(new Long(a_lDownloadId), result);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1083 */       this.m_logger.error("VideoAssetManagerImpl.runVideoConversion: Could not create converted file for video: " + e.getMessage());
/*      */ 
/* 1085 */       if (a_lDownloadId > 0L)
/*      */       {
/* 1087 */         this.m_hmInProgressConversions.remove(new Long(a_lDownloadId));
/* 1088 */         this.m_hmCompletedConversions.remove(new Long(a_lDownloadId));
/* 1089 */         this.m_hmConversionErrors.put(new Long(a_lDownloadId), e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getVideoConversionStatus(long a_lDownloadId)
/*      */     throws AssetConversionFailedException
/*      */   {
/* 1104 */     if (this.m_hmInProgressConversions.containsKey(new Long(a_lDownloadId)))
/*      */     {
/* 1106 */       return 1;
/*      */     }
/* 1108 */     if (this.m_hmCompletedConversions.containsKey(new Long(a_lDownloadId)))
/*      */     {
/* 1110 */       return 2;
/*      */     }
/* 1112 */     if (this.m_hmConversionErrors.containsKey(new Long(a_lDownloadId)))
/*      */     {
/* 1114 */       Exception e = (Exception)this.m_hmConversionErrors.get(new Long(a_lDownloadId));
/* 1115 */       this.m_hmConversionErrors.remove(new Long(a_lDownloadId));
/* 1116 */       throw new AssetConversionFailedException(e);
/*      */     }
/*      */ 
/* 1120 */     return 0;
/*      */   }
/*      */ 
/*      */   public VideoConversionResult getCompletedVideoConversionDetails(long a_lDownloadId)
/*      */   {
/* 1133 */     if (this.m_hmCompletedConversions.containsKey(new Long(a_lDownloadId)))
/*      */     {
/* 1135 */       VideoConversionResult result = (VideoConversionResult)this.m_hmCompletedConversions.get(new Long(a_lDownloadId));
/* 1136 */       this.m_hmCompletedConversions.remove(new Long(a_lDownloadId));
/* 1137 */       return result;
/*      */     }
/*      */ 
/* 1140 */     return null;
/*      */   }
/*      */ 
/*      */   public long getCurrentlyConvertingFileSize(long a_lDownloadId)
/*      */     throws Bn2Exception
/*      */   {
/* 1153 */     if (this.m_hmInProgressConversions.containsKey(new Long(a_lDownloadId)))
/*      */     {
/* 1155 */       VideoConversionResult result = (VideoConversionResult)this.m_hmInProgressConversions.get(new Long(a_lDownloadId));
/*      */ 
/* 1158 */       String sFullPath = this.m_fileStoreManager.getAbsolutePath(result.getConvertedFilename());
/* 1159 */       File file = new File(sFullPath);
/*      */ 
/* 1161 */       return file.length();
/*      */     }
/*      */ 
/* 1164 */     return 0L;
/*      */   }
/*      */ 
/*      */   public long getUniqueDownloadId()
/*      */     throws Bn2Exception
/*      */   {
/* 1183 */     synchronized (this.m_oKeyLock)
/*      */     {
/* 1186 */       DBTransaction transaction = null;
/* 1187 */       long lId = -1L;
/*      */       try
/*      */       {
/* 1191 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 1194 */         lId = this.m_refDataManager.getSystemSettingAsLong("DownloadId");
/* 1195 */         lId += 1L;
/* 1196 */         this.m_refDataManager.updateSystemSettingAsLong(transaction, "DownloadId", lId);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1201 */         if (transaction != null)
/*      */         {
/*      */           try
/*      */           {
/* 1205 */             transaction.rollback();
/*      */           }
/*      */           catch (SQLException ex)
/*      */           {
/*      */           }
/*      */         }
/*      */ 
/* 1212 */         this.m_logger.error("VideoAssetManagerImpl.getUniqueDownloadId: " + e.getMessage());
/* 1213 */         throw new Bn2Exception("VideoAssetManagerImpl.getUniqueDownloadId: " + e.getMessage(), e);
/*      */       }
/*      */       finally
/*      */       {
/* 1217 */         if (transaction != null)
/*      */         {
/*      */           try
/*      */           {
/* 1221 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException ex)
/*      */           {
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1230 */       return lId;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected UploadedFileInfo getAdditionalFileInfo(UploadedFileInfo a_sFileInfo, String a_sLocation)
/*      */   {
/* 1248 */     return a_sFileInfo;
/*      */   }
/*      */ 
/*      */   public void setRefDataManager(RefDataManager a_refDataManager)
/*      */   {
/* 1253 */     this.m_refDataManager = a_refDataManager;
/*      */   }
/*      */ 
/*      */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/* 1258 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setVideoPreviewManager(VideoPreviewManager a_videoPreviewManager)
/*      */   {
/* 1263 */     this.m_videoPreviewManager = a_videoPreviewManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.VideoAssetManagerImpl
 * JD-Core Version:    0.6.0
 */