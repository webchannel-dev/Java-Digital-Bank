/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="MultiCurveType", propOrder={"curveMember", "curveMembers"})
/*     */ public class MultiCurveType extends AbstractGeometricAggregateType
/*     */ {
/*     */   protected List<CurvePropertyType> curveMember;
/*     */   protected CurveArrayPropertyType curveMembers;
/*     */ 
/*     */   public List<CurvePropertyType> getCurveMember()
/*     */   {
/*  75 */     if (this.curveMember == null) {
/*  76 */       this.curveMember = new ArrayList();
/*     */     }
/*  78 */     return this.curveMember;
/*     */   }
/*     */ 
/*     */   public CurveArrayPropertyType getCurveMembers()
/*     */   {
/*  90 */     return this.curveMembers;
/*     */   }
/*     */ 
/*     */   public void setCurveMembers(CurveArrayPropertyType value)
/*     */   {
/* 102 */     this.curveMembers = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MultiCurveType
 * JD-Core Version:    0.6.0
 */