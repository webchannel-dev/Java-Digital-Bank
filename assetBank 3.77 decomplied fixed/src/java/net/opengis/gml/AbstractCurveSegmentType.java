/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="AbstractCurveSegmentType")
/*     */ @XmlSeeAlso({ClothoidType.class, BSplineType.class, CubicSplineType.class, GeodesicStringType.class, LineStringSegmentType.class, ArcByCenterPointType.class, ArcStringType.class, OffsetCurveType.class, ArcStringByBulgeType.class})
/*     */ public abstract class AbstractCurveSegmentType
/*     */ {
/*     */ 
/*     */   @XmlAttribute(name="numDerivativesAtStart")
/*     */   protected BigInteger numDerivativesAtStart;
/*     */ 
/*     */   @XmlAttribute(name="numDerivativesAtEnd")
/*     */   protected BigInteger numDerivativesAtEnd;
/*     */ 
/*     */   @XmlAttribute(name="numDerivativeInterior")
/*     */   protected BigInteger numDerivativeInterior;
/*     */ 
/*     */   public BigInteger getNumDerivativesAtStart()
/*     */   {
/*  73 */     if (this.numDerivativesAtStart == null) {
/*  74 */       return new BigInteger("0");
/*     */     }
/*  76 */     return this.numDerivativesAtStart;
/*     */   }
/*     */ 
/*     */   public void setNumDerivativesAtStart(BigInteger value)
/*     */   {
/*  89 */     this.numDerivativesAtStart = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getNumDerivativesAtEnd()
/*     */   {
/* 101 */     if (this.numDerivativesAtEnd == null) {
/* 102 */       return new BigInteger("0");
/*     */     }
/* 104 */     return this.numDerivativesAtEnd;
/*     */   }
/*     */ 
/*     */   public void setNumDerivativesAtEnd(BigInteger value)
/*     */   {
/* 117 */     this.numDerivativesAtEnd = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getNumDerivativeInterior()
/*     */   {
/* 129 */     if (this.numDerivativeInterior == null) {
/* 130 */       return new BigInteger("0");
/*     */     }
/* 132 */     return this.numDerivativeInterior;
/*     */   }
/*     */ 
/*     */   public void setNumDerivativeInterior(BigInteger value)
/*     */   {
/* 145 */     this.numDerivativeInterior = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractCurveSegmentType
 * JD-Core Version:    0.6.0
 */