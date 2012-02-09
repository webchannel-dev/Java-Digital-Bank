/*     */ package com.bright.assetbank.cmsintegration.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.util.Bn2FTP;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class FtpFileTransferHelper
/*     */   implements FileTransferHelper
/*     */ {
/*     */   private static final String k_sClassName = "FtpFileTransferHelper";
/*  36 */   private Bn2FTP m_ftp = null;
/*     */ 
/*     */   public boolean requiresConnection()
/*     */   {
/*  41 */     return true;
/*     */   }
/*     */ 
/*     */   public void connect(String a_sHostname, int a_iPort, String a_sUsername, String a_sPassword)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/*  51 */       this.m_ftp = new Bn2FTP(a_sHostname, a_iPort);
/*  52 */       this.m_ftp.connect();
/*  53 */       this.m_ftp.authenticate(a_sUsername, a_sPassword);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  57 */       throw new Bn2Exception("FtpFileTransferHelper.connect() : Could not connect to FTP server", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void disconnect()
/*     */   {
/*  63 */     if (this.m_ftp != null)
/*     */     {
/*  65 */       this.m_ftp.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void finalize()
/*     */   {
/*  71 */     disconnect();
/*     */   }
/*     */ 
/*     */   public String copyFile(String a_sSourceFilepath, String a_sDestinationDir, String a_sDestinationFilename, boolean a_bMakeFilenameUnique) throws IOException
/*     */   {
/*  76 */     if (this.m_ftp == null)
/*     */     {
/*  78 */       throw new IllegalStateException("FtpFileTransferHelper.copyFile() : No ftp session has been opened.");
/*     */     }
/*     */ 
/*  81 */     if (StringUtils.isEmpty(a_sDestinationDir))
/*     */     {
/*  83 */       a_sDestinationDir = ".";
/*     */     }
/*     */ 
/*  86 */     String sCmsFilename = a_sDestinationFilename;
/*     */     try
/*     */     {
/*  90 */       this.m_ftp.setType(1);
/*  91 */       this.m_ftp.changeDir(a_sDestinationDir);
/*     */ 
/*  94 */       if (a_bMakeFilenameUnique)
/*     */       {
/*  96 */         String sCmsFilenameSuffix = FileUtil.getSuffix(a_sDestinationFilename);
/*  97 */         String sCmsFilenameSuffixFree = FileUtil.getFilenameWithoutSuffix(a_sDestinationFilename);
/*  98 */         int iCount = 0;
/*  99 */         while (this.m_ftp.exists(sCmsFilename))
/*     */         {
/* 101 */           sCmsFilename = sCmsFilenameSuffixFree + iCount + "." + sCmsFilenameSuffix;
/* 102 */           iCount++;
/*     */         }
/*     */       }
/* 105 */       this.m_ftp.put(new File(a_sSourceFilepath), sCmsFilename, false);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 109 */       throw new IOException("FtpFileTransferHelper.connect() : Could not copy file to ftp server");
/*     */     }
/*     */ 
/* 112 */     return sCmsFilename;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.util.FtpFileTransferHelper
 * JD-Core Version:    0.6.0
 */