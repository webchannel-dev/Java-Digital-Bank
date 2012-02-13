/*      */ package com.bright.framework.user.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.service.AssetLogManager;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.util.UserUtil;
/*      */ import com.bright.framework.activedirectory.constant.ActiveDirectorySettings;
/*      */ import com.bright.framework.common.exception.SettingNotFoundException;
/*      */ import com.bright.framework.common.service.RefDataManager;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.encryption.bean.AESCBCPKCS5Padding;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.user.bean.RemoteUserGroup;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.user.constant.UserConstants;
/*      */ import com.bright.framework.user.constant.UserSettings;
/*      */ import com.bright.framework.user.exception.AccountSuspendedException;
/*      */ import com.bright.framework.user.exception.AuthenticationException;
/*      */ import com.bright.framework.user.exception.InvalidLoginException;
/*      */ import com.bright.framework.user.exception.InvalidSaltedHashException;
/*      */ import com.bright.framework.user.exception.LoginException;
/*      */ import com.bright.framework.user.exception.PasswordExpiredException;
/*      */ import com.bright.framework.user.exception.PasswordReminderException;
/*      */ import com.bright.framework.user.util.PasswordHasher;
/*      */ import com.bright.framework.user.util.PasswordUtil;
/*      */ import com.bright.framework.util.DateUtil;
/*      */ import com.bright.framework.util.RC4CipherUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public abstract class UserManager extends Bn2Manager
/*      */   implements UserConstants, LocalUserManager
/*      */ {
/*      */   private static final String c_ksClassName = "UserManager";
/*  104 */   protected DataSourceComponent m_dataSource = null;
/*  105 */   protected DBTransactionManager m_transactionManager = null;
/*  106 */   protected EmailManager m_emailManager = null;
/*  107 */   protected AssetLogManager m_assetLogManager = null;
/*  108 */   protected ScheduleManager m_scheduleManager = null;
/*  109 */   protected RemoteUserManager m_remoteUserManager = null;
/*  110 */   protected RefDataManager m_refDataManager = null;
/*      */ 
/*  112 */   protected PasswordHasher m_passwordHasher = new PasswordHasher();
/*      */ 
/*      */   public abstract User saveUser(DBTransaction paramDBTransaction, User paramUser)
/*      */     throws Bn2Exception;
/*      */ 
/*      */   public abstract User getUser(DBTransaction paramDBTransaction, long paramLong)
/*      */     throws Bn2Exception;
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  137 */     super.startup();
/*      */     try
/*      */     {
/*  142 */       String sEncrypting = this.m_refDataManager.getSystemSetting("ConvertingPasswords");
/*  143 */       if ((StringUtil.stringIsPopulated(sEncrypting)) && (sEncrypting.equals("true")))
/*      */       {
/*  146 */         UserUtil.encryptPasswords();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SettingNotFoundException e)
/*      */     {
/*      */     }
/*      */ 
/*  155 */     String sRemoteUserManagerClassName = AssetBankSettings.getRemoteUserDirectoryManagerClassName();
/*      */ 
/*  157 */     if (StringUtils.isNotEmpty(sRemoteUserManagerClassName))
/*      */     {
/*      */       try
/*      */       {
/*  161 */         this.m_remoteUserManager = ((RemoteUserManager)Class.forName(sRemoteUserManagerClassName).newInstance());
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  165 */         throw new Bn2Exception("UserManager.startup() : Could not instantiate Remote User Manager " + sRemoteUserManagerClassName + " : " + e, e);
/*      */       }
/*      */ 
/*  168 */       long lSynchPeriod = AssetBankSettings.getSynchronisePeriodMillis();
/*      */ 
/*  171 */       if (lSynchPeriod > 0L)
/*      */       {
/*  173 */         TimerTask task = new TimerTask()
/*      */         {
/*      */           public void run()
/*      */           {
/*      */             try
/*      */             {
/*  179 */               UserManager.this.synchroniseRemoteUsers();
/*      */             }
/*      */             catch (Bn2Exception be)
/*      */             {
                           
/*      */             }
/*      */           }
/*      */         };
/*  188 */         this.m_scheduleManager.schedule(task, 60000L, lSynchPeriod, true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected abstract void checkUserValidForLogin(User paramUser)
/*      */     throws LoginException, Bn2Exception;
/*      */ 
/*      */   public void login(DBTransaction a_dbTransaction, HttpServletRequest a_request, HttpServletResponse a_response, String a_sUsername, String a_sPassword, boolean a_bIgnorePassword)
/*      */     throws Bn2Exception
/*      */   {
/*  252 */     if (!StringUtil.stringIsPopulated(a_sUsername))
/*      */     {
/*  254 */       throw new InvalidLoginException("The username cannot be empty.");
/*      */     }
/*  256 */     if ((!StringUtil.stringIsPopulated(a_sPassword)) && (!a_bIgnorePassword))
/*      */     {
/*  258 */       throw new InvalidLoginException("The password cannot be empty.");
/*      */     }
/*      */ 
/*  262 */     HttpSession a_userSession = a_request.getSession();
/*  263 */     UserProfile userProfile = UserProfile.getUserProfile(a_userSession);
/*      */ 
/*  267 */     if (FrameworkSettings.getImportRemoteUsersOnTheFly())
/*      */     {
/*  269 */       synchroniseRemoteUser(a_sUsername, a_dbTransaction);
/*      */     }
/*      */ 
/*  273 */     User user = checkAndGetLocalUser(a_dbTransaction, a_sUsername);
/*  274 */     if (user == null)
/*      */     {
/*  276 */       throw new InvalidLoginException("No such user");
/*      */     }
/*      */ 
/*  280 */     checkUserValidForLogin(user);
/*      */ 
/*  283 */     if (!a_bIgnorePassword)
/*      */     {
/*  285 */       checkPassword(a_dbTransaction, a_request, a_response, user, a_sPassword);
/*      */     }
/*      */ 
/*  293 */     userProfile = UserProfile.getUserProfile(a_userSession);
/*  294 */     userProfile.setUser(user);
/*      */ 
/*  298 */     if (this.m_remoteUserManager != null)
/*      */     {
/*  300 */       this.m_remoteUserManager.syncRemoteUserGroups(a_dbTransaction, this, user);
/*      */     }
/*      */ 
/*  304 */     userProfile.setCurrentLanguage(user.getLanguage());
/*      */ 
/*  307 */     processPostLogin(a_dbTransaction, userProfile);
/*      */ 
/*  309 */     if (user.getPasswordExpired())
/*      */     {
/*  311 */       throw new PasswordExpiredException("password expired");
/*      */     }
/*      */   }
/*      */ 
/*      */   private User checkAndGetLocalUser(DBTransaction a_dbTransaction, String a_sUsername)
/*      */     throws Bn2Exception
/*      */   {
/*  328 */     Connection con = null;
/*  329 */     PreparedStatement psql = null;
/*  330 */     ResultSet rs = null;
/*  331 */     User user = null;
/*      */     try
/*      */     {
/*  335 */       if (a_dbTransaction == null)
/*      */       {
/*  337 */         con = getDataSourceComponent().getConnection();
/*      */       }
/*      */       else
/*      */       {
/*  341 */         con = a_dbTransaction.getConnection();
/*      */       }
/*      */ 
/*  345 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/*  349 */       String sSql = "SELECT u.Id, u.Username, u.Password, u.IsSuspended, u.DisplayName, u.DateChangedPassword FROM AssetBankUser u WHERE " + sqlGenerator.getUpperCaseFunction("u.Username") + "=?" + " AND u.Hidden = 0";
/*      */ 
/*  354 */       psql = con.prepareStatement(sSql);
/*  355 */       psql.setString(1, a_sUsername.toUpperCase());
/*      */ 
/*  357 */       rs = psql.executeQuery();
/*      */ 
/*  359 */       long lUserId = 0L;
/*  360 */       boolean bSuspended = false;
/*  361 */       boolean bExpired = false;
/*      */ 
/*  363 */       if (rs.next())
/*      */       {
/*  365 */         lUserId = rs.getLong("Id");
/*  366 */         bSuspended = rs.getBoolean("IsSuspended");
/*      */ 
/*  369 */         Date dateChangedPassword = rs.getDate("DateChangedPassword");
/*  370 */         bExpired = hasPasswordExpired(dateChangedPassword);
/*      */       }
/*      */ 
/*  373 */       psql.close();
/*      */ 
/*  377 */       if (bSuspended)
/*      */       {
/*  379 */         rs.close();
/*  380 */         throw new AccountSuspendedException("The user's account has been suspended");
/*      */       }
/*      */ 
/*  383 */       if (lUserId > 0L)
/*      */       {
/*  386 */         user = getUser(a_dbTransaction, lUserId);
/*  387 */         user.setPasswordExpired(bExpired);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  393 */       if (a_dbTransaction == null)
/*      */       {
/*      */         try
/*      */         {
/*  397 */           con.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  405 */       this.m_logger.error("UserManager.login " + e.getMessage());
/*  406 */       throw new Bn2Exception("UserManager.login " + e.getMessage(), e);
/*      */     }
/*      */     finally
/*      */     {
/*  411 */       if ((a_dbTransaction == null) && (con != null))
/*      */       {
/*      */         try
/*      */         {
/*  415 */           con.commit();
/*  416 */           con.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  420 */           this.m_logger.error("checkAndGetLocalUser: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  425 */     return user;
/*      */   }
/*      */ 
/*      */   public boolean hasPasswordExpired(Date a_dateChangedPassword)
/*      */   {
/*  443 */     boolean expired = false;
/*      */ 
/*  445 */     if (AssetBankSettings.getForcePasswordChangeAfter() == 0)
/*      */     {
/*  447 */       expired = false;
/*      */     }
/*  449 */     else if (a_dateChangedPassword == null)
/*      */     {
/*  451 */       expired = true;
/*      */     }
/*  453 */     else if (DateUtil.daysBetweenDates(new Date(), a_dateChangedPassword) > AssetBankSettings.getForcePasswordChangeAfter())
/*      */     {
/*  455 */       expired = true;
/*      */     }
/*      */ 
/*  458 */     return expired;
/*      */   }
/*      */ 
/*      */   public void saveUsernameCookie(HttpServletResponse a_response, UserProfile a_userProfile)
/*      */   {
/*  477 */     setUsernameCookie(a_response, RC4CipherUtil.encryptToHex(a_userProfile.getUser().getUsername()), 315360000);
/*      */   }
/*      */ 
/*      */   public void saveUserDetailsCookie(HttpServletResponse a_response, UserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  495 */     User user = a_userProfile.getUser();
/*  496 */     String sValue = StringUtil.pack(new String[] { String.valueOf(user.getId()), user.getUsername(), user.getEmailAddress(), user.getForename(), user.getSurname() });
/*      */ 
/*  499 */     AESCBCPKCS5Padding encrypter = new AESCBCPKCS5Padding(UserSettings.getUserDetailsCookieKey(), UserSettings.getUserDetailsCookieIV(), true);
/*  500 */     sValue = encrypter.doCipher(sValue);
/*      */ 
/*  503 */     Cookie cookie = new Cookie("AssetBankUserDetails", sValue);
/*  504 */     cookie.setMaxAge(315360000);
/*  505 */     a_response.addCookie(cookie);
/*      */   }
/*      */ 
/*      */   public void removeUsernameCookie(HttpServletResponse a_response)
/*      */   {
/*  522 */     setUsernameCookie(a_response, null, 0);
/*      */   }
/*      */ 
/*      */   private void setUsernameCookie(HttpServletResponse a_response, String sValue, int a_iExpiry)
/*      */   {
/*  543 */     Cookie cookie = new Cookie("AssetBankUserAuth", sValue);
/*      */ 
/*  545 */     cookie.setMaxAge(a_iExpiry);
/*  546 */     a_response.addCookie(cookie);
/*      */   }
/*      */ 
/*      */   public void loginUsingCookie(HttpServletRequest a_request, HttpServletResponse a_response)
/*      */     throws Bn2Exception
/*      */   {
/*  565 */     String sUsername = null;
/*      */ 
/*  568 */     Cookie[] aCookies = a_request.getCookies();
/*      */ 
/*  570 */     for (int i = 0; (aCookies != null) && (i < aCookies.length); i++)
/*      */     {
/*  573 */       if ((aCookies[i].getName() == null) || (!aCookies[i].getName().equals("AssetBankUserAuth")))
/*      */         continue;
/*  575 */       sUsername = RC4CipherUtil.decryptFromHex(aCookies[i].getValue());
/*  576 */       break;
/*      */     }
/*      */ 
/*  581 */     if (sUsername != null)
/*      */     {
/*  583 */       DBTransaction transaction = null;
/*      */       try
/*      */       {
/*  587 */         transaction = getDBTransactionManager().getNewTransaction();
/*      */ 
/*  590 */         login(transaction, a_request, a_response, sUsername, null, true);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */         try
/*      */         {
/*  601 */           if (transaction != null)
/*      */           {
/*  603 */             transaction.rollback();
/*      */           }
/*      */         }
/*      */         catch (SQLException rbe)
/*      */         {
/*      */         }
/*      */ 
/*  610 */         this.m_logger.error("UserManager.loginUsingCookie: Exception : " + e);
/*  611 */         throw new Bn2Exception("UserManager.loginUsingCookie: Exception : " + e, e);
/*      */       }
/*      */       finally
/*      */       {
/*  616 */         if (transaction != null)
/*      */         {
/*      */           try
/*      */           {
/*  620 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/*  624 */             this.m_logger.error("UserManager.deleteUser: SQL Exception whilst trying to commit transaction " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void checkPassword(DBTransaction a_dbTransaction, HttpServletRequest a_request, HttpServletResponse a_response, User a_user, String a_sPasswordEntered)
/*      */     throws LoginException, Bn2Exception
/*      */   {
/*  648 */     if ((!a_user.isRemoteUser()) || (this.m_remoteUserManager == null) || (ActiveDirectorySettings.getSuspendADAuthentication()))
/*      */     {
/*  651 */       if (!checkPassword(a_user.getPassword(), a_sPasswordEntered))
/*      */       {
/*  653 */         throw new InvalidLoginException("Incorrect password");
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  659 */       if ((a_sPasswordEntered == null) || (a_sPasswordEntered.length() == 0))
/*      */       {
/*  661 */         throw new InvalidLoginException("No password entered");
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/*  666 */         this.m_remoteUserManager.authenticateUser(a_request, a_response, a_user.getRemoteUsername(), a_sPasswordEntered, a_user.getRemoteServerIndex());
/*      */       }
/*      */       catch (AuthenticationException ae)
/*      */       {
/*  674 */         throw new InvalidLoginException("Incorrect password");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean checkPassword(String a_sDatabasePassword, String a_sCandidatePassword)
/*      */   {
/*  689 */     if ((a_sDatabasePassword == null) && (a_sCandidatePassword == null))
/*      */     {
/*  691 */       return true;
/*      */     }
/*      */ 
/*  695 */     if ((a_sDatabasePassword == null) || (a_sCandidatePassword == null))
/*      */     {
/*  697 */       return false;
/*      */     }
/*      */ 
/*  700 */     if (UserSettings.getEncryptPasswords())
/*      */     {
/*  705 */       if ((a_sDatabasePassword.equals(UserSettings.getDefaultPassword())) && (a_sCandidatePassword.equals(UserSettings.getDefaultPassword())))
/*      */       {
/*  708 */         return true;
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/*  714 */         return this.m_passwordHasher.checkPassword(a_sDatabasePassword, a_sCandidatePassword);
/*      */       }
/*      */       catch (InvalidSaltedHashException e)
/*      */       {
/*  718 */         this.m_logger.error(e.getMessage());
/*  719 */         return false;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  725 */     return a_sCandidatePassword.equals(a_sDatabasePassword);
/*      */   }
/*      */ 
/*      */   protected String hashIfNecessary(String a_sPassword)
/*      */   {
/*  731 */     return UserSettings.getEncryptPasswords() ? this.m_passwordHasher.saltAndHash(a_sPassword) : a_sPassword;
/*      */   }
/*      */ 
/*      */   protected void processPostLogin(DBTransaction a_dbTransaction, UserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   public void logout(HttpServletRequest a_request, HttpServletResponse a_response)
/*      */     throws Bn2Exception
/*      */   {
/*  769 */     UserProfile profile = UserProfile.getUserProfile(a_request.getSession());
/*      */ 
/*  771 */     if ((profile != null) && (profile.getUser() != null))
/*      */     {
/*  773 */       boolean bIsRemote = UserProfile.getUserProfile(a_request.getSession()).getUser().isRemoteUser();
/*      */ 
/*  775 */       UserProfile.resetUserProfile(a_request.getSession());
/*      */ 
/*  777 */       if ((bIsRemote) && (this.m_remoteUserManager != null))
/*      */       {
/*  779 */         this.m_remoteUserManager.logOffUser(a_request, a_response);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getUserIdForLocalUsername(DBTransaction a_dbTransaction, String a_sUsername)
/*      */     throws Bn2Exception
/*      */   {
/*  802 */     long[] lIds = getUserIdsForFieldValue(a_dbTransaction, "Username", a_sUsername, true, true, true);
/*      */ 
/*  806 */     if (lIds == null)
/*      */     {
/*  808 */       return -1L;
/*      */     }
/*      */ 
/*  811 */     return lIds[0];
/*      */   }
/*      */ 
/*      */   public long getUserIdForRemoteUsername(DBTransaction a_dbTransaction, String a_sUsername)
/*      */     throws Bn2Exception
/*      */   {
/*  817 */     long[] lIds = getUserIdsForFieldValue(a_dbTransaction, "DistinguishedName", a_sUsername, false, false, true);
/*      */ 
/*  821 */     if (lIds == null)
/*      */     {
/*  823 */       return -1L;
/*      */     }
/*      */ 
/*  826 */     return lIds[0];
/*      */   }
/*      */ 
/*      */   public long[] getUserIdsForEmailAddress(DBTransaction a_dbTransaction, String a_sEmailAddress)
/*      */     throws Bn2Exception
/*      */   {
/*  846 */     return getUserIdsForFieldValue(a_dbTransaction, "EmailAddress", a_sEmailAddress, true, true, true);
/*      */   }
/*      */ 
/*      */   private long[] getUserIdsForFieldValue(DBTransaction a_dbTransaction, String a_sFieldName, String a_sValue, boolean a_bIncludeDeleted, boolean a_bIncludeHidden, boolean a_bCaseInsensitive)
/*      */     throws Bn2Exception
/*      */   {
/*  874 */     String ksMethodName = "getUserIdsForFieldValue";
/*      */ 
/*  876 */     Connection con = null;
/*  877 */     long[] laUserId = null;
/*  878 */     Vector vecIds = new Vector();
/*      */     try
/*      */     {
/*  882 */       if (a_dbTransaction == null)
/*      */       {
/*  884 */         con = getDataSourceComponent().getConnection();
/*      */       }
/*      */       else
/*      */       {
/*  888 */         con = a_dbTransaction.getConnection();
/*      */       }
/*      */ 
/*  892 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/*  894 */       String sSql = "SELECT Id FROM AssetBankUser WHERE ";
/*      */ 
/*  898 */       if (a_bCaseInsensitive)
/*      */       {
/*  900 */         sSql = sSql + sqlGenerator.getUpperCaseFunction(a_sFieldName) + "=? ";
/*      */       }
/*      */       else
/*      */       {
/*  904 */         sSql = sSql + a_sFieldName + "=? ";
/*      */       }
/*      */ 
/*  908 */       if (!a_bIncludeDeleted)
/*      */       {
/*  910 */         sSql = sSql + " AND IsDeleted=0";
/*      */       }
/*      */ 
/*  913 */       if (!a_bIncludeHidden)
/*      */       {
/*  915 */         sSql = sSql + " AND Hidden=0";
/*      */       }
/*      */ 
/*  918 */       String sValue = a_sValue;
/*  919 */       if ((a_bCaseInsensitive) && (a_sValue != null))
/*      */       {
/*  921 */         sValue = a_sValue.toUpperCase();
/*      */       }
/*      */ 
/*  924 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  925 */       psql.setString(1, sValue);
/*      */ 
/*  927 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  930 */       while (rs.next())
/*      */       {
/*  932 */         vecIds.add(new Long(rs.getLong("Id")));
/*      */       }
/*      */ 
/*  935 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  939 */       if (a_dbTransaction == null)
/*      */       {
/*      */         try
/*      */         {
/*  943 */           con.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  951 */       this.m_logger.error("UserManager.getUserIdsForFieldValue" + e.getMessage());
/*  952 */       throw new Bn2Exception("UserManager.getUserIdsForFieldValue" + e.getMessage(), e);
/*      */     }
/*      */     finally
/*      */     {
/*  957 */       if ((a_dbTransaction == null) && (con != null))
/*      */       {
/*      */         try
/*      */         {
/*  961 */           con.commit();
/*  962 */           con.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  966 */           this.m_logger.error("Sorry, SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  971 */     if (vecIds.size() > 0)
/*      */     {
/*  973 */       laUserId = new long[vecIds.size()];
/*      */ 
/*  975 */       for (int i = 0; i < vecIds.size(); i++)
/*      */       {
/*  977 */         laUserId[i] = ((Long)vecIds.get(i)).longValue();
/*      */       }
/*      */     }
/*  980 */     return laUserId;
/*      */   }
/*      */ 
/*      */   public void changePassword(DBTransaction a_transaction, long a_lUserId, String a_sNewPassword)
/*      */     throws Bn2Exception
/*      */   {
/*  992 */     changePassword(a_transaction, a_lUserId, null, a_sNewPassword, false);
/*      */   }
/*      */ 
/*      */   public boolean changePassword(DBTransaction a_transaction, long a_lUserId, String a_sOldPassword, String a_sNewPassword, boolean a_bCheckSame)
/*      */     throws Bn2Exception
/*      */   {
/* 1017 */     boolean bSuccess = false;
/* 1018 */     Connection cCon = null;
/*      */ 
/* 1020 */     if (a_lUserId <= 0L)
/*      */     {
/* 1022 */       throw new Bn2Exception("User id is <=0 in UserManager.changePassword");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1027 */       cCon = a_transaction.getConnection();
/* 1028 */       String sOldPasswordInDB = null;
/* 1029 */       String sSql = null;
/* 1030 */       PreparedStatement psql = null;
/*      */ 
/* 1032 */       if (a_bCheckSame)
/*      */       {
/* 1035 */         sSql = "SELECT Password FROM AssetBankUser WHERE Id=?";
/* 1036 */         psql = cCon.prepareStatement(sSql);
/*      */ 
/* 1038 */         psql.setLong(1, a_lUserId);
/*      */ 
/* 1040 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 1043 */         if (rs.next())
/*      */         {
/* 1045 */           sOldPasswordInDB = rs.getString("Password");
/*      */         }
/*      */ 
/* 1048 */         psql.close();
/*      */       }
/*      */ 
/* 1052 */       if ((!a_bCheckSame) || (checkPassword(sOldPasswordInDB, a_sOldPassword)))
/*      */       {
/* 1055 */         sSql = "UPDATE AssetBankUser SET Password = ?, DateChangedPassword = ? WHERE Id =?";
/*      */ 
/* 1057 */         psql = cCon.prepareStatement(sSql);
/* 1058 */         psql.setString(1, hashIfNecessary(a_sNewPassword));
/* 1059 */         DBUtil.setFieldDateOrNull(psql, 2, new Date());
/* 1060 */         psql.setLong(3, a_lUserId);
/*      */ 
/* 1062 */         psql.executeUpdate();
/*      */ 
/* 1064 */         bSuccess = true;
/* 1065 */         psql.close();
/*      */       }
/*      */       else
/*      */       {
/* 1069 */         bSuccess = false;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1075 */       throw new Bn2Exception("Exception occurred in UserManager.changePassword", e);
/*      */     }
/*      */ 
/* 1078 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public boolean setUserPassword(long a_lUserId, String a_sNewPassword)
/*      */     throws Bn2Exception
/*      */   {
/* 1098 */     boolean bSuccess = false;
/* 1099 */     Connection cCon = null;
/*      */ 
/* 1101 */     if (a_lUserId <= 0L)
/*      */     {
/* 1103 */       throw new Bn2Exception("User id is <=0 in UserManager.setUserPassword");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1109 */       cCon = this.m_dataSource.getConnection();
/*      */ 
/* 1111 */       String sSql = "UPDATE AssetBankUser SET Password = ?, DateChangedPassword = ? WHERE Id =?";
/*      */ 
/* 1113 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/* 1114 */       psql.setString(1, hashIfNecessary(a_sNewPassword));
/* 1115 */       DBUtil.setFieldDateOrNull(psql, 2, new Date());
/* 1116 */       psql.setLong(3, a_lUserId);
/*      */ 
/* 1118 */       psql.executeUpdate();
/*      */ 
/* 1120 */       psql.close();
/* 1121 */       bSuccess = true;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 1127 */         if (cCon != null)
/*      */         {
/* 1129 */           cCon.rollback();
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */ 
/* 1137 */       throw new Bn2Exception("Exception occurred in UserManager.setUserPassword", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1142 */       if (cCon != null)
/*      */       {
/*      */         try
/*      */         {
/* 1146 */           cCon.commit();
/* 1147 */           cCon.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1152 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1157 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public Vector<User> getAllUsers(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1172 */     Connection cCon = null;
/* 1173 */     Vector vecAllUsers = null;
/*      */     try
/*      */     {
/* 1177 */       if (a_dbTransaction == null)
/*      */       {
/* 1179 */         cCon = getDataSourceComponent().getConnection();
/*      */       }
/*      */       else
/*      */       {
/* 1183 */         cCon = a_dbTransaction.getConnection();
/*      */       }
/*      */ 
/* 1186 */       String sSql = "SELECT u.Id, u.Username, u.Password, u.IsSuspended FROM AssetBankUser u";
/*      */ 
/* 1189 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/*      */ 
/* 1191 */       ResultSet rs = psql.executeQuery();
/* 1192 */       User user = null;
/*      */ 
/* 1194 */       vecAllUsers = new Vector();
/*      */ 
/* 1196 */       while (rs.next())
/*      */       {
/* 1198 */         user = new User();
/*      */ 
/* 1200 */         user.setId(rs.getLong("Id"));
/* 1201 */         user.setUsername(rs.getString("Username"));
/* 1202 */         user.setPassword(rs.getString("Password"));
/* 1203 */         user.setIsSuspended(rs.getBoolean("IsSuspended"));
/*      */ 
/* 1205 */         vecAllUsers.add(user);
/*      */       }
/*      */ 
/* 1208 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1212 */       if (a_dbTransaction == null)
/*      */       {
/*      */         try
/*      */         {
/* 1216 */           cCon.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1224 */       this.m_logger.error("SQL Exception in UserManager:getAllUsers: " + e.getMessage());
/* 1225 */       throw new Bn2Exception("SQL Exception in UserManager:getAllUsers: " + e.getMessage(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 1230 */       if ((a_dbTransaction == null) && (cCon != null))
/*      */       {
/*      */         try
/*      */         {
/* 1234 */           cCon.commit();
/* 1235 */           cCon.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1239 */           this.m_logger.error("Sorry, SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1244 */     return vecAllUsers;
/*      */   }
/*      */ 
/*      */   public void sendPasswordReminder(DBTransaction a_transaction, String a_sUsername)
/*      */     throws Bn2Exception
/*      */   {
/* 1258 */     String ksMethodName = "sendPasswordReminder";
/*      */ 
/* 1260 */     long[] laIds = null;
/*      */ 
/* 1263 */     long lUserId = getUserIdForLocalUsername(a_transaction, a_sUsername);
/*      */ 
/* 1265 */     if (lUserId > 0L)
/*      */     {
/* 1267 */       laIds = new long[1];
/* 1268 */       laIds[0] = lUserId;
/*      */     }
/*      */     else
/*      */     {
/* 1273 */       laIds = getUserIdsForEmailAddress(a_transaction, a_sUsername);
/*      */ 
/* 1276 */       if (laIds == null)
/*      */       {
/* 1278 */         this.m_logger.debug("sendPasswordReminder: No user found for user or email address: " + a_sUsername);
/* 1279 */         throw new PasswordReminderException("No user found for user or email address: " + a_sUsername);
/*      */       }
/*      */     }
/*      */ 
/* 1283 */     boolean bAtLeastOneSent = false;
/*      */ 
/* 1286 */     for (int i = 0; i < laIds.length; i++)
/*      */     {
/* 1289 */       long lUserIdToEmail = laIds[i];
/* 1290 */       ABUser user = (ABUser)getUser(a_transaction, lUserIdToEmail);
/*      */ 
/* 1292 */       if (!StringUtil.stringIsPopulated(user.getEmailAddress()))
/*      */       {
/* 1294 */         this.m_logger.warn("UserManager.sendPasswordReminder: No email address for user: " + a_sUsername);
/*      */       }
/* 1296 */       else if (user.isRemoteUser())
/*      */       {
/* 1298 */         this.m_logger.warn("UserManager.sendPasswordReminder: User is a remote user so cannot reset password: " + a_sUsername);
/*      */       }
/*      */       else
/*      */       {
/* 1303 */         String sNewPassword = PasswordUtil.generateRandomPassword();
/* 1304 */         changePassword(a_transaction, lUserIdToEmail, sNewPassword);
/*      */ 
/* 1307 */         String sName = user.getFullName();
/* 1308 */         if (!StringUtil.stringIsPopulated(sName))
/*      */         {
/* 1310 */           sName = user.getUsername();
/*      */         }
/*      */ 
/* 1313 */         HashMap params = new HashMap();
/* 1314 */         params.put("template", "password_reminder");
/* 1315 */         params.put("name", sName);
/* 1316 */         params.put("username", user.getUsername());
/* 1317 */         params.put("email", user.getEmailAddress());
/* 1318 */         params.put("password", sNewPassword);
/*      */         try
/*      */         {
/* 1322 */           this.m_emailManager.sendTemplatedEmail(params, user.getLanguage());
/* 1323 */           bAtLeastOneSent = true;
/*      */         }
/*      */         catch (Bn2Exception e)
/*      */         {
/* 1328 */           this.m_logger.warn("UserManager.sendPasswordReminder: The reminder was not successfully sent to the user: " + user.getEmailAddress() + ": " + e.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1334 */     if (!bAtLeastOneSent)
/*      */     {
/* 1336 */       throw new PasswordReminderException("UserManager.sendPasswordReminder user was found but email could not be sent for user/email address " + a_sUsername);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getUserIdsOfRemoteUsers(DBTransaction a_dbTransaction, int a_iServerIndex, String a_sUsername)
/*      */     throws Bn2Exception
/*      */   {
/* 1348 */     Connection cCon = null;
/* 1349 */     Vector vecIdList = null;
/*      */ 
/* 1353 */     a_iServerIndex = Math.max(a_iServerIndex, 0);
/*      */     try
/*      */     {
/* 1357 */       if (a_dbTransaction == null)
/*      */       {
/* 1359 */         cCon = getDataSourceComponent().getConnection();
/*      */       }
/*      */       else
/*      */       {
/* 1363 */         cCon = a_dbTransaction.getConnection();
/*      */       }
/*      */ 
/* 1366 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 1368 */       String sSql = "SELECT Id FROM AssetBankUser WHERE NotActiveDirectory = 0 AND LDAPServerIndex=? ";
/*      */ 
/* 1371 */       if (StringUtil.stringIsPopulated(a_sUsername))
/*      */       {
/* 1373 */         sSql = sSql + "AND " + sqlGenerator.getUpperCaseFunction("Username") + "=? ";
/*      */       }
/*      */ 
/* 1376 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/* 1377 */       psql.setInt(1, a_iServerIndex);
/* 1378 */       if (StringUtil.stringIsPopulated(a_sUsername))
/*      */       {
/* 1380 */         psql.setString(2, a_sUsername.toUpperCase());
/*      */       }
/*      */ 
/* 1384 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1386 */       vecIdList = new Vector();
/*      */ 
/* 1388 */       while (rs.next())
/*      */       {
/* 1390 */         Long userId = new Long(rs.getLong("Id"));
/* 1391 */         vecIdList.add(userId);
/*      */       }
/*      */ 
/* 1394 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1398 */       if (a_dbTransaction == null)
/*      */       {
/*      */         try
/*      */         {
/* 1402 */           cCon.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1410 */       this.m_logger.error("SQL Exception in UserManager:getActiveDirectoryUserIdList: " + e.getMessage());
/* 1411 */       throw new Bn2Exception("SQL Exception in UserManager:getActiveDirectoryUserIdList: " + e.getMessage(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 1416 */       if ((a_dbTransaction == null) && (cCon != null))
/*      */       {
/*      */         try
/*      */         {
/* 1420 */           cCon.commit();
/* 1421 */           cCon.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1425 */           this.m_logger.error("Sorry, SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1430 */     return vecIdList;
/*      */   }
/*      */ 
/*      */   public final Vector getMappedGroups()
/*      */     throws Bn2Exception
/*      */   {
/* 1448 */     Vector vecGroups = getAllGroups();
/* 1449 */     RemoteUserGroup group = null;
/*      */ 
/* 1452 */     for (int i = 0; i < vecGroups.size(); i++)
/*      */     {
/* 1454 */       group = (RemoteUserGroup)vecGroups.get(i);
/*      */ 
/* 1456 */       if ((group.getMapping() != null) && (group.getMapping().length() != 0)) {
/*      */         continue;
/*      */       }
/* 1459 */       vecGroups.remove(i);
/* 1460 */       i--;
/*      */     }
/*      */ 
/* 1464 */     return vecGroups;
/*      */   }
/*      */ 
/*      */   public void synchroniseRemoteUsers()
/*      */     throws Bn2Exception
/*      */   {
/* 1473 */     if (this.m_remoteUserManager != null)
/*      */     {
/* 1475 */       this.m_remoteUserManager.synchronise(this, this.m_transactionManager);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void synchroniseRemoteUser(String a_sUsername, DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1484 */     if (this.m_remoteUserManager != null)
/*      */     {
/* 1486 */       this.m_remoteUserManager.synchroniseUser(this, a_dbTransaction, a_sUsername);
/*      */     }
/*      */   }
/*      */ 
/*      */   public DBTransactionManager getDBTransactionManager()
/*      */   {
/* 1496 */     return this.m_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setDBTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 1502 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setDataSourceComponent(DataSourceComponent a_datasource)
/*      */   {
/* 1507 */     this.m_dataSource = a_datasource;
/*      */   }
/*      */ 
/*      */   protected DataSourceComponent getDataSourceComponent()
/*      */   {
/* 1512 */     return this.m_dataSource;
/*      */   }
/*      */ 
/*      */   public void setEmailManager(EmailManager a_emailManager)
/*      */   {
/* 1517 */     this.m_emailManager = a_emailManager;
/*      */   }
/*      */ 
/*      */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*      */   {
/* 1522 */     this.m_assetLogManager = a_assetLogManager;
/*      */   }
/*      */ 
/*      */   public void setRemoteUserManager(RemoteUserManager a_userManager)
/*      */   {
/* 1527 */     this.m_remoteUserManager = a_userManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager manager)
/*      */   {
/* 1532 */     this.m_scheduleManager = manager;
/*      */   }
/*      */ 
/*      */   public void setRefDataManager(RefDataManager manager)
/*      */   {
/* 1537 */     this.m_refDataManager = manager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.service.UserManager
 * JD-Core Version:    0.6.0
 */