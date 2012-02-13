/*     */ package com.bright.framework.common.bean;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ 
/*     */ public class Address extends DataBean
/*     */ {
/*  30 */   private String m_sAddressLine1 = null;
/*  31 */   private String m_sAddressLine2 = null;
/*  32 */   private String m_sTown = null;
/*  33 */   private String m_sCounty = null;
/*  34 */   private String m_sPostcode = null;
/*  35 */   private Country m_country = null;
/*     */ 
/*     */   public Address()
/*     */   {
/*  39 */     this.m_country = new Country();
/*     */   }
/*     */ 
/*     */   public void setAddressLine1(String a_sAddressLine1)
/*     */   {
/*  44 */     this.m_sAddressLine1 = a_sAddressLine1;
/*     */   }
/*     */ 
/*     */   public String getAddressLine1() {
/*  48 */     return this.m_sAddressLine1;
/*     */   }
/*     */ 
/*     */   public void setAddressLine2(String a_sAddressLine2)
/*     */   {
/*  53 */     this.m_sAddressLine2 = a_sAddressLine2;
/*     */   }
/*     */ 
/*     */   public String getAddressLine2() {
/*  57 */     return this.m_sAddressLine2;
/*     */   }
/*     */ 
/*     */   public void setTown(String a_sTown)
/*     */   {
/*  62 */     this.m_sTown = a_sTown;
/*     */   }
/*     */ 
/*     */   public String getTown() {
/*  66 */     return this.m_sTown;
/*     */   }
/*     */ 
/*     */   public void setCounty(String a_sCounty)
/*     */   {
/*  72 */     this.m_sCounty = a_sCounty;
/*     */   }
/*     */ 
/*     */   public String getCounty() {
/*  76 */     return this.m_sCounty;
/*     */   }
/*     */ 
/*     */   public void setPostcode(String a_sPostCode)
/*     */   {
/*  82 */     this.m_sPostcode = a_sPostCode;
/*     */   }
/*     */ 
/*     */   public String getPostcode() {
/*  86 */     return this.m_sPostcode;
/*     */   }
/*     */ 
/*     */   public void setCountry(Country a_country)
/*     */   {
/*  92 */     this.m_country = a_country;
/*     */   }
/*     */ 
/*     */   public Country getCountry() {
/*  96 */     return this.m_country;
/*     */   }
/*     */ 
/*     */   public void copy(Address a_address)
/*     */   {
/* 101 */     setAddressLine1(a_address.getAddressLine1());
/* 102 */     setAddressLine2(a_address.getAddressLine2());
/* 103 */     setTown(a_address.getTown());
/* 104 */     setCounty(a_address.getCounty());
/* 105 */     setPostcode(a_address.getPostcode());
/* 106 */     setCountry(a_address.getCountry());
/*     */   }
/*     */ 
/*     */   public boolean isValidPostalAddress()
/*     */   {
/* 111 */     return (StringUtil.stringIsPopulated(getAddressLine1())) && (StringUtil.stringIsPopulated(getPostcode())) && (getCountry().getId() > 0L);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.Address
 * JD-Core Version:    0.6.0
 */