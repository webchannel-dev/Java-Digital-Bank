/*      */ package com.bright.assetbank.entity.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.bean.LightweightAsset;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.Attribute.Translation;
/*      */ import com.bright.assetbank.attribute.bean.AttributeType;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ //import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*      */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.attribute.util.AttributeValueDBUtil;
/*      */ import com.bright.assetbank.attribute.util.AttributeValueUtil;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ //import com.bright.assetbank.entity.bean.AssetEntity.Translation;
/*      */ import com.bright.assetbank.entity.bean.AssetEntityRetreivalCriteria;
/*      */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*      */ import com.bright.assetbank.entity.exception.EntityHasAssetsException;
/*      */ import com.bright.assetbank.entity.exception.EntityHasRelationshipException;
/*      */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*      */ import com.bright.assetbank.plugin.service.PluginManager;
/*      */ import com.bright.assetbank.search.bean.SearchCriteria;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.bean.JDBCValue;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.search.bean.SearchResults;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AssetEntityManager extends Bn2Manager
/*      */   implements AssetEntityConstants
/*      */ {
/*      */   private static final String c_ksClassName = "AssetEntityManager";
/*   86 */   private DBTransactionManager m_transactionManager = null;
/*   87 */   private AttributeManager m_attributeManager = null;
/*   88 */   private AssetEntityRelationshipManager m_relationshipManager = null;
/*   89 */   private MultiLanguageSearchManager m_searchManager = null;
/*      */   private PluginManager m_pluginManager;
/*   92 */   public static String c_ksAssetEntityFields = "ae.Id aeId, ae.Name aeName, ae.ThumbnailFilename aeThumbnailFilename, ae.TermForSiblings aeTermForSiblings, ae.TermForSibling aeTermForSibling, ae.IsSearchable aeIsSearchable, ae.IsQuickSearchable aeIsQuickSearchable, ae.IncludedFileFormats aeIncludedFileFormats, ae.ExcludedFileFormats aeExcludedFileFormats, ae.MustHaveParent aeMustHaveParent, ae.CanCopyAssets aeCanCopyAssets, ae.DefaultCategoryId aeDefaultCategoryId, ae.UnrestAgreementId aeUnrestAgreementId, ae.RestAgreementId aeRestAgreementId, ae.ShowAttributeLabels aeShowAttributeLabels, ae.CanDownloadChildren aeCanDownloadChildren, ae.MatchOnAttributeId aeMatchOnAttributeId, ae.ShowOnDescendantCategories, ae.IsCategoryExtension, tae.Name taeName, tae.TermForSiblings taeTermForSiblings, tae.TermForSibling taeTermForSibling, trdc.FromName trdcFromName, trdc.FromNamePlural trdcFromNamePlural, trdc.ToName trdcToName, trdc.ToNamePlural trdcToNamePlural, trdp.ToName trdpToName, trdp.ToNamePlural trdpToNamePlural, l.Id lId, l.Code lCode, l.Name lName ";
/*      */ 
/*      */   public ArrayList<Long> getAllEntityIds(DBTransaction a_transaction, boolean a_bNonCategoryExtensionOnly)
/*      */     throws Bn2Exception
/*      */   {
/*  135 */     String ksMethodName = "getAllEntityIds";
/*  136 */     Connection con = null;
/*  137 */     ArrayList alEntityIds = new ArrayList();
/*  138 */     DBTransaction transaction = a_transaction;
/*  139 */     boolean bError = false;
/*      */ 
/*  141 */     if (transaction == null)
/*      */     {
/*  143 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */     try
/*      */     {
/*  147 */       con = transaction.getConnection();
/*      */ 
/*  149 */       String sSql = "SELECT Id FROM AssetEntity";
/*  150 */       if (a_bNonCategoryExtensionOnly)
/*      */       {
/*  152 */         sSql = sSql + " WHERE IsCategoryExtension=0";
/*      */       }
/*  154 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  155 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  157 */       while (rs.next())
/*      */       {
/*  159 */         alEntityIds.add(Long.valueOf(rs.getLong("Id")));
/*      */       }
/*  161 */       psql.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  165 */       bError = true;
/*  166 */       throw new Bn2Exception(getClass().getSimpleName() + "." + "getAllEntityIds" + ": Error: ", e);
/*      */     }
/*      */     finally
/*      */     {
/*  170 */       if ((transaction != null) && (a_transaction == null))
/*      */       {
/*      */         try
/*      */         {
/*  174 */           if (bError)
/*      */           {
/*  176 */             transaction.rollback();
/*      */           }
/*      */           else
/*      */           {
/*  180 */             transaction.commit();
/*      */           }
/*      */         }
/*      */         catch (SQLException e)
/*      */         {
/*  185 */           throw new Bn2Exception(getClass().getSimpleName() + "." + "getAllEntityIds" + ": Database Error: ", e);
/*      */         }
/*      */       }
/*      */     }
/*  189 */     return alEntityIds;
/*      */   }
/*      */ 
/*      */   public Vector<AssetEntity> getAllEntities(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/*  200 */     return getEntities(a_transaction, new AssetEntityRetreivalCriteria());
/*      */   }
/*      */ 
/*      */   public Vector<AssetEntity> getAllSelectedEntities(DBTransaction a_transaction, long[] a_aIds)
/*      */     throws Bn2Exception
/*      */   {
/*  212 */     AssetEntityRetreivalCriteria criteria = new AssetEntityRetreivalCriteria();
/*  213 */     criteria.setRestrictions(a_aIds);
/*  214 */     return getEntities(a_transaction, criteria);
/*      */   }
/*      */ 
/*      */   public Vector<AssetEntity> getAllQuickSearchableEntities(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/*  226 */     AssetEntityRetreivalCriteria criteria = new AssetEntityRetreivalCriteria();
/*  227 */     criteria.setSearchableOnly(true);
/*  228 */     criteria.setQuickSearch(true);
/*  229 */     return getEntities(a_transaction, criteria);
/*      */   }
/*      */ 
/*      */   public Vector<AssetEntity> getAllUploadEntities(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/*  243 */     AssetEntityRetreivalCriteria criteria = new AssetEntityRetreivalCriteria();
/*  244 */     criteria.setFileEntitiesOnly(true);
/*  245 */     criteria.setOnlyParentlessEntities(true);
/*  246 */     criteria.setCategoryExtensionStatus(2);
/*  247 */     return getEntities(a_transaction, criteria);
/*      */   }
/*      */ 
/*      */   public Vector<AssetEntity> getEntitiesWithMatchAttribute(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/*  260 */     AssetEntityRetreivalCriteria criteria = new AssetEntityRetreivalCriteria();
/*  261 */     criteria.setMatchAttributeOnly(true);
/*  262 */     criteria.setCategoryExtensionStatus(2);
/*  263 */     return getEntities(a_transaction, criteria);
/*      */   }
/*      */ 
/*      */   public Vector<AssetEntity> getEntitiesWithMatchAttributeFileEntitiesOnly(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/*  275 */     AssetEntityRetreivalCriteria criteria = new AssetEntityRetreivalCriteria();
/*  276 */     criteria.setFileEntitiesOnly(true);
/*  277 */     criteria.setMatchAttributeOnly(true);
/*  278 */     criteria.setCategoryExtensionStatus(2);
/*  279 */     return getEntities(a_transaction, criteria);
/*      */   }
/*      */ 
/*      */   public Vector<AssetEntity> getEntities(DBTransaction a_transaction, AssetEntityRetreivalCriteria a_criteria)
/*      */     throws Bn2Exception
/*      */   {
/*  289 */     String ksMethodName = "getEntities";
/*  290 */     Connection con = null;
/*  291 */     Vector entities = null;
/*  292 */     DBTransaction transaction = a_transaction;
/*      */ 
/*  294 */     if (transaction == null)
/*      */     {
/*  296 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */     try
/*      */     {
/*  300 */       con = transaction.getConnection();
/*      */ 
/*  302 */       String sSql = "SELECT " + c_ksAssetEntityFields + "FROM " + "AssetEntity ae " + "LEFT JOIN TranslatedAssetEntity tae ON tae.AssetEntityId=ae.Id " + "LEFT JOIN Language l ON l.Id=tae.LanguageId " + "LEFT JOIN TranslatedERDescription trdc ON trdc.AssetEntityId=ae.Id AND trdc.RelationshipTypeId=? AND trdc.LanguageId=l.Id " + "LEFT JOIN TranslatedERDescription trdp ON trdp.AssetEntityId=ae.Id AND trdp.RelationshipTypeId=? AND trdp.LanguageId=l.Id ";
/*      */ 
/*  310 */       if (a_criteria.isFileEntitiesOnly())
/*      */       {
/*  312 */         sSql = sSql + " INNER JOIN AllowableEntityAssetType aeat ON aeat.AssetEntityId = ae.Id ";
/*      */       }
/*      */ 
/*  315 */       sSql = sSql + " WHERE 1=1 ";
/*      */ 
/*  317 */       if (a_criteria.isSearchableOnly())
/*      */       {
/*  319 */         if (a_criteria.isQuickSearch())
/*      */         {
/*  321 */           sSql = sSql + " AND ae.IsQuickSearchable=? ";
/*      */         }
/*      */         else
/*      */         {
/*  325 */           sSql = sSql + " AND ae.IsSearchable=? ";
/*      */         }
/*      */       }
/*      */ 
/*  329 */       if (a_criteria.isOnlyParentlessEntities())
/*      */       {
/*  331 */         sSql = sSql + " AND ae.MustHaveParent=? ";
/*      */       }
/*      */ 
/*  334 */       if (a_criteria.isMatchAttributeOnly())
/*      */       {
/*  336 */         sSql = sSql + " AND ae.MatchOnAttributeId IS NOT NULL ";
/*      */       }
/*      */ 
/*  339 */       if ((a_criteria.getRestrictions() != null) && (a_criteria.getRestrictions().length > 0))
/*      */       {
/*  341 */         sSql = sSql + " AND ae.Id IN (";
/*  342 */         for (long lId : a_criteria.getRestrictions())
/*      */         {
/*  344 */           sSql = sSql + lId + ",";
/*      */         }
/*  346 */         sSql = sSql.substring(0, sSql.length() - 1);
/*  347 */         sSql = sSql + ")";
/*      */       }
/*      */ 
/*  351 */       switch (a_criteria.getCategoryExtensionStatus()) {
/*      */       case 1:
/*  353 */         sSql = sSql + " AND ae.IsCategoryExtension=1"; break;
/*      */       case 2:
/*  354 */         sSql = sSql + " AND ae.IsCategoryExtension=0";
/*      */       }
/*      */ 
/*  358 */       sSql = sSql + " ORDER BY SequenceNumber";
/*      */ 
/*  360 */       int iCol = 1;
/*  361 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  362 */       psql.setLong(iCol++, 2L);
/*  363 */       psql.setLong(iCol++, 1L);
/*      */ 
/*  365 */       if (a_criteria.isSearchableOnly())
/*      */       {
/*  367 */         psql.setBoolean(iCol++, true);
/*      */       }
/*      */ 
/*  370 */       if (a_criteria.isOnlyParentlessEntities())
/*      */       {
/*  372 */         psql.setBoolean(iCol++, false);
/*      */       }
/*      */ 
/*  375 */       ResultSet rs = psql.executeQuery();
/*  376 */       long lLastId = 0L;
/*  377 */       entities = new Vector();
/*  378 */       AssetEntity entity = null;
/*      */ 
/*  380 */       while (rs.next())
/*      */       {
/*  382 */         if (lLastId != rs.getLong("aeId"))
/*      */         {
/*  384 */           lLastId = rs.getLong("aeId");
/*      */ 
/*  386 */           entity = buildAssetEntity(transaction, rs);
/*  387 */           entities.add(entity);
/*      */         }
/*      */ 
/*  390 */         addAssetEntityTranslation(entity, rs);
/*      */       }
/*      */ 
/*  393 */       psql.close();
/*      */ 
/*  395 */       getPluginManager().getExtensionDatas(transaction, "assetEntity", entities);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  399 */       if ((transaction != null) && (a_transaction == null))
/*      */       {
/*      */         try
/*      */         {
/*  403 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  411 */       this.m_logger.error("AssetEntityManager.getEntities : SQL Exception : " + e);
/*  412 */       throw new Bn2Exception("AssetEntityManager.getEntities : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  417 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  421 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  425 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  430 */     return entities;
/*      */   }
/*      */ 
/*      */   public Vector<Long> getEntityIdsExcludedFromSearch(DBTransaction a_transaction, boolean a_bQuickSearch)
/*      */     throws Bn2Exception
/*      */   {
/*  442 */     String ksMethodName = "getEntities";
/*  443 */     Connection con = null;
/*  444 */     Vector ids = null;
/*  445 */     DBTransaction transaction = a_transaction;
/*      */ 
/*  447 */     if (transaction == null)
/*      */     {
/*  449 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  454 */       con = transaction.getConnection();
/*      */ 
/*  456 */       String sSql = "SELECT ae.Id aeId, ae.IsSearchable aeIsSearchable, ae.IsQuickSearchable aeIsQuickSearchable FROM AssetEntity ae";
/*      */ 
/*  461 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/*  463 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  465 */       ids = new Vector();
/*  466 */       int iCount = 0;
/*      */ 
/*  468 */       while (rs.next())
/*      */       {
/*  470 */         if (((a_bQuickSearch) && (!rs.getBoolean("aeIsQuickSearchable"))) || ((!a_bQuickSearch) && (!rs.getBoolean("aeIsSearchable"))))
/*      */         {
/*  472 */           ids.add(Long.valueOf(rs.getLong("aeId")));
/*      */         }
/*  474 */         iCount++;
/*      */       }
/*      */ 
/*  479 */       if (ids.size() == iCount)
/*      */       {
/*  481 */         ids.clear();
/*      */       }
/*      */ 
/*  484 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  488 */       if ((transaction != null) && (a_transaction == null))
/*      */       {
/*      */         try
/*      */         {
/*  492 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  500 */       this.m_logger.error("AssetEntityManager.getEntities : SQL Exception : " + e);
/*  501 */       throw new Bn2Exception("AssetEntityManager.getEntities : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  506 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  510 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  514 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  519 */     return ids;
/*      */   }
/*      */ 
/*      */   public AssetEntity getEntity(DBTransaction a_transaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  530 */     return getEntity(a_transaction, a_lId, false);
/*      */   }
/*      */ 
/*      */   public AssetEntity getEntity(DBTransaction a_transaction, long a_lId, boolean a_bIncludeAllowableAttributes)
/*      */     throws Bn2Exception
/*      */   {
/*  541 */     String ksMethodName = "getEntity";
/*  542 */     Connection con = null;
/*  543 */     AssetEntity entity = null;
/*  544 */     DBTransaction transaction = a_transaction;
/*      */ 
/*  546 */     if (transaction == null)
/*      */     {
/*  548 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  553 */       con = transaction.getConnection();
/*      */ 
/*  556 */       String sSql = "SELECT " + c_ksAssetEntityFields + "FROM " + "AssetEntity ae " + "LEFT JOIN TranslatedAssetEntity tae ON tae.AssetEntityId=ae.Id " + "LEFT JOIN Language l ON l.Id=tae.LanguageId " + "LEFT JOIN TranslatedERDescription trdc ON trdc.AssetEntityId=ae.Id AND trdc.RelationshipTypeId=? AND trdc.LanguageId=l.Id " + "LEFT JOIN TranslatedERDescription trdp ON trdp.AssetEntityId=ae.Id AND trdp.RelationshipTypeId=? AND trdp.LanguageId=l.Id " + "WHERE ae.Id=?";
/*      */ 
/*  565 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  566 */       psql.setLong(1, 2L);
/*  567 */       psql.setLong(2, 1L);
/*  568 */       psql.setLong(3, a_lId);
/*  569 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  571 */       while (rs.next())
/*      */       {
/*  573 */         if (entity == null)
/*      */         {
/*  575 */           entity = buildAssetEntity(transaction, rs);
/*      */         }
/*      */ 
/*  578 */         addAssetEntityTranslation(entity, rs);
/*      */       }
/*      */ 
/*  581 */       if (a_bIncludeAllowableAttributes)
/*      */       {
/*  583 */         entity.setAllowableAttributes(getAttributeIdsForEntity(transaction, a_lId, false));
/*      */       }
/*      */ 
/*  586 */       psql.close();
/*      */ 
/*  588 */       getPluginManager().getExtensionData(transaction, "assetEntity", entity);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  592 */       if ((transaction != null) && (a_transaction == null))
/*      */       {
/*      */         try
/*      */         {
/*  596 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  604 */       this.m_logger.error("AssetEntityManager.getEntity : SQL Exception : " + e);
/*  605 */       throw new Bn2Exception("AssetEntityManager.getEntity : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  610 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  614 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  618 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  623 */     return entity;
/*      */   }
/*      */ 
/*      */   public Vector<Long> getAttributeIdsForEntity(DBTransaction a_transaction, long a_lId, boolean a_bOmitWhenDataFromChildren)
/*      */     throws Bn2Exception
/*      */   {
/*  640 */     String ksMethodName = "getAttributeIdsForEntity";
/*      */ 
/*  642 */     DBTransaction transaction = a_transaction;
/*  643 */     Vector vIds = null;
/*  644 */     Connection con = null;
/*      */ 
/*  646 */     if (transaction == null)
/*      */     {
/*  648 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  653 */       con = transaction.getConnection();
/*      */ 
/*  657 */       String sSql = "SELECT aea.AttributeId aeaAttributeId FROM AssetEntityAttribute aea ";
/*      */ 
/*  662 */       if (a_bOmitWhenDataFromChildren)
/*      */       {
/*  664 */         sSql = sSql + "INNER JOIN AssetEntity ae ON ae.Id=aea.AssetEntityId INNER JOIN Attribute a ON a.Id=aea.AttributeId LEFT JOIN AllowableEntityRelationship aer ON aer.AssetEntityId=ae.Id AND aer.RelationshipTypeId=? ";
/*      */       }
/*      */ 
/*  670 */       sSql = sSql + "WHERE aea.AssetEntityId=? ";
/*      */ 
/*  672 */       if (a_bOmitWhenDataFromChildren)
/*      */       {
/*  674 */         sSql = sSql + " AND (a.GetDataFromChildren=? OR " + SQLGenerator.getInstance().getNullCheckStatement("aer.AssetEntityId") + ")";
/*      */       }
/*      */ 
/*  678 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/*  680 */       if (a_bOmitWhenDataFromChildren)
/*      */       {
/*  682 */         psql.setLong(1, 2L);
/*  683 */         psql.setLong(2, a_lId);
/*  684 */         psql.setBoolean(3, false);
/*      */       }
/*      */       else
/*      */       {
/*  688 */         psql.setLong(1, a_lId);
/*      */       }
/*      */ 
/*  691 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  693 */       vIds = new Vector();
/*      */ 
/*  695 */       while (rs.next())
/*      */       {
/*  697 */         vIds.add(Long.valueOf(rs.getLong("aeaAttributeId")));
/*      */       }
/*  699 */       psql.close();
/*      */ 
/*  705 */       Vector vStaticAtts = this.m_attributeManager.getStaticAttributeList(transaction, -1L);
/*  706 */       for (int i = 0; i < vStaticAtts.size(); i++)
/*      */       {
/*  708 */         StringDataBean att = (StringDataBean)vStaticAtts.get(i);
/*  709 */         String sDelim = ",";
/*  710 */         String sIgnoreFields = sDelim + StringUtil.convertStringArrayToString(k_aOptionalStaticAttributes, sDelim) + sDelim;
/*  711 */         boolean bAdd = sIgnoreFields.indexOf(sDelim + att.getName() + sDelim) < 0;
/*      */ 
/*  713 */         if ((att.getId() == 400L) || (!bAdd))
/*      */           continue;
/*  715 */         vIds.add(Long.valueOf(att.getId()));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  721 */       if ((transaction != null) && (a_transaction == null))
/*      */       {
/*      */         try
/*      */         {
/*  725 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  733 */       this.m_logger.error("AssetEntityManager.getAttributeIdsForEntity : SQL Exception : " + e);
/*  734 */       throw new Bn2Exception("AssetEntityManager.getAttributeIdsForEntity : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  739 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  743 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  747 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  752 */     return vIds;
/*      */   }
/*      */ 
/*      */   public Vector<Long> getAssetTypeIdsForEntity(DBTransaction a_transaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  765 */     String ksMethodName = "getAssetTypeIdsForEntity";
/*      */ 
/*  767 */     Vector vIds = null;
/*  768 */     Connection con = null;
/*      */     try
/*      */     {
/*  772 */       con = a_transaction.getConnection();
/*      */ 
/*  775 */       String sSql = "SELECT AssetTypeId FROM AllowableEntityAssetType WHERE AssetEntityId=?";
/*      */ 
/*  781 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  782 */       psql.setLong(1, a_lId);
/*  783 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  785 */       vIds = new Vector();
/*      */ 
/*  787 */       while (rs.next())
/*      */       {
/*  789 */         vIds.add(Long.valueOf(rs.getLong("AssetTypeId")));
/*      */       }
/*      */ 
/*  792 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  796 */       this.m_logger.error("AssetEntityManager.getAssetTypeIdsForEntity : SQL Exception : " + e);
/*  797 */       throw new Bn2Exception("AssetEntityManager.getAssetTypeIdsForEntity : SQL Exception : " + e, e);
/*      */     }
/*      */ 
/*  800 */     return vIds;
/*      */   }
/*      */ 
/*      */   public long getAssetEntityIdForAssetTypeAndFileFormat(DBTransaction a_transaction, long a_lAssetTypeId, String a_sFileSuffix, boolean a_bHasParent)
/*      */     throws Bn2Exception
/*      */   {
/*  813 */     String ksMethodName = "getAssetEntityIdForAssetType";
/*      */ 
/*  815 */     long lResult = 0L;
/*  816 */     Connection con = null;
/*  817 */     PreparedStatement psql = null;
/*  818 */     DBTransaction transaction = a_transaction;
/*      */ 
/*  820 */     if (transaction == null)
/*      */     {
/*  822 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  827 */       con = transaction.getConnection();
/*      */ 
/*  830 */       String sSql = "SELECT ae.Id aeId, ae.IncludedFileFormats aeIncludedFileFormats, ae.ExcludedFileFormats aeExcludedFileFormats FROM AssetEntity ae INNER JOIN AllowableEntityAssetType aeat ON ae.Id=aeat.AssetEntityId WHERE aeat.AssetTypeId=?";
/*      */ 
/*  836 */       if (!a_bHasParent)
/*      */       {
/*  838 */         sSql = sSql + " AND MustHaveParent=?";
/*      */       }
/*      */ 
/*  841 */       psql = con.prepareStatement(sSql);
/*  842 */       psql.setLong(1, a_lAssetTypeId);
/*      */ 
/*  844 */       if (!a_bHasParent)
/*      */       {
/*  846 */         psql.setBoolean(2, false);
/*      */       }
/*      */ 
/*  849 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  851 */       a_sFileSuffix = a_sFileSuffix.toLowerCase();
/*      */ 
/*  853 */       while (rs.next())
/*      */       {
/*  855 */         String sIncludedFormats = rs.getString("aeIncludedFileFormats");
/*  856 */         String sExcludedFormats = rs.getString("aeExcludedFileFormats");
/*      */ 
/*  858 */         HashSet hsIncludedFormats = new HashSet();
/*  859 */         HashSet hsExcludedFormats = new HashSet();
/*      */ 
/*  861 */         if (StringUtils.isNotEmpty(sIncludedFormats))
/*      */         {
/*  863 */           Collections.addAll(hsIncludedFormats, (String[])sIncludedFormats.toLowerCase().split("[ ,]"));
/*      */         }
/*  865 */         if (StringUtils.isNotEmpty(sExcludedFormats))
/*      */         {
/*  867 */           Collections.addAll(hsExcludedFormats, (String[])sExcludedFormats.toLowerCase().split("[ ,]"));
/*      */         }
/*      */ 
/*  870 */         if ((hsIncludedFormats.contains(a_sFileSuffix)) || ((StringUtils.isEmpty(sIncludedFormats)) && (!hsExcludedFormats.contains(a_sFileSuffix))))
/*      */         {
/*  872 */           lResult = rs.getLong("aeId");
/*  873 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  879 */       this.m_logger.error("AssetEntityManager.getAssetEntityIdForAssetType : SQL Exception : " + e);
/*  880 */       throw new Bn2Exception("AssetEntityManager.getAssetEntityIdForAssetType : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  884 */       if (psql != null)
/*      */       {
/*      */         try
/*      */         {
/*  888 */           psql.close();
/*      */         }
/*      */         catch (SQLException e)
/*      */         {
/*      */         }
/*      */       }
/*  894 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  898 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  902 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*  906 */     return lResult;
/*      */   }
/*      */ 
/*      */   private void addAssetEntityTranslation(AssetEntity a_entity, ResultSet a_rs) throws SQLException
/*      */   {
/*  911 */     if (a_rs.getLong("lId") > 0L)
/*      */     {
/*      */       //AssetEntity tmp18_17 = a_entity; tmp18_17.getClass(); AssetEntity.Translation translation = new AssetEntity.Translation(tmp18_17, new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
/*  914 */       AssetEntity.Translation translation = a_entity.new Translation(new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
                 translation.setName(a_rs.getString("taeName"));
/*  915 */       translation.setTermForSiblings(a_rs.getString("taeTermForSiblings"));
/*  916 */       translation.setTermForSibling(a_rs.getString("taeTermForSibling"));
/*  917 */       translation.setChildRelationshipFromName(a_rs.getString("trdcFromName"));
/*  918 */       translation.setChildRelationshipFromNamePlural(a_rs.getString("trdcFromNamePlural"));
/*  919 */       translation.setChildRelationshipToName(a_rs.getString("trdcToName"));
/*  920 */       translation.setChildRelationshipToNamePlural(a_rs.getString("trdcToNamePlural"));
/*  921 */       translation.setPeerRelationshipToName(a_rs.getString("trdpToName"));
/*  922 */       translation.setPeerRelationshipToNamePlural(a_rs.getString("trdpToNamePlural"));
/*      */ 
/*  924 */       a_entity.getTranslations().add(translation);
/*      */     }
/*      */   }
/*      */ 
/*      */   public AssetEntity saveEntity(DBTransaction a_transaction, AssetEntity a_entity)
/*      */     throws Bn2Exception
/*      */   {
/*  935 */     boolean bAdding = a_entity.getId() <= 0L;
/*      */ 
/*  938 */     a_entity = saveEntityOnlyNoPlugins(a_transaction, a_entity);
/*      */ 
/*  941 */     if (a_entity.getIsCategoryExtension())
/*      */     {
/*  944 */       Long categoriesAttributeId = new Long(this.m_attributeManager.getStaticAttribute("categories").getId());
/*  945 */       Set allowableAttributeIds = new HashSet(a_entity.getAllowableAttributes());
/*  946 */       allowableAttributeIds.remove(categoriesAttributeId);
/*  947 */       a_entity.setAllowableAttributes(new Vector(allowableAttributeIds));
/*      */     }
/*  949 */     setAttributeIdsForEntity(a_transaction, a_entity.getId(), new HashSet(a_entity.getAllowableAttributes()));
/*      */ 
/*  952 */     setAssetTypeIdsForEntity(a_transaction, a_entity.getId(), a_entity.getAllowableAssetTypes());
/*      */ 
/*  956 */     if (bAdding)
/*      */     {
/*  958 */       getPluginManager().addExtensionData(a_transaction, "assetEntity", a_entity);
/*      */     }
/*      */     else
/*      */     {
/*  962 */       getPluginManager().editExistingExtensionData(a_transaction, "assetEntity", a_entity);
/*      */     }
/*      */ 
/*  965 */     return a_entity;
/*      */   }
/*      */ 
/*      */   public AssetEntity saveEntityOnly(DBTransaction a_transaction, AssetEntity a_entity)
/*      */     throws Bn2Exception
/*      */   {
/*  976 */     boolean bAdding = a_entity.getId() <= 0L;
/*      */ 
/*  978 */     a_entity = saveEntityOnlyNoPlugins(a_transaction, a_entity);
/*      */ 
/*  980 */     if (bAdding)
/*      */     {
/*  982 */       getPluginManager().addExtensionData(a_transaction, "assetEntity", a_entity);
/*      */     }
/*      */     else
/*      */     {
/*  986 */       getPluginManager().editExistingExtensionData(a_transaction, "assetEntity", a_entity);
/*      */     }
/*      */ 
/*  989 */     return a_entity;
/*      */   }
/*      */ 
/*      */   private AssetEntity saveEntityOnlyNoPlugins(DBTransaction a_transaction, AssetEntity a_entity)
/*      */     throws Bn2Exception
/*      */   {
/* 1002 */     String ksMethodName = "saveEntityOnlyNoPlugins";
/*      */     try
/*      */     {
/* 1006 */       Connection con = a_transaction.getConnection();
/*      */ 
/* 1008 */       if (a_entity.getId() <= 0L)
/*      */       {
/* 1011 */         AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 1012 */         long lNewId = 0L;
/*      */ 
/* 1014 */         String sSql = "INSERT INTO AssetEntity (";
/*      */ 
/* 1016 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1018 */           lNewId = sqlGenerator.getUniqueId(con, "AssetEntitySequence");
/* 1019 */           sSql = sSql + "Id,";
/*      */         }
/*      */ 
/* 1022 */         sSql = sSql + "Name, ThumbnailFilename, TermForSiblings, TermForSibling, IsSearchable, IsQuickSearchable, IncludedFileFormats, ExcludedFileFormats, MustHaveParent, CanCopyAssets, DefaultCategoryId, UnrestAgreementId, RestAgreementId, ShowAttributeLabels, SequenceNumber, CanDownloadChildren, MatchOnAttributeId, ShowOnDescendantCategories,IsCategoryExtension) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
/*      */ 
/* 1043 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1045 */           sSql = sSql + ",?";
/*      */         }
/*      */ 
/* 1048 */         sSql = sSql + ")";
/*      */ 
/* 1050 */         int iCol = 1;
/* 1051 */         PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 1053 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1055 */           psql.setLong(iCol++, lNewId);
/*      */         }
/*      */ 
/* 1058 */         Vector vEntities = getAllEntities(a_transaction);
/*      */ 
/* 1060 */         psql.setString(iCol++, a_entity.getName());
/* 1061 */         psql.setString(iCol++, a_entity.getThumbnailFilename());
/* 1062 */         psql.setString(iCol++, a_entity.getTermForSiblings());
/* 1063 */         psql.setString(iCol++, a_entity.getTermForSibling());
/* 1064 */         psql.setBoolean(iCol++, a_entity.isSearchable());
/* 1065 */         psql.setBoolean(iCol++, a_entity.isQuickSearchable());
/* 1066 */         psql.setString(iCol++, a_entity.getIncludedFileFormats());
/* 1067 */         psql.setString(iCol++, a_entity.getExcludedFileFormats());
/* 1068 */         psql.setBoolean(iCol++, a_entity.getMustHaveParent());
/* 1069 */         psql.setBoolean(iCol++, a_entity.getCanCopyAssets());
/* 1070 */         psql.setLong(iCol++, a_entity.getDefaultCategoryId());
/* 1071 */         psql.setLong(iCol++, a_entity.getUnrestrictedAgreementId());
/* 1072 */         psql.setLong(iCol++, a_entity.getRestrictedAgreementId());
/* 1073 */         psql.setBoolean(iCol++, a_entity.getShowAttributeLabels());
/* 1074 */         psql.setInt(iCol++, vEntities.size() + 1);
/* 1075 */         psql.setBoolean(iCol++, a_entity.getCanDownloadChildrenFromParent());
/* 1076 */         DBUtil.setFieldIdOrNull(psql, iCol++, a_entity.getMatchOnAttributeId());
/* 1077 */         psql.setBoolean(iCol++, a_entity.getShowOnDescendantCategories());
/* 1078 */         psql.setBoolean(iCol++, a_entity.getIsCategoryExtension());
/*      */ 
/* 1080 */         psql.executeUpdate();
/*      */ 
/* 1083 */         if (sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1085 */           lNewId = sqlGenerator.getUniqueId(con, "AssetEntity");
/*      */         }
/*      */ 
/* 1088 */         psql.close();
/*      */ 
/* 1090 */         a_entity.setId(lNewId);
/*      */ 
/* 1093 */         for (AssetEntity.Translation translation : a_entity.getTranslations())
/*      */         {
/* 1095 */           if (translation.getLanguage().getId() > 0L)
/*      */           {
/* 1097 */             saveEntityTranslation(a_transaction, translation);
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1103 */         String sSql = "UPDATE AssetEntity SET Name=?, ThumbnailFilename=?, TermForSiblings=?, TermForSibling=?, IsSearchable=?, IsQuickSearchable=?, IncludedFileFormats=?, ExcludedFileFormats=?, MustHaveParent=?, CanCopyAssets=?, DefaultCategoryId=?, UnrestAgreementId=?, RestAgreementId=?, ShowAttributeLabels=?, CanDownloadChildren=?, MatchOnAttributeId=?, ShowOnDescendantCategories=?, IsCategoryExtension=? WHERE Id=?";
/*      */ 
/* 1124 */         int iCol = 1;
/* 1125 */         PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 1127 */         psql.setString(iCol++, a_entity.getName());
/* 1128 */         psql.setString(iCol++, a_entity.getThumbnailFilename());
/* 1129 */         psql.setString(iCol++, a_entity.getTermForSiblings());
/* 1130 */         psql.setString(iCol++, a_entity.getTermForSibling());
/* 1131 */         psql.setBoolean(iCol++, a_entity.isSearchable());
/* 1132 */         psql.setBoolean(iCol++, a_entity.isQuickSearchable());
/* 1133 */         psql.setString(iCol++, a_entity.getIncludedFileFormats());
/* 1134 */         psql.setString(iCol++, a_entity.getExcludedFileFormats());
/* 1135 */         psql.setBoolean(iCol++, a_entity.getMustHaveParent());
/* 1136 */         psql.setBoolean(iCol++, a_entity.getCanCopyAssets());
/* 1137 */         psql.setLong(iCol++, a_entity.getDefaultCategoryId());
/* 1138 */         psql.setLong(iCol++, a_entity.getUnrestrictedAgreementId());
/* 1139 */         psql.setLong(iCol++, a_entity.getRestrictedAgreementId());
/* 1140 */         psql.setBoolean(iCol++, a_entity.getShowAttributeLabels());
/* 1141 */         psql.setBoolean(iCol++, a_entity.getCanDownloadChildrenFromParent());
/* 1142 */         DBUtil.setFieldIdOrNull(psql, iCol++, a_entity.getMatchOnAttributeId());
/* 1143 */         psql.setBoolean(iCol++, a_entity.getShowOnDescendantCategories());
/* 1144 */         psql.setBoolean(iCol++, a_entity.getIsCategoryExtension());
/*      */ 
/* 1146 */         psql.setLong(iCol++, a_entity.getId());
/*      */ 
/* 1148 */         psql.executeUpdate();
/*      */ 
/* 1151 */         for (AssetEntity.Translation translation : a_entity.getTranslations())
/*      */         {
/* 1153 */           if (translation.getLanguage().getId() > 0L)
/*      */           {
/* 1155 */             saveEntityTranslation(a_transaction, translation);
/*      */           }
/*      */         }
/*      */ 
/* 1159 */         psql.close();
/*      */       }
/*      */ 
/* 1163 */       this.m_relationshipManager.saveEntityRelationshipDescriptions(a_transaction, a_entity);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1167 */       this.m_logger.error("AssetEntityManager.saveEntityOnlyNoPlugins : SQL Exception : " + e);
/* 1168 */       throw new Bn2Exception("AssetEntityManager.saveEntityOnlyNoPlugins : SQL Exception : " + e, e);
/*      */     }
/*      */ 
/* 1171 */     return a_entity;
/*      */   }
/*      */ 
/*      */   public void setAttributeIdsForEntity(DBTransaction a_transaction, long a_lAssetEntityId, Set<Long> a_attributeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1182 */     String ksMethodName = "setAttributeIdsForEntity";
/* 1183 */     Connection con = null;
/* 1184 */     String sSql = null;
/* 1185 */     PreparedStatement psql = null;
/*      */ 
/* 1188 */     a_attributeIds = new HashSet(a_attributeIds);
/*      */ 
/* 1195 */     a_attributeIds.add(Long.valueOf(301L));
        int rows;
        String sValue;
        Attribute attribute;
/*      */     try
/*      */     {
/* 1199 */       con = a_transaction.getConnection();
/*      */ 
/* 1202 */       Vector vExistingAttributeIds = getAttributeIdsForEntity(a_transaction, a_lAssetEntityId, false);
/* 1203 */       Vector vNewAttributeIds = new Vector(a_attributeIds);
/*      */ 
/* 1205 */       Vector <Long> vAttributesToRemove = (Vector)vExistingAttributeIds.clone();
/* 1206 */       vAttributesToRemove.removeAll(vNewAttributeIds);
/*      */ 
/* 1208 */       Vector<Long> vAttributesToAdd = (Vector)vNewAttributeIds.clone();
/* 1209 */       vAttributesToAdd.removeAll(vExistingAttributeIds);
/*      */ 
/* 1213 */       if (vAttributesToRemove.size() > 0)
/*      */       {
/* 1219 */         StringBuilder sbValueColumnUpdates = new StringBuilder();
/* 1220 */         StringBuilder sbTranslatedValueColumnUpdates = new StringBuilder();
/*      */ 
/* 1224 */         StringBuilder sbValueColumnWhere = new StringBuilder();
/* 1225 */         StringBuilder sbTranslatedValueColumnWhere = new StringBuilder();
/*      */ 
/* 1227 */         Collection<Long> listAttributeIds = new ArrayList();
/*      */ 
/* 1229 */         for (Long tempLong : vAttributesToRemove)
/*      */         {
/* 1231 */           long lAttributeIdToRemove = tempLong.longValue();
/* 1232 */           attribute = this.m_attributeManager.getAttribute(a_transaction, lAttributeIdToRemove);
/*      */ 
/* 1236 */           if (!attribute.getStatic())
/*      */           {
/* 1238 */             AttributeType attributeType = this.m_attributeManager.getAttributeTypeById(attribute.getTypeId());
/* 1239 */             //switch (1.$SwitchMap$com$bright$assetbank$attribute$constant$AttributeStorageType[attributeType.getAttributeStorageType().ordinal()])
                    switch (attributeType.getAttributeStorageType().ordinal())
/*      */             {
/*      */             case 1:
/* 1242 */               String valueColumnName = attribute.getValueColumnName();
/*      */ 
/* 1244 */               sbValueColumnUpdates.append(valueColumnName).append("=NULL,");
/* 1245 */               sbValueColumnWhere.append(valueColumnName).append(" IS NOT NULL AND ");
/*      */ 
/* 1247 */               if (!attribute.getIsTranslatable())
/*      */                 break;
/* 1249 */               sbTranslatedValueColumnUpdates.append(valueColumnName).append("=NULL,");
/* 1250 */               sbTranslatedValueColumnWhere.append(valueColumnName).append(" IS NOT NULL AND "); break;
/*      */             case 2:
/* 1255 */               listAttributeIds.add(Long.valueOf(attribute.getId()));
/*      */             case 3:
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1270 */         deleteAssetAttributeValues(con, "AssetAttributeValues", a_lAssetEntityId, sbValueColumnUpdates, sbValueColumnWhere);
/*      */ 
/* 1272 */         deleteAssetAttributeValues(con, "TranslatedAssetAttributeValues", a_lAssetEntityId, sbTranslatedValueColumnUpdates, sbTranslatedValueColumnWhere);
/*      */ 
/* 1277 */         if (!listAttributeIds.isEmpty())
/*      */         {
/* 1279 */           sSql = "DELETE FROM AssetListAttributeValue WHERE ListAttributeValueId IN (SELECT Id FROM ListAttributeValue WHERE AttributeId IN (" + DBUtil.getPlaceholders(listAttributeIds.size()) + ")) " + "AND AssetId IN (SELECT Id FROM Asset WHERE AssetEntityId=?)";
/*      */ 
/* 1282 */           psql = con.prepareStatement(sSql);
/*      */ 
/* 1284 */           int iParam = 1;
/* 1285 */           for (Long listAttributeId : listAttributeIds)
/*      */           {
/* 1287 */             psql.setLong(iParam++, listAttributeId.longValue());
/*      */           }
/* 1289 */           psql.setLong(iParam++, a_lAssetEntityId);
/* 1290 */           psql.executeUpdate();
/* 1291 */           psql.close();
/*      */         }
/*      */ 
/* 1295 */         sSql = "DELETE FROM AssetEntityAttribute WHERE AssetEntityId=? AND AttributeId IN (" + StringUtil.convertNumbersToString(vAttributesToRemove, ",") + ")";
/*      */ 
/* 1298 */         psql = con.prepareStatement(sSql);
/* 1299 */         psql.setLong(1, a_lAssetEntityId);
/* 1300 */         psql.executeUpdate();
/* 1301 */         psql.close();
/*      */       }
/*      */ 
/* 1304 */       if ((vAttributesToAdd != null) && (!vAttributesToAdd.isEmpty()))
/*      */       {
/* 1306 */         for (int i = 0; i < vAttributesToAdd.size(); i++)
/*      */         {
/* 1308 */           long lAttributeId = ((Long)vAttributesToAdd.get(i)).longValue();
/*      */ 
/* 1310 */           sSql = "INSERT INTO AssetEntityAttribute (AssetEntityId,AttributeId) VALUES (?,?)";
/*      */ 
/* 1312 */           psql = con.prepareStatement(sSql);
/* 1313 */           psql.setLong(1, a_lAssetEntityId);
/* 1314 */           psql.setLong(2, lAttributeId);
/* 1315 */           psql.executeUpdate();
/* 1316 */           psql.close();
/*      */         }
/*      */ 
/* 1325 */         sSql = "INSERT INTO AssetListAttributeValue (AssetId, ListAttributeValueId) SELECT a.Id AS AssetId, lav.Id AS ListAttributeValueId  FROM Attribute att, AttributeType at, Asset a,  ListAttributeValue lav WHERE lav.AttributeId = att.Id AND att.AttributeTypeId = at.Id AND a.AssetEntityId = ? AND at.AttributeStorageTypeId = " + AttributeStorageType.LIST.getId() + " " + "AND att.Id IN (" + DBUtil.getPlaceholders(vAttributesToAdd.size()) + ") " + "AND lav.Value LIKE att.DefaultValue " + "AND NOT EXISTS (SELECT 1 FROM AssetListAttributeValue alavx, ListAttributeValue lavx " + "                WHERE alavx.ListAttributeValueId = lavx.Id AND lavx.AttributeId = att.Id " + "                AND alavx.AssetId = a.Id)";
/*      */ 
/* 1338 */         psql = con.prepareStatement(sSql);
/* 1339 */         int iParam = 1;
/* 1340 */         psql.setLong(iParam++, a_lAssetEntityId);
/* 1341 */         for (Long tempLong : vAttributesToAdd)
/*      */         {
/* 1343 */           long lAttributeId = tempLong.longValue();
/* 1344 */           psql.setLong(iParam++, lAttributeId);
/*      */         }
/* 1346 */         rows = psql.executeUpdate();
/* 1347 */         psql.close();
/* 1348 */         this.m_logger.info("Added " + rows + " default list attribute values for attributes " + vAttributesToAdd + " for entity " + a_lAssetEntityId + " using SQL: " + sSql);
/*      */ 
/* 1352 */         Collection valuePerAssetAttributesToAdd = new ArrayList();
/* 1353 */         for (Long tempLong : vAttributesToAdd)
/*      */         {
/* 1355 */           long lAttributeIdToAdd = tempLong.longValue();
/* 1356 */           attribute = this.m_attributeManager.getAttribute(a_transaction, lAttributeIdToAdd);
/* 1357 */           if (!attribute.getStatic())
/*      */           {
/* 1359 */             AttributeType attributeType = this.m_attributeManager.getAttributeTypeById(attribute.getTypeId());
/* 1360 */             if (attributeType.getAttributeStorageType() == AttributeStorageType.VALUE_PER_ASSET)
/*      */             {
/* 1362 */               valuePerAssetAttributesToAdd.add(attribute);
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 1367 */         for (Iterator i$ = valuePerAssetAttributesToAdd.iterator(); i$.hasNext(); ) { attribute = (Attribute)i$.next();
/*      */ 
/* 1370 */           sValue = attribute.getDefaultValue();
/* 1371 */           sValue = AttributeValueUtil.substituteCalculatedDate(attribute, sValue);
/*      */ 
/* 1373 */           if (StringUtils.isNotEmpty(sValue))
/*      */           {
/* 1375 */             AttributeValue avDefault = new AttributeValue();
/* 1376 */             avDefault.setAttribute(attribute);
/* 1377 */             avDefault.setValue(sValue);
/*      */ 
/* 1379 */             JDBCValue jdbcValue = AttributeValueDBUtil.jdbcValueFromAttributeValue(avDefault);
/*      */ 
/* 1382 */             String valueColumnName = attribute.getValueColumnName();
/* 1383 */             String sNoAttributeValueSQL = AttributeValueDBUtil.getIsNullOrEmptySQL(attribute);
/* 1384 */             sSql = "UPDATE AssetAttributeValues SET " + valueColumnName + "=? " + "WHERE AssetId IN (SELECT Id FROM Asset WHERE AssetEntityId=?) " + "AND " + sNoAttributeValueSQL;
/*      */ 
/* 1387 */             psql = con.prepareStatement(sSql);
/* 1388 */             psql.setObject(1, jdbcValue.getValue(), jdbcValue.getJDBCType());
/* 1389 */             psql.setLong(2, a_lAssetEntityId);
/* 1390 */             rows = psql.executeUpdate();
/* 1391 */             psql.close();
/* 1392 */             this.m_logger.debug("Set " + rows + " default values (" + sValue + ") for attribute " + attribute.getId() + " using SQL: " + sSql);
/*      */ 
/* 1397 */             sSql = "INSERT INTO AssetAttributeValues (AssetId," + valueColumnName + ") " + "SELECT a.Id,? " + "FROM Asset a " + "WHERE a.AssetEntityId=? " + "AND NOT EXISTS (SELECT 1 FROM AssetAttributeValues WHERE AssetId = a.Id)";
/*      */ 
/* 1402 */             psql = con.prepareStatement(sSql);
/* 1403 */             psql.setObject(1, jdbcValue.getValue(), jdbcValue.getJDBCType());
/* 1404 */             psql.setLong(2, a_lAssetEntityId);
/* 1405 */             rows = psql.executeUpdate();
/* 1406 */             psql.close();
/* 1407 */             this.m_logger.debug("Set " + rows + " default values (" + sValue + ") for attribute " + attribute.getId() + " using SQL: " + sSql);
/*      */           }
/*      */ 
/* 1413 */           for (Iterator it = attribute.getTranslations().iterator(); it.hasNext(); )
/*      */           {
/* 1415 */             Attribute.Translation translation = (Attribute.Translation)it.next();
/*      */ 
/* 1417 */             String sTranslatedValue = translation.getDefaultValue();
/* 1418 */             if (StringUtils.isNotEmpty(sTranslatedValue))
/*      */             {
/* 1420 */               AttributeValue avDefault = new AttributeValue();
/* 1421 */               avDefault.setAttribute(attribute);
/* 1422 */               avDefault.setValue(sValue);
/* 1423 */               Language language = translation.getLanguage();
                         AttributeValue.Translation valueTranslation = avDefault.new Translation(language);
/*      */               //AttributeValue tmp1550_1548 = avDefault; tmp1550_1548.getClass(); AttributeValue.Translation valueTranslation = new AttributeValue.Translation(tmp1550_1548, language);
/* 1425 */               valueTranslation.setAttributeTranslation(translation);
/* 1426 */               valueTranslation.setValue(sTranslatedValue);
/*      */ 
/* 1428 */               JDBCValue jdbcValue = AttributeValueDBUtil.jdbcValueFromAttributeValueTranslation(valueTranslation);
/* 1429 */               long lLanguageId = language.getId();
/*      */ 
/* 1432 */               String valueColumnName = attribute.getValueColumnName();
/* 1433 */               String sNoAttributeValueSQL = AttributeValueDBUtil.getIsNullOrEmptySQL(attribute);
/* 1434 */               sSql = "UPDATE TranslatedAssetAttributeValues SET " + valueColumnName + "=? " + "WHERE LanguageId=? " + "AND AssetId IN (SELECT Id FROM Asset WHERE AssetEntityId=?) " + "AND " + sNoAttributeValueSQL;
/*      */ 
/* 1438 */               psql = con.prepareStatement(sSql);
/* 1439 */               psql.setObject(1, jdbcValue.getValue(), jdbcValue.getJDBCType());
/* 1440 */               psql.setLong(2, lLanguageId);
/* 1441 */               psql.setLong(3, a_lAssetEntityId);
/* 1442 */               rows = psql.executeUpdate();
/* 1443 */               psql.close();
/* 1444 */               this.m_logger.debug("Set " + rows + " translated default values (" + valueTranslation + ") for attribute " + attribute.getId() + " using SQL: " + sSql);
/*      */ 
/* 1449 */               sSql = "INSERT INTO TranslatedAssetAttributeValues (AssetId,LanguageId," + valueColumnName + ") " + "SELECT a.Id,?,? " + "FROM Asset a " + "WHERE a.AssetEntityId=? " + "AND NOT EXISTS (SELECT 1 FROM TranslatedAssetAttributeValues WHERE AssetId = a.Id AND LanguageId=?)";
/*      */ 
/* 1454 */               psql = con.prepareStatement(sSql);
/* 1455 */               psql.setLong(1, lLanguageId);
/* 1456 */               psql.setObject(2, jdbcValue.getValue(), jdbcValue.getJDBCType());
/* 1457 */               psql.setLong(3, a_lAssetEntityId);
/* 1458 */               psql.setLong(4, lLanguageId);
/* 1459 */               rows = psql.executeUpdate();
/* 1460 */               psql.close();
/* 1461 */               this.m_logger.debug("Set " + rows + " translated default values (" + valueTranslation + ") for attribute " + attribute.getId() + " using SQL: " + sSql);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */      // int rows;
/*      */       //Iterator i$;
/*      */      // Attribute attribute;
/*      */      // String sValue;
/*      */      // Iterator it;
/* 1472 */       throw new Bn2Exception("AssetEntityManager.setAttributeIdsForEntity : SQL Exception", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deleteAssetAttributeValues(Connection a_con, String a_sTableName, long a_lAssetEntityId, StringBuilder a_sbValueColumnUpdates, StringBuilder a_sbValueColumnWhere)
/*      */     throws SQLException
/*      */   {
/* 1480 */     if (a_sbValueColumnUpdates.length() > 0)
/*      */     {
/* 1483 */       a_sbValueColumnUpdates.setLength(a_sbValueColumnUpdates.length() - 1);
/* 1484 */       a_sbValueColumnWhere.setLength(a_sbValueColumnWhere.length() - " AND ".length());
/*      */ 
/* 1486 */       String sSQL = "UPDATE " + a_sTableName + " SET " + a_sbValueColumnUpdates + " " + "WHERE AssetId IN (SELECT Id FROM Asset WHERE AssetEntityId=?) " + "AND " + a_sbValueColumnWhere;
/*      */ 
/* 1491 */       PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 1492 */       psql.setLong(1, a_lAssetEntityId);
/* 1493 */       int rows = psql.executeUpdate();
/* 1494 */       psql.close();
/*      */ 
/* 1496 */       this.m_logger.info("Deleted attribute values for entity " + a_lAssetEntityId + " for " + rows + " assets using SQL: " + sSQL);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAssetTypeIdsForEntity(DBTransaction a_transaction, long a_lAssetEntityId, Collection<Long> a_assetTypeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1506 */     String ksMethodName = "setAssetTypeIdsForEntity";
/* 1507 */     Connection con = null;
/* 1508 */     String sSql = null;
/* 1509 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1513 */       con = a_transaction.getConnection();
/*      */ 
/* 1515 */       sSql = "DELETE FROM AllowableEntityAssetType WHERE AssetEntityId=?";
/*      */ 
/* 1517 */       psql = con.prepareStatement(sSql);
/* 1518 */       psql.setLong(1, a_lAssetEntityId);
/* 1519 */       psql.executeUpdate();
/* 1520 */       psql.close();
/*      */ 
/* 1522 */       if (a_assetTypeIds != null)
/*      */       {
/* 1524 */         for (Iterator i$ = a_assetTypeIds.iterator(); i$.hasNext(); ) { long assetTypeId = ((Long)i$.next()).longValue();
/*      */ 
/* 1526 */           sSql = "INSERT INTO AllowableEntityAssetType (AssetEntityId,AssetTypeId) VALUES (?,?)";
/*      */ 
/* 1528 */           psql = con.prepareStatement(sSql);
/* 1529 */           psql.setLong(1, a_lAssetEntityId);
/* 1530 */           psql.setLong(2, assetTypeId);
/* 1531 */           psql.executeUpdate();
/* 1532 */           psql.close();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       Iterator i$;
/* 1538 */       this.m_logger.error("AssetEntityManager.setAssetTypeIdsForEntity : SQL Exception : " + e);
/* 1539 */       throw new Bn2Exception("AssetEntityManager.setAssetTypeIdsForEntity : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void saveEntityTranslation(DBTransaction a_dbTransaction, AssetEntity.Translation a_translation)
/*      */     throws Bn2Exception
/*      */   {
/* 1551 */     String ksMethodName = "saveEntityTranslation";
/*      */ 
/* 1553 */     Connection con = null;
/* 1554 */     PreparedStatement psql = null;
/* 1555 */     String sSQL = null;
/*      */     try
/*      */     {
/* 1559 */       con = a_dbTransaction.getConnection();
/* 1560 */       int iCol = 1;
/*      */ 
/* 1562 */       sSQL = "DELETE FROM TranslatedAssetEntity WHERE AssetEntityId=? AND LanguageId=?";
/* 1563 */       psql = con.prepareStatement(sSQL);
/* 1564 */       psql.setLong(iCol++, a_translation.getEntityId());
/* 1565 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 1566 */       psql.executeUpdate();
/* 1567 */       psql.close();
/*      */ 
/* 1569 */       iCol = 1;
/* 1570 */       sSQL = "INSERT INTO TranslatedAssetEntity (Name,TermForSiblings,TermForSibling,AssetEntityId,LanguageId) VALUES (?,?,?,?,?)";
/* 1571 */       psql = con.prepareStatement(sSQL);
/* 1572 */       psql.setString(iCol++, a_translation.getName());
/* 1573 */       psql.setString(iCol++, a_translation.getTermForSiblings());
/* 1574 */       psql.setString(iCol++, a_translation.getTermForSibling());
/* 1575 */       psql.setLong(iCol++, a_translation.getEntityId());
/* 1576 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 1577 */       psql.executeUpdate();
/* 1578 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 1582 */       this.m_logger.error("AssetEntityManager.saveEntityTranslation - " + sqe);
/* 1583 */       throw new Bn2Exception("AssetEntityManager.saveEntityTranslation", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteEntity(DBTransaction a_transaction, long a_lId)
/*      */     throws Bn2Exception, EntityHasAssetsException, EntityHasRelationshipException
/*      */   {
/* 1597 */     String ksMethodName = "deleteEntity";
/*      */ 
/* 1599 */     getPluginManager().delete(a_transaction, "assetEntity", a_lId);
/*      */ 
/* 1603 */     Connection con = null;
/* 1604 */     String sSql = null;
/* 1605 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1608 */       con = a_transaction.getConnection();
/*      */ 
/* 1611 */       sSql = "SELECT Count(Id) numAssets FROM Asset WHERE AssetEntityId=?";
/* 1612 */       psql = con.prepareStatement(sSql);
/* 1613 */       psql.setLong(1, a_lId);
/* 1614 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1616 */       if ((rs.next()) && (rs.getInt("numAssets") > 0))
/*      */       {
/* 1618 */         throw new EntityHasAssetsException();
/*      */       }
/*      */ 
/* 1622 */       sSql = "SELECT Count(AssetEntityId) numRelationships FROM AllowableEntityRelationship WHERE AssetEntityId<>? AND RelatesToAssetEntityId=?";
/* 1623 */       psql = con.prepareStatement(sSql);
/* 1624 */       psql.setLong(1, a_lId);
/* 1625 */       psql.setLong(2, a_lId);
/* 1626 */       rs = psql.executeQuery();
/*      */ 
/* 1628 */       if ((rs.next()) && (rs.getInt("numRelationships") > 0))
/*      */       {
/* 1630 */         throw new EntityHasRelationshipException();
/*      */       }
/*      */ 
/* 1633 */       String[] aSql = { "DELETE FROM TranslatedAssetEntity WHERE AssetEntityId=?", "DELETE FROM TranslatedERDescription WHERE AssetEntityId=?", "DELETE FROM EntityRelationshipDescription WHERE AssetEntityId=?", "DELETE FROM AllowableEntityRelationship WHERE AssetEntityId=?", "DELETE FROM AllowableEntityAssetType WHERE AssetEntityId=?", "DELETE FROM AssetEntityAttribute WHERE AssetEntityId=?", "DELETE FROM AssetEntity WHERE Id=?" };
/*      */ 
/* 1641 */       for (String sDeleteSql : aSql)
/*      */       {
/* 1643 */         psql = con.prepareStatement(sDeleteSql);
/* 1644 */         psql.setLong(1, a_lId);
/* 1645 */         psql.executeUpdate();
/*      */       }
/*      */ 
/* 1648 */       psql.close();
/*      */ 
/* 1650 */       this.m_relationshipManager.invalidateChildEntityIdsCache();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1654 */       this.m_logger.error("AssetEntityManager.deleteEntity : SQL Exception : " + e);
/* 1655 */       throw new Bn2Exception("AssetEntityManager.deleteEntity : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void reorderEntities(DBTransaction a_dbTransaction, Collection<AssetEntity> a_entities)
/*      */     throws Bn2Exception
/*      */   {
/* 1670 */     String ksMethodName = "reorderEntities";
/* 1671 */     Connection con = null;
        String sSql;
        PreparedStatement psql;
        int iSeqNo;
/*      */     try
/*      */     {
/* 1675 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1677 */       sSql = null;
/* 1678 */       psql = null;
/* 1679 */       iSeqNo = 1;
/* 1680 */       for (AssetEntity entity : a_entities)
/*      */       {
/* 1682 */         sSql = "UPDATE AssetEntity SET SequenceNumber=? WHERE Id=?";
/* 1683 */         psql = con.prepareStatement(sSql);
/* 1684 */         psql.setInt(1, iSeqNo++);
/* 1685 */         psql.setLong(2, entity.getId());
/*      */ 
/* 1687 */         psql.executeUpdate();
/* 1688 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */      // String sSql;
/*      */       //PreparedStatement psql;
/*      */       //int iSeqNo;
/* 1693 */       this.m_logger.error("AssetEntityManager.reorderEntities : SQL Exception : " + e);
/* 1694 */       throw new Bn2Exception("AssetEntityManager.reorderEntities : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean getEntitiesHaveMatchAttribute(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1705 */     Vector<AssetEntity> vecEntities = getAllEntities(a_transaction);
/*      */ 
/* 1707 */     for (AssetEntity entity : vecEntities)
/*      */     {
/* 1709 */       if (entity.getMatchOnAttributeId() > 0L)
/*      */       {
/* 1711 */         return true;
/*      */       }
/*      */     }
/*      */ 
/* 1715 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getChildEntitiesHaveMatchAttribute(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1724 */     Vector<AssetEntity> vecEntities = getAllEntities(a_transaction);
/*      */ 
/* 1726 */     for (AssetEntity entity : vecEntities)
/*      */     {
/* 1728 */       if ((entity.getMatchOnAttributeId() > 0L) && (this.m_relationshipManager.isChildEntity(a_transaction, entity.getId())))
/*      */       {
/* 1730 */         return true;
/*      */       }
/*      */     }
/*      */ 
/* 1734 */     return false;
/*      */   }
/*      */ 
/*      */   public Vector<Long> searchForIdsOfAssetEntityInstances(long a_lEntityId)
/*      */     throws Bn2Exception
/*      */   {
/* 1750 */     SearchCriteria criteria = new SearchCriteria();
/* 1751 */     criteria.addAssetEntityIdToInclude(a_lEntityId);
/* 1752 */     SearchResults results = this.m_searchManager.search(criteria);
/* 1753 */     Vector alAssets = new Vector();
/*      */ 
/* 1755 */     if ((results.getSearchResults() != null) && (results.getSearchResults().size() > 0))
/*      */     {
/* 1758 */       for (LightweightAsset asset : (Vector<LightweightAsset>)results.getSearchResults())
/*      */       {
/* 1760 */         alAssets.add(Long.valueOf(asset.getId()));
/*      */       }
/*      */     }
/*      */ 
/* 1764 */     return alAssets;
/*      */   }
/*      */ 
/*      */   public AssetEntity buildAssetEntity(DBTransaction a_transaction, ResultSet a_rs)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1772 */     AssetEntity entity = new AssetEntity();
/* 1773 */     entity.setId(a_rs.getLong("aeId"));
/* 1774 */     entity.setName(a_rs.getString("aeName"));
/* 1775 */     entity.setThumbnailFilename(a_rs.getString("aeThumbnailFilename"));
/* 1776 */     entity.setTermForSiblings(a_rs.getString("aeTermForSiblings"));
/* 1777 */     entity.setTermForSibling(a_rs.getString("aeTermForSibling"));
/* 1778 */     entity.setSearchable(a_rs.getBoolean("aeIsSearchable"));
/* 1779 */     entity.setQuickSearchable(a_rs.getBoolean("aeIsQuickSearchable"));
/* 1780 */     entity.setIncludedFileFormats(a_rs.getString("aeIncludedFileFormats"));
/* 1781 */     entity.setExcludedFileFormats(a_rs.getString("aeExcludedFileFormats"));
/* 1782 */     entity.setMustHaveParent(a_rs.getBoolean("aeMustHaveParent"));
/* 1783 */     entity.setCanCopyAssets(a_rs.getBoolean("aeCanCopyAssets"));
/* 1784 */     entity.setDefaultCategoryId(a_rs.getLong("aeDefaultCategoryId"));
/* 1785 */     entity.setUnrestrictedAgreementId(a_rs.getLong("aeUnrestAgreementId"));
/* 1786 */     entity.setRestrictedAgreementId(a_rs.getLong("aeRestAgreementId"));
/* 1787 */     entity.setShowAttributeLabels(a_rs.getBoolean("aeShowAttributeLabels"));
/* 1788 */     entity.setCanDownloadChildrenFromParent(a_rs.getBoolean("aeCanDownloadChildren"));
/* 1789 */     entity.setMatchOnAttributeId(a_rs.getLong("aeMatchOnAttributeId"));
/* 1790 */     entity.setShowOnDescendantCategories(a_rs.getBoolean("ShowOnDescendantCategories"));
/* 1791 */     entity.setIsCategoryExtension(a_rs.getBoolean("IsCategoryExtension"));
/*      */ 
/* 1793 */     entity.setAllowableAssetTypes(getAssetTypeIdsForEntity(a_transaction, entity.getId()));
/*      */ 
/* 1795 */     ArrayList alChildRelationships = this.m_relationshipManager.getAllowableAssetEntityRelationships(a_transaction, entity.getId(), 2L);
/* 1796 */     ArrayList alPeerRelationships = this.m_relationshipManager.getAllowableAssetEntityRelationships(a_transaction, entity.getId(), 1L);
/*      */ 
/* 1798 */     entity.setChildRelationships(alChildRelationships);
/* 1799 */     entity.setPeerRelationships(alPeerRelationships);
/*      */ 
/* 1801 */     this.m_relationshipManager.populateEntityRelationshipDescriptions(a_transaction, entity);
/*      */ 
/* 1803 */     entity.setAllowChildren((alChildRelationships != null) && (alChildRelationships.size() > 0));
/* 1804 */     entity.setAllowPeers((alPeerRelationships != null) && (alPeerRelationships.size() > 0));
/*      */ 
/* 1806 */     return entity;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager transactionManager)
/*      */   {
/* 1812 */     this.m_transactionManager = transactionManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager attributeManager)
/*      */   {
/* 1817 */     this.m_attributeManager = attributeManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/* 1822 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityRelationshipManager(AssetEntityRelationshipManager a_relationshipManager)
/*      */   {
/* 1827 */     this.m_relationshipManager = a_relationshipManager;
/*      */   }
/*      */ 
/*      */   public PluginManager getPluginManager()
/*      */   {
/* 1832 */     return this.m_pluginManager;
/*      */   }
/*      */ 
/*      */   public void setPluginManager(PluginManager a_pluginManager)
/*      */   {
/* 1837 */     this.m_pluginManager = a_pluginManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.service.AssetEntityManager
 * JD-Core Version:    0.6.0
 */