/*     */ package com.bright.assetbank.entity.relationship.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity.Translation;
/*     */ import com.bright.assetbank.entity.exception.InvalidRelationshipException;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AssetEntityRelationshipManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "AssetEntityRelationshipManager";
/*  52 */   private ArrayList<Long> m_alChildEntityIds = null;
/*  53 */   private Object m_objChildEntityIdsLock = new Object();
/*  54 */   private DBTransactionManager m_transactionManager = null;
/*  55 */   private AssetEntityManager m_assetEntityManager = null;
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager transactionManager)
/*     */   {
/*  59 */     this.m_transactionManager = transactionManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager a_assetEntityManager)
/*     */   {
/*  64 */     this.m_assetEntityManager = a_assetEntityManager;
/*     */   }
/*     */ 
/*     */   public boolean isChildEntity(DBTransaction a_transaction, long a_lEntityId)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     String ksMethodName = "isChildEntity";
/*     */ 
/*  78 */     if (this.m_alChildEntityIds == null)
/*     */     {
/*  80 */       Connection con = null;
/*  81 */       DBTransaction transaction = a_transaction;
/*     */ 
/*  83 */       if (transaction == null)
/*     */       {
/*  85 */         transaction = this.m_transactionManager.getNewTransaction();
/*     */       }
/*     */       try
/*     */       {
/*  89 */         con = transaction.getConnection();
/*  90 */         String sSql = "SELECT DISTINCT RelatesToAssetEntityId FROM AllowableEntityRelationship WHERE RelationshipTypeId=?";
/*     */ 
/*  93 */         PreparedStatement psql = con.prepareStatement(sSql);
/*  94 */         psql.setLong(1, 2L);
/*     */ 
/*  96 */         ResultSet rs = psql.executeQuery();
/*     */ 
/*  98 */         ArrayList alTemp = new ArrayList();
/*     */ 
/* 100 */         while (rs.next())
/*     */         {
/* 102 */           alTemp.add(new Long(rs.getLong("RelatesToAssetEntityId")));
/*     */         }
/*     */ 
/* 105 */         psql.close();
/*     */ 
/* 107 */         synchronized (this.m_objChildEntityIdsLock)
/*     */         {
/* 109 */           this.m_alChildEntityIds = alTemp;
/*     */         }
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 114 */         if ((transaction != null) && (a_transaction == null))
/*     */           try {
/* 116 */             transaction.rollback();
/*     */           } catch (SQLException sqle) {
/*     */           }
/* 119 */         this.m_logger.error("AssetEntityRelationshipManager.isChildEntity : SQL Exception : " + e);
/* 120 */         throw new Bn2Exception("AssetEntityRelationshipManager.isChildEntity : SQL Exception : " + e, e);
/*     */       }
/*     */       finally
/*     */       {
/* 125 */         if ((a_transaction == null) && (transaction != null)) {
/*     */           try {
/* 127 */             transaction.commit();
/*     */           }
/*     */           catch (SQLException sqle) {
/* 130 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 135 */     return this.m_alChildEntityIds.contains(new Long(a_lEntityId));
/*     */   }
/*     */ 
/*     */   public ArrayList<AssetEntity> getChildEntitiesOf(DBTransaction a_transaction, long a_lEntityId)
/*     */     throws Bn2Exception
/*     */   {
/* 152 */     String ksMethodName = "getChildEntitiesOf";
/* 153 */     Connection con = null;
/* 154 */     DBTransaction transaction = a_transaction;
/* 155 */     ArrayList alEntities = null;
/*     */ 
/* 157 */     if (transaction == null)
/*     */     {
/* 159 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */     try
/*     */     {
/* 163 */       con = transaction.getConnection();
/* 164 */       String sSql = "SELECT " + AssetEntityManager.c_ksAssetEntityFields + "FROM AssetEntity ae " + "LEFT JOIN TranslatedAssetEntity tae ON tae.AssetEntityId=ae.Id " + "LEFT JOIN Language l ON l.Id=tae.LanguageId " + "LEFT JOIN TranslatedERDescription trdc ON trdc.AssetEntityId=ae.Id AND trdc.RelationshipTypeId=? AND trdc.LanguageId=l.Id " + "LEFT JOIN TranslatedERDescription trdp ON trdp.AssetEntityId=ae.Id AND trdp.RelationshipTypeId=? AND trdp.LanguageId=l.Id " + "LEFT JOIN AllowableEntityRelationship aer ON aer.RelatesToAssetEntityId=ae.Id " + "WHERE aer.RelationshipTypeId=? AND aer.AssetEntityId=?";
/*     */ 
/* 173 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 174 */       psql.setLong(1, 2L);
/* 175 */       psql.setLong(2, 1L);
/* 176 */       psql.setLong(3, 2L);
/* 177 */       psql.setLong(4, a_lEntityId);
/*     */ 
/* 179 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 181 */       while (rs.next())
/*     */       {
/* 183 */         AssetEntity entity = this.m_assetEntityManager.buildAssetEntity(transaction, rs);
/* 184 */         if (alEntities == null)
/*     */         {
/* 186 */           alEntities = new ArrayList();
/*     */         }
/* 188 */         alEntities.add(entity);
/*     */       }
/*     */ 
/* 191 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 195 */       if ((transaction != null) && (a_transaction == null))
/*     */         try {
/* 197 */           transaction.rollback();
/*     */         } catch (SQLException sqle) {
/*     */         }
/* 200 */       this.m_logger.error("AssetEntityRelationshipManager.getChildEntitiesOf : SQL Exception : " + e);
/* 201 */       throw new Bn2Exception("AssetEntityRelationshipManager.getChildEntitiesOf : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 206 */       if ((a_transaction == null) && (transaction != null)) {
/*     */         try {
/* 208 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle) {
/* 211 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 216 */     return alEntities;
/*     */   }
/*     */ 
/*     */   public void invalidateChildEntityIdsCache()
/*     */   {
/* 222 */     synchronized (this.m_objChildEntityIdsLock)
/*     */     {
/* 224 */       this.m_alChildEntityIds = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void populateEntityRelationshipDescriptions(DBTransaction a_transaction, AssetEntity a_entity)
/*     */     throws Bn2Exception
/*     */   {
/* 238 */     String ksMethodName = "populateEntityRelationshipDescriptions";
/* 239 */     Connection con = null;
/* 240 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 242 */     if (transaction == null)
/*     */     {
/* 244 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 249 */       con = transaction.getConnection();
/*     */ 
/* 251 */       String sSql = "SELECT * FROM EntityRelationshipDescription erd WHERE AssetEntityId=?";
/* 252 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 253 */       psql.setLong(1, a_entity.getId());
/* 254 */       ResultSet rs = psql.executeQuery();
/* 255 */       long lRelationshipTypeId = -1L;
/*     */ 
/* 257 */       while (rs.next())
/*     */       {
/* 259 */         lRelationshipTypeId = rs.getLong("RelationshipTypeId");
/* 260 */         if (lRelationshipTypeId == 1L)
/*     */         {
/* 262 */           a_entity.setPeerRelationshipToName(rs.getString("ToName"));
/* 263 */           a_entity.setPeerRelationshipToNamePlural(rs.getString("ToNamePlural")); continue;
/*     */         }
/* 265 */         if (lRelationshipTypeId != 2L)
/*     */           continue;
/* 267 */         a_entity.setChildRelationshipFromName(rs.getString("FromName"));
/* 268 */         a_entity.setChildRelationshipFromNamePlural(rs.getString("FromNamePlural"));
/* 269 */         a_entity.setChildRelationshipToName(rs.getString("ToName"));
/* 270 */         a_entity.setChildRelationshipToNamePlural(rs.getString("ToNamePlural"));
/*     */       }
/*     */ 
/* 274 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 278 */       if ((transaction != null) && (a_transaction == null))
/*     */         try {
/* 280 */           transaction.rollback();
/*     */         } catch (SQLException sqle) {
/*     */         }
/* 283 */       this.m_logger.error("AssetEntityRelationshipManager.populateEntityRelationshipDescriptions : SQL Exception : " + e);
/* 284 */       throw new Bn2Exception("AssetEntityRelationshipManager.populateEntityRelationshipDescriptions : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 289 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 293 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 297 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void saveEntityRelationshipDescriptions(DBTransaction a_transaction, AssetEntity a_entity)
/*     */     throws Bn2Exception
/*     */   {
/* 315 */     String ksMethodName = "saveEntityRelationshipDescriptions";
/* 316 */     Connection con = null;
/* 317 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 319 */     if (transaction == null)
/*     */     {
/* 321 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 326 */       con = transaction.getConnection();
/*     */ 
/* 328 */       String sSql = "DELETE FROM TranslatedERDescription WHERE AssetEntityId=?";
/* 329 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 330 */       psql.setLong(1, a_entity.getId());
/* 331 */       psql.executeUpdate();
/* 332 */       psql.close();
/*     */ 
/* 334 */       sSql = "DELETE FROM EntityRelationshipDescription WHERE AssetEntityId=?";
/* 335 */       psql = con.prepareStatement(sSql);
/* 336 */       psql.setLong(1, a_entity.getId());
/* 337 */       psql.executeUpdate();
/* 338 */       psql.close();
/*     */ 
/* 340 */       sSql = "INSERT INTO EntityRelationshipDescription (AssetEntityId, RelationshipTypeId, FromName, FromNamePlural, ToName, ToNamePlural) VALUES (?,?,?,?,?,?)";
/*     */ 
/* 348 */       psql = con.prepareStatement(sSql);
/*     */ 
/* 351 */       if ((StringUtil.stringIsPopulated(a_entity.getChildRelationshipFromName())) || (StringUtil.stringIsPopulated(a_entity.getChildRelationshipToName())) || (StringUtil.stringIsPopulated(a_entity.getChildRelationshipFromNamePlural())) || (StringUtil.stringIsPopulated(a_entity.getChildRelationshipToNamePlural())))
/*     */       {
/* 356 */         psql.setLong(1, a_entity.getId());
/* 357 */         psql.setLong(2, 2L);
/* 358 */         psql.setString(3, a_entity.getChildRelationshipFromName());
/* 359 */         psql.setString(4, a_entity.getChildRelationshipFromNamePlural());
/* 360 */         psql.setString(5, a_entity.getChildRelationshipToName());
/* 361 */         psql.setString(6, a_entity.getChildRelationshipToNamePlural());
/* 362 */         psql.executeUpdate();
/*     */       }
/*     */ 
/* 366 */       if ((StringUtil.stringIsPopulated(a_entity.getPeerRelationshipToName())) || (StringUtil.stringIsPopulated(a_entity.getPeerRelationshipToNamePlural())))
/*     */       {
/* 369 */         psql.setLong(1, a_entity.getId());
/* 370 */         psql.setLong(2, 1L);
/*     */ 
/* 372 */         psql.setString(3, a_entity.getPeerRelationshipToName());
/* 373 */         psql.setString(4, a_entity.getPeerRelationshipToNamePlural());
/* 374 */         psql.setString(5, a_entity.getPeerRelationshipToName());
/* 375 */         psql.setString(6, a_entity.getPeerRelationshipToNamePlural());
/* 376 */         psql.executeUpdate();
/*     */       }
/*     */ 
/* 379 */       psql.close();
/*     */ 
/* 382 */       if ((a_entity.getTranslations() != null) && (a_entity.getTranslations().size() > 0))
/*     */       {
/* 384 */         for (int i = 0; i < a_entity.getTranslations().size(); i++)
/*     */         {
/* 386 */           AssetEntity.Translation translation = (AssetEntity.Translation)a_entity.getTranslations().elementAt(i);
/*     */ 
/* 388 */           if ((StringUtil.stringIsPopulated(translation.getChildRelationshipFromName())) || (StringUtil.stringIsPopulated(translation.getChildRelationshipFromNamePlural())) || (StringUtil.stringIsPopulated(translation.getChildRelationshipToName())) || (StringUtil.stringIsPopulated(translation.getChildRelationshipToNamePlural())))
/*     */           {
/* 393 */             int iCol = 1;
/* 394 */             sSql = "INSERT INTO TranslatedERDescription (AssetEntityId, RelationshipTypeId, FromName, FromNamePlural, ToName, ToNamePlural, LanguageId) VALUES (?,?,?,?,?,?,?)";
/* 395 */             psql = con.prepareStatement(sSql);
/* 396 */             psql.setLong(iCol++, translation.getEntityId());
/* 397 */             psql.setLong(iCol++, 2L);
/* 398 */             psql.setString(iCol++, translation.getChildRelationshipFromName());
/* 399 */             psql.setString(iCol++, translation.getChildRelationshipFromNamePlural());
/* 400 */             psql.setString(iCol++, translation.getChildRelationshipToName());
/* 401 */             psql.setString(iCol++, translation.getChildRelationshipToNamePlural());
/* 402 */             psql.setLong(iCol++, translation.getLanguage().getId());
/* 403 */             psql.executeUpdate();
/* 404 */             psql.close();
/*     */           }
/*     */ 
/* 407 */           if ((!StringUtil.stringIsPopulated(translation.getPeerRelationshipToName())) && (!StringUtil.stringIsPopulated(translation.getPeerRelationshipToNamePlural()))) {
/*     */             continue;
/*     */           }
/* 410 */           int iCol = 1;
/* 411 */           sSql = "INSERT INTO TranslatedERDescription (AssetEntityId, RelationshipTypeId, FromName, FromNamePlural, ToName, ToNamePlural, LanguageId) VALUES (?,?,?,?,?,?,?)";
/* 412 */           psql = con.prepareStatement(sSql);
/* 413 */           psql.setLong(iCol++, translation.getEntityId());
/* 414 */           psql.setLong(iCol++, 1L);
/* 415 */           psql.setString(iCol++, translation.getPeerRelationshipToName());
/* 416 */           psql.setString(iCol++, translation.getPeerRelationshipToNamePlural());
/*     */ 
/* 418 */           psql.setString(iCol++, translation.getPeerRelationshipToName());
/* 419 */           psql.setString(iCol++, translation.getPeerRelationshipToNamePlural());
/* 420 */           psql.setLong(iCol++, translation.getLanguage().getId());
/* 421 */           psql.executeUpdate();
/* 422 */           psql.close();
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 429 */       if ((transaction != null) && (a_transaction == null))
/*     */         try {
/* 431 */           transaction.rollback();
/*     */         } catch (SQLException sqle) {
/*     */         }
/* 434 */       this.m_logger.error("AssetEntityRelationshipManager.saveEntityRelationshipDescriptions : SQL Exception : " + e);
/* 435 */       throw new Bn2Exception("AssetEntityRelationshipManager.saveEntityRelationshipDescriptions : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 440 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 444 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 448 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList<AssetEntityRelationship> getAllowableAssetEntityRelationships(DBTransaction a_transaction, long a_lEntityId, long a_lRelationshipTypeId)
/*     */     throws Bn2Exception
/*     */   {
/* 466 */     ArrayList alRelationships = new ArrayList();
/* 467 */     AssetEntityRelationship relationship = null;
/*     */ 
/* 469 */     String sSql = "SELECT aer.RelatesToAssetEntityId aerRelatesToAssetEntityId, aer.DefaultRelationshipCategoryId aerDRCategoryId, aer.RelationshipDescriptionLabel aerRelationshipDescriptionLbl, aer.AssetEntityId entityId, ae.IsCategoryExtension FROM AllowableEntityRelationship aer LEFT JOIN AssetEntity ae ON aer.RelatesToAssetEntityId=ae.Id WHERE aer.AssetEntityId=? AND aer.RelationshipTypeId=?";
/*     */     try
/*     */     {
/* 481 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 483 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 484 */       psql.setLong(1, a_lEntityId);
/* 485 */       psql.setLong(2, a_lRelationshipTypeId);
/* 486 */       ResultSet rs = psql.executeQuery();
/* 487 */       long lLastId = -1L;
/*     */ 
/* 489 */       while (rs.next())
/*     */       {
/* 491 */         if (rs.getLong("aerRelatesToAssetEntityId") == lLastId)
/*     */           continue;
/* 493 */         if (relationship != null)
/*     */         {
/* 495 */           alRelationships.add(relationship);
/*     */         }
/* 497 */         relationship = buildRelationship(rs);
/* 498 */         lLastId = rs.getLong("aerRelatesToAssetEntityId");
/*     */       }
/*     */ 
/* 502 */       if (relationship != null)
/*     */       {
/* 504 */         alRelationships.add(relationship);
/*     */       }
/*     */ 
/* 507 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 511 */       throw new SQLStatementException(sSql, e);
/*     */     }
/*     */ 
/* 514 */     return alRelationships;
/*     */   }
/*     */ 
/*     */   public Vector<AssetEntityRelationship> getParentRelationshipsForAssetEntity(DBTransaction a_transaction, long a_lTargetEntityId)
/*     */     throws Bn2Exception
/*     */   {
/* 527 */     String ksMethodName = "getParentRelationshipForAssetEntity";
/*     */ 
/* 529 */     Vector vRelationships = new Vector();
/* 530 */     Connection con = null;
/* 531 */     AssetEntityRelationship relationship = null;
/* 532 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 534 */     if (transaction == null)
/*     */     {
/* 536 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 541 */       con = transaction.getConnection();
/*     */ 
/* 544 */       String sSql = "SELECT aer.AssetEntityId entityId, aer.RelationshipTypeId aerRelationshipTypeId, aer.RelatesToAssetEntityId aerRelatesToAssetEntityId, aer.DefaultRelationshipCategoryId aerDRCategoryId, aer.RelationshipDescriptionLabel aerRelationshipDescriptionLbl, ae.IsCategoryExtension FROM AllowableEntityRelationship aer LEFT JOIN AssetEntity ae ON aer.RelatesToAssetEntityId=ae.Id WHERE aer.RelatesToAssetEntityId=? AND aer.RelationshipTypeId=?";
/*     */ 
/* 556 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 557 */       psql.setLong(1, a_lTargetEntityId);
/* 558 */       psql.setLong(2, 2L);
/* 559 */       ResultSet rs = psql.executeQuery();
/* 560 */       long lLastEntityId = 0L;
/* 561 */       long lLastRelationshipId = 0L;
/*     */ 
/* 563 */       while (rs.next())
/*     */       {
/* 565 */         if ((lLastEntityId == rs.getLong("aerAssetEntityId")) || (lLastRelationshipId == rs.getLong("aerRelationshipTypeId")))
/*     */           continue;
/* 567 */         relationship = buildRelationship(rs);
/* 568 */         vRelationships.add(relationship);
/*     */ 
/* 570 */         lLastEntityId = relationship.getRelatesFromAssetEntityId();
/* 571 */         lLastRelationshipId = rs.getLong("aerRelationshipTypeId");
/*     */       }
/*     */ 
/* 575 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 579 */       this.m_logger.error("AssetEntityRelationshipManager.getParentRelationshipForAssetEntity : SQL Exception : " + e);
/* 580 */       throw new Bn2Exception("AssetEntityRelationshipManager.getParentRelationshipForAssetEntity : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 585 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 589 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 593 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 598 */     return vRelationships;
/*     */   }
/*     */ 
/*     */   public void saveAllowableEntityRelationships(DBTransaction a_transaction, long a_lEntityId, long a_lRelationshipTypeId, ArrayList<AssetEntityRelationship> a_alRelationships)
/*     */     throws Bn2Exception
/*     */   {
/* 610 */     String ksMethodName = "saveAllowableEntityRelationships";
/* 611 */     Connection con = null;
/*     */     try
/*     */     {
/* 615 */       con = a_transaction.getConnection();
/*     */ 
/* 617 */       deleteAllowableEntityRelationships(a_transaction, a_lEntityId, a_lRelationshipTypeId);
/*     */       String sSql;
/* 619 */       if (a_alRelationships != null)
/*     */       {
/* 621 */         sSql = "INSERT INTO AllowableEntityRelationship (AssetEntityId,RelationshipTypeId,RelatesToAssetEntityId,DefaultRelationshipCategoryId,RelationshipDescriptionLabel) VALUES (?,?,?,?,?)";
/*     */ 
/* 625 */         for (AssetEntityRelationship aer : a_alRelationships)
/*     */         {
/* 627 */           PreparedStatement psql = con.prepareStatement(sSql);
/* 628 */           psql.setLong(1, a_lEntityId);
/* 629 */           psql.setLong(2, a_lRelationshipTypeId);
/* 630 */           long lRelatesToId = aer.getRelatesToAssetEntityId();
/*     */ 
/* 633 */           if (lRelatesToId == -1L)
/*     */           {
/* 635 */             lRelatesToId = a_lEntityId;
/*     */           }
/*     */ 
/* 638 */           DBUtil.setFieldIdOrNull(psql, 3, lRelatesToId);
/* 639 */           DBUtil.setFieldIdOrNull(psql, 4, aer.getDefaultRelationshipCategoryId());
/* 640 */           psql.setString(5, aer.getRelationshipDescriptionLabel());
/*     */ 
/* 642 */           psql.executeUpdate();
/* 643 */           psql.close();
/*     */         }
/*     */       }
/* 646 */       invalidateChildEntityIdsCache();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 650 */       this.m_logger.error("AssetEntityRelationshipManager.saveAllowableEntityRelationships : SQL Exception : " + e);
/* 651 */       throw new Bn2Exception("AssetEntityRelationshipManager.saveAllowableEntityRelationships : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteAllowableEntityRelationships(DBTransaction a_transaction, long a_lEntityId, long a_lRelationshipTypeId)
/*     */     throws Bn2Exception
/*     */   {
/* 663 */     String ksMethodName = "deleteAllowableEntityRelationships";
/* 664 */     Connection con = null;
/*     */     try
/*     */     {
/* 668 */       con = a_transaction.getConnection();
/*     */ 
/* 670 */       String sSql = "DELETE FROM AllowableEntityRelationship WHERE AssetEntityId=? AND RelationshipTypeId=?";
/* 671 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 672 */       psql.setLong(1, a_lEntityId);
/* 673 */       psql.setLong(2, a_lRelationshipTypeId);
/* 674 */       psql.executeUpdate();
/* 675 */       psql.close();
/*     */ 
/* 677 */       invalidateChildEntityIdsCache();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 681 */       this.m_logger.error("AssetEntityRelationshipManager.deleteAllowableEntityRelationships : SQL Exception : " + e);
/* 682 */       throw new Bn2Exception("AssetEntityRelationshipManager.deleteAllowableEntityRelationships : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validateAssetRelationships(DBTransaction a_transaction, long a_lOriginalAssetEntityId, String a_sTargetAssetIds, long a_lRelationshipTypeId, Language a_language)
/*     */     throws InvalidRelationshipException, Bn2Exception
/*     */   {
/* 698 */     String ksMethodName = "validateAssetRelationships";
/*     */ 
/* 700 */     Connection con = null;
/* 701 */     PreparedStatement psql = null;
/* 702 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 705 */     if (StringUtils.isEmpty(a_sTargetAssetIds))
/*     */     {
/* 707 */       return;
/*     */     }
/*     */ 
/* 710 */     if (transaction == null)
/*     */     {
/* 712 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/* 715 */     AssetEntity entity = this.m_assetEntityManager.getEntity(transaction, a_lOriginalAssetEntityId);
/*     */ 
/* 717 */     if ((entity == null) || ((a_lRelationshipTypeId == 2L) && (!entity.getAllowChildren())) || ((a_lRelationshipTypeId == 1L) && (!entity.getAllowPeers())) || ((a_lRelationshipTypeId == 3L) && (!isChildEntity(a_transaction, entity.getId()))))
/*     */     {
/* 722 */       throw new InvalidRelationshipException(true);
/*     */     }
/*     */ 
/* 725 */     String[] aIds = StringUtil.convertToArray(a_sTargetAssetIds.replaceAll(" ", ""), ",");
/* 726 */     Arrays.sort(aIds);
/*     */     try
/*     */     {
/* 730 */       con = transaction.getConnection();
/*     */ 
/* 733 */       if ((a_lRelationshipTypeId == 3L) || (a_lRelationshipTypeId == 1L))
/*     */       {
/* 735 */         long lRelationshipTypeForQuery = a_lRelationshipTypeId;
/*     */ 
/* 738 */         if (a_lRelationshipTypeId == 3L)
/*     */         {
/* 741 */           lRelationshipTypeForQuery = 2L;
/*     */ 
/* 743 */           String sSql = "SELECT a.Id aId, a.AssetEntityId entityId FROM Asset a LEFT JOIN AllowableEntityRelationship aer ON aer.AssetEntityId=a.AssetEntityId AND aer.RelationshipTypeId=? WHERE a.Id IN (" + a_sTargetAssetIds + ") AND aer.RelatesToAssetEntityId=?";
/*     */ 
/* 748 */           psql = con.prepareStatement(sSql);
/* 749 */           psql.setLong(1, lRelationshipTypeForQuery);
/* 750 */           psql.setLong(2, a_lOriginalAssetEntityId);
/*     */         }
/*     */         else
/*     */         {
/* 754 */           String sSql = "SELECT a.Id aId, a.AssetEntityId entityId FROM Asset a LEFT JOIN AllowableEntityRelationship aer ON (aer.AssetEntityId=a.AssetEntityId OR aer.RelatesToAssetEntityId) AND aer.RelationshipTypeId=? WHERE a.Id IN (" + a_sTargetAssetIds + ") AND " + "((aer.AssetEntityId=a.AssetEntityId AND aer.RelatesToAssetEntityId=?) OR " + "(aer.RelatesToAssetEntityId=a.AssetEntityId AND aer.AssetEntityId=?))";
/*     */ 
/* 761 */           psql = con.prepareStatement(sSql);
/* 762 */           psql.setLong(1, lRelationshipTypeForQuery);
/* 763 */           psql.setLong(2, a_lOriginalAssetEntityId);
/* 764 */           psql.setLong(3, a_lOriginalAssetEntityId);
/*     */         }
/*     */ 
/* 767 */         ResultSet rs = psql.executeQuery();
/* 768 */         ArrayList foundIds = new ArrayList(aIds.length);
/*     */ 
/* 770 */         while (rs.next())
/*     */         {
/* 772 */           long lAssetId = rs.getLong("aId");
/*     */ 
/* 775 */           if ((a_lRelationshipTypeId == 3L) || (entity.isValidPeerRelationship(rs.getLong("entityId"))))
/*     */           {
/* 778 */             foundIds.add(String.valueOf(lAssetId));
/*     */           }
/*     */         }
/*     */ 
/* 782 */         psql.close();
/*     */ 
/* 785 */         for (int i = 0; i < aIds.length; i++)
/*     */         {
/* 787 */           String sId = aIds[i];
/* 788 */           long lId = Long.parseLong(sId);
/*     */ 
/* 791 */           if (foundIds.contains(sId)) {
/*     */             continue;
/*     */           }
/* 794 */           String sRelName = null;
/* 795 */           if (a_lRelationshipTypeId == 3L)
/*     */           {
/* 797 */             sRelName = entity.getChildRelationshipFromName();
/*     */           }
/*     */           else
/*     */           {
/* 801 */             sRelName = entity.getPeerRelationshipToName();
/*     */           }
/*     */ 
/* 804 */           psql.close();
/* 805 */           throw new InvalidRelationshipException(false, lId, sRelName);
/*     */         }
/*     */ 
/*     */       }
/* 809 */       else if (a_lRelationshipTypeId == 2L)
/*     */       {
/* 812 */         String sSql = "SELECT a.Id, a.AssetEntityId FROM Asset a WHERE a.Id IN (" + a_sTargetAssetIds + ")";
/*     */ 
/* 815 */         psql = con.prepareStatement(sSql);
/* 816 */         ResultSet rs = psql.executeQuery();
/* 817 */         ArrayList foundIds = new ArrayList(aIds.length);
/*     */ 
/* 819 */         while (rs.next())
/*     */         {
/* 821 */           long lAssetId = rs.getLong("Id");
/*     */ 
/* 824 */           foundIds.add(String.valueOf(lAssetId));
/*     */ 
/* 827 */           if ((!entity.getAllowChildren()) || (!entity.isValidChildRelationship(rs.getLong("AssetEntityId"))))
/*     */           {
/* 829 */             psql.close();
/* 830 */             throw new InvalidRelationshipException(false, lAssetId, entity.getChildRelationshipToName());
/*     */           }
/*     */         }
/*     */ 
/* 834 */         psql.close();
/*     */ 
/* 837 */         for (int i = 0; i < aIds.length; i++)
/*     */         {
/* 839 */           String sId = aIds[i];
/*     */ 
/* 842 */           if (foundIds.contains(sId))
/*     */             continue;
/* 844 */           throw new InvalidRelationshipException(false, Long.parseLong(sId), entity.getChildRelationshipToName());
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 851 */       this.m_logger.error("AssetEntityRelationshipManager.validateAssetRelationships : SQL Exception : " + e);
/* 852 */       throw new Bn2Exception("AssetEntityRelationshipManager.validateAssetRelationships : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 857 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 861 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 865 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private AssetEntityRelationship buildRelationship(ResultSet a_rs)
/*     */     throws SQLException
/*     */   {
/* 875 */     AssetEntityRelationship relationship = new AssetEntityRelationship();
/* 876 */     relationship.setRelatesToAssetEntityId(a_rs.getLong("aerRelatesToAssetEntityId"));
/* 877 */     relationship.setRelatesFromAssetEntityId(a_rs.getLong("entityId"));
/* 878 */     relationship.setRelatesToCategoryExtensionType(new Boolean(a_rs.getBoolean("IsCategoryExtension")));
/* 879 */     relationship.setDefaultRelationshipCategoryId(a_rs.getLong("aerDRCategoryId"));
/* 880 */     relationship.setRelationshipDescriptionLabel(a_rs.getString("aerRelationshipDescriptionLbl"));
/*     */ 
/* 882 */     return relationship;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager
 * JD-Core Version:    0.6.0
 */