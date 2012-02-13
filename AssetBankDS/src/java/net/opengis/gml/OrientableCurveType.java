/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="OrientableCurveType", propOrder={"baseCurve"})
/*     */ public class OrientableCurveType extends AbstractCurveType
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected CurvePropertyType baseCurve;
/*     */ 
/*     */   @XmlAttribute(name="orientation")
/*     */   protected String orientation;
/*     */ 
/*     */   public CurvePropertyType getBaseCurve()
/*     */   {
/*  63 */     return this.baseCurve;
/*     */   }
/*     */ 
/*     */   public void setBaseCurve(CurvePropertyType value)
/*     */   {
/*  75 */     this.baseCurve = value;
/*     */   }
/*     */ 
/*     */   public String getOrientation()
/*     */   {
/*  87 */     if (this.orientation == null) {
/*  88 */       return "+";
/*     */     }
/*  90 */     return this.orientation;
/*     */   }
/*     */ 
/*     */   public void setOrientation(String value)
/*     */   {
/* 103 */     this.orientation = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.OrientableCurveType
 * JD-Core Version:    0.6.0
 */