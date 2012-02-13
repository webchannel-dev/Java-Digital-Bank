/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ public class VideoConversionResult
/*    */ {
/* 27 */   private String m_sConvertedFilename = null;
/* 28 */   private boolean m_bCompress = false;
/* 29 */   private boolean m_bUsedOriginal = false;
/* 30 */   private Asset m_convertedAsset = null;
/*    */ 
/*    */   public String getConvertedFilename()
/*    */   {
/* 34 */     return this.m_sConvertedFilename;
/*    */   }
/*    */ 
/*    */   public void setConvertedFilename(String a_sConvertedFilename)
/*    */   {
/* 39 */     this.m_sConvertedFilename = a_sConvertedFilename;
/*    */   }
/*    */ 
/*    */   public boolean getCompress()
/*    */   {
/* 44 */     return this.m_bCompress;
/*    */   }
/*    */ 
/*    */   public void setCompress(boolean a_bCompress)
/*    */   {
/* 49 */     this.m_bCompress = a_bCompress;
/*    */   }
/*    */ 
/*    */   public boolean getUsedOriginal()
/*    */   {
/* 54 */     return this.m_bUsedOriginal;
/*    */   }
/*    */ 
/*    */   public void setUsedOriginal(boolean a_bUsedOriginal)
/*    */   {
/* 59 */     this.m_bUsedOriginal = a_bUsedOriginal;
/*    */   }
/*    */ 
/*    */   public void setConvertedAsset(Asset a_convertedAsset)
/*    */   {
/* 64 */     this.m_convertedAsset = a_convertedAsset;
/*    */   }
/*    */ 
/*    */   public Asset getConvertedAsset()
/*    */   {
/* 69 */     return this.m_convertedAsset;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.VideoConversionResult
 * JD-Core Version:    0.6.0
 */