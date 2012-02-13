/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="VectorType", propOrder={"value"})
/*     */ public class VectorType
/*     */ {
/*     */ 
/*     */   @XmlValue
/*     */   protected List<Double> value;
/*     */ 
/*     */   @XmlAttribute(name="srsName")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String srsName;
/*     */ 
/*     */   @XmlAttribute(name="srsDimension")
/*     */   @XmlSchemaType(name="positiveInteger")
/*     */   protected BigInteger srsDimension;
/*     */ 
/*     */   @XmlAttribute(name="axisLabels")
/*     */   protected List<String> axisLabels;
/*     */ 
/*     */   @XmlAttribute(name="uomLabels")
/*     */   protected List<String> uomLabels;
/*     */ 
/*     */   public List<Double> getValue()
/*     */   {
/*  87 */     if (this.value == null) {
/*  88 */       this.value = new ArrayList();
/*     */     }
/*  90 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String getSrsName()
/*     */   {
/* 102 */     return this.srsName;
/*     */   }
/*     */ 
/*     */   public void setSrsName(String value)
/*     */   {
/* 114 */     this.srsName = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getSrsDimension()
/*     */   {
/* 126 */     return this.srsDimension;
/*     */   }
/*     */ 
/*     */   public void setSrsDimension(BigInteger value)
/*     */   {
/* 138 */     this.srsDimension = value;
/*     */   }
/*     */ 
/*     */   public List<String> getAxisLabels()
/*     */   {
/* 164 */     if (this.axisLabels == null) {
/* 165 */       this.axisLabels = new ArrayList();
/*     */     }
/* 167 */     return this.axisLabels;
/*     */   }
/*     */ 
/*     */   public List<String> getUomLabels()
/*     */   {
/* 193 */     if (this.uomLabels == null) {
/* 194 */       this.uomLabels = new ArrayList();
/*     */     }
/* 196 */     return this.uomLabels;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.VectorType
 * JD-Core Version:    0.6.0
 */