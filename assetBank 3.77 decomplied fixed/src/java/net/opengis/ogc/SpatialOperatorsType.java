/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="SpatialOperatorsType", propOrder={"spatialOperator"})
/*    */ public class SpatialOperatorsType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="SpatialOperator", required=true)
/*    */   protected List<SpatialOperatorType> spatialOperator;
/*    */ 
/*    */   public List<SpatialOperatorType> getSpatialOperator()
/*    */   {
/* 70 */     if (this.spatialOperator == null) {
/* 71 */       this.spatialOperator = new ArrayList();
/*    */     }
/* 73 */     return this.spatialOperator;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.SpatialOperatorsType
 * JD-Core Version:    0.6.0
 */