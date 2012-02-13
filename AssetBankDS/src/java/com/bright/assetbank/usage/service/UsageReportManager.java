/*      */ package com.bright.assetbank.usage.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.service.AssetManager;
/*      */ import com.bright.assetbank.application.util.AssetUtil;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.usage.bean.DownloadReportGroupUsage;
/*      */ import com.bright.assetbank.usage.bean.DownloadReportResult;
/*      */ import com.bright.assetbank.usage.bean.DownloadReportUserUsage;
/*      */ import com.bright.assetbank.usage.bean.ReasonForDownloadResult;
           import com.bright.assetbank.usage.bean.ReportEntity;
/*      */ import com.bright.assetbank.usage.bean.ReportEntity.CountComparatorAsc;
/*      */ import com.bright.assetbank.usage.bean.ReportEntity.CountComparatorDesc;
/*      */ import com.bright.assetbank.usage.bean.ReportGroup;
/*      */ import com.bright.assetbank.usage.bean.ReportUser;
/*      */ import com.bright.assetbank.usage.bean.ScheduledReport;
/*      */ import com.bright.assetbank.usage.bean.TotalsReport;
/*      */ import com.bright.assetbank.usage.bean.UsageReportResult;
/*      */ import com.bright.assetbank.usage.bean.ViewReportAsset;
/*      */ import com.bright.assetbank.usage.constant.UsageConstants;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.bean.FlatCategoryList;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.storage.constant.StoredFileType;
/*      */ import com.bright.framework.util.BrightDateFormat;
/*      */ import com.bright.framework.util.DateUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.mail.EmailAttachment;
/*      */ 
/*      */ public class UsageReportManager extends Bn2Manager
/*      */   implements UsageConstants
/*      */ {
/*      */   private static final String c_ksClassName = "UsageReportManager";
/*   90 */   private FileStoreManager m_fileStoreManager = null;
/*   91 */   private EmailManager m_emailManager = null;
/*   92 */   private DBTransactionManager m_transactionManager = null;
/*   93 */   private ScheduleManager m_scheduleManager = null;
/*   94 */   private CategoryManager m_categoryManager = null;
/*   95 */   private AssetManager m_assetManager = null;
/*      */ 
/*   97 */   private ListManager m_listManager = null;
/*      */ 
/*      */   public void setListManager(ListManager a_listManager)
/*      */   {
/*  101 */     this.m_listManager = a_listManager;
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  114 */     super.startup();
/*  115 */     int iHourOfDay = 0;
/*  116 */     TimerTask task = null;
/*      */ 
/*  119 */     iHourOfDay = AssetBankSettings.getHourToScheduleReports();
/*  120 */     if (iHourOfDay >= 0)
/*      */     {
/*  122 */       task = new TimerTask()
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           try
/*      */           {
/*  128 */             UsageReportManager.this.processScheduledReports();
/*      */           }
/*      */           catch (Bn2Exception bn2e)
/*      */           {
/*  132 */             UsageReportManager.this.m_logger.error("UsageReportManager: Bn2Exception whilst processing scheduled reports : " + bn2e);
/*      */           }
/*      */         }
/*      */       };
/*  137 */       this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getUsageTypeReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/*  157 */     Connection con = null;
/*  158 */     PreparedStatement psql = null;
/*  159 */     String sSQL = null;
/*  160 */     ResultSet rs = null;
/*  161 */     DBTransaction transaction = a_dbTransaction;
/*  162 */     Vector vecResults = null;
/*      */ 
/*  164 */     if (transaction == null)
/*      */     {
/*  166 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  171 */       con = transaction.getConnection();
/*      */ 
/*  173 */       sSQL = "SELECT COUNT(Id) NoOfUses, Description FROM (SELECT Id, OriginalDescription as Description FROM AssetUse WHERE TimeOfDownload >= ? AND TimeOfDownload <= ? UNION SELECT Id, sdut.OriginalDescription as Description FROM AssetUse au INNER JOIN SecondaryDownloadUsageType sdut ON sdut.AssetUseId = au.Id WHERE TimeOfDownload >= ? AND TimeOfDownload <= ?) au_union_sdut GROUP BY Description ORDER BY NoOfUses";
/*      */ 
/*  182 */       int iCol = 1;
/*      */ 
/*  184 */       psql = con.prepareStatement(sSQL);
/*  185 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtStartDate);
/*  186 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtEndDate);
/*  187 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtStartDate);
/*  188 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtEndDate);
/*  189 */       rs = psql.executeQuery();
/*  190 */       UsageReportResult reportResult = null;
/*      */ 
/*  192 */       while (rs.next())
/*      */       {
/*  194 */         if (vecResults == null)
/*      */         {
/*  196 */           vecResults = new Vector();
/*      */         }
/*      */ 
/*  200 */         reportResult = new UsageReportResult();
/*  201 */         reportResult.setCount(rs.getInt("NoOfUses"));
/*  202 */         reportResult.setUsageDescription(rs.getString("Description"));
/*      */ 
/*  204 */         vecResults.add(reportResult);
/*      */       }
/*      */ 
/*  207 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  211 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  215 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  219 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  223 */       this.m_logger.error("SQL Exception whilst getting usage type report : " + e);
/*  224 */       throw new Bn2Exception("SQL Exception whilst getting usage type report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  229 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  233 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  237 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  242 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public Vector getAssetDownloadReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, boolean a_bGetGroupInfo)
/*      */     throws Bn2Exception
/*      */   {
/*  269 */     Connection con = null;
/*  270 */     PreparedStatement psql = null;
/*  271 */     String sSQL = null;
/*  272 */     ResultSet rs = null;
/*  273 */     DBTransaction transaction = a_dbTransaction;
/*  274 */     Vector vecResults = null;
/*      */ 
/*  276 */     if (transaction == null)
/*      */     {
/*  278 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  283 */       con = transaction.getConnection();
/*      */ 
/*  285 */       if (a_bGetGroupInfo)
/*      */       {
/*  287 */         sSQL = "SELECT a.Id, a.FileLocation, a.OriginalFilename, a.ThumbnailFileLocation, u.Username, au.AssetId, ug.Name, COUNT(u.Username) userCount FROM Asset a INNER JOIN AssetUse au ON au.AssetId=a.Id INNER JOIN AssetBankUser u ON au.UserId=u.Id LEFT JOIN UserInGroup uig ON u.Id=uig.UserId LEFT JOIN UserGroup ug ON uig.UserGroupId=ug.Id WHERE (au.TimeOfDownload >= ? AND au.TimeOfDownload <= ?) OR au.AssetId IS NULL GROUP BY a.Id, a.FileLocation, a.OriginalFilename, a.ThumbnailFileLocation, ug.Name, u.Username, au.AssetId ORDER BY a.Id, ug.Name, u.Username";
/*      */       }
/*      */       else
/*      */       {
/*  308 */         sSQL = "SELECT a.Id, a.FileLocation, a.OriginalFilename, a.ThumbnailFileLocation, u.Username, au.AssetId, COUNT(u.Username) userCount FROM Asset a INNER JOIN AssetUse au ON au.AssetId=a.Id INNER JOIN AssetBankUser u ON au.UserId=u.Id WHERE (au.TimeOfDownload >= ? AND au.TimeOfDownload <= ?) OR au.AssetId IS NULL GROUP BY a.Id, a.FileLocation, a.OriginalFilename, a.ThumbnailFileLocation, u.Username, au.AssetId ORDER BY a.Id, u.Username";
/*      */       }
/*      */ 
/*  325 */       psql = con.prepareStatement(sSQL);
/*  326 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/*  327 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/*      */ 
/*  329 */       rs = psql.executeQuery();
/*      */ 
/*  331 */       DownloadReportResult drr = null;
/*  332 */       DownloadReportResult lastDrr = null;
/*  333 */       DownloadReportGroupUsage drgu = null;
/*  334 */       String sLastId = null;
/*  335 */       String sLastGroup = "";
/*  336 */       boolean bFirst = true;
/*  337 */       boolean bGroupFirst = true;
/*  338 */       boolean bNew = false;
/*      */ 
/*  340 */       vecResults = new Vector();
/*      */ 
/*  342 */       while (rs.next())
/*      */       {
/*  344 */         bNew = false;
/*      */ 
/*  346 */         if (!rs.getString("Id").equals(sLastId))
/*      */         {
/*  348 */           if (!bFirst)
/*      */           {
/*  350 */             lastDrr = drr;
/*  351 */             vecResults.add(drr);
/*      */           }
/*      */           else
/*      */           {
/*  355 */             bFirst = false;
/*      */           }
/*      */ 
/*  358 */           drr = new DownloadReportResult();
/*  359 */           long lAssetId = rs.getLong("Id");
/*  360 */           drr.setId(lAssetId);
/*  361 */           drr.setOriginalFilename(rs.getString("OriginalFilename"));
/*      */ 
/*  363 */           String sThumbnailPath = AssetUtil.getThumbnailFileLocation(transaction, this.m_assetManager, rs.getString("ThumbnailFileLocation"), rs.getString("OriginalFilename"));
/*      */ 
/*  366 */           drr.setThumbnailPath(sThumbnailPath);
/*  367 */           sLastId = rs.getString("Id");
/*  368 */           sLastGroup = "";
/*      */ 
/*  370 */           bNew = true;
/*      */         }
/*      */ 
/*  374 */         if (a_bGetGroupInfo)
/*      */         {
/*  376 */           if (((rs.getString("Name") == null) && (sLastGroup != null)) || ((rs.getString("Name") != null) && (!rs.getString("Name").equals(sLastGroup))))
/*      */           {
/*  378 */             if (!bGroupFirst)
/*      */             {
/*  380 */               if (bNew)
/*      */               {
/*  382 */                 lastDrr.getGroupUses().add(drgu);
/*      */               }
/*      */               else
/*      */               {
/*  386 */                 drr.getGroupUses().add(drgu);
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/*  391 */               bGroupFirst = false;
/*      */             }
/*      */ 
/*  394 */             drgu = new DownloadReportGroupUsage();
/*  395 */             sLastGroup = rs.getString("Name");
/*      */ 
/*  397 */             if (rs.getString("Name") == null)
/*      */             {
/*  399 */               drgu.setGroupName("Unassigned Users");
/*      */             }
/*      */             else
/*      */             {
/*  403 */               drgu.setGroupName(rs.getString("Name"));
/*      */             }
/*      */           }
/*      */ 
/*  407 */           drgu.setCount(drgu.getCount() + rs.getInt("userCount"));
/*      */ 
/*  409 */           drr.setCount(drr.getCount() + rs.getInt("userCount"));
/*      */ 
/*  412 */           DownloadReportUserUsage druu = new DownloadReportUserUsage();
/*  413 */           druu.setUsername(rs.getString("Username"));
/*  414 */           druu.setCount(rs.getInt("userCount"));
/*  415 */           drgu.getUserUses().add(druu);
/*  416 */           continue;
/*      */         }
/*      */ 
/*  420 */         if (rs.getLong("userCount") <= 0L)
/*      */           continue;
/*  422 */         drr.setCount(drr.getCount() + rs.getInt("userCount"));
/*      */ 
/*  425 */         DownloadReportUserUsage druu = new DownloadReportUserUsage();
/*  426 */         druu.setUsername(rs.getString("Username"));
/*  427 */         druu.setCount(rs.getInt("userCount"));
/*  428 */         drr.getUserUses().add(druu);
/*      */       }
/*      */ 
/*  434 */       if (drr != null)
/*      */       {
/*  436 */         drr.getGroupUses().add(drgu);
/*  437 */         vecResults.add(drr);
/*      */       }
/*      */ 
/*  440 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  444 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  448 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  452 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  456 */       this.m_logger.error("SQL Exception whilst getting download report : " + e);
/*  457 */       throw new Bn2Exception("SQL Exception whilst getting download report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  462 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  466 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  470 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  475 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public TotalsReport getAssetDownloadCount(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/*  498 */     return getAssetDownloadReportData(a_dbTransaction, a_dtStartDate, a_dtEndDate, null, 0, -1);
/*      */   }
/*      */ 
/*      */   private TotalsReport getAssetDownloadReportData(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, Boolean a_bByMostPopular, int a_iStartIndex, int a_iMaxResults)
/*      */     throws Bn2Exception
/*      */   {
/*  515 */     Connection con = null;
/*  516 */     PreparedStatement psql = null;
/*  517 */     String sSQL = null;
/*  518 */     ResultSet rs = null;
/*  519 */     DBTransaction transaction = a_dbTransaction;
/*  520 */     TotalsReport report = new TotalsReport();
/*  521 */     Vector vResults = new Vector();
/*      */ 
/*  523 */     if (transaction == null)
/*      */     {
/*  525 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  530 */       con = transaction.getConnection();
/*      */ 
/*  532 */       sSQL = "SELECT a.Id, a.FileLocation, a.OriginalFilename, a.ThumbnailFileLocation, ";
/*      */ 
/*  534 */       if ((a_dtStartDate != null) || (a_dtEndDate != null))
/*      */       {
/*  536 */         sSQL = sSQL + "COUNT(au.AssetId) downloadCount FROM Asset a INNER JOIN AssetUse au ON au.AssetId=a.Id WHERE au.TimeOfDownload >= ? AND au.TimeOfDownload <= ? GROUP BY a.Id, a.FileLocation, a.OriginalFilename, a.ThumbnailFileLocation ";
/*      */       }
/*      */       else
/*      */       {
/*  544 */         sSQL = sSQL + "a.NumDownloads downloadCount FROM Asset a ";
/*      */       }
/*      */ 
/*  548 */       if (a_bByMostPopular != null)
/*      */       {
/*  550 */         sSQL = sSQL + "ORDER BY downloadCount ";
/*      */ 
/*  552 */         if (a_bByMostPopular.booleanValue())
/*      */         {
/*  554 */           sSQL = sSQL + "DESC";
/*      */         }
/*      */       }
/*      */ 
/*  558 */       if (a_iMaxResults > 0)
/*      */       {
/*  560 */         sSQL = SQLGenerator.getInstance().setRowLimit(sSQL, a_iMaxResults + a_iStartIndex);
/*      */       }
/*      */ 
/*  563 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  565 */       if ((a_dtStartDate != null) || (a_dtEndDate != null))
/*      */       {
/*  567 */         DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/*  568 */         DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/*      */       }
/*      */ 
/*  571 */       rs = psql.executeQuery();
/*      */ 
/*  573 */       DownloadReportResult drr = null;
/*  574 */       int iTotalDownloads = 0;
/*  575 */       int iTotalAssets = 0;
/*  576 */       int iRowCount = 0;
/*      */ 
/*  578 */       while (rs.next())
/*      */       {
/*  580 */         if (iRowCount++ < a_iStartIndex)
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  585 */         int iDownloadCount = rs.getInt("downloadCount");
/*  586 */         drr = new DownloadReportResult();
/*  587 */         long lAssetId = rs.getLong("Id");
/*  588 */         drr.setId(lAssetId);
/*  589 */         drr.setOriginalFilename(rs.getString("OriginalFilename"));
/*      */ 
/*  591 */         String sThumbnailPath = AssetUtil.getThumbnailFileLocation(a_dbTransaction, this.m_assetManager, rs.getString("ThumbnailFileLocation"), rs.getString("OriginalFilename"));
/*      */ 
/*  594 */         drr.setThumbnailPath(sThumbnailPath);
/*      */ 
/*  596 */         drr.setCount(iDownloadCount);
/*  597 */         vResults.add(drr);
/*      */ 
/*  599 */         iTotalAssets++;
/*  600 */         iTotalDownloads += iDownloadCount;
/*      */       }
/*      */ 
/*  603 */       psql.close();
/*      */ 
/*  605 */       report.setTotalAssets(iTotalAssets);
/*  606 */       report.setTotalTimes(iTotalDownloads);
/*  607 */       report.setAssetLines(vResults);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  611 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  615 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  619 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  623 */       this.m_logger.error("SQL Exception whilst getting download report : " + e);
/*  624 */       throw new Bn2Exception("SQL Exception whilst getting download report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  629 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  633 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  637 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*  641 */     return report;
/*      */   }
/*      */ 
/*      */   public TotalsReport getAssetViewReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/*  647 */     return getAssetViewReportData(a_dbTransaction, a_dtStartDate, a_dtEndDate, null, 0, -1);
/*      */   }
/*      */ 
/*      */   private TotalsReport getAssetViewReportData(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, Boolean a_bByMostPopular, int a_iStartIndex, int a_iMaxResults)
/*      */     throws Bn2Exception
/*      */   {
/*  660 */     TotalsReport report = new TotalsReport();
/*  661 */     Vector vResults = new Vector();
/*  662 */     Connection con = null;
/*  663 */     PreparedStatement psql = null;
/*  664 */     String sSQL = null;
/*  665 */     ResultSet rs = null;
/*  666 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  668 */     if (transaction == null)
/*      */     {
/*  670 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  675 */       con = transaction.getConnection();
/*      */ 
/*  677 */       if ((a_dtStartDate != null) || (a_dtEndDate != null))
/*      */       {
/*  679 */         sSQL = "SELECT ass.Id assId, ass.FileLocation, ass.OriginalFilename, ass.ThumbnailFileLocation, COUNT(av.AssetId) AssetViews FROM Asset ass INNER JOIN AssetView av ON av.AssetId = ass.Id WHERE av.Time >= ? AND av.Time <= ? GROUP BY ass.Id, ass.FileLocation, ass.OriginalFilename, ass.ThumbnailFileLocation ";
/*      */       }
/*      */       else
/*      */       {
/*  695 */         sSQL = "SELECT ass.Id assId, ass.FileLocation, ass.OriginalFilename, ass.ThumbnailFileLocation, ass.NumViews AssetViews FROM Asset ass ";
/*      */       }
/*      */ 
/*  705 */       if (a_bByMostPopular != null)
/*      */       {
/*  707 */         sSQL = sSQL + "ORDER BY AssetViews ";
/*      */ 
/*  709 */         if (a_bByMostPopular.booleanValue())
/*      */         {
/*  711 */           sSQL = sSQL + "DESC";
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  716 */         sSQL = sSQL + "ORDER BY assId ";
/*      */       }
/*      */ 
/*  719 */       if (a_iMaxResults > 0)
/*      */       {
/*  721 */         sSQL = SQLGenerator.getInstance().setRowLimit(sSQL, a_iMaxResults + a_iStartIndex);
/*      */       }
/*      */ 
/*  724 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  726 */       if ((a_dtStartDate != null) || (a_dtEndDate != null))
/*      */       {
/*  728 */         DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/*  729 */         DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/*      */       }
/*      */ 
/*  732 */       rs = psql.executeQuery();
/*      */ 
/*  734 */       long lAssetId = 0L;
/*  735 */       ViewReportAsset asset = null;
/*  736 */       int iTotalViews = 0;
/*  737 */       int iTotalAssets = 0;
/*  738 */       int iRowCount = 0;
/*      */ 
/*  740 */       while (rs.next())
/*      */       {
/*  742 */         if (iRowCount++ < a_iStartIndex)
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/*  747 */         lAssetId = rs.getLong("assId");
/*      */ 
/*  749 */         asset = new ViewReportAsset();
/*  750 */         asset.setId(lAssetId);
/*  751 */         asset.setOriginalFilename(rs.getString("OriginalFilename"));
/*      */ 
/*  753 */         String sThumbnailPath = AssetUtil.getThumbnailFileLocation(a_dbTransaction, this.m_assetManager, rs.getString("ThumbnailFileLocation"), rs.getString("OriginalFilename"));
/*      */ 
/*  756 */         asset.setThumbnailPath(sThumbnailPath);
/*      */ 
/*  758 */         int iViews = rs.getInt("AssetViews");
/*  759 */         asset.setCount(iViews);
/*  760 */         iTotalViews += iViews;
/*      */ 
/*  762 */         vResults.add(asset);
/*  763 */         iTotalAssets++;
/*      */       }
/*      */ 
/*  766 */       report.setAssetLines(vResults);
/*  767 */       report.setTotalAssets(iTotalAssets);
/*  768 */       report.setTotalTimes(iTotalViews);
/*      */ 
/*  770 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  774 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  778 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  782 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  786 */       this.m_logger.error("SQL Exception whilst getting asset view report by user: " + e);
/*  787 */       throw new Bn2Exception("SQL Exception whilst getting asset view report by user: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  792 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  796 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  800 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  805 */     return report;
/*      */   }
/*      */ 
/*      */   public TotalsReport getAccessLevelViewReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/*  817 */     TotalsReport totalsReport = new TotalsReport();
/*      */ 
/*  819 */     Connection con = null;
/*  820 */     PreparedStatement psql = null;
/*  821 */     String sSQL = null;
/*  822 */     ResultSet rs = null;
/*  823 */     DBTransaction transaction = a_dbTransaction;
/*  824 */     Vector vecCategories = null;
/*      */ 
/*  826 */     if (transaction == null)
/*      */     {
/*  828 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  833 */       con = transaction.getConnection();
/*      */ 
/*  835 */       sSQL = "SELECT cat.id catId, COUNT(AssetId) AssetViews FROM AssetView av, CM_ItemInCategory ic, CM_Category cat WHERE cat.CategoryTypeId=? AND ic.ItemId = av.AssetId AND ic.CategoryId=cat.Id AND av.Time >= ? AND av.Time <= ? GROUP BY cat.id, cat.name";
/*      */ 
/*  848 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  850 */       int iCol = 1;
/*  851 */       psql.setLong(iCol++, 2L);
/*  852 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtStartDate);
/*  853 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtEndDate);
/*      */ 
/*  855 */       rs = psql.executeQuery();
/*      */ 
/*  857 */       HashMap results = new HashMap();
/*      */ 
/*  859 */       while (rs.next())
/*      */       {
/*  861 */         results.put(Long.valueOf(rs.getLong("catId")), Long.valueOf(rs.getLong("AssetViews")));
/*      */       }
/*      */ 
/*  864 */       psql.close();
/*      */ 
/*  866 */       FlatCategoryList flatCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L);
/*      */ 
/*  868 */       vecCategories = flatCategoryList.getCategories();
/*      */ 
/*  870 */       Iterator itCategories = vecCategories.iterator();
/*      */ 
/*  872 */       while (itCategories.hasNext())
/*      */       {
/*  874 */         Category nextCategory = (Category)itCategories.next();
/*  875 */         nextCategory.setNumViews(0L);
/*  876 */         if (results.containsKey(Long.valueOf(nextCategory.getId())))
/*      */         {
/*  879 */           nextCategory.setNumViews(((Long)results.get(Long.valueOf(nextCategory.getId()))).longValue());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  887 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  891 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  895 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  899 */       this.m_logger.error("SQL Exception whilst getting asset view report by user: " + e);
/*  900 */       throw new Bn2Exception("SQL Exception whilst getting asset view report by user: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  905 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  909 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  913 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  923 */       con = transaction.getConnection();
/*      */ 
/*  925 */       sSQL = "SELECT COUNT(AssetId) AssetViews FROM AssetView av WHERE av.Time >= ? AND av.Time <= ? ";
/*      */ 
/*  933 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  935 */       int iCol = 1;
/*  936 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtStartDate);
/*  937 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtEndDate);
/*      */ 
/*  939 */       rs = psql.executeQuery();
/*      */ 
/*  942 */       if (rs.next())
/*      */       {
/*  944 */         totalsReport.setTotalTimes(rs.getLong("AssetViews"));
/*      */       }
/*      */ 
/*  947 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  952 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  956 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  960 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  964 */       this.m_logger.error("SQL Exception whilst getting asset view report by user: " + e);
/*  965 */       throw new Bn2Exception("SQL Exception whilst getting asset view report by user: " + e);
/*      */     }
/*      */     finally
/*      */     {
/*  970 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  974 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  978 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  983 */     totalsReport.setAssetLines(vecCategories);
/*      */ 
/*  985 */     return totalsReport;
/*      */   }
/*      */ 
/*      */   public TotalsReport getAccessLevelDownloadReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/*  998 */     TotalsReport totalsReport = new TotalsReport();
/*      */ 
/* 1000 */     Connection con = null;
/* 1001 */     PreparedStatement psql = null;
/* 1002 */     String sSQL = null;
/* 1003 */     ResultSet rs = null;
/* 1004 */     DBTransaction transaction = a_dbTransaction;
/* 1005 */     Vector vecCategories = null;
/*      */ 
/* 1007 */     if (transaction == null)
/*      */     {
/* 1009 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/* 1012 */     HashMap results = new HashMap();
/*      */     try
/*      */     {
/* 1016 */       con = transaction.getConnection();
/*      */ 
/* 1018 */       sSQL = "SELECT cat.Id catId, COUNT(au.AssetId) AssetDownloads FROM AssetUse au, CM_Category cat, CM_ItemInCategory ic WHERE cat.CategoryTypeId=? AND ic.CategoryId=cat.Id AND ic.ItemId = au.AssetId AND au.TimeOfDownload >= ? AND au.TimeOfDownload <= ? GROUP BY cat.id, cat.name";
/*      */ 
/* 1031 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1033 */       int iCol = 1;
/* 1034 */       psql.setLong(iCol++, 2L);
/* 1035 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtStartDate);
/* 1036 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtEndDate);
/*      */ 
/* 1038 */       rs = psql.executeQuery();
/*      */ 
/* 1040 */       while (rs.next())
/*      */       {
/* 1042 */         results.put(Long.valueOf(rs.getLong("catId")), Long.valueOf(rs.getLong("AssetDownloads")));
/*      */       }
/*      */ 
/* 1045 */       psql.close();
/*      */ 
/* 1047 */       FlatCategoryList flatCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L);
/*      */ 
/* 1049 */       vecCategories = flatCategoryList.getCategories();
/*      */ 
/* 1051 */       Iterator itCategories = vecCategories.iterator();
/*      */ 
/* 1053 */       while (itCategories.hasNext())
/*      */       {
/* 1055 */         Category nextCategory = (Category)itCategories.next();
/*      */ 
/* 1057 */         if (results.containsKey(Long.valueOf(nextCategory.getId())))
/*      */         {
/* 1059 */           nextCategory.setNumDownloads(0L);
/*      */ 
/* 1061 */           nextCategory.setNumDownloads(((Long)results.get(Long.valueOf(nextCategory.getId()))).longValue());
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1068 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1072 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1076 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1080 */       this.m_logger.error("SQL Exception whilst getting asset view report by user: " + e);
/* 1081 */       throw new Bn2Exception("SQL Exception whilst getting asset view report by user: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1086 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1090 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1094 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1103 */       con = transaction.getConnection();
/*      */ 
/* 1105 */       sSQL = "SELECT COUNT(au.AssetId) AssetDownloads FROM AssetUse au WHERE au.TimeOfDownload >= ? AND au.TimeOfDownload <= ? ";
/*      */ 
/* 1113 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1115 */       int iCol = 1;
/* 1116 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtStartDate);
/* 1117 */       DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtEndDate);
/*      */ 
/* 1119 */       rs = psql.executeQuery();
/*      */ 
/* 1121 */       if (rs.next())
/*      */       {
/* 1123 */         totalsReport.setTotalTimes(rs.getLong("AssetDownloads"));
/*      */       }
/*      */ 
/* 1126 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1130 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1134 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1138 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1142 */       this.m_logger.error("SQL Exception whilst getting asset view report by user: " + e);
/* 1143 */       throw new Bn2Exception("SQL Exception whilst getting asset view report by user: " + e);
/*      */     }
/*      */     finally
/*      */     {
/* 1148 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1152 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1156 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1161 */     totalsReport.setAssetLines(vecCategories);
/*      */ 
/* 1163 */     return totalsReport;
/*      */   }
/*      */ 
/*      */   public Vector getAssetViewReportByGroup(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/* 1189 */     Vector vResults = null;
/* 1190 */     Connection con = null;
/* 1191 */     PreparedStatement psql = null;
/* 1192 */     String sSQL = null;
/* 1193 */     ResultSet rs = null;
/* 1194 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1196 */     if (transaction == null)
/*      */     {
/* 1198 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1203 */       con = transaction.getConnection();
/*      */ 
/* 1205 */       sSQL = "SELECT ass.Id assId, ass.FileLocation, ass.OriginalFilename, ass.ThumbnailFileLocation, ug.Id ugId, ug.Name, COUNT(av.AssetId) AssetViews FROM Asset ass INNER JOIN AssetView av ON av.AssetId = ass.Id LEFT JOIN AssetBankUser u ON u.Id = av.UserId LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON ug.Id = uig.UserGroupId WHERE av.Time >= ? AND av.Time <= ? GROUP BY ass.Id, ass.FileLocation, ass.OriginalFilename, ass.ThumbnailFileLocation, ug.Id, ug.Name ORDER BY ass.Id, ug.Name ";
/*      */ 
/* 1222 */       psql = con.prepareStatement(sSQL);
/* 1223 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/* 1224 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/*      */ 
/* 1226 */       rs = psql.executeQuery();
/*      */ 
/* 1228 */       vResults = new Vector();
/* 1229 */       long lAssetId = 0L;
/* 1230 */       ViewReportAsset asset = null;
/*      */ 
/* 1232 */       while (rs.next())
/*      */       {
/* 1234 */         if (lAssetId != rs.getLong("assId"))
/*      */         {
/* 1236 */           lAssetId = rs.getLong("assId");
/*      */ 
/* 1238 */           asset = new ViewReportAsset();
/* 1239 */           asset.setId(lAssetId);
/* 1240 */           asset.setOriginalFilename(rs.getString("OriginalFilename"));
/*      */ 
/* 1242 */           String sThumbnailPath = AssetUtil.getThumbnailFileLocation(a_dbTransaction, this.m_assetManager, rs.getString("ThumbnailFileLocation"), rs.getString("OriginalFilename"));
/*      */ 
/* 1245 */           asset.setThumbnailPath(sThumbnailPath);
/* 1246 */           vResults.add(asset);
/*      */         }
/*      */ 
/* 1249 */         ReportGroup group = new ReportGroup();
/* 1250 */         group.setGroupId(rs.getLong("ugId"));
/* 1251 */         group.setGroupName(rs.getString("Name"));
/* 1252 */         group.setCount(rs.getInt("AssetViews"));
/*      */ 
/* 1254 */         asset.setCount(asset.getCount() + group.getCount());
/* 1255 */         asset.getGroups().add(group);
/*      */       }
/*      */ 
/* 1258 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1262 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1266 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1270 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1274 */       this.m_logger.error("SQL Exception whilst getting asset view report by group: " + e);
/* 1275 */       throw new Bn2Exception("SQL Exception whilst getting asset view report by group: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1280 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1284 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1288 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1293 */     return vResults;
/*      */   }
/*      */ 
/*      */   public Vector getAssetViewReportByUser(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/* 1317 */     Vector vResults = null;
/* 1318 */     Connection con = null;
/* 1319 */     PreparedStatement psql = null;
/* 1320 */     String sSQL = null;
/* 1321 */     ResultSet rs = null;
/* 1322 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1324 */     if (transaction == null)
/*      */     {
/* 1326 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1331 */       con = transaction.getConnection();
/*      */ 
/* 1333 */       sSQL = "SELECT ass.Id assId, ass.FileLocation, ass.OriginalFilename, ass.ThumbnailFileLocation, u.Id userId, u.Username, u.Forename, u.Surname, COUNT(av.AssetId) AssetViews FROM Asset ass INNER JOIN AssetView av ON av.AssetId = ass.Id LEFT JOIN AssetBankUser u ON u.Id = av.UserId WHERE av.Time >= ? AND av.Time <= ? GROUP BY ass.Id, ass.FileLocation, ass.OriginalFilename, ass.ThumbnailFileLocation, u.Id, u.Username, u.Forename, u.Surname ORDER BY ass.Id, u.Surname ";
/*      */ 
/* 1350 */       psql = con.prepareStatement(sSQL);
/* 1351 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/* 1352 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/*      */ 
/* 1354 */       rs = psql.executeQuery();
/*      */ 
/* 1356 */       vResults = new Vector();
/* 1357 */       long lAssetId = 0L;
/* 1358 */       ViewReportAsset asset = null;
/*      */ 
/* 1360 */       while (rs.next())
/*      */       {
/* 1362 */         if (lAssetId != rs.getLong("assId"))
/*      */         {
/* 1364 */           lAssetId = rs.getLong("assId");
/*      */ 
/* 1366 */           asset = new ViewReportAsset();
/* 1367 */           asset.setId(lAssetId);
/* 1368 */           asset.setOriginalFilename(rs.getString("OriginalFilename"));
/*      */ 
/* 1370 */           String sThumbnailPath = AssetUtil.getThumbnailFileLocation(a_dbTransaction, this.m_assetManager, rs.getString("ThumbnailFileLocation"), rs.getString("OriginalFilename"));
/*      */ 
/* 1373 */           asset.setThumbnailPath(sThumbnailPath);
/* 1374 */           vResults.add(asset);
/*      */         }
/*      */ 
/* 1377 */         ReportUser user = new ReportUser();
/* 1378 */         user.setUserId(rs.getLong("userId"));
/* 1379 */         user.setUsername(rs.getString("Username"));
/* 1380 */         user.setForename(rs.getString("Forename"));
/* 1381 */         user.setSurname(rs.getString("Surname"));
/* 1382 */         user.setCount(rs.getInt("AssetViews"));
/*      */ 
/* 1384 */         asset.setCount(asset.getCount() + user.getCount());
/* 1385 */         asset.getUsers().add(user);
/*      */       }
/*      */ 
/* 1388 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1392 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1396 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1400 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1404 */       this.m_logger.error("SQL Exception whilst getting asset view report by user: " + e);
/* 1405 */       throw new Bn2Exception("SQL Exception whilst getting asset view report by user: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1410 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1414 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1418 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1423 */     return vResults;
/*      */   }
/*      */ 
/*      */   public Vector getUserGroupUploadReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, boolean a_bGetGroupInfo)
/*      */     throws Bn2Exception
/*      */   {
/* 1447 */     Vector vResults = null;
/* 1448 */     Connection con = null;
/* 1449 */     PreparedStatement psql = null;
/* 1450 */     String sSQL = null;
/* 1451 */     ResultSet rs = null;
/* 1452 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1454 */     if (transaction == null)
/*      */     {
/* 1456 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1461 */       con = transaction.getConnection();
/*      */ 
/* 1463 */       if (a_bGetGroupInfo)
/*      */       {
/* 1465 */         sSQL = "SELECT COUNT(ass.id) countAss, ug.Id ugId, u.Id userId, u.Username, u.Forename, u.Surname, ug.Name FROM Asset ass INNER JOIN AssetBankUser u ON u.Id = ass.AddedByUserId LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON ug.Id = uig.UserGroupId WHERE DateAdded >= ? AND DateAdded <= ? GROUP BY ug.Id, ug.Name, u.Id, u.Username, u.Forename, u.Surname ORDER BY ug.Name, u.Surname";
/*      */       }
/*      */       else
/*      */       {
/* 1469 */         sSQL = "SELECT COUNT(ass.id) countAss, u.Id userId, u.Username, u.Forename, u.Surname FROM Asset ass INNER JOIN AssetBankUser u ON u.Id = ass.AddedByUserId WHERE DateAdded >= ? AND DateAdded <= ? GROUP BY u.Id, u.Username, u.Forename, u.Surname ORDER BY u.Surname";
/*      */       }
/*      */ 
/* 1472 */       psql = con.prepareStatement(sSQL);
/* 1473 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/* 1474 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/*      */ 
/* 1476 */       rs = psql.executeQuery();
/*      */ 
/* 1478 */       long lGroupId = 0L;
/* 1479 */       ReportGroup group = new ReportGroup();
/*      */ 
/* 1481 */       vResults = new Vector();
/*      */ 
/* 1483 */       while (rs.next())
/*      */       {
/* 1485 */         ReportUser user = new ReportUser();
/* 1486 */         user.setUserId(rs.getLong("userId"));
/* 1487 */         user.setUsername(rs.getString("Username"));
/* 1488 */         user.setForename(rs.getString("Forename"));
/* 1489 */         user.setCount(rs.getInt("countAss"));
/* 1490 */         user.setSurname(rs.getString("Surname"));
/*      */ 
/* 1492 */         if (a_bGetGroupInfo)
/*      */         {
/* 1494 */           if (lGroupId != rs.getLong("ugId"))
/*      */           {
/* 1496 */             lGroupId = rs.getLong("ugId");
/*      */ 
/* 1498 */             group = new ReportGroup();
/* 1499 */             group.setGroupId(lGroupId);
/* 1500 */             group.setGroupName(rs.getString("Name"));
/* 1501 */             group.setCount(0);
/*      */ 
/* 1503 */             vResults.add(group);
/*      */           }
/*      */ 
/* 1506 */           group.getUsers().add(user);
/* 1507 */           group.setCount(group.getCount() + user.getCount());
/*      */         }
/*      */         else
/*      */         {
/* 1511 */           vResults.add(user);
/*      */         }
/*      */       }
/*      */ 
/* 1515 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1519 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1523 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1527 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1531 */       this.m_logger.error("SQL Exception whilst getting asset view report : " + e);
/* 1532 */       throw new Bn2Exception("SQL Exception whilst getting asset view report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1537 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1541 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1545 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1550 */     return vResults;
/*      */   }
/*      */ 
/*      */   public Vector getUserGroupDownloadReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, boolean a_bGetGroupInfo)
/*      */     throws Bn2Exception
/*      */   {
/* 1576 */     Vector vResults = null;
/* 1577 */     Connection con = null;
/* 1578 */     PreparedStatement psql = null;
/* 1579 */     String sSQL = null;
/* 1580 */     ResultSet rs = null;
/* 1581 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1583 */     if (transaction == null)
/*      */     {
/* 1585 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1590 */       con = transaction.getConnection();
/*      */ 
/* 1592 */       if (a_bGetGroupInfo)
/*      */       {
/* 1594 */         sSQL = "SELECT COUNT(au.AssetId) countAss, ug.Id ugId, u.Id userId, u.Username, u.Forename, u.Surname, ug.Name FROM AssetUse au INNER JOIN Asset ass ON ass.Id = au.AssetId LEFT JOIN AssetBankUser u ON u.Id = au.UserId LEFT JOIN UserInGroup uig ON uig.UserId = u.Id LEFT JOIN UserGroup ug ON ug.Id = uig.UserGroupId WHERE TimeOfDownload >= ? AND TimeOfDownload <= ? GROUP BY ug.Id, ug.Name, u.Id, u.Username, u.Forename, u.Surname ORDER BY ug.Name, u.Surname";
/*      */       }
/*      */       else
/*      */       {
/* 1598 */         sSQL = "SELECT COUNT(au.AssetId) countAss, u.Id userId, u.Username, u.Forename, u.Surname FROM AssetUse au INNER JOIN Asset ass ON ass.Id = au.AssetId LEFT JOIN AssetBankUser u ON u.Id = au.UserId WHERE TimeOfDownload >= ? AND TimeOfDownload <= ? GROUP BY u.Id, u.Username, u.Forename, u.Surname ORDER BY u.Surname";
/*      */       }
/*      */ 
/* 1601 */       psql = con.prepareStatement(sSQL);
/* 1602 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/* 1603 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/*      */ 
/* 1605 */       rs = psql.executeQuery();
/*      */ 
/* 1607 */       long lGroupId = 0L;
/* 1608 */       ReportGroup group = new ReportGroup();
/*      */ 
/* 1610 */       vResults = new Vector();
/*      */ 
/* 1612 */       while (rs.next())
/*      */       {
/* 1614 */         ReportUser user = new ReportUser();
/* 1615 */         user.setUserId(rs.getLong("userId"));
/* 1616 */         user.setUsername(rs.getString("Username"));
/* 1617 */         user.setForename(rs.getString("Forename"));
/* 1618 */         user.setCount(rs.getInt("countAss"));
/* 1619 */         user.setSurname(rs.getString("Surname"));
/*      */ 
/* 1621 */         if (a_bGetGroupInfo)
/*      */         {
/* 1623 */           if (lGroupId != rs.getLong("ugId"))
/*      */           {
/* 1625 */             lGroupId = rs.getLong("ugId");
/*      */ 
/* 1627 */             group = new ReportGroup();
/* 1628 */             group.setGroupId(lGroupId);
/* 1629 */             group.setGroupName(rs.getString("Name"));
/* 1630 */             group.setCount(0);
/*      */ 
/* 1632 */             vResults.add(group);
/*      */           }
/*      */ 
/* 1635 */           group.getUsers().add(user);
/* 1636 */           group.setCount(group.getCount() + user.getCount());
/*      */         }
/*      */         else
/*      */         {
/* 1640 */           vResults.add(user);
/*      */         }
/*      */       }
/*      */ 
/* 1644 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1648 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1652 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1656 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1660 */       this.m_logger.error("SQL Exception whilst getting asset view report : " + e);
/* 1661 */       throw new Bn2Exception("SQL Exception whilst getting asset view report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1666 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1670 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1674 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1679 */     return vResults;
/*      */   }
/*      */ 
/*      */   public Vector getReasonForDownloadReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, String a_sUsername)
/*      */     throws Bn2Exception
/*      */   {
/* 1707 */     Connection con = null;
/* 1708 */     PreparedStatement psql = null;
/* 1709 */     String sSQL = null;
/* 1710 */     ResultSet rs = null;
/* 1711 */     DBTransaction transaction = a_dbTransaction;
/* 1712 */     Vector vecResults = null;
/*      */ 
/* 1714 */     if (transaction == null)
/*      */     {
/* 1716 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1721 */       con = transaction.getConnection();
/*      */ 
/* 1723 */       sSQL = "SELECT a.Id aId, a.FileLocation, a.OriginalFilename, a.ThumbnailFileLocation, au.Id as AssetUseId, au.TimeOfDownload, ut.Description, au.OtherDescription, au.UserId, u.Title uTitle, u.Forename, u.Surname, u.Username, u.Organisation, c.Name countryName, sdut.OriginalDescription as SecondaryUsageType FROM AssetUse au LEFT JOIN Asset a ON au.AssetId=a.Id LEFT JOIN UsageType ut ON au.UsageTypeId=ut.Id LEFT JOIN AssetBankUser u ON au.UserId=u.Id LEFT JOIN Address ad ON u.AddressId=ad.Id LEFT JOIN Country c ON ad.CountryId=c.Id LEFT OUTER JOIN SecondaryDownloadUsageType sdut ON sdut.AssetUseId = au.Id WHERE TimeOfDownload >= ? AND TimeOfDownload <= ?";
/*      */ 
/* 1736 */       if ((a_sUsername != null) && (a_sUsername.length() > 0))
/*      */       {
/* 1738 */         sSQL = sSQL + " AND Username LIKE ?";
/*      */       }
/*      */ 
/* 1741 */       sSQL = sSQL + " ORDER BY au.Id";
/*      */ 
/* 1743 */       psql = con.prepareStatement(sSQL);
/* 1744 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/* 1745 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/*      */ 
/* 1747 */       if ((a_sUsername != null) && (a_sUsername.length() > 0))
/*      */       {
/* 1749 */         psql.setString(3, a_sUsername + "%");
/*      */       }
/*      */ 
/* 1752 */       rs = psql.executeQuery();
/* 1753 */       ReasonForDownloadResult reportResult = null;
/*      */ 
/* 1755 */       while (rs.next())
/*      */       {
/* 1757 */         if (vecResults == null)
/*      */         {
/* 1759 */           vecResults = new Vector();
/*      */         }
/*      */ 
/* 1763 */         if ((reportResult != null) && (reportResult.getAssetUseId() == rs.getLong("AssetUseId")))
/*      */         {
/* 1765 */           reportResult.getSecondaryUsageTypes().add(rs.getString("SecondaryUsageType")); continue;
/*      */         }
/*      */ 
/* 1770 */         if (reportResult != null)
/*      */         {
/* 1772 */           vecResults.add(reportResult);
/*      */         }
/*      */ 
/* 1776 */         String sReason = rs.getString("Description");
/* 1777 */         String sReasonDetails = rs.getString("OtherDescription");
/* 1778 */         if (StringUtil.stringIsPopulated(sReasonDetails))
/*      */         {
/* 1780 */           sReason = sReason + ": " + sReasonDetails;
/*      */         }
/*      */ 
/* 1783 */         reportResult = new ReasonForDownloadResult();
/*      */ 
/* 1785 */         reportResult.setAssetUseId(rs.getLong("AssetUseId"));
/* 1786 */         long lAssetId = rs.getLong("aId");
/* 1787 */         reportResult.setAssetId(lAssetId);
/* 1788 */         reportResult.setDownloadTime(rs.getTimestamp("TimeOfDownload"));
/* 1789 */         reportResult.setReasonForDownload(sReason);
/* 1790 */         reportResult.setUserId(rs.getLong("UserId"));
/* 1791 */         reportResult.setUsername(rs.getString("Username"));
/* 1792 */         reportResult.setCountryName(rs.getString("countryName"));
/* 1793 */         reportResult.setOrganisation(rs.getString("Organisation"));
/*      */ 
/* 1795 */         String sFullname = "";
/*      */ 
/* 1797 */         if (rs.getString("uTitle") != null)
/*      */         {
/* 1799 */           sFullname = rs.getString("uTitle") + " ";
/*      */         }
/*      */ 
/* 1802 */         if (rs.getString("Forename") != null)
/*      */         {
/* 1804 */           sFullname = sFullname + rs.getString("Forename") + " ";
/*      */         }
/*      */ 
/* 1807 */         if (rs.getString("Surname") != null)
/*      */         {
/* 1809 */           sFullname = sFullname + rs.getString("Surname") + " ";
/*      */         }
/*      */ 
/* 1812 */         reportResult.setUserFullname(sFullname);
/* 1813 */         reportResult.setOriginalFilename(rs.getString("OriginalFilename"));
/*      */ 
/* 1815 */         String sThumbnailPath = AssetUtil.getThumbnailFileLocation(a_dbTransaction, this.m_assetManager, rs.getString("ThumbnailFileLocation"), rs.getString("OriginalFilename"));
/*      */ 
/* 1818 */         reportResult.setThumbnailPath(sThumbnailPath);
/*      */ 
/* 1821 */         if (StringUtil.stringIsPopulated(rs.getString("SecondaryUsageType")))
/*      */         {
/* 1823 */           reportResult.getSecondaryUsageTypes().add(rs.getString("SecondaryUsageType"));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1831 */       if (reportResult != null)
/*      */       {
/* 1833 */         vecResults.add(reportResult);
/*      */       }
/*      */ 
/* 1836 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1840 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1844 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1848 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1852 */       this.m_logger.error("SQL Exception in getReasonForDownloadReport : " + e);
/* 1853 */       throw new Bn2Exception("SQL Exception in getReasonForDownloadReport : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1858 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1862 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1866 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1871 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public Vector getDownloadsByUploaderReport(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/* 1894 */     Vector vResults = null;
/* 1895 */     Connection con = null;
/* 1896 */     PreparedStatement psql = null;
/* 1897 */     String sSQL = null;
/* 1898 */     ResultSet rs = null;
/* 1899 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1901 */     if (transaction == null)
/*      */     {
/* 1903 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1908 */       con = transaction.getConnection();
/*      */ 
/* 1910 */       sSQL = "SELECT count(sub1.assetid) dcount, sub1.uploaderid, sub1.uploaderusername, sub1.uploaderfname, sub1.uploadersname FROM (SELECT DISTINCT ass.id assetid, uploader.Id uploaderid, uploader.Username uploaderusername, uploader.Forename uploaderfname, uploader.Surname uploadersname, downloader.Id FROM Asset ass INNER JOIN AssetBankUser uploader ON uploader.Id = ass.AddedByUserId INNER JOIN AssetUse au ON au.AssetId = ass.Id INNER JOIN AssetBankUser downloader ON downloader.Id = au.UserId WHERE au.TimeOfDownload >= ? AND au.TimeOfDownload <= ? ) sub1 GROUP BY sub1.uploaderid, sub1.uploaderusername, sub1.uploaderfname, sub1.uploadersname ORDER BY sub1.uploadersname";
/*      */ 
/* 1912 */       psql = con.prepareStatement(sSQL);
/* 1913 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/* 1914 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/*      */ 
/* 1916 */       rs = psql.executeQuery();
/*      */ 
/* 1918 */       vResults = new Vector();
/*      */ 
/* 1920 */       while (rs.next())
/*      */       {
/* 1922 */         ReportUser user = new ReportUser();
/* 1923 */         user.setUserId(rs.getLong("uploaderid"));
/* 1924 */         user.setUsername(rs.getString("uploaderusername"));
/* 1925 */         user.setForename(rs.getString("uploaderfname"));
/* 1926 */         user.setCount(rs.getInt("dcount"));
/* 1927 */         user.setSurname(rs.getString("uploadersname"));
/*      */ 
/* 1929 */         vResults.add(user);
/*      */       }
/*      */ 
/* 1932 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1936 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1940 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1944 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1948 */       this.m_logger.error("SQL Exception whilst getting asset view report : " + e);
/* 1949 */       throw new Bn2Exception("SQL Exception whilst getting asset view report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1954 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1958 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1962 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1967 */     return vResults;
/*      */   }
/*      */ 
/*      */   public String createAssetViewReportFile(Vector a_vData, Date a_dtStartDate, Date a_dtEndDate, long a_iTotalTimes, long a_iTotalAssets, boolean a_bShowGroups, boolean a_bShowUsers)
/*      */     throws Bn2Exception
/*      */   {
/* 1985 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*      */ 
/* 1988 */     Writer writer = null;
/* 1989 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/* 1993 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*      */ 
/* 1995 */       writer.append("Image View Report\n");
/*      */ 
/* 1997 */       writer.append("Report date range: " + format.format(a_dtStartDate) + " to " + format.format(a_dtEndDate) + "\n");
/* 1998 */       if (a_iTotalTimes > 0L)
/*      */       {
/* 2000 */         writer.append("Total number of views in the selected period: " + a_iTotalTimes + "\n");
/*      */       }
/*      */ 
/* 2003 */       if (a_iTotalAssets > 0L)
/*      */       {
/* 2005 */         writer.append("Total number of different assets viewed in the selected period: " + a_iTotalAssets + "\n");
/*      */       }
/*      */ 
/* 2008 */       writer.append("Below is a list of assets that have been viewed in the selected period, and the number of times they have been viewed.\n");
/*      */ 
/* 2010 */       String sAssetName = StringUtils.capitalize(this.m_listManager.getListItem("item", LanguageConstants.k_defaultLanguage).getBody());
/*      */ 
/* 2012 */       writer.append(sAssetName + " ID\t");
/* 2013 */       writer.append("Original filename\t");
/* 2014 */       writer.append("Number of views\n");
/*      */ 
/* 2016 */       for (int i = 0; i < a_vData.size(); i++)
/*      */       {
/* 2018 */         ViewReportAsset asset = (ViewReportAsset)a_vData.get(i);
/* 2019 */         writer.append(asset.getId() + "\t");
/* 2020 */         writer.append(asset.getOriginalFilename() + "\t");
/* 2021 */         if (!a_bShowGroups)
/*      */         {
/* 2023 */           writer.append(String.valueOf(asset.getCount()));
/*      */         }
/* 2025 */         writer.append("\n");
/*      */ 
/* 2027 */         if (a_bShowGroups)
/*      */         {
/* 2029 */           for (int j = 0; j < asset.getGroups().size(); j++)
/*      */           {
/* 2031 */             ReportGroup group = (ReportGroup)asset.getGroups().get(j);
/* 2032 */             writer.append("\t" + (StringUtils.isEmpty(group.getGroupName()) ? "Unassigned users (admin/anonymous)" : group.getGroupName()) + "\t\t");
/* 2033 */             writer.append(String.valueOf(group.getCount()) + "\n");
/*      */           }
/*      */         } else {
/* 2036 */           if (!a_bShowUsers)
/*      */             continue;
/* 2038 */           for (int j = 0; j < asset.getUsers().size(); j++)
/*      */           {
/* 2040 */             ReportUser user = (ReportUser)asset.getUsers().get(j);
/* 2041 */             writer.append("\t" + (user.getUserId() <= 0L ? "(Anonymous)" : new StringBuilder().append(user.getForename()).append(" ").append(user.getSurname()).append(" (").append(user.getUsername()).append(")").toString()) + "\t\t");
/* 2042 */             writer.append(String.valueOf(user.getCount()) + "\n");
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2049 */       this.m_logger.error("UsageManager.createAssetViewReportFile() : IOException whilst creating report file : " + ioe);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2055 */         writer.flush();
/* 2056 */         writer.close();
/*      */       }
/*      */       catch (IOException ioe2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2064 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public String createAssetUploadReportFile(Vector a_vData, Date a_dtStartDate, Date a_dtEndDate, boolean a_bShowGroups, boolean a_bShowUsers)
/*      */     throws Bn2Exception
/*      */   {
/* 2080 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*      */ 
/* 2083 */     Writer writer = null;
/* 2084 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/* 2088 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*      */ 
/* 2090 */       writer.append("Image Upload Report\n");
/*      */ 
/* 2092 */       writer.append("Report date range: " + format.format(a_dtStartDate) + " to " + format.format(a_dtEndDate) + "\n");
/*      */ 
/* 2094 */       if (a_bShowGroups)
/*      */       {
/* 2096 */         writer.append("Group\t");
/*      */       }
/* 2098 */       writer.append("User\t");
/* 2099 */       writer.append("Number of Uploads\n");
/*      */ 
/* 2101 */       for (int i = 0; i < a_vData.size(); i++)
/*      */       {
/* 2103 */         if (!a_bShowGroups)
/*      */         {
/* 2105 */           ReportUser user = (ReportUser)a_vData.get(i);
/* 2106 */           writer.append(user.getForename() + " " + user.getSurname() + " (" + user.getUsername() + ")\t");
/* 2107 */           writer.append(user.getCount() + "\n");
/*      */         }
/*      */         else
/*      */         {
/* 2111 */           ReportGroup group = (ReportGroup)a_vData.get(i);
/* 2112 */           writer.append((StringUtils.isEmpty(group.getGroupName()) ? "Unassigned users (admin)" : group.getGroupName()) + "\t\t");
/* 2113 */           writer.append(String.valueOf(group.getCount()) + "\n");
/*      */ 
/* 2115 */           if (!a_bShowUsers)
/*      */             continue;
/* 2117 */           for (int j = 0; j < group.getUsers().size(); j++)
/*      */           {
/* 2119 */             ReportUser user = (ReportUser)group.getUsers().get(j);
/* 2120 */             writer.append("\t" + user.getForename() + " " + user.getSurname() + " (" + user.getUsername() + ")\t");
/* 2121 */             writer.append(user.getCount() + "\n");
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2129 */       this.m_logger.error("UsageManager.createAssetUploadReportFile() : IOException whilst creating report file : " + ioe);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2135 */         writer.flush();
/* 2136 */         writer.close();
/*      */       }
/*      */       catch (IOException ioe2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2144 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public String createDownloadsbyUploaderReportFile(Vector a_vData, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/* 2158 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*      */ 
/* 2161 */     Writer writer = null;
/* 2162 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/* 2166 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*      */ 
/* 2168 */       writer.append("Downloads by Uploader Report\n");
/*      */ 
/* 2170 */       writer.append("Report date range: " + format.format(a_dtStartDate) + " to " + format.format(a_dtEndDate) + "\n");
/*      */ 
/* 2172 */       writer.append("User\t");
/* 2173 */       writer.append("Number of Downloads\n");
/*      */ 
/* 2175 */       for (int i = 0; i < a_vData.size(); i++)
/*      */       {
/* 2177 */         ReportUser user = (ReportUser)a_vData.get(i);
/* 2178 */         writer.append(user.getForename() + " " + user.getSurname() + " (" + user.getUsername() + ")\t");
/* 2179 */         writer.append(user.getCount() + "\n");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2185 */       this.m_logger.error("UsageManager.createDownloadsbyUploaderReportFile() : IOException whilst creating report file : " + ioe);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2191 */         writer.flush();
/* 2192 */         writer.close();
/*      */       }
/*      */       catch (IOException ioe2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2200 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public String createUsageReportFile(Vector a_vData, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/* 2214 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*      */ 
/* 2217 */     Writer writer = null;
/* 2218 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/* 2222 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*      */ 
/* 2224 */       writer.append("Usage Report\n");
/*      */ 
/* 2226 */       writer.append("Report date range: " + format.format(a_dtStartDate) + " to " + format.format(a_dtEndDate) + "\n");
/*      */ 
/* 2228 */       writer.append("Usage Type\t");
/* 2229 */       writer.append("Number of Downloads\n");
/*      */ 
/* 2231 */       for (int i = 0; i < a_vData.size(); i++)
/*      */       {
/* 2233 */         UsageReportResult usage = (UsageReportResult)a_vData.get(i);
/* 2234 */         writer.append(usage.getUsageDescription() + "\t");
/* 2235 */         writer.append(usage.getCount() + "\n");
/*      */       }
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2240 */       this.m_logger.error("UsageManager.createUsageReportFile() : IOException whilst creating report file : " + ioe);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2246 */         writer.flush();
/* 2247 */         writer.close();
/*      */       }
/*      */       catch (IOException ioe2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2255 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public String createDownloadCountReportFile(Vector a_vData, Date a_dtStartDate, Date a_dtEndDate, long a_iTotalTimes, long a_iTotalAssets)
/*      */     throws Bn2Exception
/*      */   {
/* 2271 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*      */ 
/* 2274 */     Writer writer = null;
/* 2275 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/* 2279 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*      */ 
/* 2281 */       writer.append("Image Download Report\n");
/*      */ 
/* 2283 */       writer.append("Report date range: " + format.format(a_dtStartDate) + " to " + format.format(a_dtEndDate) + "\n");
/*      */ 
/* 2285 */       writer.append("Total number of downloads in the selected period:" + a_iTotalTimes + "\n");
/* 2286 */       writer.append("Total number of different assets downloaded in the selected period:" + a_iTotalAssets + "\n");
/*      */ 
/* 2288 */       String sAssetName = StringUtils.capitalize(this.m_listManager.getListItem("item", LanguageConstants.k_defaultLanguage).getBody());
/*      */ 
/* 2290 */       writer.append(sAssetName + " ID\t");
/* 2291 */       writer.append("Original filename\t");
/* 2292 */       writer.append("Number of downloads\n");
/*      */ 
/* 2294 */       for (int i = 0; i < a_vData.size(); i++)
/*      */       {
/* 2296 */         DownloadReportResult result = (DownloadReportResult)a_vData.get(i);
/*      */ 
/* 2298 */         writer.append(result.getId() + "\t");
/* 2299 */         writer.append(result.getOriginalFilename() + "\t");
/* 2300 */         writer.append(result.getCount() + "\n");
/*      */       }
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2305 */       this.m_logger.error("UsageManager.createDownloadsbyUploaderReportFile() : IOException whilst creating report file : " + ioe);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2311 */         writer.flush();
/* 2312 */         writer.close();
/*      */       }
/*      */       catch (IOException ioe2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2320 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public String createReasonForDownloadReportFile(Vector a_vData, Date a_dtStartDate, Date a_dtEndDate)
/*      */     throws Bn2Exception
/*      */   {
/* 2334 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*      */ 
/* 2337 */     Writer writer = null;
/* 2338 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/* 2342 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*      */ 
/* 2344 */       writer.append("Reason for Download Report\n");
/*      */ 
/* 2346 */       writer.append("Report date range: " + format.format(a_dtStartDate) + " to " + format.format(a_dtEndDate) + "\n");
/*      */ 
/* 2348 */       String sAssetName = StringUtils.capitalize(this.m_listManager.getListItem("item", LanguageConstants.k_defaultLanguage).getBody());
/*      */ 
/* 2350 */       writer.append(sAssetName + " ID\t");
/* 2351 */       writer.append("Original filename\t");
/* 2352 */       writer.append("Date/Time of Download\t");
/* 2353 */       writer.append("Reason for Download\t");
/* 2354 */       writer.append("User\t");
/* 2355 */       if (AssetBankSettings.getUsersHaveStructuredAddress())
/*      */       {
/* 2357 */         writer.append("Country\t");
/*      */       }
/* 2359 */       writer.append("Organisation\n");
/*      */ 
/* 2361 */       for (int i = 0; i < a_vData.size(); i++)
/*      */       {
/* 2363 */         ReasonForDownloadResult result = (ReasonForDownloadResult)a_vData.get(i);
/*      */ 
/* 2365 */         writer.append(result.getAssetId() + "\t");
/* 2366 */         writer.append(result.getOriginalFilename() + "\t");
/* 2367 */         writer.append(format.format(result.getDownloadTime()) + "\t");
/* 2368 */         writer.append((result.getReasonForDownload() != null ? result.getReasonForDownload() : "") + "\t");
/* 2369 */         writer.append(result.getUserFullname() + "\t");
/* 2370 */         if (AssetBankSettings.getUsersHaveStructuredAddress())
/*      */         {
/* 2372 */           writer.append(result.getCountryName() != null ? result.getCountryName() : "");
/* 2373 */           writer.append("\t");
/*      */         }
/* 2375 */         writer.append(result.getOrganisation() != null ? result.getOrganisation() : "");
/* 2376 */         writer.append("\n");
/*      */       }
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2381 */       this.m_logger.error("UsageManager.createDownloadsbyUploaderReportFile() : IOException whilst creating report file : " + ioe);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2387 */         writer.flush();
/* 2388 */         writer.close();
/*      */       }
/*      */       catch (IOException ioe2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2396 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public String createDownloadsPerUserGroupReportFile(Vector a_vData, Date a_dtStartDate, Date a_dtEndDate, boolean a_bGroupDownloads)
/*      */     throws Bn2Exception
/*      */   {
/* 2411 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*      */ 
/* 2414 */     Writer writer = null;
/* 2415 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/* 2419 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*      */ 
/* 2421 */       writer.append("Image Download Report\n");
/*      */ 
/* 2423 */       writer.append("Report date range: " + format.format(a_dtStartDate) + " to " + format.format(a_dtEndDate) + "\n");
/*      */ 
/* 2425 */       if (a_bGroupDownloads)
/*      */       {
/* 2427 */         writer.append("Group\t");
/*      */       }
/*      */       else
/*      */       {
/* 2431 */         writer.append("User\t");
/*      */       }
/*      */ 
/* 2434 */       writer.append("Number of Downloads\n");
/*      */ 
/* 2436 */       for (int i = 0; i < a_vData.size(); i++)
/*      */       {
/* 2438 */         if (a_bGroupDownloads)
/*      */         {
/* 2440 */           ReportGroup group = (ReportGroup)a_vData.get(i);
/* 2441 */           writer.append((StringUtils.isEmpty(group.getGroupName()) ? "Unassigned users" : group.getGroupName()) + "\t");
/* 2442 */           writer.append(group.getCount() + "\n");
/*      */         }
/*      */         else
/*      */         {
/* 2446 */           ReportUser user = (ReportUser)a_vData.get(i);
/* 2447 */           writer.append(user.getForename() + " " + user.getSurname() + " (" + user.getUsername() + ")\t");
/* 2448 */           writer.append(user.getCount() + "\n");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2454 */       this.m_logger.error("UsageManager.createDownloadsbyUploaderReportFile() : IOException whilst creating report file : " + ioe);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2460 */         writer.flush();
/* 2461 */         writer.close();
/*      */       }
/*      */       catch (IOException ioe2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2469 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public String createDownloadReportFile(Vector a_vData, Date a_dtStartDate, Date a_dtEndDate, boolean a_bGroupDownloads)
/*      */     throws Bn2Exception
/*      */   {
/* 2484 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("asset_report.xls", StoredFileType.TEMP);
/*      */ 
/* 2487 */     Writer writer = null;
/* 2488 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/* 2492 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*      */ 
/* 2494 */       writer.append("Image Download Report\n");
/*      */ 
/* 2496 */       writer.append("Downloads per Image by User\n");
/*      */ 
/* 2498 */       writer.append("Report date range: " + format.format(a_dtStartDate) + " to " + format.format(a_dtEndDate) + "\n");
/*      */ 
/* 2500 */       String sAssetName = StringUtils.capitalize(this.m_listManager.getListItem("item", LanguageConstants.k_defaultLanguage).getBody());
/*      */ 
/* 2502 */       writer.append(sAssetName + " ID\t");
/* 2503 */       writer.append("Original Filename\t");
/* 2504 */       writer.append("Number of Downloads\n");
/*      */ 
/* 2506 */       for (int i = 0; i < a_vData.size(); i++)
/*      */       {
/* 2508 */         DownloadReportResult result = (DownloadReportResult)a_vData.get(i);
/* 2509 */         writer.append(result.getId() + "\t");
/* 2510 */         writer.append(result.getOriginalFilename() + "\t");
/* 2511 */         writer.append(result.getCount() + "\n");
/*      */ 
/* 2513 */         if (!a_bGroupDownloads)
/*      */         {
/* 2515 */           for (int j = 0; j < result.getUserUses().size(); j++)
/*      */           {
/* 2517 */             DownloadReportUserUsage usage = (DownloadReportUserUsage)result.getUserUses().get(j);
/* 2518 */             writer.append("\t" + usage.getUsername() + "\t\t");
/* 2519 */             writer.append(usage.getCount() + "\n");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2524 */           for (int j = 0; j < result.getGroupUses().size(); j++)
/*      */           {
/* 2526 */             DownloadReportGroupUsage usage = (DownloadReportGroupUsage)result.getGroupUses().get(j);
/* 2527 */             writer.append("\t" + usage.getGroupName() + "\t\t");
/* 2528 */             writer.append(usage.getCount() + "\n");
/* 2529 */             for (int k = 0; k < usage.getUserUses().size(); k++)
/*      */             {
/* 2531 */               DownloadReportUserUsage user = (DownloadReportUserUsage)usage.getUserUses().get(k);
/* 2532 */               writer.append("\t\t" + user.getUsername() + "\t");
/* 2533 */               writer.append(user.getCount() + "\n");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2541 */       this.m_logger.error("UsageManager.createDownloadReportFile() : IOException whilst creating report file : " + ioe);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2547 */         writer.flush();
/* 2548 */         writer.close();
/*      */       }
/*      */       catch (IOException ioe2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2556 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public String createReport(int a_lReportType, Date a_dtStartDate, Date a_dtEndDate, boolean a_bSortAscending, String a_sUsername)
/*      */     throws Bn2Exception
/*      */   {
/* 2581 */     Vector vResults = null;
/* 2582 */     String sFilename = null;
/*      */ 
/* 2584 */     switch (a_lReportType)
/*      */     {
/*      */     case 1:
/* 2588 */       vResults = getUsageTypeReport(null, a_dtStartDate, a_dtEndDate);
/* 2589 */       sortResults(vResults, a_bSortAscending);
/* 2590 */       if (vResults.size() <= 0)
/*      */         break;
/* 2592 */       sFilename = createUsageReportFile(vResults, a_dtStartDate, a_dtEndDate); break;
/*      */     case 14:
/* 2600 */       TotalsReport report = getAssetViewReport(null, a_dtStartDate, a_dtEndDate);
/*      */ 
/* 2602 */       vResults = report.getAssetLines();
/* 2603 */       sortResults(vResults, a_bSortAscending);
/* 2604 */       if (vResults.size() <= 0)
/*      */         break;
/* 2606 */       sFilename = createAssetViewReportFile(vResults, a_dtStartDate, a_dtEndDate, report.getTotalTimes(), report.getTotalAssets(), false, false); break;
/*      */     case 2:
/* 2615 */       vResults = getAssetViewReportByGroup(null, a_dtStartDate, a_dtEndDate);
/*      */ 
/* 2617 */       sortResults(vResults, a_bSortAscending);
/* 2618 */       if (vResults.size() <= 0)
/*      */         break;
/* 2620 */       sFilename = createAssetViewReportFile(vResults, a_dtStartDate, a_dtEndDate, 0L, 0L, true, false); break;
/*      */     case 3:
/* 2628 */       vResults = getAssetViewReportByUser(null, a_dtStartDate, a_dtEndDate);
/*      */ 
/* 2630 */       sortResults(vResults, a_bSortAscending);
/* 2631 */       if (vResults.size() <= 0)
/*      */         break;
/* 2633 */       sFilename = createAssetViewReportFile(vResults, a_dtStartDate, a_dtEndDate, 0L, 0L, false, true); break;
/*      */     case 4:
/*      */     case 6:
/* 2642 */       vResults = getUserGroupUploadReport(null, a_dtStartDate, a_dtEndDate, true);
/*      */ 
/* 2644 */       sortResults(vResults, a_bSortAscending);
/* 2645 */       if (vResults.size() <= 0)
/*      */         break;
/* 2647 */       sFilename = createAssetUploadReportFile(vResults, a_dtStartDate, a_dtEndDate, true, a_lReportType == 6); break;
/*      */     case 5:
/* 2656 */       vResults = getUserGroupUploadReport(null, a_dtStartDate, a_dtEndDate, false);
/*      */ 
/* 2658 */       sortResults(vResults, a_bSortAscending);
/* 2659 */       if (vResults.size() <= 0)
/*      */         break;
/* 2661 */       sFilename = createAssetUploadReportFile(vResults, a_dtStartDate, a_dtEndDate, false, true); break;
/*      */     case 13:
/* 2669 */       vResults = getDownloadsByUploaderReport(null, a_dtStartDate, a_dtEndDate);
/*      */ 
/* 2671 */       sortResults(vResults, a_bSortAscending);
/* 2672 */       if (vResults.size() <= 0)
/*      */         break;
/* 2674 */       sFilename = createDownloadsbyUploaderReportFile(vResults, a_dtStartDate, a_dtEndDate); break;
/*      */     case 7:
/* 2682 */       report = getAssetDownloadCount(null, a_dtStartDate, a_dtEndDate);
/*      */ 
/* 2684 */       vResults = report.getAssetLines();
/* 2685 */       sortResults(vResults, a_bSortAscending);
/* 2686 */       if (vResults.size() <= 0)
/*      */         break;
/* 2688 */       sFilename = createDownloadCountReportFile(vResults, a_dtStartDate, a_dtEndDate, report.getTotalTimes(), report.getTotalAssets()); break;
/*      */     case 8:
/*      */     case 9:
/* 2699 */       if (a_lReportType == 9)
/*      */       {
/* 2701 */         vResults = getAssetDownloadReport(null, a_dtStartDate, a_dtEndDate, true);
/*      */ 
/* 2703 */         sortResults(vResults, a_bSortAscending);
/* 2704 */         if (vResults.size() <= 0)
/*      */           break;
/* 2706 */         sFilename = createDownloadReportFile(vResults, a_dtStartDate, a_dtEndDate, true);
/*      */       }
/*      */       else
/*      */       {
/* 2712 */         vResults = getAssetDownloadReport(null, a_dtStartDate, a_dtEndDate, false);
/*      */ 
/* 2714 */         sortResults(vResults, a_bSortAscending);
/* 2715 */         if (vResults.size() <= 0)
/*      */           break;
/* 2717 */         sFilename = createDownloadReportFile(vResults, a_dtStartDate, a_dtEndDate, false); } break;
/*      */     case 10:
/*      */     case 11:
/* 2728 */       vResults = getUserGroupDownloadReport(null, a_dtStartDate, a_dtEndDate, a_lReportType == 11);
/*      */ 
/* 2730 */       sortResults(vResults, a_bSortAscending);
/* 2731 */       if (vResults.size() <= 0)
/*      */         break;
/* 2733 */       sFilename = createDownloadsPerUserGroupReportFile(vResults, a_dtStartDate, a_dtEndDate, a_lReportType == 11); break;
/*      */     case 12:
/* 2742 */       vResults = getReasonForDownloadReport(null, a_dtStartDate, a_dtEndDate, a_sUsername);
/*      */ 
/* 2744 */       sortResults(vResults, a_bSortAscending);
/* 2745 */       if (vResults.size() <= 0)
/*      */         break;
/* 2747 */       sFilename = createReasonForDownloadReportFile(vResults, a_dtStartDate, a_dtEndDate);
/*      */     }
/*      */ 
/* 2754 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public long createScheduledReport(DBTransaction a_dbTransaction, Date a_dtNextSendDate, String a_sReportPeriod, String a_sReportType, String a_sReportName)
/*      */     throws Bn2Exception
/*      */   {
/* 2776 */     AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 2777 */     String sSQL = null;
/* 2778 */     Connection con = null;
/* 2779 */     long lNewId = 0L;
/*      */ 
/* 2783 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2785 */     if (transaction == null)
/*      */     {
/* 2787 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2793 */       con = transaction.getConnection();
/*      */ 
/* 2795 */       sSQL = "INSERT INTO ScheduledReport (";
/* 2796 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 2798 */         lNewId = sqlGenerator.getUniqueId(con, "ScheduledReportSequence");
/* 2799 */         sSQL = sSQL + "Id, ";
/*      */       }
/*      */ 
/* 2802 */       sSQL = sSQL + "NextSendDate, ReportPeriod, ReportType, ReportName) VALUES (";
/*      */ 
/* 2804 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 2806 */         sSQL = sSQL + "?,";
/*      */       }
/*      */ 
/* 2809 */       sSQL = sSQL + "?,?,?,?)";
/*      */ 
/* 2811 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 2813 */       int iCol = 1;
/*      */ 
/* 2815 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 2817 */         psql.setLong(iCol++, lNewId);
/*      */       }
/* 2819 */       DBUtil.setFieldDateOrNull(psql, iCol++, a_dtNextSendDate);
/* 2820 */       psql.setString(iCol++, a_sReportPeriod);
/* 2821 */       psql.setString(iCol++, a_sReportType);
/* 2822 */       psql.setString(iCol++, a_sReportName);
/*      */ 
/* 2824 */       psql.executeUpdate();
/*      */ 
/* 2826 */       psql.close();
/*      */ 
/* 2828 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 2830 */         lNewId = sqlGenerator.getUniqueId(con, "ScheduledReportId");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2836 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2840 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2844 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2850 */       this.m_logger.error("SQL Exception whilst creating a scheduled report : " + e);
/*      */ 
/* 2853 */       throw new Bn2Exception("SQL Exception whilst creating a scheduled report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2860 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2864 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2868 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2875 */     return lNewId;
/*      */   }
/*      */ 
/*      */   public void updateScheduledReportDate(DBTransaction a_dbTransaction, long a_lReportId, Date a_dtNextSendDate)
/*      */     throws Bn2Exception
/*      */   {
/* 2895 */     String sSQL = null;
/* 2896 */     Connection con = null;
/*      */ 
/* 2898 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2900 */     if (transaction == null)
/*      */     {
/* 2902 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2908 */       con = transaction.getConnection();
/*      */ 
/* 2910 */       sSQL = "UPDATE ScheduledReport SET NextSendDate=? WHERE Id=?";
/*      */ 
/* 2912 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 2914 */       int iCol = 1;
/*      */ 
/* 2916 */       DBUtil.setFieldDateOrNull(psql, iCol++, a_dtNextSendDate);
/* 2917 */       psql.setLong(iCol++, a_lReportId);
/*      */ 
/* 2919 */       psql.executeUpdate();
/*      */ 
/* 2921 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2925 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2929 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2933 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2939 */       this.m_logger.error("SQL Exception whilst updating a scheduled report : " + e);
/*      */ 
/* 2942 */       throw new Bn2Exception("SQL Exception whilst updating a scheduled report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2949 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2953 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2957 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteScheduledReport(DBTransaction a_dbTransaction, long a_lReportId)
/*      */     throws Bn2Exception
/*      */   {
/* 2980 */     String sSQL = null;
/* 2981 */     Connection con = null;
/*      */ 
/* 2983 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2985 */     if (transaction == null)
/*      */     {
/* 2987 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2993 */       con = transaction.getConnection();
/*      */ 
/* 2995 */       sSQL = "DELETE FROM ScheduledReportGroup WHERE ReportId=? ";
/* 2996 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 2997 */       int iCol = 1;
/* 2998 */       psql.setLong(iCol++, a_lReportId);
/* 2999 */       psql.executeUpdate();
/* 3000 */       psql.close();
/*      */ 
/* 3002 */       sSQL = "DELETE FROM ScheduledReport WHERE Id=? ";
/* 3003 */       psql = con.prepareStatement(sSQL);
/* 3004 */       iCol = 1;
/* 3005 */       psql.setLong(iCol++, a_lReportId);
/* 3006 */       psql.executeUpdate();
/* 3007 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3012 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3016 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3020 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3026 */       this.m_logger.error("SQL Exception whilst deleting scheduled report : " + e);
/*      */ 
/* 3029 */       throw new Bn2Exception("SQL Exception whilst deleting scheduled report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3036 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3040 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3044 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void createScheduledReportGroup(DBTransaction a_dbTransaction, long a_iReportId, long a_iGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 3068 */     String sSQL = null;
/* 3069 */     Connection con = null;
/*      */ 
/* 3071 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 3073 */     if (transaction == null)
/*      */     {
/* 3075 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3081 */       con = transaction.getConnection();
/*      */ 
/* 3083 */       sSQL = "INSERT INTO ScheduledReportGroup (ReportId,GroupId) VALUES (?,?)";
/*      */ 
/* 3086 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 3088 */       int iCol = 1;
/*      */ 
/* 3090 */       psql.setLong(iCol++, a_iReportId);
/* 3091 */       psql.setLong(iCol++, a_iGroupId);
/*      */ 
/* 3093 */       psql.executeUpdate();
/* 3094 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3098 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3102 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3106 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3112 */       this.m_logger.error("SQL Exception whilst adding usergroup to scheduled report : " + e);
/*      */ 
/* 3115 */       throw new Bn2Exception("SQL Exception whilst adding usergroup to scheduled report : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3122 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3126 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3130 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getScheduledReports(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 3155 */     Vector vecItems = new Vector();
/*      */ 
/* 3157 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 3159 */     if (transaction == null)
/*      */     {
/* 3161 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3166 */       Connection con = transaction.getConnection();
/*      */ 
/* 3168 */       String sSql = "SELECT * FROM ScheduledReport";
/*      */ 
/* 3170 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 3172 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 3174 */       while (rs.next())
/*      */       {
/* 3176 */         ScheduledReport scheduledReport = new ScheduledReport();
/* 3177 */         scheduledReport.setId(rs.getLong("Id"));
/* 3178 */         scheduledReport.setNextSendDate(rs.getDate("NextSendDate"));
/* 3179 */         scheduledReport.setReportPeriod(rs.getString("ReportPeriod"));
/*      */ 
/* 3182 */         scheduledReport.setReportType(Integer.parseInt(rs.getString("ReportType")));
/*      */ 
/* 3184 */         scheduledReport.setReportName(rs.getString("ReportName"));
/* 3185 */         vecItems.add(scheduledReport);
/*      */       }
/*      */ 
/* 3188 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3193 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3197 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3201 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3207 */       this.m_logger.error("SQL Exception whilst retrieving scheduled reports from database : " + e);
/*      */ 
/* 3210 */       throw new Bn2Exception("SQL Exception whilst retrieving scheduled reports from database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3217 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3221 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3225 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3233 */     return vecItems;
/*      */   }
/*      */ 
/*      */   public List<StringDataBean> getScheduledReportGroups(DBTransaction a_dbTransaction, long a_lReportId)
/*      */     throws Bn2Exception
/*      */   {
/* 3252 */     List vecItems = new Vector();
/*      */ 
/* 3254 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 3256 */     if (transaction == null)
/*      */     {
/* 3258 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3263 */       Connection con = transaction.getConnection();
/*      */ 
/* 3265 */       String sSql = "SELECT ug.Id groupId, ug.Name groupName FROM ScheduledReportGroup srg, UserGroup ug WHERE ug.Id = srg.GroupId AND srg.ReportId = ?";
/*      */ 
/* 3269 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 3271 */       int iCol = 1;
/*      */ 
/* 3273 */       psql.setLong(iCol++, a_lReportId);
/*      */ 
/* 3275 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 3277 */       while (rs.next())
/*      */       {
/* 3279 */         StringDataBean group = new StringDataBean(rs.getLong("groupId"), rs.getString("groupName"));
/*      */ 
/* 3281 */         vecItems.add(group);
/*      */       }
/*      */ 
/* 3284 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3289 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3293 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3297 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3303 */       this.m_logger.error("SQL Exception whilst getting scheduled report group : " + e);
/*      */ 
/* 3306 */       throw new Bn2Exception("SQL Exception whilst getting scheduled report group :" + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3312 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3316 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3320 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3328 */     return vecItems;
/*      */   }
/*      */ 
/*      */   public void processScheduledReports()
/*      */     throws Bn2Exception
/*      */   {
/* 3347 */     processScheduledReports(false);
/*      */   }
/*      */ 
/*      */   public void processScheduledReports(boolean a_bForce)
/*      */     throws Bn2Exception
/*      */   {
/* 3367 */     Date dtStartDate = null;
/* 3368 */     Date dtEndDate = null;
/* 3369 */     Vector vecItems = new Vector();
/*      */ 
/* 3372 */     vecItems = getScheduledReports(null);
/*      */ 
/* 3375 */     Iterator itScheduledReports = vecItems.iterator();
/* 3376 */     while (itScheduledReports.hasNext())
/*      */     {
/* 3378 */       GregorianCalendar calendar = new GregorianCalendar();
/* 3379 */       GregorianCalendar gcStartDate = new GregorianCalendar();
/* 3380 */       Date today = DateUtil.getBeginningOfDay(calendar.getTime());
/* 3381 */       ScheduledReport scheduledReport = (ScheduledReport)itScheduledReports.next();
/*      */ 
/* 3384 */       if ((a_bForce) || (today.compareTo(scheduledReport.getNextSendDate()) >= 0))
/*      */       {
/* 3389 */         calendar.add(5, -1);
/* 3390 */         calendar.set(11, 23);
/* 3391 */         calendar.set(12, 59);
/* 3392 */         calendar.set(13, 59);
/* 3393 */         dtEndDate = calendar.getTime();
/*      */ 
/* 3396 */         if (scheduledReport.getReportPeriod().equals("Daily"))
/*      */         {
/* 3399 */           gcStartDate.add(5, -1);
/*      */         }
/* 3401 */         else if (scheduledReport.getReportPeriod().equals("Weekly"))
/*      */         {
/* 3404 */           gcStartDate.add(5, -7);
/*      */         }
/* 3406 */         else if (scheduledReport.getReportPeriod().equals("Monthly"))
/*      */         {
/* 3409 */           gcStartDate.add(2, -1);
/*      */         }
/*      */ 
/* 3413 */         gcStartDate.set(11, 0);
/* 3414 */         gcStartDate.set(12, 0);
/* 3415 */         gcStartDate.set(13, 0);
/*      */ 
/* 3417 */         dtStartDate = gcStartDate.getTime();
/*      */ 
/* 3420 */         String sFilename = createReport(scheduledReport.getReportType(), dtStartDate, dtEndDate, scheduledReport.getSortAscending(), scheduledReport.getUsername());
/*      */ 
/* 3426 */         List vecGroups = getScheduledReportGroups(null, scheduledReport.getId());
/*      */ 
/* 3429 */         Map params = new HashMap();
/* 3430 */         params.put("template", "send_report");
/* 3431 */         params.put("reportname", scheduledReport.getReportType() + " - " + scheduledReport.getReportPeriod());
/*      */ 
/* 3433 */         params.put("reportdescription", scheduledReport.getReportName());
/* 3434 */         params.put("reportperiod", "startdate");
/*      */         try
/*      */         {
/* 3440 */           if (sFilename == null)
/*      */           {
/* 3443 */             params.put("template", "send_report_no_data");
/* 3444 */             this.m_emailManager.sendEmailsToAdminAndGroups(params, vecGroups, null, false);
/*      */           }
/*      */           else
/*      */           {
/* 3449 */             EmailAttachment[] emailAttachments = new EmailAttachment[1];
/* 3450 */             EmailAttachment emailAttachment = new EmailAttachment();
/* 3451 */             emailAttachment.setName("usage_report.xls");
/* 3452 */             emailAttachment.setPath(this.m_fileStoreManager.getAbsolutePath(sFilename));
/* 3453 */             emailAttachments[0] = emailAttachment;
/*      */ 
/* 3455 */             this.m_emailManager.sendEmailsToAdminAndGroups(params, vecGroups, emailAttachments, false);
/*      */           }
/*      */ 
/* 3459 */           this.m_logger.debug("Processed and sent report '" + scheduledReport.getReportName() + "'");
/*      */ 
/* 3462 */           updateScheduledReportDate(null, scheduledReport.getId(), getNextSendDate(scheduledReport.getReportPeriod()));
/*      */         }
/*      */         catch (Throwable t)
/*      */         {
/* 3468 */           this.m_logger.warn("UsageManager: Email sending failed: " + t.getMessage(), t);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void sortResults(List a_results, boolean a_bAcsending)
/*      */   {
/* 3482 */     if ((a_results != null) && (a_results.size() > 0))
/*      */     {
/* 3484 */       if (a_bAcsending)
/*      */       {
/* 3486 */         Collections.sort(a_results, new ReportEntity.CountComparatorAsc());
/*      */       }
/*      */       else
/*      */       {
/* 3491 */         Collections.sort(a_results, new ReportEntity.CountComparatorDesc());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Date getNextSendDate(String a_sFrequency)
/*      */   {
/* 3505 */     GregorianCalendar calendar = new GregorianCalendar();
/* 3506 */     Date nextDate = null;
/*      */ 
/* 3508 */     if (a_sFrequency.equals("Daily"))
/*      */     {
/* 3510 */       calendar.add(5, 1);
/* 3511 */       nextDate = calendar.getTime();
/*      */     }
/*      */ 
/* 3514 */     if (a_sFrequency.equals("Weekly"))
/*      */     {
/* 3516 */       calendar.set(7, 2);
/* 3517 */       calendar.add(5, 7);
/* 3518 */       nextDate = calendar.getTime();
/*      */     }
/*      */ 
/* 3521 */     if (a_sFrequency.equals("Monthly"))
/*      */     {
/* 3523 */       calendar.set(5, 1);
/* 3524 */       calendar.add(2, 1);
/* 3525 */       nextDate = calendar.getTime();
/*      */     }
/*      */ 
/* 3528 */     return nextDate;
/*      */   }
/*      */ 
/*      */   public TotalsReport getLeastDownloadedAssets(DBTransaction a_dbTransaction, int a_iStartIndex, int a_iMaxResults)
/*      */     throws Bn2Exception
/*      */   {
/* 3540 */     return getAssetDownloadReportData(a_dbTransaction, null, null, Boolean.FALSE, a_iStartIndex, a_iMaxResults);
/*      */   }
/*      */ 
/*      */   public TotalsReport getMostDownloadedAssets(DBTransaction a_dbTransaction, int a_iStartIndex, int a_iMaxResults)
/*      */     throws Bn2Exception
/*      */   {
/* 3552 */     return getAssetDownloadReportData(a_dbTransaction, null, null, Boolean.TRUE, a_iStartIndex, a_iMaxResults);
/*      */   }
/*      */ 
/*      */   public TotalsReport getLeastViewedAssets(DBTransaction a_dbTransaction, int a_iStartIndex, int a_iMaxResults)
/*      */     throws Bn2Exception
/*      */   {
/* 3564 */     return getAssetViewReportData(a_dbTransaction, null, null, Boolean.FALSE, a_iStartIndex, a_iMaxResults);
/*      */   }
/*      */ 
/*      */   public TotalsReport getMostViewedAssets(DBTransaction a_dbTransaction, int a_iStartIndex, int a_iMaxResults)
/*      */     throws Bn2Exception
/*      */   {
/* 3576 */     return getAssetViewReportData(a_dbTransaction, null, null, Boolean.TRUE, a_iStartIndex, a_iMaxResults);
/*      */   }
/*      */ 
/*      */   public void setEmailManager(EmailManager emailManager)
/*      */   {
/* 3581 */     this.m_emailManager = emailManager;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager fileStoreManager)
/*      */   {
/* 3586 */     this.m_fileStoreManager = fileStoreManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager scheduleManager)
/*      */   {
/* 3591 */     this.m_scheduleManager = scheduleManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager transactionManager)
/*      */   {
/* 3596 */     this.m_transactionManager = transactionManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(CategoryManager a_categoryManager)
/*      */   {
/* 3601 */     this.m_categoryManager = a_categoryManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(AssetManager manager)
/*      */   {
/* 3606 */     this.m_assetManager = manager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.service.UsageReportManager
 * JD-Core Version:    0.6.0
 */