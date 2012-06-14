/*      */ package com.bright.assetbank.synchronise.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bright.assetbank.agreements.bean.Agreement;
/*      */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetFileSource;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*      */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*      */ import com.bright.assetbank.application.service.AssetLogManager;
/*      */ import com.bright.assetbank.application.service.AssetManager;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*      */ import com.bright.assetbank.batch.service.BatchQueueManager;
/*      */ import com.bright.assetbank.batch.update.bean.MetadataImportInfo;
/*      */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.entity.exception.InvalidRelationshipException;
/*      */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*      */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.synchronise.bean.AssetBeanReader;
/*      */ import com.bright.assetbank.synchronise.bean.ExternalAssetImpl;
/*      */ import com.bright.assetbank.synchronise.bean.ImportResult;
/*      */ import com.bright.assetbank.synchronise.bean.XMLImportAsset;
/*      */ import com.bright.assetbank.synchronise.constant.ExportConstants;
/*      */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.file.BeanReader;
/*      */ import com.bright.framework.file.BeanWrapper;
import com.bright.framework.file.DefaultBeanReader;
/*      */ import com.bright.framework.file.DefaultBeanReader.BeanPopulationException;
/*      */ import com.bright.framework.file.DefaultBeanReader.TooManyColumnsException;
/*      */ import com.bright.framework.file.ExcelFormat;
/*      */ import com.bright.framework.file.FileFormat;
/*      */ import com.bright.framework.queue.bean.MessageBatchMonitor;
/*      */ import com.bright.framework.queue.bean.QueuedItem;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.util.Counter;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.collections.CollectionUtils;
/*      */ import org.apache.commons.io.FilenameUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AssetImportManager extends BatchQueueManager
/*      */   implements ExportConstants, AssetBankConstants
/*      */ {
/*      */   private static final String c_ksClassName = "AssetImportManager";
/*      */   private static final int k_iNumAssetsPerFileRead = 100;
/*      */   private static final int k_iNumHeaderLinesToIgnore = 0;
/*  111 */   private AssetManager m_assetManager = null;
/*  112 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*  113 */   private FileStoreManager m_fileStoreManager = null;
/*  114 */   private DBTransactionManager m_transactionManager = null;
/*  115 */   private AssetCategoryManager m_categoryManager = null;
/*  116 */   private MultiLanguageSearchManager m_searchManager = null;
/*  117 */   private TaxonomyManager m_taxonomyManager = null;
/*  118 */   private ABUserManager m_userManager = null;
/*  119 */   private CategoryCountCacheManager m_categoryCountCacheManager = null;
/*  120 */   private AssetLogManager m_assetLogManager = null;
/*  121 */   private AgreementsManager m_agreementsManager = null;
/*  122 */   private AssetEntityRelationshipManager m_entityRelationshipManager = null;
/*      */ 
/*      */   public void setAssetEntityRelationshipManager(AssetEntityRelationshipManager a_entityRelationshipManager)
/*      */   {
/*  126 */     this.m_entityRelationshipManager = a_entityRelationshipManager;
/*      */   }
/*      */ 
/*      */   public int checkImportData(MetadataImportInfo a_importInfo)
/*      */     throws Bn2Exception
/*      */   {
/*  144 */     int iResult = 0;
/*      */     try
/*      */     {
/*  147 */       this.m_batchMonitor.startBatchProcess(a_importInfo.getUser().getId());
/*      */ 
/*  149 */       iResult = importAssetData(a_importInfo.getFileUrl(), a_importInfo.getUser().getId(), true, a_importInfo.getAddMissingAssets(), a_importInfo.getAddMissingCategories(), a_importInfo.getSessionId(), false, true);
/*      */     }
/*      */     finally
/*      */     {
/*  158 */       this.m_batchMonitor.endBatchProcess(a_importInfo.getUser().getId());
/*      */     }
/*  160 */     return iResult;
/*      */   }
/*      */ 
/*      */   public int queueCheck(MetadataImportInfo a_importInfo)
/*      */   {
/*  177 */     startBatchProcess(a_importInfo.getUser().getId());
/*      */ 
/*  180 */     return queueItem(a_importInfo);
/*      */   }
/*      */ 
/*      */   public int queueImport(MetadataImportInfo a_importInfo)
/*      */   {
/*  197 */     startBatchProcess(a_importInfo.getUser().getId());
/*      */ 
/*  200 */     return queueItem(a_importInfo);
/*      */   }
/*      */ 
/*      */   public int importAssetData(String a_sFileLocation, long a_lUserId, boolean a_bCheckOnly, boolean a_bCreateMissingAssets, boolean a_bCreateMissingCategories, long a_lSessionId, boolean a_bSynch, boolean a_bRemoveFromExistingCategories)
/*      */     throws Bn2Exception
/*      */   {
/*  228 */     String sFileLocation = a_sFileLocation;
/*      */ 
/*  233 */     Counter lineCounter = new Counter(2);
/*      */ 
/*  235 */     if (!a_bSynch)
/*      */     {
/*  237 */       sFileLocation = this.m_fileStoreManager.getAbsolutePath(a_sFileLocation);
/*      */     }String sImportedAssetIdFormat;
/*      */     AssetBeanReader assetReader;
/*      */     try { BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sFileLocation), "UTF-8"));
/*  243 */       FileFormat format = new ExcelFormat();
/*  244 */       CategoryHandler categoryHandler = new CategoryNameOrIdHandler(this.m_transactionManager, this.m_categoryManager, this.m_batchMonitor, lineCounter, a_lUserId, a_bCheckOnly, a_bCreateMissingCategories);
/*      */ 
/*  247 */       BeanWrapper wrapper = new ExternalAssetImpl(categoryHandler);
/*      */ 
/*  249 */       sImportedAssetIdFormat = getImportedAssetIdFormat(reader, format);
/*      */ 
/*  251 */       assetReader = new AssetBeanReader(reader, format, Asset.class, wrapper, 0, a_bCheckOnly);
/*      */ 
/*  259 */       if ((assetReader.getMissingHeaders() != null) && (assetReader.getMissingHeaders().size() > 0))
/*      */       {
/*  261 */         for (int i = 0; i < assetReader.getMissingHeaders().size(); i++)
/*      */         {
/*  263 */           this.m_batchMonitor.addMessage(a_lUserId, "Warning: Unable to find a matching attribute/field for header column " + (String)assetReader.getMissingHeaders().elementAt(i));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  269 */       throw new Bn2Exception("AssetImportManager.importAssetData() : Error with reader", e);
/*      */     }
/*      */ 
/*  272 */     return importAssetData(lineCounter, a_lUserId, a_bCheckOnly, assetReader, sImportedAssetIdFormat, a_bCreateMissingAssets, a_bCreateMissingCategories, a_lSessionId, a_bSynch, a_bRemoveFromExistingCategories);
/*      */   }
/*      */ 
/*      */   public int importAssetData(Counter a_lineCounter, long a_lUserId, boolean a_bCheckOnly, BeanReader a_reader, String a_sImportedAssetIdFormat, boolean a_bCreateMissingAssets, boolean a_bCreateMissingCategories, long a_lSessionId, boolean a_bSynch, boolean a_bRemoveFromExistingCategories)
/*      */     throws Bn2Exception
/*      */   {
/*  304 */     ImportResult result = importAssets(a_lineCounter, a_lUserId, a_bCheckOnly, a_reader, a_sImportedAssetIdFormat, false, a_bCreateMissingAssets, a_bCreateMissingCategories, a_lSessionId, a_bSynch, a_bRemoveFromExistingCategories);
/*  305 */     return result.getImportCount();
/*      */   }
/*      */ 
/*      */   public Vector importAssetData(Counter a_lineCounter, long a_lUserId, BeanReader a_reader, String a_sImportedAssetIdFormat, boolean a_bCreateMissingAssets, boolean a_bCreateMissingCategories, long a_lSessionId, boolean a_bSynch, boolean a_bRemoveFromExistingCategories)
/*      */     throws Bn2Exception
/*      */   {
/*  340 */     ImportResult result = importAssets(a_lineCounter, a_lUserId, false, a_reader, a_sImportedAssetIdFormat, true, a_bCreateMissingAssets, a_bCreateMissingCategories, a_lSessionId, a_bSynch, a_bRemoveFromExistingCategories);
/*  341 */     return result.getImportedAssets();
/*      */   }
/*      */ 
/*      */   private ImportResult importAssets(Counter a_lineCounter, long a_lUserId, boolean a_bCheckOnly, BeanReader a_reader, String a_sImportedAssetIdFormat, boolean a_bPopulateAllAssets, boolean a_bCreateMissingAssets, boolean a_bCreateMissingCategories, long a_lSessionId, boolean a_bSynch, boolean a_bRemoveFromExistingCategories)
/*      */     throws Bn2Exception
/*      */   {
/*  366 */     Vector vAssets = null;
/*  367 */     Map hmExistingAssets = new HashMap();
/*  368 */     boolean bFinished = false;
/*  369 */     Set setRelatedAssetIdsToIndex = new HashSet();
/*      */ 
/*  372 */     int iNumAssetsToRead = a_bCheckOnly ? 1 : 100;
/*  373 */     Set setUpdatedAssets = new HashSet();
/*  374 */     Vector vAllUpdatedAssets = new Vector();
/*  375 */     int iImportCount = 0;
/*  376 */     int iStackTraceCount = 0;
/*  377 */     Date dtNow = new Date();
/*      */     do
/*      */     {
/*      */       try
/*      */       {
/*  384 */         vAssets = a_reader.readBeans(iNumAssetsToRead);
/*      */ 
/*  386 */         if ((vAssets != null) && (vAssets.size() > 0))
/*      */         {
/*  388 */           setUpdatedAssets.clear();
/*  389 */           setRelatedAssetIdsToIndex.clear();
/*      */ 
/*  392 */           for (int i = 0; i < vAssets.size(); i++)
/*      */           {
/*      */             try
/*      */             {
/*  396 */               a_lineCounter.increment();
/*  397 */               Asset importAsset = (Asset)vAssets.get(i);
/*      */ 
/*  399 */               Asset existingAsset = null;
/*  400 */               Vector vecExistingAssets = getExistingAssets(importAsset, a_lUserId, a_lineCounter.getValue(), a_sImportedAssetIdFormat);
/*      */ 
/*  403 */               if ((a_bCreateMissingAssets) && ((vecExistingAssets == null) || (vecExistingAssets.size() <= 0)))
/*      */               {
/*  407 */                 if (a_bSynch)
/*      */                 {
/*  409 */                   AssetFileSource source = new AssetFileSource();
/*  410 */                   source.setIsNewWithFixedId(true);
/*  411 */                   existingAsset = new Asset();
/*  412 */                   ABUser user = this.m_userManager.getApplicationUser();
/*  413 */                   a_lUserId = user.getId();
/*  414 */                   existingAsset.setId(Long.parseLong(importAsset.getImportedAssetId().replaceAll("_synch", "")));
/*  415 */                   this.m_logger.debug("Existing Asset Id: " + existingAsset.getId());
/*  416 */                   existingAsset.setImportedAssetId(importAsset.getImportedAssetId());
/*      */ 
/*  419 */                   existingAsset.setIsUnsubmitted(false);
/*      */ 
/*  421 */                   existingAsset = this.m_assetManager.saveAsset(null, existingAsset, source, a_lUserId, null, null, false, 0);
/*      */                 }
/*      */                 else
/*      */                 {
/*  432 */                   existingAsset = new Asset();
/*  433 */                   existingAsset.setDateAdded(dtNow);
/*      */ 
/*  436 */                   existingAsset.setIsUnsubmitted(false);
/*      */                 }
/*      */ 
/*  440 */                 if ((importAsset.getImportApprovalDirective() == null) || ((!importAsset.getImportApprovalDirective().equals("TRUE")) && (!importAsset.getImportApprovalDirective().equals("FALSE"))))
/*      */                 {
/*  445 */                   importAsset.setImportApprovalDirective("TRUE");
/*      */                 }
/*      */ 
/*  448 */                 if (vecExistingAssets == null)
/*      */                 {
/*  450 */                   vecExistingAssets = new Vector();
/*      */                 }
/*  452 */                 vecExistingAssets.add(existingAsset);
/*      */               }
/*      */ 
/*  456 */               if ((vecExistingAssets != null) && (vecExistingAssets.size() > 0))
/*      */               {
/*  458 */                 if (vecExistingAssets.size() > 1)
/*      */                 {
/*  460 */                   this.m_batchMonitor.addMessage(a_lUserId, "Line " + a_lineCounter.getValue() + ": WARNING: There are " + vecExistingAssets.size() + " assets that match this entry. All matching assets will be updated.");
/*      */                 }
/*      */ 
/*  464 */                 for (int x = 0; x < vecExistingAssets.size(); x++)
/*      */                 {
/*  466 */                   existingAsset = (Asset)vecExistingAssets.elementAt(x);
/*  467 */                   this.m_logger.debug("AssetImportManager.importAssetData() : About to import asset with id : " + existingAsset.getId() + " from line: " + a_lineCounter.getValue());
/*      */ 
/*  469 */                   if (hmExistingAssets.containsKey(Long.valueOf(existingAsset.getId())))
/*      */                   {
/*  471 */                     this.m_batchMonitor.addMessage(a_lUserId, "Line " + a_lineCounter.getValue() + ": WARNING: already imported data for existing asset id " + existingAsset.getId() + " from line " + hmExistingAssets.get(Long.valueOf(existingAsset.getId())));
/*      */                   }
/*      */                   else
/*      */                   {
/*  477 */                     String sExistingPeerIds = existingAsset.getPeerAssetIdsAsString();
/*  478 */                     String sExistingChildIds = existingAsset.getChildAssetIdsAsString();
/*      */ 
/*  480 */                     existingAsset = populateAsset(existingAsset, importAsset, a_bSynch, a_bRemoveFromExistingCategories, a_sImportedAssetIdFormat, a_lUserId, a_lineCounter);
/*      */ 
/*  485 */                     Set peerAssetsToIndex = new HashSet(CollectionUtils.disjunction(StringUtil.convertToListOfLongs(existingAsset.getPeerAssetIdsAsString(), ","), StringUtil.convertToListOfLongs(sExistingPeerIds, ",")));
/*      */ 
/*  490 */                     Set childAssetsToIndex = new HashSet(CollectionUtils.disjunction(StringUtil.convertToVectorOfLongs(existingAsset.getChildAssetIdsAsString(), ","), StringUtil.convertToVectorOfLongs(sExistingChildIds, ",")));
/*      */ 
/*  494 */                     if (!a_bCheckOnly)
/*      */                     {
/*  497 */                       if ((importAsset.getAddedByUser() != null) && (StringUtils.isNotEmpty(importAsset.getAddedByUser().getFullName())))
/*      */                       {
/*  500 */                         ABUser existingUser = findExistingUser(importAsset.getAddedByUser(), existingAsset, a_lineCounter.getValue(), a_lUserId);
/*      */ 
/*  505 */                         existingAsset.setAddedByUser(existingUser);
/*      */                       }
/*      */ 
/*  509 */                       if ((importAsset instanceof XMLImportAsset))
/*      */                       {
/*  511 */                         if ((((XMLImportAsset)importAsset).getFlatCategories() != null) && (((XMLImportAsset)importAsset).getFlatCategories().length > 0))
/*      */                         {
/*  514 */                           findAndSetCategory(((XMLImportAsset)importAsset).getFlatCategories(), 1L, existingAsset, ((XMLImportAsset)importAsset).getDefaultCategoryId());
/*      */                         }
/*      */ 
/*  517 */                         if ((((XMLImportAsset)importAsset).getFlatAccessLevels() != null) && (((XMLImportAsset)importAsset).getFlatAccessLevels().length > 0))
/*      */                         {
/*  520 */                           findAndSetCategory(((XMLImportAsset)importAsset).getFlatAccessLevels(), 2L, existingAsset, ((XMLImportAsset)importAsset).getDefaultAccessLevelId());
/*      */                         }
/*      */ 
/*      */                       }
/*      */ 
/*  525 */                       if ((importAsset.getLastModifiedByUser() != null) && (StringUtils.isNotEmpty(importAsset.getLastModifiedByUser().getFullName())))
/*      */                       {
/*  528 */                         ABUser existingUser = findExistingUser(importAsset.getLastModifiedByUser(), existingAsset, a_lineCounter.getValue(), a_lUserId);
/*      */ 
/*  533 */                         existingAsset.setLastModifiedByUser(existingUser);
/*      */                       }
/*      */ 
/*  537 */                       WorkflowUpdate update = null;
/*      */ 
/*  543 */                       if ((importAsset.getImportApprovalDirective() != null) && ((importAsset.getImportApprovalDirective().equals("TRUE")) || (importAsset.getImportApprovalDirective().equals("FALSE"))))
/*      */                       {
/*  547 */                         update = new WorkflowUpdate(a_lUserId, a_lSessionId);
/*  548 */                         if (importAsset.getImportApprovalDirective().equals("TRUE"))
/*      */                         {
/*  550 */                           update.setUpdateType(1);
/*      */                         }
/*      */                         else
/*      */                         {
/*  554 */                           update.setUpdateType(2);
/*      */                         }
/*      */                       }
/*  557 */                       else if ((importAsset.getImportApprovedAccessLevels() != null) && (importAsset.getImportApprovedAccessLevels().size() > 0))
/*      */                       {
/*  561 */                         update = new WorkflowUpdate(a_lUserId, a_lSessionId);
/*  562 */                         update.setUpdateType(4);
/*  563 */                         HashMap hmWorkflowUpdates = new HashMap();
/*  564 */                         for (int a = 0; a < importAsset.getImportApprovedAccessLevels().size(); a++)
/*      */                         {
/*  566 */                           String sApprovedCategoryName = (String)importAsset.getImportApprovedAccessLevels().elementAt(a);
/*  567 */                           Vector vecCats = this.m_categoryManager.getCategoriesByName(null, 2L, sApprovedCategoryName, true);
/*  568 */                           Category fullCat = null;
/*  569 */                           if (vecCats == null)
/*      */                             continue;
/*  571 */                           fullCat = (Category)vecCats.firstElement();
/*      */ 
/*  573 */                           if (fullCat == null)
/*      */                             continue;
/*  575 */                           hmWorkflowUpdates.put(fullCat.getWorkflowName(), new Integer(0));
/*      */                         }
/*      */ 
/*  579 */                         update.setWorkflowApprovalUpdates(hmWorkflowUpdates);
/*      */                       }
/*      */ 
/*  583 */                       Long lId = saveImportedAsset(a_lUserId, existingAsset, a_lineCounter.getValue(), a_bCreateMissingCategories, a_lSessionId, update);
/*      */ 
/*  585 */                       if (lId != null)
/*      */                       {
/*  589 */                         Asset asset = getAsset(lId.longValue());
/*  590 */                         setUpdatedAssets.add(asset);
/*  591 */                         iImportCount++;
/*  592 */                         hmExistingAssets.put(lId, Integer.valueOf(a_lineCounter.getValue()));
/*      */ 
/*  595 */                         setRelatedAssetIdsToIndex.addAll(childAssetsToIndex);
/*  596 */                         setRelatedAssetIdsToIndex.addAll(peerAssetsToIndex);
/*      */ 
/*  600 */                         if ((!peerAssetsToIndex.isEmpty()) || (!childAssetsToIndex.isEmpty()))
/*      */                         {
/*  602 */                           setRelatedAssetIdsToIndex.add(Long.valueOf(asset.getId()));
/*      */                         }
/*      */                       }
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*  609 */                       iImportCount++;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/*  616 */                 this.m_batchMonitor.addMessage(a_lUserId, "Line " + a_lineCounter.getValue() + ": WARNING: cannot find existing asset for import (id=" + importAsset.getImportedAssetId() + ", filename=" + importAsset.getFileName() + ")");
/*      */               }
/*      */ 
/*      */             }
/*      */             catch (Throwable t)
/*      */             {
/*  623 */               this.m_batchMonitor.addMessage(a_lUserId, "Line " + a_lineCounter.getValue() + ": ERROR: internal error whilst saving asset data : " + t);
/*      */ 
/*  626 */               if (iStackTraceCount++ < 5)
/*      */               {
/*  628 */                 this.m_logger.error("AssetImportManager.importAssetData() : unexpected exception caught while reading import file : " + t.getMessage(), t);
/*      */               }
/*      */               else
/*      */               {
/*  632 */                 this.m_logger.error("AssetImportManager.importAssetData() : unexpected exception caught while reading import file : " + t.getMessage());
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*  637 */           if (!a_bCheckOnly)
/*      */           {
/*  640 */             if (a_bPopulateAllAssets)
/*      */             {
/*  642 */               if (vAllUpdatedAssets == null)
/*      */               {
/*  644 */                 vAllUpdatedAssets = new Vector();
/*      */               }
/*      */             }
/*      */ 
/*  648 */             vAllUpdatedAssets.addAll(setUpdatedAssets);
/*      */ 
/*  651 */             if (!setUpdatedAssets.isEmpty())
/*      */             {
/*  653 */               if (setRelatedAssetIdsToIndex.size() > 0)
/*      */               {
/*  655 */                 Vector vRelatedAssets = this.m_assetManager.getAssets(null, new Vector(setRelatedAssetIdsToIndex), true, true);
/*  656 */                 this.m_searchManager.indexDocuments(null, vRelatedAssets, true);
/*      */               }
/*      */ 
/*  660 */               this.m_categoryCountCacheManager.rebuildCacheNow();
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  666 */           bFinished = true;
/*      */         }
/*      */       }
/*      */       catch (DefaultBeanReader.TooManyColumnsException e)
/*      */       {
/*  671 */         this.m_batchMonitor.addMessage(a_lUserId, "Line " + (a_lineCounter.getValue() + 1) + ": ERROR: wrong number of columns (skipping this line)");
/*  672 */         this.m_logger.error("AssetImportManager.importAssetData() : TooManyColumnsException reading line " + (a_lineCounter.getValue() + 1) + ": " + e.getMessage());
/*  673 */         a_lineCounter.skip(1);
/*      */       }
/*      */       catch (DefaultBeanReader.BeanPopulationException e)
/*      */       {
/*  677 */         this.m_batchMonitor.addMessage(a_lUserId, "Line " + (a_lineCounter.getValue() + 1) + ": ERROR: could not successfully populate asset with data (skipping this line)");
/*  678 */         this.m_logger.error("AssetImportManager.importAssetData() : BeanPopulationException reading line " + (a_lineCounter.getValue() + 1) + ": " + e.getMessage(), e);
/*  679 */         a_lineCounter.skip(1);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  683 */         this.m_batchMonitor.addMessage(a_lUserId, "Line " + (a_lineCounter.getValue() + 1) + ": ERROR: Unhandled error reading file.");
/*  684 */         this.m_logger.error("AssetImportManager.importAssetData() : Error reading beans : ", e);
/*  685 */         throw new Bn2Exception("AssetImportManager.importAssetData() : Error reading beans : ", e);
/*      */       }
/*      */ 
/*  689 */       if ((a_bCheckOnly) && (a_lineCounter.getValue() % 100 != 0))
/*      */         continue;
/*  691 */       this.m_batchMonitor.addMessage(a_lUserId, "Reached Line " + (a_lineCounter.getValue() + 1));
/*      */     }
/*      */ 
/*  694 */     while (!bFinished);
/*      */ 
/*  697 */     ImportResult result = new ImportResult();
/*  698 */     result.setImportCount(iImportCount);
/*  699 */     result.setImportedAssets(vAllUpdatedAssets);
/*      */ 
/*  701 */     return result;
/*      */   }
/*      */ 
/*      */   private String getImportedAssetIdFormat(BufferedReader a_reader, FileFormat a_format)
/*      */     throws IOException, Bn2Exception
/*      */   {
/*  715 */     a_reader.mark(1000);
/*  716 */     String sFirstLine = a_reader.readLine();
/*  717 */     String sImportedAssetIdFormat = String.valueOf('i');
/*      */ 
/*  719 */     sFirstLine = sFirstLine.trim();
/*      */ 
/*  722 */     if (sFirstLine.contains("filenameFormat:"))
/*      */     {
/*  724 */       int iIndex = sFirstLine.indexOf("filenameFormat:") + "filenameFormat:".length();
/*  725 */       sFirstLine = sFirstLine.substring(iIndex, sFirstLine.length());
/*  726 */       char[] aCharacters = sFirstLine.toCharArray();
/*  727 */       sImportedAssetIdFormat = "";
/*      */ 
/*  729 */       for (int i = 0; i < aCharacters.length; i++)
/*      */       {
/*  731 */         if ((aCharacters[i] != 'i') && (aCharacters[i] != 't') && (aCharacters[i] != 'f') && ("none".indexOf(String.valueOf(aCharacters[i])) < 0))
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*  736 */         sImportedAssetIdFormat = sImportedAssetIdFormat + aCharacters[i];
/*      */       }
/*      */ 
/*      */     }
/*  746 */     else if ((StringUtils.isNotEmpty(sFirstLine)) && (StringUtils.countMatches(sFirstLine, "\t") > 3))
/*      */     {
/*  748 */       a_reader.reset();
/*      */     }
/*      */ 
/*  752 */     if (StringUtils.isEmpty(sImportedAssetIdFormat))
/*      */     {
/*  754 */       throw new Bn2Exception("AssetImportManager.getImportedAssetIdFormat() : Cannot find valid filename format on first line, should be: filenameFormat:[i|t|f|it|ti|if|fi|tf|ft|itf|ift|tfi|tif|fti|fit|none]");
/*      */     }
/*      */ 
/*  758 */     return sImportedAssetIdFormat;
/*      */   }
/*      */ 
/*      */   private ABUser findExistingUser(ABUser a_candidateUser, Asset a_asset, int a_iLineNo, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  780 */     boolean bMatchedMultiple = false;
/*  781 */     ABUser matchedUser = null;
/*  782 */     UserSearchCriteria criteria = new UserSearchCriteria();
/*  783 */     criteria.setForename(a_candidateUser.getForename());
/*  784 */     criteria.setSurname(a_candidateUser.getSurname());
/*      */ 
/*  786 */     Vector<ABUser> vResults = this.m_userManager.findUsers(criteria, 0);
/*      */ 
/*  788 */     for (ABUser userInResults : vResults)
/*      */     {
/*  790 */       if ((userInResults.getForename() != null) && (userInResults.getForename().trim().equalsIgnoreCase(a_candidateUser.getForename().trim())) && (userInResults.getSurname() != null) && (userInResults.getSurname().trim().equalsIgnoreCase(a_candidateUser.getSurname().trim())))
/*      */       {
/*  793 */         if (matchedUser != null)
/*      */         {
/*  795 */           bMatchedMultiple = true;
/*  796 */           break;
/*      */         }
/*  798 */         matchedUser = userInResults;
/*      */       }
/*      */     }
/*      */ 
/*  802 */     if (bMatchedMultiple)
/*      */     {
/*  804 */       addMessage(a_lUserId, "Line " + a_iLineNo + ": user '" + a_candidateUser.getFullName() + "' matched more than one user. The first was used.");
/*      */     }
/*  807 */     else if (matchedUser == null)
/*      */     {
/*  809 */       addMessage(a_lUserId, "Line " + a_iLineNo + ": user '" + a_candidateUser.getFullName() + "' could not be matched to a user.");
/*      */     }
/*      */ 
/*  813 */     return matchedUser;
/*      */   }
/*      */ 
/*      */   private void findAndSetCategory(String[] a_aCategoryNames, long a_lCatTypeId, Asset a_asset, long a_lDefaultId)
/*      */     throws Bn2Exception
/*      */   {
/*  827 */     this.m_logger.debug("AssetImportManager.findAndSetCategory() : About to find category");
/*      */ 
/*  829 */     DBTransaction dbTransaction = null;
/*  830 */     Category tempCategory = null;
/*      */     try
/*      */     {
/*  835 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*  836 */       tempCategory = this.m_categoryManager.getCategory(dbTransaction, a_lCatTypeId, a_aCategoryNames[0], true);
/*      */ 
/*  838 */       for (int i = 1; i < a_aCategoryNames.length; i++)
/*      */       {
/*  840 */         if (tempCategory == null)
/*      */           continue;
/*  842 */         Vector vecSubCategories = this.m_categoryManager.getCategories(dbTransaction, a_lCatTypeId, tempCategory.getId());
/*      */ 
/*  844 */         if (vecSubCategories == null)
/*      */           break;
/*  846 */         for (int x = 0; x < vecSubCategories.size(); x++)
/*      */         {
/*  848 */           Category cat = (Category)vecSubCategories.elementAt(x);
/*  849 */           if (!cat.getName().equals(a_aCategoryNames[i]))
/*      */             continue;
/*  851 */           tempCategory = cat;
/*  852 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  863 */       if (tempCategory == null)
/*      */       {
/*  865 */         tempCategory = this.m_categoryManager.getCategory(dbTransaction, a_lCatTypeId, a_lDefaultId);
/*      */       }
/*      */ 
/*  868 */       if (tempCategory != null)
/*      */       {
/*  870 */         if (a_lCatTypeId == 1L)
/*      */         {
/*  872 */           if (a_asset.getDescriptiveCategories() == null)
/*      */           {
/*  874 */             a_asset.setDescriptiveCategories(new Vector());
/*      */           }
/*  876 */           a_asset.getDescriptiveCategories().add(tempCategory);
/*      */         }
/*      */         else
/*      */         {
/*  880 */           if (a_asset.getPermissionCategories() == null)
/*      */           {
/*  882 */             a_asset.setPermissionCategories(new Vector());
/*      */           }
/*  884 */           a_asset.getPermissionCategories().add(tempCategory);
/*      */         }
/*      */ 
/*  887 */         this.m_logger.debug("AssetImportManager.findAndSetCategory() : Adding category " + tempCategory.getName() + " (" + tempCategory.getId() + ")");
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Bn2Exception be)
/*      */     {
/*  893 */       this.m_logger.error("Exception in AssetImportManager.findCategory:" + be.getMessage());
/*      */ 
/*  895 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  899 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/*  903 */           this.m_logger.error("Exception rolling back transaction in AssetImportManager.findCategory:" + e2.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  908 */       throw be;
/*      */     }
/*      */     finally
/*      */     {
/*  912 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  916 */           dbTransaction.commit();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/*  920 */           this.m_logger.error("Exception committing transaction in AssetImportManager:" + e2.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Asset populateAsset(Asset a_existingAsset, Asset a_importedAsset, boolean a_bSynch, boolean a_bRemoveFromExistingCategories, String a_sImportedAssetIdFormat, long a_lUserId, Counter a_lineCounter)
/*      */     throws Bn2Exception
/*      */   {
/*  944 */     if (a_importedAsset.getCode() != null)
/*      */     {
/*  946 */       a_existingAsset.setCode(a_importedAsset.getCode());
/*      */     }
/*  948 */     if (a_importedAsset.getAuthor() != null)
/*      */     {
/*  950 */       a_existingAsset.setAuthor(a_importedAsset.getAuthor());
/*      */     }
/*  952 */     if (a_importedAsset.getDateAdded() != null)
/*      */     {
/*  954 */       a_existingAsset.setDateAdded(a_importedAsset.getDateAdded());
/*      */     }
/*  956 */     if (a_importedAsset.getDateLastModified() != null)
/*      */     {
/*  958 */       a_existingAsset.setDateLastModified(a_importedAsset.getDateLastModified());
/*      */     }
/*  960 */     if (a_importedAsset.getExpiryDate() != null)
/*      */     {
/*  962 */       a_existingAsset.setExpiryDate(a_importedAsset.getExpiryDate());
/*      */     }
/*  964 */     if (a_importedAsset.getPrice() != null)
/*      */     {
/*  966 */       a_existingAsset.setPrice(a_importedAsset.getPrice());
/*      */     }
/*  968 */     if (a_importedAsset.getAgreementTypeId() > 0L)
/*      */     {
/*  970 */       a_existingAsset.setAgreementTypeId(a_importedAsset.getAgreementTypeId());
/*      */     }
/*  972 */     if (a_importedAsset.getHasAgreement())
/*      */     {
/*  974 */       a_existingAsset.setAgreement(a_importedAsset.getAgreement());
/*      */     }
/*  976 */     if (a_importedAsset.getPreviousAgreementsCount() > 0)
/*      */     {
/*  978 */       a_existingAsset.setPreviousAgreements(a_importedAsset.getPreviousAgreements());
/*      */     }
/*      */ 
/*  981 */     a_existingAsset.setIsRestricted(a_importedAsset.getIsRestricted());
/*  982 */     a_existingAsset.setIsSensitive(a_importedAsset.getIsSensitive());
/*  983 */     a_existingAsset.setAdvancedViewing(a_importedAsset.getAdvancedViewing());
/*  984 */     a_existingAsset.setExtendsCategory(a_importedAsset.getExtendsCategory());
/*      */ 
/*  988 */     Vector vecImportedPermissionCategoryIds = a_importedAsset.getPermissionCategories();
/*  989 */     if (vecImportedPermissionCategoryIds != null)
/*      */     {
/*  991 */       if ((a_bRemoveFromExistingCategories) && (!vecImportedPermissionCategoryIds.isEmpty()))
/*      */       {
/*  993 */         a_existingAsset.setPermissionCategories(vecImportedPermissionCategoryIds);
/*      */       }
/*      */       else
/*      */       {
/*  997 */         if (a_existingAsset.getPermissionCategories() == null)
/*      */         {
/*  999 */           a_existingAsset.setPermissionCategories(new Vector());
/*      */         }
/* 1001 */         a_existingAsset.getPermissionCategories().addAll(vecImportedPermissionCategoryIds);
/*      */       }
/*      */     }
/*      */ 
/* 1005 */     Vector vecImportedDescriptiveCategories = a_importedAsset.getDescriptiveCategories();
/* 1006 */     if (vecImportedDescriptiveCategories != null)
/*      */     {
/* 1008 */       if ((a_bRemoveFromExistingCategories) && (!vecImportedDescriptiveCategories.isEmpty()))
/*      */       {
/* 1010 */         a_existingAsset.setDescriptiveCategories(vecImportedDescriptiveCategories);
/*      */       }
/*      */       else
/*      */       {
/* 1014 */         if (a_existingAsset.getDescriptiveCategories() == null)
/*      */         {
/* 1016 */           a_existingAsset.setDescriptiveCategories(new Vector());
/*      */         }
/* 1018 */         a_existingAsset.getDescriptiveCategories().addAll(vecImportedDescriptiveCategories);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1024 */     String sParentIds = null;
/* 1025 */     String sChildIds = null;
/* 1026 */     String sPeerIds = null;
/*      */ 
/* 1028 */     if ((StringUtils.isNotEmpty(a_sImportedAssetIdFormat)) && (!a_sImportedAssetIdFormat.equalsIgnoreCase("none")))
/*      */     {
/* 1031 */       sParentIds = getNativeAssetIds(a_importedAsset.getParentAssetIdsAsString());
/* 1032 */       sChildIds = getNativeAssetIds(a_importedAsset.getPeerAssetIdsAsString());
/* 1033 */       sPeerIds = getNativeAssetIds(a_importedAsset.getChildAssetIdsAsString());
/*      */     }
/*      */     else
/*      */     {
/* 1037 */       if (a_importedAsset.getPeerAssetIdsAsString() != null)
/*      */       {
/* 1039 */         sPeerIds = a_importedAsset.getPeerAssetIdsAsString();
/*      */       }
/* 1041 */       if (a_importedAsset.getParentAssetIdsAsString() != null)
/*      */       {
/* 1043 */         sParentIds = a_importedAsset.getParentAssetIdsAsString();
/*      */       }
/* 1045 */       if (a_importedAsset.getChildAssetIdsAsString() != null)
/*      */       {
/* 1047 */         sChildIds = a_importedAsset.getChildAssetIdsAsString();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1052 */     checkAndSetRelationships(null, a_existingAsset, sChildIds, 2L, a_lineCounter, a_lUserId);
/* 1053 */     checkAndSetRelationships(null, a_existingAsset, sParentIds, 3L, a_lineCounter, a_lUserId);
/* 1054 */     checkAndSetRelationships(null, a_existingAsset, sPeerIds, 1L, a_lineCounter, a_lUserId);
/*      */ 
/* 1057 */     if (a_importedAsset.getAttributeValues() != null)
/*      */     {
/* 1059 */       HashMap hmLists = new HashMap();
/* 1060 */       Iterator itAtts = a_importedAsset.getAttributeValues().iterator();
/* 1061 */       while (itAtts.hasNext())
/*      */       {
/* 1063 */         AttributeValue value = (AttributeValue)itAtts.next();
/* 1064 */         if ((value.getAttribute().getIsCheckList()) || (value.getAttribute().getIsOptionList()))
/*      */         {
/* 1066 */           if (!hmLists.containsKey(new Long(value.getAttribute().getId())))
/*      */           {
/* 1068 */             hmLists.put(new Long(value.getAttribute().getId()), new Vector());
/*      */           }
/* 1070 */           ((Vector)hmLists.get(new Long(value.getAttribute().getId()))).add(value);
/*      */         }
/*      */         else
/*      */         {
/* 1074 */           Vector vecTemp = new Vector();
/* 1075 */           vecTemp.add(value);
/* 1076 */           AttributeUtil.setAttributeValuesForAttributeInAsset(a_existingAsset, vecTemp, true);
/*      */         }
/*      */       }
/*      */ 
/* 1080 */       Set keys = hmLists.keySet();
/* 1081 */       Iterator it = keys.iterator();
/* 1082 */       while (it.hasNext())
/*      */       {
/* 1084 */         Vector vecTemp = (Vector)hmLists.get(it.next());
/* 1085 */         AttributeUtil.setAttributeValuesForAttributeInAsset(a_existingAsset, vecTemp, true);
/*      */       }
/*      */     }
/*      */ 
/* 1089 */     if (a_importedAsset.getEntity().getId() > 0L)
/*      */     {
/* 1091 */       a_existingAsset.setEntity(a_importedAsset.getEntity());
/*      */     }
/*      */ 
/* 1095 */     if ((a_bSynch) && (a_importedAsset.getOriginalFilename() != null))
/*      */     {
/* 1097 */       a_existingAsset.setOriginalFilename(a_importedAsset.getOriginalFilename());
/*      */     }
/*      */ 
/* 1100 */     return a_existingAsset;
/*      */   }
/*      */ 
/*      */   private void checkAndSetRelationships(DBTransaction a_transaction, Asset a_asset, String a_sIds, long a_lRelationshipType, Counter a_lineCounter, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1107 */     if ((a_sIds != null) && (a_sIds.endsWith(",")))
/*      */     {
/* 1109 */       a_sIds = a_sIds.substring(0, a_sIds.length() - 1);
/*      */     }
/*      */ 
/* 1112 */     String sNewIds = null;
/*      */ 
/* 1114 */     if (StringUtil.stringIsPopulated(a_sIds))
/*      */     {
/*      */       try
/*      */       {
/* 1118 */         this.m_entityRelationshipManager.validateAssetRelationships(a_transaction, a_asset.getEntity().getId(), a_sIds, a_lRelationshipType, null);
/* 1119 */         sNewIds = a_sIds;
/*      */       }
/*      */       catch (InvalidRelationshipException e)
/*      */       {
/* 1123 */         if (a_lRelationshipType == 2L)
/*      */         {
/* 1125 */           this.m_batchMonitor.addMessage(a_lUserId, "Line " + a_lineCounter.getValue() + " : One of the child assets isn't valid so the child assets haven't been updated");
/*      */         }
/* 1127 */         else if (a_lRelationshipType == 3L)
/*      */         {
/* 1129 */           this.m_batchMonitor.addMessage(a_lUserId, "Line " + a_lineCounter.getValue() + " : One of the parent assets isn't valid so the parent assets haven't been updated");
/*      */         }
/* 1131 */         else if (a_lRelationshipType == 1L)
/*      */         {
/* 1133 */           this.m_batchMonitor.addMessage(a_lUserId, "Line " + a_lineCounter.getValue() + " : One of the peer assets isn't valid so the peer assets haven't been updated");
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1139 */     if (a_lRelationshipType == 2L)
/*      */     {
/* 1141 */       a_asset.setChildAssetIdsAsString(sNewIds);
/*      */     }
/* 1143 */     else if (a_lRelationshipType == 3L)
/*      */     {
/* 1145 */       a_asset.setParentAssetIdsAsString(sNewIds);
/*      */     }
/* 1147 */     else if (a_lRelationshipType == 1L)
/*      */     {
/* 1149 */       a_asset.setPeerAssetIdsAsString(sNewIds);
/*      */     }
/*      */   }
/*      */ 
/*      */   private String getNativeAssetIds(String a_sIds)
/*      */     throws AssetNotFoundException, Bn2Exception
/*      */   {
/* 1164 */     StringBuffer sbNewIdString = new StringBuffer();
/*      */ 
/* 1166 */     if (StringUtils.isNotEmpty(a_sIds))
/*      */     {
/* 1168 */       String[] asIds = a_sIds.split(" *, *");
/* 1169 */       for (String sId : asIds)
/*      */       {
/* 1171 */         Vector<Asset> vecRelatedAssets = this.m_assetManager.getImportedAssets(null, sId, null, false, -1L);
/* 1172 */         for (Asset asset : vecRelatedAssets)
/*      */         {
/* 1174 */           if (sbNewIdString.length() > 0)
/*      */           {
/* 1176 */             sbNewIdString.append(",");
/*      */           }
/* 1178 */           sbNewIdString.append(asset.getId());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1183 */     return sbNewIdString.toString();
/*      */   }
/*      */ 
/*      */   private Vector<Asset> getExistingAssets(Asset a_assetToImport, long a_lUserId, int a_iLineNo, String a_sImportedAssetIdFormat)
/*      */     throws Bn2Exception
/*      */   {
/* 1199 */     Vector vecExistingAssets = new Vector();
/*      */ 
/* 1201 */     if (a_assetToImport.getImportedAssetId() != null)
/*      */     {
/* 1204 */       if (a_sImportedAssetIdFormat.equalsIgnoreCase("none"))
/*      */       {
/*      */         try
/*      */         {
/* 1208 */           vecExistingAssets = this.m_assetManager.getImportedAssets(null, null, null, false, Long.parseLong(a_assetToImport.getImportedAssetId().trim()));
/*      */         }
/*      */         catch (NumberFormatException e) {
/*      */         }
/*      */       }
/* 1213 */       if ((vecExistingAssets == null) || (vecExistingAssets.size() <= 0))
/*      */       {
/* 1215 */         String sId = "";
/*      */ 
/* 1217 */         if ((a_sImportedAssetIdFormat != null) && (!a_sImportedAssetIdFormat.equalsIgnoreCase("none")))
/*      */         {
/* 1219 */           for (int i = 0; i < a_sImportedAssetIdFormat.length(); i++)
/*      */           {
/* 1221 */             switch (a_sImportedAssetIdFormat.charAt(i))
/*      */             {
/*      */             case 'i':
/* 1224 */               sId = sId + (sId.length() > 0 ? AssetBankSettings.getExportedAssetFilenameFieldDelimiter() : "");
/* 1225 */               sId = sId + a_assetToImport.getImportedAssetId();
/* 1226 */               break;
/*      */             case 't':
/* 1228 */               sId = sId + (sId.length() > 0 ? AssetBankSettings.getExportedAssetFilenameFieldDelimiter() : "");
/* 1229 */               sId = sId + FileUtil.getSafeFilename(AssetExportManager.replaceFilenameFieldSeparator(a_assetToImport.getName()), false);
/* 1230 */               break;
/*      */             case 'f':
/* 1232 */               sId = sId + (sId.length() > 0 ? AssetBankSettings.getExportedAssetFilenameFieldDelimiter() : "");
/* 1233 */               sId = sId + FilenameUtils.getBaseName(a_assetToImport.getFileLocation());
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1239 */         if (sId.length() == 0)
/*      */         {
/* 1241 */           sId = String.valueOf(a_assetToImport.getImportedAssetId());
/*      */         }
/*      */ 
/*      */         try
/*      */         {
/* 1246 */           vecExistingAssets = this.m_assetManager.getImportedAssets(null, sId, null, false, -1L);
/*      */ 
/* 1248 */           if (((vecExistingAssets == null) || (vecExistingAssets.size() <= 0)) && (sId != null) && (sId.indexOf(".") > 0))
/*      */           {
/* 1250 */             vecExistingAssets = this.m_assetManager.getImportedAssets(null, FileUtil.getFilepathWithoutSuffix(sId), null, false, -1L);
/*      */           }
/*      */         }
/*      */         catch (AssetNotFoundException e)
/*      */         {
/* 1255 */           System.out.println(sId);
/*      */         }
/*      */       }
/*      */     }
/* 1259 */     else if (a_assetToImport.getId() > 0L)
/*      */     {
/* 1261 */       vecExistingAssets = this.m_assetManager.getImportedAssets(null, null, null, false, a_assetToImport.getId());
/*      */     }
/*      */ 
/* 1264 */     return vecExistingAssets;
/*      */   }
/*      */ 
/*      */   private Asset getAsset(long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1279 */     Asset asset = null;
/* 1280 */     DBTransaction dbTransaction = null;
/*      */     try
/*      */     {
/* 1285 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 1288 */       asset = this.m_assetManager.getAsset(dbTransaction, a_lAssetId, null, true, false);
/*      */     }
/*      */     catch (Bn2Exception be)
/*      */     {
/* 1292 */       this.m_logger.error("Exception in DataImportManager:" + be.getMessage());
/*      */ 
/* 1294 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1298 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/* 1302 */           this.m_logger.error("Exception rolling back transaction in AssetImportManager:" + e2.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1307 */       throw be;
/*      */     }
/*      */     finally
/*      */     {
/* 1311 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1315 */           dbTransaction.commit();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/* 1319 */           this.m_logger.error("Exception committing transaction in AssetImportManager:" + e2.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1324 */     return asset;
/*      */   }
/*      */ 
/*      */   private Long saveImportedAsset(long a_lUserId, Asset a_asset, int a_iLineNo, boolean a_bCreateMissingCategories, long a_lSessionId, WorkflowUpdate a_update)
/*      */     throws Bn2Exception
/*      */   {
/* 1348 */     DBTransaction dbTransaction = null;
/*      */     try
/*      */     {
/* 1353 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 1356 */       Agreement currentAgreement = a_asset.getHasAgreement() ? a_asset.getAgreement() : null;
/* 1357 */       Vector previousAgreements = a_asset.getPreviousAgreements();
/*      */ 
/* 1359 */       String sParentIds = a_asset.getParentAssetIdsAsString();
/* 1360 */       String sChildIds = a_asset.getChildAssetIdsAsString();
/* 1361 */       String sPeerIds = a_asset.getPeerAssetIdsAsString();
/*      */ 
/* 1364 */       a_asset = this.m_assetManager.saveAsset(dbTransaction, a_asset, null, a_lUserId, null, null, false, 0, a_update);
/*      */ 
/* 1367 */       this.m_taxonomyManager.updateKeywordsForAsset(dbTransaction, a_asset.getId(), a_asset.getAttributeValues());
/*      */ 
/* 1372 */       if ((sPeerIds != null) && ((sPeerIds.length() > 0) || (AssetBankSettings.getExportRelationshipData())))
/*      */       {
/* 1374 */         this.m_assetRelationshipManager.saveRelatedAssetIds(dbTransaction, a_asset, sPeerIds, 1L, false);
/*      */       }
/*      */ 
/* 1377 */       if ((sChildIds != null) && ((sChildIds.length() > 0) || (AssetBankSettings.getExportRelationshipData())))
/*      */       {
/* 1379 */         this.m_assetRelationshipManager.saveRelatedAssetIds(dbTransaction, a_asset, sChildIds, 2L, false);
/*      */       }
/*      */ 
/* 1382 */       if ((sParentIds != null) && ((sParentIds.length() > 0) || (AssetBankSettings.getExportRelationshipData())))
/*      */       {
/* 1384 */         this.m_assetRelationshipManager.saveRelatedAssetIds(dbTransaction, a_asset, sParentIds, 3L, false);
/*      */       }
/*      */ 
/* 1388 */       if (AssetBankSettings.getAgreementsEnabled())
/*      */       {
/* 1391 */         if (((currentAgreement != null) || ((previousAgreements != null) && (previousAgreements.size() > 0))) && ((a_asset.getAgreement() != null) || (a_asset.getPreviousAgreementsCount() > 0)))
/*      */         {
/* 1394 */           this.m_agreementsManager.deleteAllAssetAgreements(dbTransaction, a_asset.getId());
/*      */         }
/*      */ 
/* 1398 */         if ((previousAgreements != null) && (previousAgreements.size() > 0))
/*      */         {
/* 1400 */           for (int i = previousAgreements.size() - 1; i >= 0; i--)
/*      */           {
/* 1402 */             Agreement previousAgreement = (Agreement)previousAgreements.get(i);
/* 1403 */             addAgreement(dbTransaction, a_asset, previousAgreement);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1408 */         if (currentAgreement != null)
/*      */         {
/* 1410 */           addAgreement(dbTransaction, a_asset, currentAgreement);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1415 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1417 */         this.m_assetLogManager.saveLog(a_asset.getId(), a_asset.getFileName(), dbTransaction, a_lUserId, new Date(), 1L, a_lSessionId, a_asset.getVersionNumber());
/*      */       }
/*      */ 
/* 1420 */       long i = Long.valueOf(a_asset.getId());
                return i;
/*      */     }
/*      */     catch (Bn2Exception be)
/*      */     {
/*      */       //int i;
/* 1424 */       this.m_logger.error("Exception in AssetImportManager:" + be.getMessage());
/*      */ 
/* 1426 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1430 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/* 1434 */           this.m_logger.error("Exception rolling back transaction in AssetImportManager:" + e2.getMessage());
                     
/*      */         }
/*      */ 
/*      */       }
/*      */      throw be;
/* 1439 */       
/*      */     }
/*      */     finally
/*      */     {
/* 1443 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1447 */           dbTransaction.commit();
/*      */         }
/*      */         catch (Exception e2)
/*      */         {
/* 1451 */           this.m_logger.error("Exception committing transaction in DataImportManager:" + e2.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addAgreement(DBTransaction a_dbTransaction, Asset a_asset, Agreement a_agreement)
/*      */     throws Bn2Exception
/*      */   {
/* 1461 */     if (a_agreement == null)
/*      */     {
/* 1463 */       return;
/*      */     }
/*      */ 
/* 1466 */     Agreement existingAgreement = null;
/*      */ 
/* 1468 */     if (a_agreement.getId() > 0L)
/*      */     {
/* 1470 */       existingAgreement = this.m_agreementsManager.getAgreement(a_dbTransaction, a_agreement.getId());
/*      */     }
/*      */ 
/* 1474 */     if ((existingAgreement == null) || (existingAgreement.getTitle() == null) || (!existingAgreement.getTitle().equals(a_agreement.getTitle())) || ((!a_agreement.getIsAvailableToAll()) && (!a_agreement.getIsSharedWithOU())))
/*      */     {
/* 1479 */       long lId = this.m_agreementsManager.addAgreement(a_dbTransaction, a_agreement, null);
/* 1480 */       existingAgreement = a_agreement;
/* 1481 */       existingAgreement.setId(lId);
/*      */     }
/*      */ 
/* 1485 */     this.m_agreementsManager.addCurrentAssetAgreement(a_dbTransaction, existingAgreement.getId(), a_asset.getId(), a_agreement.getDateActivated());
/*      */   }
/*      */ 
/*      */   public void processQueueItem(QueuedItem a_queuedItem)
/*      */     throws Bn2Exception
/*      */   {
/* 1504 */     MetadataImportInfo importInfo = null;
/*      */     try
/*      */     {
/* 1510 */       importInfo = (MetadataImportInfo)a_queuedItem;
/* 1511 */       int iImportCount = 0;
/*      */ 
/* 1514 */       if (importInfo.getCheckOnly())
/*      */       {
/* 1517 */         iImportCount = importAssetData(importInfo.getFileUrl(), importInfo.getUser().getId(), true, importInfo.getAddMissingAssets(), importInfo.getAddMissingCategories(), importInfo.getSessionId(), false, importInfo.getRemoveFromExistingCategories());
/*      */ 
/* 1524 */         addMessage(importInfo.getUser().getId(), "Finished metadata check");
/*      */       }
/*      */       else
/*      */       {
/* 1529 */         iImportCount = importAssetData(importInfo.getFileUrl(), importInfo.getUser().getId(), false, importInfo.getAddMissingAssets(), importInfo.getAddMissingCategories(), importInfo.getSessionId(), false, importInfo.getRemoveFromExistingCategories());
/*      */ 
/* 1537 */         this.m_fileStoreManager.deleteFile(importInfo.getFileUrl());
/* 1538 */         addMessage(importInfo.getUser().getId(), "Finished metadata import");
/*      */       }
/*      */ 
/* 1541 */       addMessage(importInfo.getUser().getId(), "In total, " + iImportCount + " items were successfully read.");
/*      */     }
/*      */     catch (Bn2Exception bn2e)
/*      */     {
/* 1545 */       this.m_logger.error("AssetImportManager.processQueueItem - exception:", bn2e);
/* 1546 */       throw bn2e;
/*      */     }
/*      */     finally
/*      */     {
/* 1550 */       if (importInfo != null)
/*      */       {
/* 1552 */         endBatchProcess(importInfo.getUser().getId());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAssetManager(AssetManager a_sAssetManager)
/*      */   {
/* 1559 */     this.m_assetManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*      */   {
/* 1564 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*      */   {
/* 1569 */     this.m_fileStoreManager = a_sFileStoreManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 1574 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(AssetCategoryManager a_sCategoryManager)
/*      */   {
/* 1579 */     this.m_categoryManager = a_sCategoryManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_sSearchManager)
/*      */   {
/* 1584 */     this.m_searchManager = a_sSearchManager;
/*      */   }
/*      */ 
/*      */   public void setUserManager(ABUserManager a_sUserManager)
/*      */   {
/* 1589 */     this.m_userManager = a_sUserManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_sCategoryCountCacheManager)
/*      */   {
/* 1594 */     this.m_categoryCountCacheManager = a_sCategoryCountCacheManager;
/*      */   }
/*      */ 
/*      */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*      */   {
/* 1599 */     this.m_taxonomyManager = a_taxonomyManager;
/*      */   }
/*      */ 
/*      */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*      */   {
/* 1604 */     this.m_assetLogManager = a_assetLogManager;
/*      */   }
/*      */ 
/*      */   public void setAgreementsManager(AgreementsManager a_manager)
/*      */   {
/* 1609 */     this.m_agreementsManager = a_manager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.service.AssetImportManager
 * JD-Core Version:    0.6.0
 */