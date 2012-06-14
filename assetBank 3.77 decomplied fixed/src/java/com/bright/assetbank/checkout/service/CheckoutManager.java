/*     */ package com.bright.assetbank.checkout.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class CheckoutManager extends Bn2Manager
/*     */ {
/*  27 */   private static final String c_ksClassName = CheckoutManager.class.getSimpleName();
/*     */   private DBTransactionManager m_transactionManager;
/*     */   private AssetManager m_assetManager;
/*     */ 
/*     */   public CheckoutManager()
/*     */   {
/*  28 */     this.m_transactionManager = null;
/*  29 */     this.m_assetManager = null;
/*     */   }
/*     */ 
/*     */   public long getCheckoutStatus(DBTransaction a_dbTransaction, long assetId)
/*     */     throws Bn2Exception
/*     */   {
/*  56 */     String ksMethodName = "getCheckoutStatus";
/*  57 */     DBTransaction dbTransaction = a_dbTransaction;
/*  58 */     boolean rollback = false;
/*     */ 
/*  61 */     if (dbTransaction == null) {
/*  62 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*  66 */     long ret = 0L;
/*     */     try
/*     */     {
/*  69 */       Connection conn = dbTransaction.getConnection();
/*     */ 
/*  71 */       PreparedStatement psql = conn.prepareStatement("SELECT FileCheckedOutByUserId FROM Asset WHERE Id=?");
/*  72 */       psql.setLong(1, assetId);
/*  73 */       ResultSet rs = psql.executeQuery();
/*     */ 
/*  75 */       if (rs.next())
/*     */       {
/*  77 */         ret = rs.getLong("FileCheckedOutByUserId");
/*     */       }
/*     */ 
/*  80 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*  84 */       rollback = true;
/*  85 */       this.m_logger.error(c_ksClassName + "." + "getCheckoutStatus" + " : SQL Exception : " + e);
/*  86 */       throw new Bn2Exception(c_ksClassName + "." + "getCheckoutStatus" + " : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/*  91 */       if (a_dbTransaction == null) {
/*  92 */         commitOrRollback(dbTransaction, rollback, "getCheckoutStatus");
/*     */       }
/*     */     }
/*     */ 
/*  96 */     return ret;
/*     */   }
/*     */ 
/*     */   public void checkoutAsset(DBTransaction a_dbTransaction, long assetId, long userId)
/*     */     throws Bn2Exception, CheckoutManager.CheckedOutException
/*     */   {
/* 109 */     String ksMethodName = "checkoutAsset";
/* 110 */     DBTransaction dbTransaction = a_dbTransaction;
/* 111 */     boolean rollback = false;
/*     */ 
/* 114 */     if (dbTransaction == null) {
/* 115 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/* 118 */     long existingUserId = getCheckoutStatus(dbTransaction, assetId);
/*     */ 
/* 120 */     if (existingUserId == userId)
/*     */     {
/* 122 */       return;
/* 123 */     }if (existingUserId > 0L)
/*     */     {
/* 125 */       throw new CheckedOutException(existingUserId);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 131 */       Connection conn = dbTransaction.getConnection();
/*     */ 
/* 133 */       PreparedStatement psql = conn.prepareStatement("UPDATE Asset SET FileCheckedOutByUserId=?, FileCheckOutTime=? WHERE Id=?");
/* 134 */       psql.setLong(1, userId);
/* 135 */       psql.setTimestamp(2, new Timestamp(new Date().getTime()));
/* 136 */       psql.setLong(3, assetId);
/* 137 */       psql.executeUpdate();
/* 138 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 142 */       rollback = true;
/* 143 */       this.m_logger.error(c_ksClassName + "." + "checkoutAsset" + " : SQL Exception : " + e);
/* 144 */       throw new Bn2Exception(c_ksClassName + "." + "checkoutAsset" + " : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 149 */       if (a_dbTransaction == null)
/* 150 */         commitOrRollback(dbTransaction, rollback, "checkoutAsset");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void checkinAsset(DBTransaction a_dbTransaction, long assetId, long userId)
/*     */     throws Bn2Exception, CheckoutManager.CheckedOutException
/*     */   {
/* 167 */     String ksMethodName = "checkinAsset";
/* 168 */     DBTransaction dbTransaction = a_dbTransaction;
/* 169 */     boolean rollback = false;
/*     */ 
/* 172 */     if (dbTransaction == null) {
/* 173 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/* 176 */     long existingUserId = getCheckoutStatus(dbTransaction, assetId);
/*     */ 
/* 178 */     if (existingUserId <= 0L)
/*     */     {
/* 180 */       return;
/* 181 */     }if (existingUserId != userId)
/*     */     {
/* 183 */       throw new CheckedOutException(existingUserId);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 189 */       Connection conn = dbTransaction.getConnection();
/*     */ 
/* 191 */       PreparedStatement psql = conn.prepareStatement("UPDATE Asset SET FileCheckedOutByUserId=NULL, FileCheckOutTime=NULL WHERE Id=?");
/* 192 */       psql.setLong(1, assetId);
/* 193 */       psql.executeUpdate();
/* 194 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 198 */       rollback = true;
/* 199 */       this.m_logger.error(c_ksClassName + "." + "checkinAsset" + " : SQL Exception : " + e);
/* 200 */       throw new Bn2Exception(c_ksClassName + "." + "checkinAsset" + " : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 205 */       if (a_dbTransaction == null)
/* 206 */         commitOrRollback(dbTransaction, rollback, "checkinAsset");
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<Asset> getCheckedOutAssetsForUser(DBTransaction a_dbTransaction, long userId)
/*     */     throws Bn2Exception
/*     */   {
/* 219 */     String ksMethodName = "getCheckedOutAssetsForUser";
/* 220 */     DBTransaction dbTransaction = a_dbTransaction;
/* 221 */     boolean rollback = false;
/*     */ 
/* 224 */     if (dbTransaction == null) {
/* 225 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/* 229 */     List ret = new ArrayList();
/*     */     try
/*     */     {
/* 232 */       Connection conn = dbTransaction.getConnection();
/*     */ 
/* 234 */       PreparedStatement psql = conn.prepareStatement("SELECT Id FROM Asset WHERE FileCheckedOutByUserId=?");
/* 235 */       psql.setLong(1, userId);
/* 236 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 239 */       while (rs.next())
/*     */       {
/* 241 */         ret.add(this.m_assetManager.getAsset(dbTransaction, rs.getLong("Id")));
/*     */       }
/*     */ 
/* 244 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 248 */       rollback = true;
/* 249 */       this.m_logger.error(c_ksClassName + "." + "getCheckedOutAssetsForUser" + " : SQL Exception : " + e);
/* 250 */       throw new Bn2Exception(c_ksClassName + "." + "getCheckedOutAssetsForUser" + " : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 255 */       if (a_dbTransaction == null) {
/* 256 */         commitOrRollback(dbTransaction, rollback, "getCheckedOutAssetsForUser");
/*     */       }
/*     */     }
/*     */ 
/* 260 */     return ret;
/*     */   }
/*     */ 
/*     */   public List<Asset> getAllCheckedOutAssets(DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 271 */     String ksMethodName = "getCheckedOutAssetsForUser";
/* 272 */     DBTransaction dbTransaction = a_dbTransaction;
/* 273 */     boolean rollback = false;
/*     */ 
/* 276 */     if (dbTransaction == null) {
/* 277 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/* 281 */     List ret = new ArrayList();
/*     */     try
/*     */     {
/* 284 */       Connection conn = dbTransaction.getConnection();
/*     */ 
/* 286 */       PreparedStatement psql = conn.prepareStatement("SELECT Id FROM Asset WHERE FileCheckedOutByUserId IS NOT NULL");
/* 287 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 290 */       while (rs.next())
/*     */       {
/* 292 */         ret.add(this.m_assetManager.getAsset(dbTransaction, rs.getLong("Id")));
/*     */       }
/*     */ 
/* 295 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 299 */       rollback = true;
/* 300 */       this.m_logger.error(c_ksClassName + "." + "getCheckedOutAssetsForUser" + " : SQL Exception : " + e);
/* 301 */       throw new Bn2Exception(c_ksClassName + "." + "getCheckedOutAssetsForUser" + " : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 306 */       if (a_dbTransaction == null) {
/* 307 */         commitOrRollback(dbTransaction, rollback, "getCheckedOutAssetsForUser");
/*     */       }
/*     */     }
/*     */ 
/* 311 */     return ret;
/*     */   }
/*     */ 
/*     */   private void commitOrRollback(DBTransaction a_transaction, boolean a_rollback, String a_methodName)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 323 */       if (a_rollback)
/* 324 */         a_transaction.rollback();
/*     */       else
/* 326 */         a_transaction.commit();
/*     */     }
/*     */     catch (SQLException e) {
/* 329 */       this.m_logger.error(c_ksClassName + "." + a_methodName + " : SQL Exception : " + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public DBTransactionManager getDBTransactionManager()
/*     */   {
/* 336 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 341 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public AssetManager getAssetManager()
/*     */   {
/* 346 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 351 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public class CheckedOutException extends Exception
/*     */   {
/*     */     public long userId;
/*     */ 
/*     */     public CheckedOutException(long a_userId)
/*     */     {
/*  42 */       this.userId = a_userId;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.checkout.service.CheckoutManager
 * JD-Core Version:    0.6.0
 */