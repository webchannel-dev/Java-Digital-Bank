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
/*     */ @XmlType(name="LineStringType", propOrder={"posOrPointPropertyOrPointRep", "posList", "coordinates"})
/*     */ public class LineStringType extends AbstractCurveType
/*     */ {
/*     */ 
/*     */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="pointProperty", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pointRep", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="coord", namespace="http://www.opengis.net/gml", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="pos", namespace="http://www.opengis.net/gml", type=JAXBElement.class)})
/*     */   protected List<JAXBElement<?>> posOrPointPropertyOrPointRep;
/*     */   protected DirectPositionListType posList;
/*     */   protected CoordinatesType coordinates;
/*     */ 
/*     */   public List<JAXBElement<?>> getPosOrPointPropertyOrPointRep()
/*     */   {
/*  99 */     if (this.posOrPointPropertyOrPointRep == null) {
/* 100 */       this.posOrPointPropertyOrPointRep = new ArrayList();
/*     */     }
/* 102 */     return this.posOrPointPropertyOrPointRep;
/*     */   }
/*     */ 
/*     */   public DirectPositionListType getPosList()
/*     */   {
/* 114 */     return this.posList;
/*     */   }
/*     */ 
/*     */   public void setPosList(DirectPositionListType value)
/*     */   {
/* 126 */     this.posList = value;
/*     */   }
/*     */ 
/*     */   public CoordinatesType getCoordinates()
/*     */   {
/* 138 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */   public void setCoordinates(CoordinatesType value)
/*     */   {
/* 150 */     this.coordinates = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.LineStringType
 * JD-Core Version:    0.6.0
 */