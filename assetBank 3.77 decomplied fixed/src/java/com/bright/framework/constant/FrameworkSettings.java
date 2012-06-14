/*     */ package com.bright.framework.constant;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import com.bright.framework.util.DateUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ 
/*     */ public class FrameworkSettings extends GlobalSettings
/*     */   implements FrameworkConstants
/*     */ {
/*     */   public static BrightDateFormat getStandardDateFormat()
/*     */   {
/*  46 */     return DateUtil.getStandardDateFormat();
/*     */   }
/*     */ 
/*     */   public static boolean getUseRelativeDirectories()
/*     */   {
/*  52 */     String sVal = getInstance().getStringSetting("useRelativeDirectories");
/*     */ 
/*  54 */     return (!StringUtil.stringIsPopulated(sVal)) || (!sVal.equals("false"));
/*     */   }
/*     */ 
/*     */   public static String getStringSettingAsAbsoluteDirectory(String a_settingName)
/*     */   {
/*  60 */     String maybeRelativeDirectory = getInstance().getStringSetting(a_settingName);
/*  61 */     return makeAbsolute(maybeRelativeDirectory);
/*     */   }
/*     */ 
/*     */   public static String makeAbsolute(String a_maybeRelativeDirectory)
/*     */   {
/*  66 */     if (getUseRelativeDirectories())
/*     */     {
/*  68 */       return getApplicationPath() + File.separator + a_maybeRelativeDirectory;
/*     */     }
/*     */ 
/*  72 */     return a_maybeRelativeDirectory;
/*     */   }
/*     */ 
/*     */   public static String getEmailAddress()
/*     */   {
/*  78 */     return getInstance().getStringSetting("emailAddress");
/*     */   }
/*     */ 
/*     */   public static String getEmailSMTP()
/*     */   {
/*  83 */     return getInstance().getStringSetting("emailSMTP");
/*     */   }
/*     */ 
/*     */   public static String getEmailSMTPPort()
/*     */   {
/*  88 */     return getInstance().getStringSetting("emailSMTPPort");
/*     */   }
/*     */ 
/*     */   public static String getEmailSMTPUsername()
/*     */   {
/*  93 */     return getInstance().getStringSetting("emailSMTPUsername");
/*     */   }
/*     */ 
/*     */   public static String getEmailSMTPPassword()
/*     */   {
/*  98 */     return getInstance().getStringSetting("emailSMTPPassword");
/*     */   }
/*     */ 
/*     */   public static String getEmailTemplateDirectory()
/*     */   {
/* 103 */     return getInstance().getStringSetting("cmsEmailTemplateDir");
/*     */   }
/*     */ 
/*     */   public static String getMessageTemplateDirectory()
/*     */   {
/* 108 */     return getInstance().getStringSetting("cmsMessageTemplateDir");
/*     */   }
/*     */ 
/*     */   public static String getSampleMessageTemplate()
/*     */   {
/* 113 */     return getInstance().getStringSetting("sampleMessageTemplate");
/*     */   }
/*     */ 
/*     */   public static String getMatchImageFileRegularExpression()
/*     */   {
/* 118 */     return getInstance().getStringSetting("matchImageFileRegularExpression");
/*     */   }
/*     */ 
/*     */   public static String getFullyQualifiedWebServerAppPath()
/*     */   {
/* 123 */     return getInstance().getStringSetting("fullyQualifiedWebServerAppPath");
/*     */   }
/*     */ 
/*     */   public static int getIndexAllBatchSize()
/*     */   {
/* 128 */     return getInstance().getIntSetting("indexAllBatchSize");
/*     */   }
/*     */ 
/*     */   public static boolean getSupportMultiLanguage()
/*     */   {
/* 133 */     return getInstance().getBooleanSetting("supportMultiLanguage");
/*     */   }
/*     */ 
/*     */   public static String getSearchFilestoreRoot()
/*     */   {
/* 138 */     return getInstance().getStringSetting("search-filestore-root");
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static String getFilestoreRoot()
/*     */   {
/* 147 */     return getInstance().getStringSetting("filestore-root");
/*     */   }
/*     */ 
/*     */   public static String getTemporaryDirectory()
/*     */   {
/* 152 */     return getInstance().getStringSetting("temp-dir");
/*     */   }
/*     */ 
/*     */   public static String getShareDirectory()
/*     */   {
/* 157 */     return getInstance().getStringSetting("share-dir");
/*     */   }
/*     */ 
/*     */   public static String getExportDirectory()
/*     */   {
/* 162 */     return getInstance().getStringSetting("export-dir");
/*     */   }
/*     */ 
/*     */   public static String getImportDirectory()
/*     */   {
/* 167 */     return getInstance().getStringSetting("import-dir");
/*     */   }
/*     */ 
/*     */   public static String getSavedSearchCriteriaDir()
/*     */   {
/* 172 */     return getInstance().getStringSetting("saved-search-criteria-dir");
/*     */   }
/*     */ 
/*     */   public static int getNumStorageDirectories()
/*     */   {
/* 177 */     return getInstance().getIntSetting("num-asset-storage-directories");
/*     */   }
/*     */ 
/*     */   public static String getRemoteStorageType()
/*     */   {
/* 182 */     return getInstance().getStringSetting("remote-storage-type");
/*     */   }
/*     */ 
/*     */   public static String getRemoteStorageUrl()
/*     */   {
/* 187 */     return getInstance().getStringSetting("remote-storage-url");
/*     */   }
/*     */ 
/*     */   public static String getRemoteStorageUser()
/*     */   {
/* 192 */     return getInstance().getStringSetting("remote-storage-user");
/*     */   }
/*     */ 
/*     */   public static String getRemoteStoragePassword()
/*     */   {
/* 197 */     return getInstance().getStringSetting("remote-storage-password");
/*     */   }
/*     */ 
/*     */   public static int getClearTempFilesHourOfDay()
/*     */   {
/* 202 */     return getInstance().getIntSetting("clear-temp-files-hour-of-day");
/*     */   }
/*     */ 
/*     */   public static int getDeleteTempFilesOlderThanMins()
/*     */   {
/* 207 */     return getInstance().getIntSetting("delete-temp-files-older-than-mins");
/*     */   }
/*     */ 
/*     */   public static int getDeleteShareFilesOlderThanMins()
/*     */   {
/* 212 */     return getInstance().getIntSetting("delete-share-files-older-than-mins");
/*     */   }
/*     */ 
/*     */   public static int getDeleteExportFilesOlderThanMins()
/*     */   {
/* 217 */     return getInstance().getIntSetting("delete-export-files-older-than-mins");
/*     */   }
/*     */ 
/*     */   public static int getDeleteImportFilesOlderThanMins()
/*     */   {
/* 222 */     return getInstance().getIntSetting("delete-import-files-older-than-mins");
/*     */   }
/*     */ 
/*     */   public static int getDeleteRemoteFilesOlderThanMins()
/*     */   {
/* 227 */     return getInstance().getIntSetting("delete-remote-files-older-than-mins");
/*     */   }
/*     */ 
/*     */   public static int getMaxNoOfSearchResults()
/*     */   {
/* 232 */     return getInstance().getIntSetting("max-search-results-total");
/*     */   }
/*     */ 
/*     */   public static int getRunPublishingActionsHourOfDay()
/*     */   {
/* 237 */     return getInstance().getIntSetting("run-publishing-actions-hour-of-day");
/*     */   }
/*     */ 
/*     */   public static String getSqlGeneratorClassName()
/*     */   {
/* 242 */     return getInstance().getStringSetting("sql-generator-class");
/*     */   }
/*     */ 
/*     */   public static boolean getLogStackTraceWithTransaction()
/*     */   {
/* 247 */     return getInstance().getBooleanSetting("log-stack-trace-with-transaction");
/*     */   }
/*     */ 
/*     */   public static boolean getWarnOnConcurrentTransactions()
/*     */   {
/* 252 */     return getInstance().getBooleanSetting("warn-on-concurrent-transactions");
/*     */   }
/*     */ 
/*     */   public static boolean getThreadDumpOnGetConnectionError()
/*     */   {
/* 257 */     return getInstance().getBooleanSetting("thread-dump-on-get-connection-error");
/*     */   }
/*     */ 
/*     */   public static boolean getLogStackTraceWithDeletion()
/*     */   {
/* 262 */     return getInstance().getBooleanSetting("log-stack-trace-with-deletion");
/*     */   }
/*     */ 
/*     */   public static boolean getDatabaseSupportsUTF8()
/*     */   {
/* 267 */     return getInstance().getBooleanSetting("databaseSupportsUTF8");
/*     */   }
/*     */ 
/*     */   public static boolean getDatabaseUsesNationalCharacterTypes()
/*     */   {
/* 272 */     return getInstance().getBooleanSetting("databaseUsesNationalCharacterTypes");
/*     */   }
/*     */ 
/*     */   public static int getoptimiseIndexHourOfDay()
/*     */   {
/* 277 */     return getInstance().getIntSetting("optimise-index-hour-of-day");
/*     */   }
/*     */ 
/*     */   public static int getImageMagickFileCacheSize()
/*     */   {
/* 282 */     return getInstance().getIntSetting("image-magick-file-cache-size");
/*     */   }
/*     */ 
/*     */   public static int getImageMagickOutputCacheSize()
/*     */   {
/* 287 */     return getInstance().getIntSetting("image-magick-output-cache-size");
/*     */   }
/*     */ 
/*     */   public static int getDeleteStoredFilesOlderThanMins()
/*     */   {
/* 292 */     return getInstance().getIntSetting("delete-stored-files-older-than-mins");
/*     */   }
/*     */ 
/*     */   public static int getDeleteStoredFilesLargerThanBytes()
/*     */   {
/* 297 */     return getInstance().getIntSetting("delete-stored-files-larger-than-bytes");
/*     */   }
/*     */ 
/*     */   public static String getProxyHost()
/*     */   {
/* 302 */     return getInstance().getStringSetting("proxy-host");
/*     */   }
/*     */ 
/*     */   public static int getProxyPort() {
/* 306 */     return getInstance().getIntSetting("proxy-port");
/*     */   }
/*     */ 
/*     */   public static String getProxyUsername() {
/* 310 */     return getInstance().getStringSetting("proxy-username");
/*     */   }
/*     */ 
/*     */   public static String getProxyPassword() {
/* 314 */     return getInstance().getStringSetting("proxy-password");
/*     */   }
/*     */ 
/*     */   public static String getProxyExclusions() {
/* 318 */     return getInstance().getStringSetting("proxy-exclusions");
/*     */   }
/*     */ 
/*     */   public static String getNTLMDomain() {
/* 322 */     return getInstance().getStringSetting("ntlm-domain");
/*     */   }
/*     */ 
/*     */   public static boolean getHideMissingLdapUsers()
/*     */   {
/* 327 */     return getInstance().getBooleanSetting("hide-missing-ldap-users");
/*     */   }
/*     */ 
/*     */   public static boolean getAuthenticateEveryRequest()
/*     */   {
/* 332 */     return getInstance().getBooleanSetting("authenticate-every-request");
/*     */   }
/*     */ 
/*     */   public static boolean getForceRemoteAuthentication()
/*     */   {
/* 337 */     return getInstance().getBooleanSetting("force-remote-authentication");
/*     */   }
/*     */ 
/*     */   public static String getRemoteUserDirectoryImportedUsernamePrefix()
/*     */   {
/* 342 */     return getInstance().getStringSetting("remote-directory-username-prefix");
/*     */   }
/*     */ 
/*     */   public static boolean getImportRemoteUsersOnTheFly()
/*     */   {
/* 347 */     return getInstance().getBooleanSetting("import-remote-users-on-the-fly");
/*     */   }
/*     */ 
/*     */   public static boolean getUpdateUserOnSSOLogin()
/*     */   {
/* 352 */     return getInstance().getBooleanSetting("update-remote-users-on-sso-login");
/*     */   }
/*     */ 
/*     */   public static boolean getSpecifyRemoteUsername()
/*     */   {
/* 357 */     return getInstance().getBooleanSetting("specify-remote-username");
/*     */   }
/*     */ 
/*     */   public static String getRemoteGroupMappingDelimiter()
/*     */   {
/* 362 */     return getInstance().getStringSetting("remote-group-mapping-delimiter");
/*     */   }
/*     */ 
/*     */   public static String getRemoteGroupMappingWildcard()
/*     */   {
/* 367 */     return getInstance().getStringSetting("remote-group-mapping-wildcard");
/*     */   }
/*     */ 
/*     */   public static int getStorageDeviceSafetyMarginAssetsMb()
/*     */   {
/* 372 */     return getInstance().getIntSetting("storage-device-safety-margin-assets");
/*     */   }
/*     */ 
/*     */   public static int getStorageDeviceSafetyMarginSystemMb()
/*     */   {
/* 377 */     return getInstance().getIntSetting("storage-device-safety-margin-system");
/*     */   }
/*     */ 
/*     */   public static int getStorageDeviceUsageUpdatePeriod()
/*     */   {
/* 382 */     return getInstance().getIntSetting("storage-device-usage-update-period-minutes");
/*     */   }
/*     */ 
/*     */   public static boolean getStoreFilesInDatabase()
/*     */   {
/* 387 */     return getInstance().getBooleanSetting("store-files-in-database");
/*     */   }
/*     */ 
/*     */   public static String getFileCacheDirectory()
/*     */   {
/* 392 */     return getInstance().getStringSetting("file-cache-directory");
/*     */   }
/*     */ 
/*     */   public static boolean getAddPlainTextToHtmlEmail()
/*     */   {
/* 397 */     return getInstance().getBooleanSetting("email-add-plain-text");
/*     */   }
/*     */ 
/*     */   public static int getSearchFilterBatchSize()
/*     */   {
/* 402 */     return getInstance().getIntSetting("search-filter-batch-size");
/*     */   }
/*     */ 
/*     */   public static String getExternalFilterClassName()
/*     */   {
/* 407 */     return getInstance().getStringSetting("external-filter-class");
/*     */   }
/*     */ 
/*     */   public static boolean getEmailThisPageIntegrated()
/*     */   {
/* 412 */     return getInstance().getBooleanSetting("email-this-page-integrated");
/*     */   }
/*     */ 
/*     */   public static String getWIAUrl()
/*     */   {
/* 417 */     return getInstance().getStringSetting("wia-url");
/*     */   }
/*     */ 
/*     */   public static String getExternalFilterTransactionManagerName()
/*     */   {
/* 426 */     return getInstance().getStringSetting("external-filter-transaction-manager-name");
/*     */   }
/*     */ 
/*     */   public static String getExternalFilterSchema()
/*     */   {
/* 431 */     return getInstance().getStringSetting("external-filter-schema");
/*     */   }
/*     */ 
/*     */   public static int getPotentiallyDuplicateEmailPeriod()
/*     */   {
/* 436 */     return getInstance().getIntSetting("potentially-duplicate-email-period");
/*     */   }
/*     */ 
/*     */   public static boolean getThrowExceptionOnFailedAssertion()
/*     */   {
/* 441 */     return getInstance().getBooleanSetting("throw-exception-on-failed-assertion");
/*     */   }
/*     */ 
/*     */   public static String getApplicationUrl()
/*     */   {
/* 453 */     return getInstance().getStringSetting("application-url");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.constant.FrameworkSettings
 * JD-Core Version:    0.6.0
 */