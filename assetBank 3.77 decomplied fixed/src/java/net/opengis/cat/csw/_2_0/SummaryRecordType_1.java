/*     */ package net.opengis.cat.csw._2_0;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import net.opengis.ows.BoundingBoxType;
/*     */ import org.purl.dc.elements._1.SimpleLiteral;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="SummaryRecordType", propOrder={"identifier", "title", "type", "subject", "format", "relation", "modified", "_abstract", "spatial", "boundingBox"})
/*     */ public class SummaryRecordType_1 extends AbstractRecordType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="identifier", namespace="http://purl.org/dc/elements/1.1/", type=JAXBElement.class)
/*     */   protected List<JAXBElement<SimpleLiteral>> identifier;
/*     */ 
/*     */   @XmlElementRef(name="title", namespace="http://purl.org/dc/elements/1.1/", type=JAXBElement.class)
/*     */   protected List<JAXBElement<SimpleLiteral>> title;
/*     */ 
/*     */   @XmlElement(namespace="http://purl.org/dc/elements/1.1/")
/*     */   protected SimpleLiteral type;
/*     */ 
/*     */   @XmlElement(namespace="http://purl.org/dc/elements/1.1/")
/*     */   protected List<SimpleLiteral> subject;
/*     */ 
/*     */   @XmlElementRef(name="format", namespace="http://purl.org/dc/elements/1.1/", type=JAXBElement.class)
/*     */   protected List<JAXBElement<SimpleLiteral>> format;
/*     */ 
/*     */   @XmlElementRef(name="relation", namespace="http://purl.org/dc/elements/1.1/", type=JAXBElement.class)
/*     */   protected List<JAXBElement<SimpleLiteral>> relation;
/*     */ 
/*     */   @XmlElement(namespace="http://purl.org/dc/terms/")
/*     */   protected List<SimpleLiteral> modified;
/*     */ 
/*     */   @XmlElement(name="abstract", namespace="http://purl.org/dc/terms/")
/*     */   protected List<SimpleLiteral> _abstract;
/*     */ 
/*     */   @XmlElement(namespace="http://purl.org/dc/terms/")
/*     */   protected List<SimpleLiteral> spatial;
/*     */ 
/*     */   @XmlElementRef(name="BoundingBox", namespace="http://www.opengis.net/ows", type=JAXBElement.class)
/*     */   protected List<JAXBElement<? extends BoundingBoxType>> boundingBox;
/*     */ 
/*     */   public List<JAXBElement<SimpleLiteral>> getIdentifier()
/*     */   {
/* 120 */     if (this.identifier == null) {
/* 121 */       this.identifier = new ArrayList();
/*     */     }
/* 123 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<SimpleLiteral>> getTitle()
/*     */   {
/* 150 */     if (this.title == null) {
/* 151 */       this.title = new ArrayList();
/*     */     }
/* 153 */     return this.title;
/*     */   }
/*     */ 
/*     */   public SimpleLiteral getType()
/*     */   {
/* 165 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(SimpleLiteral value)
/*     */   {
/* 177 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public List<SimpleLiteral> getSubject()
/*     */   {
/* 203 */     if (this.subject == null) {
/* 204 */       this.subject = new ArrayList();
/*     */     }
/* 206 */     return this.subject;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<SimpleLiteral>> getFormat()
/*     */   {
/* 234 */     if (this.format == null) {
/* 235 */       this.format = new ArrayList();
/*     */     }
/* 237 */     return this.format;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<SimpleLiteral>> getRelation()
/*     */   {
/* 276 */     if (this.relation == null) {
/* 277 */       this.relation = new ArrayList();
/*     */     }
/* 279 */     return this.relation;
/*     */   }
/*     */ 
/*     */   public List<SimpleLiteral> getModified()
/*     */   {
/* 305 */     if (this.modified == null) {
/* 306 */       this.modified = new ArrayList();
/*     */     }
/* 308 */     return this.modified;
/*     */   }
/*     */ 
/*     */   public List<SimpleLiteral> getAbstract()
/*     */   {
/* 334 */     if (this._abstract == null) {
/* 335 */       this._abstract = new ArrayList();
/*     */     }
/* 337 */     return this._abstract;
/*     */   }
/*     */ 
/*     */   public List<SimpleLiteral> getSpatial()
/*     */   {
/* 363 */     if (this.spatial == null) {
/* 364 */       this.spatial = new ArrayList();
/*     */     }
/* 366 */     return this.spatial;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<? extends BoundingBoxType>> getBoundingBox()
/*     */   {
/* 393 */     if (this.boundingBox == null) {
/* 394 */       this.boundingBox = new ArrayList();
/*     */     }
/* 396 */     return this.boundingBox;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.SummaryRecordType
 * JD-Core Version:    0.6.0
 */