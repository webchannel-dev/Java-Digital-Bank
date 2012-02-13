/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="ComparisonOperatorType")
/*    */ @XmlEnum
/*    */ public enum ComparisonOperatorType
/*    */ {
/* 42 */   LESS_THAN("LessThan"), 
/*    */ 
/* 44 */   GREATER_THAN("GreaterThan"), 
/*    */ 
/* 46 */   LESS_THAN_EQUAL_TO("LessThanEqualTo"), 
/*    */ 
/* 48 */   GREATER_THAN_EQUAL_TO("GreaterThanEqualTo"), 
/*    */ 
/* 50 */   EQUAL_TO("EqualTo"), 
/*    */ 
/* 52 */   NOT_EQUAL_TO("NotEqualTo"), 
/*    */ 
/* 54 */   LIKE("Like"), 
/*    */ 
/* 56 */   BETWEEN("Between"), 
/*    */ 
/* 58 */   NULL_CHECK("NullCheck");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 63 */   private ComparisonOperatorType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 67 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static ComparisonOperatorType fromValue(String v) {
/* 71 */     for (ComparisonOperatorType c : values()) {
/* 72 */       if (c.value.equals(v)) {
/* 73 */         return c;
/*    */       }
/*    */     }
/* 76 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.ComparisonOperatorType
 * JD-Core Version:    0.6.0
 */