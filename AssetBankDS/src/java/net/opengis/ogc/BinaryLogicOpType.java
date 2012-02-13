/*     */ package net.opengis.ogc;
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
/*     */ @XmlType(name="BinaryLogicOpType", propOrder={"comparisonOpsOrSpatialOpsOrLogicOps"})
/*     */ public class BinaryLogicOpType extends LogicOpsType
/*     */ {
/*     */ 
/*     */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="comparisonOps", namespace="http://www.opengis.net/ogc", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="spatialOps", namespace="http://www.opengis.net/ogc", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="logicOps", namespace="http://www.opengis.net/ogc", type=JAXBElement.class)})
/*     */   protected List<JAXBElement<?>> comparisonOpsOrSpatialOpsOrLogicOps;
/*     */ 
/*     */   public List<JAXBElement<?>> getComparisonOpsOrSpatialOpsOrLogicOps()
/*     */   {
/* 105 */     if (this.comparisonOpsOrSpatialOpsOrLogicOps == null) {
/* 106 */       this.comparisonOpsOrSpatialOpsOrLogicOps = new ArrayList();
/*     */     }
/* 108 */     return this.comparisonOpsOrSpatialOpsOrLogicOps;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.BinaryLogicOpType
 * JD-Core Version:    0.6.0
 */