/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*    */ import com.bright.assetbank.search.bean.SavedSearch;
/*    */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*    */ import com.bright.assetbank.search.service.SavedSearchManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.search.constant.SearchConstants;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RunSavedSearchAction extends BTransactionAction
/*    */   implements SearchConstants, AssetBankSearchConstants
/*    */ {
/* 46 */   private SavedSearchManager m_savedSearchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 57 */     String sName = a_request.getParameter("name");
/* 58 */     long lUserId = getLongParameter(a_request, "id");
/*    */ 
/* 60 */     SavedSearch search = this.m_savedSearchManager.getSavedSearch(a_transaction, sName, lUserId);
/*    */ 
/* 62 */     if ((search == null) || (search.getCriteria() == null))
/*    */     {
/* 64 */       this.m_logger.debug("RunSavedSearchAction.execute() : Cannot find saved search'" + sName + "' for user id=" + userProfile.getUser().getId());
/* 65 */       return a_mapping.findForward("SystemFailure");
/*    */     }
/*    */ 
/* 69 */     search.getCriteria().setupPermissions(userProfile);
/* 70 */     search.getCriteria().setSelectedFilters(userProfile.getSelectedFilters());
/*    */ 
/* 73 */     userProfile.setSearchCriteria(search.getCriteria());
/*    */ 
/* 76 */     String sForwardKey = null;
/*    */ 
/* 78 */     if (search.isBuilderSearch())
/*    */     {
/* 80 */       sForwardKey = "SearchBuilder";
/*    */     }
/*    */     else
/*    */     {
/* 84 */       sForwardKey = "SearchForm";
/*    */     }
/*    */ 
/* 87 */     a_request.getSession().setAttribute("searchBuilder", String.valueOf(search.isBuilderSearch()));
/*    */ 
/* 89 */     return new ActionForward(a_mapping.findForward(sForwardKey).getPath() + "?" + "cachedCriteria" + "=1" + "&" + "sortAttributeId" + "=" + search.getSortAttributeId() + "&" + "sortDescending" + "=" + search.isDescending() + "&" + "searchPage" + "=" + "savedSearches", true);
/*    */   }
/*    */ 
/*    */   public void setSavedSearchManager(SavedSearchManager savedSearchManager)
/*    */   {
/* 98 */     this.m_savedSearchManager = savedSearchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.RunSavedSearchAction
 * JD-Core Version:    0.6.0
 */