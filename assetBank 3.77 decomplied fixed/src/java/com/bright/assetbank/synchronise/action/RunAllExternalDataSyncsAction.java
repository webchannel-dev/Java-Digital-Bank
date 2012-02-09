/*    */ package com.bright.assetbank.synchronise.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bright.assetbank.synchronise.service.ExternalDataSynchronisationManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RunAllExternalDataSyncsAction extends Bn2Action
/*    */ {
/* 40 */   private ExternalDataSynchronisationManager m_synchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Exception
/*    */   {
/* 62 */     ActionForward forward = null;
/* 63 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 65 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 67 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 71 */     this.m_synchManager.runSynchronisationTasks();
/*    */ 
/* 73 */     forward = a_mapping.findForward("Success");
/* 74 */     return forward;
/*    */   }
/*    */ 
/*    */   public void setExternalDataSynchronisationManager(ExternalDataSynchronisationManager a_synchManager)
/*    */   {
/* 80 */     this.m_synchManager = a_synchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.RunAllExternalDataSyncsAction
 * JD-Core Version:    0.6.0
 */