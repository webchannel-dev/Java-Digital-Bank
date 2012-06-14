/*     */ package com.bright.assetbank.attribute.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SendEmailRule extends DateAttributeRule
/*     */ {
/*     */   private BrightNaturalNumber m_daysDifference;
/*     */   private boolean m_isDaysAfter;
/*  39 */   private String m_sMessage = null;
/*     */ 
/*  42 */   private List<StringDataBean> m_groups = null;
/*     */ 
/*  45 */   private boolean m_emailUsersWhoDownloadedAsset = false;
/*     */ 
/*     */   public SendEmailRule()
/*     */   {
/*  53 */     this.m_daysDifference = new BrightNaturalNumber();
/*     */   }
/*     */ 
/*     */   public long getDaysBefore()
/*     */   {
/*     */     long lDaysBefore;
/*     */    // long lDaysBefore;
/*  66 */     if (this.m_isDaysAfter)
/*     */     {
/*  68 */       lDaysBefore = 0L - this.m_daysDifference.getNumber();
/*     */     }
/*     */     else
/*     */     {
/*  72 */       lDaysBefore = this.m_daysDifference.getNumber();
/*     */     }
/*     */ 
/*  75 */     return lDaysBefore;
/*     */   }
/*     */ 
/*     */   public void setDaysBefore(long a_lDays)
/*     */   {
/*  86 */     if (a_lDays >= 0L)
/*     */     {
/*  88 */       this.m_daysDifference.setNumber(a_lDays);
/*  89 */       this.m_isDaysAfter = false;
/*     */     }
/*     */     else
/*     */     {
/*  93 */       this.m_daysDifference.setNumber(0L - a_lDays);
/*  94 */       this.m_isDaysAfter = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public BrightNaturalNumber getDaysDifference()
/*     */   {
/* 103 */     return this.m_daysDifference;
/*     */   }
/*     */ 
/*     */   public void setDaysDifference(BrightNaturalNumber a_sDaysDifference) {
/* 107 */     this.m_daysDifference = a_sDaysDifference;
/*     */   }
/*     */ 
/*     */   public String getMessage() {
/* 111 */     return this.m_sMessage;
/*     */   }
/*     */ 
/*     */   public void setMessage(String a_sMessage) {
/* 115 */     this.m_sMessage = a_sMessage;
/*     */   }
/*     */ 
/*     */   public List<StringDataBean> getGroups() {
/* 119 */     return this.m_groups;
/*     */   }
/*     */ 
/*     */   public void setGroups(List<StringDataBean> a_sVecGroups) {
/* 123 */     this.m_groups = a_sVecGroups;
/*     */   }
/*     */ 
/*     */   public boolean getIsDaysAfter()
/*     */   {
/* 128 */     return this.m_isDaysAfter;
/*     */   }
/*     */ 
/*     */   public void setIsDaysAfter(boolean a_sIsDaysAfter) {
/* 132 */     this.m_isDaysAfter = a_sIsDaysAfter;
/*     */   }
/*     */ 
/*     */   public boolean getEmailUsersWhoDownloadedAsset()
/*     */   {
/* 137 */     return this.m_emailUsersWhoDownloadedAsset;
/*     */   }
/*     */ 
/*     */   public void setEmailUsersWhoDownloadedAsset(boolean a_emailUsersWhoDownloadedAsset) {
/* 141 */     this.m_emailUsersWhoDownloadedAsset = a_emailUsersWhoDownloadedAsset;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.SendEmailRule
 * JD-Core Version:    0.6.0
 */