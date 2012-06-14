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
/*    */ @XmlType(name="CurveSegmentArrayPropertyType", propOrder={"curveSegment"})
/*    */ public class CurveSegmentArrayPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElementRef(name="_CurveSegment", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*    */   protected List<JAXBElement<? extends AbstractCurveSegmentType>> curveSegment;
/*    */ 
/*    */   public List<JAXBElement<? extends AbstractCurveSegmentType>> get_CurveSegment()
/*    */   {
/* 88 */     if (this.curveSegment == null) {
/* 89 */       this.curveSegment = new ArrayList();
/*    */     }
/* 91 */     return this.curveSegment;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CurveSegmentArrayPropertyType
 * JD-Core Version:    0.6.0
 */