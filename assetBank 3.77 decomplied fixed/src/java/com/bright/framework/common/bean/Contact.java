/*     */ package com.bright.framework.common.bean;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ 
/*     */ public class Contact extends DataBean
/*     */ {
/*  30 */   private String m_sTitle = null;
/*  31 */   private String m_sForename = null;
/*  32 */   private String m_sSurname = null;
/*  33 */   private long m_lAddressId = 0L;
/*  34 */   private String m_sTelephone = null;
/*  35 */   private String m_sFax = null;
/*  36 */   private long m_lCompanyId = 0L;
/*  37 */   private String m_sEmailAddress = null;
/*  38 */   private String m_sPosition = null;
/*     */ 
/*     */   public void setForename(String a_sForename)
/*     */   {
/*  42 */     this.m_sForename = a_sForename;
/*     */   }
/*     */ 
/*     */   public String getForename()
/*     */   {
/*  47 */     return this.m_sForename;
/*     */   }
/*     */ 
/*     */   public void setSurname(String a_sSurname)
/*     */   {
/*  52 */     this.m_sSurname = a_sSurname;
/*     */   }
/*     */ 
/*     */   public String getSurname()
/*     */   {
/*  57 */     return this.m_sSurname;
/*     */   }
/*     */ 
/*     */   public long getAddressId()
/*     */   {
/*  66 */     return this.m_lAddressId;
/*     */   }
/*     */ 
/*     */   public void setAddressId(long a_lAddressId)
/*     */   {
/*  75 */     this.m_lAddressId = a_lAddressId;
/*     */   }
/*     */ 
/*     */   public String getTelephone()
/*     */   {
/*  84 */     return this.m_sTelephone;
/*     */   }
/*     */ 
/*     */   public void setTelephone(String a_sTelephone)
/*     */   {
/*  93 */     this.m_sTelephone = a_sTelephone;
/*     */   }
/*     */ 
/*     */   public String getFax()
/*     */   {
/* 102 */     return this.m_sFax;
/*     */   }
/*     */ 
/*     */   public void setFax(String a_sFax)
/*     */   {
/* 111 */     this.m_sFax = a_sFax;
/*     */   }
/*     */ 
/*     */   public long getCompanyId()
/*     */   {
/* 120 */     return this.m_lCompanyId;
/*     */   }
/*     */ 
/*     */   public void setCompanyId(long a_lCompanyId)
/*     */   {
/* 129 */     this.m_lCompanyId = a_lCompanyId;
/*     */   }
/*     */ 
/*     */   public String getEmailAddress()
/*     */   {
/* 138 */     return this.m_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public void setEmailAddress(String a_sEmailAddress)
/*     */   {
/* 147 */     this.m_sEmailAddress = a_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 156 */     return this.m_sTitle;
/*     */   }
/*     */ 
/*     */   public void setTitle(String a_sTitle)
/*     */   {
/* 165 */     this.m_sTitle = a_sTitle;
/*     */   }
/*     */ 
/*     */   public void setPosition(String a_sPosition)
/*     */   {
/* 170 */     this.m_sPosition = a_sPosition;
/*     */   }
/*     */ 
/*     */   public String getPosition()
/*     */   {
/* 175 */     return this.m_sPosition;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.Contact
 * JD-Core Version:    0.6.0
 */