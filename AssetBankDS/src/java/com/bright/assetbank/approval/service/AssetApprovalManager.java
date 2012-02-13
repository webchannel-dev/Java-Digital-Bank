/*      */ package com.bright.assetbank.approval.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.service.AssetManagerUtil;
/*      */ import com.bright.assetbank.application.service.IAssetManager;
/*      */ import com.bright.assetbank.approval.bean.AssetApproval;
/*      */ import com.bright.assetbank.approval.bean.AssetApprovalSearchCriteria;
/*      */ import com.bright.assetbank.approval.bean.AssetInList;
/*      */ import com.bright.assetbank.approval.bean.UsersWithApprovalListRecord;
/*      */ import com.bright.assetbank.approval.constant.AssetApprovalConstants;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AssetApprovalManager extends Bn2Manager
/*      */   implements AssetApprovalConstants
/*      */ {
/*   84 */   private ScheduleManager m_scheduleManager = null;
/*   85 */   private DBTransactionManager m_transactionManager = null;
/*   86 */   protected MultiLanguageSearchManager m_searchManager = null;
/*   87 */   private IAssetManager m_assetManager = null;
/*      */   private static final String c_ksClassName = "AssetApprovalManager";
/* 1128 */   private EmailManager m_emailManager = null;
/*      */ 
/* 1134 */   private ABUserManager m_userManager = null;
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  109 */     super.startup();
/*      */ 
/*  112 */     TimerTask task = new TimerTask()
/*      */     {
/*      */       public void run()
/*      */       {
/*      */         try
/*      */         {
/*  118 */           AssetApprovalManager.this.removeExpiredApprovals();
/*      */         }
/*      */         catch (Bn2Exception bn2e)
/*      */         {
/*  122 */           AssetApprovalManager.this.m_logger.error("AssetApprovalManager: Bn2Exception whilst removing expired asset approvals : " + bn2e);
/*      */         }
/*      */       }
/*      */     };
/*  127 */     int iHourOfDay = AssetBankSettings.getRemoveExpiredApprovalsHourOfDay();
/*  128 */     if (iHourOfDay >= 0)
/*      */     {
/*  130 */       this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addAssetForApproval(DBTransaction a_dbTransaction, AssetInList a_assetApproval, long a_lUserId, boolean a_bIsHighResApproval)
/*      */     throws Bn2Exception
/*      */   {
/*  154 */     String ksMethodName = "addAssetForApproval";
/*  155 */     Connection con = null;
/*  156 */     String sSQL = null;
/*  157 */     PreparedStatement psql = null;
/*      */ 
/*  159 */     long lAssetId = a_assetApproval.getAsset().getId();
/*  160 */     String sUserNotes = a_assetApproval.getUserNotes();
/*      */     try
/*      */     {
/*  164 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  167 */       sSQL = "SELECT 1 FROM AssetApproval WHERE AssetId=? AND UserId=?";
/*  168 */       psql = con.prepareStatement(sSQL);
/*  169 */       psql.setLong(1, lAssetId);
/*  170 */       psql.setLong(2, a_lUserId);
/*  171 */       ResultSet rs = psql.executeQuery();
/*  172 */       if (rs.next())
/*      */       {
/*  174 */         psql.close();
/*      */ 
/*  176 */         sSQL = "UPDATE AssetApproval SET UserNotes=?, ApprovalStatusId=?, UsageTypeId=?, IsHighResApproval=? WHERE AssetId=? AND UserId=?";
/*  177 */         psql = con.prepareStatement(sSQL);
/*      */ 
/*  179 */         int iField = 1;
/*  180 */         psql.setString(iField++, sUserNotes);
/*  181 */         psql.setLong(iField++, 1L);
/*  182 */         DBUtil.setFieldIdOrNull(psql, iField++, a_assetApproval.getUsageTypeId());
/*  183 */         psql.setBoolean(iField++, a_bIsHighResApproval);
/*  184 */         psql.setLong(iField++, lAssetId);
/*  185 */         psql.setLong(iField++, a_lUserId);
/*  186 */         psql.executeUpdate();
/*      */       }
/*      */       else
/*      */       {
/*  191 */         psql.close();
/*      */ 
/*  193 */         sSQL = "INSERT INTO AssetApproval (AssetId, UserId, ApprovalStatusId, UsageTypeId, DateSubmitted, UserNotes, IsHighResApproval) VALUES (?,?,?,?,?,?,?)";
/*      */ 
/*  195 */         psql = con.prepareStatement(sSQL);
/*  196 */         int iCol = 1;
/*  197 */         psql.setLong(iCol++, lAssetId);
/*  198 */         psql.setLong(iCol++, a_lUserId);
/*  199 */         psql.setLong(iCol++, 1L);
/*  200 */         DBUtil.setFieldIdOrNull(psql, iCol++, a_assetApproval.getUsageTypeId());
/*  201 */         psql.setTimestamp(iCol++, new Timestamp(new Date().getTime()));
/*  202 */         psql.setString(iCol++, sUserNotes);
/*  203 */         psql.setBoolean(iCol++, a_bIsHighResApproval);
/*  204 */         psql.executeUpdate();
/*      */       }
/*      */ 
/*  207 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  212 */       throw new Bn2Exception("AssetApprovalManager.addAssetForApproval: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getAllUsersWithApprovalLists(DBTransaction a_dbTransaction, Vector a_vecCategoryIds)
/*      */     throws Bn2Exception
/*      */   {
/*  236 */     String ksMethodName = "getAllUsersWithApprovalLists";
/*  237 */     Connection con = null;
/*  238 */     String sSQL = null;
/*  239 */     PreparedStatement psql = null;
/*  240 */     Vector vecUsersWithApprovalLists = new Vector();
/*      */     try
/*      */     {
/*  244 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  248 */       sSQL = "SELECT abu.Id abuId, abu.Forename, abu.Surname, MIN(aa.DateSubmitted) AS OldestSubmitDate FROM AssetBankUser abu, AssetApproval aa ";
/*      */ 
/*  251 */       if (a_vecCategoryIds != null)
/*      */       {
/*  254 */         sSQL = sSQL + ", CM_ItemInCategory iic ";
/*      */       }
/*      */ 
/*  257 */       sSQL = sSQL + "WHERE abu.Id = aa.UserId AND aa.ApprovalStatusId = 1 ";
/*      */ 
/*  260 */       if (a_vecCategoryIds != null)
/*      */       {
/*  262 */         sSQL = sSQL + " AND aa.AssetId = iic.ItemId AND iic.CategoryId IN (0";
/*      */ 
/*  264 */         for (int i = 0; i < a_vecCategoryIds.size(); i++)
/*      */         {
/*  266 */           sSQL = sSQL + "," + ((Long)a_vecCategoryIds.get(i)).longValue();
/*      */         }
/*      */ 
/*  269 */         sSQL = sSQL + ") ";
/*      */       }
/*      */ 
/*  272 */       sSQL = sSQL + "GROUP BY abu.Id, abu.Forename, abu.Surname ORDER BY abu.Surname, abu.Forename";
/*      */ 
/*  275 */       psql = con.prepareStatement(sSQL);
/*  276 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  278 */       while (rs.next())
/*      */       {
/*  280 */         UsersWithApprovalListRecord record = new UsersWithApprovalListRecord();
/*  281 */         record.setId(rs.getLong("abuId"));
/*  282 */         record.setName(rs.getString("Forename") + " " + rs.getString("Surname"));
/*  283 */         BrightDate date = new BrightDate(rs.getDate("OldestSubmitDate"));
/*  284 */         record.setDateOfOldestApprovalRequest(date);
/*      */ 
/*  286 */         vecUsersWithApprovalLists.add(record);
/*      */       }
/*      */ 
/*  289 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  294 */       throw new Bn2Exception("AssetApprovalManager.getAllUsersWithApprovalLists: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  297 */     return vecUsersWithApprovalLists;
/*      */   }
/*      */ 
/*      */   public Vector getAssetApprovalList(DBTransaction a_dbTransaction, AssetApprovalSearchCriteria a_search, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  327 */     String ksMethodName = "getAssetApprovalList";
/*  328 */     Connection con = null;
/*  329 */     String sSQL = null;
/*  330 */     PreparedStatement psql = null;
/*  331 */     Vector vecAssetApprovalList = new Vector();
/*      */     try
/*      */     {
/*  335 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  339 */       sSQL = "SELECT ass.Id assId, ass.AssetTypeId assAssetTypeId, ass.Code, ass.FileLocation, ass.OriginalFileLocation, ass.FileSizeInBytes, ass.ThumbnailFileLocation assThumbnailFileLocation, ass.SmallFileLocation assSmallFileLocation, ass.MediumFileLocation assMediumFileLocation, ass.Price, ass.ImportedAssetId, ass.IsPreviewRestricted, ass.IsSensitive, ass.Synchronised, ass.OriginalFilename, u.Id userId, u.Username, u.Forename, u.Surname, u.EmailAddress, ff.Id ffId, ff.AssetTypeId ffAssetTypeId, ff.Name ffName, ff.Description ffDescription, ff.FileExtension, ff.IsIndexable, ff.IsConvertable, ff.IsConversionTarget, ff.ThumbnailFileLocation ffThumbnailFileLocation, ff.ContentType, ff.ConverterClass, ff.ToTextConverterClass,ff.ViewFileInclude, ff.PreviewInclude, ff.PreviewWidth, ff.PreviewHeight, ff.ConvertIndividualLayers, ff.CanViewOriginal, aa.DateSubmitted, aa.DateApproved, aa.DateExpires, aa.AdminNotes, aa.UserNotes, aa.UsageTypeId, aa.IsHighResApproval, appstatus.Id appstatusId, appstatus.Name appstatusName, ia.Height, ia.Width, ia.ColorSpace, ia.NumLayers FROM Asset ass INNER JOIN AssetApproval aa ON aa.AssetId = ass.Id INNER JOIN AssetBankUser u ON aa.UserId = u.Id INNER JOIN ApprovalStatus appstatus ON appstatus.Id = aa.ApprovalStatusId LEFT JOIN FileFormat ff ON ass.FileFormatId = ff.Id LEFT JOIN ImageAsset ia ON ia.AssetId = ass.Id ";
/*      */ 
/*  397 */       if (a_search.getCategoryIds() != null)
/*      */       {
/*  399 */         sSQL = sSQL + "INNER JOIN CM_ItemInCategory iic ON iic.ItemId = ass.Id AND iic.CategoryId IN (0";
/*      */ 
/*  401 */         for (int i = 0; i < a_search.getCategoryIds().size(); i++)
/*      */         {
/*  403 */           sSQL = sSQL + "," + ((Long)a_search.getCategoryIds().get(i)).longValue();
/*      */         }
/*      */ 
/*  406 */         sSQL = sSQL + ") ";
/*      */       }
/*      */ 
/*  409 */       sSQL = sSQL + "WHERE u.Id = ? AND aa.ApprovalStatusId = ? ";
/*      */ 
/*  412 */       if (a_search.getDateStart() != null)
/*      */       {
/*  414 */         sSQL = sSQL + "AND aa.DateApproved >= ? ";
/*      */       }
/*  416 */       if (a_search.getDateEnd() != null)
/*      */       {
/*  418 */         sSQL = sSQL + "AND aa.DateApproved <= ? ";
/*      */       }
/*  420 */       if ((a_search.getAssetIds() != null) && (a_search.getAssetIds().size() > 0))
/*      */       {
/*  422 */         sSQL = sSQL + "AND aa.AssetId IN (" + a_search.getAssetIdsString() + ") ";
/*      */       }
/*      */ 
/*  425 */       sSQL = sSQL + "ORDER BY aa.DateApproved DESC, u.Surname ASC, u.Forename ASC ";
/*      */ 
/*  427 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  429 */       int iIndex = 1;
/*  430 */       psql.setLong(iIndex++, a_search.getUserId());
/*  431 */       psql.setLong(iIndex++, a_search.getApprovalStatusId());
/*      */ 
/*  433 */       if (a_search.getDateStart() != null)
/*      */       {
/*  435 */         DBUtil.setFieldDateOrNull(psql, iIndex++, a_search.getDateStart());
/*      */       }
/*  437 */       if (a_search.getDateEnd() != null)
/*      */       {
/*  439 */         DBUtil.setFieldDateOrNull(psql, iIndex++, a_search.getDateEnd());
/*      */       }
/*      */ 
/*  442 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  444 */       while (rs.next())
/*      */       {
/*  446 */         AssetApproval approval = new AssetApproval(a_language);
/*      */ 
/*  449 */         approval.getUserReference().setId(rs.getLong("userId"));
/*  450 */         approval.getUserReference().setName(rs.getString("Forename") + " " + rs.getString("Surname"));
/*      */ 
/*  453 */         approval.setAdminNotes(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "AdminNotes"));
/*  454 */         approval.setUserNotes(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "UserNotes"));
/*  455 */         approval.getApprovalStatus().setId(rs.getLong("appstatusId"));
/*  456 */         approval.getApprovalStatus().setName(rs.getString("appstatusName"));
/*  457 */         approval.getDateSubmitted().setDate(rs.getDate("DateSubmitted"));
/*  458 */         approval.getDateApproved().setDate(rs.getDate("DateApproved"));
/*  459 */         approval.getDateExpires().setDate(rs.getDate("DateExpires"));
/*  460 */         approval.setUsageTypeId(rs.getLong("UsageTypeId"));
/*  461 */         approval.setRequiresHighResApproval(rs.getBoolean("IsHighResApproval"));
/*      */ 
/*  464 */         Asset asset = AssetManagerUtil.createAssetFromResultSet(rs);
/*      */ 
/*  467 */         if ((!asset.getHasFile()) && (AssetBankSettings.getUseFirstChildAssetAsSurrogate()))
/*      */         {
/*  469 */           asset = this.m_assetManager.getAsset(a_dbTransaction, asset.getId(), null, true, false);
/*      */         }
/*      */ 
/*  472 */         approval.setAsset(asset);
/*      */ 
/*  476 */         if ((vecAssetApprovalList.size() == 0) || (!approval.equals(vecAssetApprovalList.get(vecAssetApprovalList.size() - 1))))
/*      */         {
/*  478 */           vecAssetApprovalList.add(approval);
/*      */         }
/*      */       }
/*      */ 
/*  482 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  487 */       throw new Bn2Exception("AssetApprovalManager.getAssetApprovalList: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  490 */     return vecAssetApprovalList;
/*      */   }
/*      */ 
/*      */   public AssetApproval getAssetApproval(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  511 */     Connection con = null;
/*  512 */     ResultSet rs = null;
/*  513 */     String sSQL = null;
/*  514 */     PreparedStatement psql = null;
/*  515 */     AssetApproval approval = new AssetApproval();
/*      */     try
/*      */     {
/*  519 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  521 */       sSQL = "SELECT  aa.AdminNotes, aa.UserNotes, aa.UsageTypeId, aa.DateSubmitted, aa.DateApproved, aa.DateExpires, appstatus.Id appstatusId, appstatus.Name appstatusName FROM AssetApproval aa, ApprovalStatus appstatus WHERE aa.UserId = ? AND aa.AssetId = ? AND aa.ApprovalStatusId = appstatus.Id ";
/*      */ 
/*  531 */       psql = con.prepareStatement(sSQL);
/*  532 */       psql.setLong(1, a_lUserId);
/*  533 */       psql.setLong(2, a_lAssetId);
/*  534 */       rs = psql.executeQuery();
/*      */ 
/*  536 */       if (rs.next())
/*      */       {
/*  538 */         approval.getApprovalStatus().setId(rs.getLong("appstatusId"));
/*  539 */         approval.getApprovalStatus().setName(rs.getString("appstatusName"));
/*  540 */         approval.setAdminNotes(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "AdminNotes"));
/*  541 */         approval.setUserNotes(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "UserNotes"));
/*  542 */         approval.setUsageTypeId(rs.getLong("UsageTypeId"));
/*      */ 
/*  544 */         approval.getDateSubmitted().setDate(rs.getDate("DateSubmitted"));
/*  545 */         approval.getDateApproved().setDate(rs.getDate("DateApproved"));
/*  546 */         approval.getDateExpires().setDate(rs.getDate("DateExpires"));
/*      */       }
/*      */ 
/*  549 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  553 */       this.m_logger.error("SQL Exception whilst getting an asset box from the database : " + e);
/*  554 */       throw new Bn2Exception("SQL Exception whilst getting an asset box from the database : " + e, e);
/*      */     }
/*      */ 
/*  557 */     return approval;
/*      */   }
/*      */ 
/*      */   public long getApprovalStatus(long a_lUserId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  581 */     String ksMethodName = "getApprovalStatus";
/*  582 */     Connection con = null;
/*  583 */     String sSQL = null;
/*  584 */     PreparedStatement psql = null;
/*  585 */     long lApprovalStatusId = 0L;
/*  586 */     DBTransaction transaction = null;
/*      */     try
/*      */     {
/*  590 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*  591 */       con = transaction.getConnection();
/*      */ 
/*  593 */       sSQL = "SELECT ApprovalStatusId FROM AssetApproval WHERE UserId = ? AND AssetId = ? ";
/*      */ 
/*  598 */       psql = con.prepareStatement(sSQL);
/*  599 */       psql.setLong(1, a_lUserId);
/*  600 */       psql.setLong(2, a_lAssetId);
/*  601 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  603 */       while (rs.next())
/*      */       {
/*  605 */         lApprovalStatusId = rs.getLong("ApprovalStatusId");
/*      */       }
/*      */ 
/*  608 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/*  614 */         this.m_logger.debug("AssetApprovalManager.getApprovalStatus: About to rollback");
/*      */ 
/*  616 */         if (transaction != null)
/*      */         {
/*  618 */           transaction.rollback();
/*      */         }
/*      */       }
/*      */       catch (SQLException rbe)
/*      */       {
/*      */       }
/*      */ 
/*  625 */       throw new Bn2Exception("AssetApprovalManager.getApprovalStatus: SQL Exception whilst deleting expired approvals: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  630 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  634 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  643 */     return lApprovalStatusId;
/*      */   }
/*      */ 
/*      */   public void updateUserApprovalList(DBTransaction a_dbTransaction, Vector a_vecAssetApprovalList)
/*      */     throws Bn2Exception
/*      */   {
/*  664 */     String ksMethodName = "updateUserApprovalList";
/*  665 */     Connection con = null;
/*  666 */     String sSQL = null;
/*  667 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  671 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  673 */       Iterator it = a_vecAssetApprovalList.iterator();
/*  674 */       while (it.hasNext())
/*      */       {
/*  676 */         AssetApproval approval = (AssetApproval)it.next();
/*      */ 
/*  678 */         sSQL = "UPDATE AssetApproval SET ApprovalStatusId=?, AdminNotes=?, DateExpires=?, DateApproved=? WHERE AssetId = ? AND UserId = ? ";
/*      */ 
/*  683 */         psql = con.prepareStatement(sSQL);
/*  684 */         int iCol = 1;
/*  685 */         psql.setLong(iCol++, approval.getApprovalStatus().getId());
/*  686 */         psql.setString(iCol++, approval.getAdminNotes());
/*  687 */         DBUtil.setFieldDateOrNull(psql, iCol++, approval.getDateExpires().getDate());
/*  688 */         DBUtil.setFieldDateOrNull(psql, iCol++, approval.getDateApproved().getDate());
/*  689 */         psql.setLong(iCol++, approval.getAsset().getId());
/*  690 */         psql.setLong(iCol++, approval.getUserReference().getId());
/*      */ 
/*  692 */         psql.executeUpdate();
/*  693 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  701 */       throw new Bn2Exception("AssetApprovalManager.updateUserApprovalList: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeExpiredApprovals()
/*      */     throws Bn2Exception
/*      */   {
/*  720 */     String ksMethodName = "removeExpiredApprovals";
/*  721 */     this.m_logger.debug("AssetApprovalManager.removeExpiredApprovals: Running");
/*  722 */     DBTransaction transaction = null;
/*  723 */     Connection con = null;
/*      */     try
/*      */     {
/*  727 */       transaction = this.m_transactionManager.getNewTransaction();
/*  728 */       con = transaction.getConnection();
/*      */ 
/*  731 */       Date now = new Date();
/*      */ 
/*  733 */       String sSql = "DELETE FROM AssetApproval WHERE DateExpires < ? ";
/*  734 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  735 */       DBUtil.setFieldDateOrNull(psql, 1, now);
/*  736 */       psql.executeUpdate();
/*  737 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/*  743 */         this.m_logger.debug("AssetApprovalManager.removeExpiredApprovals: About to rollback");
/*  744 */         transaction.rollback();
/*      */       }
/*      */       catch (SQLException rbe)
/*      */       {
/*      */       }
/*      */ 
/*  750 */       throw new Bn2Exception("AssetApprovalManager.removeExpiredApprovals: SQL Exception whilst deleting expired approvals: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  755 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  759 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  764 */           this.m_logger.debug("AssetApprovalManager.removeExpiredApprovals: Exception while attempting to commit transaction.");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAssetApproval(DBTransaction a_dbTransaction, AssetApproval a_assetApproval)
/*      */     throws Bn2Exception
/*      */   {
/*  789 */     String ksMethodName = "setAssetApproval";
/*  790 */     Connection con = null;
/*  791 */     String sSQL = null;
/*  792 */     PreparedStatement psql = null;
/*      */ 
/*  794 */     long lAssetId = a_assetApproval.getAsset().getId();
/*  795 */     long lUserId = a_assetApproval.getUserReference().getId();
/*      */     try
/*      */     {
/*  798 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  801 */       sSQL = "SELECT 1 FROM AssetApproval WHERE AssetId=? AND UserId=?";
/*  802 */       psql = con.prepareStatement(sSQL);
/*  803 */       psql.setLong(1, lAssetId);
/*  804 */       psql.setLong(2, lUserId);
/*  805 */       ResultSet rs = psql.executeQuery();
/*  806 */       if (rs.next())
/*      */       {
/*  808 */         psql.close();
/*      */ 
/*  810 */         sSQL = "UPDATE AssetApproval SET UserNotes=?, AdminNotes=?, ApprovalStatusId=?, UsageTypeId=?, DateExpires=?, DateApproved=? WHERE AssetId=? AND UserId=?";
/*      */ 
/*  813 */         psql = con.prepareStatement(sSQL);
/*      */ 
/*  815 */         int iField = 1;
/*  816 */         psql.setString(iField++, a_assetApproval.getUserNotes());
/*  817 */         psql.setString(iField++, a_assetApproval.getAdminNotes());
/*  818 */         psql.setLong(iField++, a_assetApproval.getApprovalStatus().getId());
/*  819 */         DBUtil.setFieldIdOrNull(psql, iField++, a_assetApproval.getUsageTypeId());
/*  820 */         DBUtil.setFieldDateOrNull(psql, iField++, a_assetApproval.getDateExpires().getDate());
/*  821 */         DBUtil.setFieldDateOrNull(psql, iField++, a_assetApproval.getDateApproved().getDate());
/*  822 */         psql.setLong(iField++, lAssetId);
/*  823 */         psql.setLong(iField++, lUserId);
/*  824 */         psql.executeUpdate();
/*      */       }
/*      */       else
/*      */       {
/*  829 */         psql.close();
/*      */ 
/*  831 */         sSQL = "INSERT INTO AssetApproval (AssetId, UserId, ApprovalStatusId, UsageTypeId, DateSubmitted, DateApproved, DateExpires, UserNotes, AdminNotes) VALUES (?,?,?,?,?,?,?,?,?)";
/*      */ 
/*  835 */         psql = con.prepareStatement(sSQL);
/*  836 */         int iField = 1;
/*  837 */         psql.setLong(iField++, lAssetId);
/*  838 */         psql.setLong(iField++, lUserId);
/*  839 */         psql.setLong(iField++, a_assetApproval.getApprovalStatus().getId());
/*  840 */         DBUtil.setFieldIdOrNull(psql, iField++, a_assetApproval.getUsageTypeId());
/*  841 */         DBUtil.setFieldDateOrNull(psql, iField++, a_assetApproval.getDateSubmitted().getDate());
/*  842 */         DBUtil.setFieldDateOrNull(psql, iField++, a_assetApproval.getDateApproved().getDate());
/*  843 */         DBUtil.setFieldDateOrNull(psql, iField++, a_assetApproval.getDateExpires().getDate());
/*  844 */         psql.setString(iField++, a_assetApproval.getUserNotes());
/*  845 */         psql.setString(iField++, a_assetApproval.getAdminNotes());
/*  846 */         psql.executeUpdate();
/*      */       }
/*      */ 
/*  850 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  855 */       throw new Bn2Exception("AssetApprovalManager.setAssetApproval: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public int getUnapprovedUploadedAssetsCount(DBTransaction a_dbTransaction, UserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  879 */     String ksMethodName = "getUnapprovedUploadedAssetsCount";
/*  880 */     Connection con = null;
/*  881 */     String sSQL = null;
/*  882 */     PreparedStatement psql = null;
/*  883 */     ResultSet rs = null;
/*  884 */     int iCount = 0;
/*      */     try
/*      */     {
/*  888 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  890 */       if (a_userProfile.getIsAdmin())
/*      */       {
/*  893 */         sSQL = "SELECT COUNT(*) assetcount FROM Asset WHERE IsApproved=0";
/*  894 */         psql = con.prepareStatement(sSQL);
/*  895 */         rs = psql.executeQuery();
/*  896 */         if (rs.next())
/*      */         {
/*  898 */           iCount = rs.getInt("assetcount");
/*      */         }
/*      */ 
/*  901 */         psql.close();
/*      */       }
/*      */       else
/*      */       {
/*  907 */         sSQL = "SELECT ass.Id assetId, c.Id categoryId, max(cvg.CanApproveAssetUploads) canapprove FROM Asset ass, CM_ItemInCategory iic, CM_Category c, CategoryVisibleToGroup cvg, UserInGroup uig WHERE ass.IsApproved=0 AND ass.Id = iic.ItemId AND iic.CategoryId =c.Id AND c.Id = cvg.CategoryId AND cvg.UserGroupId = uig.UserGroupId AND uig.UserId = " + a_userProfile.getUser().getId() + " GROUP BY ass.Id, c.Id " + "ORDER BY ass.Id, c.Id, canapprove";
/*      */ 
/*  916 */         psql = con.prepareStatement(sSQL);
/*  917 */         rs = psql.executeQuery();
/*  918 */         long lLastAssetId = 0L;
/*  919 */         int iAssetFlag = 0;
/*      */ 
/*  921 */         while (rs.next())
/*      */         {
/*  924 */           if (lLastAssetId != rs.getLong("assetId"))
/*      */           {
/*  926 */             iCount += iAssetFlag;
/*  927 */             lLastAssetId = rs.getLong("assetId");
/*      */ 
/*  930 */             iAssetFlag = 1;
/*      */           }
/*      */ 
/*  934 */           if (rs.getInt("canapprove") != 0) {
/*      */             continue;
/*      */           }
/*  937 */           iAssetFlag = 0;
/*      */         }
/*      */ 
/*  942 */         iCount += iAssetFlag;
/*      */ 
/*  944 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  951 */       throw new Bn2Exception("AssetApprovalManager.getUnapprovedUploadedAssetsCount: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  954 */     return iCount;
/*      */   }
/*      */ 
/*      */   public void submitAndApproveAssetForUser(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  969 */     AssetApproval approval = new AssetApproval();
/*  970 */     approval.getAsset().setId(a_lAssetId);
/*  971 */     approval.getUserReference().setId(a_lUserId);
/*  972 */     approval.getApprovalStatus().setId(2L);
/*      */ 
/*  975 */     approval.getDateApproved().setDate(new Date());
/*  976 */     approval.getDateSubmitted().setDate(new Date());
/*      */ 
/*  979 */     int iExpiryDays = AssetBankSettings.getDefaultApprovalExpiryPeriod();
/*  980 */     Calendar cal = Calendar.getInstance();
/*  981 */     cal.setTime(new Date());
/*  982 */     cal.add(5, iExpiryDays);
/*  983 */     approval.getDateExpires().setDate(cal.getTime());
/*      */ 
/*  985 */     setAssetApproval(a_dbTransaction, approval);
/*      */   }
/*      */ 
/*      */   public void approveAssetForUser(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1000 */     AssetApproval approval = new AssetApproval();
/* 1001 */     approval.getAsset().setId(a_lAssetId);
/* 1002 */     approval.getUserReference().setId(a_lUserId);
/* 1003 */     approval.getApprovalStatus().setId(2L);
/*      */ 
/* 1006 */     approval.getDateApproved().setDate(new Date());
/*      */ 
/* 1009 */     int iExpiryDays = AssetBankSettings.getDefaultApprovalExpiryPeriod();
/* 1010 */     Calendar cal = Calendar.getInstance();
/* 1011 */     cal.setTime(new Date());
/* 1012 */     cal.add(5, iExpiryDays);
/* 1013 */     approval.getDateExpires().setDate(cal.getTime());
/*      */ 
/* 1015 */     setAssetApproval(a_dbTransaction, approval);
/*      */   }
/*      */ 
/*      */   public void setAssetPendingApprovalForUser(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1029 */     AssetApproval approval = new AssetApproval();
/* 1030 */     approval.getAsset().setId(a_lAssetId);
/* 1031 */     approval.getUserReference().setId(a_lUserId);
/* 1032 */     approval.getApprovalStatus().setId(1L);
/*      */ 
/* 1035 */     approval.getDateSubmitted().setDate(new Date());
/*      */ 
/* 1037 */     setAssetApproval(a_dbTransaction, approval);
/*      */   }
/*      */ 
/*      */   public void setAssetApprovalRejectedForUser(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1051 */     AssetApproval approval = new AssetApproval();
/* 1052 */     approval.getAsset().setId(a_lAssetId);
/* 1053 */     approval.getUserReference().setId(a_lUserId);
/* 1054 */     approval.getApprovalStatus().setId(3L);
/*      */ 
/* 1057 */     approval.getDateApproved().setDate(new Date());
/*      */ 
/* 1059 */     setAssetApproval(a_dbTransaction, approval);
/*      */   }
/*      */ 
/*      */   public void sendApprovalEmail(ABUser user, HashMap hmAdminEmails, boolean bNeedSuperUsers, String sRequestedAssetIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1073 */     String sName = user.getFullName();
/* 1074 */     if (!StringUtil.stringIsPopulated(sName))
/*      */     {
/* 1076 */       sName = user.getUsername();
/*      */     }
/*      */ 
/* 1079 */     String sEmailAddress = user.getEmailAddress();
/*      */ 
/* 1082 */     String sAdminEmailAddress = StringUtil.getEmailAddressesFromHashMap(hmAdminEmails);
/*      */ 
/* 1084 */     if (bNeedSuperUsers)
/*      */     {
/* 1086 */       if (StringUtil.stringIsPopulated(sAdminEmailAddress))
/*      */       {
/* 1088 */         String sSuperEmailAddress = this.m_userManager.getAdminEmailAddresses();
/* 1089 */         if (StringUtil.stringIsPopulated(sSuperEmailAddress))
/*      */         {
/* 1091 */           sAdminEmailAddress = sAdminEmailAddress + ";" + sSuperEmailAddress;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1096 */         sAdminEmailAddress = this.m_userManager.getAdminEmailAddresses();
/*      */       }
/*      */     }
/*      */ 
/* 1100 */     if (StringUtil.stringIsPopulated(sAdminEmailAddress))
/*      */     {
/* 1103 */       HashMap params = new HashMap();
/* 1104 */       params.put("template", "admin_alert_approvals");
/* 1105 */       params.put("name", sName);
/* 1106 */       params.put("email", sEmailAddress);
/* 1107 */       params.put("adminEmailAddresses", sAdminEmailAddress);
/* 1108 */       params.put("assetids", sRequestedAssetIds);
/*      */       try
/*      */       {
/* 1112 */         this.m_emailManager.sendTemplatedEmail(params, LanguageConstants.k_defaultLanguage);
/*      */       }
/*      */       catch (Bn2Exception e)
/*      */       {
/* 1117 */         this.m_logger.debug("SaveRequestApprovalAction: The alert email was not successfully sent to the admin: " + e.getMessage());
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1123 */       this.m_logger.debug("SaveRequestApprovalAction: No admins to email alert to!");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setEmailManager(EmailManager a_emailManager)
/*      */   {
/* 1131 */     this.m_emailManager = a_emailManager;
/*      */   }
/*      */ 
/*      */   public void setUserManager(ABUserManager a_userManager)
/*      */   {
/* 1137 */     this.m_userManager = a_userManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/* 1142 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 1147 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/* 1152 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(IAssetManager assetManager)
/*      */   {
/* 1158 */     this.m_assetManager = assetManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.service.AssetApprovalManager
 * JD-Core Version:    0.6.0
 */