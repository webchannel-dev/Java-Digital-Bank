/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="OffsetCurveType", propOrder={"offsetBase", "distance", "refDirection"})
/*     */ public class OffsetCurveType extends AbstractCurveSegmentType
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected CurvePropertyType offsetBase;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected LengthType distance;
/*     */   protected VectorType refDirection;
/*     */ 
/*     */   public CurvePropertyType getOffsetBase()
/*     */   {
/*  68 */     return this.offsetBase;
/*     */   }
/*     */ 
/*     */   public void setOffsetBase(CurvePropertyType value)
/*     */   {
/*  80 */     this.offsetBase = value;
/*     */   }
/*     */ 
/*     */   public LengthType getDistance()
/*     */   {
/*  92 */     return this.distance;
/*     */   }
/*     */ 
/*     */   public void setDistance(LengthType value)
/*     */   {
/* 104 */     this.distance = value;
/*     */   }
/*     */ 
/*     */   public VectorType getRefDirection()
/*     */   {
/* 116 */     return this.refDirection;
/*     */   }
/*     */ 
/*     */   public void setRefDirection(VectorType value)
/*     */   {
/* 128 */     this.refDirection = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.OffsetCurveType
 * JD-Core Version:    0.6.0
 */