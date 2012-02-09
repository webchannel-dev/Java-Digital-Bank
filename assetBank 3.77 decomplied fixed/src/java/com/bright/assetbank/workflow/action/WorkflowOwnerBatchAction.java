/*    */ package com.bright.assetbank.workflow.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.workflow.bean.Workflow;
/*    */ import java.util.Set;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class WorkflowOwnerBatchAction extends WorkflowBatchAction
/*    */ {
/*    */   public void setupBatchUpdateController(BatchUpdateControllerImpl batchUpdateController, HttpServletRequest a_request)
/*    */   {
/* 44 */     String sUrl = "viewOwnerAssetApproval?workflowName=" + batchUpdateController.getWorkflow().getName();
/* 45 */     batchUpdateController.setFinishedUrl(sUrl);
/* 46 */     batchUpdateController.setCancelUrl(sUrl);
/*    */ 
/* 49 */     batchUpdateController.setApproval(true);
/* 50 */     batchUpdateController.setUnsubmitted(false);
/* 51 */     batchUpdateController.setOwner(true);
/*    */   }
/*    */ 
/*    */   public Vector getAssets(DBTransaction a_dbTransaction, String a_sStateName, int a_iMaxBatchSize, ABUserProfile a_userProfile, HttpServletRequest a_request, String a_sWorkflowName)
/*    */     throws Bn2Exception
/*    */   {
/* 60 */     Set userPermissions = null;
/* 61 */     if (!a_userProfile.getIsAdmin())
/*    */     {
/* 63 */       userPermissions = a_userProfile.getVisiblePermissionCategories();
/*    */     }
/*    */ 
/* 67 */     Vector assetsForUpdate = this.m_assetWorkflowManager.getAssetsForBatchUpdateOwner(a_dbTransaction, a_sStateName, a_iMaxBatchSize, userPermissions, a_userProfile.getUser().getId(), a_sWorkflowName);
/*    */ 
/* 69 */     return assetsForUpdate;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.WorkflowOwnerBatchAction
 * JD-Core Version:    0.6.0
 */