/*    */ package com.bright.assetbank.usage.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.usage.service.UsageManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteUsageTypeFormatAction extends Bn2Action
/*    */   implements AssetBankConstants
/*    */ {
/* 43 */   private UsageManager m_usageManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Exception
/*    */   {
/* 67 */     ActionForward afForward = a_mapping.findForward("Success");
/* 68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 71 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 73 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 74 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 78 */     long lUsageTypeFormatId = getLongParameter(a_request, "usageTypeFormatId");
/* 79 */     this.m_usageManager.deleteUsageTypeFormat(null, lUsageTypeFormatId);
/*    */ 
/* 82 */     a_request.setAttribute("delete", new Boolean(true));
/*    */ 
/* 84 */     return afForward;
/*    */   }
/*    */ 
/*    */   public UsageManager getUsageManager()
/*    */   {
/* 90 */     return this.m_usageManager;
/*    */   }
/*    */ 
/*    */   public void setUsageManager(UsageManager a_sUsageMaager)
/*    */   {
/* 96 */     this.m_usageManager = a_sUsageMaager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.DeleteUsageTypeFormatAction
 * JD-Core Version:    0.6.0
 */