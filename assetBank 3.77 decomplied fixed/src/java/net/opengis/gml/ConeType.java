/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ConeType")
/*     */ public class ConeType extends AbstractGriddedSurfaceType
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
/*  67 */     if (this.horizontalCurveType == null) {
/*  68 */       return CurveInterpolationType.CIRCULAR_ARC_3_POINTS;
/*     */     }
/*  70 */     return this.horizontalCurveType;
/*     */   }
/*     */ 
/*     */   public void setHorizontalCurveType(CurveInterpolationType value)
/*     */   {
/*  83 */     this.horizontalCurveType = value;
/*     */   }
/*     */ 
/*     */   public CurveInterpolationType getVerticalCurveType()
/*     */   {
/*  95 */     if (this.verticalCurveType == null) {
/*  96 */       return CurveInterpolationType.LINEAR;
/*     */     }
/*  98 */     return this.verticalCurveType;
/*     */   }
/*     */ 
/*     */   public void setVerticalCurveType(CurveInterpolationType value)
/*     */   {
/* 111 */     this.verticalCurveType = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ConeType
 * JD-Core Version:    0.6.0
 */