/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetboxDownloadRequest;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.bean.VideoConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.util.DownloadUtil;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.batch.service.BatchQueueManager;
/*     */ import com.bright.assetbank.usage.bean.AssetUse;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.bean.FileBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.queue.bean.MessageBatchMonitor;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class DownloadAssetBoxBatchManager extends BatchQueueManager
/*     */   implements AssetBankConstants, AssetBoxConstants
/*     */ {
/*     */   private static final String c_ksClassName = "DownloadAssetBoxBatchManager";
/*  71 */   private UsageManager m_usageManager = null;
/*     */ 
/*  77 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/*  83 */   private IAssetManager m_assetManager = null;
/*     */ 
/*  89 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/*  96 */   private ListManager m_listManager = null;
/*     */ 
/* 102 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/*  74 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/*  80 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/*  86 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*     */   {
/*  92 */     this.m_transactionManager = a_sTransactionManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/*  99 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_manager)
/*     */   {
/* 105 */     this.m_attributeManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void processQueueItem(QueuedItem a_queuedItem)
/*     */     throws Bn2Exception
/*     */   {
/* 117 */     AssetboxDownloadRequest adRequest = null;
/*     */     try
/*     */     {
/* 121 */       adRequest = (AssetboxDownloadRequest)a_queuedItem;
/*     */ 
/* 123 */       addMessage(adRequest.getJobId(), this.m_listManager.getListItem(null, "snippet-preparing-download", adRequest.getLanguage()).getBody());
/*     */ 
/* 126 */       List vFiles = downloadAssets(adRequest);
/*     */ 
/* 128 */       if ((vFiles != null) && (vFiles.size() > 0))
/*     */       {
/* 130 */         addMessage(adRequest.getJobId(), this.m_listManager.getListItem(null, "snippet-finished-preparing-download", adRequest.getLanguage()).getBody());
/*     */       }
/*     */ 
/* 134 */       if (isThreadActive(Thread.currentThread()))
/*     */       {
/* 137 */         adRequest.processDownloadComplete(vFiles);
/*     */       }
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 142 */       this.m_logger.error("DownloadAssetBoxBatchManager.processQueueItem - exception:", bn2e);
/* 143 */       throw bn2e;
/*     */     }
/*     */     finally
/*     */     {
/* 147 */       if ((adRequest != null) && (isThreadActive(Thread.currentThread())))
/*     */       {
/* 149 */         endBatchProcess(adRequest.getJobId());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public List downloadAssets(AssetboxDownloadRequest a_adRequest)
/*     */     throws Bn2Exception
/*     */   {
/* 180 */     Vector vZipFiles = null;
/* 181 */     HashSet hsDestFileNames = null;
/* 182 */     FileFormat requestFormat = a_adRequest.getFormat();
/* 183 */     Vector vSourceFilePaths = new Vector();
/* 184 */     Vector vDestFilePaths = new Vector();
/*     */ 
/* 186 */     hsDestFileNames = new HashSet(a_adRequest.getVecAssets().size() * 2);
/*     */ 
/* 188 */     DBTransaction dbTransaction = null;
/*     */     try
/*     */     {
/* 193 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */ 
/* 195 */       Iterator itList = a_adRequest.getVecAssets().iterator();
/* 196 */       while (itList.hasNext())
/*     */       {
/* 198 */         Asset asset = (Asset)itList.next();
/* 199 */         String sFilename = DownloadUtil.getClientFilename(asset, null);
/*     */ 
/* 201 */         if (sFilename == null)
/*     */         {
/* 203 */           sFilename = FileUtil.getFilepathWithoutSuffix(asset.getFileName());
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 208 */           addMessage(a_adRequest.getJobId(), this.m_listManager.getListItem(dbTransaction, "snippet-processing-item-for-download", a_adRequest.getLanguage()).getBody() + ": " + sFilename);
/*     */ 
/* 212 */           String sTargetFileExtension = null;
/* 213 */           String sOriginalFileExtension = asset.getFormat().getFileExtension();
/*     */ 
/* 216 */           if (sOriginalFileExtension == null)
/*     */           {
/* 218 */             sOriginalFileExtension = FileUtil.getSuffix(asset.getOriginalFilename());
/*     */           }
/*     */ 
/* 222 */           boolean bPreserveFormat = UsageTypeFormat.extensionInPreserveList(sOriginalFileExtension, a_adRequest.getPreserveFormatList());
/*     */ 
/* 224 */           if (!asset.getIsImage())
/*     */           {
/* 226 */             bPreserveFormat = true;
/*     */           }
/*     */ 
/* 230 */           if (requestFormat == null)
/*     */           {
/* 233 */             sTargetFileExtension = sOriginalFileExtension;
/*     */           }
/* 237 */           else if (bPreserveFormat)
/*     */           {
/* 240 */             sTargetFileExtension = sOriginalFileExtension;
/*     */           }
/*     */           else
/*     */           {
/* 245 */             sTargetFileExtension = requestFormat.getFileExtension();
/*     */           }
/*     */ 
/* 250 */           FileFormat ffTargetFormat = this.m_assetManager.getFileFormatForExtension(dbTransaction, sTargetFileExtension);
/*     */ 
/* 253 */           ImageConversionInfo conversionInfoForCurrentFile = new ImageConversionInfo(a_adRequest.getConversionInfo());
/*     */ 
/* 259 */           if ((!this.m_assetManager.canConvertToFileFormat(asset, ffTargetFormat)) || (DownloadUtil.treatFileAsDocumentForDownload(sOriginalFileExtension)))
/*     */           {
/* 262 */             conversionInfoForCurrentFile.setUseOriginal(true);
/* 263 */             sTargetFileExtension = sOriginalFileExtension;
/*     */           }
/*     */ 
/* 267 */           long lDownloadTypeId = 2L;
/*     */ 
/* 270 */           if (conversionInfoForCurrentFile.getUseOriginal())
/*     */           {
/* 272 */             lDownloadTypeId = 3L;
/*     */           }
/*     */ 
/* 275 */           String sSource = null;
/* 276 */           if ((asset.getIsVideo()) || (asset.getIsAudio()))
/*     */           {
/* 279 */             VideoConversionInfo conversionInfo = new VideoConversionInfo();
/* 280 */             conversionInfo.setUseOriginal(true);
/*     */ 
/* 282 */             sSource = this.m_assetManager.getDownloadableAssetPath(asset, sTargetFileExtension, conversionInfo);
/*     */           }
/*     */           else
/*     */           {
/* 289 */             sSource = this.m_assetManager.getDownloadableAssetPath(asset, sTargetFileExtension, conversionInfoForCurrentFile);
/*     */ 
/* 294 */             DownloadUtil.embedMetadata(sSource, asset, false, false, conversionInfoForCurrentFile, this.m_attributeManager, this.m_fileStoreManager);
/*     */           }
/*     */ 
/* 300 */           if (!asset.getIsUnsubmitted())
/*     */           {
/* 302 */             this.m_usageManager.logAssetUseAsynchronously(asset.getId(), a_adRequest.getUserId(), a_adRequest.getAssetUse().getUsageTypeId(), a_adRequest.getAssetUse().getUsageOther(), lDownloadTypeId, ((ABUserProfile)UserProfile.getUserProfile(a_adRequest.getSession())).getSessionId(), a_adRequest.getSecondaryUsageTypes());
/*     */           }
/*     */ 
/* 312 */           String sDest = FileUtil.getSafeFilename(sFilename, false);
/*     */ 
/* 315 */           int iCount = 1;
/* 316 */           while (hsDestFileNames.contains(sDest.toLowerCase()))
/*     */           {
/* 318 */             sDest = FileUtil.getSafeFilename(sFilename + iCount++, false);
/*     */           }
/*     */ 
/* 322 */           hsDestFileNames.add(sDest.toLowerCase());
/*     */ 
/* 324 */           vSourceFilePaths.add(sSource);
/* 325 */           String sClientFilename = sDest + "." + sTargetFileExtension;
/* 326 */           vDestFilePaths.add(sClientFilename);
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/* 330 */           this.m_logger.error("DownloadAssetBoxManager.downloadAssets() : Exception caught while preparing asset for download : id=" + asset.getId() + " filename=" + asset.getFileName(), bn2e);
/*     */ 
/* 333 */           addMessage(a_adRequest.getJobId(), this.m_listManager.getListItem(dbTransaction, "snippet-error-processing-item-for-download", a_adRequest.getLanguage(), new String[] { sFilename }));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 338 */       if (vSourceFilePaths.size() > 0)
/*     */       {
/* 340 */         addMessage(a_adRequest.getJobId(), this.m_listManager.getListItem(null, "snippet-creating-zip-files", a_adRequest.getLanguage()).getBody());
/*     */ 
/* 343 */         String[] saZipFilePath = this.m_fileStoreManager.createZipFilesForDownload(FileUtil.getSafeFilename(a_adRequest.getFilename(), true), vSourceFilePaths, vDestFilePaths, AssetBankSettings.getDownloadZipMaxSizeInMBs() * 1000000);
/*     */ 
/* 349 */         vZipFiles = new Vector(saZipFilePath.length);
/* 350 */         for (int i = 0; i < saZipFilePath.length; i++)
/*     */         {
/* 352 */           String sFilename = FileUtil.getSafeFilename(a_adRequest.getFilename(), true);
/* 353 */           if (i > 0)
/*     */           {
/* 355 */             sFilename = sFilename + (i + 1);
/*     */           }
/* 357 */           vZipFiles.add(new FileBean(sFilename + ".zip", saZipFilePath[i]));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 362 */         addMessage(a_adRequest.getJobId(), "No files were available for download.");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 367 */       if (dbTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 371 */           dbTransaction.commit();
/*     */         }
/*     */         catch (Exception e2)
/*     */         {
/* 375 */           this.m_logger.error("Exception committing transaction in DownloadBatchManager:" + e2.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 380 */     return vZipFiles;
/*     */   }
/*     */ 
/*     */   public int queueItem(QueuedItem a_queuedItem)
/*     */   {
/* 393 */     AssetboxDownloadRequest adRequest = (AssetboxDownloadRequest)a_queuedItem;
/*     */ 
/* 396 */     cancelJobs(adRequest.getJobId(), true);
/*     */ 
/* 398 */     int iFreeThreads = getFreeServicingThreadCount();
/*     */ 
/* 400 */     startBatchProcess(adRequest.getJobId());
/*     */ 
/* 402 */     int iQueueCount = super.queueItem(a_queuedItem);
/*     */ 
/* 404 */     if (iFreeThreads <= 0)
/*     */     {
/*     */       try
/*     */       {
/* 408 */         this.m_batchMonitor.addMessage(adRequest.getJobId(), this.m_listManager.getListItem(null, "waitingQueuedDownloads", LanguageConstants.k_defaultLanguage).getBody() + iQueueCount);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 412 */         this.m_logger.error("Exception displaying status message:" + e.getMessage());
/*     */       }
/*     */     }
/*     */ 
/* 416 */     return iQueueCount;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.DownloadAssetBoxBatchManager
 * JD-Core Version:    0.6.0
 */