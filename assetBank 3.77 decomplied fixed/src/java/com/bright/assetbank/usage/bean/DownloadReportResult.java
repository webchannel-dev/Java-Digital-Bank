/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import com.bright.framework.storage.constant.StoredFileType;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class DownloadReportResult extends ReportEntity
/*    */ {
/* 37 */   private Vector m_vecGroupUses = new Vector();
/* 38 */   private Vector m_vecUserUses = new Vector();
/* 39 */   private String m_sOriginalFilename = null;
/* 40 */   private String m_sThumbnailPath = null;
/*    */ 
/*    */   public Vector getGroupUses()
/*    */   {
/* 45 */     return this.m_vecGroupUses;
/*    */   }
/*    */ 
/*    */   public void setGroupUses(Vector a_vecGroupUses)
/*    */   {
/* 51 */     this.m_vecGroupUses = a_vecGroupUses;
/*    */   }
/*    */ 
/*    */   public Vector getUserUses()
/*    */   {
/* 57 */     return this.m_vecUserUses;
/*    */   }
/*    */ 
/*    */   public void setUserUses(Vector a_vecUserUses)
/*    */   {
/* 63 */     this.m_vecUserUses = a_vecUserUses;
/*    */   }
/*    */ 
/*    */   public String getOriginalFilename()
/*    */   {
/* 68 */     return this.m_sOriginalFilename;
/*    */   }
/*    */ 
/*    */   public void setOriginalFilename(String a_sOriginalFilename) {
/* 72 */     this.m_sOriginalFilename = a_sOriginalFilename;
/*    */   }
/*    */ 
/*    */   public String getThumbnailPath()
/*    */   {
/* 77 */     return this.m_sThumbnailPath;
/*    */   }
/*    */ 
/*    */   public String getRc4EncryptedThumbnailPath()
/*    */   {
/* 82 */     return FileUtil.encryptFilepath(this.m_sThumbnailPath);
/*    */   }
/*    */ 
/*    */   public void setThumbnailPath(String a_sThumbnailPath)
/*    */   {
/* 87 */     this.m_sThumbnailPath = a_sThumbnailPath;
/*    */   }
/*    */ 
/*    */   public boolean getHasIcon()
/*    */   {
/* 92 */     return (this.m_sThumbnailPath != null) && (this.m_sThumbnailPath.startsWith(StoredFileType.SHARED_THUMBNAIL.getDirectoryName()));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.DownloadReportResult
 * JD-Core Version:    0.6.0
 */