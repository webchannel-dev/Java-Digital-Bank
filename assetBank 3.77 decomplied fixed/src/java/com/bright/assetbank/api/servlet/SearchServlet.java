/*     */ package com.bright.assetbank.api.servlet;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.api.exception.ApiException;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.util.AssetUtil;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ public class SearchServlet extends ApiServlet
/*     */   implements AssetBankConstants, AttributeConstants, LanguageConstants
/*     */ {
/*     */   private static final long serialVersionUID = 7950743214818950611L;
/*     */ 
/*     */   protected void performAction(HttpServletRequest a_request, Writer a_writer)
/*     */     throws ApiException
/*     */   {
/*     */     try
/*     */     {
/*  66 */       SearchCriteria criteria = SearchUtil.getSearchCriteriaFromRequest(a_request);
/*  67 */       if (!searchCriteriaSpecified(criteria))
/*     */       {
/*  69 */         throw new ApiException(getClass().getSimpleName() + ": Unable to perform search, no criteria provided");
/*     */       }
/*     */ 
/*  73 */       String sDefaultOperator = AssetBankSettings.getDefaultSearchOperator();
/*  74 */       if (StringUtil.stringIsPopulated(sDefaultOperator))
/*     */       {
/*  76 */         criteria.setDefaultOperator(sDefaultOperator);
/*     */       }
/*     */ 
/*  80 */       MultiLanguageSearchManager searchManager = (MultiLanguageSearchManager)GlobalApplication.getInstance().getComponentManager().lookup("SearchManager");
/*     */ 
/*  89 */       int iResultCount = searchManager.getHitCount(criteria);
/*     */ 
/*  92 */       criteria.setMaxNoOfResults(iResultCount);
/*     */ 
/*  95 */       String sPageIndex = a_request.getParameter("page");
/*  96 */       String sPageSize = a_request.getParameter("pageSize");
/*  97 */       int iPageIndex = -1;
/*  98 */       int iPageSize = -1;
/*     */ 
/* 100 */       if (StringUtil.stringIsPopulated(sPageIndex))
/*     */       {
/* 102 */         iPageIndex = Integer.parseInt(sPageIndex);
/* 103 */         iPageSize = AssetBankSettings.getApiPageSize();
/*     */ 
/* 105 */         if (StringUtil.stringIsPopulated(sPageSize))
/*     */         {
/* 107 */           iPageSize = Integer.parseInt(sPageSize);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 114 */       if ((AssetBankSettings.getApiPageSize() > 0) && (iPageIndex < 0))
/*     */       {
/* 117 */         iPageIndex = 0;
/* 118 */         iPageSize = AssetBankSettings.getApiPageSize();
/*     */       }
/*     */ 
/* 122 */       String sLanguageCode = a_request.getParameter("language");
/* 123 */       if (!StringUtil.stringIsPopulated(sLanguageCode))
/*     */       {
/* 125 */         sLanguageCode = "en";
/*     */       }
/*     */ 
/* 129 */       SearchResults results = searchManager.searchByPageIndex(criteria, iPageIndex, iPageSize, sLanguageCode);
/*     */ 
/* 132 */       String sGetAll = a_request.getParameter("fullList");
/* 133 */       boolean bFullList = false;
/* 134 */       if ((sGetAll != null) && (sGetAll.equals("true")))
/*     */       {
/* 136 */         bFullList = true;
/*     */       }
/*     */ 
/* 140 */       Vector vecAssets = SearchUtil.getAssetsFromSearchResults(results);
/* 141 */       AssetUtil.writeAssetsAsXML(vecAssets, bFullList, null, false, null, false, false, null, null, null, a_request, a_writer, -1L, -1L);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 145 */       throw new ApiException(e.getMessage());
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 149 */       throw new ApiException(e.getMessage());
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/* 153 */       throw new ApiException(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean searchCriteriaSpecified(SearchCriteria a_criteria)
/*     */   {
/* 201 */     return (StringUtil.stringIsPopulated(a_criteria.getKeywords())) || (StringUtil.stringIsPopulated(a_criteria.getOriginalAssetIdsString())) || (StringUtil.stringIsPopulated(a_criteria.getTitle())) || (StringUtil.stringIsPopulated(a_criteria.getFilename())) || (StringUtil.stringIsPopulated(a_criteria.getAddedBy())) || (StringUtil.stringIsPopulated(a_criteria.getAgreementText())) || ((a_criteria.getPermissionCategoriesToRefine() != null) && (a_criteria.getPermissionCategoriesToRefine().size() > 0)) || ((a_criteria.getDescriptiveCategoriesToRefine() != null) && (a_criteria.getDescriptiveCategoriesToRefine().size() > 0)) || ((a_criteria.getAttributeSearches() != null) && (a_criteria.getAttributeSearches().size() != 0)) || ((a_criteria.getApprovalStatuses() != null) && (a_criteria.getApprovalStatuses().size() > 0)) || (a_criteria.getPriceLower().getIsFormAmountEntered()) || (a_criteria.getPriceUpper().getIsFormAmountEntered()) || (a_criteria.hasDateRanges()) || (a_criteria.hasNumberRanges()) || (a_criteria.getWithoutCategory()) || (a_criteria.getDateImageAddedLower() != null) || (a_criteria.getDateImageAddedUpper() != null) || (a_criteria.getDateImageModLower() != null) || (a_criteria.getDateImageModUpper() != null) || (a_criteria.getDateDownloadedUpper() != null) || (a_criteria.getDateDownloadedLower() != null) || ((a_criteria.getAssetEntityIdsToInclude() != null) && (a_criteria.getAssetEntityIdsToInclude().size() > 0)) || (a_criteria.getOrientation() > 0) || (a_criteria.getEmptyFileStatus() > 0) || (a_criteria.getBulkUpload() > 0L) || (a_criteria.getIsComplete() != null) || (a_criteria.getIsSensitive() != null) || (a_criteria.getAgreementType() > 0L) || (a_criteria.getAverageRating() > -1.0D) || (a_criteria.getMaximumVotes() > 0) || (a_criteria.getMinimumVotes() > 0) || (a_criteria.getFilesizeLower() != null) || (a_criteria.getFilesizeUpper() != null) || (a_criteria.getAddedByUserId() > 0L);
/*     */   }
/*     */ 
/*     */   protected String getContentType()
/*     */   {
/* 211 */     return "text/xml";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.api.servlet.SearchServlet
 * JD-Core Version:    0.6.0
 */