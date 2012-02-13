/*      */ package com.bright.assetbank.custom.bgs.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bn2web.common.util.ReloadablePropertyResourceBundle;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.custom.bgs.bean.BGSSearchCriteria;
/*      */ import com.bright.assetbank.custom.bgs.bean.BoundingBoxCriteria;
/*      */ import com.bright.assetbank.custom.bgs.bean.KeywordCriteria;
/*      */ import com.bright.assetbank.custom.bgs.bean.PlacenameCriteria;
/*      */ import com.bright.assetbank.custom.bgs.bean.PointCriteria;
/*      */ import com.bright.assetbank.custom.bgs.constant.BGSRequestConstants;
/*      */ import com.bright.assetbank.custom.bgs.constant.BGSTopoRelConstants;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.externalfilter.service.ExternalFilter;
/*      */ import com.bright.assetbank.externalfilter.service.ExternalFilterManager;
/*      */ import com.bright.assetbank.externalfilter.util.ExternalFilterUtil;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.BrightDateTime;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.search.bean.SearchQuery;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Array;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import oracle.sql.ARRAY;
/*      */ import oracle.sql.ArrayDescriptor;
/*      */ import org.apache.avalon.framework.component.ComponentException;
/*      */ import org.apache.avalon.framework.component.ComponentManager;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class BGSExternalFilter
/*      */   implements ExternalFilter, BGSRequestConstants, BGSTopoRelConstants
/*      */ {
/*      */   private static final String c_ksClassName = "BGSExternalFilter";
/*      */   private static final String c_ksDateSuffix = "!D";
/*      */   private static final String c_ksDateTimeSuffix = "!DT";
/*      */   private Log m_logger;
/*      */   private ResourceBundle m_settings;
/*      */   private DBTransactionManager m_transactionManager;
/*      */   private ExternalFilterManager m_externalFilterManager;
/*      */ 
/*      */   public BGSExternalFilter()
/*      */     throws Bn2Exception
/*      */   {
/*  113 */     String dataSourceName = FrameworkSettings.getExternalFilterTransactionManagerName();
/*      */     try
/*      */     {
/*  116 */       ComponentManager componentManager = GlobalApplication.getInstance().getComponentManager();
/*  117 */       this.m_transactionManager = ((DBTransactionManager)componentManager.lookup(dataSourceName));
/*  118 */       this.m_externalFilterManager = ((ExternalFilterManager)componentManager.lookup("ExternalFilterManager"));
/*      */     }
/*      */     catch (ComponentException e)
/*      */     {
/*  122 */       throw new Bn2Exception("BGSExternalFilter: exception getting component " + e.getKey() + " from component manager", e);
/*      */     }
/*      */ 
/*  125 */     this.m_settings = ReloadablePropertyResourceBundle.getResourceBundle("BGSExternalFilter");
/*      */   }
/*      */ 
/*      */   public void validateSearchCriteria(Map<String, String> a_criteria, List<String> a_errors)
/*      */   {
/*  131 */     bgsCriteriaFromMap(a_criteria, a_errors);
/*      */   }
/*      */ 
/*      */   public boolean emptySearchCriteria(SearchQuery a_criteria)
/*      */   {
/*  143 */     Map externalFilterCriteria = a_criteria.getExternalFilterCriteria();
/*      */ 
/*  145 */     boolean bEmpty = true;
/*      */ 
/*  147 */     if ((externalFilterCriteria != null) && (!externalFilterCriteria.isEmpty()))
/*      */     {
/*  149 */       BGSSearchCriteria criteria = bgsCriteriaFromMap(externalFilterCriteria);
/*  150 */       bEmpty = criteria.isEmpty();
/*      */     }
/*      */ 
/*  153 */     return bEmpty;
/*      */   }
/*      */ 
/*      */   public Collection<Long> externalSearch(SearchQuery a_searchQuery)
/*      */     throws Bn2Exception
/*      */   {
/*  159 */     String ksMethodName = "externalSearch";
/*  160 */     Map criteriaMap = a_searchQuery.getExternalFilterCriteria();
/*  161 */     if ((criteriaMap == null) || (criteriaMap.isEmpty()))
/*      */     {
/*  163 */       this.m_logger.info("BGSExternalFilter.externalSearch: The external filter criteria map was null or empty, returning null to indicate that no filtering should be done");
/*      */ 
/*  165 */       return null;
/*      */     }
/*      */ 
/*  170 */     if (criteriaMap.get("performance-test") != null)
/*      */     {
/*  172 */       return createPerformanceTestResults();
/*      */     }
/*      */ 
/*  175 */     BGSSearchCriteria criteria = bgsCriteriaFromMap(criteriaMap);
/*      */ 
/*  177 */     Set filterIds = null;
/*      */ 
/*  179 */     DBTransaction transaction = getTransactionManager().getNewTransaction();
/*      */     try
/*      */     {
/*  186 */       PlacenameCriteria placenameCriteria = criteria.getPlacenameCriteria();
/*  187 */       if (placenameCriteria != null)
/*      */       {
/*  189 */         filterIds = intersection(filterIds, findImagesByPlacename(transaction, placenameCriteria));
/*      */       }
/*      */ 
/*  192 */       PointCriteria pointCriteria = criteria.getPointCriteria();
/*  193 */       if (pointCriteria != null)
/*      */       {
/*  195 */         filterIds = intersection(filterIds, findImagesByPoint(transaction, pointCriteria));
/*      */       }
/*      */ 
/*  198 */       BoundingBoxCriteria boundingBoxCriteria = criteria.getBoundingBoxCriteria();
/*  199 */       if (boundingBoxCriteria != null)
/*      */       {
/*  201 */         filterIds = intersection(filterIds, findImagesByBoundingBox(transaction, boundingBoxCriteria));
/*      */       }
/*      */ 
/*  204 */       KeywordCriteria keywordCriteria = criteria.getKeywordCriteria();
/*  205 */       if (keywordCriteria != null)
/*      */       {
/*  207 */         filterIds = intersection(filterIds, findImagesByKeyword(transaction, keywordCriteria));
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  215 */         transaction.commit();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  219 */         this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */       }
/*      */     }
/*      */ 
/*  223 */     return filterIds;
/*      */   }
/*      */ 
/*      */   public void clearIndex()
/*      */     throws Bn2Exception
/*      */   {
/*  235 */     String ksMethodName = "clearIndex";
/*  236 */     String schemaPrefix = getSchemaPrefix();
/*      */ 
/*  238 */     String sSQL = null;
/*      */ 
/*  241 */     DBTransaction transaction = getTransactionManager().getNewTransaction();
/*      */     try
/*      */     {
/*  245 */       Connection con = transaction.getConnection();
/*      */ 
/*  248 */       sSQL = "DELETE FROM " + schemaPrefix + getImageTable();
/*      */ 
/*  250 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  251 */       psql.executeUpdate();
/*  252 */       psql.close();
/*      */ 
/*  255 */       sSQL = "DELETE FROM " + schemaPrefix + getImagePlacenameTable();
/*      */ 
/*  257 */       psql = con.prepareStatement(sSQL);
/*  258 */       psql.executeUpdate();
/*  259 */       psql.close();
/*      */ 
/*  262 */       sSQL = "DELETE FROM " + schemaPrefix + getImageKeywordTable();
/*      */ 
/*  264 */       psql = con.prepareStatement(sSQL);
/*  265 */       psql.executeUpdate();
/*  266 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  270 */       this.m_logger.error("BGSExternalFilter.clearIndex: exception executing SQL: " + sSQL + " : " + e.getMessage());
/*  271 */       throw new Bn2Exception("BGSExternalFilter.clearIndex: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  276 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  280 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  284 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void indexAsset(Asset a_asset, boolean a_bReindex)
/*      */     throws Bn2Exception
/*      */   {
/*  293 */     indexAssets(Collections.singletonList(a_asset), a_bReindex, false);
/*      */   }
/*      */ 
/*      */   public void indexAssets(List<Asset> a_assets, boolean a_bReindex, boolean a_bQuick, boolean a_bOnlyUsageChanged)
/*      */     throws Bn2Exception
/*      */   {
/*  299 */     String ksMethodName = "indexAssets";
/*      */ 
/*  301 */     this.m_logger.debug("BGSExternalFilter.indexAssets(" + a_assets.size() + " assets, a_bReindex = " + a_bReindex + ", a_bQuick = " + a_bQuick + ", a_bOnlyUsageChanged = " + a_bOnlyUsageChanged + ")");
/*      */ 
/*  303 */     if (a_bOnlyUsageChanged)
/*      */     {
/*  305 */       this.m_logger.debug("BGSExternalFilter.indexAssets: only usage changed, not updating BGS tables");
/*      */     }
/*      */     else
/*      */     {
/*  309 */       indexAssets(a_assets, a_bReindex, a_bQuick);
/*      */     }
/*      */   }
/*      */ 
/*      */   private synchronized void indexAssets(List<Asset> a_assets, boolean a_bReindex, boolean a_bQuick) throws Bn2Exception
/*      */   {
/*  315 */     String ksMethodName = "indexAssets";
/*  316 */     this.m_logger.debug("In BGSExternalFilter:indexAssets");
/*  317 */     String schemaPrefix = getSchemaPrefix();
/*      */ 
/*  320 */     DBTransaction transaction = getTransactionManager().getNewTransaction();
/*      */     try
/*      */     {
/*  324 */       Connection con = transaction.getConnection();
/*      */ 
/*  349 */       if ((a_bReindex) || (a_bQuick))
/*      */       {
/*  351 */         List assetIds = new ArrayList();
/*  352 */         for (Asset asset : a_assets)
/*      */         {
/*  354 */           assetIds.add(new Long(asset.getId()));
/*      */         }
/*      */ 
/*  357 */         removeAssets(con, assetIds);
/*      */       }
/*      */ 
/*  361 */       Vector vecSpecialFields = new Vector();
/*  362 */       vecSpecialFields.add("x");
/*  363 */       vecSpecialFields.add("y");
/*  364 */       vecSpecialFields.add("pointEpsgCode");
/*  365 */       vecSpecialFields.add("placenameIds");
/*  366 */       vecSpecialFields.add("keywordIds");
/*      */ 
/*  371 */       Vector <String> vecExtraAttributes = new Vector();
/*  372 */       Vector <String> vecExtraColumns = new Vector();
/*  373 */       HashMap hmAttributeToFieldNameMap = this.m_externalFilterManager.getAttributeToFieldNameMap();
/*  374 */       HashMap hmDateFields = new HashMap();
/*  375 */       HashMap hmDateTimeFields = new HashMap();
/*      */ 
/*  377 */       for (Iterator i$ = hmAttributeToFieldNameMap.keySet().iterator(); i$.hasNext(); ) { long lAttrId = ((Long)i$.next()).longValue();
/*      */ 
/*  379 */         String sFieldName = (String)hmAttributeToFieldNameMap.get(Long.valueOf(lAttrId));
/*  380 */         String sColName = sFieldName;
/*      */ 
/*  383 */         if (!vecSpecialFields.contains(sFieldName))
/*      */         {
/*  386 */           if (sFieldName.endsWith("!D"))
/*      */           {
/*  388 */             sColName = sFieldName.substring(0, sFieldName.length() - "!D".length());
/*  389 */             hmDateFields.put(sFieldName, sColName);
/*      */           }
/*  391 */           if (sFieldName.endsWith("!DT"))
/*      */           {
/*  393 */             sColName = sFieldName.substring(0, sFieldName.length() - "!DT".length());
/*  394 */             hmDateTimeFields.put(sFieldName, sColName);
/*      */           }
/*      */ 
/*  398 */           vecExtraAttributes.add(sFieldName);
/*  399 */           vecExtraColumns.add(sColName);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  405 */       PreparedStatement psInsImage = null;
/*  406 */       PreparedStatement psInsImagePlacename = null;
/*  407 */       PreparedStatement psInsImageKeyword = null;
/*      */ 
/*  410 */       String sExtraCols = "";
/*  411 */       String sExtraPlaceholders = "";
/*  412 */       for (String sField : vecExtraColumns)
/*      */       {
/*  414 */         sExtraCols = sExtraCols + ", " + sField;
/*  415 */         sExtraPlaceholders = sExtraPlaceholders + ",?";
/*      */       }
/*  417 */       String sImageSql = "INSERT INTO " + schemaPrefix + getImageTable() + " (image_id, caption, x, y, epsg_code" + sExtraCols + ")" + " VALUES (?,?,?,?,?" + sExtraPlaceholders + ")";
/*      */ 
/*  421 */       String sImagePlacenameSql = "INSERT INTO " + schemaPrefix + getImagePlacenameTable() + " (image_id, placename, gazetteer_placename_id) " + " VALUES (?,?,?)";
/*      */ 
/*  425 */       String sImageKeywordSql = "INSERT INTO " + schemaPrefix + getImageKeywordTable() + " (image_id, keyword, thesaurus_keyword_id)" + " VALUES (?,?,?)";
/*      */ 
/*  429 */       psInsImage = con.prepareStatement(sImageSql);
/*  430 */       psInsImagePlacename = con.prepareStatement(sImagePlacenameSql);
/*  431 */       psInsImageKeyword = con.prepareStatement(sImageKeywordSql);
/*      */ 
/*  435 */       for (Asset asset : a_assets)
/*      */       {
/*  438 */         long lEntityId = 0L;
/*  439 */         if (asset.getEntity() != null)
/*      */         {
/*  441 */           lEntityId = asset.getEntity().getId();
/*      */         }
/*  443 */         boolean bExclude = (lEntityId > 0L) && (this.m_externalFilterManager.isAssetTypeExcluded(lEntityId));
/*      */ 
/*  445 */         if (!bExclude)
/*      */         {
/*  448 */           BigDecimal x = this.m_externalFilterManager.getFieldValueAsBigDecimal(asset, "x");
/*  449 */           BigDecimal y = this.m_externalFilterManager.getFieldValueAsBigDecimal(asset, "y");
/*  450 */           long epsgCode = this.m_externalFilterManager.getMappedFieldValueAsLong(asset, "pointEpsgCode");
/*      */ 
/*  456 */           if ((x == null) || (y == null) || (epsgCode == 0L))
/*      */           {
/*  458 */             x = y = null;
/*  459 */             epsgCode = 0L;
/*      */           }
/*      */ 
/*  464 */           AttributeValue avPlacenameIds = this.m_externalFilterManager.getFieldValue(asset, "placenameIds");
/*      */           List placenameIds;
/*      */           List untrimmedPlacenames;
/*  465 */           if (avPlacenameIds != null)
/*      */           {
/*  467 */             String sPlacenameIds = avPlacenameIds.getValue();
/*  468 */             placenameIds = convertToUniqueListOfLongs(sPlacenameIds, ",");
/*  469 */             String sPlacenames = avPlacenameIds.getAdditionalValue();
/*      */ 
/*  473 */             untrimmedPlacenames = convertToUniqueList(sPlacenames, "|");
/*  474 */             if (placenameIds.size() != untrimmedPlacenames.size())
/*      */             {
/*  476 */               this.m_logger.error("placenameIds list is not the same size as placenames (" + placenameIds.size() + " != " + untrimmedPlacenames.size() + " assetID:" + asset.getId() + ")");
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  481 */             placenameIds = Collections.emptyList();
/*  482 */             untrimmedPlacenames = Collections.emptyList();
/*      */           }
/*      */ 
/*  487 */           AttributeValue avKeywordIds = this.m_externalFilterManager.getFieldValue(asset, "keywordIds");
/*      */           List keywordIds;
/*      */           List untrimmedKeywords;
/*  488 */           if (avKeywordIds != null)
/*      */           {
/*  490 */             String sKeywordIds = avKeywordIds.getValue();
/*  491 */             keywordIds = convertToUniqueListOfLongs(sKeywordIds, ",");
/*  492 */             String sKeywords = avKeywordIds.getAdditionalValue();
/*      */ 
/*  494 */             untrimmedKeywords = convertToUniqueList(sKeywords, "|");
/*  495 */             if (keywordIds.size() != untrimmedKeywords.size())
/*      */             {
/*  497 */               this.m_logger.error("keywordIds list is not the same size as keywords (" + keywordIds.size() + " != " + untrimmedKeywords.size() + ")");
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  502 */             keywordIds = Collections.emptyList();
/*  503 */             untrimmedKeywords = Collections.emptyList();
/*      */           }
/*      */ 
/*  507 */           int i = 1;
/*  508 */           psInsImage.setLong(i++, asset.getId());
/*  509 */           psInsImage.setString(i++, asset.getName());
/*  510 */           DBUtil.setFieldBigDecimalOrNull(psInsImage, i++, x);
/*  511 */           DBUtil.setFieldBigDecimalOrNull(psInsImage, i++, y);
/*  512 */           DBUtil.setFieldLongOrNull(psInsImage, i++, epsgCode);
/*      */ 
/*  515 */           for (String sField : vecExtraAttributes)
/*      */           {
/*  518 */             boolean bIsDate = hmDateFields.containsKey(sField);
/*  519 */             if (bIsDate)
/*      */             {
/*  521 */               BrightDate date = this.m_externalFilterManager.getFieldValueAsDate(asset, sField);
/*  522 */               DBUtil.setFieldDateOrNull(psInsImage, i++, date.getDate());
/*  523 */               continue;
/*      */             }
/*      */ 
/*  527 */             boolean bIsDateTime = hmDateTimeFields.containsKey(sField);
/*  528 */             if (bIsDateTime)
/*      */             {
/*  530 */               BrightDateTime date = this.m_externalFilterManager.getFieldValueAsDateTime(asset, sField);
/*  531 */               DBUtil.setFieldTimestampOrNull(psInsImage, i++, date.getDate());
/*  532 */               continue;
/*      */             }
/*      */ 
/*  536 */             String sValue = this.m_externalFilterManager.getMappedFieldValueAsString(asset, sField);
/*  537 */             if (sValue != null)
/*      */             {
/*  539 */               psInsImage.setString(i++, sValue);
/*  540 */               continue;
/*      */             }
/*      */ 
/*  544 */             sValue = this.m_externalFilterManager.getFieldValueAsString(asset, sField);
/*  545 */             psInsImage.setString(i++, sValue);
/*      */           }
/*      */ 
/*  548 */           psInsImage.addBatch();
/*      */ 
/*  552 */           Iterator itPlacenameIds = placenameIds.iterator();
/*  553 */           Iterator itUntrimmedPlacenames = untrimmedPlacenames.iterator();
/*  554 */           while ((itPlacenameIds.hasNext()) && (itUntrimmedPlacenames.hasNext()))
/*      */           {
/*  556 */             Long placenameId = (Long)itPlacenameIds.next();
/*  557 */             String placename = ((String)itUntrimmedPlacenames.next()).trim();
/*      */ 
/*  559 */             i = 1;
/*  560 */             psInsImagePlacename.setLong(i++, asset.getId());
/*  561 */             psInsImagePlacename.setString(i++, placename);
/*  562 */             psInsImagePlacename.setLong(i++, placenameId.longValue());
/*      */ 
/*  564 */             psInsImagePlacename.addBatch();
/*      */           }
/*      */ 
/*  569 */           Iterator itKeywordIds = keywordIds.iterator();
/*  570 */           Iterator itUntrimmedKeywords = untrimmedKeywords.iterator();
/*  571 */           while ((itKeywordIds.hasNext()) && (itUntrimmedKeywords.hasNext()))
/*      */           {
/*  573 */             Long keywordId = (Long)itKeywordIds.next();
/*  574 */             String keyword = ((String)itUntrimmedKeywords.next()).trim();
/*      */ 
/*  576 */             i = 1;
/*  577 */             psInsImageKeyword.setLong(i++, asset.getId());
/*  578 */             psInsImageKeyword.setString(i++, keyword);
/*  579 */             psInsImageKeyword.setLong(i++, keywordId.longValue());
/*      */ 
/*  581 */             psInsImageKeyword.addBatch();
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  587 */       psInsImage.executeBatch();
/*  588 */       psInsImage.close();
/*      */ 
/*  590 */       psInsImagePlacename.executeBatch();
/*  591 */       psInsImagePlacename.close();
/*      */ 
/*  593 */       psInsImageKeyword.executeBatch();
/*  594 */       psInsImageKeyword.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/*  600 */         transaction.rollback();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  604 */         this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */       }
/*      */ 
/*  607 */       this.m_logger.error("BGSExternalFilter.indexAssets: exception inserting into AMS_ tables :" + e.getMessage());
/*  608 */       throw new Bn2Exception("BGSExternalFilter.indexAssets: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  613 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  617 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  621 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static List<Long> convertToUniqueListOfLongs(String a_sIds, String a_sDelim)
/*      */   {
/*  637 */     List <Long>listWithDuplicates = StringUtil.convertToListOfLongs(a_sIds, a_sDelim);
/*  638 */     List listWithoutDuplicates = new ArrayList();
/*      */ 
/*  640 */     for (Long l : listWithDuplicates)
/*      */     {
/*  642 */       if (!listWithoutDuplicates.contains(l))
/*      */       {
/*  644 */         listWithoutDuplicates.add(l);
/*      */       }
/*      */     }
/*      */ 
/*  648 */     return listWithoutDuplicates;
/*      */   }
/*      */ 
/*      */   public static List<String> convertToUniqueList(String a_sIds, String a_sDelim)
/*      */   {
/*  660 */     List <String> listWithDuplicates = StringUtil.convertToList(a_sIds, a_sDelim);
/*  661 */     List listWithoutDuplicates = new ArrayList();
/*      */ 
/*  663 */     for (String s : listWithDuplicates)
/*      */     {
/*  665 */       if (!listWithoutDuplicates.contains(s))
/*      */       {
/*  667 */         listWithoutDuplicates.add(s);
/*      */       }
/*      */     }
/*      */ 
/*  671 */     return listWithoutDuplicates;
/*      */   }
/*      */ 
/*      */   public void removeAsset(long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  677 */     String ksMethodName = "removeAsset";
/*      */ 
/*  680 */     DBTransaction transaction = getTransactionManager().getNewTransaction();
/*      */     try
/*      */     {
/*  684 */       Connection con = transaction.getConnection();
/*  685 */       removeAssets(con, Collections.singletonList(new Long(a_lAssetId)));
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  689 */       this.m_logger.error("BGSExternalFilter.removeAsset: exception removing asset from BGS tables : " + e);
/*  690 */       throw new Bn2Exception("BGSExternalFilter.removeAsset: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  695 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  699 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  703 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeAssets(Connection a_con, List<Long> a_assetIds)
/*      */     throws SQLException
/*      */   {
/*  718 */     String schemaPrefix = getSchemaPrefix();
/*      */ 
/*  720 */     PreparedStatement psDelImage = a_con.prepareStatement("DELETE FROM " + schemaPrefix + getImageTable() + " WHERE image_id = ?");
/*      */ 
/*  724 */     PreparedStatement psDelImagePlacename = a_con.prepareStatement("DELETE FROM " + schemaPrefix + getImagePlacenameTable() + " WHERE image_id = ?");
/*      */ 
/*  728 */     PreparedStatement psDelImageKeyword = a_con.prepareStatement("DELETE FROM " + schemaPrefix + getImageKeywordTable() + " WHERE image_id = ?");
/*      */ 
/*  733 */     for (Long lAssetId : a_assetIds)
/*      */     {
/*  735 */       long assetId = lAssetId.longValue();
/*      */ 
/*  737 */       psDelImage.setLong(1, assetId);
/*  738 */       psDelImage.addBatch();
/*      */ 
/*  740 */       psDelImagePlacename.setLong(1, assetId);
/*  741 */       psDelImagePlacename.addBatch();
/*      */ 
/*  743 */       psDelImageKeyword.setLong(1, assetId);
/*  744 */       psDelImageKeyword.addBatch();
/*      */     }
/*      */ 
/*  748 */     psDelImage.executeBatch();
/*  749 */     psDelImage.close();
/*      */ 
/*  751 */     psDelImagePlacename.executeBatch();
/*  752 */     psDelImagePlacename.close();
/*      */ 
/*  754 */     psDelImageKeyword.executeBatch();
/*  755 */     psDelImageKeyword.close();
/*      */   }
/*      */ 
/*      */   private Set<Long> intersection(Set<Long> a_a, Set<Long> a_b)
/*      */   {
/*  769 */     if (a_a == null)
/*      */     {
/*  771 */       return a_b;
/*      */     }
/*  773 */     if (a_b == null)
/*      */     {
/*  775 */       return a_a;
/*      */     }
/*      */ 
/*  779 */     a_a.retainAll(a_b);
/*  780 */     return a_a;
/*      */   }
/*      */ 
/*      */   private Set<Long> findImagesByPlacename(DBTransaction a_dbTransaction, PlacenameCriteria a_placenameCriteria)
/*      */     throws Bn2Exception
/*      */   {
/*  796 */     String ksMethodName = "findImagesByPlacename";
/*  797 */     this.m_logger.debug("In BGSExternalFilter:findImagesByPlacename");
/*  798 */     String schemaPrefix = getSchemaPrefix();
/*  799 */     String sSQL = null;
/*      */ 
/*  802 */     if (!a_placenameCriteria.validate())
/*      */     {
/*  804 */       throw new Bn2Exception("BGSExternalFilter.findImagesByPlacename: placename criteria invalid.");
/*      */     }
/*      */ 
/*  808 */     DBTransaction transaction = a_dbTransaction;
/*  809 */     if (transaction == null)
/*      */     {
/*  811 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*  815 */     String placenameTopoRel = a_placenameCriteria.getTopoRel();
/*      */ 
/*  817 */     String pointTopoRel = a_placenameCriteria.getTopoRel();
/*  818 */     if (pointTopoRel.equals("EQUAL"))
/*      */     {
/*  823 */       pointTopoRel = "INSIDE";
/*      */     }
/*      */     Set imageIds;
/*      */     try
/*      */     {
/*  829 */       Connection con = transaction.getConnection();
/*      */ 
/*  831 */       Collection colPlacenameIds = a_placenameCriteria.getPlacenameIds();
/*  832 */       Long[] placenameIds = (Long[])colPlacenameIds.toArray(new Long[colPlacenameIds.size()]);
/*      */ 
/*  835 */       ARRAY dbarPlacenameIds = toOracleArray(con, schemaPrefix + "GAZ_PLACE_ID_COLLECTION", placenameIds);
/*      */ 
/*  840 */       sSQL = "SELECT ip.image_id FROM " + schemaPrefix + getImagePlacenameTable() + " ip," + "      THE(SELECT " + schemaPrefix + getAssetBankPluginPackage() + ".get_related_places(?, ?) FROM DUAL) p" + " WHERE ip.gazetteer_placename_id = p.column_value";
/*      */ 
/*  846 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  848 */       int i = 1;
/*  849 */       psql.setArray(i++, dbarPlacenameIds);
/*  850 */       psql.setString(i++, placenameTopoRel);
/*      */ 
/*  852 */       this.m_logger.debug("BGSExternalFilter:findImagesByPlacename: SQL: " + sSQL + " Params: " + colPlacenameIds.toString() + ", " + placenameTopoRel);
/*      */ 
/*  856 */       ResultSet rs = psql.executeQuery();
/*  857 */       imageIds = new LinkedHashSet();
/*  858 */       while (rs.next())
/*      */       {
/*  860 */         long imageId = rs.getLong(1);
/*  861 */         imageIds.add(new Long(imageId));
/*      */       }
/*      */ 
/*  864 */       psql.close();
/*      */ 
/*  866 */       this.m_logger.debug("BGSExternalFilter:findImagesByPlacename: now have " + imageIds.size() + " unique image IDs");
/*      */ 
/*  870 */       sSQL = "{? = call " + schemaPrefix + getAssetBankPluginPackage() + ".get_images_from_placenames(?, ?)}";
/*  871 */       CallableStatement cstmt = con.prepareCall(sSQL);
/*      */ 
/*  873 */       i = 1;
/*  874 */       cstmt.registerOutParameter(i++, 2003, schemaPrefix + "IMAGE_ID_COLLECTION");
/*      */ 
/*  876 */       cstmt.setArray(i++, dbarPlacenameIds);
/*  877 */       cstmt.setString(i++, pointTopoRel);
/*      */ 
/*  879 */       this.m_logger.debug("BGSExternalFilter:findImagesByPlacename: SQL: " + sSQL + " Params: " + colPlacenameIds.toString() + ", " + pointTopoRel);
/*      */ 
/*  882 */       cstmt.execute();
/*  883 */       addDatabaseArrayToJavaSet(imageIds, cstmt.getArray(1));
/*  884 */       cstmt.close();
/*      */ 
/*  886 */       this.m_logger.debug("BGSExternalFilter:findImagesByPlacename: now have " + imageIds.size() + " unique image IDs in total");
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  891 */       this.m_logger.error("BGSExternalFilter.findImagesByPlacename: exception executing SQL: " + sSQL, e);
/*  892 */       throw new Bn2Exception("BGSExternalFilter.findImagesByPlacename: Exception occurred: " + e);
/*      */     }
/*      */     finally
/*      */     {
/*  897 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  901 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  905 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  912 */     return imageIds;
/*      */   }
/*      */ 
/*      */   private Set<Long> findImagesByPoint(DBTransaction a_dbTransaction, PointCriteria a_pointCriteria)
/*      */     throws Bn2Exception
/*      */   {
/*  926 */     String ksMethodName = "findImagesByPoint";
/*      */ 
/*  928 */     String schemaPrefix = getSchemaPrefix();
/*  929 */     String sSQL = null;
/*      */ 
/*  932 */     DBTransaction transaction = a_dbTransaction;
/*  933 */     if (transaction == null)
/*      */     {
/*  935 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */     Set imageIds;
/*      */     try
/*      */     {
/*  941 */       Connection con = transaction.getConnection();
/*      */ 
/*  946 */       sSQL = "SELECT ip.image_id FROM " + schemaPrefix + getImagePlacenameTable() + " ip," + "      THE(SELECT " + schemaPrefix + getAssetBankPluginPackage() + ".get_places_from_xy_position(?, ?, ?, ?, ?) FROM DUAL) p" + " WHERE ip.gazetteer_placename_id = p.column_value";
/*      */ 
/*  952 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  954 */       int i = 1;
/*  955 */       psql.setDouble(i++, a_pointCriteria.getX());
/*  956 */       psql.setDouble(i++, a_pointCriteria.getY());
/*  957 */       psql.setInt(i++, a_pointCriteria.getEpsgCode());
/*  958 */       DBUtil.setFieldDoubleOrNull(psql, i++, a_pointCriteria.getBuffer());
/*      */ 
/*  961 */       String sTopoRelTerm = "ANYINTERACT";
/*  962 */       psql.setString(i++, sTopoRelTerm);
/*      */ 
/*  964 */       this.m_logger.debug("BGSExternalFilter:findImagesByPoint: SQL: " + sSQL + " Params: " + a_pointCriteria.getX() + ", " + a_pointCriteria.getY() + ", " + a_pointCriteria.getEpsgCode() + ", " + a_pointCriteria.getBuffer() + ", " + sTopoRelTerm);
/*      */ 
/*  967 */       ResultSet rs = psql.executeQuery();
/*  968 */       imageIds = new LinkedHashSet();
/*  969 */       while (rs.next())
/*      */       {
/*  971 */         long imageId = rs.getLong(1);
/*  972 */         imageIds.add(new Long(imageId));
/*      */       }
/*      */ 
/*  975 */       psql.close();
/*      */ 
/*  977 */       this.m_logger.debug("BGSExternalFilter:findImagesByPoint: now have " + imageIds.size() + " unique image IDs in total");
/*      */ 
/*  981 */       sSQL = "{? = call " + schemaPrefix + getAssetBankPluginPackage() + ".get_images_from_xy_position(?, ?, ?, ?, ?)}";
/*  982 */       CallableStatement cstmt = con.prepareCall(sSQL);
/*      */ 
/*  984 */       i = 1;
/*  985 */       cstmt.registerOutParameter(i++, 2003, schemaPrefix + "IMAGE_ID_COLLECTION");
/*      */ 
/*  987 */       cstmt.setDouble(i++, a_pointCriteria.getX());
/*  988 */       cstmt.setDouble(i++, a_pointCriteria.getY());
/*  989 */       cstmt.setInt(i++, a_pointCriteria.getEpsgCode());
/*  990 */       DBUtil.setFieldDoubleOrNull(cstmt, i++, a_pointCriteria.getBuffer());
/*  991 */       cstmt.setString(i++, "INSIDE");
/*      */ 
/*  993 */       this.m_logger.debug("BGSExternalFilter:findImagesByPoint: SQL: " + sSQL + " Params: " + a_pointCriteria.getX() + ", " + a_pointCriteria.getY() + ", " + a_pointCriteria.getEpsgCode() + ", " + a_pointCriteria.getBuffer() + ", " + "INSIDE");
/*      */ 
/*  996 */       cstmt.execute();
/*  997 */       addDatabaseArrayToJavaSet(imageIds, cstmt.getArray(1));
/*  998 */       cstmt.close();
/*      */ 
/* 1000 */       this.m_logger.debug("BGSExternalFilter:findImagesByPoint: now have " + imageIds.size() + " unique image IDs in total");
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1005 */       this.m_logger.error("BGSExternalFilter.findImagesByPoint: exception executing SQL: " + sSQL + " : " + e.getMessage());
/* 1006 */       throw new Bn2Exception("BGSExternalFilter.findImagesByPoint: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1011 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1015 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1019 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1026 */     return imageIds;
/*      */   }
/*      */ 
/*      */   private Set<Long> findImagesByBoundingBox(DBTransaction a_dbTransaction, BoundingBoxCriteria a_boundingBoxCriteria)
/*      */     throws Bn2Exception
/*      */   {
/* 1040 */     String ksMethodName = "findImagesByBoundingBox";
/* 1041 */     String schemaPrefix = getSchemaPrefix();
/* 1042 */     String sSQL = null;
/*      */ 
/* 1045 */     DBTransaction transaction = a_dbTransaction;
/* 1046 */     if (transaction == null)
/*      */     {
/* 1048 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */     Set imageIds;
/*      */     try
/*      */     {
/* 1054 */       Connection con = transaction.getConnection();
/*      */ 
/* 1060 */       sSQL = "SELECT ip.image_id FROM " + schemaPrefix + getImagePlacenameTable() + " ip," + "      THE(SELECT " + schemaPrefix + getAssetBankPluginPackage() + ".get_places_from_bounding_box(?, ?, ?, ?, ?, ?, ?) FROM DUAL) p" + " WHERE ip.gazetteer_placename_id = p.column_value";
/*      */ 
/* 1067 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 1069 */       int i = 1;
/* 1070 */       psql.setDouble(i++, a_boundingBoxCriteria.getMinX());
/* 1071 */       psql.setDouble(i++, a_boundingBoxCriteria.getMinY());
/* 1072 */       psql.setDouble(i++, a_boundingBoxCriteria.getMaxX());
/* 1073 */       psql.setDouble(i++, a_boundingBoxCriteria.getMaxY());
/* 1074 */       psql.setInt(i++, a_boundingBoxCriteria.getEpsgCode());
/* 1075 */       DBUtil.setFieldDoubleOrNull(psql, i++, a_boundingBoxCriteria.getBuffer());
/*      */ 
/* 1077 */       String sTopRelCode = "INSIDE";
/* 1078 */       psql.setString(i++, sTopRelCode);
/*      */ 
/* 1080 */       this.m_logger.debug("BGSExternalFilter:findImagesByBoundingBox: SQL: " + sSQL + " Params: " + a_boundingBoxCriteria.getMinX() + ", " + a_boundingBoxCriteria.getMinY() + ", " + a_boundingBoxCriteria.getMaxX() + ", " + a_boundingBoxCriteria.getMaxY() + ", " + a_boundingBoxCriteria.getEpsgCode() + ", " + a_boundingBoxCriteria.getBuffer() + ", " + sTopRelCode);
/*      */ 
/* 1083 */       ResultSet rs = psql.executeQuery();
/* 1084 */       imageIds = new LinkedHashSet();
/* 1085 */       while (rs.next())
/*      */       {
/* 1087 */         long imageId = rs.getLong(1);
/* 1088 */         imageIds.add(new Long(imageId));
/*      */       }
/*      */ 
/* 1091 */       psql.close();
/*      */ 
/* 1093 */       this.m_logger.debug("BGSExternalFilter:findImagesByBoundingBox: now have " + imageIds.size() + " unique image IDs in total");
/*      */ 
/* 1098 */       sSQL = "{? = call " + schemaPrefix + getAssetBankPluginPackage() + ".get_images_from_bounding_box(?, ?, ?, ?, ?, ?, ?)}";
/* 1099 */       CallableStatement cstmt = con.prepareCall(sSQL);
/*      */ 
/* 1101 */       i = 1;
/* 1102 */       cstmt.registerOutParameter(i++, 2003, schemaPrefix + "IMAGE_ID_COLLECTION");
/*      */ 
/* 1104 */       cstmt.setDouble(i++, a_boundingBoxCriteria.getMinX());
/* 1105 */       cstmt.setDouble(i++, a_boundingBoxCriteria.getMinY());
/* 1106 */       cstmt.setDouble(i++, a_boundingBoxCriteria.getMaxX());
/* 1107 */       cstmt.setDouble(i++, a_boundingBoxCriteria.getMaxY());
/* 1108 */       cstmt.setInt(i++, a_boundingBoxCriteria.getEpsgCode());
/* 1109 */       DBUtil.setFieldDoubleOrNull(cstmt, i++, a_boundingBoxCriteria.getBuffer());
/* 1110 */       cstmt.setString(i++, "INSIDE");
/*      */ 
/* 1112 */       this.m_logger.debug("BGSExternalFilter:findImagesByBoundingBox: SQL: " + sSQL + " Params: " + a_boundingBoxCriteria.getMinX() + ", " + a_boundingBoxCriteria.getMinY() + ", " + a_boundingBoxCriteria.getMaxX() + ", " + a_boundingBoxCriteria.getMaxY() + ", " + a_boundingBoxCriteria.getEpsgCode() + ", " + a_boundingBoxCriteria.getBuffer() + ", " + "INSIDE");
/*      */ 
/* 1119 */       cstmt.execute();
/* 1120 */       addDatabaseArrayToJavaSet(imageIds, cstmt.getArray(1));
/* 1121 */       cstmt.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1125 */       this.m_logger.error("BGSExternalFilter.findImagesByBoundingBox: exception executing SQL: " + sSQL + " : " + e.getMessage());
/* 1126 */       throw new Bn2Exception("BGSExternalFilter.findImagesByBoundingBox: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1131 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1135 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1139 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1146 */     return imageIds;
/*      */   }
/*      */ 
/*      */   private Set<Long> findImagesByKeyword(DBTransaction a_dbTransaction, KeywordCriteria a_keywordCriteria)
/*      */     throws Bn2Exception
/*      */   {
/* 1160 */     String ksMethodName = "findImagesByKeyword";
/* 1161 */     this.m_logger.debug("In BGSExternalFilter:findImagesByKeyword");
/* 1162 */     String schemaPrefix = getSchemaPrefix();
/* 1163 */     String sSQL = null;
/*      */ 
/* 1166 */     DBTransaction transaction = a_dbTransaction;
/* 1167 */     if (transaction == null)
/*      */     {
/* 1169 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*      */     Set imageIds;
/*      */     try
/*      */     {
/* 1176 */       Connection con = transaction.getConnection();
/*      */ 
/* 1180 */       Set<Long> keywordIds = new HashSet();
/* 1181 */       keywordIds.addAll(a_keywordCriteria.getKeywordIds());
/*      */ 
/* 1184 */       String relatedKeywordType = a_keywordCriteria.getRelatedKeywordType();
/* 1185 */       if (relatedKeywordType != null)
/*      */       {
/* 1187 */         Long[] arKeywordIds = (Long[])a_keywordCriteria.getKeywordIds().toArray(new Long[a_keywordCriteria.getKeywordIds().size()]);
/* 1188 */         ARRAY dbarKeywordIds = toOracleArray(con, schemaPrefix + "GAZ_KEYWORD_ID_COLLECTION", arKeywordIds);
/*      */ 
/* 1190 */         sSQL = "{? = call " + schemaPrefix + getAssetBankPluginPackage() + ".get_related_keywords(?, ?)}";
/* 1191 */         CallableStatement cstmt = con.prepareCall(sSQL);
/*      */ 
/* 1193 */         cstmt.registerOutParameter(1, 2003, schemaPrefix + "GAZ_KEYWORD_ID_COLLECTION");
/* 1194 */         cstmt.setArray(2, dbarKeywordIds);
/* 1195 */         cstmt.setString(3, relatedKeywordType);
/*      */ 
/* 1197 */         cstmt.executeUpdate();
/*      */ 
/* 1200 */         addDatabaseArrayToJavaSet(keywordIds, cstmt.getArray(1));
/* 1201 */         cstmt.close();
/*      */ 
/* 1205 */         this.m_logger.debug("BGSExternalFilter:findImagesByKeyword: now have " + keywordIds.size() + " unique keyword IDs in total");
/*      */       }
/*      */ 
/* 1210 */       sSQL = "SELECT image_id FROM " + schemaPrefix + getImageKeywordTable() + " WHERE thesaurus_keyword_id IN (" + DBUtil.getPlaceholders(keywordIds.size()) + ")";
/*      */ 
/* 1215 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 1217 */       int i = 1;
/* 1218 */       String sParams = "";
/* 1219 */       for (Long keywordId : keywordIds)
/*      */       {
/* 1221 */         psql.setLong(i++, keywordId.longValue());
/* 1222 */         sParams = sParams + keywordId.longValue() + ", ";
/*      */       }
/*      */ 
/* 1225 */       this.m_logger.debug("BGSExternalFilter:findImagesByKeyword: SQL: " + sSQL + " Params: " + sParams);
/*      */ 
/* 1228 */       ResultSet rs = psql.executeQuery();
/* 1229 */       imageIds = new LinkedHashSet();
/* 1230 */       while (rs.next())
/*      */       {
/* 1232 */         long imageId = rs.getLong(1);
/* 1233 */         imageIds.add(new Long(imageId));
/*      */       }
/*      */ 
/* 1236 */       psql.close();
/*      */ 
/* 1238 */       this.m_logger.debug("BGSExternalFilter:findImagesByKeyword: got " + imageIds.size() + " unique image IDs");
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1243 */       this.m_logger.error("BGSExternalFilter.findImagesByKeyword: exception executing SQL: " + sSQL + " : " + e.getMessage());
/* 1244 */       throw new Bn2Exception("BGSExternalFilter.findImagesByKeyword: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1249 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1253 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1257 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1264 */     return imageIds;
/*      */   }
/*      */ 
/*      */   private ARRAY toOracleArray(Connection a_con, String a_arrayType, Object[] a_javaArray)
/*      */     throws SQLException
/*      */   {
/* 1279 */     a_con = DBUtil.unwrapConnection(a_con);
/*      */ 
/* 1283 */     ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor(a_arrayType, a_con);
/*      */ 
/* 1286 */     return new ARRAY(descriptor, a_con, a_javaArray);
/*      */   }
/*      */ 
/*      */   private void addDatabaseArrayToJavaSet(Set<Long> a_dest, Array a_src)
/*      */     throws SQLException
/*      */   {
/* 1298 */     Number[] src = (Number[])(Number[])a_src.getArray();
/* 1299 */     for (Number n : src)
/*      */     {
/* 1301 */       a_dest.add(new Long(n.longValue()));
/*      */     }
/*      */   }
/*      */ 
/*      */   private BGSSearchCriteria bgsCriteriaFromMap(Map<String, String> externalFilterCriteria)
/*      */   {
/* 1312 */     return bgsCriteriaFromMap(externalFilterCriteria, new Vector());
/*      */   }
/*      */ 
/*      */   private BGSSearchCriteria bgsCriteriaFromMap(Map<String, String> externalFilterCriteria, List<String> a_errors)
/*      */   {
/* 1322 */     PlacenameCriteria placenameCriteria = null;
/* 1323 */     PointCriteria pointCriteria = null;
/* 1324 */     BoundingBoxCriteria boundingBoxCriteria = null;
/* 1325 */     KeywordCriteria keywordCriteria = null;
/*      */ 
/* 1328 */     String sPlacenameIds = (String)externalFilterCriteria.get("placenameIds");
/* 1329 */     if (StringUtils.isNotEmpty(sPlacenameIds))
/*      */     {
/* 1331 */       List placenameIds = StringUtil.convertToListOfLongs(sPlacenameIds, ",");
/*      */ 
/* 1333 */       if (!placenameIds.isEmpty())
/*      */       {
/* 1335 */         String placenameTopoRel = (String)externalFilterCriteria.get("placenameTopoRel");
/* 1336 */         placenameCriteria = new PlacenameCriteria(placenameIds, placenameTopoRel);
/* 1337 */         placenameCriteria.validate(a_errors, "Image-Placename Relationship");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1342 */     Double x = ExternalFilterUtil.readDoubleParam(externalFilterCriteria, "x", "X", a_errors);
/* 1343 */     Double y = ExternalFilterUtil.readDoubleParam(externalFilterCriteria, "y", "Y", a_errors);
/* 1344 */     Integer pointEpsgCode = ExternalFilterUtil.readIntegerParam(externalFilterCriteria, "pointEpsgCode", "Point Co-ordinate System", a_errors);
/* 1345 */     Double pointBuffer = ExternalFilterUtil.readDoubleParam(externalFilterCriteria, "pointBuffer", "Point Buffer Distance", a_errors);
/*      */ 
/* 1349 */     if ((x != null) && (y != null) && (pointEpsgCode != null))
/*      */     {
/* 1351 */       pointCriteria = new PointCriteria(x.doubleValue(), y.doubleValue(), pointEpsgCode.intValue(), pointBuffer);
/*      */     }
/*      */ 
/* 1355 */     Double minX = ExternalFilterUtil.readDoubleParam(externalFilterCriteria, "minX", "Bounding Box Minimum X", a_errors);
/* 1356 */     Double minY = ExternalFilterUtil.readDoubleParam(externalFilterCriteria, "minY", "Bounding Box Minimum Y", a_errors);
/* 1357 */     Double maxX = ExternalFilterUtil.readDoubleParam(externalFilterCriteria, "maxX", "Bounding Box Maximum X", a_errors);
/* 1358 */     Double maxY = ExternalFilterUtil.readDoubleParam(externalFilterCriteria, "maxY", "Bounding Box Maximum Y", a_errors);
/* 1359 */     Integer bBoxEpsgCode = ExternalFilterUtil.readIntegerParam(externalFilterCriteria, "bBoxEpsgCode", "Bounding Box Co-ordinate System", a_errors);
/* 1360 */     Double bBoxBuffer = ExternalFilterUtil.readDoubleParam(externalFilterCriteria, "bBoxBuffer", "Bounding Box Buffer Distance", a_errors);
/*      */ 
/* 1364 */     if ((minX != null) && (minY != null) && (maxX != null) && (maxY != null) && (bBoxEpsgCode != null))
/*      */     {
/* 1368 */       boundingBoxCriteria = new BoundingBoxCriteria(minX.doubleValue(), minY.doubleValue(), maxX.doubleValue(), maxY.doubleValue(), bBoxEpsgCode.intValue(), bBoxBuffer);
/*      */     }
/*      */ 
/* 1373 */     String sKeywordIds = (String)externalFilterCriteria.get("keywordIds");
/* 1374 */     if (StringUtils.isNotEmpty(sKeywordIds))
/*      */     {
/* 1376 */       List keywordIds = StringUtil.convertToListOfLongs(sKeywordIds, ",");
/*      */ 
/* 1378 */       if (!keywordIds.isEmpty())
/*      */       {
/* 1380 */         String keywordMatchType = (String)externalFilterCriteria.get("relatedKeywordType");
/* 1381 */         if ((keywordMatchType != null) && (keywordMatchType.equals("")))
/*      */         {
/* 1383 */           keywordMatchType = null;
/*      */         }
/* 1385 */         keywordCriteria = new KeywordCriteria(keywordIds, keywordMatchType);
/*      */       }
/*      */     }
/*      */ 
/* 1389 */     return new BGSSearchCriteria(placenameCriteria, pointCriteria, boundingBoxCriteria, keywordCriteria);
/*      */   }
/*      */ 
/*      */   private String getSchemaPrefix()
/*      */   {
/* 1399 */     String schema = FrameworkSettings.getExternalFilterSchema();
/* 1400 */     return schema + ".";
/*      */   }
/*      */ 
/*      */   private String getImageTable()
/*      */   {
/* 1405 */     return this.m_settings.getString("image-table");
/*      */   }
/*      */ 
/*      */   private String getImageKeywordTable()
/*      */   {
/* 1410 */     return this.m_settings.getString("image-keyword-table");
/*      */   }
/*      */ 
/*      */   private String getImagePlacenameTable()
/*      */   {
/* 1415 */     return this.m_settings.getString("image-placename-table");
/*      */   }
/*      */ 
/*      */   private String getAssetBankPluginPackage()
/*      */   {
/* 1420 */     return this.m_settings.getString("asset-bank-plugin-package");
/*      */   }
/*      */ 
/*      */   public void setLogger(Log a_logger)
/*      */   {
/* 1425 */     this.m_logger = a_logger;
/*      */   }
/*      */ 
/*      */   protected DBTransactionManager getTransactionManager()
/*      */   {
/* 1430 */     return this.m_transactionManager;
/*      */   }
/*      */ 
/*      */   private Collection<Long> createPerformanceTestResults()
/*      */   {
/* 1442 */     Collection filterArray = new ArrayList(10000);
/*      */ 
/* 1444 */     int iStart = 10000;
/* 1445 */     for (int i = 0; i < 10000; i++)
/*      */     {
/* 1447 */       filterArray.add(new Long(iStart + i));
/*      */     }
/*      */ 
/* 1450 */     return filterArray;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.bgs.service.BGSExternalFilter
 * JD-Core Version:    0.6.0
 */