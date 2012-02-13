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
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="EnvelopeType", propOrder={"lowerCorner", "upperCorner", "coord", "pos", "coordinates"})
/*     */ public class EnvelopeType
/*     */ {
/*     */   protected DirectPositionType lowerCorner;
/*     */   protected DirectPositionType upperCorner;
/*     */   protected List<CoordType> coord;
/*     */   protected List<DirectPositionType> pos;
/*     */   protected CoordinatesType coordinates;
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
/*     */   public DirectPositionType getLowerCorner()
/*     */   {
/*  87 */     return this.lowerCorner;
/*     */   }
/*     */ 
/*     */   public void setLowerCorner(DirectPositionType value)
/*     */   {
/*  99 */     this.lowerCorner = value;
/*     */   }
/*     */ 
/*     */   public DirectPositionType getUpperCorner()
/*     */   {
/* 111 */     return this.upperCorner;
/*     */   }
/*     */ 
/*     */   public void setUpperCorner(DirectPositionType value)
/*     */   {
/* 123 */     this.upperCorner = value;
/*     */   }
/*     */ 
/*     */   public List<CoordType> getCoord()
/*     */   {
/* 149 */     if (this.coord == null) {
/* 150 */       this.coord = new ArrayList();
/*     */     }
/* 152 */     return this.coord;
/*     */   }
/*     */ 
/*     */   public List<DirectPositionType> getPos()
/*     */   {
/* 178 */     if (this.pos == null) {
/* 179 */       this.pos = new ArrayList();
/*     */     }
/* 181 */     return this.pos;
/*     */   }
/*     */ 
/*     */   public CoordinatesType getCoordinates()
/*     */   {
/* 193 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */   public void setCoordinates(CoordinatesType value)
/*     */   {
/* 205 */     this.coordinates = value;
/*     */   }
/*     */ 
/*     */   public String getSrsName()
/*     */   {
/* 217 */     return this.srsName;
/*     */   }
/*     */ 
/*     */   public void setSrsName(String value)
/*     */   {
/* 229 */     this.srsName = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getSrsDimension()
/*     */   {
/* 241 */     return this.srsDimension;
/*     */   }
/*     */ 
/*     */   public void setSrsDimension(BigInteger value)
/*     */   {
/* 253 */     this.srsDimension = value;
/*     */   }
/*     */ 
/*     */   public List<String> getAxisLabels()
/*     */   {
/* 279 */     if (this.axisLabels == null) {
/* 280 */       this.axisLabels = new ArrayList();
/*     */     }
/* 282 */     return this.axisLabels;
/*     */   }
/*     */ 
/*     */   public List<String> getUomLabels()
/*     */   {
/* 308 */     if (this.uomLabels == null) {
/* 309 */       this.uomLabels = new ArrayList();
/*     */     }
/* 311 */     return this.uomLabels;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.EnvelopeType
 * JD-Core Version:    0.6.0
 */