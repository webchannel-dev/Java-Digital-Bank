/*    */ package com.bright.assetbank.batch.update.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class BulkUpdateBatchSelectForm extends Bn2Form
/*    */ {
/* 31 */   private Vector m_listAlreadyUpdated = null;
/* 32 */   private Vector m_listNotUpdated = null;
/*    */ 
/*    */   public Vector getListAlreadyUpdated()
/*    */   {
/* 36 */     return this.m_listAlreadyUpdated;
/*    */   }
/*    */ 
/*    */   public void setListAlreadyUpdated(Vector a_sListAlreadyUpdated) {
/* 40 */     this.m_listAlreadyUpdated = a_sListAlreadyUpdated;
/*    */   }
/*    */ 
/*    */   public Vector getListNotUpdated() {
/* 44 */     return this.m_listNotUpdated;
/*    */   }
/*    */ 
/*    */   public void setListNotUpdated(Vector a_sListNotUpdated) {
/* 48 */     this.m_listNotUpdated = a_sListNotUpdated;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.form.BulkUpdateBatchSelectForm
 * JD-Core Version:    0.6.0
 */