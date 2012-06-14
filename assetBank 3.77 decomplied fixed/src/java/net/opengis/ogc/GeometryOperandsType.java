/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="GeometryOperandsType", propOrder={"geometryOperand"})
/*    */ public class GeometryOperandsType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="GeometryOperand", required=true)
/*    */   protected List<QName> geometryOperand;
/*    */ 
/*    */   public List<QName> getGeometryOperand()
/*    */   {
/* 71 */     if (this.geometryOperand == null) {
/* 72 */       this.geometryOperand = new ArrayList();
/*    */     }
/* 74 */     return this.geometryOperand;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.GeometryOperandsType
 * JD-Core Version:    0.6.0
 */