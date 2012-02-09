/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
/*    */ import javax.xml.bind.annotation.XmlSeeAlso;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="UnitOfMeasureType")
/*    */ @XmlSeeAlso({ConversionToPreferredUnitType.class, DerivationUnitTermType.class})
/*    */ public class UnitOfMeasureType
/*    */ {
/*    */ 
/*    */   @XmlAttribute(name="uom", required=true)
/*    */   @XmlSchemaType(name="anyURI")
/*    */   protected String uom;
/*    */ 
/*    */   public String getUom()
/*    */   {
/* 61 */     return this.uom;
/*    */   }
/*    */ 
/*    */   public void setUom(String value)
/*    */   {
/* 73 */     this.uom = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.UnitOfMeasureType
 * JD-Core Version:    0.6.0
 */