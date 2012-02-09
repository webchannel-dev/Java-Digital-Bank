/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElementRefs;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="LinearRingType", propOrder={"posOrPointPropertyOrPointRep", "posList", "coordinates", "coord"})
/*     */ public class LinearRingType extends AbstractRingType
/*     */ {
/*     */ 
/*     */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="pointProperty", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pointRep", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pos", namespace="http://www.opengis.net/gml", type=JAXBElement.class)})
/*     */   protected List<JAXBElement<?>> posOrPointPropertyOrPointRep;
/*     */   protected DirectPositionListType posList;
/*     */   protected CoordinatesType coordinates;
/*     */   protected List<CoordType> coord;
/*     */ 
/*     */   public List<JAXBElement<?>> getPosOrPointPropertyOrPointRep()
/*     */   {
/*  97 */     if (this.posOrPointPropertyOrPointRep == null) {
/*  98 */       this.posOrPointPropertyOrPointRep = new ArrayList();
/*     */     }
/* 100 */     return this.posOrPointPropertyOrPointRep;
/*     */   }
/*     */ 
/*     */   public DirectPositionListType getPosList()
/*     */   {
/* 112 */     return this.posList;
/*     */   }
/*     */ 
/*     */   public void setPosList(DirectPositionListType value)
/*     */   {
/* 124 */     this.posList = value;
/*     */   }
/*     */ 
/*     */   public CoordinatesType getCoordinates()
/*     */   {
/* 136 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */   public void setCoordinates(CoordinatesType value)
/*     */   {
/* 148 */     this.coordinates = value;
/*     */   }
/*     */ 
/*     */   public List<CoordType> getCoord()
/*     */   {
/* 174 */     if (this.coord == null) {
/* 175 */       this.coord = new ArrayList();
/*     */     }
/* 177 */     return this.coord;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.LinearRingType
 * JD-Core Version:    0.6.0
 */