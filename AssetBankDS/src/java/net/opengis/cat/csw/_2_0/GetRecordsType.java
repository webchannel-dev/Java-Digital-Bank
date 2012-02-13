/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="GetRecordsType", propOrder={"distributedSearch", "responseHandler", "abstractQuery", "any"})
/*     */ public class GetRecordsType extends RequestBaseType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="DistributedSearch")
/*     */   protected DistributedSearchType distributedSearch;
/*     */ 
/*     */   @XmlElement(name="ResponseHandler")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected List<String> responseHandler;
/*     */ 
/*     */   @XmlElementRef(name="AbstractQuery", namespace="http://www.opengis.net/cat/csw/2.0.2", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends AbstractQueryType> abstractQuery;
/*     */ 
/*     */   @XmlAnyElement(lax=true)
/*     */   protected Object any;
/*     */ 
/*     */   @XmlAttribute(name="requestId")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String requestId;
/*     */ 
/*     */   @XmlAttribute(name="resultType")
/*     */   protected ResultType resultType;
/*     */ 
/*     */   @XmlAttribute(name="outputFormat")
/*     */   protected String outputFormat;
/*     */ 
/*     */   @XmlAttribute(name="outputSchema")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String outputSchema;
/*     */ 
/*     */   @XmlAttribute(name="startPosition")
/*     */   @XmlSchemaType(name="positiveInteger")
/*     */   protected BigInteger startPosition;
/*     */ 
/*     */   @XmlAttribute(name="maxRecords")
/*     */   @XmlSchemaType(name="nonNegativeInteger")
/*     */   protected BigInteger maxRecords;
/*     */ 
/*     */   public DistributedSearchType getDistributedSearch()
/*     */   {
/* 106 */     return this.distributedSearch;
/*     */   }
/*     */ 
/*     */   public void setDistributedSearch(DistributedSearchType value)
/*     */   {
/* 118 */     this.distributedSearch = value;
/*     */   }
/*     */ 
/*     */   public List<String> getResponseHandler()
/*     */   {
/* 144 */     if (this.responseHandler == null) {
/* 145 */       this.responseHandler = new ArrayList();
/*     */     }
/* 147 */     return this.responseHandler;
/*     */   }
/*     */ 
/*     */   public JAXBElement<? extends AbstractQueryType> getAbstractQuery()
/*     */   {
/* 160 */     return this.abstractQuery;
/*     */   }
/*     */ 
/*     */   public void setAbstractQuery(JAXBElement<? extends AbstractQueryType> value)
/*     */   {
/* 173 */     this.abstractQuery = value;
/*     */   }
/*     */ 
/*     */   public Object getAny()
/*     */   {
/* 185 */     return this.any;
/*     */   }
/*     */ 
/*     */   public void setAny(Object value)
/*     */   {
/* 197 */     this.any = value;
/*     */   }
/*     */ 
/*     */   public String getRequestId()
/*     */   {
/* 209 */     return this.requestId;
/*     */   }
/*     */ 
/*     */   public void setRequestId(String value)
/*     */   {
/* 221 */     this.requestId = value;
/*     */   }
/*     */ 
/*     */   public ResultType getResultType()
/*     */   {
/* 233 */     if (this.resultType == null) {
/* 234 */       return ResultType.HITS;
/*     */     }
/* 236 */     return this.resultType;
/*     */   }
/*     */ 
/*     */   public void setResultType(ResultType value)
/*     */   {
/* 249 */     this.resultType = value;
/*     */   }
/*     */ 
/*     */   public String getOutputFormat()
/*     */   {
/* 261 */     if (this.outputFormat == null) {
/* 262 */       return "application/xml";
/*     */     }
/* 264 */     return this.outputFormat;
/*     */   }
/*     */ 
/*     */   public void setOutputFormat(String value)
/*     */   {
/* 277 */     this.outputFormat = value;
/*     */   }
/*     */ 
/*     */   public String getOutputSchema()
/*     */   {
/* 289 */     return this.outputSchema;
/*     */   }
/*     */ 
/*     */   public void setOutputSchema(String value)
/*     */   {
/* 301 */     this.outputSchema = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getStartPosition()
/*     */   {
/* 313 */     if (this.startPosition == null) {
/* 314 */       return new BigInteger("1");
/*     */     }
/* 316 */     return this.startPosition;
/*     */   }
/*     */ 
/*     */   public void setStartPosition(BigInteger value)
/*     */   {
/* 329 */     this.startPosition = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getMaxRecords()
/*     */   {
/* 341 */     if (this.maxRecords == null) {
/* 342 */       return new BigInteger("10");
/*     */     }
/* 344 */     return this.maxRecords;
/*     */   }
/*     */ 
/*     */   public void setMaxRecords(BigInteger value)
/*     */   {
/* 357 */     this.maxRecords = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.GetRecordsType
 * JD-Core Version:    0.6.0
 */