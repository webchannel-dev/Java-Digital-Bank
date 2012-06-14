/*    */ package com.bright.assetbank.synchronise.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.FileBean;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ExportResult
/*    */ {
/* 33 */   private FileBean m_sDataFile = null;
/* 34 */   private List m_sZipFiles = null;
/* 35 */   private int m_iExportCount = 0;
/* 36 */   private int m_iTotalFileCount = 0;
/*    */   private String m_sFileExportLocation;
/*    */ 
/*    */   public FileBean getDataFile()
/*    */   {
/* 41 */     return this.m_sDataFile;
/*    */   }
/*    */ 
/*    */   public void setDataFile(FileBean a_dataFile) {
/* 45 */     this.m_sDataFile = a_dataFile;
/*    */   }
/*    */ 
/*    */   public List getZipFiles() {
/* 49 */     if (this.m_sZipFiles == null)
/*    */     {
/* 51 */       this.m_sZipFiles = new Vector();
/*    */     }
/* 53 */     return this.m_sZipFiles;
/*    */   }
/*    */ 
/*    */   public void setZipFiles(List a_zipFiles) {
/* 57 */     this.m_sZipFiles = a_zipFiles;
/*    */   }
/*    */ 
/*    */   public void setExportCount(int a_iExportCount)
/*    */   {
/* 62 */     this.m_iExportCount = a_iExportCount;
/*    */   }
/*    */ 
/*    */   public int getExportCount()
/*    */   {
/* 67 */     return this.m_iExportCount;
/*    */   }
/*    */ 
/*    */   public int getTotalFileCount() {
/* 71 */     return this.m_iTotalFileCount;
/*    */   }
/*    */ 
/*    */   public void setTotalFileCount(int a_iTotalFileCount) {
/* 75 */     this.m_iTotalFileCount = a_iTotalFileCount;
/*    */   }
/*    */ 
/*    */   public String getFileExportLocation() {
/* 79 */     return this.m_sFileExportLocation;
/*    */   }
/*    */ 
/*    */   public void setFileExportLocation(String a_sFileExportLocation) {
/* 83 */     this.m_sFileExportLocation = a_sFileExportLocation;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ExportResult
 * JD-Core Version:    0.6.0
 */