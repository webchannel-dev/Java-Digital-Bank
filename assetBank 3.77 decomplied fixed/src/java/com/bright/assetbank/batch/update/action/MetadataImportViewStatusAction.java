/*    */ package com.bright.assetbank.batch.update.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
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
/*    */ public class MetadataImportViewStatusAction extends Bn2Action
/*    */ {
/* 43 */   private AssetImportManager m_importManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/*    */     try
/*    */     {
/* 70 */       MetadataImportStatusForm form = (MetadataImportStatusForm)a_form;
/*    */ 
/* 73 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 75 */       if (!userProfile.getIsAdmin())
/*    */       {
/* 77 */         this.m_logger.error("MetadataImportViewStatus.execute : User does not have permission.");
/* 78 */         return a_mapping.findForward("NoPermission");
/*    */       }
/*    */ 
/* 82 */       form.setMessages(this.m_importManager.getMessages(userProfile.getUser().getId()));
/* 83 */       form.setInProgress(this.m_importManager.isBatchInProgress(userProfile.getUser().getId()));
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 87 */       this.m_logger.error("Exception in MetadataImportViewStatus: " + e.getMessage());
/* 88 */       throw new Bn2Exception("Exception in MetadataImportViewStatus: " + e.getMessage(), e);
/*    */     }
/* 90 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setImportManager(AssetImportManager a_sImportManager)
/*    */   {
/* 95 */     this.m_importManager = a_sImportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.MetadataImportViewStatusAction
 * JD-Core Version:    0.6.0
 */