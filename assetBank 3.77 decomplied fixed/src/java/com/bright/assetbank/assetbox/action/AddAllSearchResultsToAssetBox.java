/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class AddAllSearchResultsToAssetBox extends AddToAssetBoxAction
/*     */ {
/*  42 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   protected long[] getAssetIds(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  59 */     Vector vecIds = new Vector();
/*     */ 
/*  62 */     SearchQuery searchQuery = getSearchQuery(a_request);
/*     */ 
/*  64 */     if (searchQuery == null)
/*     */     {
/*  66 */       throw new Bn2Exception("AddAllSearchResultsToAssetBox: searchCriteria is null");
/*     */     }
/*     */ 
/*  70 */     SearchResults searchResults = this.m_searchManager.search(searchQuery, userProfile.getCurrentLanguage().getCode());
/*     */ 
/*  73 */     for (int i = 0; i < searchResults.getSearchResults().size(); i++)
/*     */     {
/*  75 */       if (i >= AssetBankSettings.getAddAllToAssetBoxLimit())
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/*  81 */       LightweightAsset asset = (LightweightAsset)searchResults.getSearchResults().get(i);
/*     */ 
/*  84 */       if (userProfile.getAssetBox().containsAsset(asset.getId()))
/*     */         continue;
/*  86 */       vecIds.add(new Long(asset.getId()));
/*     */     }
/*     */ 
/*  91 */     long[] laAssetIds = new long[vecIds.size()];
/*  92 */     for (int i = 0; i < vecIds.size(); i++)
/*     */     {
/*  94 */       laAssetIds[i] = ((Long)vecIds.get(i)).longValue();
/*     */     }
/*     */ 
/*  97 */     return laAssetIds;
/*     */   }
/*     */ 
/*     */   protected SearchQuery getSearchQuery(HttpServletRequest a_request)
/*     */   {
/* 110 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 111 */     SearchQuery query = userProfile.getSearchCriteria();
/*     */ 
/* 113 */     return query;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 119 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.AddAllSearchResultsToAssetBox
 * JD-Core Version:    0.6.0
 */