/*    */ package com.bright.assetbank.updater.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.updater.service.ApplicationUpdateManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class PatchApplicationSettingsAction extends Bn2Action
/*    */ {
/*    */   private static final String c_ksClassName = "PatchApplicationSettingsAction";
/* 46 */   private ApplicationUpdateManager m_appUpdateManager = null;
/*    */ 
/*    */   public void setApplicationUpdateManager(ApplicationUpdateManager a_appUpdateManager) {
/* 49 */     this.m_appUpdateManager = a_appUpdateManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception, IOException
/*    */   {
/* 61 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 63 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 65 */       this.m_logger.error("PatchApplicationSettingsAction: User does not have admin permission : " + userProfile);
/* 66 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 69 */     String appSettingsPath = a_request.getParameter("appSettingsPath");
/* 70 */     String oldChangesFile = a_request.getParameter("oldChangesFile");
/* 71 */     String newChangesFile = a_request.getParameter("newChangesFile");
/*    */ 
/* 73 */     this.m_appUpdateManager.patchAppSettings(appSettingsPath, oldChangesFile, newChangesFile);
/*    */ 
/* 75 */     this.m_logger.info("PatchApplicationSettingsAction: App settings updated successfully");
/*    */ 
/* 77 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.action.PatchApplicationSettingsAction
 * JD-Core Version:    0.6.0
 */