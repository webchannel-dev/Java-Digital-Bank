/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="Spatial_CapabilitiesType", propOrder={"geometryOperands", "spatialOperators"})
/*    */ public class Spatial_CapabilitiesType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="GeometryOperands", required=true)
/*    */   protected GeometryOperandsType geometryOperands;
/*    */ 
/*    */   @XmlElement(name="SpatialOperators", required=true)
/*    */   protected SpatialOperatorsType spatialOperators;
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
/*    */   public SpatialOperatorsType getSpatialOperators()
/*    */   {
/* 82 */     return this.spatialOperators;
/*    */   }
/*    */ 
/*    */   public void setSpatialOperators(SpatialOperatorsType value)
/*    */   {
/* 94 */     this.spatialOperators = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.Spatial_CapabilitiesType
 * JD-Core Version:    0.6.0
 */