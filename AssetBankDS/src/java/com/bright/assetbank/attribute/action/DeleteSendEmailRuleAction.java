/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
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
/*    */ public class DeleteSendEmailRuleAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/*    */   private static final String c_ksClassName = "DeleteSendEmailRuleAction";
/* 45 */   private AttributeDateRuleManager m_attributeDateRuleManager = null;
/*    */ 
/*    */   public void setAttributeDateRuleManager(AttributeDateRuleManager a_attributeDateRuleManager) {
/* 48 */     this.m_attributeDateRuleManager = a_attributeDateRuleManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 71 */     ActionForward afForward = null;
/* 72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 75 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 77 */       this.m_logger.error("DeleteSendEmailRuleActionThis user does not have permission to view the admin pages");
/* 78 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 82 */     long lRuleId = getLongParameter(a_request, "ruleId");
/* 83 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*    */ 
/* 85 */     if (lRuleId > 0L)
/*    */     {
/* 88 */       this.m_attributeDateRuleManager.deleteSendEmailRule(a_dbTransaction, lRuleId);
/*    */     }
/*    */ 
/* 92 */     String sQueryString = "attributeId=" + lAttributeId;
/* 93 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 94 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.DeleteSendEmailRuleAction
 * JD-Core Version:    0.6.0
 */