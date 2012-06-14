/*     */ package com.bright.assetbank.ecommerce.constant;
/*     */ 
/*     */ public class WorldpaySettings extends PSPSettings
/*     */ {
/*     */   public WorldpaySettings(String a_sSettingsFilename)
/*     */   {
/*  29 */     super(a_sSettingsFilename);
/*     */   }
/*     */ 
/*     */   public String getWorldpayGateway()
/*     */   {
/*  34 */     return getStringSetting("worldpay-gateway");
/*     */   }
/*     */ 
/*     */   public String getWorldpayMerchantKey()
/*     */   {
/*  39 */     return getStringSetting("worldpay-merchant-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayMerchantValue()
/*     */   {
/*  44 */     return getStringSetting("worldpay-merchant-value");
/*     */   }
/*     */ 
/*     */   public String getWorldpayTransidKey()
/*     */   {
/*  49 */     return getStringSetting("worldpay-transid-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayAmountKey()
/*     */   {
/*  54 */     return getStringSetting("worldpay-amount-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayCurrencyKey()
/*     */   {
/*  59 */     return getStringSetting("worldpay-currency-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayCurrencyValue()
/*     */   {
/*  64 */     return getStringSetting("worldpay-currency-value");
/*     */   }
/*     */ 
/*     */   public String getWorldpayDescKey()
/*     */   {
/*  69 */     return getStringSetting("worldpay-desc-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayTestModeKey()
/*     */   {
/*  74 */     return getStringSetting("worldpay-testmode-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayTestModeValue()
/*     */   {
/*  79 */     return getStringSetting("worldpay-testmode-value");
/*     */   }
/*     */ 
/*     */   public String getWorldpaySignatureFields()
/*     */   {
/*  84 */     return getStringSetting("worldpay-signature-fields");
/*     */   }
/*     */ 
/*     */   public String getWorldpaySignatureFieldsKey()
/*     */   {
/*  89 */     return getStringSetting("worldpay-signature-fields-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpaySignatureKey()
/*     */   {
/*  94 */     return getStringSetting("worldpay-signature-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayCallbackValidKey()
/*     */   {
/* 103 */     return getStringSetting("worldpay-callback-valid-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayCallbackValidTrue()
/*     */   {
/* 108 */     return getStringSetting("worldpay-callback-valid-true");
/*     */   }
/*     */ 
/*     */   public String getWorldpayCallbackValidCancel()
/*     */   {
/* 113 */     return getStringSetting("worldpay-callback-valid-cancel");
/*     */   }
/*     */ 
/*     */   public String getWorldpayCallbackTransidKey()
/*     */   {
/* 118 */     return getStringSetting("worldpay-callback-transid-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayCallbackAmountKey()
/*     */   {
/* 123 */     return getStringSetting("worldpay-callback-amount-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayCallbackWorldpayTransidKey()
/*     */   {
/* 128 */     return getStringSetting("worldpay-callback-worldpay-transid-key");
/*     */   }
/*     */ 
/*     */   public String getWorldpayPassSignatureKey()
/*     */   {
/* 136 */     return getStringSetting("worldpay-pass-signature-key");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.constant.WorldpaySettings
 * JD-Core Version:    0.6.0
 */