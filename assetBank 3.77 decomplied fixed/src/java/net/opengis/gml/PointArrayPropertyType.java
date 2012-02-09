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
/*    */ @XmlType(name="PointArrayPropertyType", propOrder={"point"})
/*    */ public class PointArrayPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Point")
/*    */   protected List<PointType> point;
/*    */ 
/*    */   public List<PointType> getPoint()
/*    */   {
/* 73 */     if (this.point == null) {
/* 74 */       this.point = new ArrayList();
/*    */     }
/* 76 */     return this.point;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.PointArrayPropertyType
 * JD-Core Version:    0.6.0
 */