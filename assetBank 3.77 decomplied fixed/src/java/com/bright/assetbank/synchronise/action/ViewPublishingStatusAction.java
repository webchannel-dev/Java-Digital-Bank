/*    */ package com.bright.assetbank.synchronise.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.batch.update.form.StatusForm;
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
/*    */ public class ViewPublishingStatusAction extends Bn2Action
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
/* 71 */       this.m_logger.error("ViewPublishingStatusAction.execute : User does not have admin permission : " + userProfile);
/* 72 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 75 */     StatusForm form = (StatusForm)a_form;
/* 76 */     form.setInProgress(this.m_synchManager.getInProgressCount() > 0);
/* 77 */     if (form.getInProgress())
/*    */     {
/* 79 */       form.setMessages(this.m_synchManager.getMessages());
/*    */     }
/*    */     else
/*    */     {
/* 83 */       form.setMessages(this.m_synchManager.getLastLog());
/*    */     }
/*    */ 
/* 86 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setSynchronisationServiceManager(SynchronisationServiceManager a_synchManager)
/*    */   {
/* 92 */     this.m_synchManager = a_synchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.ViewPublishingStatusAction
 * JD-Core Version:    0.6.0
 */