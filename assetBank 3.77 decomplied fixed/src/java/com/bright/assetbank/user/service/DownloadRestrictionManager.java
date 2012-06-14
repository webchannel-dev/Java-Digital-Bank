/*     */ package com.bright.assetbank.user.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class DownloadRestrictionManager extends Bn2Manager
/*     */   implements UserConstants, AssetBankConstants
/*     */ {
/*  55 */   protected UsageManager m_usageManager = null;
/*     */ 
/*  68 */   protected IAssetManager m_assetManager = null;
/*     */ 
/*  81 */   protected SubscriptionManager m_subscriptionManager = null;
/*     */ 
/*  93 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/*  64 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/*  77 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager)
/*     */   {
/*  90 */     this.m_subscriptionManager = a_subscriptionManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  96 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void validateDownload(DBTransaction a_dbTransaction, DownloadForm a_form, long a_lUserId, ABUserProfile a_userProfile, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 120 */     if (a_userProfile.getIsLoggedIn())
/*     */     {
/* 122 */       if (isUniqueDownload(a_userProfile, a_asset, a_dbTransaction, a_lUserId))
/*     */       {
/* 127 */         if ((AssetBankSettings.getSubscription()) && (a_lUserId > 0L))
/*     */         {
/* 131 */           long lDownloads = this.m_subscriptionManager.getRemainingDownloadsTodayForUser(a_dbTransaction, a_lUserId);
/*     */ 
/* 135 */           if (lDownloads <= 0L)
/*     */           {
/* 137 */             a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "subNoDownloadsLeft", a_userProfile.getCurrentLanguage()).getBody());
/*     */           }
/*     */ 
/*     */         }
/* 144 */         else if (!isUserAllowedToDownload(a_dbTransaction, a_userProfile))
/*     */         {
/* 146 */           a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "GroupNoDownloadsLeft", a_userProfile.getCurrentLanguage()).getBody());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isUniqueDownload(ABUserProfile a_userProfile, Asset a_asset, DBTransaction a_dbTransaction, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 172 */     boolean bIsUniqueDownload = false;
/*     */ 
/* 174 */     boolean bUpdater = this.m_assetManager.userCanUpdateAsset(a_userProfile, a_asset);
/*     */ 
/* 177 */     if (!bUpdater)
/*     */     {
/* 181 */       boolean bApproved = this.m_assetManager.userApprovedForAsset(a_userProfile, a_asset);
/*     */ 
/* 184 */       if (!bApproved)
/*     */       {
/* 189 */         boolean bUsedToday = getUserHasAlreadyUsedAssetToday(a_dbTransaction, a_lUserId, a_asset.getId());
/*     */ 
/* 191 */         if (!bUsedToday)
/*     */         {
/* 193 */           bIsUniqueDownload = true;
/*     */         }
/*     */       }
/*     */     }
/* 197 */     return bIsUniqueDownload;
/*     */   }
/*     */ 
/*     */   public void validateAssetBoxDownload(DBTransaction a_dbTransaction, DownloadForm a_form, long a_lUserId, Collection a_assetBox, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 219 */     if ((!a_userProfile.getIsAdmin()) && (a_userProfile.getIsLoggedIn()))
/*     */     {
/* 222 */       long lNumNewImages = 0L;
/* 223 */       BrightDate dtToday = BrightDate.today();
/* 224 */       BrightDate dtTomorrow = BrightDate.tomorrow();
/*     */ 
/* 226 */       Iterator it = a_assetBox.iterator();
/* 227 */       while (it.hasNext())
/*     */       {
/* 229 */         Asset asset = ((AssetInList)it.next()).getAsset();
/*     */ 
/* 231 */         if (isUniqueDownload(a_userProfile, asset, a_dbTransaction, a_lUserId))
/*     */         {
/* 234 */           lNumNewImages += 1L;
/*     */         }
/*     */       }
/*     */       long lRemainingDownloads;
/*     */       //long lRemainingDownloads;
/* 239 */       if ((AssetBankSettings.getSubscription()) && (a_lUserId > 0L))
/*     */       {
/* 243 */         lRemainingDownloads = this.m_subscriptionManager.getRemainingDownloadsTodayForUser(a_dbTransaction, a_lUserId);
/*     */       }
/*     */       else
/*     */       {
/* 249 */         lRemainingDownloads = getUserAllowedDownloads(a_dbTransaction, a_userProfile);
/*     */ 
/* 253 */         if (lRemainingDownloads != 0L)
/*     */         {
/* 255 */           lRemainingDownloads -= getUserDownloadsInRange(a_dbTransaction, dtToday.getDate(), dtTomorrow.getDate(), a_lUserId);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 262 */       if ((lRemainingDownloads != 0L) && (lNumNewImages > lRemainingDownloads))
/*     */       {
/* 264 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "AssetBoxNoDownloadsLeft", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getUserHasAlreadyUsedAssetToday(DBTransaction a_dbTransaction, long a_lUserId, long a_lAssetId)
/*     */     throws Bn2Exception
/*     */   {
/* 289 */     BrightDate dtToday = BrightDate.today();
/* 290 */     BrightDate dtTomorrow = BrightDate.tomorrow();
/* 291 */     long lUsedDownloads = getUserDownloadsOfAssetInRange(a_dbTransaction, dtToday.getDate(), dtTomorrow.getDate(), a_lUserId, a_lAssetId);
/*     */ 
/* 293 */     boolean bUsedToday = lUsedDownloads > 0L;
/* 294 */     return bUsedToday;
/*     */   }
/*     */ 
/*     */   public boolean isUserAllowedToDownload(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 310 */     boolean bCanDownload = true;
/* 311 */     long lTodaysDownloads = 0L;
/*     */ 
/* 314 */     long lMaxDownloads = getUserAllowedDownloads(a_dbTransaction, a_userProfile);
/*     */ 
/* 317 */     if (lMaxDownloads == 0L)
/*     */     {
/* 319 */       bCanDownload = true;
/*     */     }
/*     */     else
/*     */     {
/* 324 */       BrightDate dtToday = BrightDate.today();
/* 325 */       BrightDate dtTomorrow = BrightDate.tomorrow();
/* 326 */       lTodaysDownloads = getUserDownloadsInRange(a_dbTransaction, dtToday.getDate(), dtTomorrow.getDate(), a_userProfile.getUser().getId());
/*     */ 
/* 329 */       if (lMaxDownloads - lTodaysDownloads <= 0L)
/*     */       {
/* 331 */         bCanDownload = false;
/*     */       }
/*     */     }
/*     */ 
/* 335 */     return bCanDownload;
/*     */   }
/*     */ 
/*     */   public long getUserAllowedDownloads(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 356 */     long lDownloads = 0L;
/* 357 */     long lDownloadsAllowed = 0L;
/* 358 */     Vector vMaxGroupDownloads = null;
/*     */ 
/* 360 */     vMaxGroupDownloads = getGroupAllowedDownloads(a_userProfile.getGroupIds(), a_dbTransaction);
/*     */ 
/* 362 */     for (int i = 0; i < vMaxGroupDownloads.size(); i++)
/*     */     {
/* 364 */       lDownloadsAllowed = ((Long)vMaxGroupDownloads.elementAt(i)).longValue();
/*     */ 
/* 366 */       if (lDownloadsAllowed == 0L)
/*     */       {
/* 368 */         lDownloads = 0L;
/* 369 */         break;
/*     */       }
/* 371 */       if (lDownloadsAllowed <= lDownloads)
/*     */         continue;
/* 373 */       lDownloads = lDownloadsAllowed;
/*     */     }
/*     */ 
/* 377 */     return lDownloads;
/*     */   }
/*     */ 
/*     */   public Vector getGroupAllowedDownloads(Vector a_vecGroupIds, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 398 */     DBTransaction transaction = null;
/*     */ 
/* 400 */     Vector vecResults = new Vector();
/*     */     try
/*     */     {
/* 404 */       Connection con = null;
/* 405 */       String sSQL = null;
/* 406 */       transaction = a_dbTransaction;
/*     */ 
/* 408 */       con = transaction.getConnection();
/*     */ 
/* 410 */       sSQL = "SELECT DailyDownloadLimit FROM UserGroup WHERE Id=?";
/*     */ 
/* 414 */       for (int i = 1; i < a_vecGroupIds.size(); i++)
/*     */       {
/* 416 */         sSQL = sSQL + " OR Id=?";
/*     */       }
/*     */ 
/* 419 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*     */ 
/* 421 */       for (int i = 0; i < a_vecGroupIds.size(); i++)
/*     */       {
/* 423 */         psql.setLong(i + 1, ((Long)a_vecGroupIds.elementAt(i)).longValue());
/*     */       }
/*     */ 
/* 426 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 428 */       while (rs.next())
/*     */       {
/* 430 */         vecResults.add(new Long(rs.getLong("DailyDownloadLimit")));
/*     */       }
/*     */ 
/* 433 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 438 */       this.m_logger.error("SQL Exception whilst getting max downloads for group : " + e);
/*     */ 
/* 441 */       throw new Bn2Exception("SQL Exception whilst getting max downloads for group : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 448 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 452 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 456 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 463 */     return vecResults;
/*     */   }
/*     */ 
/*     */   public long getUserDownloadsOfAssetInRange(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, long a_lUserId, long a_lAssetId)
/*     */     throws Bn2Exception
/*     */   {
/* 488 */     Connection con = null;
/* 489 */     PreparedStatement psql = null;
/* 490 */     String sSQL = null;
/* 491 */     ResultSet rs = null;
/* 492 */     long lNumDownloads = 0L;
/*     */     try
/*     */     {
/* 496 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 498 */       sSQL = "SELECT COUNT(au.AssetId) countAss FROM AssetUse au INNER JOIN AssetBankUser u ON u.Id = au.UserId WHERE (au.TimeOfDownload >= ? AND au.TimeOfDownload <= ?) AND au.UserId = ? AND au.AssetId = ? ";
/*     */ 
/* 505 */       psql = con.prepareStatement(sSQL);
/* 506 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/* 507 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/* 508 */       psql.setLong(3, a_lUserId);
/* 509 */       psql.setLong(4, a_lAssetId);
/* 510 */       rs = psql.executeQuery();
/*     */ 
/* 512 */       while (rs.next())
/*     */       {
/* 514 */         lNumDownloads = rs.getInt("countAss");
/*     */       }
/*     */ 
/* 517 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 522 */       this.m_logger.error("SQL Exception whilst getting asset view report : " + e);
/*     */ 
/* 524 */       throw new Bn2Exception("SQL Exception whilst getting asset view report : " + e, e);
/*     */     }
/*     */ 
/* 527 */     return lNumDownloads;
/*     */   }
/*     */ 
/*     */   public long getUserDownloadsInRange(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 554 */     Connection con = null;
/* 555 */     PreparedStatement psql = null;
/* 556 */     String sSQL = null;
/* 557 */     ResultSet rs = null;
/* 558 */     long lNumDownloads = 0L;
/*     */     try
/*     */     {
/* 562 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 564 */       sSQL = "SELECT COUNT(DISTINCT au.AssetId) countAss FROM AssetUse au INNER JOIN AssetBankUser u ON u.Id = au.UserId WHERE (au.TimeOfDownload >= ? AND au.TimeOfDownload <= ?) AND au.UserId = ? AND NOT EXISTS (SELECT 1 FROM AssetApproval aa \tWHERE aa.AssetId=au.AssetId \tAND aa.UserId=? \tAND ApprovalStatusId=?)";
/*     */ 
/* 574 */       psql = con.prepareStatement(sSQL);
/* 575 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/* 576 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/* 577 */       psql.setLong(3, a_lUserId);
/* 578 */       psql.setLong(4, a_lUserId);
/* 579 */       psql.setLong(5, 2L);
/* 580 */       rs = psql.executeQuery();
/*     */ 
/* 582 */       while (rs.next())
/*     */       {
/* 584 */         lNumDownloads = rs.getInt("countAss");
/*     */       }
/*     */ 
/* 587 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 592 */       this.m_logger.error("SQL Exception whilst getting asset view report : " + e);
/*     */ 
/* 594 */       throw new Bn2Exception("SQL Exception whilst getting asset view report : " + e, e);
/*     */     }
/*     */ 
/* 597 */     return lNumDownloads;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.service.DownloadRestrictionManager
 * JD-Core Version:    0.6.0
 */