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
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="CubicSplineType", propOrder={"posOrPointPropertyOrPointRep", "posList", "coordinates", "vectorAtStart", "vectorAtEnd"})
/*     */ public class CubicSplineType extends AbstractCurveSegmentType
/*     */ {
/*     */ 
/*     */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="pointProperty", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pointRep", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pos", namespace="http://www.opengis.net/gml", type=JAXBElement.class)})
/*     */   protected List<JAXBElement<?>> posOrPointPropertyOrPointRep;
/*     */   protected DirectPositionListType posList;
/*     */   protected CoordinatesType coordinates;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected VectorType vectorAtStart;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected VectorType vectorAtEnd;
/*     */ 
/*     */   @XmlAttribute(name="interpolation")
/*     */   protected CurveInterpolationType interpolation;
/*     */ 
/*     */   @XmlAttribute(name="degree")
/*     */   protected BigInteger degree;
/*     */ 
/*     */   public List<JAXBElement<?>> getPosOrPointPropertyOrPointRep()
/*     */   {
/* 113 */     if (this.posOrPointPropertyOrPointRep == null) {
/* 114 */       this.posOrPointPropertyOrPointRep = new ArrayList();
/*     */     }
/* 116 */     return this.posOrPointPropertyOrPointRep;
/*     */   }
/*     */ 
/*     */   public DirectPositionListType getPosList()
/*     */   {
/* 128 */     return this.posList;
/*     */   }
/*     */ 
/*     */   public void setPosList(DirectPositionListType value)
/*     */   {
/* 140 */     this.posList = value;
/*     */   }
/*     */ 
/*     */   public CoordinatesType getCoordinates()
/*     */   {
/* 152 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */   public void setCoordinates(CoordinatesType value)
/*     */   {
/* 164 */     this.coordinates = value;
/*     */   }
/*     */ 
/*     */   public VectorType getVectorAtStart()
/*     */   {
/* 176 */     return this.vectorAtStart;
/*     */   }
/*     */ 
/*     */   public void setVectorAtStart(VectorType value)
/*     */   {
/* 188 */     this.vectorAtStart = value;
/*     */   }
/*     */ 
/*     */   public VectorType getVectorAtEnd()
/*     */   {
/* 200 */     return this.vectorAtEnd;
/*     */   }
/*     */ 
/*     */   public void setVectorAtEnd(VectorType value)
/*     */   {
/* 212 */     this.vectorAtEnd = value;
/*     */   }
/*     */ 
/*     */   public CurveInterpolationType getInterpolation()
/*     */   {
/* 224 */     if (this.interpolation == null) {
/* 225 */       return CurveInterpolationType.CUBIC_SPLINE;
/*     */     }
/* 227 */     return this.interpolation;
/*     */   }
/*     */ 
/*     */   public void setInterpolation(CurveInterpolationType value)
/*     */   {
/* 240 */     this.interpolation = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getDegree()
/*     */   {
/* 252 */     if (this.degree == null) {
/* 253 */       return new BigInteger("3");
/*     */     }
/* 255 */     return this.degree;
/*     */   }
/*     */ 
/*     */   public void setDegree(BigInteger value)
/*     */   {
/* 268 */     this.degree = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.CubicSplineType
 * JD-Core Version:    0.6.0
 */