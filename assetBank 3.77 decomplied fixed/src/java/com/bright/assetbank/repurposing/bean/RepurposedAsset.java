/*    */ package com.bright.assetbank.repurposing.bean;
/*    */ 
/*    */ public class RepurposedAsset extends RepurposedVersion
/*    */ {
/* 27 */   private long m_lAssetId = 0L;
/* 28 */   private long m_lFileFormatId = 0L;
/* 29 */   private String m_sPreviewFileLocation = null;
/* 30 */   private long m_lDuration = -1L;
/*    */ 
/*    */   public String getPreviewFileLocation()
/*    */   {
/* 34 */     return this.m_sPreviewFileLocation;
/*    */   }
/*    */ 
/*    */   public void setPreviewFileLocation(String a_sPreviewFileLocation)
/*    */   {
/* 39 */     this.m_sPreviewFileLocation = a_sPreviewFileLocation;
/*    */   }
/*    */ 
/*    */   public long getDuration()
/*    */   {
/* 44 */     return this.m_lDuration;
/*    */   }
/*    */ 
/*    */   public void setDuration(long a_lDuration)
/*    */   {
/* 49 */     this.m_lDuration = a_lDuration;
/*    */   }
/*    */ 
/*    */   public long getAssetId()
/*    */   {
/* 54 */     return this.m_lAssetId;
/*    */   }
/*    */ 
/*    */   public void setAssetId(long a_lAssetId) {
/* 58 */     this.m_lAssetId = a_lAssetId;
/*    */   }
/*    */ 
/*    */   public long getFileFormatId() {
/* 62 */     return this.m_lFileFormatId;
/*    */   }
/*    */ 
/*    */   public void setFileFormatId(long a_lFileFormatId) {
/* 66 */     this.m_lFileFormatId = a_lFileFormatId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.bean.RepurposedAsset
 * JD-Core Version:    0.6.0
 */