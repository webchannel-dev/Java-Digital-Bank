/*      */ package com.bright.assetbank.batch.upload.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.util.Bn2FTP;
/*      */ import com.bright.assetbank.agreements.bean.Agreement;
/*      */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*      */ import com.bright.assetbank.application.bean.AssetFileSource;
/*      */ import com.bright.assetbank.application.bean.FileFormat;
/*      */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*      */ import com.bright.assetbank.application.bean.LightweightAsset;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.service.AssetLogManager;
/*      */ import com.bright.assetbank.application.service.IAssetManager;
/*      */ import com.bright.assetbank.application.util.AssetUtil;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*      */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*      */ import com.bright.assetbank.batch.service.BatchQueueManager;
/*      */ import com.bright.assetbank.batch.upload.bean.BatchBulkUploadInfo;
/*      */ import com.bright.assetbank.batch.upload.bean.UserUploadsForDay;
/*      */ import com.bright.assetbank.category.util.CategoryUtil;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*      */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*      */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*      */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*      */ import com.bright.assetbank.search.bean.SearchCriteria;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.synchronise.util.SynchUtil;
/*      */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*      */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.file.filter.IsDirectoryFilter;
/*      */ import com.bright.framework.file.filter.IsNonZipFileFilter;
/*      */ import com.bright.framework.file.filter.IsNormalFileFilter;
/*      */ import com.bright.framework.file.filter.IsZipFileFilter;
/*      */ import com.bright.framework.image.util.ImageMagick;
/*      */ import com.bright.framework.image.util.ImageUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.queue.bean.MessageBatchMonitor;
/*      */ import com.bright.framework.queue.bean.QueuedItem;
/*      */ import com.bright.framework.search.bean.SearchResults;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.storage.constant.StoredFileType;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.File;
/*      */ import java.io.FileFilter;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.Collator;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipException;
/*      */ import java.util.zip.ZipFile;
/*      */ import org.apache.commons.io.FilenameUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class ImportManager extends BatchQueueManager
/*      */   implements AssetBankConstants
/*      */ {
/*      */   private static final String k_sIdInFilenameRegEx = ".*(\\A|\\.)[0-9]+(\\Z|\\.).*";
/*      */   private static final int k_iIdInFilenameCheckLimit = 100;
/*      */   private static final String c_ksClassName = "ImportManager";
/*      */   private static final String c_ksWatchProcessDir = "_process_";
/*      */   private static final long c_klWatchLastModifiedGrace = 30000L;
/*      */   private static final long c_klWaitTimeOnDBError = 120000L;
/*      */   private static final int c_kiRetryNumberOnDBError = 60;
/*      */   private IAssetManager m_assetManager;
/*      */   private AssetRelationshipManager m_assetRelationshipManager;
/*      */   private DBTransactionManager m_transactionManager;
/*      */   Map m_mapLinkedAssetsForUsers;
/*      */   private MultiLanguageSearchManager m_searchManager;
/*      */   private CategoryManager m_categoryManager;
/*      */   protected FileStoreManager m_fileStoreManager;
/*      */   private AttributeManager m_attributeManager;
/*      */   private AttributeValueManager m_attributeValueManager;
/*      */   private ScheduleManager m_scheduleManager;
/*      */   private EmailManager m_emailManager;
/*      */   private AssetEntityManager m_assetEntityManager;
/*      */   private String m_sBulkUploadDirectory;
/*      */   private String m_sProcessedDirectory;
/*      */   private HashMap m_hmWatchedFiles;
/*      */   private ABUserManager m_userManager;
/*      */   protected TaxonomyManager m_taxonomyManager;
/*      */   private OrgUnitManager m_orgUnitManager;
/*      */   private AgreementsManager m_agreementsManager;
/*      */   private boolean m_bWatchImportInProgress;
/*      */   private Object m_oWatchImportLock;
/*      */   private AssetWorkflowManager m_assetWorkflowManager;
/*      */   private AssetLogManager m_assetLogManager;
/*      */   private ListManager m_listManager;
/*      */ 
/*      */   public ImportManager()
/*      */   {
/*  192 */     this.m_assetManager = null;
/*  193 */     this.m_assetRelationshipManager = null;
/*  194 */     this.m_transactionManager = null;
/*      */ 
/*  196 */     this.m_mapLinkedAssetsForUsers = Collections.synchronizedMap(new HashMap());
/*      */ 
/*  198 */     this.m_searchManager = null;
/*  199 */     this.m_categoryManager = null;
/*  200 */     this.m_fileStoreManager = null;
/*  201 */     this.m_attributeManager = null;
/*  202 */     this.m_attributeValueManager = null;
/*      */ 
/*  204 */     this.m_scheduleManager = null;
/*  205 */     this.m_emailManager = null;
/*  206 */     this.m_assetEntityManager = null;
/*      */ 
/*  208 */     this.m_sBulkUploadDirectory = null;
/*  209 */     this.m_sProcessedDirectory = null;
/*  210 */     this.m_hmWatchedFiles = null;
/*  211 */     this.m_userManager = null;
/*  212 */     this.m_taxonomyManager = null;
/*  213 */     this.m_orgUnitManager = null;
/*  214 */     this.m_agreementsManager = null;
/*      */ 
/*  216 */     this.m_bWatchImportInProgress = false;
/*  217 */     this.m_oWatchImportLock = new Object();
/*      */ 
/*  219 */     this.m_assetWorkflowManager = null;
/*      */ 
/* 3364 */     this.m_assetLogManager = null;
/*      */ 
/* 3391 */     this.m_listManager = null;
/*      */   }
/*      */ 
/*      */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWorkflowManager)
/*      */   {
/*  222 */     this.m_assetWorkflowManager = a_assetWorkflowManager;
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  239 */     super.startup();
/*      */ 
/*  242 */     if (FrameworkSettings.getUseRelativeDirectories())
/*      */     {
/*  244 */       this.m_sBulkUploadDirectory = (AssetBankSettings.getApplicationPath() + "/");
/*  245 */       this.m_sProcessedDirectory = (AssetBankSettings.getApplicationPath() + "/");
/*      */     }
/*      */     else
/*      */     {
/*  249 */       this.m_sBulkUploadDirectory = "";
/*  250 */       this.m_sProcessedDirectory = "";
/*      */     }
/*      */ 
/*  253 */     this.m_sBulkUploadDirectory += AssetBankSettings.getBulkUploadDirectory();
/*  254 */     this.m_sProcessedDirectory += AssetBankSettings.getBulkUploadProcessedDirectory();
/*      */ 
/*  257 */     long lWatchPeriod = AssetBankSettings.getWatchDirectoryPeriod();
/*  258 */     if ((AssetBankSettings.getWatchDirectory(0) != null) && (AssetBankSettings.getWatchDirectory(0).length() > 0) && (lWatchPeriod > 0L))
/*      */     {
/*  263 */       TimerTask task = new TimerTask()
/*      */       {
/*      */         public void run()
/*      */         {
/*  267 */           ImportManager.this.checkWatchDirectories();
/*      */         }
/*      */       };
/*  272 */       this.m_scheduleManager.schedule(task, 60000L, lWatchPeriod, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   private File[] getFileList(FileFilter a_fileFilter, User a_user)
/*      */     throws Bn2Exception
/*      */   {
/*  296 */     String sBulkUploadDir = getBulkUploadDirectory(a_user);
/*      */ 
/*  298 */     File fUploadDir = new File(sBulkUploadDir);
/*      */ 
/*  301 */     File[] aFiles = fUploadDir.listFiles(a_fileFilter);
/*      */ 
/*  303 */     return aFiles;
/*      */   }
/*      */ 
/*      */   public Vector<String> getImportDirectoryList(User a_user)
/*      */     throws Bn2Exception
/*      */   {
/*  321 */     Vector vecDirList = new Vector();
/*      */ 
/*  324 */     File[] aDirs = getFileList(new IsDirectoryFilter(), a_user);
/*      */ 
/*  327 */     File fProcessedDir = null;
/*      */ 
/*  329 */     if (this.m_sProcessedDirectory != null)
/*      */     {
/*  331 */       fProcessedDir = new File(this.m_sProcessedDirectory);
/*      */     }
/*      */ 
/*  335 */     for (int i = 0; (aDirs != null) && (i < aDirs.length); i++)
/*      */     {
/*  337 */       File fCurrent = aDirs[i];
/*      */ 
/*  340 */       if ((fCurrent.equals(fProcessedDir)) || (fCurrent.isHidden()) || (fCurrent.getName().contains("files_for_existing_assets")))
/*      */       {
/*      */         continue;
/*      */       }
/*  344 */       vecDirList.add(fCurrent.getName());
/*      */     }
/*      */ 
/*  348 */     return vecDirList;
/*      */   }
/*      */ 
/*      */   public Vector<String> getImportZipFileList(User a_user)
/*      */     throws Bn2Exception
/*      */   {
/*  364 */     Vector vecList = new Vector();
/*      */ 
/*  367 */     File[] afZips = getFileList(new IsZipFileFilter(), a_user);
/*      */ 
/*  371 */     for (int i = 0; (afZips != null) && (i < afZips.length); i++)
/*      */     {
/*  373 */       File fCurrent = afZips[i];
/*  374 */       vecList.add(fCurrent.getName());
/*      */     }
/*      */ 
/*  377 */     return vecList;
/*      */   }
/*      */ 
/*      */   public Vector<String> getImportTopLevelFileList(User a_user, boolean a_bIncludeZips)
/*      */     throws Bn2Exception
/*      */   {
/*  394 */     Vector vecList = new Vector();
/*  395 */     File[] afFiles = null;
/*      */ 
/*  398 */     if (a_bIncludeZips)
/*      */     {
/*  400 */       afFiles = getFileList(new IsNormalFileFilter(), a_user);
/*      */     }
/*      */     else
/*      */     {
/*  404 */       afFiles = getFileList(new IsNonZipFileFilter(false), a_user);
/*      */     }
/*      */ 
/*  408 */     for (int i = 0; (afFiles != null) && (i < afFiles.length); i++)
/*      */     {
/*  410 */       File fCurrent = afFiles[i];
/*  411 */       vecList.add(fCurrent.getName());
/*      */     }
/*      */ 
/*  414 */     return vecList;
/*      */   }
/*      */ 
/*      */   public boolean getIdAppearsInImportFilenames(User a_user)
/*      */     throws Bn2Exception
/*      */   {
/*  426 */     Vector<String> vFiles = getImportTopLevelFileList(a_user, true);
/*  427 */     int iCheckCount = 0;
/*      */ 
/*  430 */     for (String filename : vFiles)
/*      */     {
/*  433 */       if (iCheckCount++ > 100)
/*      */       {
/*      */         break;
/*      */       }
/*  437 */       String sExt = FilenameUtils.getExtension(filename);
/*      */ 
/*  440 */       if ((StringUtils.isNotEmpty(sExt)) && (sExt.toLowerCase().equals("zip")))
/*      */       {
/*  442 */         ZipFile zip = null;
/*      */         try
/*      */         {
/*  445 */           zip = new ZipFile(getBulkUploadDirectory(a_user) + '/' + filename);
/*  446 */           Enumeration entries = zip.entries();
/*  447 */           int iZipCheckCount = 0;
/*  448 */           while (entries.hasMoreElements())
/*      */           {
/*  451 */             if (iZipCheckCount++ > 100)
/*      */             {
/*      */               break;
/*      */             }
/*  455 */             ZipEntry entry = (ZipEntry)entries.nextElement();
/*  456 */             String sEntryName = entry.getName();
/*      */ 
/*  459 */             if (sEntryName.matches(".*(\\A|\\.)[0-9]+(\\Z|\\.).*"))
/*      */             {
/*  461 */              return true;
    
                        // int i = 1;
/*      */             //  return i;
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/*  468 */           this.m_logger.error(getClass().getSimpleName() + "getIdAppearsInImportFilenames() : Could not open zip.", e);
/*      */         }
/*      */         finally
/*      */         {
/*  472 */           if (zip != null)
/*      */           {
/*      */             try
/*      */             {
/*  476 */               zip.close();
/*      */             }
/*      */             catch (IOException e) {
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/*  484 */         filename = FilenameUtils.getBaseName(filename);
/*      */ 
/*  487 */         if (filename.matches(".*(\\A|\\.)[0-9]+(\\Z|\\.).*"))
/*      */         {
/*  489 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*  493 */     return false;
/*      */   }
/*      */ 
/*      */   public int queueImport(BatchBulkUploadInfo a_batchInfo)
/*      */     throws Bn2Exception
/*      */   {
/*  513 */     int iFreeThreads = getFreeServicingThreadCount();
/*      */ 
/*  516 */     startBatchProcess(a_batchInfo.getUser().getId());
/*      */ 
/*  518 */     if (AssetBankSettings.getGetRelatedAssets())
/*      */     {
/*  521 */       this.m_mapLinkedAssetsForUsers.put(new Long(a_batchInfo.getUser().getId()), new Vector());
/*      */     }
/*      */ 
/*  526 */     this.m_assetWorkflowManager.startAssetUploadAlertBatch(a_batchInfo.getUser().getId());
/*      */ 
/*  528 */     int iQueueCount = queueItem(a_batchInfo);
/*      */ 
/*  530 */     if (iFreeThreads <= 0)
/*      */     {
/*  532 */       this.m_batchMonitor.addMessage(a_batchInfo.getUser().getId(), this.m_listManager.getListItem(null, "waitingQueuedImports", a_batchInfo.getUser().getLanguage()).getBody() + iQueueCount);
/*      */     }
/*      */ 
/*  535 */     return iQueueCount;
/*      */   }
/*      */ 
/*      */   public void processQueueItem(QueuedItem a_queuedItem)
/*      */     throws Bn2Exception
/*      */   {
/*  555 */     String ksMethodName = "processQueueItem";
/*      */ 
/*  558 */     BatchBulkUploadInfo batchInfo = null;
/*      */     try
/*      */     {
/*  562 */       batchInfo = (BatchBulkUploadInfo)a_queuedItem;
/*      */ 
/*  565 */       if (batchInfo.getFromZip())
/*      */       {
/*  567 */         importFromZip(batchInfo.getUser(), batchInfo.getAssetMetadata(), batchInfo.getDirectoryOrFileName(), batchInfo.getAssetAttributes(), batchInfo.getPopulateNameFromFilename(), batchInfo.getUserProfile().getSessionId(), false, batchInfo.getDeferThumbnailGeneration(), batchInfo.getChosenFileFormat(), batchInfo.getImportChildAssets(), batchInfo.getWorkflowUpdate(), batchInfo.isRemoveIdFromFilename());
/*      */       }
/*  581 */       else if (batchInfo.getImportFilesToExistingAssets())
/*      */       {
/*  583 */         importFilesToExistingAssets(batchInfo.getUser(), batchInfo.getAssetEntityId(), getBulkUploadDirectory(batchInfo.getUser()) + "/" + "files_for_existing_assets");
/*      */       }
/*  586 */       else if (batchInfo.getAddPlaceholders())
/*      */       {
/*  588 */         addPlaceholders(batchInfo.getNumAssetsToAdd(), batchInfo.getAssetMetadata(), batchInfo.getAssetAttributes(), batchInfo.getUser(), batchInfo.getUserProfile().getSessionId(), batchInfo.getWorkflowUpdate(), batchInfo.getSourcePeerId());
/*      */       }
/*      */       else
/*      */       {
/*  598 */         importFromDir(batchInfo.getUser(), batchInfo.getAssetMetadata(), batchInfo.getDirectoryOrFileName(), batchInfo.getAssetAttributes(), batchInfo.getPopulateNameFromFilename(), batchInfo.getProcessZipsAsAssets(), false, batchInfo.getUserProfile().getSessionId(), batchInfo.getDeferThumbnailGeneration(), batchInfo.getChosenFileFormat(), batchInfo.getImportChildAssets(), batchInfo.getWorkflowUpdate(), batchInfo.isRemoveIdFromFilename());
/*      */       }
/*      */ 
/*  614 */       AssetUtil.reindexParents(null, batchInfo.getAssetMetadata(), this.m_assetManager, this.m_searchManager);
/*      */ 
/*  617 */       if ((AssetBankSettings.getGetRelatedAssets()) && (batchInfo.getLinkAssets()))
/*      */       {
/*  619 */         linkAssets(batchInfo.getUser().getId());
/*      */       }
/*      */ 
/*  623 */       DBTransaction transaction = null;
/*      */       try
/*      */       {
/*  626 */         transaction = this.m_transactionManager.getNewTransaction();
/*  627 */         this.m_assetWorkflowManager.sendAssetUploadAlertBatch(transaction, batchInfo.getUser().getId());
/*      */       }
/*      */       finally
/*      */       {
/*  631 */         if (transaction != null)
/*      */         {
/*      */           try
/*      */           {
/*  635 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException se)
/*      */           {
/*  639 */             throw new Bn2Exception("ImportManager.processQueueItem - SQLException committing transaction", se);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  644 */       addMessage(batchInfo.getUser().getId(), this.m_listManager.getListItem(null, "finishedImport", batchInfo.getUser().getLanguage()).getBody());
/*      */     }
/*      */     catch (Throwable bn2e)
/*      */     {
/*  648 */       this.m_logger.error("ImportManager.processQueueItem - exception:", bn2e);
/*  649 */       throw new Bn2Exception("ImportManager.processQueueItem - exception:", bn2e);
/*      */     }
/*      */     finally
/*      */     {
/*  653 */       if (batchInfo != null)
/*      */       {
/*  655 */         endImport(batchInfo.getUser().getId());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void importFromDir(ABUser a_user, Asset a_imageMetadata, String a_sDirectoryName, Vector a_vecAssetAttributes, boolean a_bPopulateNameFromFilename, boolean a_bProcessZipsAsAssets, boolean a_bReplaceIfSameFilename, long a_lSessionId, boolean a_bDeferThumbnailGeneration, long a_lChosenFileFormat, boolean a_bImportChildAssets, WorkflowUpdate a_workflowUpdate, boolean a_bRemoveIdFromFilename)
/*      */   {
/*  692 */     String ksMethodName = "importFromDir";
/*      */     try
/*      */     {
/*  697 */       if (a_sDirectoryName == null)
/*      */       {
/*  699 */         throw new Bn2Exception("ImportManager.importFromDirThe directory name is null");
/*      */       }
/*      */ 
/*  703 */       String sFullpath = getBulkUploadDirectory(a_user) + "/" + a_sDirectoryName;
/*      */ 
/*  705 */       String sProcessedDir = null;
/*  706 */       if (!AssetBankSettings.getDeleteProcessedUploadFiles())
/*      */       {
/*  709 */         sProcessedDir = getProcessedDirectory();
/*      */       }
/*      */ 
/*  713 */       File fDirectory = new File(sFullpath);
/*      */ 
/*  716 */       if (!fDirectory.isDirectory())
/*      */       {
/*  718 */         throw new Bn2Exception("ImportManager.importFromDirThe path " + sFullpath + " is not a directory.");
/*      */       }
/*      */ 
/*  721 */       addMessage(a_user.getId(), this.m_listManager.getListItem(null, "importingFilesInDirectory", a_user.getLanguage()).getBody() + " " + a_sDirectoryName);
/*      */ 
/*  724 */       boolean bIsTopLevel = false;
/*  725 */       if ((a_sDirectoryName == null) || (a_sDirectoryName.length() == 0))
/*      */       {
/*  727 */         bIsTopLevel = true;
/*      */       }
/*      */ 
/*  731 */       importAllFromDir(fDirectory, a_user, a_imageMetadata, a_vecAssetAttributes, a_imageMetadata.getDescriptiveCategories(), sProcessedDir, bIsTopLevel, a_bPopulateNameFromFilename, a_bProcessZipsAsAssets, a_bReplaceIfSameFilename, a_lSessionId, null, a_bDeferThumbnailGeneration, a_lChosenFileFormat, a_bImportChildAssets, a_workflowUpdate, a_bRemoveIdFromFilename, false);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  753 */       addMessage(a_user.getId(), "Fatal error: " + e.getMessage());
/*  754 */       this.m_logger.error("Error in ImportManager.importFromDir", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void importAllFromDir(File a_fDir, ABUser a_user, Asset a_imageMetadata, Vector a_vecAssetAttributes, Vector a_vecDescriptiveCats, String a_sProcessedDir, boolean a_bIsTopLevel, boolean a_bPopulateNameFromFilename, boolean a_bProcessZipsAsAssets, boolean a_bReplaceIfSameFilename, long a_lSessionId, String a_sPossibleCategoryName, boolean a_bDeferThumbnailGeneration, long a_lChosenFileFormat, boolean a_bImportChildAssets, WorkflowUpdate a_workflowUpdate, boolean a_bRemoveIdFromFilename, boolean a_bIsWatchDirectory)
/*      */     throws Bn2Exception, FileNotFoundException, IOException
/*      */   {
/*  803 */     String ksMethodName = "importAllFromDir";
/*  804 */     String sProcessedSubDir = null;
/*      */ 
/*  807 */     if ((a_sProcessedDir != null) && (a_sProcessedDir.length() > 0))
/*      */     {
/*  810 */       sProcessedSubDir = a_sProcessedDir + "/" + a_fDir.getName();
/*      */ 
/*  812 */       File fProcessedSub = new File(sProcessedSubDir);
/*      */ 
/*  814 */       if (!fProcessedSub.exists())
/*      */       {
/*  816 */         if (!fProcessedSub.mkdir())
/*      */         {
/*  818 */           throw new Bn2Exception("ImportManager.importAllFromDir - could not create dir " + sProcessedSubDir);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  824 */     File[] aFiles = null;
/*      */     boolean bRedoDirectoryAtEnd;
/*      */     do
/*      */     {
/*  830 */       bRedoDirectoryAtEnd = false;
/*  831 */       if (a_bIsTopLevel)
/*      */       {
/*  834 */         if (a_bProcessZipsAsAssets)
/*      */         {
/*  836 */           aFiles = a_fDir.listFiles();
/*      */         }
/*      */         else
/*      */         {
/*  840 */           aFiles = a_fDir.listFiles(new IsNonZipFileFilter(true));
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  846 */         aFiles = a_fDir.listFiles();
/*      */       }
/*      */ 
/*  849 */       Arrays.sort(aFiles, new FileComparator());
/*      */ 
/*  851 */       AssetEntity chosenEntity = null;
/*      */ 
/*  854 */       if ((a_imageMetadata.getEntity() != null) && (a_imageMetadata.getEntity().getId() > 0L))
/*      */       {
/*  856 */         chosenEntity = this.m_assetEntityManager.getEntity(null, a_imageMetadata.getEntity().getId());
/*      */       }
/*      */ 
/*  860 */       Vector vOrgUnits = null;
/*  861 */       if ((AssetBankSettings.getOrgUnitUse()) && (!a_user.getIsAdmin()))
/*      */       {
/*  863 */         Collection alIds = CategoryUtil.uniqueIdsFromCategories(a_imageMetadata.getPermissionCategories());
/*  864 */         vOrgUnits = getOrgUnitsForDiskQuotaValidation(a_user, alIds);
/*      */       }
/*      */ 
/*  869 */       if ((!a_bIsWatchDirectory) && (AssetBankSettings.getBulkUploadMatchDirNamesToCats()))
/*      */       {
/*  872 */         if (a_vecDescriptiveCats != null)
/*      */         {
/*  874 */           a_imageMetadata.setDescriptiveCategories((Vector)a_vecDescriptiveCats.clone());
/*      */         }
/*  876 */         if (!a_bIsTopLevel)
/*      */         {
/*  879 */           a_sPossibleCategoryName = matchDirectoryNamesToCategories(a_sPossibleCategoryName, a_fDir, a_imageMetadata);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  884 */       HashMap hmCandidateSubstitutes = null;
/*  885 */       String sSubstitutePrefix = null;
/*      */ 
/*  887 */       if (AssetBankSettings.getAllowImageSubstitutes())
/*      */       {
/*  889 */         hmCandidateSubstitutes = new HashMap();
/*  890 */         sSubstitutePrefix = AssetBankSettings.getSubstituteFilePrefix();
/*      */ 
/*  893 */         for (int i = 0; i < aFiles.length; i++)
/*      */         {
/*  895 */           if (!aFiles[i].getName().startsWith(sSubstitutePrefix))
/*      */             continue;
/*  897 */           FileFormat format = this.m_assetManager.getFileFormatForExtension(null, FileUtil.getSuffix(aFiles[i].getName()));
/*      */ 
/*  899 */           if (format.getAssetTypeId() == 2L)
/*      */           {
/*  901 */             addMessage(a_user.getId(), "Found candidate working image " + aFiles[i].getName() + " - this will only be used if a corresponding original image is found");
/*      */ 
/*  903 */             hmCandidateSubstitutes.put(FileUtil.getFilepathWithoutSuffix(aFiles[i].getName().toLowerCase()), aFiles[i]);
/*      */           }
/*      */           else
/*      */           {
/*  907 */             addMessage(a_user.getId(), "Skipping file " + aFiles[i].getName() + " - this file has a 'working image' prefix but is not an image");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  914 */       for (int i = 0; i < aFiles.length; i++)
/*      */       {
/*  916 */         boolean bDatabaseError = false;
/*  917 */         AssetFileSource substituteSource = null;
/*      */ 
/*  919 */         a_imageMetadata.setDateAdded(new Date());
/*      */ 
/*  922 */         if (aFiles[i].isDirectory())
/*      */         {
/*  925 */           if (a_bIsTopLevel)
/*      */             continue;
/*  927 */           importAllFromDir(aFiles[i], a_user, a_imageMetadata, a_vecAssetAttributes, a_vecDescriptiveCats, sProcessedSubDir, false, a_bPopulateNameFromFilename, a_bProcessZipsAsAssets, a_bReplaceIfSameFilename, a_lSessionId, a_sPossibleCategoryName, a_bDeferThumbnailGeneration, a_lChosenFileFormat, a_bImportChildAssets, a_workflowUpdate, a_bRemoveIdFromFilename, false);
/*      */         }
/*      */         else
/*      */         {
/*  947 */           if (!aFiles[i].canRead())
/*      */           {
/*      */             continue;
/*      */           }
/*  951 */           boolean bFileError = false;
/*      */ 
/*  953 */           if (chosenEntity != null)
/*      */           {
/*  955 */             FileFormat format = this.m_assetManager.getFileFormatForFile(null, aFiles[i].getName(), a_lChosenFileFormat);
/*      */ 
/*  957 */             if (!chosenEntity.getAllowAssetType(format.getAssetTypeId()))
/*      */             {
/*  959 */               addMessage(a_user.getId(), "Skipping file " + aFiles[i].getName() + " - incorrect format for chosen type");
/*  960 */               bFileError = true;
/*  961 */               continue;
/*      */             }
/*      */           }
/*      */ 
/*  965 */           if (vOrgUnits != null)
/*      */           {
/*  967 */             if (this.m_orgUnitManager.isDiskQuotaExceededForAny(null, vOrgUnits, aFiles[i].length()))
/*      */             {
/*  969 */               addMessage(a_user.getId(), "Skipping file " + aFiles[i].getName() + " - Org Unit disk quota would be exceeded");
/*  970 */               continue;
/*      */             }
/*      */           }
/*      */ 
/*  974 */           if (!bFileError)
/*      */           {
/*  977 */             if (!exceedsUploadLimit(a_user, aFiles[i].length(), aFiles[i].getName()))
/*      */             {
/*  979 */               if (getSkipFilename(aFiles[i].getName()))
/*      */               {
/*  981 */                 addMessage(a_user.getId(), "Skipping (and deleting) unwanted file " + aFiles[i].getName());
/*      */               }
/*      */               else
/*      */               {
/*  985 */                 a_imageMetadata.setHasSubstituteFile(false);
/*  986 */                 a_imageMetadata.setOriginalFileLocation(null);
/*      */ 
/*  988 */                 AssetFileSource source = null;
/*      */ 
/*  991 */                 if (AssetBankSettings.getAllowImageSubstitutes())
/*      */                 {
/*  994 */                   if (aFiles[i].getName().startsWith(sSubstitutePrefix))
/*      */                   {
/*      */                     continue;
/*      */                   }
/*      */ 
/*  999 */                   String sNameToCheck = sSubstitutePrefix + FileUtil.getFilepathWithoutSuffix(aFiles[i].getName().toLowerCase());
/*      */ 
/* 1002 */                   if (hmCandidateSubstitutes.containsKey(sNameToCheck))
/*      */                   {
/* 1004 */                     File file = (File)hmCandidateSubstitutes.get(sNameToCheck);
/*      */ 
/* 1006 */                     FileFormat format = this.m_assetManager.getFileFormatForExtension(null, FileUtil.getSuffix(aFiles[i].getName()));
/*      */ 
/* 1008 */                     if ((format.getAssetTypeId() == 2L) && (format.getIsConvertable()))
/*      */                     {
/* 1010 */                       substituteSource = new AssetFileSource(file);
/* 1011 */                       a_imageMetadata.setHasSubstituteFile(true);
/* 1012 */                       addMessage(a_user.getId(), "Adding file " + aFiles[i].getName() + " with working version " + file.getName());
/*      */                     }
/*      */                   }
/*      */                 }
/*      */ 
/* 1017 */                 if (substituteSource == null)
/*      */                 {
/* 1019 */                   addMessage(a_user.getId(), this.m_listManager.getListItem(null, "addingFile", a_user.getLanguage()).getBody() + " " + aFiles[i].getName());
/*      */                 }
/*      */ 
/* 1022 */                 String sFilePathInUploadDir = null;
/* 1023 */                 String sStoredFileLocation = null;
/*      */ 
/* 1026 */                 if (AssetBankSettings.getDeleteProcessedUploadFiles())
/*      */                 {
/* 1029 */                   sFilePathInUploadDir = aFiles[i].getAbsolutePath();
/*      */ 
/* 1032 */                   sStoredFileLocation = this.m_fileStoreManager.addFileByMove(aFiles[i], StoredFileType.ASSET);
/*      */ 
/* 1035 */                   source = new AssetFileSource();
/* 1036 */                   source.setStoredFileLocation(sStoredFileLocation);
/*      */                 }
/*      */                 else
/*      */                 {
/* 1041 */                   source = new AssetFileSource(aFiles[i]);
/*      */                 }
/*      */ 
/* 1044 */                 source.setOriginalFilename(aFiles[i].getName());
/*      */ 
/* 1046 */                 if (a_bImportChildAssets)
/*      */                 {
/* 1049 */                   Attribute matchAttribute = this.m_attributeManager.getAttribute(null, chosenEntity.getMatchOnAttributeId());
/*      */ 
/* 1052 */                   a_imageMetadata.setParentAssetIdsAsString("");
/*      */ 
/* 1055 */                   SynchUtil.addAssetParents(this.m_attributeValueManager, a_imageMetadata, matchAttribute, FileUtil.getFilenameWithoutSuffix(aFiles[i].getName()));
/*      */ 
/* 1059 */                   if (!StringUtil.stringIsPopulated(a_imageMetadata.getParentAssetIdsAsString()))
/*      */                   {
/* 1061 */                     addMessage(a_user.getId(), "A parent match was not found so this file has been skipped " + aFiles[i].getName());
/* 1062 */                     continue;
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/*      */                 try
/*      */                 {
/* 1069 */                   a_vecAssetAttributes = updateAutoincrementValues(a_vecAssetAttributes, this.m_attributeManager);
/*      */ 
/* 1071 */                   saveAsset(a_user.getId(), a_imageMetadata, source, substituteSource, a_vecAssetAttributes, a_bPopulateNameFromFilename, a_bReplaceIfSameFilename, a_lSessionId, false, a_bDeferThumbnailGeneration, a_lChosenFileFormat, a_workflowUpdate, a_bRemoveIdFromFilename);
/*      */ 
/* 1085 */                   a_vecAssetAttributes = resetAutoincrementValues(a_vecAssetAttributes);
/*      */                 }
/*      */                 catch (Throwable t)
/*      */                 {
/* 1090 */                   bFileError = true;
/* 1091 */                   addMessage(a_user.getId(), "Import failure (file may be missing or corrupt) " + aFiles[i].getName());
/* 1092 */                   this.m_logger.error("Exception caught whilst importing file " + aFiles[i].getName());
/*      */ 
/* 1095 */                   if (sStoredFileLocation != null)
/*      */                   {
/* 1100 */                     File fFileToCopyBack = new File(this.m_fileStoreManager.getAbsolutePath(sStoredFileLocation));
/* 1101 */                     FileUtil.copyFile(fFileToCopyBack, new File(sFilePathInUploadDir));
/*      */                   }
/*      */ 
/* 1105 */                   if (!checkDatabaseAvailability(a_user.getId(), 120000L, 60))
/*      */                   {
/* 1110 */                     bDatabaseError = true;
/* 1111 */                     bRedoDirectoryAtEnd = true;
/*      */                   }
/*      */ 
/*      */                 }
/*      */                 finally
/*      */                 {
/* 1125 */                   if (source != null)
/*      */                   {
/* 1127 */                     source.close();
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/* 1134 */           if (bDatabaseError) {
/*      */             continue;
/*      */           }
/* 1137 */           if ((!bFileError) && (AssetBankSettings.getDeleteProcessedUploadFiles()))
/*      */           {
/* 1140 */             aFiles[i].delete();
/* 1141 */             FileUtil.logFileDeletion(aFiles[i]);
/*      */ 
/* 1145 */             if ((substituteSource == null) || (substituteSource.getFile() == null))
/*      */               continue;
/* 1147 */             substituteSource.getFile().delete();
/* 1148 */             FileUtil.logFileDeletion(substituteSource.getFile());
/*      */           }
/*      */           else {
/* 1151 */             if (sProcessedSubDir == null)
/*      */               continue;
/* 1153 */             this.m_logger.debug("Moving processed (or failed) import file to " + sProcessedSubDir + "/" + aFiles[i].getName());
/*      */ 
/* 1156 */             File fRename = new File(sProcessedSubDir + "/" + aFiles[i].getName());
/*      */ 
/* 1158 */             if (aFiles[i].renameTo(fRename))
/*      */               continue;
/* 1160 */             this.m_logger.error("Failed to move failed import file to " + sProcessedSubDir + "/" + aFiles[i].getName());
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1167 */     while (bRedoDirectoryAtEnd);
/*      */ 
/* 1172 */     if (!a_bIsTopLevel)
/*      */     {
/* 1174 */       a_fDir.delete();
/* 1175 */       FileUtil.logFileDeletion(a_fDir);
/*      */     }
/*      */   }
/*      */ 
/*      */   static Vector updateAutoincrementValues(Vector a_vecAttributes, AttributeManager a_attributeManager)
/*      */     throws Bn2Exception
/*      */   {
/* 1192 */     if (a_vecAttributes != null)
/*      */     {
/* 1194 */       for (int i = 0; i < a_vecAttributes.size(); i++)
/*      */       {
/* 1196 */         Attribute attribute = (Attribute)a_vecAttributes.elementAt(i);
/*      */ 
/* 1200 */         if (!attribute.getIsAutoincrement())
/*      */           continue;
/* 1202 */         if ((attribute.getValue() == null) || (attribute.getValue().getValue() == null) || (!attribute.getValue().getValue().equals("-1")))
/*      */           continue;
/* 1204 */         attribute.getValue().setValue(a_attributeManager.getNextAutoincrementValue(null, attribute.getId()));
/* 1205 */         a_vecAttributes.setElementAt(attribute, i);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1210 */     return a_vecAttributes;
/*      */   }
/*      */ 
/*      */   static Vector resetAutoincrementValues(Vector a_vecAttributes)
/*      */   {
/* 1224 */     if (a_vecAttributes != null)
/*      */     {
/* 1226 */       for (int i = 0; i < a_vecAttributes.size(); i++)
/*      */       {
/* 1228 */         Attribute attribute = (Attribute)a_vecAttributes.elementAt(i);
/*      */ 
/* 1230 */         if (!attribute.getIsAutoincrement())
/*      */         {
/*      */           continue;
/*      */         }
/* 1234 */         if ((attribute.getValue() == null) || (!StringUtil.stringIsPopulated(attribute.getValue().getValue())))
/*      */           continue;
/* 1236 */         attribute.getValue().setValue("-1");
/* 1237 */         a_vecAttributes.setElementAt(attribute, i);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1242 */     return a_vecAttributes;
/*      */   }
/*      */ 
/*      */   private boolean exceedsUploadLimit(ABUser a_user, long a_lFileSize, String a_sFilename)
/*      */   {
/* 1258 */     if ((AssetBankSettings.getMaxFileUploadSize() > 0) && (!a_user.getIsAdmin()) && (a_lFileSize / 1024L > AssetBankSettings.getMaxFileUploadSize()))
/*      */     {
/* 1261 */       addMessage(a_user.getId(), "File " + a_sFilename + " exceeds the maximum file upload size");
/* 1262 */       return true;
/*      */     }
/* 1264 */     return false;
/*      */   }
/*      */ 
/*      */   public void importFromZip(ABUser a_user, Asset a_imageMetadata, String a_sZipFilename, Vector a_vecAssetAttributes, boolean a_bPopulateNameFromFilename, long a_lSessionId, boolean a_bSynchronisation, boolean a_bDeferThumbnailGeneration, long a_lChosenFileFormat, boolean a_bImportChildAssets, WorkflowUpdate a_workflowUpdate, boolean a_bRemoveIdFromFilename)
/*      */   {
/* 1300 */     String ksMethodName = "importFromUploadedZip";
/*      */     try
/*      */     {
/* 1304 */       if (a_sZipFilename == null)
/*      */       {
/* 1306 */         throw new Bn2Exception("ImportManager.importFromUploadedZipThe zip file name is null");
/*      */       }
/*      */ 
/* 1310 */       String sZipFile = getBulkUploadDirectory(a_user) + "/" + a_sZipFilename;
/*      */ 
/* 1312 */       String sProcessedDir = getProcessedDirectory();
/*      */ 
/* 1315 */       ZipFile zipFile = new ZipFile(sZipFile);
/*      */ 
/* 1317 */       addMessage(a_user.getId(), "Importing files from zip file: " + a_sZipFilename);
/*      */ 
/* 1319 */       HashMap hmCandidateSubstitutes = null;
/* 1320 */       String sSubstitutePrefix = null;
/*      */ 
/* 1322 */       Enumeration zipEntries = zipFile.entries();
/*      */ 
/* 1324 */       if (AssetBankSettings.getAllowImageSubstitutes())
/*      */       {
/* 1326 */         hmCandidateSubstitutes = new HashMap();
/* 1327 */         sSubstitutePrefix = AssetBankSettings.getSubstituteFilePrefix();
/*      */ 
/* 1330 */         while (zipEntries.hasMoreElements())
/*      */         {
/* 1332 */           ZipEntry entry = (ZipEntry)zipEntries.nextElement();
/*      */ 
/* 1334 */           if (entry.getName().startsWith(sSubstitutePrefix))
/*      */           {
/* 1336 */             FileFormat format = this.m_assetManager.getFileFormatForExtension(null, FileUtil.getSuffix(entry.getName()));
/*      */ 
/* 1338 */             if (format.getAssetTypeId() == 2L)
/*      */             {
/* 1340 */               addMessage(a_user.getId(), "Found candidate working image " + entry.getName() + " - this will only be used if a corresponding original image is found");
/*      */ 
/* 1342 */               hmCandidateSubstitutes.put(FileUtil.getFilepathWithoutSuffix(entry.getName().toLowerCase()), entry);
/*      */             }
/*      */             else
/*      */             {
/* 1346 */               addMessage(a_user.getId(), "Skipping file " + entry.getName() + " - this file has a 'working image' prefix but is not an image");
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 1351 */         zipEntries = zipFile.entries();
/*      */       }
/*      */ 
/* 1355 */       List zipEntriesList = Collections.list(zipEntries);
/* 1356 */       Collections.sort(zipEntriesList, new ZipEntriesComparator());
/* 1357 */       zipEntries = Collections.enumeration(zipEntriesList);
/*      */ 
/* 1359 */       AssetEntity chosenEntity = null;
/*      */ 
/* 1362 */       if ((a_imageMetadata.getEntity() != null) && (a_imageMetadata.getEntity().getId() > 0L))
/*      */       {
/* 1364 */         chosenEntity = this.m_assetEntityManager.getEntity(null, a_imageMetadata.getEntity().getId());
/*      */       }
/*      */ 
/* 1368 */       Vector vOrgUnits = null;
/* 1369 */       if ((AssetBankSettings.getOrgUnitUse()) && (!a_user.getIsAdmin()))
/*      */       {
/* 1371 */         Collection alIds = CategoryUtil.uniqueIdsFromCategories(a_imageMetadata.getPermissionCategories());
/* 1372 */         vOrgUnits = getOrgUnitsForDiskQuotaValidation(a_user, alIds);
/*      */       }
/*      */ 
/* 1376 */       while (zipEntries.hasMoreElements())
/*      */       {
/* 1378 */         ZipEntry entry = (ZipEntry)zipEntries.nextElement();
/*      */ 
/* 1381 */         if (!entry.isDirectory())
/*      */         {
/* 1383 */           AssetFileSource source = null;
/* 1384 */           AssetFileSource substituteSource = null;
/*      */ 
/* 1386 */           String sFilename = FileUtil.getFilename(entry.getName().replaceAll("\\\\", "/"));
/*      */ 
/* 1388 */           a_imageMetadata.setHasSubstituteFile(false);
/* 1389 */           a_imageMetadata.setOriginalFileLocation(null);
/*      */ 
/* 1391 */           if (chosenEntity != null)
/*      */           {
/* 1393 */             FileFormat format = this.m_assetManager.getFileFormatForFile(null, sFilename);
/*      */ 
/* 1395 */             if (!chosenEntity.getAllowAssetType(format.getAssetTypeId()))
/*      */             {
/* 1397 */               addMessage(a_user.getId(), "Skipping file " + entry.getName() + " - incorrect format for chosen type");
/* 1398 */               continue;
/*      */             }
/*      */           }
/*      */ 
/* 1402 */           if (vOrgUnits != null)
/*      */           {
/* 1404 */             if (this.m_orgUnitManager.isDiskQuotaExceededForAny(null, vOrgUnits, entry.getSize()))
/*      */             {
/* 1406 */               addMessage(a_user.getId(), "Skipping file " + entry.getName() + " - Org Unit disk quota would be exceeded");
/* 1407 */               continue;
/*      */             }
/*      */           }
/*      */ 
/* 1411 */           a_imageMetadata.setDateAdded(new Date());
/*      */ 
/* 1414 */           if (!exceedsUploadLimit(a_user, entry.getSize(), entry.getName()))
/*      */           {
/* 1416 */             if (getSkipFilename(sFilename))
/*      */             {
/* 1418 */               addMessage(a_user.getId(), "Skipping (and deleting) unwanted file " + sFilename);
/*      */             }
/*      */             else
/*      */             {
/* 1424 */               if (AssetBankSettings.getAllowImageSubstitutes())
/*      */               {
/* 1427 */                 if (entry.getName().startsWith(sSubstitutePrefix))
/*      */                 {
/*      */                   continue;
/*      */                 }
/*      */ 
/* 1432 */                 String sNameToCheck = sSubstitutePrefix + FileUtil.getFilepathWithoutSuffix(entry.getName().toLowerCase());
/*      */ 
/* 1435 */                 if (hmCandidateSubstitutes.containsKey(sNameToCheck))
/*      */                 {
/* 1437 */                   ZipEntry substitute = (ZipEntry)hmCandidateSubstitutes.get(sNameToCheck);
/*      */ 
/* 1439 */                   FileFormat format = this.m_assetManager.getFileFormatForExtension(null, FileUtil.getSuffix(entry.getName()));
/*      */ 
/* 1441 */                   if ((format.getAssetTypeId() == 2L) && (format.getIsConvertable()))
/*      */                   {
/* 1443 */                     substituteSource = new AssetFileSource();
/* 1444 */                     substituteSource.setInputStream(zipFile.getInputStream(substitute));
/* 1445 */                     substituteSource.setFilename(FileUtil.getFilename(substitute.getName().replaceAll("\\\\", "/")));
/* 1446 */                     a_imageMetadata.setHasSubstituteFile(true);
/* 1447 */                     addMessage(a_user.getId(), "Adding file " + sFilename + " with working version " + substitute.getName());
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/* 1452 */               if (substituteSource == null)
/*      */               {
/* 1454 */                 addMessage(a_user.getId(), "Adding file " + entry.getName());
/*      */               }
/*      */ 
/* 1457 */               source = new AssetFileSource();
/* 1458 */               source.setInputStream(zipFile.getInputStream(entry));
/* 1459 */               source.setFilename(sFilename);
/* 1460 */               source.setOriginalFilename(sFilename);
/*      */ 
/* 1462 */               if (a_bImportChildAssets)
/*      */               {
/* 1465 */                 Attribute matchAttribute = this.m_attributeManager.getAttribute(null, chosenEntity.getMatchOnAttributeId());
/*      */ 
/* 1468 */                 a_imageMetadata.setParentAssetIdsAsString("");
/*      */ 
/* 1471 */                 SynchUtil.addAssetParents(this.m_attributeValueManager, a_imageMetadata, matchAttribute, FileUtil.getFilenameWithoutSuffix(sFilename));
/*      */               }
/*      */ 
/*      */               try
/*      */               {
/* 1478 */                 saveAsset(a_user.getId(), a_imageMetadata, source, substituteSource, a_vecAssetAttributes, a_bPopulateNameFromFilename, false, a_lSessionId, a_bSynchronisation, a_bDeferThumbnailGeneration, a_lChosenFileFormat, a_workflowUpdate, a_bRemoveIdFromFilename);
/*      */               }
/*      */               catch (Bn2Exception bn2e)
/*      */               {
/* 1494 */                 addMessage(a_user.getId(), "Import failure (file may be missing or corrupt) " + entry.getName());
/* 1495 */                 this.m_logger.error("ImportManager.importFromUploadedZipException caught for file: " + entry.getName());
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/* 1500 */           if (source != null)
/*      */           {
/* 1502 */             source.close();
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1508 */       zipFile.close();
/*      */ 
/* 1511 */       File fZipFile = new File(sZipFile);
/*      */ 
/* 1513 */       if (AssetBankSettings.getDeleteProcessedUploadFiles())
/*      */       {
/* 1515 */         fZipFile.delete();
/* 1516 */         FileUtil.logFileDeletion(fZipFile);
/*      */       }
/*      */       else
/*      */       {
/* 1521 */         File fRename = new File(sProcessedDir + "/" + fZipFile.getName());
/* 1522 */         fZipFile.renameTo(fRename);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (ZipException ze)
/*      */     {
/* 1528 */       addMessage(a_user.getId(), "Fatal error: " + ze.getMessage());
/* 1529 */       addMessage(a_user.getId(), "Has the zip file finished uploading?");
/* 1530 */       this.m_logger.error("Error in ImportManager.importFromUploadedZip", ze);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1536 */       addMessage(a_user.getId(), "Fatal error: " + e.getMessage());
/* 1537 */       this.m_logger.error("Error in ImportManager.importFromUploadedZip", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Vector getOrgUnitsForDiskQuotaValidation(ABUser a_user, Collection<Long> a_permissionCategoryIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1552 */     Vector vOrgUnits = null;
/* 1553 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/* 1554 */     vOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(transaction, a_user.getId());
/*      */     try
/*      */     {
/* 1558 */       transaction.commit();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1562 */       throw new Bn2Exception("ImportManager.getOrgUnitsForDiskQuotaValidation() : Could not commit transaction : " + e, e);
/*      */     }
/*      */ 
/* 1567 */     if (vOrgUnits.size() > 0)
/*      */     {
/* 1569 */       HashSet hsCatsToCheck = new HashSet(a_permissionCategoryIds);
/*      */ 
/* 1571 */       this.m_categoryManager.addAncestorsOfCategoryIds(a_permissionCategoryIds, hsCatsToCheck, 2L);
/*      */ 
/* 1573 */       for (int i = vOrgUnits.size() - 1; i >= 0; i--)
/*      */       {
/* 1575 */         OrgUnit orgUnit = (OrgUnit)vOrgUnits.get(i);
/*      */ 
/* 1577 */         if ((orgUnit.getDiskQuotaMb() > 0) && (hsCatsToCheck.contains(Long.valueOf(orgUnit.getCategory().getId()))))
/*      */           continue;
/* 1579 */         vOrgUnits.remove(i);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1584 */     return vOrgUnits;
/*      */   }
/*      */ 
/*      */   private void saveAsset(long a_lUserId, Asset a_assetMetadata, AssetFileSource a_source, AssetFileSource a_substituteSource, Vector a_vecAssetAttributes, boolean a_bPopulateNameFromFilename, boolean a_bReplaceIfSameFilename, long a_lSessionId, boolean a_bSynchronisation, boolean a_bDeferThumbnailGeneration, long a_lPreferredFileFormatId, WorkflowUpdate a_update, boolean a_bRemoveIdFromFilename)
/*      */     throws Bn2Exception
/*      */   {
/* 1622 */     DBTransaction dbTransaction = null;
/*      */     try
/*      */     {
/* 1627 */       a_vecAssetAttributes = AttributeUtil.copyAttributeVector(a_vecAssetAttributes);
/*      */ 
/* 1630 */       if (a_bSynchronisation)
/*      */       {
/* 1633 */         String sFilename = a_source.getFilename();
/* 1634 */         sFilename = sFilename.substring(0, sFilename.indexOf("."));
/* 1635 */         sFilename = sFilename.replaceAll("_synch", "");
/*      */         try
/*      */         {
/* 1640 */           long lId = Long.parseLong(sFilename);
/* 1641 */           a_assetMetadata.setId(lId);
/* 1642 */           a_source.setIsNewWithFixedId(true);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1646 */           this.m_logger.error("ImportManager.saveAsset: Unable to parse id from filename: " + sFilename);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1651 */       a_assetMetadata.setImportedAssetId(FileUtil.getFilenameWithoutSuffix(a_source.getOriginalFilename()));
/*      */ 
/* 1655 */       String sFormat = AssetBankSettings.getExportedAssetFilenameFormat();
/*      */ 
/* 1659 */       if ((a_bRemoveIdFromFilename) && (sFormat.indexOf('i') >= 0) && ((sFormat.indexOf('f') >= 0) || (sFormat.indexOf('t') >= 0)))
/*      */       {
/* 1664 */         String sFilename = removeIdFromFilename(sFormat, a_source.getFilename());
/*      */ 
/* 1667 */         if ((StringUtils.isNotEmpty(sFilename)) && (StringUtils.isNotEmpty(FilenameUtils.getBaseName(sFilename))))
/*      */         {
/* 1669 */           a_source.setFilename(sFilename);
/* 1670 */           sFilename = removeIdFromFilename(sFormat, a_source.getOriginalFilename());
/* 1671 */           a_source.setOriginalFilename(sFilename);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1677 */       Asset existingAsset = null;
/* 1678 */       if (!a_source.getIsNewWithFixedId())
/*      */       {
/* 1680 */         a_assetMetadata.setId(0L);
/* 1681 */         a_assetMetadata.setFileLocation(null);
/* 1682 */         a_assetMetadata.setThumbnailImageFile(null);
/* 1683 */         a_assetMetadata.setHomogenizedImageFile(null);
/* 1684 */         a_assetMetadata.setPreviewImageFile(null);
/*      */ 
/* 1686 */         if (a_bReplaceIfSameFilename)
/*      */         {
/* 1689 */           existingAsset = getExistingAsset(a_source.getFilename());
/*      */ 
/* 1691 */           if (existingAsset != null)
/*      */           {
/* 1694 */             a_assetMetadata = existingAsset;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1703 */       if (a_bPopulateNameFromFilename)
/*      */       {
/* 1705 */         if ((a_assetMetadata.getAttributeValues() != null) && (a_assetMetadata.getAttributeValues().size() > 0))
/*      */         {
/* 1709 */           for (int i = 0; i < a_assetMetadata.getAttributeValues().size(); i++)
/*      */           {
/* 1711 */             AttributeValue val = (AttributeValue)a_assetMetadata.getAttributeValues().elementAt(i);
/* 1712 */             if (!val.getAttribute().getIsNameAttribute()) {
/*      */               continue;
/*      */             }
/* 1715 */             String sNameFromFilename = null;
/* 1716 */             if (StringUtil.stringIsPopulated(a_source.getOriginalFilename()))
/*      */             {
/* 1718 */               sNameFromFilename = a_source.getOriginalFilename();
/*      */             }
/*      */             else
/*      */             {
/* 1722 */               sNameFromFilename = a_source.getFilename();
/*      */             }
/*      */ 
/* 1725 */             if (AssetBankSettings.getPopulateNameFromFilenameRemoveExt())
/*      */             {
/* 1727 */               sNameFromFilename = FileUtil.getFilenameWithoutSuffix(sNameFromFilename);
/*      */             }
/*      */ 
/* 1730 */             val.setValue(sNameFromFilename);
/* 1731 */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1738 */       a_assetMetadata.setDateLastModified(new Date());
/* 1739 */       a_assetMetadata.getLastModifiedByUser().setId(a_lUserId);
/*      */ 
/* 1742 */       this.m_agreementsManager.processAgreementsBeforeSave(dbTransaction, a_assetMetadata);
/*      */ 
/* 1744 */       AssetConversionInfo conversionInfo = new ImageConversionInfo();
/*      */ 
/* 1746 */       if (a_bDeferThumbnailGeneration)
/*      */       {
/* 1748 */         conversionInfo.setDeferThumbnailGeneration(true);
/*      */       }
/*      */ 
/* 1753 */       conversionInfo.setDoNotReEmbedMetadata(true);
/*      */ 
/* 1756 */       String sParentIds = a_assetMetadata.getParentAssetIdsAsString();
/*      */ 
/* 1759 */       Agreement agreement = a_assetMetadata.getAgreement();
/* 1760 */       Long lAgreementTypeId = Long.valueOf(a_assetMetadata.getAgreementTypeId());
/*      */ 
/* 1763 */       a_assetMetadata = this.m_assetManager.saveAsset(null, a_assetMetadata, a_source, a_lUserId, conversionInfo, a_substituteSource, false, 0, a_lPreferredFileFormatId, a_update);
/*      */ 
/* 1775 */       boolean bNeedReindex = false;
/* 1776 */       if (StringUtils.isNotEmpty(sParentIds))
/*      */       {
/* 1778 */         this.m_assetRelationshipManager.addAssetRelationships(dbTransaction, a_assetMetadata.getId(), sParentIds, 3L);
/*      */ 
/* 1781 */         bNeedReindex = true;
/*      */       }
/*      */ 
/* 1785 */       a_assetMetadata.setAgreement(agreement);
/* 1786 */       a_assetMetadata.setAgreementTypeId(lAgreementTypeId.longValue());
/* 1787 */       this.m_agreementsManager.processAgreementAfterSave(dbTransaction, a_assetMetadata, a_lUserId);
/*      */ 
/* 1791 */       if ((a_vecAssetAttributes != null) && (ImageUtil.canReadEmbeddedData(FileUtil.getSuffix(a_source.getFilename()))))
/*      */       {
/* 1794 */         if (this.m_attributeManager.populateAssetFromEmbeddedDataMappings(a_lUserId, a_assetMetadata.getId(), a_vecAssetAttributes))
/*      */         {
/* 1796 */           bNeedReindex = false;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1801 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 1804 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1806 */         this.m_assetLogManager.saveLog(a_assetMetadata.getId(), a_assetMetadata.getFileName(), dbTransaction, a_lUserId, new Date(), 2L, a_lSessionId, a_assetMetadata.getVersionNumber());
/*      */       }
/*      */ 
/* 1809 */       Asset newAsset = null;
/*      */ 
/* 1811 */       if (bNeedReindex)
/*      */       {
/* 1814 */         newAsset = this.m_assetManager.getAsset(dbTransaction, a_assetMetadata.getId(), null, false, true);
/*      */       }
/*      */ 
/* 1817 */       dbTransaction.commit();
/* 1818 */       dbTransaction = null;
/*      */ 
/* 1821 */       if (bNeedReindex)
/*      */       {
/* 1823 */         this.m_searchManager.indexDocument(newAsset, true);
/*      */       }
/*      */ 
/* 1827 */       if ((AssetBankSettings.getGetRelatedAssets()) && (this.m_mapLinkedAssetsForUsers.get(new Long(a_lUserId)) != null))
/*      */       {
/* 1829 */         ((Vector)this.m_mapLinkedAssetsForUsers.get(new Long(a_lUserId))).add(new Long(a_assetMetadata.getId()));
/*      */       }
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*      */       try
/*      */       {
/* 1836 */         this.m_logger.debug("About to roll back add image transaction");
/* 1837 */         if (dbTransaction != null)
/*      */         {
/* 1839 */           dbTransaction.rollback();
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (SQLException se)
/*      */       {
/*      */       }
/*      */ 
/* 1847 */       this.m_logger.error("Exception in ImportManager.addImage:", e);
/* 1848 */       throw new Bn2Exception("Exception in ImportManager.addImage:", e);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1855 */         if (dbTransaction != null)
/*      */         {
/* 1857 */           dbTransaction.commit();
/*      */         }
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1862 */         this.m_logger.error("Exception commiting transaction in ImportManager.addImage:", sqle);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private String removeIdFromFilename(String sExportFilenameFormat, String sFilename)
/*      */   {
/* 1876 */     String sDelim = AssetBankSettings.getExportedAssetFilenameFieldDelimiter();
/*      */ 
/* 1879 */     sFilename = sFilename.replace(sDelim, "/");
/*      */ 
/* 1881 */     if (sExportFilenameFormat.indexOf('i') == 0)
/*      */     {
/* 1883 */       sFilename = sFilename.replaceFirst("\\A[0-9]+/", "");
/*      */     }
/*      */     else
/*      */     {
/* 1887 */       sFilename = sFilename.replaceFirst("/[0-9]+\\.", ".");
/* 1888 */       sFilename = sFilename.replaceFirst("/[0-9]+/", "/");
/*      */     }
/*      */ 
/* 1891 */     sFilename = sFilename.replace("/", sDelim);
/* 1892 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public synchronized void linkAssets(long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1907 */     if (this.m_mapLinkedAssetsForUsers.get(new Long(a_lUserId)) != null)
/*      */     {
/* 1909 */       this.m_assetRelationshipManager.relateAssets(null, (Vector)this.m_mapLinkedAssetsForUsers.get(new Long(a_lUserId)), true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isImportInProgress(long a_lUserId)
/*      */   {
/* 1925 */     return isBatchInProgress(a_lUserId);
/*      */   }
/*      */ 
/*      */   protected void endImport(long a_lUserId)
/*      */   {
/* 1945 */     if (AssetBankSettings.getGetRelatedAssets())
/*      */     {
/* 1947 */       this.m_mapLinkedAssetsForUsers.remove(new Long(a_lUserId));
/*      */     }
/*      */ 
/* 1951 */     this.m_assetWorkflowManager.endAssetUploadAlertBatch(a_lUserId);
/*      */ 
/* 1953 */     endBatchProcess(a_lUserId);
/*      */   }
/*      */ 
/*      */   public String getBulkUploadDirectory(User a_user)
/*      */     throws Bn2Exception
/*      */   {
/* 1973 */     String ksMethodName = "getBulkUploadDirectory";
/*      */ 
/* 1975 */     if (this.m_sBulkUploadDirectory == null)
/*      */     {
/* 1977 */       throw new Bn2Exception("ImportManager.getBulkUploadDirectory - the bulk upload directory is not specified in the application settings file");
/*      */     }
/*      */ 
/* 1981 */     File fUploadDir = new File(this.m_sBulkUploadDirectory);
/*      */ 
/* 1983 */     if (!fUploadDir.exists())
/*      */     {
/* 1985 */       throw new Bn2Exception("ImportManager.getBulkUploadDirectoryThe import directory " + this.m_sBulkUploadDirectory + " does not exist");
/*      */     }
/*      */ 
/* 1989 */     String sSubDir = getUserSubDirectoryName(a_user);
/* 1990 */     String sSubDirFullPath = this.m_sBulkUploadDirectory + "/" + sSubDir;
/*      */ 
/* 1992 */     File fUploadSubDir = new File(sSubDirFullPath);
/*      */ 
/* 1994 */     if (!fUploadSubDir.exists())
/*      */     {
/* 1997 */       if (!AssetBankSettings.getUseFTP())
/*      */       {
/* 1999 */         if (!fUploadSubDir.mkdir())
/*      */         {
/* 2001 */           throw new Bn2Exception("ImportManager.getBulkUploadDirectory - could not create an upload directory for user at: " + sSubDirFullPath);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/* 2009 */           Bn2FTP ftp = new Bn2FTP(AssetBankSettings.getFTPHost(), AssetBankSettings.getFTPPort());
/* 2010 */           ftp.connect();
/* 2011 */           ftp.authenticate(AssetBankSettings.getFTPUsername(), AssetBankSettings.getFTPPassword());
/*      */ 
/* 2014 */           if ((AssetBankSettings.getFTPChangeDirectory() != null) && (AssetBankSettings.getFTPChangeDirectory().length() > 0))
/*      */           {
/* 2016 */             ftp.changeDir(AssetBankSettings.getFTPChangeDirectory());
/*      */           }
/*      */ 
/* 2020 */           if (!ftp.exists(sSubDir))
/*      */           {
/* 2022 */             ftp.makeDir(sSubDir);
/*      */           }
/* 2024 */           ftp.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2028 */           throw new Bn2Exception("ImportManager.getBulkUploadDirectory - could not create subdirectory " + sSubDir + " using FTP: " + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2033 */     return sSubDirFullPath;
/*      */   }
/*      */ 
/*      */   public String getUserSubDirectoryName(User a_user)
/*      */   {
/* 2049 */     return FileUtil.getSafeFilename(a_user.getUsername(), true);
/*      */   }
/*      */ 
/*      */   private String getProcessedDirectory()
/*      */     throws Bn2Exception
/*      */   {
/* 2066 */     String ksMethodName = "getProcessedDirectory";
/*      */ 
/* 2068 */     if (this.m_sProcessedDirectory == null)
/*      */     {
/* 2070 */       throw new Bn2Exception("ImportManager.getProcessedDirectory - the 'processed' directory name is not specified in the application settings file");
/*      */     }
/*      */ 
/* 2074 */     File fDir = new File(this.m_sProcessedDirectory);
/*      */ 
/* 2076 */     if (!fDir.exists())
/*      */     {
/* 2078 */       if (!fDir.mkdir())
/*      */       {
/* 2080 */         throw new Bn2Exception("ImportManager.getProcessedDirectory - could not create the 'processed' directory at: " + this.m_sProcessedDirectory);
/*      */       }
/*      */     }
/*      */ 
/* 2084 */     return this.m_sProcessedDirectory;
/*      */   }
/*      */ 
/*      */   public void checkWatchDirectories()
/*      */   {
/* 2104 */     if (!obtainWatchImportLock())
/*      */     {
/* 2106 */       return;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2112 */       int iDirIndex = 0;
/* 2113 */       while (iDirIndex < AssetBankSettings.getWatchDirectoryCount())
/*      */       {
/* 2118 */         String sWatchDirectory = AssetBankSettings.getWatchDirectory(iDirIndex);
/*      */ 
/* 2121 */         boolean bFirstTime = false;
/* 2122 */         if (this.m_hmWatchedFiles == null)
/*      */         {
/* 2124 */           bFirstTime = true;
/* 2125 */           this.m_hmWatchedFiles = new HashMap();
/*      */         }
/*      */ 
/* 2128 */         File fWatchDir = new File(sWatchDirectory);
/*      */ 
/* 2131 */         if (!fWatchDir.exists()) {
/* 2133 */           this.m_logger.error("ImportManager.checkWatchDirectory: the watch directory " + sWatchDirectory + " does not exist, or is not accessible by the application");
/*      */           return;
/*      */         }
/* 2137 */         boolean bSendEmail = false;
/* 2138 */         Vector vecChangedDirs = new Vector();
/*      */ 
/* 2144 */         if (AssetBankSettings.getWatchDirectoryAutoAdd())
/*      */         {
/* 2147 */           processWatchDirectories(fWatchDir, iDirIndex, "");
/*      */         }
/*      */         else
/*      */         {
/* 2154 */           if ((newFilesPresent(fWatchDir, null, vecChangedDirs)) && (!bFirstTime))
/*      */           {
/* 2156 */             bSendEmail = true;
/*      */           }
/*      */ 
/* 2160 */           Iterator it = this.m_hmWatchedFiles.keySet().iterator();
/*      */ 
/* 2162 */           String sId = null;
/* 2163 */           File fFile = null;
/*      */ 
/* 2165 */           while (it.hasNext())
/*      */           {
/* 2168 */             sId = (String)it.next();
/*      */ 
/* 2171 */             fFile = new File(sWatchDirectory + "/" + sId);
/*      */ 
/* 2173 */             if (fFile.exists()) {
/*      */               continue;
/*      */             }
/* 2176 */             it.remove();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2182 */         if (bSendEmail)
/*      */         {
/*      */           try
/*      */           {
/* 2187 */             String sAdminEmailAddresses = this.m_userManager.getAdminEmailAddresses();
/*      */ 
/* 2190 */             String sDirsDesc = null;
/*      */ 
/* 2192 */             for (int i = 0; i < vecChangedDirs.size(); i++)
/*      */             {
/* 2194 */               if (sDirsDesc == null)
/*      */               {
/* 2196 */                 sDirsDesc = "";
/*      */               }
/*      */               else
/*      */               {
/* 2200 */                 sDirsDesc = sDirsDesc + ", ";
/*      */               }
/*      */ 
/* 2203 */               sDirsDesc = sDirsDesc + (String)vecChangedDirs.get(i);
/*      */             }
/*      */ 
/* 2206 */             HashMap params = new HashMap();
/* 2207 */             params.put("template", "new_watched_files");
/* 2208 */             params.put("directories", sDirsDesc);
/* 2209 */             params.put("adminEmailAddresses", sAdminEmailAddresses);
/*      */ 
/* 2211 */             this.m_emailManager.sendTemplatedEmail(params, LanguageConstants.k_defaultLanguage);
/*      */           }
/*      */           catch (Bn2Exception e)
/*      */           {
/* 2216 */             this.m_logger.error("ImportManager.checkWatchDirectory: the alert email was not successfully sent", e);
/*      */           }
/*      */         }
/* 2114 */         iDirIndex++;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/* 2223 */       this.m_logger.error("ImportManager.checkWatchDirectory: Error auto importing from watch directory", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2227 */       releaseWatchImportLock();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void processWatchDirectories(File a_fWatchDir, int a_iDirIndex, String a_sCategoryName)
/*      */   {
/* 2243 */     boolean bFilesToProcess = false;
/*      */ 
/* 2246 */     String sProcessDir = a_fWatchDir.getAbsolutePath() + "/" + "_process_";
/*      */ 
/* 2248 */     File fProcessDir = new File(sProcessDir);
/*      */ 
/* 2250 */     if (!fProcessDir.exists())
/*      */     {
/* 2252 */       if (!fProcessDir.mkdir())
/*      */       {
/* 2254 */         this.m_logger.error("ImportManager.checkWatchDirectory: could not create processed directory (" + sProcessDir + "): aborting");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2259 */     File[] aFiles = a_fWatchDir.listFiles();
/*      */ 
/* 2261 */     if (aFiles != null)
/*      */     {
/* 2263 */       for (int i = 0; i < aFiles.length; i++)
/*      */       {
/* 2267 */         if ((aFiles[i].isFile()) && (aFiles[i].lastModified() < System.currentTimeMillis() - 30000L))
/*      */         {
/* 2270 */           File fNewName = new File(sProcessDir + "/" + aFiles[i].getName());
/* 2271 */           aFiles[i].renameTo(fNewName);
/* 2272 */           bFilesToProcess = true;
/*      */         }
/*      */         else {
/* 2275 */           if ((!aFiles[i].isDirectory()) || (aFiles[i].lastModified() >= System.currentTimeMillis() - 30000L) || (aFiles[i].getName().equalsIgnoreCase("_process_")))
/*      */           {
/*      */             continue;
/*      */           }
/*      */ 
/* 2280 */           if ((!a_sCategoryName.equals("")) && (!a_sCategoryName.endsWith("/")))
/*      */           {
/* 2282 */             a_sCategoryName = a_sCategoryName + "/";
/*      */           }
/*      */ 
/* 2285 */           processWatchDirectories(aFiles[i], a_iDirIndex, a_sCategoryName + aFiles[i].getName());
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2292 */       this.m_logger.error("ImportManager.processWatchDirectories: check file permissions - could not process directory:" + a_fWatchDir.getAbsolutePath());
/*      */     }
/*      */ 
/* 2296 */     if (bFilesToProcess)
/*      */     {
/*      */       try
/*      */       {
/* 2302 */         ABUser user = this.m_userManager.getApplicationUser();
/*      */ 
/* 2304 */         Vector vecAllAttributes = this.m_assetManager.getAssetAttributes(null, null);
/* 2305 */         Asset metaData = new Asset();
/* 2306 */         metaData.setDateAdded(new Date());
/* 2307 */         metaData.setIsUnsubmitted(false);
/*      */ 
/* 2311 */         if (((AssetBankSettings.getMapWatchSubdirectoriesToCategories()) || (AssetBankSettings.getMapWatchSubdirectoriesToAccessLevels())) && (!a_sCategoryName.equals("")))
/*      */         {
/* 2314 */           DBTransaction transaction = null;
/*      */           try
/*      */           {
/* 2321 */             transaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 2324 */             if (AssetBankSettings.getMapWatchSubdirectoriesToCategories())
/*      */             {
/* 2326 */               Vector vecCategories = new Vector();
/* 2327 */               Category cat = this.m_categoryManager.ensureCategoryExists(transaction, 1L, a_sCategoryName, false);
/* 2328 */               vecCategories.add(cat);
/* 2329 */               metaData.setDescriptiveCategories(vecCategories);
/*      */             }
/* 2331 */             else if (AssetBankSettings.getMapWatchSubdirectoriesToAccessLevels())
/*      */             {
/* 2333 */               Vector vecCategories = new Vector();
/* 2334 */               Category cat = this.m_categoryManager.ensureCategoryExists(transaction, 2L, a_sCategoryName, true);
/* 2335 */               vecCategories.add(cat);
/* 2336 */               metaData.setPermissionCategories(vecCategories);
/*      */             }
/*      */ 
/*      */           }
/*      */           finally
/*      */           {
/* 2342 */             if (transaction != null)
/*      */             {
/*      */               try
/*      */               {
/* 2346 */                 transaction.commit();
/*      */               }
/*      */               catch (SQLException se)
/*      */               {
/* 2350 */                 throw new Bn2Exception("ImportManager.processWatchDirectories - SQLException committing transaction", se);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2364 */         String sCatIds = AssetBankSettings.getWatchDirectoryAccessLevels(a_iDirIndex);
/*      */ 
/* 2366 */         Vector vecCats = CategoryUtil.getCategoriesFromIds(getCategoryManager(), 2L, sCatIds);
/*      */ 
/* 2370 */         metaData.setPermissionCategories(vecCats);
/*      */ 
/* 2374 */         WorkflowUpdate update = new WorkflowUpdate(user.getId(), -1L);
/* 2375 */         update.setUpdateType(1);
/* 2376 */         update.setSetSubmitted(true);
/*      */ 
/* 2378 */         this.m_attributeManager.populateMetadataDefaults(metaData, vecAllAttributes, true);
/*      */ 
/* 2382 */         importAllFromDir(fProcessDir, user, metaData, vecAllAttributes, null, getProcessedDirectory(), true, false, true, true, 0L, null, false, 0L, false, update, false, true);
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/* 2404 */         this.m_logger.error("ImportManager.checkWatchDirectory: Error auto importing from watch directory", e);
/*      */       }
/*      */       finally
/*      */       {
/* 2408 */         releaseWatchImportLock();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean newFilesPresent(File a_fDir, String a_sFolderId, Vector a_vecChangedDirs)
/*      */   {
/* 2434 */     File[] aFiles = a_fDir.listFiles();
/*      */ 
/* 2436 */     boolean bHasNew = false;
/* 2437 */     boolean bLoggedAlready = false;
/* 2438 */     String sId = null;
/*      */ 
/* 2441 */     for (int i = 0; (aFiles != null) && (i < aFiles.length); i++)
/*      */     {
/* 2444 */       if (a_sFolderId != null)
/*      */       {
/* 2446 */         sId = a_sFolderId + "/";
/*      */       }
/*      */       else
/*      */       {
/* 2450 */         sId = "";
/*      */       }
/*      */ 
/* 2453 */       sId = sId + aFiles[i].getName();
/*      */ 
/* 2455 */       if (aFiles[i].isDirectory())
/*      */       {
/* 2458 */         if (!newFilesPresent(aFiles[i], sId, a_vecChangedDirs))
/*      */         {
/*      */           continue;
/*      */         }
/* 2462 */         bHasNew = true;
/*      */       }
/*      */       else
/*      */       {
/* 2468 */         if (this.m_hmWatchedFiles.containsKey(sId)) {
/*      */           continue;
/*      */         }
/* 2471 */         bHasNew = true;
/*      */ 
/* 2474 */         this.m_hmWatchedFiles.put(sId, null);
/*      */ 
/* 2476 */         if (bLoggedAlready) {
/*      */           continue;
/*      */         }
/* 2479 */         String sFolderDesc = null;
/*      */ 
/* 2481 */         if (a_sFolderId != null)
/*      */         {
/* 2483 */           sFolderDesc = a_sFolderId;
/*      */         }
/*      */         else
/*      */         {
/* 2487 */           sFolderDesc = "top-level";
/*      */         }
/*      */ 
/* 2490 */         a_vecChangedDirs.add(sFolderDesc);
/* 2491 */         bLoggedAlready = true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2499 */     return bHasNew;
/*      */   }
/*      */ 
/*      */   private boolean getSkipFilename(String a_sFilename)
/*      */   {
/* 2515 */     String sRegExp = AssetBankSettings.getBulkUploadFileSkip();
/*      */ 
/* 2517 */     if ((sRegExp != null) && (sRegExp.length() > 0))
/*      */     {
/* 2519 */       if (a_sFilename.matches(sRegExp))
/*      */       {
/* 2521 */         return true;
/*      */       }
/*      */     }
/*      */ 
/* 2525 */     return false;
/*      */   }
/*      */ 
/*      */   private boolean obtainWatchImportLock()
/*      */   {
/* 2541 */     synchronized (this.m_oWatchImportLock)
/*      */     {
/* 2543 */       if (this.m_bWatchImportInProgress)
/*      */       {
/* 2545 */         return false;
/*      */       }
/*      */ 
/* 2548 */       this.m_bWatchImportInProgress = true;
/*      */     }
/*      */ 
/* 2551 */     return true;
/*      */   }
/*      */ 
/*      */   private void releaseWatchImportLock()
/*      */   {
/* 2564 */     synchronized (this.m_oWatchImportLock)
/*      */     {
/* 2566 */       this.m_bWatchImportInProgress = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   private Asset getExistingAsset(String sFilename)
/*      */     throws Bn2Exception
/*      */   {
/* 2585 */     Asset asset = null;
/* 2586 */     SearchCriteria searchCriteria = new SearchCriteria();
/* 2587 */     searchCriteria.setFilename(sFilename);
/* 2588 */     searchCriteria.setMaxNoOfResults(1000);
/*      */ 
/* 2590 */     SearchResults searchResults = this.m_searchManager.search(searchCriteria);
/*      */ 
/* 2593 */     int iNumMatches = 0;
/* 2594 */     int iResIndex = 0;
/* 2595 */     while (iResIndex < searchResults.getSearchResults().size())
/*      */     {
/* 2599 */       LightweightAsset tempAsset = (LightweightAsset)searchResults.getSearchResults().get(iResIndex);
/*      */ 
/* 2602 */       Asset candidateAsset = this.m_assetManager.getAsset(null, tempAsset.getId(), null, true, false);
/*      */ 
/* 2607 */       if (candidateAsset.getFileName().equalsIgnoreCase(sFilename))
/*      */       {
/* 2609 */         asset = candidateAsset;
/* 2610 */         iNumMatches++;
/*      */       }
/* 2596 */       iResIndex++;
/*      */     }
/*      */ 
/* 2614 */     if (iNumMatches > 1)
/*      */     {
/* 2616 */       this.m_logger.error("ImportManager.getExistingAsset: there are multiple assets with filename " + sFilename + " - using one of them");
/*      */     }
/*      */ 
/* 2619 */     return asset;
/*      */   }
/*      */ 
/*      */   public void deletePartiallyUploadedFiles(String a_sUserName)
/*      */   {
/* 2641 */     File UploadDirectory = new File(this.m_sBulkUploadDirectory + "/" + a_sUserName);
/*      */ 
/* 2644 */     File[] children = UploadDirectory.listFiles();
/* 2645 */     if (children != null)
/*      */     {
/* 2647 */       for (int i = 0; i < children.length; i++)
/*      */       {
/* 2649 */         if ((children[i] == null) || (!children[i].isFile()) || (children[i].getName() == null) || (FileUtil.getSuffix(children[i].getName()) == null) || ((!FileUtil.getSuffix(children[i].getName()).equals("part")) && (!FileUtil.getSuffix(children[i].getName()).equals("tmp"))))
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/* 2656 */         children[i].delete();
/* 2657 */         FileUtil.logFileDeletion(children[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean checkDatabaseAvailability(long a_lUserId, long a_lWaitTime, int a_NumRetries)
/*      */   {
/* 2677 */     boolean bErrorThisTime = false;
/* 2678 */     boolean bWasError = false;
/* 2679 */     int iCount = 0;
/*      */ 
/* 2681 */     DBTransaction transaction = null;
/*      */     do
/*      */     {
/*      */       try
/*      */       {
/* 2688 */         iCount++;
/* 2689 */         transaction = this.m_transactionManager.getNewTransaction();
/* 2690 */         bErrorThisTime = false;
/*      */       }
/*      */       catch (Bn2Exception se)
/*      */       {
/* 2694 */         bWasError = true;
/* 2695 */         bErrorThisTime = true;
/*      */       }
/*      */       finally
/*      */       {
/* 2699 */         if (transaction != null)
/*      */         {
/*      */           try
/*      */           {
/* 2703 */             transaction.commit();
/*      */           }
/*      */           catch (Throwable se)
/*      */           {
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2713 */       if (!bErrorThisTime)
/*      */         continue;
/*      */       try
/*      */       {
/* 2717 */         addMessage(a_lUserId, "Database connectivity error. Pausing for " + a_lWaitTime + " millisecs before retrying...");
/* 2718 */         Thread.sleep(a_lWaitTime);
/*      */       }
/*      */       catch (InterruptedException ie)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2726 */     while ((bErrorThisTime) && (iCount < a_NumRetries));
/*      */ 
/* 2728 */     return !bWasError;
/*      */   }
/*      */ 
/*      */   public void importFilesToExistingAssets(ABUser a_user, long a_lAssetTypeId, String a_sDirectory)
/*      */     throws Bn2Exception
/*      */   {
/* 2745 */     String ksMethodName = "importFilesToExistingAssets";
/*      */ 
/* 2754 */     ImageMagick.clearCaches();
/*      */ 
/* 2757 */     AssetEntity entity = this.m_assetEntityManager.getEntity(null, a_lAssetTypeId);
/*      */ 
/* 2759 */     Attribute matchAttribute = this.m_attributeManager.getAttribute(null, entity.getMatchOnAttributeId());
/*      */ 
/* 2762 */     String sFullpath = a_sDirectory;
/*      */ 
/* 2765 */     File fDirectory = new File(sFullpath);
/*      */ 
/* 2768 */     if (!fDirectory.isDirectory())
/*      */     {
/* 2770 */       throw new Bn2Exception("ImportManager.importFilesToExistingAssetsThe path " + sFullpath + " is not a directory.");
/*      */     }
/*      */ 
/* 2773 */     addMessage(a_user.getId(), "Importing files to existing assets");
/*      */ 
/* 2776 */     File[] aFiles = fDirectory.listFiles();
/*      */ 
/* 2778 */     for (int i = 0; i < aFiles.length; i++)
/*      */     {
/* 2780 */       if (!(aFiles[i] != null & aFiles[i].canRead()))
/*      */         continue;
/* 2782 */       if (getSkipFilename(aFiles[i].getName()))
/*      */       {
/* 2784 */         addMessage(a_user.getId(), "Skipping unwanted file " + aFiles[i].getName());
/*      */       }
/*      */       else
/*      */       {
/* 2789 */         String sFileName = FileUtil.getFilenameWithoutSuffix(aFiles[i].getName());
/*      */ 
/* 2791 */         SearchCriteria criteria = new SearchCriteria();
/*      */ 
/* 2793 */         Vector attributeSearches = new Vector();
/*      */ 
/* 2795 */         AttributeValue attVal = new AttributeValue();
/* 2796 */         attVal.setAttribute(matchAttribute);
/* 2797 */         attVal.setValue(sFileName);
/*      */ 
/* 2799 */         attributeSearches.add(attVal);
/*      */ 
/* 2801 */         criteria.addAssetEntityIdToInclude(a_lAssetTypeId);
/* 2802 */         criteria.setAttributeSearches(attributeSearches);
/*      */ 
/* 2804 */         SearchResults searchResults = this.m_searchManager.search(criteria);
/*      */ 
/* 2808 */         if (searchResults.getNumResults() > 0)
/*      */         {
/* 2810 */           Vector results = searchResults.getSearchResults();
/*      */ 
/* 2813 */           LightweightAsset lightweightAsset = (LightweightAsset)results.firstElement();
/*      */ 
/* 2815 */           Asset asset = this.m_assetManager.getAsset(null, lightweightAsset.getId(), null, false, false);
/*      */ 
/* 2817 */           long lAssetFormatTypeId = this.m_assetManager.getFileFormatForFile(null, aFiles[i].getAbsolutePath()).getAssetTypeId();
/*      */ 
/* 2820 */           if ((asset.getFormat().getAssetTypeId() > 0L) && (lAssetFormatTypeId != asset.getFormat().getAssetTypeId()))
/*      */           {
/* 2822 */             addMessage(a_user.getId(), "Skipping file as it is the incorrect type " + aFiles[i].getName());
/*      */           }
/*      */           else
/*      */           {
/*      */             try
/*      */             {
/* 2832 */               AssetFileSource source = new AssetFileSource();
/*      */ 
/* 2834 */               String sStoredFileLocation = this.m_fileStoreManager.addFileByMove(aFiles[i], StoredFileType.ASSET);
/*      */ 
/* 2836 */               source.setOriginalFilename(sFileName);
/* 2837 */               source.setStoredFileLocation(sStoredFileLocation);
/*      */ 
/* 2840 */               boolean bRegenerateThumbnails = false;
/*      */ 
/* 2842 */               if ((lAssetFormatTypeId == 2L) || (lAssetFormatTypeId == 3L))
/*      */               {
/* 2844 */                 bRegenerateThumbnails = true;
/*      */               }
/*      */ 
/* 2848 */               asset = this.m_assetManager.saveAsset(null, asset, source, a_user.getId(), null, null, bRegenerateThumbnails, 0, lAssetFormatTypeId, null);
/*      */ 
/* 2858 */               source.close();
/*      */ 
/* 2860 */               addMessage(a_user.getId(), "Adding file " + aFiles[i].getName());
/*      */             }
/*      */             catch (IOException ioe)
/*      */             {
/* 2865 */               this.m_logger.error("IOException in importFilesToExistingAssets:", ioe);
/* 2866 */               throw new Bn2Exception("IOException in importFilesToExistingAssets ", ioe);
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2872 */           addMessage(a_user.getId(), "No match found for file " + aFiles[i].getName());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addPlaceholders(int a_iNumAssetsToAdd, Asset a_metadata, Vector a_vecAssetAttributes, ABUser a_user, long a_lSessionId, WorkflowUpdate a_update, long a_lSourcePeerId)
/*      */     throws Bn2Exception
/*      */   {
/* 2900 */     String ksMethodName = "addPlaceholders";
/*      */ 
/* 2902 */     int iMaxToShow = 10;
/* 2903 */     int iMaxHalved = iMaxToShow / 2;
/*      */ 
/* 2905 */     String sMessage = "Adding " + a_iNumAssetsToAdd + " assets";
/*      */ 
/* 2907 */     if (a_iNumAssetsToAdd > iMaxToShow)
/*      */     {
/* 2909 */       sMessage = sMessage + " of which the first and last " + iMaxHalved + " are shown below";
/*      */     }
/* 2911 */     sMessage = sMessage + ":";
/*      */ 
/* 2913 */     addMessage(a_user.getId(), sMessage);
/*      */ 
/* 2916 */     Map autoIncValues = new HashMap();
/*      */ 
/* 2918 */     if (a_vecAssetAttributes != null)
/*      */     {
/* 2921 */       Vector vEntityAttIds = null;
/* 2922 */       if (a_metadata.getEntity().getId() > 0L)
/*      */       {
/* 2924 */         vEntityAttIds = this.m_assetEntityManager.getAttributeIdsForEntity(null, a_metadata.getEntity().getId(), true);
/*      */       }
/*      */ 
/* 2927 */       for (int i = 0; i < a_vecAssetAttributes.size(); i++)
/*      */       {
/* 2929 */         Attribute attribute = (Attribute)a_vecAssetAttributes.get(i);
/*      */ 
/* 2932 */         if ((!attribute.getIsAutoincrement()) || ((vEntityAttIds != null) && (!vEntityAttIds.contains(Long.valueOf(attribute.getId())))))
/*      */           continue;
/* 2934 */         List values = this.m_attributeManager.getNextAutoincrementValues(null, attribute.getId(), a_iNumAssetsToAdd);
/* 2935 */         autoIncValues.put(Integer.valueOf(i), values);
/* 2936 */         addMessage(a_user.getId(), "Reserving '" + attribute.getLabel() + "' values: " + (String)values.get(0) + " to " + (String)values.get(values.size() - 1));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2942 */     for (int iCount = 0; iCount < a_iNumAssetsToAdd; iCount++)
/*      */     {
/* 2944 */       boolean bShowMessage = (iCount < iMaxHalved) || (iCount > a_iNumAssetsToAdd - (iMaxHalved + 1));
/* 2945 */       boolean bShowDots = (a_iNumAssetsToAdd > iMaxToShow) && (iCount == iMaxHalved);
/*      */       try
/*      */       {
                   
                    Set AutoIncValuesSet = autoIncValues.keySet(); 
                            
/* 2950 */         //for (Integer index : autoIncValues.keySet())
                    for(Iterator it = AutoIncValuesSet.iterator(); it.hasNext(); )
/*      */         {
                       Integer index = (Integer)it.next();
/* 2952 */           Attribute attribute = (Attribute)a_vecAssetAttributes.get(index.intValue());
/* 2953 */           if ((attribute.getValue() != null) && ("-1".equals(attribute.getValue().getValue())))
/*      */           {
/* 2955 */             attribute.getValue().setValue((String)((List)autoIncValues.get(index)).get(iCount));
/*      */           }
/*      */         }
/*      */ 
/* 2959 */         long lAssetId = addEmptyAsset(a_user.getId(), a_metadata, a_vecAssetAttributes, a_lSessionId, a_user.getLanguage(), bShowMessage, bShowDots, a_update);
/*      */ 
                    Set autoIncValuesSet = autoIncValues.keySet(); 
                            
/* 2950 */         //for (Integer index : autoIncValues.keySet())
                   
                    for(Iterator it = autoIncValuesSet.iterator(); it.hasNext(); )
/*      */         {
                       Integer index = (Integer)it.next();
/* 2969 */         
/* 2971 */           Attribute attribute = (Attribute)a_vecAssetAttributes.get(index.intValue());
/* 2972 */           if ((attribute.getValue() != null) && (StringUtil.stringIsPopulated(attribute.getValue().getValue())))
/*      */           {
/* 2974 */             attribute.getValue().setValue("-1");
/*      */           }
/*      */         }
/*      */ 
/* 2978 */         this.m_logger.debug("ImportManager.addPlaceholders: Added asset number: " + iCount + ": ID=" + lAssetId);
/*      */       }
/*      */       catch (Throwable t)
/*      */       {
/* 2982 */         addMessage(a_user.getId(), "Import failure for asset: " + iCount);
/* 2983 */         this.m_logger.error("ImportManager.addPlaceholders: Exception caught whilst adding placeholder " + iCount + ": " + t);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public long addEmptyAsset(long a_lUserId, Asset a_assetMetadata, Vector a_vecAssetAttributes, long a_lSessionId, Language a_language, boolean a_bShowMessage, boolean a_bShowDots, WorkflowUpdate a_update)
/*      */     throws Bn2Exception
/*      */   {
/* 3010 */     String ksMethodName = "addAsset";
/*      */ 
/* 3012 */     long lAssetId = 0L;
/*      */ 
/* 3015 */     DBTransaction dbTransaction = null;
/*      */     try
/*      */     {
/* 3020 */       a_assetMetadata.setDateLastModified(new Date());
/* 3021 */       a_assetMetadata.getLastModifiedByUser().setId(a_lUserId);
/*      */ 
/* 3024 */       a_assetMetadata.setDateAdded(new Date());
/*      */ 
/* 3027 */       a_assetMetadata.setId(0L);
/*      */ 
/* 3029 */       String sParentIds = a_assetMetadata.getParentAssetIdsAsString();
/*      */ 
/* 3031 */       boolean bNeedReindex = false;
/* 3032 */       if (StringUtils.isNotEmpty(sParentIds))
/*      */       {
/* 3034 */         bNeedReindex = true;
/*      */       }
/*      */ 
/* 3038 */       a_assetMetadata = this.m_assetManager.saveAsset(null, a_assetMetadata, null, a_lUserId, null, null, false, 0, 0L, a_update, false, bNeedReindex);
/*      */ 
/* 3052 */       if (StringUtils.isNotEmpty(sParentIds))
/*      */       {
/* 3054 */         this.m_assetRelationshipManager.addAssetRelationships(dbTransaction, a_assetMetadata.getId(), sParentIds, 3L);
/*      */       }
/*      */ 
/* 3058 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/* 3059 */       Asset newAsset = this.m_assetManager.getAsset(dbTransaction, a_assetMetadata.getId(), null, false, true);
/* 3060 */       lAssetId = newAsset.getId();
/*      */ 
/* 3063 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 3065 */         this.m_assetLogManager.saveLog(lAssetId, newAsset.getFileName(), dbTransaction, a_lUserId, new Date(), 2L, a_lSessionId, a_assetMetadata.getVersionNumber());
/*      */       }
/*      */ 
/* 3069 */       String sMessage = null;
/*      */       AttributeValue matchAttributeValue;
/* 3071 */       if (a_bShowMessage)
/*      */       {
/* 3073 */         sMessage = "Adding record: ID=" + lAssetId;
/*      */ 
/* 3076 */         matchAttributeValue = newAsset.getMatchAttributeValue();
/* 3077 */         if (matchAttributeValue != null)
/*      */         {
/* 3079 */           sMessage = sMessage + " : " + matchAttributeValue.getAttribute().getLabel() + "=" + matchAttributeValue.getValue();
/*      */         }
/*      */ 
/* 3083 */         Vector<AttributeValue> vecAttributeValues = newAsset.getAttributeValues();

/* 3084 */         for (AttributeValue avDisplay : vecAttributeValues)
/*      */         {
/* 3087 */           long lAttrId = avDisplay.getAttribute().getId();
/* 3088 */           if ((avDisplay.getAttribute().getIsAutoincrement()) && (lAttrId != matchAttributeValue.getAttribute().getId()))
/*      */           {
/* 3090 */             sMessage = sMessage + " : " + avDisplay.getAttribute().getLabel() + "=" + avDisplay.getValue();
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 3095 */       if (a_bShowDots)
/*      */       {
/* 3097 */         sMessage = "...";
/*      */       }
/*      */ 
/* 3100 */       if (sMessage != null)
/*      */       {
/* 3102 */         addMessage(a_lUserId, sMessage);
/*      */       }
/*      */ 
/* 3105 */       dbTransaction.commit();
/* 3106 */       dbTransaction = null;
/*      */ 
/* 3109 */       if (bNeedReindex)
/*      */       {
/* 3111 */         this.m_searchManager.indexDocument(newAsset, true);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*      */       try
/*      */       {
/* 3119 */         if (dbTransaction != null)
/*      */         {
/* 3121 */           dbTransaction.rollback();
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (SQLException se)
/*      */       {
/*      */       }
/*      */ 
/* 3129 */       this.m_logger.error("ImportManager.addAsset: Exception ", e);
/* 3130 */       throw new Bn2Exception("ImportManager.addAsset: Exception ", e);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 3137 */         if (dbTransaction != null)
/*      */         {
/* 3139 */           dbTransaction.commit();
/*      */         }
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 3144 */         this.m_logger.error("Exception commiting transaction in AddPlaceholdersManager.saveAsset:", sqle);
/*      */       }
/*      */     }
/*      */ 
/* 3148 */     return lAssetId;
/*      */   }
/*      */ 
/*      */   private String matchDirectoryNamesToCategories(String a_sPossibleCategoryName, File a_fDir, Asset a_imageMetadata)
/*      */     throws Bn2Exception
/*      */   {
/* 3161 */     if (a_sPossibleCategoryName == null)
/*      */     {
/* 3164 */       a_sPossibleCategoryName = "";
/*      */     }
/*      */     else
/*      */     {
/* 3170 */       DBTransaction transaction = null;
/*      */       try
/*      */       {
/* 3175 */         if (a_sPossibleCategoryName.length() > 0)
/*      */         {
/* 3178 */           a_sPossibleCategoryName = a_sPossibleCategoryName + "/";
/*      */         }
/*      */ 
/* 3181 */         a_sPossibleCategoryName = a_sPossibleCategoryName + a_fDir.getName();
/*      */ 
/* 3184 */         transaction = this.m_transactionManager.getNewTransaction();
/* 3185 */         Category cat = this.m_categoryManager.ensureCategoryExists(transaction, 1L, a_sPossibleCategoryName, false);
/*      */ 
/* 3188 */         a_imageMetadata.getDescriptiveCategories().add(cat);
/*      */       }
/*      */       finally
/*      */       {
/* 3192 */         if (transaction != null)
/*      */         {
/*      */           try
/*      */           {
/* 3196 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException se)
/*      */           {
/* 3200 */             throw new Bn2Exception("ImportManager.importAllFromDir - SQLException committing transaction", se);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3206 */     return a_sPossibleCategoryName;
/*      */   }
/*      */ 
/*      */   public ArrayList<UserUploadsForDay> getUserUploadsInfoByDay(DBTransaction a_dbTransaction, long a_lUserId, int a_iDaysToGoBack, Boolean a_boolSubmitted)
/*      */     throws Bn2Exception
/*      */   {
/* 3215 */     Connection cCon = null;
/* 3216 */     ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/* 3217 */     ArrayList list = new ArrayList();
/*      */ 
/* 3221 */     Calendar calendar = Calendar.getInstance();
/* 3222 */     calendar.add(5, 0 - AssetBankSettings.getNumberDaysMyUploads());
/* 3223 */     Date dtFromDate = calendar.getTime();
/*      */     try
/*      */     {
/* 3227 */       cCon = a_dbTransaction.getConnection();
/* 3228 */       String sDateOnly = SQLGenerator.getInstance().getDateOnlyFunction("DateAdded");
/* 3229 */       String sSql = "SELECT " + sDateOnly + " dayAdded, COUNT(*) uploadCount FROM Asset WHERE AddedByUserId=?";
/*      */ 
/* 3231 */       if (dtFromDate != null)
/*      */       {
/* 3233 */         sSql = sSql + " AND DateAdded >= ?";
/*      */       }
/* 3235 */       if (a_boolSubmitted != null)
/*      */       {
/* 3237 */         sSql = sSql + " AND IsUnsubmitted = ?";
/*      */       }
/*      */ 
/* 3240 */       sSql = sSql + " GROUP BY " + sDateOnly + " ORDER BY " + sDateOnly + " DESC";
/*      */ 
/* 3242 */       int iCol = 1;
/* 3243 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/* 3244 */       psql.setLong(iCol++, a_lUserId);
/* 3245 */       if (dtFromDate != null)
/*      */       {
/* 3247 */         DBUtil.setFieldTimestampOrNull(psql, iCol++, dtFromDate);
/*      */       }
/* 3249 */       if (a_boolSubmitted != null)
/*      */       {
/* 3251 */         psql.setBoolean(iCol++, !a_boolSubmitted.booleanValue());
/*      */       }
/*      */ 
/* 3254 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 3256 */       while (rs.next())
/*      */       {
/* 3258 */         UserUploadsForDay uploadsInfo = new UserUploadsForDay();
/* 3259 */         uploadsInfo.setDate(new BrightDate(rs.getDate("dayAdded")));
/* 3260 */         uploadsInfo.setCount(rs.getInt("uploadCount"));
/* 3261 */         list.add(uploadsInfo);
/*      */       }
/*      */ 
/* 3264 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3269 */       this.m_logger.debug("Exception occurred in ImportManager:getUserUploadsInfoByDay : " + e);
/* 3270 */       throw new Bn2Exception("Exception occurred in ImportManager:getUserUploadsInfoByDay : " + e, e);
/*      */     }
/*      */ 
/* 3273 */     return list;
/*      */   }
/*      */ 
/*      */   protected int getNumberOfServicingThreads()
/*      */   {
/* 3278 */     if (AssetBankSettings.getBulkUploadConcurrencyCount() > 0)
/*      */     {
/* 3280 */       return AssetBankSettings.getBulkUploadConcurrencyCount();
/*      */     }
/*      */ 
/* 3283 */     return super.getNumberOfServicingThreads();
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/* 3288 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/* 3293 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public CategoryManager getCategoryManager()
/*      */   {
/* 3299 */     return this.m_categoryManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(CategoryManager a_sCategoryManager)
/*      */   {
/* 3305 */     this.m_categoryManager = a_sCategoryManager;
/*      */   }
/*      */ 
/*      */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*      */   {
/* 3310 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*      */   }
/*      */ 
/*      */   public IAssetManager getAssetManager()
/*      */   {
/* 3316 */     return this.m_assetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(IAssetManager a_sAssetManager)
/*      */   {
/* 3322 */     this.m_assetManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*      */   {
/* 3327 */     this.m_fileStoreManager = a_fileStoreManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager a_attributeManager)
/*      */   {
/* 3332 */     this.m_attributeManager = a_attributeManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*      */   {
/* 3337 */     this.m_attributeValueManager = a_attributeValueManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/* 3342 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ 
/*      */   public void setEmailManager(EmailManager a_sEmailManager) {
/* 3346 */     this.m_emailManager = a_sEmailManager;
/*      */   }
/*      */ 
/*      */   public ABUserManager getUserManager() {
/* 3350 */     return this.m_userManager;
/*      */   }
/*      */ 
/*      */   public void setUserManager(ABUserManager a_sUserManager) {
/* 3354 */     this.m_userManager = a_sUserManager;
/*      */   }
/*      */ 
/*      */   public String getUploadDirectory() {
/* 3358 */     return this.m_sBulkUploadDirectory;
/*      */   }
/*      */ 
/*      */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager) {
/* 3362 */     this.m_taxonomyManager = a_taxonomyManager;
/*      */   }
/*      */ 
/*      */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*      */   {
/* 3367 */     this.m_assetLogManager = a_assetLogManager;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*      */   {
/* 3373 */     this.m_assetEntityManager = assetEntityManager;
/*      */   }
/*      */ 
/*      */   public void setOrgUnitManager(OrgUnitManager orgUnitManager)
/*      */   {
/* 3382 */     this.m_orgUnitManager = orgUnitManager;
/*      */   }
/*      */ 
/*      */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*      */   {
/* 3387 */     this.m_agreementsManager = a_agreementsManager;
/*      */   }
/*      */ 
/*      */   public void setListManager(ListManager a_listManager)
/*      */   {
/* 3394 */     this.m_listManager = a_listManager;
/*      */   }
/*      */ 
/*      */   private static class ZipEntriesComparator
/*      */     implements Comparator<ZipEntry>
/*      */   {
/* 3424 */     private Collator c = Collator.getInstance();
/*      */ 
/*      */     public int compare(ZipEntry z1, ZipEntry z2)
/*      */     {
/* 3429 */       if (z1 == z2) {
/* 3430 */         return 0;
/*      */       }
/* 3432 */       if ((z1.isDirectory()) && (!z2.isDirectory()))
/* 3433 */         return -1;
/* 3434 */       if ((!z1.isDirectory()) && (z2.isDirectory())) {
/* 3435 */         return 1;
/*      */       }
/* 3437 */       return this.c.compare(z1.getName(), z2.getName());
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class FileComparator
/*      */     implements Comparator<File>
/*      */   {
/* 3402 */     private Collator c = Collator.getInstance();
/*      */ 
/*      */     public int compare(File f1, File f2)
/*      */     {
/* 3407 */       if (f1 == f2) {
/* 3408 */         return 0;
/*      */       }
/* 3410 */       if ((f1.isDirectory()) && (f2.isFile()))
/* 3411 */         return -1;
/* 3412 */       if ((f1.isFile()) && (f2.isDirectory())) {
/* 3413 */         return 1;
/*      */       }
/* 3415 */       return this.c.compare(f1.getName(), f2.getName());
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.service.ImportManager
 * JD-Core Version:    0.6.0
 */