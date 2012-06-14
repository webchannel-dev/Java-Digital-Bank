/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="KnotTypesType")
/*    */ @XmlEnum
/*    */ public enum KnotTypesType
/*    */ {
/* 36 */   UNIFORM("uniform"), 
/*    */ 
/* 38 */   QUASI_UNIFORM("quasiUniform"), 
/*    */ 
/* 40 */   PIECEWISE_BEZIER("piecewiseBezier");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 45 */   private KnotTypesType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 49 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static KnotTypesType fromValue(String v) {
/* 53 */     for (KnotTypesType c : values()) {
/* 54 */       if (c.value.equals(v)) {
/* 55 */         return c;
/*    */       }
/*    */     }
/* 58 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.KnotTypesType
 * JD-Core Version:    0.6.0
 */