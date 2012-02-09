/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="KnotType", propOrder={"value", "multiplicity", "weight"})
/*     */ public class KnotType
/*     */ {
/*     */   protected double value;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   @XmlSchemaType(name="nonNegativeInteger")
/*     */   protected BigInteger multiplicity;
/*     */   protected double weight;
/*     */ 
/*     */   public double getValue()
/*     */   {
/*  61 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(double value)
/*     */   {
/*  69 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getMultiplicity()
/*     */   {
/*  81 */     return this.multiplicity;
/*     */   }
/*     */ 
/*     */   public void setMultiplicity(BigInteger value)
/*     */   {
/*  93 */     this.multiplicity = value;
/*     */   }
/*     */ 
/*     */   public double getWeight()
/*     */   {
/* 101 */     return this.weight;
/*     */   }
/*     */ 
/*     */   public void setWeight(double value)
/*     */   {
/* 109 */     this.weight = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.KnotType
 * JD-Core Version:    0.6.0
 */