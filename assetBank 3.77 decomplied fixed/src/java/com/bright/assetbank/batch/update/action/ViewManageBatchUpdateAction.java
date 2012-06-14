/*    */ package com.bright.assetbank.batch.update.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bright.assetbank.batch.update.form.BulkUpdateStatusForm;
/*    */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewManageBatchUpdateAction extends Bn2Action
/*    */ {
/* 43 */   private UpdateManager m_updateManager = null;
/*    */ 
/*    */   public void setUpdateManager(UpdateManager a_manager) {
/* 46 */     this.m_updateManager = a_manager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */   {
/* 55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 57 */     BulkUpdateStatusForm form = (BulkUpdateStatusForm)a_form;
/*    */ 
/* 59 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 61 */       this.m_logger.error("ViewNewDataImport.execute : User does not have permission.");
/* 62 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 66 */     form.setInProgress(this.m_updateManager.isBatchInProgress(userProfile.getUser().getId()));
/* 67 */     form.setMessages(this.m_updateManager.getMessages(userProfile.getUser().getId()));
/*    */ 
/* 69 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.ViewManageBatchUpdateAction
 * JD-Core Version:    0.6.0
 */