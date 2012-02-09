/*     */ package net.opengis.ows;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="", propOrder={"operation", "parameter", "constraint", "extendedCapabilities"})
/*     */ @XmlRootElement(name="OperationsMetadata")
/*     */ public class OperationsMetadata
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Operation", required=true)
/*     */   protected List<Operation> operation;
/*     */ 
/*     */   @XmlElement(name="Parameter")
/*     */   protected List<DomainType> parameter;
/*     */ 
/*     */   @XmlElement(name="Constraint")
/*     */   protected List<DomainType> constraint;
/*     */ 
/*     */   @XmlElement(name="ExtendedCapabilities")
/*     */   protected Object extendedCapabilities;
/*     */ 
/*     */   public List<Operation> getOperation()
/*     */   {
/*  84 */     if (this.operation == null) {
/*  85 */       this.operation = new ArrayList();
/*     */     }
/*  87 */     return this.operation;
/*     */   }
/*     */ 
/*     */   public List<DomainType> getParameter()
/*     */   {
/* 113 */     if (this.parameter == null) {
/* 114 */       this.parameter = new ArrayList();
/*     */     }
/* 116 */     return this.parameter;
/*     */   }
/*     */ 
/*     */   public List<DomainType> getConstraint()
/*     */   {
/* 142 */     if (this.constraint == null) {
/* 143 */       this.constraint = new ArrayList();
/*     */     }
/* 145 */     return this.constraint;
/*     */   }
/*     */ 
/*     */   public Object getExtendedCapabilities()
/*     */   {
/* 157 */     return this.extendedCapabilities;
/*     */   }
/*     */ 
/*     */   public void setExtendedCapabilities(Object value)
/*     */   {
/* 169 */     this.extendedCapabilities = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.OperationsMetadata
 * JD-Core Version:    0.6.0
 */