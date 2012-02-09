/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="CylinderType")
/*     */ public class CylinderType extends AbstractGriddedSurfaceType
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
/*  63 */     if (this.horizontalCurveType == null) {
/*  64 */       return CurveInterpolationType.CIRCULAR_ARC_3_POINTS;
/*     */     }
/*  66 */     return this.horizontalCurveType;
/*     */   }
/*     */ 
/*     */   public void setHorizontalCurveType(CurveInterpolationType value)
/*     */   {
/*  79 */     this.horizontalCurveType = value;
/*     */   }
/*     */ 
/*     */   public CurveInterpolationType getVerticalCurveType()
/*     */   {
/*  91 */     if (this.verticalCurveType == null) {
/*  92 */       return CurveInterpolationType.LINEAR;
/*     */     }
/*  94 */     return this.verticalCurveType;
/*     */   }
/*     */ 
/*     */   public void setVerticalCurveType(CurveInterpolationType value)
/*     */   {
/* 107 */     this.verticalCurveType = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CylinderType
 * JD-Core Version:    0.6.0
 */