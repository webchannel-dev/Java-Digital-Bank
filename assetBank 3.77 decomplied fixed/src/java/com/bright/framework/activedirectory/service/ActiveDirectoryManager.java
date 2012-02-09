/*     */ package com.bright.framework.activedirectory.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.activedirectory.constant.ActiveDirectorySettings;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.exchange.bean.ADUser;
/*     */ import com.bright.framework.exchange.exception.ADException;
/*     */ import com.bright.framework.exchange.exception.ConnectException;
/*     */ import com.bright.framework.exchange.service.ActiveDirectoryBridge;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.exception.AuthenticationErrorException;
/*     */ import com.bright.framework.user.exception.AuthenticationException;
/*     */ import com.bright.framework.user.exception.UsernameInUseException;
/*     */ import com.bright.framework.user.service.BaseRemoteUserManager;
/*     */ import com.bright.framework.user.service.LocalUserManager;
/*     */ import com.bright.framework.user.service.RemoteUserManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ActiveDirectoryManager extends BaseRemoteUserManager
/*     */   implements RemoteUserManager
/*     */ {
/*     */   private static final String c_ksClassName = "ActiveDirectoryManager";
/*  79 */   private boolean m_bSyncInProgress = false;
/*  80 */   private Object m_oSyncLock = new Object();
/*     */ 
/*  82 */   private Vector m_vecLDAPServerInfoList = new Vector();
/*     */ 
/*  84 */   private Log m_logger = null;
/*     */ 
/*     */   public ActiveDirectoryManager()
/*     */   {
/*  99 */     boolean bSuspendADAuthentication = ActiveDirectorySettings.getSuspendADAuthentication();
/*     */ 
/* 101 */     this.m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/* 103 */     if (!bSuspendADAuthentication)
/*     */     {
/* 105 */       int iLDAPServerCount = ActiveDirectorySettings.getAdLDAPServerCount();
/*     */ 
/* 107 */       if (iLDAPServerCount <= 0)
/*     */       {
/* 110 */         iLDAPServerCount = 1;
/*     */       }
/*     */ 
/* 113 */       String sDelimiter = ActiveDirectorySettings.getAdDelimiter();
/*     */ 
/* 116 */       for (int iServerIndex = 0; iServerIndex < iLDAPServerCount; iServerIndex++)
/*     */       {
/* 119 */         LDAPServerInfo ldapServerInfo = new LDAPServerInfo();
/*     */ 
/* 121 */         ldapServerInfo.m_sApplicationUserDistinguishedName = ActiveDirectorySettings.getAdWmsUserDistinguishedName(iServerIndex);
/* 122 */         ldapServerInfo.m_sApplicationUserPassword = ActiveDirectorySettings.getAdWmsUserPassword(iServerIndex);
/*     */ 
/* 124 */         String sServerUrls = ActiveDirectorySettings.getAdLDAPServerUrl(iServerIndex);
/*     */ 
/* 128 */         String sBaseList = ActiveDirectorySettings.getAdLdapBaseList(iServerIndex);
/* 129 */         String sBaseListForOnFly = ActiveDirectorySettings.getAdLdapOnFlyBaseList(iServerIndex);
/*     */ 
/* 132 */         if ((sDelimiter == null) || (sDelimiter.length() == 0))
/*     */         {
/* 134 */           ldapServerInfo.m_asLdapServerUrl = new String[1];
/* 135 */           ldapServerInfo.m_asLdapServerUrl[0] = sServerUrls;
/*     */ 
/* 137 */           ldapServerInfo.m_saAdLdapBaseList = new String[1];
/* 138 */           ldapServerInfo.m_saAdLdapBaseList[0] = sBaseList;
/*     */ 
/* 140 */           ldapServerInfo.m_saAdLdapOnFlyBaseList = new String[1];
/* 141 */           ldapServerInfo.m_saAdLdapOnFlyBaseList[0] = sBaseListForOnFly;
/*     */         }
/*     */         else
/*     */         {
/* 146 */           ldapServerInfo.m_asLdapServerUrl = sServerUrls.split(sDelimiter);
/*     */ 
/* 149 */           ldapServerInfo.m_saAdLdapBaseList = sBaseList.split(sDelimiter);
/*     */ 
/* 152 */           ldapServerInfo.m_saAdLdapOnFlyBaseList = sBaseListForOnFly.split(sDelimiter);
/*     */         }
/*     */ 
/* 156 */         this.m_vecLDAPServerInfoList.add(ldapServerInfo);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void authenticateUser(HttpServletRequest a_request, HttpServletResponse a_response, String a_sUserDistinguishedName, String a_sPassword, int a_iServerIndex)
/*     */     throws AuthenticationException, AuthenticationErrorException
/*     */   {
/* 181 */     if (!ActiveDirectorySettings.getSuspendADAuthentication())
/*     */     {
/* 184 */       if (a_iServerIndex >= this.m_vecLDAPServerInfoList.size())
/*     */       {
/* 187 */         a_iServerIndex = 0;
/*     */       }
/*     */ 
/* 191 */       LDAPServerInfo serverInfo = (LDAPServerInfo)this.m_vecLDAPServerInfoList.get(a_iServerIndex);
/*     */       try
/*     */       {
/* 196 */         ActiveDirectoryBridge adBridge = connectToADBridge(serverInfo.m_asLdapServerUrl, a_sUserDistinguishedName, a_sPassword);
/*     */ 
/* 201 */         adBridge.close();
/*     */       }
/*     */       catch (ConnectException e)
/*     */       {
/* 205 */         throw new AuthenticationErrorException("ActiveDirectoryManager.authenticateUser() : Could not connect to Active Directory : " + e, e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private ActiveDirectoryBridge connectToADBridge(String[] a_asServerUrl, String a_sDN, String a_sPassword)
/*     */     throws ConnectException, AuthenticationException
/*     */   {
/* 233 */     ActiveDirectoryBridge adBridge = null;
/* 234 */     boolean bConnected = false;
/*     */ 
/* 237 */     for (int i = 0; (!bConnected) && (i < a_asServerUrl.length); i++)
/*     */     {
/* 239 */       adBridge = new ActiveDirectoryBridge(a_asServerUrl[i]);
/*     */       try
/*     */       {
/* 244 */         adBridge.connect(a_sDN, a_sPassword);
/* 245 */         bConnected = true;
/*     */       }
/*     */       catch (ConnectException ce)
/*     */       {
/* 249 */         this.m_logger.warn("ActiveDirectoryManager.connectToADBridge: failed to connect to the following LDAP server: " + a_asServerUrl[i] + ". Error message: " + ce.getMessage());
/*     */ 
/* 251 */         if (i + 1 >= a_asServerUrl.length)
/*     */           continue;
/* 253 */         this.m_logger.warn("ActiveDirectoryManager.connectToADBridge: trying next LDAP server in list.");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 258 */     if (!bConnected)
/*     */     {
/* 261 */       throw new ConnectException("ActiveDirectoryManager.connectToADBridge: could not connect LDAP server (main or backup)");
/*     */     }
/*     */ 
/* 264 */     return adBridge;
/*     */   }
/*     */ 
/*     */   public void synchronise(LocalUserManager a_userManager, DBTransactionManager a_transactionManager)
/*     */     throws Bn2Exception
/*     */   {
/* 285 */     String ksMethodName = "synchronise";
/*     */ 
/* 287 */     boolean bSuspendADAuthentication = ActiveDirectorySettings.getSuspendADAuthentication();
/* 288 */     if (!bSuspendADAuthentication)
/*     */     {
/* 291 */       if (startSync())
/*     */       {
/*     */         try
/*     */         {
/* 296 */           synchroniseUsers(a_userManager, a_transactionManager, null, null);
/*     */         }
/*     */         finally
/*     */         {
/* 300 */           endSync();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 305 */         this.m_logger.debug("ActiveDirectoryManager.synchronise - could not start synchronisation as one is in progress already");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void synchroniseUsers(LocalUserManager a_userManager, DBTransactionManager a_transactionManager, DBTransaction a_dbTransaction, String a_sUsername)
/*     */     throws Bn2Exception
/*     */   {
/* 330 */     String ksMethodName = "synchroniseUsers";
/*     */ 
/* 333 */     DBTransaction dbTransaction = a_dbTransaction;
/* 334 */     boolean bNewTransactionPerUser = a_dbTransaction == null;
/*     */ 
/* 337 */     String sCriteria = null;
/* 338 */     if (a_sUsername == null)
/*     */     {
/* 340 */       sCriteria = ActiveDirectorySettings.getAdUserSearchCriteria();
/*     */     }
/*     */     else
/*     */     {
/* 344 */       sCriteria = ActiveDirectorySettings.getAdUsernameProperty() + "=" + a_sUsername;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 351 */       for (int iServerIndex = 0; iServerIndex < this.m_vecLDAPServerInfoList.size(); iServerIndex++)
/*     */       {
/* 354 */         Vector vecExistingActiveDirectoryUserIdList = a_userManager.getUserIdsOfRemoteUsers(null, iServerIndex, a_sUsername);
/*     */ 
/* 357 */         boolean bOnFly = StringUtil.stringIsPopulated(a_sUsername);
/* 358 */         Vector vecAllUsers = getUsersFromLDAPServer(iServerIndex, sCriteria, bOnFly);
/*     */ 
/* 361 */         if ((vecAllUsers == null) || (vecAllUsers.size() <= 0))
/*     */           continue;
/* 363 */         ADUser adUser = null;
/* 364 */         User appUser = null;
/*     */ 
/* 367 */         for (int i = 0; i < vecAllUsers.size(); i++)
/*     */         {
/* 370 */           adUser = (ADUser)vecAllUsers.get(i);
/*     */ 
/* 373 */           if ((adUser.getAccountName() == null) || (adUser.getAccountName().length() <= 0)) {
/*     */             continue;
/*     */           }
/* 376 */           if (bNewTransactionPerUser)
/*     */           {
/* 378 */             dbTransaction = a_transactionManager.getNewTransaction();
/*     */           }
/*     */ 
/* 382 */           long lUserId = a_userManager.getUserIdForLocalUsername(dbTransaction, adUser.getAccountName());
/*     */ 
/* 384 */           if (lUserId <= 0L)
/*     */           {
/* 386 */             this.m_logger.debug("synchroniseUsers: adding user: " + adUser.getAccountName());
/*     */ 
/* 389 */             appUser = a_userManager.createUser();
/*     */ 
/* 391 */             appUser.setRemoteUser(true);
/*     */ 
/* 394 */             appUser.setHidden(ActiveDirectorySettings.getHideNewADUsers());
/*     */           }
/*     */           else
/*     */           {
/* 398 */             this.m_logger.debug("synchroniseUsers: updating user: " + adUser.getAccountName());
/*     */ 
/* 401 */             appUser = a_userManager.getUser(dbTransaction, lUserId);
/*     */ 
/* 404 */             vecExistingActiveDirectoryUserIdList.remove(new Long(lUserId));
/*     */           }
/*     */ 
/* 412 */           appUser.setDisplayName(adUser.getDisplayName());
/* 413 */           appUser.setCommonName(adUser.getCN());
/* 414 */           appUser.setRemoteUsername(adUser.getDistinguishedName());
/* 415 */           appUser.setMailbox(adUser.getMailBoxName());
/* 416 */           appUser.setUsername(adUser.getAccountName());
/* 417 */           appUser.setRemoteServerIndex(iServerIndex);
/*     */ 
/* 419 */           if ((adUser.getEmailAddress() != null) && (adUser.getEmailAddress().length() > 0))
/*     */           {
/* 422 */             String sEmailSuffix = "";
/* 423 */             if (ActiveDirectorySettings.getAdEmailSuffix() != null)
/*     */             {
/* 425 */               sEmailSuffix = ActiveDirectorySettings.getAdEmailSuffix();
/*     */             }
/*     */ 
/* 428 */             appUser.setEmailAddress(adUser.getEmailAddress() + sEmailSuffix);
/*     */           }
/* 430 */           if ((adUser.getForename() != null) && (adUser.getForename().length() > 0))
/*     */           {
/* 432 */             appUser.setForename(adUser.getForename());
/*     */           }
/* 434 */           if ((adUser.getSurname() != null) && (adUser.getSurname().length() > 0))
/*     */           {
/* 436 */             appUser.setSurname(adUser.getSurname());
/*     */           }
/* 438 */           if ((adUser.getCompany() != null) && (adUser.getCompany().length() > 0))
/*     */           {
/* 440 */             appUser.setOrganisation(adUser.getCompany());
/*     */           }
/*     */ 
/*     */           try
/*     */           {
/* 446 */             User newUser = a_userManager.saveUser(dbTransaction, appUser);
/*     */ 
/* 449 */             Vector vecMappedGroups = a_userManager.getMappedGroups();
/* 450 */             if (vecMappedGroups.size() > 0)
/*     */             {
/* 452 */               updateUserInGroups(dbTransaction, a_userManager, vecMappedGroups, newUser, adUser.getMemberOf());
/*     */             }
/*     */ 
/* 460 */             a_userManager.addUserToLoggedInUsersGroup(dbTransaction, newUser.getId());
/*     */           }
/*     */           catch (UsernameInUseException e)
/*     */           {
/* 466 */             this.m_logger.error("ActiveDirectoryManager.synchroniseUsers - " + e.getMessage() + " user=" + adUser.getAccountName());
/*     */           }
/*     */ 
/* 469 */           if (!bNewTransactionPerUser) {
/*     */             continue;
/*     */           }
/* 472 */           dbTransaction.commit();
/* 473 */           dbTransaction = null;
/*     */         }
/*     */ 
/* 481 */         if (FrameworkSettings.getHideMissingLdapUsers())
/*     */         {
/* 483 */           Iterator it = vecExistingActiveDirectoryUserIdList.iterator();
/* 484 */           while (it.hasNext())
/*     */           {
/* 486 */             long lOldUserId = ((Long)it.next()).longValue();
/*     */ 
/* 488 */             this.m_logger.debug("synchroniseUsers: hiding user: " + lOldUserId);
/*     */ 
/* 490 */             if (bNewTransactionPerUser)
/*     */             {
/* 492 */               dbTransaction = a_transactionManager.getNewTransaction();
/*     */             }
/*     */ 
/* 496 */             User oldUser = a_userManager.getUser(dbTransaction, lOldUserId);
/*     */ 
/* 498 */             oldUser.setHidden(true);
/*     */ 
/* 500 */             a_userManager.saveUser(dbTransaction, oldUser);
/*     */ 
/* 502 */             if (bNewTransactionPerUser)
/*     */             {
/* 504 */               dbTransaction.commit();
/* 505 */               dbTransaction = null;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 510 */         this.m_logger.debug("Finished getting users from LDAP server: " + iServerIndex);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 520 */       this.m_logger.error("ActiveDirectoryManager.synchroniseUsers - ", e);
/*     */ 
/* 522 */       if (bNewTransactionPerUser)
/*     */       {
/*     */         try
/*     */         {
/* 526 */           if (dbTransaction != null)
/*     */           {
/* 528 */             dbTransaction.rollback();
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (SQLException se)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 538 */       throw new Bn2Exception("ActiveDirectoryManager.synchroniseUsers - " + e.getMessage(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 542 */       if (bNewTransactionPerUser)
/*     */       {
/*     */         try
/*     */         {
/* 546 */           if (dbTransaction != null)
/*     */           {
/* 548 */             dbTransaction.commit();
/*     */           }
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 553 */           this.m_logger.error("Exception commiting transaction in ActiveDirectoryManager.synchroniseUsers");
/* 554 */           throw new Bn2Exception("Exception commiting transaction in ActiveDirectoryManager.synchroniseUsers", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private Vector getUsersFromLDAPServer(int a_iServerIndex, String a_sCriteria, boolean a_bOnFly)
/*     */   {
/* 570 */     Vector vecAllUsers = null;
/*     */ 
/* 573 */     LDAPServerInfo serverInfo = (LDAPServerInfo)this.m_vecLDAPServerInfoList.get(a_iServerIndex);
/*     */ 
/* 575 */     this.m_logger.debug("getUsersFromLDAPServer.Getting users from LDAP server: " + a_iServerIndex);
/*     */ 
/* 578 */     ActiveDirectoryBridge adBridge = null;
/*     */ 
/* 580 */     String[] asBaseList = serverInfo.m_saAdLdapBaseList;
/* 581 */     if (a_bOnFly)
/*     */     {
/* 583 */       asBaseList = serverInfo.m_saAdLdapOnFlyBaseList;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 588 */       adBridge = connectToADBridge(serverInfo.m_asLdapServerUrl, serverInfo.m_sApplicationUserDistinguishedName, serverInfo.m_sApplicationUserPassword);
/*     */ 
/* 592 */       vecAllUsers = adBridge.getAllUsers(asBaseList, ActiveDirectorySettings.getAdUsernameProperty(), ActiveDirectorySettings.getAdDistinguishedNameProperty(), ActiveDirectorySettings.getAdEmailProperty(), a_sCriteria, 0L);
/*     */ 
/* 599 */       adBridge.close();
/*     */     }
/*     */     catch (ADException ade)
/*     */     {
/* 603 */       this.m_logger.error("getUsersFromLDAPServer: Failed to synchronise with LDAP server " + serverInfo.m_asLdapServerUrl + "- error message is below:");
/* 604 */       this.m_logger.error("ADException:", ade);
/*     */     }
/*     */     catch (AuthenticationException ade)
/*     */     {
/* 608 */       this.m_logger.error("getUsersFromLDAPServer: Failed to synchronise with LDAP server " + serverInfo.m_asLdapServerUrl + "- error message is below:");
/* 609 */       this.m_logger.error("AuthenticationException:", ade);
/*     */     }
/*     */ 
/* 612 */     if (vecAllUsers == null)
/*     */     {
/* 614 */       this.m_logger.debug("getUsersFromLDAPServer: Failed to read users: vecAllUsers=null");
/*     */     }
/*     */     else
/*     */     {
/* 618 */       this.m_logger.debug("getUsersFromLDAPServer: Number of users in vecAllUsers=" + vecAllUsers.size());
/*     */     }
/*     */ 
/* 621 */     return vecAllUsers;
/*     */   }
/*     */ 
/*     */   public void synchroniseUser(LocalUserManager a_userManager, DBTransaction a_dbTransaction, String a_sUsername)
/*     */     throws Bn2Exception
/*     */   {
/* 640 */     boolean bSuspendADAuthentication = ActiveDirectorySettings.getSuspendADAuthentication();
/* 641 */     if (!bSuspendADAuthentication)
/*     */     {
/* 644 */       synchroniseUsers(a_userManager, null, a_dbTransaction, a_sUsername);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean startSync()
/*     */   {
/* 662 */     boolean bStartedOk = false;
/*     */ 
/* 664 */     synchronized (this.m_oSyncLock)
/*     */     {
/* 666 */       if (!this.m_bSyncInProgress)
/*     */       {
/* 668 */         this.m_bSyncInProgress = true;
/* 669 */         bStartedOk = true;
/*     */       }
/*     */     }
/*     */ 
/* 673 */     return bStartedOk;
/*     */   }
/*     */ 
/*     */   protected void endSync()
/*     */   {
/* 688 */     synchronized (this.m_oSyncLock)
/*     */     {
/* 690 */       this.m_bSyncInProgress = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void syncRemoteUserGroups(DBTransaction a_dbTransaction, LocalUserManager a_userManager, User a_appUser)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean populateUser(HttpServletRequest a_request, User a_user)
/*     */     throws Bn2Exception
/*     */   {
/* 704 */     return false;
/*     */   }
/*     */ 
/*     */   public class LDAPServerInfo
/*     */   {
/*  70 */     protected String[] m_asLdapServerUrl = null;
/*  71 */     protected String[] m_saAdLdapBaseList = null;
/*  72 */     protected String[] m_saAdLdapOnFlyBaseList = null;
/*  73 */     protected String m_sApplicationUserDistinguishedName = null;
/*  74 */     protected String m_sApplicationUserPassword = null;
/*     */ 
/*     */     public LDAPServerInfo()
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.activedirectory.service.ActiveDirectoryManager
 * JD-Core Version:    0.6.0
 */