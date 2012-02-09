/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.bean.AudioAsset;
/*     */ import com.bright.assetbank.application.bean.UploadedFileInfo;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.bean.VideoInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetFileReadException;
/*     */ import com.bright.assetbank.application.util.AudioAssetDBUtil;
/*     */ import com.bright.assetbank.application.util.VideoUtil;
/*     */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*     */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.util.commandline.CommandLineExec;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AudioAssetManagerImpl extends FileAssetManagerImpl
/*     */ {
/*  63 */   private VideoAssetManagerImpl m_videoAssetManager = null;
/*     */ 
/*     */   public long getAssetTypeId()
/*     */   {
/*  79 */     return 4L;
/*     */   }
/*     */ 
/*     */   public Asset saveAsset(DBTransaction a_dbTransaction, Asset a_asset, AssetFileSource a_source, long a_lUserId, AssetConversionInfo a_conversionInfo, AssetFileSource a_thumbnailSource, boolean a_bForceThumbnailRegeneration, boolean a_bForcePreviewRegeneration, int a_iSaveTypeId)
/*     */     throws Bn2Exception, AssetFileReadException
/*     */   {
/* 113 */     DBTransaction transaction = a_dbTransaction;
/* 114 */     AudioAsset audio = null;
/* 115 */     boolean bIsNew = false;
/*     */ 
/* 117 */     if ((a_asset.getId() <= 0L) || ((a_source != null) && (a_source.getIsNewWithFixedId())))
/*     */     {
/* 119 */       bIsNew = true;
/*     */     }
/*     */ 
/* 123 */     if ((a_asset instanceof AudioAsset))
/*     */     {
/* 125 */       audio = (AudioAsset)a_asset;
/*     */     }
/*     */     else
/*     */     {
/* 129 */       audio = new AudioAsset(a_asset);
/*     */     }
/*     */ 
/* 133 */     super.saveAsset(a_dbTransaction, audio, a_source, a_lUserId, a_conversionInfo, a_thumbnailSource, a_bForceThumbnailRegeneration, a_bForcePreviewRegeneration, a_iSaveTypeId);
/*     */ 
/* 141 */     boolean bNewSource = false;
/* 142 */     if ((a_source != null) && (a_source.isValid()))
/*     */     {
/* 144 */       bNewSource = true;
/*     */     }
/*     */ 
/* 148 */     saveAudio(transaction, audio, bNewSource, bIsNew);
/*     */ 
/* 153 */     return audio;
/*     */   }
/*     */ 
/*     */   public void saveAudio(DBTransaction a_dbTransaction, AudioAsset a_audio, boolean a_bRegeneratePreview, boolean a_bIsNew)
/*     */     throws Bn2Exception
/*     */   {
/* 178 */     DBTransaction transaction = a_dbTransaction;
/* 179 */     String sSourcePath = null;
/*     */     try
/*     */     {
/* 184 */       if (a_bRegeneratePreview)
/*     */       {
/* 187 */         String sPreviewFilename = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(a_audio.getFileLocation(), "-p." + AssetBankSettings.getAudioPreviewFormat(), StoredFileType.PREVIEW_OR_THUMBNAIL);
/*     */ 
/* 191 */         String sEmbeddedPreviewFilename = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(a_audio.getFileLocation(), "-p." + AssetBankSettings.getEmbeddedAudioPreviewFormat(), StoredFileType.PREVIEW_OR_THUMBNAIL);
/*     */ 
/* 196 */         sSourcePath = this.m_fileStoreManager.getAbsolutePath(a_audio.getFileLocation());
/* 197 */         String sDestPath = this.m_fileStoreManager.getAbsolutePath(sPreviewFilename);
/* 198 */         String sEmbeddedDestPath = this.m_fileStoreManager.getAbsolutePath(sEmbeddedPreviewFilename);
/*     */ 
/* 200 */         String sClipDuration = "" + AssetBankSettings.getAudioPreviewDuration();
/*     */ 
/* 203 */         String[] saCommand = { ConverterSettings.getFfmpegPath(), "-i", sSourcePath, "-ar", AssetBankSettings.getAudioPreviewSampleRate(), "-ab", AssetBankSettings.getAudioBitRate(), "-t", sClipDuration, "-y", sDestPath };
/*     */ 
/* 212 */         String[] saEmbeddedCommand = { ConverterSettings.getFfmpegPath(), "-i", sSourcePath, "-ar", AssetBankSettings.getAudioPreviewSampleRate(), "-ab", AssetBankSettings.getAudioBitRate(), "-t", sClipDuration, "-y", sEmbeddedDestPath };
/*     */         try
/*     */         {
/* 224 */           CommandLineExec.execute(saCommand);
/* 225 */           a_audio.setPreviewClipLocation(sPreviewFilename);
/*     */ 
/* 227 */           this.m_fileStoreManager.storeFile(sPreviewFilename);
/*     */         }
/*     */         catch (Bn2Exception be)
/*     */         {
/* 232 */           this.m_logger.warn("Could not create preview file for audio file: " + a_audio.getOriginalFileLocation() + " : Error : " + be.getMessage());
/*     */         }
/*     */         try
/*     */         {
/* 236 */           CommandLineExec.execute(saEmbeddedCommand);
/* 237 */           a_audio.setEmbeddedPreviewClipLocation(sEmbeddedPreviewFilename);
/*     */ 
/* 239 */           this.m_fileStoreManager.storeFile(sEmbeddedPreviewFilename);
/*     */         }
/*     */         catch (Bn2Exception be)
/*     */         {
/* 244 */           this.m_logger.warn("Could not create embedded preview file for audio: " + a_audio.getOriginalFileLocation() + ". Error: " + be.getMessage());
/*     */         }
/*     */ 
/*     */       }
/* 248 */       else if ((a_bIsNew) && (StringUtils.isNotEmpty(a_audio.getFileLocation())))
/*     */       {
/* 250 */         sSourcePath = this.m_fileStoreManager.getAbsolutePath(a_audio.getPreviewClipLocation());
/*     */ 
/* 253 */         a_audio.setPreviewClipLocation(copyFile(a_audio.getPreviewClipLocation(), StoredFileType.PREVIEW_OR_THUMBNAIL));
/* 254 */         a_audio.setEmbeddedPreviewClipLocation(copyFile(a_audio.getEmbeddedPreviewClipLocation(), StoredFileType.PREVIEW_OR_THUMBNAIL));
/*     */       }
/*     */ 
/* 258 */       if ((a_bIsNew) || (a_bRegeneratePreview))
/*     */       {
/*     */         try
/*     */         {
/* 262 */           VideoInfo info = VideoUtil.getVideoInfo(sSourcePath);
/*     */ 
/* 264 */           if (transaction == null)
/*     */           {
/* 266 */             transaction = getTransactionManager().getNewTransaction();
/*     */           }
/*     */ 
/* 269 */           Connection con = transaction.getConnection();
/* 270 */           String sSQL = null;
/*     */ 
/* 272 */           sSQL = "SELECT AssetId FROM AudioAsset WHERE AssetId=?";
/* 273 */           PreparedStatement psql = con.prepareStatement(sSQL);
/* 274 */           psql.setLong(1, a_audio.getId());
/* 275 */           ResultSet rs = psql.executeQuery();
/*     */ 
/* 277 */           if ((a_bIsNew) || (!rs.next()))
/*     */           {
/* 279 */             sSQL = "INSERT INTO AudioAsset (PreviewClipLocation, EmbeddedPreviewClipLocation, Duration, AssetId)  VALUES (?,?,?,?) ";
/*     */           }
/*     */           else
/*     */           {
/* 284 */             sSQL = "UPDATE AudioAsset SET PreviewClipLocation=?, EmbeddedPreviewClipLocation=?, Duration=?  WHERE AssetId=?";
/*     */           }
/*     */ 
/* 288 */           psql.close();
/*     */ 
/* 291 */           int iCol = 1;
/* 292 */           psql = con.prepareStatement(sSQL);
/*     */ 
/* 295 */           psql.setString(iCol++, a_audio.getPreviewClipLocation());
/* 296 */           psql.setString(iCol++, a_audio.getEmbeddedPreviewClipLocation());
/* 297 */           DBUtil.setFieldLongOrNull(psql, iCol++, info.getDuration());
/* 298 */           psql.setLong(iCol++, a_audio.getId());
/*     */ 
/* 300 */           psql.executeUpdate();
/* 301 */           psql.close();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 306 */           if ((a_dbTransaction == null) && (transaction != null))
/*     */           {
/*     */             try
/*     */             {
/* 310 */               transaction.rollback();
/*     */             }
/*     */             catch (SQLException sqle)
/*     */             {
/* 314 */               this.m_logger.error("Exception whilst trying to roll back connection " + sqle.getMessage());
/*     */             }
/*     */           }
/*     */ 
/* 318 */           this.m_logger.error("Exception whilst saving audio file : " + e);
/* 319 */           throw new Bn2Exception("Exception whilst saving audio file : ", e);
/*     */         }
/*     */         finally
/*     */         {
/* 324 */           if ((a_dbTransaction == null) && (transaction != null))
/*     */           {
/*     */             try
/*     */             {
/* 328 */               transaction.commit();
/*     */             }
/*     */             catch (SQLException sqle)
/*     */             {
/* 332 */               this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 341 */       if (a_bIsNew)
/*     */       {
/* 343 */         deleteAsset(a_dbTransaction, a_audio.getId());
/* 344 */         throw new Bn2Exception("Error in AudioAssetManagerImpl.saveAsset:", t);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lId, Asset a_asset, Vector a_vVisibleAttributeIds, boolean a_bGetFeedback)
/*     */     throws Bn2Exception
/*     */   {
/* 375 */     return getAsset(a_dbTransaction, a_lId, a_asset, a_vVisibleAttributeIds, LanguageConstants.k_defaultLanguage, a_bGetFeedback);
/*     */   }
/*     */ 
/*     */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lId, Asset a_asset, Vector a_vVisibleAttributeIds, Language a_language, boolean a_bGetFeedback)
/*     */     throws Bn2Exception
/*     */   {
/* 405 */     AudioAsset audio = null;
/* 406 */     Connection con = null;
/* 407 */     ResultSet rs = null;
/* 408 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 410 */     if (transaction == null)
/*     */     {
/* 412 */       transaction = getTransactionManager().getNewTransaction();
/*     */     }
/*     */ 
/* 415 */     if (a_asset == null)
/*     */     {
/* 417 */       audio = new AudioAsset();
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 423 */         audio = (AudioAsset)a_asset;
/*     */       }
/*     */       catch (ClassCastException cce)
/*     */       {
/* 427 */         throw new Bn2Exception("AudioAssetManagerImpl.getAsset() : Passed asset object is not of type AudioAsset", cce);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 432 */     audio = (AudioAsset)super.getAsset(a_dbTransaction, a_lId, audio, a_vVisibleAttributeIds, a_language, a_bGetFeedback);
/*     */     try
/*     */     {
/* 436 */       con = transaction.getConnection();
/*     */ 
/* 438 */       String sSQL = "SELECT audioa.PreviewClipLocation audPreviewClipLocation, audioa.EmbeddedPreviewClipLocation audEmbeddedPreviewClipLocation, audioa.Duration audDuration FROM AudioAsset audioa WHERE audioa.AssetId = ?";
/*     */ 
/* 442 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 443 */       psql.setLong(1, a_lId);
/*     */ 
/* 445 */       rs = psql.executeQuery();
/*     */ 
/* 447 */       if (rs.next())
/*     */       {
/* 449 */         AudioAssetDBUtil.populateAudioAssetFromRS(audio, transaction, getAssetManager(), rs);
/*     */       }
/*     */ 
/* 452 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 456 */       this.m_logger.error("SQL Exception whilst getting an audio asset from the database : " + e);
/* 457 */       throw new Bn2Exception("SQL Exception whilst getting an audio asset from the database : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 462 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 466 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 470 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 475 */     return audio;
/*     */   }
/*     */ 
/*     */   public void deleteAsset(DBTransaction a_dbTransaction, long a_lId, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 497 */     AudioAsset audio = null;
/* 498 */     Connection con = null;
/* 499 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 501 */     if (transaction == null)
/*     */     {
/* 503 */       transaction = getTransactionManager().getNewTransaction();
/*     */     }
/*     */ 
/* 506 */     audio = (AudioAsset)getAsset(a_dbTransaction, a_lId, null, null, false);
/*     */     try
/*     */     {
/* 510 */       con = transaction.getConnection();
/*     */ 
/* 513 */       if (AssetBankSettings.getAssetRepurposingEnabled())
/*     */       {
/* 515 */         this.m_assetRepurposingManager.removeRepurposedImages(transaction, audio.getId());
/*     */       }
/*     */ 
/* 518 */       String sSQL = "DELETE FROM AudioAsset WHERE AssetId=?";
/* 519 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 520 */       psql.setLong(1, a_lId);
/*     */ 
/* 522 */       psql.executeUpdate();
/* 523 */       psql.close();
/*     */ 
/* 526 */       super.deleteAsset(transaction, a_lId, audio);
/*     */ 
/* 531 */       if ((audio.getPreviewClipLocation() != null) && (audio.getPreviewClipLocation().length() > 0))
/*     */       {
/* 533 */         this.m_fileStoreManager.deleteFile(audio.getPreviewClipLocation());
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 539 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 543 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 547 */           this.m_logger.error("SQL Exception whilst trying to roll back transaction " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 551 */       this.m_logger.error("Exception whilst deleting audio file from the database : " + e);
/* 552 */       throw new Bn2Exception("Exception whilst deleting audio file from the database : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 557 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 561 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 565 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected UploadedFileInfo getAdditionalFileInfo(UploadedFileInfo a_sFileInfo, String a_sLocation)
/*     */   {
/* 583 */     return a_sFileInfo;
/*     */   }
/*     */ 
/*     */   public String getDownloadableAssetPath(Asset a_asset, String a_sConvertToFileExtension, AssetConversionInfo a_conversionInfo)
/*     */     throws Bn2Exception
/*     */   {
/* 592 */     a_asset.setOriginalFileLocation(a_asset.getFileLocation());
/* 593 */     return this.m_videoAssetManager.getDownloadableAssetPath(new VideoAsset(a_asset), a_sConvertToFileExtension, a_conversionInfo);
/*     */   }
/*     */ 
/*     */   public void setVideoAssetManager(VideoAssetManagerImpl a_manager)
/*     */   {
/* 598 */     this.m_videoAssetManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AudioAssetManagerImpl
 * JD-Core Version:    0.6.0
 */