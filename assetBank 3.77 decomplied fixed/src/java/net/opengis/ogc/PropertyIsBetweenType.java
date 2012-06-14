/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="PropertyIsBetweenType", propOrder={"expression", "lowerBoundary", "upperBoundary"})
/*     */ public class PropertyIsBetweenType extends ComparisonOpsType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="expression", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected JAXBElement<?> expression;
/*     */ 
/*     */   @XmlElement(name="LowerBoundary", required=true)
/*     */   protected LowerBoundaryType lowerBoundary;
/*     */ 
/*     */   @XmlElement(name="UpperBoundary", required=true)
/*     */   protected UpperBoundaryType upperBoundary;
/*     */ 
/*     */   public JAXBElement<?> getExpression()
/*     */   {
/*  73 */     return this.expression;
/*     */   }
/*     */ 
/*     */   public void setExpression(JAXBElement<?> value)
/*     */   {
/*  92 */     this.expression = value;
/*     */   }
/*     */ 
/*     */   public LowerBoundaryType getLowerBoundary()
/*     */   {
/* 104 */     return this.lowerBoundary;
/*     */   }
/*     */ 
/*     */   public void setLowerBoundary(LowerBoundaryType value)
/*     */   {
/* 116 */     this.lowerBoundary = value;
/*     */   }
/*     */ 
/*     */   public UpperBoundaryType getUpperBoundary()
/*     */   {
/* 128 */     return this.upperBoundary;
/*     */   }
/*     */ 
/*     */   public void setUpperBoundary(UpperBoundaryType value)
/*     */   {
/* 140 */     this.upperBoundary = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.PropertyIsBetweenType
 * JD-Core Version:    0.6.0
 */