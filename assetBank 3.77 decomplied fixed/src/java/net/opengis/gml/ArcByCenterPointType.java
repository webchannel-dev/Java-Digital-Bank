/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ArcByCenterPointType", propOrder={"pos", "pointProperty", "pointRep", "posList", "coordinates", "radius", "startAngle", "endAngle"})
/*     */ @XmlSeeAlso({CircleByCenterPointType.class})
/*     */ public class ArcByCenterPointType extends AbstractCurveSegmentType
/*     */ {
/*     */   protected DirectPositionType pos;
/*     */   protected PointPropertyType pointProperty;
/*     */   protected PointPropertyType pointRep;
/*     */   protected DirectPositionListType posList;
/*     */   protected CoordinatesType coordinates;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected LengthType radius;
/*     */   protected AngleType startAngle;
/*     */   protected AngleType endAngle;
/*     */ 
/*     */   @XmlAttribute(name="interpolation")
/*     */   protected CurveInterpolationType interpolation;
/*     */ 
/*     */   @XmlAttribute(name="numArc", required=true)
/*     */   protected BigInteger numArc;
/*     */ 
/*     */   public DirectPositionType getPos()
/*     */   {
/*  95 */     return this.pos;
/*     */   }
/*     */ 
/*     */   public void setPos(DirectPositionType value)
/*     */   {
/* 107 */     this.pos = value;
/*     */   }
/*     */ 
/*     */   public PointPropertyType getPointProperty()
/*     */   {
/* 119 */     return this.pointProperty;
/*     */   }
/*     */ 
/*     */   public void setPointProperty(PointPropertyType value)
/*     */   {
/* 131 */     this.pointProperty = value;
/*     */   }
/*     */ 
/*     */   public PointPropertyType getPointRep()
/*     */   {
/* 143 */     return this.pointRep;
/*     */   }
/*     */ 
/*     */   public void setPointRep(PointPropertyType value)
/*     */   {
/* 155 */     this.pointRep = value;
/*     */   }
/*     */ 
/*     */   public DirectPositionListType getPosList()
/*     */   {
/* 167 */     return this.posList;
/*     */   }
/*     */ 
/*     */   public void setPosList(DirectPositionListType value)
/*     */   {
/* 179 */     this.posList = value;
/*     */   }
/*     */ 
/*     */   public CoordinatesType getCoordinates()
/*     */   {
/* 191 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */   public void setCoordinates(CoordinatesType value)
/*     */   {
/* 203 */     this.coordinates = value;
/*     */   }
/*     */ 
/*     */   public LengthType getRadius()
/*     */   {
/* 215 */     return this.radius;
/*     */   }
/*     */ 
/*     */   public void setRadius(LengthType value)
/*     */   {
/* 227 */     this.radius = value;
/*     */   }
/*     */ 
/*     */   public AngleType getStartAngle()
/*     */   {
/* 239 */     return this.startAngle;
/*     */   }
/*     */ 
/*     */   public void setStartAngle(AngleType value)
/*     */   {
/* 251 */     this.startAngle = value;
/*     */   }
/*     */ 
/*     */   public AngleType getEndAngle()
/*     */   {
/* 263 */     return this.endAngle;
/*     */   }
/*     */ 
/*     */   public void setEndAngle(AngleType value)
/*     */   {
/* 275 */     this.endAngle = value;
/*     */   }
/*     */ 
/*     */   public CurveInterpolationType getInterpolation()
/*     */   {
/* 287 */     if (this.interpolation == null) {
/* 288 */       return CurveInterpolationType.CIRCULAR_ARC_CENTER_POINT_WITH_RADIUS;
/*     */     }
/* 290 */     return this.interpolation;
/*     */   }
/*     */ 
/*     */   public void setInterpolation(CurveInterpolationType value)
/*     */   {
/* 303 */     this.interpolation = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getNumArc()
/*     */   {
/* 315 */     if (this.numArc == null) {
/* 316 */       return new BigInteger("1");
/*     */     }
/* 318 */     return this.numArc;
/*     */   }
/*     */ 
/*     */   public void setNumArc(BigInteger value)
/*     */   {
/* 331 */     this.numArc = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ArcByCenterPointType
 * JD-Core Version:    0.6.0
 */