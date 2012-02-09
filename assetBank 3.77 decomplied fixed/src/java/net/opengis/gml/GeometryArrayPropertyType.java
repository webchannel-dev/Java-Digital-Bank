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
/*     */ @XmlType(name="GeometryArrayPropertyType", propOrder={"geometry"})
/*     */ public class GeometryArrayPropertyType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="_Geometry", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*     */   protected List<JAXBElement<? extends AbstractGeometryType>> geometry;
/*     */ 
/*     */   public List<JAXBElement<? extends AbstractGeometryType>> get_Geometry()
/*     */   {
/* 100 */     if (this.geometry == null) {
/* 101 */       this.geometry = new ArrayList();
/*     */     }
/* 103 */     return this.geometry;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.GeometryArrayPropertyType
 * JD-Core Version:    0.6.0
 */