/*     */ package com.bright.framework.user.constant;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ 
/*     */ public class UserSettings extends GlobalSettings
/*     */ {
/*  28 */   private static boolean c_bForcePasswordEncryption = false;
/*     */ 
/*     */   public static void forcePasswordEncryption()
/*     */   {
/*  32 */     c_bForcePasswordEncryption = true;
/*     */   }
/*     */ 
/*     */   public static String getDefaultPassword()
/*     */   {
/*  37 */     return getInstance().getStringSetting("default-password");
/*     */   }
/*     */ 
/*     */   public static boolean getEncryptPasswords()
/*     */   {
/*  42 */     if (!c_bForcePasswordEncryption)
/*     */     {
/*  44 */       return getInstance().getBooleanSetting("encrypt-passwords");
/*     */     }
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean getAllowAutoLoginUsingCookie()
/*     */   {
/*  51 */     return getInstance().getBooleanSetting("allowAutoLoginUsingCookie");
/*     */   }
/*     */ 
/*     */   public static boolean getUserDetailsCookieEnabled()
/*     */   {
/*  56 */     return getInstance().getBooleanSetting("user-details-cookie");
/*     */   }
/*     */ 
/*     */   public static String getUserDetailsCookieKey()
/*     */   {
/*  61 */     return getInstance().getStringSetting("user-details-cookie-key");
/*     */   }
/*     */ 
/*     */   public static String getUserDetailsCookieIV()
/*     */   {
/*  66 */     return getInstance().getStringSetting("user-details-cookie-IV");
/*     */   }
/*     */ 
/*     */   public static String getPostLoginRedirectExceptions()
/*     */   {
/*  71 */     return getInstance().getStringSetting("post-login-redirect-exceptions");
/*     */   }
/*     */ 
/*     */   public static String getLoginActions()
/*     */   {
/*  76 */     return getInstance().getStringSetting("login-actions");
/*     */   }
/*     */ 
/*     */   public static String getRegistrationActions()
/*     */   {
/*  81 */     return getInstance().getStringSetting("registration-actions");
/*     */   }
/*     */ 
/*     */   public static String getChangePasswordAction()
/*     */   {
/*  86 */     return getInstance().getStringSetting("change-password-action");
/*     */   }
/*     */ 
/*     */   public static String getSSOPluginClass()
/*     */   {
/*  91 */     return getInstance().getStringSetting("sso-plugin-class");
/*     */   }
/*     */ 
/*     */   public static boolean getSSOLoginIsDefault()
/*     */   {
/*  96 */     return getInstance().getBooleanSetting("sso-login-is-default");
/*     */   }
/*     */ 
/*     */   public static boolean getSSOIsEnabled()
/*     */   {
/* 101 */     return getInstance().getBooleanSetting("sso-enabled");
/*     */   }
/*     */ 
/*     */   public static boolean getOpenIdEnabled()
/*     */   {
/* 106 */     return (getSSOIsEnabled()) && (getSSOPluginClass().equals("OpenIdSSOPlugin"));
/*     */   }
/*     */ 
/*     */   public static boolean getEncryptedSSOEnabled()
/*     */   {
/* 111 */     return (getSSOIsEnabled()) && (getSSOPluginClass().equals("EncryptedUrlSSOPlugin"));
/*     */   }
/*     */ 
/*     */   public static boolean getSagePassportEnabled()
/*     */   {
/* 116 */     return (getSSOIsEnabled()) && (getSSOPluginClass().equals("SagePassportSSOPlugin"));
/*     */   }
/*     */ 
/*     */   public static boolean getWindowsIntegratedAuthenticationEnabled()
/*     */   {
/* 121 */     return (getSSOIsEnabled()) && (getSSOPluginClass().equals("WIASSOPlugin"));
/*     */   }
/*     */ 
/*     */   public static boolean getCookieTokenEnabled()
/*     */   {
/* 126 */     return (getSSOIsEnabled()) && (getSSOPluginClass().equals("CookieTokenSSOPlugin"));
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.constant.UserSettings
 * JD-Core Version:    0.6.0
 */