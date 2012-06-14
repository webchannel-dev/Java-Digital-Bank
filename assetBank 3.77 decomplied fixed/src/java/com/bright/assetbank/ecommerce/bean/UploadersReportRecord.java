/*     */ package com.bright.assetbank.ecommerce.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ 
/*     */ public class UploadersReportRecord
/*     */ {
/*  30 */   private String m_sUsername = null;
/*  31 */   private String m_sForename = null;
/*  32 */   private String m_sSurname = null;
/*  33 */   private String m_sEmailAddress = null;
/*  34 */   private int m_iAssetsSold = 0;
/*  35 */   private BrightMoney m_totalIncome = null;
/*  36 */   private long m_lUserId = 0L;
/*     */ 
/*     */   public int getAssetsSold()
/*     */   {
/*  42 */     return this.m_iAssetsSold;
/*     */   }
/*     */ 
/*     */   public void setAssetsSold(int a_iAssetsSold)
/*     */   {
/*  48 */     this.m_iAssetsSold = a_iAssetsSold;
/*     */   }
/*     */ 
/*     */   public String getForename()
/*     */   {
/*  54 */     return this.m_sForename;
/*     */   }
/*     */ 
/*     */   public void setForename(String a_sForename)
/*     */   {
/*  60 */     this.m_sForename = a_sForename;
/*     */   }
/*     */ 
/*     */   public String getSurname()
/*     */   {
/*  66 */     return this.m_sSurname;
/*     */   }
/*     */ 
/*     */   public void setSurname(String a_sSurname)
/*     */   {
/*  72 */     this.m_sSurname = a_sSurname;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  78 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername)
/*     */   {
/*  84 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public BrightMoney getTotalIncome()
/*     */   {
/*  90 */     return this.m_totalIncome;
/*     */   }
/*     */ 
/*     */   public void setTotalIncome(BrightMoney a_totalIncome)
/*     */   {
/*  96 */     this.m_totalIncome = a_totalIncome;
/*     */   }
/*     */ 
/*     */   public String getEmailAddress()
/*     */   {
/* 103 */     return this.m_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public void setEmailAddress(String a_sEmailAddress)
/*     */   {
/* 109 */     this.m_sEmailAddress = a_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/* 115 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_lUserId)
/*     */   {
/* 121 */     this.m_lUserId = a_lUserId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.UploadersReportRecord
 * JD-Core Version:    0.6.0
 */