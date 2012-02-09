/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ 
/*    */ public class DownloadSharedAssetForm extends Bn2Form
/*    */ {
/* 27 */   private boolean conditionsAccepted = true;
/* 28 */   private boolean fileExpired = false;
/*    */ 
/*    */   public void setConditionsAccepted(boolean conditionsAccepted)
/*    */   {
/* 32 */     this.conditionsAccepted = conditionsAccepted;
/*    */   }
/*    */ 
/*    */   public boolean getConditionsAccepted()
/*    */   {
/* 37 */     return this.conditionsAccepted;
/*    */   }
/*    */ 
/*    */   public void setFileExpired(boolean fileExpired)
/*    */   {
/* 42 */     this.fileExpired = fileExpired;
/*    */   }
/*    */ 
/*    */   public boolean getFileExpired()
/*    */   {
/* 47 */     return this.fileExpired;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.DownloadSharedAssetForm
 * JD-Core Version:    0.6.0
 */