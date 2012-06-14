/*     */ package com.bright.assetbank.updater.constant;
/*     */ 
/*     */ import java.io.File;
/*     */ 
/*     */ public abstract interface ApplicationUpdateConstants
/*     */ {
/*     */   public static final String k_sChangesFileName = "changes.xml";
/*     */   public static final String k_sDefAppZipFileName = "app.zip";
/*     */   public static final String k_sParamName_Version = "version";
/*  40 */   public static final String k_sUpdaterDir = "WEB-INF" + File.separator + "manager-config" + File.separator + "updater";
/*  41 */   public static final String k_sCustomisationsDir = k_sUpdaterDir + "" + File.separator + "customise";
/*  42 */   public static final String k_sChangesStoreDir = k_sUpdaterDir + File.separator + "store";
/*     */   public static final String k_sChangesStoreFile = "appliedChanges.xml";
/*  44 */   public static final String k_sChangesStoreFilePath = k_sChangesStoreDir + File.separator + "appliedChanges.xml";
/*  45 */   public static final String k_sComponentsXconfPath = "WEB-INF" + File.separator + "components.xconf";
/*  46 */   public static final String k_sWebXmlPath = "WEB-INF" + File.separator + "web.xml";
/*  47 */   public static final String k_sAppSettingsPath = "WEB-INF" + File.separator + "classes" + File.separator + "ApplicationSettings.properties";
/*  48 */   public static final String k_sMessagesPropertiesPath = "WEB-INF" + File.separator + "classes" + File.separator + "messages.properties";
/*  49 */   public static final String k_sChangesScriptPath = k_sUpdaterDir + "/changes-";
/*     */   public static final String k_sDownloadFolder = "NewApps";
/*     */   public static final String k_sBackupFolder = "Backups";
/*     */   public static final String k_sXmlVersionElement = "version";
/*     */   public static final String k_sXmlUserChangeDescriptionElement = "userChangeDescription";
/*     */   public static final String k_sXmlUserPostUpdateMessageElement = "userPostUpdateMessage";
/*     */   public static final String k_sXmlSQLElement = "sqlStatement";
/*     */   public static final String k_sXmlAppSettingChangeElement = "appSettingChange";
/*     */   public static final String k_sXmlMsgPropChangeElement = "msgPropChange";
/*     */   public static final String k_sXmlCommentElement = "comment";
/*     */   public static final String k_sXmlAppFileChangeElement = "appFileChange";
/*     */   public static final String k_sXmlNumberAttr = "number";
/*     */   public static final String k_sXmlUpdaterChangedAttr = "updaterChanged";
/*     */   public static final String k_sXmlPrivateAttr = "private";
/*     */   public static final String k_sXmlFileArchiveAttr = "fileArchive";
/*     */   public static final String k_sXmlTypeAttr = "type";
/*     */   public static final String k_sXmlSettingAttr = "setting";
/*     */   public static final String k_sXmlFileAttr = "file";
/*     */   public static final String k_sXmlValueAttr = "value";
/*     */   public static final String k_sXmlSectionAttr = "section";
/*     */   public static final String k_sXmlMySqlAttrVal = "mysql";
/*     */   public static final String k_sXmlSQLServerAttrVal = "sqlserver";
/*     */   public static final String k_sXmlOracleAttrVal = "oracle";
/*     */   public static final String k_sXmlAllAttrVal = "all";
/*     */   public static final String k_sXmlAddAttrVal = "add";
/*     */   public static final String k_sXmlDeleteAttrVal = "delete";
/*     */   public static final String k_sXmlUpdateAttrVal = "update";
/*     */   public static final String k_sXmlAppendAttrVal = "append";
/*     */   public static final String k_sXmlDatabaseTypeAttr = "databaseType";
/*     */   public static final String k_sXmlClassAttr = "class";
/*     */   public static final String k_sXmlAppSettingConditionAttr = "appSettingCondition";
/*     */   public static final String k_sXmlChangesElement = "changes";
/*     */   public static final String k_sXmlXsiNamespaceAttr = "xsi";
/*     */   public static final String k_sXmlXsiNamespaceAttrVal = "http://www.w3.org/2001/XMLSchema-instance";
/*     */   public static final String k_sXmlXsiNoNamespaceSchemaLocationAttr = "noNamespaceSchemaLocation";
/*     */   public static final String k_sXmlXsiNoNamespaceSchemaLocationAttrVal = "http://www.assetbank.co.uk/download/update/changes.xsd";
/*     */   public static final String k_sXmlComponentElement = "component";
/*     */   public static final String k_sXmlRoleAttr = "role";
/*     */   public static final String k_sXmlJdbcDataSourceAttrVal = "JdbcDataSource";
/*     */   public static final String k_sXmlSessionConfigElement = "session-config";
/*     */   public static final String k_sAppSettingVersionSetting = "version";
/*     */   public static final String k_sUpdateInProgressTypeSettingName = "updateType";
/*     */   public static final String k_sAppSettingComment = "#";
/*     */   public static final int k_iDefaultCommentSymbols = 10;
/*     */   public static final String k_sRegexBlankLines = "[(^\\s*$)|\\n]*";
/*     */   public static final String k_sRegexAnyLines = "[(^.*$)|(.*\\n)]*";
/*     */   public static final String k_sRegexCommentLines = "(^#+.*\\n)*";
/*     */   public static final String k_sCurrentVersionSettingName = "CurrentVersion";
/*     */   public static final String k_sUpdateAvailableSettingName = "UpdateAvailable";
/*     */   public static final String k_sUpdateAvailableSettingValueYes = "yes";
/*     */   public static final String k_sUpdateAvailableSettingValueNo = "no";
/*     */   public static final String k_sStoredChangesWrittenSettingName = "StoredChangesWritten";
/*     */   public static final int k_iUpdateStatus_Resting = 1;
/*     */   public static final int k_iUpdateStatus_InProgress = 2;
/*     */   public static final int k_iUpdateStatus_Failed = 3;
/*     */   public static final int k_iUpdateStatus_Succeded = 4;
/* 120 */   public static final String[] k_saUpdateVersions = { "beta", "test" };
/*     */   public static final String k_sNoUpdateAvailableKey = "NoUpdateAvailable";
/*     */   public static final String k_sUpdatesNotEnabled = "UpdatesNotEnabled";
/*     */   public static final String k_sNoConnectionKey = "NoConnection";
/*     */   public static final String k_sInsufficientPermissionsKey = "InsufficientPermissions";
/*     */   public static final String k_sParam_BrightAdmin = "BrightAdmin";
/*     */   public static final String k_sParam_DatabaseType = "DatabaseType";
/*     */   public static final String k_sOldEmailTemplateId = "ad_user_approved";
/*     */   public static final String k_sAction_Updater = "viewApplicationUpdateAdmin";
/*     */   public static final String k_sAction_UpdateDetails = "viewApplicationUpdateDetails";
/*     */   public static final String k_sAction_StartUpdate = "startApplicationUpdate";
/*     */   public static final String k_sAction_UpdateProgress = "viewApplicationUpdateProgress";
/*     */   public static final String k_sForward_UpdateInProgress = "InProgress";
/*     */   public static final String k_sOracleDataType_NVarchar = "NVarchar2";
/*     */   public static final String k_sOracleDataType_NClob = "NClob";
/*     */   public static final String k_sOracleDataType_NChar = "NChar";
/*     */   public static final String k_sOracleDataType_Varchar = "Varchar2";
/*     */   public static final String k_sOracleDataType_Clob = "Clob";
/*     */   public static final String k_sOracleDataType_Char = "Char";
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.constant.ApplicationUpdateConstants
 * JD-Core Version:    0.6.0
 */