/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="PolygonType", propOrder={"exterior", "interior"})
/*     */ public class PolygonType extends AbstractSurfaceType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="exterior", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected JAXBElement<AbstractRingPropertyType> exterior;
/*     */ 
/*     */   @XmlElementRef(name="interior", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected List<JAXBElement<AbstractRingPropertyType>> interior;
/*     */ 
/*     */   public JAXBElement<AbstractRingPropertyType> getExterior()
/*     */   {
/*  66 */     return this.exterior;
/*     */   }
/*     */ 
/*     */   public void setExterior(JAXBElement<AbstractRingPropertyType> value)
/*     */   {
/*  79 */     this.exterior = value;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<AbstractRingPropertyType>> getInterior()
/*     */   {
/* 106 */     if (this.interior == null) {
/* 107 */       this.interior = new ArrayList();
/*     */     }
/* 109 */     return this.interior;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.PolygonType
 * JD-Core Version:    0.6.0
 */