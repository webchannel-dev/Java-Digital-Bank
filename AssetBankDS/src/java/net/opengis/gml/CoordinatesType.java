/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="CoordinatesType", propOrder={"value"})
/*     */ public class CoordinatesType
/*     */ {
/*     */ 
/*     */   @XmlValue
/*     */   protected String value;
/*     */ 
/*     */   @XmlAttribute(name="decimal")
/*     */   protected String decimal;
/*     */ 
/*     */   @XmlAttribute(name="cs")
/*     */   protected String cs;
/*     */ 
/*     */   @XmlAttribute(name="ts")
/*     */   protected String ts;
/*     */ 
/*     */   public String getValue()
/*     */   {
/*  70 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/*  82 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public String getDecimal()
/*     */   {
/*  94 */     if (this.decimal == null) {
/*  95 */       return ".";
/*     */     }
/*  97 */     return this.decimal;
/*     */   }
/*     */ 
/*     */   public void setDecimal(String value)
/*     */   {
/* 110 */     this.decimal = value;
/*     */   }
/*     */ 
/*     */   public String getCs()
/*     */   {
/* 122 */     if (this.cs == null) {
/* 123 */       return ",";
/*     */     }
/* 125 */     return this.cs;
/*     */   }
/*     */ 
/*     */   public void setCs(String value)
/*     */   {
/* 138 */     this.cs = value;
/*     */   }
/*     */ 
/*     */   public String getTs()
/*     */   {
/* 150 */     if (this.ts == null) {
/* 151 */       return " ";
/*     */     }
/* 153 */     return this.ts;
/*     */   }
/*     */ 
/*     */   public void setTs(String value)
/*     */   {
/* 166 */     this.ts = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CoordinatesType
 * JD-Core Version:    0.6.0
 */