/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.datatype.XMLGregorianCalendar;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="RequestStatusType")
/*    */ public class RequestStatusType_1
/*    */ {
/*    */ 
/*    */   @XmlAttribute(name="timestamp")
/*    */   @XmlSchemaType(name="dateTime")
/*    */   protected XMLGregorianCalendar timestamp;
/*    */ 
/*    */   public XMLGregorianCalendar getTimestamp()
/*    */   {
/* 62 */     return this.timestamp;
/*    */   }
/*    */ 
/*    */   public void setTimestamp(XMLGregorianCalendar value)
/*    */   {
/* 74 */     this.timestamp = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.RequestStatusType
 * JD-Core Version:    0.6.0
 */