/*     */ package com.bright.assetbank.actiononasset.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.actiononasset.action.ActionOnAsset;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ActionOnAssetManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "ActionOnAssetManager";
/*  41 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/*     */   public List<ActionOnAsset> getAvailableActions(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  52 */     List actions = null;
/*  53 */     String ksMethodName = "getAvailableActions";
/*  54 */     Connection con = null;
/*  55 */     String sSql = null;
/*  56 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/*  60 */       con = a_transaction.getConnection();
/*     */ 
/*  62 */       sSql = "SELECT Id, Name, Description, ActionClass FROM ActionOnAsset ORDER BY Name";
/*     */ 
/*  64 */       psql = con.prepareStatement(sSql);
/*     */ 
/*  66 */       ResultSet rs = psql.executeQuery();
/*     */ 
/*  68 */       actions = new Vector();
/*     */ 
/*  70 */       while (rs.next())
/*     */       {
/*  72 */         ActionOnAsset action = ActionOnAsset.createAction(rs.getString("ActionClass"));
/*     */ 
/*  74 */         action.setId(rs.getLong("Id"));
/*  75 */         action.setName(rs.getString("Name"));
/*  76 */         action.setDescription(rs.getString("Description"));
/*  77 */         action.setActionClassName(rs.getString("ActionClass"));
/*  78 */         actions.add(action);
/*     */       }
/*     */ 
/*  81 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*  85 */       this.m_logger.error("ActionOnAssetManager.getAvailableActions : SQL Exception : " + e);
/*  86 */       throw new Bn2Exception("ActionOnAssetManager.getAvailableActions : SQL Exception : " + e, e);
/*     */     }
/*  88 */     return actions;
/*     */   }
/*     */ 
/*     */   public ActionOnAsset getAction(DBTransaction a_transaction, long a_lActionId)
/*     */     throws Bn2Exception
/*     */   {
/* 100 */     ActionOnAsset action = null;
/* 101 */     String ksMethodName = "getAction";
/* 102 */     Connection con = null;
/* 103 */     String sSql = null;
/* 104 */     PreparedStatement psql = null;
/* 105 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 107 */     if (transaction == null)
/*     */     {
/* 109 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 114 */       con = transaction.getConnection();
/*     */ 
/* 116 */       sSql = "SELECT Id, Name, Description, ActionClass FROM ActionOnAsset WHERE Id=?";
/*     */ 
/* 118 */       psql = con.prepareStatement(sSql);
/* 119 */       psql.setLong(1, a_lActionId);
/*     */ 
/* 121 */       ResultSet rs = psql.executeQuery();
/*     */       ActionOnAsset localActionOnAsset1;
/* 123 */       if (rs.next())
/*     */       {
/* 125 */         action = ActionOnAsset.createAction(rs.getString("ActionClass"));
/*     */ 
/* 127 */         action.setId(rs.getLong("Id"));
/* 128 */         action.setName(rs.getString("Name"));
/* 129 */         action.setDescription(rs.getString("Description"));
/* 130 */         action.setActionClassName(rs.getString("ActionClass"));
/*     */ 
/* 132 */         //localActionOnAsset1 = action; jsr 136;
                   localActionOnAsset1 = action; 
/*     */       }
/*     */ 
/* 135 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 139 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 143 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 147 */           this.m_logger.error("ActionOnAssetManager.getAction : SQL Exception while rolling back : " + e);
/*     */         }
/*     */       }
/* 150 */       this.m_logger.error("ActionOnAssetManager.getAction : SQL Exception : " + e);
/* 151 */       throw new Bn2Exception("ActionOnAssetManager.getAction : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 156 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 160 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 164 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 169 */     return action;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_manager)
/*     */   {
/* 174 */     this.m_transactionManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.actiononasset.service.ActionOnAssetManager
 * JD-Core Version:    0.6.0
 */