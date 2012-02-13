/*     */ package com.bright.assetbank.publishing.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.assetbank.publishing.bean.PublishingAction;
/*     */ import com.bright.assetbank.publishing.bean.PublishingActionRequest;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.queue.service.MessagingQueueManager;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class PublishingManager extends MessagingQueueManager
/*     */ {
/*     */   private static final String c_ksClassName = "PublishingManager";
/*  39 */   private DBTransactionManager m_transactionManager = null;
/*  40 */   private ScheduleManager m_scheduleManager = null;
/*  41 */   private MultiLanguageSearchManager m_searchManager = null;
/*  42 */   private AssetManager m_assetManager = null;
/*  43 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  48 */     super.startup();
/*     */ 
/*  50 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/*  56 */           PublishingManager.this.runDailyPublishingActions();
/*     */         }
/*     */         catch (Bn2Exception err)
/*     */         {
/*  60 */           PublishingManager.this.m_logger.error("Error running daily publishing actions", err);
/*     */         }
/*     */       }
/*     */     };
/*  65 */     int iHourOfDay = FrameworkSettings.getRunPublishingActionsHourOfDay();
/*  66 */     this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, false);
/*     */   }
/*     */ 
/*     */   private void runDailyPublishingActions()
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     String ksMethodName = "runDailyPublishingActions";
/*  74 */     DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/*  77 */       for (PublishingAction publishingAction : loadDailyPublishActions(dbTransaction)) {
/*  78 */         PublishingActionRequest queueItem = new PublishingActionRequest(publishingAction);
/*  79 */         queuePublishingAction(queueItem);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  86 */         dbTransaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/*  90 */         this.m_logger.error("Exception commiting transaction PublishingManager.runDailyPublishingActions:", sqle);
/*  91 */         throw new Bn2Exception("Exception commiting transaction PublishingManager.runDailyPublishingActions:", sqle);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void queuePublishingAction(PublishingActionRequest a_request)
/*     */   {
/* 100 */     long requestId = a_request.getRequestId().longValue();
/* 101 */     startBatchProcess(requestId);
/* 102 */     queueItem(a_request);
/*     */   }
/*     */ 
/*     */   public void processQueueItem(QueuedItem a_item)
/*     */     throws Bn2Exception
/*     */   {
/* 110 */     String ksMethodName = "processQueueItem";
/*     */ 
/* 112 */     PublishingActionRequest request = (PublishingActionRequest)a_item;
/* 113 */     PublishingAction publishingAction = request.getPublishingAction();
/*     */ 
/* 115 */     String message = "Publishing action '" + publishingAction.getName() + "' started.";
/* 116 */     this.m_logger.debug(message);
/* 117 */     addMessage(request.getRequestId().longValue(), message);
/*     */ 
/* 120 */     SearchQuery query = new SearchCriteria();
/* 121 */     query.setCategoryIds(Long.toString(publishingAction.getAccessLevelId()));
/*     */ 
/* 124 */     SearchResults results = this.m_searchManager.search(query);
/* 125 */     Vector<LightweightAsset> lightweightAssets = results.getSearchResults();
/* 126 */     Vector empty = new Vector();
/*     */ 
/* 128 */     DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 131 */       for (LightweightAsset lightweightAsset : lightweightAssets) {
/* 132 */         Asset asset = this.m_assetManager.getAsset(dbTransaction, lightweightAsset.getId(), empty, false, false);
/* 133 */         String srcPath = this.m_fileStoreManager.getAbsolutePath(asset.getFileLocation());
/* 134 */         String destPath = publishingAction.getPath() + File.separatorChar + asset.getOriginalFilename();
/*     */         try
/*     */         {
/* 137 */           FileUtils.copyFile(new File(srcPath), new File(destPath));
/*     */         }
/*     */         catch (IOException err)
/*     */         {
/* 141 */           message = "Could not copy Asset " + asset.getId() + " to '" + destPath + "'";
/* 142 */           addMessage(request.getRequestId().longValue(), message + ": " + err.getMessage());
/* 143 */           throw new Bn2Exception(message, err);
/*     */         }
/* 145 */         message = "Published asset " + asset.getId() + " to " + destPath;
/* 146 */         addMessage(request.getRequestId().longValue(), message);
/* 147 */         this.m_logger.debug(message);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 154 */         dbTransaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 158 */         this.m_logger.error("Exception commiting transaction PublishingManager.processQueueItem:", sqle);
/* 159 */         throw new Bn2Exception("Exception commiting transaction PublishingManager.processQueueItem:", sqle);
/*     */       }
/*     */     }
/*     */ 
/* 163 */     message = "Publishing action '" + publishingAction.getName() + "' completed.";
/* 164 */     addMessage(request.getRequestId().longValue(), message);
/* 165 */     this.m_logger.debug(message);
/*     */ 
/* 167 */     endBatchProcess(request.getRequestId().longValue());
/*     */   }
/*     */ 
/*     */   public void savePublishAction(DBTransaction a_transaction, PublishingAction bean)
/*     */     throws Bn2Exception
/*     */   {
/* 173 */     if (bean.getId() > 0L)
/* 174 */       updatePublishAction(a_transaction, bean);
/*     */     else
/* 176 */       insertPublishAction(a_transaction, bean);
/*     */   }
/*     */ 
/*     */   private void insertPublishAction(DBTransaction a_transaction, PublishingAction bean)
/*     */     throws Bn2Exception
/*     */   {
/* 183 */     String ksMethodName = "insertPublishAction";
/* 184 */     Connection conn = null;
/* 185 */     String sSql = null;
/* 186 */     PreparedStatement psql = null;
/* 187 */     AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*     */     try
/*     */     {
/* 191 */       conn = a_transaction.getConnection();
/*     */ 
/* 194 */       String idArgSql = "";
/* 195 */       String idValSql = "";
/* 196 */       long newUniqueId = 0L;
/* 197 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 199 */         newUniqueId = sqlGenerator.getUniqueId(conn, "PublishActionSequence");
/* 200 */         idArgSql = "Id, ";
/* 201 */         idValSql = "?,";
/*     */       }
/*     */ 
/* 205 */       sSql = "INSERT INTO PublishAction (" + idArgSql + "Name, Path, CategoryId, RunDaily) VALUES (" + idValSql + "?,?,?,?)";
/* 206 */       psql = conn.prepareStatement(sSql);
/*     */ 
/* 208 */       int iCol = 1;
/* 209 */       if (!sqlGenerator.usesAutoincrementFields()) {
/* 210 */         psql.setLong(iCol++, newUniqueId);
/*     */       }
/* 212 */       psql.setString(iCol++, bean.getName());
/* 213 */       psql.setString(iCol++, bean.getPath());
/* 214 */       psql.setLong(iCol++, bean.getAccessLevelId());
/* 215 */       psql.setBoolean(iCol++, bean.getRunDaily());
/*     */ 
/* 217 */       psql.executeUpdate();
/* 218 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 222 */       this.m_logger.error("PublishingManager.insertPublishAction : SQL Exception : " + e);
/* 223 */       throw new Bn2Exception("PublishingManager.insertPublishAction : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updatePublishAction(DBTransaction a_transaction, PublishingAction bean)
/*     */     throws Bn2Exception
/*     */   {
/* 230 */     String ksMethodName = "updatePublishAction";
/* 231 */     Connection conn = null;
/* 232 */     String sSql = null;
/* 233 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 237 */       conn = a_transaction.getConnection();
/*     */ 
/* 239 */       sSql = "UPDATE PublishAction SET Name=?, Path=?, CategoryId=?, RunDaily=? WHERE Id=?";
/* 240 */       psql = conn.prepareStatement(sSql);
/* 241 */       int iCol = 1;
/* 242 */       psql.setString(iCol++, bean.getName());
/* 243 */       psql.setString(iCol++, bean.getPath());
/* 244 */       psql.setLong(iCol++, bean.getAccessLevelId());
/* 245 */       psql.setBoolean(iCol++, bean.getRunDaily());
/* 246 */       psql.setLong(iCol++, bean.getId());
/*     */ 
/* 248 */       psql.executeUpdate();
/* 249 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 253 */       this.m_logger.error("PublishingManager.updatePublishAction : SQL Exception : " + e);
/* 254 */       throw new Bn2Exception("PublishingManager.updatePublishAction : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deletePublishAction(DBTransaction a_transaction, Long a_id)
/*     */     throws Bn2Exception
/*     */   {
/* 261 */     String ksMethodName = "deletePublishAction";
/* 262 */     Connection conn = null;
/* 263 */     String sSql = null;
/* 264 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 268 */       conn = a_transaction.getConnection();
/*     */ 
/* 270 */       sSql = "DELETE FROM PublishAction WHERE Id=?";
/* 271 */       psql = conn.prepareStatement(sSql);
/* 272 */       int iCol = 1;
/* 273 */       psql.setLong(iCol++, a_id.longValue());
/*     */ 
/* 275 */       psql.executeUpdate();
/* 276 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 280 */       this.m_logger.error("PublishingManager.deletePublishAction : SQL Exception : " + e);
/* 281 */       throw new Bn2Exception("PublishingManager.deletePublishAction : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<PublishingAction> loadPublishActions(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 288 */     String ksMethodName = "loadPublishActions";
/* 289 */     List ret = new ArrayList();
/* 290 */     Connection conn = null;
/* 291 */     String sSql = null;
/* 292 */     PreparedStatement psql = null;
/* 293 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 297 */       conn = a_transaction.getConnection();
/*     */ 
/* 299 */       sSql = "SELECT * FROM PublishAction";
/* 300 */       psql = conn.prepareStatement(sSql);
/*     */ 
/* 302 */       rs = psql.executeQuery();
/*     */ 
/* 304 */       while (rs.next())
/*     */       {
/* 306 */         PublishingAction result = new PublishingAction(Long.valueOf(rs.getLong("Id")), rs.getString("Name"), rs.getString("Path"), rs.getLong("CategoryId"), rs.getBoolean("RunDaily"));
/*     */ 
/* 311 */         ret.add(result);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 316 */       this.m_logger.error("PublishingManager.loadPublishActions : SQL Exception : " + e);
/* 317 */       throw new Bn2Exception("PublishingManager.loadPublishActions : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 321 */       if (psql != null)
/*     */       {
/*     */         try
/*     */         {
/* 325 */           psql.close();
/*     */         }
/*     */         catch (SQLException e) {
/*     */         }
/*     */       }
/*     */     }
/* 331 */     return ret;
/*     */   }
/*     */ 
/*     */   public List<PublishingAction> loadDailyPublishActions(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 337 */     String ksMethodName = "loadPublishActions";
/* 338 */     List ret = new ArrayList();
/* 339 */     Connection conn = null;
/* 340 */     String sSql = null;
/* 341 */     PreparedStatement psql = null;
/* 342 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 346 */       conn = a_transaction.getConnection();
/*     */ 
/* 348 */       sSql = "SELECT * FROM PublishAction WHERE RunDaily=1";
/* 349 */       psql = conn.prepareStatement(sSql);
/* 350 */       rs = psql.executeQuery();
/*     */ 
/* 352 */       while (rs.next())
/*     */       {
/* 354 */         PublishingAction result = new PublishingAction(Long.valueOf(rs.getLong("Id")), rs.getString("Name"), rs.getString("Path"), rs.getLong("CategoryId"), rs.getBoolean("RunDaily"));
/*     */ 
/* 359 */         ret.add(result);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 364 */       this.m_logger.error("PublishingManager.loadPublishActions : SQL Exception : " + e);
/* 365 */       throw new Bn2Exception("PublishingManager.loadPublishActions : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 369 */       if (psql != null)
/*     */       {
/*     */         try
/*     */         {
/* 373 */           psql.close();
/*     */         }
/*     */         catch (SQLException e) {
/*     */         }
/*     */       }
/*     */     }
/* 379 */     return ret;
/*     */   }
/*     */ 
/*     */   public PublishingAction loadPublishAction(DBTransaction a_transaction, long a_id)
/*     */     throws Bn2Exception
/*     */   {
/* 385 */     String ksMethodName = "loadPublishActions";
/* 386 */     PublishingAction ret = null;
/* 387 */     Connection conn = null;
/* 388 */     String sSql = null;
/* 389 */     PreparedStatement psql = null;
/* 390 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 394 */       conn = a_transaction.getConnection();
/*     */ 
/* 396 */       sSql = "SELECT * FROM PublishAction WHERE Id=?";
/* 397 */       psql = conn.prepareStatement(sSql);
/* 398 */       psql.setLong(1, a_id);
/*     */ 
/* 400 */       rs = psql.executeQuery();
/*     */ 
/* 402 */       if (rs.next())
/*     */       {
/* 404 */         ret = new PublishingAction(Long.valueOf(rs.getLong("Id")), rs.getString("Name"), rs.getString("Path"), rs.getLong("CategoryId"), rs.getBoolean("RunDaily"));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 414 */       this.m_logger.error("PublishingManager.loadPublishActions : SQL Exception : " + e);
/* 415 */       throw new Bn2Exception("PublishingManager.loadPublishActions : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 419 */       if (psql != null)
/*     */       {
/*     */         try
/*     */         {
/* 423 */           psql.close();
/*     */         }
/*     */         catch (SQLException e) {
/*     */         }
/*     */       }
/*     */     }
/* 429 */     return ret;
/*     */   }
/*     */ 
/*     */   public DBTransactionManager getTransactionManager()
/*     */   {
/* 435 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 440 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_scheduleManager)
/*     */   {
/* 445 */     this.m_scheduleManager = a_scheduleManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 450 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 455 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 460 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.publishing.service.PublishingManager
 * JD-Core Version:    0.6.0
 */