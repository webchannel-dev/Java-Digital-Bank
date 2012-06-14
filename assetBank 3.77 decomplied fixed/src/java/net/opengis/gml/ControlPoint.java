/*     */ package net.opengis.gml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElements;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)

/*     */ @XmlType(name="", propOrder={"posList", "geometricPositionGroup"})

/*     */ public class ControlPoint
/*     */ {
/*     */   protected DirectPositionListType posList;
/*     */ 
/*     */   @XmlElements({@javax.xml.bind.annotation.XmlElement(name="pointProperty", type=PointPropertyType.class), @javax.xml.bind.annotation.XmlElement(name="pos", type=DirectPositionType.class)})
/*     */   protected List<Object> geometricPositionGroup;
/*     */ 
/*     */   public DirectPositionListType getPosList()
/*     */   {
/* 229 */     return this.posList;
/*     */   }
/*     */ 
/*     */   public void setPosList(DirectPositionListType value)
/*     */   {
/* 241 */     this.posList = value;
/*     */   }
/*     */ 
/*     */   public List<Object> getGeometricPositionGroup()
/*     */   {
/* 268 */     if (this.geometricPositionGroup == null) {
/* 269 */       this.geometricPositionGroup = new ArrayList();
/*     */     }
/* 271 */     return this.geometricPositionGroup;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.TinType.ControlPoint
 * JD-Core Version:    0.6.0
 */