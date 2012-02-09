/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="LinearRingPropertyType", propOrder={"linearRing"})
/*    */ public class LinearRingPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="LinearRing")
/*    */   protected LinearRingType linearRing;
/*    */ 
/*    */   public LinearRingType getLinearRing()
/*    */   {
/* 56 */     return this.linearRing;
/*    */   }
/*    */ 
/*    */   public void setLinearRing(LinearRingType value)
/*    */   {
/* 68 */     this.linearRing = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.LinearRingPropertyType
 * JD-Core Version:    0.6.0
 */