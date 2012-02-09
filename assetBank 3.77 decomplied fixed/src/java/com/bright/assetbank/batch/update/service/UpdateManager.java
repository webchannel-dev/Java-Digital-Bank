/*      */ package com.bright.assetbank.batch.update.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bright.assetbank.agreements.bean.Agreement;
/*      */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*      */ import com.bright.assetbank.application.bean.AssetFileSource;
/*      */ import com.bright.assetbank.application.bean.ImageAsset;
/*      */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*      */ import com.bright.assetbank.application.service.AssetLogManager;
/*      */ import com.bright.assetbank.application.service.IAssetManager;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.batch.service.BatchQueueManager;
/*      */ import com.bright.assetbank.batch.update.bean.Batch;
/*      */ import com.bright.assetbank.batch.update.bean.BatchUpdateBatch;
/*      */ import com.bright.assetbank.batch.update.bean.BulkUpdateBatch;
/*      */ import com.bright.assetbank.batch.update.bean.BulkUpdateInfo;
/*      */ import com.bright.assetbank.batch.update.constant.UpdateSettings;
/*      */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*      */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*      */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.category.util.CategoryUtil;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.queue.bean.QueuedItem;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class UpdateManager extends BatchQueueManager
/*      */ {
/*      */   public static final String c_ksClassName = "UpdateManager";
/*   96 */   private IAssetManager m_assetManager = null;
/*      */ 
/*  102 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*      */ 
/*  108 */   private DBTransactionManager m_transactionManager = null;
/*      */ 
/*  114 */   private CategoryManager m_categoryManager = null;
/*      */ 
/*  120 */   private CategoryCountCacheManager m_categoryCountCacheManager = null;
/*      */ 
/*  126 */   private MultiLanguageSearchManager m_searchManager = null;
/*      */ 
/*  131 */   private AssetLogManager m_assetLogManager = null;
/*      */ 
/*  137 */   private AgreementsManager m_agreementsManager = null;
/*      */ 
/*  143 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*      */ 
/*  149 */   private AttributeManager m_attributeManager = null;
/*      */ 
/*  157 */   private HashMap m_hmAssetIds = new HashMap();
/*  158 */   private HashMap m_hmBatchUpdatesInProgress = new HashMap();
/*      */ 
/*      */   public void setAssetManager(IAssetManager a_sAssetManager)
/*      */   {
/*   99 */     this.m_assetManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*      */   {
/*  105 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/*  111 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(CategoryManager a_categoryManager)
/*      */   {
/*  117 */     this.m_categoryManager = a_categoryManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_manager)
/*      */   {
/*  123 */     this.m_categoryCountCacheManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/*  129 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*      */   {
/*  134 */     this.m_assetLogManager = a_assetLogManager;
/*      */   }
/*      */ 
/*      */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*      */   {
/*  140 */     this.m_agreementsManager = a_agreementsManager;
/*      */   }
/*      */ 
/*      */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWorkflowManager)
/*      */   {
/*  146 */     this.m_assetWorkflowManager = a_assetWorkflowManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager a_attributeManager)
/*      */   {
/*  152 */     this.m_attributeManager = a_attributeManager;
/*      */   }
/*      */ 
/*      */   public BatchUpdateControllerImpl createNewBatchUpdateController()
/*      */   {
/*  168 */     BatchUpdateControllerImpl controller = new BatchUpdateControllerImpl(this);
/*  169 */     return controller;
/*      */   }
/*      */ 
/*      */   public BulkUpdateControllerImpl createNewBulkUpdateController()
/*      */   {
/*  180 */     BulkUpdateControllerImpl controller = new BulkUpdateControllerImpl(this);
/*  181 */     return controller;
/*      */   }
/*      */ 
/*      */   Batch startNewBatchUpdate(Vector a_vecAssets, long a_lUserId, boolean a_bLockAssets)
/*      */   {
/*  198 */     cancelExistingBatchForUser(a_lUserId);
/*      */ 
/*  201 */     BatchUpdateBatch batchUpdate = new BatchUpdateBatch();
/*  202 */     batchUpdate.setImages(new Vector());
/*  203 */     batchUpdate.setUserId(a_lUserId);
/*      */ 
/*  206 */     addToBatchUpdate(batchUpdate, a_vecAssets, a_bLockAssets);
/*      */ 
/*  209 */     startNewBatchUpdateForUser(a_lUserId, batchUpdate);
/*      */ 
/*  211 */     return batchUpdate;
/*      */   }
/*      */ 
/*      */   Batch startNewBulkUpdate(Vector a_vecAssets, long a_lUserId)
/*      */   {
/*  225 */     cancelExistingBatchForUser(a_lUserId);
/*      */ 
/*  228 */     BulkUpdateBatch batchUpdate = new BulkUpdateBatch();
/*  229 */     batchUpdate.setImages(new Vector());
/*  230 */     batchUpdate.setUserId(a_lUserId);
/*      */ 
/*  232 */     boolean bAssetsLocked = addToBulkUpdate(batchUpdate, a_vecAssets);
/*      */ 
/*  234 */     if (!bAssetsLocked)
/*      */     {
/*  236 */       startNewBatchUpdateForUser(a_lUserId, batchUpdate);
/*      */     }
/*      */ 
/*  239 */     return batchUpdate;
/*      */   }
/*      */ 
/*      */   private void cancelExistingBatchForUser(long a_lUserId)
/*      */   {
/*  251 */     if (this.m_hmBatchUpdatesInProgress.containsKey(new Long(a_lUserId)))
/*      */     {
/*  253 */       Batch existing = (Batch)this.m_hmBatchUpdatesInProgress.get(new Long(a_lUserId));
/*      */ 
/*  256 */       cancelBatchUpdate(existing);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void startNewBatchUpdateForUser(long a_lUserId, Batch a_batch)
/*      */   {
/*  269 */     synchronized (this.m_hmBatchUpdatesInProgress)
/*      */     {
/*  272 */       this.m_hmBatchUpdatesInProgress.put(new Long(a_lUserId), a_batch);
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean addToBatchUpdate(BatchUpdateBatch a_batchUpdate, Vector a_vecAssets, boolean a_bLockAssets)
/*      */   {
/*  289 */     int iMaxInBatch = UpdateSettings.getMaxBatchUpdateResults();
/*      */ 
/*  291 */     return addToBatchUpdate(a_batchUpdate, a_vecAssets, false, a_bLockAssets, iMaxInBatch);
/*      */   }
/*      */ 
/*      */   boolean addToBulkUpdate(BulkUpdateBatch a_batchUpdate, Vector a_vecAssets)
/*      */   {
/*  304 */     int iMaxInBatch = UpdateSettings.getMaxBulkUpdateResults();
/*  305 */     boolean bAssetsAreLocked = addToBatchUpdate(a_batchUpdate, a_vecAssets, true, true, iMaxInBatch);
/*      */ 
/*  308 */     if (a_vecAssets != null)
/*      */     {
/*  310 */       Iterator it = a_vecAssets.iterator();
/*  311 */       while (it.hasNext())
/*      */       {
/*  313 */         Object obj = it.next();
/*  314 */         long lId = a_batchUpdate.getIdFromBatchObject(obj);
/*  315 */         a_batchUpdate.addAssetToUpdate(lId);
/*      */       }
/*      */     }
/*      */ 
/*  319 */     return bAssetsAreLocked;
/*      */   }
/*      */ 
/*      */   private boolean addToBatchUpdate(Batch a_batchUpdate, Vector a_vecAssets, boolean a_bAbortIfAssetsLocked, boolean a_bLockAssets, int iMaxInBatch)
/*      */   {
/*  343 */     Object obj = null;
/*  344 */     Long olId = null;
/*      */ 
/*  346 */     int iNumAssetsLocked = 0;
/*  347 */     Vector vecAssetsToUpdate = new Vector();
/*      */ 
/*  349 */     if ((a_vecAssets != null) && (a_vecAssets.size() > 0))
/*      */     {
/*  351 */       synchronized (this.m_hmAssetIds)
/*      */       {
/*  355 */         for (int i = 0; i < a_vecAssets.size(); i++)
/*      */         {
/*  358 */           obj = a_vecAssets.get(i);
/*  359 */           olId = getLongId(a_batchUpdate, obj);
/*      */ 
/*  362 */           if (this.m_hmAssetIds.containsKey(olId))
/*      */           {
/*  364 */             iNumAssetsLocked++;
/*      */           }
/*      */           else
/*      */           {
/*  368 */             vecAssetsToUpdate.add(obj);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  375 */         int iAddedToBatch = 0;
/*  376 */         if ((iNumAssetsLocked == 0) || (!a_bAbortIfAssetsLocked))
/*      */         {
/*  379 */           for (int i = 0; (i < vecAssetsToUpdate.size()) && (iAddedToBatch < iMaxInBatch); i++)
/*      */           {
/*  381 */             obj = vecAssetsToUpdate.get(i);
/*  382 */             a_batchUpdate.getImages().add(obj);
/*      */ 
/*  385 */             if (a_bLockAssets)
/*      */             {
/*  387 */               olId = getLongId(a_batchUpdate, obj);
/*  388 */               this.m_hmAssetIds.put(olId, null);
/*      */             }
/*      */ 
/*  391 */             iAddedToBatch++;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  398 */     a_batchUpdate.setNumLockedAssetsInBatch(iNumAssetsLocked);
/*      */ 
/*  400 */     return iNumAssetsLocked > 0;
/*      */   }
/*      */ 
/*      */   private Long getLongId(Batch a_batchUpdate, Object a_obj)
/*      */   {
/*  413 */     long lId = a_batchUpdate.getIdFromBatchObject(a_obj);
/*  414 */     Long olId = new Long(lId);
/*  415 */     return olId;
/*      */   }
/*      */ 
/*      */   void freeAsset(long a_lAssetId)
/*      */   {
/*  431 */     synchronized (this.m_hmAssetIds)
/*      */     {
/*  433 */       this.m_hmAssetIds.remove(new Long(a_lAssetId));
/*      */     }
/*      */   }
/*      */ 
/*      */   void cancelBatchUpdate(Batch a_batchUpdate)
/*      */   {
/*  450 */     Object obj = null;
/*  451 */     Long olId = null;
/*      */ 
/*  454 */     Vector vecAssets = a_batchUpdate.getImages();
/*      */ 
/*  457 */     for (int i = 0; i < vecAssets.size(); i++)
/*      */     {
/*  459 */       obj = vecAssets.get(i);
/*  460 */       olId = getLongId(a_batchUpdate, obj);
/*      */ 
/*  462 */       freeAsset(olId.longValue());
/*      */     }
/*      */ 
/*  466 */     synchronized (this.m_hmBatchUpdatesInProgress)
/*      */     {
/*  468 */       this.m_hmBatchUpdatesInProgress.remove(new Long(a_batchUpdate.getUserId()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public int queueBulkUpdate(BulkUpdateInfo a_bulkInfo)
/*      */   {
/*  486 */     startBatchProcess(a_bulkInfo.getUser().getId());
/*      */ 
/*  489 */     return queueItem(a_bulkInfo);
/*      */   }
/*      */ 
/*      */   public void processQueueItem(QueuedItem a_queuedItem)
/*      */     throws Bn2Exception
/*      */   {
/*  510 */     String ksMethodName = "processQueueItem";
/*      */ 
/*  512 */     DBTransaction dbTransaction = null;
/*  513 */     BulkUpdateInfo batchInfo = null;
/*      */     try
/*      */     {
/*  518 */       batchInfo = (BulkUpdateInfo)a_queuedItem;
/*      */ 
/*  520 */       if (batchInfo != null)
/*      */       {
/*  523 */         addMessage(batchInfo.getUser().getId(), "Starting bulk update");
/*      */ 
/*  526 */         this.m_assetWorkflowManager.startAssetUploadAlertBatch(batchInfo.getUser().getId());
/*      */ 
/*  528 */         BulkUpdateBatch batch = (BulkUpdateBatch)batchInfo.getBatchUpdate();
/*  529 */         Vector vecAssets = batch.getImages();
/*  530 */         Iterator it = vecAssets.iterator();
/*      */ 
/*  532 */         while (it.hasNext())
/*      */         {
/*  534 */           Object obj = it.next();
/*  535 */           long lId = batch.getIdFromBatchObject(obj);
/*      */ 
/*  538 */           if (batch.getAssetIsToUpdate(lId))
/*      */           {
/*  541 */             dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/*  544 */             if (batchInfo.getDeleteAssets())
/*      */             {
/*  547 */               Asset asset = this.m_assetManager.getAsset(dbTransaction, lId, null, false, false);
/*      */ 
/*  549 */               addMessage(batchInfo.getUser().getId(), "Deleting asset: " + lId);
/*      */ 
/*  552 */               if (this.m_assetManager.userCanDeleteAsset(batchInfo.getUserProfile(), asset))
/*      */               {
/*  554 */                 this.m_assetManager.deleteAsset(null, lId);
/*      */                 try
/*      */                 {
/*  558 */                   dbTransaction.commit();
/*      */                 }
/*      */                 catch (SQLException sqle)
/*      */                 {
/*  562 */                   throw new Bn2Exception(sqle.getMessage(), sqle);
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/*  567 */                 addMessage(batchInfo.getUser().getId(), "You do not have permission to delete asset: " + lId);
/*      */               }
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  573 */               Asset asset = this.m_assetManager.getAsset(dbTransaction, lId, null, false, false);
/*      */ 
/*  575 */               boolean skip = false;
/*  576 */               if (!this.m_assetManager.userCanUpdateAsset(batchInfo.getUserProfile(), asset))
/*      */               {
/*  578 */                 skip = true;
/*  579 */                 addMessage(batchInfo.getUser().getId(), "You do not have permission to update asset: " + lId + "(" + asset.getSearchName() + ")");
/*      */               }
/*  581 */               else if (asset.getExtendsCategory().getId() > 0L)
/*      */               {
/*  583 */                 skip = true;
/*  584 */                 addMessage(batchInfo.getUser().getId(), "Skipping asset: " + lId + " because it is an extension asset");
/*      */               }
/*      */ 
/*  587 */               if (!skip)
/*      */               {
/*  591 */                 Asset originalAsset = null;
/*  592 */                 if (AssetBankSettings.getAuditLogEnabled())
/*      */                 {
/*  594 */                   originalAsset = this.m_assetManager.getAsset(dbTransaction, lId, null, false, false);
/*      */                 }
/*      */ 
/*  597 */                 addMessage(batchInfo.getUser().getId(), "Updating asset: " + lId);
/*  598 */                 asset = updateAssetMetadata(dbTransaction, asset, batchInfo);
/*      */ 
/*  601 */                 asset.setDateLastModified(new Date());
/*  602 */                 asset.setLastModifiedByUser(batchInfo.getUser());
/*      */ 
/*  605 */                 AssetConversionInfo conversionInfo = null;
/*      */ 
/*  607 */                 if ((asset.getIsImage()) && (batchInfo.getRotateImagesAngle() != 0))
/*      */                 {
/*  610 */                   ((ImageAsset)asset).setLargeImageFile(null);
/*  611 */                   ((ImageAsset)asset).setUnwatermarkedLargeImageFile(null);
/*  612 */                   ImageConversionInfo imageConversionInfo = new ImageConversionInfo();
/*  613 */                   imageConversionInfo.setRotationAngle(batchInfo.getRotateImagesAngle());
/*  614 */                   conversionInfo = imageConversionInfo;
/*      */                 }
/*      */ 
/*  618 */                 if (batchInfo.getUpdateAgreement())
/*      */                 {
/*  620 */                   asset.getAgreement().setId(batchInfo.getAssetMetadata().getAgreement().getId());
/*  621 */                   this.m_agreementsManager.processAgreementsBeforeSave(dbTransaction, asset);
/*      */                 }
/*      */ 
/*  625 */                 if (batchInfo.getUnrelateAssets())
/*      */                 {
/*  627 */                   this.m_assetRelationshipManager.saveRelatedAssetIds(dbTransaction, asset, "", 1L, false);
/*      */                 }
/*      */ 
/*  631 */                 AssetFileSource substituteFileSource = null;
/*      */                 try
/*      */                 {
/*  635 */                   if ((asset.getTypeId() != 2L) && (batchInfo.getSubstituteFile() != null))
/*      */                   {
/*  637 */                     substituteFileSource = new AssetFileSource(batchInfo.getSubstituteFile());
/*      */                   }
/*      */ 
/*      */                 }
/*      */                 catch (IOException io)
/*      */                 {
/*  644 */                   this.m_logger.error("Exception when trying to add new thumbnail on bulk update:", io);
/*      */                 }
/*      */ 
/*      */                 try
/*      */                 {
/*  650 */                   this.m_assetManager.saveAsset(dbTransaction, asset, null, batchInfo.getUser().getId(), conversionInfo, substituteFileSource, false, 0, batchInfo.getWorkflowUpdate());
/*      */                 }
/*      */                 finally
/*      */                 {
/*  655 */                   if (substituteFileSource != null)
/*      */                   {
/*      */                     try
/*      */                     {
/*  659 */                       substituteFileSource.close();
/*      */                     }
/*      */                     catch (IOException io)
/*      */                     {
/*  663 */                       this.m_logger.error("Exception when trying to add new thumbnail on bulk update:", io);
/*      */                     }
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/*  669 */                 if (batchInfo.getUpdateAgreement())
/*      */                 {
/*  671 */                   asset.getAgreement().setId(batchInfo.getAssetMetadata().getAgreement().getId());
/*  672 */                   this.m_agreementsManager.processAgreementAfterSave(dbTransaction, asset, batchInfo.getUser().getId());
/*      */                 }
/*      */ 
/*      */                 try
/*      */                 {
/*  678 */                   dbTransaction.commit();
/*  679 */                   dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */                 }
/*      */                 catch (SQLException sqle)
/*      */                 {
/*  683 */                   throw new Bn2Exception(sqle.getMessage());
/*      */                 }
/*      */ 
/*  687 */                 asset = this.m_assetManager.getAsset(dbTransaction, lId, null, true, true);
/*      */ 
/*  690 */                 if (AssetBankSettings.getAuditLogEnabled())
/*      */                 {
/*  692 */                   this.m_assetLogManager.generateChangeLogEntry(originalAsset, asset, dbTransaction, batchInfo.getUser(), batchInfo.getSessionId());
/*      */                 }
/*      */ 
/*  699 */                 if ((StringUtils.isNotEmpty(asset.getChildAssetIdsAsString())) && ((AssetBankSettings.getRestrictChildAssetsWithParent()) || (AssetBankSettings.getIncludeParentMetadataForSearch())))
/*      */                 {
/*  703 */                   reindexRelatedAssets(dbTransaction, asset, asset.getChildAssetIdsAsString());
/*      */                 }
/*      */ 
/*      */                 try
/*      */                 {
/*  708 */                   dbTransaction.commit();
/*      */                 }
/*      */                 catch (SQLException sqle)
/*      */                 {
/*  712 */                   throw new Bn2Exception(sqle.getMessage());
/*      */                 }
/*      */               }
/*      */ 
/*  716 */               batch.addAssetUpdated(lId);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  722 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*  723 */         this.m_assetWorkflowManager.sendAssetUploadAlertBatch(dbTransaction, batchInfo.getUser().getId());
/*  724 */         this.m_assetWorkflowManager.endAssetUploadAlertBatch(batchInfo.getUser().getId());
/*      */ 
/*  726 */         addMessage(batchInfo.getUser().getId(), "Finished bulk update");
/*      */       }
/*      */     }
/*      */     catch (Bn2Exception bn2e)
/*      */     {
/*  731 */       this.m_logger.error("UpdateManager.processQueueItem - exception:", bn2e);
/*      */ 
/*  733 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  737 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/*  741 */           this.m_logger.error("Exception rolling back transaction in UpdateManager:" + e2.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  746 */       throw bn2e;
/*      */     }
/*      */     catch (RuntimeException e)
/*      */     {
/*  750 */       this.m_logger.error("UpdateManager.processQueueItem", e);
/*      */ 
/*  752 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  756 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/*  760 */           this.m_logger.error("Exception rolling back transaction in UpdateManager:" + e2.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  765 */       throw e;
/*      */     }
/*      */     finally
/*      */     {
/*  769 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  773 */           dbTransaction.commit();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/*  777 */           this.m_logger.error("Exception committing transaction in UpdateManager:" + e2.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  782 */       if (batchInfo != null)
/*      */       {
/*  784 */         endBatchProcess(batchInfo.getUser().getId());
/*  785 */         cancelBatchUpdate(batchInfo.getBatchUpdate());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void reindexRelatedAssets(DBTransaction a_dbTransaction, Asset a_asset, String a_sRelatedAssetIds)
/*      */     throws AssetNotFoundException, Bn2Exception
/*      */   {
/*  801 */     String[] sIds = a_sRelatedAssetIds.split("[ ,]+");
/*      */ 
/*  803 */     for (int i = 0; i < sIds.length; i++)
/*      */     {
/*      */       try
/*      */       {
/*  807 */         if (StringUtils.isNotEmpty(sIds[i]))
/*      */         {
/*  809 */           Asset asset = this.m_assetManager.getAsset(a_dbTransaction, Long.parseLong(sIds[i]), null, true, true);
/*  810 */           this.m_searchManager.indexDocument(asset, true);
/*      */         }
/*      */       }
/*      */       catch (NumberFormatException e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Asset updateAssetMetadata(DBTransaction a_dbTransaction, Asset a_asset, BulkUpdateInfo a_info)
/*      */     throws Bn2Exception
/*      */   {
/*  841 */     Asset metadata = a_info.getAssetMetadata();
/*  842 */     Map hmAttributesToReplace = a_info.getAttributesToReplace();
/*      */ 
/*  847 */     if ((metadata.getDateAdded() != null) && (hmAttributesToReplace.containsKey("dateAdded")))
/*      */     {
/*  849 */       a_asset.setDateAdded(metadata.getDateAdded());
/*      */     }
/*  851 */     if (hmAttributesToReplace.containsKey("price"))
/*      */     {
/*  853 */       a_asset.setPrice(metadata.getPrice());
/*      */     }
/*  855 */     if (hmAttributesToReplace.containsKey("orientation"))
/*      */     {
/*  857 */       a_asset.setOrientation(metadata.getOrientation());
/*      */     }
/*  859 */     if (hmAttributesToReplace.containsKey("promoted"))
/*      */     {
/*  861 */       a_asset.setIsPromoted(metadata.getIsPromoted());
/*      */     }
/*  863 */     if (hmAttributesToReplace.containsKey("advancedViewing"))
/*      */     {
/*  865 */       a_asset.setAdvancedViewing(metadata.getAdvancedViewing());
/*      */     }
/*  867 */     if (hmAttributesToReplace.containsKey("featured"))
/*      */     {
/*  869 */       a_asset.setIsFeatured(metadata.getIsFeatured());
/*  870 */       a_asset.setFeaturedInBrandsList(metadata.getFeaturedInBrandsList());
/*      */     }
/*  872 */     if (hmAttributesToReplace.containsKey("previewrestricted"))
/*      */     {
/*  874 */       a_asset.setIsRestricted(metadata.getIsRestricted());
/*      */     }
/*  876 */     if (hmAttributesToReplace.containsKey("sensitive"))
/*      */     {
/*  878 */       a_asset.setIsSensitive(metadata.getIsSensitive());
/*      */     }
/*  880 */     if (hmAttributesToReplace.containsKey("agreements"))
/*      */     {
/*  882 */       a_asset.setAgreementTypeId(metadata.getAgreementTypeId());
/*      */     }
/*      */ 
/*  890 */     Vector vecExistingAttributes = a_asset.getAttributeValues();
/*  891 */     Vector vecNewAttributes = metadata.getAttributeValues();
/*      */ 
/*  894 */     updateFlexibleAttributes(vecExistingAttributes, vecNewAttributes, hmAttributesToReplace, a_info.getAttributeDelimiters(), false, false, false);
/*      */ 
/*  904 */     updateFlexibleAttributes(vecExistingAttributes, vecNewAttributes, a_info.getAttributesToAppend(), a_info.getAttributeDelimiters(), true, false, false);
/*      */ 
/*  914 */     updateFlexibleAttributes(vecExistingAttributes, vecNewAttributes, a_info.getAttributesToPrepend(), a_info.getAttributeDelimiters(), false, true, false);
/*      */ 
/*  924 */     updateFlexibleAttributes(vecExistingAttributes, vecNewAttributes, a_info.getAttributesToRemove(), a_info.getAttributeDelimiters(), false, false, true);
/*      */ 
/*  933 */     updateCategories(a_dbTransaction, a_asset, a_info);
/*      */ 
/*  935 */     return a_asset;
/*      */   }
/*      */ 
/*      */   private void updateFlexibleAttributes(Vector a_vecExistingAttributes, Vector a_vecNewAttributes, Map<String, String> a_hmAttributesToUpdate, Map<String, String> a_hmAttributeDelimiters, boolean a_bAppend, boolean a_bPrepend, boolean a_bRemove)
/*      */   {
/*  954 */     Iterator itAttributesToUpdate = a_hmAttributesToUpdate.values().iterator();
/*  955 */     while (itAttributesToUpdate.hasNext())
/*      */     {
/*  957 */       String sAttributeKey = (String)itAttributesToUpdate.next();
/*      */ 
/*  960 */       if (StringUtil.stringIsInteger(sAttributeKey))
/*      */       {
/*  962 */         long lAttributeId = Long.parseLong(sAttributeKey);
/*      */ 
/*  965 */         if (!a_bRemove)
/*      */         {
/*  968 */           Iterator itExisting = a_vecExistingAttributes.iterator();
/*  969 */           Vector vecValuesToAdd = null;
/*      */ 
/*  971 */           while (itExisting.hasNext())
/*      */           {
/*  973 */             AttributeValue avExisting = (AttributeValue)itExisting.next();
/*      */ 
/*  975 */             if (avExisting.getAttribute().getId() == lAttributeId)
/*      */             {
/*  977 */               itExisting.remove();
/*      */ 
/*  980 */               if ((a_bAppend) || (a_bPrepend))
/*      */               {
/*  982 */                 if (vecValuesToAdd == null)
/*      */                 {
/*  984 */                   vecValuesToAdd = new Vector();
/*      */                 }
/*  986 */                 vecValuesToAdd.add(avExisting);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  994 */           Iterator itNew = a_vecNewAttributes.iterator();
/*  995 */           while (itNew.hasNext())
/*      */           {
/*  997 */             AttributeValue avNew = (AttributeValue)itNew.next();
/*      */ 
/*  999 */             if (avNew.getAttribute().getId() == lAttributeId)
/*      */             {
/* 1002 */               if (vecValuesToAdd != null)
/*      */               {
/* 1004 */                 AttributeValue firstVal = (AttributeValue)vecValuesToAdd.firstElement();
/*      */ 
/* 1006 */                 if ((firstVal.getAttribute().getIsCheckList()) || (firstVal.getAttribute().getIsOptionList()))
/*      */                 {
/* 1009 */                   a_vecExistingAttributes.add(avNew);
/*      */                 }
/*      */                 else
/*      */                 {
/* 1014 */                   String sDelimiter = (String)a_hmAttributeDelimiters.get(sAttributeKey);
/*      */ 
/* 1017 */                   if (!StringUtil.stringIsPopulated(firstVal.getValue()))
/*      */                   {
/* 1019 */                     firstVal.setValue(avNew.getValue());
/*      */                   }
/* 1021 */                   else if (a_bAppend)
/*      */                   {
/* 1023 */                     firstVal.setValue(firstVal.getValue() + sDelimiter + avNew.getValue());
/*      */                   }
/*      */                   else
/*      */                   {
/* 1027 */                     firstVal.setValue(avNew.getValue() + sDelimiter + firstVal.getValue());
/*      */                   }
/*      */ 
/* 1031 */                   if (firstVal.getKeywordCategories() != null)
/*      */                   {
/* 1033 */                     firstVal.getKeywordCategories().addAll(avNew.getKeywordCategories());
/*      */                   }
/*      */ 
/* 1037 */                   Iterator itTranslations = firstVal.getTranslations().iterator();
/* 1038 */                   while (itTranslations.hasNext())
/*      */                   {
/* 1040 */                     AttributeValue.Translation translation = (AttributeValue.Translation)itTranslations.next();
/* 1041 */                     String sTranslation = avNew.getValueForLanguage(translation.getLanguage());
/* 1042 */                     if (StringUtils.isNotEmpty(sTranslation))
/*      */                     {
/* 1044 */                       String sNewValue = StringUtils.isNotEmpty(translation.getValue()) ? translation.getValue() : "";
/* 1045 */                       if (a_bAppend)
/* 1046 */                         translation.setValue(sNewValue + sDelimiter + avNew.getValueForLanguage(translation.getLanguage()));
/*      */                       else {
/* 1048 */                         translation.setValue(avNew.getValueForLanguage(translation.getLanguage()) + sNewValue + sDelimiter);
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/* 1056 */                 vecValuesToAdd = new Vector();
/* 1057 */                 vecValuesToAdd.add(avNew);
/*      */               }
/* 1059 */               a_vecExistingAttributes.addAll(vecValuesToAdd);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1066 */           Vector vecKeywordsToRemove = new Vector();
/*      */ 
/* 1069 */           Iterator itNew = a_vecNewAttributes.iterator();
/* 1070 */           while (itNew.hasNext())
/*      */           {
/* 1072 */             AttributeValue avToRemove = (AttributeValue)itNew.next();
/*      */ 
/* 1074 */             if (avToRemove.getAttribute().getId() == lAttributeId)
/*      */             {
/* 1076 */               vecKeywordsToRemove = avToRemove.getKeywordCategories();
/* 1077 */               break;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1082 */           Iterator itExisting = a_vecExistingAttributes.iterator();
/*      */ 
/* 1084 */           while (itExisting.hasNext())
/*      */           {
/* 1086 */             AttributeValue avExisting = (AttributeValue)itExisting.next();
/*      */ 
/* 1088 */             if (avExisting.getAttribute().getId() == lAttributeId)
/*      */             {
/* 1091 */               avExisting.getKeywordCategories().removeAll(vecKeywordsToRemove);
/* 1092 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateCategories(DBTransaction a_dbTransaction, Asset a_asset, BulkUpdateInfo a_info)
/*      */     throws Bn2Exception
/*      */   {
/* 1104 */     Asset metadata = a_info.getAssetMetadata();
/*      */ 
/* 1107 */     Vector newPermissionCategories = metadata.getPermissionCategories();
/* 1108 */     if (newPermissionCategories == null)
/*      */     {
/* 1110 */       newPermissionCategories = new Vector();
/*      */     }
/*      */ 
/* 1113 */     if (a_info.getAppendAccessLevels())
/*      */     {
/* 1115 */       a_asset.getPermissionCategories().addAll(newPermissionCategories);
/*      */     }
/* 1117 */     else if (a_info.getReplaceAccessLevels())
/*      */     {
/* 1120 */       if (newPermissionCategories.isEmpty())
/*      */       {
/* 1122 */         addMessage(a_info.getUser().getId(), "Access levels for this asset cannot be updated because an asset must be in at least one access level");
/*      */       }
/*      */       else
/*      */       {
/* 1126 */         a_asset.setPermissionCategories(newPermissionCategories);
/*      */       }
/*      */     }
/* 1129 */     else if (a_info.getRemoveAccessLevels())
/*      */     {
/* 1132 */       Vector vecPermissionCategories = (Vector)a_asset.getPermissionCategories().clone();
/*      */ 
/* 1135 */       a_asset.getPermissionCategories().removeAll(newPermissionCategories);
/* 1136 */       if (a_asset.getPermissionCategories().size() == 0)
/*      */       {
/* 1138 */         addMessage(a_info.getUser().getId(), "Access levels for this asset cannot be updated because an asset must be in at least one access level");
/* 1139 */         a_asset.setPermissionCategories(vecPermissionCategories);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1144 */     if ((!a_info.getUserProfile().getIsAdmin()) && ((a_info.getReplaceAccessLevels()) || (a_info.getRemoveAccessLevels())))
/*      */     {
/* 1147 */       CategoryUtil.addInvisibleCategoryIds(this.m_categoryManager.getCategoriesForItem(a_dbTransaction, 2L, a_asset.getId()), a_info.getUserProfile().getUpdateableOrUpdateableWithApprovalCategoryIds(), a_asset.getPermissionCategories(), false);
/*      */     }
/*      */ 
/* 1154 */     Attribute catAtt = this.m_attributeManager.getStaticAttribute("categories");
/*      */ 
/* 1157 */     Vector newDescriptiveCategories = metadata.getDescriptiveCategories();
/* 1158 */     if (newDescriptiveCategories == null) newDescriptiveCategories = new Vector();
/*      */ 
/* 1161 */     if (a_info.getAppendCategories())
/*      */     {
/* 1163 */       a_asset.getDescriptiveCategories().addAll(newDescriptiveCategories);
/*      */     }
/* 1165 */     else if (a_info.getReplaceCategories())
/*      */     {
/* 1167 */       if ((catAtt.isMandatory()) && (newDescriptiveCategories.isEmpty()))
/*      */       {
/* 1169 */         addMessage(a_info.getUser().getId(), "Categories for this asset cannot be updated because an asset must be in at least one category");
/*      */       }
/*      */       else
/*      */       {
/* 1173 */         a_asset.setDescriptiveCategories(newDescriptiveCategories);
/*      */       }
/*      */     }
/* 1176 */     else if (a_info.getRemoveCategories())
/*      */     {
/* 1179 */       Vector vecDescriptiveCategories = (Vector)a_asset.getDescriptiveCategories().clone();
/*      */ 
/* 1182 */       a_asset.getDescriptiveCategories().removeAll(newDescriptiveCategories);
/*      */ 
/* 1184 */       if ((catAtt.isMandatory()) && (a_asset.getDescriptiveCategories().size() == 0))
/*      */       {
/* 1186 */         addMessage(a_info.getUser().getId(), "Categories for this asset cannot be updated because an asset must be in at least one category");
/* 1187 */         a_asset.setDescriptiveCategories(vecDescriptiveCategories);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1192 */     if ((a_info.getUserProfile().getRootDescriptiveCategoryIds().size() > 0) && ((a_info.getReplaceCategories()) || (a_info.getRemoveCategories())))
/*      */     {
/* 1195 */       CategoryUtil.addInvisibleCategoryIds(this.m_categoryManager.getCategoriesForItem(a_dbTransaction, 1L, a_asset.getId()), a_info.getUserProfile().getRootDescriptiveCategoryIds(), a_asset.getDescriptiveCategories(), true);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.service.UpdateManager
 * JD-Core Version:    0.6.0
 */