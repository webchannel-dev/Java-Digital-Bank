/*     */ package com.bright.framework.activedirectory.constant;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ 
/*     */ public class ActiveDirectorySettings extends GlobalSettings
/*     */ {
/*     */   public static boolean getSuspendADAuthentication()
/*     */   {
/*  30 */     return getInstance().getBooleanSetting("suspend-ad-authentication");
/*     */   }
/*     */ 
/*     */   public static boolean getHideNewADUsers()
/*     */   {
/*  35 */     return getInstance().getBooleanSetting("hide-new-ad-users");
/*     */   }
/*     */ 
/*     */   public static String getAdDelimiter()
/*     */   {
/*  40 */     return getInstance().getStringSetting("ad-delimiter");
/*     */   }
/*     */ 
/*     */   public static String getAdUsernameProperty()
/*     */   {
/*  45 */     return getInstance().getStringSetting("ad-username-property");
/*     */   }
/*     */ 
/*     */   public static String getAdDistinguishedNameProperty()
/*     */   {
/*  50 */     return getInstance().getStringSetting("ad-distinguished-name-property");
/*     */   }
/*     */ 
/*     */   public static String getAdUserSearchCriteria()
/*     */   {
/*  55 */     return getInstance().getStringSetting("ad-user-search-criteria");
/*     */   }
/*     */ 
/*     */   public static String getAdEmailProperty()
/*     */   {
/*  60 */     return getInstance().getStringSetting("ad-email-property");
/*     */   }
/*     */ 
/*     */   public static String getAdEmailSuffix()
/*     */   {
/*  65 */     return getInstance().getStringSetting("ad-email-suffix");
/*     */   }
/*     */ 
/*     */   public static boolean getAdLdapSupportsPaging()
/*     */   {
/*  70 */     return getInstance().getBooleanSetting("ad-ldap-supports-paging");
/*     */   }
/*     */ 
/*     */   public static int getAdLdapPageSize()
/*     */   {
/*  75 */     return getInstance().getIntSetting("ad-ldap-page-size");
/*     */   }
/*     */ 
/*     */   public static String getLdapDerefAliases()
/*     */   {
/*  80 */     return getInstance().getStringSetting("ldap-deref-aliases");
/*     */   }
/*     */ 
/*     */   public static int getAdLDAPServerCount()
/*     */   {
/*  85 */     return getInstance().getIntSetting("ad-ldap-server-count");
/*     */   }
/*     */ 
/*     */   public static String getAdLDAPServerUrl(int a_iServerIndex)
/*     */   {
/*  90 */     return getInstance().getStringSetting(getIndexedPropertyName("ad-ldap-server-url", a_iServerIndex));
/*     */   }
/*     */ 
/*     */   public static String getAdLdapBaseList(int a_iServerIndex)
/*     */   {
/*  95 */     return getInstance().getStringSetting(getIndexedPropertyName("ad-ldap-base-list", a_iServerIndex));
/*     */   }
/*     */ 
/*     */   public static String getAdLdapOnFlyBaseList(int a_iServerIndex)
/*     */   {
/* 100 */     return getInstance().getStringSetting(getIndexedPropertyName("ad-ldap-on-the-fly-base-list", a_iServerIndex));
/*     */   }
/*     */ 
/*     */   public static String getAdWmsUserDistinguishedName(int a_iServerIndex)
/*     */   {
/* 105 */     return getInstance().getStringSetting(getIndexedPropertyName("ad-wms-user-distinguished-name", a_iServerIndex));
/*     */   }
/*     */ 
/*     */   public static String getAdWmsUserPassword(int a_iServerIndex)
/*     */   {
/* 110 */     return getInstance().getStringSetting(getIndexedPropertyName("ad-wms-user-password", a_iServerIndex));
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.activedirectory.constant.ActiveDirectorySettings
 * JD-Core Version:    0.6.0
 */