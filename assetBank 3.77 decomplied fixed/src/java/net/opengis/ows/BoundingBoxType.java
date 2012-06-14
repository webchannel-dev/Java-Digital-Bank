/*     */ package net.opengis.ows;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlList;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="BoundingBoxType", propOrder={"lowerCorner", "upperCorner"})
/*     */ @XmlSeeAlso({WGS84BoundingBoxType.class})
/*     */ public class BoundingBoxType
/*     */ {
/*     */ 
/*     */   @XmlList
/*     */   @XmlElement(name="LowerCorner", type=Double.class)
/*     */   protected List<Double> lowerCorner;
/*     */ 
/*     */   @XmlList
/*     */   @XmlElement(name="UpperCorner", type=Double.class)
/*     */   protected List<Double> upperCorner;
/*     */ 
/*     */   @XmlAttribute(name="crs")
/*     */   @XmlSchemaType(name="anyURI")
/*     */   protected String crs;
/*     */ 
/*     */   @XmlAttribute(name="dimensions")
/*     */   @XmlSchemaType(name="positiveInteger")
/*     */   protected BigInteger dimensions;
/*     */ 
/*     */   public List<Double> getLowerCorner()
/*     */   {
/*  94 */     if (this.lowerCorner == null) {
/*  95 */       this.lowerCorner = new ArrayList();
/*     */     }
/*  97 */     return this.lowerCorner;
/*     */   }
/*     */ 
/*     */   public List<Double> getUpperCorner()
/*     */   {
/* 123 */     if (this.upperCorner == null) {
/* 124 */       this.upperCorner = new ArrayList();
/*     */     }
/* 126 */     return this.upperCorner;
/*     */   }
/*     */ 
/*     */   public String getCrs()
/*     */   {
/* 138 */     return this.crs;
/*     */   }
/*     */ 
/*     */   public void setCrs(String value)
/*     */   {
/* 150 */     this.crs = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getDimensions()
/*     */   {
/* 162 */     return this.dimensions;
/*     */   }
/*     */ 
/*     */   public void setDimensions(BigInteger value)
/*     */   {
/* 174 */     this.dimensions = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.BoundingBoxType
 * JD-Core Version:    0.6.0
 */