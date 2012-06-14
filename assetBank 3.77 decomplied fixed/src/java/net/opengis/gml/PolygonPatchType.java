/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="PolygonPatchType", propOrder={"exterior", "interior"})
/*     */ public class PolygonPatchType extends AbstractSurfacePatchType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="exterior", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected JAXBElement<AbstractRingPropertyType> exterior;
/*     */ 
/*     */   @XmlElementRef(name="interior", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected List<JAXBElement<AbstractRingPropertyType>> interior;
/*     */ 
/*     */   @XmlAttribute(name="interpolation")
/*     */   protected SurfaceInterpolationType interpolation;
/*     */ 
/*     */   public JAXBElement<AbstractRingPropertyType> getExterior()
/*     */   {
/*  70 */     return this.exterior;
/*     */   }
/*     */ 
/*     */   public void setExterior(JAXBElement<AbstractRingPropertyType> value)
/*     */   {
/*  83 */     this.exterior = value;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<AbstractRingPropertyType>> getInterior()
/*     */   {
/* 110 */     if (this.interior == null) {
/* 111 */       this.interior = new ArrayList();
/*     */     }
/* 113 */     return this.interior;
/*     */   }
/*     */ 
/*     */   public SurfaceInterpolationType getInterpolation()
/*     */   {
/* 125 */     if (this.interpolation == null) {
/* 126 */       return SurfaceInterpolationType.PLANAR;
/*     */     }
/* 128 */     return this.interpolation;
/*     */   }
/*     */ 
/*     */   public void setInterpolation(SurfaceInterpolationType value)
/*     */   {
/* 141 */     this.interpolation = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.PolygonPatchType
 * JD-Core Version:    0.6.0
 */