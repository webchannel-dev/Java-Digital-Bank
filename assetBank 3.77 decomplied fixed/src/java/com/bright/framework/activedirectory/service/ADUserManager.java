/*     */ package com.bright.framework.activedirectory.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.activedirectory.constant.ActiveDirectorySettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.exception.AuthenticationException;
/*     */ import com.bright.framework.user.exception.InvalidLoginException;
/*     */ import com.bright.framework.user.exception.LoginException;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ /** @deprecated */
/*     */ public abstract class ADUserManager extends UserManager
/*     */ {
/*     */   public abstract ActiveDirectoryManager getActiveDirectoryManager();
/*     */ 
/*     */   protected void checkPassword(DBTransaction a_dbTransaction, HttpServletRequest a_request, HttpServletResponse a_response, User a_user, String a_sPasswordEntered, String a_sPasswordInDatabase)
/*     */     throws LoginException, Bn2Exception
/*     */   {
/*  74 */     if (!a_user.isRemoteUser())
/*     */     {
/*  77 */       super.checkPassword(a_dbTransaction, 
/*  78 */         a_request, 
/*  79 */         a_response, 
/*  80 */         a_user, 
/*  81 */         a_sPasswordEntered/*, */
/*  82 */        /* a_sPasswordInDatabase*/);
/*     */     }
/*  87 */     else if (!ActiveDirectorySettings.getSuspendADAuthentication())
/*     */     {
/*  90 */       if ((a_sPasswordEntered == null) || (a_sPasswordEntered.length() == 0))
/*     */       {
/*  92 */         throw new InvalidLoginException("No password entered");
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/*  97 */         getActiveDirectoryManager().authenticateUser(a_request, 
/*  98 */           a_response, 
/*  99 */           a_user.getRemoteUsername(), 
/* 100 */           a_sPasswordEntered, 
/* 101 */           a_user.getRemoteServerIndex());
/*     */       }
/*     */       catch (AuthenticationException ae)
/*     */       {
/* 105 */         throw new InvalidLoginException("Incorrect password");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getActiveDirectoryUserIdList(DBTransaction a_dbTransaction, int a_iServerIndex)
/*     */     throws Bn2Exception
/*     */   {
/* 120 */     Connection cCon = null;
/* 121 */     Vector vecIdList = null;
/*     */     try
/*     */     {
/* 125 */       if (a_dbTransaction == null)
/*     */       {
/* 127 */         cCon = getDataSourceComponent().getConnection();
/*     */       }
/*     */       else
/*     */       {
/* 131 */         cCon = a_dbTransaction.getConnection();
/*     */       }
/*     */ 
/* 134 */       String sSql = "SELECT Id FROM AssetBankUser WHERE NotActiveDirectory = 0 AND LDAPServerIndex=?";
/*     */ 
/* 137 */       PreparedStatement psql = cCon.prepareStatement(sSql);
/* 138 */       psql.setInt(1, a_iServerIndex);
/*     */ 
/* 140 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 142 */       vecIdList = new Vector();
/*     */ 
/* 144 */       while (rs.next())
/*     */       {
/* 146 */         Long userId = new Long(rs.getLong("Id"));
/* 147 */         vecIdList.add(userId);
/*     */       }
/*     */ 
/* 150 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 154 */       if (a_dbTransaction == null)
/*     */       {
/*     */         try
/*     */         {
/* 158 */           cCon.rollback();
/*     */         }
/*     */         catch (SQLException localSQLException1)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 166 */       this.m_logger.error("SQL Exception in UserManager:getActiveDirectoryUserIdList: " + e.getMessage());
/* 167 */       throw new Bn2Exception("SQL Exception in UserManager:getActiveDirectoryUserIdList: " + e.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/* 172 */       if ((a_dbTransaction == null) && (cCon != null))
/*     */       {
/*     */         try
/*     */         {
/* 176 */           cCon.commit();
/* 177 */           cCon.close();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 181 */           this.m_logger.error("Sorry, SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 186 */     return vecIdList;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.activedirectory.service.ADUserManager
 * JD-Core Version:    0.6.0
 */