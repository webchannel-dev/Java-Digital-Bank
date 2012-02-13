/*    */ package com.bright.assetbank.workflow.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class WorkflowOwnerBatchAdminAction extends WorkflowBatchAction
/*    */ {
/*    */   public void setupBatchUpdateController(BatchUpdateControllerImpl batchUpdateController, HttpServletRequest a_request)
/*    */   {
/* 35 */     String sUrl = "viewOwnerAssetApprovalAdmin?selectedUserId=" + getSelectedUserId(a_request);
/* 36 */     batchUpdateController.setFinishedUrl(sUrl);
/* 37 */     batchUpdateController.setCancelUrl(sUrl);
/*    */ 
/* 40 */     batchUpdateController.setApproval(true);
/* 41 */     batchUpdateController.setUnsubmitted(false);
/* 42 */     batchUpdateController.setOwner(true);
/*    */   }
/*    */ 
/*    */   public Vector getAssets(DBTransaction a_dbTransaction, String a_sStateName, int a_iMaxBatchSize, ABUserProfile a_userProfile, HttpServletRequest a_request, String a_sWorkflowName)
/*    */     throws Bn2Exception
/*    */   {
/* 55 */     Vector assetsForUpdate = this.m_assetWorkflowManager.getAssetsForBatchUpdateOwner(a_dbTransaction, a_sStateName, a_iMaxBatchSize, null, getSelectedUserId(a_request), a_sWorkflowName);
/*    */ 
/* 59 */     return assetsForUpdate;
/*    */   }
/*    */ 
/*    */   private long getSelectedUserId(HttpServletRequest a_request)
/*    */   {
/* 64 */     long lUserId = getLongParameter(a_request, "selectedUserId");
/* 65 */     return lUserId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.action.WorkflowOwnerBatchAdminAction
 * JD-Core Version:    0.6.0
 */