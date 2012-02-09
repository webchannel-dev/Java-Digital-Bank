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
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="BSplineType", propOrder={"posOrPointPropertyOrPointRep", "posList", "coordinates", "degree", "knot"})
/*     */ @XmlSeeAlso({BezierType.class})
/*     */ public class BSplineType extends AbstractCurveSegmentType
/*     */ {
/*     */ 
/*     */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="pointProperty", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pointRep", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pos", namespace="http://www.opengis.net/gml", type=JAXBElement.class)})
/*     */   protected List<JAXBElement<?>> posOrPointPropertyOrPointRep;
/*     */   protected DirectPositionListType posList;
/*     */   protected CoordinatesType coordinates;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   @XmlSchemaType(name="nonNegativeInteger")
/*     */   protected BigInteger degree;
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected List<KnotPropertyType> knot;
/*     */ 
/*     */   @XmlAttribute(name="interpolation")
/*     */   protected CurveInterpolationType interpolation;
/*     */ 
/*     */   @XmlAttribute(name="isPolynomial")
/*     */   protected Boolean isPolynomial;
/*     */ 
/*     */   @XmlAttribute(name="knotType")
/*     */   protected KnotTypesType knotType;
/*     */ 
/*     */   public List<JAXBElement<?>> getPosOrPointPropertyOrPointRep()
/*     */   {
/* 120 */     if (this.posOrPointPropertyOrPointRep == null) {
/* 121 */       this.posOrPointPropertyOrPointRep = new ArrayList();
/*     */     }
/* 123 */     return this.posOrPointPropertyOrPointRep;
/*     */   }
/*     */ 
/*     */   public DirectPositionListType getPosList()
/*     */   {
/* 135 */     return this.posList;
/*     */   }
/*     */ 
/*     */   public void setPosList(DirectPositionListType value)
/*     */   {
/* 147 */     this.posList = value;
/*     */   }
/*     */ 
/*     */   public CoordinatesType getCoordinates()
/*     */   {
/* 159 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */   public void setCoordinates(CoordinatesType value)
/*     */   {
/* 171 */     this.coordinates = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getDegree()
/*     */   {
/* 183 */     return this.degree;
/*     */   }
/*     */ 
/*     */   public void setDegree(BigInteger value)
/*     */   {
/* 195 */     this.degree = value;
/*     */   }
/*     */ 
/*     */   public List<KnotPropertyType> getKnot()
/*     */   {
/* 221 */     if (this.knot == null) {
/* 222 */       this.knot = new ArrayList();
/*     */     }
/* 224 */     return this.knot;
/*     */   }
/*     */ 
/*     */   public CurveInterpolationType getInterpolation()
/*     */   {
/* 236 */     if (this.interpolation == null) {
/* 237 */       return CurveInterpolationType.POLYNOMIAL_SPLINE;
/*     */     }
/* 239 */     return this.interpolation;
/*     */   }
/*     */ 
/*     */   public void setInterpolation(CurveInterpolationType value)
/*     */   {
/* 252 */     this.interpolation = value;
/*     */   }
/*     */ 
/*     */   public Boolean isIsPolynomial()
/*     */   {
/* 264 */     return this.isPolynomial;
/*     */   }
/*     */ 
/*     */   public void setIsPolynomial(Boolean value)
/*     */   {
/* 276 */     this.isPolynomial = value;
/*     */   }
/*     */ 
/*     */   public KnotTypesType getKnotType()
/*     */   {
/* 288 */     return this.knotType;
/*     */   }
/*     */ 
/*     */   public void setKnotType(KnotTypesType value)
/*     */   {
/* 300 */     this.knotType = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.BSplineType
 * JD-Core Version:    0.6.0
 */