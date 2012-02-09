/*     */ package net.opengis.ows;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="CapabilitiesBaseType", propOrder={"serviceIdentification", "serviceProvider", "operationsMetadata"})
/*     */ public class CapabilitiesBaseType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="ServiceIdentification")
/*     */   protected ServiceIdentification serviceIdentification;
/*     */ 
/*     */   @XmlElement(name="ServiceProvider")
/*     */   protected ServiceProvider serviceProvider;
/*     */ 
/*     */   @XmlElement(name="OperationsMetadata")
/*     */   protected OperationsMetadata operationsMetadata;
/*     */ 
/*     */   @XmlAttribute(name="version", required=true)
/*     */   protected String version;
/*     */ 
/*     */   @XmlAttribute(name="updateSequence")
/*     */   protected String updateSequence;
/*     */ 
/*     */   public ServiceIdentification getServiceIdentification()
/*     */   {
/*  71 */     return this.serviceIdentification;
/*     */   }
/*     */ 
/*     */   public void setServiceIdentification(ServiceIdentification value)
/*     */   {
/*  83 */     this.serviceIdentification = value;
/*     */   }
/*     */ 
/*     */   public ServiceProvider getServiceProvider()
/*     */   {
/*  95 */     return this.serviceProvider;
/*     */   }
/*     */ 
/*     */   public void setServiceProvider(ServiceProvider value)
/*     */   {
/* 107 */     this.serviceProvider = value;
/*     */   }
/*     */ 
/*     */   public OperationsMetadata getOperationsMetadata()
/*     */   {
/* 119 */     return this.operationsMetadata;
/*     */   }
/*     */ 
/*     */   public void setOperationsMetadata(OperationsMetadata value)
/*     */   {
/* 131 */     this.operationsMetadata = value;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 143 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String value)
/*     */   {
/* 155 */     this.version = value;
/*     */   }
/*     */ 
/*     */   public String getUpdateSequence()
/*     */   {
/* 167 */     return this.updateSequence;
/*     */   }
/*     */ 
/*     */   public void setUpdateSequence(String value)
/*     */   {
/* 179 */     this.updateSequence = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.CapabilitiesBaseType
 * JD-Core Version:    0.6.0
 */