/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="DistanceType")
/*    */ public class DistanceType
/*    */ {
/*    */ 
/*    */   @XmlAttribute(name="units", required=true)
/*    */   protected String units;
/*    */ 
/*    */   public String getUnits()
/*    */   {
/* 50 */     return this.units;
/*    */   }
/*    */ 
/*    */   public void setUnits(String value)
/*    */   {
/* 62 */     this.units = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.DistanceType
 * JD-Core Version:    0.6.0
 */