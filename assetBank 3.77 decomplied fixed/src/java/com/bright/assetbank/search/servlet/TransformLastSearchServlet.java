/*    */ package com.bright.assetbank.search.servlet;
/*    */ 
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.search.bean.SearchQuery;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class TransformLastSearchServlet extends TransformSearchServlet
/*    */ {
/*    */   private static final long serialVersionUID = -7514148349758679438L;
/*    */ 
/*    */   protected SearchQuery getSearchCriteriaToTransform(HttpServletRequest a_request)
/*    */   {
/* 40 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 42 */     SearchQuery criteria = userProfile.getSearchCriteria();
/* 43 */     return criteria;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.servlet.TransformLastSearchServlet
 * JD-Core Version:    0.6.0
 */