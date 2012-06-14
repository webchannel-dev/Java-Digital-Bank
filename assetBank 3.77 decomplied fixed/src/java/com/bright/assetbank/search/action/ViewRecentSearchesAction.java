/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.search.form.SavedSearchForm;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewRecentSearchesAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 46 */     ActionForward afForward = null;
/* 47 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 48 */     SavedSearchForm form = (SavedSearchForm)a_form;
/*    */ 
/* 50 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 52 */       this.m_logger.debug("ViewSavedSearchesAction.execute() : A user must be logged in to view saved searches.");
/* 53 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 56 */     form.setRecentSearches(userProfile.getRecentSearches());
/*    */ 
/* 58 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 60 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.ViewRecentSearchesAction
 * JD-Core Version:    0.6.0
 */