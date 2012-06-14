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
/*     */ @XmlType(name="DirectPositionListType", propOrder={"value"})
/*     */ public class DirectPositionListType
/*     */ {
/*     */ 
/*     */   @XmlValue
/*     */   protected List<Double> value;
/*     */ 
/*     */   @XmlAttribute(name="count")
/*     */   @XmlSchemaType(name="positiveInteger")
/*     */   protected BigInteger count;
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
/*  88 */     if (this.value == null) {
/*  89 */       this.value = new ArrayList();
/*     */     }
/*  91 */     return this.value;
/*     */   }
/*     */ 
/*     */   public BigInteger getCount()
/*     */   {
/* 103 */     return this.count;
/*     */   }
/*     */ 
/*     */   public void setCount(BigInteger value)
/*     */   {
/* 115 */     this.count = value;
/*     */   }
/*     */ 
/*     */   public String getSrsName()
/*     */   {
/* 127 */     return this.srsName;
/*     */   }
/*     */ 
/*     */   public void setSrsName(String value)
/*     */   {
/* 139 */     this.srsName = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getSrsDimension()
/*     */   {
/* 151 */     return this.srsDimension;
/*     */   }
/*     */ 
/*     */   public void setSrsDimension(BigInteger value)
/*     */   {
/* 163 */     this.srsDimension = value;
/*     */   }
/*     */ 
/*     */   public List<String> getAxisLabels()
/*     */   {
/* 189 */     if (this.axisLabels == null) {
/* 190 */       this.axisLabels = new ArrayList();
/*     */     }
/* 192 */     return this.axisLabels;
/*     */   }
/*     */ 
/*     */   public List<String> getUomLabels()
/*     */   {
/* 218 */     if (this.uomLabels == null) {
/* 219 */       this.uomLabels = new ArrayList();
/*     */     }
/* 221 */     return this.uomLabels;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.DirectPositionListType
 * JD-Core Version:    0.6.0
 */