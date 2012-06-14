/*    */ package com.bright.assetbank.usage.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.usage.service.UsageManager;
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
/*    */ public class DeleteUsageTypeAction extends BTransactionAction
/*    */ {
/* 46 */   private UsageManager m_usageManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 65 */     ActionForward afForward = null;
/* 66 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 69 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 71 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 72 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 76 */     long lUsageTypeId = getLongParameter(a_request, "id");
/* 77 */     long lParentId = getLongParameter(a_request, "parentId");
/*    */ 
/* 79 */     this.m_usageManager.deleteUsageTypeValue(a_dbTransaction, lUsageTypeId);
/*    */ 
/* 82 */     String sQueryString = "parentId=" + lParentId;
/* 83 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*    */ 
/* 85 */     return afForward;
/*    */   }
/*    */ 
/*    */   public UsageManager getUsageManager()
/*    */   {
/* 91 */     return this.m_usageManager;
/*    */   }
/*    */ 
/*    */   public void setUsageManager(UsageManager a_sUsageMaager)
/*    */   {
/* 97 */     this.m_usageManager = a_sUsageMaager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.DeleteUsageTypeAction
 * JD-Core Version:    0.6.0
 */