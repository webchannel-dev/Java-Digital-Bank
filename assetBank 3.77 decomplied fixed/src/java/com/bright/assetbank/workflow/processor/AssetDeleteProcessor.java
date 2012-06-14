/*    */ package com.bright.assetbank.workflow.processor;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*    */ import com.bright.framework.workflow.processor.EnterStateProcessorImpl;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class AssetDeleteProcessor extends EnterStateProcessorImpl
/*    */ {
/*    */   private AssetWorkflowManager m_assetWorkflowManager;
/*    */ 
/*    */   public void setAssetWorkflowManager(AssetWorkflowManager a_awm)
/*    */   {
/* 37 */     this.m_assetWorkflowManager = a_awm;
/*    */   }
/*    */ 
/*    */   public boolean process(DBTransaction a_dbTransaction, WorkflowInfo a_wfInfo)
/*    */     throws Bn2Exception
/*    */   {
/* 44 */     long lAssetId = a_wfInfo.getEntityId();
/*    */ 
/* 47 */     String sWorkflowName = a_wfInfo.getWorkflowName();
/* 48 */     String sStateName = a_wfInfo.getStateName();
/* 49 */     this.m_logger.debug("In AssetDeleteProcessor:" + sWorkflowName + ", " + sStateName + ", assetid=" + lAssetId);
/*    */ 
/* 52 */     this.m_assetWorkflowManager.deleteAsset(a_dbTransaction, lAssetId, a_wfInfo.getId());
/*    */ 
/* 54 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.processor.AssetDeleteProcessor
 * JD-Core Version:    0.6.0
 */