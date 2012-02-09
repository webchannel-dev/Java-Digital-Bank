/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ public class UploadedImageInfo extends UploadedFileInfo
/*    */ {
/* 21 */   private int m_iImageHeight = 0;
/* 22 */   private int m_iImageWidth = 0;
/* 23 */   private int m_iNumLayers = 0;
/*    */ 
/*    */   public UploadedImageInfo(UploadedFileInfo a_toCopy)
/*    */   {
/* 30 */     super(a_toCopy);
/*    */   }
/*    */ 
/*    */   public int getImageHeight()
/*    */   {
/* 35 */     return this.m_iImageHeight;
/*    */   }
/*    */ 
/*    */   public void setImageHeight(int a_sImageHeight)
/*    */   {
/* 40 */     this.m_iImageHeight = a_sImageHeight;
/*    */   }
/*    */ 
/*    */   public int getImageWidth()
/*    */   {
/* 45 */     return this.m_iImageWidth;
/*    */   }
/*    */ 
/*    */   public void setImageWidth(int a_sImageWidth)
/*    */   {
/* 50 */     this.m_iImageWidth = a_sImageWidth;
/*    */   }
/*    */ 
/*    */   public int getNumLayers()
/*    */   {
/* 55 */     return this.m_iNumLayers;
/*    */   }
/*    */ 
/*    */   public void setNumLayers(int numLayers)
/*    */   {
/* 60 */     this.m_iNumLayers = numLayers;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.UploadedImageInfo
 * JD-Core Version:    0.6.0
 */