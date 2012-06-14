/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="RequestBaseType")
/*     */ @XmlSeeAlso({GetDomainType.class, DescribeRecordType.class, GetRecordByIdType.class, TransactionType.class, HarvestType.class, GetRecordsType.class})
/*     */ public abstract class RequestBaseType_1
/*     */ {
/*     */ 
/*     */   @XmlAttribute(name="service", required=true)
/*     */   protected String service;
/*     */ 
/*     */   @XmlAttribute(name="version", required=true)
/*     */   protected String version;
/*     */ 
/*     */   public String getService()
/*     */   {
/*  67 */     if (this.service == null) {
/*  68 */       return "CSW";
/*     */     }
/*  70 */     return this.service;
/*     */   }
/*     */ 
/*     */   public void setService(String value)
/*     */   {
/*  83 */     this.service = value;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/*  95 */     if (this.version == null) {
/*  96 */       return "2.0.2";
/*     */     }
/*  98 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String value)
/*     */   {
/* 111 */     this.version = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.RequestBaseType
 * JD-Core Version:    0.6.0
 */