/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="FormulaType", propOrder={"a", "b", "c", "d"})
/*     */ public class FormulaType
/*     */ {
/*     */   protected Double a;
/*     */   protected double b;
/*     */   protected double c;
/*     */   protected Double d;
/*     */ 
/*     */   public Double getA()
/*     */   {
/*  63 */     return this.a;
/*     */   }
/*     */ 
/*     */   public void setA(Double value)
/*     */   {
/*  75 */     this.a = value;
/*     */   }
/*     */ 
/*     */   public double getB()
/*     */   {
/*  83 */     return this.b;
/*     */   }
/*     */ 
/*     */   public void setB(double value)
/*     */   {
/*  91 */     this.b = value;
/*     */   }
/*     */ 
/*     */   public double getC()
/*     */   {
/*  99 */     return this.c;
/*     */   }
/*     */ 
/*     */   public void setC(double value)
/*     */   {
/* 107 */     this.c = value;
/*     */   }
/*     */ 
/*     */   public Double getD()
/*     */   {
/* 119 */     return this.d;
/*     */   }
/*     */ 
/*     */   public void setD(Double value)
/*     */   {
/* 131 */     this.d = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.FormulaType
 * JD-Core Version:    0.6.0
 */