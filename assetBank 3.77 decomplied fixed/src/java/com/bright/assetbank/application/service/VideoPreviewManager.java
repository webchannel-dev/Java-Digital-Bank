/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.bean.VideoInfo;
/*     */ import com.bright.assetbank.application.bean.VideoPreviewQueueItem;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.converter.constant.ConverterSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.queue.service.QueueManager;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.util.commandline.CommandLineExec;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class VideoPreviewManager extends QueueManager
/*     */ {
/*  27 */   protected FileStoreManager m_fileStoreManager = null;
/*  28 */   protected DBTransactionManager m_transactionManager = null;
/*     */ 
/*     */   public int queueItem(VideoPreviewQueueItem a_queuedItem)
/*     */   {
/*  32 */     a_queuedItem.getVideoAsset().setPreviewClipBeingGenerated(true);
/*  33 */     return super.queueItem(a_queuedItem);
/*     */   }
/*     */ 
/*     */   public void processQueueItem(QueuedItem a_queueItem) throws Bn2Exception {
/*  37 */     VideoPreviewQueueItem queueItem = (VideoPreviewQueueItem)a_queueItem;
/*     */ 
/*  39 */     VideoAsset videoAsset = queueItem.getVideoAsset();
/*  40 */     VideoInfo videoInfo = queueItem.getVideoInfo();
/*  41 */     String sourcePath = queueItem.getSourcePath();
/*  42 */     int startPreviewAtFrame = queueItem.getStartPreviewAtFrame();
/*     */ 
/*  45 */     String sClipDuration = "" + AssetBankSettings.getVideoPreviewDuration();
/*  46 */     String sSize = getPreviewSize(videoInfo);
/*     */ 
/*  49 */     if ((AssetBankSettings.getVideoPreviewFormat() != null) && (AssetBankSettings.getVideoPreviewFormat().length() > 0))
/*     */     {
/*  51 */       String sPreviewFilename = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(videoAsset.getOriginalFileLocation(), "-p." + AssetBankSettings.getVideoPreviewFormat(), StoredFileType.PREVIEW_OR_THUMBNAIL);
/*     */ 
/*  55 */       String sPreviewDestPath = this.m_fileStoreManager.getAbsolutePath(sPreviewFilename);
/*     */ 
/*  58 */       String[] saPreviewCommand = { ConverterSettings.getFfmpegPath(), "-i", sourcePath, "-r", String.valueOf(AssetBankSettings.getDefaultVideoPreviewFrameRate()), "-ar", AssetBankSettings.getAudioPreviewSampleRate(), "-b", String.valueOf(AssetBankSettings.getDefaultVideoPreviewBitrate()), "-ss", String.valueOf(startPreviewAtFrame), "-t", sClipDuration, "-s", sSize, "-y", sPreviewDestPath };
/*     */       try
/*     */       {
/*  72 */         CommandLineExec.execute(saPreviewCommand);
/*  73 */         videoAsset.setPreviewClipLocation(sPreviewFilename);
/*     */ 
/*  75 */         this.m_fileStoreManager.storeFile(sPreviewFilename);
/*     */       }
/*     */       catch (Bn2Exception be)
/*     */       {
/*  80 */         this.m_logger.warn("Could not create preview file for video: " + videoAsset.getOriginalFileLocation() + ". Error: " + be.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  86 */     if ((AssetBankSettings.getEmbeddedVideoPreviewFormat() != null) && (AssetBankSettings.getEmbeddedVideoPreviewFormat().length() > 0))
/*     */     {
/*  89 */       String sEmbeddedPreviewFilename = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(videoAsset.getOriginalFileLocation(), "-p." + AssetBankSettings.getEmbeddedVideoPreviewFormat(), StoredFileType.PREVIEW_OR_THUMBNAIL);
/*     */ 
/*  92 */       String sEmbeddedDestPath = this.m_fileStoreManager.getAbsolutePath(sEmbeddedPreviewFilename);
/*  93 */       String[] saEmbeddedCommand = { ConverterSettings.getFfmpegPath(), "-i", sourcePath, "-r", String.valueOf(AssetBankSettings.getDefaultVideoPreviewFrameRate()), "-ar", AssetBankSettings.getAudioPreviewSampleRate(), "-b", String.valueOf(AssetBankSettings.getDefaultVideoPreviewBitrate()), "-ss", String.valueOf(startPreviewAtFrame), "-t", sClipDuration, "-s", sSize, "-y", sEmbeddedDestPath };
/*     */       try
/*     */       {
/* 106 */         CommandLineExec.execute(saEmbeddedCommand);
/* 107 */         videoAsset.setEmbeddedPreviewClipLocation(sEmbeddedPreviewFilename);
/*     */ 
/* 109 */         this.m_fileStoreManager.storeFile(sEmbeddedPreviewFilename);
/*     */       }
/*     */       catch (Bn2Exception be)
/*     */       {
/* 114 */         this.m_logger.warn("Could not create embedded preview file for video: " + videoAsset.getOriginalFileLocation() + ". Error: " + be.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     videoAsset.setPreviewClipBeingGenerated(false);
/* 120 */     saveChangesToDatabase(videoAsset, videoInfo);
/*     */   }
/*     */ 
/*     */   private void saveChangesToDatabase(VideoAsset a_videoAsset, VideoInfo a_videoInfo) throws Bn2Exception
/*     */   {
/* 125 */     DBTransaction transaction = null;
/*     */     try
/*     */     {
/* 128 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */ 
/* 130 */       Connection con = transaction.getConnection();
/* 131 */       String sSQL = null;
/*     */ 
/* 133 */       sSQL = "SELECT AssetId FROM VideoAsset WHERE AssetId=?";
/* 134 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 135 */       psql.setLong(1, a_videoAsset.getId());
/* 136 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 138 */       if (!rs.next())
/*     */       {
/* 140 */         sSQL = "INSERT INTO VideoAsset (PreviewClipLocation, EmbeddedPreviewClipLocation, Duration, Width, Height, PAR, PreviewClipBeingGenerated, AssetId)  VALUES (?,?,?,?,?,?,?,?) ";
/*     */       }
/*     */       else
/*     */       {
/* 145 */         sSQL = "UPDATE VideoAsset SET PreviewClipLocation=?, EmbeddedPreviewClipLocation=?, Duration=?, Width=?, Height=?, PAR=?, PreviewClipBeingGenerated=? WHERE AssetId=?";
/*     */       }
/*     */ 
/* 149 */       psql.close();
/*     */ 
/* 152 */       int iCol = 1;
/* 153 */       psql = con.prepareStatement(sSQL);
/*     */ 
/* 156 */       psql.setString(iCol++, a_videoAsset.getPreviewClipLocation());
/* 157 */       psql.setString(iCol++, a_videoAsset.getEmbeddedPreviewClipLocation());
/* 158 */       DBUtil.setFieldLongOrNull(psql, iCol++, a_videoInfo.getDuration());
/* 159 */       DBUtil.setFieldIntOrNull(psql, iCol++, a_videoInfo.getWidth());
/* 160 */       DBUtil.setFieldIntOrNull(psql, iCol++, a_videoInfo.getHeight());
/* 161 */       psql.setFloat(iCol++, a_videoInfo.getPAR());
/* 162 */       psql.setBoolean(iCol++, a_videoAsset.getPreviewClipBeingGenerated());
/* 163 */       psql.setLong(iCol++, a_videoAsset.getId());
/*     */ 
/* 165 */       psql.executeUpdate();
/* 166 */       psql.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 171 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 175 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 179 */           this.m_logger.error("Exception whilst trying to roll back connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 183 */       this.m_logger.error("Exception whilst saving video : " + e);
/* 184 */       throw new Bn2Exception("Exception whilst saving video : ", e);
/*     */     }
/*     */     finally
/*     */     {
/* 189 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 193 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 197 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getPreviewSize(VideoInfo a_info)
/*     */   {
/* 206 */     String sSize = "";
/* 207 */     long lHeight = -1L;
/* 208 */     long lWidth = -1L;
/* 209 */     if (a_info.getWidth() > a_info.getDisplayHeight())
/*     */     {
/* 211 */       double dAspectRatio = a_info.getWidth() / a_info.getDisplayHeight();
/* 212 */       lHeight = Math.round(getMaxPreviewWidth() / dAspectRatio);
/* 213 */       if (lHeight % 2L != 0L)
/*     */       {
/* 215 */         lHeight += 1L;
/*     */       }
/*     */ 
/* 219 */       if ((getMaxPreviewWidth() > 0) && (lHeight > 0L))
/*     */       {
/* 221 */         lWidth = getMaxPreviewWidth();
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 226 */       double dAspectRatio = a_info.getDisplayHeight() / a_info.getWidth();
/* 227 */       lWidth = Math.round(getMaxPreviewHeight() / dAspectRatio);
/* 228 */       if (lWidth % 2L != 0L)
/*     */       {
/* 230 */         lWidth += 1L;
/*     */       }
/*     */ 
/* 233 */       if ((getMaxPreviewHeight() > 0) && (lWidth > 0L))
/*     */       {
/* 235 */         lHeight = getMaxPreviewHeight();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 240 */     if ((lHeight > 0L) && (lWidth > 0L))
/*     */     {
/* 242 */       sSize = lWidth + "x" + lHeight;
/*     */     }
/*     */     else
/*     */     {
/* 247 */       sSize = a_info.getWidth() + "x" + a_info.getDisplayHeight();
/*     */     }
/*     */ 
/* 250 */     return sSize;
/*     */   }
/*     */ 
/*     */   public int getMaxPreviewHeight()
/*     */   {
/* 256 */     return AssetBankSettings.getVideoPreviewImageMaxHeight() > 0 ? AssetBankSettings.getVideoPreviewImageMaxHeight() : AssetBankSettings.getPreviewImageMaxHeight();
/*     */   }
/*     */ 
/*     */   public int getMaxPreviewWidth()
/*     */   {
/* 262 */     return AssetBankSettings.getVideoPreviewImageMaxWidth() > 0 ? AssetBankSettings.getVideoPreviewImageMaxWidth() : AssetBankSettings.getPreviewImageMaxWidth();
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 268 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transationManager)
/*     */   {
/* 273 */     this.m_transactionManager = a_transationManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.VideoPreviewManager
 * JD-Core Version:    0.6.0
 */