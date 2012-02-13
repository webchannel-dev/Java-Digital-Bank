/*    */ package com.bright.assetbank.report.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ import com.bright.framework.storage.constant.StoredFileType;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ 
/*    */ public class AssetRecord extends StringDataBean
/*    */ {
/* 33 */   boolean m_bApproved = false;
/*    */ 
/* 36 */   private String m_sAttributeValue = "";
/*    */ 
/* 39 */   private String m_sAttributeName = "";
/*    */ 
/* 41 */   private String m_sThumbnailPath = null;
/*    */ 
/*    */   public boolean getApproved()
/*    */   {
/* 45 */     return this.m_bApproved;
/*    */   }
/*    */ 
/*    */   public void setApproved(boolean a_sApproved) {
/* 49 */     this.m_bApproved = a_sApproved;
/*    */   }
/*    */ 
/*    */   public String getAttributeValue() {
/* 53 */     return this.m_sAttributeValue;
/*    */   }
/*    */ 
/*    */   public void setAttributeValue(String a_sAttributeValue) {
/* 57 */     this.m_sAttributeValue = a_sAttributeValue;
/*    */   }
/*    */ 
/*    */   public String getAttributeName() {
/* 61 */     return this.m_sAttributeName;
/*    */   }
/*    */ 
/*    */   public void setAttributeName(String a_sAttributeName) {
/* 65 */     this.m_sAttributeName = a_sAttributeName;
/*    */   }
/*    */ 
/*    */   public String getThumbnailPath()
/*    */   {
/* 70 */     return this.m_sThumbnailPath;
/*    */   }
/*    */ 
/*    */   public String getRc4EncryptedThumbnailPath()
/*    */   {
/* 75 */     return FileUtil.encryptFilepath(this.m_sThumbnailPath);
/*    */   }
/*    */ 
/*    */   public void setThumbnailPath(String a_sThumbnailPath)
/*    */   {
/* 80 */     this.m_sThumbnailPath = a_sThumbnailPath;
/*    */   }
/*    */ 
/*    */   public boolean getHasIcon()
/*    */   {
/* 85 */     return (this.m_sThumbnailPath != null) && (this.m_sThumbnailPath.startsWith(StoredFileType.SHARED_THUMBNAIL.getDirectoryName()));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.bean.AssetRecord
 * JD-Core Version:    0.6.0
 */