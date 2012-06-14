/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import net.opengis.gml.AbstractGeometryType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="DistanceBufferType", propOrder={"propertyName", "geometry", "distance"})
/*     */ public class DistanceBufferType extends SpatialOpsType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="PropertyName", required=true)
/*     */   protected PropertyNameType propertyName;
/*     */ 
/*     */   @XmlElementRef(name="_Geometry", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends AbstractGeometryType> geometry;
/*     */ 
/*     */   @XmlElement(name="Distance", required=true)
/*     */   protected DistanceType distance;
/*     */ 
/*     */   public PropertyNameType getPropertyName()
/*     */   {
/*  93 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   public void setPropertyName(PropertyNameType value)
/*     */   {
/* 105 */     this.propertyName = value;
/*     */   }
/*     */ 
/*     */   public JAXBElement<? extends AbstractGeometryType> get_Geometry()
/*     */   {
/* 143 */     return this.geometry;
/*     */   }
/*     */ 
/*     */   public void set_Geometry(JAXBElement<? extends AbstractGeometryType> value)
/*     */   {
/* 181 */     this.geometry = value;
/*     */   }
/*     */ 
/*     */   public DistanceType getDistance()
/*     */   {
/* 193 */     return this.distance;
/*     */   }
/*     */ 
/*     */   public void setDistance(DistanceType value)
/*     */   {
/* 205 */     this.distance = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.DistanceBufferType
 * JD-Core Version:    0.6.0
 */