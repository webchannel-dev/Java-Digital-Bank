/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="PropertyIsLikeType", propOrder={"propertyName", "literal"})
/*     */ public class PropertyIsLikeType extends ComparisonOpsType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="PropertyName", required=true)
/*     */   protected PropertyNameType propertyName;
/*     */ 
/*     */   @XmlElement(name="Literal", required=true)
/*     */   protected LiteralType literal;
/*     */ 
/*     */   @XmlAttribute(name="wildCard", required=true)
/*     */   protected String wildCard;
/*     */ 
/*     */   @XmlAttribute(name="singleChar", required=true)
/*     */   protected String singleChar;
/*     */ 
/*     */   @XmlAttribute(name="escapeChar", required=true)
/*     */   protected String escapeChar;
/*     */ 
/*     */   public PropertyNameType getPropertyName()
/*     */   {
/*  70 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   public void setPropertyName(PropertyNameType value)
/*     */   {
/*  82 */     this.propertyName = value;
/*     */   }
/*     */ 
/*     */   public LiteralType getLiteral()
/*     */   {
/*  94 */     return this.literal;
/*     */   }
/*     */ 
/*     */   public void setLiteral(LiteralType value)
/*     */   {
/* 106 */     this.literal = value;
/*     */   }
/*     */ 
/*     */   public String getWildCard()
/*     */   {
/* 118 */     return this.wildCard;
/*     */   }
/*     */ 
/*     */   public void setWildCard(String value)
/*     */   {
/* 130 */     this.wildCard = value;
/*     */   }
/*     */ 
/*     */   public String getSingleChar()
/*     */   {
/* 142 */     return this.singleChar;
/*     */   }
/*     */ 
/*     */   public void setSingleChar(String value)
/*     */   {
/* 154 */     this.singleChar = value;
/*     */   }
/*     */ 
/*     */   public String getEscapeChar()
/*     */   {
/* 166 */     return this.escapeChar;
/*     */   }
/*     */ 
/*     */   public void setEscapeChar(String value)
/*     */   {
/* 178 */     this.escapeChar = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.PropertyIsLikeType
 * JD-Core Version:    0.6.0
 */