/*     */ package com.bright.assetbank.ecommerce.constant;
/*     */ 
/*     */ public class NetbanxSettings extends PSPSettings
/*     */ {
/*     */   public NetbanxSettings(String a_sSettingsFilename)
/*     */   {
/*  23 */     super(a_sSettingsFilename);
/*     */   }
/*     */ 
/*     */   public String getNetbanxGateway()
/*     */   {
/*  29 */     return getStringSetting("netbanx-gateway");
/*     */   }
/*     */ 
/*     */   public String getMerchantValue()
/*     */   {
/*  34 */     return getStringSetting("merchant-value");
/*     */   }
/*     */ 
/*     */   public String getAmountKey()
/*     */   {
/*  40 */     return getStringSetting("amount-key");
/*     */   }
/*     */ 
/*     */   public String getCurrencyCodeKey()
/*     */   {
/*  45 */     return getStringSetting("currency-code-key");
/*     */   }
/*     */ 
/*     */   public String getCurrencyCodeValue()
/*     */   {
/*  50 */     return getStringSetting("currency-code-value");
/*     */   }
/*     */ 
/*     */   public String getTransactionIdKey()
/*     */   {
/*  55 */     return getStringSetting("transid-key");
/*     */   }
/*     */ 
/*     */   public String getPassthruTransactionIdKey()
/*     */   {
/*  60 */     return getStringSetting("passthru-transid-key");
/*     */   }
/*     */ 
/*     */   public String getChecksumKey()
/*     */   {
/*  65 */     return getStringSetting("checksum-key");
/*     */   }
/*     */ 
/*     */   public String getCallbackSuccessUrlKey()
/*     */   {
/*  70 */     return getStringSetting("callback-success-url-key");
/*     */   }
/*     */ 
/*     */   public String getCallbackSuccessUrlValue() {
/*  74 */     return getStringSetting("callback-success-url-value");
/*     */   }
/*     */ 
/*     */   public String getCallbackFailureUrlKey()
/*     */   {
/*  80 */     return getStringSetting("callback-failure-url-key");
/*     */   }
/*     */ 
/*     */   public String getCallbackFailureUrlValue() {
/*  84 */     return getStringSetting("callback-failure-url-value");
/*     */   }
/*     */ 
/*     */   public String getRedirectSuccessUrlKey()
/*     */   {
/*  89 */     return getStringSetting("redirect-success-url-key");
/*     */   }
/*     */ 
/*     */   public String getRedirectSuccessUrlValue() {
/*  93 */     return getStringSetting("redirect-success-url-value");
/*     */   }
/*     */ 
/*     */   public String getRedirectFailureUrlKey()
/*     */   {
/*  98 */     return getStringSetting("redirect-failure-url-key");
/*     */   }
/*     */ 
/*     */   public String getRedirectFailureUrlValue() {
/* 102 */     return getStringSetting("redirect-failure-url-value");
/*     */   }
/*     */ 
/*     */   public String getReturnUrlKey()
/*     */   {
/* 107 */     return getStringSetting("return-url-key");
/*     */   }
/*     */ 
/*     */   public String getReturnUrlValue() {
/* 111 */     return getStringSetting("return-url-value");
/*     */   }
/*     */ 
/*     */   public String getUserEmailKey()
/*     */   {
/* 116 */     return getStringSetting("user-email-key");
/*     */   }
/*     */ 
/*     */   public String getSynchronousCallbackKey()
/*     */   {
/* 121 */     return getStringSetting("synchronous-callback-key");
/*     */   }
/*     */ 
/*     */   public String getSynchronousCallbackValue() {
/* 125 */     return getStringSetting("synchronous-callback-value");
/*     */   }
/*     */ 
/*     */   public String getCallbackStatusKey()
/*     */   {
/* 130 */     return getStringSetting("callback-status-key");
/*     */   }
/*     */ 
/*     */   public String getCallbackStatusSuccessValue() {
/* 134 */     return getStringSetting("callback-status-success");
/*     */   }
/*     */ 
/*     */   public String getCallbackAmountKey()
/*     */   {
/* 139 */     return getStringSetting("callback-amount-key");
/*     */   }
/*     */ 
/*     */   public String getCallbackNetbanxTransidKey()
/*     */   {
/* 144 */     return getStringSetting("callback-netbanx-transid");
/*     */   }
/*     */ 
/*     */   public String getCallbackChecksumKey()
/*     */   {
/* 149 */     return getStringSetting("callback-checksum");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.constant.NetbanxSettings
 * JD-Core Version:    0.6.0
 */