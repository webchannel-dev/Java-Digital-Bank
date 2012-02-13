/*    */ package com.bright.assetbank.synchronise.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.synchronise.service.SynchronisationServiceManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class PublishFlaggedAssetsAction extends BTransactionAction
/*    */ {
/* 49 */   private SynchronisationServiceManager m_synchronisationServiceManager = null;
/*    */ 
/*    */   public void setSynchronisationServiceManager(SynchronisationServiceManager a_synchronisationServiceManager) {
/* 52 */     this.m_synchronisationServiceManager = a_synchronisationServiceManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 64 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 67 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 69 */       this.m_logger.error("PublishFlaggedAssetsAction.execute : User does not have admin permission : " + userProfile);
/* 70 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 74 */     if (this.m_synchronisationServiceManager.getInProgressCount() <= 0)
/*    */     {
/* 76 */       this.m_synchronisationServiceManager.exportAndPublishFlaggedAssets(a_dbTransaction, userProfile.getUser());
/*    */     }
/*    */ 
/* 80 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.PublishFlaggedAssetsAction
 * JD-Core Version:    0.6.0
 */