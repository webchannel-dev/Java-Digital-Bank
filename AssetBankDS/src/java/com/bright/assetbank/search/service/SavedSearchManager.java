/*     */ package com.bright.assetbank.search.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.bean.SavedSearch;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderClause;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderQuery;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.exception.MaxSearchesReachedException;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.service.RefDataManager;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DateFormat;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class SavedSearchManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "AssetApprovalManager";
/*  77 */   private Object m_objPromotedSearchCacheLock = new Object();
/*  78 */   private HashMap m_hmPromotedSearchCache = new HashMap();
/*  79 */   private Object m_objGlobalPromotedSearchCacheLock = new Object();
/*  80 */   private Vector m_vecGlobalPromotedSearchCache = null;
/*  81 */   private ScheduleManager m_scheduleManager = null;
/*     */ 
/*  87 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/*  93 */   private RefDataManager m_refDataManager = null;
/*     */ 
/*  98 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/* 104 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/* 110 */   private EmailManager m_emailManager = null;
/*     */ 
/* 116 */   private ABUserManager m_userManager = null;
/*     */ 
/* 122 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*     */   {
/*  84 */     this.m_scheduleManager = a_sScheduleManager;
/*     */   }
/*     */ 
/*     */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/*  90 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setRefDataManager(RefDataManager a_refDataManager)
/*     */   {
/*  96 */     this.m_refDataManager = a_refDataManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 101 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 107 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/* 113 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 119 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 125 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/* 131 */     super.startup();
/*     */ 
/* 134 */     if (AssetBankSettings.getSavedSearchAlertPeriod() > 0)
/*     */     {
/* 136 */       TimerTask task = new TimerTask()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           try
/*     */           {
/* 142 */             SavedSearchManager.this.processSavedSearchAlerts(null);
/*     */           }
/*     */           catch (Bn2Exception e)
/*     */           {
/* 146 */             SavedSearchManager.this.m_logger.error("Could not process saved search alerts in scheduled task due to exception: " + e.getLocalizedMessage(), e);
/*     */           }
/*     */         }
/*     */       };
/* 151 */       this.m_scheduleManager.schedule(task, 60000L, AssetBankSettings.getSavedSearchAlertPeriod() * 60L * 60L * 1000L, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processSavedSearchAlerts(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 159 */     String ksMethodName = "processSavedSearchAlerts";
/* 160 */     Connection con = null;
/* 161 */     String sSql = null;
/* 162 */     PreparedStatement psql = null;
/* 163 */     ResultSet rs = null;
/* 164 */     DBTransaction transaction = a_transaction;
/*     */     try
/*     */     {
/* 168 */       if (transaction == null)
/*     */       {
/* 170 */         transaction = this.m_transactionManager.getNewTransaction();
/*     */       }
/* 172 */       con = transaction.getConnection();
/*     */ 
/* 175 */       long lTime = this.m_refDataManager.getSystemSettingAsLong("LastSavedSearchAlert");
/*     */ 
/* 177 */       if (lTime > 0L)
/*     */       {
/* 179 */         GregorianCalendar lastRun = new GregorianCalendar();
/* 180 */         lastRun.setTimeInMillis(lTime);
/* 181 */         boolean bRunAlerts = false;
/*     */ 
/* 184 */         sSql = "SELECT Id FROM Asset WHERE DateAdded>?";
/* 185 */         psql = con.prepareStatement(sSql);
/* 186 */         psql.setDate(1, new Date(lastRun.getTimeInMillis()));
/* 187 */         rs = psql.executeQuery();
/* 188 */         if (rs.next())
/*     */         {
/* 190 */           bRunAlerts = true;
/*     */         }
/* 192 */         psql.close();
/*     */ 
/* 194 */         if (bRunAlerts)
/*     */         {
/* 196 */           sSql = "SELECT ss.Name, ss.SearchCriteriaFile, u.EmailAddress, u.Id, u.IsAdmin FROM SavedSearch ss, AssetBankUser u WHERE ss.UserId=u.Id AND ss.NewAssetAlert=1 ORDER BY ss.UserId";
/*     */ 
/* 198 */           psql = con.prepareStatement(sSql);
/* 199 */           rs = psql.executeQuery();
/* 200 */           HashMap hmEmails = new HashMap();
/*     */ 
/* 202 */           while (rs.next())
/*     */           {
/* 207 */             String sName = rs.getString("Name");
/* 208 */             String sFile = rs.getString("SearchCriteriaFile");
/* 209 */             String sEmail = rs.getString("EmailAddress");
/* 210 */             long lUserId = rs.getLong("Id");
/* 211 */             boolean bAdmin = rs.getBoolean("IsAdmin");
/*     */ 
/* 213 */             if (StringUtil.stringIsPopulated(sFile))
/*     */             {
/*     */               try
/*     */               {
/* 218 */                 BaseSearchQuery criteria = getCriteria(sFile);
/*     */ 
/* 221 */                 ABUserProfile userProfile = new ABUserProfile();
/* 222 */                 ABUser user = new ABUser();
/* 223 */                 Vector vecAtributeExclusions = this.m_userManager.getAttributeExclusionsForUser(transaction, lUserId);
/* 224 */                 user.setId(lUserId);
/* 225 */                 user.setIsAdmin(bAdmin);
/* 226 */                 userProfile.setUser(user);
/* 227 */                 userProfile.setAttributeExclusions(vecAtributeExclusions);
/* 228 */                 this.m_userManager.setCategoryPermissions(con, userProfile, lUserId);
/* 229 */                 criteria.setupPermissions(userProfile);
/*     */ 
/* 232 */                 if ((criteria instanceof SearchCriteria))
/*     */                 {
/* 234 */                   ((SearchCriteria)criteria).setDateImageAddedLower(lastRun.getTime());
/*     */                 }
/* 236 */                 else if ((criteria instanceof SearchBuilderQuery))
/*     */                 {
/* 239 */                   SearchBuilderClause clause = new SearchBuilderClause();
/* 240 */                   Attribute attribute = this.m_attributeManager.getAttribute(transaction, "dateAdded");
/* 241 */                   clause.setAttributeId(attribute.getId());
/* 242 */                   clause.setOperatorId(6L);
/* 243 */                   clause.setDate(true);
/* 244 */                   DateFormat format = AssetBankSettings.getStandardDateFormat();
/* 245 */                   String sDate = format.format(lastRun.getTime());
/* 246 */                   clause.setValue(sDate);
/* 247 */                   ((SearchBuilderQuery)criteria).getClauses().add(clause);
/*     */                 }
/*     */ 
/* 250 */                 int iHitCount = this.m_searchManager.getHitCount(criteria);
/*     */ 
/* 252 */                 if (iHitCount > 0)
/*     */                 {
/* 255 */                   if (!hmEmails.containsKey(sEmail))
/*     */                   {
/* 257 */                     hmEmails.put(sEmail, new Vector());
/*     */                   }
/* 259 */                   Vector vecSearches = (Vector)hmEmails.get(sEmail);
/* 260 */                   vecSearches.add(sName);
/* 261 */                   hmEmails.put(sEmail, vecSearches);
/*     */                 }
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 266 */                 this.m_logger.error("SavedSearchManager.processSavedSearchAlerts: Unable to check search alert for : " + sName + " : " + e.getMessage());
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 272 */           Set keys = hmEmails.keySet();
/* 273 */           Iterator it = keys.iterator();
/*     */ 
/* 275 */           while (it.hasNext())
/*     */           {
/* 277 */             String sEmail = (String)it.next();
/* 278 */             Map hmParams = new HashMap();
/* 279 */             hmParams.put("template", "saved_search_alert_email");
/*     */ 
/* 281 */             Vector vecSearches = (Vector)hmEmails.get(sEmail);
/* 282 */             if (vecSearches != null)
/*     */             {
/* 284 */               String sSearches = "";
/* 285 */               for (int i = 0; i < vecSearches.size(); i++)
/*     */               {
/* 287 */                 sSearches = sSearches + (String)vecSearches.elementAt(i) + "\n";
/*     */               }
/*     */ 
/* 290 */               hmParams.put("searches", sSearches);
/* 291 */               hmParams.put("recipients", sEmail);
/*     */               try
/*     */               {
/* 296 */                 this.m_emailManager.sendTemplatedEmail(hmParams, null, false, LanguageConstants.k_defaultLanguage);
/*     */               }
/*     */               catch (Exception be)
/*     */               {
/* 300 */                 this.m_logger.error("SavedSearchManager.processSavedSearchAlerts: Error sending email: " + be.getMessage());
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 306 */           GregorianCalendar now = new GregorianCalendar();
/* 307 */           this.m_refDataManager.updateSystemSettingAsLong(transaction, "LastSavedSearchAlert", now.getTimeInMillis());
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 313 */         GregorianCalendar now = new GregorianCalendar();
/* 314 */         this.m_refDataManager.updateSystemSettingAsLong(transaction, "LastSavedSearchAlert", now.getTimeInMillis());
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 321 */       if ((transaction != null) && (a_transaction == null))
/*     */       {
/*     */         try
/*     */         {
/* 325 */           transaction.rollback(); } catch (Exception se) {
/*     */         }
/*     */       }
/* 328 */       this.m_logger.error("AssetApprovalManager.processSavedSearchAlerts : SQL Exception : " + e);
/* 329 */       throw new Bn2Exception("AssetApprovalManager.processSavedSearchAlerts : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 333 */       if ((transaction != null) && (a_transaction == null))
/*     */       {
/*     */         try
/*     */         {
/* 337 */           transaction.commit();
/*     */         }
/*     */         catch (Exception se)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void saveSearch(DBTransaction a_transaction, SavedSearch a_search, UserProfile a_userProfile, String a_sOldName, boolean a_bForceRegen)
/*     */     throws Bn2Exception, MaxSearchesReachedException, IOException
/*     */   {
/* 351 */     String ksMethodName = "saveSearch";
/* 352 */     Connection con = null;
/* 353 */     String sSql = null;
/* 354 */     PreparedStatement psql = null;
/* 355 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 359 */       con = a_transaction.getConnection();
/*     */ 
/* 362 */       if (StringUtil.stringIsPopulated(a_sOldName))
/*     */       {
/* 364 */         deleteSavedSearch(a_transaction, a_sOldName, a_userProfile, false, false);
/*     */       }
/*     */ 
/* 368 */       sSql = "SELECT * FROM SavedSearch WHERE Name=? AND UserId=?";
/* 369 */       psql = con.prepareStatement(sSql);
/* 370 */       psql.setString(1, a_search.getName());
/* 371 */       psql.setLong(2, a_userProfile.getUser().getId());
/* 372 */       rs = psql.executeQuery();
/* 373 */       if (rs.next())
/*     */       {
/* 375 */         deleteSavedSearch(a_transaction, a_search.getName(), a_userProfile, true, true);
/*     */       }
/* 377 */       psql.close();
/*     */ 
/* 380 */       if ((!StringUtil.stringIsPopulated(a_sOldName)) || (a_bForceRegen))
/*     */       {
/* 382 */         a_search.setCriteriaFile(SearchUtil.serializeSearch(this.m_fileStoreManager, a_search.getCriteria()));
/*     */       }
/*     */ 
/* 386 */       sSql = "SELECT COUNT(*) numSearches FROM SavedSearch WHERE UserId=?";
/* 387 */       psql = con.prepareStatement(sSql);
/* 388 */       psql.setLong(1, a_userProfile.getUser().getId());
/*     */ 
/* 390 */       rs = psql.executeQuery();
/*     */ 
/* 392 */       if ((rs.next()) && (rs.getInt("numSearches") >= AssetBankSettings.getMaxSavedSearches()))
/*     */       {
/* 394 */         throw new MaxSearchesReachedException(AssetBankSettings.getMaxSavedSearches());
/*     */       }
/*     */ 
/* 397 */       psql.close();
/*     */ 
/* 399 */       sSql = "INSERT INTO SavedSearch (Keywords,Name,UserId,IsRssFeed,IsBuilderSearch,SortAttributeId,IsDescending,Promoted,SearchImage,AvailableToAll,NewAssetAlert,SearchCriteriaFile) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/* 401 */       psql = con.prepareStatement(sSql);
/*     */ 
/* 403 */       int iCol = 1;
/* 404 */       psql.setString(iCol++, a_search.getKeywords());
/* 405 */       psql.setString(iCol++, a_search.getName());
/* 406 */       psql.setLong(iCol++, a_userProfile.getUser().getId());
/* 407 */       psql.setBoolean(iCol++, a_search.isRssFeed());
/* 408 */       psql.setBoolean(iCol++, a_search.isBuilderSearch());
/* 409 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_search.getSortAttributeId());
/* 410 */       psql.setBoolean(iCol++, a_search.isDescending());
/* 411 */       psql.setBoolean(iCol++, a_search.getPromoted());
/* 412 */       psql.setString(iCol++, a_search.getImage());
/* 413 */       psql.setBoolean(iCol++, a_search.getAvailableToAll());
/* 414 */       psql.setBoolean(iCol++, a_search.getAlert());
/* 415 */       psql.setString(iCol++, a_search.getCriteriaFile());
/*     */ 
/* 417 */       psql.executeUpdate();
/* 418 */       psql.close();
/*     */ 
/* 420 */       invalidatePromotedSearchesCache(a_userProfile.getUser().getId());
/*     */ 
/* 423 */       if (a_userProfile.getIsAdmin())
/*     */       {
/* 425 */         invalidateGlobalPromotedSearchesCache();
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 430 */       this.m_logger.error("AssetApprovalManager.saveSearch : SQL Exception : " + e);
/* 431 */       throw new Bn2Exception("AssetApprovalManager.saveSearch : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public SavedSearch getSavedSearch(DBTransaction a_transaction, String a_sName, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 442 */     SavedSearch search = null;
/* 443 */     String ksMethodName = "getSavedSearch";
/* 444 */     Connection con = null;
/* 445 */     String sSql = null;
/* 446 */     PreparedStatement psql = null;
/* 447 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 451 */       con = a_transaction.getConnection();
/*     */ 
/* 453 */       sSql = "SELECT Name, Keywords, IsRssFeed, IsBuilderSearch, SortAttributeId, IsDescending, Promoted, SearchImage, AvailableToAll, NewAssetAlert, SearchCriteriaFile, UserId FROM SavedSearch WHERE Name=? AND UserId=?";
/* 454 */       psql = con.prepareStatement(sSql);
/* 455 */       psql.setString(1, a_sName);
/* 456 */       psql.setLong(2, a_lUserId);
/*     */ 
/* 458 */       rs = psql.executeQuery();
/*     */ 
/* 460 */       while (rs.next())
/*     */       {
/* 462 */         search = buildSavedSearch(rs);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 467 */       this.m_logger.error("AssetApprovalManager.getSavedSearch : SQL Exception : " + e);
/* 468 */       throw new Bn2Exception("AssetApprovalManager.getSavedSearch : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 472 */       if (psql != null)
/*     */       {
/*     */         try
/*     */         {
/* 476 */           psql.close();
/*     */         }
/*     */         catch (SQLException e) {
/*     */         }
/*     */       }
/*     */     }
/* 482 */     return search;
/*     */   }
/*     */ 
/*     */   public Vector getSavedSearches(DBTransaction a_transaction, long a_lUserId, boolean a_bPromoted)
/*     */     throws Bn2Exception
/*     */   {
/* 494 */     if (a_bPromoted)
/*     */     {
/* 497 */       if (this.m_hmPromotedSearchCache.containsKey(new Long(a_lUserId)))
/*     */       {
/* 499 */         return (Vector)this.m_hmPromotedSearchCache.get(new Long(a_lUserId));
/*     */       }
/*     */     }
/*     */ 
/* 503 */     String ksMethodName = "getSavedSearches";
/* 504 */     Vector vSearches = null;
/* 505 */     Connection con = null;
/* 506 */     String sSql = null;
/* 507 */     PreparedStatement psql = null;
/* 508 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 512 */       con = a_transaction.getConnection();
/*     */ 
/* 514 */       sSql = "SELECT Name, Keywords, IsRssFeed, IsBuilderSearch, SortAttributeId, IsDescending, Promoted, SearchImage, AvailableToAll, NewAssetAlert, SearchCriteriaFile, UserId FROM SavedSearch WHERE UserId=?";
/*     */ 
/* 516 */       if (a_bPromoted)
/*     */       {
/* 518 */         sSql = sSql + " AND Promoted=1";
/*     */       }
/*     */ 
/* 521 */       psql = con.prepareStatement(sSql);
/* 522 */       psql.setLong(1, a_lUserId);
/*     */ 
/* 524 */       rs = psql.executeQuery();
/*     */ 
/* 526 */       vSearches = new Vector();
/*     */ 
/* 528 */       while (rs.next())
/*     */       {
/* 530 */         SavedSearch search = buildSavedSearch(rs);
/* 531 */         vSearches.add(search);
/*     */       }
/*     */ 
/* 534 */       if (a_bPromoted)
/*     */       {
/* 537 */         cachePromotedSearches(vSearches, a_lUserId);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 542 */       this.m_logger.error("AssetApprovalManager.getSavedSearches : SQL Exception : " + e);
/* 543 */       throw new Bn2Exception("AssetApprovalManager.getSavedSearches : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 547 */       if (psql != null)
/*     */       {
/*     */         try
/*     */         {
/* 551 */           psql.close();
/*     */         }
/*     */         catch (SQLException e) {
/*     */         }
/*     */       }
/*     */     }
/* 557 */     return vSearches;
/*     */   }
/*     */ 
/*     */   public Vector getGlobalPromotedSavedSearches(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 571 */     if (this.m_vecGlobalPromotedSearchCache != null)
/*     */     {
/* 573 */       return this.m_vecGlobalPromotedSearchCache;
/*     */     }
/*     */ 
/* 576 */     String ksMethodName = "getGlobalPromotedSavedSearches";
/* 577 */     Vector vSearches = null;
/* 578 */     Connection con = null;
/* 579 */     String sSql = null;
/* 580 */     PreparedStatement psql = null;
/* 581 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 585 */       con = a_transaction.getConnection();
/*     */ 
/* 587 */       sSql = "SELECT Name, Keywords, IsRssFeed, IsBuilderSearch, SortAttributeId, IsDescending, Promoted, SearchImage, AvailableToAll, NewAssetAlert, SearchCriteriaFile, UserId FROM SavedSearch WHERE AvailableToAll=1";
/* 588 */       psql = con.prepareStatement(sSql);
/* 589 */       rs = psql.executeQuery();
/*     */ 
/* 591 */       vSearches = new Vector();
/*     */ 
/* 593 */       while (rs.next())
/*     */       {
/* 595 */         SavedSearch search = buildSavedSearch(rs);
/* 596 */         vSearches.add(search);
/*     */       }
/*     */ 
/* 600 */       cacheGlobalPromotedSearches(vSearches);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 604 */       this.m_logger.error("AssetApprovalManager.getGlobalPromotedSavedSearches : SQL Exception : " + e);
/* 605 */       throw new Bn2Exception("AssetApprovalManager.getGlobalPromotedSavedSearches : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 609 */       if (psql != null)
/*     */       {
/*     */         try
/*     */         {
/* 613 */           psql.close();
/*     */         }
/*     */         catch (SQLException e) {
/*     */         }
/*     */       }
/*     */     }
/* 619 */     return vSearches;
/*     */   }
/*     */ 
/*     */   private void cachePromotedSearches(Vector a_vecSearches, long a_lUserId)
/*     */   {
/* 625 */     synchronized (this.m_objPromotedSearchCacheLock)
/*     */     {
/* 627 */       this.m_hmPromotedSearchCache.put(new Long(a_lUserId), a_vecSearches);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void invalidatePromotedSearchesCache(long a_lUserId)
/*     */   {
/* 633 */     synchronized (this.m_objPromotedSearchCacheLock)
/*     */     {
/* 635 */       this.m_hmPromotedSearchCache.remove(new Long(a_lUserId));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void invalidateGlobalPromotedSearchesCache()
/*     */   {
/* 641 */     synchronized (this.m_objGlobalPromotedSearchCacheLock)
/*     */     {
/* 643 */       this.m_vecGlobalPromotedSearchCache = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void cacheGlobalPromotedSearches(Vector a_vecSearches)
/*     */   {
/* 649 */     synchronized (this.m_objGlobalPromotedSearchCacheLock)
/*     */     {
/* 651 */       this.m_vecGlobalPromotedSearchCache = a_vecSearches;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteSavedSearch(DBTransaction a_transaction, String a_sName, UserProfile a_userProfile, boolean a_bDeleteImage, boolean a_bDeleteCriteria)
/*     */     throws Bn2Exception
/*     */   {
/* 664 */     String ksMethodName = "deleteSavedSearch";
/* 665 */     Connection con = null;
/* 666 */     String sSql = null;
/* 667 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 671 */       con = a_transaction.getConnection();
/*     */ 
/* 674 */       SavedSearch search = getSavedSearch(a_transaction, a_sName, a_userProfile.getUser().getId());
/* 675 */       if ((a_bDeleteImage) && (StringUtil.stringIsPopulated(search.getImage())))
/*     */       {
/* 677 */         File fToDelete = new File(AssetBankSettings.getApplicationPath() + "/" + search.getImage());
/* 678 */         fToDelete.delete();
/* 679 */         FileUtil.logFileDeletion(fToDelete);
/*     */       }
/*     */ 
/* 682 */       if ((a_bDeleteCriteria) && (StringUtil.stringIsPopulated(search.getCriteriaFile())))
/*     */       {
/* 684 */         File fToDelete = new File(this.m_fileStoreManager.getAbsolutePath(search.getCriteriaFile()));
/* 685 */         fToDelete.delete();
/* 686 */         FileUtil.logFileDeletion(fToDelete);
/*     */       }
/*     */ 
/* 689 */       sSql = "DELETE FROM SavedSearch WHERE Name=? AND UserId=?";
/* 690 */       psql = con.prepareStatement(sSql);
/* 691 */       psql.setString(1, a_sName);
/* 692 */       psql.setLong(2, a_userProfile.getUser().getId());
/* 693 */       psql.executeUpdate();
/*     */ 
/* 695 */       invalidatePromotedSearchesCache(a_userProfile.getUser().getId());
/*     */ 
/* 698 */       if (a_userProfile.getIsAdmin())
/*     */       {
/* 700 */         invalidateGlobalPromotedSearchesCache();
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 705 */       this.m_logger.error("AssetApprovalManager.deleteSavedSearch : SQL Exception : " + e);
/* 706 */       throw new Bn2Exception("AssetApprovalManager.deleteSavedSearch : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 710 */       if (psql != null)
/*     */       {
/*     */         try
/*     */         {
/* 714 */           psql.close();
/*     */         }
/*     */         catch (SQLException e)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void enableDisableSavedSearchRss(DBTransaction a_transaction, String a_sName, long a_lUserId, boolean a_bEnable)
/*     */     throws Bn2Exception
/*     */   {
/* 732 */     String ksMethodName = "enableDisableSavedSearchRss";
/* 733 */     Connection con = null;
/* 734 */     String sSql = null;
/* 735 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 739 */       con = a_transaction.getConnection();
/*     */ 
/* 741 */       sSql = "UPDATE SavedSearch SET IsRssFeed=? WHERE Name=? AND UserId=?";
/* 742 */       psql = con.prepareStatement(sSql);
/* 743 */       psql.setBoolean(1, a_bEnable);
/* 744 */       psql.setString(2, a_sName);
/* 745 */       psql.setLong(3, a_lUserId);
/* 746 */       psql.executeUpdate();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 750 */       this.m_logger.error("AssetApprovalManager.enableDisableSavedSearchRss : SQL Exception : " + e);
/* 751 */       throw new Bn2Exception("AssetApprovalManager.enableDisableSavedSearchRss : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 755 */       if (psql != null)
/*     */       {
/*     */         try
/*     */         {
/* 759 */           psql.close();
/*     */         }
/*     */         catch (SQLException e) {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private SavedSearch buildSavedSearch(ResultSet a_rs) throws Bn2Exception, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
/* 768 */     SavedSearch search = new SavedSearch();
/* 769 */     search.setName(a_rs.getString("Name"));
/* 770 */     search.setKeywords(a_rs.getString("Keywords"));
/* 771 */     search.setRssFeed(a_rs.getBoolean("IsRssFeed"));
/* 772 */     search.setBuilderSearch(a_rs.getBoolean("IsBuilderSearch"));
/* 773 */     search.setSortAttributeId(a_rs.getLong("SortAttributeId"));
/* 774 */     search.setDescending(a_rs.getBoolean("IsDescending"));
/* 775 */     search.setPromoted(a_rs.getBoolean("Promoted"));
/* 776 */     search.setImage(a_rs.getString("SearchImage"));
/* 777 */     search.setAvailableToAll(a_rs.getBoolean("AvailableToAll"));
/* 778 */     search.setAlert(a_rs.getBoolean("NewAssetAlert"));
/* 779 */     search.setCriteriaFile(a_rs.getString("SearchCriteriaFile"));
/* 780 */     search.setUserId(a_rs.getLong("UserId"));
/*     */ 
/* 782 */     if (StringUtil.stringIsPopulated(search.getCriteriaFile()))
/*     */     {
/* 784 */       search.setCriteria(getCriteria(search.getCriteriaFile()));
/*     */     }
/*     */ 
/* 787 */     return search;
/*     */   }
/*     */ 
/*     */   private BaseSearchQuery getCriteria(String a_sFile) throws Bn2Exception, ClassNotFoundException, SQLException, FileNotFoundException, IOException
/*     */   {
/* 792 */     String sPath = this.m_fileStoreManager.getAbsolutePath(a_sFile);
/* 793 */     return SearchUtil.getCriteria(sPath);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.service.SavedSearchManager
 * JD-Core Version:    0.6.0
 */