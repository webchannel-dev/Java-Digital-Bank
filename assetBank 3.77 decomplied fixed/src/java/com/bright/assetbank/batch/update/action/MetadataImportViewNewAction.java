/*    */ package com.bright.assetbank.batch.update.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
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
/*    */ public class MetadataImportViewNewAction extends Bn2Action
/*    */ {
/*    */   private AssetImportManager m_importManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */   {
/* 66 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 68 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 70 */       this.m_logger.error("ViewNewDataImport.execute : User does not have permission.");
/* 71 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 75 */     if (this.m_importManager.isBatchInProgress(userProfile.getUser().getId()))
/*    */     {
/* 77 */       return a_mapping.findForward("BatchInProgress");
/*    */     }
/*    */ 
/* 80 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setImportManager(AssetImportManager a_sImportManager)
/*    */   {
/* 85 */     this.m_importManager = a_sImportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.MetadataImportViewNewAction
 * JD-Core Version:    0.6.0
 */