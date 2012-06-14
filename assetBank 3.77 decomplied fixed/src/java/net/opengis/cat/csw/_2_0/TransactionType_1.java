/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElements;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="TransactionType", propOrder={"insertOrUpdateOrDelete"})
/*     */ public class TransactionType_1 extends RequestBaseType
/*     */ {
/*     */ 
/*     */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="Update", type=UpdateType.class), @javax.xml.bind.annotation.XmlElement(name="Insert", type=InsertType.class), @javax.xml.bind.annotation.XmlElement(name="Delete", type=DeleteType.class)})
/*     */   protected List<Object> insertOrUpdateOrDelete;
/*     */ 
/*     */   @XmlAttribute(name="verboseResponse")
/*     */   protected Boolean verboseResponse;
/*     */ 
/*     */   @XmlAttribute(name="requestId")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String requestId;
/*     */ 
/*     */   public List<Object> getInsertOrUpdateOrDelete()
/*     */   {
/*  98 */     if (this.insertOrUpdateOrDelete == null) {
/*  99 */       this.insertOrUpdateOrDelete = new ArrayList();
/*     */     }
/* 101 */     return this.insertOrUpdateOrDelete;
/*     */   }
/*     */ 
/*     */   public boolean isVerboseResponse()
/*     */   {
/* 113 */     if (this.verboseResponse == null) {
/* 114 */       return false;
/*     */     }
/* 116 */     return this.verboseResponse.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setVerboseResponse(Boolean value)
/*     */   {
/* 129 */     this.verboseResponse = value;
/*     */   }
/*     */ 
/*     */   public String getRequestId()
/*     */   {
/* 141 */     return this.requestId;
/*     */   }
/*     */ 
/*     */   public void setRequestId(String value)
/*     */   {
/* 153 */     this.requestId = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.TransactionType
 * JD-Core Version:    0.6.0
 */