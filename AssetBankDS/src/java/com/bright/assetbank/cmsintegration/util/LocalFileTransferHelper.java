/*    */ package com.bright.assetbank.cmsintegration.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ 
/*    */ public class LocalFileTransferHelper
/*    */   implements FileTransferHelper
/*    */ {
/*    */   public String copyFile(String a_sSourceFilepath, String a_sDestinationDir, String a_sDestinationFilename, boolean a_bMakeFilenameUnique)
/*    */     throws IOException
/*    */   {
/* 37 */     File fDirectory = new File(a_sDestinationDir);
/* 38 */     if (!fDirectory.exists())
/*    */     {
/* 40 */       fDirectory.mkdir();
/*    */     }
/*    */ 
/* 43 */     String sDestFilename = a_sDestinationFilename;
/*    */ 
/* 46 */     if (a_bMakeFilenameUnique)
/*    */     {
/* 48 */       sDestFilename = FileUtil.getUniqueFilename(a_sDestinationDir, sDestFilename);
/*    */     }
/*    */ 
/* 51 */     String sDestination = a_sDestinationDir + "/" + sDestFilename;
/*    */ 
/* 54 */     FileUtils.copyFile(new File(a_sSourceFilepath), new File(sDestination));
/*    */ 
/* 56 */     return sDestFilename;
/*    */   }
/*    */ 
/*    */   public boolean requiresConnection()
/*    */   {
/* 61 */     return false;
/*    */   }
/*    */ 
/*    */   public void connect(String a_sHostname, int a_iPort, String a_sUsername, String a_sPassword)
/*    */     throws Bn2Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public void disconnect()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.util.LocalFileTransferHelper
 * JD-Core Version:    0.6.0
 */