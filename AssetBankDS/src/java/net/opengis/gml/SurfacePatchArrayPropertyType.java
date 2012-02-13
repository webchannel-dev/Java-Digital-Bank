/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
/*    */ import javax.xml.bind.annotation.XmlSeeAlso;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="SurfacePatchArrayPropertyType", propOrder={"surfacePatch"})
/*    */ @XmlSeeAlso({TrianglePatchArrayPropertyType.class, PolygonPatchArrayPropertyType.class})
/*    */ public class SurfacePatchArrayPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElementRef(name="_SurfacePatch", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*    */   protected List<JAXBElement<? extends AbstractSurfacePatchType>> surfacePatch;
/*    */ 
/*    */   public List<JAXBElement<? extends AbstractSurfacePatchType>> get_SurfacePatch()
/*    */   {
/* 86 */     if (this.surfacePatch == null) {
/* 87 */       this.surfacePatch = new ArrayList();
/*    */     }
/* 89 */     return this.surfacePatch;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.SurfacePatchArrayPropertyType
 * JD-Core Version:    0.6.0
 */