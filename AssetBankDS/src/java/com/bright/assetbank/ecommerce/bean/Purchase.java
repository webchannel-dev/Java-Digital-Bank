/*     */ package com.bright.assetbank.ecommerce.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.service.PaymentCallbackProcessor;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.common.bean.BrightDecimal;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class Purchase
/*     */ {
/*     */   private PspPaymentReturn m_paymentReturn;
/*  50 */   private ABUser m_registerUser = null;
/*     */ 
/*  52 */   private BrightMoney m_subtotal = null;
/*  53 */   private BrightDecimal m_vatPercent = null;
/*     */ 
/*  55 */   private String m_sUserNotes = null;
/*     */ 
/*  57 */   private boolean m_bPrefersOfflinePayment = false;
/*     */   private boolean m_hasPaid;
/*     */   private boolean m_hasRegistered;
/*     */   private long m_userId;
/*     */   private String m_purchaseId;
/*     */   private String m_emailAddress;
/*     */   private BrightMoney m_chargedAmount;
/*     */   private String m_loginUser;
/*     */   private String m_loginPassword;
/*     */   private Date m_dateCreated;
/*     */   private PaymentCallbackProcessor m_refPaymentProcessor;
/*     */ 
/*     */   public Purchase()
/*     */   {
/*  65 */     this.m_chargedAmount = new BrightMoney();
/*  66 */     this.m_subtotal = new BrightMoney();
/*  67 */     this.m_dateCreated = new Date();
/*  68 */     this.m_vatPercent = new BrightDecimal();
/*     */ 
/*  71 */     this.m_paymentReturn = new PspPaymentReturn();
/*     */   }
/*     */ 
/*     */   public abstract String getDescription();
/*     */ 
/*     */   public void registerPaymentSuccess(DBTransaction a_dbTransaction, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     this.m_refPaymentProcessor.registerPaymentSuccess(a_dbTransaction, this, a_language);
/*     */   }
/*     */ 
/*     */   public boolean getHasPaid()
/*     */   {
/* 108 */     return this.m_hasPaid;
/*     */   }
/*     */ 
/*     */   public void setHasPaid(boolean a_sHasPaid)
/*     */   {
/* 117 */     this.m_hasPaid = a_sHasPaid;
/*     */   }
/*     */ 
/*     */   public boolean getHasRegistered()
/*     */   {
/* 133 */     return this.m_hasRegistered;
/*     */   }
/*     */ 
/*     */   public void setHasRegistered(boolean a_sHasRegistered)
/*     */   {
/* 142 */     this.m_hasRegistered = a_sHasRegistered;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/* 158 */     return this.m_userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_sUserId)
/*     */   {
/* 167 */     this.m_userId = a_sUserId;
/*     */   }
/*     */ 
/*     */   public String getPurchaseId()
/*     */   {
/* 184 */     return this.m_purchaseId;
/*     */   }
/*     */ 
/*     */   public void setPurchaseId(String a_purchaseId)
/*     */   {
/* 193 */     this.m_purchaseId = a_purchaseId;
/*     */   }
/*     */ 
/*     */   public String getEmailAddress()
/*     */   {
/* 209 */     return this.m_emailAddress;
/*     */   }
/*     */ 
/*     */   public void setEmailAddress(String a_sEmailAddress)
/*     */   {
/* 218 */     this.m_emailAddress = a_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public BrightMoney getChargedAmount()
/*     */   {
/* 234 */     return this.m_chargedAmount;
/*     */   }
/*     */ 
/*     */   public void setChargedAmount(BrightMoney a_sChargedAmount)
/*     */   {
/* 243 */     this.m_chargedAmount = a_sChargedAmount;
/*     */   }
/*     */ 
/*     */   public String getLoginUser()
/*     */   {
/* 259 */     return this.m_loginUser;
/*     */   }
/*     */ 
/*     */   public void setLoginUser(String a_sLoginUser)
/*     */   {
/* 268 */     this.m_loginUser = a_sLoginUser;
/*     */   }
/*     */ 
/*     */   public String getLoginPassword()
/*     */   {
/* 284 */     return this.m_loginPassword;
/*     */   }
/*     */ 
/*     */   public void setLoginPassword(String a_sLoginPassword)
/*     */   {
/* 293 */     this.m_loginPassword = a_sLoginPassword;
/*     */   }
/*     */ 
/*     */   public Date getDateCreated()
/*     */   {
/* 309 */     return this.m_dateCreated;
/*     */   }
/*     */ 
/*     */   public void setDateCreated(Date a_sDateCreated)
/*     */   {
/* 318 */     this.m_dateCreated = a_sDateCreated;
/*     */   }
/*     */ 
/*     */   public PaymentCallbackProcessor getRefPaymentProcessor()
/*     */   {
/* 334 */     return this.m_refPaymentProcessor;
/*     */   }
/*     */ 
/*     */   public void setRefPaymentProcessor(PaymentCallbackProcessor a_sRefPaymentProcessor)
/*     */   {
/* 343 */     this.m_refPaymentProcessor = a_sRefPaymentProcessor;
/*     */   }
/*     */ 
/*     */   public PspPaymentReturn getPaymentReturn()
/*     */   {
/* 349 */     return this.m_paymentReturn;
/*     */   }
/*     */ 
/*     */   public void setPaymentReturn(PspPaymentReturn a_sPaymentReturn) {
/* 353 */     this.m_paymentReturn = a_sPaymentReturn;
/*     */   }
/*     */ 
/*     */   public ABUser getRegisterUser()
/*     */   {
/* 358 */     return this.m_registerUser;
/*     */   }
/*     */ 
/*     */   public void setRegisterUser(ABUser a_sRegisterUser)
/*     */   {
/* 363 */     this.m_registerUser = a_sRegisterUser;
/*     */   }
/*     */ 
/*     */   public BrightMoney getSubtotal()
/*     */   {
/* 368 */     return this.m_subtotal;
/*     */   }
/*     */ 
/*     */   public void setSubtotal(BrightMoney a_sSubtotal)
/*     */   {
/* 373 */     this.m_subtotal = a_sSubtotal;
/*     */   }
/*     */ 
/*     */   public BrightDecimal getVatPercent()
/*     */   {
/* 378 */     return this.m_vatPercent;
/*     */   }
/*     */ 
/*     */   public void setVatPercent(BrightDecimal a_sVatPercent)
/*     */   {
/* 383 */     this.m_vatPercent = a_sVatPercent;
/*     */   }
/*     */ 
/*     */   public boolean isPrefersOfflinePayment()
/*     */   {
/* 391 */     return this.m_bPrefersOfflinePayment;
/*     */   }
/*     */ 
/*     */   public void setPrefersOfflinePayment(boolean a_dtPrefersOfflinePayment)
/*     */   {
/* 399 */     this.m_bPrefersOfflinePayment = a_dtPrefersOfflinePayment;
/*     */   }
/*     */ 
/*     */   public String getUserNotes()
/*     */   {
/* 407 */     return this.m_sUserNotes;
/*     */   }
/*     */ 
/*     */   public void setUserNotes(String a_dtUserNotes)
/*     */   {
/* 415 */     this.m_sUserNotes = a_dtUserNotes;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.Purchase
 * JD-Core Version:    0.6.0
 */