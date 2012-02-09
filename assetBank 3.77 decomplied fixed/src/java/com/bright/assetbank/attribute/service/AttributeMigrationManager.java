/*     */ package com.bright.assetbank.attribute.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeType;
/*     */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.attribute.util.AttributeValueDBUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.bean.JDBCValue;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AttributeMigrationManager extends Bn2Manager
/*     */ {
/*  80 */   private static String c_ksClassName = AttributeMigrationManager.class.getSimpleName();
/*     */   private AttributeManager m_attributeManager;
/*     */   private AttributeStorageManager m_attributeStorageManager;
/*     */   private DBTransactionManager m_transactionManager;
/*     */ 
/*     */   public void migrate()
/*     */     throws Bn2Exception
/*     */   {
/* 101 */     String ksMethodName = "migrate";
/*     */ 
/* 110 */     preMigrationCheck();
/*     */ 
/* 112 */     createStorageForAttributes();
/*     */ 
/* 114 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 117 */       Connection con = transaction.getConnection();
/*     */ 
/* 119 */       migrateValuePerAssetAttributesAndTranslations(con);
/*     */ 
/* 121 */       migrateListAttributes(con);
/*     */ 
/* 123 */       migrateFilterAttributes(con);
/*     */ 
/* 125 */       populateLastAttributeValue();
/*     */ 
/* 127 */       disableOldTables(con);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/*     */       try
/*     */       {
/* 133 */         transaction.rollback();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 137 */         this.m_logger.error("SQL Exception whilst rolling back connection ", sqle);
/*     */       }
/*     */ 
/* 140 */       throw e;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       try
/*     */       {
/* 146 */         transaction.rollback();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 150 */         this.m_logger.error("SQL Exception whilst rolling back connection ", sqle);
/*     */       }
/*     */ 
/* 153 */       throw new Bn2Exception("SQL Exception in migrate", e);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 159 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 163 */         this.m_logger.error("SQL Exception whilst trying to close connection ", sqle);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void preMigrationCheck()
/*     */     throws Bn2Exception
/*     */   {
/* 177 */     String ksMethodName = "preMigrationCheck";
/* 178 */     this.m_logger.info(c_ksClassName + ": performing pre-migration checks");
/*     */ 
/* 180 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 183 */       Connection con = transaction.getConnection();
/* 184 */       ApplicationSql applicationSql = SQLGenerator.getInstance();
/*     */ 
/* 187 */       String sSQL = "SELECT av.Id, av.Value FROM AttributeValue av JOIN Attribute a ON a.Id = av.AttributeId JOIN AttributeType atype ON atype.Id = a.AttributeTypeId WHERE atype.AttributeStorageTypeId = " + AttributeStorageType.LIST.getId() + " " + "AND " + applicationSql.getLengthUpperBoundFunctionForLargeTextField("Value") + " > 255";
/*     */ 
/* 194 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*     */ 
/* 196 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 198 */       if (rs.next())
/*     */       {
/* 200 */         throw new Bn2Exception("Found some list attribute values whose value is too long to migrate. You can see which they are by running the following SQL: " + sSQL);
/*     */       }
/*     */ 
/* 204 */       psql.close();
/*     */ 
/* 211 */       DatabaseMetaData databaseMetaData = con.getMetaData();
/* 212 */       String sDriverName = databaseMetaData.getDriverName();
/* 213 */       if ((sDriverName.toLowerCase().startsWith("oracle")) && (databaseMetaData.getDriverMajorVersion() < 11))
/*     */       {
/* 216 */         throw new Bn2Exception("The JDBC driver that you have installed (" + sDriverName + " " + databaseMetaData.getDriverVersion() + ") is too old " + "for this version of Asset Bank. See " + "http://www.assetbank.co.uk/go/knowledgebase/jdbc-driver.html" + " for instructions on how to upgrade the JDBC driver.");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 226 */       throw new Bn2Exception("SQL Exception in preMigrationCheck", e);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 232 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 236 */         this.m_logger.error("SQL Exception whilst trying to close connection ", sqle);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createStorageForAttributes()
/*     */     throws Bn2Exception
/*     */   {
/* 253 */     Collection<Attribute> attributes = this.m_attributeManager.getAttributes(null, -1L, false, true, 2, false);
/*     */ 
/* 255 */     for (Attribute attribute : attributes)
/*     */     {
/* 257 */       this.m_attributeStorageManager.createStorageForAttribute(null, attribute);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void migrateValuePerAssetAttributesAndTranslations(Connection a_con)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 264 */     migrateValuePerAssetAttributesAndTranslations(a_con, null);
/*     */   }
/*     */ 
/*     */   void migrateValuePerAssetAttributesAndTranslations(Connection a_con, Set<Long> a_assetIds)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 281 */     List<Attribute> attributes = this.m_attributeManager.getAttributes(null, -1L, false, true, 2, false);
/* 282 */     Map attributesById = new HashMap();
/* 283 */     for (Attribute attribute : attributes)
/*     */     {
/* 285 */       attributesById.put(Long.valueOf(attribute.getId()), attribute);
/*     */     }
/*     */ 
/* 290 */     Collection valuePerAssetAttributes = new ArrayList();
/* 291 */     for (Attribute attribute : attributes)
/*     */     {
/* 293 */       if (!attribute.getStatic())
/*     */       {
/* 295 */         AttributeType attributeType = this.m_attributeManager.getAttributeTypeById(attribute.getTypeId());
/* 296 */         if (attributeType.getAttributeStorageType() == AttributeStorageType.VALUE_PER_ASSET)
/*     */         {
/* 298 */           valuePerAssetAttributes.add(attribute);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 304 */     migrateValuePerAssetAttributes(a_con, attributesById, valuePerAssetAttributes, a_assetIds);
/* 305 */     migrateTranslatedValuePerAssetAttributes(a_con, attributesById, valuePerAssetAttributes, a_assetIds);
/*     */   }
/*     */ 
/*     */   private void migrateValuePerAssetAttributes(Connection a_con, Map<Long, Attribute> a_attributesById, Collection<Attribute> a_valuePerAssetAttributes, Set<Long> a_assetIds)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 317 */     String ksMethodName = "migrateValuePerAssetAttributes";
/*     */ 
/* 319 */     this.m_logger.info(c_ksClassName + ": migrating per-asset attributes");
/*     */ 
/* 322 */     Set<Long> valuePerAssetAttributeIds = new HashSet();
/* 323 */     for (Attribute attribute : a_valuePerAssetAttributes)
/*     */     {
/* 325 */       valuePerAssetAttributeIds.add(Long.valueOf(attribute.getId()));
/*     */     }
/*     */ 
/* 328 */     ApplicationSql applicationSql = SQLGenerator.getInstance();
/*     */ 
/* 330 */     String sAssetIdRestriction = "AND aav.AssetId IN (" + DBUtil.getPlaceholders(a_assetIds.size()) + ") ";
/*     */ 
/* 333 */     String sSQL = "SELECT aav.AssetId, av.AttributeId, av.Value, av.AdditionalValue, av.DateValue, av.DateTimeValue FROM AssetAttributeValue aav JOIN AttributeValue av ON av.Id = aav.AttributeValueId WHERE av.AttributeId IN (" + DBUtil.getPlaceholders(valuePerAssetAttributeIds.size()) + ") " + sAssetIdRestriction + "AND ((" + applicationSql.getFieldNotEmptyStatement("av.Value") + ")" + "  OR (" + applicationSql.getFieldNotEmptyStatement("av.AdditionalValue") + ")" + "  OR av.DateValue IS NOT NULL" + "  OR av.DateTimeValue IS NOT NULL) " + "ORDER BY aav.AssetId";
/*     */ 
/* 345 */     this.m_logger.debug(c_ksClassName + ": " + sSQL + " " + valuePerAssetAttributeIds + ", " + a_assetIds);
/*     */ 
/* 347 */     PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 348 */     int iParam = 1;
/* 349 */     for (Long attributeId : valuePerAssetAttributeIds)
/*     */     {
/* 351 */       psql.setLong(iParam++, attributeId.longValue());
/*     */     }
/* 353 */     if (a_assetIds != null)
/*     */     {
/* 355 */       for (Long assetId : a_assetIds)
/*     */       {
/* 357 */         psql.setLong(iParam++, assetId.longValue());
/*     */       }
/*     */     }
/*     */ 
/* 361 */     ResultSet rs = psql.executeQuery();
/* 362 */     long lAssetId = -1L;
/* 363 */     HashMap valuesByAttributeId = null;
/* 364 */     long lAssetCount = 0L;
/*     */     while (true)
/*     */     {
/* 367 */       boolean bHaveRow = rs.next();
/*     */       long lNewAssetId;
/*     */       //long lNewAssetId;
/* 370 */       if (bHaveRow)
/*     */       {
/* 372 */         lNewAssetId = rs.getInt("AssetId");
/*     */       }
/*     */       else
/*     */       {
/* 379 */         lNewAssetId = -1L;
/*     */       }
/*     */ 
/* 383 */       if (lNewAssetId != lAssetId)
/*     */       {
/* 387 */         if (lAssetId != -1L)
/*     */         {
/* 391 */           AttributeValueDBUtil.insertAssetAttributeValues(a_con, lAssetId, a_attributesById, valuesByAttributeId);
/*     */ 
/* 393 */           lAssetCount += 1L;
/* 394 */           if (lAssetCount % 1000L == 0L)
/*     */           {
/* 396 */             this.m_logger.info(c_ksClassName + "." + "migrateValuePerAssetAttributes" + ": migrated " + lAssetCount + " assets");
/*     */           }
/*     */         }
/* 399 */         lAssetId = lNewAssetId;
/* 400 */         valuesByAttributeId = new HashMap();
/*     */       }
/*     */ 
/* 404 */       if (!bHaveRow)
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/* 409 */       long lAttributeId = rs.getLong("AttributeId");
/* 410 */       Attribute attribute = (Attribute)a_attributesById.get(Long.valueOf(lAttributeId));
/* 411 */       JDBCValue value = getValueFromResultSetForAttribute(rs, attribute);
/*     */ 
/* 413 */       if (value != null)
/*     */       {
/* 415 */         valuesByAttributeId.put(Long.valueOf(lAttributeId), value);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 420 */     psql.close();
/*     */   }
/*     */ 
/*     */   private void migrateTranslatedValuePerAssetAttributes(Connection a_con, Map<Long, Attribute> a_attributesById, Collection<Attribute> a_valuePerAssetAttributes, Set<Long> a_assetIds)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 432 */     this.m_logger.info(c_ksClassName + ": migrating translations of per-asset attributes");
/* 433 */     String ksMethodName = "migrateTranslatedValuePerAssetAttributes";
/*     */ 
/* 437 */     Set <Long>valuePerAssetAttributeIds = new HashSet();
/* 438 */     for (Attribute attribute : a_valuePerAssetAttributes)
/*     */     {
/* 440 */       valuePerAssetAttributeIds.add(Long.valueOf(attribute.getId()));
/*     */     }
/*     */ 
/* 443 */     ApplicationSql applicationSql = SQLGenerator.getInstance();
/*     */ 
/* 445 */     String sAssetIdRestriction = "AND aav.AssetId IN (" + DBUtil.getPlaceholders(a_assetIds.size()) + ") ";
/*     */ 
/* 448 */     String sSQL = "SELECT aav.AssetId, tav.LanguageId, av.AttributeId, tav.Value, tav.AdditionalValue FROM AssetAttributeValue aav JOIN AttributeValue av ON av.Id = aav.AttributeValueId JOIN TranslatedAttributeValue tav ON tav.AttributeValueId = av.Id WHERE av.AttributeId IN (" + DBUtil.getPlaceholders(valuePerAssetAttributeIds.size()) + ") " + sAssetIdRestriction + "AND tav.LanguageId <> " + 1L + " " + "AND ((" + applicationSql.getFieldNotEmptyStatement("tav.Value") + ")" + "  OR (" + applicationSql.getFieldNotEmptyStatement("tav.AdditionalValue") + ")) " + "ORDER BY aav.AssetId, tav.LanguageId";
/*     */ 
/* 460 */     PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 461 */     int iParam = 1;
/* 462 */     for (Long attributeId : valuePerAssetAttributeIds)
/*     */     {
/* 464 */       psql.setLong(iParam++, attributeId.longValue());
/*     */     }
/* 466 */     if (a_assetIds != null)
/*     */     {
/* 468 */       for (Long assetId : a_assetIds)
/*     */       {
/* 470 */         psql.setLong(iParam++, assetId.longValue());
/*     */       }
/*     */     }
/*     */ 
/* 474 */     ResultSet rs = psql.executeQuery();
/* 475 */     long lAssetId = -1L;
/* 476 */     long lLanguageId = -1L;
/* 477 */     HashMap valuesByAttributeId = null;
/* 478 */     long lTranslationCount = 0L;
/*     */     while (true)
/*     */     {
/* 481 */       boolean bHaveRow = rs.next();
/*     */       long lNewLanguageId;
/*     */       long lNewAssetId;
/*     */       //long lNewLanguageId;
/* 485 */       if (bHaveRow)
/*     */       {
/* 487 */          lNewAssetId = rs.getInt("AssetId");
/* 488 */         lNewLanguageId = rs.getInt("LanguageId");
/*     */       }
/*     */       else
/*     */       {
/* 495 */         lNewAssetId = -1L;
/* 496 */         lNewLanguageId = -1L;
/*     */       }
/*     */ 
/* 500 */       if ((lNewAssetId != lAssetId) || (lNewLanguageId != lLanguageId))
/*     */       {
/* 505 */         if (lAssetId != -1L)
/*     */         {
/* 509 */           AttributeValueDBUtil.insertAssetAttributeValues(a_con, lAssetId, lLanguageId, a_attributesById, valuesByAttributeId);
/*     */ 
/* 513 */           lTranslationCount += 1L;
/* 514 */           if (lTranslationCount % 1000L == 0L)
/*     */           {
/* 516 */             this.m_logger.info(c_ksClassName + "." + "migrateTranslatedValuePerAssetAttributes" + ": migrated " + lTranslationCount + " asset translations");
/*     */           }
/*     */         }
/* 519 */         lAssetId = lNewAssetId;
/* 520 */         lLanguageId = lNewLanguageId;
/* 521 */         valuesByAttributeId = new HashMap();
/*     */       }
/*     */ 
/* 525 */       if (!bHaveRow)
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/* 530 */       long lAttributeId = rs.getLong("AttributeId");
/* 531 */       Attribute attribute = (Attribute)a_attributesById.get(Long.valueOf(lAttributeId));
/* 532 */       JDBCValue value = getValueFromResultSetForAttribute(rs, attribute);
/*     */ 
/* 534 */       if (value != null)
/*     */       {
/* 536 */         valuesByAttributeId.put(Long.valueOf(lAttributeId), value);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 541 */     psql.close();
/*     */   }
/*     */ 
/*     */   private JDBCValue getValueFromResultSetForAttribute(ResultSet a_rs, Attribute a_attribute)
/*     */     throws SQLException
/*     */   {
/* 547 */     JDBCValue value = null;
/* 548 */     if (a_attribute.getTypeId() == 3L)
/*     */     {
/* 550 */       Date date = a_rs.getDate("DateValue");
/* 551 */       if (date != null)
/*     */       {
/* 553 */         value = new JDBCValue(91, date);
/*     */       }
/*     */     }
/* 556 */     else if (a_attribute.getTypeId() == 8L)
/*     */     {
/* 558 */       Timestamp timestamp = a_rs.getTimestamp("DateTimeValue");
/* 559 */       if (timestamp != null)
/*     */       {
/* 561 */         value = new JDBCValue(93, timestamp);
/*     */       }
/*     */     }
/* 564 */     else if (a_attribute.getTypeId() == 12L)
/*     */     {
/* 566 */       String sValue = a_rs.getString("Value");
/* 567 */       String sAdditionalValue = a_rs.getString("AdditionalValue");
/*     */ 
/* 570 */       String sPacked = AttributeUtil.packExternalDictionaryIDsAndValues(sValue, sAdditionalValue);
/* 571 */       value = new JDBCValue(12, sPacked);
/*     */     }
/*     */     else
/*     */     {
/* 576 */       String s = a_rs.getString("Value");
/*     */ 
/* 578 */       if (StringUtils.isNotEmpty(s))
/*     */       {
/* 580 */         value = new JDBCValue(12, s);
/*     */       }
/*     */     }
/* 583 */     return value;
/*     */   }
/*     */ 
/*     */   private void migrateListAttributes(Connection a_con)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 589 */     this.m_logger.info(c_ksClassName + ": migrating list attribute values");
/*     */ 
/* 591 */     ApplicationSql applicationSql = SQLGenerator.getInstance();
/*     */     try
/*     */     {
/* 596 */       applicationSql.prepareForIdentityInsert(a_con, "ListAttributeValue");
/* 597 */       String sSQL = "INSERT INTO ListAttributeValue (Id, AttributeId, IsEditable, Value, AdditionalValue, SequenceNumber, ActionOnAssetId, MapToFieldValue) SELECT av.Id, av.AttributeId, av.IsEditable, av.Value, av.AdditionalValue, av.SequenceNumber, av.ActionOnAssetId, av.MapToFieldValue FROM AttributeValue av JOIN Attribute att ON av.AttributeId = att.Id JOIN AttributeType at ON att.AttributeTypeId = at.Id WHERE at.AttributeStorageTypeId = " + AttributeStorageType.LIST.getId();
/*     */ 
/* 605 */       PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 606 */       psql.executeUpdate();
/* 607 */       psql.close();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 613 */         applicationSql.postIdentityInsert(a_con, "ListAttributeValue", "ListAttributeValueSequence");
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 617 */         this.m_logger.error("SQLException in postIdentityInsert", e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 622 */     String sSQL = "INSERT INTO TranslatedListAttributeValue (ListAttributeValueId, LanguageId, Value, AdditionalValue) SELECT tav.AttributeValueId, tav.LanguageId, tav.Value, tav.AdditionalValue FROM TranslatedAttributeValue tav JOIN ListAttributeValue lav ON tav.AttributeValueId = lav.Id";
/*     */ 
/* 627 */     PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 628 */     psql.executeUpdate();
/* 629 */     psql.close();
/*     */ 
/* 632 */     sSQL = "INSERT INTO AssetListAttributeValue (AssetId, ListAttributeValueId) SELECT aav.AssetId, aav.AttributeValueId FROM AssetAttributeValue aav JOIN ListAttributeValue lav ON aav.AttributeValueId = lav.Id";
/*     */ 
/* 637 */     psql = a_con.prepareStatement(sSQL);
/* 638 */     psql.executeUpdate();
/* 639 */     psql.close();
/*     */ 
/* 652 */     this.m_attributeManager.invalidateAttributeCache();
/*     */   }
/*     */ 
/*     */   private void migrateFilterAttributes(Connection a_con)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 658 */     this.m_logger.info(c_ksClassName + ": migrating filter storage");
/*     */ 
/* 660 */     String sSQL = "INSERT INTO FilterListAttributeValue (FilterId, ListAttributeValueId) SELECT fav.FilterId, fav.AttributeValueId FROM FilterAttributeValue fav WHERE fav.ListAttribute = 1";
/*     */ 
/* 666 */     PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 667 */     psql.executeUpdate();
/* 668 */     psql.close();
/*     */ 
/* 670 */     sSQL = "INSERT INTO FilterAssetAttributeValue (FilterId, AttributeId, Value, DateValue, DateTimeValue) SELECT fav.FilterId, av.AttributeId, av.Value, av.DateValue, av.DateTimeValue FROM FilterAttributeValue fav JOIN AttributeValue av ON av.Id = fav.AttributeValueId WHERE fav.ListAttribute = 0";
/*     */ 
/* 678 */     psql = a_con.prepareStatement(sSQL);
/* 679 */     psql.executeUpdate();
/* 680 */     psql.close();
/*     */   }
/*     */ 
/*     */   private void populateLastAttributeValue()
/*     */     throws Bn2Exception
/*     */   {
/* 690 */     this.m_logger.info(c_ksClassName + ": populating LastAttributeValue for autoincrement attributes");
/*     */ 
/* 692 */     String ksMethodName = "populateLastAttributeValue";
/*     */ 
/* 695 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 698 */       Connection con = transaction.getConnection();
/*     */ 
/* 700 */       String sSql = "SELECT a.Id aId, MAX(av.NumberValue) maxValue FROM AttributeValue av JOIN Attribute a ON a.Id = av.AttributeId WHERE a.AttributeTypeId=? GROUP BY a.Id";
/*     */ 
/* 706 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 707 */       psql.setLong(1, 11L);
/* 708 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 710 */       while (rs.next())
/*     */       {
/* 712 */         if (rs.getLong("aId") <= 0L)
/*     */           continue;
/* 714 */         sSql = "INSERT INTO LastAttributeValue (AttributeId, NumberValue) VALUES (?,?)";
/* 715 */         PreparedStatement psql2 = con.prepareStatement(sSql);
/* 716 */         psql2.setLong(1, rs.getLong("aId"));
/* 717 */         psql2.setLong(2, rs.getLong("maxValue"));
/* 718 */         psql2.executeUpdate();
/* 719 */         psql2.close();
/*     */       }
/*     */ 
/* 723 */       psql.close();
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */       try
/*     */       {
/* 729 */         transaction.rollback();
/*     */       }
/*     */       catch (SQLException sqle2)
/*     */       {
/* 733 */         this.m_logger.error(c_ksClassName + "." + "populateLastAttributeValue" + ": SQLException whilst rolling back transaction.", sqle2);
/*     */       }
/*     */ 
/* 736 */       throw new Bn2Exception(c_ksClassName + "." + "populateLastAttributeValue" + ": SQL Exception", sqle);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 743 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 747 */         this.m_logger.error(c_ksClassName + ": SQL Exception whilst trying to close connection " + sqle.getMessage(), sqle);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void disableOldTables(Connection a_con)
/*     */     throws Bn2Exception, SQLException
/*     */   {
/* 758 */     this.m_logger.info(c_ksClassName + ": renaming old tables and dropping FKs.");
/*     */ 
/* 760 */     ApplicationSql applicationSql = SQLGenerator.getInstance();
/* 761 */     String sCatalog = applicationSql.getCatalog(a_con);
/* 762 */     String sSchemaName = applicationSql.getDefaultSchemaName(a_con);
/* 763 */     String[] oldTableNames = { "AttributeValue", "TranslatedAttributeValue", "AssetAttributeValue", "FilterAttributeValue" };
/*     */ 
/* 769 */     for (String sTableName : oldTableNames)
/*     */     {
/* 771 */       applicationSql.dropAllForeignKeys(a_con, sCatalog, sSchemaName, sTableName);
/* 772 */       applicationSql.renameTable(a_con, sTableName, "BAK_" + sTableName);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 780 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeStorageManager(AttributeStorageManager a_attributeStorageManager)
/*     */   {
/* 785 */     this.m_attributeStorageManager = a_attributeStorageManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 790 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.service.AttributeMigrationManager
 * JD-Core Version:    0.6.0
 */