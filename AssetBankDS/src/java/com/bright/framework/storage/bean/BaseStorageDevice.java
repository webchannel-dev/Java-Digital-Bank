/*      */ package com.bright.framework.storage.bean;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import com.bright.framework.storage.constant.StorageDeviceType;
/*      */ import com.bright.framework.storage.constant.StoredFileType;
/*      */ import com.bright.framework.storage.exception.UnsupportedStoredFileTypeException;
/*      */ import com.bright.framework.storage.util.StorageDeviceUtil;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.File;
/*      */ import java.io.FileFilter;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.util.Date;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public abstract class BaseStorageDevice extends StringDataBean
/*      */   implements StorageDevice
/*      */ {
/*   52 */   private static final int c_iNumStorageDirs = FrameworkSettings.getNumStorageDirectories();
/*      */   private static final int k_iBytesAtATime = 8192;
/*   55 */   protected Log m_logger = GlobalApplication.getInstance().getLogger();
/*      */   private StorageDeviceType m_type;
/*      */   private String m_sFactoryClassName;
/*      */   private String m_sDeviceTypeName;
/*      */   private String m_sLocalBasePath;
/*      */   private String m_sSubPath;
/*      */   private String m_sHttpBaseUrl;
/*   63 */   private boolean m_bIsLocked = false;
/*   64 */   private long m_lMaxSpaceInMb = 0L;
/*   65 */   private volatile long m_lUsedSpaceInBytes = 0L;
/*   66 */   private volatile long m_lUsedLocalSpaceInBytes = 0L;
/*   67 */   private Date m_lLastUsageScan = null;
/*   68 */   private int m_iNumStoredAssets = 0;
/*   69 */   private boolean m_bVerifyHttpUrls = false;
/*      */ 
/*   71 */   protected final AtomicInteger m_aiStorageAccessCount = new AtomicInteger(0);
/*   72 */   protected final AtomicLong m_alStorageLastAccess = new AtomicLong(0L);
/*   73 */   private Object m_oUniqueFilenameLock = new Object();
/*      */ 
/*   76 */   private volatile int m_iNexStorageSubdirectory = -1;
/*      */ 
/*      */   public BaseStorageDevice(String a_sFactoryClassName, String a_sFactoryDisplayName)
/*      */   {
/*   80 */     this.m_sFactoryClassName = a_sFactoryClassName;
/*   81 */     this.m_sDeviceTypeName = a_sFactoryDisplayName;
/*      */   }
/*      */ 
/*      */   public final void initialiseStorage()
/*      */     throws Bn2Exception
/*      */   {
/*   91 */     String sFullPath = getFullLocalBasePath();
/*      */ 
/*   93 */     if ((isStorageFor(StorageDeviceType.ASSETS)) || (isStorageFor(StorageDeviceType.THUMBNAILS)))
/*      */     {
/*   95 */       int iNumDirs = FrameworkSettings.getNumStorageDirectories();
/*      */ 
/*   97 */       if (iNumDirs <= 0)
/*      */       {
/*   99 */         this.m_logger.error("Number of storage directories must be a positive integer - check the applciation settings");
/*  100 */         throw new Bn2Exception("Number of storage directories must be a positive integer - check the applciation settings");
/*      */       }
/*      */ 
/*  103 */       for (int i = 0; i < iNumDirs; i++)
/*      */       {
/*  107 */         File fStorageDir = new File(sFullPath + "/" + i);
/*      */ 
/*  109 */         if (fStorageDir.exists()) {
/*      */           continue;
/*      */         }
/*  112 */         fStorageDir.mkdir();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  118 */     if (isStorageFor(StorageDeviceType.SYSTEM))
/*      */     {
/*  120 */       FileUtil.ensureDirectoryExists(new File(sFullPath + "/" + FrameworkSettings.getTemporaryDirectory()));
/*  121 */       FileUtil.ensureDirectoryExists(new File(sFullPath + "/" + FrameworkSettings.getShareDirectory()));
/*  122 */       FileUtil.ensureDirectoryExists(new File(sFullPath + "/" + FrameworkSettings.getExportDirectory()));
/*  123 */       FileUtil.ensureDirectoryExists(new File(sFullPath + "/" + FrameworkSettings.getImportDirectory()));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doInitialiseStorage()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void doStoreNewFile(String a_sRelativePath, StoredFileType a_type)
/*      */     throws UnsupportedStoredFileTypeException, Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected final String getRelativePath(String a_sPathWithoutDeviceId)
/*      */   {
/*  158 */     if (getId() != 1L)
/*      */     {
/*  160 */       return getId() + ":" + a_sPathWithoutDeviceId;
/*      */     }
/*  162 */     return a_sPathWithoutDeviceId;
/*      */   }
/*      */ 
/*      */   public final String getFullLocalPath(String a_sRelativePath)
/*      */     throws Bn2Exception
/*      */   {
/*  172 */     synchronized (this.m_aiStorageAccessCount)
/*      */     {
/*  174 */       this.m_aiStorageAccessCount.incrementAndGet();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  180 */       prepareFileForLocalAccess(a_sRelativePath);
/*      */ 
/*  183 */      // ??? = buildFullLocalPath(a_sRelativePath);
                String localpath = buildFullLocalPath(a_sRelativePath);
                return localpath;
/*      */     }
/*      */     finally
/*      */     {
/*  187 */       this.m_alStorageLastAccess.set(System.currentTimeMillis());
/*  188 */       this.m_aiStorageAccessCount.decrementAndGet();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected String buildFullLocalPath(String a_sRelativePath)
/*      */   {
/*  199 */     String sPath = getLocalBasePath();
/*      */ 
/*  201 */     if (isLocalBasePathRelative())
/*      */     {
/*  203 */       sPath = FrameworkSettings.getApplicationPath() + "/" + sPath;
/*      */     }
/*      */ 
/*  206 */     return sPath + '/' + StorageDeviceUtil.removeDevicePrefix(a_sRelativePath);
/*      */   }
/*      */ 
/*      */   protected void prepareFileForLocalAccess(String a_sRelativePath)
/*      */     throws Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   protected abstract String generateUniqueFilename(String paramString1, String paramString2, StoredFileType paramStoredFileType)
/*      */     throws Bn2Exception;
/*      */ 
/*      */   public final StorageDeviceType getType()
/*      */   {
/*  238 */     return this.m_type;
/*      */   }
/*      */ 
/*      */   public final void setType(StorageDeviceType a_type)
/*      */   {
/*  247 */     this.m_type = a_type;
/*      */   }
/*      */ 
/*      */   public final long getTypeId()
/*      */   {
/*  252 */     return this.m_type != null ? this.m_type.getId() : 0L;
/*      */   }
/*      */ 
/*      */   public final void setTypeId(long a_lId)
/*      */   {
/*  257 */     if (a_lId > 0L)
/*      */     {
/*  259 */       this.m_type = StorageDeviceType.forId(a_lId);
/*      */     }
/*      */   }
/*      */ 
/*      */   public final boolean isAvailableForStorage(StorageDeviceType a_type)
/*      */   {
/*  269 */     if (this.m_type.includes(a_type));
/*  269 */     return (getHasFreeSpace() & !isLocked());
/*      */   }
/*      */ 
/*      */   public final boolean isStorageFor(StorageDeviceType a_type)
/*      */   {
/*  278 */     if ((a_type.includes(StorageDeviceType.ASSETS)) && (this.m_type.includes(StorageDeviceType.ASSETS)))
/*      */     {
/*  280 */       return true;
/*      */     }
/*  282 */     if ((a_type.includes(StorageDeviceType.THUMBNAILS)) && (this.m_type.includes(StorageDeviceType.THUMBNAILS)))
/*      */     {
/*  284 */       return true;
/*      */     }
/*  286 */     if ((a_type.includes(StorageDeviceType.SYSTEM)) && (this.m_type.includes(StorageDeviceType.SYSTEM)))
/*      */     {
/*  288 */       return true;
/*      */     }
/*  290 */     return this.m_type.includes(a_type);
/*      */   }
/*      */ 
/*      */   public final int getFreeSpaceSafetyMargin()
/*      */   {
/*  299 */     int iSafetyMargin = 0;
/*      */ 
/*  302 */     if (this.m_type.includes(StorageDeviceType.ASSETS))
/*      */     {
/*  304 */       iSafetyMargin = FrameworkSettings.getStorageDeviceSafetyMarginAssetsMb();
/*      */     }
/*  307 */     else if ((this.m_type.includes(StorageDeviceType.SYSTEM)) || (this.m_type.includes(StorageDeviceType.THUMBNAILS)))
/*      */     {
/*  309 */       iSafetyMargin = FrameworkSettings.getStorageDeviceSafetyMarginSystemMb();
/*      */     }
/*  311 */     return iSafetyMargin;
/*      */   }
/*      */ 
/*      */   public final String getSubPath()
/*      */   {
/*  320 */     return this.m_sSubPath;
/*      */   }
/*      */ 
/*      */   public final void setSubPath(String a_sPath)
/*      */   {
/*  329 */     if (a_sPath.startsWith("/"))
/*      */     {
/*  331 */       throw new IllegalArgumentException("The sub path must not start with a slash ('/')");
/*      */     }
/*  333 */     if (a_sPath.endsWith("/"))
/*      */     {
/*  335 */       throw new IllegalArgumentException("The sub path must not end with a slash ('/')");
/*      */     }
/*  337 */     this.m_sSubPath = a_sPath;
/*      */   }
/*      */ 
/*      */   public final boolean isLocalBasePathRelative()
/*      */   {
/*  346 */     return (this.m_sLocalBasePath != null) && (!this.m_sLocalBasePath.startsWith("\\")) && (!this.m_sLocalBasePath.startsWith("/")) && (this.m_sLocalBasePath.indexOf(":") <= 0);
/*      */   }
/*      */ 
/*      */   public final boolean getHasFreeSpace()
/*      */   {
/*  358 */     int iSafetyMargin = getFreeSpaceSafetyMargin();
/*      */ 
/*  360 */     return (this.m_lMaxSpaceInMb == 0L) || (this.m_lMaxSpaceInMb - this.m_lUsedSpaceInBytes / 1048576L > iSafetyMargin);
/*      */   }
/*      */ 
/*      */   public final long getFreeSpaceInBytes()
/*      */   {
/*  369 */     if (this.m_lMaxSpaceInMb <= 0L)
/*      */     {
/*  371 */       return -1L;
/*      */     }
/*  373 */     return this.m_lMaxSpaceInMb * 1024L * 1024L - this.m_lUsedSpaceInBytes;
/*      */   }
/*      */ 
/*      */   public final boolean isLocked()
/*      */   {
/*  382 */     return this.m_bIsLocked;
/*      */   }
/*      */ 
/*      */   public final void setLocked(boolean a_iIsLocked)
/*      */   {
/*  391 */     this.m_bIsLocked = a_iIsLocked;
/*      */   }
/*      */ 
/*      */   public final long getMaxSpaceInMb()
/*      */   {
/*  400 */     return this.m_lMaxSpaceInMb;
/*      */   }
/*      */ 
/*      */   public final void setMaxSpaceInMb(long a_iMaxSpaceInMb)
/*      */   {
/*  409 */     this.m_lMaxSpaceInMb = a_iMaxSpaceInMb;
/*      */   }
/*      */ 
/*      */   public final long getUsedSpaceInBytes()
/*      */   {
/*  418 */     return this.m_lUsedSpaceInBytes;
/*      */   }
/*      */ 
/*      */   public final void setUsedSpaceInBytes(long a_iUsedSpaceInBytes)
/*      */   {
/*  427 */     this.m_lUsedSpaceInBytes = a_iUsedSpaceInBytes;
/*      */   }
/*      */ 
/*      */   public final long getUsedLocalSpaceInBytes()
/*      */   {
/*  436 */     return this.m_lUsedLocalSpaceInBytes;
/*      */   }
/*      */ 
/*      */   public final void setUsedLocalSpaceInBytes(long a_iUsedLocalSpaceInBytes)
/*      */   {
/*  445 */     this.m_lUsedLocalSpaceInBytes = a_iUsedLocalSpaceInBytes;
/*      */   }
/*      */ 
/*      */   public final Date getLastUsageScan()
/*      */   {
/*  454 */     return this.m_lLastUsageScan;
/*      */   }
/*      */ 
/*      */   public final void setLastUsageScan(Date a_iLastScan)
/*      */   {
/*  459 */     this.m_lLastUsageScan = a_iLastScan;
/*      */   }
/*      */ 
/*      */   public final int getNumStoredAssets()
/*      */   {
/*  468 */     return this.m_iNumStoredAssets;
/*      */   }
/*      */ 
/*      */   public final void setNumStoredAssets(int a_iNumStoredAssets)
/*      */   {
/*  477 */     this.m_iNumStoredAssets = a_iNumStoredAssets;
/*      */   }
/*      */ 
/*      */   public String getHttpBaseUrl()
/*      */   {
/*  486 */     return this.m_sHttpBaseUrl;
/*      */   }
/*      */ 
/*      */   public void setHttpBaseUrl(String a_sHttpBaseUrl)
/*      */   {
/*  495 */     this.m_sHttpBaseUrl = a_sHttpBaseUrl;
/*      */   }
/*      */ 
/*      */   public final boolean isHttpUrlRelative()
/*      */   {
/*  504 */     return StringUtils.isEmpty(this.m_sHttpBaseUrl);
/*      */   }
/*      */ 
/*      */   public final String getHttpUrl(String a_sRelativePath)
/*      */     throws Bn2Exception, IOException
/*      */   {
/*      */     try
/*      */     {
/*  516 */       return getHttpUrl(a_sRelativePath, this.m_bVerifyHttpUrls);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/*  520 */       if (this.m_bVerifyHttpUrls)
/*      */       {
/*  522 */         throw e;
/*      */       }
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  527 */       if (this.m_bVerifyHttpUrls)
/*      */       {
/*  529 */         throw e;
/*      */       }
/*      */     }
/*  532 */     return null;
/*      */   }
/*      */ 
/*      */   public String getHttpUrl(String a_sRelativePath, boolean a_bTestFileExists)
/*      */     throws Bn2Exception, IOException
/*      */   {
/*  542 */     synchronized (this.m_aiStorageAccessCount)
/*      */     {
/*  544 */       this.m_aiStorageAccessCount.incrementAndGet();
/*      */     }
                URL url=null;
/*      */ 
/*      */     try
/*      */     {
/*  551 */       if ((isHttpUrlRelative()) && (!isLocalFileStorage()))
/*      */       {
/*  553 */         prepareFileForLocalAccess(a_sRelativePath);
/*      */       }
/*      */ 
/*  556 */       String sUrl = (StringUtils.isNotEmpty(getHttpBaseUrl()) ? getHttpBaseUrl() + '/' : "") + (StringUtils.isNotEmpty(getSubPath()) ? getSubPath() + '/' : "") + StorageDeviceUtil.removeDevicePrefix(getPathForHttpUrl(a_sRelativePath));
/*      */ 
/*  560 */       if (a_bTestFileExists)
/*      */       {
/*  562 */         url = new URL(sUrl);
/*  563 */         URLConnection con = ((URL)url).openConnection();
/*  564 */         String sContentLength = ((URL)url).openConnection().getHeaderField("Content-Length");
/*  565 */         con.getInputStream().close();
/*      */ 
/*  567 */         if ((StringUtils.isEmpty(sContentLength)) || ("0".equals(sContentLength.trim())))
/*      */         {
/*  569 */           throw new IOException(getClass().getSimpleName() + "getHttpUrl() : The content length obtained from the url is " + sContentLength);
/*      */         }
/*      */       }
/*      */ 
/*  573 */      // url = sUrl;
/*      */     }
/*      */     finally
/*      */     {
/*      */       //Object url;
/*  577 */       this.m_alStorageLastAccess.set(System.currentTimeMillis());
/*  578 */       this.m_aiStorageAccessCount.decrementAndGet();
                 return url.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected String getPathForHttpUrl(String a_sRelativePath)
/*      */   {
/*  591 */     return FileUtil.encryptFilepath(a_sRelativePath);
/*      */   }
/*      */ 
/*      */   public String getLocalBasePath()
/*      */   {
/*  600 */     return this.m_sLocalBasePath;
/*      */   }
/*      */ 
/*      */   public String getFullLocalBasePath(StoredFileType a_fileType)
/*      */   {
/*  609 */     if (StringUtils.isNotEmpty(a_fileType.getDirectoryName()))
/*      */     {
/*  611 */       return getFullLocalBasePath() + '/' + a_fileType.getDirectoryName();
/*      */     }
/*  613 */     return getFullLocalBasePath();
/*      */   }
/*      */ 
/*      */   public String getFullLocalBasePath()
/*      */   {
/*  618 */     if (isLocalBasePathRelative())
/*      */     {
/*  620 */       return FrameworkSettings.getApplicationPath() + '/' + this.m_sLocalBasePath;
/*      */     }
/*  622 */     return this.m_sLocalBasePath;
/*      */   }
/*      */ 
/*      */   public void setLocalBasePath(String a_sPath)
/*      */   {
/*  627 */     if (a_sPath.endsWith("/"))
/*      */     {
/*  629 */       throw new IllegalArgumentException("The lcoal base path must not end with a slash ('/')");
/*      */     }
/*  631 */     this.m_sLocalBasePath = a_sPath;
/*      */   }
/*      */ 
/*      */   public String getRelativePathForNewFile(String a_sRequestedFilename, StoredFileType a_type)
/*      */     throws UnsupportedStoredFileTypeException, Bn2Exception
/*      */   {
/*  637 */     return getRelativePathForNewFile(a_sRequestedFilename, null, a_type);
/*      */   }
/*      */ 
/*      */   public String getRelativePathForNewFile(String m_sRequestedFilename, String a_sStorageDirectory, StoredFileType a_type)
/*      */     throws UnsupportedStoredFileTypeException, Bn2Exception
/*      */   {
/*      */     String sStorageDirectory=null;
/*      */     //String sStorageDirectory;
/*  649 */     if (a_sStorageDirectory != null)
/*      */     {
/*  652 */       sStorageDirectory = a_sStorageDirectory;
/*      */     }
/*      */     else
/*      */     {
/*      */       //String sStorageDirectory;
/*  654 */       if (a_type.equals(StoredFileType.TEMP))
/*      */       {
/*  656 */         sStorageDirectory = StoredFileType.TEMP.getDirectoryName();
/*      */       }
/*      */       else
/*      */       {
/*      */         //String sStorageDirectory;
/*  658 */         if (a_type.equals(StoredFileType.EXPORT))
/*      */         {
/*  660 */           sStorageDirectory = StoredFileType.EXPORT.getDirectoryName();
/*      */         }
/*      */         else
/*      */         {
/*      */           //String sStorageDirectory;
/*  662 */           if (a_type.equals(StoredFileType.IMPORT))
/*      */           {
/*  664 */             sStorageDirectory = StoredFileType.IMPORT.getDirectoryName();
/*      */           }
/*      */           else
/*      */           {
/*      */             //String sStorageDirectory;
/*  666 */             if (StorageDeviceUtil.hasStorageDirectory(m_sRequestedFilename))
/*      */             {
/*  669 */               sStorageDirectory = StorageDeviceUtil.getStorageDirectory(m_sRequestedFilename);
/*      */             }
/*      */             else
/*      */             {
/*  674 */               sStorageDirectory = String.valueOf(getNextSubdirectory());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  678 */     FileUtil.ensureDirectoryExists(new File(getFullLocalBasePath() + '/' + sStorageDirectory));
/*      */ 
/*  680 */     synchronized (this.m_oUniqueFilenameLock)
/*      */     {
/*  683 */       String sUniqueFilename = generateUniqueFilename(sStorageDirectory, m_sRequestedFilename, a_type);
/*      */ 
/*  685 */       return getRelativePath(sStorageDirectory + '/' + sUniqueFilename);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getNextStorageSubdirectory()
/*      */   {
/*  691 */     return this.m_iNexStorageSubdirectory;
/*      */   }
/*      */ 
/*      */   public void setNextStorageSubdirectory(int a_iNextStorageSubdirectory)
/*      */   {
/*  696 */     this.m_iNexStorageSubdirectory = a_iNextStorageSubdirectory;
/*      */   }
/*      */ 
/*      */   protected synchronized int getNextSubdirectory()
/*      */   {
/*  701 */     this.m_iNexStorageSubdirectory += 1;
/*      */ 
/*  703 */     if (this.m_iNexStorageSubdirectory >= c_iNumStorageDirs)
/*      */     {
/*  705 */       this.m_iNexStorageSubdirectory = 0;
/*      */     }
/*      */ 
/*  708 */     return this.m_iNexStorageSubdirectory;
/*      */   }
/*      */ 
/*      */   public final void cleanFileStore()
/*      */     throws Bn2Exception
/*      */   {
/*  719 */     if (this.m_type.includes(StorageDeviceType.SYSTEM))
/*      */     {
/*  721 */       clearTempStorage();
/*      */     }
/*      */ 
/*  724 */     if (((this.m_type.includes(StorageDeviceType.ASSETS)) || (this.m_type.includes(StorageDeviceType.THUMBNAILS))) && (!isLocalFileStorage()))
/*      */     {
/*  726 */       clearAssetStorage();
/*      */     }
/*      */ 
/*  730 */     doCleanFileStore();
/*      */   }
/*      */ 
/*      */   protected void doCleanFileStore()
/*      */     throws Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   private void clearTempStorage()
/*      */     throws Bn2Exception
/*      */   {
/*  750 */     long iTempAge = System.currentTimeMillis() - FrameworkSettings.getDeleteTempFilesOlderThanMins() * 60000;
/*  751 */     long iShareAge = System.currentTimeMillis() - FrameworkSettings.getDeleteShareFilesOlderThanMins() * 60000;
/*  752 */     long iExportAge = System.currentTimeMillis() - FrameworkSettings.getDeleteExportFilesOlderThanMins() * 60000;
/*  753 */     long iImportAge = System.currentTimeMillis() - FrameworkSettings.getDeleteImportFilesOlderThanMins() * 60000;
/*      */ 
/*  755 */     FileUtil.clearDirectory(getLocalBasePath() + "/" + StoredFileType.TEMP.getDirectoryName(), iTempAge);
/*  756 */     FileUtil.clearDirectory(getLocalBasePath() + "/" + StoredFileType.SHARED.getDirectoryName(), iShareAge);
/*  757 */     FileUtil.clearDirectory(getLocalBasePath() + "/" + StoredFileType.EXPORT.getDirectoryName(), iExportAge);
/*  758 */     FileUtil.clearDirectory(getLocalBasePath() + "/" + StoredFileType.IMPORT.getDirectoryName(), iImportAge);
/*      */   }
/*      */ 
/*      */   private void clearAssetStorage()
/*      */     throws Bn2Exception
/*      */   {
    
/*  769 */     if (isLocalFileStorage())
/*      */     {
/*  771 */       return;
/*      */     }
/*      */ 
/*  774 */     final long lOlderThan = System.currentTimeMillis() - FrameworkSettings.getDeleteStoredFilesOlderThanMins() * 60000;
/*      */ 

/*  800 */    //1StorageFileFilter filter = new FileFilter(){
/*      */     //FileFilter filter = new FileFilter(){
                class StorageFileFilter implements FileFilter{
/*  778 */       private int m_iNumFiles = 0;
/*  779 */       private int m_iMaxSizeToKeep = FrameworkSettings.getDeleteStoredFilesLargerThanBytes();
/*      */ 
/*      */       public boolean accept(File pathname)
/*      */       {
/*  783 */         if ((pathname.lastModified() < lOlderThan) && (pathname.length() > this.m_iMaxSizeToKeep))
/*      */         {
/*  785 */           this.m_iNumFiles += 1;
/*  786 */           return true;
/*      */         }
/*  788 */         return false;
/*      */       }
/*      */ 
/*      */       public int getNumMatches()
/*      */       {
/*  793 */         return this.m_iNumFiles;
/*      */       }
/*      */     }
               StorageFileFilter filter = new StorageFileFilter();
/*  801 */     long lStart = System.currentTimeMillis();
/*      */ 
/*  804 */     synchronized (this.m_aiStorageAccessCount)
/*      */     {
/*  807 */       while (this.m_aiStorageAccessCount.get() > 0)
/*      */       {
/*      */         try
/*      */         {
/*  811 */           this.m_aiStorageAccessCount.wait(1000L);
/*      */         }
/*      */         catch (InterruptedException e)
/*      */         {
/*      */         }
/*      */       }
/*  817 */       while (System.currentTimeMillis() - this.m_alStorageLastAccess.get() < 10000L)
/*      */       {
/*      */         try
/*      */         {
/*  821 */           Thread.sleep(10001L - (System.currentTimeMillis() - this.m_alStorageLastAccess.get()));
/*      */         }
/*      */         catch (InterruptedException e)
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/*  828 */       int iNumDirs = FrameworkSettings.getNumStorageDirectories();
/*      */ 
/*  831 */       for (int i = 0; i < iNumDirs; i++)
/*      */       {
/*  833 */         FileUtil.clearDirectory(getLocalBasePath() + "/" + i, filter);
/*      */       }
/*      */     }
/*      */ 
/*  837 */     this.m_logger.debug("FileStoreManager.clearStorageDirectories() : Cleared " + filter.getNumMatches() + " files in " + (System.currentTimeMillis() - lStart) + "ms");
/*      */   }
/*      */ 
/*      */   public final void storeExistingFile(String a_sRelativePath)
/*      */     throws FileNotFoundException, IOException, Bn2Exception
/*      */   {
/*  848 */     synchronized (this.m_aiStorageAccessCount)
/*      */     {
/*  850 */       this.m_aiStorageAccessCount.incrementAndGet();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  855 */       doStoreExistingFile(a_sRelativePath);
/*      */     }
/*      */     finally
/*      */     {
/*  859 */       this.m_alStorageLastAccess.set(System.currentTimeMillis());
/*  860 */       this.m_aiStorageAccessCount.decrementAndGet();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doStoreExistingFile(String a_sRelativePath)
/*      */     throws FileNotFoundException, IOException, Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   public final String storeNewFile(String a_sRequestedFilename, InputStream a_inputStream, StoredFileType a_type)
/*      */     throws UnsupportedStoredFileTypeException, Bn2Exception
/*      */   {
/*  885 */     return storeNewFile(a_sRequestedFilename, a_inputStream, a_type, null);
/*      */   }
/*      */ 
/*      */   public final String storeNewFile(String a_sRequestedFilename, InputStream a_inputStream, StoredFileType a_type, String a_sStorageDir)
/*      */     throws UnsupportedStoredFileTypeException, Bn2Exception
/*      */   {
/*  897 */     synchronized (this.m_aiStorageAccessCount)
/*      */     {
/*  899 */       this.m_aiStorageAccessCount.incrementAndGet();
/*      */     }
                String sFullFilePath = null;
/*      */ 
/*      */     try
/*      */     {
/*  904 */       String sFilepath = null;
/*      */ 
/*  906 */       if ((StringUtil.stringIsPopulated(a_sRequestedFilename)) && (a_inputStream != null))
/*      */       {
/*  908 */         sFilepath = getRelativePathForNewFile(a_sRequestedFilename, a_sStorageDir, a_type);
/*      */ 
/*  910 */         this.m_logger.debug("FileStoreManager.saveFile: filename of uploaded file = " + sFilepath);
/*      */ 
/*  912 */         if ((a_sRequestedFilename == null) || (a_sRequestedFilename.length() == 0))
/*      */         {
/*  914 */           throw new Bn2Exception("You must select a file to upload.");
/*      */         }
/*      */ 
/*  918 */         sFullFilePath = getFullLocalPath(sFilepath);
/*      */ 
/*  920 */         this.m_logger.debug("filename in DMS = " + sFullFilePath);
/*      */         try
/*      */         {
/*  926 */           OutputStream bos = new FileOutputStream(sFullFilePath);
/*  927 */           int iBytesRead = 0;
/*  928 */           byte[] buffer = new byte[8192];
/*      */ 
/*  931 */           while ((iBytesRead = a_inputStream.read(buffer, 0, 8192)) != -1)
/*      */           {
/*  934 */             bos.write(buffer, 0, iBytesRead);
/*      */           }
/*      */ 
/*  937 */           bos.close();
/*      */ 
/*  940 */           a_inputStream.close();
/*      */         }
/*      */         catch (IOException ioe)
/*      */         {
/*  944 */           throw new Bn2Exception(ioe.getMessage());
/*      */         }
/*      */ 
/*  947 */         doStoreNewFile(sFilepath, a_type);
/*      */       }
/*      */ 
/*  950 */       sFullFilePath = sFilepath;
/*      */     }
/*      */     finally
/*      */     {
/*      */       //String sFullFilePath;
/*  954 */       this.m_alStorageLastAccess.set(System.currentTimeMillis());
/*  955 */       this.m_aiStorageAccessCount.decrementAndGet();
                 return sFullFilePath;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Boolean removeFile(String a_sRelativePath)
/*      */   {
/*  965 */     Boolean bRemoved = null;
/*      */ 
/*  967 */     if (StringUtils.isEmpty(a_sRelativePath))
/*      */     {
/*  969 */       return null;
/*      */     }
/*      */ 
/*  973 */     File fToDelete = new File(buildFullLocalPath(a_sRelativePath));
/*  974 */     if (fToDelete.exists())
/*      */     {
/*  976 */       bRemoved = Boolean.valueOf(fToDelete.delete());
/*      */     }
/*  978 */     FileUtil.logFileDeletion(fToDelete);
/*      */ 
/*  981 */     Boolean bRemovedFromStorage = doRemoveFile(a_sRelativePath);
/*      */ 
/*  985 */     if (bRemovedFromStorage != null)
/*      */     {
/*  987 */       return bRemovedFromStorage;
/*      */     }
/*  989 */     return bRemoved;
/*      */   }
/*      */ 
/*      */   protected Boolean doRemoveFile(String a_sRelativePath)
/*      */   {
/* 1001 */     return null;
/*      */   }
/*      */ 
/*      */   public final void runUsageScan()
/*      */     throws Bn2Exception
/*      */   {
/* 1011 */     long lSize = FileUtils.sizeOfDirectory(new File(getFullLocalBasePath()));
/* 1012 */     setUsedLocalSpaceInBytes(lSize);
/* 1013 */     doRunUsageScan();
/* 1014 */     setLastUsageScan(new Date());
/*      */   }
/*      */ 
/*      */   protected void doRunUsageScan()
/*      */     throws Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   public String getFactoryClassName()
/*      */   {
/* 1032 */     return this.m_sFactoryClassName;
/*      */   }
/*      */ 
/*      */   public void setFactoryClassName(String a_sFactoryClassName)
/*      */   {
/* 1041 */     this.m_sFactoryClassName = a_sFactoryClassName;
/*      */   }
/*      */ 
/*      */   public boolean getVerifyHttpUrls()
/*      */   {
/* 1050 */     return this.m_bVerifyHttpUrls;
/*      */   }
/*      */ 
/*      */   public void setVerifyHttpUrls(boolean a_bVerifyHttpUrls)
/*      */   {
/* 1059 */     this.m_bVerifyHttpUrls = a_bVerifyHttpUrls;
/*      */   }
/*      */ 
/*      */   public String getDeviceTypeName()
/*      */   {
/* 1064 */     return this.m_sDeviceTypeName;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.bean.BaseStorageDevice
 * JD-Core Version:    0.6.0
 */