/*    */ package com.bright.assetbank.workflow.processor;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*    */ import com.bright.framework.workflow.processor.EnterStateProcessorImpl;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class AssetUnsubmittedProcessor extends EnterStateProcessorImpl
/*    */ {
/*    */   public boolean process(DBTransaction a_dbTransaction, WorkflowInfo a_wfInfo)
/*    */     throws Bn2Exception
/*    */   {
/* 42 */     long lAssetId = a_wfInfo.getEntityId();
/*    */ 
/* 45 */     String sWorkflowName = a_wfInfo.getWorkflowName();
/* 46 */     String sStateName = a_wfInfo.getStateName();
/* 47 */     this.m_logger.debug("In AssetSubmittedProcessor:" + sWorkflowName + ", " + sStateName + ", assetid=" + lAssetId);
/*    */ 
/* 49 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.processor.AssetUnsubmittedProcessor
 * JD-Core Version:    0.6.0
 */