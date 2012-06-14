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
/*    */ public class DeleteChangeAttributeValueRuleAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/*    */   private static final String c_ksClassName = "DeleteChangeAttributeValueRuleAction";
/* 45 */   private AttributeDateRuleManager m_attributeDateRuleManager = null;
/*    */ 
/*    */   public void setAttributeDateRuleManager(AttributeDateRuleManager a_attributeDateRuleManager) {
/* 48 */     this.m_attributeDateRuleManager = a_attributeDateRuleManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 72 */     ActionForward afForward = null;
/* 73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 76 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 78 */       this.m_logger.error("DeleteChangeAttributeValueRuleActionThis user does not have permission to view the admin pages");
/* 79 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 83 */     long lRuleId = getLongParameter(a_request, "ruleId");
/* 84 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*    */ 
/* 86 */     if (lRuleId > 0L)
/*    */     {
/* 89 */       this.m_attributeDateRuleManager.deleteChangeAttributeValueRule(a_dbTransaction, lRuleId);
/*    */     }
/*    */ 
/* 93 */     String sQueryString = "attributeId=" + lAttributeId;
/* 94 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 95 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.DeleteChangeAttributeValueRuleAction
 * JD-Core Version:    0.6.0
 */