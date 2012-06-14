/*     */ package com.bright.assetbank.custom.bgs.bean;
/*     */ 
/*     */ public class BoundingBoxCriteria
/*     */ {
/*     */   private double m_minX;
/*     */   private double m_minY;
/*     */   private double m_maxX;
/*     */   private double m_maxY;
/*     */   private int m_epsgCode;
/*     */   private Double m_buffer;
/*     */ 
/*     */   public BoundingBoxCriteria()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BoundingBoxCriteria(double a_minX, double a_minY, double a_maxX, double a_maxY, int a_epsgCode, Double a_buffer)
/*     */   {
/*  38 */     this.m_minX = a_minX;
/*  39 */     this.m_minY = a_minY;
/*  40 */     this.m_maxX = a_maxX;
/*  41 */     this.m_maxY = a_maxY;
/*  42 */     this.m_epsgCode = a_epsgCode;
/*  43 */     this.m_buffer = a_buffer;
/*     */   }
/*     */ 
/*     */   public double getMinX()
/*     */   {
/*  48 */     return this.m_minX;
/*     */   }
/*     */ 
/*     */   public void setMinX(double a_minX)
/*     */   {
/*  53 */     this.m_minX = a_minX;
/*     */   }
/*     */ 
/*     */   public double getMinY()
/*     */   {
/*  58 */     return this.m_minY;
/*     */   }
/*     */ 
/*     */   public void setMinY(double a_minY)
/*     */   {
/*  63 */     this.m_minY = a_minY;
/*     */   }
/*     */ 
/*     */   public double getMaxX()
/*     */   {
/*  68 */     return this.m_maxX;
/*     */   }
/*     */ 
/*     */   public void setMaxX(double a_maxX)
/*     */   {
/*  73 */     this.m_maxX = a_maxX;
/*     */   }
/*     */ 
/*     */   public double getMaxY()
/*     */   {
/*  78 */     return this.m_maxY;
/*     */   }
/*     */ 
/*     */   public void setMaxY(double a_maxY)
/*     */   {
/*  83 */     this.m_maxY = a_maxY;
/*     */   }
/*     */ 
/*     */   public int getEpsgCode()
/*     */   {
/*  88 */     return this.m_epsgCode;
/*     */   }
/*     */ 
/*     */   public void setEpsgCode(int a_epsgCode)
/*     */   {
/*  93 */     this.m_epsgCode = a_epsgCode;
/*     */   }
/*     */ 
/*     */   public Double getBuffer()
/*     */   {
/*  98 */     return this.m_buffer;
/*     */   }
/*     */ 
/*     */   public void setBuffer(Double a_buffer)
/*     */   {
/* 103 */     this.m_buffer = a_buffer;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.bgs.bean.BoundingBoxCriteria
 * JD-Core Version:    0.6.0
 */