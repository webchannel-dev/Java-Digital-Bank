/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="DerivationUnitTermType")
/*    */ public class DerivationUnitTermType extends UnitOfMeasureType
/*    */ {
/*    */ 
/*    */   @XmlAttribute(name="exponent")
/*    */   protected BigInteger exponent;
/*    */ 
/*    */   public BigInteger getExponent()
/*    */   {
/* 55 */     return this.exponent;
/*    */   }
/*    */ 
/*    */   public void setExponent(BigInteger value)
/*    */   {
/* 67 */     this.exponent = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.DerivationUnitTermType
 * JD-Core Version:    0.6.0
 */