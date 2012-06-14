/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="", propOrder={"spatialCapabilities", "scalarCapabilities", "idCapabilities"})
/*     */ @XmlRootElement(name="Filter_Capabilities")
/*     */ public class Filter_Capabilities
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Spatial_Capabilities", required=true)
/*     */   protected Spatial_CapabilitiesType spatialCapabilities;
/*     */ 
/*     */   @XmlElement(name="Scalar_Capabilities", required=true)
/*     */   protected Scalar_CapabilitiesType scalarCapabilities;
/*     */ 
/*     */   @XmlElement(name="Id_Capabilities", required=true)
/*     */   protected Id_CapabilitiesType idCapabilities;
/*     */ 
/*     */   public Spatial_CapabilitiesType getSpatial_Capabilities()
/*     */   {
/*  64 */     return this.spatialCapabilities;
/*     */   }
/*     */ 
/*     */   public void setSpatial_Capabilities(Spatial_CapabilitiesType value)
/*     */   {
/*  76 */     this.spatialCapabilities = value;
/*     */   }
/*     */ 
/*     */   public Scalar_CapabilitiesType getScalar_Capabilities()
/*     */   {
/*  88 */     return this.scalarCapabilities;
/*     */   }
/*     */ 
/*     */   public void setScalar_Capabilities(Scalar_CapabilitiesType value)
/*     */   {
/* 100 */     this.scalarCapabilities = value;
/*     */   }
/*     */ 
/*     */   public Id_CapabilitiesType getId_Capabilities()
/*     */   {
/* 112 */     return this.idCapabilities;
/*     */   }
/*     */ 
/*     */   public void setId_Capabilities(Id_CapabilitiesType value)
/*     */   {
/* 124 */     this.idCapabilities = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.Filter_Capabilities
 * JD-Core Version:    0.6.0
 */