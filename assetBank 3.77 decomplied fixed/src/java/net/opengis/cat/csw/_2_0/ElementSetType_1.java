/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="ElementSetType")
/*    */ @XmlEnum
/*    */ public enum ElementSetType_1
/*    */ {
/* 36 */   BRIEF("brief"), 
/*    */ 
/* 38 */   SUMMARY("summary"), 
/*    */ 
/* 40 */   FULL("full");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 45 */   private ElementSetType_1(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 49 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static ElementSetType fromValue(String v) {
/* 53 */     for (ElementSetType c : values()) {
/* 54 */       if (c.value.equals(v)) {
/* 55 */         return c;
/*    */       }
/*    */     }
/* 58 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.ElementSetType
 * JD-Core Version:    0.6.0
 */