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
/*    */ @XmlType(name="CurveArrayPropertyType", propOrder={"curve"})
/*    */ public class CurveArrayPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElementRef(name="_Curve", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*    */   protected List<JAXBElement<? extends AbstractCurveType>> curve;
/*    */ 
/*    */   public List<JAXBElement<? extends AbstractCurveType>> get_Curve()
/*    */   {
/* 77 */     if (this.curve == null) {
/* 78 */       this.curve = new ArrayList();
/*    */     }
/* 80 */     return this.curve;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CurveArrayPropertyType
 * JD-Core Version:    0.6.0
 */