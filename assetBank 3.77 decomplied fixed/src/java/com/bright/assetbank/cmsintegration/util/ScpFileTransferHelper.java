/*     */ package com.bright.assetbank.cmsintegration.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.jcraft.jsch.Channel;
/*     */ import com.jcraft.jsch.ChannelExec;
/*     */ import com.jcraft.jsch.JSch;
/*     */ import com.jcraft.jsch.JSchException;
/*     */ import com.jcraft.jsch.Session;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ScpFileTransferHelper
/*     */   implements FileTransferHelper
/*     */ {
/*     */   private static final String k_sScpCode_SourceMode = "C0644";
/*     */   private static final int k_iFileReadBlockSizeInBytes = 1024;
/*     */   private static final int k_iErrorCode_Fatal = 2;
/*     */   private static final String k_sClassName = "ScpFileTransferHelper";
/*  50 */   private Session m_session = null;
/*     */ 
/*     */   public boolean requiresConnection()
/*     */   {
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */   public void connect(String a_sHostname, int a_iPort, String a_sUsername, String a_sPassword)
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     JSch jsch = new JSch();
/*     */     try
/*     */     {
/*  68 */       jsch.setKnownHosts(AssetBankSettings.getKnownHostsFilepath());
/*     */     }
/*     */     catch (JSchException e)
/*     */     {
/*  72 */       throw new Bn2Exception("ScpFileTransferHelper.connect() : Could not initialise JSch with location of known_hosts file (from app setting: known-hosts-file) : " + e.getLocalizedMessage(), e);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  77 */       this.m_session = jsch.getSession(a_sUsername, a_sHostname, a_iPort);
/*  78 */       this.m_session.setPassword(a_sPassword);
/*  79 */       this.m_session.connect();
/*     */     }
/*     */     catch (JSchException e)
/*     */     {
/*  83 */       throw new Bn2Exception("ScpFileTransferHelper.connect() : Could not connect to host using SCP due to JSchException: " + e.getLocalizedMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void disconnect()
/*     */   {
/*  89 */     if ((this.m_session != null) && (this.m_session.isConnected()))
/*     */     {
/*  91 */       this.m_session.disconnect();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void finalize()
/*     */   {
/*  97 */     disconnect();
/*     */   }
/*     */ 
/*     */   public String copyFile(String a_sSourceFilepath, String a_sDestinationDir, String a_sDestinationFilename, boolean a_bMakeFilenameUnique) throws IOException
/*     */   {
/* 102 */     if ((this.m_session == null) || (!this.m_session.isConnected()))
/*     */     {
/* 104 */       throw new IllegalStateException("ScpFileTransferHelper.copyFile() : No scp session has been opened.");
/*     */     }
/*     */ 
/* 107 */     if (StringUtils.isEmpty(a_sDestinationDir))
/*     */     {
/* 109 */       a_sDestinationDir = ".";
/*     */     }
/*     */ 
/* 112 */     String sCmsFilename = a_sDestinationFilename;
/*     */ 
/* 116 */     String sCommand = "scp -p -t " + a_sDestinationDir + "/" + a_sDestinationFilename;
/* 117 */     Channel channel = null;
/* 118 */     OutputStream out = null;
/* 119 */     InputStream in = null;
/* 120 */     FileInputStream fis = null;
/*     */     try
/*     */     {
/* 125 */       channel = this.m_session.openChannel("exec");
/* 126 */       ((ChannelExec)channel).setCommand(sCommand);
/* 127 */       channel.connect();
/*     */ 
/* 130 */       out = channel.getOutputStream();
/* 131 */       in = channel.getInputStream();
/* 132 */       BufferedReader reader = new BufferedReader(new InputStreamReader(in));
/*     */ 
/* 135 */       checkAck(reader);
/*     */ 
/* 138 */       long filesize = new File(a_sSourceFilepath).length();
/* 139 */       sCommand = "C0644 " + filesize + " ";
/* 140 */       sCommand = sCommand + a_sSourceFilepath.substring(a_sSourceFilepath.lastIndexOf('/') + 1);
/* 141 */       sCommand = sCommand + "\n";
/* 142 */       out.write(sCommand.getBytes());
/* 143 */       out.flush();
/*     */ 
/* 146 */       checkAck(reader);
/*     */ 
/* 149 */       fis = new FileInputStream(a_sSourceFilepath);
/* 150 */       byte[] buf = new byte[1024];
/*     */       while (true)
/*     */       {
/* 153 */         int len = fis.read(buf, 0, buf.length);
/* 154 */         if (len <= 0)
/*     */         {
/*     */           break;
/*     */         }
/* 158 */         out.write(buf, 0, len);
/*     */       }
/* 160 */       fis.close();
/* 161 */       fis = null;
/*     */ 
/* 163 */       buf[0] = 0;
/* 164 */       out.write(buf, 0, 1);
/* 165 */       out.flush();
/*     */ 
/* 168 */       checkAck(reader);
/*     */     }
/*     */     catch (JSchException e)
/*     */     {
/* 172 */       throw new IOException("ScpFileTransferHelper.copyFile() : JSchException caught while copying file to remote server using scp : " + e.getLocalizedMessage());
/*     */     }
/*     */     finally
/*     */     {
/* 176 */       IOUtils.closeQuietly(out);
/* 177 */       IOUtils.closeQuietly(in);
/* 178 */       IOUtils.closeQuietly(fis);
/* 179 */       if (channel.isConnected())
/*     */       {
/* 181 */         channel.disconnect();
/*     */       }
/*     */     }
/*     */ 
/* 185 */     return sCmsFilename;
/*     */   }
/*     */ 
/*     */   private static int checkAck(BufferedReader a_reader)
/*     */     throws IOException
/*     */   {
/* 196 */     int iReturnCode = a_reader.read();
/*     */ 
/* 198 */     if (iReturnCode > 0)
/*     */     {
/* 200 */       throw new IOException("Return code from SCP server was " + iReturnCode + ", indicating a" + (iReturnCode == 2 ? " fatal" : "n") + " error. Message from server: " + a_reader.readLine());
/*     */     }
/*     */ 
/* 203 */     return iReturnCode;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.util.ScpFileTransferHelper
 * JD-Core Version:    0.6.0
 */