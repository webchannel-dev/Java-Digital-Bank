/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ public class ImageFileInfo
/*    */ {
/* 18 */   private int[] m_iHeights = null;
/* 19 */   private int[] m_iWidths = null;
/* 20 */   private int m_iColorSpace = 0;
/*    */ 
/*    */   public ImageFileInfo(int a_iNumOfLayers)
/*    */   {
/* 24 */     this.m_iHeights = new int[a_iNumOfLayers];
/* 25 */     this.m_iWidths = new int[a_iNumOfLayers];
/*    */   }
/*    */ 
/*    */   public int getHeight()
/*    */   {
/* 30 */     return getHeight(0);
/*    */   }
/*    */ 
/*    */   public int getHeight(int a_iLayer)
/*    */   {
/* 35 */     return this.m_iHeights[a_iLayer];
/*    */   }
/*    */ 
/*    */   public int getColorSpace()
/*    */   {
/* 40 */     return this.m_iColorSpace;
/*    */   }
/*    */ 
/*    */   public int getWidth()
/*    */   {
/* 45 */     return getWidth(0);
/*    */   }
/*    */ 
/*    */   public int getWidth(int a_iLayer)
/*    */   {
/* 50 */     return this.m_iWidths[a_iLayer];
/*    */   }
/*    */ 
/*    */   public int getNumberOfLayers()
/*    */   {
/* 55 */     return this.m_iHeights.length;
/*    */   }
/*    */ 
/*    */   public void setHeight(int a_iLayer, int a_iHeight)
/*    */   {
/* 61 */     this.m_iHeights[a_iLayer] = a_iHeight;
/*    */   }
/*    */ 
/*    */   public void setWidth(int a_iLayer, int a_iWidth)
/*    */   {
/* 67 */     this.m_iWidths[a_iLayer] = a_iWidth;
/*    */   }
/*    */ 
/*    */   public void setColorSpace(int a_iColorSpace)
/*    */   {
/* 72 */     this.m_iColorSpace = a_iColorSpace;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.ImageFileInfo
 * JD-Core Version:    0.6.0
 */