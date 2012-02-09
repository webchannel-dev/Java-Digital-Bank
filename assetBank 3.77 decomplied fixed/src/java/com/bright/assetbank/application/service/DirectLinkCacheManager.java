/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.DirectLinkCacheAssetFileFilter;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.bean.StorageDevice;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class DirectLinkCacheManager extends Bn2Manager
/*     */ {
/*  48 */   private String m_sDirectLinkCacheDirectory = FrameworkSettings.getFileCacheDirectory() + "/" + AssetBankSettings.getDirectImageLinkCacheDirectory();
/*  49 */   private AssetManager m_assetManager = null;
/*  50 */   private FileStoreManager m_fileStoreManager = null;
/*  51 */   private StorageDeviceManager m_storageDeviceManager = null;
/*     */ 
/*  68 */   private static final String c_ksClassName = DirectLinkCacheManager.class.getSimpleName();
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/*  55 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/*  60 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setStorageDeviceManager(StorageDeviceManager a_storageDeviceManager)
/*     */   {
/*  65 */     this.m_storageDeviceManager = a_storageDeviceManager;
/*     */   }
/*     */ 
/*     */   public String getImageFromCache(DBTransaction a_dbTransaction, LightweightAsset a_asset, int a_iHeight, int a_iWidth, boolean a_bPreserveAspectRatio)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     String sCacheFilename = getCacheFilename(a_asset, a_iHeight, a_iWidth, a_bPreserveAspectRatio);
/*     */ 
/*  86 */     String sFilePath = this.m_sDirectLinkCacheDirectory + "/" + sCacheFilename;
/*  87 */     String sAbsolutePath = this.m_fileStoreManager.getAbsolutePath(sFilePath);
/*  88 */     String sDirectory = sAbsolutePath.substring(0, sAbsolutePath.lastIndexOf("/"));
/*     */ 
/*  91 */     File directory = new File(sDirectory);
/*  92 */     if (!directory.exists())
/*     */     {
/*  94 */       directory.mkdir();
/*  95 */       return cacheImage(a_dbTransaction, a_asset, a_iHeight, a_iWidth, a_bPreserveAspectRatio);
/*     */     }
/*     */ 
/*  99 */     File file = new File(sAbsolutePath);
/* 100 */     if (!file.exists())
/*     */     {
/* 102 */       return cacheImage(a_dbTransaction, a_asset, a_iHeight, a_iWidth, a_bPreserveAspectRatio);
/*     */     }
/*     */ 
/* 105 */     return sFilePath;
/*     */   }
/*     */ 
/*     */   public void dumpAssetCache(DBTransaction a_dbTransaction, long a_lAssetId)
/*     */     throws Bn2Exception
/*     */   {
/* 118 */     String sFilePath = this.m_sDirectLinkCacheDirectory;
/*     */ 
/* 121 */     Vector<StorageDevice> vecDevices = this.m_storageDeviceManager.getAllDevices(a_dbTransaction);
/*     */ 
/* 123 */     for (StorageDevice device : vecDevices)
/*     */     {
/* 125 */       String sPath = this.m_fileStoreManager.getAbsolutePath(device.getId() + ":" + sFilePath);
/*     */ 
/* 128 */       File cacheDirectory = new File(sPath);
/*     */ 
/* 130 */       if (cacheDirectory.exists())
/*     */       {
/* 132 */         DirectLinkCacheAssetFileFilter filter = new DirectLinkCacheAssetFileFilter(a_lAssetId);
/* 133 */         File[] matchingFiles = cacheDirectory.listFiles(filter);
/*     */ 
/* 135 */         if (matchingFiles != null)
/*     */         {
/* 137 */           for (File file : matchingFiles)
/*     */           {
/* 139 */             file.delete();
/* 140 */             FileUtil.logFileDeletion(file);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String cacheImage(DBTransaction a_dbTransaction, LightweightAsset a_asset, int a_iHeight, int a_iWidth, boolean a_bPreserveAspectRatio)
/*     */     throws Bn2Exception
/*     */   {
/*     */     String sMessage;
/*     */     try
/*     */     {
/* 163 */       ImageConversionInfo info = new ImageConversionInfo();
/* 164 */       info.setMaxHeight(a_iHeight);
/* 165 */       info.setMaxWidth(a_iWidth);
/* 166 */       info.setMaintainAspectRatio(a_bPreserveAspectRatio);
/* 167 */       info.setScaleUp(true);
/* 168 */       info.setDeferAllowed(false);
/* 169 */       info.setJpegQuality(AssetBankSettings.getJpgConversionQuality());
/*     */ 
/* 171 */       String sFormatExt = "jpg";
/*     */ 
/* 173 */       if (FileUtil.getSuffix(a_asset.getOriginalFilename()).equals("gif"))
/*     */       {
/* 175 */         sFormatExt = "gif";
/*     */       }
/*     */ 
/* 179 */       Asset asset = null;
/* 180 */       if ((a_asset instanceof Asset))
/*     */       {
/* 182 */         asset = (Asset)a_asset;
/*     */       }
/*     */       else
/*     */       {
/* 186 */         asset = this.m_assetManager.getAsset(a_dbTransaction, a_asset.getId(), null, false, false);
/*     */       }
/*     */ 
/* 189 */       String sTempFilePath = this.m_fileStoreManager.getAbsolutePath(this.m_assetManager.getDownloadableAssetPath(asset, sFormatExt, info));
/*     */ 
/* 192 */       String sCacheFilename = getCacheFilename(a_asset, a_iHeight, a_iWidth, a_bPreserveAspectRatio);
/* 193 */       String sFilePath = this.m_sDirectLinkCacheDirectory + "/" + sCacheFilename;
/* 194 */       String sPath = this.m_fileStoreManager.getAbsolutePath(sFilePath);
/* 195 */       FileUtil.moveFile(sTempFilePath, sPath);
/*     */ 
/* 198 */       return sFilePath;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 202 */       sMessage = c_ksClassName + ".cacheImage : Error occurred caching direct link to image";
/* 203 */       this.m_logger.error(sMessage, e);
                throw new Bn2Exception(sMessage, e);
/* 204 */     }
/*     */   }
/*     */ 
/*     */   private String getCacheFilename(LightweightAsset a_asset, int a_iHeight, int a_iWidth, boolean a__bRatio)
/*     */   {
/* 221 */     String sCacheFilename = a_asset.getId() + "-" + a_iHeight + "x" + a_iWidth + "-" + a__bRatio + "." + getFormatExt(a_asset);
/* 222 */     return sCacheFilename;
/*     */   }
/*     */ 
/*     */   private String getFormatExt(LightweightAsset a_asset)
/*     */   {
/* 233 */     String sFormatExt = "jpg";
/*     */ 
/* 235 */     if (FileUtil.getSuffix(a_asset.getOriginalFilename()).equals("gif"))
/*     */     {
/* 237 */       sFormatExt = "gif";
/*     */     }
/*     */ 
/* 240 */     return sFormatExt;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.DirectLinkCacheManager
 * JD-Core Version:    0.6.0
 */