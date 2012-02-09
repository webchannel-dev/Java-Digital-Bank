/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="CurveInterpolationType")
/*    */ @XmlEnum
/*    */ public enum CurveInterpolationType
/*    */ {
/* 44 */   LINEAR("linear"), 
/*    */ 
/* 46 */   GEODESIC("geodesic"), 
/*    */ 
/* 48 */   CIRCULAR_ARC_3_POINTS("circularArc3Points"), 
/*    */ 
/* 50 */   CIRCULAR_ARC_2_POINT_WITH_BULGE("circularArc2PointWithBulge"), 
/*    */ 
/* 52 */   CIRCULAR_ARC_CENTER_POINT_WITH_RADIUS("circularArcCenterPointWithRadius"), 
/*    */ 
/* 54 */   ELLIPTICAL("elliptical"), 
/*    */ 
/* 56 */   CLOTHOID("clothoid"), 
/*    */ 
/* 58 */   CONIC("conic"), 
/*    */ 
/* 60 */   POLYNOMIAL_SPLINE("polynomialSpline"), 
/*    */ 
/* 62 */   CUBIC_SPLINE("cubicSpline"), 
/*    */ 
/* 64 */   RATIONAL_SPLINE("rationalSpline");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 69 */   private CurveInterpolationType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 73 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static CurveInterpolationType fromValue(String v) {
/* 77 */     for (CurveInterpolationType c : values()) {
/* 78 */       if (c.value.equals(v)) {
/* 79 */         return c;
/*    */       }
/*    */     }
/* 82 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CurveInterpolationType
 * JD-Core Version:    0.6.0
 */