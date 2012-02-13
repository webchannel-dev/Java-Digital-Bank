/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="BinaryOperatorType", propOrder={"expression"})
/*    */ public class BinaryOperatorType extends ExpressionType
/*    */ {
/*    */ 
/*    */   @XmlElementRef(name="expression", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*    */   protected List<JAXBElement<?>> expression;
/*    */ 
/*    */   public List<JAXBElement<?>> getExpression()
/*    */   {
/* 80 */     if (this.expression == null) {
/* 81 */       this.expression = new ArrayList();
/*    */     }
/* 83 */     return this.expression;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.BinaryOperatorType
 * JD-Core Version:    0.6.0
 */