/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="ConversionToPreferredUnitType", propOrder={"factor", "formula"})
/*    */ public class ConversionToPreferredUnitType extends UnitOfMeasureType
/*    */ {
/*    */   protected Double factor;
/*    */   protected FormulaType formula;
/*    */ 
/*    */   public Double getFactor()
/*    */   {
/* 59 */     return this.factor;
/*    */   }
/*    */ 
/*    */   public void setFactor(Double value)
/*    */   {
/* 71 */     this.factor = value;
/*    */   }
/*    */ 
/*    */   public FormulaType getFormula()
/*    */   {
/* 83 */     return this.formula;
/*    */   }
/*    */ 
/*    */   public void setFormula(FormulaType value)
/*    */   {
/* 95 */     this.formula = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ConversionToPreferredUnitType
 * JD-Core Version:    0.6.0
 */