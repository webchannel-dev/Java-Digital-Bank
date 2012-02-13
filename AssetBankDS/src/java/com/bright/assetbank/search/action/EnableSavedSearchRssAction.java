/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
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
/*    */ public class EnableSavedSearchRssAction extends BTransactionAction
/*    */ {
/* 42 */   private SavedSearchManager m_savedSearchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 53 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 55 */       this.m_logger.debug("SaveSearchAction.execute() : A user must be logged in to enable/disable an rss feed.");
/* 56 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 59 */     String sName = a_request.getParameter("name");
/* 60 */     boolean bDisable = Boolean.parseBoolean(a_request.getParameter("disable"));
/* 61 */     this.m_savedSearchManager.enableDisableSavedSearchRss(a_transaction, sName, userProfile.getUser().getId(), !bDisable);
/*    */ 
/* 63 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setSavedSearchManager(SavedSearchManager savedSearchManager)
/*    */   {
/* 68 */     this.m_savedSearchManager = savedSearchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.EnableSavedSearchRssAction
 * JD-Core Version:    0.6.0
 */