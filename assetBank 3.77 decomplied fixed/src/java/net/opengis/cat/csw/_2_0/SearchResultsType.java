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
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="SearchResultsType", propOrder={"abstractRecord", "any"})
/*     */ public class SearchResultsType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="AbstractRecord", namespace="http://www.opengis.net/cat/csw/2.0.2", type=JAXBElement.class)
/*     */   protected List<JAXBElement<? extends AbstractRecordType>> abstractRecord;
/*     */ 
/*     */   @XmlAnyElement(lax=true)
/*     */   protected List<Object> any;
/*     */ 
/*     */   @XmlAttribute(name="resultSetId")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String resultSetId;
/*     */ 
/*     */   @XmlAttribute(name="elementSet")
/*     */   protected ElementSetType elementSet;
/*     */ 
/*     */   @XmlAttribute(name="recordSchema")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String recordSchema;
/*     */ 
/*     */   @XmlAttribute(name="numberOfRecordsMatched", required=true)
/*     */   @XmlSchemaType(name="nonNegativeInteger")
/*     */   protected BigInteger numberOfRecordsMatched;
/*     */ 
/*     */   @XmlAttribute(name="numberOfRecordsReturned", required=true)
/*     */   @XmlSchemaType(name="nonNegativeInteger")
/*     */   protected BigInteger numberOfRecordsReturned;
/*     */ 
/*     */   @XmlAttribute(name="nextRecord")
/*     */   @XmlSchemaType(name="nonNegativeInteger")
/*     */   protected BigInteger nextRecord;
/*     */ 
/*     */   @XmlAttribute(name="expires")
/*     */   @XmlSchemaType(name="dateTime")
/*     */   protected XMLGregorianCalendar expires;
/*     */ 
/*     */   public List<JAXBElement<? extends AbstractRecordType>> getAbstractRecord()
/*     */   {
/* 129 */     if (this.abstractRecord == null) {
/* 130 */       this.abstractRecord = new ArrayList();
/*     */     }
/* 132 */     return this.abstractRecord;
/*     */   }
/*     */ 
/*     */   public List<Object> getAny()
/*     */   {
/* 158 */     if (this.any == null) {
/* 159 */       this.any = new ArrayList();
/*     */     }
/* 161 */     return this.any;
/*     */   }
/*     */ 
/*     */   public String getResultSetId()
/*     */   {
/* 173 */     return this.resultSetId;
/*     */   }
/*     */ 
/*     */   public void setResultSetId(String value)
/*     */   {
/* 185 */     this.resultSetId = value;
/*     */   }
/*     */ 
/*     */   public ElementSetType getElementSet()
/*     */   {
/* 197 */     return this.elementSet;
/*     */   }
/*     */ 
/*     */   public void setElementSet(ElementSetType value)
/*     */   {
/* 209 */     this.elementSet = value;
/*     */   }
/*     */ 
/*     */   public String getRecordSchema()
/*     */   {
/* 221 */     return this.recordSchema;
/*     */   }
/*     */ 
/*     */   public void setRecordSchema(String value)
/*     */   {
/* 233 */     this.recordSchema = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getNumberOfRecordsMatched()
/*     */   {
/* 245 */     return this.numberOfRecordsMatched;
/*     */   }
/*     */ 
/*     */   public void setNumberOfRecordsMatched(BigInteger value)
/*     */   {
/* 257 */     this.numberOfRecordsMatched = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getNumberOfRecordsReturned()
/*     */   {
/* 269 */     return this.numberOfRecordsReturned;
/*     */   }
/*     */ 
/*     */   public void setNumberOfRecordsReturned(BigInteger value)
/*     */   {
/* 281 */     this.numberOfRecordsReturned = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getNextRecord()
/*     */   {
/* 293 */     return this.nextRecord;
/*     */   }
/*     */ 
/*     */   public void setNextRecord(BigInteger value)
/*     */   {
/* 305 */     this.nextRecord = value;
/*     */   }
/*     */ 
/*     */   public XMLGregorianCalendar getExpires()
/*     */   {
/* 317 */     return this.expires;
/*     */   }
/*     */ 
/*     */   public void setExpires(XMLGregorianCalendar value)
/*     */   {
/* 329 */     this.expires = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.SearchResultsType
 * JD-Core Version:    0.6.0
 */