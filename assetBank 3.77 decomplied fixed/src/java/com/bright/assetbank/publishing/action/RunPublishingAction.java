/*    */ package com.bright.assetbank.publishing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.publishing.bean.PublishingAction;
/*    */ import com.bright.assetbank.publishing.bean.PublishingActionRequest;
/*    */ import com.bright.assetbank.publishing.service.PublishingManager;
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
/*    */ public class RunPublishingAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 23 */   private PublishingManager m_publishingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 31 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 34 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 36 */       this.m_logger.debug("This user does not have permission to view the admin page");
/* 37 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 41 */     Long id = Long.valueOf(getLongParameter(a_request, "publishingActionId"));
/* 42 */     PublishingAction publishingAction = this.m_publishingManager.loadPublishAction(a_dbTransaction, id.longValue());
/* 43 */     PublishingActionRequest queuedItem = new PublishingActionRequest(publishingAction);
/*    */ 
/* 45 */     this.m_publishingManager.queuePublishingAction(queuedItem);
/*    */ 
/* 48 */     return createRedirectingForward("/action/viewPublishingActionStatus?queuedTaskId=" + queuedItem.getRequestId());
/*    */   }
/*    */ 
/*    */   public void setPublishingManager(PublishingManager a_publishingManager)
/*    */   {
/* 54 */     this.m_publishingManager = a_publishingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.publishing.action.RunPublishingAction
 * JD-Core Version:    0.6.0
 */