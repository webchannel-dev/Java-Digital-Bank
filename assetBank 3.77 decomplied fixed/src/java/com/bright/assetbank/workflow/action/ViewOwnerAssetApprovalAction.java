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
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.workflow.bean.Workflow;
/*    */ import com.bright.framework.workflow.service.WorkflowManager;
/*    */ import java.util.Set;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewOwnerAssetApprovalAction extends BTransactionAction
/*    */ {
/* 43 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*    */ 
/* 49 */   private WorkflowManager m_workflowManager = null;
/*    */ 
/*    */   public void setAssetWorkflowManager(AssetWorkflowManager a_wm)
/*    */   {
/* 46 */     this.m_assetWorkflowManager = a_wm;
/*    */   }
/*    */ 
/*    */   public void setWorkflowManager(WorkflowManager a_wm)
/*    */   {
/* 52 */     this.m_workflowManager = a_wm;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 64 */     ActionForward afForward = null;
/* 65 */     AssetApprovalForm form = (AssetApprovalForm)a_form;
/* 66 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 69 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 71 */       this.m_logger.debug("ViewAssetApprovalAction: not logged in");
/* 72 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 75 */     long lUserId = userProfile.getUser().getId();
/* 76 */     form.setWorkflows(this.m_workflowManager.getWorkflowBeans());
/* 77 */     Workflow workflow = WorkflowUtil.getSelectedWorkflow(a_request, form.getWorkflows());
/* 78 */     form.setSelectedWorkflow(workflow);
/*    */ 
/* 81 */     Set userPermissionCatIds = null;
/* 82 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 84 */       userPermissionCatIds = userProfile.getVisiblePermissionCategories();
/*    */     }
/* 86 */     TransitionableAssets list = this.m_assetWorkflowManager.getTransitionableAssetsForOwner(a_dbTransaction, lUserId, userPermissionCatIds, workflow.getName());
/* 87 */     form.setApprovalList(list);
/*    */ 
/* 89 */     afForward = a_mapping.findForward("Success");
/* 90 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.ViewOwnerAssetApprovalAction
 * JD-Core Version:    0.6.0
 */