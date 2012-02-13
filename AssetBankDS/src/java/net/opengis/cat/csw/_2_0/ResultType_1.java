/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="ResultType")
/*    */ @XmlEnum
/*    */ public enum ResultType_1
/*    */ {
/* 41 */   RESULTS("results"), 
/*    */ 
/* 48 */   HITS("hits"), 
/*    */ 
/* 56 */   VALIDATE("validate");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 61 */   private ResultType_1(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 65 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static ResultType fromValue(String v) {
/* 69 */     for (ResultType c : values()) {
/* 70 */       if (c.value.equals(v)) {
/* 71 */         return c;
/*    */       }
/*    */     }
/* 74 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.ResultType
 * JD-Core Version:    0.6.0
 */