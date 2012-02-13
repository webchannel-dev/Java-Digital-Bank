/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import net.opengis.gml.AbstractGeometryType;
/*     */ import net.opengis.gml.EnvelopeType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="BinarySpatialOpType", propOrder={"propertyName", "geometry", "envelope"})
/*     */ public class BinarySpatialOpType extends SpatialOpsType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="PropertyName", required=true)
/*     */   protected PropertyNameType propertyName;
/*     */ 
/*     */   @XmlElementRef(name="_Geometry", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends AbstractGeometryType> geometry;
/*     */ 
/*     */   @XmlElement(name="Envelope", namespace="http://www.opengis.net/gml")
/*     */   protected EnvelopeType envelope;
/*     */ 
/*     */   public PropertyNameType getPropertyName()
/*     */   {
/*  96 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   public void setPropertyName(PropertyNameType value)
/*     */   {
/* 108 */     this.propertyName = value;
/*     */   }
/*     */ 
/*     */   public JAXBElement<? extends AbstractGeometryType> get_Geometry()
/*     */   {
/* 146 */     return this.geometry;
/*     */   }
/*     */ 
/*     */   public void set_Geometry(JAXBElement<? extends AbstractGeometryType> value)
/*     */   {
/* 184 */     this.geometry = value;
/*     */   }
/*     */ 
/*     */   public EnvelopeType getEnvelope()
/*     */   {
/* 196 */     return this.envelope;
/*     */   }
/*     */ 
/*     */   public void setEnvelope(EnvelopeType value)
/*     */   {
/* 208 */     this.envelope = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.BinarySpatialOpType
 * JD-Core Version:    0.6.0
 */