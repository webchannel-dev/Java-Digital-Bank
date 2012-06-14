/*     */ package com.bright.assetbank.user.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.synchronise.bean.ExportResult;
/*     */ import com.bright.assetbank.user.bean.UserBeanWriter;
/*     */ import com.bright.framework.common.bean.FileBean;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.file.ExcelFormat;
/*     */ import com.bright.framework.file.FileFormat;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class UserExportManager extends Bn2Manager
/*     */   implements FrameworkConstants
/*     */ {
/*  47 */   private FileStoreManager m_fileStoreManager = null;
/*  48 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public ExportResult exportUsers(DBTransaction a_dbTransaction, Vector<Long> a_vecUserIds)
/*     */     throws Bn2Exception
/*     */   {
/*  64 */     ExportResult result = null;
/*  65 */     BufferedWriter writer = null;
/*     */     try
/*     */     {
/*  69 */       String sFilename = "export.xls";
/*     */ 
/*  71 */       FileFormat format = new ExcelFormat();
/*  72 */       String sDataFilepath = this.m_fileStoreManager.getUniqueFilepath(sFilename, StoredFileType.EXPORT);
/*  73 */       File exportFile = new File(this.m_fileStoreManager.getAbsolutePath(sDataFilepath));
/*  74 */       writer = new BufferedWriter(new FileWriter(exportFile));
/*     */ 
/*  77 */       Vector vecUsers = this.m_userManager.getUsers(a_dbTransaction, a_vecUserIds);
/*  78 */       UserBeanWriter fileWriter = new UserBeanWriter(writer, format);
/*     */ 
/*  80 */       if (!UserSettings.getEncryptPasswords())
/*     */       {
/*  82 */         fileWriter.setHidePasswords(true);
/*     */       }
/*     */ 
/*  85 */       fileWriter.writeBeans(vecUsers, null, true);
/*  86 */       writer.close();
/*     */ 
/*  89 */       result = new ExportResult();
/*  90 */       result.setDataFile(new FileBean(sFilename, sDataFilepath));
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*  94 */       this.m_logger.error("UserExportManager.exportUsers() : Unable to create file for output");
/*  95 */       throw new Bn2Exception("UserExportManager.exportUsers() : Unable to create file for output", ioe);
/*     */     }
/*     */ 
/*  98 */     return result;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/* 103 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 108 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.service.UserExportManager
 * JD-Core Version:    0.6.0
 */