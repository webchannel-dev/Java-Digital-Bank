/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="UnaryLogicOpType", propOrder={"comparisonOps", "spatialOps", "logicOps"})
/*     */ public class UnaryLogicOpType extends LogicOpsType
/*     */ {
/*     */ 
/*     */   @XmlElementRef(name="comparisonOps", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends ComparisonOpsType> comparisonOps;
/*     */ 
/*     */   @XmlElementRef(name="spatialOps", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends SpatialOpsType> spatialOps;
/*     */ 
/*     */   @XmlElementRef(name="logicOps", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)
/*     */   protected JAXBElement<? extends LogicOpsType> logicOps;
/*     */ 
/*     */   public JAXBElement<? extends ComparisonOpsType> getComparisonOps()
/*     */   {
/*  76 */     return this.comparisonOps;
/*     */   }
/*     */ 
/*     */   public void setComparisonOps(JAXBElement<? extends ComparisonOpsType> value)
/*     */   {
/*  97 */     this.comparisonOps = value;
/*     */   }
/*     */ 
/*     */   public JAXBElement<? extends SpatialOpsType> getSpatialOps()
/*     */   {
/* 120 */     return this.spatialOps;
/*     */   }
/*     */ 
/*     */   public void setSpatialOps(JAXBElement<? extends SpatialOpsType> value)
/*     */   {
/* 143 */     this.spatialOps = value;
/*     */   }
/*     */ 
/*     */   public JAXBElement<? extends LogicOpsType> getLogicOps()
/*     */   {
/* 158 */     return this.logicOps;
/*     */   }
/*     */ 
/*     */   public void setLogicOps(JAXBElement<? extends LogicOpsType> value)
/*     */   {
/* 173 */     this.logicOps = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.UnaryLogicOpType
 * JD-Core Version:    0.6.0
 */