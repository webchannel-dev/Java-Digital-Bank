/*    */ package com.bright.assetbank.workflow.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.workflow.bean.TransitionableAssets;
/*    */ import com.bright.assetbank.workflow.form.AssetApprovalForm;
/*    */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*    */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.workflow.bean.Workflow;
/*    */ import com.bright.framework.workflow.service.WorkflowManager;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewOwnerAssetApprovalAdminAction extends BTransactionAction
/*    */ {
/* 50 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*    */ 
/* 56 */   private WorkflowManager m_workflowManager = null;
/*    */ 
/*    */   public void setAssetWorkflowManager(AssetWorkflowManager a_wm)
/*    */   {
/* 53 */     this.m_assetWorkflowManager = a_wm;
/*    */   }
/*    */ 
/*    */   public void setWorkflowManager(WorkflowManager a_wm)
/*    */   {
/* 59 */     this.m_workflowManager = a_wm;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     ActionForward afForward = null;
/* 71 */     AssetApprovalForm form = (AssetApprovalForm)a_form;
/* 72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 75 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 77 */       this.m_logger.debug("ViewOwnerAssetApprovalAdminAction: not admin");
/* 78 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 81 */     long lUserId = form.getSelectedUserId();
/* 82 */     form.setWorkflows(this.m_workflowManager.getWorkflowBeans());
/* 83 */     Workflow workflow = WorkflowUtil.getSelectedWorkflow(a_request, form.getWorkflows());
/* 84 */     form.setSelectedWorkflow(workflow);
/*    */ 
/* 86 */     if (lUserId > 0L)
/*    */     {
/* 89 */       TransitionableAssets list = this.m_assetWorkflowManager.getTransitionableAssetsForOwner(a_dbTransaction, lUserId, null, workflow.getName());
/* 90 */       form.setApprovalList(list);
/*    */     }
/*    */ 
/* 94 */     Vector listUsers = this.m_assetWorkflowManager.getUsersWithSubmittedAssets(a_dbTransaction);
/* 95 */     form.setListUsers(listUsers);
/* 96 */     afForward = a_mapping.findForward("Success");
/* 97 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.ViewOwnerAssetApprovalAdminAction
 * JD-Core Version:    0.6.0
 */