/*    */ package com.bright.assetbank.category.servlet;
/*    */ 
/*    */ import com.bright.assetbank.search.servlet.TransformSearchServlet;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.search.bean.SearchQuery;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class TransformLastBrowseServlet extends TransformSearchServlet
/*    */ {
/*    */   private static final long serialVersionUID = -7514148349758679438L;
/*    */ 
/*    */   protected SearchQuery getSearchCriteriaToTransform(HttpServletRequest a_request)
/*    */   {
/* 41 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 43 */     SearchQuery criteria = userProfile.getBrowseCriteria();
/* 44 */     return criteria;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.servlet.TransformLastBrowseServlet
 * JD-Core Version:    0.6.0
 */