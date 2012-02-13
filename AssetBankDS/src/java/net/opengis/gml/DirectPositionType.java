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
/*     */ @XmlType(name="DirectPositionType", propOrder={"value"})
/*     */ public class DirectPositionType
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
/*  86 */     if (this.value == null) {
/*  87 */       this.value = new ArrayList();
/*     */     }
/*  89 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String getSrsName()
/*     */   {
/* 101 */     return this.srsName;
/*     */   }
/*     */ 
/*     */   public void setSrsName(String value)
/*     */   {
/* 113 */     this.srsName = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getSrsDimension()
/*     */   {
/* 125 */     return this.srsDimension;
/*     */   }
/*     */ 
/*     */   public void setSrsDimension(BigInteger value)
/*     */   {
/* 137 */     this.srsDimension = value;
/*     */   }
/*     */ 
/*     */   public List<String> getAxisLabels()
/*     */   {
/* 163 */     if (this.axisLabels == null) {
/* 164 */       this.axisLabels = new ArrayList();
/*     */     }
/* 166 */     return this.axisLabels;
/*     */   }
/*     */ 
/*     */   public List<String> getUomLabels()
/*     */   {
/* 192 */     if (this.uomLabels == null) {
/* 193 */       this.uomLabels = new ArrayList();
/*     */     }
/* 195 */     return this.uomLabels;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.DirectPositionType
 * JD-Core Version:    0.6.0
 */