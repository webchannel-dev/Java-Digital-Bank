/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="LineStringSegmentArrayPropertyType", propOrder={"lineStringSegment"})
/*    */ public class LineStringSegmentArrayPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="LineStringSegment")
/*    */   protected List<LineStringSegmentType> lineStringSegment;
/*    */ 
/*    */   public List<LineStringSegmentType> getLineStringSegment()
/*    */   {
/* 70 */     if (this.lineStringSegment == null) {
/* 71 */       this.lineStringSegment = new ArrayList();
/*    */     }
/* 73 */     return this.lineStringSegment;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.LineStringSegmentArrayPropertyType
 * JD-Core Version:    0.6.0
 */