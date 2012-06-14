/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bn2web.common.xml.EmptyEntityResolver;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.attribute.service.AttributeMigrationManager;
/*     */ import com.bright.assetbank.updater.constant.ApplicationUpdateConstants;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.framework.common.exception.SettingNotFoundException;
/*     */ import com.bright.framework.common.service.RefDataManager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.storage.bean.StorageDevice;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.util.PasswordHasher;
/*     */ import com.bright.framework.user.util.SimplePasswordEncrypter;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.util.XMLUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Random;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ 
/*     */ public class UpgradeManager extends Bn2Manager
/*     */ {
/*     */   private static final String k_sSettingName_RequiresAssetStatsUpdate = "UpdateAssetStats";
/*     */   private static final String k_sSettingName_CreateDefaultStorageDevice = "InitStorageDevice";
/*     */   private static final String k_sSettingName_PasswordsUpgraded = "PasswordsUpgraded";
/*     */   private static final String k_sSettingName_AttributeValuesUpgraded = "AttValsUpgraded";
/*     */   private static final String c_ksClassName = "UpgradeManager";
/*  82 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/*  89 */   private IAssetManager m_assetManager = null;
/*     */ 
/*  96 */   private RefDataManager m_refDataManager = null;
/*     */ 
/* 103 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*     */   private AttributeMigrationManager m_attributeMigrationManager;
/* 115 */   private StorageDeviceManager m_storageDeviceManager = null;
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*     */   {
/*  86 */     this.m_transactionManager = a_sTransactionManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/*  93 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setRefDataManager(RefDataManager a_refDataManager)
/*     */   {
/* 100 */     this.m_refDataManager = a_refDataManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWorkflowManager)
/*     */   {
/* 106 */     this.m_assetWorkflowManager = a_assetWorkflowManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeMigrationManager(AttributeMigrationManager a_attributeMigrationManager)
/*     */   {
/* 112 */     this.m_attributeMigrationManager = a_attributeMigrationManager;
/*     */   }
/*     */ 
/*     */   public void setStorageDeviceManager(StorageDeviceManager a_manager)
/*     */   {
/* 118 */     this.m_storageDeviceManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/* 136 */     super.startup();
/*     */ 
/* 138 */     generateFeaturedImages();
/* 139 */     checkApplicationVersion();
/*     */ 
/* 141 */     convertEmailTemplates();
/* 142 */     generateAssetWorkflows();
/* 143 */     populateDefaultStorageDevice();
/* 144 */     upgradePasswords();
/* 145 */     upgradeAttributeValues();
/*     */ 
/* 148 */     new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/* 154 */           UpgradeManager.this.setAssetAccessStats();
/*     */         }
/*     */         catch (Bn2Exception e)
/*     */         {
/* 158 */           UpgradeManager.this.m_logger.error("UpgradeManager.startup() : Exception whist setting asset access stats in Asset table", e);
/*     */         }
/*     */       }
/*     */     }
/* 148 */     .start();
/*     */   }
/*     */ 
/*     */   private void setAssetAccessStats()
/*     */     throws Bn2Exception
/*     */   {
/* 172 */     ApplicationSql appSql = SQLGenerator.getInstance();
/* 173 */     int iNumUpdated = 0;
/* 174 */     int iNumConsecutiveErrors = 0;
/* 175 */     boolean bUpdateRequired = false;
/*     */     try
/*     */     {
/* 179 */       bUpdateRequired = Boolean.parseBoolean(this.m_refDataManager.getSystemSetting("UpdateAssetStats"));
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 186 */     if (bUpdateRequired)
/*     */     {
/*     */       do
/*     */       {
/* 190 */         iNumUpdated = 0;
/* 191 */         DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/* 192 */         PreparedStatement psql = null;
/*     */         try
/*     */         {
/* 196 */           Connection con = dbTransaction.getConnection();
/*     */ 
/* 198 */           String sSql = "SELECT Id FROM Asset WHERE NumViews=-1";
/*     */ 
/* 200 */           sSql = appSql.setRowLimit(sSql, 100);
/*     */ 
/* 202 */           psql = con.prepareStatement(sSql);
/* 203 */           ResultSet rs = psql.executeQuery();
/*     */ 
/* 205 */           while (rs.next())
/*     */           {
/* 207 */             long lAssetId = rs.getLong("Id");
/*     */ 
/* 209 */             sSql = "UPDATE Asset SET NumViews=(SELECT Count(*) FROM AssetView WHERE AssetId=?) WHERE Id=?";
/* 210 */             PreparedStatement psql2 = con.prepareStatement(sSql);
/* 211 */             psql2.setLong(1, lAssetId);
/* 212 */             psql2.setLong(2, lAssetId);
/* 213 */             psql2.executeUpdate();
/* 214 */             psql2.close();
/*     */ 
/* 216 */             sSql = "UPDATE Asset SET NumDownloads=(SELECT Count(*) FROM AssetUse WHERE AssetId=?) WHERE Id=?";
/* 217 */             psql2 = con.prepareStatement(sSql);
/* 218 */             psql2.setLong(1, lAssetId);
/* 219 */             psql2.setLong(2, lAssetId);
/* 220 */             psql2.executeUpdate();
/* 221 */             psql2.close();
/*     */ 
/* 223 */             iNumUpdated++;
/*     */           }
/* 225 */           iNumConsecutiveErrors = 0;
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 229 */           iNumConsecutiveErrors++; if (iNumConsecutiveErrors > 1)
/*     */           {
/*     */             try
/*     */             {
/* 233 */               Thread.sleep(1000 + new Random().nextInt() % 1000);
/*     */             }
/*     */             catch (InterruptedException ie) {
/*     */             }
/*     */           }
/* 238 */           if (dbTransaction != null)
/*     */           {
/*     */             try
/*     */             {
/* 242 */               dbTransaction.rollback();
/*     */             }
/*     */             catch (SQLException sqle)
/*     */             {
/* 246 */               this.m_logger.error("UpgradeManager.setAssetAccessStats() : Exception whist rolling back transaction", sqle);
/*     */             }
/*     */           }
/*     */         }
/*     */         finally
/*     */         {
/*     */           try
/*     */           {
/* 254 */             if (psql != null)
/*     */             {
/* 256 */               psql.close();
/*     */             }
/* 258 */             if (dbTransaction != null)
/*     */             {
/* 260 */               dbTransaction.commit();
/*     */             }
/*     */           }
/*     */           catch (SQLException sqle)
/*     */           {
/* 265 */             this.m_logger.error("UpgradeManager.setAssetAccessStats() : Exception whist closing jdbc resources", sqle);
/*     */           }
/*     */         }
/*     */       }
/* 269 */       while ((iNumUpdated > 0) && (iNumConsecutiveErrors < 5));
/*     */ 
/* 271 */       if (iNumConsecutiveErrors < 5)
/*     */       {
/* 274 */         this.m_refDataManager.deleteSystemSetting(null, "UpdateAssetStats");
/*     */       }
/*     */       else
/*     */       {
/* 278 */         this.m_logger.error("UpgradeManager.setAssetAccessStats() : Operation terminated with 5 consecutive errors and so will be re-attempted upon next startup.");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void generateFeaturedImages()
/*     */     throws Bn2Exception
/*     */   {
/* 296 */     DBTransaction dbTransaction = null;
/*     */ 
/* 299 */     dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 303 */       this.m_assetManager.generateFeaturedImages(dbTransaction);
/*     */     }
/*     */     catch (Bn2Exception e) {
/* 306 */       this.m_logger.error("UpgradeManager: SQL Exception in generateFeaturedImages : " + e);
/*     */ 
/* 308 */       throw new Bn2Exception("UpgradeManager: SQL Exception in generateFeaturedImages : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 313 */       if (dbTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 317 */           dbTransaction.commit();
/*     */         }
/*     */         catch (SQLException sqle) {
/* 320 */           this.m_logger.error("UpgradeManager: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void generateAssetWorkflows()
/*     */     throws Bn2Exception
/*     */   {
/* 333 */     Thread thread = new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/* 340 */           UpgradeManager.this.m_assetWorkflowManager.generateAssetWorkflows();
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/* 344 */           UpgradeManager.this.m_logger.error("UpgradeManager:  Error whilst generating workflows : " + bn2e);
/*     */         }
/*     */       }
/*     */     };
/* 349 */     thread.start();
/*     */   }
/*     */ 
/*     */   private void convertEmailTemplates()
/*     */     throws Bn2Exception
/*     */   {
/* 366 */     boolean bAlreadyRun = false;
/* 367 */     DBTransaction dbTransaction = null;
/*     */     try
/*     */     {
/* 372 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */ 
/* 374 */       Connection con = dbTransaction.getConnection();
/*     */ 
/* 376 */       String sSql = "SELECT TextId FROM EmailTemplate WHERE TextId=?";
/*     */ 
/* 378 */       PreparedStatement psql = con.prepareStatement(sSql);
/*     */ 
/* 380 */       psql.setString(1, "ad_user_approved");
/*     */ 
/* 382 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 384 */       if (rs.next())
/*     */       {
/* 386 */         bAlreadyRun = true;
/*     */       }
/*     */ 
/* 389 */       psql.close();
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 393 */       this.m_logger.error("UpgradeManager: SQL Exception in convertEmailTemplates : " + e);
/*     */ 
/* 395 */       throw new Bn2Exception("UpgradeManager: SQL Exception in convertEmailTemplates : " + e, e);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 400 */       throw new Bn2Exception("UpgradeManager: SQL Exception in convertEmailTemplates : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 406 */       if (dbTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 410 */           dbTransaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 414 */           this.m_logger.error("UpgradeManager: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 423 */     if (!bAlreadyRun)
/*     */     {
/* 425 */       String sBody = null;
/* 426 */       String sSubject = null;
/* 427 */       String sFromAddress = null;
/* 428 */       Vector vTO = null;
/* 429 */       Vector vCC = null;
/* 430 */       Vector vBCC = null;
/* 431 */       String sTO = null;
/* 432 */       String sCC = null;
/* 433 */       String sBCC = null;
/* 434 */       String sFileName = null;
/* 435 */       String sCode = null;
/* 436 */       String sTextId = null;
/*     */ 
/* 439 */       String sTemplateDirectory = FrameworkSettings.getApplicationPath() + "/" + FrameworkSettings.getEmailTemplateDirectory() + "/";
/*     */ 
/* 443 */       File fTemplateDir = new File(sTemplateDirectory);
/* 444 */       File[] aFiles = fTemplateDir.listFiles();
/*     */ 
/* 447 */       for (int x = 0; x < aFiles.length; x++)
/*     */       {
/* 449 */         if (aFiles[x].getName().matches("\\.svn"))
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/* 454 */         sFileName = aFiles[x].getName();
/*     */ 
/* 458 */         SAXBuilder saxBuilder = new SAXBuilder();
/*     */ 
/* 461 */         saxBuilder.setDTDHandler(null);
/*     */ 
/* 466 */         EmptyEntityResolver emptyResolver = new EmptyEntityResolver();
/* 467 */         saxBuilder.setEntityResolver(emptyResolver);
/*     */         try
/*     */         {
/* 472 */           Document document = saxBuilder.build(aFiles[x]);
/*     */ 
/* 474 */           Element root = document.getRootElement();
/*     */ 
/* 477 */           List lstTemplates = root.getChildren("email-template");
/* 478 */           ListIterator lstiTemplates = (ListIterator)(ListIterator)lstTemplates.iterator();
/*     */ 
/* 481 */           if (lstTemplates.size() == 0)
/*     */           {
/* 483 */             this.m_logger.error("Email template file " + aFiles[x] + " has no template elements.");
/*     */ 
/* 485 */             throw new Bn2Exception("Email template file " + aFiles[x] + " has no template elements.");
/*     */           }
/*     */ 
/* 490 */           for (int iTemplate = 0; iTemplate < lstTemplates.size(); iTemplate++)
/*     */           {
/* 492 */             Element xmlTemplate = (Element)(Element)lstiTemplates.next();
/*     */ 
/* 495 */             Element subject = xmlTemplate.getChild("subject");
/* 496 */             if (subject != null)
/*     */             {
/* 498 */               sSubject = subject.getTextTrim();
/*     */             }
/*     */ 
/* 502 */             Element body = xmlTemplate.getChild("body");
/* 503 */             if (body != null)
/*     */             {
/* 505 */               sBody = body.getTextTrim();
/*     */             }
/*     */ 
/* 509 */             Element xmlAddresses = xmlTemplate.getChild("addresses");
/*     */ 
/* 511 */             vTO = XMLUtil.getTextFromChildren(xmlAddresses, "to");
/*     */ 
/* 513 */             vCC = XMLUtil.getTextFromChildren(xmlAddresses, "cc");
/*     */ 
/* 515 */             vBCC = XMLUtil.getTextFromChildren(xmlAddresses, "bcc");
/*     */ 
/* 517 */             sFromAddress = xmlAddresses.getChild("from").getTextTrim();
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (JDOMException jdome)
/*     */         {
/* 524 */           this.m_logger.error("JDOM Exception caught during EmailManager.sendTemplatedEmail : " + jdome);
/*     */         }
/*     */         catch (IOException ioe)
/*     */         {
/* 530 */           this.m_logger.error("IO Exception caught during EmailManager.sendTemplatedEmail : " + ioe);
/*     */ 
/* 533 */           throw new Bn2Exception("IO Exception caught during EmailManager.sendTemplatedEmail", ioe);
/*     */         }
/*     */ 
/* 538 */         sTO = "";
/* 539 */         sCC = "";
/* 540 */         sBCC = "";
/*     */ 
/* 543 */         for (int a = 0; a < vTO.capacity(); a++)
/*     */         {
/* 545 */           sTO = sTO + (String)vTO.elementAt(a) + ";";
/*     */         }
/*     */ 
/* 548 */         for (int a = 0; a < vCC.capacity(); a++)
/*     */         {
/* 550 */           sCC = sCC + (String)vCC.elementAt(a) + ";";
/*     */         }
/*     */ 
/* 553 */         for (int a = 0; a < vBCC.capacity(); a++)
/*     */         {
/* 555 */           sBCC = sBCC + (String)vBCC.elementAt(a) + ";";
/*     */         }
/*     */ 
/* 559 */         sTextId = sFileName.substring(0, sFileName.length() - 4);
/*     */ 
/* 561 */         sCode = sTextId.replace('_', ' ');
/*     */ 
/* 564 */         sBody = sBody.replace("\n", "<br>");
/*     */ 
/* 567 */         if (File.separator.equals("/"))
/*     */         {
/* 569 */           sBody = sBody.replace("<br><br>", "<br>");
/*     */         }
/*     */ 
/* 573 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */         try
/*     */         {
/* 577 */           Connection con = dbTransaction.getConnection();
/*     */ 
/* 579 */           String sSQL = null;
/* 580 */           PreparedStatement psql = null;
/*     */ 
/* 582 */           sSQL = "INSERT INTO EmailTemplate (LanguageId, TypeId, Code, AddrFrom, AddrTo, AddrCc, AddrBcc, Subject, Body, TextId) VALUES (?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/* 584 */           psql = con.prepareStatement(sSQL);
/* 585 */           int iCol = 1;
/* 586 */           psql.setInt(iCol++, 1);
/* 587 */           psql.setLong(iCol++, 1L);
/* 588 */           psql.setString(iCol++, sCode);
/* 589 */           psql.setString(iCol++, sFromAddress);
/* 590 */           psql.setString(iCol++, sTO);
/* 591 */           psql.setString(iCol++, sCC);
/* 592 */           psql.setString(iCol++, sBCC);
/* 593 */           psql.setString(iCol++, sSubject);
/* 594 */           psql.setString(iCol++, sBody);
/* 595 */           psql.setString(iCol++, sTextId);
/*     */ 
/* 597 */           psql.executeUpdate();
/*     */ 
/* 599 */           psql.close();
/*     */         }
/*     */         catch (Bn2Exception e)
/*     */         {
/* 604 */           this.m_logger.error("UpgradeManager: SQL Exception in convertEmailTemplates : " + e);
/*     */ 
/* 608 */           throw new Bn2Exception("UpgradeManager: SQL Exception in convertEmailTemplates : " + e, e);
/*     */         }
/*     */         catch (SQLException e)
/*     */         {
/* 613 */           throw new Bn2Exception("UpgradeManager: SQL Exception in convertEmailTemplates : " + e, e);
/*     */         }
/*     */         finally
/*     */         {
/* 619 */           if (dbTransaction != null)
/*     */           {
/*     */             try
/*     */             {
/* 623 */               dbTransaction.commit();
/*     */             }
/*     */             catch (SQLException sqle)
/*     */             {
/* 627 */               this.m_logger.error("UpgradeManager: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 639 */       String sTemplateDirectoryStandard = FrameworkSettings.getApplicationPath() + "/" + FrameworkSettings.getEmailTemplateDirectory();
/*     */ 
/* 643 */       sTemplateDirectoryStandard = sTemplateDirectoryStandard.replace("/standard", "");
/*     */ 
/* 646 */       String sNewDir = sTemplateDirectoryStandard + ".UNUSED";
/* 647 */       FileUtil.copyDir(sTemplateDirectoryStandard, sNewDir);
/*     */ 
/* 649 */       String sTemplateDirectoryCustomise = FrameworkSettings.getApplicationPath() + "/" + ApplicationUpdateConstants.k_sCustomisationsDir + "/" + FrameworkSettings.getEmailTemplateDirectory();
/*     */ 
/* 654 */       sTemplateDirectoryCustomise = sTemplateDirectoryCustomise.replace("/standard", "");
/*     */ 
/* 658 */       File fCheck = new File(sNewDir);
/*     */ 
/* 660 */       if (fCheck.exists())
/*     */       {
/* 662 */         FileUtil.deleteDir(sTemplateDirectoryStandard);
/*     */ 
/* 665 */         FileUtil.deleteDir(sTemplateDirectoryCustomise);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkApplicationVersion()
/*     */     throws Bn2Exception
/*     */   {
/* 684 */     String ksMethodName = "checkApplicationVersion";
/*     */ 
/* 686 */     String sCurrentVersion = this.m_refDataManager.getSystemSetting("CurrentVersion");
/*     */ 
/* 689 */     if (!StringUtil.stringIsPopulated(sCurrentVersion))
/*     */     {
/* 691 */       DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */ 
/* 693 */       this.m_refDataManager.updateSystemSetting(dbTransaction, "CurrentVersion", AssetBankSettings.getApplicationVersion());
/*     */       try
/*     */       {
/* 698 */         dbTransaction.commit();
/*     */       }
/*     */       catch (SQLException e) {
/* 701 */         this.m_logger.error("UpgradeManager.checkApplicationVersion - " + e);
/* 702 */         throw new Bn2Exception("UpgradeManager.checkApplicationVersion", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void populateDefaultStorageDevice()
/*     */     throws Bn2Exception
/*     */   {
/* 714 */     boolean bUpdateRequired = false;
/*     */     try
/*     */     {
/* 718 */       bUpdateRequired = Boolean.parseBoolean(this.m_refDataManager.getSystemSetting("InitStorageDevice"));
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 725 */     if (bUpdateRequired)
/*     */     {
/* 727 */       DBTransaction dbTransaction = null;
/*     */ 
/* 730 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */       try
/*     */       {
/* 734 */         StorageDevice device = this.m_storageDeviceManager.getDeviceFromDatabase(dbTransaction, 1L);
/*     */ 
/* 736 */         if (device != null)
/*     */         {
/* 738 */           String filestoreRoot = AssetBankSettings.getFilestoreRoot();
/* 739 */           if (!StringUtils.isEmpty(filestoreRoot))
/*     */           {
/* 741 */             device.setSubPath(filestoreRoot);
/*     */           }
/*     */           else
/*     */           {
/* 745 */             this.m_logger.error("UpgradeManager: no filestore-root in system settings file, so can't update the path of the default storage device");
/*     */           }
/*     */ 
/* 748 */           this.m_storageDeviceManager.saveDevice(dbTransaction, device);
/*     */ 
/* 750 */           this.m_refDataManager.deleteSystemSetting(null, "InitStorageDevice");
/*     */         }
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 755 */         this.m_logger.error("UpgradeManager: SQL Exception in populateDefaultStorageDevice : " + e);
/* 756 */         throw new Bn2Exception("UpgradeManager: SQL Exception in populateDefaultStorageDevice : " + e, e);
/*     */       }
/*     */       finally
/*     */       {
/* 761 */         if (dbTransaction != null)
/*     */         {
/*     */           try
/*     */           {
/* 765 */             dbTransaction.commit();
/*     */           }
/*     */           catch (SQLException sqle)
/*     */           {
/* 769 */             this.m_logger.error("UpgradeManager: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void upgradePasswords() throws Bn2Exception
/*     */   {
/* 784 */     String ksMethodName = "upgradePasswords";
/*     */     boolean passwordsUpgraded;
/*     */     try
/*     */     {
/* 789 */       passwordsUpgraded = Boolean.parseBoolean(this.m_refDataManager.getSystemSetting("PasswordsUpgraded"));
/*     */     }
/*     */     catch (SettingNotFoundException e)
/*     */     {
/* 795 */       passwordsUpgraded = false;
/*     */     }
/*     */ 
/* 799 */     if (passwordsUpgraded)
/*     */     {
/* 801 */       return;
/*     */     }
/*     */ 
/* 804 */     SimplePasswordEncrypter encrypter = new SimplePasswordEncrypter();
/* 805 */     PasswordHasher hasher = new PasswordHasher();
/*     */ 
/* 808 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 811 */       if (UserSettings.getEncryptPasswords())
/*     */       {
/* 813 */         this.m_logger.info("UpgradeManager.upgradePasswords: About to convert from encrypted passwords to hashed passwords");
/* 814 */         Connection con = transaction.getConnection();
/*     */ 
/* 816 */         PreparedStatement psql = con.prepareStatement("SELECT id, password FROM AssetBankUser", 1003, 1008);
/*     */ 
/* 821 */         ResultSet rs = psql.executeQuery();
/*     */ 
/* 823 */         while (rs.next())
/*     */         {
/* 826 */           String encryptedPassword = rs.getString("password");
/*     */ 
/* 828 */           if (encryptedPassword != null)
/*     */           {
/* 833 */             String password = encryptedPassword.equals(UserSettings.getDefaultPassword()) ? encryptedPassword : encrypter.decrypt(encryptedPassword);
/*     */ 
/* 837 */             String hashedPassword = hasher.saltAndHash(password);
/*     */ 
/* 839 */             rs.updateString("password", hashedPassword);
/* 840 */             rs.updateRow();
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 846 */         this.m_logger.info("UpgradeManager.upgradePasswords: Encrypted passwords not in use - no password upgrade necessary");
/*     */       }
/*     */ 
/* 849 */       this.m_refDataManager.setSystemSetting(transaction, "PasswordsUpgraded", "true");
/* 850 */       this.m_logger.info("UpgradeManager.upgradePasswords: Changed PasswordsUpgraded SystemSetting");
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/* 854 */       this.m_logger.error("UpgradeManager.upgradePasswords: SQL Exception" + sqle);
/*     */       try
/*     */       {
/* 858 */         transaction.rollback();
/*     */       }
/*     */       catch (SQLException sqle2)
/*     */       {
/* 862 */         this.m_logger.error("UpgradeManager.upgradePasswords: SQLException whilst rolling back transaction.", sqle2);
/*     */       }
/*     */ 
/* 865 */       throw new Bn2Exception("UpgradeManager.upgradePasswords: SQL Exception: " + sqle, sqle);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 872 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 876 */         this.m_logger.error("UpgradeManager: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void upgradeAttributeValues() throws Bn2Exception
/*     */   {
/* 888 */     String ksMethodName = "upgradeAttributeValues";
/*     */     boolean attributeValuesUpgraded;
/*     */     try
/*     */     {
/* 893 */       attributeValuesUpgraded = Boolean.parseBoolean(this.m_refDataManager.getSystemSetting("AttValsUpgraded"));
/*     */     }
/*     */     catch (SettingNotFoundException e)
/*     */     {
/* 899 */       attributeValuesUpgraded = false;
/*     */     }
/*     */ 
/* 903 */     if (attributeValuesUpgraded)
/*     */     {
/* 905 */       return;
/*     */     }
/*     */ 
/* 908 */     this.m_logger.info("UpgradeManager.upgradeAttributeValues: Starting attribute value data migration");
/* 909 */     this.m_attributeMigrationManager.migrate();
/*     */ 
/* 911 */     this.m_refDataManager.setSystemSetting(null, "AttValsUpgraded", "true");
/* 912 */     this.m_logger.info("UpgradeManager.upgradeAttributeValues: Data migration complete, changed AttValsUpgraded SystemSetting");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.UpgradeManager
 * JD-Core Version:    0.6.0
 */