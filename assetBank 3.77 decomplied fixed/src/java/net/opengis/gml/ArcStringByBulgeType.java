/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRefs;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ArcStringByBulgeType", propOrder={"posOrPointPropertyOrPointRep", "posList", "coordinates", "bulge", "normal"})
/*     */ @XmlSeeAlso({ArcByBulgeType.class})
/*     */ public class ArcStringByBulgeType extends AbstractCurveSegmentType
/*     */ {
/*     */ 
/*     */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="pointProperty", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pointRep", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pos", namespace="http://www.opengis.net/gml", type=JAXBElement.class)})
/*     */   protected List<JAXBElement<?>> posOrPointPropertyOrPointRep;
/*     */   protected DirectPositionListType posList;
/*     */   protected CoordinatesType coordinates;
/*     */ 
/*     */   @XmlElement(type=Double.class)
/*     */   protected List<Double> bulge;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected List<VectorType> normal;
/*     */ 
/*     */   @XmlAttribute(name="interpolation")
/*     */   protected CurveInterpolationType interpolation;
/*     */ 
/*     */   @XmlAttribute(name="numArc")
/*     */   protected BigInteger numArc;
/*     */ 
/*     */   public List<JAXBElement<?>> getPosOrPointPropertyOrPointRep()
/*     */   {
/* 115 */     if (this.posOrPointPropertyOrPointRep == null) {
/* 116 */       this.posOrPointPropertyOrPointRep = new ArrayList();
/*     */     }
/* 118 */     return this.posOrPointPropertyOrPointRep;
/*     */   }
/*     */ 
/*     */   public DirectPositionListType getPosList()
/*     */   {
/* 130 */     return this.posList;
/*     */   }
/*     */ 
/*     */   public void setPosList(DirectPositionListType value)
/*     */   {
/* 142 */     this.posList = value;
/*     */   }
/*     */ 
/*     */   public CoordinatesType getCoordinates()
/*     */   {
/* 154 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */   public void setCoordinates(CoordinatesType value)
/*     */   {
/* 166 */     this.coordinates = value;
/*     */   }
/*     */ 
/*     */   public List<Double> getBulge()
/*     */   {
/* 192 */     if (this.bulge == null) {
/* 193 */       this.bulge = new ArrayList();
/*     */     }
/* 195 */     return this.bulge;
/*     */   }
/*     */ 
/*     */   public List<VectorType> getNormal()
/*     */   {
/* 221 */     if (this.normal == null) {
/* 222 */       this.normal = new ArrayList();
/*     */     }
/* 224 */     return this.normal;
/*     */   }
/*     */ 
/*     */   public CurveInterpolationType getInterpolation()
/*     */   {
/* 236 */     if (this.interpolation == null) {
/* 237 */       return CurveInterpolationType.CIRCULAR_ARC_2_POINT_WITH_BULGE;
/*     */     }
/* 239 */     return this.interpolation;
/*     */   }
/*     */ 
/*     */   public void setInterpolation(CurveInterpolationType value)
/*     */   {
/* 252 */     this.interpolation = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getNumArc()
/*     */   {
/* 264 */     return this.numArc;
/*     */   }
/*     */ 
/*     */   public void setNumArc(BigInteger value)
/*     */   {
/* 276 */     this.numArc = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ArcStringByBulgeType
 * JD-Core Version:    0.6.0
 */