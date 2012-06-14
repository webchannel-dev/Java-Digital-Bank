/*     */ package com.bright.assetbank.ecommerce.constant;
/*     */ 
/*     */ public class TNSCommsXLSettings extends PSPSettings
/*     */ {
/*     */   public TNSCommsXLSettings(String a_sSettingsFilename)
/*     */   {
/*  29 */     super(a_sSettingsFilename);
/*     */   }
/*     */ 
/*     */   public String getMerchantId()
/*     */   {
/*  34 */     return getStringSetting("merchant-id");
/*     */   }
/*     */ 
/*     */   public String getVPCVersion()
/*     */   {
/*  39 */     return getStringSetting("vpc-version");
/*     */   }
/*     */ 
/*     */   public String getVPCCommand()
/*     */   {
/*  44 */     return getStringSetting("vpc-command");
/*     */   }
/*     */ 
/*     */   public String getVPCAccessCode()
/*     */   {
/*  49 */     return getStringSetting("vpc-accesscode");
/*     */   }
/*     */ 
/*     */   public String getVPCLocale()
/*     */   {
/*  54 */     return getStringSetting("vpc-locale");
/*     */   }
/*     */ 
/*     */   public String getPaymentUrl()
/*     */   {
/*  59 */     return getStringSetting("payment-url");
/*     */   }
/*     */ 
/*     */   public String getSecureHashSecret()
/*     */   {
/*  64 */     return getStringSetting("secure-hash-secret");
/*     */   }
/*     */ 
/*     */   public String getParamSecureHash()
/*     */   {
/*  70 */     return getStringSetting("param-secure-hash");
/*     */   }
/*     */ 
/*     */   public String getParamTransactionRef()
/*     */   {
/*  75 */     return getStringSetting("param-transaction-ref");
/*     */   }
/*     */ 
/*     */   public String getParamAmount()
/*     */   {
/*  80 */     return getStringSetting("param-amount");
/*     */   }
/*     */ 
/*     */   public String getParamMerchant()
/*     */   {
/*  85 */     return getStringSetting("param-merchant");
/*     */   }
/*     */ 
/*     */   public String getParamReturnURL()
/*     */   {
/*  90 */     return getStringSetting("param-return-url");
/*     */   }
/*     */ 
/*     */   public String getParamOrderInfo()
/*     */   {
/*  95 */     return getStringSetting("param-order-info");
/*     */   }
/*     */ 
/*     */   public String getParamVersion()
/*     */   {
/* 100 */     return getStringSetting("param-version");
/*     */   }
/*     */ 
/*     */   public String getParamCommand()
/*     */   {
/* 105 */     return getStringSetting("param-command");
/*     */   }
/*     */ 
/*     */   public String getParamAccessCode()
/*     */   {
/* 110 */     return getStringSetting("param-access-code");
/*     */   }
/*     */ 
/*     */   public String getParamLocale()
/*     */   {
/* 115 */     return getStringSetting("param-locale");
/*     */   }
/*     */ 
/*     */   public String getParamTransactionResponse()
/*     */   {
/* 120 */     return getStringSetting("param-transaction-response");
/*     */   }
/*     */ 
/*     */   public String getParamTransactionNo()
/*     */   {
/* 125 */     return getStringSetting("param-transaction-no");
/*     */   }
/*     */ 
/*     */   public String getReturnCode_Success()
/*     */   {
/* 130 */     return getStringSetting("return-code-success");
/*     */   }
/*     */ 
/*     */   public String getReturnCode_Cancel()
/*     */   {
/* 135 */     return getStringSetting("return-code-cancel");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.constant.TNSCommsXLSettings
 * JD-Core Version:    0.6.0
 */