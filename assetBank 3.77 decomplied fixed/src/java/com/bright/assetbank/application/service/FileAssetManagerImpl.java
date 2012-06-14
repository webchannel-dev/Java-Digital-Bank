/*      */ package com.bright.assetbank.application.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*      */ import com.bright.assetbank.application.bean.AssetFileSource;
/*      */ import com.bright.assetbank.application.bean.AudioAsset;
/*      */ import com.bright.assetbank.application.bean.FileFormat;
/*      */ import com.bright.assetbank.application.bean.ImageAsset;
/*      */ import com.bright.assetbank.application.bean.ImageFileInfo;
/*      */ import com.bright.assetbank.application.bean.UploadedFileInfo;
/*      */ import com.bright.assetbank.application.bean.VideoAsset;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.constant.GeneratedImageVersion;
/*      */ import com.bright.assetbank.application.exception.AssetFileReadException;
/*      */ import com.bright.assetbank.application.exception.UploadedFileNotFoundException;
/*      */ import com.bright.assetbank.application.util.ABImageMagick;
/*      */ import com.bright.assetbank.application.util.AssetDBUtil;
/*      */ import com.bright.assetbank.application.util.AudioAssetDBUtil;
/*      */ import com.bright.assetbank.application.util.VideoUtil;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*      */ import com.bright.assetbank.attribute.bean.DisplayAttribute;
/*      */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*      */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*      */ import com.bright.assetbank.category.util.CategoryUtil;
/*      */ import com.bright.assetbank.converter.AssetConverter;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*      */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*      */ import com.bright.assetbank.feedback.service.AssetFeedbackManager;
/*      */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.usage.bean.ColorSpace;
/*      */ import com.bright.assetbank.usage.service.UsageManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.image.bean.ImageFile;
/*      */ import com.bright.framework.image.exception.ImageException;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.storage.constant.StoredFileType;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import com.bright.framework.workflow.service.WorkflowManager;
/*      */ import java.io.File;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class FileAssetManagerImpl extends Bn2Manager
/*      */   implements IAssetManagerImpl, AssetBankConstants, AttributeConstants
/*      */ {
/*  181 */   private DBTransactionManager m_transactionManager = null;
/*  182 */   protected FileStoreManager m_fileStoreManager = null;
/*  183 */   private AssetCategoryManager m_categoryManager = null;
/*  184 */   private ScheduleManager m_scheduleManager = null;
/*  185 */   private IAssetManager m_assetManager = null;
/*  186 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*  187 */   private HashMap m_hmTempUploadedFiles = new HashMap();
/*  188 */   private Object m_oSaveAssetLock = new Object();
/*  189 */   protected AttributeManager m_attributeManager = null;
/*  190 */   protected AttributeValueManager m_attributeValueManager = null;
/*  191 */   private AssetEntityManager m_assetEntityManager = null;
/*  192 */   protected AssetRepurposingManager m_assetRepurposingManager = null;
/*  193 */   private MultiLanguageSearchManager m_searchManager = null;
/*  194 */   private AgreementsManager m_agreementsManager = null;
/*  195 */   private AssetFeedbackManager m_feedbackManager = null;
/*  196 */   private WorkflowManager m_workflowManager = null;
/*  197 */   protected UsageManager m_usageManager = null;
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  215 */     super.startup();
/*      */ 
/*  218 */     TimerTask task = new TimerTask()
/*      */     {
/*      */       public void run()
/*      */       {
/*  222 */         FileAssetManagerImpl.this.cleanupUploadedFiles();
/*      */       }
/*      */     };
/*  226 */     int iHourOfDay = AssetBankSettings.getCleanupUploadedFilesHourOfDay();
/*  227 */     getScheduleManager().scheduleDailyTask(task, iHourOfDay, false);
/*      */   }
/*      */ 
/*      */   public long getAssetTypeId()
/*      */   {
/*  245 */     return 1L;
/*      */   }
/*      */ 
/*      */   public int getMaxPreviewHeight()
/*      */   {
/*  250 */     return AssetBankSettings.getPreviewImageMaxHeight();
/*      */   }
/*      */ 
/*      */   public int getMaxPreviewWidth()
/*      */   {
/*  255 */     return AssetBankSettings.getPreviewImageMaxWidth();
/*      */   }
/*      */ 
/*      */   public boolean canStoreFileFormat(FileFormat a_format)
/*      */   {
/*  275 */     return true;
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vVisibleAttributeIds, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  299 */     return getAsset(a_dbTransaction, a_lAssetId, null, a_vVisibleAttributeIds, a_bGetFeedback);
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId, Asset a_asset, Vector a_vVisibleAttributeIds, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  319 */     return getAsset(a_dbTransaction, a_lAssetId, a_asset, a_vVisibleAttributeIds, false, LanguageConstants.k_defaultLanguage, a_bGetFeedback);
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId, Asset a_asset, Vector a_vVisibleAttributeIds, Language a_language, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  341 */     return getAsset(a_dbTransaction, a_lAssetId, a_asset, a_vVisibleAttributeIds, false, a_language, a_bGetFeedback);
/*      */   }
/*      */ 
/*      */   private Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId, Asset a_asset, Vector a_vVisibleAttributeIds, boolean a_bGetImportedAsset, Language a_language, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  377 */     Asset asset = a_asset;
/*  378 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  380 */     if (transaction == null)
/*      */     {
/*  382 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  387 */       asset = this.m_assetManager.getAssetStaticOnly(transaction, a_lAssetId, a_asset, a_bGetImportedAsset);
/*      */ 
/*  389 */       AssetEntity entity = null;
/*      */ 
/*  391 */       if (asset != null)
/*      */       {
/*  395 */         Vector vEntityAttributes = null;
/*      */ 
/*  398 */         if ((asset.getEntity() != null) && (asset.getEntity().getId() > 0L))
/*      */         {
/*  400 */           entity = this.m_assetEntityManager.getEntity(transaction, asset.getEntity().getId(), true);
/*  401 */           entity.setLanguage(a_language);
/*  402 */           asset.setEntity(entity);
/*  403 */           vEntityAttributes = entity.getAllowableAttributes();
/*      */         }
/*      */ 
/*  407 */         if (AssetBankSettings.getAssetRepurposingEnabled())
/*      */         {
/*  409 */           asset.setHasRepurposedVersions(this.m_assetRepurposingManager.getHasRepurposedAssets(transaction, a_lAssetId));
/*      */         }
/*      */ 
/*  413 */         boolean bPopFromChild = false;
/*  414 */         if (StringUtils.isNotEmpty(asset.getChildAssetIdsAsString()))
/*      */         {
/*  416 */           bPopFromChild = true;
/*      */         }
/*      */ 
/*  421 */         asset.setDescriptiveCategories(getCategoryManager().getCategoriesForItem(transaction, 1L, asset.getId(), a_language));
/*  422 */         asset.setPermissionCategories(getCategoryManager().getCategoriesForItem(transaction, 2L, asset.getId(), a_language));
/*  423 */         asset.setApprovalStatuses(getCategoryManager().getApprovalStatusesForItem(transaction, asset.getId()));
/*      */ 
/*  426 */         asset.setAgreement(this.m_agreementsManager.getCurrentAgreementForAsset(transaction, a_lAssetId));
/*      */ 
/*  429 */         asset.setWorkflows(this.m_workflowManager.getWorkflowsForEntity(transaction, asset.getId()));
/*      */ 
/*  432 */         if ((a_bGetFeedback) && ((AssetBankSettings.getFeedbackComments()) || (AssetBankSettings.getFeedbackRatings())))
/*      */         {
/*  435 */           if (this.m_feedbackManager.assetCanBeRated(transaction, asset))
/*      */           {
/*  438 */             asset.setCanBeRated(true);
/*  439 */             asset.setAssetFeedback(this.m_feedbackManager.getFeedbackForAsset(transaction, asset.getId()));
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  444 */         setupAssetAttributeValues(transaction, asset, vEntityAttributes, a_vVisibleAttributeIds, a_language, bPopFromChild);
/*      */       }
/*      */ 
/*  448 */       if ((asset != null) && (asset.getEntity().getId() > 0L) && (StringUtils.isEmpty(asset.getFileLocation())) && ((StringUtils.isEmpty(asset.getThumbnailImageFile().getPath())) || (FileFormat.s_noFileFormat.getThumbnailImageLocation().equals(asset.getThumbnailImageFile().getPath())) || ((asset.getEntity().getThumbnailFilename() != null) && (asset.getThumbnailImageFile().getPath().endsWith("/" + asset.getEntity().getThumbnailFilename())))) && (StringUtils.isNotEmpty(asset.getChildAssetIdsAsString())) && (AssetBankSettings.getUseFirstChildAssetAsSurrogate()))
/*      */       {
/*  457 */         asset = populateFileDetailsFromChild(transaction, asset, !AssetBankSettings.getAgreementsEnabled());
/*      */       }
/*      */ 
/*  461 */       if (entity != null)
/*      */       {
/*  463 */         if ((asset.getFormat().equals(FileFormat.s_noFileFormat)) && ((StringUtils.isEmpty(asset.getThumbnailImageFile().getPath())) || (FileFormat.s_noFileFormat.getThumbnailImageLocation().equals(asset.getThumbnailImageFile().getPath()))) && (StringUtils.isNotEmpty(entity.getThumbnailFilename())))
/*      */         {
/*  468 */           asset.getThumbnailImageFile().setPath(StoredFileType.SHARED_THUMBNAIL.getDirectoryName() + "/" + entity.getThumbnailFilename());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  473 */       if ((AssetBankSettings.getAgreementsEnabled()) && (asset != null) && (asset.getEntity().getId() > 0L) && (StringUtils.isNotEmpty(asset.getParentAssetIdsAsString())) && (!asset.getEntity().getIncludesAttribute(400L)))
/*      */       {
/*  479 */         asset.setAgreementTypeId(this.m_agreementsManager.getAgreementStatusForAsset(transaction, StringUtil.getIdsArray(asset.getParentAssetIdsAsString())[0]));
/*  480 */         asset.setIsRestricted(asset.getAgreementTypeId() == 3L);
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  487 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  491 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  495 */           this.m_logger.error("SQL Exception whilst trying to close connection", sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  500 */     return asset;
/*      */   }
/*      */ 
/*      */   private void populateAttributesFromChildren(DBTransaction a_dbTransaction, Asset a_asset) throws Bn2Exception
/*      */   {
/*  505 */     Vector attVals = a_asset.getAttributeValues();
/*  506 */     for (int i = 0; i < attVals.size(); i++)
/*      */     {
/*  508 */       AttributeValue attVal = (AttributeValue)attVals.get(i);
/*  509 */       if (!attVal.getAttribute().getDataFromChildren())
/*      */         continue;
/*  511 */       attVal.setValue(this.m_attributeValueManager.getMergedAttributeValue(a_dbTransaction, attVal.getAttribute().getId(), StringUtil.getIdsArray(a_asset.getChildAssetIdsAsString())));
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<Asset> getAssets(DBTransaction a_dbTransaction, Vector a_vAssetIds, Vector a_vVisibleAttributeIds, boolean a_bGetRelatedAssetIds, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  528 */     return getAssets(a_dbTransaction, a_vAssetIds, a_vVisibleAttributeIds, -1, -1, a_bGetRelatedAssetIds, a_bGetFeedback);
/*      */   }
/*      */ 
/*      */   public Vector<Asset> getAssets(DBTransaction a_dbTransaction, Vector a_vAssetIds, Vector a_vVisibleAttributeIds, int a_iStartIndex, int a_iEndIndex, boolean a_bGetRelatedAssetIds, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/*  556 */     Vector vAssets = getAssets(a_dbTransaction, a_vAssetIds, a_vVisibleAttributeIds, a_iStartIndex, a_iEndIndex, -1L, -1, a_bGetRelatedAssetIds, a_bGetFeedback, false);
/*      */ 
/*  567 */     return vAssets;
/*      */   }
/*      */ 
/*      */   public Vector<Asset> getAssetsByIdAndBatchSize(DBTransaction a_dbTransaction, Vector a_vAssetIds, Vector a_vVisibleAttributeIds, long a_lStartId, int a_iBatchSize, boolean a_bGetRelatedAssetIds, boolean a_bGetFeedback, boolean a_bGetPreviousAgreements)
/*      */     throws Bn2Exception
/*      */   {
/*  594 */     return getAssets(a_dbTransaction, a_vAssetIds, a_vVisibleAttributeIds, -1, -1, a_lStartId, a_iBatchSize, a_bGetRelatedAssetIds, a_bGetFeedback, a_bGetPreviousAgreements);
/*      */   }
/*      */ 
/*      */   private Vector<Asset> getAssets(DBTransaction a_dbTransaction, Vector a_vAssetIds, Vector a_vVisibleAttributeIds, int a_iStartIndex, int a_iEndIndex, long a_iStartId, int a_iBatchSize, boolean a_bGetRelatedAssetIds, boolean a_bGetFeedback, boolean a_bGetPreviousAgreements)
/*      */     throws Bn2Exception
/*      */   {
/*  653 */     String ksMethodName = "getAssets";
/*      */ 
/*  655 */     Vector vAssets = new Vector();
/*  656 */     Connection con = null;
/*  657 */     ResultSet rs = null;
/*  658 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  661 */     if ((a_vAssetIds != null) && (a_vAssetIds.size() == 0))
/*      */     {
/*  663 */       return vAssets;
/*      */     }
/*      */ 
/*  666 */     if (transaction == null)
/*      */     {
/*  668 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*  671 */     String sSQL = null;
/*      */     try
/*      */     {
/*  674 */       con = transaction.getConnection();
/*  675 */       ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*      */ 
/*  677 */       sSQL = "SELECT ass.Id assId, ass.AssetTypeId assAssetTypeId, ass.Code, ass.FileLocation, ass.OriginalFileLocation, ass.FileSizeInBytes, ass.DateAdded, ass.DateLastModified, ass.ExpiryDate, ass.Author, ass.IsUnsubmitted, ass.Price, ass.ImportedAssetId, ass.IsPreviewRestricted, ass.IsSensitive, ass.Synchronised, ass.BulkUploadTimestamp, ass.CurrentVersionId, ass.VersionNumber, ass.NumViews, ass.NumDownloads, ass.LastDownloaded, ass.AssetEntityId, ass.OriginalFilename, ass.AgreementTypeId, ass.HasSubstituteFile, ass.IsBrandTemplate, u.Id userId, u.Username, u.Forename, u.Surname, u.EmailAddress, u2.Id lastModUserId, u2.Username lastModUsername, u2.Forename lastModForename, u2.Surname lastModSurname, u2.EmailAddress lastModEmailAddress, ff.Id ffId, ff.AssetTypeId ffAssetTypeId, ff.Name, ff.Description ffDescription, ff.FileExtension, ff.IsIndexable, ff.IsConvertable, ff.IsConversionTarget, ff.ThumbnailFileLocation ffThumbnailFileLocation, ff.ContentType, ff.ConverterClass, ff.ToTextConverterClass,ff.ViewFileInclude, ff.PreviewInclude, ff.PreviewWidth, ff.PreviewHeight, ff.ConvertIndividualLayers, ff.CanViewOriginal, ass.ThumbnailFileLocation assThumbnailFileLocation, ass.SmallFileLocation assSmallFileLocation, ass.MediumFileLocation assMediumFileLocation, ass.AdvancedViewing, ia.AssetId imageid, ia.Width imagewidth, ia.Height imageheight, ia.NumLayers numimagelayers, ia.LargeFileLocation, ia.UnwatermarkedLargeFileLocation, pa.AssetId paAssetId, fa.AssetId faAssetId, cea.Id ceaId, cea.ParentId ceaParentId, cea.Name ceaName, cea.CategoryTypeId ceaCategoryTypeId  FROM Asset ass LEFT JOIN AssetBankUser u ON ass.AddedByUserId = u.Id LEFT JOIN AssetBankUser u2 ON ass.LastModifiedByUserId = u2.Id LEFT JOIN FileFormat ff ON ass.FileFormatId = ff.Id LEFT JOIN ImageAsset ia ON ia.AssetId = ass.Id LEFT JOIN PromotedAsset pa ON ass.Id=pa.AssetId LEFT JOIN FeaturedAsset fa ON ass.Id=fa.AssetId LEFT JOIN CM_Category cea ON ass.Id=cea.ExtensionAssetId WHERE 1=1 ";
/*      */ 
/*  760 */       if ((a_vAssetIds != null) && (a_vAssetIds.size() > 0))
/*      */       {
/*  764 */         String sStatement = sqlGenerator.getIdInStatement(a_vAssetIds, "ass.Id");
/*  765 */         sSQL = sSQL + " AND " + sStatement;
/*      */       }
/*      */ 
/*  769 */       if (a_iStartId > 0L)
/*      */       {
/*  771 */         sSQL = sSQL + " AND ass.Id >= " + a_iStartId;
/*      */       }
/*      */ 
/*  775 */       sSQL = sSQL + " ORDER BY ass.Id ";
/*      */ 
/*  778 */       if (a_iBatchSize > 0)
/*      */       {
/*  780 */         sSQL = sqlGenerator.setRowLimit(sSQL, a_iBatchSize);
/*      */       }
/*      */ 
/*  784 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  786 */       rs = psql.executeQuery();
/*      */ 
/*  788 */       int iIndex = 0;
/*  789 */       boolean bLimitByIndex = false;
/*      */ 
/*  791 */       if ((a_iStartIndex >= 0) && (a_iEndIndex > 0) && (a_iStartIndex < a_iEndIndex))
/*      */       {
/*  793 */         bLimitByIndex = true;
/*      */       }
/*      */ 
/*  797 */       while ((rs.next()) && ((!bLimitByIndex) || (iIndex < a_iEndIndex)))
/*      */       {
/*  800 */         if ((!bLimitByIndex) || (iIndex >= a_iStartIndex))
/*      */         {
/*  808 */           Asset asset = null;
/*      */ 
/*  810 */           if (rs.getLong("imageid") > 0L)
/*      */           {
/*  813 */             asset = new ImageAsset();
/*  814 */             ((ImageAsset)asset).setWidth(rs.getInt("imagewidth"));
/*  815 */             ((ImageAsset)asset).setHeight(rs.getInt("imageheight"));
/*  816 */             ((ImageAsset)asset).setNumPages(rs.getInt("numimagelayers"));
/*      */ 
/*  818 */             if (StringUtil.stringIsPopulated(rs.getString("LargeFileLocation")))
/*      */             {
/*  820 */               ((ImageAsset)asset).setLargeImageFile(new ImageFile(rs.getString("LargeFileLocation")));
/*      */             }
/*      */ 
/*  823 */             if (StringUtil.stringIsPopulated(rs.getString("UnwatermarkedLargeFileLocation")))
/*      */             {
/*  825 */               ((ImageAsset)asset).setUnwatermarkedLargeImageFile(new ImageFile(rs.getString("UnwatermarkedLargeFileLocation")));
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  830 */             asset = new Asset();
/*      */           }
/*      */ 
/*  834 */           asset.setId(rs.getLong("assId"));
/*  835 */           asset.setTypeId(rs.getLong("assAssetTypeId"));
/*  836 */           asset.setCode(rs.getString("Code"));
/*  837 */           asset.setFileLocation(rs.getString("FileLocation"));
/*  838 */           asset.setOriginalFileLocation(rs.getString("OriginalFileLocation"));
/*  839 */           asset.setFileSizeInBytes(rs.getLong("FileSizeInBytes"));
/*  840 */           asset.setAuthor(rs.getString("Author"));
/*  841 */           asset.setDateAdded(rs.getTimestamp("DateAdded"));
/*  842 */           asset.setDateLastModified(rs.getTimestamp("DateLastModified"));
/*  843 */           asset.setExpiryDate(rs.getDate("ExpiryDate"));
/*  844 */           asset.setIsUnsubmitted(rs.getBoolean("IsUnsubmitted"));
/*  845 */           asset.getPrice().setAmount(rs.getLong("Price"));
/*  846 */           asset.setImportedAssetId(rs.getString("ImportedAssetId"));
/*  847 */           asset.setIsRestricted(rs.getBoolean("IsPreviewRestricted"));
/*  848 */           asset.setIsSensitive(rs.getBoolean("IsSensitive"));
/*  849 */           asset.setSynchronised(rs.getBoolean("Synchronised"));
/*  850 */           asset.setCurrentVersionId(rs.getLong("CurrentVersionId"));
/*  851 */           asset.setVersionNumber(rs.getInt("VersionNumber"));
/*  852 */           asset.setNumViews(rs.getInt("NumViews"));
/*  853 */           asset.setNumDownloads(rs.getInt("NumDownloads"));
/*  854 */           asset.setDateLastDownloaded(rs.getTimestamp("LastDownloaded"));
/*  855 */           asset.setOriginalFilename(rs.getString("OriginalFilename"));
/*  856 */           asset.setAgreementTypeId(rs.getLong("AgreementTypeId"));
/*  857 */           asset.setHasSubstituteFile(rs.getBoolean("HasSubstituteFile"));
/*  858 */           asset.setIsBrandTemplate(rs.getBoolean("IsBrandTemplate"));
/*  859 */           asset.setAdvancedViewing(rs.getBoolean("AdvancedViewing"));
/*  860 */           asset.setExtendsCategory(this.m_assetManager.buildExtendedCategoryInfo(a_dbTransaction, rs));
/*      */ 
/*  862 */           if (rs.getLong("AssetEntityId") > 0L)
/*      */           {
/*  864 */             asset.setEntity(new AssetEntity(rs.getLong("AssetEntityId")));
/*      */           }
/*      */ 
/*  867 */           if (rs.getLong("paAssetId") > 0L)
/*      */           {
/*  869 */             asset.setIsPromoted(true);
/*      */           }
/*      */ 
/*  872 */           if (rs.getLong("faAssetId") > 0L)
/*      */           {
/*  874 */             asset.setIsFeatured(true);
/*      */           }
/*      */ 
/*  877 */           if (rs.getTimestamp("BulkUploadTimestamp") != null)
/*      */           {
/*  879 */             asset.setBulkUploadTimestamp(new Date(rs.getTimestamp("BulkUploadTimestamp").getTime()));
/*      */           }
/*      */ 
/*  882 */           ABUser user = new ABUser();
/*  883 */           user.setId(rs.getLong("userId"));
/*  884 */           user.setForename(rs.getString("Forename"));
/*  885 */           user.setSurname(rs.getString("Surname"));
/*  886 */           user.setUsername(rs.getString("Username"));
/*  887 */           user.setEmailAddress(rs.getString("EmailAddress"));
/*      */ 
/*  889 */           asset.setAddedByUser(user);
/*      */ 
/*  892 */           user = new ABUser();
/*  893 */           user.setId(rs.getLong("lastModUserId"));
/*  894 */           user.setForename(rs.getString("lastModForename"));
/*  895 */           user.setSurname(rs.getString("lastModSurname"));
/*  896 */           user.setUsername(rs.getString("lastModUsername"));
/*  897 */           user.setEmailAddress(rs.getString("lastModEmailAddress"));
/*      */ 
/*  899 */           asset.setLastModifiedByUser(user);
/*      */ 
/*  901 */           addFileFormat(rs, asset);
/*      */ 
/*  903 */           AssetDBUtil.addImageFiles(rs, asset);
/*      */ 
/*  905 */           Vector vEntityAttributes = null;
/*  906 */           AssetEntity entity = null;
/*      */ 
/*  909 */           if ((asset.getEntity() != null) && (asset.getEntity().getId() > 0L))
/*      */           {
/*  911 */             entity = this.m_assetEntityManager.getEntity(transaction, asset.getEntity().getId(), true);
/*  912 */             asset.setEntity(entity);
/*  913 */             vEntityAttributes = entity.getAllowableAttributes();
/*      */           }
/*      */ 
/*  917 */           asset.setDescriptiveCategories(getCategoryManager().getCategoriesForItem(transaction, 1L, asset.getId()));
/*  918 */           asset.setPermissionCategories(getCategoryManager().getCategoriesForItem(transaction, 2L, asset.getId()));
/*  919 */           asset.setApprovalStatuses(getCategoryManager().getApprovalStatusesForItem(transaction, asset.getId()));
/*      */ 
/*  922 */           asset.setWorkflows(this.m_workflowManager.getWorkflowsForEntity(transaction, asset.getId()));
/*      */ 
/*  925 */           if (a_bGetRelatedAssetIds)
/*      */           {
/*  928 */             asset.setPeerAssetIdsAsString(this.m_assetRelationshipManager.getRelatedAssetIdString(transaction, asset.getId(), 1L));
/*  929 */             asset.setChildAssetIdsAsString(this.m_assetRelationshipManager.getRelatedAssetIdString(transaction, asset.getId(), 2L));
/*  930 */             asset.setParentAssetIdsAsString(this.m_assetRelationshipManager.getRelatedAssetIdString(transaction, asset.getId(), 3L));
/*  931 */             asset.setRelationshipDescriptions(this.m_assetRelationshipManager.getRelationshipDescriptionsForAssetAsTarget(a_dbTransaction, asset.getId()));
/*      */           }
/*      */ 
/*  936 */           boolean bPopFromChild = false;
/*  937 */           if (a_bGetRelatedAssetIds)
/*      */           {
/*  939 */             if (StringUtils.isNotEmpty(asset.getChildAssetIdsAsString()))
/*      */             {
/*  942 */               bPopFromChild = true;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  947 */           if ((a_bGetFeedback) && ((AssetBankSettings.getFeedbackComments()) || (AssetBankSettings.getFeedbackRatings())))
/*      */           {
/*  950 */             if (this.m_feedbackManager.assetCanBeRated(transaction, asset))
/*      */             {
/*  953 */               asset.setCanBeRated(true);
/*  954 */               asset.setAssetFeedback(this.m_feedbackManager.getFeedbackForAsset(transaction, asset.getId()));
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  959 */           setupAssetAttributeValues(transaction, asset, vEntityAttributes, a_vVisibleAttributeIds, LanguageConstants.k_defaultLanguage, bPopFromChild);
/*      */ 
/*  962 */           if ((asset.getEntity().getId() > 0L) && (StringUtils.isEmpty(asset.getFileLocation())) && ((StringUtils.isEmpty(asset.getThumbnailImageFile().getPath())) || (FileFormat.s_noFileFormat.getThumbnailImageLocation().equals(asset.getThumbnailImageFile().getPath()))) && (StringUtils.isNotEmpty(asset.getChildAssetIdsAsString())) && (AssetBankSettings.getUseFirstChildAssetAsSurrogate()))
/*      */           {
/*  969 */             asset = populateFileDetailsFromChild(transaction, asset, !AssetBankSettings.getAgreementsEnabled());
/*      */           }
/*      */ 
/*  973 */           if (entity != null)
/*      */           {
/*  975 */             if ((asset.getFormat().equals(FileFormat.s_noFileFormat)) && ((StringUtils.isEmpty(asset.getThumbnailImageFile().getPath())) || (FileFormat.s_noFileFormat.getThumbnailImageLocation().equals(asset.getThumbnailImageFile().getPath()))) && (StringUtils.isNotEmpty(entity.getThumbnailFilename())))
/*      */             {
/*  980 */               asset.getThumbnailImageFile().setPath(StoredFileType.SHARED_THUMBNAIL.getDirectoryName() + "/" + entity.getThumbnailFilename());
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  985 */           if ((AssetBankSettings.getAgreementsEnabled()) && (asset != null) && (asset.getEntity().getId() > 0L) && (StringUtils.isNotEmpty(asset.getParentAssetIdsAsString())) && (!asset.getEntity().getIncludesAttribute(400L)))
/*      */           {
/*  991 */             asset.setAgreementTypeId(this.m_agreementsManager.getAgreementStatusForAsset(transaction, StringUtil.getIdsArray(asset.getParentAssetIdsAsString())[0]));
/*  992 */             asset.setIsRestricted(asset.getAgreementTypeId() == 3L);
/*      */           }
/*      */ 
/*  996 */           if ((AssetBankSettings.getAgreementsEnabled()) && ((AssetBankSettings.getCanSearchAgreements()) || (a_bGetPreviousAgreements)))
/*      */           {
/*  998 */             asset.setAgreement(this.m_agreementsManager.getCurrentAgreementForAsset(transaction, asset.getId()));
/*      */ 
/* 1000 */             if (a_bGetPreviousAgreements)
/*      */             {
/* 1002 */               asset.setPreviousAgreements(this.m_agreementsManager.getAgreements(a_dbTransaction, false, null, asset.getId(), false));
/*      */             }
/*      */           }
/*      */ 
/* 1006 */           vAssets.add(asset);
/*      */         }
/*      */ 
/* 1009 */         iIndex++;
/*      */       }
/*      */ 
/* 1012 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1017 */       this.m_logger.error("SQLException in getAssets. SQL = " + sSQL);
/* 1018 */       throw new Bn2Exception("SQL Exception whilst getting some assets from the database : ", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1023 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1027 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1031 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1036 */     return vAssets;
/*      */   }
/*      */ 
/*      */   private void addFileFormat(ResultSet a_rs, Asset a_asset)
/*      */     throws SQLException
/*      */   {
/* 1047 */     if (a_rs.getLong("ffId") > 0L)
/*      */     {
/* 1049 */       FileFormat format = buildFileFormat(a_rs);
/*      */ 
/* 1051 */       a_asset.setFormat(format);
/*      */     }
/* 1053 */     else if (a_asset.getTypeId() > 0L)
/*      */     {
/* 1055 */       a_asset.setFormat(FileFormat.s_unknownFileFormat);
/*      */     }
/*      */     else
/*      */     {
/* 1059 */       a_asset.setFormat(FileFormat.s_noFileFormat);
/*      */     }
/*      */   }
/*      */ 
/*      */   private FileFormat buildFileFormat(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 1071 */     FileFormat format = new FileFormat();
/* 1072 */     format.setId(a_rs.getLong("ffId"));
/* 1073 */     format.setAssetTypeId(a_rs.getLong("ffAssetTypeId"));
/* 1074 */     format.setName(a_rs.getString("Name"));
/* 1075 */     format.setDescription(a_rs.getString("ffDescription"));
/* 1076 */     format.setFileExtension(a_rs.getString("FileExtension"));
/* 1077 */     format.setIndexable(a_rs.getBoolean("IsIndexable"));
/* 1078 */     format.setConvertable(a_rs.getBoolean("IsConvertable"));
/* 1079 */     format.setConversionTarget(a_rs.getBoolean("IsConversionTarget"));
/* 1080 */     format.setThumbnailImageLocation(a_rs.getString("ffThumbnailFileLocation"));
/* 1081 */     format.setContentType(a_rs.getString("ContentType"));
/* 1082 */     format.setConverterClass(a_rs.getString("ConverterClass"));
/* 1083 */     format.setAssetToTextConverterClass(a_rs.getString("ToTextConverterClass"));
/* 1084 */     format.setViewFileInclude(a_rs.getString("ViewFileInclude"));
/* 1085 */     format.setPreviewInclude(a_rs.getString("PreviewInclude"));
/* 1086 */     format.setPreviewHeight(a_rs.getInt("PreviewHeight"));
/* 1087 */     format.setPreviewWidth(a_rs.getInt("PreviewWidth"));
/* 1088 */     format.setCanConvertIndividualLayers(a_rs.getBoolean("ConvertIndividualLayers"));
/* 1089 */     format.setCanViewOriginal(a_rs.getBoolean("CanViewOriginal"));
/* 1090 */     return format;
/*      */   }
/*      */ 
/*      */   private Asset populateFileDetailsFromChild(DBTransaction a_dbTransaction, Asset a_asset, boolean a_bInheritRestrictedStatus)
/*      */     throws Bn2Exception
/*      */   {
/* 1102 */     Connection con = null;
/* 1103 */     PreparedStatement psql = null;
/* 1104 */     ResultSet rs = null;
/* 1105 */     DBTransaction transaction = a_dbTransaction;
/* 1106 */     String sSQL = null;
/* 1107 */     long lChildId = 0L;
/*      */ 
/* 1109 */     if ((!StringUtils.isEmpty(a_asset.getChildAssetIdsAsString())) && (a_asset.getChildAssetIdsAsString().trim().matches("[0-9]+.*")))
/*      */     {
/* 1111 */       lChildId = Long.parseLong(a_asset.getChildAssetIdsAsString().trim().split("[ ,]+")[0]);
/*      */     }
/*      */ 
/* 1115 */     if (lChildId <= 0L)
/*      */     {
/* 1117 */       return a_asset;
/*      */     }
/*      */ 
/* 1120 */     if (transaction == null)
/*      */     {
/* 1122 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
                AudioAsset audioAsset;
/*      */ 
/*      */     try
/*      */     {
/* 1127 */       con = transaction.getConnection();
/*      */ 
/* 1137 */       sSQL = "SELECT ass.ThumbnailFileLocation assThumbnailFileLocation, ass.SmallFileLocation assSmallFileLocation, ass.MediumFileLocation assMediumFileLocation, ass.IsPreviewRestricted assIsPreviewRestricted, ass.FileSizeInBytes assFileSizeInBytes, ff.Id ffId, ff.AssetTypeId ffAssetTypeId, ff.Name, ff.Description ffDescription, ff.FileExtension, ff.IsIndexable, ff.IsConvertable, ff.IsConversionTarget, ff.ThumbnailFileLocation ffThumbnailFileLocation, ff.ContentType, ff.ConverterClass, ff.ToTextConverterClass,ff.ViewFileInclude, ff.PreviewInclude, ff.PreviewWidth, ff.PreviewHeight, ff.ConvertIndividualLayers, ff.CanViewOriginal, va.AssetId videoId, va.EmbeddedPreviewClipLocation, va.PreviewClipLocation, va.Width vidWidth, va.Height vidHeight, va.Duration vidDuration, ia.AssetId imageId, ia.Width imagewidth, ia.Height imageheight, audioa.AssetId audioId, audioa.PreviewClipLocation audPreviewClipLocation, audioa.EmbeddedPreviewClipLocation audEmbeddedPreviewClipLocation, audioa.Duration audDuration  FROM Asset ass LEFT JOIN FileFormat ff ON ass.FileFormatId = ff.Id LEFT JOIN ImageAsset ia ON ia.AssetId = ass.Id LEFT JOIN VideoAsset va ON va.AssetId = ass.Id LEFT JOIN AudioAsset audioa ON audioa.AssetId = ass.Id WHERE ass.Id=? ";
/*      */ 
/* 1180 */       psql = con.prepareStatement(sSQL);
/* 1181 */       psql.setLong(1, lChildId);
/*      */ 
/* 1183 */       rs = psql.executeQuery();
/*      */ 
/* 1185 */       if (rs.next())
/*      */       {
/* 1187 */         if (rs.getLong("videoId") > 0L)
/*      */         {
/* 1189 */           a_asset = new VideoAsset(a_asset);
/* 1190 */           ((VideoAsset)a_asset).setDuration(rs.getInt("vidDuration"));
/* 1191 */           ((VideoAsset)a_asset).setHeight(rs.getInt("vidHeight"));
/* 1192 */           ((VideoAsset)a_asset).setWidth(rs.getInt("vidWidth"));
/* 1193 */           ((VideoAsset)a_asset).setEmbeddedPreviewClipLocation(rs.getString("EmbeddedPreviewClipLocation"));
/* 1194 */           ((VideoAsset)a_asset).setPreviewClipLocation(rs.getString("PreviewClipLocation"));
/*      */         }
/* 1196 */         else if (rs.getLong("imageId") > 0L)
/*      */         {
/* 1198 */           a_asset = new ImageAsset(a_asset);
/* 1199 */           ((ImageAsset)a_asset).setHeight(rs.getInt("imageheight"));
/* 1200 */           ((ImageAsset)a_asset).setWidth(rs.getInt("imagewidth"));
/*      */         }
/* 1202 */         else if (rs.getLong("audioId") > 0L)
/*      */         {
/* 1204 */           audioAsset = new AudioAsset(a_asset);
/* 1205 */           AudioAssetDBUtil.populateAudioAssetFromRS(audioAsset, transaction, getAssetManager(), rs);
/* 1206 */           a_asset = audioAsset;
/*      */         }
/*      */ 
/* 1212 */         if ((a_bInheritRestrictedStatus) && (StringUtils.isNotEmpty(rs.getString("assThumbnailFileLocation"))))
/*      */         {
/* 1214 */           a_asset.setIsRestricted(rs.getBoolean("assIsPreviewRestricted"));
/*      */         }
/* 1216 */         AssetDBUtil.addImageFiles(rs, a_asset);
/* 1217 */         addFileFormat(rs, a_asset);
/*      */ 
/* 1219 */         a_asset.setFileSizeInBytes(rs.getLong("assFileSizeInBytes"));
/* 1220 */         a_asset.setTypeId(a_asset.getFormat().getAssetTypeId());
/* 1221 */         a_asset.setSurrogateAssetId(lChildId);
/*      */ 
/* 1223 */         if (rs.getLong("ffAssetTypeId") > 0L)
/*      */         {
/* 1225 */           a_asset.setTypeId(rs.getLong("ffAssetTypeId"));
/*      */         }
/*      */       }
/*      */ 
/* 1229 */       psql.close();
/*      */ 
/* 1231 */       audioAsset =(AudioAsset) a_asset;
/*      */       return audioAsset;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1235 */       this.m_logger.error("SQL Exception whilst getting file details from child asset : " + e);
/* 1236 */       throw new Bn2Exception("SQL Exception whilst getting file details from child asset : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1241 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1245 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1249 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
                      throw new Bn2Exception(sqle.getMessage());
/*      */         }
/*      */       }
/* 1250 */     } //throw localObject;
/*      */   }
/*      */ 
/*      */   public void setupAssetAttributeValues(DBTransaction a_dbTransaction, Asset a_asset, Vector a_vecValidFlexibleAttributeIds, Vector a_vecVisibleAttributeIds, Language a_language, boolean a_bPopulateAttributesFromChild)
/*      */     throws Bn2Exception
/*      */   {
/* 1272 */     a_asset.setAttributeValues(this.m_attributeValueManager.getAttributesForAsset(a_dbTransaction, a_asset, a_vecValidFlexibleAttributeIds, a_vecVisibleAttributeIds, a_language));
/*      */ 
/* 1275 */     for (int i = 0; i < a_asset.getAttributeValues().size(); i++)
/*      */     {
/* 1278 */       AttributeValue temp = (AttributeValue)a_asset.getAttributeValues().elementAt(i);
/*      */ 
/* 1280 */       if (!temp.getAttribute().getIsNameAttribute())
/*      */         continue;
/* 1282 */       temp.setLanguage(a_language);
/*      */ 
/* 1284 */       a_asset.setSearchName(temp.getValue());
/*      */ 
/* 1286 */       if (StringUtil.stringIsPopulated(a_asset.getSearchName()))
/*      */       {
/*      */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1293 */     if (a_bPopulateAttributesFromChild)
/*      */     {
/* 1295 */       populateAttributesFromChildren(a_dbTransaction, a_asset);
/*      */     }
/*      */ 
/* 1301 */     for (long lDisplayGroupId : AttributeConstants.k_aDisplayAttributeGroups)
/*      */     {
/* 1303 */       Vector vecAllDisplayAttributes = this.m_attributeManager.getDisplayAttributes(a_dbTransaction, lDisplayGroupId);
/* 1304 */       Vector vecDisplayAttributeValues = new Vector();
/*      */ 
/* 1307 */       if (vecAllDisplayAttributes != null)
/*      */       {
/* 1310 */         Vector vParentIds = StringUtil.convertToVectorOfLongs(a_asset.getParentAssetIdsAsString(), ",");
/* 1311 */         if ((vParentIds != null) && (AssetBankSettings.getIncludeParentMetadataForSearch()))
/*      */         {
/* 1313 */           for (int i = 0; i < vParentIds.size(); i++)
/*      */           {
/* 1315 */             long lParentId = ((Long)vParentIds.get(i)).longValue();
/* 1316 */             if (lParentId <= 0L)
/*      */               continue;
/* 1318 */             Vector vecParentAttributeValues = this.m_attributeValueManager.getAttributesForAsset(a_dbTransaction, lParentId, null, a_vecVisibleAttributeIds, a_language);
/* 1319 */             addParentDisplayAttributes(a_dbTransaction, a_language, vecParentAttributeValues, vecAllDisplayAttributes, vecDisplayAttributeValues);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1325 */         addAssetDisplayAttributes(a_dbTransaction, a_language, a_asset, vecAllDisplayAttributes, vecDisplayAttributeValues);
/*      */       }
/* 1327 */       a_asset.setDisplayAttributes(lDisplayGroupId, vecDisplayAttributeValues);
/*      */     }
/*      */ 
/* 1331 */     a_asset.setAPIAttributes(AttributeUtil.getAPIAttributeString(a_asset.getDisplayAttributes(1L), a_language.getCode()));
/*      */   }
/*      */ 
/*      */   private void addAssetDisplayAttributes(DBTransaction a_dbTransaction, Language a_language, Asset a_asset, Vector<DisplayAttribute> vecAllDisplayAttributes, Vector<DisplayAttribute> vecDisplayAttributeValues)
/*      */   {
/* 1350 */     if (a_asset.getAttributeValues() == null)
/*      */     {
/* 1352 */       return;
/*      */     }
/*      */ 
/* 1356 */     for (int i = 0; i < vecAllDisplayAttributes.size(); i++)
/*      */     {
/* 1359 */       DisplayAttribute tempDispAtt = (DisplayAttribute)vecAllDisplayAttributes.elementAt(i);
/* 1360 */       DisplayAttribute dispAtt = new DisplayAttribute(tempDispAtt);
/*      */ 
/* 1363 */       for (int x = 0; x < a_asset.getAttributeValues().size(); x++)
/*      */       {
/* 1365 */         AttributeValue temp = (AttributeValue)a_asset.getAttributeValues().elementAt(x);
/*      */ 
/* 1367 */         if ((temp == null) || (temp.getAttribute().getId() != dispAtt.getAttribute().getId()))
/*      */           continue;
/* 1369 */         String sValue = AttributeUtil.getAttributeValueAsString(a_asset, temp, a_language);
/* 1370 */         dispAtt.setDisplayValue(sValue);
/*      */ 
/* 1373 */         Iterator itTranslations = temp.getTranslations().iterator();
/* 1374 */         while (itTranslations.hasNext())
/*      */         {
/* 1376 */           AttributeValue.Translation translation = (AttributeValue.Translation)itTranslations.next();
/* 1377 */           if (StringUtils.isNotEmpty(translation.getValue()))
/*      */           {
/* 1379 */             dispAtt.setTranslation(translation.getLanguage().getCode(), translation.getValue());
/*      */           }
/*      */         }
/* 1382 */         vecDisplayAttributeValues.add(dispAtt);
/* 1383 */         break;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addParentDisplayAttributes(DBTransaction a_dbTransaction, Language a_language, Vector<AttributeValue> a_vecAttributeValues, Vector<DisplayAttribute> vecAllDisplayAttributes, Vector<DisplayAttribute> vecDisplayAttributeValues)
/*      */   {
/* 1402 */     if (a_vecAttributeValues == null)
/*      */     {
/* 1404 */       return;
/*      */     }
/*      */ 
/* 1408 */     for (int i = 0; i < vecAllDisplayAttributes.size(); i++)
/*      */     {
/* 1411 */       DisplayAttribute tempDispAtt = (DisplayAttribute)vecAllDisplayAttributes.elementAt(i);
/* 1412 */       DisplayAttribute dispAtt = new DisplayAttribute(tempDispAtt);
/*      */ 
/* 1415 */       if ((dispAtt == null) || (!dispAtt.getShowOnChild()) || (!dispAtt.getAttribute().getShowOnChild()))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/* 1421 */       for (int x = 0; x < a_vecAttributeValues.size(); x++)
/*      */       {
/* 1423 */         AttributeValue temp = (AttributeValue)a_vecAttributeValues.elementAt(x);
/*      */ 
/* 1425 */         if ((temp == null) || (temp.getAttribute().getId() != dispAtt.getAttribute().getId()))
/*      */           continue;
/* 1427 */         String sValue = AttributeUtil.getFlexibleAttributeValueAsString(temp, a_language);
/* 1428 */         dispAtt.setDisplayValue(sValue);
/*      */ 
/* 1431 */         Iterator itTranslations = temp.getTranslations().iterator();
/* 1432 */         while (itTranslations.hasNext())
/*      */         {
/* 1434 */           AttributeValue.Translation translation = (AttributeValue.Translation)itTranslations.next();
/* 1435 */           if (StringUtils.isNotEmpty(translation.getValue()))
/*      */           {
/* 1437 */             dispAtt.setTranslation(translation.getLanguage().getCode(), translation.getValue());
/*      */           }
/*      */         }
/* 1440 */         vecDisplayAttributeValues.add(dispAtt);
/* 1441 */         break;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getDownloadableAssetPath(Asset a_asset, String a_sConvertToFileExtension, AssetConversionInfo a_conversionInfo)
/*      */     throws Bn2Exception
/*      */   {
/* 1470 */     if (a_asset != null)
/*      */     {
/* 1472 */       return a_asset.getFileLocation();
/*      */     }
/*      */ 
/* 1475 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean canConvertToFileFormat(Asset a_asset, FileFormat a_format)
/*      */     throws Bn2Exception
/*      */   {
/* 1498 */     return false;
/*      */   }
/*      */ 
/*      */   public Asset saveAsset(DBTransaction a_dbTransaction, Asset a_asset, AssetFileSource a_source, long a_lUserId, AssetConversionInfo a_conversionInfo, AssetFileSource a_thumbnailSource, boolean a_bForceThumbnailRegeneration, boolean a_bForcePreviewRegeneration, int a_iSaveTypeId)
/*      */     throws Bn2Exception, AssetFileReadException
/*      */   {
/* 1531 */     String sAssetFileLocation = null;
/* 1532 */     long lFileSize = 0L;
/* 1533 */     boolean bConversionFailed = false;
/*      */ 
/* 1536 */     if ((a_source != null) && (a_source.isValid()))
/*      */     {
/* 1539 */       if ((!a_asset.getHasSubstituteFile()) && (StringUtils.isNotEmpty(a_asset.getFileLocation())))
/*      */       {
/* 1542 */         this.m_fileStoreManager.deleteFile(a_asset.getFileLocation());
/*      */       }
/*      */ 
/* 1545 */       if (StringUtils.isNotEmpty(a_asset.getOriginalFileLocation()))
/*      */       {
/* 1547 */         this.m_fileStoreManager.deleteFile(a_asset.getOriginalFileLocation());
/* 1548 */         a_asset.setOriginalFileLocation(null);
/*      */       }
/*      */ 
/* 1552 */       String sConvertedLocation = null;
/* 1553 */       if ((a_source.getStoredFileLocation() != null) && (a_source.getStoredFileLocation().length() > 0))
/*      */       {
/* 1557 */         sAssetFileLocation = a_source.getStoredFileLocation();
/*      */ 
/* 1560 */         UploadedFileInfo uploadedFileInfo = getUploadedFileInfo(a_source.getStoredFileLocation(), true);
/*      */ 
/* 1562 */         if (uploadedFileInfo != null)
/*      */         {
/* 1565 */           sConvertedLocation = uploadedFileInfo.getConvertedLocation();
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1573 */         sAssetFileLocation = this.m_fileStoreManager.addFile(a_source.getInputStream(), a_source.getFilename(), StoredFileType.ASSET);
/*      */       }
/*      */ 
/* 1579 */       File fNewFile = new File(this.m_fileStoreManager.getAbsolutePath(sAssetFileLocation));
/* 1580 */       lFileSize = fNewFile.length();
/*      */ 
/* 1584 */       if (sConvertedLocation != null)
/*      */       {
/* 1586 */         a_asset.setOriginalFileLocation(sAssetFileLocation);
/* 1587 */         sAssetFileLocation = sConvertedLocation;
/*      */ 
/* 1590 */         this.m_fileStoreManager.storeFile(sAssetFileLocation);
/*      */       }
/* 1593 */       else if ((StringUtils.isNotEmpty(a_asset.getFormat().getConverterClass())) && ((a_conversionInfo == null) || (!a_conversionInfo.getDeferThumbnailGeneration())))
/*      */       {
/*      */         try
/*      */         {
/* 1597 */           a_asset.setOriginalFileLocation(sAssetFileLocation);
/* 1598 */           sAssetFileLocation = createConvertedVersion(a_asset, a_source.getFilename(), sAssetFileLocation);
/*      */         }
/*      */         catch (Throwable e)
/*      */         {
/* 1603 */           this.m_logger.debug(getClass().getSimpleName() + " : Error converting file: ", e);
/* 1604 */           bConversionFailed = true;
/*      */         }
/*      */ 
/*      */       }
/* 1609 */       else if ((a_asset.getIsImage()) && (!a_asset.getHasSubstituteFile()))
/*      */       {
/* 1611 */         a_asset.setOriginalFileLocation(null);
/*      */       }
/* 1613 */       else if (a_asset.getHasSubstituteFile())
/*      */       {
/* 1615 */         a_asset.setOriginalFileLocation(sAssetFileLocation);
/*      */       }
/*      */ 
/* 1620 */       a_asset.setFileLocation(sAssetFileLocation);
/*      */ 
/* 1623 */       a_asset.setFileSizeInBytes(lFileSize);
/*      */ 
/* 1626 */       a_asset.setOriginalFilename(a_source.getOriginalFilename());
/*      */     }
/* 1631 */     else if (((a_asset.getId() <= 0L) || ((a_source != null) && (a_source.getIsNewWithFixedId()))) && (StringUtils.isNotEmpty(a_asset.getFileLocation())))
/*      */     {
/* 1633 */       File tmpFile = new File(this.m_fileStoreManager.getAbsolutePath(a_asset.getFileLocation()));
/* 1634 */       String sFileLocation = this.m_fileStoreManager.addFile(tmpFile, StoredFileType.ASSET);
/* 1635 */       a_asset.setFileLocation(sFileLocation);
/*      */ 
/* 1638 */       a_asset.setOriginalFileLocation(copyFile(a_asset.getOriginalFileLocation(), StoredFileType.ASSET));
/* 1639 */       a_asset.getThumbnailImageFile().setPath(copyFile(a_asset.getThumbnailImageFile().getPath(), StoredFileType.PREVIEW_OR_THUMBNAIL));
/* 1640 */       a_asset.getHomogenizedImageFile().setPath(copyFile(a_asset.getHomogenizedImageFile().getPath(), StoredFileType.PREVIEW_OR_THUMBNAIL));
/* 1641 */       a_asset.getPreviewImageFile().setPath(copyFile(a_asset.getPreviewImageFile().getPath(), StoredFileType.PREVIEW_OR_THUMBNAIL));
/*      */ 
/* 1643 */       a_asset.setImportedAssetId(FileUtil.getFilenameWithoutSuffix(sFileLocation, true));
/*      */     }
/*      */ 
/* 1647 */     boolean bGenerateThumbnails = getNeedToGenerateThumbnails(a_source, a_thumbnailSource, a_conversionInfo);
/*      */ 
/* 1650 */     boolean bOldThumbnailsRemoved = false;
/*      */ 
/* 1653 */     if ((a_thumbnailSource != null) && (a_thumbnailSource.getRemove()))
/*      */     {
/* 1656 */       if (a_asset.getHasConvertedImages())
/*      */       {
/* 1658 */         deleteGeneratedImages(a_asset, null);
/* 1659 */         bOldThumbnailsRemoved = true;
/*      */ 
/* 1663 */         if ((a_asset.getTypeId() != 1L) || (StringUtils.isEmpty(a_asset.getFormat().getConverterClass())))
/*      */         {
/* 1665 */           this.m_fileStoreManager.deleteFile(a_asset.getFileLocation());
/*      */ 
/* 1667 */           a_asset.setHasSubstituteFile(false);
/* 1668 */           a_asset.setFileLocation(a_asset.getOriginalFileLocation());
/*      */ 
/* 1670 */           if (a_asset.getIsImage())
/*      */           {
/* 1672 */             a_asset.setOriginalFileLocation(null);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/* 1678 */       else if (a_asset.getId() > 0L)
/*      */       {
/* 1680 */         bGenerateThumbnails = ((a_source == null) || (!a_source.isValid())) && (StringUtils.isNotEmpty(a_asset.getFormat().getConverterClass())) && (!bConversionFailed);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1687 */       bGenerateThumbnails |= ((a_source != null) && (a_source.isValid()) && (((StringUtils.isNotEmpty(a_asset.getFormat().getConverterClass())) && (!bConversionFailed)) || ((a_asset.getTypeId() == 1L) && (a_asset.getFormat().getIsConvertable()))));
/*      */     }
/*      */ 
/* 1692 */     if ((bGenerateThumbnails) || (a_bForceThumbnailRegeneration))
/*      */     {
/* 1694 */       boolean bUseConverterForThumbnails = (a_asset.getFormat().getAssetTypeId() == 1L) && (StringUtils.isNotEmpty(a_asset.getFormat().getConverterClass()));
/* 1695 */       String sThumbnailSourceId = null;
/*      */ 
/* 1697 */       if ((a_thumbnailSource != null) && (a_thumbnailSource.isValid()))
/*      */       {
/* 1700 */         sThumbnailSourceId = this.m_fileStoreManager.addFile(a_thumbnailSource.getInputStream(), a_thumbnailSource.getFilename(), StoredFileType.TEMP);
/*      */       }
/* 1704 */       else if ((bUseConverterForThumbnails) || ((a_asset.getTypeId() == 1L) && (a_asset.getFormat().getIsConvertable())))
/*      */       {
/* 1706 */         sThumbnailSourceId = a_asset.getFileLocation();
/*      */       }
/* 1708 */       else if (((a_asset instanceof VideoAsset)) && (a_thumbnailSource != null) && (a_thumbnailSource.getRemove()))
/*      */       {
/* 1711 */         sThumbnailSourceId = FileUtil.getFilepathWithoutSuffix(a_asset.getFileLocation());
/* 1712 */         sThumbnailSourceId = sThumbnailSourceId + "." + "png";
/* 1713 */         sThumbnailSourceId = this.m_fileStoreManager.getUniqueFilepath(sThumbnailSourceId, StoredFileType.TEMP);
/*      */ 
/* 1715 */         if (StringUtil.stringIsPopulated(a_asset.getOriginalFileLocation()))
/*      */         {
/* 1717 */           VideoUtil.createPreviewImage(this.m_fileStoreManager.getAbsolutePath(a_asset.getOriginalFileLocation()), this.m_fileStoreManager.getAbsolutePath(sThumbnailSourceId), 0);
/*      */         }
/*      */         else
/*      */         {
/* 1721 */           VideoUtil.createPreviewImage(this.m_fileStoreManager.getAbsolutePath(a_asset.getFileLocation()), this.m_fileStoreManager.getAbsolutePath(sThumbnailSourceId), 0);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1726 */       if ((a_conversionInfo == null) || (!a_conversionInfo.getDeferThumbnailGeneration()))
/*      */       {
/* 1728 */         createImageVersions(a_asset, sThumbnailSourceId, a_conversionInfo, a_iSaveTypeId, null);
/* 1729 */         bOldThumbnailsRemoved = true;
/*      */       }
/*      */ 
/* 1733 */       if ((sThumbnailSourceId != null) && (!bUseConverterForThumbnails) && (!sThumbnailSourceId.equals(a_asset.getFileLocation())))
/*      */       {
/* 1735 */         this.m_fileStoreManager.deleteFile(sThumbnailSourceId);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1740 */     if ((a_source != null) && (a_source.isValid()) && (a_asset.getId() > 0L) && (!bOldThumbnailsRemoved))
/*      */     {
/* 1742 */       deleteGeneratedImages(a_asset, null);
/*      */     }
/*      */ 
/* 1745 */     boolean bNew = false;
/* 1746 */     if ((a_source != null) && (a_source.getIsNewWithFixedId()))
/*      */     {
/* 1748 */       bNew = true;
/*      */     }
/*      */ 
/* 1752 */     saveAssetToDatabase(a_dbTransaction, a_asset, a_lUserId, sAssetFileLocation != null, bNew);
/*      */ 
/* 1754 */     return a_asset;
/*      */   }
/*      */ 
/*      */   protected String copyFile(String sSource, StoredFileType a_fileType)
/*      */     throws Bn2Exception
/*      */   {
/* 1765 */     String sDest = null;
/*      */ 
/* 1767 */     if (StringUtils.isNotEmpty(sSource))
/*      */     {
/* 1769 */       String sDir = this.m_fileStoreManager.getStorageDirectory(sSource);
/* 1770 */       File tmpFile = new File(this.m_fileStoreManager.getAbsolutePath(sSource));
/* 1771 */       sDest = this.m_fileStoreManager.addFile(tmpFile, a_fileType, sDir);
/*      */     }
/* 1773 */     return sDest;
/*      */   }
/*      */ 
/*      */   protected boolean getNeedToGenerateThumbnails(AssetFileSource a_source, AssetFileSource a_thumbnailSource, AssetConversionInfo a_conversionInf)
/*      */   {
/* 1795 */     return (a_thumbnailSource != null) && (a_thumbnailSource.isValid());
/*      */   }
/*      */ 
/*      */   public void createImageVersions(Asset a_asset, String a_sSourceFileId, AssetConversionInfo a_conversionInfo, int a_iSaveTypeId, Set<GeneratedImageVersion> a_versionsToCreate)
/*      */     throws Bn2Exception, AssetFileReadException
/*      */   {
/*      */     try
/*      */     {
/* 1828 */       String sSource = this.m_fileStoreManager.getAbsolutePath(a_sSourceFileId);
/*      */ 
/* 1830 */       String sSourceFilenameForNaming = null;
/*      */ 
/* 1833 */       if (StoredFileType.TEMP.equals(StoredFileType.getTypeForPath(a_sSourceFileId)))
/*      */       {
/* 1835 */         sSourceFilenameForNaming = this.m_fileStoreManager.getUniqueFilepath(FileUtil.getFilename(a_sSourceFileId), StoredFileType.PREVIEW_OR_THUMBNAIL);
/*      */       }
/*      */       else
/*      */       {
/* 1840 */         sSourceFilenameForNaming = a_sSourceFileId;
/*      */       }
/*      */ 
/* 1844 */       String sRgbColorProfile = this.m_usageManager.getColorSpace(null, 1).getFileLocation();
/* 1845 */       String sCmykColorProfile = this.m_usageManager.getColorSpace(null, 2).getFileLocation();
/*      */ 
/* 1848 */       ImageFileInfo imageInfo = ABImageMagick.getInfo(sSource);
/*      */ 
/* 1850 */       int iSourceWidth = imageInfo.getWidth();
/* 1851 */       int iSourceHeight = imageInfo.getHeight();
/* 1852 */       boolean bIsCMYK = ABImageMagick.getIsCMYK(imageInfo.getColorSpace());
/* 1853 */       int[] aiPreviewDim = null;
/*      */ 
/* 1856 */       deleteGeneratedImages(a_asset, a_versionsToCreate);
/*      */ 
/* 1859 */       if ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.PREVIEW)))
/*      */       {
/* 1862 */         int iPreviewImageWidth = getMaxPreviewWidth();
/* 1863 */         int iPreviewImageHeight = getMaxPreviewHeight();
/*      */ 
/* 1866 */         if ((iPreviewImageWidth > iSourceWidth) && (iPreviewImageHeight > iSourceHeight))
/*      */         {
/* 1868 */           iPreviewImageWidth = iSourceWidth;
/* 1869 */           iPreviewImageHeight = iSourceHeight;
/*      */         }
/*      */ 
/* 1873 */         String sTempFilename = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(sSourceFilenameForNaming, "-m.jpg", StoredFileType.PREVIEW_OR_THUMBNAIL);
/*      */ 
/* 1878 */         aiPreviewDim = ABImageMagick.resizeToJpg(sSource, this.m_fileStoreManager.getAbsolutePath(sTempFilename), iSourceWidth, iSourceHeight, iPreviewImageWidth, iPreviewImageHeight, bIsCMYK, sRgbColorProfile, sCmykColorProfile, imageInfo.getNumberOfLayers());
/*      */ 
/* 1890 */         this.m_logger.trace("Generated medium/preview image for asset id=" + a_asset.getId());
/*      */ 
/* 1892 */         a_asset.setPreviewImageFile(new ImageFile(sTempFilename));
/*      */ 
/* 1895 */         sSource = this.m_fileStoreManager.getAbsolutePath(sTempFilename);
/* 1896 */         iSourceWidth = aiPreviewDim[0];
/* 1897 */         iSourceHeight = aiPreviewDim[1];
/* 1898 */         bIsCMYK = false;
/*      */       }
/*      */ 
/* 1902 */       if ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.THUMBNAIL)))
/*      */       {
/* 1905 */         int iThumbImageWidth = AssetBankSettings.getThumbnailImageMaxWidth();
/* 1906 */         int iThumbImageHeight = AssetBankSettings.getThumbnailImageMaxHeight();
/*      */ 
/* 1908 */         String sTempFilename = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(sSourceFilenameForNaming, "-s.jpg", StoredFileType.PREVIEW_OR_THUMBNAIL);
/*      */ 
/* 1912 */         ABImageMagick.resizeToJpg(sSource, this.m_fileStoreManager.getAbsolutePath(sTempFilename), iSourceWidth, iSourceHeight, iThumbImageWidth, iThumbImageHeight, bIsCMYK, sRgbColorProfile, sCmykColorProfile, imageInfo.getNumberOfLayers());
/*      */ 
/* 1924 */         this.m_logger.trace("Generated thumbnail image for asset id=" + a_asset.getId());
/*      */ 
/* 1926 */         a_asset.setThumbnailImageFile(new ImageFile(sTempFilename));
/*      */       }
/*      */ 
/* 1930 */       if ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.HOMOGENIZED)))
/*      */       {
/* 1932 */         String sTempFilename = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(sSourceFilenameForNaming, "-t.jpg", StoredFileType.PREVIEW_OR_THUMBNAIL);
/*      */ 
/* 1936 */         if (AssetBankSettings.getThumbnailsCroppedNotScaled())
/*      */         {
/* 1938 */           ABImageMagick.resizeAndCropToJpg(sSource, this.m_fileStoreManager.getAbsolutePath(sTempFilename), iSourceWidth, iSourceHeight, AssetBankSettings.getHomogenizedImageMaxWidth(), AssetBankSettings.getHomogenizedImageMaxHeight(), bIsCMYK, sRgbColorProfile, sCmykColorProfile, imageInfo.getNumberOfLayers());
/*      */ 
/* 1950 */           this.m_logger.trace("Generated cropped homogenised small image for asset id=" + a_asset.getId());
/*      */         }
/*      */         else
/*      */         {
/* 1954 */           ABImageMagick.resizeToJpg(sSource, this.m_fileStoreManager.getAbsolutePath(sTempFilename), iSourceWidth, iSourceHeight, AssetBankSettings.getHomogenizedImageMaxWidth(), AssetBankSettings.getHomogenizedImageMaxHeight(), bIsCMYK, sRgbColorProfile, sCmykColorProfile, imageInfo.getNumberOfLayers());
/*      */ 
/* 1966 */           this.m_logger.trace("Generated homogenised small image for asset id=" + a_asset.getId());
/*      */         }
/*      */ 
/* 1969 */         a_asset.setHomogenizedImageFile(new ImageFile(sTempFilename));
/*      */       }
/*      */ 
/* 1973 */       if ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.PREVIEW)))
/*      */       {
/* 1975 */         if ((AssetBankSettings.getWatermarkPreview()) && ((a_conversionInfo == null) || (!a_conversionInfo.getSupressWatermark())))
/*      */         {
/* 1977 */           ABImageMagick.addWatermark(this.m_fileStoreManager.getAbsolutePath(a_asset.getPreviewImageFile().getPath()), this.m_fileStoreManager.getAbsolutePath(a_asset.getPreviewImageFile().getPath()), aiPreviewDim[0], aiPreviewDim[1], null, false);
/*      */ 
/* 1985 */           this.m_logger.trace("Added watermark to preview image for asset id=" + a_asset.getId());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1990 */       if ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.PREVIEW)))
/*      */       {
/* 1992 */         this.m_fileStoreManager.storeFile(a_asset.getPreviewImageFile().getPath());
/*      */       }
/* 1994 */       if ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.HOMOGENIZED)))
/*      */       {
/* 1996 */         this.m_fileStoreManager.storeFile(a_asset.getHomogenizedImageFile().getPath());
/*      */       }
/* 1998 */       if ((a_versionsToCreate == null) || (a_versionsToCreate.contains(GeneratedImageVersion.THUMBNAIL)))
/*      */       {
/* 2000 */         this.m_fileStoreManager.storeFile(a_asset.getThumbnailImageFile().getPath());
/*      */       }
/*      */     }
/*      */     catch (ImageException ie)
/*      */     {
/* 2005 */       throw new AssetFileReadException("FileAssetManagerImpl.createThumbnailsThere was a problem reading or creating a scaled image file ", ie);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void deleteGeneratedImages(Asset a_asset, Set<GeneratedImageVersion> a_versionsToDelete)
/*      */   {
/* 2024 */     if ((a_asset.getPreviewImageFile() != null) && (StringUtil.stringIsPopulated(a_asset.getPreviewImageFile().getPath())) && ((a_versionsToDelete == null) || (a_versionsToDelete.contains(GeneratedImageVersion.PREVIEW))))
/*      */     {
/* 2028 */       this.m_fileStoreManager.deleteFile(a_asset.getPreviewImageFile().getPath());
/* 2029 */       a_asset.setPreviewImageFile(null);
/*      */     }
/*      */ 
/* 2032 */     if ((a_asset.getThumbnailImageFile() != null) && (StringUtil.stringIsPopulated(a_asset.getThumbnailImageFile().getPath())) && ((a_versionsToDelete == null) || (a_versionsToDelete.contains(GeneratedImageVersion.THUMBNAIL))))
/*      */     {
/* 2037 */       if (!StoredFileType.SHARED_THUMBNAIL.equals(StoredFileType.getTypeForPath(a_asset.getThumbnailImageFile().getPath())))
/*      */       {
/* 2039 */         this.m_fileStoreManager.deleteFile(a_asset.getThumbnailImageFile().getPath());
/*      */       }
/* 2041 */       a_asset.setThumbnailImageFile(null);
/*      */     }
/* 2043 */     if ((a_asset.getHomogenizedImageFile() != null) && (StringUtil.stringIsPopulated(a_asset.getHomogenizedImageFile().getPath())) && ((a_versionsToDelete == null) || (a_versionsToDelete.contains(GeneratedImageVersion.HOMOGENIZED))))
/*      */     {
/* 2047 */       this.m_fileStoreManager.deleteFile(a_asset.getHomogenizedImageFile().getPath());
/* 2048 */       a_asset.setHomogenizedImageFile(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void saveAssetToDatabase(DBTransaction a_dbTransaction, Asset a_asset, long a_lUserId, boolean a_bUpdateFileLocation, boolean a_bNewWithPresetId)
/*      */     throws Bn2Exception
/*      */   {
/* 2083 */     Connection con = null;
/* 2084 */     PreparedStatement psql = null;
/* 2085 */     DBTransaction transaction = a_dbTransaction;
/* 2086 */     String sSQL = null;
/* 2087 */     int iCol = 1;
/*      */ 
/* 2091 */     synchronized (this.m_oSaveAssetLock)
/*      */     {
/* 2094 */       if (transaction == null)
/*      */       {
/* 2096 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 2101 */         con = transaction.getConnection();
/*      */ 
/* 2104 */         AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 2105 */         long lNewId = 0L;
/* 2106 */         boolean bPresetId = false;
/*      */ 
/* 2108 */         if ((a_asset.getId() <= 0L) || (a_bNewWithPresetId))
/*      */         {
/* 2110 */           sSQL = "INSERT INTO Asset (";
/*      */ 
/* 2112 */           if ((a_bNewWithPresetId) && (a_asset.getId() > 0L))
/*      */           {
/* 2115 */             PreparedStatement idPsql = con.prepareStatement("SELECT * FROM Asset WHERE Id=?");
/* 2116 */             idPsql.setLong(1, a_asset.getId());
/* 2117 */             ResultSet rs = idPsql.executeQuery();
/* 2118 */             boolean bFound = rs.next();
/* 2119 */             idPsql.close();
/* 2120 */             if (bFound)
/*      */             {
/* 2122 */               a_asset.setId(-1L);
/*      */             }
/*      */             else
/*      */             {
/* 2126 */               lNewId = a_asset.getId();
/* 2127 */               bPresetId = true;
/* 2128 */               sSQL = sSQL + "Id, ";
/*      */             }
/*      */           }
/*      */ 
/* 2132 */           if (bPresetId)
/*      */           {
/* 2134 */             sqlGenerator.prepareForIdentityInsert(con, "Asset");
/*      */           }
/*      */ 
/* 2137 */           if ((!sqlGenerator.usesAutoincrementFields()) && (!bPresetId))
/*      */           {
/* 2139 */             lNewId = sqlGenerator.getUniqueId(con, "AssetSequence");
/* 2140 */             sSQL = sSQL + "Id, ";
/*      */           }
/*      */ 
/* 2143 */           sSQL = sSQL + "FileLocation," + "OriginalFileLocation," + "FileSizeInBytes," + "DateAdded," + "DateLastModified," + "ExpiryDate," + "Author," + "AssetTypeId," + "FileFormatId, " + "AddedByUserId," + "LastModifiedByUserId," + "ThumbnailFileLocation," + "MediumFileLocation, " + "SmallFileLocation, " + "IsUnsubmitted, " + "Price," + "ImportedAssetId," + "IsPreviewRestricted, " + "IsSensitive, " + "VersionNumber, " + "NumViews, " + "NumDownloads, " + "LastDownloaded, " + "AssetEntityId, " + "OriginalFilename, " + "HasSubstituteFile, " + "IsBrandTemplate, " + "AdvancedViewing, ";
/*      */ 
/* 2173 */           if (a_asset.getBulkUploadTimestamp() != null)
/*      */           {
/* 2175 */             sSQL = sSQL + "BulkUploadTimestamp, ";
/*      */           }
/*      */ 
/* 2178 */           if (a_asset.getCurrentVersionId() > 0L)
/*      */           {
/* 2180 */             sSQL = sSQL + "CurrentVersionId, ";
/*      */           }
/*      */ 
/* 2183 */           if (a_asset.getAgreementTypeId() > 0L)
/*      */           {
/* 2185 */             sSQL = sSQL + "AgreementTypeId, ";
/*      */           }
/*      */ 
/* 2188 */           sSQL = sSQL + "Synchronised) VALUES (";
/*      */ 
/* 2190 */           if ((!sqlGenerator.usesAutoincrementFields()) || (bPresetId))
/*      */           {
/* 2192 */             sSQL = sSQL + "?, ";
/*      */           }
/*      */ 
/* 2195 */           if (a_asset.getBulkUploadTimestamp() != null)
/*      */           {
/* 2197 */             sSQL = sSQL + "?, ";
/*      */           }
/*      */ 
/* 2200 */           if (a_asset.getCurrentVersionId() > 0L)
/*      */           {
/* 2202 */             sSQL = sSQL + "?, ";
/*      */           }
/*      */ 
/* 2205 */           if (a_asset.getAgreementTypeId() > 0L)
/*      */           {
/* 2207 */             sSQL = sSQL + "?, ";
/*      */           }
/*      */ 
/* 2211 */           sSQL = sSQL + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/* 2213 */           psql = con.prepareStatement(sSQL);
/*      */ 
/* 2215 */           if ((!sqlGenerator.usesAutoincrementFields()) || (bPresetId))
/*      */           {
/* 2217 */             psql.setLong(iCol++, lNewId);
/*      */           }
/*      */ 
/* 2220 */           psql.setString(iCol++, a_asset.getFileLocation());
/* 2221 */           psql.setString(iCol++, a_asset.getOriginalFileLocation());
/* 2222 */           psql.setLong(iCol++, a_asset.getFileSizeInBytes());
/* 2223 */           DBUtil.setFieldTimestampOrNull(psql, iCol++, a_asset.getDateAdded());
/* 2224 */           DBUtil.setFieldTimestampOrNull(psql, iCol++, a_asset.getDateLastModified());
/* 2225 */           DBUtil.setFieldDateOrNull(psql, iCol++, a_asset.getExpiryDate());
/* 2226 */           psql.setString(iCol++, a_asset.getAuthor());
/* 2227 */           DBUtil.setFieldIdOrNull(psql, iCol++, a_asset.getFormat().getAssetTypeId());
/* 2228 */           DBUtil.setFieldIdOrNull(psql, iCol++, a_asset.getFormat());
/* 2229 */           psql.setLong(iCol++, a_lUserId);
/* 2230 */           DBUtil.setFieldIdOrNull(psql, iCol++, a_asset.getLastModifiedByUser());
/*      */ 
/* 2232 */           if (!FileFormat.s_noFileFormat.getThumbnailImageLocation().equals(a_asset.getThumbnailImageFile().getPath()))
/*      */           {
/* 2234 */             psql.setString(iCol++, a_asset.getThumbnailImageFile().getPath());
/*      */           }
/*      */           else
/*      */           {
/* 2238 */             psql.setString(iCol++, null);
/*      */           }
/*      */ 
/* 2241 */           psql.setString(iCol++, a_asset.getPreviewImageFile() == null ? "" : a_asset.getPreviewImageFile().getPath());
/* 2242 */           psql.setString(iCol++, a_asset.getHomogenizedImageFile() == null ? "" : a_asset.getHomogenizedImageFile().getPath());
/*      */ 
/* 2244 */           psql.setBoolean(iCol++, a_asset.getIsUnsubmitted());
/* 2245 */           psql.setLong(iCol++, a_asset.getPrice().getAmount());
/* 2246 */           psql.setString(iCol++, a_asset.getImportedAssetId());
/* 2247 */           psql.setBoolean(iCol++, a_asset.getIsRestricted());
/* 2248 */           psql.setBoolean(iCol++, a_asset.getIsSensitive());
/* 2249 */           psql.setInt(iCol++, a_asset.getVersionNumber());
/* 2250 */           psql.setInt(iCol++, a_asset.getNumViews());
/* 2251 */           psql.setInt(iCol++, a_asset.getNumDownloads());
/*      */ 
/* 2253 */           DBUtil.setFieldTimestampOrNull(psql, iCol++, a_asset.getDateLastDownloaded());
/*      */ 
/* 2255 */           DBUtil.setFieldIdOrNull(psql, iCol++, a_asset.getEntity().getId());
/* 2256 */           psql.setString(iCol++, a_asset.getOriginalFilename());
/* 2257 */           psql.setBoolean(iCol++, a_asset.getHasSubstituteFile());
/* 2258 */           psql.setBoolean(iCol++, a_asset.getIsBrandTemplate());
/* 2259 */           psql.setBoolean(iCol++, a_asset.getAdvancedViewing());
/*      */ 
/* 2261 */           if (a_asset.getBulkUploadTimestamp() != null)
/*      */           {
/* 2263 */             DBUtil.setFieldTimestampOrNull(psql, iCol++, a_asset.getBulkUploadTimestamp());
/*      */           }
/*      */ 
/* 2266 */           if (a_asset.getCurrentVersionId() > 0L)
/*      */           {
/* 2268 */             psql.setLong(iCol++, a_asset.getCurrentVersionId());
/*      */           }
/*      */ 
/* 2271 */           if (a_asset.getAgreementTypeId() > 0L)
/*      */           {
/* 2273 */             psql.setLong(iCol++, a_asset.getAgreementTypeId());
/*      */           }
/*      */ 
/* 2276 */           psql.setBoolean(iCol++, a_asset.isSynchronised());
/*      */         }
/*      */         else
/*      */         {
/* 2280 */           sSQL = "UPDATE Asset SET FileLocation=?, OriginalFileLocation=?, MediumFileLocation=?, SmallFileLocation=?, ThumbnailFileLocation=?, " + (a_bUpdateFileLocation ? "FileSizeInBytes=?, " : "") + (a_bUpdateFileLocation ? "AssetTypeId=?, " : "") + (a_bUpdateFileLocation ? "FileFormatId=?, " : "") + (a_asset.getDateAdded() != null ? "DateAdded=?," : "") + "DateLastModified=?, " + "ExpiryDate=?, " + "Author=?, " + (a_asset.getAddedByUser() != null ? "AddedByUserId=?," : "") + "LastModifiedByUserId=?,  " + "IsUnsubmitted=?, " + "Price=?, " + "IsPreviewRestricted=?, " + "IsSensitive=?, " + "VersionNumber=?, " + "AgreementTypeId=?, " + "OriginalFilename=?, " + "HasSubstituteFile=?, " + "Synchronised=?, " + "IsBrandTemplate=?, " + "AdvancedViewing=? ";
/*      */ 
/* 2306 */           if (a_asset.getEntity().getId() > 0L)
/*      */           {
/* 2308 */             sSQL = sSQL + ", AssetEntityId=? ";
/*      */           }
/*      */ 
/* 2311 */           if (a_asset.getBulkUploadTimestamp() != null)
/*      */           {
/* 2313 */             sSQL = sSQL + ", BulkUploadTimestamp=? ";
/*      */           }
/*      */ 
/* 2316 */           sSQL = sSQL + "WHERE Id=?";
/*      */ 
/* 2318 */           psql = con.prepareStatement(sSQL);
/*      */ 
/* 2320 */           psql.setString(iCol++, a_asset.getFileLocation());
/* 2321 */           psql.setString(iCol++, a_asset.getOriginalFileLocation());
/* 2322 */           psql.setString(iCol++, a_asset.getPreviewImageFile() == null ? "" : a_asset.getPreviewImageFile().getPath());
/* 2323 */           psql.setString(iCol++, a_asset.getHomogenizedImageFile() == null ? "" : a_asset.getHomogenizedImageFile().getPath());
/*      */ 
/* 2325 */           if (!FileFormat.s_noFileFormat.getThumbnailImageLocation().equals(a_asset.getThumbnailImageFile().getPath()))
/*      */           {
/* 2327 */             psql.setString(iCol++, a_asset.getThumbnailImageFile().getPath());
/*      */           }
/*      */           else
/*      */           {
/* 2331 */             psql.setString(iCol++, null);
/*      */           }
/*      */ 
/* 2334 */           if (a_bUpdateFileLocation)
/*      */           {
/* 2336 */             psql.setLong(iCol++, a_asset.getFileSizeInBytes());
/* 2337 */             DBUtil.setFieldIdOrNull(psql, iCol++, a_asset.getFormat().getAssetTypeId());
/* 2338 */             DBUtil.setFieldIdOrNull(psql, iCol++, a_asset.getFormat());
/*      */           }
/*      */ 
/* 2341 */           if (a_asset.getDateAdded() != null)
/*      */           {
/* 2343 */             DBUtil.setFieldTimestampOrNull(psql, iCol++, a_asset.getDateAdded());
/*      */           }
/*      */ 
/* 2346 */           DBUtil.setFieldTimestampOrNull(psql, iCol++, a_asset.getDateLastModified());
/* 2347 */           DBUtil.setFieldDateOrNull(psql, iCol++, a_asset.getExpiryDate());
/* 2348 */           psql.setString(iCol++, a_asset.getAuthor());
/*      */ 
/* 2350 */           if (a_asset.getAddedByUser() != null)
/*      */           {
/* 2352 */             DBUtil.setFieldIdOrNull(psql, iCol++, a_asset.getAddedByUser());
/*      */           }
/*      */ 
/* 2355 */           DBUtil.setFieldIdOrNull(psql, iCol++, a_asset.getLastModifiedByUser());
/* 2356 */           psql.setBoolean(iCol++, a_asset.getIsUnsubmitted());
/* 2357 */           psql.setLong(iCol++, a_asset.getPrice().getAmount());
/* 2358 */           psql.setBoolean(iCol++, a_asset.getIsRestricted());
/* 2359 */           psql.setBoolean(iCol++, a_asset.getIsSensitive());
/* 2360 */           psql.setInt(iCol++, a_asset.getVersionNumber());
/* 2361 */           psql.setLong(iCol++, a_asset.getAgreementTypeId());
/* 2362 */           psql.setString(iCol++, a_asset.getOriginalFilename());
/* 2363 */           psql.setBoolean(iCol++, a_asset.getHasSubstituteFile());
/* 2364 */           psql.setBoolean(iCol++, a_asset.isSynchronised());
/* 2365 */           psql.setBoolean(iCol++, a_asset.getIsBrandTemplate());
/* 2366 */           psql.setBoolean(iCol++, a_asset.getAdvancedViewing());
/*      */ 
/* 2368 */           if (a_asset.getEntity().getId() > 0L)
/*      */           {
/* 2370 */             psql.setLong(iCol++, a_asset.getEntity().getId());
/*      */           }
/*      */ 
/* 2373 */           if (a_asset.getBulkUploadTimestamp() != null)
/*      */           {
/* 2375 */             DBUtil.setFieldTimestampOrNull(psql, iCol++, a_asset.getBulkUploadTimestamp());
/*      */           }
/*      */ 
/* 2379 */           psql.setLong(iCol++, a_asset.getId());
/*      */         }
/*      */ 
/* 2382 */         psql.executeUpdate();
/* 2383 */         if (bPresetId)
/*      */         {
/* 2385 */           sqlGenerator.postIdentityInsert(con, "Asset", "AssetSequence");
/*      */         }
/* 2387 */         psql.close();
/*      */ 
/* 2389 */         if ((a_asset.getId() <= 0L) && (sqlGenerator.usesAutoincrementFields()))
/*      */         {
/* 2391 */           lNewId = sqlGenerator.getUniqueId(con, "Asset");
/* 2392 */           a_asset.setId(lNewId);
/*      */         }
/* 2394 */         else if (a_asset.getId() <= 0L)
/*      */         {
/* 2396 */           a_asset.setId(lNewId);
/*      */         }
/*      */ 
/* 2400 */         this.m_attributeValueManager.saveAttributeValues(transaction, a_asset);
/*      */ 
/* 2403 */         saveAssetCategoriesToDatabase(a_asset, con, a_asset.getDescriptiveCategories(), 1L);
/* 2404 */         saveAssetCategoriesToDatabase(a_asset, con, a_asset.getPermissionCategories(), 2L);
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 2408 */         this.m_logger.error("SQL Exception whilst saving an asset to the database : " + e);
/* 2409 */         throw new Bn2Exception("SQL Exception whilst saving an asset to the database : " + e, e);
/*      */       }
/*      */       finally
/*      */       {
/* 2414 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/* 2418 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 2422 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveAssetCategoriesToDatabase(Asset a_asset, Connection con, Vector<Category> a_vecCategories, long a_lCategoryTypeId)
/*      */     throws SQLException
/*      */   {
/* 2432 */     PreparedStatement psql = null;
/* 2433 */     String sSQL = null;
/* 2434 */     String sIdsToRemove = "0";
/* 2435 */     Vector categoryIds = CategoryUtil.getCategoryIdVector(a_vecCategories);
/*      */ 
/* 2438 */     if (categoryIds != null)
/*      */     {
/* 2441 */       HashSet hsCategoryIds = new HashSet(categoryIds);
/* 2442 */       HashSet hsCategoriesToInsert = new HashSet(categoryIds);
/*      */ 
/* 2444 */       sSQL = "SELECT Id FROM CM_Category cat INNER JOIN CM_ItemInCategory iic ON iic.CategoryId = cat.Id AND iic.ItemId=? WHERE cat.CategoryTypeId=?";
/*      */ 
/* 2450 */       psql = con.prepareStatement(sSQL);
/* 2451 */       psql.setLong(1, a_asset.getId());
/* 2452 */       psql.setLong(2, a_lCategoryTypeId);
/* 2453 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 2455 */       while (rs.next())
/*      */       {
/* 2457 */         Long id = Long.valueOf(rs.getLong("Id"));
/* 2458 */         if (!hsCategoryIds.contains(id))
/*      */         {
/* 2460 */           sIdsToRemove = sIdsToRemove + "," + id.longValue();
/*      */         }
/*      */         else
/*      */         {
/* 2464 */           hsCategoriesToInsert.remove(id);
/*      */         }
/*      */       }
/*      */ 
/* 2468 */       psql.close();
/*      */ 
/* 2470 */       if (StringUtil.stringIsPopulated(sIdsToRemove))
/*      */       {
/* 2472 */         sSQL = "DELETE FROM CM_ItemInCategory WHERE ItemId=? AND CategoryId IN (" + sIdsToRemove + ")";
/* 2473 */         psql = con.prepareStatement(sSQL);
/* 2474 */         psql.setLong(1, a_asset.getId());
/* 2475 */         psql.executeUpdate();
/* 2476 */         psql.close();
/*      */ 
/* 2479 */         if (AssetBankSettings.getCategoryExtensionAssetsEnabled())
/*      */         {
/*      */           try
/*      */           {
/* 2483 */             Vector vecIds = StringUtil.convertToVectorOfLongs(sIdsToRemove, ",");
/* 2484 */             for (Iterator i$ = vecIds.iterator(); i$.hasNext(); ) { long lCatId = ((Long)i$.next()).longValue();
/*      */ 
/* 2486 */               Category cat = this.m_categoryManager.getCategory(null, a_lCategoryTypeId, lCatId);
/* 2487 */               if ((cat != null) && (cat.getExtensionAssetId() > 0L))
/*      */               {
/* 2490 */                 this.m_assetRelationshipManager.deleteAssetRelationship(null, cat.getExtensionAssetId(), a_asset.getId(), 2L, true);
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Bn2Exception e)
/*      */           {
/*      */             Iterator i$;
/* 2496 */             this.m_logger.error(getClass().getSimpleName() + ": Error checking category extension assets:", e);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 2501 */       Iterator itIdsToAdd = hsCategoriesToInsert.iterator();
/* 2502 */       while (itIdsToAdd.hasNext())
/*      */       {
/* 2504 */         long lCatId = ((Long)itIdsToAdd.next()).longValue();
/* 2505 */         sSQL = "INSERT INTO CM_ItemInCategory (CategoryId,ItemId) VALUES (?,?)";
/* 2506 */         psql = con.prepareStatement(sSQL);
/* 2507 */         psql.setLong(1, lCatId);
/* 2508 */         psql.setLong(2, a_asset.getId());
/* 2509 */         psql.executeUpdate();
/* 2510 */         psql.close();
/*      */ 
/* 2512 */         if (AssetBankSettings.getCategoryExtensionAssetsEnabled())
/*      */         {
/* 2516 */           this.m_categoryManager.checkExtensionAsset(null, lCatId, a_lCategoryTypeId, -1L, a_asset);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteAsset(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 2544 */     deleteAsset(a_dbTransaction, a_lAssetId, null);
/*      */   }
/*      */ 
/*      */   public void deleteAsset(DBTransaction a_dbTransaction, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2555 */     deleteAsset(a_dbTransaction, a_asset.getId(), a_asset);
/*      */   }
/*      */ 
/*      */   protected void deleteAsset(DBTransaction a_dbTransaction, long a_lAssetId, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2580 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2582 */     if (transaction == null)
/*      */     {
/* 2584 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */     Asset asset;
/*      */     //Asset asset;
/* 2589 */     if (a_asset != null)
/*      */     {
/* 2591 */       asset = a_asset;
/*      */     }
/*      */     else
/*      */     {
/* 2595 */       asset = getAsset(transaction, a_lAssetId, null, false);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2602 */       Connection con = transaction.getConnection();
/*      */ 
/* 2605 */       String[] aSQL = { "DELETE FROM WorkflowInfo WHERE WorkflowableEntityId=?", "DELETE FROM AssetRating WHERE AssetId=?", "DELETE FROM AssetView WHERE AssetId=?", "DELETE FROM AssetAttributeValues WHERE AssetId=?", "DELETE FROM TranslatedAssetAttributeValues WHERE AssetId=?", "DELETE FROM AssetListAttributeValue WHERE AssetId=?", "DELETE FROM CM_ItemInCategory WHERE ItemId=?", "DELETE FROM AssetBoxAsset WHERE AssetId=?", "DELETE FROM AssetBoxPriceBand WHERE AssetId=?", "DELETE FROM AssetApproval WHERE AssetId=?", "DELETE FROM PromotedAsset WHERE AssetId=?", "DELETE FROM FeaturedAsset WHERE AssetId=?", "DELETE FROM FeaturedAssetInBrand WHERE AssetId=?", "DELETE FROM AssetInMarketingEmail WHERE AssetId=?", "DELETE FROM AssetBoxPriceBand WHERE AssetId=?", "UPDATE AssetChangeLog SET AssetId = null WHERE AssetId=?", "UPDATE CM_Category SET ExtensionAssetId = null WHERE ExtensionAssetId=?", "DELETE FROM AssetAgreement WHERE AssetId=?", "DELETE FROM AssetWorkflowAudit WHERE AssetId=?", "DELETE FROM RepurposedAsset WHERE AssetId=?" };
/*      */ 
/* 2626 */       PreparedStatement psql = null;
/* 2627 */       for (int i = 0; i < aSQL.length; i++)
/*      */       {
/* 2629 */         psql = con.prepareStatement(aSQL[i]);
/* 2630 */         psql.setLong(1, a_lAssetId);
/* 2631 */         psql.executeUpdate();
/* 2632 */         psql.close();
/*      */       }
/*      */ 
/* 2636 */       Vector vIdsToReindex = new Vector();
/* 2637 */       String sSQL = null;
/* 2638 */       ResultSet rs = null;
/*      */ 
/* 2640 */       if ((AssetBankSettings.getIncludeParentMetadataForSearch()) || (asset.getEntity().getId() > 0L))
/*      */       {
/* 2642 */         sSQL = "SELECT ChildId, MustHaveParent FROM RelatedAsset ra INNER JOIN Asset a ON a.Id = ra.ChildId LEFT JOIN AssetEntity ae ON ae.Id = a.AssetEntityId WHERE ra.ParentId=?";
/*      */ 
/* 2649 */         psql = con.prepareStatement(sSQL);
/* 2650 */         psql.setLong(1, a_lAssetId);
/*      */ 
/* 2652 */         rs = psql.executeQuery();
/*      */ 
/* 2654 */         while (rs.next())
/*      */         {
/* 2656 */           if (rs.getBoolean("MustHaveParent"))
/*      */           {
/* 2659 */             Vector vParents = this.m_assetRelationshipManager.getRelatedAssetIds(transaction, rs.getLong("ChildId"), 3L);
/*      */ 
/* 2662 */             if (vParents.size() <= 1)
/*      */             {
/* 2664 */               this.m_assetManager.deleteAsset(transaction, rs.getLong("ChildId"));
/*      */             }
/* 2666 */             continue;
/* 2667 */           }if (!AssetBankSettings.getIncludeParentMetadataForSearch())
/*      */             continue;
/* 2669 */           vIdsToReindex.add(Long.valueOf(rs.getLong("ChildId")));
/*      */         }
/*      */ 
/* 2673 */         psql.close();
/*      */       }
/*      */ 
/* 2677 */       sSQL = "DELETE FROM SecondaryDownloadUsageType WHERE AssetUseId IN (SELECT Id FROM AssetUse WHERE AssetId=?)";
/* 2678 */       psql = con.prepareStatement(sSQL);
/* 2679 */       psql.setLong(1, a_lAssetId);
/* 2680 */       psql.executeUpdate();
/* 2681 */       psql.close();
/*      */ 
/* 2684 */       sSQL = "DELETE FROM AssetUse WHERE AssetId=?";
/* 2685 */       psql = con.prepareStatement(sSQL);
/* 2686 */       psql.setLong(1, a_lAssetId);
/* 2687 */       psql.executeUpdate();
/* 2688 */       psql.close();
/*      */ 
/* 2691 */       sSQL = "DELETE FROM RelatedAsset WHERE ParentId=? OR ChildId=?";
/* 2692 */       psql = con.prepareStatement(sSQL);
/* 2693 */       psql.setLong(1, a_lAssetId);
/* 2694 */       psql.setLong(2, a_lAssetId);
/* 2695 */       psql.executeUpdate();
/* 2696 */       psql.close();
/*      */ 
/* 2699 */       sSQL = "SELECT Id FROM Asset WHERE CurrentVersionId=?";
/* 2700 */       psql = con.prepareStatement(sSQL);
/* 2701 */       psql.setLong(1, a_lAssetId);
/* 2702 */       rs = psql.executeQuery();
/* 2703 */       while (rs.next())
/*      */       {
/* 2705 */         this.m_assetManager.deleteAsset(transaction, rs.getLong("Id"));
/*      */       }
/* 2707 */       psql.close();
/*      */ 
/* 2709 */       sSQL = "DELETE FROM Asset WHERE Id=?";
/* 2710 */       psql = con.prepareStatement(sSQL);
/* 2711 */       psql.setLong(1, a_lAssetId);
/* 2712 */       psql.executeUpdate();
/* 2713 */       psql.close();
/*      */ 
/* 2715 */       if (asset.getSurrogateAssetId() <= 0L)
/*      */       {
/* 2717 */         deleteGeneratedImages(asset, null);
/*      */ 
/* 2720 */         if (StringUtil.stringIsPopulated(asset.getFileLocation())) {
/* 2721 */           this.m_fileStoreManager.deleteFile(asset.getFileLocation());
/*      */         }
/* 2723 */         if (StringUtil.stringIsPopulated(asset.getOriginalFileLocation())) {
/* 2724 */           this.m_fileStoreManager.deleteFile(asset.getOriginalFileLocation());
/*      */         }
/*      */ 
/* 2727 */         this.m_assetRepurposingManager.removeRepurposedImages(transaction, a_lAssetId);
/*      */       }
/*      */ 
/* 2730 */       if (vIdsToReindex.size() > 0)
/*      */       {
/* 2732 */         this.m_searchManager.indexDocuments(transaction, getAssets(transaction, vIdsToReindex, null, 0, -1, true, true), true);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2737 */       this.m_logger.error("SQL Exception whilst deleting asset from the database : " + e);
/* 2738 */       throw new Bn2Exception("SQL Exception whilst deleting asset from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2743 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2747 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2751 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public UploadedFileInfo storeTempUploadedFile(FileFormat a_format, AssetFileSource a_source, StoredFileType a_type)
/*      */     throws Bn2Exception
/*      */   {
/* 2774 */     String sAssetFileLocation = this.m_fileStoreManager.addFile(a_source.getInputStream(), a_source.getFilename(), a_type);
/*      */ 
/* 2779 */     UploadedFileInfo uploadFileInfo = new UploadedFileInfo();
/* 2780 */     uploadFileInfo.setFileLocation(sAssetFileLocation);
/* 2781 */     uploadFileInfo.setTimeUploaded(System.currentTimeMillis());
/* 2782 */     a_source.setStoredFileLocation(sAssetFileLocation);
/* 2783 */     uploadFileInfo.setFileSizeInBytes(new File(this.m_fileStoreManager.getAbsolutePath(sAssetFileLocation)).length());
/*      */ 
/* 2785 */     uploadFileInfo.setAssetTypeId(getAssetTypeId());
/*      */ 
/* 2788 */     String sRgbColorProfile = this.m_usageManager.getColorSpace(null, 1).getFileLocation();
/* 2789 */     String sCmykColorProfile = this.m_usageManager.getColorSpace(null, 2).getFileLocation();
/*      */ 
/* 2792 */     synchronized (this.m_hmTempUploadedFiles)
/*      */     {
/* 2794 */       this.m_hmTempUploadedFiles.put(sAssetFileLocation, uploadFileInfo);
/*      */     }
/*      */ 
/* 2802 */     String sFileToConvert = null;
/* 2803 */     String sConvertedFileLocation = null;
/*      */ 
/* 2805 */     if (a_format.getConverterClass() != null)
/*      */     {
/* 2808 */       String sProposedFilename = FileUtil.getFilepathWithoutSuffix(a_source.getFilename());
/* 2809 */       sProposedFilename = sProposedFilename + "." + a_format.getFileExtension();
/*      */ 
/* 2812 */       String sTempFileLocation = this.m_fileStoreManager.getUniqueFilepath(sProposedFilename, StoredFileType.PREVIEW_OR_THUMBNAIL);
/*      */ 
/* 2816 */       AssetConverter converter = a_format.getConverterInstance();
/*      */ 
/* 2819 */       String sRelativeSourcePath = this.m_fileStoreManager.getRealRelativePath(uploadFileInfo.getFileLocation());
/* 2820 */       String sBaseSourcePath = this.m_fileStoreManager.getFullBasePath(uploadFileInfo.getFileLocation());
/* 2821 */       String sBaseDestPath = this.m_fileStoreManager.getFullBasePath(sTempFileLocation);
/*      */ 
/* 2825 */       long lDeviceId = this.m_fileStoreManager.getDeviceIdForRelativePath(sTempFileLocation);
/*      */       try
/*      */       {
/* 2830 */         sConvertedFileLocation = converter.convert(sBaseSourcePath, sBaseDestPath, sRelativeSourcePath, this.m_fileStoreManager.getRealRelativePath(sTempFileLocation));
/*      */ 
/* 2836 */         sFileToConvert = this.m_fileStoreManager.getRelativePathForDevice(sConvertedFileLocation, lDeviceId);
/*      */ 
/* 2839 */         if (!sConvertedFileLocation.equals(sTempFileLocation))
/*      */         {
/* 2841 */           this.m_fileStoreManager.deleteFile(sTempFileLocation);
/*      */         }
/*      */ 
/* 2845 */         sConvertedFileLocation = sFileToConvert;
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/* 2850 */         this.m_logger.error(getClass().getSimpleName() + " : Error converting asset: ", e);
/*      */       }
/*      */     }
/* 2853 */     else if ((getAssetTypeId() == 2L) || (getAssetTypeId() == 3L) || ((getAssetTypeId() == 1L) && (a_format.getIsConvertable())))
/*      */     {
/* 2858 */       sFileToConvert = uploadFileInfo.getFileLocation();
/*      */     }
/*      */ 
/* 2861 */     if (sFileToConvert != null)
/*      */     {
/* 2866 */       String sSuffix = "" + System.currentTimeMillis() + "-m.jpg";
/*      */ 
/* 2868 */       String sPreviewFile = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(sFileToConvert, sSuffix, StoredFileType.TEMP);
/*      */ 
/* 2872 */       ABImageMagick.resizeToJpg(this.m_fileStoreManager.getAbsolutePath(sFileToConvert), this.m_fileStoreManager.getAbsolutePath(sPreviewFile), getMaxPreviewWidth(), getMaxPreviewHeight(), sRgbColorProfile, sCmykColorProfile);
/*      */ 
/* 2882 */       this.m_fileStoreManager.storeFile(sPreviewFile);
/*      */ 
/* 2885 */       uploadFileInfo.setPreviewImage(new ImageFile(sPreviewFile));
/*      */ 
/* 2889 */       uploadFileInfo.setConvertedLocation(sConvertedFileLocation);
/*      */     }
/*      */ 
/* 2894 */     return uploadFileInfo;
/*      */   }
/*      */ 
/*      */   public void storeTempUploadedFile(UploadedFileInfo a_uploadFileInfo)
/*      */   {
/* 2912 */     synchronized (this.m_hmTempUploadedFiles)
/*      */     {
/* 2914 */       this.m_hmTempUploadedFiles.put(a_uploadFileInfo.getFileLocation(), a_uploadFileInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   public final UploadedFileInfo getUploadedFileInfo(String a_sLocation, boolean a_bRemove)
/*      */     throws UploadedFileNotFoundException
/*      */   {
/* 2939 */     if (!this.m_hmTempUploadedFiles.containsKey(a_sLocation))
/*      */     {
/* 2941 */       return null;
/*      */     }
/*      */ 
/* 2945 */     UploadedFileInfo uploadedFileInfo = (UploadedFileInfo)this.m_hmTempUploadedFiles.get(a_sLocation);
/*      */ 
/* 2948 */     if (a_bRemove)
/*      */     {
/* 2950 */       synchronized (this.m_hmTempUploadedFiles)
/*      */       {
/* 2952 */         this.m_hmTempUploadedFiles.remove(a_sLocation);
/*      */       }
/*      */ 
/* 2955 */       if (uploadedFileInfo.getPreviewImage() != null)
/*      */       {
/* 2957 */         this.m_fileStoreManager.deleteFile(uploadedFileInfo.getPreviewImage().getPath());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2962 */     uploadedFileInfo = getAdditionalFileInfo(uploadedFileInfo, a_sLocation);
/*      */ 
/* 2964 */     return uploadedFileInfo;
/*      */   }
/*      */ 
/*      */   protected UploadedFileInfo getAdditionalFileInfo(UploadedFileInfo a_fileInfo, String a_sLocation)
/*      */   {
/* 2981 */     return a_fileInfo;
/*      */   }
/*      */ 
/*      */   protected void cleanupUploadedFiles()
/*      */   {
/* 2999 */     if (!this.m_hmTempUploadedFiles.isEmpty())
/*      */     {
/* 3002 */       synchronized (this.m_hmTempUploadedFiles)
/*      */       {
/* 3004 */         Set setFiles = this.m_hmTempUploadedFiles.keySet();
/* 3005 */         Iterator itFiles = setFiles.iterator();
/*      */ 
/* 3008 */         while (itFiles.hasNext())
/*      */         {
/* 3011 */           String sLocation = (String)itFiles.next();
/*      */ 
/* 3014 */           UploadedFileInfo uploadedFileInfo = (UploadedFileInfo)this.m_hmTempUploadedFiles.get(sLocation);
/*      */ 
/* 3017 */           if (uploadedFileInfo.getTimeUploaded() < System.currentTimeMillis() + 7200000L)
/*      */           {
/* 3020 */             this.m_fileStoreManager.deleteFile(sLocation);
/*      */ 
/* 3023 */             if (uploadedFileInfo.getPreviewImage() != null)
/*      */             {
/* 3025 */               this.m_fileStoreManager.deleteFile(uploadedFileInfo.getPreviewImage().getPath());
/*      */             }
/*      */ 
/* 3029 */             if (uploadedFileInfo.getConvertedLocation() != null)
/*      */             {
/* 3031 */               this.m_fileStoreManager.deleteFile(uploadedFileInfo.getConvertedLocation());
/*      */             }
/*      */ 
/* 3035 */             itFiles.remove();
/* 3036 */             this.m_logger.debug("FileAssetManagerImpl.cleanupUploadFiles: deleted file: " + sLocation);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAssetNamingAttributes(DBTransaction a_dbTransaction, Vector a_vecAttributeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 3061 */     setSignificantAttribute(a_dbTransaction, a_vecAttributeIds, "NameAttribute");
/*      */   }
/*      */ 
/*      */   public void setAssetDescriptionAttribute(DBTransaction a_dbTransaction, long a_lAttributeId)
/*      */     throws Bn2Exception
/*      */   {
/* 3079 */     Vector vecAttributeIds = new Vector();
/* 3080 */     vecAttributeIds.add(new Long(a_lAttributeId));
/* 3081 */     setSignificantAttribute(a_dbTransaction, vecAttributeIds, "IsDescriptionAttribute");
/*      */   }
/*      */ 
/*      */   private void setSignificantAttribute(DBTransaction a_dbTransaction, Vector a_vecAttributeIds, String a_sFlagColumnName)
/*      */     throws Bn2Exception
/*      */   {
/* 3095 */     String ksMethodName = "setSignificantAttribute";
/*      */ 
/* 3097 */     Connection con = null;
/* 3098 */     PreparedStatement psql = null;
/* 3099 */     String sSQL = null;
/*      */     try
/*      */     {
/* 3103 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 3106 */       sSQL = "UPDATE Attribute SET " + a_sFlagColumnName + "=0";
/* 3107 */       psql = con.prepareStatement(sSQL);
/* 3108 */       psql.executeUpdate();
/* 3109 */       psql.close();
/*      */ 
/* 3112 */       sSQL = "UPDATE Attribute SET " + a_sFlagColumnName + "=1 WHERE Id=?";
/* 3113 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 3115 */       if (a_vecAttributeIds != null)
/*      */       {
/* 3117 */         for (int i = 0; i < a_vecAttributeIds.size(); i++)
/*      */         {
/* 3119 */           long lTempId = ((Long)a_vecAttributeIds.elementAt(i)).longValue();
/* 3120 */           psql.setLong(1, lTempId);
/* 3121 */           psql.executeUpdate();
/*      */         }
/*      */       }
/*      */ 
/* 3125 */       psql.close();
/*      */ 
/* 3128 */       this.m_attributeManager.invalidateAttributeCache();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 3132 */       this.m_logger.error("FileAssetManagerImpl.setSignificantAttribute - " + sqe);
/* 3133 */       throw new Bn2Exception("FileAssetManagerImpl.setSignificantAttribute", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<FileFormat> getSupportedFileFormats(DBTransaction a_dbTransaction, long a_lTypeId, boolean a_bWritableOnly)
/*      */     throws Bn2Exception
/*      */   {
/* 3153 */     Vector vFormats = null;
/* 3154 */     Connection con = null;
/* 3155 */     ResultSet rs = null;
/* 3156 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 3158 */     if (transaction == null)
/*      */     {
/* 3160 */       transaction = getTransactionManager().getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3165 */       con = transaction.getConnection();
/*      */ 
/* 3167 */       String sSQL = "SELECT Id, Name, Description, FileExtension FROM FileFormat WHERE AssetTypeId=? AND IsConvertable=1 ";
/*      */ 
/* 3173 */       if (a_bWritableOnly)
/*      */       {
/* 3175 */         sSQL = sSQL + " AND IsConversionTarget=1";
/*      */       }
/*      */ 
/* 3179 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 3180 */       psql.setLong(1, a_lTypeId);
/*      */ 
/* 3182 */       rs = psql.executeQuery();
/*      */ 
/* 3184 */       vFormats = new Vector();
/*      */ 
/* 3186 */       while (rs.next())
/*      */       {
/* 3188 */         FileFormat format = new FileFormat();
/*      */ 
/* 3190 */         format.setId(rs.getLong("Id"));
/* 3191 */         format.setName(rs.getString("Name"));
/* 3192 */         format.setDescription(rs.getString("Description"));
/* 3193 */         format.setFileExtension(rs.getString("FileExtension"));
/*      */ 
/* 3195 */         vFormats.add(format);
/*      */       }
/* 3197 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3201 */       this.m_logger.error("SQL Exception whilst getting the formats from the database : " + e);
/* 3202 */       throw new Bn2Exception("SQL Exception whilst getting the formats from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3207 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3211 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3215 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3220 */     return vFormats;
/*      */   }
/*      */ 
/*      */   public String createConvertedVersion(Asset a_asset, String a_sOriginalFilename, String a_sAssetFileLocation)
/*      */     throws Bn2Exception
/*      */   {
/* 3237 */     String sAssetFileLocation = null;
/*      */ 
/* 3240 */     String sOriginalFileLocation = a_sAssetFileLocation;
/*      */ 
/* 3242 */     String sProposedFilepath = FileUtil.getFilepathWithoutSuffix(a_sOriginalFilename);
/* 3243 */     sProposedFilepath = sProposedFilepath + "." + a_asset.getFormat().getFileExtension();
/*      */ 
/* 3246 */     String sTempFileLocation = this.m_fileStoreManager.getUniqueFilepath(sProposedFilepath, StoredFileType.PREVIEW_OR_THUMBNAIL);
/*      */ 
/* 3249 */     AssetConverter converter = a_asset.getFormat().getConverterInstance();
/*      */ 
/* 3252 */     String sRelativeSourcePath = this.m_fileStoreManager.getRealRelativePath(sOriginalFileLocation);
/* 3253 */     String sBaseSourcePath = this.m_fileStoreManager.getFullBasePath(sOriginalFileLocation);
/* 3254 */     String sBaseDestPath = this.m_fileStoreManager.getFullBasePath(sTempFileLocation);
/*      */ 
/* 3258 */     long lDeviceId = this.m_fileStoreManager.getDeviceIdForRelativePath(sTempFileLocation);
/*      */ 
/* 3261 */     sAssetFileLocation = converter.convert(sBaseSourcePath, sBaseDestPath, sRelativeSourcePath, this.m_fileStoreManager.getRealRelativePath(sTempFileLocation));
/*      */ 
/* 3267 */     sAssetFileLocation = this.m_fileStoreManager.getRelativePathForDevice(sAssetFileLocation, lDeviceId);
/*      */ 
/* 3270 */     this.m_fileStoreManager.storeFile(sAssetFileLocation);
/*      */ 
/* 3273 */     if (!sAssetFileLocation.equals(sTempFileLocation))
/*      */     {
/* 3275 */       this.m_fileStoreManager.deleteFile(sTempFileLocation);
/*      */     }
/*      */ 
/* 3278 */     return sAssetFileLocation;
/*      */   }
/*      */ 
/*      */   public DBTransactionManager getTransactionManager()
/*      */   {
/* 3284 */     return this.m_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 3289 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*      */   {
/* 3294 */     this.m_fileStoreManager = a_fileStoreManager;
/*      */   }
/*      */ 
/*      */   public AssetCategoryManager getCategoryManager()
/*      */   {
/* 3300 */     return this.m_categoryManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*      */   {
/* 3306 */     this.m_categoryManager = a_categoryManager;
/*      */   }
/*      */ 
/*      */   public ScheduleManager getScheduleManager()
/*      */   {
/* 3312 */     return this.m_scheduleManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/* 3318 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ 
/*      */   public IAssetManager getAssetManager()
/*      */   {
/* 3324 */     return this.m_assetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(IAssetManager a_assetManager)
/*      */   {
/* 3330 */     this.m_assetManager = a_assetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*      */   {
/* 3335 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager a_attributeManager)
/*      */   {
/* 3340 */     this.m_attributeManager = a_attributeManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*      */   {
/* 3345 */     this.m_attributeValueManager = a_attributeValueManager;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*      */   {
/* 3350 */     this.m_assetEntityManager = assetEntityManager;
/*      */   }
/*      */ 
/*      */   public void setAssetRepurposingManager(AssetRepurposingManager assetRepurposingManager)
/*      */   {
/* 3355 */     this.m_assetRepurposingManager = assetRepurposingManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager searchManager)
/*      */   {
/* 3360 */     this.m_searchManager = searchManager;
/*      */   }
/*      */ 
/*      */   public void setAgreementsManager(AgreementsManager a_manager)
/*      */   {
/* 3365 */     this.m_agreementsManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setAssetFeedbackManager(AssetFeedbackManager a_feedbackManager)
/*      */   {
/* 3370 */     this.m_feedbackManager = a_feedbackManager;
/*      */   }
/*      */ 
/*      */   public void setWorkflowManager(WorkflowManager a_workflowManager)
/*      */   {
/* 3375 */     this.m_workflowManager = a_workflowManager;
/*      */   }
/*      */ 
/*      */   public void setUsageManager(UsageManager a_sUsageManager)
/*      */   {
/* 3380 */     this.m_usageManager = a_sUsageManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.FileAssetManagerImpl
 * JD-Core Version:    0.6.0
 */