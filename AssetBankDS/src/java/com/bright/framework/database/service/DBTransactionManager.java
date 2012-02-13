/*     */ package com.bright.framework.database.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.DebugUtil;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class DBTransactionManager extends Bn2Manager
/*     */ {
/*  56 */   private ThreadLocal<WeakReference<DBTransaction>> m_currentTransaction = new ThreadLocal();
/*     */ 
/*  58 */   private DataSourceComponent m_dataSource = null;
/*     */ 
/*     */   public DBTransaction getNewTransaction()
/*     */     throws Bn2Exception
/*     */   {
/*     */     Connection connection;
/*     */     try
/*     */     {
/*  79 */       connection = this.m_dataSource.getConnection();
/*     */     }
/*     */     catch (Throwable sqle)
/*     */     {
/*  83 */       if (FrameworkSettings.getThreadDumpOnGetConnectionError())
/*     */       {
/*  85 */         this.m_logger.error("Thread dump after getConnection failure:\n\n" + DebugUtil.getThreadDumpAsString());
/*     */       }
/*  87 */       throw new Bn2Exception("Could not get connection from pool in DBTransaction.getNewTransaction", sqle);
/*     */     }
/*     */ 
/*  90 */     DBTransaction transaction = new DBTransaction(this, connection);
/*     */ 
/*  92 */     transactionStarted(transaction);
/*     */ 
/*  94 */     return transaction;
/*     */   }
/*     */ 
/*     */   public DBTransaction getCurrentOrNewTransaction()
/*     */     throws Bn2Exception
/*     */   {
/* 103 */     DBTransaction transaction = getCurrentTransaction();
/*     */ 
/* 105 */     if (transaction == null)
/*     */     {
/* 107 */       transaction = getNewTransaction();
/*     */     }
/*     */     else
/*     */     {
/* 111 */       transaction.incUsageCount();
/*     */     }
/* 113 */     return transaction;
/*     */   }
/*     */ 
/*     */   public <T> T execute(DBTransaction a_transaction, DBTransactionCallback<T> a_callback)
/*     */     throws Bn2Exception
/*     */   {
/* 134 */     boolean bRollback = true;
/*     */ 
/* 136 */     DBTransaction transaction = a_transaction;
/* 137 */     if (transaction == null)
/*     */     {
/* 139 */       transaction = getCurrentOrNewTransaction();
/*     */     }
                Object result;
/*     */     try {
/* 143 */       result = a_callback.doInTransaction(transaction);
/*     */ 
/* 146 */       bRollback = false;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 150 */       throw new Bn2Exception("SQLException occurred", e);
/*     */     }
/*     */     finally
/*     */     {
/* 154 */       if (bRollback)
/*     */       {
/*     */         try
/*     */         {
/* 158 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException eRollback)
/*     */         {
/* 162 */           this.m_logger.error("SQLException whilst trying to roll back transaction ", eRollback);
/*     */         }
/*     */       }
/*     */ 
/* 166 */       if (a_transaction == null)
/*     */       {
/*     */         try
/*     */         {
/* 170 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 174 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 179 */     return (T) result;
/*     */   }
/*     */   public <T> T executeInNewTransaction(DBTransactionCallback<T> a_callback) throws Bn2Exception {
/* 198 */     boolean bRollback = true;
/*     */ 
/* 200 */     DBTransaction transaction = getNewTransaction();
/*     */     Object result;
/*     */     try {
/* 203 */       result = a_callback.doInTransaction(transaction);
/*     */ 
/* 206 */       bRollback = false;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 210 */       throw new Bn2Exception("SQLException occurred", e);
/*     */     }
/*     */     finally
/*     */     {
/* 214 */       if (bRollback)
/*     */       {
/*     */         try
/*     */         {
/* 218 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException eRollback)
/*     */         {
/* 222 */           this.m_logger.error("SQLException whilst trying to roll back transaction ", eRollback);
/*     */         }
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 228 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 232 */         this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */       }
/*     */     }
/*     */ 
/* 236 */     return (T) result;
/*     */   }
/*     */ 
/*     */   public <T> T execute(DBTransactionCallback<T> a_callback)
/*     */     throws Bn2Exception
/*     */   {
/* 252 */     return execute(null, a_callback);
/*     */   }
/*     */ 
/*     */   private void transactionStarted(DBTransaction a_transaction)
/*     */   {
/* 260 */     DBTransaction preExistingCurrentTransaction = getCurrentTransaction();
/* 261 */     if (preExistingCurrentTransaction == null)
/*     */     {
/* 263 */       setCurrentTransaction(a_transaction);
/*     */     }
/* 265 */     else if (FrameworkSettings.getWarnOnConcurrentTransactions())
/*     */     {
/* 267 */       String stackTraceNow = DebugUtil.stackTraceElementsToString(Thread.currentThread().getStackTrace());
/* 268 */       this.m_logger.warn("More than one concurrent transaction in the same thread. Stack trace where new transaction started: " + stackTraceNow);
/* 269 */       String preExistingStackTrace = preExistingCurrentTransaction.getCreationStackTrace();
/* 270 */       if (preExistingStackTrace != null)
/*     */       {
/* 272 */         this.m_logger.warn("Stack trace where pre-existing transaction started: " + preExistingStackTrace);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void transactionFinished(DBTransaction a_transaction)
/*     */   {
/* 284 */     if (getCurrentTransaction() == a_transaction)
/*     */     {
/* 286 */       setCurrentTransaction(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   private DBTransaction getCurrentTransaction()
/*     */   {
/* 296 */     WeakReference transactionRef = (WeakReference)this.m_currentTransaction.get();
/*     */     DBTransaction transaction;
/*     */    // DBTransaction transaction;
/* 298 */     if (transactionRef != null)
/*     */     {
/* 300 */       transaction = (DBTransaction)transactionRef.get();
/*     */     }
/*     */     else
/*     */     {
/* 304 */       transaction = null;
/*     */     }
/* 306 */     return transaction;
/*     */   }
/*     */ 
/*     */   private void setCurrentTransaction(DBTransaction a_transaction)
/*     */   {
/* 314 */     this.m_currentTransaction.set(new WeakReference(a_transaction));
/*     */   }
/*     */ 
/*     */   public void setDataSourceComponent(DataSourceComponent a_datasource)
/*     */   {
/* 330 */     this.m_dataSource = a_datasource;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.service.DBTransactionManager
 * JD-Core Version:    0.6.0
 */