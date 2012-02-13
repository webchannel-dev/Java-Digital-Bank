/*     */ package com.bright.framework.common.bean;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class Company extends DataBean
/*     */ {
/*  30 */   private long m_lTypeId = 0L;
/*     */ 
/*  32 */   private String m_sName = null;
/*  33 */   private long m_lAddressId = 0L;
/*  34 */   private long m_lHQAddressId = 0L;
/*  35 */   private String m_sParentCompanyName = null;
/*  36 */   private String m_sTelephone = null;
/*  37 */   private String m_sFax = null;
/*  38 */   private Vector m_vecContacts = null;
/*  39 */   private String m_sWebsiteUrl = null;
/*  40 */   private boolean m_bIsSuspended = false;
/*     */ 
/*     */   public Contact getContact()
/*     */   {
/*  54 */     Contact contact = null;
/*     */ 
/*  56 */     if ((this.m_vecContacts != null) && (this.m_vecContacts.size() > 0))
/*     */     {
/*  59 */       contact = (Contact)this.m_vecContacts.get(0);
/*     */     }
/*     */ 
/*  62 */     return contact;
/*     */   }
/*     */ 
/*     */   public void setTypeId(long a_lTypeId)
/*     */   {
/*  67 */     this.m_lTypeId = a_lTypeId;
/*     */   }
/*     */ 
/*     */   public long getTypeId()
/*     */   {
/*  72 */     return this.m_lTypeId;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/*  77 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  82 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public long getAddressId()
/*     */   {
/*  87 */     return this.m_lAddressId;
/*     */   }
/*     */ 
/*     */   public void setAddressId(long a_lAddressId)
/*     */   {
/*  92 */     this.m_lAddressId = a_lAddressId;
/*     */   }
/*     */ 
/*     */   public long getHQAddressId()
/*     */   {
/*  97 */     return this.m_lHQAddressId;
/*     */   }
/*     */ 
/*     */   public void setHQAddressId(long a_lHQAddressId)
/*     */   {
/* 102 */     this.m_lHQAddressId = a_lHQAddressId;
/*     */   }
/*     */ 
/*     */   public String getParentCompanyName()
/*     */   {
/* 111 */     return this.m_sParentCompanyName;
/*     */   }
/*     */ 
/*     */   public void setParentCompanyName(String a_sParentCompanyName)
/*     */   {
/* 120 */     this.m_sParentCompanyName = a_sParentCompanyName;
/*     */   }
/*     */ 
/*     */   public String getTelephone()
/*     */   {
/* 129 */     return this.m_sTelephone;
/*     */   }
/*     */ 
/*     */   public void setTelephone(String a_sTelephone)
/*     */   {
/* 138 */     this.m_sTelephone = a_sTelephone;
/*     */   }
/*     */ 
/*     */   public String getFax()
/*     */   {
/* 147 */     return this.m_sFax;
/*     */   }
/*     */ 
/*     */   public void setFax(String a_sFax)
/*     */   {
/* 156 */     this.m_sFax = a_sFax;
/*     */   }
/*     */ 
/*     */   public Vector getContacts()
/*     */   {
/* 161 */     return this.m_vecContacts;
/*     */   }
/*     */ 
/*     */   public void setContacts(Vector a_vecContacts)
/*     */   {
/* 166 */     this.m_vecContacts = a_vecContacts;
/*     */   }
/*     */ 
/*     */   public String getWebsiteUrl()
/*     */   {
/* 171 */     return this.m_sWebsiteUrl;
/*     */   }
/*     */ 
/*     */   public void setWebsiteUrl(String a_sWebsiteUrl)
/*     */   {
/* 176 */     this.m_sWebsiteUrl = a_sWebsiteUrl;
/*     */   }
/*     */ 
/*     */   public boolean getIsSuspended()
/*     */   {
/* 181 */     return this.m_bIsSuspended;
/*     */   }
/*     */ 
/*     */   public void setIsSuspended(boolean a_bIsSuspended)
/*     */   {
/* 186 */     this.m_bIsSuspended = a_bIsSuspended;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.Company
 * JD-Core Version:    0.6.0
 */