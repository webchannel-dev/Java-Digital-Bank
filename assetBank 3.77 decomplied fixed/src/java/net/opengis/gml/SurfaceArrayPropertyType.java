/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="SurfaceArrayPropertyType", propOrder={"surface"})
/*    */ public class SurfaceArrayPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElementRef(name="_Surface", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*    */   protected List<JAXBElement<? extends AbstractSurfaceType>> surface;
/*    */ 
/*    */   public List<JAXBElement<? extends AbstractSurfaceType>> get_Surface()
/*    */   {
/* 79 */     if (this.surface == null) {
/* 80 */       this.surface = new ArrayList();
/*    */     }
/* 82 */     return this.surface;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.SurfaceArrayPropertyType
 * JD-Core Version:    0.6.0
 */