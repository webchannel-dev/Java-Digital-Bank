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
/*    */ public class ViewSavedSearchAction extends BTransactionAction
/*    */ {
/* 43 */   private SavedSearchManager m_savedSearchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     ActionForward afForward = null;
/* 53 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 54 */     SavedSearchForm form = (SavedSearchForm)a_form;
/*    */ 
/* 56 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 58 */       this.m_logger.debug("ViewSavedSearchAction.execute() : A user must be logged in to view saved searches.");
/* 59 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 62 */     String sName = a_request.getParameter("name");
/* 63 */     form.setSavedSearch(this.m_savedSearchManager.getSavedSearch(a_transaction, sName, userProfile.getUser().getId()));
/* 64 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 66 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setSavedSearchManager(SavedSearchManager a_savedSearchManager)
/*    */   {
/* 71 */     this.m_savedSearchManager = a_savedSearchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.ViewSavedSearchAction
 * JD-Core Version:    0.6.0
 */