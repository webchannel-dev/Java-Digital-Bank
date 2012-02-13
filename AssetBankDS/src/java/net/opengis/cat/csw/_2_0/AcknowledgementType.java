/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="AcknowledgementType", propOrder={"echoedRequest", "requestId"})
/*     */ public class AcknowledgementType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="EchoedRequest", required=true)
/*     */   protected EchoedRequestType echoedRequest;
/*     */ 
/*     */   @XmlElement(name="RequestId")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String requestId;
/*     */ 
/*     */   @XmlAttribute(name="timeStamp", required=true)
/*     */   @XmlSchemaType(name="dateTime")
/*     */   protected XMLGregorianCalendar timeStamp;
/*     */ 
/*     */   public EchoedRequestType getEchoedRequest()
/*     */   {
/*  73 */     return this.echoedRequest;
/*     */   }
/*     */ 
/*     */   public void setEchoedRequest(EchoedRequestType value)
/*     */   {
/*  85 */     this.echoedRequest = value;
/*     */   }
/*     */ 
/*     */   public String getRequestId()
/*     */   {
/*  97 */     return this.requestId;
/*     */   }
/*     */ 
/*     */   public void setRequestId(String value)
/*     */   {
/* 109 */     this.requestId = value;
/*     */   }
/*     */ 
/*     */   public XMLGregorianCalendar getTimeStamp()
/*     */   {
/* 121 */     return this.timeStamp;
/*     */   }
/*     */ 
/*     */   public void setTimeStamp(XMLGregorianCalendar value)
/*     */   {
/* 133 */     this.timeStamp = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.AcknowledgementType
 * JD-Core Version:    0.6.0
 */