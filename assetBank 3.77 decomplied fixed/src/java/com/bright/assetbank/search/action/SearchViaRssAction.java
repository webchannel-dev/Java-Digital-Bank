/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.search.bean.SearchQuery;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class SearchViaRssAction extends SearchAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     if (Boolean.TRUE.equals(a_request.getAttribute("rssSearch")))
/*    */     {
/*    */       try
/*    */       {
/* 56 */         return super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*    */       }
/*    */       catch (Throwable t)
/*    */       {
/* 60 */         this.m_logger.error("SearchViaRssAction.execute() : Exception caught while running search for RSS feed : " + t.getLocalizedMessage(), t);
/* 61 */         return a_mapping.findForward("Failure");
/*    */       }
/*    */     }
/* 64 */     return a_mapping.findForward("NoPermission");
/*    */   }
/*    */ 
/*    */   protected void prepareSearchCriteriaBeforeSearch(HttpServletRequest a_request, SearchQuery a_searchQuery)
/*    */   {
/* 69 */     int iMaxResults = getIntParameter(a_request, "numAssets");
/*    */ 
/* 71 */     if ((iMaxResults > AssetBankSettings.getNumberOfSearchResultsViaRss()) || (iMaxResults <= 0))
/*    */     {
/* 73 */       iMaxResults = AssetBankSettings.getNumberOfSearchResultsViaRss();
/*    */     }
/*    */ 
/* 76 */     a_searchQuery.setMaxNoOfResults(iMaxResults);
/*    */ 
/* 78 */     if (a_request.getParameter("lang") != null)
/*    */     {
/* 80 */       a_searchQuery.setLanguageCode(a_request.getParameter("lang"));
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 86 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.SearchViaRssAction
 * JD-Core Version:    0.6.0
 */