/*    */ package com.bright.assetbank.search.servlet;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.servlet.TransformAssetsServlet;
/*    */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*    */ import com.bright.assetbank.search.util.SearchUtil;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.search.bean.SearchQuery;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public abstract class TransformSearchServlet extends TransformAssetsServlet
/*    */   implements AssetBankSearchConstants
/*    */ {
/*    */   protected Vector<Asset> getAssets(HttpServletRequest a_request)
/*    */     throws Bn2Exception
/*    */   {
/* 42 */     SearchQuery searchCriteria = getSearchCriteriaToTransform(a_request);
/* 43 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 46 */     int iPage = -1;
/* 47 */     int iPageSize = -1;
/*    */     try
/*    */     {
/* 50 */       iPage = Integer.parseInt(a_request.getParameter("page"));
/* 51 */       iPageSize = Integer.parseInt(a_request.getParameter("pageSize"));
/*    */     }
/*    */     catch (NumberFormatException e) {
/*    */     }
/* 55 */     searchCriteria.setPageIndex(iPage);
/* 56 */     if (iPageSize != 0)
/*    */     {
/* 58 */       searchCriteria.setPageSize(iPageSize);
/*    */     }
/*    */     else
/*    */     {
/* 62 */       searchCriteria.setPageSize(-1);
/*    */     }
/* 64 */     Vector vecAssets = SearchUtil.getAssetsFromSearchCriteria(searchCriteria, userProfile.getCurrentLanguage().getCode());
/* 65 */     return vecAssets;
/*    */   }
/*    */ 
/*    */   protected abstract SearchQuery getSearchCriteriaToTransform(HttpServletRequest paramHttpServletRequest);
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.servlet.TransformSearchServlet
 * JD-Core Version:    0.6.0
 */