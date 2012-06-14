/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ import com.bright.assetbank.application.util.VideoUtil;
/*    */ 
/*    */ public class VideoInfoImpl
/*    */   implements VideoInfo
/*    */ {
/* 18 */   private int m_iHeight = 0;
/* 19 */   private int m_iWidth = 0;
/* 20 */   private long m_lDuration = 0L;
/* 21 */   private float m_fPAR = 1.0F;
/*    */ 
/*    */   public int getHeight()
/*    */   {
/* 25 */     return this.m_iHeight;
/*    */   }
/*    */ 
/*    */   public void setHeight(int a_iHeight) {
/* 29 */     this.m_iHeight = a_iHeight;
/*    */   }
/*    */ 
/*    */   public int getWidth() {
/* 33 */     return this.m_iWidth;
/*    */   }
/*    */ 
/*    */   public void setWidth(int a_iWidth) {
/* 37 */     this.m_iWidth = a_iWidth;
/*    */   }
/*    */ 
/*    */   public long getDuration() {
/* 41 */     return this.m_lDuration;
/*    */   }
/*    */ 
/*    */   public void setDuration(long a_lDuration) {
/* 45 */     this.m_lDuration = a_lDuration;
/*    */   }
/*    */ 
/*    */   public void setPAR(float a_fPAR)
/*    */   {
/* 50 */     this.m_fPAR = a_fPAR;
/*    */   }
/*    */ 
/*    */   public float getPAR()
/*    */   {
/* 55 */     return this.m_fPAR;
/*    */   }
/*    */ 
/*    */   public int getDisplayHeight()
/*    */   {
/* 60 */     return VideoUtil.getDisplayHeight(this.m_iHeight, this.m_fPAR);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.VideoInfoImpl
 * JD-Core Version:    0.6.0
 */