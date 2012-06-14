/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="SortOrderType")
/*    */ @XmlEnum
/*    */ public enum SortOrderType
/*    */ {
/* 34 */   DESC, 
/* 35 */   ASC;
/*    */ 
/*    */   public String value() {
/* 38 */     return name();
/*    */   }
/*    */ 
/*    */   public static SortOrderType fromValue(String v) {
/* 42 */     return valueOf(v);
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.SortOrderType
 * JD-Core Version:    0.6.0
 */