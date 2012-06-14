/*    */ package com.bright.assetbank.custom.bgs.bean;
/*    */ 
/*    */ public class PointCriteria
/*    */ {
/*    */   private double m_x;
/*    */   private double m_y;
/*    */   private int m_epsgCode;
/*    */   private Double m_buffer;
/*    */ 
/*    */   public PointCriteria()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PointCriteria(double a_x, double a_y, int a_epsgCode, Double a_buffer)
/*    */   {
/* 36 */     this.m_x = a_x;
/* 37 */     this.m_y = a_y;
/* 38 */     this.m_epsgCode = a_epsgCode;
/* 39 */     this.m_buffer = a_buffer;
/*    */   }
/*    */ 
/*    */   public double getX()
/*    */   {
/* 44 */     return this.m_x;
/*    */   }
/*    */ 
/*    */   public void setX(double a_x)
/*    */   {
/* 49 */     this.m_x = a_x;
/*    */   }
/*    */ 
/*    */   public double getY()
/*    */   {
/* 54 */     return this.m_y;
/*    */   }
/*    */ 
/*    */   public void setY(double a_y)
/*    */   {
/* 59 */     this.m_y = a_y;
/*    */   }
/*    */ 
/*    */   public int getEpsgCode()
/*    */   {
/* 64 */     return this.m_epsgCode;
/*    */   }
/*    */ 
/*    */   public void setEpsgCode(int a_epsgCode)
/*    */   {
/* 69 */     this.m_epsgCode = a_epsgCode;
/*    */   }
/*    */ 
/*    */   public Double getBuffer()
/*    */   {
/* 74 */     return this.m_buffer;
/*    */   }
/*    */ 
/*    */   public void setBuffer(Double a_buffer)
/*    */   {
/* 79 */     this.m_buffer = a_buffer;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.bgs.bean.PointCriteria
 * JD-Core Version:    0.6.0
 */