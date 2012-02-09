/*     */ package com.bright.assetbank.subscription.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Subscription extends DataBean
/*     */ {
/*  36 */   private SubscriptionModel m_model = null;
/*  37 */   private StringDataBean m_refUser = null;
/*  38 */   private BrightDate m_startDate = null;
/*  39 */   private boolean m_bActive = false;
/*  40 */   private BrightMoney m_pricePaid = null;
/*     */ 
/*     */   public Subscription()
/*     */   {
/*  44 */     this.m_model = new SubscriptionModel();
/*  45 */     this.m_refUser = new StringDataBean();
/*  46 */     this.m_pricePaid = new BrightMoney();
/*     */ 
/*  49 */     this.m_startDate = BrightDate.today();
/*     */   }
/*     */ 
/*     */   public boolean getExpired()
/*     */   {
/*  61 */     BrightDate expires = getExpiryDate();
/*  62 */     BrightDate today = BrightDate.today();
/*     */ 
/*  64 */     boolean bExpired = expires.getDate().compareTo(today.getDate()) < 0;
/*  65 */     return bExpired;
/*     */   }
/*     */ 
/*     */   public boolean getStarted()
/*     */   {
/*  78 */     BrightDate today = BrightDate.today();
/*     */ 
/*  80 */     boolean bStarted = this.m_startDate.getDate().compareTo(today.getDate()) <= 0;
/*  81 */     return bStarted;
/*     */   }
/*     */ 
/*     */   public long getDaysLeft()
/*     */   {
/*  93 */     BrightDate expires = getExpiryDate();
/*  94 */     BrightDate today = BrightDate.today();
/*     */ 
/*  96 */     long lDaysLeft = expires.getJulianDay() - today.getJulianDay();
/*     */ 
/*  98 */     return lDaysLeft;
/*     */   }
/*     */ 
/*     */   public BrightDate getExpiryDate()
/*     */   {
/* 110 */     Calendar cal = Calendar.getInstance();
/* 111 */     cal.setTime(this.m_startDate.getDate());
/*     */ 
/* 113 */     int iDurationDays = new Long(this.m_model.getDuration().getNumber()).intValue();
/* 114 */     cal.add(5, iDurationDays);
/* 115 */     Date dtExpires = cal.getTime();
/*     */ 
/* 117 */     BrightDate expiry = new BrightDate(dtExpires);
/* 118 */     return expiry;
/*     */   }
/*     */ 
/*     */   public SubscriptionModel getModel()
/*     */   {
/* 125 */     return this.m_model;
/*     */   }
/*     */ 
/*     */   public void setModel(SubscriptionModel a_sModel) {
/* 129 */     this.m_model = a_sModel;
/*     */   }
/*     */ 
/*     */   public StringDataBean getRefUser() {
/* 133 */     return this.m_refUser;
/*     */   }
/*     */ 
/*     */   public void setRefUser(StringDataBean a_sRefUser) {
/* 137 */     this.m_refUser = a_sRefUser;
/*     */   }
/*     */ 
/*     */   public BrightDate getStartDate() {
/* 141 */     return this.m_startDate;
/*     */   }
/*     */ 
/*     */   public void setStartDate(BrightDate a_sStartDate) {
/* 145 */     this.m_startDate = a_sStartDate;
/*     */   }
/*     */ 
/*     */   public boolean getActive()
/*     */   {
/* 150 */     return this.m_bActive;
/*     */   }
/*     */ 
/*     */   public void setActive(boolean a_sActive) {
/* 154 */     this.m_bActive = a_sActive;
/*     */   }
/*     */ 
/*     */   public BrightMoney getPricePaid()
/*     */   {
/* 159 */     return this.m_pricePaid;
/*     */   }
/*     */ 
/*     */   public void setPricePaid(BrightMoney a_sPricePaid) {
/* 163 */     this.m_pricePaid = a_sPricePaid;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.bean.Subscription
 * JD-Core Version:    0.6.0
 */