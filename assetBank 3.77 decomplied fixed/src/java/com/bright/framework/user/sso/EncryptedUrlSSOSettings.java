/*     */ package com.bright.framework.user.sso;
/*     */ 
/*     */ import com.bright.framework.common.constant.Settings;
/*     */ 
/*     */ public class EncryptedUrlSSOSettings extends Settings
/*     */ {
/*     */   public EncryptedUrlSSOSettings(String a_sSettingsFilename)
/*     */   {
/*  25 */     super(a_sSettingsFilename);
/*     */   }
/*     */ 
/*     */   public String getEncryptedStringParamName()
/*     */   {
/*  30 */     return getStringSetting("encrypted-string-param");
/*     */   }
/*     */ 
/*     */   public String getEncryptionKey()
/*     */   {
/*  35 */     return getStringSetting("encryption-key");
/*     */   }
/*     */ 
/*     */   public boolean getEncryptionKeyIsBase64()
/*     */   {
/*  40 */     return getBooleanSetting("encryption-key-is-base64");
/*     */   }
/*     */ 
/*     */   public boolean getEncryptedValueIsBase64()
/*     */   {
/*  45 */     return getBooleanSetting("encrypted-value-is-base64");
/*     */   }
/*     */ 
/*     */   public String getEncoding()
/*     */   {
/*  50 */     return getStringSetting("encoding");
/*     */   }
/*     */ 
/*     */   public String getCipherEncoding()
/*     */   {
/*  55 */     return getStringSetting("cipher-encoding");
/*     */   }
/*     */ 
/*     */   public boolean getUseIV()
/*     */   {
/*  60 */     return getBooleanSetting("use-iv");
/*     */   }
/*     */ 
/*     */   public String getIV()
/*     */   {
/*  65 */     return getStringSetting("iv");
/*     */   }
/*     */ 
/*     */   public boolean getIVIsBase64()
/*     */   {
/*  70 */     return getBooleanSetting("iv-is-base64");
/*     */   }
/*     */ 
/*     */   public String getUsernameDateSeparator()
/*     */   {
/*  75 */     return getStringSetting("username-date-separator");
/*     */   }
/*     */ 
/*     */   public String getValueSeparator()
/*     */   {
/*  80 */     return getStringSetting("value-separator");
/*     */   }
/*     */ 
/*     */   public String getUsernameParameter()
/*     */   {
/*  85 */     return getStringSetting("username-param");
/*     */   }
/*     */ 
/*     */   public String getEmailParameter()
/*     */   {
/*  90 */     return getStringSetting("email-param");
/*     */   }
/*     */ 
/*     */   public String getDateParameter()
/*     */   {
/*  95 */     return getStringSetting("date-param");
/*     */   }
/*     */ 
/*     */   public String getDateFormat()
/*     */   {
/* 100 */     return getStringSetting("date-format");
/*     */   }
/*     */ 
/*     */   public boolean getPerformDateCheck()
/*     */   {
/* 105 */     return getBooleanSetting("perform-date-check");
/*     */   }
/*     */ 
/*     */   public int getDateTimeTolerance()
/*     */   {
/* 110 */     return getIntSetting("date-time-tolerance");
/*     */   }
/*     */ 
/*     */   public String getUsernameNotFoundUrl()
/*     */   {
/* 115 */     return getStringSetting("username-not-found-url");
/*     */   }
/*     */ 
/*     */   public boolean getUsernameIsEmail()
/*     */   {
/* 120 */     return getBooleanSetting("username-is-email");
/*     */   }
/*     */ 
/*     */   public boolean getValidateEmailAddress()
/*     */   {
/* 125 */     return getBooleanSetting("validate-email-address");
/*     */   }
/*     */ 
/*     */   public String getValidEmailDomains()
/*     */   {
/* 130 */     return getStringSetting("valid-email-domains");
/*     */   }
/*     */ 
/*     */   public String getForenameParameter()
/*     */   {
/* 135 */     return getStringSetting("forename-param");
/*     */   }
/*     */ 
/*     */   public String getSurnameParameter()
/*     */   {
/* 140 */     return getStringSetting("surname-param");
/*     */   }
/*     */ 
/*     */   public String getGroupsParameter()
/*     */   {
/* 145 */     return getStringSetting("groups-param");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.EncryptedUrlSSOSettings
 * JD-Core Version:    0.6.0
 */