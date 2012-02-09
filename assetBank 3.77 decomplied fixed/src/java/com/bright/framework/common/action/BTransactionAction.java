/*     */ package com.bright.framework.common.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import java.sql.SQLException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class BTransactionAction extends Bn2Action
/*     */ {
/*  46 */   protected DBTransactionManager m_transactionManager = null;
/*     */ 
/*     */   public abstract ActionForward execute(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, DBTransaction paramDBTransaction)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public final ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  93 */     ActionForward afForward = null;
/*  94 */     DBTransaction dbTransaction = null;
/*     */     try
/*     */     {
/*  99 */       if (this.m_transactionManager == null)
/*     */       {
/* 101 */         this.m_logger.debug("TransactionManager is null");
/*     */       }
/*     */ 
/* 105 */       dbTransaction = getNewTransaction();
/*     */ 
/* 108 */       afForward = execute(a_mapping, a_form, a_request, a_response, dbTransaction);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 112 */       if (dbTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 116 */           dbTransaction.rollback();
/*     */         }
/*     */         catch (Exception e2)
/*     */         {
/* 120 */           this.m_logger.error("Exception rolling back transaction in Bn2TransactionAction:" + getClass().getName(), e2);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 126 */       afForward = a_mapping.findForward("SystemFailure");
/*     */ 
/* 131 */       if ((e instanceof Bn2Exception))
/*     */       {
/* 133 */         throw ((Bn2Exception)e);
/*     */       }
/*     */ 
/* 137 */       throw new Bn2Exception("Exception thrown by " + getClass().getName(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 142 */       if (!commitTransaction(dbTransaction))
/*     */       {
/* 144 */         afForward = a_mapping.findForward("SystemFailure");
/*     */       }
/*     */     }
/*     */ 
/* 148 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected DBTransaction getNewTransaction()
/*     */     throws Bn2Exception
/*     */   {
/* 160 */     DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/* 161 */     return dbTransaction;
/*     */   }
/*     */ 
/*     */   protected boolean commitTransaction(DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 181 */     boolean bSuccess = true;
/*     */     try
/*     */     {
/* 184 */       if (a_dbTransaction != null)
/*     */       {
/* 186 */         a_dbTransaction.commit();
/*     */       }
/*     */       else
/*     */       {
/* 190 */         bSuccess = false;
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 195 */       this.m_logger.error("Exception commiting transaction in Bn2TransactionAction:" + getClass().getName() + e.getMessage());
/* 196 */       bSuccess = false;
/*     */     }
/* 198 */     return bSuccess;
/*     */   }
/*     */ 
/*     */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 203 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.action.BTransactionAction
 * JD-Core Version:    0.6.0
 */