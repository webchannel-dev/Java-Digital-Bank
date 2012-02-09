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
/*    */ public class GenerateChangesSqlScriptAction extends Bn2Action
/*    */ {
/* 37 */   private ApplicationUpdateManager m_appUpdateManager = null;
/*    */ 
/*    */   public void setApplicationUpdateManager(ApplicationUpdateManager a_appUpdateManager) {
/* 40 */     this.m_appUpdateManager = a_appUpdateManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 72 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsInitialOrgUnitAdmin()))
/*    */     {
/* 74 */       this.m_logger.error("GenerateChangesSqlScript : User does not have admin permission : " + userProfile);
/* 75 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 79 */     String sDataBaseType = a_request.getParameter("DatabaseType");
/*    */ 
/* 81 */     this.m_appUpdateManager.generateDatabaseChangesScript(sDataBaseType);
/*    */ 
/* 83 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.action.GenerateChangesSqlScriptAction
 * JD-Core Version:    0.6.0
 */