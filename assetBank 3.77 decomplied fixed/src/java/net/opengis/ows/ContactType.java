/*     */ package net.opengis.ows;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="ContactType", propOrder={"phone", "address", "onlineResource", "hoursOfService", "contactInstructions"})
/*     */ public class ContactType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="Phone")
/*     */   protected TelephoneType phone;
/*     */ 
/*     */   @XmlElement(name="Address")
/*     */   protected AddressType address;
/*     */ 
/*     */   @XmlElement(name="OnlineResource")
/*     */   protected OnlineResourceType onlineResource;
/*     */ 
/*     */   @XmlElement(name="HoursOfService")
/*     */   protected String hoursOfService;
/*     */ 
/*     */   @XmlElement(name="ContactInstructions")
/*     */   protected String contactInstructions;
/*     */ 
/*     */   public TelephoneType getPhone()
/*     */   {
/*  72 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(TelephoneType value)
/*     */   {
/*  84 */     this.phone = value;
/*     */   }
/*     */ 
/*     */   public AddressType getAddress()
/*     */   {
/*  96 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(AddressType value)
/*     */   {
/* 108 */     this.address = value;
/*     */   }
/*     */ 
/*     */   public OnlineResourceType getOnlineResource()
/*     */   {
/* 120 */     return this.onlineResource;
/*     */   }
/*     */ 
/*     */   public void setOnlineResource(OnlineResourceType value)
/*     */   {
/* 132 */     this.onlineResource = value;
/*     */   }
/*     */ 
/*     */   public String getHoursOfService()
/*     */   {
/* 144 */     return this.hoursOfService;
/*     */   }
/*     */ 
/*     */   public void setHoursOfService(String value)
/*     */   {
/* 156 */     this.hoursOfService = value;
/*     */   }
/*     */ 
/*     */   public String getContactInstructions()
/*     */   {
/* 168 */     return this.contactInstructions;
/*     */   }
/*     */ 
/*     */   public void setContactInstructions(String value)
/*     */   {
/* 180 */     this.contactInstructions = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.ContactType
 * JD-Core Version:    0.6.0
 */