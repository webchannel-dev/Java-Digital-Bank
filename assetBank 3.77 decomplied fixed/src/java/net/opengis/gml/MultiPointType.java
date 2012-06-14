/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="MultiPointType", propOrder={"pointMember", "pointMembers"})
/*     */ public class MultiPointType extends AbstractGeometricAggregateType
/*     */ {
/*     */   protected List<PointPropertyType> pointMember;
/*     */   protected PointArrayPropertyType pointMembers;
/*     */ 
/*     */   public List<PointPropertyType> getPointMember()
/*     */   {
/*  75 */     if (this.pointMember == null) {
/*  76 */       this.pointMember = new ArrayList();
/*     */     }
/*  78 */     return this.pointMember;
/*     */   }
/*     */ 
/*     */   public PointArrayPropertyType getPointMembers()
/*     */   {
/*  90 */     return this.pointMembers;
/*     */   }
/*     */ 
/*     */   public void setPointMembers(PointArrayPropertyType value)
/*     */   {
/* 102 */     this.pointMembers = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MultiPointType
 * JD-Core Version:    0.6.0
 */