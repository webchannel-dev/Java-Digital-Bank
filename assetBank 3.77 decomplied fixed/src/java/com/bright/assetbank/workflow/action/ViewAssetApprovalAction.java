/*    */ package com.bright.assetbank.workflow.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.approval.service.AssetApprovalManager;
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
/*    */ import java.util.Set;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAssetApprovalAction extends BTransactionAction
/*    */ {
/* 47 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*    */ 
/* 53 */   private AssetApprovalManager m_approvalManager = null;
/*    */ 
/*    */   public void setAssetWorkflowManager(AssetWorkflowManager a_wm)
/*    */   {
/* 50 */     this.m_assetWorkflowManager = a_wm;
/*    */   }
/*    */ 
/*    */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*    */   {
/* 56 */     this.m_approvalManager = a_approvalManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 68 */     ActionForward afForward = null;
/* 69 */     AssetApprovalForm form = (AssetApprovalForm)a_form;
/* 70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 73 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 75 */       this.m_logger.debug("ViewAssetApprovalAction: not logged in");
/* 76 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 79 */     long lUserId = userProfile.getUser().getId();
/* 80 */     form.setWorkflows(this.m_assetWorkflowManager.getApprovableWorkflowBeansForUser(a_dbTransaction, lUserId, userProfile.getIsAdmin()));
/* 81 */     Workflow workflow = WorkflowUtil.getSelectedWorkflow(a_request, form.getWorkflows());
/* 82 */     String sWorkflowName = workflow.getName();
/* 83 */     form.setSelectedWorkflow(workflow);
/*    */ 
/* 86 */     Set userPermissionsApproval = null;
/* 87 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 89 */       userPermissionsApproval = userProfile.getPermissionCategoryIds(12);
/*    */     }
/* 91 */     TransitionableAssets list = this.m_assetWorkflowManager.getTransitionableAssetsForApprover(a_dbTransaction, lUserId, userPermissionsApproval, sWorkflowName);
/*    */ 
/* 93 */     form.setApprovalList(list);
/*    */ 
/* 96 */     form.setDownloadedAssetsForApproval(this.m_approvalManager.getAllUsersWithApprovalLists(a_dbTransaction, userProfile.getCategoryIdsAsVector(7)).size());
/*    */ 
/* 98 */     afForward = a_mapping.findForward("Success");
/* 99 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.ViewAssetApprovalAction
 * JD-Core Version:    0.6.0
 */