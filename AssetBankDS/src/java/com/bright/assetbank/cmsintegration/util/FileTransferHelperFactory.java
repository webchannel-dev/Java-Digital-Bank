/*    */ package com.bright.assetbank.cmsintegration.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class FileTransferHelperFactory
/*    */ {
/*    */   public static final String k_sTransferTypeScp = "scp";
/*    */   public static final String k_sTransferTypeSftp = "sftp";
/*    */   public static final String k_sTransferTypeLocal = "local";
/*    */   public static final String k_sTransferTypeFtp = "ftp";
/*    */ 
/*    */   public static FileTransferHelper createHelper(String a_sMethod)
/*    */     throws Bn2Exception
/*    */   {
/* 36 */     if ("local".equalsIgnoreCase(a_sMethod))
/*    */     {
/* 38 */       return new LocalFileTransferHelper();
/*    */     }
/* 40 */     if ("scp".equalsIgnoreCase(a_sMethod))
/*    */     {
/* 42 */       return new ScpFileTransferHelper();
/*    */     }
/* 44 */     if ("sftp".equalsIgnoreCase(a_sMethod))
/*    */     {
/* 46 */       return new SftpFileTransferHelper();
/*    */     }
/* 48 */     if ("ftp".equalsIgnoreCase(a_sMethod))
/*    */     {
/* 50 */       return new FtpFileTransferHelper();
/*    */     }
/*    */ 
/* 53 */     throw new Bn2Exception("FileTransferHelperFactory.createHelper() : Value of AssetBankSettings.getCmsFileTransferMethod() isn't one of (local|scp|sftp)");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.util.FileTransferHelperFactory
 * JD-Core Version:    0.6.0
 */