/*     */ package com.bright.framework.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.storage.bean.StorageDevice;
/*     */ import com.bright.framework.storage.constant.StorageDeviceType;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.storage.util.StorageDeviceUtil;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class FileStoreManager extends Bn2Manager
/*     */   implements ImageConstants, FrameworkConstants
/*     */ {
/*  89 */   private static final String c_ksClassName = FileStoreManager.class.getSimpleName();
/*     */   private static final int k_iBytesAtATime = 8192;
            public static final  StoredFileType k_iStoredFileType_TEMP = StoredFileType.TEMP;
            public static final StoredFileType k_iStoredFileType_IMPORT = StoredFileType.IMPORT;
/*  92 */   private ScheduleManager m_scheduleManager = null;
/*  93 */   private StorageDeviceManager m_storageDeviceManager = null;
/*  94 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/*  96 */   private String m_sApplicationBaseUrl = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/* 126 */     super.startup();
/*     */ 
/* 129 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/* 135 */           FileStoreManager.this.m_storageDeviceManager.cleanAllDevices(null);
/*     */         }
/*     */         catch (Bn2Exception e)
/*     */         {
/* 139 */           FileStoreManager.this.m_logger.error("Scheduled task to clean database file store encountered a Bn2Exception : " + e.getLocalizedMessage(), e);
/*     */         }
/*     */       }
/*     */     };
/* 144 */     int iHourOfDay = FrameworkSettings.getClearTempFilesHourOfDay();
/* 145 */     this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, false);
/*     */   }
/*     */ 
/*     */   public String getUniqueFilepath(String a_sFilename, StoredFileType a_fileType)
/*     */     throws Bn2Exception
/*     */   {
/* 155 */     return getUniqueFilepath(a_sFilename, a_fileType, null);
/*     */   }
/*     */ 
/*     */   private String getUniqueFilepath(String a_sFilename, StoredFileType a_fileType, String a_sStorageDir)
/*     */     throws Bn2Exception
/*     */   {
/* 174 */     String sStorageDirectory = null;
/* 175 */     String sFilename = null;
/*     */ 
/* 177 */     StorageDevice device = this.m_storageDeviceManager.getDeviceForNewFile(null, StorageDeviceType.getTypeFor(a_fileType));
/*     */ 
/* 180 */     sFilename = StorageDeviceUtil.getRealRelativePath(a_sFilename);
/*     */ 
/* 183 */     sFilename = FileUtil.getSafeFilename(sFilename, true);
/*     */ 
/* 186 */     sFilename = FileUtil.lowercaseSuffix(sFilename);
/*     */ 
/* 188 */     return device.getRelativePathForNewFile(sFilename, sStorageDirectory, a_fileType);
/*     */   }
/*     */ 
/*     */   public String getUniqueFilenameForRelatedFile(String a_sSourceFileId, String a_sSuffix, StoredFileType a_fileType)
/*     */     throws Bn2Exception
/*     */   {
/* 211 */     long lDeviceId = StorageDeviceUtil.getDeviceIdForRelativePath(a_sSourceFileId);
/*     */ 
/* 213 */     StorageDevice device = this.m_storageDeviceManager.getDeviceForNewFile(null, lDeviceId, StorageDeviceType.getTypeFor(a_fileType));
/*     */ 
/* 215 */     String sCandidateFilename = FileUtil.getFilepathWithoutSuffix(FileUtil.getFilename(a_sSourceFileId)) + a_sSuffix;
/*     */ 
/* 217 */     String sStorageDirectory = StorageDeviceUtil.getStorageDirectory(a_sSourceFileId);
/*     */ 
/* 219 */     return device.getRelativePathForNewFile(sCandidateFilename, sStorageDirectory, a_fileType);
/*     */   }
/*     */ 
/*     */   public String addFile(InputStream a_inputStream, String a_sSourceFilename, StoredFileType a_type)
/*     */     throws Bn2Exception
/*     */   {
/* 236 */     return addFile(a_inputStream, a_sSourceFilename, a_type, null);
/*     */   }
/*     */ 
/*     */   public String addFile(InputStream a_inputStream, String a_sSourceFilename, StoredFileType a_fileType, String a_sStorageDir)
/*     */     throws Bn2Exception
/*     */   {
/* 259 */     StorageDevice device = this.m_storageDeviceManager.getDeviceForNewFile(null, StorageDeviceType.getTypeFor(a_fileType));
/*     */ 
/* 261 */     return device.storeNewFile(a_sSourceFilename, a_inputStream, a_fileType);
/*     */   }
/*     */ 
/*     */   public String addFile(File a_fNewFile, StoredFileType a_fileType)
/*     */     throws Bn2Exception
/*     */   {
/* 275 */     return addFile(a_fNewFile, a_fileType, null);
/*     */   }
/*     */ 
/*     */   public String addFile(File a_fNewFile, StoredFileType a_fileType, String a_sStorageDir)
/*     */     throws Bn2Exception
/*     */   {
/* 296 */     String sPath = null;
/*     */     try
/*     */     {
/* 300 */       FileInputStream fis = new FileInputStream(a_fNewFile);
/* 301 */       sPath = addFile(fis, a_fNewFile.getName(), a_fileType, a_sStorageDir);
/* 302 */       fis.close();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 306 */       this.m_logger.error("IOException caught in FileStoreManager.addFile()");
/* 307 */       throw new Bn2Exception("IOException caught in FileStoreManager.addFile() ", ioe);
/*     */     }
/*     */ 
/* 310 */     return sPath;
/*     */   }
/*     */ 
/*     */   public void storeFile(String a_sRelativePath)
/*     */     throws Bn2Exception
/*     */   {
/* 316 */     StorageDevice device = this.m_storageDeviceManager.getDeviceForRelativePath(a_sRelativePath);
/*     */     try
/*     */     {
/* 320 */       device.storeExistingFile(a_sRelativePath);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 324 */       this.m_logger.error(getClass().getSimpleName() + ".storeFile() : IOException storing file : " + e.getLocalizedMessage());
/* 325 */       throw new Bn2Exception(getClass().getSimpleName() + ".storeFile() : IOException storing file : " + e.getLocalizedMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String addFileByMove(File a_fFile, StoredFileType a_fileType)
/*     */     throws Bn2Exception
/*     */   {
/* 346 */     String sStoredFileLocation = getUniqueFilepath(a_fFile.getName(), a_fileType);
/* 347 */     moveFileToExistingLocation(a_fFile, sStoredFileLocation);
/* 348 */     return sStoredFileLocation;
/*     */   }
/*     */ 
/*     */   public boolean moveFileToExistingLocation(File a_fFile, String a_sFileLocation)
/*     */     throws Bn2Exception
/*     */   {
/* 367 */     boolean bMoved = false;
/*     */ 
/* 369 */     String sFullPath = getAbsolutePath(a_sFileLocation);
/*     */ 
/* 372 */     File fNewFile = new File(sFullPath);
/* 373 */     fNewFile.delete();
/* 374 */     FileUtil.logFileDeletion(fNewFile);
/*     */     try
/*     */     {
/* 379 */       FileUtils.copyFile(a_fFile, fNewFile);
/* 380 */       a_fFile.delete();
/*     */ 
/* 382 */       StorageDevice device = this.m_storageDeviceManager.getDeviceForRelativePath(a_sFileLocation);
/* 383 */       device.storeExistingFile(a_sFileLocation);
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/* 387 */       this.m_logger.error(getClass().getSimpleName() + ".moveFileToExistingLocation() : Couldn't find moved file: " + a_sFileLocation);
/* 388 */       throw new Bn2Exception("Couldn't find moved file: " + a_sFileLocation, e);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 392 */       this.m_logger.error(getClass().getSimpleName() + ".moveFileToExistingLocation() : IOException whilst storing moved file: " + a_sFileLocation, e);
/* 393 */       throw new Bn2Exception("Couldn't store moved file: " + a_sFileLocation, e);
/*     */     }
/*     */ 
/* 396 */     return bMoved;
/*     */   }
/*     */ 
/*     */   public void deleteFileFromDatabase(DBTransaction a_transaction, String a_sUrl)
/*     */     throws Bn2Exception
/*     */   {
/* 408 */     Connection con = null;
/* 409 */     String sSql = null;
/* 410 */     PreparedStatement psql = null;
/* 411 */     DBTransaction transaction = a_transaction;
/* 412 */     ApplicationSql appSql = SQLGenerator.getInstance();
/*     */ 
/* 414 */     if (transaction == null)
/*     */     {
/* 416 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 421 */       con = transaction.getConnection();
/*     */ 
/* 423 */       sSql = "DELETE FROM AssetFile WHERE " + appSql.getLowerCaseFunction("Uri") + "=" + appSql.getLowerCaseFunction("?");
/*     */ 
/* 425 */       psql = con.prepareStatement(sSql);
/* 426 */       psql.setString(1, a_sUrl);
/* 427 */       psql.execute();
/* 428 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 432 */       if ((transaction != null) && (a_transaction == null))
/*     */       {
/*     */         try
/*     */         {
/* 436 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 444 */       this.m_logger.error(c_ksClassName + ".deleteFileFromDatabase : SQL Exception : " + e);
/* 445 */       throw new Bn2Exception(c_ksClassName + ".deleteFileFromDatabase : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 449 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 453 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 457 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String createZipFileForDownload(String sFilenameNoExtension, Vector a_vSourceFilePaths, Vector a_vDestinationFilePaths)
/*     */     throws Bn2Exception
/*     */   {
/* 481 */     return createZipFilesForDownload(sFilenameNoExtension, a_vSourceFilePaths, a_vDestinationFilePaths, 0L)[0];
/*     */   }
/*     */ 
/*     */   public String[] createZipFilesForDownload(String sFilenameNoExtension, Vector a_vSourceFilePaths, Vector a_vDestinationFilePaths, long a_lMaxSizeOfSingleZip)
/*     */     throws Bn2Exception
/*     */   {
/* 506 */     return createZipFilesForDownload(sFilenameNoExtension, a_vSourceFilePaths, a_vDestinationFilePaths, a_lMaxSizeOfSingleZip, StoredFileType.TEMP);
/*     */   }
/*     */ 
/*     */   public String[] createZipFilesForDownload(String sFilenameNoExtension, Vector a_vSourceFilePaths, Vector a_vDestinationFilePaths, long a_lMaxSizeOfSingleZip, StoredFileType a_fileType)
/*     */     throws Bn2Exception
/*     */   {
/* 538 */     String[] saZipFilePaths = null;
/* 539 */     byte[] buff = new byte[8192];
/* 540 */     File outputFile = null;
/* 541 */     ZipOutputStream zout = null;
/* 542 */     String sOutputFilename = null;
/* 543 */     Vector vecFilenames = new Vector();
/*     */ 
/* 545 */     String sTempFile = null;
/*     */     try
/*     */     {
/* 549 */       int iSourceFileIndex = 0;
/*     */ 
/* 552 */       while (iSourceFileIndex < a_vSourceFilePaths.size())
/*     */       {
/* 554 */         sOutputFilename = getUniqueFilepath(sFilenameNoExtension + ".zip", a_fileType);
/* 555 */         StorageDevice device = this.m_storageDeviceManager.getDeviceForRelativePath(sOutputFilename);
/*     */ 
/* 558 */         outputFile = new File(this.m_storageDeviceManager.getFullPathForRelativePath(sOutputFilename));
/*     */ 
/* 561 */         zout = new ZipOutputStream(new FileOutputStream(outputFile));
/*     */ 
/* 563 */         int iBytesWrittenToZip = 0;
/*     */ 
/* 567 */         while ((iSourceFileIndex < a_vSourceFilePaths.size()) && ((iBytesWrittenToZip < a_lMaxSizeOfSingleZip) || (a_lMaxSizeOfSingleZip <= 0L)))
/*     */         {
/* 569 */           sTempFile = (String)a_vSourceFilePaths.get(iSourceFileIndex);
/*     */ 
/* 571 */           if (StringUtil.stringIsPopulated(sTempFile))
/*     */           {
/* 574 */             InputStream in = new FileInputStream(getAbsolutePath(sTempFile));
/* 575 */             ZipEntry entry = new ZipEntry((String)a_vDestinationFilePaths.get(iSourceFileIndex));
/*     */ 
/* 578 */             zout.putNextEntry(entry);
/*     */ 
/* 581 */             int iNumBytes = 0;
/* 582 */             while ((iNumBytes = in.read(buff)) != -1)
/*     */             {
/* 584 */               zout.write(buff, 0, iNumBytes);
/*     */ 
/* 587 */               iBytesWrittenToZip += iNumBytes;
/*     */             }
/*     */ 
/* 591 */             zout.closeEntry();
/*     */ 
/* 594 */             in.close();
/*     */ 
/* 597 */             if (StoredFileType.isTransientFileType(sTempFile))
/*     */             {
/* 599 */               String sPath = this.m_storageDeviceManager.getFullPathForRelativePath(sTempFile);
/* 600 */               File fDelete = new File(sPath);
/* 601 */               fDelete.delete();
/* 602 */               FileUtil.logFileDeletion(fDelete);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 607 */           iSourceFileIndex++;
/*     */         }
/*     */ 
/* 611 */         zout.close();
/*     */ 
/* 614 */         device.storeExistingFile(sOutputFilename);
/*     */ 
/* 617 */         vecFilenames.add(sOutputFilename);
/*     */       }
/*     */ 
/* 621 */       saZipFilePaths = new String[vecFilenames.size()];
/*     */ 
/* 623 */       for (int i = 0; i < vecFilenames.size(); i++)
/*     */       {
/* 625 */         saZipFilePaths[i] = ((String)vecFilenames.get(i));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 631 */       this.m_logger.error("FilestoreManager.createZipFileForDownload() : IOException caught whilst creating a zip file");
/* 632 */       throw new Bn2Exception("IOException caught whilst creating a zip file", ioe);
/*     */     }
/*     */ 
/* 635 */     return saZipFilePaths;
/*     */   }
/*     */ 
/*     */   public Boolean deleteFile(String a_sFileToDeleteUrl)
/*     */   {
/* 651 */     if (a_sFileToDeleteUrl != null)
/*     */     {
/*     */       try
/*     */       {
/* 655 */         StorageDevice device = this.m_storageDeviceManager.getDeviceForRelativePath(a_sFileToDeleteUrl);
/* 656 */         return device.removeFile(a_sFileToDeleteUrl);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 660 */         this.m_logger.error(getClass().getSimpleName() + ".deleteFile() : Bn2Exception caught whilst deleteing file " + a_sFileToDeleteUrl + " : " + e.getLocalizedMessage(), e);
/*     */       }
/*     */     }
/*     */ 
/* 664 */     return null;
/*     */   }
/*     */ 
/*     */   public String getStorageDirectory(String a_sFileId)
/*     */   {
/* 680 */     return StorageDeviceUtil.getStorageDirectory(a_sFileId);
/*     */   }
/*     */ 
/*     */   public String getFullBasePath(String a_sRelativePath)
/*     */     throws Bn2Exception
/*     */   {
/* 696 */     return this.m_storageDeviceManager.getFullBasePathForRelativePath(a_sRelativePath);
/*     */   }
/*     */ 
/*     */   public String getRealRelativePath(String a_sIdentifyingPath)
/*     */     throws Bn2Exception
/*     */   {
/* 711 */     return StorageDeviceUtil.getRealRelativePath(a_sIdentifyingPath);
/*     */   }
/*     */ 
/*     */   public long getDeviceIdForRelativePath(String a_sRelativePath)
/*     */     throws Bn2Exception
/*     */   {
/* 722 */     return StorageDeviceUtil.getDeviceIdForRelativePath(a_sRelativePath);
/*     */   }
/*     */ 
/*     */   public String getRelativePathForDevice(String a_sPath, long a_lDeviceId)
/*     */   {
/* 734 */     return StorageDeviceUtil.getRelativePathForDevice(a_sPath, a_lDeviceId);
/*     */   }
/*     */ 
/*     */   public String getAbsolutePath(String a_sRelativePath)
/*     */     throws Bn2Exception
/*     */   {
/* 751 */     StorageDevice device = this.m_storageDeviceManager.getDeviceForRelativePath(a_sRelativePath);
/* 752 */     return device.getFullLocalPath(a_sRelativePath);
/*     */   }
/*     */ 
/*     */   public String getHttpUrlNeverFail(String a_sRelativePath)
/*     */   {
/*     */     try
/*     */     {
/* 765 */       StorageDevice device = this.m_storageDeviceManager.getDeviceForRelativePath(a_sRelativePath);
/* 766 */       return device.getHttpUrl(a_sRelativePath);
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 770 */       this.m_logger.warn("Throwable caught whilst getting http url for: " + a_sRelativePath, t);
/* 771 */     }return null;
/*     */   }
/*     */ 
/*     */   public String getHttpUrl(String a_sRelativePath)
/*     */     throws Bn2Exception
/*     */   {
/* 783 */     StorageDevice device = this.m_storageDeviceManager.getDeviceForRelativePath(a_sRelativePath);
/*     */     try
/*     */     {
/*     */       String sUrl;
/*     */      // String sUrl;
/* 790 */       if (device.isHttpUrlRelative())
/*     */       {
/* 792 */         sUrl = this.m_sApplicationBaseUrl + "/servlet/display?file=" + FileUtil.encryptFilepath(device.getFullLocalPath(a_sRelativePath));
/*     */       }
/*     */       else
/*     */       {
/* 796 */         sUrl = device.getHttpUrl(a_sRelativePath);
/*     */       }
/*     */ 
/* 799 */       return sUrl;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 803 */       this.m_logger.error(getClass().getName() + "getHttpUrl() : IOException caught whilst getting http url for: " + a_sRelativePath + " : " + e.getMessage());
/* 804 */     throw new Bn2Exception(getClass().getName() + "getHttpUrl() : IOException caught whilst getting http url for: " + a_sRelativePath, e);
/*     */   }}
/*     */ 
/*     */   public String getHttpUrlWithFail(String a_sRelativePath)
/*     */     throws Bn2Exception, IOException
/*     */   {
/*     */     try
/*     */     {
/* 818 */       StorageDevice device = this.m_storageDeviceManager.getDeviceForRelativePath(a_sRelativePath);
/* 819 */       return device.getHttpUrl(a_sRelativePath, true);
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/* 823 */       this.m_logger.error("File not found whilst getting http url for: " + a_sRelativePath);
/* 824 */       throw new Bn2Exception("File not found whilst getting http url for: " + a_sRelativePath, e);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 828 */       this.m_logger.error("IOException whilst getting http url for: " + a_sRelativePath);
/* 829 */     throw new Bn2Exception("IOException whilst getting http url for: " + a_sRelativePath, e);
/*     */  } }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*     */   {
/* 835 */     this.m_scheduleManager = a_sScheduleManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 840 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setStorageDeviceManager(StorageDeviceManager a_manager)
/*     */   {
/* 845 */     this.m_storageDeviceManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setApplicationBaseUrl(String a_sApplicationBaseUrl)
/*     */   {
/* 850 */     this.m_sApplicationBaseUrl = a_sApplicationBaseUrl;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.service.FileStoreManager
 * JD-Core Version:    0.6.0
 */