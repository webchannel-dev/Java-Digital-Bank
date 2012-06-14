/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="DegreesType", propOrder={"value"})
/*     */ public class DegreesType
/*     */ {
/*     */ 
/*     */   @XmlValue
/*     */   protected int value;
/*     */ 
/*     */   @XmlAttribute(name="direction")
/*     */   protected String direction;
/*     */ 
/*     */   public int getValue()
/*     */   {
/*  70 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(int value)
/*     */   {
/*  78 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public String getDirection()
/*     */   {
/*  90 */     return this.direction;
/*     */   }
/*     */ 
/*     */   public void setDirection(String value)
/*     */   {
/* 102 */     this.direction = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.DegreesType
 * JD-Core Version:    0.6.0
 */