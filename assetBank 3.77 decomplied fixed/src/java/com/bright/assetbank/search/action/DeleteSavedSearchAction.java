/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.search.service.SavedSearchManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteSavedSearchAction extends BTransactionAction
/*    */ {
/* 43 */   private SavedSearchManager m_savedSearchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     ActionForward afForward = null;
/* 53 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 55 */     String sName = a_request.getParameter("name");
/*    */ 
/* 57 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 59 */       this.m_logger.debug("SaveSearchAction.execute() : A user must be logged in to delete a saved search.");
/* 60 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 63 */     if (StringUtils.isNotEmpty(sName))
/*    */     {
/* 65 */       this.m_savedSearchManager.deleteSavedSearch(a_transaction, sName, userProfile, true, true);
/*    */     }
/*    */ 
/* 68 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 70 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setSavedSearchManager(SavedSearchManager savedSearchManager)
/*    */   {
/* 75 */     this.m_savedSearchManager = savedSearchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.DeleteSavedSearchAction
 * JD-Core Version:    0.6.0
 */