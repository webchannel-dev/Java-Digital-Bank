/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ClothoidType", propOrder={"refLocation", "scaleFactor", "startParameter", "endParameter"})
/*     */ public class ClothoidType extends AbstractCurveSegmentType
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected RefLocation refLocation;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected BigDecimal scaleFactor;
/*     */   protected double startParameter;
/*     */   protected double endParameter;
/*     */ 
/*     */   public RefLocation getRefLocation()
/*     */   {
/*  95 */     return this.refLocation;
/*     */   }
/*     */ 
/*     */   public void setRefLocation(RefLocation value)
/*     */   {
/* 107 */     this.refLocation = value;
/*     */   }
/*     */ 
/*     */   public BigDecimal getScaleFactor()
/*     */   {
/* 119 */     return this.scaleFactor;
/*     */   }
/*     */ 
/*     */   public void setScaleFactor(BigDecimal value)
/*     */   {
/* 131 */     this.scaleFactor = value;
/*     */   }
/*     */ 
/*     */   public double getStartParameter()
/*     */   {
/* 139 */     return this.startParameter;
/*     */   }
/*     */ 
/*     */   public void setStartParameter(double value)
/*     */   {
/* 147 */     this.startParameter = value;
/*     */   }
/*     */ 
/*     */   public double getEndParameter()
/*     */   {
/* 155 */     return this.endParameter;
/*     */   }
/*     */ 
/*     */   public void setEndParameter(double value)
/*     */   {
/* 163 */     this.endParameter = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ClothoidType
 * JD-Core Version:    0.6.0
 */