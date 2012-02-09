/*     */ package com.bright.framework.exchange.bean;
/*     */ 
/*     */ public class ADUser
/*     */ {
/*  27 */   private String m_sCN = null;
/*  28 */   private String m_sDistinguishedName = null;
/*  29 */   private String m_sAccountName = null;
/*  30 */   private String m_sDisplayName = null;
/*  31 */   private String m_sEmailAddress = null;
/*  32 */   private String m_sForename = null;
/*  33 */   private String m_sSurname = null;
/*  34 */   private String[] m_asMemberOf = null;
/*  35 */   private String m_sCompany = null;
/*     */ 
/*     */   public String getMailBoxName()
/*     */   {
/*  50 */     return this.m_sAccountName;
/*     */   }
/*     */ 
/*     */   public String getCN()
/*     */   {
/*  56 */     return this.m_sCN;
/*     */   }
/*     */ 
/*     */   public void setCN(String a_sName)
/*     */   {
/*  61 */     this.m_sCN = a_sName;
/*     */   }
/*     */ 
/*     */   public String getDisplayName()
/*     */   {
/*  66 */     return this.m_sDisplayName;
/*     */   }
/*     */ 
/*     */   public void setDisplayName(String a_sDisplayName)
/*     */   {
/*  71 */     this.m_sDisplayName = a_sDisplayName;
/*     */   }
/*     */ 
/*     */   public String getEmailAddress()
/*     */   {
/*  76 */     return this.m_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public void setEmailAddress(String a_sEmailAddress)
/*     */   {
/*  81 */     this.m_sEmailAddress = a_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public String getAccountName()
/*     */   {
/*  86 */     return this.m_sAccountName;
/*     */   }
/*     */ 
/*     */   public void setAccountName(String a_sAccountName)
/*     */   {
/*  91 */     this.m_sAccountName = a_sAccountName;
/*     */   }
/*     */ 
/*     */   public String getForename()
/*     */   {
/*  96 */     return this.m_sForename;
/*     */   }
/*     */ 
/*     */   public void setForename(String a_sForename)
/*     */   {
/* 101 */     this.m_sForename = a_sForename;
/*     */   }
/*     */ 
/*     */   public String getSurname()
/*     */   {
/* 106 */     return this.m_sSurname;
/*     */   }
/*     */ 
/*     */   public void setSurname(String a_sSurname)
/*     */   {
/* 111 */     this.m_sSurname = a_sSurname;
/*     */   }
/*     */ 
/*     */   public String getDistinguishedName()
/*     */   {
/* 116 */     return this.m_sDistinguishedName;
/*     */   }
/*     */ 
/*     */   public void setDistinguishedName(String a_sDistinguishedName)
/*     */   {
/* 121 */     this.m_sDistinguishedName = a_sDistinguishedName;
/*     */   }
/*     */ 
/*     */   public String[] getMemberOf()
/*     */   {
/* 126 */     return this.m_asMemberOf;
/*     */   }
/*     */ 
/*     */   public void setMemberOf(String[] a_asMemberOf) {
/* 130 */     this.m_asMemberOf = a_asMemberOf;
/*     */   }
/*     */ 
/*     */   public String getCompany()
/*     */   {
/* 135 */     return this.m_sCompany;
/*     */   }
/*     */ 
/*     */   public void setCompany(String a_sCompany)
/*     */   {
/* 140 */     this.m_sCompany = a_sCompany;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.exchange.bean.ADUser
 * JD-Core Version:    0.6.0
 */