/*    */ package com.bright.framework.image.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.UploadedFile;
/*    */ 
/*    */ public class ImageFile extends UploadedFile
/*    */ {
/* 29 */   private int m_iPixelHeight = 0;
/* 30 */   private int m_iPixelWidth = 0;
/*    */ 
/*    */   public ImageFile()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ImageFile(String a_sURL)
/*    */   {
/* 47 */     setPath(a_sURL);
/*    */   }
/*    */ 
/*    */   public int getPixelHeight()
/*    */   {
/* 53 */     return this.m_iPixelHeight;
/*    */   }
/*    */ 
/*    */   public void setPixelHeight(int a_iPixelHeight)
/*    */   {
/* 59 */     this.m_iPixelHeight = a_iPixelHeight;
/*    */   }
/*    */ 
/*    */   public int getPixelWidth()
/*    */   {
/* 65 */     return this.m_iPixelWidth;
/*    */   }
/*    */ 
/*    */   public void setPixelWidth(int a_iPixelWidth)
/*    */   {
/* 71 */     this.m_iPixelWidth = a_iPixelWidth;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.bean.ImageFile
 * JD-Core Version:    0.6.0
 */