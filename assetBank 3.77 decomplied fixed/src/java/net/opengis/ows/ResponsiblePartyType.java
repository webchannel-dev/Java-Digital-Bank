/*     */ package net.opengis.ows;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ResponsiblePartyType", propOrder={"individualName", "organisationName", "positionName", "contactInfo", "role"})
/*     */ public class ResponsiblePartyType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="IndividualName")
/*     */   protected String individualName;
/*     */ 
/*     */   @XmlElement(name="OrganisationName")
/*     */   protected String organisationName;
/*     */ 
/*     */   @XmlElement(name="PositionName")
/*     */   protected String positionName;
/*     */ 
/*     */   @XmlElement(name="ContactInfo")
/*     */   protected ContactType contactInfo;
/*     */ 
/*     */   @XmlElement(name="Role", required=true)
/*     */   protected CodeType role;
/*     */ 
/*     */   public String getIndividualName()
/*     */   {
/*  72 */     return this.individualName;
/*     */   }
/*     */ 
/*     */   public void setIndividualName(String value)
/*     */   {
/*  84 */     this.individualName = value;
/*     */   }
/*     */ 
/*     */   public String getOrganisationName()
/*     */   {
/*  96 */     return this.organisationName;
/*     */   }
/*     */ 
/*     */   public void setOrganisationName(String value)
/*     */   {
/* 108 */     this.organisationName = value;
/*     */   }
/*     */ 
/*     */   public String getPositionName()
/*     */   {
/* 120 */     return this.positionName;
/*     */   }
/*     */ 
/*     */   public void setPositionName(String value)
/*     */   {
/* 132 */     this.positionName = value;
/*     */   }
/*     */ 
/*     */   public ContactType getContactInfo()
/*     */   {
/* 144 */     return this.contactInfo;
/*     */   }
/*     */ 
/*     */   public void setContactInfo(ContactType value)
/*     */   {
/* 156 */     this.contactInfo = value;
/*     */   }
/*     */ 
/*     */   public CodeType getRole()
/*     */   {
/* 168 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(CodeType value)
/*     */   {
/* 180 */     this.role = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.ResponsiblePartyType
 * JD-Core Version:    0.6.0
 */