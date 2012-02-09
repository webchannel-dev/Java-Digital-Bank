/*    */ package com.bright.assetbank.subscription.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RunSubscriptionActivationAction extends Bn2Action
/*    */ {
/* 42 */   private SubscriptionManager m_subscriptionManager = null;
/*    */ 
/*    */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager) {
/* 45 */     this.m_subscriptionManager = a_subscriptionManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     ActionForward afForward = null;
/* 55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 58 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 60 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 61 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 65 */     this.m_subscriptionManager.runSubscriptionActivationRoutine();
/*    */ 
/* 67 */     afForward = a_mapping.findForward("Success");
/* 68 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.action.RunSubscriptionActivationAction
 * JD-Core Version:    0.6.0
 */