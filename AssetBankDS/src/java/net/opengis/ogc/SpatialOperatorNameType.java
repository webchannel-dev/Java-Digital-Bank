/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="SpatialOperatorNameType")
/*    */ @XmlEnum
/*    */ public enum SpatialOperatorNameType
/*    */ {
/* 44 */   BBOX("BBOX"), 
/* 45 */   EQUALS("Equals"), 
/*    */ 
/* 47 */   DISJOINT("Disjoint"), 
/*    */ 
/* 49 */   INTERSECTS("Intersects"), 
/*    */ 
/* 51 */   TOUCHES("Touches"), 
/*    */ 
/* 53 */   CROSSES("Crosses"), 
/*    */ 
/* 55 */   WITHIN("Within"), 
/*    */ 
/* 57 */   CONTAINS("Contains"), 
/*    */ 
/* 59 */   OVERLAPS("Overlaps"), 
/*    */ 
/* 61 */   BEYOND("Beyond"), 
/*    */ 
/* 63 */   D_WITHIN("DWithin");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 68 */   private SpatialOperatorNameType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 72 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static SpatialOperatorNameType fromValue(String v) {
/* 76 */     for (SpatialOperatorNameType c : values()) {
/* 77 */       if (c.value.equals(v)) {
/* 78 */         return c;
/*    */       }
/*    */     }
/* 81 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.SpatialOperatorNameType
 * JD-Core Version:    0.6.0
 */