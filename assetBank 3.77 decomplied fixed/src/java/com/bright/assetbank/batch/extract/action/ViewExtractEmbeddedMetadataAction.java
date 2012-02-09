/*    */ package com.bright.assetbank.batch.extract.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.batch.extract.service.ManualExtractQueueManager;
/*    */ import com.bright.framework.search.form.ReindexStatusForm;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewExtractEmbeddedMetadataAction extends Bn2Action
/*    */ {
/* 37 */   private ManualExtractQueueManager m_extractQueueManager = null;
/*    */ 
/*    */   public void setManualExtractQueueManager(ManualExtractQueueManager a_sManager) {
/* 40 */     this.m_extractQueueManager = a_sManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     ActionForward afForward = null;
/* 55 */     ReindexStatusForm statusForm = (ReindexStatusForm)a_form;
/* 56 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 59 */     if (this.m_extractQueueManager.getMessages(userProfile.getUser().getId()) != null)
/*    */     {
/* 61 */       statusForm.setMessages((Vector)this.m_extractQueueManager.getMessages(userProfile.getUser().getId()).clone());
/*    */     }
/*    */     else
/*    */     {
/* 65 */       statusForm.setMessages(null);
/*    */     }
/*    */ 
/* 69 */     if (this.m_extractQueueManager.isBatchInProgress(userProfile.getUser().getId()))
/*    */     {
/* 71 */       statusForm.setReindexInProgress(true);
/*    */     }
/*    */     else
/*    */     {
/* 75 */       statusForm.setReindexInProgress(false);
/*    */     }
/*    */ 
/* 79 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 81 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.extract.action.ViewExtractEmbeddedMetadataAction
 * JD-Core Version:    0.6.0
 */