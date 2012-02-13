/*     */ package net.opengis.ows;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="", propOrder={"dcp", "parameter", "constraint", "metadata"})
/*     */ @XmlRootElement(name="Operation")
/*     */ public class Operation
/*     */ {
/*     */ 
/*     */   @XmlElement(name="DCP", required=true)
/*     */   protected List<DCP> dcp;
/*     */ 
/*     */   @XmlElement(name="Parameter")
/*     */   protected List<DomainType> parameter;
/*     */ 
/*     */   @XmlElement(name="Constraint")
/*     */   protected List<DomainType> constraint;
/*     */ 
/*     */   @XmlElement(name="Metadata")
/*     */   protected List<MetadataType> metadata;
/*     */ 
/*     */   @XmlAttribute(name="name", required=true)
/*     */   protected String name;
/*     */ 
/*     */   public List<DCP> getDCP()
/*     */   {
/*  88 */     if (this.dcp == null) {
/*  89 */       this.dcp = new ArrayList();
/*     */     }
/*  91 */     return this.dcp;
/*     */   }
/*     */ 
/*     */   public List<DomainType> getParameter()
/*     */   {
/* 117 */     if (this.parameter == null) {
/* 118 */       this.parameter = new ArrayList();
/*     */     }
/* 120 */     return this.parameter;
/*     */   }
/*     */ 
/*     */   public List<DomainType> getConstraint()
/*     */   {
/* 146 */     if (this.constraint == null) {
/* 147 */       this.constraint = new ArrayList();
/*     */     }
/* 149 */     return this.constraint;
/*     */   }
/*     */ 
/*     */   public List<MetadataType> getMetadata()
/*     */   {
/* 175 */     if (this.metadata == null) {
/* 176 */       this.metadata = new ArrayList();
/*     */     }
/* 178 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 190 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String value)
/*     */   {
/* 202 */     this.name = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.Operation
 * JD-Core Version:    0.6.0
 */