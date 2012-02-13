/*    */ package com.bright.assetbank.workflow.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.workflow.constant.AssetWorkflowConstants;
/*    */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
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
/*    */ public class DeleteWorkflowAuditEntry extends BTransactionAction
/*    */   implements AssetWorkflowConstants
/*    */ {
/* 37 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*    */ 
/*    */   public void setAssetWorkflowManager(AssetWorkflowManager a_wm) {
/* 40 */     this.m_assetWorkflowManager = a_wm;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 50 */     ActionForward afForward = null;
/* 51 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 54 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 56 */       this.m_logger.debug("DeleteWorkflowAuditEntry: not logged in");
/* 57 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 61 */     long lAuditEntryId = getLongParameter(a_request, "id");
/* 62 */     long lAssetId = getLongParameter(a_request, "assetId");
/*    */ 
/* 64 */     this.m_assetWorkflowManager.deleteWorkflowAudit(a_dbTransaction, lAuditEntryId);
/*    */ 
/* 67 */     String sQueryString = "id=" + lAssetId;
/* 68 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 69 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.DeleteWorkflowAuditEntry
 * JD-Core Version:    0.6.0
 */