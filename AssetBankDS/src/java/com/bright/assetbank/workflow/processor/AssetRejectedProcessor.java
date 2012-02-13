/*    */ package com.bright.assetbank.workflow.processor;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*    */ import com.bright.framework.workflow.processor.EnterStateProcessorImpl;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class AssetRejectedProcessor extends EnterStateProcessorImpl
/*    */ {
/*    */   private AssetWorkflowManager m_assetWorkflowManager;
/*    */ 
/*    */   public void setAssetWorkflowManager(AssetWorkflowManager a_awm)
/*    */   {
/* 36 */     this.m_assetWorkflowManager = a_awm;
/*    */   }
/*    */ 
/*    */   public boolean process(DBTransaction a_dbTransaction, WorkflowInfo a_wfInfo)
/*    */     throws Bn2Exception
/*    */   {
/* 43 */     long lAssetId = a_wfInfo.getEntityId();
/*    */ 
/* 46 */     String sWorkflowName = a_wfInfo.getWorkflowName();
/* 47 */     String sStateName = a_wfInfo.getStateName();
/* 48 */     this.m_logger.debug("In AssetRejectedProcessor:" + sWorkflowName + ", " + sStateName + ", assetid=" + lAssetId);
/*    */ 
/* 51 */    // this.m_assetWorkflowManager.rejectAsset(a_dbTransaction, lAssetId);
/*    */ 
/* 53 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.processor.AssetRejectedProcessor
 * JD-Core Version:    0.6.0
 */