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
/*     */ @XmlType(name="", propOrder={"serviceType", "serviceTypeVersion", "fees", "accessConstraints"})
/*     */ @XmlRootElement(name="ServiceIdentification")
/*     */ public class ServiceIdentification extends DescriptionType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="ServiceType", required=true)
/*     */   protected CodeType serviceType;
/*     */ 
/*     */   @XmlElement(name="ServiceTypeVersion", required=true)
/*     */   protected List<String> serviceTypeVersion;
/*     */ 
/*     */   @XmlElement(name="Fees")
/*     */   protected String fees;
/*     */ 
/*     */   @XmlElement(name="AccessConstraints")
/*     */   protected List<String> accessConstraints;
/*     */ 
/*     */   public CodeType getServiceType()
/*     */   {
/*  72 */     return this.serviceType;
/*     */   }
/*     */ 
/*     */   public void setServiceType(CodeType value)
/*     */   {
/*  84 */     this.serviceType = value;
/*     */   }
/*     */ 
/*     */   public List<String> getServiceTypeVersion()
/*     */   {
/* 110 */     if (this.serviceTypeVersion == null) {
/* 111 */       this.serviceTypeVersion = new ArrayList();
/*     */     }
/* 113 */     return this.serviceTypeVersion;
/*     */   }
/*     */ 
/*     */   public String getFees()
/*     */   {
/* 125 */     return this.fees;
/*     */   }
/*     */ 
/*     */   public void setFees(String value)
/*     */   {
/* 137 */     this.fees = value;
/*     */   }
/*     */ 
/*     */   public List<String> getAccessConstraints()
/*     */   {
/* 163 */     if (this.accessConstraints == null) {
/* 164 */       this.accessConstraints = new ArrayList();
/*     */     }
/* 166 */     return this.accessConstraints;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.ServiceIdentification
 * JD-Core Version:    0.6.0
 */