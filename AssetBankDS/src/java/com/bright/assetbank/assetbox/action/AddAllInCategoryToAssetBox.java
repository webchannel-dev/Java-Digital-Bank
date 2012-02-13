/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bright.assetbank.search.bean.SearchCriteria;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class AddAllInCategoryToAssetBox extends AddAllSearchResultsToAssetBox
/*    */ {
/*    */   protected com.bright.framework.search.bean.BaseSearchQuery getSearchQuery(HttpServletRequest a_request)
/*    */   {
/* 40 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 43 */     String sCategoryId = a_request.getParameter("categoryId");
/*    */ 
/* 45 */     com.bright.assetbank.search.bean.BaseSearchQuery criteria = null;
/*    */ 
/* 47 */     if (StringUtil.stringIsPopulated(sCategoryId))
/*    */     {
/* 49 */       criteria = new SearchCriteria();
/* 50 */       criteria.setCategoryIds(sCategoryId);
/* 51 */       criteria.setIncludeImplicitCategoryMembers(false);
/* 52 */       criteria.setSelectedFilters(userProfile.getSelectedFilters());
/*    */     }
/*    */ 
/* 55 */     return criteria;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.AddAllInCategoryToAssetBox
 * JD-Core Version:    0.6.0
 */