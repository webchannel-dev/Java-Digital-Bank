/*     */ package net.opengis.ows;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ResponsiblePartySubsetType", propOrder={"individualName", "positionName", "contactInfo", "role"})
/*     */ public class ResponsiblePartySubsetType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="IndividualName")
/*     */   protected String individualName;
/*     */ 
/*     */   @XmlElement(name="PositionName")
/*     */   protected String positionName;
/*     */ 
/*     */   @XmlElement(name="ContactInfo")
/*     */   protected ContactType contactInfo;
/*     */ 
/*     */   @XmlElement(name="Role")
/*     */   protected CodeType role;
/*     */ 
/*     */   public String getIndividualName()
/*     */   {
/*  68 */     return this.individualName;
/*     */   }
/*     */ 
/*     */   public void setIndividualName(String value)
/*     */   {
/*  80 */     this.individualName = value;
/*     */   }
/*     */ 
/*     */   public String getPositionName()
/*     */   {
/*  92 */     return this.positionName;
/*     */   }
/*     */ 
/*     */   public void setPositionName(String value)
/*     */   {
/* 104 */     this.positionName = value;
/*     */   }
/*     */ 
/*     */   public ContactType getContactInfo()
/*     */   {
/* 116 */     return this.contactInfo;
/*     */   }
/*     */ 
/*     */   public void setContactInfo(ContactType value)
/*     */   {
/* 128 */     this.contactInfo = value;
/*     */   }
/*     */ 
/*     */   public CodeType getRole()
/*     */   {
/* 140 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(CodeType value)
/*     */   {
/* 152 */     this.role = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.ResponsiblePartySubsetType
 * JD-Core Version:    0.6.0
 */