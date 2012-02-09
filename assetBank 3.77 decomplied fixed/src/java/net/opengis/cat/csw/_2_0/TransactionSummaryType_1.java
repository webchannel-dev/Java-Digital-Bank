/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="TransactionSummaryType", propOrder={"totalInserted", "totalUpdated", "totalDeleted"})
/*     */ public class TransactionSummaryType_1
/*     */ {
/*     */ 
/*     */   @XmlSchemaType(name="nonNegativeInteger")
/*     */   protected BigInteger totalInserted;
/*     */ 
/*     */   @XmlSchemaType(name="nonNegativeInteger")
/*     */   protected BigInteger totalUpdated;
/*     */ 
/*     */   @XmlSchemaType(name="nonNegativeInteger")
/*     */   protected BigInteger totalDeleted;
/*     */ 
/*     */   @XmlAttribute(name="requestId")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String requestId;
/*     */ 
/*     */   public BigInteger getTotalInserted()
/*     */   {
/*  74 */     return this.totalInserted;
/*     */   }
/*     */ 
/*     */   public void setTotalInserted(BigInteger value)
/*     */   {
/*  86 */     this.totalInserted = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getTotalUpdated()
/*     */   {
/*  98 */     return this.totalUpdated;
/*     */   }
/*     */ 
/*     */   public void setTotalUpdated(BigInteger value)
/*     */   {
/* 110 */     this.totalUpdated = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getTotalDeleted()
/*     */   {
/* 122 */     return this.totalDeleted;
/*     */   }
/*     */ 
/*     */   public void setTotalDeleted(BigInteger value)
/*     */   {
/* 134 */     this.totalDeleted = value;
/*     */   }
/*     */ 
/*     */   public String getRequestId()
/*     */   {
/* 146 */     return this.requestId;
/*     */   }
/*     */ 
/*     */   public void setRequestId(String value)
/*     */   {
/* 158 */     this.requestId = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.TransactionSummaryType
 * JD-Core Version:    0.6.0
 */