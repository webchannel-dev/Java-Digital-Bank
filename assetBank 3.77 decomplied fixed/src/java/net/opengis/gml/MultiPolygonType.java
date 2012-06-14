/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="MultiPolygonType", propOrder={"polygonMember"})
/*    */ public class MultiPolygonType extends AbstractGeometricAggregateType
/*    */ {
/*    */   protected List<PolygonPropertyType> polygonMember;
/*    */ 
/*    */   public List<PolygonPropertyType> getPolygonMember()
/*    */   {
/* 72 */     if (this.polygonMember == null) {
/* 73 */       this.polygonMember = new ArrayList();
/*    */     }
/* 75 */     return this.polygonMember;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MultiPolygonType
 * JD-Core Version:    0.6.0
 */