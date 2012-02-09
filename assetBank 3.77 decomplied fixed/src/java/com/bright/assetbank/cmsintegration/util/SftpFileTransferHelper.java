/*     */ package com.bright.assetbank.cmsintegration.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.jcraft.jsch.ChannelSftp;
/*     */ import com.jcraft.jsch.ChannelSftp.LsEntry;
/*     */ import com.jcraft.jsch.JSch;
/*     */ import com.jcraft.jsch.JSchException;
/*     */ import com.jcraft.jsch.Session;
/*     */ import com.jcraft.jsch.SftpException;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class SftpFileTransferHelper
/*     */   implements FileTransferHelper
/*     */ {
/*     */   private static final String k_sClassName = "SftpFileTransferHelper";
/*  45 */   private Session m_session = null;
/*     */ 
/*     */   public boolean requiresConnection()
/*     */   {
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */   public void connect(String a_sHostname, int a_iPort, String a_sUsername, String a_sPassword)
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     JSch jsch = new JSch();
/*     */     try
/*     */     {
/*  63 */       jsch.setKnownHosts(AssetBankSettings.getKnownHostsFilepath());
/*     */     }
/*     */     catch (JSchException e)
/*     */     {
/*  67 */       throw new Bn2Exception("ScpFileTransferHelper.connect() : Could not initialise JSch with location of known_hosts file (from app setting: known-hosts-file) : " + e.getLocalizedMessage(), e);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  72 */       this.m_session = jsch.getSession(a_sUsername, a_sHostname, a_iPort);
/*  73 */       this.m_session.setPassword(a_sPassword);
/*  74 */       this.m_session.connect();
/*     */     }
/*     */     catch (JSchException e)
/*     */     {
/*  78 */       throw new Bn2Exception("ScpFileTransferHelper.connect() : Could not connect to host using SCP due to JSchException: " + e.getLocalizedMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void disconnect()
/*     */   {
/*  84 */     if ((this.m_session != null) && (this.m_session.isConnected()))
/*     */     {
/*  86 */       this.m_session.disconnect();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void finalize()
/*     */   {
/*  92 */     disconnect();
/*     */   }
/*     */ 
/*     */   public String copyFile(String a_sSourceFilepath, String a_sDestinationDir, String a_sDestinationFilename, boolean a_bMakeFilenameUnique) throws IOException
/*     */   {
/*  97 */     if ((this.m_session == null) || (!this.m_session.isConnected()))
/*     */     {
/*  99 */       throw new IllegalStateException("ScpFileTransferHelper.copyFile() : No scp session has been opened.");
/*     */     }
/*     */ 
/* 102 */     if (StringUtils.isEmpty(a_sDestinationDir))
/*     */     {
/* 104 */       a_sDestinationDir = ".";
/*     */     }
/*     */ 
/* 107 */     String sCmsFilename = a_sDestinationFilename;
/*     */ 
/* 111 */     ChannelSftp channel = null;
/* 112 */     FileInputStream fis = null;
/*     */     try
/*     */     {
/* 117 */       channel = (ChannelSftp)this.m_session.openChannel("sftp");
/* 118 */       channel.connect();
/*     */ 
/* 120 */       if (a_bMakeFilenameUnique)
/*     */       {
/* 122 */         int iSuffix = 1;
/* 123 */         Vector vListing = channel.ls(a_sDestinationDir);
/* 124 */         for (int i = 0; i < vListing.size(); i++)
/*     */         {
/* 126 */           ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry)vListing.get(i);
/* 127 */           if (!entry.getFilename().equalsIgnoreCase(sCmsFilename))
/*     */             continue;
/* 129 */           sCmsFilename = FileUtil.getFilepathWithoutSuffix(a_sDestinationFilename) + iSuffix++ + "." + FileUtil.getSuffix(a_sDestinationFilename);
/* 130 */           i = 0;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 135 */       fis = new FileInputStream(a_sSourceFilepath);
/*     */ 
/* 137 */       channel.put(fis, a_sDestinationDir + "/" + sCmsFilename);
/*     */     }
/*     */     catch (JSchException e)
/*     */     {
/* 141 */       throw new IOException("SftpFileTransferHelper.copyFile() : JSchException caught while copying file to remote server using sftp : " + e.getMessage());
/*     */     }
/*     */     catch (SftpException e)
/*     */     {
/* 145 */       throw new IOException("SftpFileTransferHelper.copyFile() : SftpException caught while copying file to remote server using sftp : " + e.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/* 149 */       IOUtils.closeQuietly(fis);
/* 150 */       if (channel.isConnected())
/*     */       {
/* 152 */         channel.disconnect();
/*     */       }
/*     */     }
/*     */ 
/* 156 */     return sCmsFilename;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.util.SftpFileTransferHelper
 * JD-Core Version:    0.6.0
 */