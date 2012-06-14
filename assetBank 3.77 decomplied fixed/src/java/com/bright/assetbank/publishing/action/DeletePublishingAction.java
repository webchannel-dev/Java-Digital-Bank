/*    */ package com.bright.assetbank.publishing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
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
/*    */ public class DeletePublishingAction extends BTransactionAction
/*    */ {
/* 21 */   private PublishingManager m_publishingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 29 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 32 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 34 */       this.m_logger.debug("This user does not have permission to view the admin page");
/* 35 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 39 */     Long id = Long.valueOf(getLongParameter(a_request, "publishingActionId"));
/* 40 */     if (id.longValue() > 0L)
/*    */     {
/* 42 */       this.m_publishingManager.deletePublishAction(a_dbTransaction, id);
/*    */     }
/*    */ 
/* 46 */     return createRedirectingForward("/action/viewPublishing");
/*    */   }
/*    */ 
/*    */   public void setPublishingManager(PublishingManager a_publishingManager)
/*    */   {
/* 53 */     this.m_publishingManager = a_publishingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.publishing.action.DeletePublishingAction
 * JD-Core Version:    0.6.0
 */