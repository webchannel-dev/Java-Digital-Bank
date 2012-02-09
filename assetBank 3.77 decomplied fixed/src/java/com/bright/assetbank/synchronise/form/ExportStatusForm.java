/*    */ package com.bright.assetbank.synchronise.form;
/*    */ 
/*    */ import com.bright.assetbank.batch.update.form.StatusForm;
/*    */ import com.bright.assetbank.synchronise.bean.ExportResult;
/*    */ 
/*    */ public class ExportStatusForm extends StatusForm
/*    */ {
/* 30 */   private ExportResult m_result = null;
/*    */ 
/*    */   public void setResult(ExportResult a_result)
/*    */   {
/* 34 */     this.m_result = a_result;
/*    */   }
/*    */ 
/*    */   public ExportResult getResult()
/*    */   {
/* 39 */     return this.m_result;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.form.ExportStatusForm
 * JD-Core Version:    0.6.0
 */