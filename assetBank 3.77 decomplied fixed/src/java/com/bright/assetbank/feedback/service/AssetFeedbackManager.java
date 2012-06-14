/*     */ package com.bright.assetbank.feedback.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.assetbank.feedback.bean.AssetFeedback;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AssetFeedbackManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "AssetFeedbackManager";
/*  53 */   private DBTransactionManager m_transactionManager = null;
/*     */   private static final String c_sFeedbackFields = "f.FeedbackComment, f.Rating, f.Id fId, f.UserId, f.AssetId, f.DateOfFeedback, f.FeedbackSubject";
/*  58 */   private Vector m_vecRatingCache = null;
/*  59 */   private Object m_objRatingCacheLock = new Object();
/*     */ 
/*     */   public AssetFeedback getFeedback(DBTransaction a_dbTransaction, long a_lFeedbackId)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     return getFeedback(a_dbTransaction, a_lFeedbackId, -1L, -1L);
/*     */   }
/*     */ 
/*     */   public AssetFeedback getFeedback(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/*  88 */     return getFeedback(a_dbTransaction, -1L, a_lAssetId, a_lUserId);
/*     */   }
/*     */ 
/*     */   private AssetFeedback getFeedback(DBTransaction a_dbTransaction, long a_lFeedbackId, long a_lAssetId, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 113 */     String ksMethodName = "getFeedback";
/* 114 */     AssetFeedback fb = null;
/* 115 */     Connection con = null;
/* 116 */     ResultSet rs = null;
/* 117 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 119 */     if (transaction == null)
/*     */     {
/* 121 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 126 */       con = transaction.getConnection();
/*     */ 
/* 128 */       String sSQL = "SELECT f.FeedbackComment, f.Rating, f.Id fId, f.UserId, f.AssetId, f.DateOfFeedback, f.FeedbackSubject FROM AssetRating f WHERE ";
/*     */ 
/* 130 */       if (a_lFeedbackId > 0L)
/*     */       {
/* 132 */         sSQL = sSQL + "f.Id=?";
/*     */       }
/*     */       else
/*     */       {
/* 136 */         sSQL = sSQL + "f.AssetId=? AND f.UserId=?";
/*     */       }
/*     */ 
/* 140 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*     */ 
/* 142 */       if (a_lFeedbackId > 0L)
/*     */       {
/* 144 */         psql.setLong(1, a_lFeedbackId);
/*     */       }
/*     */       else
/*     */       {
/* 148 */         psql.setLong(1, a_lAssetId);
/* 149 */         psql.setLong(2, a_lUserId);
/*     */       }
/*     */ 
/* 152 */       rs = psql.executeQuery();
/*     */ 
/* 154 */       if (rs.next())
/*     */       {
/* 156 */         fb = buildFeedback(rs, false);
/*     */       }
/*     */ 
/* 159 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 163 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 167 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 171 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 175 */       this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst getting rating from the database : " + e);
/* 176 */       throw new Bn2Exception("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst getting rating from the database : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 181 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 185 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 189 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 194 */     return fb;
/*     */   }
/*     */ 
/*     */   public Vector<AssetFeedback> getFeedbackForAsset(DBTransaction a_dbTransaction, long a_lAssetId)
/*     */     throws Bn2Exception
/*     */   {
/* 215 */     String ksMethodName = "getFeedbackForAsset";
/* 216 */     Vector vecFeedback = new Vector();
/* 217 */     Connection con = null;
/* 218 */     ResultSet rs = null;
/* 219 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 221 */     if (transaction == null)
/*     */     {
/* 223 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 228 */       con = transaction.getConnection();
/*     */ 
/* 230 */       String sSQL = "SELECT f.FeedbackComment, f.Rating, f.Id fId, f.UserId, f.AssetId, f.DateOfFeedback, f.FeedbackSubject, u.Id userId, u.Forename, u.Surname, u.EmailAddress FROM AssetRating f LEFT JOIN AssetBankUser u ON f.UserId=u.Id WHERE f.AssetId=?";
/*     */ 
/* 235 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 236 */       psql.setLong(1, a_lAssetId);
/* 237 */       rs = psql.executeQuery();
/*     */ 
/* 239 */       while (rs.next())
/*     */       {
/* 241 */         AssetFeedback fb = buildFeedback(rs, true);
/* 242 */         vecFeedback.add(fb);
/*     */       }
/*     */ 
/* 245 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 249 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 253 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 257 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 261 */       this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst getting feedback from the database : " + e);
/* 262 */       throw new Bn2Exception("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst getting feedback from the database : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 267 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 271 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 275 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 280 */     return vecFeedback;
/*     */   }
/*     */ 
/*     */   public boolean saveFeedback(DBTransaction a_dbTransaction, AssetFeedback a_feedback)
/*     */     throws Bn2Exception
/*     */   {
/* 300 */     String ksMethodName = "saveFeedback";
/* 301 */     Connection con = null;
/* 302 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 304 */     if (transaction == null)
/*     */     {
/* 306 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 311 */       con = transaction.getConnection();
/*     */ 
/* 313 */       String sSQL = null;
/* 314 */       boolean bAdding = false;
/* 315 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*     */ 
/* 317 */       if (a_feedback.getId() > 0L)
/*     */       {
/* 320 */         sSQL = "UPDATE AssetRating SET UserId=?, AssetId=?, Rating=?, FeedbackComment=?, DateOfFeedback=?, FeedbackSubject=? WHERE Id=?";
/*     */       }
/*     */       else
/*     */       {
/* 325 */         if (!AssetBankSettings.getMultipleFeedbackEntries())
/*     */         {
/* 327 */           sSQL = "SELECT * FROM AssetRating WHERE UserId=? AND AssetId=?";
/* 328 */           PreparedStatement psql = con.prepareStatement(sSQL);
/* 329 */           psql.setLong(1, a_feedback.getUserId());
/* 330 */           psql.setLong(2, a_feedback.getAssetId());
/* 331 */           ResultSet rs = psql.executeQuery();
/* 332 */           if (rs.next())
/*     */           {
/* 334 */             psql.close();
/* 335 */             int i = 0;
/*     */             //return i;
                     return false;
                     
/*     */           }
/* 337 */           psql.close();
/*     */         }
/*     */ 
/* 340 */         bAdding = true;
/*     */ 
/* 343 */         sSQL = "INSERT INTO AssetRating (UserId, AssetId, Rating, FeedbackComment, DateOfFeedback, FeedbackSubject";
/*     */ 
/* 345 */         if (!sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 347 */           a_feedback.setId(sqlGenerator.getUniqueId(con, "AssetRatingSequence"));
/* 348 */           sSQL = sSQL + ", Id";
/*     */         }
/*     */ 
/* 351 */         sSQL = sSQL + ") VALUES (?,?,?,?,?,?";
/*     */ 
/* 353 */         if (!sqlGenerator.usesAutoincrementFields())
/*     */         {
/* 355 */           sSQL = sSQL + ",?";
/*     */         }
/*     */ 
/* 358 */         sSQL = sSQL + ")";
/*     */       }
/*     */ 
/* 363 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 364 */       psql.setLong(1, a_feedback.getUserId());
/* 365 */       psql.setLong(2, a_feedback.getAssetId());
/* 366 */       psql.setInt(3, a_feedback.getRating());
/* 367 */       psql.setString(4, a_feedback.getComments());
/* 368 */       DBUtil.setFieldDateOrNull(psql, 5, a_feedback.getDate());
/* 369 */       psql.setString(6, a_feedback.getSubject());
/*     */ 
/* 371 */       if ((!bAdding) || (!sqlGenerator.usesAutoincrementFields()))
/*     */       {
/* 373 */         psql.setLong(7, a_feedback.getId());
/*     */       }
/*     */ 
/* 376 */       psql.executeUpdate();
/*     */ 
/* 378 */       if (sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 380 */         a_feedback.setId(sqlGenerator.getUniqueId(con, "AssetRating"));
/*     */       }
/*     */ 
/* 383 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 387 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 391 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 395 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 399 */       this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst save rating from the database : " + e);
/* 400 */       throw new Bn2Exception("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst save rating from the database : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 405 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 409 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 413 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 418 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean assetCanBeRated(DBTransaction a_dbTransaction, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 438 */     String ksMethodName = "assetCanBeRated";
/* 439 */     boolean bCanRate = false;
/* 440 */     boolean bSetValue = false;
/*     */ 
/* 442 */     if (this.m_vecRatingCache != null)
/*     */     {
/* 444 */       return cachedRatingSwitch(a_asset);
/*     */     }
/*     */ 
/* 449 */     Connection con = null;
/* 450 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 452 */     if (transaction == null)
/*     */     {
/* 454 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 459 */       synchronized (this.m_objRatingCacheLock)
/*     */       {
/* 461 */         con = transaction.getConnection();
/* 462 */         String sSQL = "SELECT cvg.CategoryId FROM CategoryVisibleToGroup cvg, CM_Category c WHERE cvg.CategoryId=c.Id AND c.CategoryTypeId=? AND cvg.CantReviewAssets=0";
/* 463 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 464 */         psql.setLong(1, 2L);
/* 465 */         ResultSet rs = psql.executeQuery();
/*     */ 
/* 467 */         while (rs.next())
/*     */         {
/* 469 */           long lCatId = rs.getLong("CategoryId");
/*     */ 
/* 471 */           if (this.m_vecRatingCache == null)
/*     */           {
/* 473 */             this.m_vecRatingCache = new Vector();
/*     */           }
/*     */ 
/* 476 */           this.m_vecRatingCache.add(new Long(lCatId));
/*     */ 
/* 479 */           if (!bSetValue)
/*     */           {
/* 481 */             for (int i = 0; i < a_asset.getPermissionCategories().size(); i++)
/*     */             {
/* 483 */               Category cat = (Category)a_asset.getPermissionCategories().get(i);
/* 484 */               if (cat.getId() != lCatId)
/*     */                 continue;
/* 486 */               bCanRate = true;
/* 487 */               bSetValue = true;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 492 */         psql.close();
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 497 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 501 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 505 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 509 */       this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst save rating from the database : " + e);
/* 510 */       throw new Bn2Exception("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst save rating from the database : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 515 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 519 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 523 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 528 */     return bCanRate;
/*     */   }
/*     */ 
/*     */   public void clearRatingCache()
/*     */   {
/* 534 */     synchronized (this.m_objRatingCacheLock)
/*     */     {
/* 536 */       this.m_vecRatingCache = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean cachedRatingSwitch(Asset a_asset)
/*     */   {
/* 543 */     synchronized (this.m_objRatingCacheLock)
/*     */     {
/* 545 */       for (int i = 0; i < a_asset.getPermissionCategories().size(); i++)
/*     */       {
/* 547 */         Category cat = (Category)a_asset.getPermissionCategories().get(i);
/* 548 */         if (this.m_vecRatingCache.contains(new Long(cat.getId())))
/*     */         {
/* 550 */           return true;
/*     */         }
/*     */       }
/* 553 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteFeedback(DBTransaction a_dbTransaction, long a_lFeedbackId)
/*     */     throws Bn2Exception
/*     */   {
/* 561 */     deleteFeedback(a_dbTransaction, a_lFeedbackId, -1L, -1L);
/*     */   }
/*     */ 
/*     */   public void deleteFeedback(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 567 */     deleteFeedback(a_dbTransaction, -1L, a_lAssetId, a_lUserId);
/*     */   }
/*     */ 
/*     */   private void deleteFeedback(DBTransaction a_dbTransaction, long a_lFeedbackId, long a_lAssetId, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 585 */     String ksMethodName = "deleteFeedback";
/* 586 */     Connection con = null;
/* 587 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 589 */     if (transaction == null)
/*     */     {
/* 591 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 596 */       con = transaction.getConnection();
/*     */ 
/* 598 */       String sSQL = "";
/*     */       PreparedStatement psql;
/* 601 */       if (a_lFeedbackId > 0L)
/*     */       {
/* 603 */         sSQL = "DELETE FROM AssetRating WHERE Id=?";
/* 604 */         psql = con.prepareStatement(sSQL);
/* 605 */         psql.setLong(1, a_lFeedbackId);
/*     */       }
/*     */       else
/*     */       {
/* 609 */         sSQL = "DELETE FROM AssetRating WHERE AssetId=? AND UserId=?";
/* 610 */         psql = con.prepareStatement(sSQL);
/* 611 */         psql.setLong(1, a_lAssetId);
/* 612 */         psql.setLong(2, a_lUserId);
/*     */       }
/*     */ 
/* 615 */       psql.executeUpdate();
/* 616 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 620 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 624 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 628 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to rollback " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 632 */       this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst deleting rating from the database : " + e);
/* 633 */       throw new Bn2Exception("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst deleting rating from the database : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 638 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 642 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 646 */           this.m_logger.error("AssetFeedbackManager." + ksMethodName + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private AssetFeedback buildFeedback(ResultSet a_rs, boolean a_bGetUser)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 657 */     AssetFeedback fb = new AssetFeedback();
/* 658 */     fb.setComments(a_rs.getString("FeedbackComment"));
/* 659 */     fb.setRating(a_rs.getInt("Rating"));
/* 660 */     fb.setId(a_rs.getLong("fId"));
/* 661 */     fb.setUserId(a_rs.getLong("UserId"));
/* 662 */     fb.setAssetId(a_rs.getLong("AssetId"));
/* 663 */     fb.setDate(a_rs.getDate("DateOfFeedback"));
/* 664 */     fb.setSubject(a_rs.getString("FeedbackSubject"));
/*     */ 
/* 667 */     if (a_bGetUser)
/*     */     {
/* 669 */       long lUserId = a_rs.getLong("userId");
/* 670 */       if (lUserId > 0L)
/*     */       {
/* 672 */         ABUser user = new ABUser();
/* 673 */         user.setId(lUserId);
/* 674 */         user.setForename(a_rs.getString("Forename"));
/* 675 */         user.setSurname(a_rs.getString("Surname"));
/* 676 */         user.setEmailAddress(a_rs.getString("EmailAddress"));
/* 677 */         fb.setUser(user);
/*     */       }
/*     */     }
/*     */ 
/* 681 */     return fb;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 686 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.feedback.service.AssetFeedbackManager
 * JD-Core Version:    0.6.0
 */