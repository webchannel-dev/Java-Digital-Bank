/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="CurveType", propOrder={"segments"})
/*    */ public class CurveType extends AbstractCurveType
/*    */ {
/*    */ 
/*    */   @XmlElement(required=true)
/*    */   protected CurveSegmentArrayPropertyType segments;
/*    */ 
/*    */   public CurveSegmentArrayPropertyType getSegments()
/*    */   {
/* 60 */     return this.segments;
/*    */   }
/*    */ 
/*    */   public void setSegments(CurveSegmentArrayPropertyType value)
/*    */   {
/* 72 */     this.segments = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CurveType
 * JD-Core Version:    0.6.0
 */