/*    */ package com.bright.assetbank.custom.indesign.asset.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class FileInfo
/*    */   implements Serializable
/*    */ {
/*    */   private String m_fileLocation;
/*    */   private String m_originalFilename;
/*    */   private long m_fileSizeInBytes;
/*    */ 
/*    */   public void populateFileInfoFrom(FileInfo a_source)
/*    */   {
/* 29 */     setFileLocation(a_source.getFileLocation());
/* 30 */     setOriginalFilename(a_source.getOriginalFilename());
/* 31 */     setFileSizeInBytes(a_source.getFileSizeInBytes());
/*    */   }
/*    */ 
/*    */   public String getFileLocation()
/*    */   {
/* 37 */     return this.m_fileLocation;
/*    */   }
/*    */ 
/*    */   public void setFileLocation(String a_fileLocation)
/*    */   {
/* 42 */     this.m_fileLocation = a_fileLocation;
/*    */   }
/*    */ 
/*    */   public String getOriginalFilename()
/*    */   {
/* 47 */     return this.m_originalFilename;
/*    */   }
/*    */ 
/*    */   public void setOriginalFilename(String a_originalFilename)
/*    */   {
/* 52 */     this.m_originalFilename = a_originalFilename;
/*    */   }
/*    */ 
/*    */   public long getFileSizeInBytes()
/*    */   {
/* 57 */     return this.m_fileSizeInBytes;
/*    */   }
/*    */ 
/*    */   public void setFileSizeInBytes(long a_fileSizeInBytes)
/*    */   {
/* 62 */     this.m_fileSizeInBytes = a_fileSizeInBytes;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.bean.FileInfo
 * JD-Core Version:    0.6.0
 */