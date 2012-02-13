/*    */ package com.bright.assetbank.workflow.processor;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*    */ import com.bright.framework.workflow.processor.EnterStateProcessorImpl;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class AssetSubmittedProcessor extends EnterStateProcessorImpl
/*    */ {
/*    */   public boolean process(DBTransaction a_dbTransaction, WorkflowInfo a_wfInfo)
/*    */     throws Bn2Exception
/*    */   {
/* 41 */     long lAssetId = a_wfInfo.getEntityId();
/*    */ 
/* 44 */     String sWorkflowName = a_wfInfo.getWorkflowName();
/* 45 */     String sStateName = a_wfInfo.getStateName();
/* 46 */     this.m_logger.debug("In AssetSubmittedProcessor:" + sWorkflowName + ", " + sStateName + ", assetid=" + lAssetId);
/*    */ 
/* 48 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.processor.AssetSubmittedProcessor
 * JD-Core Version:    0.6.0
 */