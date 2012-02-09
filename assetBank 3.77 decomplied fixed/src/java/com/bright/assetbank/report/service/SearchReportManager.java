/*     */ package com.bright.assetbank.report.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.report.bean.SearchReportEntry;
/*     */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.queue.service.QueueManager;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class SearchReportManager extends QueueManager
/*     */   implements AssetBankSearchConstants
/*     */ {
/*     */   private static final String c_ksClassName = "SearchReportManager";
/*  62 */   private DBTransactionManager m_transactionManager = null;
/*  63 */   private ScheduleManager m_scheduleManager = null;
/*  64 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     int iHourOfDay = AssetBankSettings.getStremlineSearchReportHour();
/*     */ 
/*  82 */     if (iHourOfDay > 0)
/*     */     {
/*  85 */       TimerTask task = new TimerTask()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           try
/*     */           {
/*  91 */             SearchReportManager.this.streamlineSearchReport(AssetBankSettings.getSearchReportDaysToKeep());
/*     */           }
/*     */           catch (Bn2Exception bn2e)
/*     */           {
/*  95 */             SearchReportManager.this.m_logger.error("SearchReportManager: Bn2Exception whilst attempting to streamline search report in scheduled task : " + bn2e);
/*     */           }
/*     */         }
/*     */       };
/* 100 */       this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, false);
/*     */     }
/*     */ 
/* 103 */     super.startup();
/*     */   }
/*     */ 
/*     */   public void processQueueItem(QueuedItem a_queuedItem)
/*     */     throws Bn2Exception
/*     */   {
/* 120 */     SearchReportEntry searchReportEntry = (SearchReportEntry)a_queuedItem;
/*     */ 
/* 122 */     saveReportEntry(searchReportEntry);
/*     */   }
/*     */ 
/*     */   public void addSearchToQueue(SearchQuery a_searchQuery, boolean a_bSuccess)
/*     */   {
/* 141 */     SearchReportEntry sre = new SearchReportEntry();
/*     */ 
/* 143 */     if (StringUtil.stringIsPopulated(a_searchQuery.getQueryDescription()))
/*     */     {
/* 145 */       Calendar now = new GregorianCalendar();
/* 146 */       sre.setSearchTerm(a_searchQuery.getQueryDescription());
/* 147 */       sre.setDate(now.getTime());
/* 148 */       sre.setSuccessful(a_bSuccess);
/*     */ 
/* 151 */       if (AssetBankSettings.getStoreFullQuery())
/*     */       {
/* 153 */         sre.setLuceneQuery(a_searchQuery.getLuceneQuery());
/*     */       }
/*     */ 
/* 157 */       queueItem(sre);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getSearchReport(Date a_dtStartDate, Date a_dtEndDate, boolean a_bGroupedResults, int a_iSuccessType)
/*     */     throws Bn2Exception
/*     */   {
/* 180 */     String ksMethodName = "getSearchReport";
/*     */ 
/* 183 */     DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/* 184 */     Vector vecSearchReport = null;
/*     */     try
/*     */     {
/* 189 */       Connection con = dbTransaction.getConnection();
/*     */ 
/* 192 */       String sSql = "";
/* 193 */       String sSqlExtra = "";
/*     */ 
/* 195 */       if (!a_bGroupedResults)
/*     */       {
/* 197 */         sSql = "SELECT * ";
/* 198 */         sSqlExtra = " ORDER BY SearchDate";
/*     */       }
/*     */       else
/*     */       {
/* 202 */         sSql = "SELECT COUNT(SearchTerm) AS noOfRes, SearchTerm ";
/* 203 */         sSqlExtra = " GROUP BY SearchTerm";
/*     */       }
/*     */ 
/* 207 */       sSql = sSql + "FROM SearchReport WHERE 1=1";
/*     */ 
/* 209 */       if (a_dtStartDate != null)
/*     */       {
/* 211 */         sSql = sSql + " AND SearchDate>=?";
/*     */       }
/*     */ 
/* 214 */       if (a_dtEndDate != null)
/*     */       {
/* 216 */         sSql = sSql + " AND SearchDate<=?";
/*     */       }
/*     */ 
/* 220 */       if (a_iSuccessType == 1)
/*     */       {
/* 222 */         sSql = sSql + " AND Success=1";
/*     */       }
/* 224 */       else if (a_iSuccessType == 2)
/*     */       {
/* 226 */         sSql = sSql + " AND Success=0";
/*     */       }
/*     */ 
/* 230 */       sSql = sSql + sSqlExtra;
/*     */ 
/* 233 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 234 */       int iCol = 1;
/*     */ 
/* 236 */       if (a_dtStartDate != null)
/*     */       {
/* 238 */         DBUtil.setFieldDateOrNull(psql, iCol++, a_dtStartDate);
/*     */       }
/*     */ 
/* 241 */       if (a_dtEndDate != null)
/*     */       {
/* 243 */         DBUtil.setFieldDateOrNull(psql, iCol++, a_dtEndDate);
/*     */       }
/*     */ 
/* 246 */       ResultSet rs = psql.executeQuery();
/* 247 */       vecSearchReport = new Vector();
/*     */ 
/* 250 */       while (rs.next())
/*     */       {
/* 252 */         SearchReportEntry sre = new SearchReportEntry();
/* 253 */         sre.setSearchTerm(rs.getString("SearchTerm"));
/*     */ 
/* 256 */         if (a_bGroupedResults)
/*     */         {
/* 258 */           sre.setResultCount(rs.getInt("noOfRes"));
/*     */         }
/*     */         else
/*     */         {
/* 262 */           sre.setDate(rs.getDate("SearchDate"));
/* 263 */           sre.setLuceneQuery(rs.getString("FullQuery"));
/* 264 */           sre.setSuccessful(rs.getBoolean("Success"));
/*     */         }
/*     */ 
/* 267 */         vecSearchReport.add(sre);
/*     */       }
/*     */ 
/* 270 */       psql.close();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */       try
/*     */       {
/* 276 */         dbTransaction.rollback();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/*     */       }
/*     */ 
/* 283 */       this.m_logger.error("Exception in SearchReportManager.getSearchReport:", sqle);
/* 284 */       throw new Bn2Exception("Exception in SearchReportManager.getSearchReport:", sqle);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 291 */         dbTransaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 295 */         this.m_logger.error("Exception commiting transaction SearchReportManager.getSearchReport:", sqle);
/* 296 */         throw new Bn2Exception("Exception commiting transaction SearchReportManager.getSearchReport:", sqle);
/*     */       }
/*     */     }
/*     */ 
/* 300 */     return vecSearchReport;
/*     */   }
/*     */ 
/*     */   private void saveReportEntry(SearchReportEntry a_searchReportEntry)
/*     */     throws Bn2Exception
/*     */   {
/* 317 */     String ksMethodName = "saveReportLine";
/*     */ 
/* 320 */     DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 325 */       Connection con = dbTransaction.getConnection();
/*     */ 
/* 328 */       String sSql = "INSERT INTO SearchReport (SearchTerm, FullQuery, Success, SearchDate) VALUES (?,?,?,?)";
/* 329 */       PreparedStatement psql = con.prepareStatement(sSql);
/*     */ 
/* 332 */       int iCol = 1;
/* 333 */       psql.setString(iCol++, a_searchReportEntry.getSearchTerm());
/* 334 */       psql.setString(iCol++, a_searchReportEntry.getLuceneQuery());
/* 335 */       psql.setBoolean(iCol++, a_searchReportEntry.getSuccessful());
/* 336 */       DBUtil.setFieldDateOrNull(psql, iCol++, a_searchReportEntry.getDate());
/*     */ 
/* 338 */       psql.executeUpdate();
/* 339 */       psql.close();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */       try
/*     */       {
/* 345 */         dbTransaction.rollback();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/*     */       }
/*     */ 
/* 352 */       this.m_logger.error("Exception in SearchReportManager.saveReportLine:", sqle);
/* 353 */       throw new Bn2Exception("Exception in SearchReportManager.saveReportLine:", sqle);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 360 */         dbTransaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 364 */         this.m_logger.error("Exception commiting transaction SearchReportManager.saveReportLine:", sqle);
/* 365 */         throw new Bn2Exception("Exception commiting transaction SearchReportManager.saveReportLine:", sqle);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void streamlineSearchReport(int a_iDays)
/*     */     throws Bn2Exception
/*     */   {
/* 385 */     String ksMethodName = "streamlineSearchReport";
/*     */ 
/* 388 */     DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 393 */       Connection con = dbTransaction.getConnection();
/*     */ 
/* 396 */       Calendar time = new GregorianCalendar();
/* 397 */       time.add(5, 0 - a_iDays);
/*     */ 
/* 400 */       String sSql = "DELETE FROM SearchReport WHERE SearchDate<?";
/* 401 */       PreparedStatement psql = con.prepareStatement(sSql);
/*     */ 
/* 404 */       int iCol = 1;
/* 405 */       DBUtil.setFieldDateOrNull(psql, iCol++, time.getTime());
/*     */ 
/* 407 */       psql.executeUpdate();
/* 408 */       psql.close();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */       try
/*     */       {
/* 414 */         dbTransaction.rollback();
/*     */       }
/*     */       catch (SQLException se)
/*     */       {
/*     */       }
/*     */ 
/* 421 */       this.m_logger.error("Exception in SearchReportManager.streamlineSearchReport:", sqle);
/* 422 */       throw new Bn2Exception("Exception in SearchReportManager.streamlineSearchReport:", sqle);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 429 */         dbTransaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 433 */         this.m_logger.error("Exception commiting transaction SearchReportManager.streamlineSearchReport:", sqle);
/* 434 */         throw new Bn2Exception("Exception commiting transaction SearchReportManager.streamlineSearchReport:", sqle);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String createSearchReportFile(Vector a_vData, Date a_startDate, Date a_endDate, boolean a_bGroupedReport, boolean a_bSuccessfulSearches)
/*     */     throws Bn2Exception
/*     */   {
/* 452 */     String sFilename = this.m_fileStoreManager.getUniqueFilepath("search_report.xls", StoredFileType.TEMP);
/*     */ 
/* 455 */     Writer writer = null;
/* 456 */     BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*     */     try
/*     */     {
/* 460 */       writer = new BufferedWriter(new FileWriter(this.m_fileStoreManager.getAbsolutePath(sFilename)));
/*     */ 
/* 462 */       writer.append("Search Report\n");
/*     */ 
/* 464 */       writer.append("Report date range: ");
/* 465 */       if ((a_startDate != null) && (a_endDate == null))
/*     */       {
/* 467 */         writer.append("searches performed after " + format.format(a_startDate));
/*     */       }
/* 469 */       else if ((a_startDate == null) && (a_endDate != null))
/*     */       {
/* 471 */         writer.append("searches performed before " + format.format(a_endDate));
/*     */       }
/* 473 */       else if ((a_startDate != null) && (a_endDate != null))
/*     */       {
/* 475 */         writer.append(format.format(a_startDate) + " to " + format.format(a_endDate));
/*     */       }
/* 477 */       writer.append("\n");
/*     */ 
/* 479 */       if (!a_bGroupedReport)
/*     */       {
/* 481 */         writer.append("Date of Search\t");
/*     */       }
/* 483 */       writer.append("Search Term\t");
/* 484 */       if (!a_bGroupedReport)
/*     */       {
/* 486 */         writer.append("Successful?");
/*     */       }
/* 490 */       else if (a_bSuccessfulSearches)
/*     */       {
/* 492 */         writer.append("No. of Successful Searches");
/*     */       }
/*     */       else
/*     */       {
/* 496 */         writer.append("No. of Failed Searches");
/*     */       }
/*     */ 
/* 499 */       writer.append("\n");
/*     */ 
/* 501 */       for (int i = 0; i < a_vData.size(); i++)
/*     */       {
/* 503 */         SearchReportEntry entry = (SearchReportEntry)a_vData.get(i);
/* 504 */         if (!a_bGroupedReport)
/*     */         {
/* 506 */           writer.append(format.format(entry.getDate()) + "\t");
/*     */         }
/* 508 */         writer.append("\"" + entry.getSearchTerm().replace("\"", "'") + "\"\t");
/* 509 */         if (!a_bGroupedReport)
/*     */         {
/* 511 */           writer.append(entry.getSuccessful() ? "yes" : "no");
/*     */         }
/*     */         else
/*     */         {
/* 515 */           writer.append(String.valueOf(entry.getResultCount()));
/*     */         }
/*     */ 
/* 518 */         writer.append("\n");
/*     */       }
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 523 */       this.m_logger.error("SearchReportManager.createSearchReportFile() : IOException whilst creating report file : " + ioe);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 529 */         writer.flush();
/* 530 */         writer.close();
/*     */       }
/*     */       catch (IOException ioe2)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 538 */     return sFilename;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 543 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_scheduleManager)
/*     */   {
/* 548 */     this.m_scheduleManager = a_scheduleManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/* 553 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.service.SearchReportManager
 * JD-Core Version:    0.6.0
 */