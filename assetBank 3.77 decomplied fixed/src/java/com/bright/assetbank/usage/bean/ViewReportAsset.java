/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import com.bright.framework.storage.constant.StoredFileType;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ViewReportAsset extends ReportEntity
/*    */ {
/* 35 */   private Vector m_vGroups = null;
/* 36 */   private Vector m_vUsers = null;
/* 37 */   private String m_sOriginalFilename = null;
/* 38 */   private String m_sThumbnailPath = null;
/*    */ 
/*    */   public Vector getGroups()
/*    */   {
/* 43 */     if (this.m_vGroups == null)
/*    */     {
/* 45 */       this.m_vGroups = new Vector();
/*    */     }
/*    */ 
/* 48 */     return this.m_vGroups;
/*    */   }
/*    */ 
/*    */   public void setGroups(Vector a_sGroups)
/*    */   {
/* 54 */     this.m_vGroups = a_sGroups;
/*    */   }
/*    */ 
/*    */   public Vector getUsers()
/*    */   {
/* 60 */     if (this.m_vUsers == null)
/*    */     {
/* 62 */       this.m_vUsers = new Vector();
/*    */     }
/*    */ 
/* 65 */     return this.m_vUsers;
/*    */   }
/*    */ 
/*    */   public void setUsers(Vector a_sUsers)
/*    */   {
/* 71 */     this.m_vUsers = a_sUsers;
/*    */   }
/*    */ 
/*    */   public String getOriginalFilename() {
/* 75 */     return this.m_sOriginalFilename;
/*    */   }
/*    */ 
/*    */   public void setOriginalFilename(String a_sOriginalFilename) {
/* 79 */     this.m_sOriginalFilename = a_sOriginalFilename;
/*    */   }
/*    */ 
/*    */   public String getThumbnailPath()
/*    */   {
/* 84 */     return this.m_sThumbnailPath;
/*    */   }
/*    */ 
/*    */   public String getRc4EncryptedThumbnailPath()
/*    */   {
/* 89 */     return FileUtil.encryptFilepath(this.m_sThumbnailPath);
/*    */   }
/*    */ 
/*    */   public void setThumbnailPath(String a_sThumbnailPath)
/*    */   {
/* 94 */     this.m_sThumbnailPath = a_sThumbnailPath;
/*    */   }
/*    */ 
/*    */   public boolean getHasIcon()
/*    */   {
/* 99 */     return (this.m_sThumbnailPath != null) && (this.m_sThumbnailPath.startsWith(StoredFileType.SHARED_THUMBNAIL.getDirectoryName()));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.ViewReportAsset
 * JD-Core Version:    0.6.0
 */