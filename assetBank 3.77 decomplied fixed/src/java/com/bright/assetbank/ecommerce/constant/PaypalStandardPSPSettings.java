/*     */ package com.bright.assetbank.ecommerce.constant;
/*     */ 
/*     */ public class PaypalStandardPSPSettings extends PSPSettings
/*     */ {
/*     */   public PaypalStandardPSPSettings(String a_sSettingsFilename)
/*     */   {
/*  32 */     super(a_sSettingsFilename);
/*     */   }
/*     */ 
/*     */   public String getPaypalAccountKey()
/*     */   {
/*  37 */     return getStringSetting("key-account");
/*     */   }
/*     */ 
/*     */   public String getPaypalAccount()
/*     */   {
/*  42 */     return getStringSetting("account");
/*     */   }
/*     */ 
/*     */   public String getPaypalItemNameKey()
/*     */   {
/*  47 */     return getStringSetting("key-item-name");
/*     */   }
/*     */ 
/*     */   public String getPaypalItemNumberKey()
/*     */   {
/*  52 */     return getStringSetting("key-item-number");
/*     */   }
/*     */ 
/*     */   public String getPaypalCurrencyCodeKey()
/*     */   {
/*  57 */     return getStringSetting("key-currency");
/*     */   }
/*     */ 
/*     */   public String getPaypalCurrencyCode()
/*     */   {
/*  62 */     return getStringSetting("currency");
/*     */   }
/*     */ 
/*     */   public String getPaypalCommandKey()
/*     */   {
/*  67 */     return getStringSetting("key-command");
/*     */   }
/*     */ 
/*     */   public String getPaypalCommand()
/*     */   {
/*  72 */     return getStringSetting("command");
/*     */   }
/*     */ 
/*     */   public String getPaypalCustomKey()
/*     */   {
/*  77 */     return getStringSetting("key-custom");
/*     */   }
/*     */ 
/*     */   public String getPaypalAmountKey()
/*     */   {
/*  82 */     return getStringSetting("key-amount");
/*     */   }
/*     */ 
/*     */   public String getPaypalReturnAmountKey()
/*     */   {
/*  87 */     return getStringSetting("key-return-amount");
/*     */   }
/*     */ 
/*     */   public String getPaypalNotifyUrlKey()
/*     */   {
/*  92 */     return getStringSetting("key-notify-url");
/*     */   }
/*     */ 
/*     */   public String getPaypalPaymentStatusKey()
/*     */   {
/*  97 */     return getStringSetting("key-payment-status");
/*     */   }
/*     */ 
/*     */   public String getPaypalReturnUrlKey()
/*     */   {
/* 102 */     return getStringSetting("key-return-url");
/*     */   }
/*     */ 
/*     */   public String getPaypalReturnUrl()
/*     */   {
/* 107 */     return getStringSetting("paypal-return-url");
/*     */   }
/*     */ 
/*     */   public String getPaypalUrl()
/*     */   {
/* 112 */     return getStringSetting("paypal-url");
/*     */   }
/*     */ 
/*     */   public String getPaypalPaymentStatusSuccess()
/*     */   {
/* 117 */     return getStringSetting("payment-status-success");
/*     */   }
/*     */ 
/*     */   public String getPaypalPaymentStatusFailure()
/*     */   {
/* 122 */     return getStringSetting("payment-status-failure");
/*     */   }
/*     */ 
/*     */   public String getPaypalPaymentStatusPending()
/*     */   {
/* 127 */     return getStringSetting("payment-status-pending");
/*     */   }
/*     */ 
/*     */   public String getPaypalTransactionIdKey()
/*     */   {
/* 132 */     return getStringSetting("key-psp-transaction-id-key");
/*     */   }
/*     */ 
/*     */   public String getPaypalCancelReturnUrlKey()
/*     */   {
/* 137 */     return getStringSetting("key-cancel-return-url");
/*     */   }
/*     */ 
/*     */   public String getPaypalCancelReturnUrl()
/*     */   {
/* 142 */     return getStringSetting("paypal-cancel-url");
/*     */   }
/*     */ 
/*     */   public String getSharedSecretParameter()
/*     */   {
/* 147 */     return getStringSetting("shared-secret-param");
/*     */   }
/*     */ 
/*     */   public String getSharedSecret()
/*     */   {
/* 152 */     return getStringSetting("shared-secret");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.constant.PaypalStandardPSPSettings
 * JD-Core Version:    0.6.0
 */