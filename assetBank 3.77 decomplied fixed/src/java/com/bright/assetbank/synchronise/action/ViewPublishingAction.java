/*    */ package com.bright.assetbank.synchronise.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.synchronise.service.SynchronisationServiceManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewPublishingAction extends Bn2Action
/*    */ {
/* 42 */   private SynchronisationServiceManager m_synchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 66 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 69 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 71 */       this.m_logger.error("ViewPublishingAction.execute : User does not have admin permission : " + userProfile);
/* 72 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 75 */     if (this.m_synchManager.getInProgressCount() > 0)
/*    */     {
/* 77 */       return a_mapping.findForward("InProgress");
/*    */     }
/* 79 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setSynchronisationServiceManager(SynchronisationServiceManager a_synchManager)
/*    */   {
/* 85 */     this.m_synchManager = a_synchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.ViewPublishingAction
 * JD-Core Version:    0.6.0
 */