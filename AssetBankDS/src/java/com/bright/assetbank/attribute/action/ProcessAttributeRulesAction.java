/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.service.AttributeDateRuleManager;
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
/*    */ public class ProcessAttributeRulesAction extends BTransactionAction
/*    */ {
/*    */   private static final String c_ksClassName = "ProcessAttributeRulesAction";
/* 44 */   private AttributeDateRuleManager m_attributeDateRuleManager = null;
/*    */ 
/*    */   public void setAttributeDateRuleManager(AttributeDateRuleManager a_attributeDateRuleManager) {
/* 47 */     this.m_attributeDateRuleManager = a_attributeDateRuleManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     ActionForward afForward = null;
/* 71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 74 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 76 */       this.m_logger.error("ProcessAttributeRulesActionThis user does not have permission to view the admin pages");
/* 77 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 80 */     this.m_attributeDateRuleManager.processChangeAttributeValueRules(true);
/* 81 */     this.m_attributeDateRuleManager.processSendEmailRules(true);
/*    */ 
/* 83 */     afForward = a_mapping.findForward("Success");
/* 84 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ProcessAttributeRulesAction
 * JD-Core Version:    0.6.0
 */