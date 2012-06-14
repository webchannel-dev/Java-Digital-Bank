/*      */ package com.bright.assetbank.application.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bn2web.common.util.Randomizer;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*      */ import com.bright.assetbank.application.bean.AssetFileSource;
/*      */ import com.bright.assetbank.application.bean.FileFormat;
/*      */ import com.bright.assetbank.application.bean.ImageAsset;
/*      */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*      */ import com.bright.assetbank.application.bean.ImageFileInfo;
/*      */ import com.bright.assetbank.application.bean.LightweightAsset;
/*      */ import com.bright.assetbank.application.bean.SelectedListboxValue;
/*      */ import com.bright.assetbank.application.bean.UploadedFileInfo;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.exception.AssetFileReadException;
/*      */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*      */ import com.bright.assetbank.application.util.ABImageMagick;
/*      */ import com.bright.assetbank.application.util.AssetDBUtil;
/*      */ import com.bright.assetbank.application.util.AssetUtil;
/*      */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*      */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*      */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*      */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*      */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*      */ import com.bright.assetbank.search.bean.SearchCriteria;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.thumbnails.service.ThumbnailGenerationManager;
/*      */ import com.bright.assetbank.usage.bean.ColorSpace;
/*      */ import com.bright.assetbank.usage.service.UsageManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.user.bean.Group;
/*      */ import com.bright.assetbank.user.constant.UserConstants;
/*      */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*      */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.common.bean.RefDataItem;
/*      */ import com.bright.framework.common.service.RefDataManager;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.bean.DataBean;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.image.bean.ImageFile;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.search.bean.SearchResults;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.storage.constant.StoredFileType;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.NumberUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import com.bright.framework.workflow.service.WorkflowManager;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.collections.CollectionUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.lucene.search.SortField;
/*      */ 
/*      */ public class AssetManager extends Bn2Manager
/*      */   implements IAssetManager, AssetBankConstants, AssetEntityConstants, UserConstants
/*      */ {
/*      */   private static final String c_ksClassName = "AssetManager";
/*  218 */   private DBTransactionManager m_transactionManager = null;
/*      */ 
/*  220 */   private FileAssetManagerImpl m_fileAssetManager = null;
/*  221 */   private ImageAssetManagerImpl m_imageAssetManager = null;
/*  222 */   private VideoAssetManagerImpl m_videoAssetManager = null;
/*  223 */   private AudioAssetManagerImpl m_audioAssetManager = null;
/*  224 */   private AssetApprovalManager m_assetApprovalManager = null;
/*  225 */   private ScheduleManager m_scheduleManager = null;
/*  226 */   protected EmailManager m_emailManager = null;
/*  227 */   private CategoryCountCacheManager m_categoryCountCacheManager = null;
/*  228 */   private AssetCategoryManager m_categoryManager = null;
/*  229 */   private RefDataManager m_refDataManager = null;
/*  230 */   private MultiLanguageSearchManager m_searchManager = null;
/*  231 */   private AssetEntityManager m_assetEntityManager = null;
/*  232 */   protected ThumbnailGenerationManager m_thumbnailGenerationManager = null;
/*  233 */   protected AttributeManager m_attributeManager = null;
/*  234 */   protected AttributeValueManager m_attributeValueManager = null;
/*  235 */   protected WorkflowManager m_workflowManager = null;
/*  236 */   protected AssetWorkflowManager m_assetWorkflowManager = null;
/*  237 */   protected FileStoreManager m_fileStoreManager = null;
/*  238 */   protected DirectLinkCacheManager m_directLinkCacheManager = null;
/*  239 */   private UsageManager m_usageManager = null;
/*  240 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*      */ 
/*  243 */   private Object m_oFeaturedLock = new Object();
/*  244 */   private Vector<Asset> m_vecFeaturedImages = null;
/*  245 */   private long m_lFeaturedIndex = -1L;
/*  246 */   private HashMap<Long, Vector<DataBean>> m_hmFeaturedImageBrandMapping = null;
/*      */ 
/*  248 */   private RefDataItem[] m_aAssetTypes = null;
/*  249 */   private Object m_oAssetTypesLock = new Object();
/*      */ 
/*  251 */   private HashMap<Long, Integer> m_hmUserToCurrentFeaturedIndexMap = new HashMap();
/*      */ 
/*  253 */   private HashMap m_hmRecentlyAddedCache = new HashMap();
/*      */ 
/*  255 */   private final Collection<AssetLoadParticipant> m_assetLoadParticipants = new ArrayList();
/*  256 */   private final Collection<AssetSaveParticipant> m_assetSaveParticipants = new ArrayList();
/*  257 */   private final Collection<AssetDeleteParticipant> m_assetDeleteParticipants = new ArrayList();
/*  258 */   private List<UploadAssetExtension> m_uploadAssetExtensions = new ArrayList();
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  277 */     super.startup();
/*  278 */     int iHourOfDay = 0;
/*  279 */     TimerTask task = null;
/*      */ 
/*  282 */     iHourOfDay = AssetBankSettings.getUpdateFeaturedIndexHourOfDay();
/*  283 */     if (iHourOfDay >= 0)
/*      */     {
/*  285 */       task = new TimerTask()
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           try
/*      */           {
/*  291 */             AssetManager.this.updateFeaturedIndex();
/*      */           }
/*      */           catch (Bn2Exception bn2e)
/*      */           {
/*  295 */             AssetManager.this.m_logger.error("AssetManager: Bn2Exception whilst updating featured image index : " + bn2e);
/*      */           }
/*      */         }
/*      */       };
/*  300 */       getScheduleManager().scheduleDailyTask(task, iHourOfDay, false);
/*      */     }
/*      */   }
/*      */ 
         //11/27/2011
            public Vector<Long> deleteAssetRelationship(DBTransaction a_dbTransaction, long a_lParentAssetId, long a_lChildAssetId, long a_lRelationshipTypeId) throws Bn2Exception{
                return this.m_assetRelationshipManager.deleteAssetRelationship(a_dbTransaction, a_lParentAssetId, a_lChildAssetId, a_lRelationshipTypeId);
            }
/*      */   public void addUploadAssetExtension(UploadAssetExtension a_uploadAssetExtension)
/*      */   {
/*  307 */     this.m_uploadAssetExtensions.add(a_uploadAssetExtension);
/*      */   }
/*      */ 
/*      */   public boolean allowFileUploadOnAdd(AssetEntity a_entity)
/*      */   {
/*  326 */     for (UploadAssetExtension uploadAssetExtension : this.m_uploadAssetExtensions)
/*      */     {
/*  328 */       if (!uploadAssetExtension.allowFileUploadOnAdd(a_entity))
/*      */       {
/*  330 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*  334 */     return true;
/*      */   }
/*      */ 
/*      */   public FileFormat getFileFormatForExtension(DBTransaction a_dbTransaction, String a_sExtension)
/*      */     throws Bn2Exception
/*      */   {
/*  351 */     return getFileFormatForExtension(a_dbTransaction, a_sExtension, 0L);
/*      */   }
/*      */ 
/*      */   public FileFormat getFileFormatForExtension(DBTransaction a_dbTransaction, String a_sExtension, long a_lPreferredFormat)
/*      */     throws Bn2Exception
/*      */   {
/*  366 */     Vector formats = getFileFormatsForExtension(a_dbTransaction, a_sExtension);
/*      */ 
/*  368 */     if (formats.size() == 0)
/*      */     {
/*  370 */       return FileFormat.s_unknownFileFormat;
/*      */     }
/*      */     Iterator iter;
/*  373 */     if (a_lPreferredFormat > 0L)
/*      */     {
/*  375 */       for (iter = formats.iterator(); iter.hasNext(); )
/*      */       {
/*  377 */         FileFormat format = (FileFormat)iter.next();
/*  378 */         if (format.getId() == a_lPreferredFormat)
/*      */         {
/*  380 */           return format;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  385 */     return (FileFormat)formats.get(0);
/*      */   }
/*      */ 
/*      */   private Vector<FileFormat> getFileFormatsForExtension(DBTransaction a_dbTransaction, String a_sExtension)
/*      */     throws Bn2Exception
/*      */   {
/*  408 */     FileFormat format = null;
/*  409 */     Connection con = null;
/*  410 */     PreparedStatement psql = null;
/*  411 */     ResultSet rs = null;
/*  412 */     DBTransaction transaction = a_dbTransaction;
/*  413 */     String sSQL = null;
/*  414 */     Vector formats = new Vector();
/*      */ 
/*  416 */     if (a_sExtension == null)
/*      */     {
/*  418 */       formats.add(FileFormat.s_unknownFileFormat);
/*  419 */       return formats;
/*      */     }
/*      */ 
/*  422 */     if (transaction == null)
/*      */     {
/*  424 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  429 */       con = transaction.getConnection();
/*      */ 
/*  432 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/*  434 */       sSQL = "SELECT Id, AssetTypeId, FileExtension, Name, Description, IsIndexable, IsConvertable, IsConversionTarget, ThumbnailFileLocation, ContentType, ConverterClass, ToTextConverterClass, ViewFileInclude, PreviewInclude, PreviewWidth, PreviewHeight, ConvertIndividualLayers, CanViewOriginal FROM FileFormat WHERE " + sqlGenerator.getLowerCaseFunction("FileExtension") + " = ? " + "ORDER BY Id";
/*      */ 
/*  458 */       psql = con.prepareStatement(sSQL);
/*  459 */       psql.setString(1, a_sExtension.toLowerCase());
/*      */ 
/*  461 */       rs = psql.executeQuery();
/*      */ 
/*  463 */       while (rs.next())
/*      */       {
/*  465 */         format = new FileFormat();
/*      */ 
/*  467 */         format.setId(rs.getLong("Id"));
/*  468 */         format.setAssetTypeId(rs.getLong("AssetTypeId"));
/*  469 */         format.setFileExtension(rs.getString("FileExtension"));
/*  470 */         format.setName(rs.getString("Name"));
/*  471 */         format.setDescription(rs.getString("Description"));
/*  472 */         format.setIndexable(rs.getBoolean("IsIndexable"));
/*  473 */         format.setConvertable(rs.getBoolean("IsConvertable"));
/*  474 */         format.setConversionTarget(rs.getBoolean("IsConversionTarget"));
/*  475 */         format.setThumbnailImageLocation(rs.getString("ThumbnailFileLocation"));
/*  476 */         format.setContentType(rs.getString("ContentType"));
/*  477 */         format.setConverterClass(rs.getString("ConverterClass"));
/*  478 */         format.setAssetToTextConverterClass(rs.getString("ToTextConverterClass"));
/*  479 */         format.setViewFileInclude(rs.getString("ViewFileInclude"));
/*  480 */         format.setPreviewInclude(rs.getString("PreviewInclude"));
/*  481 */         format.setPreviewHeight(rs.getInt("PreviewHeight"));
/*  482 */         format.setPreviewWidth(rs.getInt("PreviewWidth"));
/*  483 */         format.setCanConvertIndividualLayers(rs.getBoolean("ConvertIndividualLayers"));
/*  484 */         format.setCanViewOriginal(rs.getBoolean("CanViewOriginal"));
/*      */ 
/*  486 */         formats.add(format);
/*      */       }
/*      */ 
/*  489 */       if (formats.size() == 0)
/*      */       {
/*  491 */         formats.add(FileFormat.s_unknownFileFormat);
/*      */       }
/*      */ 
/*  494 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  498 */       this.m_logger.error("SQL Exception in AssetManager.getFileFormatForExtension : " + e);
/*  499 */       throw new Bn2Exception("SQL Exception in AssetManager.getFileFormatForExtension: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  504 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  508 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  512 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  517 */     return formats;
/*      */   }
/*      */ 
/*      */   public Vector getAmbiguousFileFormats(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  532 */     FileFormat format = null;
/*  533 */     Connection con = null;
/*  534 */     PreparedStatement psql = null;
/*  535 */     ResultSet rs = null;
/*  536 */     DBTransaction transaction = a_dbTransaction;
/*  537 */     String sSQL = null;
/*  538 */     Vector formats = new Vector();
/*      */ 
/*  540 */     if (transaction == null)
/*      */     {
/*  542 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  547 */       con = transaction.getConnection();
/*      */ 
/*  549 */       sSQL = "SELECT FileExtension,COUNT(FileExtension) numFormats FROM FileFormat GROUP BY FileExtension ORDER BY numFormats DESC";
/*      */ 
/*  557 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  559 */       rs = psql.executeQuery();
/*      */ 
/*  561 */       while (rs.next())
/*      */       {
/*  563 */         if (rs.getInt("numFormats") <= 1)
/*      */           continue;
/*  565 */         String sExt = rs.getString("FileExtension");
/*      */ 
/*  567 */         sSQL = "SELECT Id, AssetTypeId, Name FROM FileFormat WHERE FileExtension=? ORDER BY Name";
/*      */ 
/*  569 */         PreparedStatement psqlFormat = con.prepareStatement(sSQL);
/*  570 */         psqlFormat.setString(1, sExt);
/*      */ 
/*  572 */         ResultSet rsFormat = psqlFormat.executeQuery();
/*      */ 
/*  574 */         while (rsFormat.next())
/*      */         {
/*  576 */           format = new FileFormat();
/*  577 */           format.setId(rsFormat.getLong("Id"));
/*  578 */           format.setAssetTypeId(rsFormat.getLong("AssetTypeId"));
/*  579 */           format.setFileExtension(sExt);
/*  580 */           format.setName(rsFormat.getString("Name"));
/*  581 */           formats.add(format);
/*      */         }
/*      */ 
/*  584 */         psqlFormat.close();
/*      */       }
/*      */ 
/*  588 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  592 */       this.m_logger.error("SQL Exception in AssetManager.getFileFormatDuplicates : " + e);
/*  593 */       throw new Bn2Exception("SQL Exception in AssetManager.getFileFileFormatDuplicates: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  598 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  602 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  606 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  611 */     return formats;
/*      */   }
/*      */ 
/*      */   public void updateDateLastModifiedForAssets(DBTransaction a_dbTransaction, Date a_dtDate, String a_sIds)
/*      */     throws Bn2Exception
/*      */   {
/*  622 */     String[] aIds = a_sIds.split("[ ,]+");
/*  623 */     if ((aIds != null) && (aIds.length > 0))
/*      */     {
/*  625 */       Vector vecIds = new Vector();
/*  626 */       for (int i = 0; i < aIds.length; i++)
/*      */       {
/*  628 */         vecIds.add(new Long(aIds[i]));
/*      */       }
/*  630 */       updateDateLastModifiedForAssets(a_dbTransaction, a_dtDate, vecIds);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateDateLastModifiedForAssets(DBTransaction a_dbTransaction, Date a_dtDate, Vector a_vecIds)
/*      */     throws Bn2Exception
/*      */   {
/*  649 */     Connection con = null;
/*  650 */     PreparedStatement psql = null;
/*  651 */     DBTransaction transaction = a_dbTransaction;
/*  652 */     String sSQL = null;
/*      */ 
/*  654 */     if ((a_vecIds != null) && (a_vecIds.size() > 0) && (a_dtDate != null))
/*      */     {
/*  656 */       if (transaction == null)
/*      */       {
/*  658 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/*  663 */         con = transaction.getConnection();
/*  664 */         sSQL = "UPDATE Asset SET DateLastModified=? WHERE Id IN (";
/*      */ 
/*  666 */         for (int i = 0; i < a_vecIds.size(); i++)
/*      */         {
/*  668 */           sSQL = sSQL + ((Long)a_vecIds.elementAt(i)).longValue();
/*      */ 
/*  670 */           if (i >= a_vecIds.size() - 1)
/*      */             continue;
/*  672 */           sSQL = sSQL + ",";
/*      */         }
/*      */ 
/*  675 */         sSQL = sSQL + ")";
/*      */ 
/*  677 */         psql = con.prepareStatement(sSQL);
/*  678 */         psql.setTimestamp(1, new Timestamp(a_dtDate.getTime()));
/*  679 */         psql.executeUpdate();
/*  680 */         psql.close();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/*  684 */         this.m_logger.error("SQL Exception in AssetManager.updateDateLastModifiedForAssets : " + e);
/*  685 */         throw new Bn2Exception("SQL Exception in AssetManager.updateDateLastModifiedForAssets: " + e, e);
/*      */       }
/*      */       finally
/*      */       {
/*  690 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/*  694 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/*  698 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean doesAssetExist(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  720 */     boolean bExists = false;
/*  721 */     Connection con = null;
/*  722 */     PreparedStatement psql = null;
/*  723 */     ResultSet rs = null;
/*  724 */     DBTransaction transaction = a_dbTransaction;
/*  725 */     String sSQL = null;
/*      */ 
/*  727 */     if (transaction == null)
/*      */     {
/*  729 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  734 */       con = transaction.getConnection();
/*      */ 
/*  736 */       sSQL = "SELECT * FROM Asset WHERE Id=?";
/*      */ 
/*  738 */       psql = con.prepareStatement(sSQL);
/*  739 */       psql.setLong(1, a_lAssetId);
/*      */ 
/*  741 */       rs = psql.executeQuery();
/*      */ 
/*  743 */       if (rs.next())
/*      */       {
/*  745 */         bExists = true;
/*      */       }
/*      */ 
/*  748 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  752 */       this.m_logger.error("SQL Exception in AssetManager.doesAssetExist : " + e);
/*  753 */       throw new Bn2Exception("SQL Exception in AssetManager.doesAssetExist: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  758 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  762 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  766 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  771 */     return bExists;
/*      */   }
/*      */ 
/*      */   public FileFormat getFileFormatForFile(DBTransaction a_dbTransaction, String a_sFileName)
/*      */     throws Bn2Exception
/*      */   {
/*  793 */     return getFileFormatForFile(a_dbTransaction, a_sFileName, 0L);
/*      */   }
/*      */ 
/*      */   public FileFormat getFileFormatForFile(DBTransaction a_dbTransaction, String a_sFileName, long a_lPreferredFormat)
/*      */     throws Bn2Exception
/*      */   {
/*  810 */     String sExtension = null;
/*      */ 
/*  813 */     sExtension = FileUtil.getSuffix(a_sFileName);
/*      */ 
/*  816 */     if (sExtension == null)
/*      */     {
/*  818 */       return FileFormat.s_unknownFileFormat;
/*      */     }
/*      */ 
/*  821 */     return getFileFormatForExtension(a_dbTransaction, sExtension, a_lPreferredFormat);
/*      */   }
/*      */ 
/*      */   public Vector<FileFormat> getAllFileFormatsForFile(DBTransaction a_dbTransaction, String a_sFileName)
/*      */     throws Bn2Exception
/*      */   {
/*  833 */     String sExtension = FileUtil.getSuffix(a_sFileName);
/*      */ 
/*  836 */     if (sExtension == null)
/*      */     {
/*  838 */       Vector formats = new Vector();
/*  839 */       formats.add(FileFormat.s_unknownFileFormat);
/*  840 */       return formats;
/*      */     }
/*      */ 
/*  843 */     return getFileFormatsForExtension(a_dbTransaction, sExtension);
/*      */   }
/*      */ 
/*      */   public IAssetManagerImpl getAssetManagerForAssetType(long a_lAssetTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  862 */     IAssetManagerImpl assetManagerImpl = null;
/*      */ 
/*  864 */     if (a_lAssetTypeId == 2L)
/*      */     {
/*  866 */       assetManagerImpl = this.m_imageAssetManager;
/*      */     }
/*  868 */     else if (a_lAssetTypeId == 3L)
/*      */     {
/*  870 */       assetManagerImpl = this.m_videoAssetManager;
/*      */     }
/*  872 */     else if (a_lAssetTypeId == 4L)
/*      */     {
/*  874 */       assetManagerImpl = this.m_audioAssetManager;
/*      */     }
/*  876 */     else if ((a_lAssetTypeId == 1L) || (a_lAssetTypeId <= 0L))
/*      */     {
/*  878 */       assetManagerImpl = this.m_fileAssetManager;
/*      */     }
/*      */     else
/*      */     {
/*  882 */       this.m_logger.error("AssetManager.getAssetManagerForAssetTypeId() : Unknown asset type id " + a_lAssetTypeId);
/*  883 */       throw new Bn2Exception("AssetManager.getAssetManagerForAssetTypeId() : Unknown asset type id " + a_lAssetTypeId);
/*      */     }
/*      */ 
/*  886 */     return assetManagerImpl;
/*      */   }
/*      */ 
/*      */   private IAssetManagerImpl getAssetManagerForAsset(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception, AssetNotFoundException
/*      */   {
/*  908 */     long lAssetTypeId = 0L;
/*  909 */     IAssetManagerImpl assetManagerImpl = null;
/*  910 */     Connection con = null;
/*  911 */     PreparedStatement psql = null;
/*  912 */     ResultSet rs = null;
/*  913 */     DBTransaction transaction = a_dbTransaction;
/*  914 */     String sSQL = null;
/*      */ 
/*  916 */     if (transaction == null)
/*      */     {
/*  918 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  923 */       con = transaction.getConnection();
/*      */ 
/*  925 */       sSQL = "SELECT AssetTypeId FROM Asset WHERE Id=?";
/*      */ 
/*  927 */       psql = con.prepareStatement(sSQL);
/*  928 */       psql.setLong(1, a_lAssetId);
/*      */ 
/*  930 */       rs = psql.executeQuery();
/*      */ 
/*  932 */       if (rs.next())
/*      */       {
/*  934 */         lAssetTypeId = rs.getLong("AssetTypeId");
/*      */       }
/*      */       else
/*      */       {
/*  938 */         this.m_logger.error("AssetManager.getAsset() : No asset found for id " + a_lAssetId);
/*  939 */         throw new AssetNotFoundException("AssetManager.getAsset() : No asset found for id " + a_lAssetId);
/*      */       }
/*      */ 
/*  942 */       psql.close();
/*      */ 
/*  944 */       assetManagerImpl = getAssetManagerForAssetType(lAssetTypeId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  948 */       this.m_logger.error("SQL Exception in AssetManager.getAssetTypeIdForAsset : " + e);
/*  949 */       throw new Bn2Exception("SQL Exception in AssetManager.getAssetTypeIdForAsset: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  954 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  958 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  962 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  967 */     return assetManagerImpl;
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws AssetNotFoundException, Bn2Exception
/*      */   {
/*  979 */     return getAsset(a_dbTransaction, a_lAssetId, null);
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vVisibleAttributeIds)
/*      */     throws AssetNotFoundException, Bn2Exception
/*      */   {
/*  985 */     return getAsset(a_dbTransaction, a_lAssetId, a_vVisibleAttributeIds, true, true);
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vVisibleAttributeIds, boolean a_bGetRelatedAssets, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/* 1014 */     return getAsset(a_dbTransaction, a_lAssetId, a_vVisibleAttributeIds, a_bGetRelatedAssets, LanguageConstants.k_defaultLanguage, a_bGetFeedback);
/*      */   }
/*      */ 
/*      */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vVisibleAttributeIds, boolean a_bGetRelatedAssets, Language a_language, boolean a_bGetFeedback)
/*      */     throws Bn2Exception, AssetNotFoundException
/*      */   {
/* 1036 */     Asset asset = null;
/* 1037 */     IAssetManagerImpl assetManagerImpl = null;
/*      */ 
/* 1039 */     assetManagerImpl = getAssetManagerForAsset(a_dbTransaction, a_lAssetId);
/*      */ 
/* 1041 */     asset = assetManagerImpl.getAsset(a_dbTransaction, a_lAssetId, null, a_vVisibleAttributeIds, a_language, a_bGetFeedback);
/*      */ 
/* 1044 */     asset.setIsPromoted(assetIsPromoted(a_dbTransaction, a_lAssetId));
/* 1045 */     asset.setIsFeatured(assetIsFeatured(a_dbTransaction, a_lAssetId));
/*      */ 
/* 1048 */     HashMap hmBrands = getFeaturedImageBrandMapping(a_dbTransaction);
/* 1049 */     Long olAssetId = new Long(a_lAssetId);
/* 1050 */     if (hmBrands.containsKey(olAssetId))
/*      */     {
/* 1052 */       Vector vecBrands = (Vector)hmBrands.get(olAssetId);
/* 1053 */       asset.setFeaturedInBrandsList(vecBrands);
/*      */     }
/*      */ 
/* 1057 */     if (a_bGetRelatedAssets)
/*      */     {
/* 1059 */       if ((StringUtils.isEmpty(asset.getParentAssetIdsAsString())) && (StringUtils.isEmpty(asset.getChildAssetIdsAsString())) && (StringUtils.isEmpty(asset.getPeerAssetIdsAsString())))
/*      */       {
/* 1061 */         asset.setPeerAssetIdsAsString(this.m_assetRelationshipManager.getRelatedAssetIdString(a_dbTransaction, a_lAssetId, 1L));
/* 1062 */         asset.setChildAssetIdsAsString(this.m_assetRelationshipManager.getRelatedAssetIdString(a_dbTransaction, a_lAssetId, 2L));
/* 1063 */         asset.setParentAssetIdsAsString(this.m_assetRelationshipManager.getRelatedAssetIdString(a_dbTransaction, a_lAssetId, 3L));
/*      */       }
/* 1065 */       asset.setRelationshipDescriptions(this.m_assetRelationshipManager.getRelationshipDescriptionsForAssetAsTarget(a_dbTransaction, asset.getId()));
/*      */     }
/*      */ 
/* 1068 */     for (AssetLoadParticipant participant : this.m_assetLoadParticipants)
/*      */     {
/* 1070 */       participant.assetWasLoaded(asset);
/*      */     }
/*      */ 
/* 1073 */     return asset;
/*      */   }
/*      */ 
/*      */   public Asset getAssetStaticOnly(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1088 */     return getAssetStaticOnly(a_dbTransaction, a_lAssetId, null, false);
/*      */   }
/*      */ 
/*      */   public Asset getAssetStaticOnly(DBTransaction a_dbTransaction, long a_lAssetId, Asset a_asset, boolean a_bGetImportedAsset)
/*      */     throws Bn2Exception
/*      */   {
/* 1111 */     Asset asset = a_asset;
/* 1112 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1114 */     if (transaction == null)
/*      */     {
/* 1116 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1121 */       Connection con = transaction.getConnection();
/*      */ 
/* 1123 */       String sSQL = "SELECT ass.Id assId, ass.Code, ass.AssetTypeId assTypeId, ass.FileLocation, ass.OriginalFileLocation, ass.FileSizeInBytes, ass.DateAdded, ass.DateLastModified, ass.ExpiryDate, ass.Author, ass.IsUnsubmitted, ass.Price, ass.ImportedAssetId, ass.IsPreviewRestricted, ass.IsSensitive, ass.BulkUploadTimestamp, ass.Synchronised, ass.CurrentVersionId, ass.AgreementTypeId, ass.VersionNumber, ass.NumViews, ass.NumDownloads, ass.LastDownloaded, ass.AssetEntityId, ass.OriginalFilename, ass.HasSubstituteFile, ass.IsBrandTemplate, ass.CanEmbedFile, ass.AdvancedViewing, ass.AdvancedViewing, ass.FileCheckOutTime, u.Id addedUserId, u.Username addedUsername, u.Forename addedForename, u.Surname addedSurname, u.EmailAddress addedEmailAddress, u2.Id lastModUserId, u2.Username lastModUsername, u2.Forename lastModForename, u2.Surname lastModSurname, u2.EmailAddress lastModEmailAddress, u3.Id checkedOutUserId, u3.Username checkedOutUsername, u3.Forename checkedOutForename, u3.Surname checkedOutSurname, u3.EmailAddress checkedOutEmailAddress, ff.Id ffId, ff.AssetTypeId, ff.Name, ff.Description ffDescription, ff.FileExtension, ff.IsIndexable, ff.IsConvertable, ff.IsConversionTarget, ff.ThumbnailFileLocation ffThumbnailFileLocation,ff.ContentType, ff.ConverterClass, ff.ToTextConverterClass,ff.ViewFileInclude, ff.PreviewInclude, ff.PreviewWidth, ff.PreviewHeight, ff.ConvertIndividualLayers, ff.CanViewOriginal, ass.ThumbnailFileLocation assThumbnailFileLocation, ass.SmallFileLocation assSmallFileLocation, ass.MediumFileLocation assMediumFileLocation, ra.RelationshipTypeId raRelationshipTypeId, ra.ChildId raChildId, ra.ParentId raParentId, cea.Id ceaId, cea.ParentId ceaParentId, cea.Name ceaName, cea.CategoryTypeId ceaCategoryTypeId FROM Asset ass LEFT JOIN AssetBankUser u ON ass.AddedByUserId = u.Id LEFT JOIN FileFormat ff ON ass.FileFormatId = ff.Id LEFT JOIN AssetBankUser u2 ON ass.LastModifiedByUserId = u2.Id LEFT JOIN AssetBankUser u3 ON ass.FileCheckedOutByUserId = u3.Id LEFT JOIN RelatedAsset ra ON ra.ChildId=? OR ra.ParentId=? LEFT JOIN CM_Category cea ON ass.Id=cea.ExtensionAssetId WHERE ";
/*      */ 
/* 1208 */       if (a_bGetImportedAsset)
/*      */       {
/* 1210 */         sSQL = sSQL + "ass.ImportedAssetId = ? ";
/*      */       }
/*      */       else
/*      */       {
/* 1214 */         sSQL = sSQL + "ass.Id = ? ";
/*      */       }
/*      */ 
/* 1217 */       sSQL = sSQL + "ORDER BY ra.SequenceNumber";
/*      */ 
/* 1220 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 1222 */       int iCol = 1;
/*      */ 
/* 1225 */       psql.setLong(iCol++, a_lAssetId);
/* 1226 */       psql.setLong(iCol++, a_lAssetId);
/* 1227 */       psql.setLong(iCol++, a_lAssetId);
/*      */ 
/* 1229 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1231 */       StringBuffer sChildIds = new StringBuffer();
/* 1232 */       StringBuffer sPeerIds = new StringBuffer();
/* 1233 */       StringBuffer sParentIds = new StringBuffer();
/* 1234 */       boolean bFirst = true;
/*      */ 
/* 1236 */       while (rs.next())
/*      */       {
/* 1238 */         if (bFirst)
/*      */         {
/* 1240 */           if (asset == null)
/*      */           {
/* 1242 */             asset = new Asset();
/*      */           }
/*      */ 
/* 1246 */           asset.setId(rs.getLong("assId"));
/* 1247 */           asset.setCode(rs.getString("Code"));
/* 1248 */           asset.setFileLocation(rs.getString("FileLocation"));
/* 1249 */           asset.setOriginalFileLocation(rs.getString("OriginalFileLocation"));
/* 1250 */           asset.setFileSizeInBytes(rs.getLong("FileSizeInBytes"));
/* 1251 */           asset.setDateAdded(rs.getTimestamp("DateAdded"));
/* 1252 */           asset.setDateLastModified(rs.getTimestamp("DateLastModified"));
/* 1253 */           asset.setAuthor(rs.getString("Author"));
/* 1254 */           asset.setIsUnsubmitted(rs.getBoolean("IsUnsubmitted"));
/* 1255 */           asset.setExpiryDate(rs.getDate("ExpiryDate"));
/* 1256 */           asset.getPrice().setAmount(rs.getLong("Price"));
/* 1257 */           asset.setImportedAssetId(rs.getString("ImportedAssetId"));
/* 1258 */           asset.setIsRestricted(rs.getBoolean("IsPreviewRestricted"));
/* 1259 */           asset.setIsSensitive(rs.getBoolean("IsSensitive"));
/* 1260 */           asset.setSynchronised(rs.getBoolean("Synchronised"));
/* 1261 */           asset.setCurrentVersionId(rs.getLong("CurrentVersionId"));
/* 1262 */           asset.setVersionNumber(rs.getInt("VersionNumber"));
/* 1263 */           asset.setNumViews(rs.getInt("NumViews"));
/* 1264 */           asset.setNumDownloads(rs.getInt("NumDownloads"));
/* 1265 */           asset.setDateLastDownloaded(rs.getTimestamp("LastDownloaded"));
/* 1266 */           asset.setOriginalFilename(rs.getString("OriginalFilename"));
/* 1267 */           asset.setAgreementTypeId(rs.getLong("AgreementTypeId"));
/* 1268 */           asset.setHasSubstituteFile(rs.getBoolean("HasSubstituteFile"));
/* 1269 */           asset.setIsBrandTemplate(rs.getBoolean("IsBrandTemplate"));
/* 1270 */           asset.setCanEmbedFile(rs.getBoolean("CanEmbedFile"));
/* 1271 */           asset.setAdvancedViewing(rs.getBoolean("AdvancedViewing"));
/* 1272 */           asset.setExtendsCategory(buildExtendedCategoryInfo(a_dbTransaction, rs));
/* 1273 */           asset.setDateCheckedOut(rs.getTimestamp("FileCheckOutTime"));
/*      */ 
/* 1275 */           if (rs.getLong("AssetEntityId") > 0L)
/*      */           {
/* 1277 */             asset.setEntity(new AssetEntity(rs.getLong("AssetEntityId")));
/*      */           }
/*      */ 
/* 1280 */           if (rs.getTimestamp("BulkUploadTimestamp") != null)
/*      */           {
/* 1282 */             asset.setBulkUploadTimestamp(new Date(rs.getTimestamp("BulkUploadTimestamp").getTime()));
/*      */           }
/*      */ 
/* 1285 */           ABUser user = new ABUser();
/* 1286 */           user.setId(rs.getLong("addedUserId"));
/* 1287 */           user.setForename(rs.getString("addedForename"));
/* 1288 */           user.setSurname(rs.getString("addedSurname"));
/* 1289 */           user.setUsername(rs.getString("addedUsername"));
/* 1290 */           user.setEmailAddress(rs.getString("addedEmailAddress"));
/*      */ 
/* 1292 */           asset.setAddedByUser(user);
/*      */ 
/* 1295 */           user = new ABUser();
/* 1296 */           user.setId(rs.getLong("lastModUserId"));
/* 1297 */           user.setForename(rs.getString("lastModForename"));
/* 1298 */           user.setSurname(rs.getString("lastModSurname"));
/* 1299 */           user.setUsername(rs.getString("lastModUsername"));
/* 1300 */           user.setEmailAddress(rs.getString("lastModEmailAddress"));
/*      */ 
/* 1302 */           asset.setLastModifiedByUser(user);
/*      */ 
/* 1305 */           user = new ABUser();
/* 1306 */           user.setId(rs.getLong("checkedOutUserId"));
/* 1307 */           user.setForename(rs.getString("checkedOutForename"));
/* 1308 */           user.setSurname(rs.getString("checkedOutSurname"));
/* 1309 */           user.setUsername(rs.getString("checkedOutUsername"));
/* 1310 */           user.setEmailAddress(rs.getString("checkedOutEmailAddress"));
/*      */ 
/* 1312 */           asset.setCheckedOutByUser(user);
/*      */ 
/* 1314 */           asset.setTypeId(rs.getLong("assTypeId"));
/*      */ 
/* 1316 */           if (rs.getLong("ffId") > 0L)
/*      */           {
/* 1318 */             FileFormat format = new FileFormat();
/* 1319 */             format.setId(rs.getLong("ffId"));
/* 1320 */             format.setAssetTypeId(rs.getLong("AssetTypeId"));
/* 1321 */             format.setName(rs.getString("Name"));
/* 1322 */             format.setDescription(rs.getString("ffDescription"));
/* 1323 */             format.setFileExtension(rs.getString("FileExtension"));
/* 1324 */             format.setIndexable(rs.getBoolean("IsIndexable"));
/* 1325 */             format.setConvertable(rs.getBoolean("IsConvertable"));
/* 1326 */             format.setConversionTarget(rs.getBoolean("IsConversionTarget"));
/* 1327 */             format.setThumbnailImageLocation(rs.getString("ffThumbnailFileLocation"));
/* 1328 */             format.setContentType(rs.getString("ContentType"));
/* 1329 */             format.setConverterClass(rs.getString("ConverterClass"));
/* 1330 */             format.setAssetToTextConverterClass(rs.getString("ToTextConverterClass"));
/* 1331 */             format.setViewFileInclude(rs.getString("ViewFileInclude"));
/* 1332 */             format.setPreviewInclude(rs.getString("PreviewInclude"));
/* 1333 */             format.setPreviewHeight(rs.getInt("PreviewHeight"));
/* 1334 */             format.setPreviewWidth(rs.getInt("PreviewWidth"));
/* 1335 */             format.setCanConvertIndividualLayers(rs.getBoolean("ConvertIndividualLayers"));
/* 1336 */             format.setCanViewOriginal(rs.getBoolean("CanViewOriginal"));
/*      */ 
/* 1338 */             asset.setFormat(format);
/*      */           }
/* 1340 */           else if (asset.getTypeId() > 0L)
/*      */           {
/* 1342 */             asset.setFormat(FileFormat.s_unknownFileFormat);
/*      */           }
/*      */           else
/*      */           {
/* 1346 */             asset.setFormat(FileFormat.s_noFileFormat);
/*      */           }
/*      */ 
/* 1349 */           AssetDBUtil.addImageFiles(rs, asset);
/* 1350 */           bFirst = false;
/*      */         }
/*      */ 
/* 1353 */         long lRelationshipTypeId = rs.getLong("raRelationshipTypeId");
/*      */ 
/* 1356 */         if (lRelationshipTypeId > 0L)
/*      */         {
/* 1358 */           if (lRelationshipTypeId == 2L)
/*      */           {
/* 1360 */             if (rs.getLong("raChildId") == asset.getId())
/*      */             {
/* 1362 */               StringUtil.appendDelimitedValue(sParentIds, rs.getLong("raParentId"), ',');
/*      */             }
/*      */             else
/*      */             {
/* 1366 */               StringUtil.appendDelimitedValue(sChildIds, rs.getLong("raChildId"), ',');
/*      */             }
/*      */           }
/* 1369 */           else if (lRelationshipTypeId == 1L)
/*      */           {
/* 1371 */             if (rs.getLong("raChildId") == asset.getId())
/*      */             {
/* 1373 */               StringUtil.appendDelimitedValue(sPeerIds, rs.getLong("raParentId"), ',');
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1379 */       psql.close();
/*      */ 
/* 1381 */       if (asset != null)
/*      */       {
/* 1384 */         if (sParentIds.length() > 0)
/*      */         {
/* 1386 */           asset.setParentAssetIdsAsString(sParentIds.toString());
/*      */         }
/* 1388 */         if (sChildIds.length() > 0)
/*      */         {
/* 1390 */           asset.setChildAssetIdsAsString(sChildIds.toString());
/*      */         }
/* 1392 */         if (sPeerIds.length() > 0)
/*      */         {
/* 1394 */           asset.setPeerAssetIdsAsString(sPeerIds.toString());
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1400 */       throw new Bn2Exception("SQL Exception whilst getting an asset from the database : ", e);
/*      */     }
/*      */     finally
/*      */     {
/* 1405 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1409 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1413 */           this.m_logger.error("SQL Exception whilst trying to close connection", sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1418 */     return asset;
/*      */   }
/*      */ 
/*      */   public Vector getImportedAssets(DBTransaction a_dbTransaction, String a_sImportedAssetId, Vector a_vVisibleAttributeIds, boolean a_bGetRelatedAssets, long a_lImportedAssetId)
/*      */     throws AssetNotFoundException, Bn2Exception
/*      */   {
/* 1441 */     Connection con = null;
/* 1442 */     ResultSet rs = null;
/* 1443 */     String sSQL = null;
/* 1444 */     Vector vecAssets = new Vector();
/* 1445 */     Vector vecIds = new Vector();
/* 1446 */     DBTransaction transaction = a_dbTransaction;
/* 1447 */     long lAssetId = a_lImportedAssetId;
/*      */ 
/* 1449 */     if (lAssetId <= 0L)
/*      */     {
/* 1451 */       if (transaction == null)
/*      */       {
/* 1453 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1458 */         con = transaction.getConnection();
/*      */ 
/* 1461 */         sSQL = "SELECT Id FROM Asset WHERE ImportedAssetId=?";
/*      */ 
/* 1463 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 1464 */         psql.setString(1, a_sImportedAssetId);
/* 1465 */         rs = psql.executeQuery();
/*      */ 
/* 1467 */         while (rs.next())
/*      */         {
/* 1469 */           vecIds.add(new Long(rs.getLong("Id")));
/*      */         }
/*      */ 
/* 1472 */         psql.close();
/*      */ 
/* 1475 */         if (vecIds.isEmpty())
/*      */         {
/* 1478 */           sSQL = "SELECT Id FROM Asset WHERE OriginalFilename=?";
/*      */ 
/* 1480 */           psql = con.prepareStatement(sSQL);
/*      */ 
/* 1482 */           psql.setString(1, a_sImportedAssetId);
/* 1483 */           rs = psql.executeQuery();
/*      */ 
/* 1485 */           while (rs.next())
/*      */           {
/* 1487 */             vecIds.add(new Long(rs.getLong("Id")));
/*      */           }
/* 1489 */           psql.close();
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 1496 */         this.m_logger.error("SQL Exception in AssetManager.getImportedAsset : " + e);
/* 1497 */         throw new Bn2Exception("SQL Exception in AssetManager.getImportedAsset : ", e);
/*      */       }
/*      */       finally
/*      */       {
/* 1502 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/* 1506 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 1510 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1517 */       vecIds.add(new Long(lAssetId));
/*      */     }
/*      */ 
/* 1520 */     if (vecIds.size() > 0)
/*      */     {
/* 1522 */       for (int i = 0; i < vecIds.size(); i++)
/*      */       {
/* 1524 */         Asset asset = getAsset(a_dbTransaction, ((Long)vecIds.elementAt(i)).longValue(), a_vVisibleAttributeIds, a_bGetRelatedAssets, false);
/* 1525 */         vecAssets.add(asset);
/*      */       }
/*      */     }
/*      */ 
/* 1529 */     return vecAssets;
/*      */   }
/*      */ 
/*      */   public Vector<Asset> getAssets(DBTransaction a_dbTransaction, Vector<Long> a_vAssetIds, boolean a_bGetRelatedAssetIds, boolean a_bGetFeedback)
/*      */     throws Bn2Exception
/*      */   {
/* 1551 */     Vector assets = this.m_fileAssetManager.getAssets(a_dbTransaction, a_vAssetIds, null, -1, -1, a_bGetRelatedAssetIds, a_bGetFeedback);
/*      */ 
/* 1559 */     for (AssetLoadParticipant participant : this.m_assetLoadParticipants)
/*      */     {
/* 1561 */       participant.assetsWereLoaded(assets);
/*      */     }
/*      */ 
/* 1564 */     return assets;
/*      */   }
/*      */ 
/*      */   public Vector<Asset> getAssetsByIdAndBatchSize(DBTransaction a_dbTransaction, Vector a_vAssetIds, Vector a_vAllowedCategoryIds, long a_lStartId, int a_iBatchSize, boolean a_bGetRelatedAssetIds, boolean a_bGetFeedback, boolean a_bGetPreviousAgreements)
/*      */     throws Bn2Exception
/*      */   {
/* 1592 */     return this.m_fileAssetManager.getAssetsByIdAndBatchSize(a_dbTransaction, a_vAssetIds, a_vAllowedCategoryIds, a_lStartId, a_iBatchSize, a_bGetRelatedAssetIds, a_bGetFeedback, a_bGetPreviousAgreements);
/*      */   }
/*      */ 
/*      */   public Asset saveAsset(DBTransaction a_dbTransaction, Asset a_asset, AssetFileSource a_source, long a_lUserId, AssetConversionInfo a_conversionInfo, AssetFileSource a_substituteSource, boolean a_bForceThumbnailRegeneration, int a_iSaveTypeId, WorkflowUpdate a_update)
/*      */     throws Bn2Exception, AssetFileReadException
/*      */   {
/* 1625 */     return saveAsset(a_dbTransaction, a_asset, a_source, a_lUserId, a_conversionInfo, a_substituteSource, a_bForceThumbnailRegeneration, a_iSaveTypeId, 0L, a_update);
/*      */   }
/*      */ 
/*      */   public Asset saveAsset(DBTransaction a_dbTransaction, Asset a_asset, AssetFileSource a_source, long a_lUserId, AssetConversionInfo a_conversionInfo, AssetFileSource a_substituteSource, boolean a_bForceThumbnailRegeneration, int a_iSaveTypeId)
/*      */     throws Bn2Exception, AssetFileReadException
/*      */   {
/* 1647 */     return saveAsset(a_dbTransaction, a_asset, a_source, a_lUserId, a_conversionInfo, a_substituteSource, a_bForceThumbnailRegeneration, a_iSaveTypeId, 0L, null);
/*      */   }
/*      */ 
/*      */   public Asset saveAsset(DBTransaction a_dbTransaction, Asset a_asset, AssetFileSource a_source, long a_lUserId, AssetConversionInfo a_conversionInfo, AssetFileSource a_substituteSource, boolean a_bForceThumbnailRegeneration, int a_iSaveTypeId, long a_lFileFormatId, WorkflowUpdate a_update)
/*      */     throws Bn2Exception, AssetFileReadException
/*      */   {
/* 1672 */     return saveAsset(a_dbTransaction, a_asset, a_source, a_lUserId, a_conversionInfo, a_substituteSource, a_bForceThumbnailRegeneration, a_iSaveTypeId, a_lFileFormatId, a_update, false, false);
/*      */   }
/*      */ 
/*      */   public Asset saveAsset(DBTransaction a_dbTransaction, Asset a_asset, AssetFileSource a_source, long a_lUserId, AssetConversionInfo a_conversionInfo, AssetFileSource a_substituteSource, boolean a_bForceThumbnailRegeneration, int a_iSaveTypeId, long a_lFileFormatId, WorkflowUpdate a_update, boolean a_bForceClearCaches, boolean a_bDeferIndexing)
/*      */     throws Bn2Exception, AssetFileReadException
/*      */   {
/* 1722 */     String ksMethodName = "saveAsset";
/*      */ 
/* 1725 */     boolean bIsNew = a_asset.getId() <= 0L;
/*      */ 
/* 1727 */     FileFormat format = null;
/* 1728 */     IAssetManagerImpl assetManagerImpl = null;
/* 1729 */     boolean bNewWithPresetId = false;
/*      */ 
/* 1731 */     if ((a_source != null) && (a_source.getIsNewWithFixedId()))
/*      */     {
/* 1733 */       bNewWithPresetId = true;
/*      */     }
/* 1735 */     AssetSaveContext context = new AssetSaveContext(a_asset, (bIsNew) || (bNewWithPresetId), a_update);
/*      */ 
/* 1738 */     if ((a_source == null) || (!a_source.isValid()))
/*      */     {
/* 1740 */       if ((!bIsNew) && (!bNewWithPresetId))
/*      */       {
/* 1742 */         assetManagerImpl = getAssetManagerForAsset(a_dbTransaction, a_asset.getId());
/*      */       }
/* 1744 */       else if (a_asset.getTypeId() > 0L)
/*      */       {
/* 1746 */         assetManagerImpl = getAssetManagerForAssetType(a_asset.getTypeId());
/*      */       }
/*      */       else
/*      */       {
/* 1750 */         assetManagerImpl = this.m_fileAssetManager;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1756 */       format = getFileFormatForFile(a_dbTransaction, a_source.getFilename(), a_lFileFormatId);
/*      */ 
/* 1759 */       a_asset.setFormat(format);
/*      */ 
/* 1762 */       assetManagerImpl = getAssetManagerForAssetType(format.getAssetTypeId());
/*      */ 
/* 1765 */       a_asset.setTypeId(format.getAssetTypeId());
/*      */     }
/*      */ 
/* 1769 */     for (AssetSaveParticipant participant : this.m_assetSaveParticipants)
/*      */     {
/* 1771 */       participant.initAssetSave(context);
/*      */     }
/*      */ 
/* 1774 */     if (context.getNeedOriginalAsset())
/*      */     {
/* 1776 */       if ((bIsNew) || (bNewWithPresetId))
/*      */       {
/* 1778 */         context.setOriginalAsset(null);
/*      */       }
/*      */       else
/*      */       {
/* 1782 */         context.setOriginalAsset(getAsset(a_dbTransaction, a_asset.getId(), null, false, false));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1787 */     if ((!bIsNew) && ((a_source == null) || (!a_source.getIsNewWithFixedId())) && (a_iSaveTypeId != 0))
/*      */     {
/* 1789 */       Asset previousAssetVersion = getAsset(a_dbTransaction, a_asset.getId(), null, false, false);
/*      */ 
/* 1793 */       if (a_iSaveTypeId == 2)
/*      */       {
/* 1795 */         prepareForNewVersion(previousAssetVersion);
/*      */ 
/* 1797 */         previousAssetVersion.setCurrentVersionId(a_asset.getId());
/* 1798 */         previousAssetVersion.setDateAdded(a_asset.getDateAdded());
/* 1799 */         previousAssetVersion.setImportedAssetId(null);
/* 1800 */         previousAssetVersion.setIsUnsubmitted(false);
/*      */ 
/* 1803 */         previousAssetVersion = assetManagerImpl.saveAsset(a_dbTransaction, previousAssetVersion, null, a_lUserId, null, null, false, false, a_iSaveTypeId);
/*      */ 
/* 1814 */         this.m_assetWorkflowManager.approveAssetEndAllWorkflows(a_dbTransaction, previousAssetVersion, true);
/*      */ 
/* 1817 */         previousAssetVersion = getAsset(a_dbTransaction, previousAssetVersion.getId(), null, false, true);
/*      */ 
/* 1820 */         this.m_searchManager.indexDocument(previousAssetVersion, true);
/*      */ 
/* 1823 */         a_asset.setVersionNumber(previousAssetVersion.getVersionNumber() + 1);
/* 1824 */         a_asset.setDateAdded(new Date());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1829 */     Vector vPreviousCatIds = new Vector(0);
/* 1830 */     Vector vPreviousAccessLevelIds = new Vector(0);
/* 1831 */     Map mExistingKeywords = new HashMap();
/*      */ 
/* 1833 */     if (!bIsNew)
/*      */     {
/* 1836 */       vPreviousCatIds = this.m_categoryManager.getCategoryIdsForItem(a_dbTransaction, 1L, a_asset.getId());
/* 1837 */       vPreviousAccessLevelIds = this.m_categoryManager.getCategoryIdsForItem(a_dbTransaction, 2L, a_asset.getId());
/*      */ 
/* 1840 */       for (AttributeValue value : a_asset.getAttributeValues())
/*      */       {
/* 1842 */         if (value.getAttribute().getTypeId() == 7L)
/*      */         {
/* 1844 */           mExistingKeywords.put(Long.valueOf(value.getAttribute().getTreeId()), this.m_categoryManager.getCategoryIdsForItem(a_dbTransaction, value.getAttribute().getTreeId(), a_asset.getId()));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1851 */     Asset asset = assetManagerImpl.saveAsset(a_dbTransaction, a_asset, a_source, a_lUserId, a_conversionInfo, a_substituteSource, a_bForceThumbnailRegeneration, false, a_iSaveTypeId);
/*      */ 
/* 1861 */     context.setAsset(asset);
/*      */ 
/* 1864 */     DBTransaction dbTransaction = null;
/*      */ 
/* 1866 */     if (a_dbTransaction == null)
/*      */     {
/* 1868 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */     else
/*      */     {
/* 1872 */       dbTransaction = a_dbTransaction;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1877 */       if (asset.getIsPromoted())
/*      */       {
/* 1879 */         addAssetToPromoted(dbTransaction, asset.getId());
/*      */       }
/*      */       else
/*      */       {
/* 1883 */         removeAssetFromPromoted(dbTransaction, asset.getId());
/*      */       }
/*      */ 
/* 1888 */       if (asset.getTypeId() == 2L)
/*      */       {
/* 1890 */         if (asset.getIsFeatured())
/*      */         {
/* 1892 */           addAssetToFeatured(dbTransaction, asset.getId());
/*      */         }
/*      */         else
/*      */         {
/* 1896 */           removeAssetFromFeatured(dbTransaction, asset.getId());
/*      */         }
/*      */ 
/* 1900 */         saveFeaturedImageBrandMapping(dbTransaction, asset);
/*      */       }
/*      */ 
/* 1904 */       for (AssetSaveParticipant participant : this.m_assetSaveParticipants)
/*      */       {
/* 1906 */         participant.save(context);
/*      */       }
/*      */ 
/* 1916 */       asset = getAsset(dbTransaction, asset.getId(), null, true, true);
/* 1917 */       context.setAsset(asset);
/*      */     }
/*      */     finally
/*      */     {
/* 1922 */       if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1926 */           dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1930 */           this.m_logger.error("AssetManager.saveAsset: SQL Exception whilst trying to close connection: " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1935 */     Vector vecCategories = asset.getDescriptiveCategories();
/* 1936 */     Set newCatIds = com.bright.assetbank.category.util.CategoryUtil.uniqueIdsFromCategories(vecCategories);
/* 1937 */     Vector vecAccessLevels = asset.getPermissionCategories();
/* 1938 */     Set newAccessLevelIds = com.bright.assetbank.category.util.CategoryUtil.uniqueIdsFromCategories(vecAccessLevels);
/*      */ 
/* 1943 */     clearCategoryCountCache(new Vector(CollectionUtils.union(vPreviousCatIds, newCatIds)), 1L);
/* 1944 */     clearCategoryCountCache(new Vector(CollectionUtils.union(vPreviousAccessLevelIds, newAccessLevelIds)), 2L);
/*      */ 
/* 1947 */     for (AttributeValue value : a_asset.getAttributeValues())
/*      */     {
/* 1949 */       if (value.getAttribute().getTypeId() == 7L)
/*      */       {
/* 1951 */         Vector keywords = com.bright.framework.category.util.CategoryUtil.getCategoryIdVector(value.getKeywordCategories());
/* 1952 */         Vector existingKeywords = (Vector)mExistingKeywords.get(Long.valueOf(value.getAttribute().getTreeId()));
/*      */ 
/* 1954 */         if (keywords == null)
/*      */         {
/* 1956 */           keywords = new Vector();
/*      */         }
/* 1958 */         if (existingKeywords == null)
/*      */         {
/* 1960 */           existingKeywords = new Vector();
/*      */         }
/*      */ 
/* 1963 */         clearCategoryCountCache(new Vector(CollectionUtils.union(existingKeywords, keywords)), value.getAttribute().getTreeId());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1969 */     if ((a_bForceClearCaches) || ((a_source != null) && (a_source.isValid())) || (a_substituteSource != null))
/*      */     {
/* 1971 */       clearAssetCaches();
/*      */     }
/*      */ 
/* 1975 */     if ((asset.getIsImage()) && (a_source != null) && (a_source.isValid()) && (a_source.getFileSize() > 0L))
/*      */     {
/* 1977 */       this.m_directLinkCacheManager.dumpAssetCache(dbTransaction, asset.getId());
/*      */     }
/*      */ 
/* 1981 */     if ((a_conversionInfo != null) && (a_conversionInfo.getDeferThumbnailGeneration()))
/*      */     {
/* 1983 */       this.m_thumbnailGenerationManager.notifyThumbnailsNeedGenerating(asset.getId());
/*      */     }
/*      */ 
/* 1987 */     if (!a_bDeferIndexing)
/*      */     {
/* 1989 */       this.m_searchManager.indexDocument(asset, true);
/*      */     }
/*      */ 
/* 1993 */     for (AssetSaveParticipant participant : this.m_assetSaveParticipants)
/*      */     {
/* 1995 */       participant.assetWasSaved(context);
/*      */     }
/*      */ 
/* 1998 */     return asset;
/*      */   }
/*      */ 
/*      */   public Vector<Asset> getFullyPopulatedAssetVersions(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 2015 */     Vector versions = null;
/*      */ 
/* 2017 */     Connection con = null;
/* 2018 */     ResultSet rs = null;
/* 2019 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/* 2023 */       if (transaction == null)
/*      */       {
/* 2025 */         transaction = getTransactionManager().getNewTransaction();
/*      */       }
/*      */ 
/* 2028 */       con = transaction.getConnection();
/*      */ 
/* 2030 */       String sSQL = "SELECT a.Id FROM Asset a WHERE a.CurrentVersionId=? ORDER BY a.VersionNumber DESC";
/*      */ 
/* 2034 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 2035 */       psql.setLong(1, a_lAssetId);
/* 2036 */       rs = psql.executeQuery();
/* 2037 */       Vector vecIds = null;
/*      */ 
/* 2039 */       while (rs.next())
/*      */       {
/* 2041 */         long lId = rs.getLong("Id");
/*      */ 
/* 2043 */         if (lId > 0L)
/*      */         {
/* 2045 */           if (vecIds == null)
/*      */           {
/* 2047 */             vecIds = new Vector();
/*      */           }
/* 2049 */           vecIds.add(new Long(lId));
/*      */         }
/*      */       }
/*      */ 
/* 2053 */       if (vecIds != null)
/*      */       {
/* 2055 */         versions = getAssets(null, vecIds, true, true);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2060 */       this.m_logger.error("SQL Exception whilst getting asset versions for asset id=" + a_lAssetId + " : " + e);
/* 2061 */       throw new Bn2Exception("SQL Exception whilst getting asset versions for asset id=" + a_lAssetId + " : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2066 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2070 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2074 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2079 */     return versions;
/*      */   }
/*      */ 
/*      */   private void prepareForNewVersion(Asset a_asset)
/*      */   {
/* 2090 */     a_asset.setId(0L);
/* 2091 */     a_asset.setDateAdded(new Date());
/*      */ 
/* 2094 */     if (a_asset.getSurrogateAssetId() > 0L)
/*      */     {
/* 2096 */       a_asset.setTypeId(0L);
/* 2097 */       a_asset.setFormat(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteAsset(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 2114 */     IAssetManagerImpl assetManagerImpl = getAssetManagerForAsset(a_dbTransaction, a_lAssetId);
/*      */ 
/* 2116 */     Vector vCatIds = this.m_categoryManager.getCategoryIdsForItem(a_dbTransaction, 1L, a_lAssetId);
/* 2117 */     Vector vAccessLevelIds = this.m_categoryManager.getCategoryIdsForItem(a_dbTransaction, 2L, a_lAssetId);
/*      */ 
/* 2120 */     Asset asset = getAsset(a_dbTransaction, a_lAssetId, null, false, false);
/* 2121 */     AssetDeleteContext context = new AssetDeleteContext(asset);
/*      */ 
/* 2124 */     for (AssetDeleteParticipant participant : this.m_assetDeleteParticipants)
/*      */     {
/* 2126 */       participant.assetWillBeDeleted(context);
/*      */     }
/*      */ 
/* 2129 */     assetManagerImpl.deleteAsset(a_dbTransaction, asset);
/*      */ 
/* 2131 */     this.m_searchManager.removeDocument(asset);
/*      */ 
/* 2133 */     AssetUtil.reindexParents(a_dbTransaction, asset, this, this.m_searchManager);
/*      */ 
/* 2136 */     for (AssetDeleteParticipant participant : this.m_assetDeleteParticipants)
/*      */     {
/* 2138 */       participant.assetWasDeleted(context);
/*      */     }
/*      */ 
/* 2142 */     clearAssetCaches();
/* 2143 */     if (asset.getIsImage())
/*      */     {
/* 2145 */       this.m_directLinkCacheManager.dumpAssetCache(a_dbTransaction, a_lAssetId);
/*      */     }
/* 2147 */     clearCategoryCountCache(vCatIds, 1L);
/* 2148 */     clearCategoryCountCache(vAccessLevelIds, 2L);
/*      */   }
/*      */ 
/*      */   public String getDownloadableAssetPath(Asset a_asset, String a_sFileTypeExtension, AssetConversionInfo a_conversionInfo)
/*      */     throws Bn2Exception
/*      */   {
/* 2173 */     String sPath = null;
/* 2174 */     IAssetManagerImpl assetManagerImpl = null;
/*      */ 
/* 2176 */     assetManagerImpl = getAssetManagerForAsset(null, a_asset.getId());
/*      */ 
/* 2178 */     sPath = assetManagerImpl.getDownloadableAssetPath(a_asset, a_sFileTypeExtension, a_conversionInfo);
/*      */ 
/* 2182 */     return sPath;
/*      */   }
/*      */ 
/*      */   public boolean canConvertToFileFormat(Asset a_asset, FileFormat a_format)
/*      */     throws Bn2Exception
/*      */   {
/* 2204 */     IAssetManagerImpl assetManagerImpl = null;
/*      */ 
/* 2206 */     assetManagerImpl = getAssetManagerForAsset(null, a_asset.getId());
/*      */ 
/* 2208 */     return assetManagerImpl.canConvertToFileFormat(a_asset, a_format);
/*      */   }
/*      */ 
/*      */   public boolean userCanViewAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2226 */     return checkUserPermissionForAsset(a_userProfile, a_asset, 1);
/*      */   }
/*      */ 
/*      */   public boolean userCanDownloadAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2244 */     boolean bDownloadable = checkUserPermissionForAsset(a_userProfile, a_asset, 2);
/* 2245 */     boolean bApprovable = checkUserPermissionForAsset(a_userProfile, a_asset, 7);
/* 2246 */     boolean bViewRestricted = checkUserPermissionForAsset(a_userProfile, a_asset, 13);
/* 2247 */     boolean bCanDownload = ((bDownloadable) || (bApprovable)) && ((a_userProfile.getIsAdmin()) || (!a_asset.getIsRestricted()) || (bViewRestricted));
/* 2248 */     return bCanDownload;
/*      */   }
/*      */ 
/*      */   public boolean userCanDownloadWithApprovalAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2261 */     return (checkUserPermissionForAsset(a_userProfile, a_asset, 5)) && ((a_userProfile.getIsAdmin()) || (!a_asset.getIsRestricted()));
/*      */   }
/*      */ 
/*      */   public boolean userCanDownloadAssetNow(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2283 */     boolean bCanDownload = userCanDownloadAsset(a_userProfile, a_asset);
/*      */ 
/* 2285 */     if ((!bCanDownload) && (a_userProfile.getUser() != null))
/*      */     {
/* 2287 */       if (userCanDownloadWithApprovalAsset(a_userProfile, a_asset))
/*      */       {
/* 2289 */         bCanDownload = this.m_assetApprovalManager.getApprovalStatus(a_userProfile.getUser().getId(), a_asset.getId()) == 2L;
/*      */       }
/*      */     }
/* 2292 */     return bCanDownload;
/*      */   }
/*      */ 
/*      */   public String getTemporaryLargeFile(ImageAsset a_image, int a_iSize, int a_iLayerNo, boolean a_bForceNoWatermark)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/* 2312 */       String sFormat = "jpg";
/*      */ 
/* 2314 */       ImageConversionInfo conversionInfo = new ImageConversionInfo();
/* 2315 */       if (!a_bForceNoWatermark)
/*      */       {
/* 2317 */         conversionInfo.setAddWatermark(AssetBankSettings.getWatermarkFullSize());
/*      */       }
/*      */ 
/* 2320 */       if (ABImageMagick.getIsCMYK(a_image.getColorSpace()))
/*      */       {
/* 2323 */         ColorSpace destinationColorSpace = this.m_usageManager.getColorSpace(null, 1);
/* 2324 */         ColorSpace currentColorSpace = this.m_usageManager.getCurrentColorSpace(a_image, null);
/*      */ 
/* 2326 */         conversionInfo.setConvertToColorSpace(destinationColorSpace);
/* 2327 */         conversionInfo.setCurrentColorSpace(currentColorSpace);
/*      */       }
/*      */ 
/* 2331 */       if (a_iSize > -1)
/*      */       {
/* 2333 */         conversionInfo.setMaxWidth(a_iSize);
/* 2334 */         conversionInfo.setMaxHeight(a_iSize);
/* 2335 */         conversionInfo.setMaintainAspectRatio(true);
/*      */ 
/* 2338 */         if (a_iLayerNo > 0)
/*      */         {
/* 2340 */           conversionInfo.setLayerToConvert(a_iLayerNo);
/*      */         }
/*      */ 
/* 2344 */         if (a_image.getNumPages() > 1)
/*      */         {
/* 2347 */           String sImagePath = this.m_fileStoreManager.getAbsolutePath(a_image.getFileLocation());
/* 2348 */           ImageFileInfo imageInfo = ABImageMagick.getInfo(sImagePath, a_iLayerNo - 1);
/*      */ 
/* 2351 */           a_image.setWidth(imageInfo.getWidth());
/* 2352 */           a_image.setHeight(imageInfo.getHeight());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2357 */       String sDownloadPath = getDownloadableAssetPath(a_image, sFormat, conversionInfo);
/*      */ 
/* 2361 */       return sDownloadPath;
/*      */     }
/*      */     catch (Exception e) {
/*      */     
/* 2365 */     throw new Bn2Exception(e.getMessage(), e);}
/*      */   }
/*      */ 
/*      */   public boolean userApprovedForAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2382 */     boolean bCanDownload = this.m_assetApprovalManager.getApprovalStatus(a_userProfile.getUser().getId(), a_asset.getId()) == 2L;
/* 2383 */     return bCanDownload;
/*      */   }
/*      */ 
/*      */   public boolean userApprovedForHighResAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2394 */     boolean bCanDownload = this.m_assetApprovalManager.getApprovalStatus(a_userProfile.getUser().getId(), a_asset.getId()) == 4L;
/* 2395 */     return bCanDownload;
/*      */   }
/*      */ 
/*      */   public boolean userApprovalIsPending(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2406 */     boolean bCanDownload = this.m_assetApprovalManager.getApprovalStatus(a_userProfile.getUser().getId(), a_asset.getId()) == 1L;
/* 2407 */     return bCanDownload;
/*      */   }
/*      */ 
/*      */   public boolean userCanUpdateAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2428 */     boolean bHasEditAssetPermission = checkUserPermissionForAsset(a_userProfile, a_asset, 3);
/*      */ 
/* 2433 */     boolean bUserOwnsAsset = checkUserOwnsAsset(a_userProfile, a_asset);
/* 2434 */     boolean bCanEditAsset = (bHasEditAssetPermission) && ((!a_userProfile.getUserCanOnlyEditOwnFiles()) || (bUserOwnsAsset));
/*      */ 
/* 2437 */     boolean bCanEditWithApproval = false;
/* 2438 */     if (AssetBankSettings.getUploadWithApprovalCanEditOwnWithApproval())
/*      */     {
/* 2440 */       bCanEditWithApproval = (bUserOwnsAsset) && (checkUserPermissionForAsset(a_userProfile, a_asset, 6));
/*      */     }
/*      */ 
/* 2447 */     boolean bOwnerEditing = false;
/* 2448 */     if ((bUserOwnsAsset) && (this.m_workflowManager.getIsEntityInAnyOwnerOnlyState(null, a_asset.getId(), a_asset.getWorkflows())))
/*      */     {
/* 2451 */       bOwnerEditing = true;
/*      */     }
/*      */ 
/* 2455 */     boolean bCanEditUnsubmittedAsset = (a_asset.getIsUnsubmitted()) && (bUserOwnsAsset);
/*      */ 
/* 2457 */     return (bCanEditAsset) || (bCanEditWithApproval) || (bCanEditUnsubmittedAsset) || (bOwnerEditing);
/*      */   }
/*      */ 
/*      */   public boolean userCanDeleteAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2478 */     boolean bHasDeleteAssetPermission = checkUserPermissionForAsset(a_userProfile, a_asset, 4);
/*      */ 
/* 2483 */     boolean bUserOwnsAsset = checkUserOwnsAsset(a_userProfile, a_asset);
/* 2484 */     boolean bCanDeleteAsset = (bHasDeleteAssetPermission) && ((!a_userProfile.getUserCanOnlyEditOwnFiles()) || (bUserOwnsAsset));
/*      */ 
/* 2487 */     boolean bCanDeleteUnsubmittedAsset = (a_asset.getIsUnsubmitted()) && (bUserOwnsAsset);
/*      */ 
/* 2489 */     return (bCanDeleteAsset) || (bCanDeleteUnsubmittedAsset);
/*      */   }
/*      */ 
/*      */   public boolean userCanDownloadOriginal(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2496 */     return checkUserPermissionForAsset(a_userProfile, a_asset, 8);
/*      */   }
/*      */ 
/*      */   public boolean userCanDownloadAdvanced(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2504 */     return checkUserPermissionForAsset(a_userProfile, a_asset, 9);
/*      */   }
/*      */ 
/*      */   public boolean userCanReviewAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2513 */     return checkUserPermissionForAsset(a_userProfile, a_asset, 11);
/*      */   }
/*      */ 
/*      */   public boolean userCanViewRestrictedAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2521 */     return checkUserPermissionForAsset(a_userProfile, a_asset, 13);
/*      */   }
/*      */ 
/*      */   public boolean userMustRequestApprovalForHighRes(ABUserProfile a_userProfile, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2534 */     if (a_userProfile.getIsAdmin())
/*      */     {
/* 2536 */       return false;
/*      */     }
/*      */ 
/* 2539 */     return checkUserPermissionForAsset(a_userProfile, a_asset, 14);
/*      */   }
/*      */ 
/*      */   public boolean canViewRestrictedAsset(ABUserProfile a_userProfile, LightweightAsset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 2547 */     Set userCatsWithPermission = a_userProfile.getPermissionCategoryIds(13);
/*      */ 
/* 2554 */     return (a_asset.getRestrictiveCatIds() != null) && (userCatsWithPermission != null) && (userHasCategoryPermissions(userCatsWithPermission, a_asset.getRestrictiveCatIds(), false));
/*      */   }
/*      */ 
/*      */   private boolean checkUserPermissionForAsset(ABUserProfile a_userProfile, Asset a_asset, int a_iRequiredPermissionLevel)
/*      */   {
/* 2584 */     Asset asset = a_asset;
/*      */ 
/* 2587 */     if (a_userProfile.getIsAdmin())
/*      */     {
/* 2589 */       return true;
/*      */     }
/*      */ 
/* 2592 */     boolean bUserOwnsAsset = checkUserOwnsAsset(a_userProfile, a_asset);
/* 2593 */     boolean bUnsubmitted = asset.getIsUnsubmitted();
/*      */ 
/* 2597 */     if ((bUnsubmitted) && (!bUserOwnsAsset))
/*      */     {
/* 2599 */       return false;
/*      */     }
/*      */ 
/* 2605 */     boolean bRequiresPermissionOnAllAccessLevels = ((AssetBankSettings.getUserRequiresAllAccessLevelsToViewAsset()) && (a_iRequiredPermissionLevel != 11)) || ((AssetBankSettings.getUserRequiresAllAccessLevelsToDeleteAsset()) && (a_iRequiredPermissionLevel == 4));
/*      */ 
/* 2613 */     if (!asset.getIsFullyApproved())
/*      */     {
/* 2615 */       boolean bApprovedForAL = false;
/*      */ 
/* 2617 */       if (asset.getIsPartiallyApproved())
/*      */       {
/* 2620 */         Set userPermissions = a_userProfile.getPermissionCategoryIds(1);
/* 2621 */         Vector vecCategories = asset.getApprovedPermissionCategories();
/* 2622 */         bApprovedForAL = userHasCategoryPermissions(userPermissions, vecCategories, bRequiresPermissionOnAllAccessLevels);
/*      */       }
/*      */ 
/* 2626 */       boolean bApprover = false;
/* 2627 */       Set userApprovalPermission = a_userProfile.getPermissionCategoryIds(12);
/*      */ 
/* 2630 */       if ((userApprovalPermission != null) && (userHasCategoryPermissions(userApprovalPermission, asset.getRestrictivePermissionCategories(), bRequiresPermissionOnAllAccessLevels)))
/*      */       {
/* 2633 */         bApprover = true;
/*      */       }
/*      */ 
/* 2637 */       boolean bAdvancedViewing = false;
/* 2638 */       if ((AssetBankSettings.getAdvancedViewingEnabled()) && (a_iRequiredPermissionLevel == 1) && (asset.getAdvancedViewing()) && (a_userProfile.hasAdvancedViewing()))
/*      */       {
/* 2642 */         bAdvancedViewing = true;
/*      */       }
/*      */ 
/* 2646 */       if ((!bApprovedForAL) && (!bUnsubmitted) && (!bApprover) && (!bAdvancedViewing))
/*      */       {
/* 2648 */         return false;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2654 */     if ((asset.getRestrictivePermissionCategories() == null) || (asset.getRestrictivePermissionCategories().size() == 0))
/*      */     {
/* 2656 */       return false;
/*      */     }
/*      */ 
/* 2670 */     Set userCatsWithPermission = a_userProfile.getPermissionCategoryIds(a_iRequiredPermissionLevel);
/*      */ 
/* 2673 */     if ((userCatsWithPermission == null) || (!userHasCategoryPermissions(userCatsWithPermission, asset.getRestrictivePermissionCategories(), bRequiresPermissionOnAllAccessLevels)))
/*      */     {
/* 2678 */       return false;
/*      */     }
/*      */ 
/* 2682 */     if (assetIsExcluded(asset, a_userProfile.getAttributeExclusions()))
/*      */     {
/* 2684 */       return false;
/*      */     }
/*      */ 
/* 2688 */     if (asset.getHasExpired())
/*      */     {
/* 2691 */       if ((a_iRequiredPermissionLevel == 2) || (a_iRequiredPermissionLevel == 5))
/*      */       {
/* 2694 */         return false;
/*      */       }
/*      */     }
/*      */ 
/* 2698 */     return true;
/*      */   }
/*      */ 
/*      */   public static boolean checkUserOwnsAsset(ABUserProfile a_userProfile, Asset a_asset)
/*      */   {
/* 2703 */     return checkUserOwnsAsset(a_userProfile.getUser(), a_asset);
/*      */   }
/*      */ 
/*      */   public static boolean checkUserOwnsAsset(User a_user, Asset a_asset)
/*      */   {
/* 2715 */     boolean bUserOwnsAsset = false;
/*      */ 
/* 2717 */     if (a_user != null)
/*      */     {
/* 2719 */       bUserOwnsAsset = a_asset.getAddedByUser().getId() == a_user.getId();
/*      */     }
/*      */ 
/* 2722 */     return bUserOwnsAsset;
/*      */   }
/*      */ 
/*      */   private boolean assetIsExcluded(Asset a_asset, Vector a_vAttributeExclusions)
/*      */   {
/* 2741 */     for (int i = 0; i < a_vAttributeExclusions.size(); i++)
/*      */     {
/* 2743 */       AttributeValue excludedValue = (AttributeValue)a_vAttributeExclusions.get(i);
/*      */ 
/* 2745 */       AttributeValue assetValue = a_asset.getAttributeValue((int)excludedValue.getAttribute().getId());
/*      */ 
/* 2752 */       if ((assetValue != null) && (assetValue.getValue() != null) && (assetValue.getValue().equalsIgnoreCase(excludedValue.getValue())))
/*      */       {
/* 2754 */         return true;
/*      */       }
/*      */     }
/*      */ 
/* 2758 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean userHasCategoryPermissions(Set a_categoryIds, List<Category> a_assetCategories, boolean a_bUserRequiresAll)
/*      */   {
/* 2780 */     Set catIds = new HashSet(a_assetCategories.size());
/*      */ 
/* 2782 */     for (int i = 0; i < a_assetCategories.size(); i++)
/*      */     {
/* 2784 */       Category cat = (Category)a_assetCategories.get(i);
/* 2785 */       catIds.add(Long.valueOf(cat.getId()));
/*      */     }
/* 2787 */     return userHasCategoryPermissions(a_categoryIds, catIds, a_bUserRequiresAll);
/*      */   }
/*      */ 
/*      */   public static boolean userHasCategoryPermissions(Set<Long> a_userCategoryIds, Set<Long> a_requiredCatIds, boolean a_bUserRequiresAll)
/*      */   {
/* 2803 */     if (a_requiredCatIds.size() == 0)
/*      */     {
/* 2805 */       return true;
/*      */     }
/*      */ 
/* 2808 */     if (a_userCategoryIds == null)
/*      */     {
/* 2811 */       return false;
/*      */     }
/*      */ 
/* 2814 */     if (a_bUserRequiresAll)
/*      */     {
/* 2816 */       return a_userCategoryIds.containsAll(a_requiredCatIds);
/*      */     }
/*      */ 
/* 2819 */     return CollectionUtils.containsAny(a_userCategoryIds, a_requiredCatIds);
/*      */   }
/*      */ 
/*      */   public boolean userCanApproveAssetsForCategory(ABUserProfile a_userProfile, long a_lCategoryId)
/*      */   {
/* 2827 */     Set userCatsWithPermission = a_userProfile.getPermissionCategoryIds(12);
/* 2828 */     return userCatsWithPermission.contains(new Long(a_lCategoryId));
/*      */   }
/*      */ 
/*      */   public Attribute getAssetAttribute(DBTransaction a_dbTransaction, long a_lAttributeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2842 */     Vector vAtts = this.m_attributeValueManager.getAssetAttributes(a_dbTransaction, a_lAttributeId, null, false, false);
/*      */ 
/* 2844 */     if ((vAtts != null) && (vAtts.size() >= 1))
/*      */     {
/* 2846 */       return (Attribute)vAtts.firstElement();
/*      */     }
/* 2848 */     return null;
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAssetAttributes(DBTransaction a_dbTransaction, Vector a_vVisibleAttributeIds, boolean a_bSearchFieldAttributes, boolean a_bSearchBuilderAttributes)
/*      */     throws Bn2Exception
/*      */   {
/* 2868 */     return this.m_attributeValueManager.getAssetAttributes(a_dbTransaction, 0L, a_vVisibleAttributeIds, a_bSearchFieldAttributes, a_bSearchBuilderAttributes);
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAssetAttributes(DBTransaction a_dbTransaction, Vector a_vVisibleAttributeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 2886 */     return this.m_attributeValueManager.getAssetAttributes(a_dbTransaction, 0L, a_vVisibleAttributeIds, false, false);
/*      */   }
/*      */ 
/*      */   public Vector getSelectedListboxCategoryValues(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 2906 */     Connection con = null;
/* 2907 */     PreparedStatement psql = null;
/* 2908 */     ResultSet rs = null;
/* 2909 */     DBTransaction transaction = a_dbTransaction;
/* 2910 */     String sSQL = null;
/* 2911 */     Vector vecSelectedValues = null;
/*      */ 
/* 2913 */     if (transaction == null)
/*      */     {
/* 2915 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2920 */       con = transaction.getConnection();
/*      */ 
/* 2922 */       sSQL = "SELECT c.Name cName, pc.Name pcName, c.IsListboxCategory FROM CM_ItemInCategory iic JOIN CM_Category c ON iic.CategoryId=c.Id LEFT JOIN CM_Category pc ON c.ParentId=pc.Id WHERE ItemId=?";
/*      */ 
/* 2927 */       psql = con.prepareStatement(sSQL);
/* 2928 */       psql.setLong(1, a_lAssetId);
/*      */ 
/* 2930 */       rs = psql.executeQuery();
/*      */ 
/* 2932 */       while (rs.next())
/*      */       {
/* 2934 */         if (!rs.getBoolean("IsListboxCategory"))
/*      */           continue;
/* 2936 */         if (vecSelectedValues == null)
/*      */         {
/* 2938 */           vecSelectedValues = new Vector();
/*      */         }
/*      */ 
/* 2942 */         SelectedListboxValue slv = new SelectedListboxValue();
/* 2943 */         slv.setTopCategoryName(rs.getString("pcName"));
/* 2944 */         slv.setSelectedSubCategory(rs.getString("cName"));
/*      */ 
/* 2946 */         vecSelectedValues.add(slv);
/*      */       }
/*      */ 
/* 2950 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2954 */       this.m_logger.error("SQL Exception in AssetManager.getSelectedListboxCategoryValues : " + e);
/* 2955 */       throw new Bn2Exception("SQL Exception in AssetManager.getSelectedListboxCategoryValues: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2960 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2964 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2968 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2973 */     return vecSelectedValues;
/*      */   }
/*      */ 
/*      */   public UploadedFileInfo storeTempUploadedFile(AssetFileSource a_source, StoredFileType iType)
/*      */     throws Bn2Exception
/*      */   {
/* 2994 */     return storeTempUploadedFile(a_source, null, iType);
/*      */   }
/*      */ 
/*      */   public UploadedFileInfo storeTempUploadedFile(AssetFileSource a_source, FileFormat a_format, StoredFileType iType)
/*      */     throws Bn2Exception
/*      */   {
/* 3009 */     IAssetManagerImpl assetManagerImpl = null;
/*      */ 
/* 3011 */     if (a_format == null)
/*      */     {
/* 3013 */       a_format = getFileFormatForFile(null, a_source.getFilename());
/*      */     }
/*      */ 
/* 3017 */     assetManagerImpl = getAssetManagerForAssetType(a_format.getAssetTypeId());
/*      */ 
/* 3019 */     return assetManagerImpl.storeTempUploadedFile(a_format, a_source, iType);
/*      */   }
/*      */ 
/*      */   public UploadedFileInfo getUploadedFileInfo(String a_sLocation, long a_lFileFormatId, boolean a_bRemove)
/*      */     throws Bn2Exception
/*      */   {
/* 3041 */     IAssetManagerImpl assetManagerImpl = null;
/*      */ 
/* 3044 */     Vector formats = getAllFileFormatsForFile(null, a_sLocation);
/* 3045 */     FileFormat format = null;
/*      */ 
/* 3047 */     if ((formats.size() == 1) || (a_lFileFormatId <= 0L))
/*      */     {
/* 3049 */       format = (FileFormat)formats.get(0);
/*      */     }
/*      */     else
/*      */     {
/* 3053 */       for (int i = 0; i < formats.size(); i++)
/*      */       {
/* 3055 */         format = (FileFormat)formats.get(i);
/* 3056 */         if (format.getId() == a_lFileFormatId)
/*      */         {
/*      */           break;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3064 */     assetManagerImpl = getAssetManagerForAssetType(format.getAssetTypeId());
/*      */ 
/* 3067 */     return assetManagerImpl.getUploadedFileInfo(a_sLocation, a_bRemove);
/*      */   }
/*      */ 
/*      */   public FileFormat getFileFormat(DBTransaction a_dbTransaction, long a_lFormatId)
/*      */     throws Bn2Exception
/*      */   {
/* 3086 */     FileFormat format = null;
/* 3087 */     Connection con = null;
/* 3088 */     ResultSet rs = null;
/* 3089 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 3091 */     if (transaction == null)
/*      */     {
/* 3093 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3098 */       con = transaction.getConnection();
/*      */ 
/* 3100 */       String sSQL = "SELECT Id, Name, Description, FileExtension, AssetTypeId FROM FileFormat WHERE Id=?";
/*      */ 
/* 3105 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 3106 */       psql.setLong(1, a_lFormatId);
/*      */ 
/* 3108 */       rs = psql.executeQuery();
/*      */ 
/* 3110 */       format = new FileFormat();
/*      */ 
/* 3112 */       while (rs.next())
/*      */       {
/* 3114 */         format = new FileFormat();
/*      */ 
/* 3116 */         format.setId(rs.getLong("Id"));
/* 3117 */         format.setName(rs.getString("Name"));
/* 3118 */         format.setDescription(rs.getString("Description"));
/* 3119 */         format.setFileExtension(rs.getString("FileExtension"));
/* 3120 */         format.setAssetTypeId(rs.getLong("AssetTypeId"));
/*      */       }
/* 3122 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3126 */       this.m_logger.error("SQL Exception whilst getting the image format from the database : " + e);
/* 3127 */       throw new Bn2Exception("SQL Exception whilst getting the image format from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3132 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3136 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3140 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3145 */     return format;
/*      */   }
/*      */ 
/*      */   private void clearCategoryCountCache(Vector a_vCats, long a_lTreeId)
/*      */     throws Bn2Exception
/*      */   {
/* 3157 */     this.m_categoryCountCacheManager.invalidateCache(a_vCats, a_lTreeId);
/*      */   }
/*      */ 
/*      */   public Vector<Asset> getAssetVersions(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 3178 */     Vector versions = new Vector();
/*      */ 
/* 3180 */     Connection con = null;
/* 3181 */     ResultSet rs = null;
/* 3182 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 3184 */     if (transaction == null)
/*      */     {
/* 3186 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3191 */       con = transaction.getConnection();
/*      */ 
/* 3193 */       String sSQL = "SELECT a.Id, a.AssetTypeId aTypeId, a.VersionNumber, a.DateAdded, a.ThumbnailFileLocation aThumbnailFileLocation, ff.ThumbnailFileLocation ffThumbnailFileLocation, ff.AssetTypeId ffTypeId, u.Username, u.Forename, u.Surname, u.EmailAddress FROM Asset a INNER JOIN AssetBankUser u ON u.Id=a.AddedByUserId LEFT JOIN FileFormat ff ON ff.Id = a.FileFormatId WHERE a.Id=? OR a.CurrentVersionId=? ORDER BY a.VersionNumber DESC";
/*      */ 
/* 3213 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 3214 */       psql.setLong(1, a_lAssetId);
/* 3215 */       psql.setLong(2, a_lAssetId);
/* 3216 */       rs = psql.executeQuery();
/*      */ 
/* 3218 */       while (rs.next())
/*      */       {
/* 3220 */         Asset asset = new Asset();
/* 3221 */         asset.setId(rs.getLong("Id"));
/* 3222 */         asset.setVersionNumber(rs.getInt("VersionNumber"));
/* 3223 */         asset.setDateAdded(rs.getTimestamp("DateAdded"));
/*      */ 
/* 3225 */         ImageFile thumbnail = new ImageFile();
/*      */ 
/* 3227 */         if (StringUtils.isNotEmpty(rs.getString("aThumbnailFileLocation")))
/*      */         {
/* 3229 */           thumbnail.setPath(rs.getString("aThumbnailFileLocation"));
/* 3230 */           asset.setTypeId(2L);
/*      */         }
/* 3232 */         else if (StringUtils.isNotEmpty(rs.getString("ffThumbnailFileLocation")))
/*      */         {
/* 3234 */           thumbnail.setPath(rs.getString("ffThumbnailFileLocation"));
/* 3235 */           asset.setTypeId(rs.getLong("ffTypeId"));
/*      */         }
/*      */         else
/*      */         {
/* 3239 */           thumbnail.setPath(FileFormat.s_noFileFormat.getThumbnailImageLocation());
/*      */         }
/*      */ 
/* 3242 */         asset.setThumbnailImageFile(thumbnail);
/*      */ 
/* 3244 */         ABUser user = new ABUser();
/* 3245 */         user.setForename(rs.getString("Forename"));
/* 3246 */         user.setSurname(rs.getString("Surname"));
/* 3247 */         user.setUsername(rs.getString("Username"));
/* 3248 */         user.setEmailAddress(rs.getString("EmailAddress"));
/* 3249 */         asset.setAddedByUser(user);
/*      */ 
/* 3251 */         versions.add(asset);
/*      */       }
/* 3253 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3257 */       this.m_logger.error("SQL Exception whilst getting asset versions for asset id=" + a_lAssetId + " : " + e);
/* 3258 */       throw new Bn2Exception("SQL Exception whilst getting asset versions for asset id=" + a_lAssetId + " : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3263 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3267 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3271 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3276 */     return versions;
/*      */   }
/*      */ 
/*      */   public void addAssetToPromoted(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 3301 */     if (!assetIsPromoted(a_dbTransaction, a_lId))
/*      */     {
/* 3303 */       Connection con = null;
/* 3304 */       ResultSet rs = null;
/* 3305 */       String sSQL = null;
/*      */       try
/*      */       {
/* 3309 */         con = a_dbTransaction.getConnection();
/*      */ 
/* 3312 */         sSQL = "SELECT AssetId FROM PromotedAsset WHERE AssetId=?";
/*      */ 
/* 3314 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 3315 */         psql.setLong(1, a_lId);
/* 3316 */         rs = psql.executeQuery();
/*      */ 
/* 3319 */         if (!rs.next())
/*      */         {
/* 3321 */           psql.close();
/* 3322 */           sSQL = "INSERT INTO PromotedAsset (AssetId, DatePromoted) VALUES (?,?) ";
/* 3323 */           psql = con.prepareStatement(sSQL);
/* 3324 */           psql.setLong(1, a_lId);
/* 3325 */           psql.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
/* 3326 */           psql.executeUpdate();
/*      */         }
/*      */ 
/* 3329 */         psql.close();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 3334 */         this.m_logger.error("SQL Exception in AssetManager.promoteAsset : " + e);
/* 3335 */         throw new Bn2Exception("SQL Exception in AssetManager.promoteAsset : ", e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeAssetFromPromoted(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 3357 */     Connection con = null;
/* 3358 */     String sSQL = null;
/*      */     try
/*      */     {
/* 3362 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 3364 */       sSQL = "DELETE FROM PromotedAsset WHERE AssetId=?";
/*      */ 
/* 3366 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 3367 */       psql.setLong(1, a_lId);
/* 3368 */       psql.executeUpdate();
/* 3369 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3374 */       this.m_logger.error("SQL Exception in AssetManager.removeAssetFromPromoted : " + e);
/* 3375 */       throw new Bn2Exception("SQL Exception in AssetManager.removeAssetFromPromoted : ", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeAllFromPromoted(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 3395 */     Connection con = null;
/* 3396 */     String sSQL = null;
/* 3397 */     PreparedStatement psql = null;
/* 3398 */     ResultSet rs = null;
/* 3399 */     Vector vecAssetsToIndex = new Vector();
/*      */     try
/*      */     {
/* 3404 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 3406 */       sSQL = "SELECT AssetId FROM PromotedAsset";
/*      */ 
/* 3408 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 3410 */       rs = psql.executeQuery();
/*      */ 
/* 3412 */       while (rs.next())
/*      */       {
/* 3414 */         vecAssetsToIndex.add(new Long(rs.getLong("AssetId")));
/*      */       }
/*      */ 
/* 3417 */       psql.close();
/*      */ 
/* 3421 */       sSQL = "DELETE FROM PromotedAsset";
/*      */ 
/* 3423 */       psql = con.prepareStatement(sSQL);
/* 3424 */       psql.executeUpdate();
/* 3425 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3430 */       this.m_logger.error("SQL Exception in AssetManager.removeAllFromPromoted : " + e);
/* 3431 */       throw new Bn2Exception("SQL Exception in AssetManager.removeAllFromPromoted: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3436 */       if ((a_dbTransaction == null) && (a_dbTransaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3440 */           a_dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3444 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3450 */     for (int i = 0; i < vecAssetsToIndex.size(); i++)
/*      */     {
/* 3452 */       long lId = ((Long)vecAssetsToIndex.elementAt(i)).longValue();
/* 3453 */       Asset asset = getAsset(a_dbTransaction, lId, null, true, true);
/*      */ 
/* 3456 */       this.m_searchManager.indexDocument(asset, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public SearchResults getPromotedAssets(DBTransaction a_transaction, int a_iPageIndex, int a_iPageSize, Vector a_vecFilters, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/* 3486 */     SearchCriteria criteria = new SearchCriteria();
/* 3487 */     criteria.setIsPromoted(new Boolean(true));
/* 3488 */     criteria.setSelectedFilters(a_vecFilters);
/* 3489 */     criteria.setupPermissions(a_userProfile);
/*      */ 
/* 3493 */     SortField[] aSortFields = new SortField[1];
/* 3494 */     String sField = "f_long_added_sort";
/* 3495 */     aSortFields[0] = new SortField(sField, 3, true);
/* 3496 */     criteria.setSortFields(aSortFields);
/*      */ 
/* 3498 */     SearchResults results = this.m_searchManager.searchByPageIndex(criteria, a_iPageIndex, a_iPageSize, a_userProfile.getCurrentLanguage().getCode());
/*      */ 
/* 3500 */     if ((!a_userProfile.getIsAdmin()) && (AssetBankSettings.getCanRestrictAssets()) && (AssetBankSettings.getHideRestrictedImages()))
/*      */     {
/* 3502 */       if ((results != null) && (results.getSearchResults() != null))
/*      */       {
/* 3504 */         for (int i = 0; i < results.getSearchResults().size(); i++)
/*      */         {
/* 3506 */           LightweightAsset asset = (LightweightAsset)results.getSearchResults().elementAt(i);
/* 3507 */           asset.setOverrideRestriction(canViewRestrictedAsset(a_userProfile, asset));
/*      */         }
/*      */       }
/*      */     }
/* 3511 */     return results;
/*      */   }
/*      */ 
/*      */   public Vector<Asset> getShowOnDescendantAssets(DBTransaction a_transaction, ABUserProfile a_userProfile, long a_lCatId, long a_lCatTreeId)
/*      */     throws Bn2Exception
/*      */   {
/* 3524 */     Vector vecAncestors = this.m_categoryManager.getAncestors(a_transaction, a_lCatTreeId, a_lCatId);
/* 3525 */     Vector vecAssets = null;
/*      */ 
/* 3527 */     if ((vecAncestors != null) && (vecAncestors.size() > 0))
/*      */     {
/* 3530 */       SearchCriteria criteria = new SearchCriteria();
/* 3531 */       criteria.setCategoryIds(com.bright.assetbank.category.util.CategoryUtil.convertCategoryVectorToString(vecAncestors, ","));
/* 3532 */       criteria.setOrCategories(true);
/* 3533 */       if (!a_userProfile.getIsLoggedIn())
/*      */       {
/* 3536 */         criteria.addGroupRestriction(2L);
/*      */       }
/* 3538 */       else if (!((ABUser)a_userProfile.getUser()).getIsAdmin())
/*      */       {
                       Vector <Group> vg = a_userProfile.getUser().getGroups();
/* 3540 */         for (Group group : vg)
/*      */         {
/* 3542 */           criteria.addGroupRestriction(group.getId());
/*      */         }
/*      */       }
/* 3545 */       criteria.setShowOnDescendants(true);
/* 3546 */       criteria.setIncludeImplicitCategoryMembers(false);
/* 3547 */       criteria.setSelectedFilters(a_userProfile.getSelectedFilters());
/*      */ 
/* 3550 */       SearchResults results = this.m_searchManager.search(criteria);
/* 3551 */       return results.getSearchResults();
/*      */     }
/*      */ 
/* 3554 */     return vecAssets;
/*      */   }
/*      */ 
/*      */   private Vector<Asset> getAssetsBuilder(DBTransaction a_transaction, String a_sCriteria, boolean a_bGetPermissionCats)
/*      */     throws Bn2Exception
/*      */   {
/* 3577 */     Connection con = null;
/* 3578 */     ResultSet rs = null;
/* 3579 */     Vector vec = new Vector();
/* 3580 */     DBTransaction transaction = a_transaction;
/*      */ 
/* 3582 */     if (transaction == null)
/*      */     {
/* 3584 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3589 */       con = transaction.getConnection();
/*      */ 
/* 3591 */       String sSQL = "SELECT a.Id, a.FileLocation, a.OriginalFileLocation, a.FileSizeInBytes, a.ThumbnailFileLocation, a.IsUnsubmitted, a.Price, a.ImportedAssetId, a.SmallFileLocation, a.MediumFileLocation, a.IsPreviewRestricted, a.IsSensitive, a.Synchronised, a.CurrentVersionId, a.VersionNumber, a.NumViews, a.NumDownloads, a.LastDownloaded, a.OriginalFilename, a.IsBrandTemplate, a.AdvancedViewing, a.FileCheckedOutByUserId, ia.Height, ia.Width, ia.NumLayers, ia.FeaturedFileLocation, ff.Id ffId, ff.AssetTypeId, ff.Name, ff.Description ffDescription, ff.FileExtension, ff.IsIndexable, ff.IsConvertable, ff.IsConversionTarget, ff.ThumbnailFileLocation ffThumbnailFileLocation,ff.ContentType, ff.ConverterClass, ff.ToTextConverterClass, ff.ViewFileInclude, ff.PreviewInclude, ff.PreviewWidth, ff.PreviewHeight, ff.ConvertIndividualLayers, ff.CanViewOriginal, cea.Id ceaId, cea.ParentId ceaParentId, cea.Name ceaName, cea.CategoryTypeId ceaCategoryTypeId FROM Asset a LEFT JOIN ImageAsset ia ON a.Id=ia.AssetId LEFT JOIN FileFormat ff ON a.FileFormatId=ff.Id LEFT JOIN CM_Category cea ON a.Id=cea.ExtensionAssetId " + a_sCriteria;
/*      */ 
/* 3646 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 3647 */       rs = psql.executeQuery();
/*      */ 
/* 3649 */       Asset asset = null;
/*      */ 
/* 3651 */       while (rs.next())
/*      */       {
/* 3653 */         asset = new Asset();
/*      */ 
/* 3656 */         if (rs.getString("SmallFileLocation") != null)
/*      */         {
/* 3658 */           asset = new ImageAsset();
/* 3659 */           ImageAsset image = (ImageAsset)asset;
/* 3660 */           image.setHomogenizedImageFile(new ImageFile(rs.getString("SmallFileLocation")));
/* 3661 */           image.setPreviewImageFile(new ImageFile(rs.getString("MediumFileLocation")));
/* 3662 */           image.setHeight(rs.getInt("Height"));
/* 3663 */           image.setWidth(rs.getInt("Width"));
/* 3664 */           image.setNumPages(rs.getInt("NumLayers"));
/* 3665 */           image.setFeaturedImageFile(new ImageFile(rs.getString("FeaturedFileLocation")));
/*      */         }
/*      */ 
/* 3669 */         if (rs.getLong("ffId") > 0L)
/*      */         {
/* 3671 */           FileFormat format = new FileFormat();
/* 3672 */           format.setId(rs.getLong("ffId"));
/* 3673 */           format.setAssetTypeId(rs.getLong("AssetTypeId"));
/* 3674 */           format.setName(rs.getString("Name"));
/* 3675 */           format.setDescription(rs.getString("ffDescription"));
/* 3676 */           format.setFileExtension(rs.getString("FileExtension"));
/* 3677 */           format.setIndexable(rs.getBoolean("IsIndexable"));
/* 3678 */           format.setConvertable(rs.getBoolean("IsConvertable"));
/* 3679 */           format.setConversionTarget(rs.getBoolean("IsConversionTarget"));
/* 3680 */           format.setThumbnailImageLocation(rs.getString("ffThumbnailFileLocation"));
/* 3681 */           format.setContentType(rs.getString("ContentType"));
/* 3682 */           format.setConverterClass(rs.getString("ConverterClass"));
/* 3683 */           format.setAssetToTextConverterClass(rs.getString("ToTextConverterClass"));
/* 3684 */           format.setViewFileInclude(rs.getString("ViewFileInclude"));
/* 3685 */           format.setPreviewInclude(rs.getString("PreviewInclude"));
/* 3686 */           format.setPreviewHeight(rs.getInt("PreviewHeight"));
/* 3687 */           format.setPreviewWidth(rs.getInt("PreviewWidth"));
/* 3688 */           format.setCanConvertIndividualLayers(rs.getBoolean("ConvertIndividualLayers"));
/* 3689 */           format.setCanViewOriginal(rs.getBoolean("CanViewOriginal"));
/* 3690 */           asset.setFormat(format);
/*      */         }
/*      */ 
/* 3694 */         asset.setId(rs.getLong("Id"));
/* 3695 */         asset.setTypeId(rs.getLong("AssetTypeId"));
/* 3696 */         asset.setFileLocation(rs.getString("FileLocation"));
/* 3697 */         asset.setOriginalFileLocation(rs.getString("OriginalFileLocation"));
/* 3698 */         asset.setOriginalFilename(rs.getString("OriginalFilename"));
/* 3699 */         asset.setFileSizeInBytes(rs.getLong("FileSizeInBytes"));
/* 3700 */         asset.setIsUnsubmitted(rs.getBoolean("IsUnsubmitted"));
/* 3701 */         asset.getPrice().setAmount(rs.getLong("Price"));
/* 3702 */         asset.setImportedAssetId(rs.getString("ImportedAssetId"));
/* 3703 */         asset.setIsRestricted(rs.getBoolean("IsPreviewRestricted"));
/* 3704 */         asset.setSynchronised(rs.getBoolean("Synchronised"));
/* 3705 */         asset.setCurrentVersionId(rs.getLong("CurrentVersionId"));
/* 3706 */         asset.setVersionNumber(rs.getInt("VersionNumber"));
/* 3707 */         asset.setNumViews(rs.getInt("NumViews"));
/* 3708 */         asset.setNumDownloads(rs.getInt("NumDownloads"));
/* 3709 */         asset.setDateLastDownloaded(rs.getTimestamp("LastDownloaded"));
/* 3710 */         asset.setIsBrandTemplate(rs.getBoolean("IsBrandTemplate"));
/* 3711 */         asset.setAdvancedViewing(rs.getBoolean("AdvancedViewing"));
/* 3712 */         asset.setExtendsCategory(buildExtendedCategoryInfo(a_transaction, rs));
/*      */ 
/* 3714 */         if ((rs.getString("ThumbnailFileLocation") == null) || (rs.getString("ThumbnailFileLocation").trim().length() == 0))
/*      */         {
/* 3717 */           asset.setThumbnailImageFile(new ImageFile(asset.getFormat().getThumbnailImageLocation()));
/*      */         }
/*      */         else
/*      */         {
/* 3721 */           asset.setThumbnailImageFile(new ImageFile(rs.getString("ThumbnailFileLocation")));
/*      */         }
/*      */ 
/* 3724 */         if (a_bGetPermissionCats)
/*      */         {
/* 3726 */           asset.setPermissionCategories(this.m_categoryManager.getCategoriesForItem(transaction, 2L, asset.getId(), LanguageConstants.k_defaultLanguage));
/* 3727 */           asset.setApprovalStatuses(this.m_categoryManager.getApprovalStatusesForItem(transaction, asset.getId()));
/*      */         }
/*      */ 
/* 3732 */         vec.add(asset);
/*      */       }
/* 3734 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3738 */       this.m_logger.error("SQL Exception in getAssetsBuilder : " + e);
/* 3739 */       throw new Bn2Exception("SQL Exception in getAssetsBuilder: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3744 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3748 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3752 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3757 */     return vec;
/*      */   }
/*      */ 
/*      */   private HashMap<Long, Vector<DataBean>> getFeaturedImageBrandMapping(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/* 3771 */     Connection con = null;
/* 3772 */     ResultSet rs = null;
/* 3773 */     HashMap hm = new HashMap();
/* 3774 */     DBTransaction transaction = a_transaction;
/*      */ 
/* 3776 */     if (transaction == null)
/*      */     {
/* 3778 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 3783 */       con = transaction.getConnection();
/*      */ 
/* 3785 */       String sSQL = "SELECT BrandId, AssetId FROM FeaturedAssetInBrand ";
/*      */ 
/* 3788 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 3789 */       rs = psql.executeQuery();
/*      */ 
/* 3791 */       while (rs.next())
/*      */       {
/* 3793 */         long lAssetId = rs.getLong("AssetId");
/* 3794 */         long lBrandId = rs.getLong("BrandId");
/*      */ 
/* 3796 */         DataBean brand = new DataBean();
/* 3797 */         brand.setId(lBrandId);
/*      */ 
/* 3799 */         Long olAssetId = new Long(lAssetId);
/*      */ 
/* 3801 */         if (!hm.containsKey(olAssetId))
/*      */         {
/* 3803 */           Vector vec = new Vector();
/* 3804 */           hm.put(olAssetId, vec);
/*      */         }
/*      */ 
/* 3807 */         ((Vector)hm.get(olAssetId)).add(brand);
/*      */       }
/* 3809 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3813 */       this.m_logger.error("SQL Exception in getFeaturedImageBrandMapping : " + e);
/* 3814 */       throw new Bn2Exception("SQL Exception in getFeaturedImageBrandMapping: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3819 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3823 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3827 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3832 */     return hm;
/*      */   }
/*      */ 
/*      */   public LightweightAsset[] getPromotedAssets(DBTransaction a_transaction, int a_iCount, Vector a_vecFilters, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/* 3859 */     SearchCriteria query = new SearchCriteria();
/* 3860 */     query.setIsPromoted(new Boolean(true));
/* 3861 */     query.setSelectedFilters(a_vecFilters);
/*      */ 
/* 3864 */     query.setupPermissions(a_userProfile);
/*      */ 
/* 3867 */     query.setMaxNoOfResults(AssetBankSettings.getMaxPromotedImageResults());
/*      */ 
/* 3869 */     SearchResults results = this.m_searchManager.search(query, a_userProfile.getCurrentLanguage().getCode());
/*      */ 
/* 3871 */     Vector vecPromotedAssets = results.getSearchResults();
/* 3872 */     int iNumToShow = a_iCount;
/*      */ 
/* 3874 */     if (iNumToShow > vecPromotedAssets.size())
/*      */     {
/* 3876 */       iNumToShow = vecPromotedAssets.size();
/*      */     }
/*      */ 
/* 3879 */     LightweightAsset[] aAssets = new LightweightAsset[iNumToShow];
/* 3880 */     Randomizer randomiser = new Randomizer(vecPromotedAssets.size());
/*      */ 
/* 3882 */     for (int i = 0; i < iNumToShow; i++)
/*      */     {
/* 3884 */       aAssets[i] = ((LightweightAsset)(LightweightAsset)vecPromotedAssets.elementAt(randomiser.getNextIntValue()));
/* 3885 */       if ((a_userProfile.getIsAdmin()) || (!AssetBankSettings.getCanRestrictAssets()) || (!AssetBankSettings.getHideRestrictedImages()))
/*      */         continue;
/* 3887 */       aAssets[i].setOverrideRestriction(canViewRestrictedAsset(a_userProfile, aAssets[i]));
/*      */     }
/*      */ 
/* 3891 */     return aAssets;
/*      */   }
/*      */ 
/*      */   private boolean assetIsPromoted(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 3913 */     Connection con = null;
/* 3914 */     String sSQL = null;
/* 3915 */     PreparedStatement psql = null;
/* 3916 */     ResultSet rs = null;
/* 3917 */     DBTransaction dbTransaction = a_dbTransaction;
/*      */ 
/* 3919 */     boolean bFound = false;
/*      */     try
/*      */     {
/* 3925 */       if (dbTransaction == null)
/*      */       {
/* 3927 */         dbTransaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/*      */ 
/* 3930 */       con = dbTransaction.getConnection();
/*      */ 
/* 3932 */       sSQL = "SELECT * FROM PromotedAsset WHERE AssetId=?";
/*      */ 
/* 3934 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 3936 */       psql.setLong(1, a_lId);
/*      */ 
/* 3938 */       rs = psql.executeQuery();
/*      */ 
/* 3940 */       if (rs.next())
/*      */       {
/* 3942 */         bFound = true;
/*      */       }
/*      */ 
/* 3945 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3949 */       if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3953 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (SQLException ex)
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/* 3960 */       this.m_logger.error("SQL Exception in AssetManager.assetIsPromoted : " + e);
/* 3961 */       throw new Bn2Exception("SQL Exception in AssetManager.assetIsPromoted: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 3966 */       if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 3970 */           dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException ex)
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3979 */     return bFound;
/*      */   }
/*      */ 
/*      */   public void addAssetToFeatured(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 4001 */     String ksMethodName = "addAssetToFeatured";
/*      */ 
/* 4004 */     if (!assetIsFeatured(a_dbTransaction, a_lId))
/*      */     {
/* 4006 */       Connection con = null;
/* 4007 */       ResultSet rs = null;
/* 4008 */       String sSQL = null;
/*      */       try
/*      */       {
/* 4012 */         con = a_dbTransaction.getConnection();
/*      */ 
/* 4015 */         sSQL = "SELECT AssetId FROM FeaturedAsset WHERE AssetId=?";
/*      */ 
/* 4017 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 4018 */         psql.setLong(1, a_lId);
/* 4019 */         rs = psql.executeQuery();
/*      */ 
/* 4022 */         boolean bAlreadyFeatured = rs.next();
/* 4023 */         psql.close();
/*      */ 
/* 4025 */         if (!bAlreadyFeatured)
/*      */         {
/* 4028 */           sSQL = "INSERT INTO FeaturedAsset (AssetId, DateFeatured) VALUES (?,?) ";
/* 4029 */           psql = con.prepareStatement(sSQL);
/* 4030 */           psql.setLong(1, a_lId);
/* 4031 */           psql.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
/* 4032 */           psql.executeUpdate();
/* 4033 */           psql.close();
/*      */ 
/* 4036 */           ImageAsset image = (ImageAsset)getAsset(a_dbTransaction, a_lId, null, false, false);
/* 4037 */           if (StringUtils.isEmpty(image.getFeaturedImageFile().getPath()))
/*      */           {
/* 4039 */             this.m_imageAssetManager.createFeaturedImageFile(image);
/*      */ 
/* 4043 */             String sFeaturedLocation = image.getFeaturedImageFile().getPath();
/* 4044 */             if (StringUtil.stringIsPopulated(sFeaturedLocation))
/*      */             {
/* 4046 */               sSQL = "UPDATE ImageAsset SET FeaturedFileLocation=? WHERE AssetId=? ";
/* 4047 */               psql = con.prepareStatement(sSQL);
/* 4048 */               psql.setString(1, sFeaturedLocation);
/* 4049 */               psql.setLong(2, a_lId);
/* 4050 */               psql.executeUpdate();
/* 4051 */               psql.close();
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 4057 */         clearAssetCaches();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 4062 */         this.m_logger.error("AssetManager.addAssetToFeatured: " + e);
/* 4063 */         throw new Bn2Exception("AssetManager.addAssetToFeatured: SQL Exception: ", e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeAssetFromFeatured(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 4084 */     String ksMethodName = "removeAssetFromFeatured";
/*      */ 
/* 4087 */     if (assetIsFeatured(a_dbTransaction, a_lId))
/*      */     {
/* 4089 */       Connection con = null;
/* 4090 */       String sSQL = null;
/* 4091 */       PreparedStatement psql = null;
/*      */       try
/*      */       {
/* 4095 */         con = a_dbTransaction.getConnection();
/*      */ 
/* 4098 */         ImageAsset image = (ImageAsset)getAsset(a_dbTransaction, a_lId, null, false, false);
/* 4099 */         this.m_imageAssetManager.deleteFeaturedImageFile(image);
/*      */ 
/* 4101 */         sSQL = "UPDATE ImageAsset SET FeaturedFileLocation=Null WHERE AssetId=? ";
/* 4102 */         psql = con.prepareStatement(sSQL);
/* 4103 */         psql.setLong(1, a_lId);
/* 4104 */         psql.executeUpdate();
/* 4105 */         psql.close();
/*      */ 
/* 4107 */         sSQL = "DELETE FROM FeaturedAsset WHERE AssetId=?";
/* 4108 */         psql = con.prepareStatement(sSQL);
/* 4109 */         psql.setLong(1, a_lId);
/* 4110 */         psql.executeUpdate();
/* 4111 */         psql.close();
/*      */ 
/* 4114 */         clearAssetCaches();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 4118 */         this.m_logger.error("AssetManager.removeAssetFromFeatured: " + e);
/* 4119 */         throw new Bn2Exception("AssetManager.removeAssetFromFeatured: SQL Exception: ", e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void saveFeaturedImageBrandMapping(DBTransaction a_dbTransaction, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 4136 */     String ksMethodName = "saveFeaturedImageBrandMapping";
/*      */ 
/* 4138 */     Connection con = null;
/* 4139 */     String sSQL = null;
/*      */ 
/* 4141 */     long lAssetId = a_asset.getId();
/* 4142 */     Vector vecBrands = a_asset.getFeaturedInBrandsList();
/*      */ 
/* 4145 */     if (vecBrands != null)
/*      */     {
/*      */       try
/*      */       {
/* 4150 */         con = a_dbTransaction.getConnection();
/*      */ 
/* 4154 */         sSQL = "DELETE FROM FeaturedAssetInBrand WHERE AssetId=?";
/*      */ 
/* 4156 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 4157 */         psql.setLong(1, lAssetId);
/* 4158 */         psql.executeUpdate();
/* 4159 */         psql.close();
/*      */ 
/* 4161 */         Iterator it = vecBrands.iterator();
/* 4162 */         while (it.hasNext())
/*      */         {
/* 4164 */           DataBean brand = (DataBean)it.next();
/*      */ 
/* 4167 */           sSQL = "INSERT INTO FeaturedAssetInBrand (AssetId, BrandId) VALUES (?,?) ";
/* 4168 */           psql = con.prepareStatement(sSQL);
/* 4169 */           psql.setLong(1, lAssetId);
/* 4170 */           psql.setLong(2, brand.getId());
/* 4171 */           psql.executeUpdate();
/* 4172 */           psql.close();
/*      */         }
/*      */ 
/* 4176 */         clearFeaturedItemBrandMappingCache();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 4181 */         this.m_logger.error("AssetManager.saveFeaturedImageBrandMapping: " + e);
/* 4182 */         throw new Bn2Exception("AssetManager.saveFeaturedImageBrandMapping: SQL Exception: ", e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getFeaturedAssets(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/* 4206 */     Vector vecRet = this.m_vecFeaturedImages;
/*      */ 
/* 4209 */     if (vecRet == null)
/*      */     {
/* 4212 */       synchronized (this.m_oFeaturedLock)
/*      */       {
/* 4214 */         String sSQLCriteria = "INNER JOIN FeaturedAsset fa ON (a.Id=fa.AssetId) ORDER BY fa.DateFeatured DESC";
/*      */ 
/* 4217 */         vecRet = this.m_vecFeaturedImages = getAssetsBuilder(a_transaction, sSQLCriteria, true);
/*      */ 
/* 4220 */         this.m_hmFeaturedImageBrandMapping = getFeaturedImageBrandMapping(a_transaction);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4225 */     return vecRet;
/*      */   }
/*      */ 
/*      */   public Vector searchForFeaturedAssets(Vector a_vecFilters, String a_sLanguageCode)
/*      */     throws Bn2Exception
/*      */   {
/* 4243 */     SearchCriteria query = new SearchCriteria();
/* 4244 */     query.setIsFeatured(new Boolean(true));
/* 4245 */     query.setMaxNoOfResults(1000);
/*      */ 
/* 4247 */     if (AssetBankSettings.getFiltersAffectFeatured())
/*      */     {
/* 4249 */       query.setSelectedFilters(a_vecFilters);
/*      */     }
/*      */ 
/* 4252 */     SearchResults results = this.m_searchManager.search(query, a_sLanguageCode);
/*      */ 
/* 4254 */     return results.getSearchResults();
/*      */   }
/*      */ 
/*      */   private Vector getFeaturedAssets(DBTransaction a_dbTransaction, long a_lBrandId)
/*      */     throws Bn2Exception
/*      */   {
/* 4270 */     Vector vecFeaturedAssets = getFeaturedAssets(a_dbTransaction);
/* 4271 */     Vector vecFeaturedAssetsInBrand = new Vector();
/*      */ 
/* 4273 */     Iterator itAssets = vecFeaturedAssets.iterator();
/* 4274 */     while (itAssets.hasNext())
/*      */     {
/* 4276 */       Asset assetToCheck = (Asset)itAssets.next();
/*      */ 
/* 4278 */       boolean bIsInBrand = false;
/* 4279 */       Long olAssetId = new Long(assetToCheck.getId());
/* 4280 */       if (this.m_hmFeaturedImageBrandMapping.containsKey(olAssetId))
/*      */       {
/* 4282 */         Vector vecBrands = (Vector)this.m_hmFeaturedImageBrandMapping.get(olAssetId);
/* 4283 */         Iterator itBrands = vecBrands.iterator();
/* 4284 */         while (itBrands.hasNext())
/*      */         {
/* 4286 */           DataBean brand = (DataBean)itBrands.next();
/* 4287 */           if (brand.getId() == a_lBrandId)
/*      */           {
/* 4289 */             bIsInBrand = true;
/* 4290 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 4295 */       if (bIsInBrand)
/*      */       {
/* 4297 */         vecFeaturedAssetsInBrand.add(assetToCheck);
/*      */       }
/*      */     }
/*      */ 
/* 4301 */     return vecFeaturedAssetsInBrand;
/*      */   }
/*      */ 
/*      */   public Asset getFeaturedImage(DBTransaction a_transaction, long a_lBrandId, Vector a_vecFilters, String a_sLanguageCode, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 4324 */     Vector vecFeaturedAssets = null;
/*      */ 
/* 4327 */     if ((AssetBankSettings.getUseBrands()) && (a_lBrandId > 0L))
/*      */     {
/* 4329 */       vecFeaturedAssets = getFeaturedAssets(a_transaction, a_lBrandId);
/*      */     }
/*      */     else
/*      */     {
/* 4334 */       vecFeaturedAssets = searchForFeaturedAssets(a_vecFilters, a_sLanguageCode);
/*      */     }
/* 4336 */     int iSize = vecFeaturedAssets.size();
/* 4337 */     LightweightAsset asset = null;
/*      */ 
/* 4339 */     if (AssetBankSettings.getChangeFeaturedImageOnRefresh())
/*      */     {
/* 4341 */       if (iSize > 0)
/*      */       {
/* 4343 */         if (iSize == 1)
/*      */         {
/* 4346 */           asset = (LightweightAsset)vecFeaturedAssets.firstElement();
/*      */         }
/* 4350 */         else if (a_lUserId > 0L)
/*      */         {
/* 4355 */           int iLastIndex = -1;
/* 4356 */           int iIndex = -1;
/*      */ 
/* 4358 */           if (this.m_hmUserToCurrentFeaturedIndexMap.containsKey(new Long(a_lUserId)))
/*      */           {
/* 4360 */             iLastIndex = ((Integer)this.m_hmUserToCurrentFeaturedIndexMap.get(new Long(a_lUserId))).intValue();
/*      */           }
/*      */ 
/* 4365 */           int iCount = 0;
/* 4366 */           while ((iCount < 100) && ((iIndex == iLastIndex) || (iIndex == -1)))
/*      */           {
/* 4368 */             iIndex = NumberUtil.getRandomInt(0, iSize);
/* 4369 */             iCount++;
/*      */           }
/*      */ 
/* 4373 */           asset = (LightweightAsset)vecFeaturedAssets.elementAt(iIndex);
/* 4374 */           this.m_hmUserToCurrentFeaturedIndexMap.put(new Long(a_lUserId), new Integer(iIndex));
/*      */         }
/*      */         else
/*      */         {
/* 4379 */           int iIndex = NumberUtil.getRandomInt(0, iSize);
/* 4380 */           asset = (LightweightAsset)vecFeaturedAssets.elementAt(iIndex);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 4388 */       if (this.m_lFeaturedIndex < 0L)
/*      */       {
/* 4390 */         this.m_lFeaturedIndex = this.m_refDataManager.getSystemSettingAsLong("FeaturedImageIndex");
/*      */       }
/*      */ 
/* 4393 */       int iIndex = 0;
/*      */ 
/* 4396 */       if (iSize > 0)
/*      */       {
/* 4398 */         iIndex = new Long(this.m_lFeaturedIndex % iSize).intValue();
/* 4399 */         asset = (LightweightAsset)vecFeaturedAssets.elementAt(iIndex);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4404 */     Asset returnAsset = null;
/* 4405 */     if (asset != null)
/*      */     {
/* 4407 */       if ((AssetBankSettings.getUseBrands()) && (a_lBrandId > 0L))
/*      */       {
/* 4410 */         returnAsset = (Asset)asset;
/*      */       }
/*      */       else
/*      */       {
/* 4414 */         Vector vecFullyPopulatedFeaturedAssets = getFeaturedAssets(a_transaction);
/*      */ 
/* 4416 */         for (int i = 0; i < vecFullyPopulatedFeaturedAssets.size(); i++)
/*      */         {
/* 4418 */           Asset tempAsset = (Asset)vecFullyPopulatedFeaturedAssets.elementAt(i);
/*      */ 
/* 4420 */           if (tempAsset.getId() != asset.getId())
/*      */             continue;
/* 4422 */           returnAsset = tempAsset;
/* 4423 */           break;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4429 */     return returnAsset;
/*      */   }
/*      */ 
/*      */   private void updateFeaturedIndex()
/*      */     throws Bn2Exception
/*      */   {
/* 4447 */     DBTransaction dbTransaction = null;
/*      */ 
/* 4450 */     dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */     try
/*      */     {
/* 4454 */       rotateFeaturedImage(dbTransaction);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 4458 */       this.m_logger.error("AssetManager: SQL Exception in updateFeaturedIndex : " + e);
/* 4459 */       throw new Bn2Exception("AssetManager: SQL Exception in updateFeaturedIndex : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 4464 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 4468 */           dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4472 */           this.m_logger.error("AssetManager: SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void rotateFeaturedImage(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/* 4499 */       long lIndex = this.m_refDataManager.getSystemSettingAsLong("FeaturedImageIndex");
/* 4500 */       lIndex += 1L;
/*      */ 
/* 4502 */       if (lIndex >= 1000000L)
/*      */       {
/* 4504 */         lIndex = 0L;
/*      */       }
/*      */ 
/* 4507 */       this.m_refDataManager.updateSystemSettingAsLong(a_dbTransaction, "FeaturedImageIndex", lIndex);
/* 4508 */       this.m_lFeaturedIndex = lIndex;
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 4512 */       this.m_logger.error("AssetManager: SQL Exception in updateFeaturedIndex : " + e);
/* 4513 */       throw new Bn2Exception("AssetManager: SQL Exception in updateFeaturedIndex : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void generateFeaturedImages(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/* 4536 */       Vector vecFeaturedAssets = getFeaturedAssets(a_dbTransaction);
/*      */ 
/* 4538 */       Iterator it = vecFeaturedAssets.iterator();
/* 4539 */       while (it.hasNext())
/*      */       {
/* 4541 */         ImageAsset image = (ImageAsset)it.next();
/*      */ 
/* 4544 */         if (!StringUtil.stringIsPopulated(image.getFeaturedImageFile().getPath()))
/*      */         {
/* 4547 */           this.m_imageAssetManager.createFeaturedImageFile(image);
/*      */ 
/* 4550 */           String sFeaturedLocation = image.getFeaturedImageFile().getPath();
/* 4551 */           if (StringUtil.stringIsPopulated(sFeaturedLocation))
/*      */           {
/* 4553 */             Connection con = a_dbTransaction.getConnection();
/* 4554 */             String sSQL = "UPDATE ImageAsset SET FeaturedFileLocation=? WHERE AssetId=? ";
/* 4555 */             PreparedStatement psql = con.prepareStatement(sSQL);
/* 4556 */             psql.setString(1, sFeaturedLocation);
/* 4557 */             psql.setLong(2, image.getId());
/* 4558 */             psql.executeUpdate();
/* 4559 */             psql.close();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 4566 */       this.m_logger.error("AssetManager: SQL Exception in generateFeaturedImages : " + e);
/* 4567 */       throw new Bn2Exception("AssetManager: SQL Exception in generateFeaturedImages : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean assetIsFeatured(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 4590 */     Vector vecFeaturedAssets = getFeaturedAssets(a_dbTransaction);
/*      */ 
/* 4592 */     boolean bFound = false;
/* 4593 */     Iterator it = vecFeaturedAssets.iterator();
/* 4594 */     while ((!bFound) && (it.hasNext()))
/*      */     {
/* 4596 */       Asset asset = (Asset)it.next();
/* 4597 */       if (asset.getId() == a_lId)
/*      */       {
/* 4599 */         bFound = true;
/*      */       }
/*      */     }
/*      */ 
/* 4603 */     return bFound;
/*      */   }
/*      */ 
/*      */   public void clearAssetCaches()
/*      */   {
/* 4617 */     synchronized (this.m_oFeaturedLock)
/*      */     {
/* 4619 */       this.m_vecFeaturedImages = null;
/*      */     }
/*      */ 
/* 4623 */     synchronized (this.m_hmRecentlyAddedCache)
/*      */     {
/* 4625 */       this.m_hmRecentlyAddedCache.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void clearFeaturedItemBrandMappingCache()
/*      */   {
/* 4637 */     synchronized (this.m_oFeaturedLock)
/*      */     {
/* 4639 */       this.m_hmFeaturedImageBrandMapping = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public SearchResults getRecentAssets(DBTransaction a_dbTransaction, int a_iNum, ABUserProfile a_userProfile, int a_iPage, int a_iPageSize, boolean a_bCanBeCached)
/*      */     throws Bn2Exception
/*      */   {
/* 4670 */     String sLanguageCode = a_userProfile.getCurrentLanguage().getCode();
/* 4671 */     SearchResults searchResults = null;
/* 4672 */     String sCacheKey = null;
/*      */ 
/* 4674 */     if (a_bCanBeCached)
/*      */     {
/* 4677 */       sCacheKey = a_userProfile.getPermissionsKeyForCaching();
/*      */ 
/* 4680 */       searchResults = (SearchResults)this.m_hmRecentlyAddedCache.get(sCacheKey);
/*      */     }
/*      */ 
/* 4684 */     if (searchResults == null)
/*      */     {
/* 4686 */       SearchCriteria searchCriteria = new SearchCriteria();
/*      */ 
/* 4689 */       searchCriteria.setMaxNoOfResults(a_iNum);
/*      */ 
/* 4692 */       searchCriteria.setupPermissions(a_userProfile);
/*      */ 
/* 4695 */       searchCriteria.setSelectedFilters(a_userProfile.getSelectedFilters());
/* 4696 */       searchCriteria.setIsPreviewRestricted(Boolean.FALSE);
/* 4697 */       searchCriteria.setIsSensitive(Boolean.FALSE);
/* 4698 */       Vector vecApprovalStatus = new Vector();
/* 4699 */       vecApprovalStatus.add(Integer.valueOf(2));
/* 4700 */       vecApprovalStatus.add(Integer.valueOf(3));
/* 4701 */       searchCriteria.setApprovalStatuses(vecApprovalStatus);
/*      */ 
/* 4708 */       SortField[] aSortFields = new SortField[1];
/* 4709 */       String sField = "f_long_added_sort";
/* 4710 */       aSortFields[0] = new SortField(sField, 3, true);
/*      */ 
/* 4712 */       searchCriteria.setSortFields(aSortFields);
/*      */ 
/* 4715 */       searchCriteria.setHasOwnThumbnail(Boolean.valueOf(true));
/*      */ 
/* 4717 */       searchCriteria.setAssetEntityIdsToExclude(this.m_assetEntityManager.getEntityIdsExcludedFromSearch(a_dbTransaction, false));
/*      */ 
/* 4720 */       int iMaxAgeWeeks = 2;
/* 4721 */       int iAttempts = 0;
/*      */       do
/*      */       {
/* 4726 */         searchCriteria.setMaxAgeInIndex(iMaxAgeWeeks);
/* 4727 */         if (iAttempts > 0)
/*      */         {
/* 4729 */           this.m_logger.debug(getClass().getSimpleName() + ".getRecentAssets() : Retrying after insufficient matches returned : age in weeks=" + iMaxAgeWeeks);
/*      */         }
/* 4731 */         searchResults = this.m_searchManager.searchByPageIndex(searchCriteria, a_iPage, a_iPageSize, sLanguageCode);
/* 4732 */         iMaxAgeWeeks *= 4;
/*      */ 
/* 4734 */         iAttempts++; } while ((iAttempts < 3) && (searchResults.getNumResults() < a_iNum));
/*      */ 
/* 4736 */       if (a_bCanBeCached)
/*      */       {
/* 4738 */         synchronized (this.m_hmRecentlyAddedCache)
/*      */         {
/* 4741 */           this.m_hmRecentlyAddedCache.put(sCacheKey, searchResults);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4746 */     return searchResults;
/*      */   }
/*      */ 
/*      */   public SearchResults getAssetsByDownloads(int a_iNum, ABUserProfile a_userProfile, int a_iPage, int a_iPageSize, String a_sLanguageCode, boolean a_bMostPopular)
/*      */     throws Bn2Exception
/*      */   {
/* 4761 */     return getSortedAssets("f_downloads", a_iNum, a_userProfile, a_iPage, a_iPageSize, a_sLanguageCode, a_bMostPopular);
/*      */   }
/*      */ 
/*      */   public SearchResults getAssetsByViews(int a_iNum, ABUserProfile a_userProfile, int a_iPage, int a_iPageSize, String a_sLanguageCode, boolean a_bMostPopular)
/*      */     throws Bn2Exception
/*      */   {
/* 4775 */     return getSortedAssets("f_views", a_iNum, a_userProfile, a_iPage, a_iPageSize, a_sLanguageCode, a_bMostPopular);
/*      */   }
/*      */ 
/*      */   private SearchResults getSortedAssets(String a_sFieldName, int a_iNum, ABUserProfile a_userProfile, int a_iPage, int a_iPageSize, String a_sLanguageCode, boolean a_bReverseOrding)
/*      */     throws Bn2Exception
/*      */   {
/* 4799 */     SearchCriteria searchCriteria = new SearchCriteria();
/* 4800 */     SearchResults searchResults = null;
/*      */ 
/* 4803 */     searchCriteria.setMaxNoOfResults(a_iNum);
/*      */ 
/* 4806 */     searchCriteria.setupPermissions(a_userProfile);
/* 4807 */     searchCriteria.setSelectedFilters(a_userProfile.getSelectedFilters());
/*      */ 
/* 4809 */     if (!a_userProfile.getIsAdmin())
/*      */     {
/* 4811 */       searchCriteria.addApprovalStatus(2);
/* 4812 */       searchCriteria.addApprovalStatus(3);
/*      */     }
/*      */ 
/* 4816 */     SortField[] aSortFields = new SortField[1];
/* 4817 */     SortField field = new SortField(a_sFieldName, 4, a_bReverseOrding);
/* 4818 */     aSortFields[0] = field;
/* 4819 */     searchCriteria.setSortFields(aSortFields);
/*      */ 
/* 4821 */     searchResults = this.m_searchManager.searchByPageIndex(searchCriteria, a_iPage, a_iPageSize, a_sLanguageCode);
/*      */ 
/* 4823 */     return searchResults;
/*      */   }
/*      */ 
/*      */   public RefDataItem[] getAssetTypes()
/*      */     throws Bn2Exception
/*      */   {
/* 4841 */     if (this.m_aAssetTypes == null)
/*      */     {
/* 4843 */       synchronized (this.m_oAssetTypesLock)
/*      */       {
/* 4845 */         if (this.m_aAssetTypes == null)
/*      */         {
/* 4848 */           Connection con = null;
/* 4849 */           ResultSet rs = null;
/* 4850 */           DBTransaction transaction = null;
/*      */           try
/*      */           {
/* 4854 */             transaction = getTransactionManager().getCurrentOrNewTransaction();
/* 4855 */             con = transaction.getConnection();
/*      */ 
/* 4857 */             String sSQL = "SELECT Id, Name FROM AssetType ORDER BY Id";
/*      */ 
/* 4860 */             PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 4862 */             rs = psql.executeQuery();
/* 4863 */             Vector vecResults = new Vector();
/*      */ 
/* 4865 */             while (rs.next())
/*      */             {
/* 4867 */               RefDataItem type = new RefDataItem();
/* 4868 */               type.setId(rs.getLong("Id"));
/* 4869 */               type.setDescription(rs.getString("Name"));
/* 4870 */               vecResults.add(type);
/*      */             }
/* 4872 */             psql.close();
/*      */ 
/* 4874 */             this.m_aAssetTypes = new RefDataItem[vecResults.size()];
/*      */ 
/* 4876 */             for (int i = 0; i < vecResults.size(); i++)
/*      */             {
/* 4878 */               this.m_aAssetTypes[i] = ((RefDataItem)vecResults.get(i));
/*      */             }
/*      */           }
/*      */           catch (SQLException e)
/*      */           {
/* 4883 */             this.m_logger.error("SQL Exception in AssetManager.getAssetTypes : " + e);
/* 4884 */             throw new Bn2Exception("SQL Exception in AssetManager.getAssetTypes : " + e, e);
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/* 4890 */               transaction.commit();
/*      */             }
/*      */             catch (SQLException sqle)
/*      */             {
/* 4894 */               this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4901 */     return this.m_aAssetTypes;
/*      */   }
/*      */ 
/*      */   public Vector getAssetsForViewByPopularity(DBTransaction a_dbTransaction, Vector a_vAssetIds, boolean a_bPreserverOrder)
/*      */     throws Bn2Exception
/*      */   {
/* 4915 */     Vector results = null;
/* 4916 */     Connection con = null;
/* 4917 */     ResultSet rs = null;
/* 4918 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 4920 */     if (transaction == null)
/*      */     {
/* 4922 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/* 4925 */     int iNumToReturn = a_vAssetIds.size();
/*      */     try
/*      */     {
/* 4929 */       con = transaction.getConnection();
/*      */ 
/* 4931 */       String sIds = StringUtil.convertNumbersToString(a_vAssetIds, ",");
/*      */ 
/* 4933 */       if (StringUtils.isNotEmpty(sIds))
/*      */       {
/* 4935 */         String sSQL = "SELECT a.Id, a.VersionNumber, a.DateAdded, a.FileLocation, a.ThumbnailFileLocation aThumbnailFileLocation, a.NumViews, a.NumDownloads, a.LastDownloaded, ff.ThumbnailFileLocation ffThumbnailFileLocation, u.Username, u.Forename, u.Surname, u.EmailAddress FROM Asset a INNER JOIN AssetBankUser u ON u.Id=a.AddedByUserId INNER JOIN FileFormat ff ON ff.Id = a.FileFormatId WHERE a.Id IN (" + sIds + ")";
/*      */ 
/* 4956 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 4957 */         rs = psql.executeQuery();
/*      */ 
/* 4959 */         if (a_bPreserverOrder)
/*      */         {
/* 4961 */           results = new Vector(iNumToReturn);
/* 4962 */           results.setSize(iNumToReturn);
/*      */         }
/*      */         else
/*      */         {
/* 4966 */           results = new Vector();
/*      */         }
/*      */ 
/* 4969 */         while (rs.next())
/*      */         {
/* 4971 */           Asset asset = new Asset();
/* 4972 */           asset.setId(rs.getLong("Id"));
/* 4973 */           asset.setDateAdded(rs.getTimestamp("DateAdded"));
/* 4974 */           asset.setFileLocation(rs.getString("FileLocation"));
/* 4975 */           asset.setNumViews(rs.getInt("NumViews"));
/* 4976 */           asset.setNumDownloads(rs.getInt("NumDownloads"));
/* 4977 */           asset.setDateLastDownloaded(rs.getTimestamp("LastDownloaded"));
/* 4978 */           asset.setVersionNumber(rs.getInt("VersionNumber"));
/*      */ 
/* 4980 */           ImageFile thumbnail = new ImageFile();
/*      */ 
/* 4982 */           if (StringUtils.isNotEmpty(rs.getString("aThumbnailFileLocation")))
/*      */           {
/* 4984 */             thumbnail.setPath(rs.getString("aThumbnailFileLocation"));
/*      */           }
/*      */           else
/*      */           {
/* 4988 */             thumbnail.setPath(rs.getString("ffThumbnailFileLocation"));
/*      */           }
/* 4990 */           asset.setThumbnailImageFile(thumbnail);
/*      */ 
/* 4992 */           ABUser user = new ABUser();
/* 4993 */           user.setForename(rs.getString("Forename"));
/* 4994 */           user.setSurname(rs.getString("Surname"));
/* 4995 */           user.setUsername(rs.getString("Username"));
/* 4996 */           user.setEmailAddress(rs.getString("EmailAddress"));
/* 4997 */           asset.setAddedByUser(user);
/*      */ 
/* 4999 */           if (a_bPreserverOrder)
/*      */           {
/* 5001 */             int i = a_vAssetIds.indexOf(Long.valueOf(asset.getId()));
/* 5002 */             if (i >= results.size())
/*      */               break;
/* 5004 */             results.set(i, asset);
/*      */           }
/*      */           else
/*      */           {
/* 5013 */             results.add(asset);
/*      */           }
/*      */         }
/* 5016 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 5021 */       this.m_logger.error("SQL Exception whilst getting assets : " + StringUtil.convertNumbersToString(results, ",") + " : " + e);
/* 5022 */       throw new Bn2Exception("SQL Exception whilst getting assets : " + StringUtil.convertNumbersToString(results, ",") + " : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 5027 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 5031 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 5035 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 5040 */     return results;
/*      */   }
/*      */ 
/*      */   public int getTotalAssetCount(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 5048 */     int iResult = 0;
/* 5049 */     Connection con = null;
/* 5050 */     ResultSet rs = null;
/* 5051 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 5053 */     if (transaction == null)
/*      */     {
/* 5055 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 5060 */       con = transaction.getConnection();
/*      */ 
/* 5062 */       String sSQL = "SELECT COUNT(Id) assetCount FROM Asset";
/* 5063 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 5064 */       rs = psql.executeQuery();
/*      */ 
/* 5066 */       if (rs.next())
/*      */       {
/* 5068 */         iResult = rs.getInt("assetCount");
/*      */       }
/*      */ 
/* 5071 */       psql.close();
/*      */ 
/* 5073 */       int i = iResult;
/*      */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 5077 */       this.m_logger.error("SQL Exception whilst getting asset count : " + e);
/* 5078 */       throw new Bn2Exception("SQL Exception whilst getting asset count : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 5083 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 5087 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 5091 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
                     throw new Bn2Exception(sqle.getMessage());
/*      */         }
/*      */       }
/* 5092 */     }//throw localObject;
/*      */   }
/*      */ 
/*      */   public static boolean userHasPermissionForAsset(LightweightAsset a_asset, Set<Long> a_userCategoryPermissions)
/*      */   {
/* 5109 */     boolean bHasPermission = userHasRequiredPermissions(a_asset.getRestrictiveCatIds(), a_userCategoryPermissions);
/*      */ 
/* 5111 */     return bHasPermission;
/*      */   }
/*      */ 
/*      */   public static boolean userHasRequiredPermissions(Set<Long> a_requiredPermissionCatIds, Set<Long> a_userPermissions)
/*      */   {
/* 5124 */     boolean bHasPermission = false;
/*      */ 
/* 5126 */     if (a_userPermissions == null)
/*      */     {
/* 5129 */       bHasPermission = true;
/*      */     }
/* 5134 */     else if (!a_requiredPermissionCatIds.isEmpty())
/*      */     {
/* 5136 */       bHasPermission = com.bright.assetbank.category.util.CategoryUtil.checkAccessLevelPermissions(a_userPermissions, a_requiredPermissionCatIds);
/*      */     }
/*      */ 
/* 5140 */     return bHasPermission;
/*      */   }
/*      */ 
/*      */   public void registerAssetLoadParticipant(AssetLoadParticipant a_participant)
/*      */   {
/* 5149 */     synchronized (this.m_assetLoadParticipants)
/*      */     {
/* 5151 */       this.m_assetLoadParticipants.add(a_participant);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void registerAssetSaveParticipant(AssetSaveParticipant a_participant)
/*      */   {
/* 5160 */     synchronized (this.m_assetSaveParticipants)
/*      */     {
/* 5162 */       this.m_assetSaveParticipants.add(a_participant);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void registerAssetDeleteParticipant(AssetDeleteParticipant a_participant)
/*      */   {
/* 5171 */     synchronized (this.m_assetDeleteParticipants)
/*      */     {
/* 5173 */       this.m_assetDeleteParticipants.add(a_participant);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAssetNonDuplicateStatus(DBTransaction a_dbTransaction, long a_lAssetId, boolean b_nonDuplicate)
/*      */     throws Bn2Exception
/*      */   {
/* 5180 */     Connection con = null;
/* 5181 */     PreparedStatement psql = null;
/* 5182 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 5184 */     if (transaction == null)
/*      */     {
/* 5186 */       transaction = getTransactionManager().getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 5191 */       con = transaction.getConnection();
/*      */ 
/* 5193 */       String sSql = "UPDATE Asset SET IsNotDuplicate=? WHERE Id=?";
/*      */ 
/* 5195 */       psql = con.prepareStatement(sSql);
/* 5196 */       psql.setBoolean(1, b_nonDuplicate);
/* 5197 */       psql.setLong(2, a_lAssetId);
/*      */ 
/* 5199 */       psql.executeUpdate();
/*      */ 
/* 5201 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 5205 */       this.m_logger.error("SQL Exception whilst saving asset's non-duplicate status : " + e);
/* 5206 */       throw new Bn2Exception("SQL Exception whilst saving asset's non-duplicate status : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 5211 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 5215 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 5219 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ExtendedCategoryInfo buildExtendedCategoryInfo(DBTransaction a_transaction, ResultSet a_rs)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 5237 */     ExtendedCategoryInfo cat = new ExtendedCategoryInfo();
/* 5238 */     long lCatId = a_rs.getLong("ceaId");
/* 5239 */     if (lCatId > 0L)
/*      */     {
/* 5241 */       cat.setId(lCatId);
/* 5242 */       long lParentId = a_rs.getLong("ceaParentId");
/* 5243 */       if ((lParentId == -1L) || (lParentId > 0L))
/*      */       {
/* 5245 */         cat.setParentId(lParentId);
/*      */       }
/* 5247 */       cat.setCategoryTypeId(a_rs.getLong("ceaCategoryTypeId"));
/*      */ 
/* 5249 */       Category category = this.m_categoryManager.getCategory(a_transaction, cat.getCategoryTypeId(), lCatId);
/* 5250 */       cat.setName(category.getFullName());
/*      */     }
/* 5252 */     return cat;
/*      */   }
/*      */ 
/*      */   public DBTransactionManager getTransactionManager()
/*      */   {
/* 5261 */     return this.m_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 5267 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setWorkflowManager(WorkflowManager a_workflowManager)
/*      */   {
/* 5272 */     this.m_workflowManager = a_workflowManager;
/*      */   }
/*      */ 
/*      */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWworkflowManager)
/*      */   {
/* 5277 */     this.m_assetWorkflowManager = a_assetWworkflowManager;
/*      */   }
/*      */ 
/*      */   public FileAssetManagerImpl getFileAssetManager()
/*      */   {
/* 5283 */     return this.m_fileAssetManager;
/*      */   }
/*      */ 
/*      */   public void setFileAssetManager(FileAssetManagerImpl a_sFileAssetManager)
/*      */   {
/* 5289 */     this.m_fileAssetManager = a_sFileAssetManager;
/*      */   }
/*      */ 
/*      */   public ImageAssetManagerImpl getImageAssetManager()
/*      */   {
/* 5295 */     return this.m_imageAssetManager;
/*      */   }
/*      */ 
/*      */   public void setImageAssetManager(ImageAssetManagerImpl a_sImageAssetManager)
/*      */   {
/* 5301 */     this.m_imageAssetManager = a_sImageAssetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetApprovalManager(AssetApprovalManager a_assetApprovalManager)
/*      */   {
/* 5306 */     this.m_assetApprovalManager = a_assetApprovalManager;
/*      */   }
/*      */ 
/*      */   public ScheduleManager getScheduleManager()
/*      */   {
/* 5312 */     return this.m_scheduleManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/* 5318 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ 
/*      */   public EmailManager getEmailManager()
/*      */   {
/* 5324 */     return this.m_emailManager;
/*      */   }
/*      */ 
/*      */   public void setEmailManager(EmailManager a_sEmailManager)
/*      */   {
/* 5329 */     this.m_emailManager = a_sEmailManager;
/*      */   }
/*      */ 
/*      */   public VideoAssetManagerImpl getVideoAssetManager() {
/* 5333 */     return this.m_videoAssetManager;
/*      */   }
/*      */ 
/*      */   public void setVideoAssetManager(VideoAssetManagerImpl a_videoAssetManager) {
/* 5337 */     this.m_videoAssetManager = a_videoAssetManager;
/*      */   }
/*      */ 
/*      */   public AudioAssetManagerImpl getAudioAssetManager() {
/* 5341 */     return this.m_audioAssetManager;
/*      */   }
/*      */ 
/*      */   public void setAudioAssetManager(AudioAssetManagerImpl a_audioAssetManager) {
/* 5345 */     this.m_audioAssetManager = a_audioAssetManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_sCategoryCountCacheManager) {
/* 5349 */     this.m_categoryCountCacheManager = a_sCategoryCountCacheManager;
/*      */   }
/*      */ 
/*      */   public void setRefDataManager(RefDataManager a_refDataManager) {
/* 5353 */     this.m_refDataManager = a_refDataManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/* 5358 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*      */   {
/* 5363 */     this.m_assetEntityManager = assetEntityManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(AssetCategoryManager a_manager)
/*      */   {
/* 5368 */     this.m_categoryManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setThumbnailGenerationManager(ThumbnailGenerationManager a_thumbnailGenerationManager) {
/* 5372 */     this.m_thumbnailGenerationManager = a_thumbnailGenerationManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager a_attributeManager) {
/* 5376 */     this.m_attributeManager = a_attributeManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*      */   {
/* 5381 */     this.m_attributeValueManager = a_attributeValueManager;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*      */   {
/* 5386 */     this.m_fileStoreManager = a_fileStoreManager;
/*      */   }
/*      */ 
/*      */   public void setDirectLinkCacheManager(DirectLinkCacheManager a_directLinkCacheManager)
/*      */   {
/* 5391 */     this.m_directLinkCacheManager = a_directLinkCacheManager;
/*      */   }
/*      */ 
/*      */   public void setUsageManager(UsageManager a_sUsageManager)
/*      */   {
/* 5396 */     this.m_usageManager = a_sUsageManager;
/*      */   }
/*      */ 
/*      */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*      */   {
/* 5401 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*      */   }

/*      */

    public boolean demoteChildInRelationshipSequence(DBTransaction a_dbTransaction, long lParentId, long lChildId) throws Bn2Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.m_assetRelationshipManager.demoteChildInRelationshipSequence(a_dbTransaction, lParentId, lChildId);
    }

    public Vector addAssetRelationships(DBTransaction a_dbTransaction, long lOriginalAssetId, String string, long lRelationshipTypeId) throws Bn2Exception {
       return this.m_assetRelationshipManager.addAssetRelationships(a_dbTransaction, lOriginalAssetId, k_sCmsItemItem, lRelationshipTypeId);
    }

    public Vector getRelatedAssetIds(DBTransaction a_dbTransaction, long id, long l) {
      return this.getRelatedAssetIds(a_dbTransaction, id, l);
    }
 }

/* Location:           C:\Users\mamatha\Documents\AssetBank\asset-bank3.77V - Copy\WEB-INF\classes\com.jar
 * Qualified Name:     com.bright.assetbank.application.service.AssetManager
 * JD-Core Version:    0.6.0
 */