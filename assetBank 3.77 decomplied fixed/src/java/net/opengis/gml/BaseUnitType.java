/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="BaseUnitType", propOrder={"unitsSystem"})
/*    */ public class BaseUnitType extends UnitDefinitionType
/*    */ {
/*    */ 
/*    */   @XmlElement(required=true)
/*    */   protected ReferenceType unitsSystem;
/*    */ 
/*    */   public ReferenceType getUnitsSystem()
/*    */   {
/* 58 */     return this.unitsSystem;
/*    */   }
/*    */ 
/*    */   public void setUnitsSystem(ReferenceType value)
/*    */   {
/* 70 */     this.unitsSystem = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.BaseUnitType
 * JD-Core Version:    0.6.0
 */