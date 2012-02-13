/*     */ package com.bright.assetbank.synchronise.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.synchronise.bean.FileListReader;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class SimpleFileImportManager extends Bn2Manager
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "SimpleFileImportManager";
/*  45 */   private IAssetManager m_assetManager = null;
/*  46 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public HashMap importNewFiles(FileListReader a_reader)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     return importFiles(a_reader, -1L, false);
/*     */   }
/*     */ 
/*     */   public HashMap importNewFiles(FileListReader a_reader, long a_lUserid)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     return importFiles(a_reader, a_lUserid, false);
/*     */   }
/*     */ 
/*     */   public HashMap updateExistingFiles(FileListReader a_reader)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     return importFiles(a_reader, -1L, true);
/*     */   }
/*     */ 
/*     */   public HashMap updateExistingFiles(FileListReader a_reader, long a_lUserid)
/*     */     throws Bn2Exception
/*     */   {
/*  95 */     return importFiles(a_reader, a_lUserid, true);
/*     */   }
/*     */ 
/*     */   private HashMap importFiles(FileListReader a_reader, long a_lUserId, boolean a_bUpdating)
/*     */     throws Bn2Exception
/*     */   {
/* 117 */     String ksMethodName = "importFiles";
/*     */ 
/* 120 */     String[] aFiles = null;
/*     */ 
/* 122 */     if (a_bUpdating)
/*     */     {
/* 124 */       aFiles = a_reader.getFilesToUpdate();
/*     */     }
/*     */     else
/*     */     {
/* 128 */       aFiles = a_reader.getFiles();
/*     */     }
/*     */ 
/* 131 */     HashMap hmIds = null;
/*     */ 
/* 133 */     if ((aFiles != null) && (aFiles.length > 0))
/*     */     {
/* 135 */       this.m_logger.debug("SimpleFileImportManager.importFiles: Found " + aFiles.length + " files");
/*     */ 
/* 137 */       hmIds = new HashMap();
/*     */ 
/* 140 */       for (int i = 0; i < aFiles.length; i++)
/*     */       {
/* 142 */         String sFile = aFiles[i];
/*     */         try
/*     */         {
/* 146 */           long lId = -1L;
/*     */ 
/* 148 */           if (a_bUpdating)
/*     */           {
/* 150 */             String[] aPair = sFile.split(":");
/* 151 */             sFile = aPair[0];
/* 152 */             lId = Long.parseLong(aPair[1]);
/*     */           }
/*     */ 
/* 155 */           AssetFileSource source = new AssetFileSource();
/* 156 */           File file = new File(sFile);
/*     */ 
/* 158 */           FileInputStream input = new FileInputStream(file);
/* 159 */           source.setInputStream(input);
/* 160 */           source.setFilename(sFile);
/* 161 */           Asset tempAsset = new Asset();
/*     */ 
/* 164 */           if (a_bUpdating)
/*     */           {
/* 166 */             tempAsset = this.m_assetManager.getAsset(null, lId, null, false, false);
/*     */           }
/*     */ 
/* 170 */           if (a_lUserId <= 0L)
/*     */           {
/* 172 */             ABUser user = this.m_userManager.getApplicationUser();
/* 173 */             a_lUserId = user.getId();
/*     */           }
/*     */ 
/* 176 */           tempAsset = this.m_assetManager.saveAsset(null, tempAsset, source, a_lUserId, null, null, false, 0);
/*     */ 
/* 186 */           if (a_bUpdating)
/*     */           {
/* 188 */             this.m_logger.debug("SimpleFileImportManager.importFiles: Updating file (index: " + i + "). Existing asset has id " + tempAsset.getId());
/*     */           }
/*     */           else
/*     */           {
/* 192 */             this.m_logger.debug("SimpleFileImportManager.importFiles: Saved new file (index: " + i + "). New asset has id " + tempAsset.getId());
/*     */           }
/*     */ 
/* 196 */           hmIds.put(sFile, new Long(tempAsset.getId()));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 203 */           this.m_logger.error("SimpleFileImportManager.importFiles: Error: Unable to import file : " + sFile);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 209 */     return hmIds;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_assetManager)
/*     */   {
/* 215 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 220 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.service.SimpleFileImportManager
 * JD-Core Version:    0.6.0
 */