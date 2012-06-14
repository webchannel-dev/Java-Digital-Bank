/*    */ package com.bright.assetbank.batch.update.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bright.assetbank.batch.update.form.MetadataImportStatusForm;
/*    */ import com.bright.assetbank.synchronise.service.AssetImportManager;
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
/*    */ public class ViewManageMetadataImportAction extends Bn2Action
/*    */ {
/*    */   private AssetImportManager m_importManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */   {
/* 54 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 56 */     MetadataImportStatusForm form = (MetadataImportStatusForm)a_form;
/*    */ 
/* 58 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 60 */       this.m_logger.error("ViewNewDataImport.execute : User does not have permission.");
/* 61 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 65 */     form.setInProgress(this.m_importManager.isBatchInProgress(userProfile.getUser().getId()));
/* 66 */     form.setMessages(this.m_importManager.getMessages(userProfile.getUser().getId()));
/*    */ 
/* 69 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setImportManager(AssetImportManager a_sImportManager)
/*    */   {
/* 74 */     this.m_importManager = a_sImportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.ViewManageMetadataImportAction
 * JD-Core Version:    0.6.0
 */