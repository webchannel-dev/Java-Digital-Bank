/*     */ package com.bright.assetbank.thumbnails.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.bean.AudioAsset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.constant.GeneratedImageVersion;
/*     */ import com.bright.assetbank.application.service.AudioAssetManagerImpl;
/*     */ import com.bright.assetbank.application.service.FileAssetManagerImpl;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.service.ImageAssetManagerImpl;
/*     */ import com.bright.assetbank.application.service.VideoAssetManagerImpl;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.common.bean.UploadedFile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.queue.service.QueueManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ThumbnailGenerationManager extends QueueManager
/*     */ {
/*  61 */   private DBTransactionManager m_transactionManager = null;
/*  62 */   private IAssetManager m_assetManager = null;
/*  63 */   private ImageAssetManagerImpl m_imageAssetManager = null;
/*  64 */   private VideoAssetManagerImpl m_videoAssetManager = null;
/*  65 */   private AudioAssetManagerImpl m_audioAssetManager = null;
/*  66 */   private FileAssetManagerImpl m_fileAssetManager = null;
/*  67 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */   private static final int k_iMaxQueueSize = 200;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     super.startup();
/*     */ 
/*  83 */     notifyThumbnailsNeedGenerating(-1L);
/*     */   }
/*     */ 
/*     */   public void regenerateAssetThumbnails(long a_lId, Set<GeneratedImageVersion> a_versionsToGenerate, boolean skipVideoThumbnails)
/*     */     throws Bn2Exception
/*     */   {
/*  95 */     boolean bSaveToDB = false;
/*     */ 
/*  97 */     this.m_logger.debug("ThumbnailGenerationManager.regenerateAssetThumbnails - generating thumbnails for asset id=" + a_lId);
/*     */ 
/* 100 */     Asset asset = this.m_assetManager.getAsset(null, a_lId, null, false, false);
/*     */ 
/* 103 */     if ((asset.getFormat().getConverterClass() != null) && (asset.getTypeId() == 1L))
/*     */     {
/* 106 */       String sAssetFileLocation = this.m_imageAssetManager.createConvertedVersion(asset, asset.getOriginalFilename(), asset.getOriginalFileLocation());
/*     */ 
/* 110 */       asset.setFileLocation(sAssetFileLocation);
/* 111 */       bSaveToDB = true;
/*     */     }
/*     */ 
/* 114 */     if ((asset instanceof AudioAsset))
/*     */     {
/* 116 */       AudioAsset audioAsset = (AudioAsset)asset;
/*     */ 
/* 118 */       this.m_audioAssetManager.saveAudio(null, audioAsset, true, false);
/*     */     }
/* 120 */     if ((asset instanceof VideoAsset))
/*     */     {
/* 122 */       VideoAsset videoAsset = (VideoAsset)asset;
/*     */ 
/* 124 */       AssetFileSource thumbnailSource = new AssetFileSource();
/* 125 */       thumbnailSource.setRemove(true);
/* 126 */       this.m_videoAssetManager.saveAsset(null, videoAsset, null, videoAsset.getLastModifiedByUser().getId(), null, thumbnailSource, true, true, -1, skipVideoThumbnails);
/*     */ 
/* 133 */       this.m_searchManager.indexDocument(videoAsset, true);
/* 134 */       this.m_assetManager.clearAssetCaches();
/*     */     }
/* 136 */     else if ((asset instanceof ImageAsset))
/*     */     {
/* 138 */       ImageAsset imageAsset = (ImageAsset)asset;
/*     */ 
/* 140 */       if ((imageAsset.getLargeImageFile() == null) && (AssetBankSettings.getCacheLargeImage()) && (imageAsset.getNumPages() <= 1))
/*     */       {
/* 145 */         bSaveToDB = true;
/*     */       }
/*     */ 
/* 149 */       String sOldThumbLocation = imageAsset.getThumbnailImageFile().getPath();
/* 150 */       ImageFile oldThumnailFile = imageAsset.getThumbnailImageFile();
/* 151 */       ImageFile oldHomogenizedFile = imageAsset.getHomogenizedImageFile();
/* 152 */       ImageFile oldPreviewFile = imageAsset.getPreviewImageFile();
/* 153 */       ImageFile oldFeaturedFile = imageAsset.getFeaturedImageFile();
/* 154 */       ImageFile oldLargeFile = imageAsset.getLargeImageFile();
/* 155 */       ImageFile oldUnwatermarkedFile = imageAsset.getUnwatermarkedLargeImageFile();
/*     */ 
/* 157 */       this.m_imageAssetManager.createImageVersions(imageAsset, null, null, -1, a_versionsToGenerate);
/*     */ 
/* 167 */       if ((bSaveToDB) || (sOldThumbLocation == null) || ((AssetBankSettings.getCacheLargeImage()) && ((!sOldThumbLocation.endsWith("-l-s.jpg")) || (hasChanged(oldThumnailFile, imageAsset.getThumbnailImageFile())) || (hasChanged(oldHomogenizedFile, imageAsset.getHomogenizedImageFile())) || (hasChanged(oldPreviewFile, imageAsset.getPreviewImageFile())) || (hasChanged(oldFeaturedFile, imageAsset.getFeaturedImageFile())) || (hasChanged(oldLargeFile, imageAsset.getLargeImageFile())) || (hasChanged(oldUnwatermarkedFile, imageAsset.getUnwatermarkedLargeImageFile())))))
/*     */       {
/* 178 */         AssetFileSource thumbnailSource = new AssetFileSource();
/* 179 */         thumbnailSource.setRegenerate(true);
/* 180 */         this.m_imageAssetManager.saveAsset(null, imageAsset, null, imageAsset.getLastModifiedByUser().getId(), null, thumbnailSource, false, false, -1);
/*     */ 
/* 188 */         this.m_searchManager.indexDocument(imageAsset, true);
/*     */ 
/* 191 */         this.m_assetManager.clearAssetCaches();
/*     */       }
/*     */ 
/*     */     }
/* 195 */     else if (asset.getFormat().getConverterClass() != null)
/*     */     {
/* 197 */       this.m_fileAssetManager.saveAsset(null, asset, null, asset.getLastModifiedByUser().getId(), null, null, true, true, -1);
/*     */ 
/* 204 */       this.m_searchManager.indexDocument(asset, true);
/*     */ 
/* 207 */       this.m_assetManager.clearAssetCaches();
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean hasChanged(UploadedFile a_oldFile, UploadedFile a_newFile)
/*     */   {
/* 213 */     if ((a_oldFile == null) || (a_oldFile.getPath() == null))
/*     */     {
/* 215 */       return (a_newFile != null) && (a_newFile.getPath() != null);
/*     */     }
/* 217 */     if ((a_newFile == null) || (a_newFile.getPath() == null))
/*     */     {
/* 219 */       return true;
/*     */     }
/* 221 */     return !a_oldFile.getPath().equals(a_newFile.getPath());
/*     */   }
/*     */ 
/*     */   public void notifyThumbnailsNeedGenerating(long a_lAssetId)
/*     */   {
/* 232 */     this.m_logger.debug("ThumbnailGenerationManager.notifyThumbnailsNeedGenerating for asset " + a_lAssetId);
/*     */ 
/* 235 */     if (getItemCount() <= 200)
/*     */     {
/* 238 */       QueuedItem task = new QueuedItem();
/* 239 */       task.setJobId(a_lAssetId);
/* 240 */       super.queueItem(task);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processQueueItem(QueuedItem a_item)
/*     */     throws Bn2Exception
/*     */   {
/* 250 */     this.m_logger.debug("ThumbnailGenerationManager.processQueueItem");
/*     */ 
/* 253 */     if (a_item.getJobId() > 0L)
/*     */     {
/*     */       try
/*     */       {
/* 258 */         regenerateAssetThumbnails(a_item.getJobId(), null, false);
/*     */       }
/*     */       catch (Throwable bn2e)
/*     */       {
/* 262 */         GlobalApplication.getInstance().getLogger().error("ThumbnailGenerationManager.processQueueItem - couldn't generate thumbnails for asset: " + a_item.getJobId() + " - error is: ", bn2e);
/*     */ 
/* 265 */         setFailedThumbnailGeneration(a_item.getJobId());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 270 */     if (getItemCount() == 0)
/*     */     {
/* 272 */       buildQueueFromDatabase();
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void buildQueueFromDatabase() throws Bn2Exception
/*     */   {
/* 278 */     this.m_logger.debug("ThumbnailGenerationManager.buildQueueFromDatabase");
/*     */ 
/* 280 */     DBTransaction dbTransaction = null;
/* 281 */     Connection con = null;
/* 282 */     PreparedStatement psql = null;
/* 283 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 288 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */ 
/* 290 */       con = dbTransaction.getConnection();
/*     */ 
/* 294 */       psql = con.prepareStatement("SELECT Id FROM Asset WHERE (ThumbnailFileLocation IS NULL OR ThumbnailFileLocation='') AND (" + SQLGenerator.getInstance().getFieldNotEmptyStatement("FileLocation") + ") " + "AND AssetTypeId IN (?,?)" + "AND ThumbnailGenerationFailed=?");
/*     */ 
/* 299 */       psql.setLong(1, 2L);
/* 300 */       psql.setLong(2, 3L);
/* 301 */       psql.setBoolean(3, false);
/*     */ 
/* 303 */       rs = psql.executeQuery();
/*     */ 
/* 305 */       while (rs.next())
/*     */       {
/* 308 */         notifyThumbnailsNeedGenerating(rs.getLong("Id"));
/*     */ 
/* 310 */         if (getItemCount() < 200)
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 317 */       psql.close();
/*     */     }
/*     */     catch (Exception e2)
/*     */     {
/* 321 */       this.m_logger.error("Exception in " + getClass().getName() + ": ", e2);
/*     */       try
/*     */       {
/* 325 */         dbTransaction.rollback();
/*     */       }
/*     */       catch (Exception e3)
/*     */       {
/* 329 */         this.m_logger.error("Exception rolling back transaction in " + getClass().getName() + ": ", e2);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 337 */         if (dbTransaction != null)
/*     */         {
/* 339 */           dbTransaction.commit();
/*     */         }
/*     */       }
/*     */       catch (SQLException e2)
/*     */       {
/* 344 */         this.m_logger.error("Exception commiting transaction in :" + getClass().getName() + ": ", e2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setFailedThumbnailGeneration(long a_lAssetId)
/*     */     throws Bn2Exception
/*     */   {
/* 352 */     DBTransaction dbTransaction = null;
/* 353 */     Connection con = null;
/* 354 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 359 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/* 360 */       con = dbTransaction.getConnection();
/*     */ 
/* 362 */       psql = con.prepareStatement("UPDATE Asset SET ThumbnailGenerationFailed=? WHERE Id=?");
/*     */ 
/* 364 */       psql.setBoolean(1, true);
/* 365 */       psql.setLong(2, a_lAssetId);
/* 366 */       psql.executeUpdate();
/* 367 */       psql.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 372 */       this.m_logger.error("Exception in " + getClass().getName() + ": ", e);
/*     */       try
/*     */       {
/* 376 */         dbTransaction.rollback();
/*     */       }
/*     */       catch (Exception e2)
/*     */       {
/* 380 */         this.m_logger.error("Exception rolling back transaction in " + getClass().getName() + ": ", e2);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 388 */         if (dbTransaction != null)
/*     */         {
/* 390 */           dbTransaction.commit();
/*     */         }
/*     */       }
/*     */       catch (SQLException e2)
/*     */       {
/* 395 */         this.m_logger.error("Exception commiting transaction in :" + getClass().getName() + ": ", e2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int getNumberOfServicingThreads()
/*     */   {
/* 402 */     if (AssetBankSettings.getThumbnailGenerationConcurrencyCount() > 0)
/*     */     {
/* 404 */       return AssetBankSettings.getThumbnailGenerationConcurrencyCount();
/*     */     }
/*     */ 
/* 407 */     return super.getNumberOfServicingThreads();
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_sSearchManager)
/*     */   {
/* 412 */     this.m_searchManager = a_sSearchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 418 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setImageAssetManager(ImageAssetManagerImpl a_sImageAssetManager)
/*     */   {
/* 424 */     this.m_imageAssetManager = a_sImageAssetManager;
/*     */   }
/*     */ 
/*     */   public void setVideoAssetManager(VideoAssetManagerImpl a_sAssetManager)
/*     */   {
/* 429 */     this.m_videoAssetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAudioAssetManager(AudioAssetManagerImpl a_sAssetManager)
/*     */   {
/* 434 */     this.m_audioAssetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setFileAssetManager(FileAssetManagerImpl a_sAssetManager)
/*     */   {
/* 439 */     this.m_fileAssetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setDBTransactionManager(DBTransactionManager a_sTransactionManager)
/*     */   {
/* 444 */     this.m_transactionManager = a_sTransactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.thumbnails.service.ThumbnailGenerationManager
 * JD-Core Version:    0.6.0
 */