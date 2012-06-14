/*    */ package com.bright.framework.user.sso;
/*    */ 
/*    */ import com.bright.framework.common.constant.Settings;
/*    */ 
/*    */ public class OpenIdSSOSettings extends Settings
/*    */ {
/*    */   public OpenIdSSOSettings(String a_sSettingsFilename)
/*    */   {
/* 25 */     super(a_sSettingsFilename);
/*    */   }
/*    */ 
/*    */   public String getLoginJsp()
/*    */   {
/* 30 */     return getStringSetting("login-jsp");
/*    */   }
/*    */ 
/*    */   public String getOpenIdPrefix()
/*    */   {
/* 35 */     return getStringSetting("open-id-prefix");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.OpenIdSSOSettings
 * JD-Core Version:    0.6.0
 */