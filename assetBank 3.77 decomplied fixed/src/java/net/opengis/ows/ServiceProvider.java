/*     */ package net.opengis.ows;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="", propOrder={"providerName", "providerSite", "serviceContact"})
/*     */ @XmlRootElement(name="ServiceProvider")
/*     */ public class ServiceProvider
/*     */ {
/*     */ 
/*     */   @XmlElement(name="ProviderName", required=true)
/*     */   protected String providerName;
/*     */ 
/*     */   @XmlElement(name="ProviderSite")
/*     */   protected OnlineResourceType providerSite;
/*     */ 
/*     */   @XmlElement(name="ServiceContact", required=true)
/*     */   protected ResponsiblePartySubsetType serviceContact;
/*     */ 
/*     */   public String getProviderName()
/*     */   {
/*  64 */     return this.providerName;
/*     */   }
/*     */ 
/*     */   public void setProviderName(String value)
/*     */   {
/*  76 */     this.providerName = value;
/*     */   }
/*     */ 
/*     */   public OnlineResourceType getProviderSite()
/*     */   {
/*  88 */     return this.providerSite;
/*     */   }
/*     */ 
/*     */   public void setProviderSite(OnlineResourceType value)
/*     */   {
/* 100 */     this.providerSite = value;
/*     */   }
/*     */ 
/*     */   public ResponsiblePartySubsetType getServiceContact()
/*     */   {
/* 112 */     return this.serviceContact;
/*     */   }
/*     */ 
/*     */   public void setServiceContact(ResponsiblePartySubsetType value)
/*     */   {
/* 124 */     this.serviceContact = value;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.ServiceProvider
 * JD-Core Version:    0.6.0
 */