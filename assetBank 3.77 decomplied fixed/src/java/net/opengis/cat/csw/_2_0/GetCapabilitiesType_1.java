/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="GetCapabilitiesType")
/*    */ public class GetCapabilitiesType_1 extends net.opengis.ows.GetCapabilitiesType
/*    */ {
/*    */ 
/*    */   @XmlAttribute(name="service")
/*    */   protected String service;
/*    */ 
/*    */   public String getService()
/*    */   {
/* 57 */     if (this.service == null) {
/* 58 */       return "http://www.opengis.net/cat/csw";
/*    */     }
/* 60 */     return this.service;
/*    */   }
/*    */ 
/*    */   public void setService(String value)
/*    */   {
/* 73 */     this.service = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.GetCapabilitiesType
 * JD-Core Version:    0.6.0
 */