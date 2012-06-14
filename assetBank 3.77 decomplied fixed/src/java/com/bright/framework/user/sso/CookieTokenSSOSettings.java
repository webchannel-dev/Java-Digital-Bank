/*    */ package com.bright.framework.user.sso;
/*    */ 
/*    */ import com.bright.framework.common.constant.Settings;
/*    */ 
/*    */ public class CookieTokenSSOSettings extends Settings
/*    */ {
/*    */   public CookieTokenSSOSettings(String a_sSettingsFilename)
/*    */   {
/* 26 */     super(a_sSettingsFilename);
/*    */   }
/*    */ 
/*    */   public String getScriptUrl()
/*    */   {
/* 31 */     return getStringSetting("script-url");
/*    */   }
/*    */ 
/*    */   public String getLogoutUrl()
/*    */   {
/* 36 */     return getStringSetting("logout-url");
/*    */   }
/*    */ 
/*    */   public String getReturnUrlParameter()
/*    */   {
/* 41 */     return getStringSetting("return-url-param");
/*    */   }
/*    */ 
/*    */   public String getPassThruParameter()
/*    */   {
/* 46 */     return getStringSetting("pass-thru-param");
/*    */   }
/*    */ 
/*    */   public String getSalt()
/*    */   {
/* 51 */     return getStringSetting("salt");
/*    */   }
/*    */ 
/*    */   public String getCookieName()
/*    */   {
/* 56 */     return getStringSetting("cookie-name");
/*    */   }
/*    */ 
/*    */   public String getUsernameParam()
/*    */   {
/* 61 */     return getStringSetting("username");
/*    */   }
/*    */ 
/*    */   public String getFullNameParam()
/*    */   {
/* 66 */     return getStringSetting("full-name");
/*    */   }
/*    */ 
/*    */   public String getEmailParam()
/*    */   {
/* 71 */     return getStringSetting("email");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.CookieTokenSSOSettings
 * JD-Core Version:    0.6.0
 */