/*      */ package com.bright.assetbank.entity.relationship.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.LightweightAsset;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*      */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*      */ import com.bright.assetbank.application.service.AssetManager;
/*      */ import com.bright.assetbank.application.service.AssetSaveContext;
/*      */ import com.bright.assetbank.application.service.AssetSaveParticipant;
/*      */ import com.bright.assetbank.approval.bean.AssetInList;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.batch.service.BatchQueueManager;
/*      */ import com.bright.assetbank.batch.upload.service.ImportManager;
/*      */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*      */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*      */ import com.bright.assetbank.entity.relationship.bean.EmptyRelatedAssetBatch;
/*      */ import com.bright.assetbank.entity.relationship.bean.RelationshipDescriptionEntry;
/*      */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*      */ import com.bright.assetbank.search.bean.SearchCriteria;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.user.bean.Group;
/*      */ import com.bright.assetbank.user.constant.UserConstants;
/*      */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*      */ import com.bright.framework.common.bean.NameValueBean;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.bean.DataBean;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.queue.bean.QueuedItem;
/*      */ import com.bright.framework.search.bean.SearchResults;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.collections.CollectionUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AssetRelationshipManager extends BatchQueueManager
/*      */   implements AssetEntityConstants, AssetSaveParticipant, AssetBankConstants, UserConstants
/*      */ {
/*      */   private DBTransactionManager m_transactionManager;
/*      */   private MultiLanguageSearchManager m_searchManager;
/*      */   private AssetManager m_assetManager;
/*      */   private AssetEntityManager m_assetEntityManager;
/*      */   private ImportManager m_importManager;
/*      */   private AttributeManager m_attributeManager;
/*      */   private AssetCategoryManager m_categoryManager;
/*      */ 
/*      */   public AssetRelationshipManager()
/*      */   {
/*   81 */     this.m_transactionManager = null;
/*   82 */     this.m_searchManager = null;
/*   83 */     this.m_assetManager = null;
/*   84 */     this.m_assetEntityManager = null;
/*   85 */     this.m_importManager = null;
/*   86 */     this.m_attributeManager = null;
/*   87 */     this.m_categoryManager = null;
/*      */   }
/*      */ 
/*      */   public void startup() throws Bn2Exception {
/*   91 */     super.startup();
/*      */ 
/*   94 */     this.m_assetManager.registerAssetSaveParticipant(this);
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/*   99 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/*  104 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(AssetManager a_assetManager)
/*      */   {
/*  109 */     this.m_assetManager = a_assetManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager a_attributeManager)
/*      */   {
/*  114 */     this.m_attributeManager = a_attributeManager;
/*      */   }
/*      */ 
/*      */   public void setImportManager(ImportManager a_importManager)
/*      */   {
/*  119 */     this.m_importManager = a_importManager;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityManager(AssetEntityManager a_assetEntityManager)
/*      */   {
/*  124 */     this.m_assetEntityManager = a_assetEntityManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*      */   {
/*  129 */     this.m_categoryManager = a_categoryManager;
/*      */   }
/*      */ 
/*      */   public Asset getRelatedAssetByFilename(DBTransaction a_dbTransaction, long a_lAssetId, String a_sFilename)
/*      */     throws AssetNotFoundException, Bn2Exception
/*      */   {
/*  151 */     DBTransaction transaction = a_dbTransaction;
/*  152 */     String sSQL = null;
/*  153 */     Connection con = null;
/*  154 */     ResultSet rs = null;
/*  155 */     Asset asset = null;
/*  156 */     ApplicationSql appSql = SQLGenerator.getInstance();
/*  157 */     PreparedStatement psql = null;
/*      */ 
/*  159 */     if (transaction == null)
/*      */     {
/*  161 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  166 */       con = transaction.getConnection();
/*      */ 
/*  168 */       if (StringUtils.isNotEmpty(a_sFilename))
/*      */       {
/*  170 */         sSQL = "SELECT a.Id FROM Asset a LEFT JOIN RelatedAsset ra ON (a.Id=ra.ChildId OR a.Id=ra.ParentId) WHERE " + appSql.getLowerCaseFunction("a.OriginalFilename") + "=" + appSql.getLowerCaseFunction("?") + " AND (NOT " + appSql.getNullCheckStatement("ra.ParentId") + " OR a.Id=?)" + "ORDER BY a.Id DESC";
/*      */ 
/*  176 */         psql = con.prepareStatement(sSQL);
/*  177 */         psql.setString(1, a_sFilename);
/*  178 */         psql.setLong(2, a_lAssetId);
/*  179 */         rs = psql.executeQuery();
/*      */ 
/*  181 */         if (rs.next())
/*      */         {
/*  183 */           asset = this.m_assetManager.getAsset(a_dbTransaction, rs.getLong("a.Id"), null, false, false);
/*      */         }
/*      */ 
/*  186 */         psql.close();
/*      */       }
/*      */       else
/*      */       {
/*  190 */         asset = this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, null, false, false);
/*      */       }
/*      */ 
/*  193 */       if (asset == null)
/*      */       {
/*  195 */         throw new AssetNotFoundException("No asset found with filename '" + a_sFilename + "' related to asset id=" + a_lAssetId);
/*      */       }
/*      */ 
/*  198 */       Asset localAsset1 = asset;
/*      */       return localAsset1;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  202 */       this.m_logger.error("SQL Exception in AssetManager.getImportedAsset : " + e);
/*  203 */       throw new Bn2Exception("SQL Exception in AssetManager.getImportedAsset : ", e);
/*      */     }
/*      */     finally
/*      */     {
/*  208 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  212 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  216 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           throw new Bn2Exception(sqle.getMessage());
                   }
/*      */       }
/*  217 */     }//throw localObject;
/*      */   }
/*      */ 
/*      */   public ArrayList<RelationshipDescriptionEntry> getRelationshipDescriptionsForAssetAsTarget(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  235 */     DBTransaction transaction = a_dbTransaction;
/*  236 */     boolean bError = false;
/*  237 */     ArrayList alEntries = new ArrayList();
/*      */     try
/*      */     {
/*  240 */       if (transaction == null)
/*      */       {
/*  242 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/*      */ 
/*  245 */       Connection con = transaction.getConnection();
/*      */ 
/*  247 */       String sSQL = "SELECT * FROM RelatedAsset WHERE ChildId=?";
/*  248 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  249 */       psql.setLong(1, a_lAssetId);
/*  250 */       ResultSet rs = psql.executeQuery();
/*  251 */       while (rs.next())
/*      */       {
/*  253 */         RelationshipDescriptionEntry entry = buildRelationshipDescriptionEntry(rs);
/*  254 */         alEntries.add(entry);
/*      */       }
/*  256 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  260 */       bError = true;
/*  261 */       throw new Bn2Exception(getClass().getSimpleName() + ".getRelationshipDescriptionsForAssetAsTarget: Error: ", e);
/*      */     }
/*      */     finally
/*      */     {
/*  266 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  270 */           if (bError)
/*      */           {
/*  272 */             transaction.rollback();
/*      */           }
/*      */           else
/*      */           {
/*  276 */             transaction.commit();
/*      */           }
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  281 */           this.m_logger.error(getClass().getSimpleName() + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  286 */     return alEntries;
/*      */   }
/*      */ 
/*      */   public void saveAssetRelationshipDescriptions(DBTransaction a_dbTransaction, ArrayList<RelationshipDescriptionEntry> a_alEntries)
/*      */     throws Bn2Exception
/*      */   {
/*  302 */     DBTransaction transaction = a_dbTransaction;
/*  303 */     boolean bError = false;
/*      */     try
/*      */     {
/*  307 */       if (transaction == null)
/*      */       {
/*  309 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/*      */ 
/*  312 */       Connection con = transaction.getConnection();
/*      */ 
/*  314 */       String sSQL = "UPDATE RelatedAsset SET RelationshipDescription=? WHERE ParentId=? AND ChildId=? AND RelationshipTypeId=?";
/*  315 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  318 */       for (RelationshipDescriptionEntry entry : a_alEntries)
/*      */       {
/*  320 */         psql.setString(1, entry.getDescription());
/*  321 */         psql.setLong(2, entry.getSourceAssetId());
/*  322 */         psql.setLong(3, entry.getTargetAssetId());
/*  323 */         psql.setLong(4, entry.getRelationshipTypeId());
/*  324 */         psql.executeUpdate();
/*      */       }
/*  326 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  330 */       bError = true;
/*  331 */       throw new Bn2Exception(getClass().getSimpleName() + ".saveAssetRelationshipDescriptions: Error: ", e);
/*      */     }
/*      */     finally
/*      */     {
/*  336 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  340 */           if (bError)
/*      */           {
/*  342 */             transaction.rollback();
/*      */           }
/*      */           else
/*      */           {
/*  346 */             transaction.commit();
/*      */           }
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  351 */           this.m_logger.error(getClass().getSimpleName() + ": SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<LightweightAsset> getRelatedAssets(DBTransaction a_dbTransaction, long[] a_alAssetIds, ABUserProfile a_userProfile, long a_lRelationshipTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  374 */     SearchCriteria criteria = new SearchCriteria();
/*      */ 
/*  376 */     if (a_lRelationshipTypeId == 1L)
/*      */     {
/*  378 */       criteria.setPeerAssetIds(a_alAssetIds);
/*      */     }
/*      */ 
/*  381 */     if (a_lRelationshipTypeId == 2L)
/*      */     {
/*  383 */       criteria.setParentAssetIds(a_alAssetIds);
/*      */     }
/*      */ 
/*  387 */     criteria.setupPermissions(a_userProfile);
/*  388 */     criteria.setSelectedFilters(a_userProfile.getSelectedFilters());
/*      */ 
/*  391 */     criteria.setMaxNoOfResults(AssetBankSettings.getRelatedAssetsMaxForView());
/*      */ 
/*  394 */     SearchResults submittedSearchResults = this.m_searchManager.search(criteria, a_userProfile.getCurrentLanguage().getCode());
/*      */ 
/*  397 */     criteria.setIsUnsubmitted(Boolean.valueOf(true));
/*  398 */     SearchResults unsubmittedSearchResults = this.m_searchManager.searchByPageIndex(criteria, -1, -1, a_userProfile.getCurrentLanguage().getCode());
/*  399 */     Vector vResults = null;
/*      */ 
/*  401 */     Connection con = null;
/*  402 */     ResultSet rs = null;
/*  403 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/*      */       Vector<LightweightAsset> vecResultsToCheck;
/*  408 */       if (unsubmittedSearchResults != null)
/*      */       {
/*  410 */         vecResultsToCheck = unsubmittedSearchResults.getSearchResults();
/*  411 */         HashMap hmResults = new HashMap();
/*  412 */         String sIds = "";
/*  413 */         for (LightweightAsset tempAsset : vecResultsToCheck)
/*      */         {
/*  415 */           hmResults.put(new Long(tempAsset.getId()), tempAsset);
/*  416 */           sIds = sIds + tempAsset.getId() + ",";
/*      */         }
/*      */ 
/*  420 */         if (StringUtil.stringIsPopulated(sIds))
/*      */         {
/*  422 */           sIds = sIds.substring(0, sIds.length() - 1);
/*  423 */           String sSQL = "SELECT Id, AddedByUserId FROM Asset WHERE Id IN (" + sIds + ")";
/*  424 */           if (transaction == null)
/*      */           {
/*  426 */             transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */           }
/*  428 */           con = transaction.getConnection();
/*  429 */           PreparedStatement psql = con.prepareStatement(sSQL);
/*  430 */           rs = psql.executeQuery();
/*  431 */           while (rs.next())
/*      */           {
/*  433 */             long lAssetId = rs.getLong("Id");
/*  434 */             long lUserId = rs.getLong("AddedByUserId");
/*      */ 
/*  436 */             if (lUserId == a_userProfile.getUser().getId())
/*      */             {
/*  438 */               LightweightAsset temp = (LightweightAsset)hmResults.get(new Long(lAssetId));
/*  439 */               if (temp != null)
/*      */               {
/*  441 */                 if (vResults == null)
/*      */                 {
/*  443 */                   vResults = new Vector();
/*      */                 }
/*  445 */                 vResults.add(temp);
/*      */               }
/*      */             }
/*      */           }
/*  449 */           psql.close();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  454 */       if ((submittedSearchResults == null) && (vResults == null))
/*      */       {
/*  456 */         vecResultsToCheck = null;
/*      */         return vecResultsToCheck;
/*      */       }
/*  459 */       if (vResults == null)
/*      */       {
/*  461 */         vResults = new Vector();
/*      */       }
/*      */ 
/*  464 */       vResults.addAll(submittedSearchResults.getSearchResults());
/*      */ 
/*  467 */       if ((a_lRelationshipTypeId == 2L) && (a_alAssetIds.length == 1) && (vResults != null) && (vResults.size() > 0))
/*      */       {
/*  469 */         if (con == null)
/*      */         {
/*  471 */           if (transaction == null)
/*      */           {
/*  473 */             transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */           }
/*  475 */           con = transaction.getConnection();
/*      */         }
/*      */ 
/*  478 */         String sChildIds = "0";
/*  479 */         HashMap hmChildren = new HashMap();
/*      */ 
/*  481 */         for (int i = 0; i < vResults.size(); i++)
/*      */         {
/*  483 */           DataBean result = (DataBean)vResults.get(i);
/*  484 */           sChildIds = sChildIds + "," + result.getId();
/*  485 */           hmChildren.put(Long.valueOf(result.getId()), result);
/*      */         }
/*      */ 
/*  488 */         String sSQL = "SELECT ChildId, IsSensitive FROM RelatedAsset, Asset WHERE Id=ChildId AND ParentId=? AND ChildId in (" + sChildIds + ") AND RelationshipTypeId=? " + "ORDER BY SequenceNumber";
/*      */ 
/*  494 */         PreparedStatement psql = con.prepareStatement(sSQL);
/*  495 */         psql.setLong(1, a_alAssetIds[0]);
/*  496 */         psql.setLong(2, 2L);
/*      */ 
/*  498 */         rs = psql.executeQuery();
/*      */ 
/*  500 */         int i = 0;
/*      */ 
/*  502 */         while (rs.next())
/*      */         {
/*  504 */           if (!hmChildren.containsKey(Long.valueOf(rs.getLong("ChildId"))))
/*      */             continue;
/*  506 */           LightweightAsset a = (LightweightAsset)hmChildren.get(Long.valueOf(rs.getLong("ChildId")));
/*  507 */           a.setIsSensitive(rs.getBoolean("IsSensitive"));
/*  508 */           vResults.set(i++, a);
/*      */         }
/*      */ 
/*  512 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  517 */       this.m_logger.error("SQL Exception whilst getting the order of related assets : " + e);
/*  518 */       throw new Bn2Exception("SQL Exception whilst getting the order of related assets : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  523 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  527 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  531 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  536 */     return vResults;
/*      */   }
/*      */ 
/*      */   public HashMap<String, Vector<LightweightAsset>> getRelatedAssetsByCategoryNames(DBTransaction a_dbTransaction, long[] a_alAssetIds, ABUserProfile a_userProfile, long a_lRelationshipTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  547 */     Vector vecRelatedAssets = getRelatedAssets(a_dbTransaction, a_alAssetIds, a_userProfile, a_lRelationshipTypeId);
/*  548 */     HashMap hmCategorisedRelatedAssets = null;
/*      */ 
/*  550 */     if (vecRelatedAssets != null)
/*      */     {
/*  552 */       for (int i = 0; i < vecRelatedAssets.size(); i++)
/*      */       {
/*  554 */         LightweightAsset asset = (LightweightAsset)vecRelatedAssets.elementAt(i);
/*  555 */         String sCatKey = asset.getDescriptiveCategoryNamesString();
/*      */ 
/*  557 */         if (!StringUtil.stringIsPopulated(sCatKey))
/*      */         {
/*  560 */           sCatKey = "Uncategorised";
/*      */         }
/*      */ 
/*  563 */         String[] aCategoryNames = sCatKey.split(",");
/*      */ 
/*  565 */         for (int x = 0; x < aCategoryNames.length; x++)
/*      */         {
/*  567 */           LightweightAsset newAsset = new LightweightAsset(asset);
/*  568 */           Vector vecAssets = null;
/*      */ 
/*  570 */           if (hmCategorisedRelatedAssets == null)
/*      */           {
/*  572 */             hmCategorisedRelatedAssets = new HashMap();
/*      */           }
/*      */ 
/*  575 */           if (!hmCategorisedRelatedAssets.containsKey(aCategoryNames[x]))
/*      */           {
/*  577 */             vecAssets = new Vector();
/*      */           }
/*      */           else
/*      */           {
/*  581 */             vecAssets = (Vector)hmCategorisedRelatedAssets.get(aCategoryNames[x]);
/*      */           }
/*      */ 
/*  584 */           vecAssets.add(newAsset);
/*  585 */           hmCategorisedRelatedAssets.put(aCategoryNames[x], vecAssets);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  590 */     return hmCategorisedRelatedAssets;
/*      */   }
/*      */ 
/*      */   public String getRelatedAssetIdString(DBTransaction a_dbTransaction, long a_lAssetId, long a_lRelationshipTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  611 */     Vector vecRelatedAssetIds = getRelatedAssetIds(a_dbTransaction, a_lAssetId, a_lRelationshipTypeId);
/*  612 */     return StringUtil.convertNumbersToString(vecRelatedAssetIds, ",");
/*      */   }
/*      */ 
/*      */   public Vector<Long> getRelatedAssetIds(DBTransaction a_dbTransaction, long a_lAssetId, long a_lRelationshipTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  636 */     LinkedHashSet assetIds = new LinkedHashSet();
/*  637 */     Connection con = null;
/*  638 */     ResultSet rs = null;
/*  639 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  641 */     long lRelationshipIdForQuery = a_lRelationshipTypeId == 1L ? 1L : 2L;
/*      */ 
/*  645 */     if (transaction == null)
/*      */     {
/*  647 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  652 */       con = transaction.getConnection();
/*      */ 
/*  654 */       String sSQL = null;
/*      */ 
/*  668 */       if (a_lRelationshipTypeId == 3L)
/*      */       {
/*  670 */         sSQL = "SELECT ParentId, ChildId FROM RelatedAsset WHERE ChildId=? AND RelationshipTypeId=" + lRelationshipIdForQuery;
/*      */       }
/*      */       else
/*      */       {
/*  674 */         sSQL = "SELECT ChildId, ParentId FROM RelatedAsset WHERE ParentId=? AND RelationshipTypeId=" + lRelationshipIdForQuery + " ORDER BY SequenceNumber";
/*      */       }
/*      */ 
/*  679 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  680 */       psql.setLong(1, a_lAssetId);
/*      */ 
/*  682 */       rs = psql.executeQuery();
/*      */ 
/*  684 */       while (rs.next())
/*      */       {
/*  686 */         if ((a_lRelationshipTypeId == 2L) || (a_lRelationshipTypeId == 1L))
/*      */         {
/*  688 */           assetIds.add(new Long(rs.getLong("ChildId"))); continue;
/*      */         }
/*  690 */         if ((a_lRelationshipTypeId != 3L) || (rs.getLong("ChildId") != a_lAssetId))
/*      */           continue;
/*  692 */         assetIds.add(new Long(rs.getLong("ParentId")));
/*      */       }
/*      */ 
/*  695 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  699 */       this.m_logger.error("SQL Exception whilst getting the related assets : " + e);
/*  700 */       throw new Bn2Exception("SQL Exception whilst getting the related assets : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  705 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  709 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  713 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  718 */     return new Vector(assetIds);
/*      */   }
/*      */ 
/*      */   public HashMap<Long, Boolean> getAssetRelationshipMap(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  738 */     HashMap hmRelationshipMap = new HashMap();
/*      */ 
/*  740 */     Connection con = null;
/*  741 */     ResultSet rs = null;
/*  742 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  744 */     if (transaction == null)
/*      */     {
/*  746 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  751 */       con = transaction.getConnection();
/*      */ 
/*  753 */       String sSQL = "SELECT a.Id, ra.ParentId, ra.ChildId FROM Asset a LEFT JOIN RelatedAsset ra ON a.Id=ra.ParentId OR a.Id=ra.ChildId ORDER BY a.Id";
/*      */ 
/*  758 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  759 */       rs = psql.executeQuery();
/*  760 */       long lCurrentAssetId = 0L;
/*      */ 
/*  762 */       while (rs.next())
/*      */       {
/*  764 */         if (rs.getLong("Id") == lCurrentAssetId)
/*      */           continue;
/*  766 */         lCurrentAssetId = rs.getLong("Id");
/*      */ 
/*  768 */         if ((rs.getLong("ParentId") <= 0L) && (rs.getLong("ChildId") <= 0L))
/*      */         {
/*  771 */           hmRelationshipMap.put(new Long(lCurrentAssetId), new Boolean(false)); continue;
/*      */         }
/*      */ 
/*  776 */         hmRelationshipMap.put(new Long(lCurrentAssetId), new Boolean(true));
/*      */       }
/*      */ 
/*  780 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  784 */       this.m_logger.error("SQL Exception whilst getting the related assets map : " + e);
/*  785 */       throw new Bn2Exception("SQL Exception whilst getting the related assets map : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  790 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  794 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  798 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  803 */     return hmRelationshipMap;
/*      */   }
/*      */ 
/*      */   public void relateAssets(DBTransaction a_dbTransaction, Vector<AssetInList> a_vecAssets)
/*      */     throws Bn2Exception
/*      */   {
/*  825 */     relateAssets(a_dbTransaction, a_vecAssets, false);
/*      */   }
/*      */ 
/*      */   public void relateAssets(DBTransaction a_dbTransaction, Vector a_vecAssets, boolean a_bIdsOnly)
/*      */     throws Bn2Exception
/*      */   {
/*  851 */     Connection con = null;
/*  852 */     DBTransaction transaction = a_dbTransaction;
/*  853 */     Vector vecAssetsToIndex = new Vector();
/*  854 */     for (int i = 0; i < a_vecAssets.size(); i++)
/*      */     {
/*  856 */       if (!a_bIdsOnly)
/*      */       {
/*  858 */         AssetInList temp = (AssetInList)a_vecAssets.elementAt(i);
/*  859 */         vecAssetsToIndex.add(new Long(temp.getId()));
/*      */       }
/*      */       else
/*      */       {
/*  863 */         vecAssetsToIndex.add((Long)a_vecAssets.elementAt(i));
/*      */       }
/*      */     }
/*      */ 
/*  867 */     if (transaction == null)
/*      */     {
/*  869 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  874 */       con = transaction.getConnection();
/*      */ 
/*  877 */       String sDeleteSQL = "DELETE FROM RelatedAsset WHERE ParentId=? AND ChildId=? AND RelationshipTypeId=?";
/*  878 */       String sSQL = "INSERT INTO RelatedAsset (ParentId, ChildId, RelationshipTypeId) VALUES (?,?,?)";
/*      */ 
/*  881 */       PreparedStatement delPsql = con.prepareStatement(sDeleteSQL);
/*  882 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  885 */       long lFirstId = 0L;
/*  886 */       long lSecondId = 0L;
/*  887 */       AssetInList firstAsset = null;
/*  888 */       AssetInList tempAsset = null;
/*      */ 
/*  890 */       while (!a_vecAssets.isEmpty())
/*      */       {
/*  892 */         if (!a_bIdsOnly)
/*      */         {
/*  894 */           firstAsset = (AssetInList)a_vecAssets.firstElement();
/*  895 */           lFirstId = firstAsset.getAsset().getId();
/*      */         }
/*      */         else
/*      */         {
/*  899 */           lFirstId = ((Long)a_vecAssets.firstElement()).longValue();
/*      */         }
/*      */ 
/*  902 */         a_vecAssets.removeElementAt(0);
/*      */ 
/*  905 */         for (int i = 0; i < a_vecAssets.size(); i++)
/*      */         {
/*  907 */           if (!a_bIdsOnly)
/*      */           {
/*  909 */             tempAsset = (AssetInList)a_vecAssets.elementAt(i);
/*  910 */             lSecondId = tempAsset.getAsset().getId();
/*      */           }
/*      */           else
/*      */           {
/*  914 */             lSecondId = ((Long)a_vecAssets.elementAt(i)).longValue();
/*      */           }
/*      */ 
/*  918 */           delPsql.setLong(1, lFirstId);
/*  919 */           delPsql.setLong(2, lSecondId);
/*  920 */           delPsql.setLong(3, 1L);
/*  921 */           delPsql.executeUpdate();
/*      */ 
/*  923 */           delPsql.setLong(1, lSecondId);
/*  924 */           delPsql.setLong(2, lFirstId);
/*  925 */           delPsql.setLong(3, 1L);
/*  926 */           delPsql.executeUpdate();
/*      */ 
/*  929 */           psql.setLong(1, lFirstId);
/*  930 */           psql.setLong(2, lSecondId);
/*  931 */           psql.setLong(3, 1L);
/*  932 */           psql.executeUpdate();
/*      */ 
/*  934 */           psql.setLong(1, lSecondId);
/*  935 */           psql.setLong(2, lFirstId);
/*  936 */           psql.setLong(3, 1L);
/*  937 */           psql.executeUpdate();
/*      */         }
/*      */       }
/*      */ 
/*  941 */       psql.close();
/*  942 */       delPsql.close();
/*      */ 
/*  945 */       for (int i = 0; i < vecAssetsToIndex.size(); i++)
/*      */       {
/*  947 */         long lId = ((Long)vecAssetsToIndex.elementAt(i)).longValue();
/*  948 */         Asset asset = this.m_assetManager.getAsset(transaction, lId, null, true, true);
/*      */ 
/*  951 */         this.m_searchManager.indexDocument(asset, true);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  956 */       this.m_logger.error("SQL Exception whilst setting up the related assets : " + e);
/*  957 */       throw new Bn2Exception("SQL Exception whilst setting up the related assets : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  962 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  966 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  970 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<Long> deleteAssetRelationship(DBTransaction a_dbTransaction, long a_lParentAssetId, long a_lChildAssetId, long a_lRelationshipTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  984 */     return deleteAssetRelationship(a_dbTransaction, a_lParentAssetId, a_lChildAssetId, a_lRelationshipTypeId, false);
/*      */   }
/*      */ 
/*      */   public Vector<Long> deleteAssetRelationship(DBTransaction a_dbTransaction, long a_lParentAssetId, long a_lChildAssetId, long a_lRelationshipTypeId, boolean a_bIgnoreCategoryCheck)
/*      */     throws Bn2Exception
/*      */   {
/* 1011 */     Vector vAffectedAssetIds = new Vector();
/* 1012 */     Connection con = null;
/* 1013 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1015 */     if (transaction == null)
/*      */     {
/* 1017 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1022 */       con = transaction.getConnection();
/*      */ 
/* 1025 */       String sSQL = "DELETE FROM RelatedAsset WHERE ParentId=? AND ChildId=? AND RelationshipTypeId=?";
/*      */ 
/* 1028 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 1030 */       psql.setLong(1, a_lParentAssetId);
/* 1031 */       psql.setLong(2, a_lChildAssetId);
/* 1032 */       psql.setLong(3, a_lRelationshipTypeId);
/* 1033 */       psql.executeUpdate();
/*      */ 
/* 1035 */       vAffectedAssetIds.add(Long.valueOf(a_lChildAssetId));
/*      */       Iterator i$;
/* 1038 */       if (a_lRelationshipTypeId == 1L)
/*      */       {
/* 1040 */         psql.setLong(1, a_lChildAssetId);
/* 1041 */         psql.setLong(2, a_lParentAssetId);
/* 1042 */         psql.setLong(3, a_lRelationshipTypeId);
/* 1043 */         psql.executeUpdate();
/*      */ 
/* 1047 */         if (AssetBankSettings.getAssetEntitiesEnabled())
/*      */         {
/* 1049 */           Vector ids = getRelatedAssetIds(a_dbTransaction, a_lParentAssetId, 1L);
/* 1050 */           for (i$ = ids.iterator(); i$.hasNext(); ) { long lPeerId = ((Long)i$.next()).longValue();
/*      */ 
/* 1052 */             if (lPeerId != a_lChildAssetId)
/*      */             {
/* 1054 */               psql.setLong(1, lPeerId);
/* 1055 */               psql.setLong(2, a_lChildAssetId);
/* 1056 */               psql.setLong(3, a_lRelationshipTypeId);
/* 1057 */               psql.executeUpdate();
/*      */ 
/* 1059 */               psql.setLong(1, a_lChildAssetId);
/* 1060 */               psql.setLong(2, lPeerId);
/* 1061 */               psql.setLong(3, a_lRelationshipTypeId);
/* 1062 */               psql.executeUpdate();
/*      */ 
/* 1064 */               vAffectedAssetIds.add(Long.valueOf(lPeerId));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1069 */       else if ((AssetBankSettings.getCategoryExtensionAssetsEnabled()) && (!a_bIgnoreCategoryCheck))
/*      */       {
/* 1072 */         Asset asset = this.m_assetManager.getAsset(a_dbTransaction, a_lParentAssetId);
/* 1073 */         if ((asset.getExtendsCategory() != null) && (asset.getExtendsCategory().getId() > 0L))
/*      */         {
/* 1077 */           this.m_categoryManager.deleteItemFromCategory(a_dbTransaction, a_lChildAssetId, asset.getExtendsCategory().getId(), asset.getExtendsCategory().getCategoryTypeId(), true);
/*      */         }
/*      */       }
/*      */ 
/* 1081 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1085 */       this.m_logger.error("SQL Exception whilst deleting asset relationships : " + e);
/* 1086 */       throw new Bn2Exception("SQL Exception whilst deleting asset relationships : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1091 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1095 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1099 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/* 1103 */     return vAffectedAssetIds;
/*      */   }
/*      */ 
/*      */   public void saveRelatedAssetIds(DBTransaction a_dbTransaction, Asset a_originalAsset, String a_sRelatedIds, long a_lRelationshipTypeId, boolean a_bNewAsset)
/*      */     throws Bn2Exception
/*      */   {
/* 1115 */     if (a_originalAsset == null)
/*      */     {
/* 1117 */       throw new Bn2Exception(getClass().getSimpleName() + ": Unable to save related assets, null asset provided");
/*      */     }
/*      */ 
/* 1120 */     saveRelatedAssetIds(a_dbTransaction, a_originalAsset, a_originalAsset.getId(), a_sRelatedIds, a_lRelationshipTypeId, false, a_bNewAsset);
/*      */   }
/*      */ 
/*      */   public void saveRelatedAssetIds(DBTransaction a_dbTransaction, Asset a_originalAsset, long a_lAssetId, String a_sRelatedIds, long a_lRelationshipTypeId, boolean a_bIgnoreCategoryCheck, boolean a_bNewAsset)
/*      */     throws Bn2Exception
/*      */   {
/* 1143 */     PreparedStatement psql = null;
/* 1144 */     PreparedStatement psqlDelete = null;
/* 1145 */     DBTransaction transaction = a_dbTransaction;
/* 1146 */     Vector existingParentIds = null;
/* 1147 */     int iSeq = 0;
/*      */ 
/* 1149 */     if (transaction == null)
/*      */     {
/* 1151 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/* 1154 */     String sSqlDelete = null;
/* 1155 */     String sSqlInsert = null;
/*      */     try
/*      */     {
/* 1161 */       String sOldIds = null;
/*      */ 
/* 1163 */       if (a_originalAsset != null)
/*      */       {
/* 1165 */         sOldIds = a_originalAsset.getRelatedAssetIdsAsString(a_lRelationshipTypeId);
/*      */       }
/*      */ 
/* 1168 */       if ((a_sRelatedIds != null) && ((a_bNewAsset) || (sOldIds == null) || (!StringUtil.delimitedListOfStringsAreEqual(sOldIds, a_sRelatedIds, ","))))
/*      */       {
/* 1173 */         Connection con = transaction.getConnection();
/*      */ 
/* 1175 */         if (a_lRelationshipTypeId == 3L)
/*      */         {
/* 1177 */           existingParentIds = StringUtil.convertToVectorOfLongs(sOldIds, ",");
/*      */         }
/*      */ 
/* 1180 */         Set<Long> ids = StringUtil.convertToSetOfLongs(a_sRelatedIds.replaceAll(" ", ""), ",");
/* 1181 */         Vector vecRelatedAssetIds = StringUtil.convertToVectorOfLongs(a_sRelatedIds, ",");
/* 1182 */         Vector vecRelatedAssets = this.m_assetManager.getAssets(transaction, vecRelatedAssetIds, false, false);
/* 1183 */         Vector vecAssetsToCheck = null;
/* 1184 */         long lRelationshipId = a_lRelationshipTypeId;
/*      */ 
/* 1186 */         if (a_lRelationshipTypeId == 2L)
/*      */         {
/* 1189 */           if (AssetBankSettings.getCategoryExtensionAssetsEnabled())
/*      */           {
/*      */             Iterator i$;
/* 1191 */             if ((a_originalAsset != null) && (a_originalAsset.getExtendsCategory() != null) && (a_originalAsset.getExtendsCategory().getId() > 0L))
/*      */             {
/* 1195 */               ArrayList missingIds = new ArrayList();
/* 1196 */               if (a_originalAsset.getChildAssetIds() != null)
/*      */               {
/* 1198 */                 missingIds.addAll(a_originalAsset.getChildAssetIds());
/* 1199 */                 missingIds.removeAll(vecRelatedAssetIds);
/*      */ 
/* 1201 */                 for (i$ = missingIds.iterator(); i$.hasNext(); ) { long lId = ((Long)i$.next()).longValue();
/*      */ 
/* 1203 */                   Asset asset = getAsset(vecRelatedAssets, lId);
/*      */ 
/* 1206 */                   this.m_categoryManager.deleteItemFromCategory(transaction, asset.getId(), a_originalAsset.getExtendsCategory().getId(), a_originalAsset.getExtendsCategory().getCategoryTypeId());
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 1213 */             vecAssetsToCheck = new Vector();
/* 1214 */             vecAssetsToCheck.add(a_originalAsset);
/*      */           }
/*      */ 
/* 1217 */           sSqlDelete = "DELETE FROM RelatedAsset WHERE ParentId=? AND RelationshipTypeId=?";
/* 1218 */           sSqlInsert = "INSERT INTO RelatedAsset (ParentId,ChildId,RelationshipTypeId,SequenceNumber) VALUES (?,?,?,?)";
/*      */         }
/* 1220 */         else if (a_lRelationshipTypeId == 3L)
/*      */         {
/* 1222 */           if (AssetBankSettings.getCategoryExtensionAssetsEnabled())
/*      */           {
/*      */             Iterator i$;
/* 1225 */             if ((a_originalAsset != null) && (a_originalAsset.getParentAssetIdsAsString() != null))
/*      */             {
/* 1228 */               ArrayList missingIds = new ArrayList();
/* 1229 */               missingIds.addAll(StringUtil.convertToVectorOfLongs(a_originalAsset.getParentAssetIdsAsString(), ","));
/* 1230 */               missingIds.removeAll(vecRelatedAssetIds);
/*      */ 
/* 1232 */               for (i$ = missingIds.iterator(); i$.hasNext(); ) { long lId = ((Long)i$.next()).longValue();
/*      */ 
/* 1234 */                 Asset asset = getAsset(vecRelatedAssets, lId);
/*      */ 
/* 1236 */                 if ((asset.getExtendsCategory() != null) && (asset.getExtendsCategory().getId() > 0L))
/*      */                 {
/* 1239 */                   this.m_categoryManager.deleteItemFromCategory(transaction, a_originalAsset.getId(), asset.getExtendsCategory().getId(), asset.getExtendsCategory().getCategoryTypeId());
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 1247 */             vecAssetsToCheck = vecRelatedAssets;
/*      */           }
/*      */ 
/* 1250 */           sSqlDelete = "DELETE FROM RelatedAsset WHERE ChildId=? AND RelationshipTypeId=?";
/*      */ 
/* 1253 */           if ((ids != null) && (ids.size() > 0))
/*      */           {
/* 1255 */             sSqlDelete = sSqlDelete + " AND ParentId NOT IN (" + StringUtil.convertNumbersToString(ids, ",") + ")";
/*      */           }
/*      */ 
/* 1258 */           sSqlInsert = "INSERT INTO RelatedAsset (ChildId,ParentId,RelationshipTypeId,SequenceNumber) VALUES (?,?,?,?)";
/* 1259 */           lRelationshipId = 2L;
/*      */         }
/* 1261 */         else if (a_lRelationshipTypeId == 1L)
/*      */         {
/* 1263 */           sSqlDelete = "DELETE FROM RelatedAsset WHERE (ParentId=? OR ChildId=?) AND RelationshipTypeId=?";
/* 1264 */           sSqlInsert = "INSERT INTO RelatedAsset (ParentId,ChildId,RelationshipTypeId,SequenceNumber) VALUES (?,?,?,?)";
/*      */         }
/*      */ 
/* 1268 */         int iCol = 1;
/* 1269 */         psqlDelete = con.prepareStatement(sSqlDelete);
/* 1270 */         psqlDelete.setLong(iCol++, a_lAssetId);
/* 1271 */         if (a_lRelationshipTypeId == 1L)
/*      */         {
/* 1273 */           psqlDelete.setLong(iCol++, a_lAssetId);
/*      */         }
/* 1275 */         psqlDelete.setLong(iCol++, lRelationshipId);
/* 1276 */         psqlDelete.executeUpdate();
/*      */ 
/* 1278 */         if ((ids != null) && (ids.size() > 0))
/*      */         {
/* 1280 */           psql = con.prepareStatement(sSqlInsert);
/*      */ 
/* 1283 */           for (Long lId : ids)
/*      */           {
/* 1286 */             if (a_lRelationshipTypeId == 1L)
/*      */             {
/* 1288 */               psqlDelete.setLong(1, lId.longValue());
/* 1289 */               psqlDelete.setLong(2, lId.longValue());
/* 1290 */               psqlDelete.setLong(3, lRelationshipId);
/* 1291 */               psqlDelete.executeUpdate();
/*      */             }
/* 1294 */             else if (a_lRelationshipTypeId == 3L)
/*      */             {
/* 1296 */               String sSQL = "SELECT MAX(SequenceNumber) maxSeq FROM RelatedAsset WHERE ParentId=? AND RelationshipTypeId=?";
/* 1297 */               PreparedStatement psqlParent = con.prepareStatement(sSQL);
/* 1298 */               psqlParent.setLong(1, lId.longValue());
/* 1299 */               psqlParent.setLong(2, 2L);
/* 1300 */               ResultSet rs = psqlParent.executeQuery();
/*      */ 
/* 1302 */               if (rs.next())
/*      */               {
/* 1304 */                 iSeq = rs.getInt("maxSeq") + 1;
/*      */               }
/* 1306 */               psqlParent.close();
/*      */ 
/* 1309 */               existingParentIds = getRelatedAssetIds(transaction, a_lAssetId, a_lRelationshipTypeId);
/*      */             }
/*      */             else
/*      */             {
/* 1313 */               iSeq++;
/*      */             }
/*      */ 
/* 1316 */             if ((a_lRelationshipTypeId != 3L) || (!existingParentIds.contains(lId)))
/*      */             {
/* 1319 */               if (a_lAssetId != lId.longValue())
/*      */               {
/* 1321 */                 psql.setLong(1, a_lAssetId);
/* 1322 */                 psql.setLong(2, lId.longValue());
/* 1323 */                 psql.setLong(3, lRelationshipId);
/* 1324 */                 psql.setInt(4, iSeq);
/* 1325 */                 psql.executeUpdate();
/*      */ 
/* 1327 */                 if (a_lRelationshipTypeId == 1L)
/*      */                 {
/* 1329 */                   psql.setLong(1, lId.longValue());
/* 1330 */                   psql.setLong(2, a_lAssetId);
/* 1331 */                   psql.setLong(3, lRelationshipId);
/* 1332 */                   psql.setInt(4, iSeq);
/* 1333 */                   psql.executeUpdate();
/*      */                 }
/*      */ 
/* 1337 */                 if ((AssetBankSettings.getCategoryExtensionAssetsEnabled()) && (!a_bIgnoreCategoryCheck) && ((a_lRelationshipTypeId == 2L) || (a_lRelationshipTypeId == 3L)))
/*      */                 {
/* 1342 */                   Asset tempAsset = null;
/* 1343 */                   long lAssetToAdd = -1L;
/* 1344 */                   if (a_lRelationshipTypeId == 3L)
/*      */                   {
/* 1346 */                     tempAsset = getAsset(vecAssetsToCheck, lId.longValue());
/* 1347 */                     lAssetToAdd = a_lAssetId;
/*      */                   }
/*      */                   else
/*      */                   {
/* 1351 */                     tempAsset = a_originalAsset;
/* 1352 */                     lAssetToAdd = lId.longValue();
/*      */                   }
/*      */ 
/* 1357 */                   if ((tempAsset != null) && (tempAsset.getExtendsCategory() != null) && (tempAsset.getExtendsCategory().getId() > 0L))
/*      */                   {
/* 1361 */                     this.m_categoryManager.addItemToCategory(transaction, lAssetToAdd, tempAsset.getExtendsCategory().getId(), tempAsset.getExtendsCategory().getCategoryTypeId(), true);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1369 */           if (a_lRelationshipTypeId == 1L)
/*      */           {
/* 1371 */             Long[] idArray = (Long[])ids.toArray(new Long[ids.size()]);
/*      */ 
/* 1373 */             for (int i = 0; i < idArray.length; i++)
/*      */             {
/* 1375 */               for (int j = 0; j < idArray.length; j++)
/*      */               {
/* 1377 */                 if ((idArray[i] == idArray[j]) || (idArray[i].longValue() == a_lAssetId) || (idArray[j].longValue() == a_lAssetId))
/*      */                   continue;
/* 1379 */                 psql.setLong(1, idArray[i].longValue());
/* 1380 */                 psql.setLong(2, idArray[j].longValue());
/* 1381 */                 psql.setLong(3, lRelationshipId);
/* 1382 */                 psql.setInt(4, iSeq);
/* 1383 */                 psql.executeUpdate();
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1389 */           psqlDelete.close();
/* 1390 */           psql.close();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1396 */       this.m_logger.error("SQL Exception whilst saving asset relationships : " + e);
/* 1397 */       throw new Bn2Exception("SQL Exception whilst saving asset relationships : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1402 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1406 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1410 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Asset getAsset(Vector<Asset> a_vecAssets, long a_lId)
/*      */   {
/* 1426 */     if (a_vecAssets != null)
/*      */     {
/* 1428 */       for (Asset asset : a_vecAssets)
/*      */       {
/* 1430 */         if (asset.getId() == a_lId)
/*      */         {
/* 1432 */           return asset;
/*      */         }
/*      */       }
/*      */     }
/* 1436 */     return null;
/*      */   }
/*      */ 
/*      */   public Vector<Long> addAssetRelationships(DBTransaction a_dbTransaction, long a_lAssetId, String a_sRelatedIds, long a_lRelationshipTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1454 */     Vector vAffectedAssetIds = new Vector();
/* 1455 */     Connection con = null;
/* 1456 */     PreparedStatement psql = null;
/* 1457 */     PreparedStatement psqlDelete = null;
/* 1458 */     DBTransaction transaction = a_dbTransaction;
/* 1459 */     int iSeq = 0;
/*      */ 
/* 1461 */     if (transaction == null)
/*      */     {
/* 1463 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1468 */       String sSqlDelete = null;
/* 1469 */       String sSqlInsert = null;
/*      */ 
/* 1472 */       if (StringUtils.isNotEmpty(a_sRelatedIds))
/*      */       {
/* 1474 */         con = transaction.getConnection();
/*      */ 
/* 1476 */         long[] ids = StringUtil.getIdsArray(a_sRelatedIds.replaceAll(" ", ""));
/*      */ 
/* 1479 */         if ((ids.length > 0) && (a_lRelationshipTypeId == 2L))
/*      */         {
/* 1481 */           String sSQL = "SELECT MAX(SequenceNumber) maxSeq FROM RelatedAsset WHERE ParentId=? AND RelationshipTypeId=?";
/* 1482 */           psql = con.prepareStatement(sSQL);
/* 1483 */           psql.setLong(1, a_lAssetId);
/* 1484 */           psql.setLong(2, a_lRelationshipTypeId);
/* 1485 */           ResultSet rs = psql.executeQuery();
/*      */ 
/* 1487 */           if (rs.next())
/*      */           {
/* 1489 */             iSeq = rs.getInt("maxSeq");
/*      */           }
/*      */         }
/* 1492 */         else if ((ids.length == 1) && (a_lRelationshipTypeId == 3L))
/*      */         {
/* 1494 */           String sSQL = "SELECT MAX(SequenceNumber) maxSeq FROM RelatedAsset WHERE ParentId=? AND RelationshipTypeId=?";
/* 1495 */           psql = con.prepareStatement(sSQL);
/* 1496 */           psql.setLong(1, ids[0]);
/* 1497 */           psql.setLong(2, 2L);
/* 1498 */           ResultSet rs = psql.executeQuery();
/*      */ 
/* 1500 */           if (rs.next())
/*      */           {
/* 1502 */             iSeq = rs.getInt("maxSeq");
/*      */           }
/*      */         }
/*      */ 
/* 1506 */         long lRelationshipId = a_lRelationshipTypeId;
/*      */ 
/* 1508 */         if (a_lRelationshipTypeId == 2L)
/*      */         {
/* 1510 */           sSqlDelete = "DELETE FROM RelatedAsset WHERE ParentId=? AND ChildId=? AND RelationshipTypeId=? ";
/* 1511 */           sSqlInsert = "INSERT INTO RelatedAsset (ParentId,ChildId,RelationshipTypeId,SequenceNumber) VALUES (?,?,?,?)";
/*      */         }
/* 1513 */         else if (a_lRelationshipTypeId == 3L)
/*      */         {
/* 1515 */           sSqlDelete = "DELETE FROM RelatedAsset WHERE ChildId=? AND ParentId=? AND RelationshipTypeId=? ";
/* 1516 */           sSqlInsert = "INSERT INTO RelatedAsset (ChildId,ParentId,RelationshipTypeId,SequenceNumber) VALUES (?,?,?,?)";
/* 1517 */           lRelationshipId = 2L;
/*      */         }
/* 1519 */         else if (a_lRelationshipTypeId == 1L)
/*      */         {
/* 1521 */           sSqlDelete = "DELETE FROM RelatedAsset WHERE ParentId=? AND ChildId=? AND RelationshipTypeId=? ";
/* 1522 */           sSqlInsert = "INSERT INTO RelatedAsset (ParentId,ChildId,RelationshipTypeId,SequenceNumber) VALUES (?,?,?,?)";
/*      */         }
/*      */ 
/* 1525 */         psqlDelete = con.prepareStatement(sSqlDelete);
/* 1526 */         psql = con.prepareStatement(sSqlInsert);
/*      */ 
/* 1528 */         for (int i = 0; i < ids.length; i++)
/*      */         {
/* 1530 */           psqlDelete.setLong(1, a_lAssetId);
/* 1531 */           psqlDelete.setLong(2, ids[i]);
/* 1532 */           psqlDelete.setLong(3, lRelationshipId);
/* 1533 */           psqlDelete.executeUpdate();
/*      */ 
/* 1535 */           if (a_lRelationshipTypeId == 1L)
/*      */           {
/* 1537 */             psqlDelete.setLong(1, ids[i]);
/* 1538 */             psqlDelete.setLong(2, a_lAssetId);
/* 1539 */             psqlDelete.setLong(3, lRelationshipId);
/* 1540 */             psqlDelete.executeUpdate();
/*      */           }
/*      */           else
/*      */           {
/* 1544 */             iSeq++;
/*      */ 
/* 1547 */             if ((a_lRelationshipTypeId == 2L) && (AssetBankSettings.getIncludeParentMetadataForSearch()))
/*      */             {
/* 1550 */               vAffectedAssetIds.add(Long.valueOf(ids[i]));
/*      */             }
/*      */           }
/*      */ 
/* 1554 */           psql.setLong(1, a_lAssetId);
/* 1555 */           psql.setLong(2, ids[i]);
/* 1556 */           psql.setLong(3, lRelationshipId);
/* 1557 */           psql.setInt(4, iSeq);
/* 1558 */           psql.executeUpdate();
/*      */ 
/* 1560 */           if (a_lRelationshipTypeId == 1L)
/*      */           {
/* 1562 */             psql.setLong(1, ids[i]);
/* 1563 */             psql.setLong(2, a_lAssetId);
/* 1564 */             psql.setLong(3, lRelationshipId);
/* 1565 */             psql.setInt(4, iSeq);
/* 1566 */             psql.executeUpdate();
/*      */           }
/*      */ 
/* 1569 */           vAffectedAssetIds.add(Long.valueOf(ids[i]));
/*      */ 
/* 1572 */           if (a_lRelationshipTypeId != 1L)
/*      */             continue;
/* 1574 */           Vector vIds = getRelatedAssetIds(a_dbTransaction, a_lAssetId, 1L);
/*      */ 
/* 1576 */           if (vIds == null)
/*      */             continue;
/* 1578 */           for (int j = 0; j < vIds.size(); j++)
/*      */           {
/* 1580 */             long lOtherAssetId = ((Long)vIds.get(j)).longValue();
/*      */ 
/* 1582 */             if (lOtherAssetId == ids[i])
/*      */               continue;
/* 1584 */             psqlDelete.setLong(1, ids[i]);
/* 1585 */             psqlDelete.setLong(2, lOtherAssetId);
/* 1586 */             psqlDelete.setLong(3, lRelationshipId);
/* 1587 */             psqlDelete.executeUpdate();
/*      */ 
/* 1589 */             psqlDelete.setLong(1, lOtherAssetId);
/* 1590 */             psqlDelete.setLong(2, ids[i]);
/* 1591 */             psqlDelete.setLong(3, lRelationshipId);
/* 1592 */             psqlDelete.executeUpdate();
/*      */ 
/* 1594 */             psql.setLong(1, ids[i]);
/* 1595 */             psql.setLong(2, lOtherAssetId);
/* 1596 */             psql.setLong(3, lRelationshipId);
/* 1597 */             psql.setInt(4, iSeq);
/* 1598 */             psql.executeUpdate();
/*      */ 
/* 1600 */             psql.setLong(1, lOtherAssetId);
/* 1601 */             psql.setLong(2, ids[i]);
/* 1602 */             psql.setLong(3, lRelationshipId);
/* 1603 */             psql.setInt(4, iSeq);
/* 1604 */             psql.executeUpdate();
/*      */ 
/* 1606 */             vAffectedAssetIds.add(Long.valueOf(lOtherAssetId));
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1613 */         psqlDelete.close();
/* 1614 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1619 */       this.m_logger.error("SQL Exception whilst adding asset relationships : " + e);
/* 1620 */       throw new Bn2Exception("SQL Exception whilst adding asset relationships : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1625 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1629 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1633 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1638 */     return vAffectedAssetIds;
/*      */   }
/*      */ 
/*      */   public boolean demoteChildInRelationshipSequence(DBTransaction a_dbTransaction, long a_lAssetId, long a_lChildAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1648 */     Connection con = null;
/* 1649 */     PreparedStatement psql = null;
/* 1650 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1652 */     if (transaction == null)
/*      */     {
/* 1654 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
                 String sSqlUpdate;
/*      */ 
/*      */     try
/*      */     {
/* 1659 */       con = transaction.getConnection();
/*      */ 
/* 1661 */       String sSql = "SELECT ParentId, ChildId, SequenceNumber FROM RelatedAsset WHERE ParentId=? AND RelationshipTypeId=? ORDER BY SequenceNumber ";
/*      */ 
/* 1665 */       psql = con.prepareStatement(sSql);
/* 1666 */       psql.setLong(1, a_lAssetId);
/* 1667 */       psql.setLong(2, 2L);
/*      */ 
/* 1669 */       ResultSet rs = psql.executeQuery();
/* 1670 */       int iSeq = 1;
/* 1671 */       boolean bFoundChildToMove = false;
/* 1672 */       boolean bReindexParent = false;
/*      */ 
/* 1674 */       while (rs.next())
/*      */       {
/* 1676 */         sSqlUpdate = "UPDATE RelatedAsset SET SequenceNumber=? WHERE ChildId=? AND ParentId=? AND RelationshipTypeId=?";
/* 1677 */         PreparedStatement psqlUpdate = con.prepareStatement(sSqlUpdate);
/*      */ 
/* 1679 */         if (rs.getLong("ChildId") == a_lChildAssetId)
/*      */         {
/* 1682 */           if ((iSeq == 1) && (AssetBankSettings.getUseFirstChildAssetAsSurrogate()))
/*      */           {
/* 1684 */             bReindexParent = true;
/*      */           }
/*      */ 
/* 1687 */           psqlUpdate.setInt(1, iSeq++ + (rs.isLast() ? 0 : 1));
/* 1688 */           bFoundChildToMove = true;
/*      */         }
/* 1692 */         else if (bFoundChildToMove)
/*      */         {
/* 1694 */           psqlUpdate.setInt(1, iSeq++ - 1);
/* 1695 */           bFoundChildToMove = false;
/*      */         }
/*      */         else
/*      */         {
/* 1699 */           psqlUpdate.setInt(1, iSeq++);
/*      */         }
/*      */ 
/* 1703 */         psqlUpdate.setLong(2, rs.getLong("ChildId"));
/* 1704 */         psqlUpdate.setLong(3, rs.getLong("ParentId"));
/* 1705 */         psqlUpdate.setLong(4, 2L);
/*      */ 
/* 1707 */         psqlUpdate.executeUpdate();
/*      */ 
/* 1709 */         psqlUpdate.close();
/*      */       }
/*      */ 
/* 1712 */       psql.close();
/*      */ 
/* 1714 */       //sSqlUpdate = bReindexParent;
/*      */       //return sSqlUpdate;
                return bReindexParent;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1718 */       this.m_logger.error("SQL Exception whilst moving asset relationships : " + e);
/* 1719 */       throw new Bn2Exception("SQL Exception whilst moving asset relationships : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1724 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1728 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1732 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           throw new Bn2Exception(sqle.getMessage());
                    }
/*      */       }
/* 1733 */     } //throw localObject;
/*      */   }
/*      */ 
/*      */   public HashMap<Long, ArrayList<Asset>> getDefaultRelatedAssetsForAsset(DBTransaction a_transaction, ABUser a_user, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 1750 */     HashMap hmRelatedAssetMap = null;
/*      */     HashMap hmRels;
/*      */     SearchCriteria criteria;
/*      */     Iterator i$;
        Long longRelType;
/* 1753 */     if ((a_asset.getEntity() != null) && (a_asset.getEntity().getHasDefaultRelationship()))
/*      */     {
/* 1756 */       hmRels = new HashMap();
/* 1757 */       hmRels.put(new Long(2L), a_asset.getEntity().getChildRelationships());
/* 1758 */       hmRels.put(new Long(1L), a_asset.getEntity().getPeerRelationships());
/*      */ 
/* 1761 */       criteria = new SearchCriteria();
/* 1762 */       if (!a_user.getIsAdmin())
/*      */       {
/* 1764 */         for (int i = 0; i < a_user.getGroups().size(); i++)
/*      */         {
/* 1766 */           Group group = (Group)a_user.getGroups().elementAt(i);
/* 1767 */           criteria.addGroupRestriction(group.getId());
/*      */         }
/*      */       }
/*      */ 
/* 1771 */       for (i$ = hmRels.keySet().iterator(); i$.hasNext(); ) { longRelType = (Long)i$.next();
/*      */            ArrayList<AssetEntityRelationship> arListAER = (ArrayList<AssetEntityRelationship>) hmRels.get(longRelType);
/* 1773 */         for (AssetEntityRelationship aer : arListAER)
/*      */         {
/* 1775 */           if (aer.getDefaultRelationshipCategoryId() > 0L)
/*      */           {
/* 1778 */             criteria.addAssetEntityIdToInclude(aer.getRelatesToAssetEntityId());
/* 1779 */             criteria.setCategoryIds(String.valueOf(aer.getDefaultRelationshipCategoryId()));
/* 1780 */             SearchResults results = this.m_searchManager.search(criteria);
/* 1781 */             Vector<LightweightAsset> vecResults = (Vector)results.getSearchResults().clone();
/*      */ 
/* 1784 */             if ((results != null) && (results.getSearchResults() != null) && (results.getSearchResults().size() > 0) && ((results.getSearchResults().size() != 1) || (((LightweightAsset)results.getSearchResults().firstElement()).getId() != a_asset.getId())))
/*      */             {
/* 1792 */               for (LightweightAsset tempAsset : vecResults)
/*      */               {
/* 1794 */                 if ((tempAsset.getId() == a_asset.getId()) || ((longRelType.longValue() == 2L) && (a_asset.getHasChild(tempAsset.getId()))) || ((longRelType.longValue() == 1L) && (a_asset.getHasPeer(tempAsset.getId()))))
/*      */                 {
/* 1800 */                   results.getSearchResults().remove(tempAsset);
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/* 1805 */               if (hmRelatedAssetMap == null)
/*      */               {
/* 1807 */                 hmRelatedAssetMap = new HashMap();
/*      */               }
/* 1809 */               if (hmRelatedAssetMap.get(longRelType) == null)
/*      */               {
/* 1811 */                 hmRelatedAssetMap.put(longRelType, new ArrayList());
/*      */               }
/* 1813 */               ((ArrayList)hmRelatedAssetMap.get(longRelType)).addAll(results.getSearchResults());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     //Long longRelType;
/* 1820 */     return hmRelatedAssetMap;
/*      */   }
/*      */ 
/*      */   public void processQueueItem(QueuedItem a_queuedItem)
/*      */     throws Bn2Exception
/*      */   {
/* 1833 */     EmptyRelatedAssetBatch batch = (EmptyRelatedAssetBatch)a_queuedItem;
/* 1834 */     startBatchProcess(batch.getJobId());
/* 1835 */     createEmptyRelatedAssets(batch);
/*      */   }
/*      */ 
/*      */   public void createEmptyRelatedAssets(EmptyRelatedAssetBatch a_batch)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/* 1852 */       addMessage(a_batch.getJobId(), "Starting creation of empty assets");
/*      */ 
/* 1855 */       if ((a_batch.getRelatedAssets() != null) && (a_batch.getRelatedAssets().size() > 0))
/*      */       {
/* 1858 */          WorkflowUpdate update = new WorkflowUpdate(a_batch.getUserProfile().getUser().getId(), -1L);
/* 1859 */         update.setUpdateType(1);
/* 1860 */         update.setSetSubmitted(true);
/*      */ 
/* 1862 */         for (Iterator i$ = a_batch.getRelatedAssets().keySet().iterator(); i$.hasNext(); ) { long lEntityId = ((Long)i$.next()).longValue();
/*      */ 
/* 1864 */           AssetEntity entity = this.m_assetEntityManager.getEntity(null, lEntityId);
/* 1865 */           NameValueBean bean = (NameValueBean)a_batch.getRelatedAssets().get(new Long(lEntityId));
/* 1866 */           int iQuantity = Integer.parseInt(bean.getValue());
/*      */ 
/* 1869 */           Asset asset = new Asset();
/* 1870 */           asset.setEntity(entity);
/* 1871 */           asset.setPermissionCategories(a_batch.getAsset().getPermissionCategories());
/*      */ 
/* 1873 */           Vector vecAttributes = this.m_attributeManager.getNameAttributes(null);
/* 1874 */           if ((StringUtil.stringIsPopulated(bean.getName())) && (vecAttributes != null) && (vecAttributes.size() > 0))
/*      */           {
/* 1877 */             AttributeValue value = new AttributeValue();
/* 1878 */             value.setAttribute((Attribute)vecAttributes.firstElement());
/* 1879 */             value.setValue(bean.getName());
/* 1880 */             Vector vecAttributeValues = new Vector();
/* 1881 */             vecAttributeValues.add(value);
/* 1882 */             asset.setAttributeValues(vecAttributeValues);
/*      */           }
/*      */ 
/* 1885 */           long lSourcePeerId = -1L;
/* 1886 */           switch (a_batch.getRelType())
/*      */           {
/*      */           case 2:
/* 1889 */             asset.setParentAssetIdsAsString(String.valueOf(a_batch.getAsset().getId()));
/* 1890 */             addMessage(a_batch.getJobId(), "Child relationships with type: " + entity.getName());
/* 1891 */             break;
/*      */           case 1:
/* 1893 */             lSourcePeerId = a_batch.getAsset().getId();
/* 1894 */             addMessage(a_batch.getJobId(), "Peer relationships with type: " + entity.getName());
/*      */           }
/*      */ 
/* 1898 */           String sPeerIds = "";
/*      */          
/* 1900 */           for (int i = 0; i < iQuantity; i++)
/*      */           {
/* 1902 */             long lAssetId = this.m_importManager.addEmptyAsset(a_batch.getUserProfile().getUser().getId(), asset, null, a_batch.getUserProfile().getSessionId(), a_batch.getUserProfile().getCurrentLanguage(), false, false, update);
/*      */ 
/* 1910 */             addMessage(a_batch.getJobId(), "Added empty asset with id: " + lAssetId);
/*      */ 
/* 1914 */             if (lSourcePeerId <= 0L)
/*      */               continue;
/* 1916 */             sPeerIds = sPeerIds + "," + lAssetId;
/*      */           }
/*      */ 
/* 1920 */           addMessage(a_batch.getJobId(), "Added all empty assets of this type");
/*      */ 
/* 1923 */           if (lSourcePeerId > 0L)
/*      */           {
/* 1925 */             addMessage(a_batch.getJobId(), "About to setup peer relationships");
/*      */ 
/* 1928 */             Asset tempAsset = this.m_assetManager.getAsset(null, lSourcePeerId, null, true, true);
/* 1929 */             if (StringUtil.stringIsPopulated(tempAsset.getPeerAssetIdsAsString()))
/*      */             {
/* 1931 */               sPeerIds = sPeerIds + "," + tempAsset.getPeerAssetIdsAsString();
/*      */             }
/*      */ 
/* 1935 */             saveRelatedAssetIds(null, tempAsset, sPeerIds, 1L, false);
/*      */ 
/* 1937 */             addMessage(a_batch.getJobId(), "Saved peer relationships");
/*      */ 
/* 1940 */             tempAsset.setPeerAssetIdsAsString(sPeerIds);
/* 1941 */             this.m_searchManager.indexDocument(tempAsset, true);
/*      */ 
/* 1944 */             long[] aIds = StringUtil.convertToArrayOfLongs(sPeerIds, ",");
/* 1945 */             for (long lId : aIds)
/*      */             {
/* 1947 */               tempAsset = this.m_assetManager.getAsset(null, lId, null, true, true);
/* 1948 */               this.m_searchManager.indexDocument(tempAsset, true);
/* 1949 */               addMessage(a_batch.getJobId(), "Indexed peer asset with id: " + lId);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */      
/*      */       Iterator i$;
/* 1957 */       addMessage(a_batch.getJobId(), "Finished adding empty assets");
/* 1958 */       endBatchProcess(a_batch.getJobId());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void restrictChildAssets(DBTransaction a_dbTransaction, long a_lAssetId, boolean a_bUnrestrict)
/*      */     throws Bn2Exception
/*      */   {
/* 1969 */     Connection con = null;
/* 1970 */     PreparedStatement psql = null;
/* 1971 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1973 */     if (transaction == null)
/*      */     {
/* 1975 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1980 */       con = transaction.getConnection();
/*      */ 
/* 1982 */       String sSql = "UPDATE Asset SET IsPreviewRestricted=? WHERE Id IN (SELECT ChildId FROM RelatedAsset WHERE ParentId=? AND RelationshipTypeId=?)";
/*      */ 
/* 1984 */       psql = con.prepareStatement(sSql);
/* 1985 */       psql.setBoolean(1, a_bUnrestrict);
/* 1986 */       psql.setLong(2, a_lAssetId);
/* 1987 */       psql.setLong(3, 2L);
/*      */ 
/* 1989 */       psql.executeUpdate();
/*      */ 
/* 1991 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1995 */       this.m_logger.error("SQL Exception whilst restricting child assets : " + e);
/* 1996 */       throw new Bn2Exception("SQL Exception whilst restricting child assets : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2001 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2005 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2009 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void assetWasSaved(AssetSaveContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   public void initAssetSave(AssetSaveContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/* 2032 */     a_context.needOriginalAsset();
/*      */   }
/*      */ 
/*      */   public void save(AssetSaveContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/* 2048 */     saveRelationships(a_context.getOriginalAsset(), a_context.getAsset().getId(), a_context.isNew(), a_context.getAsset().getChildAssetIdsAsString(), a_context.getAsset().getPeerAssetIdsAsString(), a_context.getAsset().getParentAssetIdsAsString());
/*      */   }
/*      */ 
/*      */   public void saveRelationships(Asset a_asset, long a_lAssetId, boolean a_bNew, String a_sNewChildIds, String a_sNewPeerIds, String a_sNewParentIds)
/*      */     throws Bn2Exception
/*      */   {
/* 2076 */     DBTransaction transaction = null;
/* 2077 */     boolean bError = false;
/*      */     try
/*      */     {
/* 2081 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */ 
/* 2083 */       String sOldChildIds = "";
/* 2084 */       if ((a_asset != null) && (a_asset.getChildAssetIdsAsString() != null))
/*      */       {
/* 2086 */         sOldChildIds = a_asset.getChildAssetIdsAsString();
/*      */       }
/*      */ 
/* 2089 */       String sOldPeerIds = "";
/* 2090 */       if ((a_asset != null) && (a_asset.getPeerAssetIdsAsString() != null))
/*      */       {
/* 2092 */         sOldPeerIds = a_asset.getPeerAssetIdsAsString();
/*      */       }
/*      */ 
/* 2095 */       String sOldParentIds = "";
/* 2096 */       if ((a_asset != null) && (a_asset.getParentAssetIdsAsString() != null))
/*      */       {
/* 2098 */         sOldParentIds = a_asset.getParentAssetIdsAsString();
/*      */       }
/*      */ 
/* 2101 */       Vector vChildren = StringUtil.convertToVectorOfLongs(sOldChildIds, ",");
/* 2102 */       Vector vParents = StringUtil.convertToVectorOfLongs(sOldParentIds, ",");
/* 2103 */       Vector vPeers = StringUtil.convertToVectorOfLongs(sOldPeerIds, ",");
/*      */ 
/* 2105 */       SaveRelationshipPredicate predicate = new SaveRelationshipPredicate();
/*      */ 
/* 2108 */       ArrayList alIdsToIndex = new ArrayList();
/* 2109 */       if (predicate.saveRelationships(a_sNewChildIds, sOldChildIds))
/*      */       {
/* 2111 */         saveRelatedAssetIds(transaction, a_asset, a_lAssetId, a_sNewChildIds, 2L, false, a_bNew);
/* 2112 */         alIdsToIndex.addAll(getIdsToIndex(a_sNewChildIds, vChildren));
/*      */       }
/*      */ 
/* 2115 */       if (predicate.saveRelationships(a_sNewPeerIds, sOldPeerIds))
/*      */       {
/* 2117 */         saveRelatedAssetIds(transaction, a_asset, a_lAssetId, a_sNewPeerIds, 1L, false, a_bNew);
/* 2118 */         alIdsToIndex.addAll(getIdsToIndex(a_sNewPeerIds, vPeers));
/*      */       }
/*      */ 
/* 2121 */       if (predicate.saveRelationships(a_sNewParentIds, sOldParentIds))
/*      */       {
/* 2123 */         saveRelatedAssetIds(transaction, a_asset, a_lAssetId, a_sNewParentIds, 3L, false, a_bNew);
/* 2124 */         alIdsToIndex.addAll(getIdsToIndex(a_sNewParentIds, vParents));
/*      */       }
/*      */       ArrayList alDone=new ArrayList();;
/*      */       Iterator i$;
/* 2128 */    
/* 2129 */       for (i$ = alIdsToIndex.iterator(); i$.hasNext(); ) { long lId = ((Long)i$.next()).longValue();
/*      */ 
/* 2131 */         if ((lId > 0L) && (!alDone.contains(Long.valueOf(lId))))
/*      */         {
/* 2133 */           Asset asset = this.m_assetManager.getAsset(transaction, lId, null, true, true);
/* 2134 */           this.m_searchManager.indexDocument(asset, true);
/* 2135 */           alDone.add(Long.valueOf(lId));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       
/* 2141 */       bError = true;
/* 2142 */       throw new Bn2Exception(getClass().getSimpleName() + ": Error saving relationships ", e);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2148 */         if ((bError) && (transaction != null))
/*      */         {
/* 2150 */           transaction.rollback();
/*      */         }
/* 2152 */         else if (transaction != null)
/*      */         {
/* 2154 */           transaction.commit();
/*      */         }
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 2159 */         throw new Bn2Exception(getClass().getSimpleName() + ": Error handling transaction ", e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Collection<Long> getIdsToIndex(String a_sIdList, Collection<Long> a_existingIds)
/*      */   {
/* 2175 */     Collection result = new ArrayList();
/* 2176 */     if (StringUtil.stringIsPopulated(a_sIdList))
/*      */     {
/* 2178 */       Vector vIds = StringUtil.convertToVectorOfLongs(a_sIdList.replaceAll(" ", ""), ",");
/* 2179 */       if (a_existingIds != null)
/*      */       {
/* 2181 */         result = CollectionUtils.disjunction(vIds, a_existingIds);
/*      */       }
/*      */       else
/*      */       {
/* 2185 */         result = vIds;
/*      */       }
/*      */     }
/* 2188 */     return result;
/*      */   }
/*      */ 
/*      */   private RelationshipDescriptionEntry buildRelationshipDescriptionEntry(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 2224 */     RelationshipDescriptionEntry entry = new RelationshipDescriptionEntry();
/* 2225 */     entry.setSourceAssetId(a_rs.getLong("ParentId"));
/* 2226 */     entry.setTargetAssetId(a_rs.getLong("ChildId"));
/* 2227 */     entry.setRelationshipTypeId(a_rs.getLong("RelationshipTypeId"));
/* 2228 */     entry.setDescription(a_rs.getString("RelationshipDescription"));
/*      */ 
/* 2230 */     return entry;
/*      */   }
/*      */ 
/*      */   private class SaveRelationshipPredicate
/*      */   {
/*      */     private SaveRelationshipPredicate()
/*      */     {
/*      */     }
/*      */ 
/*      */     public boolean saveRelationships(String a_sNewRelatedAssetIds, String a_sOriginalRelatedAssetIds)
/*      */     {
/* 2208 */       return ((!StringUtil.stringIsPopulated(a_sNewRelatedAssetIds)) && (StringUtil.stringIsPopulated(a_sOriginalRelatedAssetIds))) || ((StringUtil.stringIsPopulated(a_sNewRelatedAssetIds)) && (!a_sNewRelatedAssetIds.equals(a_sOriginalRelatedAssetIds)));
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.service.AssetRelationshipManager
 * JD-Core Version:    0.6.0
 */