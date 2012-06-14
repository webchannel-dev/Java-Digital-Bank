/*     */ package net.opengis.ows;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="IdentificationType", propOrder={"identifier", "boundingBox", "outputFormat", "availableCRS", "metadata"})
/*     */ public class IdentificationType extends DescriptionType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Identifier")
/*     */   protected CodeType identifier;
/*     */ 
/*     */   @XmlElementRef(name="BoundingBox", namespace="http://www.opengis.net/ows", type=JAXBElement.class)
/*     */   protected List<JAXBElement<? extends BoundingBoxType>> boundingBox;
/*     */ 
/*     */   @XmlElement(name="OutputFormat")
/*     */   protected List<String> outputFormat;
/*     */ 
/*     */   @XmlElementRef(name="AvailableCRS", namespace="http://www.opengis.net/ows", type=JAXBElement.class)
/*     */   protected List<JAXBElement<String>> availableCRS;
/*     */ 
/*     */   @XmlElement(name="Metadata")
/*     */   protected List<MetadataType> metadata;
/*     */ 
/*     */   public CodeType getIdentifier()
/*     */   {
/*  78 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   public void setIdentifier(CodeType value)
/*     */   {
/*  90 */     this.identifier = value;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<? extends BoundingBoxType>> getBoundingBox()
/*     */   {
/* 117 */     if (this.boundingBox == null) {
/* 118 */       this.boundingBox = new ArrayList();
/*     */     }
/* 120 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */   public List<String> getOutputFormat()
/*     */   {
/* 146 */     if (this.outputFormat == null) {
/* 147 */       this.outputFormat = new ArrayList();
/*     */     }
/* 149 */     return this.outputFormat;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<String>> getAvailableCRS()
/*     */   {
/* 176 */     if (this.availableCRS == null) {
/* 177 */       this.availableCRS = new ArrayList();
/*     */     }
/* 179 */     return this.availableCRS;
/*     */   }
/*     */ 
/*     */   public List<MetadataType> getMetadata()
/*     */   {
/* 205 */     if (this.metadata == null) {
/* 206 */       this.metadata = new ArrayList();
/*     */     }
/* 208 */     return this.metadata;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.IdentificationType
 * JD-Core Version:    0.6.0
 */