/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="UpperBoundaryType", propOrder={"expression"})
/*    */ public class UpperBoundaryType
/*    */ {
/*    */ 
/*    */   @XmlElementRef(name="expression", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*    */   protected JAXBElement<?> expression;
/*    */ 
/*    */   public JAXBElement<?> getExpression()
/*    */   {
/* 62 */     return this.expression;
/*    */   }
/*    */ 
/*    */   public void setExpression(JAXBElement<?> value)
/*    */   {
/* 81 */     this.expression = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.UpperBoundaryType
 * JD-Core Version:    0.6.0
 */