/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="FilterType", propOrder={"spatialOps", "comparisonOps", "logicOps", "id"})
/*     */ public class FilterType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="spatialOps", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends SpatialOpsType> spatialOps;
/*     */ 
/*     */   @XmlElementRef(name="comparisonOps", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends ComparisonOpsType> comparisonOps;
/*     */ 
/*     */   @XmlElementRef(name="logicOps", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends LogicOpsType> logicOps;
/*     */ 
/*     */   @XmlElementRef(name="_Id", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected List<JAXBElement<? extends AbstractIdType>> id;
/*     */ 
/*     */   public JAXBElement<? extends SpatialOpsType> getSpatialOps()
/*     */   {
/*  80 */     return this.spatialOps;
/*     */   }
/*     */ 
/*     */   public void setSpatialOps(JAXBElement<? extends SpatialOpsType> value)
/*     */   {
/* 103 */     this.spatialOps = value;
/*     */   }
/*     */ 
/*     */   public JAXBElement<? extends ComparisonOpsType> getComparisonOps()
/*     */   {
/* 124 */     return this.comparisonOps;
/*     */   }
/*     */ 
/*     */   public void setComparisonOps(JAXBElement<? extends ComparisonOpsType> value)
/*     */   {
/* 145 */     this.comparisonOps = value;
/*     */   }
/*     */ 
/*     */   public JAXBElement<? extends LogicOpsType> getLogicOps()
/*     */   {
/* 160 */     return this.logicOps;
/*     */   }
/*     */ 
/*     */   public void setLogicOps(JAXBElement<? extends LogicOpsType> value)
/*     */   {
/* 175 */     this.logicOps = value;
/*     */   }
/*     */ 
/*     */   public List<JAXBElement<? extends AbstractIdType>> get_Id()
/*     */   {
/* 203 */     if (this.id == null) {
/* 204 */       this.id = new ArrayList();
/*     */     }
/* 206 */     return this.id;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.FilterType
 * JD-Core Version:    0.6.0
 */