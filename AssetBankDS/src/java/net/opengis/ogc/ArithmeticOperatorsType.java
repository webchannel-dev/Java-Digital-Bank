/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElements;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="ArithmeticOperatorsType", propOrder={"simpleArithmeticOrFunctions"})
/*    */ public class ArithmeticOperatorsType
/*    */ {
/*    */ 
/*    */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="SimpleArithmetic", type=SimpleArithmetic.class), @javax.xml.bind.annotation.XmlElement(name="Functions", type=FunctionsType.class)})
/*    */   protected List<Object> simpleArithmeticOrFunctions;
/*    */ 
/*    */   public List<Object> getSimpleArithmeticOrFunctions()
/*    */   {
/* 76 */     if (this.simpleArithmeticOrFunctions == null) {
/* 77 */       this.simpleArithmeticOrFunctions = new ArrayList();
/*    */     }
/* 79 */     return this.simpleArithmeticOrFunctions;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.ArithmeticOperatorsType
 * JD-Core Version:    0.6.0
 */