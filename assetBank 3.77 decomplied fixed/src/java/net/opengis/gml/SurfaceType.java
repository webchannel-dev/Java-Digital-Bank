/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
/*    */ import javax.xml.bind.annotation.XmlSeeAlso;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="SurfaceType", propOrder={"patches"})
/*    */ @XmlSeeAlso({TriangulatedSurfaceType.class, PolyhedralSurfaceType.class})
/*    */ public class SurfaceType extends AbstractSurfaceType
/*    */ {
/*    */ 
/*    */   @XmlElementRef(name="patches", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*    */   protected JAXBElement<? extends SurfacePatchArrayPropertyType> patches;
/*    */ 
/*    */   public JAXBElement<? extends SurfacePatchArrayPropertyType> getPatches()
/*    */   {
/* 67 */     return this.patches;
/*    */   }
/*    */ 
/*    */   public void setPatches(JAXBElement<? extends SurfacePatchArrayPropertyType> value)
/*    */   {
/* 81 */     this.patches = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.SurfaceType
 * JD-Core Version:    0.6.0
 */