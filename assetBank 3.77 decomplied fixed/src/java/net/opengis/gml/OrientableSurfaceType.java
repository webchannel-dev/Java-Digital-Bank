/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="OrientableSurfaceType", propOrder={"baseSurface"})
/*     */ public class OrientableSurfaceType extends AbstractSurfaceType
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected SurfacePropertyType baseSurface;
/*     */ 
/*     */   @XmlAttribute(name="orientation")
/*     */   protected String orientation;
/*     */ 
/*     */   public SurfacePropertyType getBaseSurface()
/*     */   {
/*  62 */     return this.baseSurface;
/*     */   }
/*     */ 
/*     */   public void setBaseSurface(SurfacePropertyType value)
/*     */   {
/*  74 */     this.baseSurface = value;
/*     */   }
/*     */ 
/*     */   public String getOrientation()
/*     */   {
/*  86 */     if (this.orientation == null) {
/*  87 */       return "+";
/*     */     }
/*  89 */     return this.orientation;
/*     */   }
/*     */ 
/*     */   public void setOrientation(String value)
/*     */   {
/* 102 */     this.orientation = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.OrientableSurfaceType
 * JD-Core Version:    0.6.0
 */