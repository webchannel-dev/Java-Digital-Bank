/*    */ package com.bright.assetbank.workflow.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class WorkflowAuditForm extends Bn2Form
/*    */ {
/*    */   private Vector m_vecApprovalAudit;
/* 27 */   private long m_lAssetId = 0L;
/*    */ 
/*    */   public Vector getApprovalAudit()
/*    */   {
/* 32 */     return this.m_vecApprovalAudit;
/*    */   }
/*    */ 
/*    */   public void setApprovalAudit(Vector a_vecApprovalAudit) {
/* 36 */     this.m_vecApprovalAudit = a_vecApprovalAudit;
/*    */   }
/*    */ 
/*    */   public long getAssetId()
/*    */   {
/* 41 */     return this.m_lAssetId;
/*    */   }
/*    */ 
/*    */   public void setAssetId(long a_lAssetId) {
/* 45 */     this.m_lAssetId = a_lAssetId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.form.WorkflowAuditForm
 * JD-Core Version:    0.6.0
 */