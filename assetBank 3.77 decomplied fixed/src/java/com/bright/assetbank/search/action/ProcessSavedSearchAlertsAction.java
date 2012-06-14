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
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ProcessSavedSearchAlertsAction extends BTransactionAction
/*    */ {
/* 41 */   private SavedSearchManager m_savedSearchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 50 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 52 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 54 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 57 */     this.m_savedSearchManager.processSavedSearchAlerts(a_transaction);
/* 58 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setSavedSearchManager(SavedSearchManager savedSearchManager)
/*    */   {
/* 63 */     this.m_savedSearchManager = savedSearchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.ProcessSavedSearchAlertsAction
 * JD-Core Version:    0.6.0
 */