/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="CoordType", propOrder={"x", "y", "z"})
/*     */ public class CoordType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="X", required=true)
/*     */   protected BigDecimal x;
/*     */ 
/*     */   @XmlElement(name="Y")
/*     */   protected BigDecimal y;
/*     */ 
/*     */   @XmlElement(name="Z")
/*     */   protected BigDecimal z;
/*     */ 
/*     */   public BigDecimal getX()
/*     */   {
/*  66 */     return this.x;
/*     */   }
/*     */ 
/*     */   public void setX(BigDecimal value)
/*     */   {
/*  78 */     this.x = value;
/*     */   }
/*     */ 
/*     */   public BigDecimal getY()
/*     */   {
/*  90 */     return this.y;
/*     */   }
/*     */ 
/*     */   public void setY(BigDecimal value)
/*     */   {
/* 102 */     this.y = value;
/*     */   }
/*     */ 
/*     */   public BigDecimal getZ()
/*     */   {
/* 114 */     return this.z;
/*     */   }
/*     */ 
/*     */   public void setZ(BigDecimal value)
/*     */   {
/* 126 */     this.z = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CoordType
 * JD-Core Version:    0.6.0
 */