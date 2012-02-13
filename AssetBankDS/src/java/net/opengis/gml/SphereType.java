/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="SphereType")
/*     */ public class SphereType extends AbstractGriddedSurfaceType
/*     */ {
/*     */ 
/*     */   @XmlAttribute(name="horizontalCurveType")
/*     */   protected CurveInterpolationType horizontalCurveType;
/*     */ 
/*     */   @XmlAttribute(name="verticalCurveType")
/*     */   protected CurveInterpolationType verticalCurveType;
/*     */ 
/*     */   public CurveInterpolationType getHorizontalCurveType()
/*     */   {
/*  89 */     if (this.horizontalCurveType == null) {
/*  90 */       return CurveInterpolationType.CIRCULAR_ARC_3_POINTS;
/*     */     }
/*  92 */     return this.horizontalCurveType;
/*     */   }
/*     */ 
/*     */   public void setHorizontalCurveType(CurveInterpolationType value)
/*     */   {
/* 105 */     this.horizontalCurveType = value;
/*     */   }
/*     */ 
/*     */   public CurveInterpolationType getVerticalCurveType()
/*     */   {
/* 117 */     if (this.verticalCurveType == null) {
/* 118 */       return CurveInterpolationType.CIRCULAR_ARC_3_POINTS;
/*     */     }
/* 120 */     return this.verticalCurveType;
/*     */   }
/*     */ 
/*     */   public void setVerticalCurveType(CurveInterpolationType value)
/*     */   {
/* 133 */     this.verticalCurveType = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.SphereType
 * JD-Core Version:    0.6.0
 */