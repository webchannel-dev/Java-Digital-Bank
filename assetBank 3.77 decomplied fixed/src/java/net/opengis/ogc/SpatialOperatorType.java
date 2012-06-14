/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="SpatialOperatorType", propOrder={"geometryOperands"})
/*    */ public class SpatialOperatorType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="GeometryOperands")
/*    */   protected GeometryOperandsType geometryOperands;
/*    */ 
/*    */   @XmlAttribute(name="name")
/*    */   protected SpatialOperatorNameType name;
/*    */ 
/*    */   public GeometryOperandsType getGeometryOperands()
/*    */   {
/* 58 */     return this.geometryOperands;
/*    */   }
/*    */ 
/*    */   public void setGeometryOperands(GeometryOperandsType value)
/*    */   {
/* 70 */     this.geometryOperands = value;
/*    */   }
/*    */ 
/*    */   public SpatialOperatorNameType getName()
/*    */   {
/* 82 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(SpatialOperatorNameType value)
/*    */   {
/* 94 */     this.name = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.SpatialOperatorType
 * JD-Core Version:    0.6.0
 */