/*    */ package com.bright.assetbank.ecommerce.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class EcommerceSettings extends GlobalSettings
/*    */ {
/*    */   public static boolean getECommerce()
/*    */   {
/* 31 */     return getInstance().getBooleanSetting("ecommerce");
/*    */   }
/*    */ 
/*    */   public static int getPurchaseExpiryPeriod()
/*    */   {
/* 36 */     return getInstance().getIntSetting("purchase-expiry-period");
/*    */   }
/*    */ 
/*    */   public static boolean getPurchaseShowTCs()
/*    */   {
/* 41 */     return getInstance().getBooleanSetting("purchase-show-tcs");
/*    */   }
/*    */ 
/*    */   public static String getPspPluginClass()
/*    */   {
/* 46 */     return getInstance().getStringSetting("psp-plugin-class");
/*    */   }
/*    */ 
/*    */   public static String getPspCallbackUrl()
/*    */   {
/* 51 */     return getInstance().getStringSetting("psp-callback-url");
/*    */   }
/*    */ 
/*    */   public static boolean getEcommerceOfflineOption()
/*    */   {
/* 56 */     return getInstance().getBooleanSetting("ecommerce-offline-option");
/*    */   }
/*    */ 
/*    */   public static String getPspDescStem()
/*    */   {
/* 61 */     return getInstance().getStringSetting("psp-desc-stem");
/*    */   }
/*    */ 
/*    */   public static float getVATPercent()
/*    */   {
/* 66 */     return getInstance().getFloatSetting("vat");
/*    */   }
/*    */ 
/*    */   public static String getVATName()
/*    */   {
/* 71 */     return getInstance().getStringSetting("vat-name");
/*    */   }
/*    */ 
/*    */   public static String getPspSecret()
/*    */   {
/* 76 */     return getInstance().getStringSetting("psp-secret");
/*    */   }
/*    */ 
/*    */   public static String getPspSecretCallback()
/*    */   {
/* 81 */     return getInstance().getStringSetting("psp-secret-callback");
/*    */   }
/*    */ 
/*    */   public static String getPurchaseIdStem()
/*    */   {
/* 86 */     return getInstance().getStringSetting("ecommerce-purchaseid-stem");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.constant.EcommerceSettings
 * JD-Core Version:    0.6.0
 */