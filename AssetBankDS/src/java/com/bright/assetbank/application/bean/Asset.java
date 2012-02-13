/*      */ package com.bright.assetbank.application.bean;
/*      */ 
/*      */ import com.bright.assetbank.agreements.bean.Agreement;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.bean.DisplayAttribute;
/*      */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*      */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*      */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.feedback.bean.AssetFeedback;
/*      */ import com.bright.assetbank.plugin.bean.ABExtensibleBean;
/*      */ import com.bright.assetbank.plugin.bean.ABExtensibleBeanHelper;
/*      */ import com.bright.assetbank.search.constant.AssetIndexConstants;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.bean.Item;
/*      */ import com.bright.framework.category.util.CategoryApprovalPredicate;
/*      */ import com.bright.framework.category.util.CategoryMatchesWorkflowPredicate;
/*      */ import com.bright.framework.category.util.CategoryUtil;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.BrightDateTime;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.ObjectUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.lucene.document.Document;
/*      */ import org.apache.lucene.document.Field;
/*      */ import org.apache.lucene.document.Field.Store;
/*      */ 
/*      */ public class Asset extends LightweightAsset
/*      */   implements Item, ABExtensibleBean
/*      */ {
/*  148 */   private ABExtensibleBeanHelper m_extensibleBeanHelper = new ABExtensibleBeanHelper();
/*      */ 
/*  151 */   private String m_sCode = null;
/*  152 */   private String m_sAuthor = null;
/*  153 */   private ABUser m_addedByUser = null;
/*  154 */   private Vector<AttributeValue> m_vAttributeValues = null;
/*      */ 
/*  156 */   private ABUser m_lastModifiedByUser = null;
/*  157 */   private ABUser m_checkedOutByUser = null;
/*      */ 
/*  159 */   private boolean m_bIsPromoted = false;
/*      */ 
/*  161 */   private boolean m_bIsBrandTemplate = false;
/*      */ 
/*  163 */   private Vector<Category> m_vecDescriptiveCategories = null;
/*      */ 
/*  166 */   private Vector m_featuredInBrandsList = null;
/*      */ 
/*  169 */   private String m_lImportedAssetId = null;
/*      */ 
/*  171 */   private String m_sParentAssetIdsAsString = null;
/*  172 */   private String m_sPeerAssetIdsAsString = null;
/*  173 */   private String m_sChildAssetIdsAsString = null;
/*      */ 
/*  176 */   private boolean m_bSynchronised = false;
/*      */ 
/*  178 */   private Date m_dtBulkUploadTimestamp = null;
/*      */ 
/*  181 */   private HashMap<Long, Vector<DisplayAttribute>> m_hmDisplayAttributes = new HashMap();
/*      */ 
/*  183 */   private String m_sSelectedName = null;
/*  184 */   private String m_sSelectedDescription = null;
/*      */ 
/*  186 */   private long m_lCurrentVersionId = 0L;
/*  187 */   private int m_iVersionNumber = 1;
/*      */ 
/*  189 */   private String m_sPreviewClipLocation = null;
/*  190 */   private String m_sEmbeddedPreviewClipLocation = null;
/*  191 */   private int m_iNumPages = 0;
/*      */ 
/*  193 */   private boolean m_bHasRepurposedVersions = false;
/*      */ 
/*  195 */   private long m_lSurrogateAssetId = 0L;
/*  196 */   private boolean m_bHasSubstituteFile = false;
/*      */ 
/*  198 */   private Vector<AssetFeedback> m_vecFeedback = null;
/*  199 */   private float m_fRatingForSearch = -1.0F;
/*  200 */   private int m_iFeedbackCountForSearch = -1;
/*  201 */   private Vector m_vecApprovedAccessLevelsForImport = null;
/*  202 */   private String m_sImportApprovalDirective = null;
/*      */ 
/*  204 */   private boolean m_bCanEmbedFile = false;
/*  205 */   private boolean m_bAdvancedViewing = false;
/*      */ 
/*  208 */   private int m_iAutoRotate = 0;
/*      */ 
/*  210 */   private boolean m_bNotDuplicate = false;
/*  211 */   private ExtendedCategoryInfo m_extendsCategory = new ExtendedCategoryInfo();
/*      */ 
/*      */   public void setExtensionData(String a_sExtensionKey, Serializable a_extensionData)
/*      */   {
/*  215 */     this.m_extensibleBeanHelper.setExtensionData(a_sExtensionKey, a_extensionData);
/*      */   }
/*      */ 
/*      */   public Serializable getExtensionData(String a_sExtensionKey)
/*      */   {
/*  220 */     return this.m_extensibleBeanHelper.getExtensionData(a_sExtensionKey);
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
                
/*  225 */     return getName(LanguageConstants.k_defaultLanguage);
/*      */   }
/*      */ 
/*      */   public String getName(Language a_language)
/*      */   {
/*  232 */     if ((getAttributeValues() != null) && (getAttributeValues().size() > 0))
/*      */     {
/*  234 */       for (int i = 0; i < getAttributeValues().size(); i++)
/*      */       {
/*  236 */         AttributeValue val = (AttributeValue)getAttributeValues().elementAt(i);
/*  237 */         if (!val.getAttribute().getIsNameAttribute())
/*      */           continue;
/*  239 */         this.m_sSelectedName = AttributeUtil.getAttributeValueAsString(this, val, a_language);
/*  240 */         if (StringUtil.stringIsPopulated(this.m_sSelectedName))
/*      */         {
/*      */           break;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  248 */     if (!StringUtil.stringIsPopulated(this.m_sSelectedName))
/*      */     {
/*  251 */       this.m_sSelectedName = String.valueOf(getId());
/*      */     }
/*  253 */     return this.m_sSelectedName;
/*      */   }
/*      */ 
/*      */   public String getDescription(Language a_language)
/*      */   {
/*  260 */     if ((getAttributeValues() != null) && (getAttributeValues().size() > 0))
/*      */     {
/*  262 */       for (int i = 0; i < getAttributeValues().size(); i++)
/*      */       {
/*  264 */         AttributeValue val = (AttributeValue)getAttributeValues().elementAt(i);
/*  265 */         if (!val.getAttribute().getIsDescriptionAttribute())
/*      */           continue;
/*  267 */         this.m_sSelectedDescription = AttributeUtil.getAttributeValueAsString(this, val, a_language);
/*  268 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  273 */     return this.m_sSelectedDescription;
/*      */   }
/*      */ 
/*      */   public void setName(String a_sSelectedName)
/*      */   {
/*  278 */     this.m_sSelectedName = a_sSelectedName;
/*      */   }
/*      */ 
/*      */   public Asset()
/*      */   {
/*  291 */     this.m_addedByUser = new ABUser();
/*      */   }
/*      */ 
/*      */   public Asset(Asset a_asset)
/*      */   {
/*  306 */     super(a_asset);
/*      */ 
/*  308 */     this.m_extensibleBeanHelper = a_asset.m_extensibleBeanHelper;
/*  309 */     this.m_sCode = a_asset.m_sCode;
/*  310 */     this.m_sAuthor = a_asset.m_sAuthor;
/*  311 */     this.m_addedByUser = a_asset.m_addedByUser;
/*  312 */     this.m_vAttributeValues = a_asset.m_vAttributeValues;
/*  313 */     this.m_lastModifiedByUser = a_asset.m_lastModifiedByUser;
/*  314 */     this.m_lImportedAssetId = a_asset.m_lImportedAssetId;
/*  315 */     this.m_bIsPromoted = a_asset.m_bIsPromoted;
/*  316 */     this.m_bIsBrandTemplate = a_asset.m_bIsBrandTemplate;
/*  317 */     this.m_vecDescriptiveCategories = a_asset.m_vecDescriptiveCategories;
/*  318 */     this.m_sPeerAssetIdsAsString = a_asset.m_sPeerAssetIdsAsString;
/*  319 */     this.m_sChildAssetIdsAsString = a_asset.m_sChildAssetIdsAsString;
/*  320 */     this.m_sParentAssetIdsAsString = a_asset.m_sParentAssetIdsAsString;
/*  321 */     this.m_lImportedAssetId = a_asset.m_lImportedAssetId;
/*  322 */     this.m_featuredInBrandsList = a_asset.m_featuredInBrandsList;
/*  323 */     this.m_bSynchronised = a_asset.m_bSynchronised;
/*  324 */     this.m_dtBulkUploadTimestamp = a_asset.getBulkUploadTimestamp();
/*  325 */     this.m_lCurrentVersionId = a_asset.m_lCurrentVersionId;
/*  326 */     this.m_iVersionNumber = a_asset.m_iVersionNumber;
/*  327 */     this.m_sPreviewClipLocation = a_asset.m_sPreviewClipLocation;
/*  328 */     this.m_sEmbeddedPreviewClipLocation = a_asset.m_sEmbeddedPreviewClipLocation;
/*  329 */     this.m_hmDisplayAttributes = a_asset.m_hmDisplayAttributes;
/*  330 */     this.m_bHasSubstituteFile = a_asset.m_bHasSubstituteFile;
/*  331 */     this.m_vecFeedback = a_asset.getAssetFeedback();
/*  332 */     this.m_iAutoRotate = a_asset.m_iAutoRotate;
/*  333 */     this.m_bAdvancedViewing = a_asset.getAdvancedViewing();
/*  334 */     this.m_extendsCategory = a_asset.getExtendsCategory();
/*      */   }
/*      */ 
/*      */   public Document createLuceneDocument(Object a_params)
/*      */   {
/*  375 */     Document doc = super.createLuceneDocument(a_params);
/*  376 */     CreateLuceneDocumentFromAssetParameters params = (CreateLuceneDocumentFromAssetParameters)a_params;
/*  377 */     String[] a_asSortFieldNames = params.getSortFieldNames();
/*  378 */     Language a_language = params.getLanguage();
/*      */ 
/*  381 */     String sAPIAttributes = AttributeUtil.getAPIAttributeString(getDisplayAttributes(1L), a_language.getCode());
/*  382 */     addFieldToDoc(doc, "f_apiAttributes", sAPIAttributes, Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  384 */     addFieldToDoc(doc, "f_code", getCode() != null ? getCode() : String.valueOf(getId()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  386 */     if (StringUtil.stringIsPopulated(getOriginalFilename()))
/*      */     {
/*  388 */       addFieldToDoc(doc, "f_filename", getOriginalFilename(), Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*  389 */       addFieldToDoc(doc, "f_emptyFile", "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */     else
/*      */     {
/*  393 */       addFieldToDoc(doc, "f_emptyFile", "1", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  396 */     addFieldToDoc(doc, "f_author", this.m_sAuthor == null ? "" : this.m_sAuthor, Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  399 */     addFieldToDoc(doc, "f_addedBy", this.m_addedByUser.getFullName() + " " + this.m_addedByUser.getUsername(), Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*  400 */     addFieldToDoc(doc, "f_addedByUserId", String.valueOf(this.m_addedByUser.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  402 */     if (this.m_lastModifiedByUser != null)
/*      */     {
/*  404 */       addFieldToDoc(doc, "f_modifiedBy", this.m_lastModifiedByUser.getFullName() + " " + this.m_lastModifiedByUser.getUsername(), Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*  405 */       addFieldToDoc(doc, "f_modifiedByUserId", String.valueOf(this.m_lastModifiedByUser.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  409 */     for (long lDisplayAttributeGroup : AttributeConstants.k_aDisplayAttributeGroups)
/*      */     {
/*  411 */       String sSummary = AttributeUtil.getDisplayAttributeDescriptions(getDisplayAttributes(lDisplayAttributeGroup), a_language.getCode());
/*  412 */       addFieldToDoc(doc, "f_summary" + lDisplayAttributeGroup, sSummary, Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */     }
/*  414 */     addFieldToDoc(doc, "f_searchName", getName(a_language), Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*  415 */     addFieldToDoc(doc, "f_extendsCategory", String.valueOf(getExtendsCategory().getCategoryTypeId()), Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  417 */     String sDescription = getDescription(a_language);
/*  418 */     if (StringUtil.stringIsPopulated(sDescription))
/*      */     {
/*  420 */       addFieldToDoc(doc, "f_searchDescription", sDescription, Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  423 */     if (this.m_dtBulkUploadTimestamp != null)
/*      */     {
/*  425 */       addFieldToDoc(doc, "f_bulkUploadTime", String.valueOf(this.m_dtBulkUploadTimestamp.getTime()), Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  429 */     addLongFieldToDoc(doc, "f_long_feedbackCount", getAssetFeedbackCount(), Field.Store.YES, a_asSortFieldNames);
/*      */ 
/*  437 */     String sExplicitCategoryIds = "";
/*      */ 
/*  440 */     Vector <Category> vecExplicitCategories = getCategoriesFromAllTrees();
/*      */ 
/*  443 */     Set restrictiveCatIds = new TreeSet();
/*  444 */     Set restrictiveApprovedCatIds = new TreeSet();
/*      */ 
/*  447 */     if (vecExplicitCategories != null)
/*      */     {
/*  449 */       StringBuilder sbExplicitCategoryIds = new StringBuilder();
/*      */ 
/*  451 */       for (Category category : vecExplicitCategories)
/*      */       {
/*  454 */         sbExplicitCategoryIds.append(category.getId()).append(AssetIndexConstants.k_sIdTokenDelimiter);
/*      */ 
/*  457 */         if (category.getCategoryTypeId() == 2L)
/*      */         {
/*  460 */           Category restrictiveCat = category.getClosestRestrictiveAncestor();
/*      */ 
/*  462 */           if (restrictiveCat != null)
/*      */           {
/*  464 */             restrictiveCatIds.add(Long.valueOf(restrictiveCat.getId()));
/*      */ 
/*  466 */             if (isApproved(restrictiveCat))
/*      */             {
/*  468 */               restrictiveApprovedCatIds.add(Long.valueOf(restrictiveCat.getId()));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  473 */       sExplicitCategoryIds = sbExplicitCategoryIds.toString();
/*      */     }
/*      */ 
/*  477 */     Collection<Category> allCategoriesIncludingAncestors = categoriesAndAncestors(vecExplicitCategories);
/*      */ 
/*  480 */     if ((this.m_vecDescriptiveCategories == null) || (this.m_vecDescriptiveCategories.size() == 0))
/*      */     {
/*  482 */       addFieldToDoc(doc, "f_catsEmpty", "0", Field.Store.NO, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  486 */     String sAllCategoryIds = "";
/*  487 */     for (Category category : allCategoriesIncludingAncestors)
/*      */     {
/*  489 */       sAllCategoryIds = sAllCategoryIds + category.getId() + AssetIndexConstants.k_sIdTokenDelimiter;
/*      */     }
/*      */ 
/*  492 */     String sCategoryNames = "";
/*  493 */     for (String sCategoryName : getDescriptiveAndPermissionCategoryNames(a_language, allCategoriesIncludingAncestors))
/*      */     {
/*  495 */       sCategoryNames = sCategoryNames + sCategoryName + AssetIndexConstants.k_sIdTokenDelimiter;
/*      */     }
/*      */ 
/*  498 */     String sDescriptiveCategoryNames = getDescriptiveCategoryNames(a_language);
/*      */ 
/*  501 */     addFieldToDoc(doc, "f_expCatIds", sExplicitCategoryIds, Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  504 */     addFieldToDoc(doc, "f_descCats", sDescriptiveCategoryNames, Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  507 */     addFieldToDoc(doc, "f_allCatIds", sAllCategoryIds, Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  510 */     String sIds = StringUtil.convertNumbersToString(restrictiveCatIds, AssetIndexConstants.k_sIdTokenDelimiter);
/*  511 */     addFieldToDoc(doc, "f_restrictiveCats", sIds, Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  514 */     if (getApprovalStatus() == 2)
/*      */     {
/*  517 */       sIds = StringUtil.convertNumbersToString(restrictiveApprovedCatIds, AssetIndexConstants.k_sIdTokenDelimiter);
/*  518 */       addFieldToDoc(doc, "f_approvedCats", sIds, Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */     else
/*      */     {
/*  522 */       addFieldToDoc(doc, "f_approvedCats", "0", Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  526 */     addFieldToDoc(doc, "f_advancedViewing", getAdvancedViewing() ? "1" : "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */ 
/*  528 */     String sKeywordSearchableAttributes = null;
/*      */ 
/*  531 */     if (this.m_vAttributeValues != null)
/*      */     {
/*  533 */       sKeywordSearchableAttributes = "";
/*      */ 
/*  535 */       for (int i = 0; i < this.m_vAttributeValues.size(); i++)
/*      */       {
/*  537 */         AttributeValue attValue = (AttributeValue)this.m_vAttributeValues.get(i);
/*  538 */         Attribute attribute = attValue.getAttribute();
/*      */ 
/*  540 */         String sFieldName = AttributeUtil.getFlexibleAttributeIndexFieldName(attribute);
/*      */ 
/*  542 */         if ((attribute.getIsDatepicker()) || (attribute.getIsDateTime()))
/*      */         {
/*  545 */           if (attribute.getIsDatepicker())
/*      */           {
/*  547 */             addDateFieldToDoc(doc, sFieldName, attValue.getDateValue().getDate(), Field.Store.NO, a_asSortFieldNames);
/*      */           } else {
/*  549 */             if (!attribute.getIsDateTime())
/*      */               continue;
/*  551 */             addDateFieldToDoc(doc, sFieldName, attValue.getDateTimeValue().getDate(), Field.Store.NO, a_asSortFieldNames);
/*      */           }
/*      */         }
/*  554 */         else if (attribute.getIsNumeric())
/*      */         {
/*  556 */           if (!StringUtils.isNotEmpty(attValue.getValue()))
/*      */             continue;
/*      */           try
/*      */           {
/*  560 */             BigDecimal dbValue = new BigDecimal(attValue.getValue());
/*  561 */             if (attribute.getMaxDecimalPlaces() == 0)
/*      */             {
/*  563 */               addLongFieldToDoc(doc, "f_long_att_" + attribute.getId(), dbValue.longValue(), Field.Store.NO, a_asSortFieldNames);
/*      */             }
/*      */             else
/*      */             {
/*  567 */               addDoubleFieldToDoc(doc, "f_dbl_att_" + attribute.getId(), dbValue.doubleValue(), Field.Store.NO, a_asSortFieldNames);
/*      */             }
/*      */ 
/*      */           }
/*      */           catch (NumberFormatException e)
/*      */           {
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  580 */           String sAttValue = attValue.getValueForIndex(a_language);
/*  581 */           Field.Index index = Field.Index.ANALYZED;
/*      */ 
/*  584 */           if (attribute.getIsKeywordPicker())
/*      */           {
/*  586 */             sAttValue = AttributeUtil.getKeywordsStringForAttributeValue(attValue, a_language);
/*      */           }
/*  589 */           else if ((StringUtils.isNotEmpty(sAttValue)) && (!a_language.getCode().equals("en")) && (attValue.hasNoTranslation(a_language)))
/*      */           {
/*  591 */             sAttValue = sAttValue + " untranslated";
/*      */           }
/*  593 */           else if (attribute.getIsHyperlink())
/*      */           {
/*  596 */             if (sAttValue != null)
/*      */             {
/*  598 */               sAttValue = sAttValue.replace("==", " ");
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  603 */           if (!StringUtil.stringIsPopulated(sAttValue))
/*      */           {
/*  605 */             sAttValue = "isempty";
/*      */           }
/*      */ 
/*  609 */           if (attribute.getKeywordSearchable())
/*      */           {
/*  611 */             sKeywordSearchableAttributes = sKeywordSearchableAttributes + sAttValue + " ";
/*      */           }
/*      */ 
/*  615 */           if (attribute.getIsList())
/*      */           {
/*  617 */             index = Field.Index.NOT_ANALYZED;
/*      */           }
/*      */ 
/*  621 */           addFieldToDoc(doc, sFieldName, sAttValue, Field.Store.NO, index, a_asSortFieldNames);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  636 */     String sKeywords = "";
/*      */ 
/*  638 */     if (StringUtils.isNotEmpty(sKeywordSearchableAttributes))
/*      */     {
/*  640 */       sKeywords = sKeywordSearchableAttributes;
/*      */     }
/*      */ 
/*  643 */     if ((AssetBankSettings.includeCategoryNamesInKeywordSearch()) && (StringUtils.isNotEmpty(sCategoryNames)))
/*      */     {
/*  646 */       sKeywords = sKeywords + " " + sCategoryNames;
/*      */     }
/*      */ 
/*  649 */     if (StringUtils.isNotEmpty(getOriginalFilename()))
/*      */     {
/*  651 */       sKeywords = sKeywords + " " + getOriginalFilename();
/*      */     }
/*      */ 
/*  654 */     sKeywords = sKeywords + " " + getKeywordsAndSynonyms(a_language);
/*      */ 
/*  657 */     sKeywords = sKeywords + " " + getFileKeywords();
/*      */ 
/*  659 */     addFieldToDoc(doc, "f_keywords", sKeywords, Field.Store.NO, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  662 */     addFieldToDoc(doc, "f_relatedAssetIds", StringUtil.getEmptyStringIfNull(getPeerAssetIdsAsString()), Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  665 */     addFieldToDoc(doc, "f_parentAssetIds", StringUtil.getEmptyStringIfNull(getParentAssetIdsAsString()), Field.Store.YES, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */ 
/*  667 */     if (StringUtils.isNotEmpty(getChildAssetIdsAsString()))
/*      */     {
/*  669 */       addFieldToDoc(doc, "f_hasChildren", "1", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */     else
/*      */     {
/*  673 */       addFieldToDoc(doc, "f_hasChildren", "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  676 */     if (getIsPromoted())
/*      */     {
/*  678 */       addFieldToDoc(doc, "f_promoted", "1", Field.Store.NO, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */     else
/*      */     {
/*  682 */       addFieldToDoc(doc, "f_promoted", "0", Field.Store.NO, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  685 */     if (isMetadataComplete(a_language))
/*      */     {
/*  687 */       addFieldToDoc(doc, "f_complete", "1", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */     else
/*      */     {
/*  691 */       addFieldToDoc(doc, "f_complete", "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  694 */     if (getCurrentVersionId() <= 0L)
/*      */     {
/*  696 */       addFieldToDoc(doc, "f_leafVersion", "1", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */     else
/*      */     {
/*  700 */       addFieldToDoc(doc, "f_leafVersion", "0", Field.Store.YES, Field.Index.NOT_ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  703 */     if (getAverageRating() >= 0.0F)
/*      */     {
/*  705 */       addDoubleFieldToDoc(doc, "f_dbl_averageRating", getAverageRating(), Field.Store.YES, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  710 */     if ((AssetBankSettings.getCanSearchAgreements()) && (getAgreement() != null) && (getAgreement().getId() > 0L))
/*      */     {
/*  712 */       addFieldToDoc(doc, "f_agreement", getAgreement().getTitle() + " " + getAgreement().getBody(), Field.Store.NO, Field.Index.ANALYZED, a_asSortFieldNames);
/*      */     }
/*      */ 
/*  715 */     return doc;
/*      */   }
/*      */ 
/*      */   private Collection<Category> categoriesAndAncestors(Collection<Category> a_categories)
/*      */   {
/*  721 */     HashMap hmAllCategories = new HashMap();
/*  722 */     if (a_categories != null)
/*      */     {
/*  725 */       for (Category category : a_categories)
/*      */       {
/*  728 */         hmAllCategories.put(new Long(category.getId()), category);
/*      */ 
/*  731 */         if (category.getAncestors() != null)
/*      */         {
/*  733 */           for (int j = 0; j < category.getAncestors().size(); j++)
/*      */           {
/*  735 */             Category catAns = (Category)category.getAncestors().get(j);
/*  736 */             hmAllCategories.put(new Long(catAns.getId()), catAns);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  741 */     return hmAllCategories.values();
/*      */   }
/*      */ 
/*      */   private Collection<String> getDescriptiveAndPermissionCategoryNames(Language a_language, Collection<Category> a_categories)
/*      */   {
/*  746 */     List categoryNames = new ArrayList();
/*  747 */     for (Category category : a_categories)
/*      */     {
/*  750 */       if ((category.getCategoryTypeId() == 2L) || (category.getCategoryTypeId() == 1L))
/*      */       {
/*  752 */         categoryNames.add(category.getName(a_language));
/*      */       }
/*      */     }
/*  755 */     return categoryNames;
/*      */   }
/*      */ 
/*      */   public Collection<String> getDescriptiveAndPermissionCategoryNamesIncludingAncestors(Language a_language)
/*      */   {
/*  760 */     return getDescriptiveAndPermissionCategoryNames(a_language, getCategoriesFromAllTreesAndAncestors());
/*      */   }
/*      */ 
/*      */   private Collection<Category> getCategoriesFromAllTreesAndAncestors()
/*      */   {
/*  765 */     return categoriesAndAncestors(getCategoriesFromAllTrees());
/*      */   }
/*      */ 
/*      */   public void populateFromLuceneDocument(Document a_document)
/*      */   {
/*  771 */     super.populateFromLuceneDocument(a_document);
/*      */ 
/*  774 */     if (a_document.get("f_promoted") != null)
/*      */     {
/*  776 */       String sPromoted = a_document.get("f_promoted");
/*      */ 
/*  778 */       if (sPromoted.equals("1"))
/*      */       {
/*  780 */         setIsPromoted(true);
/*      */       }
/*      */     }
/*      */ 
/*  784 */     if (a_document.get("f_long_feedbackCount") != null)
/*      */     {
/*  786 */       String sFeedbackCount = a_document.get("f_long_feedbackCount");
/*  787 */       setFeedbackCountForSearch(Integer.parseInt(sFeedbackCount));
/*      */     }
/*      */   }
/*      */ 
/*      */   private String getDescriptiveCategoryNames(Language a_language)
/*      */   {
/*  795 */     String sCatNames = "";
/*      */ 
/*  797 */     if (this.m_vecDescriptiveCategories != null)
/*      */     {
/*  799 */       for (Category cat : this.m_vecDescriptiveCategories)
/*      */       {
/*  801 */         sCatNames = sCatNames + cat.getName(a_language) + ",";
/*      */       }
/*      */     }
/*  804 */     return sCatNames;
/*      */   }
/*      */ 
/*      */   public String getKeywordsAndSynonyms(Language a_language)
/*      */   {
/*  824 */     StringBuilder sbKeywords = new StringBuilder();
/*  825 */     if (this.m_vAttributeValues != null)
/*      */     {
/*  827 */       Iterator it = this.m_vAttributeValues.iterator();
/*  828 */       String sDelim = AssetBankSettings.getKeywordDelimiter();
/*      */ 
/*  830 */       while (it.hasNext())
/*      */       {
/*  832 */         AttributeValue val = (AttributeValue)it.next();
/*      */ 
/*  834 */         if (val.getAttribute().getIsKeywordPicker())
/*      */         {
/*  836 */           sbKeywords.append(sDelim);
/*  837 */           sbKeywords.append(AttributeUtil.getKeywordsStringForAttributeValue(val, a_language));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  842 */     return sbKeywords.toString();
/*      */   }
/*      */ 
/*      */   private Vector<Category> getCategoriesFromAllTrees()
/*      */   {
/*  854 */     Vector vecAllCategories = new Vector();
/*      */ 
/*  858 */     if (this.m_vecDescriptiveCategories != null)
/*      */     {
/*  860 */       Iterator it = this.m_vecDescriptiveCategories.iterator();
/*  861 */       while (it.hasNext())
/*      */       {
/*  863 */         Category cat = (Category)it.next();
/*  864 */         vecAllCategories.add(cat);
/*      */       }
/*      */     }
/*      */ 
/*  868 */     if (this.m_vecPermissionCategories != null)
/*      */     {
/*  870 */       Iterator it = this.m_vecPermissionCategories.iterator();
/*  871 */       while (it.hasNext())
/*      */       {
/*  873 */         Category cat = (Category)it.next();
/*  874 */         vecAllCategories.add(cat);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  880 */     if (this.m_vAttributeValues != null)
/*      */     {
/*  882 */       for (int i = 0; i < this.m_vAttributeValues.size(); i++)
/*      */       {
/*  884 */         AttributeValue value = (AttributeValue)this.m_vAttributeValues.elementAt(i);
/*      */ 
/*  886 */         if (!value.getAttribute().getIsKeywordPicker())
/*      */           continue;
/*  888 */         if (value.getKeywordCategories() == null)
/*      */           continue;
/*  890 */         Iterator it = value.getKeywordCategories().iterator();
/*      */ 
/*  892 */         while (it.hasNext())
/*      */         {
/*  894 */           Category cat = (Category)it.next();
/*  895 */           vecAllCategories.add(cat);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  902 */     return vecAllCategories;
/*      */   }
/*      */ 
/*      */   public String getCode()
/*      */   {
/*  920 */     if ((this.m_sCode == null) || (this.m_sCode.trim().length() == 0))
/*      */     {
/*  922 */       this.m_sCode = getIdWithPadding();
/*      */     }
/*      */ 
/*  925 */     return this.m_sCode;
/*      */   }
/*      */ 
/*      */   public void setCode(String a_sCode)
/*      */   {
/*  931 */     this.m_sCode = a_sCode;
/*      */   }
/*      */ 
/*      */   public String getFileName()
/*      */   {
/*  936 */     String sFilename = null;
/*  937 */     if ((getOriginalFileLocation() != null) && (getOriginalFileLocation().length() > 0))
/*      */     {
/*  939 */       sFilename = FileUtil.getFilename(getOriginalFileLocation());
/*      */     }
/*      */     else
/*      */     {
/*  943 */       sFilename = FileUtil.getFilename(getFileLocation());
/*      */     }
/*      */ 
/*  946 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public Vector<AttributeValue> getAttributeValues()
/*      */   {
/*  952 */     if (this.m_vAttributeValues == null)
/*      */     {
/*  954 */       this.m_vAttributeValues = new Vector();
/*      */     }
/*      */ 
/*  957 */     return this.m_vAttributeValues;
/*      */   }
/*      */ 
/*      */   public void addAttributeValue(AttributeValue a_attVal)
/*      */   {
/*  962 */     getAttributeValues().add(a_attVal);
/*      */   }
/*      */ 
/*      */   public boolean getIsAttributeValueVisible(String a_sAttributeName)
/*      */   {
/*  968 */     for (int i = 0; i < getAttributeValues().size(); i++)
/*      */     {
/*  970 */       AttributeValue val = (AttributeValue)getAttributeValues().elementAt(i);
/*  971 */       Attribute temp = val.getAttribute();
/*      */ 
/*  973 */       if ((temp.getFieldName() != null) && (temp.getFieldName().equals(a_sAttributeName)))
/*      */       {
/*  975 */         return temp.getIsVisible();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  980 */     return true;
/*      */   }
/*      */ 
/*      */   public void setAttributeValues(Vector<AttributeValue> a_vAttributes)
/*      */   {
/*  987 */     this.m_vAttributeValues = a_vAttributes;
/*      */   }
/*      */ 
/*      */   public AttributeValue getFirstAttributeValue()
/*      */   {
/*  995 */     if ((this.m_vAttributeValues != null) && (this.m_vAttributeValues.size() > 0))
/*      */     {
/*  997 */       for (int i = 0; i < this.m_vAttributeValues.size(); i++)
/*      */       {
/*  999 */         AttributeValue value = (AttributeValue)this.m_vAttributeValues.elementAt(i);
/* 1000 */         if ((!value.getAttribute().getIsGroupHeading()) && (!value.getAttribute().isHidden()) && (value.getAttribute().getSequence() >= 0))
/*      */         {
/* 1004 */           return value;
/*      */         }
/*      */       }
/*      */     }
/* 1008 */     return null;
/*      */   }
/*      */ 
/*      */   public long getFirstAttributeId()
/*      */   {
/* 1013 */     AttributeValue val = getFirstAttributeValue();
/* 1014 */     if (val != null)
/*      */     {
/* 1016 */       return val.getAttribute().getId();
/*      */     }
/* 1018 */     return -1L;
/*      */   }
/*      */ 
/*      */   public AttributeValue getAttributeValue(long a_lAttributeId)
/*      */   {
/* 1036 */     AttributeValue attVal = null;
/*      */ 
/* 1038 */     if (this.m_vAttributeValues != null)
/*      */     {
/* 1040 */       for (int i = 0; i < this.m_vAttributeValues.size(); i++)
/*      */       {
/* 1042 */         if (((AttributeValue)this.m_vAttributeValues.get(i)).getAttribute().getId() != a_lAttributeId)
/*      */           continue;
/* 1044 */         attVal = (AttributeValue)this.m_vAttributeValues.get(i);
/* 1045 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1050 */     return attVal;
/*      */   }
/*      */ 
/*      */   public AttributeValue getMatchAttributeValue()
/*      */   {
/* 1060 */     AttributeValue attVal = null;
/*      */ 
/* 1062 */     long lAttributeId = getEntity().getMatchOnAttributeId();
/* 1063 */     if (lAttributeId > 0L)
/*      */     {
/* 1065 */       attVal = getAttributeValue(lAttributeId);
/*      */     }
/*      */ 
/* 1068 */     return attVal;
/*      */   }
/*      */ 
/*      */   public AttributeValue getStaticAttribute(String a_sStaticFieldName)
/*      */   {
/* 1086 */     for (int i = 0; i < this.m_vAttributeValues.size(); i++)
/*      */     {
/* 1088 */       String sFieldname = ((AttributeValue)this.m_vAttributeValues.get(i)).getAttribute().getFieldName();
/* 1089 */       if ((sFieldname != null) && (sFieldname.equalsIgnoreCase(a_sStaticFieldName)))
/*      */       {
/* 1091 */         return (AttributeValue)this.m_vAttributeValues.get(i);
/*      */       }
/*      */     }
/* 1094 */     return null;
/*      */   }
/*      */ 
/*      */   public Vector getMultipleAttributeValues(int a_lAttributeId)
/*      */   {
/* 1112 */     Vector vecValues = new Vector();
/* 1113 */     AttributeValue attVal = null;
/*      */ 
/* 1116 */     for (int i = 0; i < this.m_vAttributeValues.size(); i++)
/*      */     {
/* 1118 */       if (((AttributeValue)this.m_vAttributeValues.get(i)).getAttribute().getId() != a_lAttributeId)
/*      */         continue;
/* 1120 */       attVal = (AttributeValue)this.m_vAttributeValues.get(i);
/* 1121 */       vecValues.add(attVal);
/*      */     }
/*      */ 
/* 1125 */     return vecValues;
/*      */   }
/*      */ 
/*      */   public void setAttributeValue(long a_lAttributeId, AttributeValue a_object)
/*      */   {
/* 1137 */     if (this.m_vAttributeValues != null)
/*      */     {
/* 1139 */       for (int i = 0; i < this.m_vAttributeValues.size(); i++)
/*      */       {
/* 1141 */         if (((AttributeValue)this.m_vAttributeValues.get(i)).getAttribute().getId() != a_lAttributeId)
/*      */           continue;
/* 1143 */         this.m_vAttributeValues.set(i, a_object);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ABUser getAddedByUser()
/*      */   {
/* 1153 */     return this.m_addedByUser;
/*      */   }
/*      */ 
/*      */   public void setAddedByUser(ABUser a_addedByUser)
/*      */   {
/* 1159 */     this.m_addedByUser = a_addedByUser;
/*      */   }
/*      */ 
/*      */   public String getAuthor()
/*      */   {
/* 1165 */     return this.m_sAuthor;
/*      */   }
/*      */ 
/*      */   public void setAuthor(String a_sAuthor)
/*      */   {
/* 1171 */     this.m_sAuthor = a_sAuthor;
/*      */   }
/*      */ 
/*      */   public ABUser getLastModifiedByUser()
/*      */   {
/* 1176 */     if (this.m_lastModifiedByUser == null)
/*      */     {
/* 1178 */       this.m_lastModifiedByUser = new ABUser();
/*      */     }
/*      */ 
/* 1181 */     return this.m_lastModifiedByUser;
/*      */   }
/*      */ 
/*      */   public void setLastModifiedByUser(ABUser a_lastModifiedByUser) {
/* 1185 */     this.m_lastModifiedByUser = a_lastModifiedByUser;
/*      */   }
/*      */ 
/*      */   public ABUser getCheckedOutByUser()
/*      */   {
/* 1190 */     if (this.m_checkedOutByUser == null)
/*      */     {
/* 1192 */       this.m_checkedOutByUser = new ABUser();
/*      */     }
/*      */ 
/* 1195 */     return this.m_checkedOutByUser;
/*      */   }
/*      */ 
/*      */   public void setCheckedOutByUser(ABUser a_checkedOutByUser) {
/* 1199 */     this.m_checkedOutByUser = a_checkedOutByUser;
/*      */   }
/*      */ 
/*      */   public boolean getFileIsCheckedOut() {
/* 1203 */     return (this.m_checkedOutByUser != null) && (this.m_checkedOutByUser.getId() > 0L);
/*      */   }
/*      */ 
/*      */   public Vector<Category> getDescriptiveCategories()
/*      */   {
/* 1208 */     return this.m_vecDescriptiveCategories;
/*      */   }
/*      */ 
/*      */   public void setDescriptiveCategories(Vector<Category> a_vecDescriptiveCategories)
/*      */   {
/* 1213 */     this.m_vecDescriptiveCategories = a_vecDescriptiveCategories;
/*      */   }
/*      */ 
/*      */   public void addDescriptiveCategories(Category a_cat)
/*      */   {
/* 1218 */     if (this.m_vecDescriptiveCategories == null)
/*      */     {
/* 1220 */       this.m_vecDescriptiveCategories = new Vector();
/*      */     }
/* 1222 */     this.m_vecDescriptiveCategories.add(a_cat);
/*      */   }
/*      */ 
/*      */   public boolean getIsPromoted()
/*      */   {
/* 1227 */     return this.m_bIsPromoted;
/*      */   }
/*      */ 
/*      */   public void setIsPromoted(boolean a_sIsPromoted) {
/* 1231 */     this.m_bIsPromoted = a_sIsPromoted;
/*      */   }
/*      */ 
/*      */   public boolean getIsBrandTemplate()
/*      */   {
/* 1236 */     return this.m_bIsBrandTemplate;
/*      */   }
/*      */ 
/*      */   public void setIsBrandTemplate(boolean a_bIsBrandTemplate)
/*      */   {
/* 1241 */     this.m_bIsBrandTemplate = a_bIsBrandTemplate;
/*      */   }
/*      */ 
/*      */   public Vector getFeaturedInBrandsList()
/*      */   {
/* 1246 */     return this.m_featuredInBrandsList;
/*      */   }
/*      */ 
/*      */   public void setFeaturedInBrandsList(Vector a_sFeaturedInBrandsList)
/*      */   {
/* 1251 */     this.m_featuredInBrandsList = a_sFeaturedInBrandsList;
/*      */   }
/*      */ 
/*      */   public String getImportedAssetId()
/*      */   {
/* 1256 */     return this.m_lImportedAssetId;
/*      */   }
/*      */ 
/*      */   public void setImportedAssetId(String a_sImportedAssetId)
/*      */   {
/* 1261 */     this.m_lImportedAssetId = a_sImportedAssetId;
/*      */   }
/*      */ 
/*      */   public String getPeerAssetIdsAsString()
/*      */   {
/* 1271 */     return this.m_sPeerAssetIdsAsString;
/*      */   }
/*      */ 
/*      */   public void setPeerAssetIdsAsString(String a_sRelatedAssetIds)
/*      */   {
/* 1276 */     this.m_sPeerAssetIdsAsString = a_sRelatedAssetIds;
/*      */   }
/*      */ 
/*      */   public boolean isSynchronised()
/*      */   {
/* 1281 */     return this.m_bSynchronised;
/*      */   }
/*      */ 
/*      */   public void setSynchronised(boolean a_bSynchronised)
/*      */   {
/* 1286 */     this.m_bSynchronised = a_bSynchronised;
/*      */   }
/*      */ 
/*      */   public int getVersionNumber()
/*      */   {
/* 1291 */     return this.m_iVersionNumber;
/*      */   }
/*      */ 
/*      */   public void setVersionNumber(int versionNumber)
/*      */   {
/* 1296 */     this.m_iVersionNumber = versionNumber;
/*      */   }
/*      */ 
/*      */   public long getCurrentVersionId()
/*      */   {
/* 1301 */     return this.m_lCurrentVersionId;
/*      */   }
/*      */ 
/*      */   public void setCurrentVersionId(long currentVersionId)
/*      */   {
/* 1306 */     this.m_lCurrentVersionId = currentVersionId;
/*      */   }
/*      */ 
/*      */   public void setBulkUploadTimestamp(Date a_dtBulkUploadTimestamp)
/*      */   {
/* 1311 */     this.m_dtBulkUploadTimestamp = a_dtBulkUploadTimestamp;
/*      */   }
/*      */ 
/*      */   public Date getBulkUploadTimestamp()
/*      */   {
/* 1316 */     return this.m_dtBulkUploadTimestamp;
/*      */   }
/*      */ 
/*      */   public Vector<DisplayAttribute> getDisplayAttributes(String a_sGroupId)
/*      */   {
/* 1321 */     return getDisplayAttributes(Long.parseLong(a_sGroupId));
/*      */   }
/*      */ 
/*      */   public Vector<DisplayAttribute> getDisplayAttributes(long a_lGroupId)
/*      */   {
/* 1326 */     return (Vector)this.m_hmDisplayAttributes.get(new Long(a_lGroupId));
/*      */   }
/*      */ 
/*      */   public void setDisplayAttributes(long a_lGroupId, Vector<DisplayAttribute> a_vecDisplayAttributes)
/*      */   {
/* 1331 */     this.m_hmDisplayAttributes.put(new Long(a_lGroupId), a_vecDisplayAttributes);
/*      */   }
/*      */ 
/*      */   private boolean isMetadataComplete(Language a_language)
/*      */   {
/* 1339 */     if (this.m_vAttributeValues != null)
/*      */     {
/* 1341 */       Iterator attValues = this.m_vAttributeValues.iterator();
/* 1342 */       while (attValues.hasNext())
/*      */       {
/* 1344 */         AttributeValue value = (AttributeValue)attValues.next();
/* 1345 */         if ((!ObjectUtils.equals(value.getAttribute().getFieldName(), "file")) && (!ObjectUtils.equals(value.getAttribute().getFieldName(), "assetId")) && (!ObjectUtils.equals(value.getAttribute().getFieldName(), "agreements")) && (!ObjectUtils.equals(value.getAttribute().getFieldName(), "categories")) && (value.isIncomplete(a_language)))
/*      */         {
/* 1351 */           return false;
/*      */         }
/*      */ 
/* 1355 */         if (((ObjectUtils.equals(value.getAttribute().getFieldName(), "categories")) && (value.getAttribute().isMandatory())) || (value.getAttribute().isRequiredForCompleteness()))
/*      */         {
/* 1358 */           if ((getDescriptiveCategories() == null) || (getDescriptiveCategories().isEmpty()))
/*      */           {
/* 1360 */             return false;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1365 */         if ((ObjectUtils.equals(value.getAttribute().getFieldName(), "agreements")) && ((value.getAttribute().isMandatory()) || (value.getAttribute().isRequiredForCompleteness())) && (getAgreementTypeId() <= 0L))
/*      */         {
/* 1369 */           return false;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1379 */     return (getAgreementTypeId() != 2L) || (getAgreement().getId() > 0L);
/*      */   }
/*      */ 
/*      */   public boolean getShowAdvancedDownloadOptions()
/*      */   {
/* 1387 */     if ((getDescriptiveCategories() != null) && (getDescriptiveCategories().size() > 0))
/*      */     {
/* 1389 */       for (int i = 0; i < getDescriptiveCategories().size(); i++)
/*      */       {
/* 1391 */         Category tempCat = (Category)getDescriptiveCategories().get(i);
/*      */ 
/* 1393 */         if (tempCat.getAllowAdvancedOptions())
/*      */         {
/* 1395 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1401 */       return true;
/*      */     }
/* 1403 */     return false;
/*      */   }
/*      */ 
/*      */   public String getEncryptedPreviewClipLocation()
/*      */   {
/* 1408 */     if (this.m_sPreviewClipLocation != null)
/*      */     {
/* 1410 */       return FileUtil.encryptFilepath(this.m_sPreviewClipLocation);
/*      */     }
/*      */ 
/* 1413 */     return null;
/*      */   }
/*      */ 
/*      */   public String getEncryptedEmbeddedPreviewClipLocation()
/*      */   {
/* 1418 */     if (this.m_sEmbeddedPreviewClipLocation != null)
/*      */     {
/* 1420 */       return FileUtil.encryptFilepath(this.m_sEmbeddedPreviewClipLocation);
/*      */     }
/*      */ 
/* 1423 */     return null;
/*      */   }
/*      */ 
/*      */   public String getPreviewClipLocation()
/*      */   {
/* 1428 */     return this.m_sPreviewClipLocation;
/*      */   }
/*      */ 
/*      */   public void setPreviewClipLocation(String a_sPreviewClipLocation)
/*      */   {
/* 1433 */     this.m_sPreviewClipLocation = a_sPreviewClipLocation;
/*      */   }
/*      */ 
/*      */   public String getEmbeddedPreviewClipLocation()
/*      */   {
/* 1439 */     return this.m_sEmbeddedPreviewClipLocation;
/*      */   }
/*      */ 
/*      */   public void setEmbeddedPreviewClipLocation(String a_sEmbeddedPreviewClipLocation)
/*      */   {
/* 1445 */     this.m_sEmbeddedPreviewClipLocation = a_sEmbeddedPreviewClipLocation;
/*      */   }
/*      */ 
/*      */   public String getRelatedAssetIdsAsString(long a_lRelationshipType)
/*      */   {
/* 1450 */     if (a_lRelationshipType == 2L)
/*      */     {
/* 1452 */       return getChildAssetIdsAsString();
/*      */     }
/*      */ 
/* 1455 */     if (a_lRelationshipType == 3L)
/*      */     {
/* 1457 */       return getParentAssetIdsAsString();
/*      */     }
/*      */ 
/* 1460 */     if (a_lRelationshipType == 1L)
/*      */     {
/* 1462 */       return getPeerAssetIdsAsString();
/*      */     }
/* 1464 */     return null;
/*      */   }
/*      */ 
/*      */   public String getChildAssetIdsAsString()
/*      */   {
/* 1474 */     return this.m_sChildAssetIdsAsString;
/*      */   }
/*      */ 
/*      */   public void setChildAssetIdsAsString(String childAssetIds)
/*      */   {
/* 1479 */     this.m_sChildAssetIdsAsString = childAssetIds;
/*      */   }
/*      */ 
/*      */   public Collection<Long> getChildAssetIds()
/*      */   {
/* 1489 */     String sChildAssetIds = getChildAssetIdsAsString();
/*      */ 
/* 1491 */     if (sChildAssetIds == null)
/*      */     {
/* 1493 */       return null;
/*      */     }
/*      */ 
/* 1497 */     return StringUtil.convertToListOfLongs(sChildAssetIds, ",");
/*      */   }
/*      */ 
/*      */   public String getParentAssetIdsAsString()
/*      */   {
/* 1508 */     return this.m_sParentAssetIdsAsString;
/*      */   }
/*      */ 
/*      */   public void setParentAssetIdsAsString(String parentAssetIds)
/*      */   {
/* 1513 */     this.m_sParentAssetIdsAsString = parentAssetIds;
/*      */   }
/*      */ 
/*      */   public int getNumPages()
/*      */   {
/* 1518 */     return this.m_iNumPages;
/*      */   }
/*      */ 
/*      */   public void setNumPages(int numLayers)
/*      */   {
/* 1523 */     this.m_iNumPages = numLayers;
/*      */   }
/*      */ 
/*      */   public boolean getHasRepurposedVersions()
/*      */   {
/* 1528 */     return this.m_bHasRepurposedVersions;
/*      */   }
/*      */ 
/*      */   public void setHasRepurposedVersions(boolean hasRepurposedVersions)
/*      */   {
/* 1533 */     this.m_bHasRepurposedVersions = hasRepurposedVersions;
/*      */   }
/*      */ 
/*      */   public long getSurrogateAssetId()
/*      */   {
/* 1538 */     return this.m_lSurrogateAssetId;
/*      */   }
/*      */ 
/*      */   public void setSurrogateAssetId(long surrogateAssetId)
/*      */   {
/* 1543 */     this.m_lSurrogateAssetId = surrogateAssetId;
/*      */   }
/*      */ 
/*      */   public boolean getHasSubstituteFile()
/*      */   {
/* 1548 */     return this.m_bHasSubstituteFile;
/*      */   }
/*      */ 
/*      */   public void setHasSubstituteFile(boolean hasSubstituteFile)
/*      */   {
/* 1553 */     this.m_bHasSubstituteFile = hasSubstituteFile;
/*      */   }
/*      */ 
/*      */   public void setAssetFeedback(Vector<AssetFeedback> a_vecFeedback)
/*      */   {
/* 1558 */     this.m_vecFeedback = a_vecFeedback;
/*      */   }
/*      */ 
/*      */   public Vector<AssetFeedback> getAssetFeedback()
/*      */   {
/* 1563 */     return this.m_vecFeedback;
/*      */   }
/*      */ 
/*      */   public int getAssetFeedbackCount()
/*      */   {
/* 1568 */     if (getAssetFeedback() != null)
/*      */     {
/* 1570 */       return getAssetFeedback().size();
/*      */     }
/*      */ 
/* 1573 */     return 0;
/*      */   }
/*      */ 
/*      */   public void setFeedbackCountForSearch(int a_iFeedbackCountForSearch)
/*      */   {
/* 1578 */     this.m_iFeedbackCountForSearch = a_iFeedbackCountForSearch;
/*      */   }
/*      */ 
/*      */   public int getFeedbackCountForSearch()
/*      */   {
/* 1583 */     return this.m_iFeedbackCountForSearch;
/*      */   }
/*      */ 
/*      */   public float getAverageRating()
/*      */   {
/* 1588 */     if (this.m_fAverageRating > 0.0F)
/*      */     {
/* 1590 */       return this.m_fAverageRating;
/*      */     }
/*      */ 
/* 1593 */     if ((getAssetFeedback() != null) && (getAssetFeedback().size() > 0))
/*      */     {
/* 1595 */       int iRatingTotal = 0;
/* 1596 */       int iCount = 0;
/*      */ 
/* 1598 */       for (int i = 0; i < getAssetFeedback().size(); i++)
/*      */       {
/* 1600 */         AssetFeedback fb = (AssetFeedback)getAssetFeedback().elementAt(i);
/* 1601 */         iRatingTotal += fb.getRating();
/* 1602 */         iCount++;
/*      */       }
/*      */ 
/* 1606 */       float fAverage = iRatingTotal / iCount;
/* 1607 */       DecimalFormat formatter = new DecimalFormat("#,###,###,##0.00");
/* 1608 */       fAverage = new Float(formatter.format(fAverage)).floatValue();
/*      */ 
/* 1610 */       this.m_fAverageRating = fAverage;
/* 1611 */       return this.m_fAverageRating;
/*      */     }
/* 1613 */     return -1.0F;
/*      */   }
/*      */ 
/*      */   public boolean getUserGivenFeedback(String a_sUserId)
/*      */   {
/* 1618 */     long lUserId = new Long(a_sUserId).longValue();
/*      */ 
/* 1620 */     if ((getAssetFeedback() != null) && (getAssetFeedback().size() > 0))
/*      */     {
/* 1622 */       for (int i = 0; i < getAssetFeedback().size(); i++)
/*      */       {
/* 1624 */         AssetFeedback fb = (AssetFeedback)getAssetFeedback().elementAt(i);
/* 1625 */         if (fb.getUserId() == lUserId)
/*      */         {
/* 1627 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1632 */     return false;
/*      */   }
/*      */ 
/*      */   public int getAutoRotate()
/*      */   {
/* 1637 */     return this.m_iAutoRotate;
/*      */   }
/*      */ 
/*      */   public void setAutoRotate(int a_iAutoRotate)
/*      */   {
/* 1642 */     this.m_iAutoRotate = a_iAutoRotate;
/*      */   }
/*      */ 
/*      */   public boolean getCanEmbedFile()
/*      */   {
/* 1647 */     return this.m_bCanEmbedFile;
/*      */   }
/*      */ 
/*      */   public void setCanEmbedFile(boolean a_bAllowEmbedding)
/*      */   {
/* 1652 */     this.m_bCanEmbedFile = a_bAllowEmbedding;
/*      */   }
/*      */ 
/*      */   public boolean getPreviewClipBeingGenerated()
/*      */   {
/* 1659 */     return false;
/*      */   }
/*      */ 
/*      */   public void setImportApprovedAccessLevels(Vector a_vecApprovedAccessLevelsForImport)
/*      */   {
/* 1669 */     this.m_vecApprovedAccessLevelsForImport = a_vecApprovedAccessLevelsForImport;
/*      */   }
/*      */ 
/*      */   public Vector getImportApprovedAccessLevels()
/*      */   {
/* 1674 */     return this.m_vecApprovedAccessLevelsForImport;
/*      */   }
/*      */ 
/*      */   public void setImportApprovalDirective(String a_sImportApprovalDirective)
/*      */   {
/* 1679 */     this.m_sImportApprovalDirective = a_sImportApprovalDirective;
/*      */   }
/*      */ 
/*      */   public String getImportApprovalDirective()
/*      */   {
/* 1684 */     return this.m_sImportApprovalDirective;
/*      */   }
/*      */ 
/*      */   private void addWorkflow(WorkflowInfo a_wf)
/*      */   {
/* 1689 */     if (getWorkflows() == null)
/*      */     {
/* 1691 */       setWorkflows(new Vector());
/*      */     }
/* 1693 */     getWorkflows().add(a_wf);
/*      */   }
/*      */ 
/*      */   public boolean getInWorkflow(String a_sWorkflowName)
/*      */   {
/* 1699 */     if (getWorkflows() != null)
/*      */     {
/* 1701 */       for (int i = 0; i < getWorkflows().size(); i++)
/*      */       {
/* 1703 */         if (a_sWorkflowName.equals(((WorkflowInfo)getWorkflows().elementAt(i)).getWorkflowName()))
/*      */         {
/* 1705 */           return true;
/*      */         }
/*      */       }
/*      */     }
/* 1709 */     return false;
/*      */   }
/*      */ 
/*      */   public void addWorkflowAndSetUnapproved(WorkflowInfo a_wf)
/*      */   {
/* 1721 */     if (!getInWorkflow(a_wf.getWorkflowName()))
/*      */     {
/* 1725 */       Vector vPermCats = getPermissionCategories();
/*      */ 
/* 1727 */       for (int i = 0; i < vPermCats.size(); i++)
/*      */       {
/* 1729 */         Category cat = (Category)vPermCats.elementAt(i);
/* 1730 */         if ((!cat.getWorkflowName().equals(a_wf.getWorkflowName())) && ((StringUtil.stringIsPopulated(cat.getWorkflowName())) || (!a_wf.getWorkflowName().equals("default")))) {
/*      */           continue;
/*      */         }
/* 1733 */         setApproved(cat, false);
/*      */       }
/*      */ 
/* 1736 */       addWorkflow(a_wf);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeWorkflowAndSetApproved(WorkflowInfo a_wf)
/*      */   {
/* 1749 */     removeWorkflowAndChangeApproval(a_wf, true);
/*      */   }
/*      */ 
/*      */   public void removeWorkflowAndSetUnnapproved(WorkflowInfo a_wf)
/*      */   {
/* 1761 */     removeWorkflowAndChangeApproval(a_wf, false);
/*      */   }
/*      */ 
/*      */   private void removeWorkflowAndChangeApproval(WorkflowInfo a_wf, boolean a_bApprovalStatus)
/*      */   {
/* 1767 */     if (getInWorkflow(a_wf.getWorkflowName()))
/*      */     {
/* 1769 */       for (int i = 0; i < getPermissionCategories().size(); i++)
/*      */       {
/* 1771 */         Category cat = (Category)getPermissionCategories().elementAt(i);
/* 1772 */         if ((!cat.getWorkflowName().equals(a_wf.getWorkflowName())) && ((StringUtil.stringIsPopulated(cat.getWorkflowName())) || (!a_wf.getWorkflowName().equals("default")))) {
/*      */           continue;
/*      */         }
/* 1775 */         setApproved(cat, a_bApprovalStatus);
/*      */       }
/*      */ 
/* 1780 */       removeFromWorkflowsByName(a_wf);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeFromWorkflowsByName(WorkflowInfo a_wf)
/*      */   {
/* 1787 */     for (int i = 0; i < getWorkflows().size(); i++)
/*      */     {
/* 1789 */       WorkflowInfo wf = (WorkflowInfo)getWorkflows().elementAt(i);
/* 1790 */       if (!wf.getWorkflowName().equals(a_wf.getWorkflowName()))
/*      */         continue;
/* 1792 */       getWorkflows().remove(wf);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<Category> getPermissionCategoriesForWorkflow(String a_sWFName)
/*      */   {
/* 1799 */     CategoryMatchesWorkflowPredicate catPred = new CategoryMatchesWorkflowPredicate(a_sWFName);
/* 1800 */     return CategoryUtil.getMatchingCategories(getPermissionCategories(), catPred);
/*      */   }
/*      */ 
/*      */   public Vector<Category> getApprovedPermissionCategories()
/*      */   {
/* 1805 */     CategoryApprovalPredicate catPred = new CategoryApprovalPredicate(this, true);
/* 1806 */     return CategoryUtil.getMatchingCategories(getPermissionCategories(), catPred);
/*      */   }
/*      */ 
/*      */   public Vector<Category> getUnapprovedPermissionCategories()
/*      */   {
/* 1811 */     CategoryApprovalPredicate catPred = new CategoryApprovalPredicate(this, false);
/* 1812 */     return CategoryUtil.getMatchingCategories(getPermissionCategories(), catPred);
/*      */   }
/*      */ 
/*      */   public Vector<String> getMissingWorkflows()
/*      */   {
/* 1824 */     Vector vecMissingWorkflows = new Vector();
/* 1825 */     Vector vecUnnapprovedAccessLevels = getUnapprovedPermissionCategories();
/* 1826 */     if ((vecUnnapprovedAccessLevels != null) && (vecUnnapprovedAccessLevels.size() > 0))
/*      */     {
/* 1828 */       for (int i = 0; i < vecUnnapprovedAccessLevels.size(); i++)
/*      */       {
/* 1830 */         Category cat = (Category)vecUnnapprovedAccessLevels.elementAt(i);
/* 1831 */         if ((vecMissingWorkflows.contains(cat.getWorkflowName())) || (getInWorkflow(cat.getWorkflowName())))
/*      */           continue;
/* 1833 */         vecMissingWorkflows.add(cat.getWorkflowName());
/*      */       }
/*      */     }
/*      */ 
/* 1837 */     return vecMissingWorkflows;
/*      */   }
/*      */ 
/*      */   public boolean getIsApprovedForWorkflow(String a_sWFName)
/*      */   {
/* 1848 */     Vector<Category> vecCats = getPermissionCategoriesForWorkflow(a_sWFName);
/* 1849 */     if (vecCats != null)
/*      */     {
/* 1851 */       for (Category cat : vecCats)
/*      */       {
/* 1853 */         if (!isApproved(cat))
/*      */         {
/* 1855 */           return false;
/*      */         }
/*      */       }
/*      */     }
/* 1859 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isNotDuplicate()
/*      */   {
/* 1864 */     return this.m_bNotDuplicate;
/*      */   }
/*      */ 
/*      */   public void setNotDuplicate(boolean a_bNotDuplicate)
/*      */   {
/* 1869 */     this.m_bNotDuplicate = a_bNotDuplicate;
/*      */   }
/*      */ 
/*      */   public void setExtendsCategory(ExtendedCategoryInfo a_category)
/*      */   {
/* 1874 */     this.m_extendsCategory = a_category;
/*      */   }
/*      */ 
/*      */   public ExtendedCategoryInfo getExtendsCategory()
/*      */   {
/* 1879 */     return this.m_extendsCategory;
/*      */   }
/*      */ 
/*      */   public void setAdvancedViewing(boolean a_bAdvancedViewing)
/*      */   {
/* 1884 */     this.m_bAdvancedViewing = a_bAdvancedViewing;
/*      */   }
/*      */ 
/*      */   public boolean getAdvancedViewing()
/*      */   {
/* 1896 */     return this.m_bAdvancedViewing;
/*      */   }
/*      */ 
/*      */   public int getNoOfChildAssets()
/*      */   {
/* 1901 */     if (this.m_sChildAssetIdsAsString != null)
/*      */     {
/* 1903 */       String[] aTemp = this.m_sChildAssetIdsAsString.split(",");
/* 1904 */       int iCount = 0;
/* 1905 */       for (String sTemp : aTemp)
/*      */       {
/* 1907 */         if (!StringUtil.stringIsPopulated(sTemp))
/*      */           continue;
/* 1909 */         iCount++;
/*      */       }
/*      */ 
/* 1912 */       return iCount;
/*      */     }
/* 1914 */     return 0;
/*      */   }
/*      */ 
/*      */   public boolean getHasPeer(long a_lAssetId)
/*      */   {
/* 1919 */     return getHasRelatedAsset(a_lAssetId, this.m_sPeerAssetIdsAsString);
/*      */   }
/*      */ 
/*      */   public boolean getHasChild(long a_lAssetId)
/*      */   {
/* 1924 */     return getHasRelatedAsset(a_lAssetId, this.m_sChildAssetIdsAsString);
/*      */   }
/*      */ 
/*      */   private boolean getHasRelatedAsset(long a_lAssetId, String a_sRelAssetIds)
/*      */   {
/* 1934 */     return (a_sRelAssetIds != null) && ((a_sRelAssetIds.equals(String.valueOf(a_lAssetId))) || (a_sRelAssetIds.contains("," + a_lAssetId + ",")) || (a_sRelAssetIds.startsWith(a_lAssetId + ",")) || (a_sRelAssetIds.endsWith("," + a_lAssetId)));
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.Asset
 * JD-Core Version:    0.6.0
 */