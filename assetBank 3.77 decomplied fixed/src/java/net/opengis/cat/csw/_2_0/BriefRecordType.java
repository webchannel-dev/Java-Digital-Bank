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
/*     */ @XmlType(name="BriefRecordType", propOrder={"identifier", "title", "type", "boundingBox"})
/*     */ public class BriefRecordType extends AbstractRecordType
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
/*     */   @XmlElementRef(name="BoundingBox", namespace="http://www.opengis.net/ows", type=JAXBElement.class)
/*     */   protected List<JAXBElement<? extends BoundingBoxType>> boundingBox;
/*     */ 
/*     */   public List<JAXBElement<SimpleLiteral>> getIdentifier()
/*     */   {
/*  96 */     if (this.identifier == null) {
/*  97 */       this.identifier = new ArrayList();
/*     */     }
/*  99 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<SimpleLiteral>> getTitle()
/*     */   {
/* 126 */     if (this.title == null) {
/* 127 */       this.title = new ArrayList();
/*     */     }
/* 129 */     return this.title;
/*     */   }
/*     */ 
/*     */   public SimpleLiteral getType()
/*     */   {
/* 141 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(SimpleLiteral value)
/*     */   {
/* 153 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<? extends BoundingBoxType>> getBoundingBox()
/*     */   {
/* 180 */     if (this.boundingBox == null) {
/* 181 */       this.boundingBox = new ArrayList();
/*     */     }
/* 183 */     return this.boundingBox;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.BriefRecordType
 * JD-Core Version:    0.6.0
 */