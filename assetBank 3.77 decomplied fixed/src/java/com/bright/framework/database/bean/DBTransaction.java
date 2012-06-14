/*     */ package com.bright.framework.database.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.util.DebugUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class DBTransaction
/*     */ {
/*     */   private DBTransactionManager m_transactionManager;
/*  50 */   private Connection m_connection = null;
/*  51 */   private boolean m_bClosed = false;
/*  52 */   private StackTraceElement[] m_stackTrace = null;
/*     */ 
/*  54 */   private int m_iUsageCount = 1;
/*  55 */   private List<TransactionListener> m_listeners = null;
/*     */ 
/*     */   public DBTransaction(DBTransactionManager a_transactionManager, Connection a_connection)
/*     */   {
/*  70 */     if (a_connection == null)
/*     */     {
/*  72 */       throw new NullPointerException();
/*     */     }
/*  74 */     if (a_transactionManager == null)
/*     */     {
/*  76 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*  81 */     if (FrameworkSettings.getLogStackTraceWithTransaction())
/*     */     {
/*  83 */       this.m_stackTrace = Thread.currentThread().getStackTrace();
/*     */     }
/*     */ 
/*  86 */     this.m_transactionManager = a_transactionManager;
/*  87 */     this.m_connection = a_connection;
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */   {
/* 101 */     if (!this.m_bClosed)
/*     */     {
/* 103 */       String sTraceMessage = "";
/* 104 */       String creationStackTrace = getCreationStackTrace();
/* 105 */       if (creationStackTrace != null)
/*     */       {
/* 107 */         sTraceMessage = "The stack trace of the thread that created the transaction is: \n";
/*     */ 
/* 110 */         sTraceMessage = sTraceMessage + creationStackTrace;
/*     */       }
/*     */ 
/* 113 */       GlobalApplication.getInstance().getLogger().error("DEBUG ERROR: A transaction was not properly closed - you must call commit or rollback. " + sTraceMessage);
/*     */ 
/* 116 */       if (this.m_connection != null)
/*     */       {
/*     */         try
/*     */         {
/* 120 */           commit();
/*     */         }
/*     */         catch (Throwable t)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getCreationStackTrace()
/*     */   {
/* 135 */     return this.m_stackTrace == null ? null : DebugUtil.stackTraceElementsToString(this.m_stackTrace);
/*     */   }
/*     */ 
/*     */   public Connection getConnection()
/*     */     throws Bn2Exception
/*     */   {
/* 153 */     if (this.m_bClosed)
/*     */     {
/* 155 */       throw new Bn2Exception("This transaction has been closed.");
/*     */     }
/*     */ 
/* 158 */     return this.m_connection;
/*     */   }
/*     */ 
/*     */   public void commit()
/*     */     throws Bn2Exception, SQLException
/*     */   {
/* 176 */     if (this.m_connection == null)
/*     */     {
/* 178 */       throw new Bn2Exception("The connection is null in DBTransaction.commit");
/*     */     }
/*     */ 
/* 184 */     this.m_iUsageCount -= 1;
/*     */ 
/* 189 */     if ((!this.m_bClosed) && (this.m_iUsageCount <= 0))
/*     */     {
/* 191 */       this.m_transactionManager.transactionFinished(this);
/*     */       try
/*     */       {
/* 196 */         this.m_connection.commit();
/*     */       }
/*     */       finally
/*     */       {
/* 201 */         this.m_connection.close();
/*     */       }
/*     */ 
/* 204 */       this.m_bClosed = true;
/*     */ 
/* 207 */       if (this.m_listeners != null)
/*     */       {
/* 209 */         for (TransactionListener listener : this.m_listeners)
/*     */         {
/* 211 */           listener.doAfterCommit();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void commit2()
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 233 */       commit();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 237 */       throw new Bn2Exception("SQLException during commit", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void rollback()
/*     */     throws Bn2Exception, SQLException
/*     */   {
/* 255 */     if (this.m_connection == null)
/*     */     {
/* 257 */       throw new Bn2Exception("The connection is null in DBTransaction.commit");
/*     */     }
/*     */ 
/* 261 */     if (!this.m_bClosed)
/*     */     {
/* 263 */       this.m_transactionManager.transactionFinished(this);
/*     */       try
/*     */       {
/* 268 */         this.m_connection.rollback();
/*     */       }
/*     */       finally
/*     */       {
/* 273 */         this.m_connection.close();
/*     */       }
/*     */ 
/* 276 */       this.m_bClosed = true;
/*     */ 
/* 279 */       if (this.m_listeners != null)
/*     */       {
/* 281 */         for (TransactionListener listener : this.m_listeners)
/*     */         {
/* 283 */           listener.doAfterRollback();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void rollback2()
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 305 */       rollback();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 309 */       throw new Bn2Exception("SQLException during rollback", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void incUsageCount()
/*     */   {
/* 315 */     this.m_iUsageCount += 1;
/*     */   }
/*     */ 
/*     */   public void addListener(TransactionListener a_listener)
/*     */   {
/* 320 */     if (this.m_listeners == null)
/*     */     {
/* 322 */       this.m_listeners = new LinkedList();
/*     */     }
/* 324 */     this.m_listeners.add(a_listener);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.bean.DBTransaction
 * JD-Core Version:    0.6.0
 */