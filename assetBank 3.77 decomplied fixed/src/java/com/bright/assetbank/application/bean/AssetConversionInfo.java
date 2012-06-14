/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ public class AssetConversionInfo
/*    */ {
/*    */   private boolean m_bDeferAllowed;
/* 29 */   private boolean m_bSupressWatermark = false;
/* 30 */   private boolean m_bDeferThumbnailGeneration = false;
/* 31 */   private boolean m_bDoNotReEmbedMetadata = false;
/*    */ 
/*    */   public AssetConversionInfo()
/*    */   {
/*    */   }
/*    */ 
/*    */   public AssetConversionInfo(AssetConversionInfo a_copyFrom)
/*    */   {
/* 41 */     this.m_bDeferAllowed = a_copyFrom.m_bDeferAllowed;
/* 42 */     this.m_bDeferThumbnailGeneration = a_copyFrom.m_bDeferThumbnailGeneration;
/* 43 */     this.m_bSupressWatermark = a_copyFrom.m_bSupressWatermark;
/*    */   }
/*    */ 
/*    */   public boolean isDeferAllowed()
/*    */   {
/* 48 */     return this.m_bDeferAllowed;
/*    */   }
/*    */ 
/*    */   public void setDeferAllowed(boolean a_sDeferAllowed)
/*    */   {
/* 53 */     this.m_bDeferAllowed = a_sDeferAllowed;
/*    */   }
/*    */ 
/*    */   public boolean getSupressWatermark()
/*    */   {
/* 58 */     return this.m_bSupressWatermark;
/*    */   }
/*    */ 
/*    */   public void setSupressWatermark(boolean supressWatermark)
/*    */   {
/* 63 */     this.m_bSupressWatermark = supressWatermark;
/*    */   }
/*    */ 
/*    */   public boolean getDeferThumbnailGeneration()
/*    */   {
/* 68 */     return this.m_bDeferThumbnailGeneration;
/*    */   }
/*    */ 
/*    */   public void setDeferThumbnailGeneration(boolean a_bDeferThumbnailGeneration)
/*    */   {
/* 73 */     this.m_bDeferThumbnailGeneration = a_bDeferThumbnailGeneration;
/*    */   }
/*    */ 
/*    */   public boolean getDoNotReEmbedMetadata()
/*    */   {
/* 78 */     return this.m_bDoNotReEmbedMetadata;
/*    */   }
/*    */ 
/*    */   public void setDoNotReEmbedMetadata(boolean a_bDoNotReEmbedMetadata)
/*    */   {
/* 83 */     this.m_bDoNotReEmbedMetadata = a_bDoNotReEmbedMetadata;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.AssetConversionInfo
 * JD-Core Version:    0.6.0
 */