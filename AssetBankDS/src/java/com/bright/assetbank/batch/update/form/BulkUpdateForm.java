/*    */ package com.bright.assetbank.batch.update.form;
/*    */ 
/*    */ import com.bright.assetbank.application.form.AssetForm;
/*    */ 
/*    */ public class BulkUpdateForm extends AssetForm
/*    */ {
/* 24 */   private int m_iRotateImagesAngle = 0;
/* 25 */   private String m_sWorkflowName = null;
/* 26 */   private boolean m_bUnrelateAssets = false;
/*    */ 
/*    */   public boolean getUnrelateAssets()
/*    */   {
/* 30 */     return this.m_bUnrelateAssets;
/*    */   }
/*    */ 
/*    */   public void setUnrelateAssets(boolean a_bUnrelateAssets)
/*    */   {
/* 35 */     this.m_bUnrelateAssets = a_bUnrelateAssets;
/*    */   }
/*    */ 
/*    */   public void setRotateImagesAngle(int a_iRotateImagesAngle)
/*    */   {
/* 40 */     this.m_iRotateImagesAngle = a_iRotateImagesAngle;
/*    */   }
/*    */ 
/*    */   public int getRotateImagesAngle()
/*    */   {
/* 45 */     return this.m_iRotateImagesAngle;
/*    */   }
/*    */ 
/*    */   public void setSelectedWorkflow(String a_sWorkflowName)
/*    */   {
/* 50 */     this.m_sWorkflowName = a_sWorkflowName;
/*    */   }
/*    */ 
/*    */   public String getSelectedWorkflow()
/*    */   {
/* 55 */     return this.m_sWorkflowName;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.form.BulkUpdateForm
 * JD-Core Version:    0.6.0
 */