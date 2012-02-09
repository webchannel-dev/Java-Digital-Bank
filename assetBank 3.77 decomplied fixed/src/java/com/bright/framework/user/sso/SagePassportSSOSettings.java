/*     */ package com.bright.framework.user.sso;
/*     */ 
/*     */ import com.bright.framework.common.constant.Settings;
/*     */ 
/*     */ public class SagePassportSSOSettings extends Settings
/*     */ {
/*     */   public SagePassportSSOSettings(String a_sSettingsFilename)
/*     */   {
/*  25 */     super(a_sSettingsFilename);
/*     */   }
/*     */ 
/*     */   public String getLoginJsp()
/*     */   {
/*  30 */     return getStringSetting("login-jsp");
/*     */   }
/*     */ 
/*     */   public String getLogoutUrl()
/*     */   {
/*  35 */     return getStringSetting("logout-url");
/*     */   }
/*     */ 
/*     */   public String getLogoutImagePath()
/*     */   {
/*  40 */     return getStringSetting("logout-image-path");
/*     */   }
/*     */ 
/*     */   public String getOperationId()
/*     */   {
/*  45 */     return getStringSetting("operationid");
/*     */   }
/*     */ 
/*     */   public String getSagePassportUrl()
/*     */   {
/*  50 */     return getStringSetting("sage-passport-url");
/*     */   }
/*     */ 
/*     */   public String getCookieName()
/*     */   {
/*  55 */     return getStringSetting("cookie-name");
/*     */   }
/*     */ 
/*     */   public String getQueryMemberXml()
/*     */   {
/*  60 */     return getStringSetting("querymember-xml");
/*     */   }
/*     */ 
/*     */   public String getCookieXmlEmail()
/*     */   {
/*  65 */     return getStringSetting("cookie-xml-email");
/*     */   }
/*     */ 
/*     */   public String getCookieXmlName()
/*     */   {
/*  70 */     return getStringSetting("cookie-xml-name");
/*     */   }
/*     */ 
/*     */   public String getCookieXmlMemberId()
/*     */   {
/*  75 */     return getStringSetting("cookie-xml-memberid");
/*     */   }
/*     */ 
/*     */   public String getQueryMemberUrl()
/*     */   {
/*  80 */     return getStringSetting("querymember-url");
/*     */   }
/*     */ 
/*     */   public String getQueryMemberXmlState()
/*     */   {
/*  85 */     return getStringSetting("querymember-xml-state");
/*     */   }
/*     */ 
/*     */   public String getQueryMemberValid()
/*     */   {
/*  90 */     return getStringSetting("querymember-user-valid");
/*     */   }
/*     */ 
/*     */   public String getCookieDomain()
/*     */   {
/*  95 */     return getStringSetting("cookie-domain");
/*     */   }
/*     */ 
/*     */   public String getCookiePath()
/*     */   {
/* 100 */     return getStringSetting("cookie-path");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.SagePassportSSOSettings
 * JD-Core Version:    0.6.0
 */