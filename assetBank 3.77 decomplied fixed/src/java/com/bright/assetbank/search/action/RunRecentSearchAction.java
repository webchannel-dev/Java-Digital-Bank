/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.search.bean.RecentSearch;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.search.constant.SearchConstants;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RunRecentSearchAction extends Bn2Action
/*    */   implements SearchConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 50 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 52 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 54 */       this.m_logger.debug("RunRecentSearchAction.execute() : A user must be logged in to save a search.");
/* 55 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 58 */     int iIndex = getIntParameter(a_request, "index");
/* 59 */     RecentSearch search = null;
/*    */ 
/* 61 */     if (iIndex < userProfile.getRecentSearches().size())
/*    */     {
/* 63 */       search = (RecentSearch)userProfile.getRecentSearches().get(iIndex);
/*    */     }
/*    */ 
/* 66 */     if (search == null)
/*    */     {
/* 68 */       this.m_logger.debug("RunRecentSearchAction.execute() : Index " + iIndex + " is out of range of recent searches for user id=" + userProfile.getUser().getId());
/* 69 */       return a_mapping.findForward("SystemFailure");
/*    */     }
/*    */ 
/* 72 */     String sForwardKey = null;
/*    */ 
/* 74 */     if (search.isBuilderSearch())
/*    */     {
/* 76 */       sForwardKey = "SearchBuilder";
/*    */     }
/*    */     else
/*    */     {
/* 80 */       sForwardKey = "SearchForm";
/*    */     }
/*    */ 
/* 83 */     return new ActionForward(a_mapping.findForward(sForwardKey).getPath() + "?" + search.getQueryString() + "&" + "sortAttributeId" + "=" + search.getSortAttributeId() + "&" + "sortDescending" + "=" + search.isDescending() + "&" + "searchPage" + "=" + "savedSearches", true);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.RunRecentSearchAction
 * JD-Core Version:    0.6.0
 */