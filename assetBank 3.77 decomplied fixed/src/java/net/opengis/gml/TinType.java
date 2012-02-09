/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="TinType", propOrder={"stopLines", "breakLines", "maxLength", "controlPoint"})
/*     */ public class TinType extends TriangulatedSurfaceType
/*     */ {
/*     */   protected List<LineStringSegmentArrayPropertyType> stopLines;
/*     */   protected List<LineStringSegmentArrayPropertyType> breakLines;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected LengthType maxLength;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected ControlPoint controlPoint;
/*     */ 
/*     */   public List<LineStringSegmentArrayPropertyType> getStopLines()
/*     */   {
/* 102 */     if (this.stopLines == null) {
/* 103 */       this.stopLines = new ArrayList();
/*     */     }
/* 105 */     return this.stopLines;
/*     */   }
/*     */ 
/*     */   public List<LineStringSegmentArrayPropertyType> getBreakLines()
/*     */   {
/* 131 */     if (this.breakLines == null) {
/* 132 */       this.breakLines = new ArrayList();
/*     */     }
/* 134 */     return this.breakLines;
/*     */   }
/*     */ 
/*     */   public LengthType getMaxLength()
/*     */   {
/* 146 */     return this.maxLength;
/*     */   }
/*     */ 
/*     */   public void setMaxLength(LengthType value)
/*     */   {
/* 158 */     this.maxLength = value;
/*     */   }
/*     */ 
/*     */   public ControlPoint getControlPoint()
/*     */   {
/* 170 */     return this.controlPoint;
/*     */   }
/*     */ 
/*     */   public void setControlPoint(ControlPoint value)
/*     */   {
/* 182 */     this.controlPoint = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.TinType
 * JD-Core Version:    0.6.0
 */