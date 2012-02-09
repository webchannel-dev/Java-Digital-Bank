/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="SolidType", propOrder={"exterior", "interior"})
/*     */ public class SolidType extends AbstractSolidType
/*     */ {
/*     */   protected SurfacePropertyType exterior;
/*     */   protected List<SurfacePropertyType> interior;
/*     */ 
/*     */   public SurfacePropertyType getExterior()
/*     */   {
/*  61 */     return this.exterior;
/*     */   }
/*     */ 
/*     */   public void setExterior(SurfacePropertyType value)
/*     */   {
/*  73 */     this.exterior = value;
/*     */   }
/*     */ 
/*     */   public List<SurfacePropertyType> getInterior()
/*     */   {
/*  99 */     if (this.interior == null) {
/* 100 */       this.interior = new ArrayList();
/*     */     }
/* 102 */     return this.interior;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.SolidType
 * JD-Core Version:    0.6.0
 */