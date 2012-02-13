/*    */ package com.bright.assetbank.workflow.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.workflow.bean.Workflow;
/*    */ import java.util.Set;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class WorkflowApproverBatchAction extends WorkflowBatchAction
/*    */ {
/*    */   public void setupBatchUpdateController(BatchUpdateControllerImpl batchUpdateController, HttpServletRequest a_request)
/*    */   {
/* 45 */     batchUpdateController.setFinishedUrl("viewAssetUploadApproval?workflowName=" + batchUpdateController.getWorkflow().getName());
/* 46 */     batchUpdateController.setCancelUrl("viewAssetUploadApproval?workflowName=" + batchUpdateController.getWorkflow().getName());
/*    */ 
/* 49 */     batchUpdateController.setApproval(true);
/* 50 */     batchUpdateController.setUnsubmitted(false);
/* 51 */     batchUpdateController.setOwner(false);
/*    */   }
/*    */ 
/*    */   public Vector getAssets(DBTransaction a_dbTransaction, String a_sStateName, int a_iMaxBatchSize, ABUserProfile a_userProfile, HttpServletRequest a_request, String a_sWorkflowName)
/*    */     throws Bn2Exception
/*    */   {
/* 59 */     Set userPermissions = null;
/* 60 */     if (!a_userProfile.getIsAdmin())
/*    */     {
/* 62 */       userPermissions = a_userProfile.getPermissionCategoryIds(12);
/*    */     }
/* 64 */     Vector assetsForUpdate = this.m_assetWorkflowManager.getAssetsForBatchUpdate(a_dbTransaction, a_sStateName, a_iMaxBatchSize, userPermissions, a_sWorkflowName);
/*    */ 
/* 66 */     return assetsForUpdate;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.WorkflowApproverBatchAction
 * JD-Core Version:    0.6.0
 */