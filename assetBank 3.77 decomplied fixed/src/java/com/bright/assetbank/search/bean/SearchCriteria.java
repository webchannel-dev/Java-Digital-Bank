/*      */ package com.bright.assetbank.search.bean;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*      */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*      */ import com.bright.assetbank.search.lucene.CategoryApprovalFilter;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.user.bean.GroupAttributeExclusion;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.search.bean.SearchQuery;
/*      */ import com.bright.framework.search.constant.SearchConstants;
/*      */ import com.bright.framework.search.lucene.FieldDateRange;
/*      */ import com.bright.framework.search.lucene.FieldNumericRange;
/*      */ import com.bright.framework.search.service.IndexManager;
/*      */ import com.bright.framework.util.DateUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import org.apache.avalon.framework.component.ComponentException;
/*      */ import org.apache.avalon.framework.component.ComponentManager;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ public class SearchCriteria extends BaseSearchQuery
/*      */   implements SearchQuery, AssetBankConstants, SearchConstants, AttributeConstants, AssetBankSearchConstants
/*      */ {
/*      */   private static final long serialVersionUID = 898298424684237631L;
/*  112 */   private String m_sAuthor = null;
/*  113 */   private String m_sAddedBy = null;
/*  114 */   private long m_lAddedByUserId = 0L;
/*  115 */   private String m_sFilename = null;
/*  116 */   private String m_sTitle = null;
/*  117 */   private Date m_dateImageAddedLower = null;
/*  118 */   private Date m_dateImageAddedUpper = null;
/*  119 */   private Date m_dateImageModLower = null;
/*  120 */   private Date m_dateImageModUpper = null;
/*  121 */   private Date m_dateImageCreatedLower = null;
/*  122 */   private Date m_dateImageCreatedUpper = null;
/*  123 */   private Date m_dateDownloadedLower = null;
/*  124 */   private Date m_dateDownloadedUpper = null;
/*  125 */   private String m_sAssetIds = null;
/*  126 */   private Vector<Integer> m_vecApprovalStatuses = new Vector();
/*  127 */   private Boolean m_boolIsUnsubmitted = null;
/*  128 */   private Boolean m_boolIsPromoted = null;
/*  129 */   private Boolean m_boolIsFeatured = null;
/*  130 */   private Boolean m_boolIsPreviewRestricted = null;
/*  131 */   private Boolean m_boolHasWorkflow = null;
/*  132 */   private int m_iEmptyFileStatus = -1;
/*  133 */   private Boolean m_boolHasOwnThumbnail = null;
/*      */ 
/*  137 */   private Vector m_vAttributeSearches = null;
/*      */ 
/*  141 */   private boolean m_bAttributeSearchesAreEscaped = false;
/*  142 */   private int m_iOrientation = 0;
/*  143 */   private Vector<FieldDateRange> m_vecDateRanges = null;
/*  144 */   private Vector<FieldNumericRange> m_vecNumberRanges = null;
/*      */ 
/*  147 */   private int[] m_aiTypeIds = null;
/*      */ 
/*  149 */   private long[] m_alPeerAssetIds = null;
/*  150 */   private long[] m_alChildAssetIds = null;
/*  151 */   private long[] m_alParentAssetIds = null;
/*      */ 
/*  153 */   private String m_sOriginalAssetIdsString = null;
/*      */ 
/*  155 */   private HashMap m_hmDatesToRefineUpper = new HashMap();
/*  156 */   private HashMap m_hmDatesToRefineLower = new HashMap();
/*  157 */   private Map<String, Number> m_hmNumbersToRefineUpper = new HashMap();
/*  158 */   private Map<String, Number> m_hmNumbersToRefineLower = new HashMap();
/*      */ 
/*  160 */   private Boolean m_boolIsSensitive = null;
/*      */ 
/*  162 */   private String m_sAgreementText = null;
/*  163 */   private long m_lAgreementType = 0L;
/*  164 */   private double m_dAverageRating = -1.0D;
/*  165 */   private int m_iMinimumVotes = -1;
/*  166 */   private int m_iMaximumVotes = -1;
/*  167 */   private Double m_dFilesizeLower = null;
/*  168 */   private Double m_dFilesizeUpper = null;
/*  169 */   private int m_iMaxAgeInIndex = 0;
/*  170 */   private boolean m_bShowOnDescendants = false;
/*  171 */   private long m_lExtensionTypeToExclude = -1L;
/*      */ 
/*  178 */   private ArrayList<GroupRestriction> m_alGroupRestrictions = new ArrayList();
/*      */ 
/*      */   public void setExtensionTypeToExclude(long a_lExtensionTypeToExclude)
/*      */   {
/*  175 */     this.m_lExtensionTypeToExclude = a_lExtensionTypeToExclude;
/*      */   }
/*      */ 
/*      */   public SearchCriteria()
/*      */   {
/*  185 */     setPriceLower(new BrightMoney());
/*  186 */     setPriceUpper(new BrightMoney());
/*      */   }
/*      */ 
/*      */   public String getLuceneQuery()
/*      */   {
/*  216 */     String sQuery = "";
/*  217 */     String sKeywords = getKeywords();
/*      */ 
/*  220 */     if ((sKeywords != null) && (sKeywords.trim().length() > 0))
/*      */     {
/*  223 */       sQuery = "f_keywords:(" + sKeywords + ")";
/*      */     }
/*      */ 
/*  227 */     if ((this.m_aiTypeIds != null) && (this.m_aiTypeIds.length > 0))
/*      */     {
/*  229 */       if (sQuery.length() > 0)
/*      */       {
/*  231 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  234 */       sQuery = sQuery + "f_typeid:(";
/*      */ 
/*  236 */       for (int i = 0; i < this.m_aiTypeIds.length; i++)
/*      */       {
/*  238 */         sQuery = sQuery + this.m_aiTypeIds[i] + " ";
/*      */       }
/*      */ 
/*  241 */       sQuery = sQuery + ")";
/*      */     }
/*      */ 
/*  245 */     if ((this.m_sAssetIds != null) && (this.m_sAssetIds.trim().length() > 0))
/*      */     {
/*  247 */       if (sQuery.length() > 0)
/*      */       {
/*  249 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  252 */       sQuery = sQuery + "f_id:(" + this.m_sAssetIds + ")";
/*      */     }
/*      */ 
/*  256 */     if (this.m_sAuthor != null)
/*      */     {
/*  258 */       if (sQuery.length() > 0)
/*      */       {
/*  260 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  263 */       sQuery = sQuery + "f_author:(" + this.m_sAuthor + ")";
/*      */     }
/*      */ 
/*  267 */     if (StringUtils.isNotEmpty(this.m_sAddedBy))
/*      */     {
/*  269 */       if (sQuery.length() > 0)
/*      */       {
/*  271 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  274 */       sQuery = sQuery + "f_addedBy:(" + this.m_sAddedBy + ")";
/*      */     }
/*      */ 
/*  278 */     if (this.m_lAddedByUserId > 0L)
/*      */     {
/*  280 */       if (sQuery.length() > 0)
/*      */       {
/*  282 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  285 */       sQuery = sQuery + "f_addedByUserId:(" + this.m_lAddedByUserId + ")";
/*      */     }
/*      */ 
/*  289 */     if (StringUtils.isNotEmpty(this.m_sFilename))
/*      */     {
/*  291 */       if (sQuery.length() > 0)
/*      */       {
/*  293 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  296 */       sQuery = sQuery + "f_filename:(" + this.m_sFilename + ")";
/*      */     }
/*      */ 
/*  300 */     if (StringUtils.isNotEmpty(this.m_sTitle))
/*      */     {
/*  302 */       if (sQuery.length() > 0)
/*      */       {
/*  304 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  307 */       sQuery = sQuery + "f_name:(" + this.m_sTitle + ")";
/*      */     }
/*      */ 
/*  310 */     if ((this.m_iEmptyFileStatus == 1) || (this.m_iEmptyFileStatus == 2))
/*      */     {
/*  313 */       if (sQuery.length() > 0)
/*      */       {
/*  315 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  318 */       if (this.m_iEmptyFileStatus == 1)
/*      */       {
/*  320 */         sQuery = sQuery + "f_emptyFile:(1)";
/*      */       }
/*      */       else
/*      */       {
/*  324 */         sQuery = sQuery + "f_emptyFile:(0)";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  329 */     if (this.m_iOrientation > 0)
/*      */     {
/*  331 */       if (sQuery.length() > 0)
/*      */       {
/*  333 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  336 */       sQuery = sQuery + "f_orientation:(" + this.m_iOrientation + ")";
/*      */     }
/*      */ 
/*  339 */     Vector vecAttributeVals = this.m_vAttributeSearches;
/*  340 */     Vector vecIds = new Vector();
/*      */ 
/*  343 */     vecAttributeVals = addFilterCriteria(vecAttributeVals, vecIds);
/*      */ 
/*  346 */     sQuery = addAttributeSearches(sQuery, vecAttributeVals, this.m_bAttributeSearchesAreEscaped);
/*      */ 
/*  348 */     sQuery = addCategorySearch(sQuery, vecIds);
/*      */ 
/*  351 */     if ((this.m_iMinimumVotes >= 0) || (this.m_iMaximumVotes >= 0))
/*      */     {
/*  353 */       sQuery = addNumericQuery(sQuery, "f_long_feedbackCount", Integer.valueOf(this.m_iMinimumVotes), Integer.valueOf(this.m_iMaximumVotes));
/*      */     }
/*  355 */     if (this.m_dAverageRating >= 0.0D)
/*      */     {
/*  357 */       sQuery = addNumericQuery(sQuery, "f_dbl_averageRating", Double.valueOf(this.m_dAverageRating), Double.valueOf(AssetBankSettings.getMaxRating()));
/*      */     }
/*  359 */     if ((this.m_dFilesizeLower != null) || (this.m_dFilesizeUpper != null))
/*      */     {
/*  361 */       if (this.m_dFilesizeLower != null)
/*      */       {
/*  363 */         this.m_dFilesizeLower = Double.valueOf(this.m_dFilesizeLower.doubleValue() * 1024.0D);
/*      */       }
/*  365 */       if (this.m_dFilesizeUpper != null)
/*      */       {
/*  367 */         this.m_dFilesizeUpper = Double.valueOf(this.m_dFilesizeUpper.doubleValue() * 1024.0D);
/*      */       }
/*  369 */       sQuery = addNumericQuery(sQuery, "f_long_fileSize", this.m_dFilesizeLower, this.m_dFilesizeUpper);
/*      */     }
/*      */ 
/*  373 */     if (this.m_vecNumberRanges != null)
/*      */     {
/*  375 */       for (FieldNumericRange range : this.m_vecNumberRanges)
/*      */       {
/*  377 */         sQuery = addNumericQuery(sQuery, range.getFieldName(), range.getLower(), range.getUpper());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  382 */     if ((this.m_dateImageAddedLower != null) || (this.m_dateImageAddedUpper != null))
/*      */     {
/*  384 */       sQuery = addDateQuery(sQuery, "f_long_added", this.m_dateImageAddedLower, this.m_dateImageAddedUpper);
/*      */     }
/*  386 */     if ((this.m_dateImageModLower != null) || (this.m_dateImageModUpper != null))
/*      */     {
/*  388 */       sQuery = addDateQuery(sQuery, "f_long_modified", this.m_dateImageModLower, this.m_dateImageModUpper);
/*      */     }
/*  390 */     if ((this.m_dateDownloadedLower != null) || (this.m_dateDownloadedUpper != null))
/*      */     {
/*  392 */       sQuery = addDateQuery(sQuery, "f_long_lastDownload", this.m_dateDownloadedLower, this.m_dateDownloadedUpper);
/*      */     }
/*      */ 
/*  396 */     if (this.m_vecDateRanges != null)
/*      */     {
/*  398 */       for (FieldDateRange range : this.m_vecDateRanges)
/*      */       {
/*  400 */         sQuery = addNumericQuery(sQuery, range.getFieldName(), Long.valueOf(range.getLower()), Long.valueOf(range.getUpper()));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  414 */     if (sQuery.length() == 0)
/*      */     {
/*  416 */       sQuery = sQuery + IndexManager.allDocsTerm() + " ";
/*      */     }
/*      */ 
/*  422 */     if ((getApprovalStatuses() != null) && (getApprovalStatuses().size() > 0))
/*      */     {
/*  424 */       String sApprovalQuery = "";
/*      */ 
/*  426 */       for (int i = 0; i < getApprovalStatuses().size(); i++)
/*      */       {
/*  428 */         if (StringUtil.stringIsPopulated(sApprovalQuery))
/*      */         {
/*  430 */           sApprovalQuery = "(" + sApprovalQuery + ") OR ";
/*      */         }
/*  432 */         sApprovalQuery = sApprovalQuery + "f_approvalStatus:(" + ((Integer)getApprovalStatuses().elementAt(i)).intValue() + ")";
/*      */       }
/*  434 */       if (StringUtil.stringIsPopulated(sApprovalQuery))
/*      */       {
/*  436 */         if (sQuery.length() > 0)
/*      */         {
/*  438 */           sQuery = "(" + sQuery + ") AND ";
/*      */         }
/*      */ 
/*  441 */         sQuery = sQuery + "(" + sApprovalQuery + ")";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  446 */     sQuery = addBooleanQuery(sQuery, this.m_boolIsUnsubmitted, "f_unsubmitted");
/*  447 */     sQuery = addBooleanQuery(sQuery, this.m_boolIsPromoted, "f_promoted");
/*  448 */     sQuery = addBooleanQuery(sQuery, this.m_boolIsPreviewRestricted, "f_previewRestricted");
/*  449 */     sQuery = addBooleanQuery(sQuery, this.m_boolIsFeatured, "f_featured");
/*  450 */     sQuery = addBooleanQuery(sQuery, this.m_boolIsSensitive, "f_sensitive");
/*  451 */     sQuery = addBooleanQuery(sQuery, getIsComplete(), "f_complete");
/*  452 */     sQuery = addBooleanQuery(sQuery, this.m_boolHasWorkflow, "f_hasWorkflow");
/*  453 */     sQuery = addBooleanQuery(sQuery, this.m_boolHasOwnThumbnail, "f_ownThumbnail");
/*      */ 
/*  456 */     if (this.m_bShowOnDescendants)
/*      */     {
/*  458 */       sQuery = addBooleanQuery(sQuery, Boolean.valueOf(this.m_bShowOnDescendants), "f_showOnDescendants");
/*      */     }
/*      */ 
/*  461 */     if (this.m_lExtensionTypeToExclude > 0L)
/*      */     {
/*  463 */       if (sQuery.length() > 0)
/*      */       {
/*  465 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*  467 */       if (this.m_lExtensionTypeToExclude == 2L)
/*      */       {
/*  470 */         sQuery = sQuery + "f_extendsCategory:(1 OR \\-99)";
/*      */       }
/*      */       else
/*      */       {
/*  475 */         sQuery = sQuery + "f_extendsCategory:(2 OR \\-99)";
/*      */       }
/*      */     }
/*      */ 
/*  479 */     sQuery = addIdListToQuery(sQuery, getPeerAssetIds(), "f_relatedAssetIds", false);
/*  480 */     sQuery = addIdListToQuery(sQuery, getParentAssetIds(), "f_parentAssetIds", false);
/*      */ 
/*  482 */     if (getBulkUpload() > 0L)
/*      */     {
/*  484 */       if (sQuery.length() > 0)
/*      */       {
/*  486 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*      */ 
/*  489 */       sQuery = sQuery + "f_bulkUploadTime:(" + getBulkUpload() + ")";
/*      */     }
/*      */ 
/*  492 */     sQuery = addPreviousVersionSearch(sQuery);
/*      */ 
/*  495 */     if (!StringUtil.stringIsPopulated(sQuery))
/*      */     {
/*  497 */       sQuery = IndexManager.allDocsTerm();
/*      */     }
/*      */ 
/*  501 */     sQuery = addAttributeExclusions(sQuery);
/*      */ 
/*  503 */     sQuery = addEntitySearch(sQuery);
/*      */ 
/*  505 */     if (this.m_lAgreementType > 0L)
/*      */     {
/*  507 */       if (sQuery.length() > 0)
/*      */       {
/*  509 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*  511 */       sQuery = sQuery + "f_agreementTypeId:(" + this.m_lAgreementType + ")";
/*      */     }
/*      */ 
/*  514 */     if (StringUtils.isNotEmpty(this.m_sAgreementText))
/*      */     {
/*  516 */       if (sQuery.length() > 0)
/*      */       {
/*  518 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*  520 */       sQuery = sQuery + "f_agreement:(" + this.m_sAgreementText + ")";
/*      */     }
/*      */ 
/*  524 */     if (this.m_iMaxAgeInIndex > 0)
/*      */     {
/*  526 */       Calendar cal = Calendar.getInstance();
/*  527 */       cal.add(3, -this.m_iMaxAgeInIndex);
/*  528 */       if (sQuery.length() > 0)
/*      */       {
/*  530 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*  532 */       sQuery = sQuery + "f_long_added:[" + cal.getTimeInMillis() + " TO " + System.currentTimeMillis() + "]";
/*      */     }
/*      */ 
/*  536 */     if (((getPriceLower() != null) && (getPriceLower().getIsFormAmountEntered())) || ((getPriceUpper() != null) && (getPriceUpper().getIsFormAmountEntered())))
/*      */     {
/*  539 */       if (sQuery.length() > 0)
/*      */       {
/*  541 */         sQuery = "(" + sQuery + ") AND ";
/*      */       }
/*  543 */       long lLower = (getPriceLower() != null) && (getPriceLower().getIsFormAmountEntered()) ? getPriceLower().getAmount() : 0L;
/*  544 */       long lUpper = (getPriceUpper() != null) && (getPriceUpper().getIsFormAmountEntered()) ? getPriceUpper().getAmount() : 9223372036854775807L;
/*  545 */       sQuery = sQuery + "f_long_price:[" + lLower + " TO " + lUpper + "]";
/*      */     }
/*      */ 
/*  548 */     return sQuery;
/*      */   }
/*      */ 
/*      */   private String addBooleanQuery(String a_sQuery, Boolean a_value, String a_sField)
/*      */   {
/*  553 */     if (a_value != null)
/*      */     {
/*  555 */       if (a_sQuery.length() > 0)
/*      */       {
/*  557 */         a_sQuery = "(" + a_sQuery + ") AND ";
/*      */       }
/*      */ 
/*  560 */       String sValue = null;
/*      */ 
/*  562 */       if (a_value.booleanValue())
/*      */       {
/*  564 */         sValue = "1";
/*      */       }
/*      */       else
/*      */       {
/*  568 */         sValue = "0";
/*      */       }
/*      */ 
/*  571 */       a_sQuery = a_sQuery + a_sField + ":(" + sValue + ")";
/*      */     }
/*  573 */     return a_sQuery;
/*      */   }
/*      */ 
/*      */   private String addNumericQuery(String a_sQuery, String a_sField, Number a_nLower, Number a_nUpper)
/*      */   {
/*  578 */     a_nLower = a_nLower != null ? a_nLower : Double.valueOf(-1.797693134862316E+307D);
/*  579 */     a_nUpper = a_nUpper != null ? a_nUpper : Double.valueOf(1.7976931348623157E+308D);
/*      */ 
/*  581 */     if (a_sQuery.length() > 0)
/*      */     {
/*  583 */       a_sQuery = "(" + a_sQuery + ") AND ";
/*      */     }
/*      */ 
/*  586 */     a_sQuery = a_sQuery + a_sField + ":[" + a_nLower + " TO " + a_nUpper + "]";
/*      */ 
/*  588 */     return a_sQuery;
/*      */   }
/*      */ 
/*      */   private String addDateQuery(String a_sQuery, String a_sField, Date a_dtLower, Date a_dtUpper)
/*      */   {
/*  593 */     long lLower = a_dtLower != null ? a_dtLower.getTime() : 0L;
/*  594 */     long lUpper = a_dtUpper != null ? a_dtUpper.getTime() : 9223372036854775807L;
/*      */ 
/*  596 */     return addNumericQuery(a_sQuery, a_sField, Long.valueOf(lLower), Long.valueOf(lUpper));
/*      */   }
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  606 */     return StringUtils.isEmpty(getLuceneQuery());
/*      */   }
/*      */ 
/*      */   protected CategoryApprovalFilter getCategoryApprovalFilter()
/*      */     throws Bn2Exception
/*      */   {
/*  617 */     if ((getApprovalStatuses() != null) && (getApprovalStatuses().contains(new Integer(2))))
/*      */     {
/*  620 */       return buildCategoryApprovalFilter();
/*      */     }
/*  622 */     return null;
/*      */   }
/*      */ 
/*      */   public void addDateAttributeRange(Attribute a_att, Date a_dtLower, Date a_dtUpper)
/*      */   {
/*  643 */     if (this.m_vecDateRanges == null)
/*      */     {
/*  645 */       this.m_vecDateRanges = new Vector();
/*      */     }
/*      */ 
/*  649 */     this.m_hmDatesToRefineLower.put(String.valueOf(a_att.getId()), a_dtLower);
/*  650 */     this.m_hmDatesToRefineUpper.put(String.valueOf(a_att.getId()), a_dtUpper);
/*      */ 
/*  652 */     a_dtLower = DateUtil.getBeginningOfDay(a_dtLower);
/*  653 */     a_dtUpper = DateUtil.getEndOfDay(a_dtUpper);
/*      */ 
/*  655 */     addDateQuery(this.m_vecDateRanges, AttributeUtil.getFlexibleAttributeIndexFieldName(a_att), a_dtLower, a_dtUpper);
/*      */   }
/*      */ 
/*      */   public Date getLowerDateToRefine(String a_sAttributeId)
/*      */   {
/*  661 */     return (Date)this.m_hmDatesToRefineLower.get(a_sAttributeId);
/*      */   }
/*      */ 
/*      */   public Date getUpperDateToRefine(String a_sAttributeId)
/*      */   {
/*  666 */     return (Date)this.m_hmDatesToRefineUpper.get(a_sAttributeId);
/*      */   }
/*      */ 
/*      */   public void addNumericFlexibleAttributeRange(Attribute a_att, Number a_nLower, Number a_nUpper)
/*      */   {
/*  680 */     if (this.m_vecNumberRanges == null)
/*      */     {
/*  682 */       this.m_vecNumberRanges = new Vector();
/*      */     }
/*      */ 
/*  686 */     this.m_hmNumbersToRefineLower.put(String.valueOf(a_att.getId()), a_nLower);
/*  687 */     this.m_hmNumbersToRefineUpper.put(String.valueOf(a_att.getId()), a_nUpper);
/*      */ 
/*  689 */     String sFieldName = AttributeUtil.getFlexibleAttributeIndexFieldName(a_att);
/*      */ 
/*  691 */     addNumericQuery(this.m_vecNumberRanges, sFieldName, a_nLower, a_nUpper);
/*      */   }
/*      */ 
/*      */   public Map getLowerNumbersToRefine()
/*      */   {
/*  696 */     return this.m_hmNumbersToRefineLower;
/*      */   }
/*      */ 
/*      */   public Map getUpperNumbersToRefine()
/*      */   {
/*  701 */     return this.m_hmNumbersToRefineUpper;
/*      */   }
/*      */ 
/*      */   public boolean hasDateRanges()
/*      */   {
/*  716 */     boolean bHasDateRanges = (this.m_vecDateRanges != null) && (this.m_vecDateRanges.size() > 0);
/*  717 */     return bHasDateRanges;
/*      */   }
/*      */ 
/*      */   public boolean hasNumberRanges()
/*      */   {
/*  722 */     return (this.m_vecNumberRanges != null) && (!this.m_vecNumberRanges.isEmpty());
/*      */   }
/*      */ 
/*      */   public void setAddedBy(String a_sAddedBy)
/*      */   {
/*  727 */     this.m_sAddedBy = a_sAddedBy;
/*      */   }
/*      */ 
/*      */   public String getAddedBy()
/*      */   {
/*  732 */     return this.m_sAddedBy;
/*      */   }
/*      */ 
/*      */   public void setAddedByUserId(long a_lAddedByUserId)
/*      */   {
/*  737 */     this.m_lAddedByUserId = a_lAddedByUserId;
/*      */   }
/*      */ 
/*      */   public long getAddedByUserId()
/*      */   {
/*  742 */     return this.m_lAddedByUserId;
/*      */   }
/*      */ 
/*      */   public void setDateImageAddedLower(Date a_dateImageAddedLower)
/*      */   {
/*  747 */     this.m_dateImageAddedLower = a_dateImageAddedLower;
/*      */   }
/*      */ 
/*      */   public Date getDateImageAddedLower()
/*      */   {
/*  752 */     return this.m_dateImageAddedLower;
/*      */   }
/*      */ 
/*      */   public void setDateImageAddedUpper(Date a_dateImageAddedUpper)
/*      */   {
/*  757 */     this.m_dateImageAddedUpper = a_dateImageAddedUpper;
/*      */   }
/*      */ 
/*      */   public Date getDateImageAddedUpper()
/*      */   {
/*  762 */     return this.m_dateImageAddedUpper;
/*      */   }
/*      */ 
/*      */   public void setDateImageCreatedLower(Date a_dateImageCreatedLower)
/*      */   {
/*  767 */     this.m_dateImageCreatedLower = a_dateImageCreatedLower;
/*      */   }
/*      */ 
/*      */   public Date getDateImageCreatedLower()
/*      */   {
/*  772 */     return this.m_dateImageCreatedLower;
/*      */   }
/*      */ 
/*      */   public void setDateImageCreatedUpper(Date a_dateImageCreatedUpper)
/*      */   {
/*  777 */     this.m_dateImageCreatedUpper = a_dateImageCreatedUpper;
/*      */   }
/*      */ 
/*      */   public Date getDateImageCreatedUpper()
/*      */   {
/*  782 */     return this.m_dateImageCreatedUpper;
/*      */   }
/*      */ 
/*      */   public String getAuthor()
/*      */   {
/*  788 */     return this.m_sAuthor;
/*      */   }
/*      */ 
/*      */   public void setAuthor(String a_sAuthor)
/*      */   {
/*  794 */     this.m_sAuthor = a_sAuthor;
/*      */   }
/*      */ 
/*      */   public Vector getAttributeSearches()
/*      */   {
/*  801 */     return this.m_vAttributeSearches;
/*      */   }
/*      */ 
/*      */   public void setAttributeSearches(Vector a_vAttributeSearches)
/*      */   {
/*  807 */     this.m_vAttributeSearches = a_vAttributeSearches;
/*      */   }
/*      */ 
/*      */   public boolean getAttributeSearchesAreEscaped()
/*      */   {
/*  812 */     return this.m_bAttributeSearchesAreEscaped;
/*      */   }
/*      */ 
/*      */   public void setAttributeSearchesAreEscaped(boolean a_bAttributeSearchesAreEscaped)
/*      */   {
/*  817 */     this.m_bAttributeSearchesAreEscaped = a_bAttributeSearchesAreEscaped;
/*      */   }
/*      */ 
/*      */   public String getAssetId()
/*      */   {
/*  823 */     return this.m_sAssetIds;
/*      */   }
/*      */ 
/*      */   public void setAssetIds(String a_sAssetIds)
/*      */   {
/*  829 */     this.m_sAssetIds = a_sAssetIds;
/*      */   }
/*      */ 
/*      */   public void setAssetIds(Collection<Long> a_assetIds)
/*      */   {
/*  834 */     if (a_assetIds.isEmpty())
/*      */     {
/*  840 */       setAssetIds("NotAnAssetId");
/*      */     }
/*      */     else
/*      */     {
/*  844 */       setAssetIds(StringUtil.convertNumbersToString(a_assetIds, " "));
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<Integer> getApprovalStatuses()
/*      */   {
/*  851 */     return this.m_vecApprovalStatuses;
/*      */   }
/*      */ 
/*      */   public void setApprovalStatuses(Vector a_vecApprovalStatuses)
/*      */   {
/*  856 */     this.m_vecApprovalStatuses = a_vecApprovalStatuses;
/*      */   }
/*      */ 
/*      */   public void addApprovalStatus(int a_iApprovalStatus)
/*      */   {
/*  861 */     if (getApprovalStatuses() == null)
/*      */     {
/*  863 */       setApprovalStatuses(new Vector());
/*      */     }
/*  865 */     getApprovalStatuses().add(new Integer(a_iApprovalStatus));
/*      */   }
/*      */ 
/*      */   public boolean getSelectedApproved()
/*      */   {
/*  870 */     int[] aStatuses = { 3 };
/*  871 */     return getContainsApprovalStatus(aStatuses);
/*      */   }
/*      */ 
/*      */   public boolean getSelectedAwaitingApproval()
/*      */   {
/*  876 */     int[] aStatuses = { 1, 2 };
/*  877 */     return getContainsApprovalStatus(aStatuses);
/*      */   }
/*      */ 
/*      */   private boolean getContainsApprovalStatus(int[] a_iApprovalStatusId)
/*      */   {
/*  882 */     boolean bFound = false;
/*  883 */     boolean bFirst = true;
/*      */     try
/*      */     {
/*  887 */       if ((getApprovalStatuses() != null) && (getApprovalStatuses().size() > 0) && (a_iApprovalStatusId != null) && (a_iApprovalStatusId.length > 0))
/*      */       {
/*  890 */         for (int x = 0; x < a_iApprovalStatusId.length; x++)
/*      */         {
/*  892 */           int iAppStatus = a_iApprovalStatusId[x];
/*  893 */           boolean bCheck = false;
/*  894 */           for (int i = 0; i < getApprovalStatuses().size(); i++)
/*      */           {
/*  896 */             int iApprovalStatus = ((Integer)getApprovalStatuses().elementAt(i)).intValue();
/*  897 */             bCheck = (bCheck) || (iApprovalStatus == iAppStatus);
/*      */           }
/*      */ 
/*  900 */           if (bFirst)
/*      */           {
/*  902 */             bFound = bCheck;
/*  903 */             bFirst = false;
/*      */           }
/*      */           else
/*      */           {
/*  907 */             bFound = (bFound) && (bCheck);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */ 
/*  916 */     return bFound;
/*      */   }
/*      */ 
/*      */   public Boolean getIsUnsubmitted()
/*      */   {
/*  921 */     return this.m_boolIsUnsubmitted;
/*      */   }
/*      */ 
/*      */   public void setIsUnsubmitted(Boolean a_boolIsUnsubmitted)
/*      */   {
/*  926 */     this.m_boolIsUnsubmitted = a_boolIsUnsubmitted;
/*      */   }
/*      */ 
/*      */   public void setIsPromoted(Boolean a_boolIsPromoted)
/*      */   {
/*  931 */     this.m_boolIsPromoted = a_boolIsPromoted;
/*      */   }
/*      */ 
/*      */   public Boolean getIsPromoted()
/*      */   {
/*  936 */     return this.m_boolIsPromoted;
/*      */   }
/*      */ 
/*      */   public void setEmptyFileStatus(int a_iEmptyFileStatus)
/*      */   {
/*  941 */     this.m_iEmptyFileStatus = a_iEmptyFileStatus;
/*      */   }
/*      */ 
/*      */   public int getEmptyFileStatus()
/*      */   {
/*  946 */     return this.m_iEmptyFileStatus;
/*      */   }
/*      */ 
/*      */   public void setIsFeatured(Boolean a_boolIsFeatured)
/*      */   {
/*  951 */     this.m_boolIsFeatured = a_boolIsFeatured;
/*      */   }
/*      */ 
/*      */   public Boolean getIsFeatured()
/*      */   {
/*  956 */     return this.m_boolIsFeatured;
/*      */   }
/*      */ 
/*      */   public void setIsPreviewRestricted(Boolean a_boolIsPreviewRestricted)
/*      */   {
/*  961 */     this.m_boolIsPreviewRestricted = a_boolIsPreviewRestricted;
/*      */   }
/*      */ 
/*      */   public Boolean getIsPreviewRestricted()
/*      */   {
/*  966 */     return this.m_boolIsPreviewRestricted;
/*      */   }
/*      */ 
/*      */   public int getOrientation()
/*      */   {
/*  972 */     return this.m_iOrientation;
/*      */   }
/*      */ 
/*      */   public void setOrientation(int a_iOrientation)
/*      */   {
/*  977 */     this.m_iOrientation = a_iOrientation;
/*      */   }
/*      */ 
/*      */   public String getFilename()
/*      */   {
/*  982 */     return this.m_sFilename;
/*      */   }
/*      */ 
/*      */   public void setFilename(String a_sFilename) {
/*  986 */     this.m_sFilename = a_sFilename;
/*      */   }
/*      */ 
/*      */   public Date getDateImageModLower() {
/*  990 */     return this.m_dateImageModLower;
/*      */   }
/*      */ 
/*      */   public void setDateImageModLower(Date a_sDateImageModLower) {
/*  994 */     this.m_dateImageModLower = a_sDateImageModLower;
/*      */   }
/*      */ 
/*      */   public Date getDateImageModUpper() {
/*  998 */     return this.m_dateImageModUpper;
/*      */   }
/*      */ 
/*      */   public void setDateImageModUpper(Date a_sDateImageModUpper) {
/* 1002 */     this.m_dateImageModUpper = a_sDateImageModUpper;
/*      */   }
/*      */ 
/*      */   public int[] getTypeIds()
/*      */   {
/* 1007 */     return this.m_aiTypeIds;
/*      */   }
/*      */ 
/*      */   public void setTypeIds(int[] a_sAiTypeIds) {
/* 1011 */     this.m_aiTypeIds = a_sAiTypeIds;
/*      */   }
/*      */ 
/*      */   public String getTitle()
/*      */   {
/* 1016 */     return this.m_sTitle;
/*      */   }
/*      */ 
/*      */   public void setTitle(String a_sTitle)
/*      */   {
/* 1021 */     this.m_sTitle = a_sTitle;
/*      */   }
/*      */ 
/*      */   public long[] getPeerAssetIds()
/*      */   {
/* 1027 */     return this.m_alPeerAssetIds;
/*      */   }
/*      */ 
/*      */   public void setPeerAssetIds(long[] a_alRelatedAssetIds)
/*      */   {
/* 1033 */     this.m_alPeerAssetIds = a_alRelatedAssetIds;
/*      */   }
/*      */ 
/*      */   public void setupPermissions(ABUserProfile a_userProfile)
/*      */   {
/* 1049 */     super.setupPermissions(a_userProfile);
/*      */ 
/* 1052 */     if (!a_userProfile.getIsAdmin())
/*      */     {
/* 1055 */       addApprovalStatus(2);
/* 1056 */       addApprovalStatus(3);
/* 1057 */       if (getAdvancedViewing())
/*      */       {
/* 1060 */         addApprovalStatus(1);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1066 */       setApprovalStatuses(null);
/*      */     }
/*      */ 
/* 1069 */     if ((AssetBankSettings.getHideIncompleteAssets()) && (!a_userProfile.getCanUpdateAssets()))
/*      */     {
/* 1071 */       setIsComplete(Boolean.TRUE);
/*      */     }
/*      */ 
/* 1075 */     setIsUnsubmitted(Boolean.FALSE);
/*      */   }
/*      */ 
/*      */   public void addGroupRestriction(long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 1081 */     addGroupRestriction(a_lGroupId, false);
/*      */   }
/*      */ 
/*      */   public void addImplicitGroupRestriction(long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 1088 */     addGroupRestriction(a_lGroupId, true);
/*      */   }
/*      */ 
/*      */   private void addGroupRestriction(long a_lGroupId, boolean a_bImplicit)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/* 1097 */       GroupRestriction gr = new GroupRestriction(a_lGroupId, a_bImplicit);
/* 1098 */       this.m_alGroupRestrictions.add(gr);
/*      */ 
/* 1101 */       addAttributeExclusions(a_lGroupId);
/* 1102 */       addCategoryRestrictions(a_lGroupId);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1106 */       throw new Bn2Exception(getClass().getSimpleName() + ".addGroupRestriction: Error: ", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public ArrayList<GroupRestriction> getGroupRestrictions()
/*      */   {
/* 1112 */     return this.m_alGroupRestrictions;
/*      */   }
/*      */ 
/*      */   public String getGroupRestrictionString()
/*      */   {
/* 1117 */     String sString = "";
/* 1118 */     String sSeparator = ",";
/* 1119 */     if (this.m_alGroupRestrictions != null)
/*      */     {
/* 1121 */       for (GroupRestriction gr : this.m_alGroupRestrictions)
/*      */       {
/* 1123 */         if (!gr.isImplicit())
/*      */         {
/* 1125 */           sString = sString + gr.getGroupId() + sSeparator;
/*      */         }
/*      */       }
/*      */     }
/* 1129 */     if (StringUtil.stringIsPopulated(sString))
/*      */     {
/* 1131 */       sString = sSeparator + sString;
/*      */     }
/* 1133 */     return sString;
/*      */   }
/*      */ 
/*      */   public boolean getContainsGroupRestriction(String a_sGroupId, boolean a_bExplicitOnly)
/*      */   {
/* 1138 */     long lGroupId = Long.parseLong(a_sGroupId);
/* 1139 */     return getContainsGroupRestriction(lGroupId, a_bExplicitOnly);
/*      */   }
/*      */ 
/*      */   public boolean getContainsGroupRestriction(long a_lGroupId, boolean a_bExplicitOnly)
/*      */   {
/* 1144 */     if (this.m_alGroupRestrictions != null)
/*      */     {
/* 1146 */       for (GroupRestriction gr : this.m_alGroupRestrictions)
/*      */       {
/* 1148 */         if ((gr.getGroupId() == a_lGroupId) && ((!a_bExplicitOnly) || (!gr.isImplicit())))
/*      */         {
/* 1150 */           return true;
/*      */         }
/*      */       }
/*      */     }
/* 1154 */     return false;
/*      */   }
/*      */ 
/*      */   private void addAttributeExclusions(long a_lGroupId)
/*      */     throws Bn2Exception, ComponentException
/*      */   {
/* 1164 */     ABUserManager userManager = (ABUserManager)(ABUserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager");
/* 1165 */     Vector<GroupAttributeExclusion> vecAttributeExclusions = userManager.getAttributeExclusionsForGroup(null, a_lGroupId);
/*      */     AttributeManager attributeManager;
/* 1166 */     if ((vecAttributeExclusions != null) && (vecAttributeExclusions.size() > 0))
/*      */     {
/* 1168 */       if (getAttributeExclusions() == null)
/*      */       {
/* 1170 */         setAttributeExclusions(new Vector());
/*      */       }
/*      */ 
/* 1173 */       attributeManager = (AttributeManager)(AttributeManager)GlobalApplication.getInstance().getComponentManager().lookup("AttributeManager");
/* 1174 */       for (GroupAttributeExclusion gae : vecAttributeExclusions)
/*      */       {
/* 1176 */         AttributeValue value = new AttributeValue();
/* 1177 */         value.setAttribute(attributeManager.getAttribute(null, gae.getItemId()));
/* 1178 */         value.setValue(gae.getValue());
/* 1179 */         getAttributeExclusions().add(value);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getOriginalAssetIdsString() {
/* 1185 */     return this.m_sOriginalAssetIdsString;
/*      */   }
/*      */ 
/*      */   public void setOriginalAssetIdsString(String a_sOriginalAssetIdsString) {
/* 1189 */     this.m_sOriginalAssetIdsString = a_sOriginalAssetIdsString;
/*      */   }
/*      */ 
/*      */   public AttributeValue getAttributeValueForAttribute(String a_sAttributeId)
/*      */   {
/* 1194 */     return AttributeUtil.getAttributeValueForAttribute(getAttributeSearches(), a_sAttributeId);
/*      */   }
/*      */ 
/*      */   public boolean getAttributeHasValue(String a_sIdValuePair)
/*      */   {
/* 1199 */     return AttributeUtil.getAttributeHasValue(a_sIdValuePair, getAttributeSearches());
/*      */   }
/*      */ 
/*      */   public Date getDateDownloadedLower()
/*      */   {
/* 1204 */     return this.m_dateDownloadedLower;
/*      */   }
/*      */ 
/*      */   public void setDateDownloadedLower(Date dateDownloadedLower)
/*      */   {
/* 1209 */     this.m_dateDownloadedLower = dateDownloadedLower;
/*      */   }
/*      */ 
/*      */   public Date getDateDownloadedUpper()
/*      */   {
/* 1214 */     return this.m_dateDownloadedUpper;
/*      */   }
/*      */ 
/*      */   public void setDateDownloadedUpper(Date dateDownloadedUpper)
/*      */   {
/* 1219 */     this.m_dateDownloadedUpper = dateDownloadedUpper;
/*      */   }
/*      */ 
/*      */   public long[] getChildAssetIds()
/*      */   {
/* 1224 */     return this.m_alChildAssetIds;
/*      */   }
/*      */ 
/*      */   public void setChildAssetIds(long[] childAssetIds)
/*      */   {
/* 1229 */     this.m_alChildAssetIds = childAssetIds;
/*      */   }
/*      */ 
/*      */   public long[] getParentAssetIds()
/*      */   {
/* 1234 */     return this.m_alParentAssetIds;
/*      */   }
/*      */ 
/*      */   public void setParentAssetIds(long[] parentAssetIds)
/*      */   {
/* 1239 */     this.m_alParentAssetIds = parentAssetIds;
/*      */   }
/*      */ 
/*      */   public Boolean getIsSensitive()
/*      */   {
/* 1244 */     return this.m_boolIsSensitive;
/*      */   }
/*      */ 
/*      */   public void setIsSensitive(Boolean sensitive)
/*      */   {
/* 1249 */     this.m_boolIsSensitive = sensitive;
/*      */   }
/*      */ 
/*      */   public String getAgreementText()
/*      */   {
/* 1254 */     return this.m_sAgreementText;
/*      */   }
/*      */ 
/*      */   public void setAgreementText(String agreementText)
/*      */   {
/* 1259 */     this.m_sAgreementText = agreementText;
/*      */   }
/*      */ 
/*      */   public long getAgreementType()
/*      */   {
/* 1264 */     return this.m_lAgreementType;
/*      */   }
/*      */ 
/*      */   public void setAgreementType(long agreementType)
/*      */   {
/* 1269 */     this.m_lAgreementType = agreementType;
/*      */   }
/*      */ 
/*      */   public void setAverageRating(double a_dAverageRating)
/*      */   {
/* 1274 */     this.m_dAverageRating = a_dAverageRating;
/*      */   }
/*      */ 
/*      */   public double getAverageRating()
/*      */   {
/* 1279 */     return this.m_dAverageRating;
/*      */   }
/*      */ 
/*      */   public Double getFilesizeLower()
/*      */   {
/* 1284 */     return this.m_dFilesizeLower;
/*      */   }
/*      */ 
/*      */   public void setFilesizeLower(Double a_filesizeLower)
/*      */   {
/* 1290 */     this.m_dFilesizeLower = a_filesizeLower;
/*      */   }
/*      */ 
/*      */   public Double getFilesizeUpper()
/*      */   {
/* 1296 */     return this.m_dFilesizeUpper;
/*      */   }
/*      */ 
/*      */   public void setFilesizeUpper(Double a_filesizeUpper)
/*      */   {
/* 1302 */     this.m_dFilesizeUpper = a_filesizeUpper;
/*      */   }
/*      */ 
/*      */   public Boolean getHasWorkflow()
/*      */   {
/* 1308 */     return this.m_boolHasWorkflow;
/*      */   }
/*      */ 
/*      */   public void setHasWorkflow(Boolean a_sBoolHasWorkflow)
/*      */   {
/* 1314 */     this.m_boolHasWorkflow = a_sBoolHasWorkflow;
/*      */   }
/*      */ 
/*      */   public void setMaximumVotes(int a_iMaximumVotes)
/*      */   {
/* 1319 */     this.m_iMaximumVotes = a_iMaximumVotes;
/*      */   }
/*      */ 
/*      */   public int getMaximumVotes()
/*      */   {
/* 1324 */     return this.m_iMaximumVotes;
/*      */   }
/*      */ 
/*      */   public void setMinimumVotes(int a_iMinimumVotes)
/*      */   {
/* 1329 */     this.m_iMinimumVotes = a_iMinimumVotes;
/*      */   }
/*      */ 
/*      */   public int getMinimumVotes()
/*      */   {
/* 1334 */     return this.m_iMinimumVotes;
/*      */   }
/*      */ 
/*      */   public int getMaxAgeInIndex()
/*      */   {
/* 1339 */     return this.m_iMaxAgeInIndex;
/*      */   }
/*      */ 
/*      */   public void setMaxAgeInIndex(int a_bMaxAgeInIndex)
/*      */   {
/* 1344 */     this.m_iMaxAgeInIndex = a_bMaxAgeInIndex;
/*      */   }
/*      */ 
/*      */   public Boolean getHasOwnThumbnail()
/*      */   {
/* 1349 */     return this.m_boolHasOwnThumbnail;
/*      */   }
/*      */ 
/*      */   public void setHasOwnThumbnail(Boolean a_bHasOwnThumbnail)
/*      */   {
/* 1354 */     this.m_boolHasOwnThumbnail = a_bHasOwnThumbnail;
/*      */   }
/*      */ 
/*      */   public void setShowOnDescendants(boolean a_bShowOnDescendants)
/*      */   {
/* 1359 */     this.m_bShowOnDescendants = a_bShowOnDescendants;
/*      */   }
/*      */ 
/*      */   public boolean getShowOnDescendants()
/*      */   {
/* 1364 */     return this.m_bShowOnDescendants;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.bean.SearchCriteria
 * JD-Core Version:    0.6.0
 */