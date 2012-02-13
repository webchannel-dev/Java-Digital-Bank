/*    */ package com.bright.assetbank.workflow.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.workflow.constant.AssetWorkflowConstants;
/*    */ import com.bright.assetbank.workflow.form.WorkflowAuditForm;
/*    */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewWorkflowAudit extends BTransactionAction
/*    */   implements AssetWorkflowConstants
/*    */ {
/* 36 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*    */ 
/*    */   public void setAssetWorkflowManager(AssetWorkflowManager a_wm) {
/* 39 */     this.m_assetWorkflowManager = a_wm;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 49 */     ActionForward afForward = null;
/* 50 */     WorkflowAuditForm form = (WorkflowAuditForm)a_form;
/*    */ 
/* 53 */     long lAssetId = getLongParameter(a_request, "id");
/* 54 */     form.setApprovalAudit(this.m_assetWorkflowManager.getWorkflowAudit(a_dbTransaction, lAssetId));
/*    */ 
/* 56 */     afForward = a_mapping.findForward("Success");
/* 57 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.ViewWorkflowAudit
 * JD-Core Version:    0.6.0
 */