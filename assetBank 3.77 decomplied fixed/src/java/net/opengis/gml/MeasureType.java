/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
/*    */ import javax.xml.bind.annotation.XmlSeeAlso;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.bind.annotation.XmlValue;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="MeasureType", propOrder={"value"})
/*    */ @XmlSeeAlso({SpeedType.class, ScaleType.class, AngleType.class, VolumeType.class, GridLengthType.class, LengthType.class, AreaType.class, TimeType.class})
/*    */ public class MeasureType
/*    */ {
/*    */ 
/*    */   @XmlValue
/*    */   protected double value;
/*    */ 
/*    */   @XmlAttribute(name="uom", required=true)
/*    */   @XmlSchemaType(name="anyURI")
/*    */   protected String uom;
/*    */ 
/*    */   public double getValue()
/*    */   {
/* 67 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(double value)
/*    */   {
/* 75 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getUom()
/*    */   {
/* 87 */     return this.uom;
/*    */   }
/*    */ 
/*    */   public void setUom(String value)
/*    */   {
/* 99 */     this.uom = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MeasureType
 * JD-Core Version:    0.6.0
 */