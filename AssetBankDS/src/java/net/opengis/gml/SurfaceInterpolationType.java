/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="SurfaceInterpolationType")
/*    */ @XmlEnum
/*    */ public enum SurfaceInterpolationType
/*    */ {
/* 43 */   NONE("none"), 
/*    */ 
/* 45 */   PLANAR("planar"), 
/*    */ 
/* 47 */   SPHERICAL("spherical"), 
/*    */ 
/* 49 */   ELLIPTICAL("elliptical"), 
/*    */ 
/* 51 */   CONIC("conic"), 
/*    */ 
/* 53 */   TIN("tin"), 
/*    */ 
/* 55 */   PARAMETRIC_CURVE("parametricCurve"), 
/*    */ 
/* 57 */   POLYNOMIAL_SPLINE("polynomialSpline"), 
/*    */ 
/* 59 */   RATIONAL_SPLINE("rationalSpline"), 
/*    */ 
/* 61 */   TRIANGULATED_SPLINE("triangulatedSpline");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 66 */   private SurfaceInterpolationType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 70 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static SurfaceInterpolationType fromValue(String v) {
/* 74 */     for (SurfaceInterpolationType c : values()) {
/* 75 */       if (c.value.equals(v)) {
/* 76 */         return c;
/*    */       }
/*    */     }
/* 79 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.SurfaceInterpolationType
 * JD-Core Version:    0.6.0
 */