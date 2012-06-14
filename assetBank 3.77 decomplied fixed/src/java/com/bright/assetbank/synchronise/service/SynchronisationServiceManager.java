/*     */ package com.bright.assetbank.synchronise.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.batch.service.BatchQueueManager;
/*     */ import com.bright.assetbank.batch.upload.service.ImportManager;
/*     */ import com.bright.assetbank.synchronise.bean.SynchronisationBatch;
/*     */ import com.bright.assetbank.synchronise.constant.SynchronisationSettings;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.assetbank.webservice.synchronise.service.SynchronisationService;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import com.bright.framework.common.bean.BooleanDataBean;
/*     */ import com.bright.framework.common.exception.SettingNotFoundException;
/*     */ import com.bright.framework.common.service.RefDataManager;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.queue.bean.MessageBatchMonitor;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class SynchronisationServiceManager extends BatchQueueManager
/*     */ {
/*     */   private static final String c_ksClassName = "SynchronisationServiceManager";
/*  74 */   private long m_lUserRunningSynch = -1L;
/*  75 */   private Vector m_vecLastLog = new Vector();
/*  76 */   private Vector m_vecLogIntro = new Vector();
/*  77 */   private Date m_dtCurrentSynchStart = null;
/*     */ 
/*  79 */   private ImportManager m_importManager = null;
/*     */ 
/*  85 */   private AssetImportManager m_assetImportManager = null;
/*     */ 
/*  91 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/* 101 */   private ABUserManager m_userManager = null;
/*     */ 
/* 107 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/* 113 */   private RefDataManager m_refDataManager = null;
/*     */ 
/* 119 */   private AssetExportManager m_exportManager = null;
/*     */ 
/* 125 */   private ScheduleManager m_scheduleManager = null;
/*     */ 
/*     */   public void setImportManager(ImportManager a_importManager)
/*     */   {
/*  82 */     this.m_importManager = a_importManager;
/*     */   }
/*     */ 
/*     */   public void setAssetImportManager(AssetImportManager a_assetImportManager)
/*     */   {
/*  88 */     this.m_assetImportManager = a_assetImportManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/*  94 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public FileStoreManager getFileStoreManager() {
/*  98 */     return this.m_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 104 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*     */   {
/* 110 */     this.m_transactionManager = a_sTransactionManager;
/*     */   }
/*     */ 
/*     */   public void setRefDataManager(RefDataManager a_refDataManager)
/*     */   {
/* 116 */     this.m_refDataManager = a_refDataManager;
/*     */   }
/*     */ 
/*     */   public void setExportManager(AssetExportManager a_sExportManager)
/*     */   {
/* 122 */     this.m_exportManager = a_sExportManager;
/*     */   }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*     */   {
/* 128 */     this.m_scheduleManager = a_sScheduleManager;
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/* 148 */     super.startup();
/*     */ 
/* 151 */     int iHourOfDay = AssetBankSettings.getSynchAssetsHourOfDay();
/* 152 */     if ((AssetBankSettings.getAllowPublishing()) && (iHourOfDay >= 0))
/*     */     {
/* 154 */       TimerTask task = new TimerTask()
/*     */       {
/*     */         public void run()
/*     */         {
/* 158 */           DBTransaction dbTransaction = null;
/*     */           try
/*     */           {
/* 161 */             dbTransaction = SynchronisationServiceManager.this.m_transactionManager.getNewTransaction();
/* 162 */             ABUser user = SynchronisationServiceManager.this.m_userManager.getApplicationUser();
/* 163 */             SynchronisationServiceManager.this.exportAndPublishFlaggedAssets(dbTransaction, user);
/*     */           }
/*     */           catch (Bn2Exception bn2e)
/*     */           {
/* 167 */             SynchronisationServiceManager.this.m_logger.error("SynchronisationServiceManager: Bn2Exception running scheduled asset synch : " + bn2e);
/*     */           }
/*     */           finally
/*     */           {
/*     */             try
/*     */             {
/* 173 */               dbTransaction.commit();
/*     */             }
/*     */             catch (SQLException e)
/*     */             {
/* 177 */               SynchronisationServiceManager.this.m_logger.error("SynchronisationServiceManager: SQLException running scheduled asset synch : " + e);
/*     */             }
/*     */             catch (Bn2Exception bn2e)
/*     */             {
/* 181 */               SynchronisationServiceManager.this.m_logger.error("SynchronisationServiceManager: Bn2Exception running scheduled asset synch : " + bn2e);
/*     */             }
/*     */           }
/*     */         }
/*     */       };
/* 189 */       this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void exportAndPublishFlaggedAssets(DBTransaction a_dbTransaction, User a_user)
/*     */     throws Bn2Exception
/*     */   {
/* 224 */     startBatchProcess(a_user.getId());
/* 225 */     this.m_lUserRunningSynch = a_user.getId();
/* 226 */     this.m_vecLastLog.clear();
/* 227 */     this.m_vecLogIntro.clear();
/*     */ 
/* 229 */     this.m_vecLogIntro.add("Synchronisation process initialised by user: " + a_user.getUsername() + " (" + a_user.getId() + ")");
/*     */ 
/* 231 */     long lTime = -1L;
/*     */     try
/*     */     {
/* 234 */       lTime = this.m_refDataManager.getSystemSettingAsLong("LastSyncTime");
/*     */     }
/*     */     catch (SettingNotFoundException e)
/*     */     {
/*     */     }
/*     */ 
/* 241 */     Date dtLastSyncTime = null;
/*     */ 
/* 243 */     if (lTime > 0L)
/*     */     {
/* 245 */       dtLastSyncTime = new Date(lTime);
/* 246 */       this.m_vecLogIntro.add("Last synchronisation run at: " + dtLastSyncTime.toString());
/*     */     }
/*     */ 
/* 249 */     this.m_dtCurrentSynchStart = new Date();
/* 250 */     String sExportName = "export." + new SimpleDateFormat("yyyy-MM-dd.kk-mm-ss").format(this.m_dtCurrentSynchStart);
/*     */ 
/* 253 */     Vector assetIdsAndSync = getAssetIdsForSynchronisation(a_dbTransaction, dtLastSyncTime);
/*     */ 
/* 255 */     if (assetIdsAndSync.size() > 0)
/*     */     {
/* 257 */       this.m_vecLogIntro.add("There are " + assetIdsAndSync.size() + " assets to synchronise");
/* 258 */       int iStartIndex = 0;
/* 259 */       int iEndIndex = SynchronisationSettings.getPublishingBatchSize();
/* 260 */       int iCount = 1;
/*     */       do
/*     */       {
/* 265 */         Vector vecIdsChunk = CollectionUtil.getSubSection(assetIdsAndSync, iStartIndex, iEndIndex);
/* 266 */         SynchronisationBatch batch = new SynchronisationBatch();
/* 267 */         batch.setAssetIdsToPublish(vecIdsChunk);
/* 268 */         batch.setUser(a_user);
/* 269 */         batch.setExportName(sExportName + "-" + iCount);
/* 270 */         queueItem(batch);
/* 271 */         iCount++;
/* 272 */         iStartIndex = iEndIndex;
/* 273 */         iEndIndex = iStartIndex + SynchronisationSettings.getPublishingBatchSize();
/*     */       }
/* 275 */       while (iStartIndex < assetIdsAndSync.size());
/*     */     }
/*     */     else
/*     */     {
/* 279 */       this.m_vecLogIntro.add("There are no assets to synchronise");
/* 280 */       finishSynchronisation();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processQueueItem(QueuedItem a_queuedItem)
/*     */     throws Bn2Exception
/*     */   {
/* 293 */     SynchronisationBatch batch = (SynchronisationBatch)a_queuedItem;
/* 294 */     boolean[] abExportFiles = new boolean[batch.getAssetIdsToPublish().size()];
/* 295 */     Vector vAssetIds = new Vector();
/*     */ 
/* 297 */     for (int i = 0; i < batch.getAssetIdsToPublish().size(); i++)
/*     */     {
/* 299 */       vAssetIds.add(new Long(((BooleanDataBean)batch.getAssetIdsToPublish().get(i)).getId()));
/* 300 */       abExportFiles[i] = (!((BooleanDataBean)batch.getAssetIdsToPublish().get(i)).getBoolVal() ? true : false);
/*     */     }
/*     */ 
/* 303 */     this.m_exportManager.exportAssets(vAssetIds, batch.getExportName(), "Exported by Asset Bank for publish process", abExportFiles, false, true, false, batch.getUser().getId(), true);
/* 304 */     addMessage(batch.getUser().getId(), "Prepared batch of assets for synchronisation... starting transfer");
/*     */ 
/* 307 */     String sPath = getFileStoreManager().getUniqueFilepath("exportAssetsTemp", StoredFileType.EXPORT);
/* 308 */     sPath = getFileStoreManager().getFullBasePath(sPath);
/*     */ 
/* 310 */     addMessage(batch.getUser().getId(), "Starting batch of " + batch.getAssetIdsToPublish().size() + " assets");
/*     */ 
/* 313 */     SynchronisationService synchService = new SynchronisationService();
/* 314 */     if (synchService.publishExportedAssets(batch.getExportName(), getSynchFiles(sPath, batch.getExportName())))
/*     */     {
/* 317 */       DBTransaction transaction = null;
/*     */       try
/*     */       {
/* 320 */         transaction = this.m_transactionManager.getNewTransaction();
/* 321 */         setAssetsAsSynchronised(transaction, vAssetIds);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 325 */         GlobalApplication.getInstance().getLogger().error("SynchronisationServiceManager: Unable to mark assets as synchronised:", e);
/* 326 */         if (transaction != null) {
/*     */           try {
/* 328 */             transaction.rollback(); } catch (SQLException ex) {
/* 329 */           }throw new Bn2Exception("SynchronisationServiceManager: Unable to mark assets as synchronised:", e);
/*     */         }
/*     */       }
/*     */       finally {
/*     */         try {
/* 334 */           transaction.commit();
/*     */         } catch (SQLException ex) {
/*     */         }
/*     */       }
/* 338 */       deleteSynchFiles(sPath, batch.getExportName());
/*     */     }
/* 340 */     addMessage(batch.getUser().getId(), "Batch completed");
/*     */ 
/* 343 */     if (getItemCount() <= 0)
/*     */     {
/* 345 */       finishSynchronisation();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void cancelSynchronisation()
/*     */     throws Bn2Exception
/*     */   {
/* 352 */     if (this.m_lUserRunningSynch > 0L)
/*     */     {
/* 354 */       this.m_vecQueue.clear();
/* 355 */       addMessage(this.m_lUserRunningSynch, "Synchronisation cancelled... the current batch will be completed");
/* 356 */       finishSynchronisation();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void finishSynchronisation()
/*     */     throws Bn2Exception
/*     */   {
/* 363 */     addMessage(this.m_lUserRunningSynch, "Synchronisation complete");
/* 364 */     this.m_batchMonitor.endBatchProcess(this.m_lUserRunningSynch);
/* 365 */     this.m_lUserRunningSynch = -1L;
/* 366 */     DBTransaction transaction = null;
/*     */     try
/*     */     {
/* 370 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */ 
/* 374 */       this.m_refDataManager.setSystemSetting(transaction, "LastSyncTime", String.valueOf(this.m_dtCurrentSynchStart.getTime()));
/* 375 */       this.m_dtCurrentSynchStart = null;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 379 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 383 */           transaction.rollback();
/*     */         } catch (SQLException ex) {
/*     */         }
/*     */       }
/*     */     }
/*     */     finally {
/* 389 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 393 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException ex)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getMessages()
/*     */   {
/* 406 */     if (this.m_lUserRunningSynch > 0L)
/*     */     {
/* 408 */       Vector vecMessages = new Vector();
/* 409 */       vecMessages.addAll(this.m_vecLogIntro);
/* 410 */       vecMessages.addAll(getMessages(this.m_lUserRunningSynch));
/* 411 */       return vecMessages;
/*     */     }
/* 413 */     return null;
/*     */   }
/*     */ 
/*     */   protected void addMessage(long a_lUserId, String a_sMessage)
/*     */   {
/* 424 */     super.addMessage(a_lUserId, a_sMessage);
/* 425 */     this.m_vecLastLog.add(a_sMessage);
/*     */   }
/*     */ 
/*     */   public Vector getLastLog()
/*     */   {
/* 436 */     Vector vecMessages = new Vector();
/* 437 */     vecMessages.addAll(this.m_vecLogIntro);
/* 438 */     vecMessages.addAll(this.m_vecLastLog);
/* 439 */     return vecMessages;
/*     */   }
/*     */ 
/*     */   private Vector getSynchFiles(String a_sBasePath, String a_sExportId)
/*     */   {
/* 458 */     Vector files = new Vector();
/* 459 */     File exportDir = new File(a_sBasePath + "/" + FrameworkSettings.getExportDirectory());
/* 460 */     File[] asExportFiles = exportDir.listFiles();
/* 461 */     for (int i = 0; i < asExportFiles.length; i++)
/*     */     {
/* 463 */       File f = asExportFiles[i];
/* 464 */       if (!f.getAbsolutePath().contains(a_sExportId))
/*     */         continue;
/* 466 */       files.add(f);
/*     */     }
/*     */ 
/* 470 */     return files;
/*     */   }
/*     */ 
/*     */   private void deleteSynchFiles(String a_sBasePath, String a_sExportId)
/*     */   {
/* 489 */     File exportDir = new File(a_sBasePath + "/" + FrameworkSettings.getExportDirectory());
/* 490 */     File[] asExportFiles = exportDir.listFiles();
/* 491 */     for (int i = 0; i < asExportFiles.length; i++)
/*     */     {
/* 493 */       File f = asExportFiles[i];
/* 494 */       if ((!f.getAbsolutePath().contains(a_sExportId)) || (f.isDirectory()))
/*     */         continue;
/* 496 */       f.delete();
/* 497 */       FileUtil.logFileDeletion(f);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Vector<BooleanDataBean> getAssetIdsForSynchronisation(DBTransaction a_dbTransaction, Date a_dtLastSyncTime)
/*     */     throws Bn2Exception
/*     */   {
/* 514 */     String ksMethodName = "getAssetIdsForSynchronisation";
/*     */ 
/* 516 */     Vector vAssetIdsAndSync = new Vector();
/*     */     try
/*     */     {
/* 525 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 529 */       String sSQL = "SELECT DISTINCT Asset.Id, Asset.Synchronised FROM Asset INNER JOIN CM_ItemInCategory ON Asset.Id = CM_ItemInCategory.ItemId INNER JOIN CM_Category ON CM_Category.Id = CM_ItemInCategory.CategoryId WHERE CM_Category.Synchronised = 1 AND CM_Category.CategoryTypeId = 2 AND NOT EXISTS (SELECT 1 FROM CM_ItemInCategory icns JOIN CM_Category cns ON icns.CategoryId = cns.Id WHERE icns.ItemId = Asset.Id AND cns.CategoryTypeId = 2 AND cns.Synchronised = 0)";
/*     */ 
/* 543 */       if (a_dtLastSyncTime != null)
/*     */       {
/* 545 */         sSQL = sSQL + " AND (Asset.Synchronised = 0 OR Asset.DateLastModified > ?)";
/*     */       }
/* 547 */       sSQL = sSQL + " ORDER BY Asset.Id";
/*     */ 
/* 549 */       int iField = 1;
/* 550 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*     */ 
/* 552 */       if (a_dtLastSyncTime != null)
/*     */       {
/* 554 */         DBUtil.setFieldTimestampOrNull(psql, iField++, a_dtLastSyncTime);
/*     */       }
/*     */ 
/* 557 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 559 */       while (rs.next())
/*     */       {
/* 561 */         BooleanDataBean idAndSync = new BooleanDataBean();
/* 562 */         idAndSync.setId(rs.getLong("Id"));
/* 563 */         idAndSync.setBoolVal(rs.getBoolean("Synchronised"));
/* 564 */         vAssetIdsAndSync.add(idAndSync);
/*     */       }
/* 566 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 570 */       this.m_logger.error("SynchronisationServiceManager.getAssetIdsForSynchronisation - " + e);
/* 571 */       throw new Bn2Exception("SynchronisationServiceManager.getAssetIdsForSynchronisation", e);
/*     */     }
/*     */ 
/* 574 */     return vAssetIdsAndSync;
/*     */   }
/*     */ 
/*     */   private void setAssetsAsSynchronised(DBTransaction a_dbTransaction, Vector a_vAssetIds)
/*     */     throws Bn2Exception
/*     */   {
/* 594 */     String sIds = StringUtil.convertNumbersToString(a_vAssetIds, ",");
/* 595 */     String ksMethodName = "setAssetsAsSynchronised";
/*     */ 
/* 597 */     Connection con = null;
/* 598 */     PreparedStatement psql = null;
/* 599 */     String sSQL = null;
/*     */     try
/*     */     {
/* 604 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 606 */       sSQL = "UPDATE Asset SET Synchronised = 1 WHERE Id IN (" + sIds + ") ";
/*     */ 
/* 608 */       psql = con.prepareStatement(sSQL);
/* 609 */       psql.executeUpdate();
/*     */ 
/* 611 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 615 */       this.m_logger.error("SynchronisationServiceManager.setAssetsAsSynchronised - " + e);
/* 616 */       throw new Bn2Exception("SynchronisationServiceManager.setAssetsAsSynchronised", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ABUser getUserForImport()
/*     */     throws Bn2Exception
/*     */   {
/* 642 */     return this.m_userManager.getApplicationUser();
/*     */   }
/*     */ 
/*     */   public void bulkUploadImportedZips(String a_sBasePath, String a_sImportId, ABUser a_user, long a_lSessionId)
/*     */     throws Bn2Exception
/*     */   {
/* 667 */     File importDir = new File(a_sBasePath + "/" + FrameworkSettings.getImportDirectory());
/* 668 */     File[] asImportFiles = importDir.listFiles();
/*     */ 
/* 671 */     WorkflowUpdate update = new WorkflowUpdate(a_user.getId(), a_lSessionId);
/* 672 */     update.setUpdateType(3);
/*     */ 
/* 674 */     for (int i = 0; i < asImportFiles.length; i++)
/*     */     {
/* 676 */       File f = asImportFiles[i];
/* 677 */       if ((!f.getName().contains(a_sImportId)) || (!FileUtil.getSuffix(f.getName()).equals("zip"))) {
/*     */         continue;
/*     */       }
/* 680 */       FileUtil.copyFile(f.getAbsolutePath(), this.m_importManager.getBulkUploadDirectory(a_user) + File.separator + f.getName());
/* 681 */       this.m_importManager.importFromZip(a_user, new Asset(), f.getName(), null, true, a_lSessionId, true, false, 0L, false, update, false);
/* 682 */       f.delete();
/* 683 */       FileUtil.logFileDeletion(f);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateAssetsFromMetaFile(String a_sBasePath, String a_sImportId, User a_user)
/*     */     throws Bn2Exception
/*     */   {
/* 706 */     File importDir = new File(a_sBasePath + "/" + FrameworkSettings.getImportDirectory());
/* 707 */     File[] asImportFiles = importDir.listFiles();
/* 708 */     for (int i = 0; i < asImportFiles.length; i++)
/*     */     {
/* 710 */       File f = asImportFiles[i];
/* 711 */       if ((!f.getName().contains(a_sImportId)) || (!FileUtil.getSuffix(f.getName()).equals("xls"))) {
/*     */         continue;
/*     */       }
/* 714 */       String sFileLocation = f.getAbsolutePath();
/* 715 */       this.m_assetImportManager.importAssetData(sFileLocation, a_user.getId(), false, true, true, 0L, true, true);
/* 716 */       f.delete();
/* 717 */       FileUtil.logFileDeletion(f);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.service.SynchronisationServiceManager
 * JD-Core Version:    0.6.0
 */