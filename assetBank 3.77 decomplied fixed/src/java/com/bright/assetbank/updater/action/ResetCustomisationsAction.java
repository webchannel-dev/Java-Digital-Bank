/*    */ package com.bright.assetbank.updater.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.updater.service.ApplicationUpdateManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ResetCustomisationsAction extends Bn2Action
/*    */ {
/* 36 */   private ApplicationUpdateManager m_appUpdateManager = null;
/*    */ 
/*    */   public void setApplicationUpdateManager(ApplicationUpdateManager a_appUpdateManager) {
/* 39 */     this.m_appUpdateManager = a_appUpdateManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 71 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsInitialOrgUnitAdmin()))
/*    */     {
/* 73 */       this.m_logger.error("ResetCustomisations Action : User does not have admin permission : " + userProfile);
/* 74 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 77 */     this.m_appUpdateManager.resetCustomisations();
/*    */ 
/* 79 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.action.ResetCustomisationsAction
 * JD-Core Version:    0.6.0
 */