/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="MultiSurfaceType", propOrder={"surfaceMember", "surfaceMembers"})
/*     */ public class MultiSurfaceType extends AbstractGeometricAggregateType
/*     */ {
/*     */   protected List<SurfacePropertyType> surfaceMember;
/*     */   protected SurfaceArrayPropertyType surfaceMembers;
/*     */ 
/*     */   public List<SurfacePropertyType> getSurfaceMember()
/*     */   {
/*  75 */     if (this.surfaceMember == null) {
/*  76 */       this.surfaceMember = new ArrayList();
/*     */     }
/*  78 */     return this.surfaceMember;
/*     */   }
/*     */ 
/*     */   public SurfaceArrayPropertyType getSurfaceMembers()
/*     */   {
/*  90 */     return this.surfaceMembers;
/*     */   }
/*     */ 
/*     */   public void setSurfaceMembers(SurfaceArrayPropertyType value)
/*     */   {
/* 102 */     this.surfaceMembers = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MultiSurfaceType
 * JD-Core Version:    0.6.0
 */