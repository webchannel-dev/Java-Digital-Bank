/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.search.bean.SavedSearch;
/*    */ import com.bright.assetbank.search.form.SavedSearchForm;
/*    */ import com.bright.assetbank.search.service.SavedSearchManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewSaveSearchAction extends BTransactionAction
/*    */ {
/* 45 */   private SavedSearchManager m_savedSearchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     ActionForward afForward = null;
/* 55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 57 */     SavedSearchForm form = (SavedSearchForm)a_form;
/*    */ 
/* 60 */     int iIndex = getIntParameter(a_request, "index");
/*    */ 
/* 62 */     if (iIndex < 0)
/*    */     {
/* 64 */       iIndex = 0;
/*    */     }
/*    */ 
/* 67 */     if (userProfile.getSearchCriteria() == null)
/*    */     {
/* 69 */       this.m_logger.error("ViewSaveSearchAction.execute() : Cannot save a search as the cached criteria is null");
/* 70 */       afForward = a_mapping.findForward("SystemFailure");
/*    */     }
/* 72 */     else if ((userProfile.getRecentSearches() != null) && (userProfile.getRecentSearches().size() > iIndex))
/*    */     {
/* 74 */       SavedSearch searchToSave = (SavedSearch)userProfile.getRecentSearches().get(iIndex);
/*    */ 
/* 76 */       form.setSavedSearch(searchToSave);
/*    */ 
/* 78 */       if ((StringUtils.isEmpty(form.getSavedSearch().getName())) && (StringUtils.isNotEmpty(searchToSave.getKeywords())))
/*    */       {
/* 80 */         form.getSavedSearch().setName(searchToSave.getKeywords().trim());
/*    */       }
/*    */ 
/* 83 */       form.setSavedSearches(this.m_savedSearchManager.getSavedSearches(a_transaction, userProfile.getUser().getId(), false));
/*    */ 
/* 85 */       afForward = a_mapping.findForward("Success");
/*    */     }
/*    */     else
/*    */     {
/* 89 */       this.m_logger.debug("ViewSaveSearchAction.execute() : Cannot save a search - no recent search was found at index " + iIndex);
/* 90 */       afForward = a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 93 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setSavedSearchManager(SavedSearchManager a_savedSearchManager)
/*    */   {
/* 98 */     this.m_savedSearchManager = a_savedSearchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.ViewSaveSearchAction
 * JD-Core Version:    0.6.0
 */