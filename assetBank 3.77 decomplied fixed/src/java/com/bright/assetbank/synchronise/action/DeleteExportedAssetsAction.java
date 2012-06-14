/*    */ package com.bright.assetbank.synchronise.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.synchronise.form.DeleteExportedStatusForm;
/*    */ import com.bright.assetbank.synchronise.service.AssetExportManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteExportedAssetsAction extends Bn2Action
/*    */ {
/* 42 */   private AssetExportManager m_exportManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 60 */     ActionForward forward = null;
/*    */ 
/* 62 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*    */     {
/* 64 */       this.m_logger.error("ExportFromSearchAction.execute : User does not have admin permission : " + userProfile);
/* 65 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 68 */     String sFileLocation = a_request.getParameter("filename");
/*    */ 
/* 70 */     if (sFileLocation != null)
/*    */     {
/* 72 */       getExportManager().deleteExportedAssetsAsynchronously(sFileLocation);
/* 73 */       DeleteExportedStatusForm form = (DeleteExportedStatusForm)a_form;
/* 74 */       form.setFileLocation(sFileLocation);
/* 75 */       forward = a_mapping.findForward("Success");
/*    */     }
/*    */     else
/*    */     {
/* 79 */       forward = a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 82 */     return forward;
/*    */   }
/*    */ 
/*    */   public AssetExportManager getExportManager()
/*    */   {
/* 87 */     return this.m_exportManager;
/*    */   }
/*    */ 
/*    */   public void setExportManager(AssetExportManager a_sExportManager)
/*    */   {
/* 92 */     this.m_exportManager = a_sExportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.DeleteExportedAssetsAction
 * JD-Core Version:    0.6.0
 */