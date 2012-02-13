/*    */ package com.bright.assetbank.subscription.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.subscription.form.SubscriptionModelsForm;
/*    */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewSubscriptionModelsAction extends BTransactionAction
/*    */ {
/* 45 */   private SubscriptionManager m_subscriptionManager = null;
/*    */ 
/*    */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager) {
/* 48 */     this.m_subscriptionManager = a_subscriptionManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 65 */     ActionForward afForward = null;
/* 66 */     SubscriptionModelsForm form = (SubscriptionModelsForm)a_form;
/* 67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 70 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 72 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 73 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 77 */     Vector vecModels = this.m_subscriptionManager.getSubscriptionModels(a_dbTransaction, false);
/* 78 */     form.setModels(vecModels);
/*    */ 
/* 80 */     afForward = a_mapping.findForward("Success");
/* 81 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.action.ViewSubscriptionModelsAction
 * JD-Core Version:    0.6.0
 */