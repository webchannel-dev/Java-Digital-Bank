/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="DMSAngleType", propOrder={"degrees", "decimalMinutes", "minutes", "seconds"})
/*     */ public class DMSAngleType
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected DegreesType degrees;
/*     */   protected BigDecimal decimalMinutes;
/*     */   protected Integer minutes;
/*     */   protected BigDecimal seconds;
/*     */ 
/*     */   public DegreesType getDegrees()
/*     */   {
/*  70 */     return this.degrees;
/*     */   }
/*     */ 
/*     */   public void setDegrees(DegreesType value)
/*     */   {
/*  82 */     this.degrees = value;
/*     */   }
/*     */ 
/*     */   public BigDecimal getDecimalMinutes()
/*     */   {
/*  94 */     return this.decimalMinutes;
/*     */   }
/*     */ 
/*     */   public void setDecimalMinutes(BigDecimal value)
/*     */   {
/* 106 */     this.decimalMinutes = value;
/*     */   }
/*     */ 
/*     */   public Integer getMinutes()
/*     */   {
/* 118 */     return this.minutes;
/*     */   }
/*     */ 
/*     */   public void setMinutes(Integer value)
/*     */   {
/* 130 */     this.minutes = value;
/*     */   }
/*     */ 
/*     */   public BigDecimal getSeconds()
/*     */   {
/* 142 */     return this.seconds;
/*     */   }
/*     */ 
/*     */   public void setSeconds(BigDecimal value)
/*     */   {
/* 154 */     this.seconds = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.DMSAngleType
 * JD-Core Version:    0.6.0
 */