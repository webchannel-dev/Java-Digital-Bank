/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="BinaryComparisonOpType", propOrder={"expression"})
/*     */ public class BinaryComparisonOpType extends ComparisonOpsType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="expression", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected List<JAXBElement<?>> expression;
/*     */ 
/*     */   @XmlAttribute(name="matchCase")
/*     */   protected Boolean matchCase;
/*     */ 
/*     */   public List<JAXBElement<?>> getExpression()
/*     */   {
/*  84 */     if (this.expression == null) {
/*  85 */       this.expression = new ArrayList();
/*     */     }
/*  87 */     return this.expression;
/*     */   }
/*     */ 
/*     */   public boolean isMatchCase()
/*     */   {
/*  99 */     if (this.matchCase == null) {
/* 100 */       return true;
/*     */     }
/* 102 */     return this.matchCase.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setMatchCase(Boolean value)
/*     */   {
/* 115 */     this.matchCase = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.BinaryComparisonOpType
 * JD-Core Version:    0.6.0
 */