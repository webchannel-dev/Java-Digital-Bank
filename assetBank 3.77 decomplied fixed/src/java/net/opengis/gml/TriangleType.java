/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="TriangleType", propOrder={"exterior"})
/*     */ public class TriangleType extends AbstractSurfacePatchType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="exterior", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected JAXBElement<AbstractRingPropertyType> exterior;
/*     */ 
/*     */   @XmlAttribute(name="interpolation")
/*     */   protected SurfaceInterpolationType interpolation;
/*     */ 
/*     */   public JAXBElement<AbstractRingPropertyType> getExterior()
/*     */   {
/*  64 */     return this.exterior;
/*     */   }
/*     */ 
/*     */   public void setExterior(JAXBElement<AbstractRingPropertyType> value)
/*     */   {
/*  77 */     this.exterior = value;
/*     */   }
/*     */ 
/*     */   public SurfaceInterpolationType getInterpolation()
/*     */   {
/*  89 */     if (this.interpolation == null) {
/*  90 */       return SurfaceInterpolationType.PLANAR;
/*     */     }
/*  92 */     return this.interpolation;
/*     */   }
/*     */ 
/*     */   public void setInterpolation(SurfaceInterpolationType value)
/*     */   {
/* 105 */     this.interpolation = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.TriangleType
 * JD-Core Version:    0.6.0
 */