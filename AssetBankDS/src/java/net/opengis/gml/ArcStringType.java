/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElementRefs;
/*     */ import javax.xml.bind.annotation.XmlSeeAlso;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ArcStringType", propOrder={"posOrPointPropertyOrPointRep", "posList", "coordinates"})
/*     */ @XmlSeeAlso({ArcType.class})
/*     */ public class ArcStringType extends AbstractCurveSegmentType
/*     */ {
/*     */ 
/*     */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="pointProperty", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pointRep", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pos", namespace="http://www.opengis.net/gml", type=JAXBElement.class)})
/*     */   protected List<JAXBElement<?>> posOrPointPropertyOrPointRep;
/*     */   protected DirectPositionListType posList;
/*     */   protected CoordinatesType coordinates;
/*     */ 
/*     */   @XmlAttribute(name="interpolation")
/*     */   protected CurveInterpolationType interpolation;
/*     */ 
/*     */   @XmlAttribute(name="numArc")
/*     */   protected BigInteger numArc;
/*     */ 
/*     */   public List<JAXBElement<?>> getPosOrPointPropertyOrPointRep()
/*     */   {
/* 106 */     if (this.posOrPointPropertyOrPointRep == null) {
/* 107 */       this.posOrPointPropertyOrPointRep = new ArrayList();
/*     */     }
/* 109 */     return this.posOrPointPropertyOrPointRep;
/*     */   }
/*     */ 
/*     */   public DirectPositionListType getPosList()
/*     */   {
/* 121 */     return this.posList;
/*     */   }
/*     */ 
/*     */   public void setPosList(DirectPositionListType value)
/*     */   {
/* 133 */     this.posList = value;
/*     */   }
/*     */ 
/*     */   public CoordinatesType getCoordinates()
/*     */   {
/* 145 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */   public void setCoordinates(CoordinatesType value)
/*     */   {
/* 157 */     this.coordinates = value;
/*     */   }
/*     */ 
/*     */   public CurveInterpolationType getInterpolation()
/*     */   {
/* 169 */     if (this.interpolation == null) {
/* 170 */       return CurveInterpolationType.CIRCULAR_ARC_3_POINTS;
/*     */     }
/* 172 */     return this.interpolation;
/*     */   }
/*     */ 
/*     */   public void setInterpolation(CurveInterpolationType value)
/*     */   {
/* 185 */     this.interpolation = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getNumArc()
/*     */   {
/* 197 */     return this.numArc;
/*     */   }
/*     */ 
/*     */   public void setNumArc(BigInteger value)
/*     */   {
/* 209 */     this.numArc = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ArcStringType
 * JD-Core Version:    0.6.0
 */