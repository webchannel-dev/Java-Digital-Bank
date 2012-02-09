/*    */ package com.bright.assetbank.cmsintegration.bean;
/*    */ 
/*    */ public class CmsUploadResult
/*    */ {
/* 26 */   private String m_sRemoteFilename = null;
/* 27 */   private String m_sRemoteDirectory = null;
/* 28 */   private String m_sSubfolder = null;
/*    */ 
/*    */   public String getRemoteDirectory()
/*    */   {
/* 32 */     return this.m_sRemoteDirectory;
/*    */   }
/*    */ 
/*    */   public void setRemoteDirectory(String remoteDirectory) {
/* 36 */     this.m_sRemoteDirectory = remoteDirectory;
/*    */   }
/*    */ 
/*    */   public String getRemoteFilename()
/*    */   {
/* 41 */     return this.m_sRemoteFilename;
/*    */   }
/*    */ 
/*    */   public void setRemoteFilename(String remoteFilename) {
/* 45 */     this.m_sRemoteFilename = remoteFilename;
/*    */   }
/*    */ 
/*    */   public String getSubfolder()
/*    */   {
/* 50 */     return this.m_sSubfolder;
/*    */   }
/*    */ 
/*    */   public void setSubfolder(String a_sSubfolder) {
/* 54 */     this.m_sSubfolder = a_sSubfolder;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.bean.CmsUploadResult
 * JD-Core Version:    0.6.0
 */