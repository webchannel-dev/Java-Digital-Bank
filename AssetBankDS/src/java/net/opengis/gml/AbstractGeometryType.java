/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="AbstractGeometryType")
/*     */ @XmlSeeAlso({AbstractRingType.class, AbstractGeometricPrimitiveType.class, AbstractGeometricAggregateType.class})
/*     */ public abstract class AbstractGeometryType extends AbstractGMLType
/*     */ {
/*     */ 
/*     */   @XmlAttribute(name="gid")
/*     */   protected String gid;
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
/*     */   public String getGid()
/*     */   {
/*  79 */     return this.gid;
/*     */   }
/*     */ 
/*     */   public void setGid(String value)
/*     */   {
/*  91 */     this.gid = value;
/*     */   }
/*     */ 
/*     */   public String getSrsName()
/*     */   {
/* 103 */     return this.srsName;
/*     */   }
/*     */ 
/*     */   public void setSrsName(String value)
/*     */   {
/* 115 */     this.srsName = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getSrsDimension()
/*     */   {
/* 127 */     return this.srsDimension;
/*     */   }
/*     */ 
/*     */   public void setSrsDimension(BigInteger value)
/*     */   {
/* 139 */     this.srsDimension = value;
/*     */   }
/*     */ 
/*     */   public List<String> getAxisLabels()
/*     */   {
/* 165 */     if (this.axisLabels == null) {
/* 166 */       this.axisLabels = new ArrayList();
/*     */     }
/* 168 */     return this.axisLabels;
/*     */   }
/*     */ 
/*     */   public List<String> getUomLabels()
/*     */   {
/* 194 */     if (this.uomLabels == null) {
/* 195 */       this.uomLabels = new ArrayList();
/*     */     }
/* 197 */     return this.uomLabels;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractGeometryType
 * JD-Core Version:    0.6.0
 */