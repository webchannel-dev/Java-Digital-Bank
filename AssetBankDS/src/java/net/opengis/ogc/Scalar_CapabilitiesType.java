/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="Scalar_CapabilitiesType", propOrder={"logicalOperators", "comparisonOperators", "arithmeticOperators"})
/*     */ public class Scalar_CapabilitiesType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="LogicalOperators")
/*     */   protected LogicalOperators logicalOperators;
/*     */ 
/*     */   @XmlElement(name="ComparisonOperators")
/*     */   protected ComparisonOperatorsType comparisonOperators;
/*     */ 
/*     */   @XmlElement(name="ArithmeticOperators")
/*     */   protected ArithmeticOperatorsType arithmeticOperators;
/*     */ 
/*     */   public LogicalOperators getLogicalOperators()
/*     */   {
/*  62 */     return this.logicalOperators;
/*     */   }
/*     */ 
/*     */   public void setLogicalOperators(LogicalOperators value)
/*     */   {
/*  74 */     this.logicalOperators = value;
/*     */   }
/*     */ 
/*     */   public ComparisonOperatorsType getComparisonOperators()
/*     */   {
/*  86 */     return this.comparisonOperators;
/*     */   }
/*     */ 
/*     */   public void setComparisonOperators(ComparisonOperatorsType value)
/*     */   {
/*  98 */     this.comparisonOperators = value;
/*     */   }
/*     */ 
/*     */   public ArithmeticOperatorsType getArithmeticOperators()
/*     */   {
/* 110 */     return this.arithmeticOperators;
/*     */   }
/*     */ 
/*     */   public void setArithmeticOperators(ArithmeticOperatorsType value)
/*     */   {
/* 122 */     this.arithmeticOperators = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.Scalar_CapabilitiesType
 * JD-Core Version:    0.6.0
 */