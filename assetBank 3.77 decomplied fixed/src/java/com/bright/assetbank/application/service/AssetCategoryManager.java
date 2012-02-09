/*      */ package com.bright.assetbank.application.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.LightweightAsset;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*      */ import com.bright.assetbank.application.util.ABImageMagick;
/*      */ import com.bright.assetbank.application.util.AssetUtil;
/*      */ import com.bright.assetbank.batch.upload.service.ImportManager;
/*      */ import com.bright.assetbank.category.bean.CategoryImportBatch;
/*      */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*      */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*      */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*      */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*      */ import com.bright.assetbank.search.bean.SearchCriteria;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.bean.Category.Translation;
/*      */ import com.bright.framework.category.bean.CategoryBeanWrapper;
/*      */ import com.bright.framework.category.bean.CategoryImpl;
/*      */ import com.bright.framework.category.bean.FlatCategoryList;
/*      */ import com.bright.framework.category.bean.Item;
/*      */ import com.bright.framework.category.constant.CategorySettings;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.category.util.CategoryUtil;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.bean.TransactionListener;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.file.BeanWrapper;
/*      */ import com.bright.framework.file.DefaultBeanReader;
/*      */ import com.bright.framework.file.DefaultBeanReader.BeanPopulationException;
/*      */ import com.bright.framework.file.DefaultBeanReader.NoMatchForColumnHeaderException;
/*      */ import com.bright.framework.file.DefaultBeanReader.TooManyColumnsException;
/*      */ import com.bright.framework.file.ExcelFormat;
/*      */ import com.bright.framework.file.FileFormat;
/*      */ import com.bright.framework.image.exception.ImageException;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.service.LanguageManager;
/*      */ import com.bright.framework.queue.bean.QueuedItem;
/*      */ import com.bright.framework.search.bean.SearchResults;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.util.CollectionUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.avalon.framework.component.ComponentException;
/*      */ import org.apache.avalon.framework.component.ComponentManager;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AssetCategoryManager extends CategoryManager
/*      */   implements AssetBankConstants, AssetSaveParticipant, AssetDeleteParticipant
/*      */ {
/*  121 */   private List<CategoryDiskUsageExtension> m_categoryDiskUsageExtensions = new ArrayList();
/*      */ 
/*  133 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*      */ 
/*  140 */   private AssetManager m_assetManager = null;
/*      */ 
/*  165 */   private MultiLanguageSearchManager m_searchManager = null;
/*      */ 
/*  188 */   private CategoryCountCacheManager m_categoryCountCacheManager = null;
/*      */ 
/*  194 */   private ListManager m_listManager = null;
/*      */ 
/*  200 */   private AssetEntityManager m_entityManager = null;
/*      */ 
/*  206 */   private ImportManager m_importManager = null;
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  126 */     super.startup();
/*      */ 
/*  129 */     this.m_assetManager.registerAssetSaveParticipant(this);
/*  130 */     this.m_assetManager.registerAssetDeleteParticipant(this);
/*      */   }
/*      */ 
/*      */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*      */   {
/*  137 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*      */   }
/*      */ 
/*      */   public AssetManager getAssetManager()
/*      */   {
/*  144 */     if (this.m_assetManager == null)
/*      */     {
/*      */       try
/*      */       {
/*  148 */         this.m_assetManager = ((AssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager"));
/*      */       }
/*      */       catch (ComponentException ce)
/*      */       {
/*  152 */         GlobalApplication.getInstance().getLogger().error("ComponentException whilst getting asset manager component : " + ce);
/*      */       }
/*      */     }
/*      */ 
/*  156 */     return this.m_assetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(AssetManager a_sAssetManager)
/*      */   {
/*  162 */     this.m_assetManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/*  168 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public MultiLanguageSearchManager getSearchManager() {
/*  172 */     if (this.m_searchManager == null)
/*      */     {
/*      */       try
/*      */       {
/*  176 */         this.m_searchManager = ((MultiLanguageSearchManager)GlobalApplication.getInstance().getComponentManager().lookup("SearchManager"));
/*      */       }
/*      */       catch (ComponentException ce)
/*      */       {
/*  180 */         GlobalApplication.getInstance().getLogger().error("ComponentException whilst getting search manager component : " + ce);
/*      */       }
/*      */     }
/*      */ 
/*  184 */     return this.m_searchManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_sCategoryCountCacheManager)
/*      */   {
/*  191 */     this.m_categoryCountCacheManager = a_sCategoryCountCacheManager;
/*      */   }
/*      */ 
/*      */   public void setListManager(ListManager listManager)
/*      */   {
/*  197 */     this.m_listManager = listManager;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityManager(AssetEntityManager a_entityManager)
/*      */   {
/*  203 */     this.m_entityManager = a_entityManager;
/*      */   }
/*      */ 
/*      */   public void setImportManager(ImportManager a_importManager)
/*      */   {
/*  209 */     this.m_importManager = a_importManager;
/*      */   }
/*      */ 
/*      */   public void addCategoryDiskUsageExtension(CategoryDiskUsageExtension a_e)
/*      */   {
/*  214 */     this.m_categoryDiskUsageExtensions.add(a_e);
/*      */   }
/*      */ 
/*      */   public boolean newDescriptiveCategory(DBTransaction a_dbTransaction, long a_lCatTreeId, Category a_newCategory, long a_lParentCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  237 */     a_newCategory.setIsRestrictive(false);
/*      */ 
/*  240 */     if (a_lParentCatId == -1L)
/*      */     {
/*  243 */       a_newCategory.setIsBrowsable(true);
/*  244 */       a_newCategory.setIsListboxCategory(false);
/*      */     }
/*      */     else
/*      */     {
/*  249 */       Category parentCategory = getCategory(a_dbTransaction, a_lCatTreeId, a_lParentCatId);
/*      */ 
/*  251 */       a_newCategory.setIsBrowsable(parentCategory.getIsBrowsable());
/*  252 */       a_newCategory.setIsListboxCategory(parentCategory.getIsListboxCategory());
/*      */     }
/*      */ 
/*  256 */     boolean bSuccess = addCategory(a_dbTransaction, a_lCatTreeId, a_newCategory, a_lParentCatId);
/*      */ 
/*  258 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public boolean newPermissionCategory(DBTransaction a_dbTransaction, long a_lCatTreeId, Category a_newCategory, long a_lParentCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  282 */     if (a_lParentCatId == -1L)
/*      */     {
/*  285 */       a_newCategory.setIsBrowsable(true);
/*  286 */       a_newCategory.setIsListboxCategory(false);
/*  287 */       a_newCategory.setIsRestrictive(true);
/*      */     }
/*      */     else
/*      */     {
/*  292 */       Category parentCategory = getCategory(a_dbTransaction, a_lCatTreeId, a_lParentCatId);
/*      */ 
/*  294 */       a_newCategory.setIsBrowsable(parentCategory.getIsBrowsable());
/*  295 */       a_newCategory.setIsListboxCategory(parentCategory.getIsListboxCategory());
/*      */     }
/*      */ 
/*  299 */     boolean bSuccess = addCategory(a_dbTransaction, a_lCatTreeId, a_newCategory, a_lParentCatId);
/*      */ 
/*  301 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public boolean newCategory(DBTransaction a_dbTransaction, long a_lCatTreeId, Category a_newCategory, long a_lParentCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  326 */     a_newCategory.setIsRestrictive(false);
/*      */ 
/*  329 */     boolean bSuccess = addCategory(a_dbTransaction, a_lCatTreeId, a_newCategory, a_lParentCatId);
/*      */ 
/*  334 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public Item getItem(long a_lItemId)
/*      */     throws Bn2Exception
/*      */   {
/*  355 */     return getAssetManager().getAsset(null, a_lItemId, null, false, false);
/*      */   }
/*      */ 
/*      */   public int getItemsInCategoryCount(DBTransaction a_dbTransaction, long a_lCatId)
/*      */     throws Bn2Exception
/*      */   {
/*  377 */     BaseSearchQuery searchCriteria = new SearchCriteria();
/*  378 */     searchCriteria.setCategoryIds(String.valueOf(a_lCatId));
/*      */ 
/*  380 */     return getSearchManager().getHitCount(searchCriteria);
/*      */   }
/*      */ 
/*      */   public void deleteCategory(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCategoryId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  406 */     deleteCategory(a_dbTransaction, a_lTreeId, a_lCategoryId, -1L, a_lUserId);
/*      */   }
/*      */ 
/*      */   public void deleteCategory(DBTransaction a_dbTransaction, long a_lTreeId, long a_lCategoryId, long a_lMoveToCategoryId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  442 */     Connection con = null;
/*  443 */     PreparedStatement psql = null;
/*  444 */     String sSQL = null;
/*      */     try
/*      */     {
/*  448 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  451 */       final long[] alCatIds = getCategoryAndDescendantIds(a_dbTransaction, a_lTreeId, a_lCategoryId);
/*      */ 
/*  454 */       if ((a_lMoveToCategoryId > 0L) && (alCatIds.length > 1))
/*      */       {
/*  456 */         throw new Bn2Exception("AssetCategoryManager.deleteCategory : cannot move assets to another category as this category has sub-categories");
/*      */       }
/*      */ 
/*  461 */       String sCategoryIds = StringUtil.convertNumbersToString(alCatIds, ", ");
/*      */ 
/*  465 */       String sAssetIdsToUpdate = "";
/*  466 */       String sSql = "SELECT ItemId FROM CM_ItemInCategory WHERE CategoryId IN (" + sCategoryIds + ")";
/*  467 */       psql = con.prepareStatement(sSql);
/*  468 */       ResultSet rs = psql.executeQuery();
/*  469 */       while (rs.next())
/*      */       {
/*  471 */         psql = con.prepareStatement(sSql);
/*      */ 
/*  473 */         if (sAssetIdsToUpdate.length() > 0)
/*      */         {
/*  475 */           sAssetIdsToUpdate = sAssetIdsToUpdate + ", ";
/*      */         }
/*      */ 
/*  478 */         sAssetIdsToUpdate = sAssetIdsToUpdate + rs.getLong("ItemId");
/*      */       }
/*      */ 
/*  481 */       psql.close();
/*      */ 
/*  484 */       if (a_lMoveToCategoryId > 0L)
/*      */       {
/*  486 */         moveItems(a_dbTransaction, a_lTreeId, a_lCategoryId, a_lMoveToCategoryId);
/*      */       }
/*      */ 
/*  490 */       if (a_lTreeId == 2L)
/*      */       {
/*  492 */         tidyWorkflowsForAccessLevel(a_dbTransaction, a_lCategoryId);
/*      */       }
/*      */ 
/*  496 */       sSQL = "SELECT ExtensionAssetId FROM CM_Category WHERE Id IN (" + sCategoryIds + ")";
/*  497 */       psql = con.prepareStatement(sSQL);
/*  498 */       rs = psql.executeQuery();
/*  499 */       ArrayList<Long>  alExtensionAssets = new ArrayList();
/*  500 */       while (rs.next())
/*      */       {
/*  502 */         long lId = rs.getLong("ExtensionAssetId");
/*  503 */         if (lId > 0L)
/*      */         {
/*  505 */           alExtensionAssets.add(new Long(lId));
/*      */         }
/*      */       }
/*  508 */       psql.close();
/*      */ 
/*  512 */       sSQL = "DELETE FROM CategoryVisibleToGroup WHERE CategoryId IN (" + sCategoryIds + ")";
/*  513 */       psql = con.prepareStatement(sSQL);
/*  514 */       psql.executeUpdate();
/*  515 */       psql.close();
/*      */ 
/*  519 */       sSQL = "UPDATE OrgUnit SET RootDescriptiveCategoryId=? WHERE RootDescriptiveCategoryId IN (" + sCategoryIds + ")";
/*  520 */       psql = con.prepareStatement(sSQL);
/*  521 */       psql.setNull(1, -5);
/*  522 */       psql.executeUpdate();
/*  523 */       psql.close();
/*      */ 
/*  526 */       String sField = null;
/*  527 */       if (a_lTreeId == 1L)
/*      */       {
/*  529 */         sField = "CategoryIds";
/*      */       }
/*  531 */       else if (a_lTreeId == 2L)
/*      */       {
/*  533 */         sField = "AccessLevelIds";
/*      */       }
/*      */ 
/*  536 */       if (StringUtil.stringIsPopulated(sField))
/*      */       {
/*  538 */         sSQL = "UPDATE Filter SET " + sField + "=REPLACE(" + sField + ", '," + a_lCategoryId + ",', ',')";
/*  539 */         psql = con.prepareStatement(sSQL);
/*  540 */         psql.executeUpdate();
/*  541 */         psql.close();
/*      */       }
/*      */ 
/*  544 */       sSQL = "DELETE FROM FilterForCategory WHERE CategoryId=?";
/*  545 */       psql = con.prepareStatement(sSQL);
/*  546 */       psql.setLong(1, a_lCategoryId);
/*  547 */       psql.executeUpdate();
/*  548 */       psql.close();
/*      */ 
/*  551 */       super.deleteCategoryFromDatabase(a_dbTransaction, a_lTreeId, a_lCategoryId);
/*      */ 
/*  554 */       if (StringUtil.stringIsPopulated(sAssetIdsToUpdate))
/*      */       {
/*  556 */         sSql = "UPDATE Asset SET LastModifiedByUserId =?, DateLastModified=? WHERE Id IN (" + sAssetIdsToUpdate + ")";
/*  557 */         psql = con.prepareStatement(sSql);
/*  558 */         psql.setLong(1, a_lUserId);
/*  559 */         DBUtil.setFieldTimestampOrNull(psql, 2, new Date());
/*      */ 
/*  561 */         psql.executeUpdate();
/*  562 */         psql.close();
/*      */       }
/*      */ 
/*  566 */       if (alExtensionAssets.size() > 0)
/*      */       {
/*  568 */         for (Long longId : alExtensionAssets)
/*      */         {
/*      */           try
/*      */           {
/*  572 */             this.m_assetManager.deleteAsset(a_dbTransaction, longId.longValue());
/*      */           }
/*      */           catch (AssetNotFoundException e)
/*      */           {
/*  576 */             this.m_logger.error(getClass().getSimpleName() + ": Unable to delete asset with id: " + longId.longValue(), e);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  583 */       if (StringUtil.stringIsPopulated(sAssetIdsToUpdate))
/*      */       {
/*  589 */        // a_dbTransaction.addListener(new TransactionListener(alCatIds)
                    a_dbTransaction.addListener(new TransactionListener()
/*      */         {
/*      */           public void doAfterCommit()
/*      */           {
/*      */             try
/*      */             {
/*  596 */               AssetCategoryManager.this.reindexAssetsInCategories(alCatIds);
/*      */             }
/*      */             catch (Bn2Exception e)
/*      */             {
/*  600 */               AssetCategoryManager.this.m_logger.error("Error reindexing assets after category deletion", e);
/*      */             }
/*      */           }
/*      */         });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  609 */       throw new Bn2Exception("Exception whilst deleting a category : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addItemToCategory(DBTransaction a_dbTransaction, long a_lAssetId, long a_lCatId, long a_lCatTreeId)
/*      */     throws Bn2Exception
/*      */   {
/*  617 */     addItemToCategory(a_dbTransaction, a_lAssetId, a_lCatId, a_lCatTreeId, false);
/*      */   }
/*      */ 
/*      */   public void addItemToCategory(DBTransaction a_dbTransaction, long a_lAssetId, long a_lCatId, long a_lCatTreeId, boolean a_bIgnoreRelationshipCheck)
/*      */     throws Bn2Exception
/*      */   {
/*  636 */     if ((AssetBankSettings.getCategoryExtensionAssetsEnabled()) && (!a_bIgnoreRelationshipCheck) && ((a_lCatTreeId == 2L) || (a_lCatTreeId == 1L)))
/*      */     {
/*  641 */       checkExtensionAsset(a_dbTransaction, a_lCatId, a_lCatTreeId, a_lAssetId, null);
/*      */     }
/*      */ 
/*  645 */     super.addItemToCategory(a_dbTransaction, a_lAssetId, a_lCatId, a_lCatTreeId);
/*      */   }
/*      */ 
/*      */   public void deleteItemFromCategory(DBTransaction a_dbTransaction, long a_lItemId, long a_lCatId, long a_lCatTreeId)
/*      */     throws Bn2Exception
/*      */   {
/*  655 */     deleteItemFromCategory(a_dbTransaction, a_lItemId, a_lCatId, a_lCatTreeId, false);
/*      */   }
/*      */ 
/*      */   public void deleteItemFromCategory(DBTransaction a_dbTransaction, long a_lItemId, long a_lCatId, long a_lCatTreeId, boolean a_bIgnoreRelationshipCheck)
/*      */     throws Bn2Exception
/*      */   {
/*  676 */     if ((AssetBankSettings.getCategoryExtensionAssetsEnabled()) && (!a_bIgnoreRelationshipCheck) && ((a_lCatTreeId == 2L) || (a_lCatTreeId == 1L)))
/*      */     {
/*  681 */       Category cat = getCategory(a_dbTransaction, a_lCatTreeId, a_lCatId);
/*  682 */       if (cat.getExtensionAssetId() > 0L)
/*      */       {
/*  685 */         this.m_assetRelationshipManager.deleteAssetRelationship(a_dbTransaction, cat.getExtensionAssetId(), a_lCatId, 2L, true);
/*      */       }
/*      */     }
/*      */ 
/*  689 */     super.deleteItemFromCategory(a_dbTransaction, a_lItemId, a_lCatId, a_lCatTreeId);
/*      */   }
/*      */ 
/*      */   public void checkExtensionAsset(DBTransaction a_dbTransaction, long a_lCatId, long a_lCatTreeId, long a_lAssetId, Asset a_tempAsset)
/*      */   {
/*      */     try
/*      */     {
/*  706 */       Category cat = getCategory(a_dbTransaction, a_lCatTreeId, a_lCatId);
/*  707 */       if (cat.getExtensionAssetId() > 0L)
/*      */       {
/*  712 */         Asset extensionAsset = this.m_assetManager.getAsset(a_dbTransaction, cat.getExtensionAssetId());
/*  713 */         Asset tempAsset = a_tempAsset;
/*  714 */         if (tempAsset == null)
/*      */         {
/*  716 */           tempAsset = this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId);
/*      */         }
/*      */ 
/*  719 */         if (CollectionUtil.longArrayContains(extensionAsset.getEntity().getChildRelationshipIds(), tempAsset.getEntity().getId()))
/*      */         {
/*  721 */           String sRelatedIds = extensionAsset.getChildAssetIdsAsString();
/*  722 */           if (StringUtil.stringIsPopulated(sRelatedIds))
/*      */           {
/*  724 */             sRelatedIds = sRelatedIds + "," + String.valueOf(tempAsset.getId());
/*      */           }
/*      */           else
/*      */           {
/*  728 */             sRelatedIds = String.valueOf(tempAsset.getId());
/*      */           }
/*      */ 
/*  732 */           this.m_assetRelationshipManager.saveRelatedAssetIds(a_dbTransaction, extensionAsset, extensionAsset.getId(), sRelatedIds, 2L, true, false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/*  744 */       this.m_logger.error(getClass().getSimpleName() + ": Error setting up relationship for extended category asset: ", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void tidyWorkflowsForAccessLevel(DBTransaction a_dbTransaction, long a_lCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/*  754 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/*  757 */       String sSQL = "SELECT c.Id, c.WorkflowName FROM CM_Category c WHERE c.WorkflowName=(SELECT WorkflowName FROM CM_Category WHERE Id=?)";
/*      */ 
/*  759 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  760 */       psql.setLong(1, a_lCategoryId);
/*  761 */       ResultSet rs = psql.executeQuery();
/*  762 */       Vector vecCatIds = new Vector();
/*  763 */       String sWorkflowName = null;
/*  764 */       while (rs.next())
/*      */       {
/*  766 */         vecCatIds.add(rs.getString("Id"));
/*  767 */         if (sWorkflowName != null)
/*      */           continue;
/*  769 */         sWorkflowName = rs.getString("WorkflowName");
/*      */       }
/*      */ 
/*  772 */       psql.close();
/*  773 */       String sCatIdString = StringUtil.convertStringVectorToString(vecCatIds, ",");
/*      */ 
/*  775 */       if (StringUtil.stringIsPopulated(sCatIdString))
/*      */       {
/*  779 */         sSQL = "DELETE FROM WorkflowInfo WHERE WorkflowName=? AND WorkflowableEntityId IN (SELECT ItemId FROM CM_ItemInCategory WHERE CategoryId=? AND NOT ItemId IN (SELECT ItemId FROM CM_ItemInCategory WHERE CategoryId IN (" + sCatIdString + ")))";
/*      */ 
/*  784 */         psql = con.prepareStatement(sSQL);
/*  785 */         psql.setString(1, sWorkflowName);
/*  786 */         psql.setLong(2, a_lCategoryId);
/*  787 */         psql.executeUpdate();
/*      */       }
/*      */       else
/*      */       {
/*  793 */         sSQL = "DELETE FROM WorkflowInfo WHERE WorkflowName=?";
/*  794 */         psql = con.prepareStatement(sSQL);
/*  795 */         psql.setString(1, sWorkflowName);
/*  796 */         psql.executeUpdate();
/*      */       }
/*  798 */       psql.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  802 */       throw new Bn2Exception("Exception whilst tidying up workflows for access level " + a_lCategoryId + " : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void reindexAssetsInCategories(long[] alCatIds)
/*      */     throws Bn2Exception
/*      */   {
/*  828 */     String sCategoryIds = StringUtil.convertNumbersToString(alCatIds, ", ");
/*  829 */     this.m_logger.debug("reindexAssetsInCategories: " + sCategoryIds);
/*      */ 
/*  832 */     BaseSearchQuery searchCriteria = new SearchCriteria();
/*  833 */     searchCriteria.setCategoryIds(sCategoryIds);
/*  834 */     searchCriteria.setOrCategories(true);
/*      */ 
/*  837 */     SearchResults searchResults = getSearchManager().search(searchCriteria);
/*  838 */     Vector vecResults = searchResults.getSearchResults();
/*      */ 
/*  841 */     reindexAssetsInCategoriesAsynchronously(vecResults);
/*  842 */     this.m_logger.debug("reindexAssetsInCategories: reindexing categories now");
/*      */   }
/*      */ 
/*      */   private void reindexAssetsInCategoriesAsynchronously(final Vector<LightweightAsset> a_vecAssets)
/*      */   {
/*  858 */     Thread thread = new Thread()
/*      */     {
/*      */       public void run()
/*      */       {
/*      */         try
/*      */         {
/*  865 */           AssetCategoryManager.this.reindexAssetsInCategoriesNow(a_vecAssets);
/*  866 */           AssetCategoryManager.this.m_categoryCountCacheManager.invalidateCache();
/*      */         }
/*      */         catch (Bn2Exception bn2e)
/*      */         {
/*  870 */           AssetCategoryManager.this.m_logger.error("reindexAssetsInCategoriesAsynchronously : Bn2Exception whilst reindexing : " + bn2e, bn2e);
/*      */         }
/*      */       }
/*      */     };
/*  875 */     thread.start();
/*      */   }
/*      */ 
/*      */   private void reindexAssetsInCategoriesNow(Vector<LightweightAsset> a_vecAssets)
/*      */     throws Bn2Exception
/*      */   {
/*  893 */     DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */     try
/*      */     {
/*  898 */       if (a_vecAssets != null)
/*      */       {
/*  900 */         for (LightweightAsset tempAsset : a_vecAssets)
/*      */         {
/*  903 */           Asset asset = getAssetManager().getAsset(dbTransaction, tempAsset.getId(), null, true, true);
/*      */ 
/*  906 */           this.m_searchManager.indexDocument(asset, true);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  912 */       throw new Bn2Exception("reindexAssetsInCategoriesNow: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  919 */         dbTransaction.commit();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  923 */         this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */       }
/*      */     }
/*      */ 
/*  927 */     this.m_logger.debug("reindexAssetsInCategories: reindexing complete");
/*      */   }
/*      */ 
/*      */   public String getItemTableName()
/*      */   {
/*  946 */     return "Asset";
/*      */   }
/*      */ 
/*      */   public Vector<Category> getCategories(String a_sTypeId, String a_sCategoryId)
/*      */     throws Bn2Exception
/*      */   {
/*  965 */     long lTypeId = Long.parseLong(a_sTypeId);
/*  966 */     long lCatId = Long.parseLong(a_sCategoryId);
/*      */ 
/*  968 */     Vector vec = null;
/*  969 */     DBTransaction dbTransaction = null;
/*      */     try
/*      */     {
/*  973 */       dbTransaction = getTransactionManager().getNewTransaction();
/*  974 */       vec = getCategories(dbTransaction, lTypeId, lCatId);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  978 */       throw new Bn2Exception("AssetCategoryManager.getCategories: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  984 */         dbTransaction.commit();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  988 */         this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */       }
/*      */     }
/*      */ 
/*  992 */     return vec;
/*      */   }
/*      */ 
/*      */   public String getCategoryTypeName(long a_lCategoryTypeId)
/*      */   {
/* 1006 */     return null;
/*      */   }
/*      */ 
/*      */   public String getRootCategoryName(DBTransaction a_transaction, Language a_language, long a_lCategoryTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1013 */     String sItemId = getRootCategoryItemId(a_lCategoryTypeId);
/*      */ 
/* 1015 */     return this.m_listManager.getListItem(a_transaction, sItemId, a_language).getBody();
/*      */   }
/*      */ 
/*      */   private String getRootCategoryItemId(long a_lCategoryTypeId)
/*      */   {
/*      */     String sItemId;
/*      */     //String sItemId;
/* 1022 */     if (a_lCategoryTypeId == 1L)
/*      */     {
/* 1024 */       sItemId = "category-root";
/*      */     }
/*      */     else
/*      */     {
/*      */       //String sItemId=;
/* 1026 */       if (a_lCategoryTypeId == 2L)
/*      */       {
/* 1028 */         sItemId = "access-level-root";
/*      */       }
/*      */       else
/*      */       {
/* 1032 */         sItemId = "keyword-root";
/*      */       }
/*      */     }
/* 1034 */     return sItemId;
/*      */   }
/*      */ 
/*      */   public boolean changeCategoryParent(DBTransaction a_dbTransaction, long a_lTreeId, Category a_category, long a_lNewParentId)
/*      */     throws Bn2Exception
/*      */   {
/* 1064 */     boolean bSuccess = super.changeCategoryParent(a_dbTransaction, a_lTreeId, a_category, a_lNewParentId);
/*      */ 
/* 1066 */     if (bSuccess)
/*      */     {
/* 1069 */       long[] alCatIds = getCategoryAndDescendantIds(a_dbTransaction, a_lTreeId, a_category.getId());
/* 1070 */       reindexAssetsInCategories(alCatIds);
/*      */     }
/*      */ 
/* 1073 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public boolean updateCategoryDetails(DBTransaction a_dbTransaction, long a_lTreeId, Category a_category)
/*      */     throws Bn2Exception
/*      */   {
/* 1097 */     return updateCategoryDetails(a_dbTransaction, a_lTreeId, a_category, true);
/*      */   }
/*      */ 
/*      */   public boolean updateCategoryDetails(DBTransaction a_dbTransaction, long a_lTreeId, Category a_category, boolean a_bReindex)
/*      */     throws Bn2Exception
/*      */   {
/* 1115 */     boolean bSuccess = super.updateCategoryDetails(a_dbTransaction, a_lTreeId, a_category);
/*      */ 
/* 1117 */     if ((bSuccess) && (a_bReindex))
/*      */     {
/* 1120 */       long[] alCatIds = getCategoryAndDescendantIds(a_dbTransaction, a_lTreeId, a_category.getId());
/* 1121 */       reindexAssetsInCategories(alCatIds);
/*      */     }
/*      */ 
/* 1124 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public Vector<Category> removeEmptyCategories(Vector<Category> a_vCats, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/* 1138 */     Vector vFiltered = new Vector();
/* 1139 */     if (a_vCats != null)
/*      */     {
/* 1141 */       for (int i = 0; i < a_vCats.size(); i++)
/*      */       {
/* 1143 */         Category cat = (Category)a_vCats.get(i);
/* 1144 */         if (this.m_categoryCountCacheManager.getItemCount(cat.getId(), cat.getCategoryTypeId(), a_userProfile).intValue() <= 0)
/*      */           continue;
/* 1146 */         vFiltered.add(cat);
/*      */       }
/*      */     }
/*      */ 
/* 1150 */     return vFiltered;
/*      */   }
/*      */ 
/*      */   public long getDiskUsageForCategoryAssets(DBTransaction a_dbTransaction, long a_lCategoryId, long a_lCategoryTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1166 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1168 */     long lResult = 0L;
/*      */ 
/* 1170 */     long[] ids = getCategoryAndDescendantIds(a_dbTransaction, a_lCategoryTypeId, a_lCategoryId);
/*      */     try
/*      */     {
/* 1174 */       if (transaction == null)
/*      */       {
/* 1176 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/* 1179 */       Connection con = transaction.getConnection();
/*      */ 
/* 1181 */       String sSql = "SELECT SUM(aFileSizeInBytes) diskUsageInBytes FROM (SELECT a.FileSizeInBytes aFileSizeInBytes FROM Asset a, CM_ItemInCategory iic, CM_Category c WHERE a.Id=iic.ItemId  AND iic.CategoryId IN (" + StringUtil.convertNumbersToString(ids, ",") + ") " + " AND c.Id=iic.CategoryId " + " AND c.CategoryTypeId=? " + "GROUP BY a.FileSizeInBytes) DummyAlias";
/*      */ 
/* 1189 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 1190 */       psql.setLong(1, a_lCategoryTypeId);
/* 1191 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1193 */       if (rs.next())
/*      */       {
/* 1195 */         lResult = rs.getLong("diskUsageInBytes");
/*      */       }
/* 1197 */       psql.close();
/*      */ 
/* 1199 */       for (CategoryDiskUsageExtension extension : this.m_categoryDiskUsageExtensions)
/*      */       {
/* 1201 */         lResult += extension.getAdditionalDiskUsageForCategoryAssets(transaction, ids, a_lCategoryTypeId);
/*      */       }
/*      */     
                 return lResult;
/* 1205 */     //  ??? = lResult;
/*      */      // return ???;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1209 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1213 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1217 */           this.m_logger.error("SQL Exception whilst trying to roll back transaction : " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1221 */       this.m_logger.error("SQL Exception whilst getting disk usage for a category : " + e);
/* 1222 */       throw new Bn2Exception("SQL Exception whilst getting disk usage for a category : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1227 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1231 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1235 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
                      
/*      */         }
/*      */       }
/* 1236 */     }//throw localObject;
/*      */   }
/*      */ 
/*      */   public LightweightAsset findExtensionAssetForCategory(ABUserProfile a_userProfile, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1255 */     SearchCriteria criteria = new SearchCriteria();
/* 1256 */     criteria.setAssetIds(String.valueOf(a_lAssetId));
/* 1257 */     if (!a_userProfile.getIsAdmin())
/*      */     {
/* 1259 */       if (a_userProfile.getGroupIds() == null)
/*      */       {
/* 1261 */         criteria.addGroupRestriction(2L);
/*      */       }
/*      */       else
/*      */       {
/* 1265 */         for (Long longGroupId : a_userProfile.getGroupIds())
/*      */         {
/* 1267 */           criteria.addGroupRestriction(longGroupId.longValue());
/*      */         }
/*      */       }
/*      */     }
/* 1271 */     SearchResults results = this.m_searchManager.search(criteria);
/* 1272 */     if ((results != null) && (results.getTotalHits() > 0))
/*      */     {
/* 1274 */       return (LightweightAsset)results.getSearchResults().firstElement();
/*      */     }
/* 1276 */     return null;
/*      */   }
/*      */ 
/*      */   public void updateCategoryWorkflowsForChildren(DBTransaction a_dbTransaction, long a_lCategoryId, long a_lCategoryTypeId, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/* 1290 */     Connection con = null;
/* 1291 */     PreparedStatement psql = null;
/* 1292 */     DBTransaction transaction = a_dbTransaction;
/* 1293 */     String sSql = null;
/*      */ 
/* 1295 */     long[] ids = getCategoryAndDescendantIds(a_dbTransaction, a_lCategoryTypeId, a_lCategoryId);
/* 1296 */     String sIds = StringUtil.convertNumbersToString(ids, ",");
/*      */ 
/* 1298 */     if (StringUtil.stringIsPopulated(sIds))
/*      */     {
/*      */       try
/*      */       {
/* 1302 */         if (transaction == null)
/*      */         {
/* 1304 */           transaction = this.m_transactionManager.getNewTransaction();
/*      */         }
/*      */ 
/* 1307 */         con = transaction.getConnection();
/*      */ 
/* 1309 */         sSql = "UPDATE CM_Category SET WorkflowName=? WHERE Id IN (" + sIds + ")";
/*      */ 
/* 1311 */         psql = con.prepareStatement(sSql);
/* 1312 */         psql.setString(1, a_sWorkflowName);
/* 1313 */         psql.executeUpdate();
/* 1314 */         psql.close();
/*      */ 
/* 1318 */         for (int i = 0; i < ids.length; i++)
/*      */         {
/* 1320 */           long lId = ids[i];
/* 1321 */           Category cat = getCategory(transaction, a_lCategoryTypeId, lId);
/* 1322 */           cat.setWorkflowName(a_sWorkflowName);
/*      */         }
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 1327 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/* 1331 */             transaction.rollback();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 1335 */             this.m_logger.error("SQL Exception whilst trying to roll back transaction : " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */ 
/* 1339 */         this.m_logger.error("SQL Exception whilst updating workflows for a categories descendants : " + e);
/* 1340 */         throw new Bn2Exception("SQL Exception whilst updating workflows for a categories descendants : " + e, e);
/*      */       }
/*      */       finally
/*      */       {
/* 1345 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/* 1349 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 1353 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void initAssetSave(AssetSaveContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/* 1370 */     if (AssetBankSettings.getCategoryExtensionAssetsEnabled())
/*      */     {
/* 1372 */       a_context.needOriginalAsset();
/* 1373 */       a_context.setAttribute(getClass().getSimpleName(), "catExtensionCatId", new Long(a_context.getAsset().getExtendsCategory().getId()));
/*      */ 
/* 1376 */       a_context.setAttribute(getClass().getSimpleName(), "catExtensionTreeId", new Long(a_context.getAsset().getExtendsCategory().getCategoryTypeId()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void save(AssetSaveContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/* 1384 */     if (AssetBankSettings.getCategoryExtensionAssetsEnabled())
/*      */     {
/* 1388 */       long lExtendsCatId = ((Long)(Long)a_context.getAttribute(getClass().getSimpleName(), "catExtensionCatId")).longValue();
/* 1389 */       long lTreeId = ((Long)(Long)a_context.getAttribute(getClass().getSimpleName(), "catExtensionTreeId")).longValue();
/*      */ 
/* 1391 */       boolean bNamesMatch = true;
/* 1392 */       if ((!a_context.isNew()) && (lExtendsCatId == a_context.getOriginalAsset().getExtendsCategory().getId()))
/*      */       {
/* 1394 */         bNamesMatch = AssetUtil.doNamesMatch(a_context.getAsset(), a_context.getOriginalAsset());
/*      */       }
/*      */ 
/* 1399 */       if (lExtendsCatId > 0L)
/*      */       {
/* 1401 */         if ((a_context.isNew()) || (lExtendsCatId != a_context.getOriginalAsset().getExtendsCategory().getId()) || (!bNamesMatch))
/*      */         {
/* 1403 */           extendCategory(null, lExtendsCatId, lTreeId, a_context.getAsset(), false);
/*      */         }
/*      */         else
/*      */         {
/* 1411 */           Category category = getCategory(null, lTreeId, lExtendsCatId);
/* 1412 */           populateExtensionAsset(a_context.getAsset(), category);
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
/*      */   public void assetWillBeDeleted(AssetDeleteContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   public void assetWasDeleted(AssetDeleteContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/* 1436 */     if ((a_context != null) && (a_context.getOriginalAsset() != null) && (a_context.getOriginalAsset().getExtendsCategory() != null) && (a_context.getOriginalAsset().getExtendsCategory().getId() > 0L) && (a_context.getOriginalAsset().getExtendsCategory().getCategoryTypeId() > 0L))
/*      */     {
/* 1443 */       clearCache(a_context.getOriginalAsset().getExtendsCategory().getCategoryTypeId());
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean extendCategory(DBTransaction a_dbTransaction, long a_lCategoryId, long a_lCategoryTreeId, Asset a_asset, boolean a_bImporting)
/*      */     throws Bn2Exception
/*      */   {
/* 1464 */     DBTransaction transaction = a_dbTransaction;
/* 1465 */     boolean bSuccess = false;
/*      */     try
/*      */     {
/* 1470 */       if ((a_lCategoryId > 0L) && (a_asset.getId() > 0L))
/*      */       {
/* 1472 */         if (transaction == null)
/*      */         {
/* 1474 */           transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */         }
/*      */ 
/* 1478 */         Category category = getCategory(transaction, a_lCategoryTreeId, a_lCategoryId).clone();
/* 1479 */         category.setExtensionAssetId(a_asset.getId());
/*      */ 
/* 1482 */         if ((!a_bImporting) || (AssetBankSettings.getReplaceExtendedCategoryNamesOnImport()))
/*      */         {
/* 1484 */           category.setName(a_asset.getName());
/*      */ 
/* 1487 */           List<Language> langs = this.m_languageManager.getLanguages(transaction);
/*      */ 
/* 1489 */           for (Language lang : langs)
/*      */           {
/* 1491 */             String sName = a_asset.getName(lang);
/* 1492 */             if ((sName != null) && (!sName.equals(category.getName())))
/*      */             {
/* 1494 */               boolean bSet = false;
/*      */ 
/* 1497 */               Vector<Category.Translation> vecTranslations = category.getTranslations();
/* 1498 */               for (Category.Translation trans : vecTranslations)
/*      */               {
/* 1500 */                 if (trans.getLanguage().getId() == lang.getId())
/*      */                 {
/* 1502 */                   trans.setName(sName);
/* 1503 */                   bSet = true;
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/* 1508 */               if (!bSet)
/*      */               {
/* 1510 */                 Category.Translation trans = category.createTranslation(lang);
/* 1511 */                 trans.setName(sName);
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1518 */         bSuccess = updateCategoryDetails(transaction, a_lCategoryTreeId, category, false);
/*      */ 
/* 1520 */         if (!bSuccess)
/*      */         {
/* 1523 */           updateExtensionAssetId(transaction, category.getId(), a_asset.getId());
/* 1524 */           populateExtensionAsset(a_asset, category);
/* 1525 */           cacheUpdateCategory(a_lCategoryTreeId, category);
/*      */         }
/*      */         else
/*      */         {
/* 1529 */           populateExtensionAsset(a_asset, category);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1535 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */         try {
/* 1537 */           transaction.rollback(); } catch (SQLException ex) {
/*      */         }
/* 1539 */       String sMessage = getClass().getSimpleName() + ".extendCategory: Error extending category: ";
/* 1540 */       this.m_logger.error(sMessage, e);
/* 1541 */       throw new Bn2Exception(sMessage, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1545 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */         try {
/* 1547 */           transaction.commit();
/*      */         } catch (SQLException ex) {
/*      */         }
/*      */     }
/* 1551 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   private void populateExtensionAsset(Asset a_asset, Category a_category)
/*      */   {
/* 1564 */     a_asset.getExtendsCategory().setId(a_category.getId());
/* 1565 */     a_asset.getExtendsCategory().setCategoryTypeId(a_category.getCategoryTypeId());
/* 1566 */     a_asset.getExtendsCategory().setParentId(a_category.getParentId());
/* 1567 */     a_asset.getExtendsCategory().setName(a_category.getFullName());
/*      */   }
/*      */ 
/*      */   private void updateExtensionAssetId(DBTransaction a_transaction, long a_lCatId, long a_lAssetId)
/*      */     throws Bn2Exception, SQLException
/*      */   {
/* 1585 */     Connection con = a_transaction.getConnection();
/* 1586 */     String sSQL = "UPDATE CM_Category SET ExtensionAssetId=? WHERE Id=?";
/* 1587 */     PreparedStatement psql = con.prepareStatement(sSQL);
/* 1588 */     DBUtil.setFieldIdOrNull(psql, 1, a_lAssetId);
/* 1589 */     psql.setLong(2, a_lCatId);
/* 1590 */     psql.executeUpdate();
/* 1591 */     psql.close();
/*      */   }
/*      */ 
/*      */   public void processQueueItem(QueuedItem a_item)
/*      */     throws Bn2Exception
/*      */   {
/* 1603 */     DBTransaction transaction = null;
/* 1604 */     CategoryImportBatch batch = (CategoryImportBatch)a_item;
/* 1605 */     startBatchProcess(batch.getJobId());
/*      */ 
/* 1607 */     boolean bError = false;
/* 1608 */     int iCatsAdded = 0;
/*      */     try
/*      */     {
/* 1611 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/* 1612 */       iCatsAdded = importCategories(transaction, batch);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1616 */       this.m_logger.error(getClass().getSimpleName() + ".processQueueItem: Error: ", ex);
/* 1617 */       bError = true;
/*      */     }
/*      */     finally
/*      */     {
/* 1621 */       addMessage(batch.getJobId(), "Import finished. " + iCatsAdded + " imported.");
/* 1622 */       endBatchProcess(batch.getJobId());
/*      */       try
/*      */       {
/* 1626 */         if ((bError) && (transaction != null))
/*      */         {
/* 1628 */           transaction.rollback();
/*      */         }
/* 1630 */         else if (transaction != null)
/*      */         {
/* 1632 */           transaction.commit();
/*      */         }
/*      */       }
/*      */       catch (SQLException ex)
/*      */       {
/* 1637 */         this.m_logger.error(getClass().getSimpleName() + ".processQueueItem: Error handling transaction: ", ex);
/*      */       }
/*      */     }
/*      */   }
/*      */ /*  private */
/*      */     public int importCategories(DBTransaction a_dbTransaction, CategoryImportBatch a_batch)
/*      */     throws Bn2Exception, DefaultBeanReader.TooManyColumnsException
/*      */   {
/* 1654 */     addMessage(a_batch.getJobId(), "Starting import categories");
/*      */ 
/* 1656 */     int k_iNumCatsPerFileRead = 1000;
/*      */ 
/* 1658 */     String sFileLocation = null;
/* 1659 */     BufferedReader reader = null;
/* 1660 */     int iCatsAdded = 0;
/*      */ 
/* 1662 */     sFileLocation = this.m_fileStoreManager.getAbsolutePath(a_batch.getImportFileLocation());
/*      */ 
/* 1665 */     HashMap hmFullnameCache = new HashMap();
/*      */ 
/* 1667 */     Vector vecFullList = getFlatCategoryList(a_dbTransaction, a_batch.getCategoryTypeId()).getCategories();
/*      */ 
/* 1669 */     Category catItem = null;
/*      */ 
/* 1672 */     for (int i = 0; i < vecFullList.size(); i++)
/*      */     {
/* 1674 */       catItem = (Category)vecFullList.get(i);
/* 1675 */       hmFullnameCache.put(catItem.getJSUnicodeFullName(), catItem);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1680 */       reader = new BufferedReader(new FileReader(new File(sFileLocation)));
/* 1681 */       FileFormat format = new ExcelFormat();
/* 1682 */       BeanWrapper wrapper = new CategoryBeanWrapper();
/* 1683 */       DefaultBeanReader catReader = null;
/*      */       try
/*      */       {
/* 1687 */         catReader = new DefaultBeanReader(reader, format, CategoryImpl.class, wrapper);
/*      */       }
/*      */       catch (DefaultBeanReader.NoMatchForColumnHeaderException e)
/*      */       {
/* 1691 */         this.m_logger.error("CategoryManager.importCategories() : NoMatchForColumnHeaderException reading data file header : " + e.getMessage());
/*      */ 
/* 1694 */         throw new Bn2Exception("CategoryManager.importCategories() : NoMatchForColumnHeaderException due to unexpected column", e);
/*      */       }
/*      */ 
/* 1697 */       Vector vCats = null;
/* 1698 */       int iLineNo = 1;
/* 1699 */       boolean bFinished = false;
/* 1700 */       HashMap alCatsNeedingExtension = new HashMap();
/* 1701 */       HashMap hmEntities = new HashMap();
/*      */       do
/*      */       {
/*      */         try
/*      */         {
/* 1709 */           vCats = catReader.readBeans(1000);
/*      */ 
/* 1711 */           if (vCats.size() > 0)
/*      */           {
/* 1714 */             for (int i = 0; i < vCats.size(); i++)
/*      */             {
/* 1716 */               iLineNo++;
/* 1717 */               Category cat = (Category)vCats.get(i);
/*      */ 
/* 1719 */               if ((cat == null) || (cat.getName() == null)) {
/*      */                 continue;
/*      */               }
/* 1722 */               this.m_logger.debug("Processing line: " + cat.getName());
/*      */ 
/* 1725 */               String[] aSubCats = cat.getName().split("/");
/*      */ 
/* 1728 */               long lParentId = -1L;
/* 1729 */               String sFullname = null;
/*      */ 
/* 1731 */               for (int iCatIndex = 0; (aSubCats != null) && (iCatIndex < aSubCats.length); iCatIndex++)
/*      */               {
/* 1734 */                 if (sFullname != null)
/*      */                 {
/* 1736 */                   sFullname = sFullname + "/";
/*      */                 }
/*      */                 else
/*      */                 {
/* 1740 */                   sFullname = "";
/*      */                 }
/*      */ 
/* 1744 */                 sFullname = sFullname + CategoryUtil.getJSUnicodeName(aSubCats[iCatIndex]);
/*      */ 
/* 1747 */                 boolean bCatExists = false;
/* 1748 */                 Category tempCategory = null;
/* 1749 */                 String sFullCatName = "";
/* 1750 */                 Iterator itFullNameCache = hmFullnameCache.keySet().iterator();
/*      */ 
/* 1752 */                 while (itFullNameCache.hasNext())
/*      */                 {
/* 1754 */                   sFullCatName = (String)itFullNameCache.next();
/*      */ 
/* 1756 */                   if (!sFullCatName.equalsIgnoreCase(sFullname))
/*      */                     continue;
/* 1758 */                   bCatExists = true;
/* 1759 */                   tempCategory = (Category)hmFullnameCache.get(sFullCatName);
/*      */                 }
/*      */ 
/* 1765 */                 if (!bCatExists)
/*      */                 {
/* 1768 */                   tempCategory = new CategoryImpl();
/* 1769 */                   tempCategory.setName(aSubCats[iCatIndex].trim());
/*      */ 
/* 1773 */                   if (cat.getName().endsWith(aSubCats[iCatIndex].trim()))
/*      */                   {
/* 1775 */                     tempCategory.setDescription(cat.getDescription());
/*      */                   }
/*      */ 
/* 1778 */                   tempCategory.setCategoryTypeId(a_batch.getCategoryTypeId());
/* 1779 */                   tempCategory.setIsBrowsable(cat.getIsBrowsable());
/* 1780 */                   tempCategory.setExtensionEntityId(cat.getExtensionEntityId());
/*      */ 
/* 1783 */                   tempCategory.setIsRestrictive((a_batch.getCategoryTypeId() == 2L) && (lParentId <= 0L));
/*      */ 
/* 1785 */                   boolean bSuccess = false;
/*      */ 
/* 1788 */                   bSuccess = addCategory(a_dbTransaction, a_batch.getCategoryTypeId(), tempCategory, lParentId);
/*      */ 
/* 1790 */                   addMessage(a_batch.getJobId(), "Added category: " + tempCategory.getFullName() + " ID: " + tempCategory.getId());
/*      */ 
/* 1792 */                   if (((a_batch.getCategoryTypeId() == 2L) || (a_batch.getCategoryTypeId() == 1L)) && (StringUtil.stringIsPopulated(tempCategory.getExtensionEntityId())) && (Long.parseLong(tempCategory.getExtensionEntityId()) > 0L))
/*      */                   {
/* 1798 */                     long lEntityId = Long.parseLong(tempCategory.getExtensionEntityId());
/* 1799 */                     AssetEntity entity = (AssetEntity)hmEntities.get(Long.valueOf(lEntityId));
/*      */ 
/* 1801 */                     if (entity == null)
/*      */                     {
/* 1803 */                       entity = this.m_entityManager.getEntity(null, lEntityId);
/* 1804 */                       hmEntities.put(Long.valueOf(lEntityId), entity);
/*      */                     }
/* 1806 */                     alCatsNeedingExtension.put(Long.valueOf(tempCategory.getId()), entity);
/*      */                   }
/*      */ 
/* 1809 */                   if (bSuccess)
/*      */                   {
/* 1811 */                     iCatsAdded++;
/*      */                   }
/*      */ 
/* 1815 */                   hmFullnameCache.put(sFullname, tempCategory);
/*      */                 }
/*      */ 
/* 1819 */                 lParentId = tempCategory.getId();
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1826 */             bFinished = true;
/*      */           }
/*      */         }
/*      */         catch (DefaultBeanReader.BeanPopulationException e)
/*      */         {
/* 1831 */           this.m_logger.error("CategoryManager.importCategories : BeanPopulationException reading line " + (iLineNo + 1) + ": " + e.getMessage());
/*      */ 
/* 1833 */           iLineNo += 1000;
/*      */         }
/*      */       }
/* 1835 */       while (!bFinished);
/*      */ 
/* 1839 */       if ((alCatsNeedingExtension != null) && (alCatsNeedingExtension.keySet().size() > 0))
/*      */       {
/* 1841 */         addMessage(a_batch.getJobId(), "About to add extension assets for new categories");
/*      */ 
/* 1844 */         a_dbTransaction.commit();
/* 1845 */         HashMap hmItemsToCategories = new HashMap();
/*      */ 
/* 1847 */         for (Iterator i$ = alCatsNeedingExtension.keySet().iterator(); i$.hasNext(); ) { long lCatId = ((Long)i$.next()).longValue();
/*      */ 
/* 1849 */           AssetEntity entity = (AssetEntity)alCatsNeedingExtension.get(Long.valueOf(lCatId));
/*      */ 
/* 1852 */           if ((entity != null) && (entity.getIsCategoryExtension()))
/*      */           {
/* 1854 */             WorkflowUpdate update = new WorkflowUpdate(a_batch.getUserProfile().getUser().getId(), -1L);
/* 1855 */             update.setUpdateType(1);
/* 1856 */             update.setSetSubmitted(true);
/*      */ 
/* 1858 */             Asset asset = new Asset();
/* 1859 */             asset.setEntity(entity);
/*      */ 
/* 1862 */             long lAssetId = this.m_importManager.addEmptyAsset(a_batch.getUserProfile().getUser().getId(), asset, null, a_batch.getUserProfile().getSessionId(), a_batch.getUserProfile().getCurrentLanguage(), false, false, update);
/*      */ 
/* 1871 */             extendCategory(null, lCatId, a_batch.getCategoryTypeId(), asset, true);
/* 1872 */             hmItemsToCategories.put(Long.valueOf(lAssetId), Long.valueOf(lCatId));
/*      */ 
/* 1874 */             addMessage(a_batch.getJobId(), "Added extension asset with id " + lAssetId + " to category " + lCatId);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1879 */         a_dbTransaction = this.m_transactionManager.getCurrentOrNewTransaction();
/* 1880 */         addMessage(a_batch.getJobId(), "About to put extension assets in categories");
/*      */ 
/* 1882 */         for (Iterator i$ = hmItemsToCategories.keySet().iterator(); i$.hasNext(); ) { long lAssetId = ((Long)i$.next()).longValue();
/*      */ 
/* 1885 */           if (lAssetId > 0L)
/*      */           {
/* 1887 */             addItemToCategory(a_dbTransaction, lAssetId, ((Long)hmItemsToCategories.get(Long.valueOf(lAssetId))).longValue(), a_batch.getCategoryTypeId());
/*      */           }
/*      */         }
/*      */ 
/* 1891 */         addMessage(a_batch.getJobId(), "Done");
/*      */       }
/*      */       else
/*      */       {
/* 1896 */         this.m_logger.error(getClass().getSimpleName() + ".importCategories: Unable to add extension asset to category");
/*      */       }
/*      */     }
/*      */     catch (FileNotFoundException fnfe)
/*      */     {
/* 1901 */       throw new Bn2Exception("AssetImportManager.importCategories() : input data file not found: " + sFileLocation, fnfe);
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 1907 */       throw new Bn2Exception("AssetImportManager.importCategories() : IO problem with data file : " + sFileLocation, ioe);
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/* 1913 */       throw new Bn2Exception("AssetImportManager.importCategories() : Database problem when importing categories : ", se);
/*      */     }
/* 1915 */     return iCatsAdded;
/*      */   }
/*      */ 
/*      */   public void setCategoryImage(Category a_category, String a_sSourceImageFullPath, String a_sRgbColorProfile, String a_sCmykColorProfile)
/*      */     throws Bn2Exception
/*      */   {
/* 1938 */     String sDestinationThumbnail = CategorySettings.getApplicationPath() + "/" + getCategoryImageUrl(a_category.getId(), false, false);
/*      */ 
/* 1942 */     String sDestinationLogo = CategorySettings.getApplicationPath() + "/" + getCategoryImageUrl(a_category.getId(), false, true);
/*      */     try
/*      */     {
/* 1947 */       ABImageMagick.convertToCategoryImage(a_sSourceImageFullPath, sDestinationThumbnail, a_sRgbColorProfile, a_sCmykColorProfile);
/*      */ 
/* 1949 */       ABImageMagick.convertToCategoryLogo(a_sSourceImageFullPath, sDestinationLogo, a_sRgbColorProfile, a_sCmykColorProfile);
/*      */ 
/* 1951 */       a_category.setImageUrl(getCategoryImageUrl(a_category.getId(), true, false));
/*      */ 
/* 1954 */       cacheUpdateCategory(a_category.getCategoryTypeId(), a_category);
/*      */     }
/*      */     catch (ImageException ie)
/*      */     {
/* 1958 */       this.m_logger.error("CategoryManager.setCategoryImage() : could not create destination image.");
/* 1959 */       throw new Bn2Exception("CategoryManager.setCategoryImage() : could not create destination image.", ie);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetCategoryManager
 * JD-Core Version:    0.6.0
 */