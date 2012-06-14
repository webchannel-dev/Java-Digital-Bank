/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ public class AudioAsset extends Asset
/*    */ {
/* 30 */   private FileFormat m_previewClipFormat = null;
/* 31 */   private long m_lDuration = 0L;
/*    */ 
/*    */   public AudioAsset()
/*    */   {
/*    */   }
/*    */ 
/*    */   public AudioAsset(Asset a_asset)
/*    */   {
/* 51 */     super(a_asset);
/*    */   }
/*    */ 
/*    */   public FileFormat getPreviewClipFormat()
/*    */   {
/* 56 */     return this.m_previewClipFormat;
/*    */   }
/*    */ 
/*    */   public void setPreviewClipFormat(FileFormat a_sPreviewClipFormat) {
/* 60 */     this.m_previewClipFormat = a_sPreviewClipFormat;
/*    */   }
/*    */ 
/*    */   public long getDuration()
/*    */   {
/* 65 */     return this.m_lDuration;
/*    */   }
/*    */ 
/*    */   public void setDuration(long a_iDuration)
/*    */   {
/* 70 */     this.m_lDuration = a_iDuration;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.AudioAsset
 * JD-Core Version:    0.6.0
 */