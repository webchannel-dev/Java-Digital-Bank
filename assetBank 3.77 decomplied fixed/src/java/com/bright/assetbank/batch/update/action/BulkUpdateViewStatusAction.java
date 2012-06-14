/*    */ package com.bright.assetbank.batch.update.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.batch.update.form.BulkUpdateStatusForm;
/*    */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
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
/*    */ public class BulkUpdateViewStatusAction extends Bn2Action
/*    */ {
/* 43 */   private UpdateManager m_updateManager = null;
/*    */ 
/*    */   public void setUpdateManager(UpdateManager a_manager) {
/* 46 */     this.m_updateManager = a_manager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/*    */     try
/*    */     {
/* 57 */       BulkUpdateStatusForm form = (BulkUpdateStatusForm)a_form;
/*    */ 
/* 60 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 61 */       if (!userProfile.getIsLoggedIn())
/*    */       {
/* 63 */         this.m_logger.error("BulkUpdateViewStatusAction.execute : User does not have permission.");
/* 64 */         return a_mapping.findForward("NoPermission");
/*    */       }
/*    */ 
/* 68 */       form.setMessages(this.m_updateManager.getMessages(userProfile.getUser().getId()));
/* 69 */       form.setInProgress(this.m_updateManager.isBatchInProgress(userProfile.getUser().getId()));
/*    */ 
/* 72 */       if ((!form.getInProgress()) && (userProfile.getBatchUpdateController() != null) && (userProfile.getBatchUpdateController().getDelete()))
/*    */       {
/* 74 */         userProfile.getBatchUpdateController().cancelCurrentBatchUpdate();
/* 75 */         userProfile.setBatchUpdateController(null);
/* 76 */         form.setDelete(true);
/*    */       }
/*    */ 
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 82 */       this.m_logger.error("Exception in MetadataImportViewStatus: " + e.getMessage());
/* 83 */       throw new Bn2Exception("Exception in MetadataImportViewStatus: " + e.getMessage(), e);
/*    */     }
/* 85 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.BulkUpdateViewStatusAction
 * JD-Core Version:    0.6.0
 */