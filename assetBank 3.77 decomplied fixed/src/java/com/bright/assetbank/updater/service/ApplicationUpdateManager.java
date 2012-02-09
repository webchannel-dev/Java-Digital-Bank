/*      */ package com.bright.assetbank.updater.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.database.MySqlSQLGenerator;
/*      */ import com.bright.assetbank.database.OracleSQLGenerator;
/*      */ import com.bright.assetbank.database.SQLServerGenerator;
/*      */ import com.bright.assetbank.updater.bean.VersionDetail;
/*      */ import com.bright.assetbank.updater.constant.ApplicationUpdateConstants;
/*      */ import com.bright.assetbank.updater.exception.UpdatePermissionDetails;
/*      */ import com.bright.assetbank.updater.exception.UpdatePermissionsException;
/*      */ import com.bright.framework.common.exception.SettingNotFoundException;
/*      */ import com.bright.framework.common.service.RefDataManager;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.exception.SQLStatementException;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.message.constant.MessageConstants;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.sql.Connection;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipInputStream;
/*      */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.jdom.Attribute;
/*      */ import org.jdom.Document;
/*      */ import org.jdom.Element;
/*      */ import org.jdom.JDOMException;
/*      */ import org.jdom.Namespace;
/*      */ import org.jdom.input.SAXBuilder;
/*      */ import org.jdom.output.XMLOutputter;
/*      */ 
/*      */ public class ApplicationUpdateManager extends Bn2Manager
/*      */   implements ApplicationUpdateConstants, MessageConstants, AssetBankConstants
/*      */ {
/*      */   private static final String k_sApplicationSettings = "ApplicationSettings";
/*      */   private static final String c_ksClassName = "ApplicationUpdateManager";
/*  116 */   private DBTransactionManager m_transactionManager = null;
/*      */ 
/*  122 */   private ScheduleManager m_scheduleManager = null;
/*      */ 
/*  128 */   private RefDataManager m_refDataManager = null;
/*      */ 
/*  134 */   private DataSourceComponent m_dataSource = null;
/*      */ 
/*  140 */   protected ListManager m_listManager = null;
/*      */   private AtomicInteger m_aiUpdateStatus;
/*  150 */   private Vector m_vecProgressMessages = null;
/*      */ 
/*  153 */   private Vector m_vecErrorMessages = null;
/*      */ 
/*  156 */   private Collection m_storedUpdateDetails = null;
/*      */ 
/*  159 */   private String m_sFailureError = null;
/*      */ 
/*  162 */   private ResourceBundle m_appSettings = null;
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/*  119 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/*  125 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ 
/*      */   public void setRefDataManager(RefDataManager a_refDataManager)
/*      */   {
/*  131 */     this.m_refDataManager = a_refDataManager;
/*      */   }
/*      */ 
/*      */   public void setDataSourceComponent(DataSourceComponent a_datasource)
/*      */   {
/*  137 */     this.m_dataSource = a_datasource;
/*      */   }
/*      */ 
/*      */   public void setListManager(ListManager listManager)
/*      */   {
/*  143 */     this.m_listManager = listManager;
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  180 */     super.startup();
/*      */ 
/*  183 */     this.m_aiUpdateStatus = new AtomicInteger(1);
/*  184 */     this.m_vecProgressMessages = new Vector();
/*  185 */     this.m_vecErrorMessages = new Vector();
/*  186 */     this.m_sFailureError = null;
/*      */ 
/*  188 */     this.m_appSettings = ResourceBundle.getBundle("ApplicationSettings");
/*      */   }
/*      */ 
/*      */   protected boolean getNeverSkipStartup()
/*      */   {
/*  196 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean updateIsAvailableQuickCheck(String a_sVersion)
/*      */     throws Bn2Exception
/*      */   {
/*  215 */     if (StringUtil.stringIsPopulated(a_sVersion))
/*      */     {
/*      */       try
/*      */       {
/*  219 */         return this.m_refDataManager.getSystemSetting("UpdateAvailable_" + a_sVersion).equals("yes");
/*      */       }
/*      */       catch (SettingNotFoundException e)
/*      */       {
/*  224 */         return false;
/*      */       }
/*      */     }
/*  227 */     return this.m_refDataManager.getSystemSetting("UpdateAvailable").equals("yes");
/*      */   }
/*      */ 
/*      */   public boolean updateIsAvailableFullCheck(DBTransaction a_dbTransaction, String a_sVersion)
/*      */     throws Bn2Exception, IOException
/*      */   {
/*  251 */     if (updateIsAvailableQuickCheck(a_sVersion))
/*      */     {
/*  253 */       return true;
/*      */     }
/*      */ 
/*  257 */     Document changesDOM = retrieveChangesJDOM(a_sVersion);
/*  258 */     removedAppliedChanges(changesDOM);
/*  259 */     boolean bUnAppliedVerisons = getHasVersionElements(changesDOM);
/*      */ 
/*  262 */     String sCurrentVersion = this.m_refDataManager.getSystemSetting("CurrentVersion");
/*  263 */     String sLatestVersion = parseChangesForLatestVersion(changesDOM, sCurrentVersion, true);
/*      */ 
/*  266 */     boolean bUpdateAvailable = (bUnAppliedVerisons) && (versionIsLater(sLatestVersion, sCurrentVersion));
/*      */ 
/*  269 */     String sSettingName = "UpdateAvailable";
/*      */ 
/*  271 */     if (StringUtil.stringIsPopulated(a_sVersion))
/*      */     {
/*  273 */       sSettingName = sSettingName + "_" + a_sVersion;
/*      */     }
/*      */ 
/*  276 */     if (bUpdateAvailable)
/*      */     {
/*  278 */       this.m_refDataManager.setSystemSetting(a_dbTransaction, sSettingName, "yes");
/*      */     }
/*      */     else
/*      */     {
/*  284 */       this.m_refDataManager.setSystemSetting(a_dbTransaction, sSettingName, "no");
/*      */     }
/*      */ 
/*  289 */     return bUpdateAvailable;
/*      */   }
/*      */ 
/*      */   public boolean startUpdate(final String a_sContextPath, boolean a_bBrightAdmin, final UserProfile a_userProfile, final String a_sVersion)
/*      */     throws Bn2Exception, UpdatePermissionsException
/*      */   {
/*  313 */     UpdatePermissionDetails updatePermissionDetails = checkUpdatePermissions();
/*  314 */     if (updatePermissionDetails.getInsufficientPermissions())
/*      */     {
/*  316 */       throw new UpdatePermissionsException(updatePermissionDetails);
/*      */     }
/*      */ 
/*  323 */     if ((this.m_aiUpdateStatus.compareAndSet(1, 2)) || ((a_bBrightAdmin) && (this.m_aiUpdateStatus.compareAndSet(3, 2))))
/*      */     {
/*  329 */       Thread thread = new Thread()
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           try
/*      */           {
/*  335 */             ApplicationUpdateManager.this.applyUpdate(a_sContextPath, a_userProfile, a_sVersion);
/*      */           }
/*      */           catch (Throwable t)
/*      */           {
/*  342 */             ApplicationUpdateManager.this.m_aiUpdateStatus.compareAndSet(2, 3);
/*      */ 
/*  344 */             ApplicationUpdateManager.this.setFailureError(t.getMessage());
/*  345 */             ApplicationUpdateManager.this.m_logger.error("StartUpdate : Error whilst applying update", t);
/*      */ 
/*  349 */             if ((t instanceof Error))
/*      */             {
/*  351 */               throw ((Error)t);
/*      */             }
/*      */           }
/*      */         }
/*      */       };
/*  356 */       thread.start();
/*      */ 
/*  358 */       return true;
/*      */     }
/*      */ 
/*  361 */     return false;
/*      */   }
/*      */ 
/*      */   private void applyUpdate(String a_sContextPath, UserProfile userProfile, String a_sVersion)
/*      */     throws Exception
/*      */   {
/*  376 */     String ksMethodName = "applyUpdate";
/*  377 */     DBTransaction dbTransaction = null;
/*      */     try
/*      */     {
/*  382 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/*  385 */       String sCurrentVersion = this.m_refDataManager.getSystemSetting("CurrentVersion");
/*  386 */       Document changesJDOM = retrieveChangesJDOM(a_sVersion);
/*      */ 
/*  389 */       Document previouslyAppliedChangesJDOM = removedAppliedChanges(changesJDOM);
/*      */ 
/*  392 */       String sLatestVersion = parseChangesForLatestVersion(changesJDOM, sCurrentVersion, true);
/*      */ 
/*  396 */       if (!AssetBankSettings.isApplicationUpdateInProgress())
/*      */       {
/*  398 */         this.m_logger.info("Update not in progress.  Checking for updater changed flag");
/*  399 */         String sUpdaterChangedVersion = parseChangesForUpdaterChanged(changesJDOM, sCurrentVersion);
/*      */ 
/*  402 */         removeVersions(changesJDOM, null, sUpdaterChangedVersion, false, true);
/*      */       }
/*      */ 
/*  406 */       String sNewVersion = parseChangesForLatestVersion(changesJDOM, sCurrentVersion, true);
/*      */ 
/*  408 */       this.m_logger.info("Updating: " + sCurrentVersion + ": New Version: " + sNewVersion + ": Final Version: " + sLatestVersion);
/*      */ 
/*  410 */       if (!sNewVersion.equals(sLatestVersion))
/*      */       {
/*  413 */         if (StringUtil.stringIsPopulated(a_sVersion))
/*      */         {
/*  415 */           ApplicationSettingsUtil appSettings = new ApplicationSettingsUtil();
/*  416 */           appSettings.updateSetting("updateType", a_sVersion, null, null);
/*  417 */           appSettings.saveSettings();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  422 */       setStoredUpdateDetails(getUpdateDetails(changesJDOM, null));
/*      */ 
/*  424 */       addProgressMessage("Starting Update From Version % to %".replaceFirst("%", sCurrentVersion).replaceFirst("%", sNewVersion));
/*      */ 
/*  426 */       String sBackupDir = getTempDir(a_sContextPath, "Backups");
/*  427 */       addProgressMessage("Removing Previous Application Backup : " + sBackupDir);
/*      */ 
/*  429 */       deleteTempDir(sBackupDir);
/*  430 */       addProgressMessage("Done");
/*      */ 
/*  432 */       addProgressMessage("Creating Application Backup : " + sBackupDir);
/*      */ 
/*  434 */       backUpWebApp(sBackupDir);
/*  435 */       addProgressMessage("Done");
/*      */ 
/*  437 */       String sDownloadDir = getTempDir(a_sContextPath, "NewApps");
/*  438 */       deleteTempDir(sDownloadDir);
/*  439 */       addProgressMessage("Downloading Updated Application Files");
/*      */ 
/*  441 */       downloadApp(AssetBankSettings.getApplicationUpdateUrl(a_sVersion) + parseChangesForArchiveName(changesJDOM, sNewVersion), sDownloadDir);
/*  442 */       addProgressMessage("Done");
/*      */ 
/*  444 */       addProgressMessage("Updating Database");
/*  445 */       applyDatabaseChanges(changesJDOM);
/*  446 */       addProgressMessage("Done");
/*      */ 
/*  448 */       addProgressMessage("Updating Application Settings");
/*  449 */       applyAppSettingsChanges(changesJDOM, sNewVersion);
/*  450 */       addProgressMessage("Done");
/*      */ 
/*  452 */       addProgressMessage("Updating Messages Properties");
/*  453 */       applyMessagesPropertiesChanges(changesJDOM, sNewVersion);
/*  454 */       addProgressMessage("Done");
/*      */ 
/*  456 */       addProgressMessage("Restructuring Application Files");
/*  457 */       applyAppFileChanges(changesJDOM);
/*  458 */       addProgressMessage("Done");
/*      */ 
/*  460 */       addProgressMessage("Replacing Application Files");
/*  461 */       overwiteApp(sDownloadDir);
/*  462 */       addProgressMessage("Done");
/*      */ 
/*  464 */       addProgressMessage("Reseting Customisations");
/*  465 */       resetCustomisations();
/*  466 */       addProgressMessage("Done");
/*      */ 
/*  470 */       addProgressMessage("Merging Components File");
/*  471 */       mergeComponentsXConf(sBackupDir + File.separator + k_sComponentsXconfPath, AssetBankSettings.getApplicationPath() + File.separator + k_sComponentsXconfPath);
/*      */ 
/*  473 */       addProgressMessage("Done");
/*      */ 
/*  478 */       addProgressMessage("Merging Web.xml File");
/*  479 */       mergeWebXml(sBackupDir + File.separator + k_sWebXmlPath, AssetBankSettings.getApplicationPath() + File.separator + k_sWebXmlPath);
/*      */ 
/*  481 */       addProgressMessage("Done");
/*      */ 
/*  484 */       addVersions(previouslyAppliedChangesJDOM, changesJDOM);
/*  485 */       writeStoreChangesJDOM(dbTransaction, previouslyAppliedChangesJDOM);
/*      */ 
/*  488 */       this.m_refDataManager.updateSystemSetting(dbTransaction, "CurrentVersion", sNewVersion);
/*      */ 
/*  493 */       if (sNewVersion.equals(sLatestVersion))
/*      */       {
/*  496 */         this.m_refDataManager.setSystemSetting(dbTransaction, "UpdateAvailable", "no");
/*      */ 
/*  500 */         if (StringUtil.stringIsPopulated(a_sVersion))
/*      */         {
/*  502 */           this.m_refDataManager.setSystemSetting(dbTransaction, "UpdateAvailable_" + a_sVersion, "no");
/*      */         }
/*      */ 
/*      */         try
/*      */         {
/*  510 */           ApplicationSettingsUtil appSettings = new ApplicationSettingsUtil();
/*  511 */           appSettings.updateSetting("updateType", "", null, null);
/*  512 */           appSettings.saveSettings();
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/*  516 */           addErrorMessage("Application Settings Error: " + e.getMessage(), e);
/*  517 */           addProgressMessage("There was a problem reading/writing to ApplicationSettings.properties.  See below for details.");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  522 */       if (this.m_vecErrorMessages.isEmpty())
/*      */       {
/*  524 */         this.m_aiUpdateStatus.compareAndSet(2, 4);
/*      */       }
/*      */       else
/*      */       {
/*  529 */         this.m_aiUpdateStatus.compareAndSet(2, 3);
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  537 */         dbTransaction.commit();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/*  541 */         this.m_logger.error("ApplicationUpdateManager.applyUpdate - " + e);
/*  542 */         throw new Bn2Exception("ApplicationUpdateManager.applyUpdate", e);
/*      */       }
/*      */     }
/*      */ 
/*  546 */     addProgressMessage("Update Complete");
/*      */   }
/*      */ 
/*      */   public void patchAppSettings(String a_sAppSettingsPath, String a_sOldChangesFile, String a_sNewChangesFile)
/*      */     throws Bn2Exception
/*      */   {
/*  563 */     String kMethodName = "applyAppSettingsChanges";
/*      */     try
/*      */     {
/*  567 */       Document changesJDOM = buildJDOMFromLocalXml(a_sNewChangesFile);
/*  568 */       Document oldChangesJDOM = buildJDOMFromLocalXml(a_sOldChangesFile);
/*      */ 
/*  571 */       removeVersions(changesJDOM, oldChangesJDOM);
/*      */ 
/*  573 */       String sNewVersion = parseChangesForLatestVersion(changesJDOM, null, true);
/*      */ 
/*  575 */       ApplicationSettingsUtil appSettings = new ApplicationSettingsUtil(a_sAppSettingsPath);
/*  576 */       applyAppSettingsChanges(appSettings, changesJDOM, sNewVersion);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  580 */       throw new Bn2Exception("IOException in applyAppSettingsChanges", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private String getTempDir(String a_sContextPath, String a_sSubFolder)
/*      */     throws Bn2Exception
/*      */   {
/*  607 */     String ksMethodName = "getBackUpDir";
/*  608 */     String sBaseDir = AssetBankSettings.getApplicationUpdateBackupDirLinux();
/*      */ 
/*  610 */     if ((File.separator.equals("\\")) || (!StringUtil.stringIsPopulated(sBaseDir)))
/*      */     {
/*  613 */       sBaseDir = AssetBankSettings.getApplicationUpdateBackupDir();
/*      */     }
/*      */ 
/*  616 */     if (!StringUtil.stringIsPopulated(sBaseDir))
/*      */     {
/*  618 */       Exception e = new Exception("Update Backup Directory Not Specified");
/*  619 */       this.m_logger.error("ApplicationUpdateManager.getBackUpDir - " + e);
/*  620 */       throw new Bn2Exception("ApplicationUpdateManager.getBackUpDir", e);
/*      */     }
/*      */ 
/*  623 */     if (a_sSubFolder != null)
/*      */     {
/*  625 */       sBaseDir = sBaseDir + "/" + a_sSubFolder;
/*      */     }
/*      */ 
/*  628 */     if (a_sContextPath != null)
/*      */     {
/*  630 */       sBaseDir = sBaseDir + a_sContextPath;
/*      */     }
/*      */     else
/*      */     {
/*  634 */       sBaseDir = sBaseDir + "/nocontext";
/*      */     }
/*      */ 
/*  637 */     return sBaseDir;
/*      */   }
/*      */ 
/*      */   private void backUpWebApp(String a_sBackUpDir)
/*      */     throws Bn2Exception
/*      */   {
/*  657 */     String ksMethodName = "backUpWebApp";
/*      */ 
/*  660 */     String[] exceptions = { "\\Q" + AssetBankSettings.getApplicationPath() + File.separator + "files\\E.*", "\\Q" + AssetBankSettings.getApplicationPath() + File.separator + "bulk-upload\\E.*" };
/*      */     try
/*      */     {
/*  667 */       FileUtil.copyDir(AssetBankSettings.getApplicationPath(), a_sBackUpDir, exceptions);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/*  673 */       this.m_logger.error("ApplicationUpdateManager.backUpWebApp - " + e);
/*  674 */       throw new Bn2Exception("ApplicationUpdateManager.backUpWebApp", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deleteTempDir(String a_sTempDir)
/*      */   {
/*  691 */     FileUtil.deleteDir(a_sTempDir);
/*      */   }
/*      */ 
/*      */   private void downloadApp(String a_sAppZipUrl, String a_sLocation)
/*      */     throws Bn2Exception
/*      */   {
/*  714 */     String ksMethodName = "downloadApp";
/*  715 */     int kiBufSize = 2048;
/*      */ 
/*  721 */     URLConnection connection = null;
/*      */     try
/*      */     {
/*  724 */       connection = createGetConnection(a_sAppZipUrl);
/*  725 */       InputStream response = connection.getInputStream();
/*      */ 
/*  727 */       ZipInputStream zis = new ZipInputStream(response);
/*      */       ZipEntry entry;
/*  729 */       while ((entry = zis.getNextEntry()) != null)
/*      */       {
/*  732 */         String sFileName = a_sLocation + File.separator + entry.getName();
/*      */ 
/*  736 */         if (entry.isDirectory())
/*      */         {
/*  738 */           File file = new File(sFileName);
/*  739 */           file.mkdirs();
/*      */         }
/*      */         else
/*      */         {
/*  746 */           byte[] data = new byte[2048];
/*  747 */           BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(sFileName), 2048);
/*      */           int count;
/*  749 */           while ((count = zis.read(data, 0, 2048)) != -1)
/*      */           {
/*  751 */             out.write(data, 0, count);
/*      */           }
/*      */ 
/*  754 */           out.flush();
/*  755 */           out.close();
/*      */         }
/*      */       }
/*      */ 
/*  759 */       zis.close();
/*  760 */       response.close();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  764 */       this.m_logger.error("ApplicationUpdateManager.downloadApp - " + e);
/*  765 */       throw new Bn2Exception("ApplicationUpdateManager.downloadApp", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void overwiteApp(String a_sDownloadedNewApp)
/*      */   {
/*  785 */     String ksMethodName = "overwiteApp";
/*      */     try
/*      */     {
/*  790 */       FileUtil.copyDir(a_sDownloadedNewApp, AssetBankSettings.getApplicationPath(), null);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/*  796 */       addErrorMessage("Update Files Error: " + e.getMessage(), e);
/*  797 */       addProgressMessage("There was a problem updating the application files.  See below for details.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void resetCustomisations()
/*      */   {
/*  816 */     String ksMethodName = "resetCustomisations";
/*      */     try
/*      */     {
/*  820 */       FileUtil.copyDir(AssetBankSettings.getApplicationPath() + File.separator + k_sCustomisationsDir, AssetBankSettings.getApplicationPath());
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/*  825 */       addErrorMessage("Reset Customisations Error: " + e.getMessage(), e);
/*  826 */       addProgressMessage("There was a problem reseting the application customisations.  See below for details.");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void mergeComponentsXConf(String a_sOldFile, String a_sNewFile)
/*      */   {
/*  848 */     String ksMethodName = "mergeComponentsXConf";
/*      */     try
/*      */     {
/*  853 */       Document oldXconf = buildJDOMFromLocalXml(a_sOldFile);
/*  854 */       Document newXconf = buildJDOMFromLocalXml(a_sNewFile);
/*      */ 
/*  857 */       Element oldDataSource = (Element)oldXconf.getRootElement().getContent(new DataSourceFilter()).get(0);
/*  858 */       Element newDataSource = (Element)newXconf.getRootElement().getContent(new DataSourceFilter()).get(0);
/*      */ 
/*  861 */       if (!oldDataSource.getAttributeValue("class").equals(newDataSource.getAttributeValue("class")))
/*      */       {
/*  863 */         oldDataSource.getAttribute("class").setValue(newDataSource.getAttributeValue("class"));
/*      */       }
/*      */ 
/*  867 */       newXconf.getRootElement().setContent(newXconf.getRootElement().indexOf(newDataSource), oldDataSource.detach());
/*      */ 
/*  870 */       writeDOMToFile(newXconf, a_sNewFile);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  874 */       addErrorMessage("Merge Components.xconf Error: " + e.getMessage(), e);
/*  875 */       addProgressMessage("There was a problem merging components.xconf.  See below for details.");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeDOMToFile(Document a_dom, String a_sFile)
/*      */     throws FileNotFoundException, IOException
/*      */   {
/*  893 */     XMLOutputter out = new XMLOutputter();
/*  894 */     out.output(a_dom, new BufferedOutputStream(new FileOutputStream(a_sFile)));
/*      */   }
/*      */ 
/*      */   public void generateDatabaseChangesScript(String a_validSqlType)
/*      */     throws Bn2Exception
/*      */   {
/*  916 */     String ksMethodName = "generateDatabaseChangesScript";
/*      */ 
/*  918 */     Document changesDOM = buildJDOMFromLocalXml(AssetBankSettings.getApplicationPath() + File.separator + k_sUpdaterDir + File.separator + "changes.xml");
/*      */     String validSqlType;
/*      */    // String validSqlType;
/*  922 */     if (a_validSqlType == null)
/*      */     {
/*  924 */       validSqlType = getSQLTypeValue();
/*      */     }
/*      */     else
/*      */     {
/*  928 */       validSqlType = a_validSqlType;
/*      */     }
        Iterator itSql;
/*      */ 
/*      */     try
/*      */     {
/*  933 */       File sFile = new File(AssetBankSettings.getApplicationPath() + File.separator + k_sChangesScriptPath + validSqlType + ".sql");
/*  934 */       sFile.createNewFile();
/*  935 */       BufferedWriter out = new BufferedWriter(new FileWriter(sFile));
/*      */ 
/*  937 */       Element rootChanges = changesDOM.getRootElement();
/*      */ 
/*  939 */       for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */       {
/*  941 */         Element versionElement = (Element)itVer.next();
/*  942 */         String sVersionNumber = versionElement.getAttributeValue("number");
/*  943 */         out.write("-- Version " + sVersionNumber);
/*  944 */         out.newLine();
/*      */ 
/*  946 */         for (itSql = versionElement.getChildren("sqlStatement").iterator(); itSql.hasNext(); )
/*      */         {
/*  948 */           Element sqlElement = (Element)itSql.next();
/*  949 */           String sqlElementType = sqlElement.getAttributeValue("type");
/*      */ 
/*  952 */           if ((sqlElementType.equals(validSqlType)) || (sqlElementType.equals("all")))
/*      */           {
/*  954 */             out.write(sqlElement.getTextTrim() + ";");
/*  955 */             out.newLine();
/*      */           }
/*      */         }
/*      */       }
/*      */       //Iterator itSql;
/*  960 */       out.flush();
/*  961 */       out.close();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  965 */       this.m_logger.error("ApplicationUpdateManager.generateDatabaseChangesScript - " + e);
/*  966 */       throw new Bn2Exception("ApplicationUpdateManager.generateDatabaseChangesScript", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void applyDatabaseChanges(Document a_validChangesDOM)
/*      */     throws Bn2Exception
/*      */   {
/*  990 */     String ksMethodName = "applyDatabaseChanges";
/*      */ 
/*  993 */     String validSqlType = getSQLTypeValue();
/*      */ 
/*  995 */     Connection con = null;
/*  996 */     Statement sqlStatement = null;
/*  997 */     String statementText = "No Statements Executed";
/*  998 */     boolean bStatementError = false;
        Iterator itVer;
        Iterator itSql;
/*      */     try
/*      */     {
/* 1002 */       con = this.m_dataSource.getConnection();
/*      */ 
/* 1005 */       Element rootChanges = a_validChangesDOM.getRootElement();
/*      */ 
/* 1007 */       for (itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */       {
/* 1009 */         Element versionElement = (Element)itVer.next();
/*      */ 
/* 1011 */         for (itSql = versionElement.getChildren("sqlStatement").iterator(); itSql.hasNext(); )
/*      */         {
/* 1013 */           Element sqlElement = (Element)itSql.next();
/*      */ 
/* 1015 */           String sAppSettingCondition = sqlElement.getAttributeValue("appSettingCondition");
/*      */ 
/* 1019 */           if ((StringUtils.isNotEmpty(sAppSettingCondition)) && 
/* 1021 */             (!checkAppsettingCondition(sAppSettingCondition)))
/*      */           {
/*      */             continue;
/*      */           }
/*      */ 
/* 1027 */           String sqlElementType = sqlElement.getAttributeValue("type");
/*      */ 
/* 1030 */           if ((sqlElementType.equals(validSqlType)) || (sqlElementType.equals("all")))
/*      */           {
/* 1035 */             if ((sqlElementType.equals("oracle")) && (!AssetBankSettings.getDatabaseUsesNationalCharacterTypes()))
/*      */             {
/* 1037 */               statementText = statementText.replaceAll("(?i)[ \t]*NVarchar2", " Varchar2");
/* 1038 */               statementText = statementText.replaceAll("(?i)[ \t]*NClob", " Clob");
/* 1039 */               statementText = statementText.replaceAll("(?i)[ \t]*NChar", " Char");
/*      */             }
/*      */ 
/* 1042 */             sqlStatement = con.createStatement();
/* 1043 */             statementText = sqlElement.getTextTrim();
/*      */             try
/*      */             {
/* 1048 */               this.m_logger.info("ApplicationUpdateManager.applyDatabaseChanges About to execute statement: " + statementText);
/* 1049 */               sqlStatement.execute(statementText);
/* 1050 */               sqlStatement.close();
/*      */             }
/*      */             catch (SQLException e)
/*      */             {
/* 1054 */               this.m_logger.error("ApplicationUpdateManager.applyDatabaseChanges Error while executing: " + statementText);
/* 1055 */               addErrorMessage("Database Update Error: " + e.getMessage(), e);
/* 1056 */               if (!bStatementError)
/*      */               {
/* 1058 */                 addProgressMessage("Some databases updates could not be run.  See below for details.");
/* 1059 */                 bStatementError = true;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */      // Iterator itVer;
/*      */      // Iterator itSql;
/* 1068 */       this.m_logger.error("ApplicationUpdateManager.applyDatabaseChanges Error while executing: " + statementText + " - " + e);
/* 1069 */       addErrorMessage("Database Error: " + e.getMessage(), e);
/* 1070 */       addProgressMessage("There was problem with the connection to the database.  See below for details.");
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1076 */         con.commit();
/* 1077 */         con.close();
/*      */       }
/*      */       catch (SQLException e1)
/*      */       {
/* 1081 */         addErrorMessage("Database Error: " + e1.getMessage(), e1);
/* 1082 */         addProgressMessage("There was problem with the connection to the database.  See below for details.");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private String getPropertyValue(ResourceBundle a_bundle, String a_sKey)
/*      */   {
/*      */     try
/*      */     {
/* 1097 */       return a_bundle.getString(a_sKey);
/*      */     }
/*      */     catch (MissingResourceException e) {
/*      */     }
/* 1101 */     return null;
/*      */   }
/*      */ 
/*      */   private void applyAppSettingsChanges(Document a_validChangesDOM, String a_sLatestVersion)
/*      */     throws Bn2Exception
/*      */   {
/* 1124 */     String ksMethodName = "applyAppSettingsChanges";
/*      */     try
/*      */     {
/* 1128 */       ApplicationSettingsUtil appSettings = new ApplicationSettingsUtil();
/*      */ 
/* 1130 */       applyAppSettingsChanges(appSettings, a_validChangesDOM, a_sLatestVersion);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1134 */       addErrorMessage("Application Settings Error: " + e.getMessage(), e);
/* 1135 */       addProgressMessage("There was a problem reading/writing to ApplicationSettings.properties.  See below for details.");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void applyAppSettingsChanges(ApplicationSettingsUtil a_appSettings, Document a_validChangesDOM, String a_sLatestVersion)
/*      */     throws Bn2Exception
/*      */   {
/* 1156 */     String ksMethodName = "applyAppSettingsChanges";
/* 1157 */     boolean bSettingChangeError = false;
        Iterator itApp;
/*      */     try
/*      */     {
/* 1161 */       String sValidDBType = getSQLTypeValue();
/*      */ 
/* 1163 */       Element rootChanges = a_validChangesDOM.getRootElement();
/*      */ 
/* 1165 */       for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */       {
/* 1167 */         Element versionElement = (Element)itVer.next();
/*      */ 
/* 1169 */         for (itApp = versionElement.getChildren("appSettingChange").iterator(); itApp.hasNext(); )
/*      */         {
/*      */           try
/*      */           {
/* 1173 */             Element appElement = (Element)itApp.next();
/*      */ 
/* 1176 */             String sAppElementType = appElement.getAttributeValue("type");
/* 1177 */             String sName = appElement.getAttributeValue("setting");
/* 1178 */             String sValue = appElement.getAttributeValue("value");
/* 1179 */             String sSection = appElement.getAttributeValue("section");
/* 1180 */             String sDBType = appElement.getAttributeValue("databaseType");
/*      */ 
/* 1182 */             String sAppSettingCondition = appElement.getAttributeValue("appSettingCondition");
/*      */ 
/* 1184 */             if ((StringUtils.isEmpty(sName)) || (StringUtils.isEmpty(sAppElementType)))
/*      */             {
/* 1186 */               throw new Exception("appSettingChange elements must have 'setting' and a 'type' attributes.");
/*      */             }
/*      */ 
/* 1191 */             if ((StringUtils.isNotEmpty(sAppSettingCondition)) && 
/* 1193 */               (!checkAppsettingCondition(sAppSettingCondition)))
/*      */             {
/*      */               continue;
/*      */             }
/*      */ 
/* 1200 */             if ((StringUtils.isEmpty(sDBType)) || (sDBType.equals(sValidDBType)) || (sDBType.equals("all")))
/*      */             {
/* 1202 */               Vector vecComments = new Vector();
/* 1203 */               for (Iterator itComments = appElement.getChildren("comment").iterator(); itComments.hasNext(); )
/*      */               {
/* 1205 */                 vecComments.add(((Element)itComments.next()).getTextTrim());
/*      */               }
/*      */ 
/* 1209 */               if (sAppElementType.equals("add"))
/*      */               {
/* 1211 */                 a_appSettings.addSetting(sName, sValue, sSection, vecComments);
/*      */               }
/* 1213 */               else if (sAppElementType.equals("delete"))
/*      */               {
/* 1215 */                 if (sName != null)
/*      */                 {
/* 1217 */                   a_appSettings.deleteSetting(sName);
/*      */                 }
/*      */                 else
/*      */                 {
/* 1221 */                   a_appSettings.deleteSection(sSection);
/*      */                 }
/*      */               }
/* 1224 */               else if (sAppElementType.equals("update"))
/*      */               {
/* 1226 */                 a_appSettings.updateSetting(sName, sValue, sSection, vecComments);
/*      */               }
/* 1228 */               else if (sAppElementType.equals("append"))
/*      */               {
/* 1230 */                 a_appSettings.appendSetting(sName, sValue, sSection, vecComments);
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1236 */             addErrorMessage("Application Settings Error: " + e.getMessage(), e);
/* 1237 */             if (!bSettingChangeError)
/*      */             {
/* 1239 */               addProgressMessage("There was problem updating ApplicationSettings.properties.  See below for details.");
/* 1240 */               bSettingChangeError = true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       //Iterator itApp;
/* 1247 */       a_appSettings.updateSetting("version", a_sLatestVersion, null, null);
/*      */ 
/* 1250 */       a_appSettings.saveSettings();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1254 */       addErrorMessage("Application Settings Error: " + e.getMessage(), e);
/* 1255 */       addProgressMessage("There was a problem reading/writing to ApplicationSettings.properties.  See below for details.");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void applyMessagesPropertiesChanges(Document a_validChangesDOM, String a_sLatestVersion)
/*      */   {
/* 1276 */     String ksMethodName = "applyMessagesPropertiesChanges";
/* 1277 */     boolean bSettingChangeError = false;
        Iterator itApp;
/*      */     try
/*      */     {
/* 1280 */       MessagesPropertiesUtil messProp = new MessagesPropertiesUtil();
/*      */ 
/* 1282 */       Element rootChanges = a_validChangesDOM.getRootElement();
/*      */ 
/* 1284 */       for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */       {
/* 1286 */         Element versionElement = (Element)itVer.next();
/*      */ 
/* 1288 */         for (itApp = versionElement.getChildren("msgPropChange").iterator(); itApp.hasNext(); )
/*      */         {
/*      */           try
/*      */           {
/* 1292 */             Element msgElement = (Element)itApp.next();
/*      */ 
/* 1295 */             String sMsgElementType = msgElement.getAttributeValue("type");
/* 1296 */             String sName = msgElement.getAttributeValue("setting");
/* 1297 */             String sValue = msgElement.getAttributeValue("value");
/*      */ 
/* 1299 */             if ((StringUtils.isEmpty(sName)) || (StringUtils.isEmpty(sMsgElementType)))
/*      */             {
/* 1301 */               throw new Exception("appSettingChange elements must have 'setting' and a 'type' attributes.");
/*      */             }
/*      */ 
/* 1305 */             if (sMsgElementType.equals("add"))
/*      */             {
/* 1307 */               messProp.addSetting(sName, sValue);
/*      */             }
/* 1309 */             else if (sMsgElementType.equals("delete"))
/*      */             {
/* 1311 */               messProp.deleteSetting(sName);
/*      */             }
/* 1313 */             else if (sMsgElementType.equals("update"))
/*      */             {
/* 1315 */               messProp.updateSetting(sName, sValue);
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1320 */             addErrorMessage("Messages Error: " + e.getMessage(), e);
/* 1321 */             if (!bSettingChangeError)
/*      */             {
/* 1323 */               addProgressMessage("There was problem updating Messages.properties.  See below for details.");
/* 1324 */               bSettingChangeError = true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       //Iterator itApp;
/* 1331 */       messProp.saveSettings();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1335 */       addErrorMessage("Messages Error: " + e.getMessage(), e);
/* 1336 */       addProgressMessage("There was problem reading/writing to messages.properties.  See below for details.");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void applyAppFileChanges(Document a_validChangesDOM)
/*      */   {
/* 1361 */     Element rootChanges = a_validChangesDOM.getRootElement();
        Iterator itAppFile;
/*      */ 
/* 1363 */     for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */     {
/* 1365 */       Element versionElement = (Element)itVer.next();
/*      */ 
/* 1367 */       for (itAppFile = versionElement.getChildren("appFileChange").iterator(); itAppFile.hasNext(); )
/*      */       {
/* 1369 */         Element appFileElement = (Element)itAppFile.next();
/* 1370 */         String sAppFileElementType = appFileElement.getAttributeValue("type");
/* 1371 */         String sAppFile = appFileElement.getAttributeValue("file");
/*      */ 
/* 1373 */         if (sAppFileElementType.equals("delete"))
/*      */         {
/* 1375 */           FileUtil.deleteDir(AssetBankSettings.getApplicationPath() + File.separator + sAppFile);
/*      */         }
/*      */       }
/*      */     }
/*      */    // Iterator itAppFile;
/*      */   }
/*      */ 
/*      */   private String parseChangesForLatestVersion(Document a_changesDOM, String a_sStartVersion, boolean a_bIncludePrivate)
/*      */   {
/* 1401 */     String sLatestVersion = "-1";
/* 1402 */     if (a_sStartVersion != null)
/*      */     {
/* 1404 */       sLatestVersion = a_sStartVersion;
/*      */     }
/*      */ 
/* 1407 */     Element rootChanges = a_changesDOM.getRootElement();
/*      */ 
/* 1409 */     for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */     {
/* 1411 */       Element versionElement = (Element)itVer.next();
/* 1412 */       String sVersion = versionElement.getAttributeValue("number");
/* 1413 */       boolean bIsPrivate = Boolean.parseBoolean(versionElement.getAttributeValue("private"));
/* 1414 */       if ((versionIsLater(sVersion, sLatestVersion)) && ((a_bIncludePrivate) || (!bIsPrivate)))
/*      */       {
/* 1416 */         sLatestVersion = sVersion;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1421 */     return sLatestVersion;
/*      */   }
/*      */ 
/*      */   private String parseChangesForUpdaterChanged(Document a_changesDOM, String a_sVersion)
/*      */   {
/* 1444 */     String sUpdatedChangedVersion = null;
/* 1445 */     Element rootChanges = a_changesDOM.getRootElement();
/*      */ 
/* 1447 */     for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */     {
/* 1449 */       Element versionElement = (Element)itVer.next();
/* 1450 */       String sVersion = versionElement.getAttributeValue("number");
/*      */ 
/* 1455 */       if ((Boolean.parseBoolean(versionElement.getAttributeValue("updaterChanged"))) && ((a_sVersion == null) || (versionIsLater(sVersion, a_sVersion))) && ((sUpdatedChangedVersion == null) || (versionIsLater(sUpdatedChangedVersion, sVersion))))
/*      */       {
/* 1460 */         sUpdatedChangedVersion = sVersion;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1465 */     return sUpdatedChangedVersion;
/*      */   }
/*      */ 
/*      */   private String parseChangesForArchiveName(Document a_changesDOM, String a_sVersion)
/*      */   {
/* 1487 */     Element rootChanges = a_changesDOM.getRootElement();
/*      */ 
/* 1489 */     for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */     {
/* 1491 */       Element versionElement = (Element)itVer.next();
/* 1492 */       if (StringUtil.compareDotNotationNumbers(a_sVersion, versionElement.getAttributeValue("number")) == 0)
/*      */       {
/* 1494 */         String archiveName = versionElement.getAttributeValue("fileArchive");
/* 1495 */         if (archiveName != null)
/*      */         {
/* 1497 */           return archiveName;
/*      */         }
/*      */ 
/* 1500 */         return "app.zip";
/*      */       }
/*      */     }
/*      */ 
/* 1504 */     return "app.zip";
/*      */   }
/*      */ 
/*      */   private boolean versionIsLater(String a_sVersionA, String a_sVersionB)
/*      */   {
/* 1518 */     return StringUtil.compareDotNotationNumbers(a_sVersionA, a_sVersionB) > 0;
/*      */   }
/*      */ 
/*      */   public Collection getUpdateDetails(Document a_changesDOM, String a_sVersion)
/*      */     throws Bn2Exception, IOException
/*      */   {
/* 1539 */     Document changesDOM = a_changesDOM;
/* 1540 */     if (changesDOM == null)
/*      */     {
/* 1542 */       changesDOM = retrieveChangesJDOM(a_sVersion);
/* 1543 */       removedAppliedChanges(changesDOM);
/*      */     }
/*      */ 
/* 1546 */     Vector updateDetails = new Vector();
/* 1547 */     Element rootChanges = changesDOM.getRootElement();
/*      */ 
/* 1550 */     for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */     {
/* 1552 */       VersionDetail versionDetail = new VersionDetail();
/* 1553 */       Element versionElement = (Element)itVer.next();
/* 1554 */       String sVersion = versionElement.getAttributeValue("number");
/* 1555 */       versionDetail.setVersionNumber(sVersion);
/*      */ 
/* 1558 */       for (Iterator itDescs = versionElement.getChildren("userChangeDescription").iterator(); itDescs.hasNext(); )
/*      */       {
/* 1560 */         versionDetail.addChangeDescription(((Element)itDescs.next()).getTextTrim());
/*      */       }
/*      */ 
/* 1564 */       for (Iterator itMsgs = versionElement.getChildren("userPostUpdateMessage").iterator(); itMsgs.hasNext(); )
/*      */       {
/* 1566 */         versionDetail.addSuccessMessage(((Element)itMsgs.next()).getTextTrim());
/*      */       }
/*      */ 
/* 1569 */       versionDetail.setUpdaterChanged(Boolean.parseBoolean(versionElement.getAttributeValue("updaterChanged")));
/* 1570 */       versionDetail.setPrivate(Boolean.parseBoolean(versionElement.getAttributeValue("private")));
/* 1571 */       updateDetails.add(versionDetail);
/*      */     }
/*      */ 
/* 1574 */     return updateDetails;
/*      */   }
/*      */ 
/*      */   private Document removeVersions(Document a_changesDOM, String a_sLowerBoundVersion, String a_sUpperBoundVersion, boolean a_bLowerInc, boolean a_bUpperInc)
/*      */   {
/* 1604 */     Document removedChangesDOM = createEmptyChangesDOM();
/* 1605 */     Element rootRemoved = removedChangesDOM.getRootElement();
/*      */ 
/* 1607 */     Element rootChanges = a_changesDOM.getRootElement();
/*      */ 
/* 1609 */     for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */     {
/* 1611 */       Element versionElement = (Element)itVer.next();
/* 1612 */       String sVersion = versionElement.getAttributeValue("number");
/*      */ 
/* 1614 */       int iLowerCompare = 1;
/* 1615 */       int iUpperCompare = -1;
/* 1616 */       if (a_sLowerBoundVersion != null)
/*      */       {
/* 1618 */         iLowerCompare = StringUtil.compareDotNotationNumbers(sVersion, a_sLowerBoundVersion);
/*      */       }
/* 1620 */       if (a_sUpperBoundVersion != null)
/*      */       {
/* 1622 */         iUpperCompare = StringUtil.compareDotNotationNumbers(sVersion, a_sUpperBoundVersion);
/*      */       }
/*      */ 
/* 1625 */       if (((a_bLowerInc) && (iLowerCompare < 0)) || ((!a_bLowerInc) && (iLowerCompare <= 0)) || ((a_bUpperInc) && (iUpperCompare > 0)) || ((!a_bUpperInc) && (iUpperCompare >= 0)))
/*      */       {
/* 1628 */         itVer.remove();
/* 1629 */         versionElement.detach();
/* 1630 */         rootRemoved.addContent(versionElement);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1636 */     return removedChangesDOM;
/*      */   }
/*      */ 
/*      */   private void removeVersions(Document a_changesDOM, Document a_toRemoveChangesDOM)
/*      */   {
/* 1651 */     HashSet versionsToRemove = new HashSet();
/*      */ 
/* 1654 */     Element rootChangesToRemove = a_toRemoveChangesDOM.getRootElement();
/* 1655 */     for (Iterator itVer = rootChangesToRemove.getChildren("version").iterator(); itVer.hasNext(); )
/*      */     {
/* 1657 */       Element versionElement = (Element)itVer.next();
/* 1658 */       String sVersion = versionElement.getAttributeValue("number");
/* 1659 */       versionsToRemove.add(sVersion);
/*      */     }
/*      */ 
/* 1663 */     Element rootChanges = a_changesDOM.getRootElement();
/* 1664 */     for (Iterator itVer = rootChanges.getChildren("version").iterator(); itVer.hasNext(); )
/*      */     {
/* 1666 */       Element versionElement = (Element)itVer.next();
/* 1667 */       String sVersion = versionElement.getAttributeValue("number");
/* 1668 */       if (versionsToRemove.contains(sVersion))
/*      */       {
/* 1670 */         itVer.remove();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addVersions(Document a_changesDOM, Document a_toAddChangesDOM)
/*      */   {
/* 1688 */     Element rootChanges = a_changesDOM.getRootElement();
/* 1689 */     Element rootChangesToAdd = a_toAddChangesDOM.getRootElement();
/* 1690 */     for (Iterator itVer = rootChangesToAdd.getChildren("version").iterator(); itVer.hasNext(); )
/*      */     {
/* 1692 */       Element versionElement = (Element)itVer.next();
/* 1693 */       itVer.remove();
/* 1694 */       versionElement.detach();
/* 1695 */       rootChanges.addContent(versionElement);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Document removedAppliedChanges(Document a_changesJDOM)
/*      */     throws Bn2Exception
/*      */   {
/* 1721 */     Document previouslyAppliedChangesJDOM = null;
/*      */ 
/* 1726 */     if (getStoredChangesFileExists())
/*      */     {
/* 1728 */       this.m_logger.info("Stored changes file found.  Using to calculate version elements to be applied");
/* 1729 */       previouslyAppliedChangesJDOM = getStoredChangesJDOM();
/* 1730 */       removeVersions(a_changesJDOM, previouslyAppliedChangesJDOM);
/*      */     }
/*      */     else
/*      */     {
/* 1734 */       String sCurrentVersion = this.m_refDataManager.getSystemSetting("CurrentVersion");
/* 1735 */       this.m_logger.info("Stored changes not found.  Assuming all versions prior to " + sCurrentVersion + " have been applied");
/*      */ 
/* 1737 */       previouslyAppliedChangesJDOM = removeVersions(a_changesJDOM, sCurrentVersion, null, false, false);
/*      */     }
/*      */ 
/* 1740 */     return previouslyAppliedChangesJDOM;
/*      */   }
/*      */ 
/*      */   private boolean getHasVersionElements(Document a_changesDOM)
/*      */   {
/* 1753 */     Element rootChanges = a_changesDOM.getRootElement();
/* 1754 */     return rootChanges.getChild("version") != null;
/*      */   }
/*      */ 
/*      */   private Document retrieveChangesJDOM(String a_sVersion)
/*      */     throws Bn2Exception, IOException
/*      */   {
/* 1774 */     return buildJDOMFromRemoteXML(AssetBankSettings.getApplicationUpdateUrl(a_sVersion) + "changes.xml");
/*      */   }
/*      */ 
/*      */   private Document buildJDOMFromRemoteXML(String a_sXmlUrl)
/*      */     throws Bn2Exception, IOException
/*      */   {
/* 1795 */     String ksMethodName = "buildJDOMFromRemoteXML";
/*      */ 
/* 1797 */     Document dom = null;
/* 1798 */     SAXBuilder saxBuilder = new SAXBuilder();
/*      */ 
/* 1804 */     InputStream response = null;
/*      */ 
/* 1807 */     URLConnection connection = createGetConnection(a_sXmlUrl);
/*      */     try
/*      */     {
/* 1811 */       response = connection.getInputStream();
/* 1812 */       dom = saxBuilder.build(response);
/* 1813 */       response.close();
/*      */     }
/*      */     catch (JDOMException e)
/*      */     {
/* 1817 */       this.m_logger.error("ApplicationUpdateManager.buildJDOMFromRemoteXML - " + e);
/* 1818 */       throw new Bn2Exception("ApplicationUpdateManager.buildJDOMFromRemoteXML", e);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1822 */       this.m_logger.error("ApplicationUpdateManager.buildJDOMFromRemoteXML - " + e);
/* 1823 */       throw new Bn2Exception("ApplicationUpdateManager.buildJDOMFromRemoteXML", e);
/*      */     }
/*      */ 
/* 1826 */     return dom;
/*      */   }
/*      */ 
/*      */   private Document buildJDOMFromLocalXml(String a_sXmlPath)
/*      */     throws Bn2Exception
/*      */   {
/* 1846 */     Document dom = null;
/* 1847 */     SAXBuilder saxBuilder = new SAXBuilder();
/*      */ 
/* 1850 */     saxBuilder.setValidation(false);
/* 1851 */     saxBuilder.setFeature("http://xml.org/sax/features/validation", false);
/* 1852 */     saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
/* 1853 */     saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
/*      */     try
/*      */     {
/* 1857 */       dom = saxBuilder.build(a_sXmlPath);
/*      */     }
/*      */     catch (JDOMException e)
/*      */     {
/* 1861 */       throw new Bn2Exception("Exception whilst building DOM from " + a_sXmlPath, e);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1865 */       throw new Bn2Exception("Exception whilst building DOM from " + a_sXmlPath, e);
/*      */     }
/*      */ 
/* 1868 */     return dom;
/*      */   }
/*      */ 
/*      */   private URLConnection createGetConnection(String a_sGetURL)
/*      */     throws Bn2Exception, IOException
/*      */   {
/* 1892 */     URL url = new URL(a_sGetURL);
/* 1893 */     URLConnection connection = url.openConnection();
/* 1894 */     return connection;
/*      */   }
/*      */ 
/*      */   private boolean getStoredChangesFileExists()
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/* 1910 */       this.m_refDataManager.getSystemSetting("StoredChangesWritten");
/*      */     }
/*      */     catch (SettingNotFoundException e)
/*      */     {
/* 1914 */       return false;
/*      */     }
/*      */ 
/* 1917 */     return new File(AssetBankSettings.getApplicationPath() + File.separator + k_sChangesStoreFilePath).exists();
/*      */   }
/*      */ 
/*      */   private Document getStoredChangesJDOM()
/*      */     throws Bn2Exception
/*      */   {
/* 1930 */     return buildJDOMFromLocalXml(AssetBankSettings.getApplicationPath() + File.separator + k_sChangesStoreFilePath);
/*      */   }
/*      */ 
/*      */   private void writeStoreChangesJDOM(DBTransaction a_dbTransaction, Document a_storedChangesDOM)
/*      */     throws Bn2Exception
/*      */   {
/* 1944 */     String ksMethodName = "writeStoreChangesJDOM";
/*      */     try
/*      */     {
/* 1949 */       String sFullFilePath = AssetBankSettings.getApplicationPath() + File.separator + k_sChangesStoreFilePath;
/*      */ 
/* 1954 */       File fullFilePath = new File(sFullFilePath);
/* 1955 */       FileUtils.forceMkdir(fullFilePath.getParentFile());
/*      */ 
/* 1958 */       writeDOMToFile(a_storedChangesDOM, sFullFilePath);
/*      */ 
/* 1960 */       this.m_refDataManager.setSystemSetting(a_dbTransaction, "StoredChangesWritten", "true");
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1964 */       throw new Bn2Exception("ApplicationUpdateManager.writeStoreChangesJDOM", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Document createEmptyChangesDOM()
/*      */   {
/* 1977 */     Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
/* 1978 */     Element root = new Element("changes");
/* 1979 */     root.addNamespaceDeclaration(xsiNamespace);
/* 1980 */     root.setAttribute("noNamespaceSchemaLocation", "http://www.assetbank.co.uk/download/update/changes.xsd", xsiNamespace);
/*      */ 
/* 1982 */     Document changes = new Document(root);
/*      */ 
/* 1984 */     return changes;
/*      */   }
/*      */ 
/*      */   private UpdatePermissionDetails checkUpdatePermissions()
/*      */     throws Bn2Exception
/*      */   {
/* 1997 */     String ksMethodName = "checkUpdatePermissions";
/*      */ 
/* 2000 */     UpdatePermissionDetails updatePermissionDetails = new UpdatePermissionDetails();
/*      */ 
/* 2003 */     DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */     try
/*      */     {
/* 2006 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 2008 */       Connection con = dbTransaction.getConnection();
/*      */ 
/* 2010 */       sqlGenerator.testAlterSchema(con);
/*      */     }
/*      */     catch (SQLStatementException e)
/*      */     {
/* 2015 */       this.m_logger.info("ApplicationUpdateManager.checkUpdatePermissions - Exception while checking database permissions:", e);
/* 2016 */       updatePermissionDetails.setInsufficientDatabasePermissions(true);
/* 2017 */       updatePermissionDetails.setDatabasePermissionError(e.getMessage() + ": " + e.getSQLException().getMessage());
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2023 */         dbTransaction.commit();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 2027 */         throw new Bn2Exception("ApplicationUpdateManager.checkUpdatePermissions", e);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2033 */     String[] saCheckDirs = StringUtil.convertToArray(AssetBankSettings.getCheckPermissionDirs(), ",");
/* 2034 */     String sIgnoreRegExpr = AssetBankSettings.getCheckPermissionDirsIgnore();
/* 2035 */     for (String checkDir : saCheckDirs)
/*      */     {
/* 2037 */       File dir = new File(AssetBankSettings.getApplicationPath() + File.separator + checkDir);
/* 2038 */       checkCanReadWrite(dir, updatePermissionDetails, sIgnoreRegExpr);
/*      */     }
/*      */ 
/* 2041 */     return updatePermissionDetails;
/*      */   }
/*      */ 
/*      */   private void checkCanReadWrite(File a_file, UpdatePermissionDetails a_updatePermissionDetails, String a_sIgnoreRegExpr)
/*      */   {
/* 2061 */     String ksMethodName = "checkCanReadWrite";
/*      */ 
/* 2064 */     if (a_file.isDirectory())
/*      */     {
/* 2066 */       File[] children = a_file.listFiles();
/* 2067 */       for (int i = 0; (children != null) && (i < children.length); i++)
/*      */       {
/* 2069 */         checkCanReadWrite(children[i], a_updatePermissionDetails, a_sIgnoreRegExpr);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2074 */     if (a_file.getAbsolutePath().matches(a_sIgnoreRegExpr))
/*      */     {
/* 2076 */       return;
/*      */     }
/*      */ 
/* 2081 */     if ((!a_file.canWrite()) && (a_file.canRead()))
/*      */     {
/* 2083 */       a_updatePermissionDetails.setInsufficientFileSystemPermissions(true);
/* 2084 */       a_updatePermissionDetails.getInsufficientPermissionFiles().add(a_file.getAbsolutePath());
/* 2085 */       this.m_logger.error("ApplicationUpdateManager.checkCanReadWrite - Error while checking file system permissions - insufficient permission on " + a_file.getAbsolutePath());
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean checkAppsettingCondition(String a_sAppSettingCondition)
/*      */   {
/* 2101 */     if (a_sAppSettingCondition.indexOf('=') > 0)
/*      */     {
/* 2103 */       String[] property = a_sAppSettingCondition.split("=");
/* 2104 */       String sValue = getPropertyValue(this.m_appSettings, property[0]);
/* 2105 */       if ((sValue == null) || (!sValue.trim().equalsIgnoreCase(property[1])))
/*      */       {
/* 2107 */         return false;
/*      */       }
/*      */ 
/*      */     }
/* 2111 */     else if (StringUtils.isEmpty(getPropertyValue(this.m_appSettings, a_sAppSettingCondition)))
/*      */     {
/* 2113 */       return false;
/*      */     }
/*      */ 
/* 2116 */     return true;
/*      */   }
/*      */ 
/*      */   private void mergeWebXml(String a_sOldFile, String a_sNewFile)
/*      */   {
/* 2132 */     String ksMethodName = "mergeWebXML";
/*      */     try
/*      */     {
/* 2137 */       Document oldWebXml = buildJDOMFromLocalXml(a_sOldFile);
/* 2138 */       Document newWebXml = buildJDOMFromLocalXml(a_sNewFile);
/*      */ 
/* 2141 */       Element oldSessionConfig = (Element)oldWebXml.getRootElement().getContent(new SessionConfigFilter()).get(0);
/* 2142 */       Element newSessionConfig = (Element)newWebXml.getRootElement().getContent(new SessionConfigFilter()).get(0);
/*      */ 
/* 2145 */       newWebXml.getRootElement().setContent(newWebXml.getRootElement().indexOf(newSessionConfig), oldSessionConfig.detach());
/*      */ 
/* 2148 */       writeDOMToFile(newWebXml, a_sNewFile);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2152 */       addErrorMessage("Merge Web.xml Error: " + e.getMessage(), e);
/* 2153 */       addProgressMessage("There was a problem merging web.xml.  See below for details.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getUpdateStatus()
/*      */   {
/* 2163 */     return this.m_aiUpdateStatus.get();
/*      */   }
/*      */ 
/*      */   public List getProgressDisplayMessages()
/*      */   {
/* 2174 */     return (List)this.m_vecProgressMessages.clone();
/*      */   }
/*      */ 
/*      */   public List getErrorDisplayMessages()
/*      */   {
/* 2185 */     return (List)this.m_vecErrorMessages.clone();
/*      */   }
/*      */ 
/*      */   public Collection getStoredUpdateDetails()
/*      */   {
/* 2193 */     return this.m_storedUpdateDetails;
/*      */   }
/*      */ 
/*      */   public void setStoredUpdateDetails(Collection a_StoredUpdatedetails)
/*      */   {
/* 2202 */     this.m_storedUpdateDetails = a_StoredUpdatedetails;
/*      */   }
/*      */ 
/*      */   private void addProgressMessage(String a_sProgressMessage)
/*      */   {
/* 2211 */     this.m_logger.info("ApplicationUpdateManager: " + a_sProgressMessage);
/* 2212 */     this.m_vecProgressMessages.add(a_sProgressMessage);
/*      */   }
/*      */ 
/*      */   private void addErrorMessage(String a_sErrorMessage, Throwable e)
/*      */   {
/* 2220 */     this.m_logger.error("ApplicationUpdateManager: " + a_sErrorMessage, e);
/* 2221 */     this.m_vecErrorMessages.add(a_sErrorMessage);
/*      */   }
/*      */ 
/*      */   public String getFailureError()
/*      */   {
/* 2230 */     return this.m_sFailureError;
/*      */   }
/*      */ 
/*      */   private void setFailureError(String a_sFailureError)
/*      */   {
/* 2238 */     this.m_sFailureError = a_sFailureError;
/*      */   }
/*      */ 
/*      */   private String getSQLTypeValue()
/*      */     throws Bn2Exception
/*      */   {
/* 2255 */     if ((SQLGenerator.getInstance() instanceof MySqlSQLGenerator))
/*      */     {
/* 2257 */       return "mysql";
/*      */     }
/* 2259 */     if ((SQLGenerator.getInstance() instanceof SQLServerGenerator))
/*      */     {
/* 2261 */       return "sqlserver";
/*      */     }
/* 2263 */     if ((SQLGenerator.getInstance() instanceof OracleSQLGenerator))
/*      */     {
/* 2265 */       return "oracle";
/*      */     }
/*      */ 
/* 2268 */     return null;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.service.ApplicationUpdateManager
 * JD-Core Version:    0.6.0
 */