/*     */ package com.bright.assetbank.ecommerce.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ 
/*     */ public class PspPaymentReturn
/*     */ {
/*     */   private boolean m_bCancelled;
/*     */   private String m_sSignature;
/*     */   private String m_sAuthNumber;
/*     */   private String m_sPspUserId;
/*     */   private boolean m_sValid;
/*     */   private String m_transactionId;
/*     */   private String m_pspTransactionId;
/*     */   private BrightMoney m_chargedAmount;
/*     */ 
/*     */   public PspPaymentReturn()
/*     */   {
/*  42 */     this.m_chargedAmount = new BrightMoney();
/*     */   }
/*     */ 
/*     */   public boolean getValid()
/*     */   {
/*  58 */     return this.m_sValid;
/*     */   }
/*     */ 
/*     */   public void setValid(boolean a_sValid)
/*     */   {
/*  67 */     this.m_sValid = a_sValid;
/*     */   }
/*     */ 
/*     */   public String getTransactionId()
/*     */   {
/*  83 */     return this.m_transactionId;
/*     */   }
/*     */ 
/*     */   public void setTransactionId(String a_sTransactionId)
/*     */   {
/*  92 */     this.m_transactionId = a_sTransactionId;
/*     */   }
/*     */ 
/*     */   public String getPspTransactionId()
/*     */   {
/* 108 */     return this.m_pspTransactionId;
/*     */   }
/*     */ 
/*     */   public void setPspTransactionId(String a_sPspTransactionId)
/*     */   {
/* 117 */     this.m_pspTransactionId = a_sPspTransactionId;
/*     */   }
/*     */ 
/*     */   public BrightMoney getChargedAmount()
/*     */   {
/* 133 */     return this.m_chargedAmount;
/*     */   }
/*     */ 
/*     */   public void setChargedAmount(BrightMoney a_sChargedAmount)
/*     */   {
/* 142 */     this.m_chargedAmount = a_sChargedAmount;
/*     */   }
/*     */ 
/*     */   public boolean getCancelled()
/*     */   {
/* 148 */     return this.m_bCancelled;
/*     */   }
/*     */ 
/*     */   public void setCancelled(boolean a_sCancelled) {
/* 152 */     this.m_bCancelled = a_sCancelled;
/*     */   }
/*     */ 
/*     */   public String getSignature()
/*     */   {
/* 158 */     return this.m_sSignature;
/*     */   }
/*     */ 
/*     */   public void setSignature(String a_sSignature) {
/* 162 */     this.m_sSignature = a_sSignature;
/*     */   }
/*     */ 
/*     */   public String getAuthNumber()
/*     */   {
/* 167 */     return this.m_sAuthNumber;
/*     */   }
/*     */ 
/*     */   public void setAuthNumber(String a_sAuthNumber) {
/* 171 */     this.m_sAuthNumber = a_sAuthNumber;
/*     */   }
/*     */ 
/*     */   public String getPspUserId()
/*     */   {
/* 179 */     return this.m_sPspUserId;
/*     */   }
/*     */ 
/*     */   public void setPspUserId(String a_sPspUserId)
/*     */   {
/* 188 */     this.m_sPspUserId = a_sPspUserId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.PspPaymentReturn
 * JD-Core Version:    0.6.0
 */