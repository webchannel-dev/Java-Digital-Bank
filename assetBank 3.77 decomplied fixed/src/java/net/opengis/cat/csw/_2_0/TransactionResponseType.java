/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="TransactionResponseType", propOrder={"transactionSummary", "insertResult"})
/*     */ public class TransactionResponseType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="TransactionSummary", required=true)
/*     */   protected TransactionSummaryType transactionSummary;
/*     */ 
/*     */   @XmlElement(name="InsertResult")
/*     */   protected List<InsertResultType> insertResult;
/*     */ 
/*     */   @XmlAttribute(name="version")
/*     */   protected String version;
/*     */ 
/*     */   public TransactionSummaryType getTransactionSummary()
/*     */   {
/*  71 */     return this.transactionSummary;
/*     */   }
/*     */ 
/*     */   public void setTransactionSummary(TransactionSummaryType value)
/*     */   {
/*  83 */     this.transactionSummary = value;
/*     */   }
/*     */ 
/*     */   public List<InsertResultType> getInsertResult()
/*     */   {
/* 109 */     if (this.insertResult == null) {
/* 110 */       this.insertResult = new ArrayList();
/*     */     }
/* 112 */     return this.insertResult;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 124 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String value)
/*     */   {
/* 136 */     this.version = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.TransactionResponseType
 * JD-Core Version:    0.6.0
 */