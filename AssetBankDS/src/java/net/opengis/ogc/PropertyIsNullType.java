/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="PropertyIsNullType", propOrder={"propertyName"})
/*    */ public class PropertyIsNullType extends ComparisonOpsType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="PropertyName", required=true)
/*    */   protected PropertyNameType propertyName;
/*    */ 
/*    */   public PropertyNameType getPropertyName()
/*    */   {
/* 56 */     return this.propertyName;
/*    */   }
/*    */ 
/*    */   public void setPropertyName(PropertyNameType value)
/*    */   {
/* 68 */     this.propertyName = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.PropertyIsNullType
 * JD-Core Version:    0.6.0
 */