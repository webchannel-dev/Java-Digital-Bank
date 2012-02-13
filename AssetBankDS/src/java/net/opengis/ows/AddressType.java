/*     */ package net.opengis.ows;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="AddressType", propOrder={"deliveryPoint", "city", "administrativeArea", "postalCode", "country", "electronicMailAddress"})
/*     */ public class AddressType
/*     */ {
/*     */ 
/*     */   @XmlElement(name="DeliveryPoint")
/*     */   protected List<String> deliveryPoint;
/*     */ 
/*     */   @XmlElement(name="City")
/*     */   protected String city;
/*     */ 
/*     */   @XmlElement(name="AdministrativeArea")
/*     */   protected String administrativeArea;
/*     */ 
/*     */   @XmlElement(name="PostalCode")
/*     */   protected String postalCode;
/*     */ 
/*     */   @XmlElement(name="Country")
/*     */   protected String country;
/*     */ 
/*     */   @XmlElement(name="ElectronicMailAddress")
/*     */   protected List<String> electronicMailAddress;
/*     */ 
/*     */   public List<String> getDeliveryPoint()
/*     */   {
/*  92 */     if (this.deliveryPoint == null) {
/*  93 */       this.deliveryPoint = new ArrayList();
/*     */     }
/*  95 */     return this.deliveryPoint;
/*     */   }
/*     */ 
/*     */   public String getCity()
/*     */   {
/* 107 */     return this.city;
/*     */   }
/*     */ 
/*     */   public void setCity(String value)
/*     */   {
/* 119 */     this.city = value;
/*     */   }
/*     */ 
/*     */   public String getAdministrativeArea()
/*     */   {
/* 131 */     return this.administrativeArea;
/*     */   }
/*     */ 
/*     */   public void setAdministrativeArea(String value)
/*     */   {
/* 143 */     this.administrativeArea = value;
/*     */   }
/*     */ 
/*     */   public String getPostalCode()
/*     */   {
/* 155 */     return this.postalCode;
/*     */   }
/*     */ 
/*     */   public void setPostalCode(String value)
/*     */   {
/* 167 */     this.postalCode = value;
/*     */   }
/*     */ 
/*     */   public String getCountry()
/*     */   {
/* 179 */     return this.country;
/*     */   }
/*     */ 
/*     */   public void setCountry(String value)
/*     */   {
/* 191 */     this.country = value;
/*     */   }
/*     */ 
/*     */   public List<String> getElectronicMailAddress()
/*     */   {
/* 217 */     if (this.electronicMailAddress == null) {
/* 218 */       this.electronicMailAddress = new ArrayList();
/*     */     }
/* 220 */     return this.electronicMailAddress;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.AddressType
 * JD-Core Version:    0.6.0
 */