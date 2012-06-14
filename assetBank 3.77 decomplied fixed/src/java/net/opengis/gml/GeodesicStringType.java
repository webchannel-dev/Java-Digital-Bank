/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElements;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="GeodesicStringType", propOrder={"posList", "geometricPositionGroup"})
/*     */ @XmlSeeAlso({GeodesicType.class})
/*     */ public class GeodesicStringType extends AbstractCurveSegmentType
/*     */ {
/*     */   protected DirectPositionListType posList;
/*     */ 
/*     */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="pos", type=DirectPositionType.class), @javax.xml.bind.annotation.XmlElement(name="pointProperty", type=PointPropertyType.class)})
/*     */   protected List<Object> geometricPositionGroup;
/*     */ 
/*     */   @XmlAttribute(name="interpolation")
/*     */   protected CurveInterpolationType interpolation;
/*     */ 
/*     */   public DirectPositionListType getPosList()
/*     */   {
/*  80 */     return this.posList;
/*     */   }
/*     */ 
/*     */   public void setPosList(DirectPositionListType value)
/*     */   {
/*  92 */     this.posList = value;
/*     */   }
/*     */ 
/*     */   public List<Object> getGeometricPositionGroup()
/*     */   {
/* 119 */     if (this.geometricPositionGroup == null) {
/* 120 */       this.geometricPositionGroup = new ArrayList();
/*     */     }
/* 122 */     return this.geometricPositionGroup;
/*     */   }
/*     */ 
/*     */   public CurveInterpolationType getInterpolation()
/*     */   {
/* 134 */     if (this.interpolation == null) {
/* 135 */       return CurveInterpolationType.GEODESIC;
/*     */     }
/* 137 */     return this.interpolation;
/*     */   }
/*     */ 
/*     */   public void setInterpolation(CurveInterpolationType value)
/*     */   {
/* 150 */     this.interpolation = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.GeodesicStringType
 * JD-Core Version:    0.6.0
 */