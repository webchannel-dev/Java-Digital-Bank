/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.search.form.SavedSearchForm;
/*    */ import com.bright.assetbank.search.service.SavedSearchManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewSavedSearchesAction extends BTransactionAction
/*    */ {
/* 42 */   private SavedSearchManager m_savedSearchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ActionForward afForward = null;
/* 52 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 53 */     SavedSearchForm form = (SavedSearchForm)a_form;
/*    */ 
/* 55 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 57 */       this.m_logger.debug("ViewSavedSearchesAction.execute() : A user must be logged in to view saved searches.");
/* 58 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 61 */     form.setSavedSearches(this.m_savedSearchManager.getSavedSearches(a_transaction, userProfile.getUser().getId(), false));
/*    */ 
/* 63 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 65 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setSavedSearchManager(SavedSearchManager a_savedSearchManager)
/*    */   {
/* 70 */     this.m_savedSearchManager = a_savedSearchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.ViewSavedSearchesAction
 * JD-Core Version:    0.6.0
 */