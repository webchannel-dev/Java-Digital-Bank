/*    */ package com.bright.assetbank.updater.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.updater.service.ApplicationUpdateManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewApplicationUpdateAdminAction extends ApplicationUpdateAction
/*    */ {
/* 47 */   private ApplicationUpdateManager m_appUpdateManager = null;
/*    */ 
/*    */   public void setApplicationUpdateManager(ApplicationUpdateManager a_appUpdateManager) {
/* 50 */     this.m_appUpdateManager = a_appUpdateManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 60 */     ActionForward afForward = null;
/*    */ 
/* 63 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 65 */     if ((!AssetBankSettings.isApplicationUpdateInProgress()) && (!userProfile.getIsAdmin()) && (!userProfile.getIsInitialOrgUnitAdmin()))
/*    */     {
/* 67 */       this.m_logger.error("ViewApplicationUpdateAdmin : User does not have admin permission : " + userProfile);
/* 68 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 71 */     String sVersion = getVersion(a_request);
/*    */ 
/* 74 */     boolean bAvailable = false;
/*    */     try
/*    */     {
/* 77 */       bAvailable = this.m_appUpdateManager.updateIsAvailableFullCheck(a_dbTransaction, sVersion);
/*    */     }
/*    */     catch (IOException ioe)
/*    */     {
/* 81 */       this.m_logger.error("IOException in ViewApplicationUpdateProgressAction - Could not connect to update server : " + ioe);
/* 82 */       return a_mapping.findForward("NoConnection");
/*    */     }
/*    */ 
/* 85 */     if (bAvailable)
/*    */     {
/* 87 */       afForward = a_mapping.findForward("Success");
/*    */     }
/*    */     else
/*    */     {
/* 91 */       afForward = a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 94 */     return afForward;
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 99 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.action.ViewApplicationUpdateAdminAction
 * JD-Core Version:    0.6.0
 */