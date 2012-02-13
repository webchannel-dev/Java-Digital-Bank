/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewItemAction extends BrowseItemsAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  62 */     ActionForward afForward = null;
/*     */ 
/*  65 */     long lCatId = getLongParameter(a_request, "categoryId");
/*  66 */     long lCatTreeId = getLongParameter(a_request, "categoryTypeId");
/*  67 */     int iIndex = getIntParameter(a_request, "index");
/*  68 */     long lFilterId = getLongParameter(a_request, "filterId");
/*     */ 
/*  70 */     SearchCriteria searchCriteria = new SearchCriteria();
/*     */ 
/*  72 */     long lSortOrder = getIntParameter(a_request, "sortAttributeId");
/*     */ 
/*  75 */     if (lSortOrder > 0L)
/*     */     {
/*  77 */       searchCriteria.setSortAttributeId(lSortOrder);
/*  78 */       searchCriteria.setSortDescending(Boolean.parseBoolean(a_request.getParameter("sortDescending")));
/*     */     }
/*     */     else
/*     */     {
/*  84 */       searchCriteria.setSortDescending(!this.m_attributeManager.getCustomSortOrderDefined(a_dbTransaction, 2L));
/*     */     }
/*     */ 
/*  87 */     if (lFilterId > 0L)
/*     */     {
/*  89 */       Filter filter = this.m_filterManager.getFilter(a_dbTransaction, lFilterId);
/*  90 */       searchCriteria.addSelectedFilter(filter);
/*     */     }
/*     */ 
/*  94 */     SearchResults results = getResults(searchCriteria, lCatId, lCatTreeId, userProfile, -1, -1, a_dbTransaction);
/*     */ 
/*  96 */     long lItemId = 0L;
/*  97 */     long lExpectedId = getLongParameter(a_request, "expectedAssetId");
/*     */ 
/*  99 */     if (results.getSearchResults().size() > iIndex)
/*     */     {
/* 102 */       LightweightAsset result = (LightweightAsset)results.getSearchResults().elementAt(iIndex);
/* 103 */       lItemId = result.getId();
/*     */     }
/*     */     String sQueryString;
/*     */     //String sQueryString;
/* 110 */     if ((lExpectedId > 0L) && (lExpectedId != lItemId))
/*     */     {
/* 112 */       sQueryString = "id=" + lExpectedId;
/*     */     }
/*     */     else
/*     */     {
/* 117 */       long lTotal = results.getNumResults();
/*     */ 
/* 120 */       Category cat = this.m_categoryManager.getCategory(a_dbTransaction, lCatTreeId, lCatId);
/* 121 */       String sCollection = cat.getName();
/*     */ 
/* 123 */       sQueryString = "id=" + lItemId + "&" + "index" + "=" + iIndex + "&" + "total" + "=" + lTotal + "&" + "collection" + "=" + sCollection + "&" + "categoryId" + "=" + lCatId + "&" + "categoryTypeId" + "=" + lCatTreeId + "&" + "filterId" + "=" + lFilterId + (a_request.getParameter("sortAttributeId") != null ? "&sortAttributeId=" + a_request.getParameter("sortAttributeId") : "") + (a_request.getParameter("sortDescending") != null ? "&sortDescending=" + a_request.getParameter("sortDescending") : "");
/*     */     }
/*     */ 
/* 135 */     String sShowAdd = a_request.getParameter("showadd");
/* 136 */     if (StringUtil.stringIsPopulated(sShowAdd))
/*     */     {
/* 138 */       sQueryString = sQueryString + "&showadd=true";
/*     */     }
/*     */ 
/* 141 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 142 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected boolean setupPanels()
/*     */   {
/* 147 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.ViewItemAction
 * JD-Core Version:    0.6.0
 */