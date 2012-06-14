/*      */ package com.bright.assetbank.application.bean;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.agreements.bean.Agreement;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*      */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*      */ import com.bright.assetbank.converter.AssetToTextConverter;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.entity.relationship.bean.RelationshipDescriptionEntry;
/*      */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.database.bean.DataBean;
/*      */ import com.bright.framework.image.bean.ImageFile;
/*      */ import com.bright.framework.search.bean.IterableIndexableDocument;
/*      */ import com.bright.framework.search.bean.SearchResult;
/*      */ import com.bright.framework.search.constant.SearchConstants;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.storage.constant.StoredFileType;
/*      */ import com.bright.framework.util.DateUtil;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*      */ import java.io.IOException;
/*      */ import java.text.DateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.avalon.framework.component.ComponentManager;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.lucene.document.Document;
/*      */ import org.apache.lucene.document.Field;
/*      */ import org.apache.lucene.document.Field.Index;
/*      */ import org.apache.lucene.document.Field.Store;
/*      */ import org.apache.lucene.document.NumericField;
/*      */ 
/*      */ public class LightweightAsset extends DataBean
/*      */   implements IterableIndexableDocument, SearchResult, AssetBankConstants, AssetBankSearchConstants, SearchConstants
/*      */ {
/*   92 */   private long m_lTypeId = 0L;
/*      */ 
/*   94 */   private Date m_dtAdded = null;
/*   95 */   private Date m_dtLastModified = null;
/*   96 */   private Date m_dtExpiryDate = null;
/*   97 */   private Date m_dtCheckedOut = null;
/*   98 */   private ImageFile m_thumbnailImageFile = null;
/*   99 */   private ImageFile m_homogenizedImageFile = null;
/*  100 */   protected ImageFile m_previewImageFile = null;
/*  101 */   private BrightMoney m_price = null;
/*  102 */   private int m_iOrientation = 0;
/*  103 */   private long m_lFileSizeInBytes = 0L;
/*  104 */   private String m_sDescriptiveCategoryNames = null;
/*  105 */   private String m_sFileLocation = null;
/*  106 */   private boolean m_bInAssetBox = false;
/*      */ 
/*  108 */   private HashMap<Long, Vector<Description>> m_hmDescriptions = new HashMap();
/*      */ 
/*  111 */   private boolean m_bIsRestricted = false;
/*  112 */   private boolean m_bIsComplete = false;
/*      */ 
/*  115 */   private int m_iPosition = 0;
/*  116 */   private float m_fSearchScore = 0.0F;
/*  117 */   private FileFormat m_format = null;
/*      */ 
/*  119 */   private String m_sSearchName = null;
/*  120 */   private String m_sSearchDescription = null;
/*      */ 
/*  122 */   private boolean m_bIsFeatured = false;
/*  123 */   private boolean m_bIsSensitive = false;
/*      */ 
/*  125 */   private int m_iNumViews = 0;
/*  126 */   private int m_iNumDownloads = 0;
/*  127 */   private Date m_dtLastDownloaded = null;
/*      */ 
/*  129 */   private AssetEntity m_entity = null;
/*      */ 
/*  131 */   private String m_sOriginalFilename = null;
/*  132 */   private String m_sOriginalFileLocation = null;
/*      */ 
/*  134 */   private Agreement m_agreement = null;
/*  135 */   private Vector<Agreement> m_vecPreviousAgreements = null;
/*  136 */   private long m_lAgreementTypeId = 0L;
/*  137 */   private String m_sAPIAttributes = null;
/*      */ 
/*  139 */   private boolean m_bUnsubmitted = false;
/*  140 */   private Set<Long> m_restrictiveCatIds = null;
/*  141 */   private boolean m_bOverrideRestriction = false;
/*      */ 
/*  143 */   private Vector<WorkflowInfo> m_vecWorkflows = null;
/*  144 */   private int m_iApprovalStatus = -1;
/*      */ 
/*  146 */   protected float m_fAverageRating = -1.0F;
/*  147 */   private boolean m_bCanBeRated = false;
/*      */ 
/*  150 */   protected Vector<Category> m_vecPermissionCategories = null;
/*      */ 
/*  153 */   AssetApprovalStatuses m_approvalStatuses = new AssetApprovalStatuses();
/*  154 */   protected ArrayList<RelationshipDescriptionEntry> m_alRelDescriptions = null;
/*  155 */   protected String m_sRelationshipDescriptionSearchString = null;
/*      */ 
/*      */   public String getSearchName()
/*      */   {
/*  159 */     return this.m_sSearchName;
/*      */   }
/*      */ 
/*      */   public void setSearchName(String a_sSearchName)
/*      */   {
/*  164 */     this.m_sSearchName = a_sSearchName;
/*      */   }
/*      */ 
/*      */   public boolean getIsFeatured()
/*      */   {
/*  169 */     return this.m_bIsFeatured;
/*      */   }
/*      */ 
/*      */   public void setIsFeatured(boolean a_sIsFeatured) {
/*  173 */     this.m_bIsFeatured = a_sIsFeatured;
/*      */   }
/*      */ 
/*      */   public LightweightAsset()
/*      */   {
/*  184 */     this.m_price = new BrightMoney();
/*      */   }
/*      */ 
/*      */   public LightweightAsset(LightweightAsset a_asset)
/*      */   {
/*  198 */     super(a_asset);
/*      */ 
/*  200 */     this.m_format = a_asset.m_format;
/*  201 */     this.m_lTypeId = a_asset.m_lTypeId;
/*  202 */     this.m_sFileLocation = a_asset.m_sFileLocation;
/*  203 */     this.m_sOriginalFileLocation = a_asset.m_sOriginalFileLocation;
/*  204 */     this.m_lFileSizeInBytes = a_asset.m_lFileSizeInBytes;
/*  205 */     this.m_dtAdded = a_asset.m_dtAdded;
/*  206 */     this.m_dtLastModified = a_asset.m_dtLastModified;
/*  207 */     this.m_bInAssetBox = a_asset.m_bInAssetBox;
/*  208 */     this.m_dtExpiryDate = a_asset.m_dtExpiryDate;
/*  209 */     this.m_thumbnailImageFile = a_asset.m_thumbnailImageFile;
/*  210 */     this.m_homogenizedImageFile = a_asset.m_homogenizedImageFile;
/*  211 */     this.m_previewImageFile = a_asset.m_previewImageFile;
/*  212 */     this.m_iPosition = a_asset.m_iPosition;
/*  213 */     this.m_fSearchScore = a_asset.m_fSearchScore;
/*  214 */     this.m_bIsFeatured = a_asset.m_bIsFeatured;
/*      */ 
/*  216 */     this.m_bUnsubmitted = a_asset.m_bUnsubmitted;
/*  217 */     this.m_price = a_asset.m_price;
/*  218 */     this.m_iOrientation = a_asset.m_iOrientation;
/*      */ 
/*  220 */     this.m_bIsRestricted = a_asset.m_bIsRestricted;
/*  221 */     this.m_bIsSensitive = a_asset.m_bIsSensitive;
/*  222 */     this.m_bIsComplete = a_asset.m_bIsComplete;
/*      */ 
/*  224 */     this.m_iNumViews = a_asset.m_iNumViews;
/*  225 */     this.m_iNumDownloads = a_asset.m_iNumDownloads;
/*  226 */     this.m_dtLastDownloaded = a_asset.m_dtLastDownloaded;
/*      */ 
/*  228 */     this.m_entity = a_asset.m_entity;
/*  229 */     this.m_sOriginalFilename = a_asset.getOriginalFilename();
/*      */ 
/*  231 */     this.m_agreement = a_asset.m_agreement;
/*  232 */     this.m_vecPreviousAgreements = a_asset.m_vecPreviousAgreements;
/*  233 */     this.m_lAgreementTypeId = a_asset.m_lAgreementTypeId;
/*      */ 
/*  235 */     this.m_vecPermissionCategories = a_asset.m_vecPermissionCategories;
/*  236 */     this.m_vecWorkflows = a_asset.m_vecWorkflows;
/*      */ 
/*  238 */     this.m_bCanBeRated = a_asset.m_bCanBeRated;
/*  239 */     this.m_fAverageRating = a_asset.m_fAverageRating;
/*      */ 
/*  241 */     this.m_approvalStatuses = a_asset.m_approvalStatuses;
/*      */   }
/*      */ 
/*      */   public Document createLuceneDocument(Object a_params)
/*      */   {
/*  246 */     CreateLuceneDocumentFromAssetParameters params = (CreateLuceneDocumentFromAssetParameters)a_params;
/*  247 */     String[] a_asSortFieldNames = params.getSortFieldNames();
/*      */ 
/*  249 */     Document doc = new Document();
/*      */ 
/*  251 */     addDateFieldToDoc(doc, "f_long_added", this.m_dtAdded, Field.Store.NO, a_asSortFieldNames);
/*  252 */     addDateFieldToDoc(doc, "f_addedWeek", this.m_dtAdded, a_asSortFieldNames, Field.Store.NO, DateUtil.getLuceneWeekInYearDateFormat());
/*  253 */     addDateFieldToDoc(doc, "f_long_modified", this.m_dtLastModified, Field.Store.NO, a_asSortFieldNames);
/*  254 */     addDateFieldToDoc(doc, "f_expires", this.m_dtExpiryDate, Field.Store.NO, a_asSortFieldNames);
/*  255 */     addDateFieldToDoc(doc, "f_long_lastDownload", getDateLastDownloaded(), Field.Store.NO, a_asSortFieldNames);
/*      */ 
/*  257 */     addFieldToDoc(doc, "f_fLoc", (this.m_thumbnailImageFile == null) || (this.m_thumbnailImageFile.getPath() == null) ? "" : this.m_thumbnailImageFile.getPath(), Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/*  258 */     addFieldToDoc(doc, "f_typeid", String.valueOf(getTypeId()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  261 */     maybeAddLongSortFieldToDoc(doc, "f_id", getId(), a_asSortFieldNames);
/*      */ 
/*  264 */     addLongFieldToDoc(doc, "f_long_id", getId(), Field.Store.NO, a_asSortFieldNames);
/*      */ 
/*  267 */     addFieldToDoc(doc, "f_fhomLoc", (getHomogenizedImageFile() == null) || (getHomogenizedImageFile().getPath() == null) ? "" : getHomogenizedImageFile().getPath(), Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/*      */ 
/*  270 */     addFieldToDoc(doc, "f_fprevLoc", (getPreviewImageFile() == null) || (getPreviewImageFile().getPath() == null) ? "" : getPreviewImageFile().getPath(), Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/*      */ 
/*  273 */     addFieldToDoc(doc, "f_approvalStatus", String.valueOf(getApprovalStatus()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  276 */     addFieldToDoc(doc, "f_unsubmitted", getIsUnsubmitted() ? "1" : "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  279 */     addFieldToDoc(doc, "f_hasWorkflow", getHasWorkflow() ? "1" : "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  282 */     addFieldToDoc(doc, "f_ownThumbnail", getHasConvertedThumbnail() ? "1" : "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  285 */     addFieldToDoc(doc, "f_previewRestricted", getIsRestricted() ? "1" : "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  288 */     addLongFieldToDoc(doc, "f_long_price", getPrice().getAmount(), Field.Store.YES, a_asSortFieldNames);
/*      */ 
/*  291 */     addFieldToDoc(doc, "f_orientation", String.valueOf(getOrientation()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  294 */     addLongFieldToDoc(doc, "f_long_fileSize", getFileSizeInBytes(), Field.Store.YES, a_asSortFieldNames);
/*      */ 
/*  299 */     if (getIsFeatured())
/*      */     {
/*  301 */       addFieldToDoc(doc, "f_featured", "1", Field.Store.NO, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */     else
/*      */     {
/*  305 */       addFieldToDoc(doc, "f_featured", "0", Field.Store.NO, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  309 */     addFieldToDoc(doc, "f_views", String.valueOf(getNumViews()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*  310 */     addFieldToDoc(doc, "f_downloads", String.valueOf(getNumDownloads()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  313 */     addFieldToDoc(doc, "f_canBeRated", getCanBeRated() ? "1" : "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  316 */     if ((getEntity() != null) && (getEntity().getId() > 0L))
/*      */     {
/*  318 */       addFieldToDoc(doc, "f_entityId", String.valueOf(getEntity().getId()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*  319 */       addFieldToDoc(doc, "f_entityName", String.valueOf(getEntity().getName()), Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/*      */ 
/*  321 */       if (getEntity().getMustHaveParent())
/*      */       {
/*  323 */         addFieldToDoc(doc, "f_mustHaveParent", "1", Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/*      */       }
/*      */       else
/*      */       {
/*  327 */         addFieldToDoc(doc, "f_mustHaveParent", "0", Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/*      */       }
/*      */ 
/*  331 */       if (getEntity().getShowOnDescendantCategories())
/*      */       {
/*  333 */         addFieldToDoc(doc, "f_showOnDescendants", "1", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */       }
/*      */       else
/*      */       {
/*  337 */         addFieldToDoc(doc, "f_showOnDescendants", "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */       }
/*      */ 
/*  340 */       if (getRelationshipDescriptions() != null)
/*      */       {
/*  342 */         String sRelationshipDescriptionString = "";
/*  343 */         for (RelationshipDescriptionEntry rde : getRelationshipDescriptions())
/*      */         {
/*  345 */           sRelationshipDescriptionString = sRelationshipDescriptionString + rde.getSourceAssetId() + ":" + rde.getRelationshipTypeId() + ":" + (StringUtil.stringIsPopulated(rde.getDescription()) ? rde.getDescription() : "") + ",";
/*      */         }
/*  347 */         if (StringUtil.stringIsPopulated(sRelationshipDescriptionString))
/*      */         {
/*  349 */           addFieldToDoc(doc, "f_relationshipDescriptions", sRelationshipDescriptionString, Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  355 */       addFieldToDoc(doc, "f_entityId", String.valueOf(0), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  358 */     if (getIsSensitive())
/*      */     {
/*  360 */       addFieldToDoc(doc, "f_sensitive", "1", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */     else
/*      */     {
/*  364 */       addFieldToDoc(doc, "f_sensitive", "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  367 */     if (getAgreementTypeId() > 0L)
/*      */     {
/*  369 */       addFieldToDoc(doc, "f_agreementTypeId", String.valueOf(getAgreementTypeId()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */     else
/*      */     {
/*  373 */       addFieldToDoc(doc, "f_agreementTypeId", String.valueOf(0), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  376 */     return doc;
/*      */   }
/*      */ 
/*      */   public boolean getHasWorkflow()
/*      */   {
/*  387 */     return (getWorkflows() != null) && (getWorkflows().size() > 0);
/*      */   }
/*      */ 
/*      */   public String getFileKeywords()
/*      */   {
/*  404 */     String sKeywords = "";
/*  405 */     String sPath = null;
/*      */ 
/*  408 */     if (this.m_format.getIsIndexable())
/*      */     {
/*      */       try
/*      */       {
/*  412 */         FileStoreManager fileStoreManager = (FileStoreManager)GlobalApplication.getInstance().getComponentManager().lookup("FileStoreManager");
/*      */ 
/*  415 */         if ((this.m_format.getAssetToTextConverterClass() != null) && (this.m_format.getAssetToTextConverterClass().length() > 0))
/*      */         {
/*  417 */           AssetToTextConverter converter = this.m_format.getAssetToTextConverterInstance();
/*      */ 
/*  419 */           String sFileLocation = (this.m_sOriginalFileLocation != null) && (this.m_sOriginalFileLocation.length() > 0) ? this.m_sOriginalFileLocation : this.m_sFileLocation;
/*      */ 
/*  421 */           String sText = converter.getText(fileStoreManager.getAbsolutePath(sFileLocation));
/*      */ 
/*  423 */           if (sText != null)
/*      */           {
/*  425 */             sKeywords = sKeywords + sText;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  431 */           sPath = fileStoreManager.getAbsolutePath(this.m_sFileLocation);
/*  432 */           sKeywords = sKeywords + FileUtil.readIntoStringBuffer(fileStoreManager.getAbsolutePath(this.m_sFileLocation));
/*      */         }
/*      */       }
/*      */       catch (IOException ioe)
/*      */       {
/*  437 */         GlobalApplication.getInstance().getLogger().error("LightweightAsset.getFileKeywords() : Cannot read asset file from " + (sPath != null ? sPath : this.m_sFileLocation), ioe);
/*      */       }
/*      */       catch (Bn2Exception be)
/*      */       {
/*  442 */         GlobalApplication.getInstance().getLogger().error("LightweightAsset.getFileKeywords() : Cannot convert asset file to text for " + (sPath != null ? sPath : this.m_sFileLocation), be);
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*  447 */         GlobalApplication.getInstance().getLogger().error("LightweightAsset.getFileKeywords() : Cannot convert asset file to text for  " + (sPath != null ? sPath : this.m_sFileLocation), e);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  452 */     return sKeywords;
/*      */   }
/*      */ 
/*      */   public void populateFromLuceneDocument(Document a_document)
/*      */   {
/*  476 */     setId(Long.parseLong(a_document.get("f_id")));
/*      */ 
/*  478 */     String sTest = a_document.get("f_typeid");
/*      */ 
/*  480 */     setTypeId(Long.parseLong(sTest));
/*      */ 
/*  483 */     ImageFile iFile = new ImageFile();
/*  484 */     iFile.setPath(a_document.get("f_fLoc"));
/*  485 */     setThumbnailImageFile(iFile);
/*      */ 
/*  488 */     ImageFile iHFile = new ImageFile();
/*  489 */     iHFile.setPath(a_document.get("f_fhomLoc"));
/*  490 */     setHomogenizedImageFile(iHFile);
/*      */ 
/*  493 */     ImageFile iPreviewFile = new ImageFile();
/*  494 */     iPreviewFile.setPath(a_document.get("f_fprevLoc"));
/*  495 */     setPreviewImageFile(iPreviewFile);
/*      */ 
/*  498 */     if (a_document.get("f_approvalStatus") != null)
/*      */     {
/*  500 */       setApprovalStatus(Integer.parseInt(a_document.get("f_approvalStatus")));
/*      */     }
/*      */ 
/*  504 */     if ((a_document.get("f_unsubmitted") != null) && (a_document.get("f_unsubmitted").equals("1")))
/*      */     {
/*  507 */       setIsUnsubmitted(true);
/*      */     }
/*      */ 
/*  510 */     if ((a_document.get("f_previewRestricted") != null) && (a_document.get("f_previewRestricted").equals("1")))
/*      */     {
/*  513 */       setIsRestricted(true);
/*      */     }
/*      */ 
/*  517 */     if (a_document.get("f_long_price") != null)
/*      */     {
/*  519 */       getPrice().setAmount(Long.parseLong(a_document.get("f_long_price")));
/*      */     }
/*      */ 
/*  523 */     if (a_document.get("f_orientation") != null)
/*      */     {
/*  525 */       setOrientation(Integer.parseInt(a_document.get("f_orientation")));
/*      */     }
/*      */ 
/*  529 */     if (a_document.get("f_long_fileSize") != null)
/*      */     {
/*  531 */       setFileSizeInBytes(Long.parseLong(a_document.get("f_long_fileSize")));
/*      */     }
/*      */ 
/*  535 */     if (a_document.get("f_filename") != null)
/*      */     {
/*  537 */       setFileLocation(a_document.get("f_filename"));
/*      */     }
/*      */ 
/*  541 */     if (a_document.get("f_descCats") != null)
/*      */     {
/*  543 */       setDescriptiveCategoryNamesString(a_document.get("f_descCats"));
/*      */     }
/*      */ 
/*  546 */     if (a_document.get("f_searchName") != null)
/*      */     {
/*  548 */       setSearchName(a_document.get("f_searchName"));
/*      */     }
/*      */ 
/*  551 */     if (a_document.get("f_searchDescription") != null)
/*      */     {
/*  553 */       setSearchDescription(a_document.get("f_searchDescription"));
/*      */     }
/*      */ 
/*  556 */     if (a_document.get("f_apiAttributes") != null)
/*      */     {
/*  558 */       setAPIAttributes(a_document.get("f_apiAttributes"));
/*      */     }
/*      */ 
/*  561 */     if (a_document.get("f_relationshipDescriptions") != null)
/*      */     {
/*  563 */       setRelationshipDescriptionSearchString(a_document.get("f_relationshipDescriptions"));
/*      */     }
/*      */ 
/*  566 */     for (long lDisplayAttributeGroup : AttributeConstants.k_aDisplayAttributeGroups)
/*      */     {
/*  568 */       String sDescriptions = a_document.get("f_summary" + lDisplayAttributeGroup);
/*  569 */       Vector vecDescriptions = AttributeUtil.getDisplayDescriptions(sDescriptions);
/*  570 */       setDescriptions(lDisplayAttributeGroup, vecDescriptions);
/*      */     }
/*      */ 
/*  575 */     if (a_document.get("f_featured") != null)
/*      */     {
/*  577 */       String sPromoted = a_document.get("f_featured");
/*  578 */       if (sPromoted.equals("1"))
/*      */       {
/*  580 */         setIsFeatured(true);
/*      */       }
/*      */     }
/*      */ 
/*  584 */     if ((a_document.get("f_complete") != null) && ("1".equals(a_document.get("f_complete"))))
/*      */     {
/*  586 */       setComplete(true);
/*      */     }
/*      */ 
/*  589 */     if (a_document.get("f_views") != null)
/*      */     {
/*  591 */       setNumViews(Integer.parseInt(a_document.get("f_views")));
/*      */     }
/*      */ 
/*  594 */     if (a_document.get("f_downloads") != null)
/*      */     {
/*  596 */       setNumDownloads(Integer.parseInt(a_document.get("f_downloads")));
/*      */     }
/*      */ 
/*  600 */     if (a_document.get("f_agreementTypeId") != null)
/*      */     {
/*  602 */       setAgreementTypeId(Long.parseLong(a_document.get("f_agreementTypeId")));
/*      */     }
/*      */ 
/*  606 */     if ((a_document.get("f_canBeRated") != null) && ("1".equals(a_document.get("f_canBeRated"))))
/*      */     {
/*  608 */       setCanBeRated(true);
/*      */     }
/*      */ 
/*  611 */     if (a_document.get("f_dbl_averageRating") != null)
/*      */     {
/*  613 */       setAverageRating(Float.parseFloat(a_document.get("f_dbl_averageRating")));
/*      */     }
/*      */ 
/*  617 */     if (StringUtils.isNotEmpty(a_document.get("f_restrictiveCats")))
/*      */     {
/*  619 */       setRestrictiveCatIds(StringUtil.convertToSetOfLongs(a_document.get("f_restrictiveCats"), " "));
/*      */     }
/*      */     else
/*      */     {
/*  627 */       setRestrictiveCatIds(new HashSet());
/*      */     }
/*      */ 
/*  630 */     if (a_document.get("f_entityId") != null)
/*      */     {
/*      */       try
/*      */       {
/*  634 */         setEntity(new AssetEntity(Long.parseLong(a_document.get("f_entityId")), a_document.get("f_entityName")));
/*  635 */         getEntity().setMustHaveParent("1".equals(a_document.get("f_mustHaveParent")));
/*  636 */         getEntity().setShowOnDescendantCategories("1".equals(a_document.get("f_showOnDescendants")));
/*      */       }
/*      */       catch (NumberFormatException pe)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  644 */     if ((a_document.get("f_sensitive") != null) && ("1".equals(a_document.get("f_sensitive"))))
/*      */     {
/*  646 */       setIsSensitive(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getDescriptiveCategoryNamesString()
/*      */   {
/*  654 */     return this.m_sDescriptiveCategoryNames;
/*      */   }
/*      */ 
/*      */   public void setDescriptiveCategoryNamesString(String a_sDescriptiveCategoryNames)
/*      */   {
/*  659 */     this.m_sDescriptiveCategoryNames = a_sDescriptiveCategoryNames;
/*      */   }
/*      */ 
/*      */   protected void addFieldToDoc(Document a_doc, String a_sFieldName, String a_sValue, Field.Store a_store, Field.Index a_index, String[] a_asSortFieldNames)
/*      */   {
/*  686 */     a_doc.add(new Field(a_sFieldName, a_sValue, a_store, a_index));
/*      */ 
/*  692 */     maybeAddSortFieldToDoc(a_doc, a_sFieldName, a_sValue, a_asSortFieldNames);
/*      */   }
/*      */ 
/*      */   private void maybeAddSortFieldToDoc(Document a_doc, String a_sFieldName, String a_sValue, String[] a_asSortFieldNames)
/*      */   {
/*  701 */     if ((a_asSortFieldNames != null) && (StringUtil.arrayContains(a_asSortFieldNames, a_sFieldName)) && ((a_sValue == null) || (!a_sValue.equals("isempty"))))
/*      */     {
/*  706 */       if (a_sValue != null)
/*      */       {
/*  708 */         a_sValue = a_sValue.toLowerCase();
/*      */ 
/*  710 */         if (a_sValue.length() > 50)
/*      */         {
/*  712 */           a_sValue = a_sValue.substring(0, 50);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  717 */       a_doc.add(new Field(a_sFieldName + "_sort", a_sValue, Field.Store.NO, Field.Index.NOT_ANALYZED));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addDateFieldToDoc(Document a_doc, String a_sFieldName, Date a_date, Field.Store a_store, String[] a_asSortFieldNames)
/*      */   {
/*  726 */     addDateFieldToDoc(a_doc, a_sFieldName, a_date, a_asSortFieldNames, a_store, null);
/*      */   }
/*      */ 
/*      */   protected void addDateFieldToDoc(Document a_doc, String a_sFieldName, Date a_date, String[] a_asSortFieldNames, Field.Store a_store, DateFormat a_format)
/*      */   {
/*  731 */     if (a_date != null)
/*      */     {
/*  733 */       if (a_format != null)
/*      */       {
/*  735 */         addFieldToDoc(a_doc, a_sFieldName, a_format.format(a_date), a_store, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */       }
/*      */       else
/*      */       {
/*  739 */         addLongFieldToDoc(a_doc, a_sFieldName, a_date.getTime(), a_store, a_asSortFieldNames);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  744 */       addFieldToDoc(a_doc, a_sFieldName, "isempty", a_store, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addLongFieldToDoc(Document a_doc, String a_sFieldName, long a_lValue, Field.Store a_store, String[] a_asSortFieldNames)
/*      */   {
/*  757 */     a_doc.add(new NumericField(a_sFieldName, a_store, true).setLongValue(a_lValue));
/*      */ 
/*  759 */     maybeAddLongSortFieldToDoc(a_doc, a_sFieldName, a_lValue, a_asSortFieldNames);
/*      */   }
/*      */ 
/*      */   private void maybeAddLongSortFieldToDoc(Document a_doc, String a_sFieldName, long a_lValue, String[] a_asSortFieldNames)
/*      */   {
/*  764 */     if ((a_asSortFieldNames != null) && (StringUtil.arrayContains(a_asSortFieldNames, a_sFieldName)))
/*      */     {
/*  767 */       a_doc.add(new NumericField(a_sFieldName + "_sort", 2147483647, Field.Store.NO, true).setLongValue(a_lValue));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addDoubleFieldToDoc(Document a_doc, String a_sFieldName, double a_dValue, Field.Store a_store, String[] a_asSortFieldNames)
/*      */   {
/*  780 */     a_doc.add(new NumericField(a_sFieldName, a_store, true).setDoubleValue(a_dValue));
/*      */ 
/*  782 */     if ((a_asSortFieldNames != null) && (StringUtil.arrayContains(a_asSortFieldNames, a_sFieldName)))
/*      */     {
/*  785 */       a_doc.add(new NumericField(a_sFieldName + "_sort", 2147483647, a_store, true).setDoubleValue(a_dValue));
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getIndexableDocId()
/*      */   {
/*  803 */     return String.valueOf(getId());
/*      */   }
/*      */ 
/*      */   public String getTypeIdAsString()
/*      */   {
/*  813 */     return String.valueOf(getTypeId());
/*      */   }
/*      */ 
/*      */   public long getTypeId()
/*      */   {
/*  820 */     return this.m_lTypeId;
/*      */   }
/*      */ 
/*      */   public void setTypeId(long a_lTypeId)
/*      */   {
/*  826 */     this.m_lTypeId = a_lTypeId;
/*      */   }
/*      */ 
/*      */   public String getFileLocation()
/*      */   {
/*  832 */     return this.m_sFileLocation;
/*      */   }
/*      */ 
/*      */   public void setFileLocation(String a_sFileLocation)
/*      */   {
/*  838 */     this.m_sFileLocation = a_sFileLocation;
/*      */   }
/*      */ 
/*      */   public boolean getIsDirectDownloadFileType()
/*      */   {
/*  843 */     return AssetBankSettings.getIsDirectDownloadFileType(FileUtil.getSuffix(this.m_sFileLocation));
/*      */   }
/*      */ 
/*      */   public void setDateAdded(Date a_dtAdded)
/*      */   {
/*  849 */     this.m_dtAdded = a_dtAdded;
/*      */   }
/*      */ 
/*      */   public Date getDateAdded()
/*      */   {
/*  855 */     return this.m_dtAdded;
/*      */   }
/*      */ 
/*      */   public FileFormat getFormat()
/*      */   {
/*  861 */     if (this.m_format == null)
/*      */     {
/*  863 */       this.m_format = new FileFormat();
/*      */     }
/*      */ 
/*  866 */     return this.m_format;
/*      */   }
/*      */ 
/*      */   public void setFormat(FileFormat a_sFormat)
/*      */   {
/*  872 */     this.m_format = a_sFormat;
/*      */   }
/*      */ 
/*      */   public boolean equals(Object a_sObj)
/*      */   {
/*  894 */     return (a_sObj != null) && (((LightweightAsset)a_sObj).getId() == getId());
/*      */   }
/*      */ 
/*      */   public String getIdWithPadding()
/*      */   {
/*  909 */     String sCode = getPaddedId(getId());
/*  910 */     return sCode;
/*      */   }
/*      */ 
/*      */   public boolean getHasExpired()
/*      */   {
/*  925 */     if (this.m_dtExpiryDate == null)
/*      */     {
/*  927 */       return false;
/*      */     }
/*      */ 
/*  931 */     return this.m_dtExpiryDate.before(new Date());
/*      */   }
/*      */ 
/*      */   public static String getPaddedId(long a_lId)
/*      */   {
/*  951 */     if (a_lId <= 0L)
/*      */     {
/*  953 */       return "";
/*      */     }
/*      */ 
/*  957 */     String sCode = String.valueOf(a_lId);
/*      */ 
/*  960 */     int iPaddingCount = AssetBankSettings.getAssetCodeCharCount();
/*      */ 
/*  962 */     if ((sCode != null) && (iPaddingCount > 0))
/*      */     {
/*  964 */       String sPadding = "";
/*  965 */       for (int i = sCode.length(); i < iPaddingCount; i++)
/*      */       {
/*  967 */         sPadding = sPadding + AssetBankSettings.getAssetCodePaddingChar();
/*      */       }
/*      */ 
/*  970 */       sCode = sPadding + sCode;
/*      */     }
/*      */ 
/*  973 */     return sCode;
/*      */   }
/*      */ 
/*      */   public long getFileSizeInBytes()
/*      */   {
/*  979 */     return this.m_lFileSizeInBytes;
/*      */   }
/*      */ 
/*      */   public void setFileSizeInBytes(long a_sFileSizeInBytes)
/*      */   {
/*  985 */     this.m_lFileSizeInBytes = a_sFileSizeInBytes;
/*      */   }
/*      */ 
/*      */   public ImageFile getThumbnailImageFile()
/*      */   {
/*  991 */     if (this.m_thumbnailImageFile == null)
/*      */     {
/*  993 */       this.m_thumbnailImageFile = new ImageFile();
/*      */     }
/*      */ 
/*  996 */     return this.m_thumbnailImageFile;
/*      */   }
/*      */ 
/*      */   public void setThumbnailImageFile(ImageFile a_imageFile)
/*      */   {
/* 1002 */     this.m_thumbnailImageFile = a_imageFile;
/*      */   }
/*      */ 
/*      */   public boolean getHasConvertedImages()
/*      */   {
/* 1007 */     return (this.m_previewImageFile != null) && (StringUtils.isNotEmpty(this.m_previewImageFile.getPath()));
/*      */   }
/*      */ 
/*      */   public boolean getHasConvertedThumbnail()
/*      */   {
/* 1012 */     return (this.m_thumbnailImageFile != null) && (StringUtils.isNotEmpty(this.m_thumbnailImageFile.getPath())) && (!this.m_thumbnailImageFile.getPath().startsWith(StoredFileType.SHARED_THUMBNAIL.getDirectoryName()));
/*      */   }
/*      */ 
/*      */   public Date getExpiryDate()
/*      */   {
/* 1019 */     return this.m_dtExpiryDate;
/*      */   }
/*      */ 
/*      */   public void setExpiryDate(Date a_sDtExpiryDate) {
/* 1023 */     this.m_dtExpiryDate = a_sDtExpiryDate;
/*      */   }
/*      */ 
/*      */   public Date getDateLastModified()
/*      */   {
/* 1028 */     return this.m_dtLastModified;
/*      */   }
/*      */ 
/*      */   public void setDateLastModified(Date a_dtLastModified) {
/* 1032 */     this.m_dtLastModified = a_dtLastModified;
/*      */   }
/*      */ 
/*      */   public Date getDateCheckedOut()
/*      */   {
/* 1037 */     return this.m_dtCheckedOut;
/*      */   }
/*      */ 
/*      */   public void setDateCheckedOut(Date a_dtCheckedOut) {
/* 1041 */     this.m_dtCheckedOut = a_dtCheckedOut;
/*      */   }
/*      */ 
/*      */   public boolean getIsUnsubmitted()
/*      */   {
/* 1046 */     return this.m_bUnsubmitted;
/*      */   }
/*      */ 
/*      */   public void setIsUnsubmitted(boolean a_bUnsubmitted) {
/* 1050 */     this.m_bUnsubmitted = a_bUnsubmitted;
/*      */   }
/*      */ 
/*      */   public BrightMoney getPrice()
/*      */   {
/* 1055 */     return this.m_price;
/*      */   }
/*      */ 
/*      */   public void setPrice(BrightMoney a_sPrice) {
/* 1059 */     this.m_price = a_sPrice;
/*      */   }
/*      */ 
/*      */   public ImageFile getHomogenizedImageFile()
/*      */   {
/* 1078 */     if (this.m_homogenizedImageFile == null)
/*      */     {
/* 1080 */       this.m_homogenizedImageFile = new ImageFile();
/*      */     }
/*      */ 
/* 1083 */     return this.m_homogenizedImageFile;
/*      */   }
/*      */ 
/*      */   public void setHomogenizedImageFile(ImageFile a_imageFile)
/*      */   {
/* 1088 */     this.m_homogenizedImageFile = a_imageFile;
/*      */   }
/*      */ 
/*      */   public int getOrientation()
/*      */   {
/* 1093 */     return this.m_iOrientation;
/*      */   }
/*      */ 
/*      */   public void setOrientation(int a_iOrientation) {
/* 1097 */     this.m_iOrientation = a_iOrientation;
/*      */   }
/*      */ 
/*      */   public boolean getIsVideo()
/*      */   {
/* 1102 */     return getTypeId() == 3L;
/*      */   }
/*      */ 
/*      */   public boolean getIsAudio()
/*      */   {
/* 1107 */     return getTypeId() == 4L;
/*      */   }
/*      */ 
/*      */   public boolean getIsImage()
/*      */   {
/* 1112 */     return getTypeId() == 2L;
/*      */   }
/*      */ 
/*      */   public int getPosition()
/*      */   {
/* 1117 */     return this.m_iPosition;
/*      */   }
/*      */ 
/*      */   public void setPosition(int a_sPosition) {
/* 1121 */     this.m_iPosition = a_sPosition;
/*      */   }
/*      */ 
/*      */   public void setScore(float a_fScore)
/*      */   {
/* 1138 */     this.m_fSearchScore = a_fScore;
/*      */   }
/*      */ 
/*      */   public float getScore()
/*      */   {
/* 1155 */     return this.m_fSearchScore;
/*      */   }
/*      */ 
/*      */   public boolean getIsRestricted()
/*      */   {
/* 1163 */     return this.m_bIsRestricted;
/*      */   }
/*      */ 
/*      */   public void setIsRestricted(boolean a_bIsRestricted)
/*      */   {
/* 1171 */     this.m_bIsRestricted = a_bIsRestricted;
/*      */   }
/*      */ 
/*      */   public void setDescriptions(long a_lGroupId, Vector<Description> a_vecDescriptions)
/*      */   {
/* 1176 */     this.m_hmDescriptions.put(new Long(a_lGroupId), a_vecDescriptions);
/*      */   }
/*      */ 
/*      */   public void setAllDescriptions(HashMap<Long, Vector<Description>> a_hmDescriptions)
/*      */   {
/* 1181 */     this.m_hmDescriptions = a_hmDescriptions;
/*      */   }
/*      */ 
/*      */   public HashMap<Long, Vector<Description>> getAllDescriptions()
/*      */   {
/* 1186 */     return this.m_hmDescriptions;
/*      */   }
/*      */ 
/*      */   public Vector<Description> getDescriptions(String a_sGroupId)
/*      */   {
/* 1191 */     return getDescriptions(Long.parseLong(a_sGroupId));
/*      */   }
/*      */ 
/*      */   public Vector<Description> getDescriptions(long a_lGroupId)
/*      */   {
/* 1196 */     return (Vector)this.m_hmDescriptions.get(new Long(a_lGroupId));
/*      */   }
/*      */ 
/*      */   public boolean getInAssetBox()
/*      */   {
/* 1202 */     return this.m_bInAssetBox;
/*      */   }
/*      */ 
/*      */   public void setInAssetBox(boolean a_sInAssetBox)
/*      */   {
/* 1208 */     this.m_bInAssetBox = a_sInAssetBox;
/*      */   }
/*      */ 
/*      */   public ImageFile getPreviewImageFile()
/*      */   {
/* 1214 */     if (this.m_previewImageFile == null)
/*      */     {
/* 1216 */       this.m_previewImageFile = new ImageFile();
/*      */     }
/*      */ 
/* 1219 */     return this.m_previewImageFile;
/*      */   }
/*      */ 
/*      */   public void setPreviewImageFile(ImageFile a_imageFile)
/*      */   {
/* 1225 */     this.m_previewImageFile = a_imageFile;
/*      */   }
/*      */ 
/*      */   public boolean isComplete()
/*      */   {
/* 1230 */     return this.m_bIsComplete;
/*      */   }
/*      */ 
/*      */   public void setComplete(boolean isComplete)
/*      */   {
/* 1235 */     this.m_bIsComplete = isComplete;
/*      */   }
/*      */ 
/*      */   public int getNumDownloads()
/*      */   {
/* 1240 */     return this.m_iNumDownloads;
/*      */   }
/*      */ 
/*      */   public void setNumDownloads(int numDownloads)
/*      */   {
/* 1245 */     this.m_iNumDownloads = numDownloads;
/*      */   }
/*      */ 
/*      */   public int getNumViews()
/*      */   {
/* 1250 */     return this.m_iNumViews;
/*      */   }
/*      */ 
/*      */   public void setNumViews(int numViews)
/*      */   {
/* 1255 */     this.m_iNumViews = numViews;
/*      */   }
/*      */ 
/*      */   public Date getDateLastDownloaded()
/*      */   {
/* 1260 */     return this.m_dtLastDownloaded;
/*      */   }
/*      */ 
/*      */   public void setDateLastDownloaded(Date dtLastDownload)
/*      */   {
/* 1265 */     this.m_dtLastDownloaded = dtLastDownload;
/*      */   }
/*      */ 
/*      */   public AssetEntity getEntity()
/*      */   {
/* 1270 */     if (this.m_entity == null)
/*      */     {
/* 1272 */       this.m_entity = new AssetEntity();
/*      */     }
/* 1274 */     return this.m_entity;
/*      */   }
/*      */ 
/*      */   public void setEntity(AssetEntity entity)
/*      */   {
/* 1279 */     this.m_entity = entity;
/*      */   }
/*      */ 
/*      */   public boolean getHasFile()
/*      */   {
/* 1284 */     return StringUtils.isNotEmpty(this.m_sFileLocation);
/*      */   }
/*      */ 
/*      */   public boolean getIsSensitive()
/*      */   {
/* 1289 */     return this.m_bIsSensitive;
/*      */   }
/*      */ 
/*      */   public void setIsSensitive(boolean isSensitive)
/*      */   {
/* 1294 */     this.m_bIsSensitive = isSensitive;
/*      */   }
/*      */ 
/*      */   public void setOriginalFilename(String a_sOriginalFilename)
/*      */   {
/* 1299 */     this.m_sOriginalFilename = a_sOriginalFilename;
/*      */   }
/*      */ 
/*      */   public String getOriginalFilename()
/*      */   {
/* 1304 */     return this.m_sOriginalFilename;
/*      */   }
/*      */ 
/*      */   public String getSearchDescription()
/*      */   {
/* 1309 */     return this.m_sSearchDescription;
/*      */   }
/*      */ 
/*      */   public void setSearchDescription(String searchDescription)
/*      */   {
/* 1314 */     this.m_sSearchDescription = searchDescription;
/*      */   }
/*      */ 
/*      */   public Agreement getAgreement()
/*      */   {
/* 1319 */     if (this.m_agreement == null)
/*      */     {
/* 1321 */       this.m_agreement = new Agreement();
/*      */     }
/* 1323 */     return this.m_agreement;
/*      */   }
/*      */ 
/*      */   public void setAgreement(Agreement a_agreement)
/*      */   {
/* 1328 */     this.m_agreement = a_agreement;
/*      */   }
/*      */ 
/*      */   public boolean getHasAgreement()
/*      */   {
/* 1333 */     return getAgreement().getId() > 0L;
/*      */   }
/*      */ 
/*      */   public Vector<Agreement> getPreviousAgreements()
/*      */   {
/* 1338 */     return this.m_vecPreviousAgreements;
/*      */   }
/*      */ 
/*      */   public int getPreviousAgreementsCount()
/*      */   {
/* 1343 */     if (this.m_vecPreviousAgreements != null)
/*      */     {
/* 1345 */       return this.m_vecPreviousAgreements.size();
/*      */     }
/* 1347 */     return 0;
/*      */   }
/*      */ 
/*      */   public void setPreviousAgreements(Vector<Agreement> a_vecPreviousAgreements)
/*      */   {
/* 1352 */     this.m_vecPreviousAgreements = a_vecPreviousAgreements;
/*      */   }
/*      */ 
/*      */   public long getAgreementTypeId()
/*      */   {
/* 1357 */     return this.m_lAgreementTypeId;
/*      */   }
/*      */ 
/*      */   public void setAgreementTypeId(long a_lAgreementTypeId)
/*      */   {
/* 1362 */     this.m_lAgreementTypeId = a_lAgreementTypeId;
/*      */   }
/*      */ 
/*      */   public void setAPIAttributes(String a_sAPIAttributes)
/*      */   {
/* 1367 */     this.m_sAPIAttributes = a_sAPIAttributes;
/*      */   }
/*      */ 
/*      */   public String getAPIAttributes()
/*      */   {
/* 1372 */     return this.m_sAPIAttributes;
/*      */   }
/*      */ 
/*      */   public void setOverrideRestriction(boolean a_bOverrideRestriction)
/*      */   {
/* 1377 */     this.m_bOverrideRestriction = a_bOverrideRestriction;
/*      */   }
/*      */ 
/*      */   public boolean getOverrideRestriction()
/*      */   {
/* 1382 */     return this.m_bOverrideRestriction;
/*      */   }
/*      */ 
/*      */   public void setWorkflows(Vector<WorkflowInfo> a_vecWorkflows)
/*      */   {
/* 1387 */     this.m_vecWorkflows = a_vecWorkflows;
/*      */   }
/*      */ 
/*      */   public Vector<WorkflowInfo> getWorkflows()
/*      */   {
/* 1392 */     return this.m_vecWorkflows;
/*      */   }
/*      */ 
/*      */   public int getApprovalStatus()
/*      */   {
/* 1397 */     if (this.m_iApprovalStatus <= 0)
/*      */     {
/* 1399 */       this.m_iApprovalStatus = 1;
/* 1400 */       boolean bSomeApproved = false;
/* 1401 */       boolean bSomeUnapproved = false;
/*      */ 
/* 1403 */       Vector vPermCats = getPermissionCategories();
/*      */ 
/* 1406 */       if (vPermCats != null)
/*      */       {
/* 1408 */         for (int i = 0; i < vPermCats.size(); i++)
/*      */         {
/* 1410 */           Category cat = (Category)vPermCats.elementAt(i);
/* 1411 */           if (isApproved(cat))
/*      */           {
/* 1413 */             bSomeApproved = true;
/*      */           }
/*      */           else
/*      */           {
/* 1417 */             bSomeUnapproved = true;
/*      */           }
/*      */         }
/*      */ 
/* 1421 */         if ((bSomeApproved) && (bSomeUnapproved))
/*      */         {
/* 1423 */           this.m_iApprovalStatus = 2;
/*      */         }
/* 1425 */         else if (bSomeApproved)
/*      */         {
/* 1427 */           this.m_iApprovalStatus = 3;
/*      */         }
/*      */       }
/*      */     }
/* 1431 */     return this.m_iApprovalStatus;
/*      */   }
/*      */ 
/*      */   public void setAverageRating(float a_fAverageRating)
/*      */   {
/* 1436 */     this.m_fAverageRating = a_fAverageRating;
/*      */   }
/*      */ 
/*      */   public float getAverageRating()
/*      */   {
/* 1441 */     return this.m_fAverageRating;
/*      */   }
/*      */ 
/*      */   public int getAverageRatingFloor()
/*      */   {
/* 1446 */     float fAverageRating = getAverageRating();
/* 1447 */     return (int)Math.floor(fAverageRating);
/*      */   }
/*      */ 
/*      */   public float getAverageRatingFraction()
/*      */   {
/* 1452 */     float fAverageRating = getAverageRating();
/* 1453 */     int iFloor = getAverageRatingFloor();
/* 1454 */     return fAverageRating - iFloor;
/*      */   }
/*      */ 
/*      */   public void forceApprovalStatusRecalculation()
/*      */   {
/* 1459 */     this.m_iApprovalStatus = -1;
/*      */   }
/*      */ 
/*      */   public boolean getIsPartiallyApproved()
/*      */   {
/* 1464 */     return getApprovalStatus() == 2;
/*      */   }
/*      */ 
/*      */   public boolean getIsFullyApproved()
/*      */   {
/* 1469 */     return getApprovalStatus() == 3;
/*      */   }
/*      */ 
/*      */   public boolean getIsUnnapproved()
/*      */   {
/* 1474 */     return getApprovalStatus() == 1;
/*      */   }
/*      */ 
/*      */   private void setApprovalStatus(int a_iApprovalStatus)
/*      */   {
/* 1479 */     this.m_iApprovalStatus = a_iApprovalStatus;
/*      */   }
/*      */ 
/*      */   public Vector<Category> getPermissionCategories()
/*      */   {
/* 1484 */     return this.m_vecPermissionCategories;
/*      */   }
/*      */ 
/*      */   public Vector<Category> getRestrictivePermissionCategories()
/*      */   {
/* 1493 */     Vector vResult = new Vector();
/*      */ 
/* 1495 */     if (this.m_vecPermissionCategories != null)
/*      */     {
/* 1497 */       for (Category cat : this.m_vecPermissionCategories)
/*      */       {
/* 1499 */         Category restrictiveCat = cat.getClosestRestrictiveAncestor();
/* 1500 */         if (restrictiveCat != null)
/*      */         {
/* 1502 */           vResult.add(restrictiveCat);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1507 */     return vResult;
/*      */   }
/*      */ 
/*      */   public void setPermissionCategories(Vector<Category> a_vecPermissionCategories)
/*      */   {
/* 1512 */     this.m_vecPermissionCategories = a_vecPermissionCategories;
/*      */   }
/*      */ 
/*      */   public void addPermissionCategory(Category a_cat)
/*      */   {
/* 1517 */     if (this.m_vecPermissionCategories == null)
/*      */     {
/* 1519 */       this.m_vecPermissionCategories = new Vector();
/*      */     }
/* 1521 */     this.m_vecPermissionCategories.add(a_cat);
/*      */   }
/*      */ 
/*      */   public boolean isApproved(long a_lCatId)
/*      */   {
/* 1526 */     return this.m_approvalStatuses.isApproved(a_lCatId);
/*      */   }
/*      */ 
/*      */   public boolean isApproved(Category a_permissionCategory)
/*      */   {
/* 1531 */     return isApproved(a_permissionCategory.getId());
/*      */   }
/*      */ 
/*      */   public void setApproved(long a_lCatId, boolean a_bIsApproved)
/*      */   {
/* 1536 */     this.m_approvalStatuses.setApproved(a_lCatId, a_bIsApproved);
/*      */   }
/*      */ 
/*      */   public void setApproved(Category a_permissionCategory, boolean a_bIsApproved)
/*      */   {
/* 1541 */     setApproved(a_permissionCategory.getId(), a_bIsApproved);
/*      */   }
/*      */ 
/*      */   public void setApprovalStatuses(Map<Long, Boolean> a_approvalStatusByCatId)
/*      */   {
/* 1551 */     this.m_approvalStatuses = new AssetApprovalStatuses(a_approvalStatusByCatId);
/*      */   }
/*      */ 
/*      */   public Set<Long> getRestrictiveCatIds()
/*      */   {
/* 1556 */     return this.m_restrictiveCatIds;
/*      */   }
/*      */ 
/*      */   public void setRestrictiveCatIds(Set<Long> a_catIds)
/*      */   {
/* 1561 */     this.m_restrictiveCatIds = a_catIds;
/*      */   }
/*      */ 
/*      */   public String getOriginalFileLocation()
/*      */   {
/* 1566 */     return this.m_sOriginalFileLocation;
/*      */   }
/*      */ 
/*      */   public void setOriginalFileLocation(String a_sOriginalFileLocation)
/*      */   {
/* 1571 */     this.m_sOriginalFileLocation = a_sOriginalFileLocation;
/*      */   }
/*      */ 
/*      */   public void setCanBeRated(boolean a_bCanBeRated)
/*      */   {
/* 1576 */     this.m_bCanBeRated = a_bCanBeRated;
/*      */   }
/*      */ 
/*      */   public boolean getCanBeRated()
/*      */   {
/* 1581 */     return this.m_bCanBeRated;
/*      */   }
/*      */ 
/*      */   public void setRelationshipDescriptions(ArrayList<RelationshipDescriptionEntry> a_alRelDescriptions)
/*      */   {
/* 1586 */     this.m_alRelDescriptions = a_alRelDescriptions;
/*      */   }
/*      */ 
/*      */   public ArrayList<RelationshipDescriptionEntry> getRelationshipDescriptions()
/*      */   {
/* 1596 */     return this.m_alRelDescriptions;
/*      */   }
/*      */ 
/*      */   public void setRelationshipDescriptionSearchString(String a_sRelationshipDescriptionSearchString)
/*      */   {
/* 1601 */     this.m_sRelationshipDescriptionSearchString = a_sRelationshipDescriptionSearchString;
/*      */   }
/*      */ 
/*      */   public String getRelationshipDescriptionSearchString()
/*      */   {
/* 1606 */     return this.m_sRelationshipDescriptionSearchString;
/*      */   }
/*      */ 
/*      */   public String getRelationshipDescriptionFromSearchString(String sIdentifier)
/*      */   {
/* 1611 */     if (StringUtil.stringIsPopulated(this.m_sRelationshipDescriptionSearchString))
/*      */     {
/* 1613 */       String[] aParts = sIdentifier.split(":");
/* 1614 */       long lSourceAssetId = Long.parseLong(aParts[0]);
/* 1615 */       long lRelationshipTypeId = Long.parseLong(aParts[1]);
/*      */ 
/* 1617 */       String[] aDescriptions = this.m_sRelationshipDescriptionSearchString.split(",");
/*      */ 
/* 1619 */       for (String sDescription : aDescriptions)
/*      */       {
/* 1621 */         String[] aValues = sDescription.split(":");
/* 1622 */         long lTempSourceAssetId = Long.parseLong(aValues[0]);
/* 1623 */         long lTempRelationshipTypeId = Long.parseLong(aValues[1]);
/*      */ 
/* 1625 */         if ((lSourceAssetId != lTempSourceAssetId) || (lRelationshipTypeId != lTempRelationshipTypeId)) {
/*      */           continue;
/*      */         }
/* 1628 */         if ((aValues.length < 3) || (!StringUtil.stringIsPopulated(aValues[2])))
/*      */         {
/* 1630 */           return "";
/*      */         }
/* 1632 */         return aValues[2];
/*      */       }
/*      */     }
/*      */ 
/* 1636 */     return "";
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.LightweightAsset
 * JD-Core Version:    0.6.0
 */