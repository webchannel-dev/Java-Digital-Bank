/*    */ package com.bright.assetbank.workflow.processor;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*    */ import com.bright.framework.workflow.processor.EnterStateProcessorImpl;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class AssetApprovedProcessor extends EnterStateProcessorImpl
/*    */ {
/*    */   private AssetWorkflowManager m_assetWorkflowManager;
/*    */ 
/*    */   public void setAssetWorkflowManager(AssetWorkflowManager a_awm)
/*    */   {
/* 38 */     this.m_assetWorkflowManager = a_awm;
/*    */   }
/*    */ 
/*    */   public boolean process(DBTransaction a_dbTransaction, WorkflowInfo a_wfInfo)
/*    */     throws Bn2Exception
/*    */   {
/* 45 */     long lAssetId = a_wfInfo.getEntityId();
/*    */ 
/* 48 */     String sWorkflowName = a_wfInfo.getWorkflowName();
/* 49 */     String sStateName = a_wfInfo.getStateName();
/* 50 */     this.m_logger.debug("In AssetApprovedProcessor:" + sWorkflowName + ", " + sStateName + ", assetid=" + lAssetId);
/*    */ 
/* 53 */     this.m_assetWorkflowManager.approveAssetEndWorkflow(a_dbTransaction, lAssetId, a_wfInfo.getId());
/*    */ 
/* 55 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.processor.AssetApprovedProcessor
 * JD-Core Version:    0.6.0
 */