/*     */ package net.opengis.gml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="PointType", propOrder={"pos", "coordinates", "coord"})
/*     */ public class PointType extends AbstractGeometricPrimitiveType
/*     */ {
/*     */   protected DirectPositionType pos;
/*     */   protected CoordinatesType coordinates;
/*     */   protected CoordType coord;
/*     */ 
/*     */   public DirectPositionType getPos()
/*     */   {
/*  64 */     return this.pos;
/*     */   }
/*     */ 
/*     */   public void setPos(DirectPositionType value)
/*     */   {
/*  76 */     this.pos = value;
/*     */   }
/*     */ 
/*     */   public CoordinatesType getCoordinates()
/*     */   {
/*  90 */     return this.coordinates;
/*     */   }
/*     */ 
/*     */   public void setCoordinates(CoordinatesType value)
/*     */   {
/* 102 */     this.coordinates = value;
/*     */   }
/*     */ 
/*     */   public CoordType getCoord()
/*     */   {
/* 115 */     return this.coord;
/*     */   }
/*     */ 
/*     */   public void setCoord(CoordType value)
/*     */   {
/* 127 */     this.coord = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.PointType
 * JD-Core Version:    0.6.0
 */