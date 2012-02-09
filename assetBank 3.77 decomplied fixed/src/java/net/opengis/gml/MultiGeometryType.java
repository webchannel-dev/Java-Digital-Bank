/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="MultiGeometryType", propOrder={"geometryMember", "geometryMembers"})
/*     */ public class MultiGeometryType extends AbstractGeometricAggregateType
/*     */ {
/*     */   protected List<GeometryPropertyType> geometryMember;
/*     */   protected GeometryArrayPropertyType geometryMembers;
/*     */ 
/*     */   public List<GeometryPropertyType> getGeometryMember()
/*     */   {
/*  75 */     if (this.geometryMember == null) {
/*  76 */       this.geometryMember = new ArrayList();
/*     */     }
/*  78 */     return this.geometryMember;
/*     */   }
/*     */ 
/*     */   public GeometryArrayPropertyType getGeometryMembers()
/*     */   {
/*  90 */     return this.geometryMembers;
/*     */   }
/*     */ 
/*     */   public void setGeometryMembers(GeometryArrayPropertyType value)
/*     */   {
/* 102 */     this.geometryMembers = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MultiGeometryType
 * JD-Core Version:    0.6.0
 */