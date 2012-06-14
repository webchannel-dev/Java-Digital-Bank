/*    */ package com.bright.assetbank.batch.update.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class StatusForm extends Bn2Form
/*    */ {
/* 31 */   private boolean m_bInProgress = false;
/* 32 */   private Vector m_vecMessages = null;
/*    */ 
/*    */   public boolean getInProgress()
/*    */   {
/* 36 */     return this.m_bInProgress;
/*    */   }
/*    */ 
/*    */   public void setInProgress(boolean a_bInProgress) {
/* 40 */     this.m_bInProgress = a_bInProgress;
/*    */   }
/*    */ 
/*    */   public Vector getMessages() {
/* 44 */     return this.m_vecMessages;
/*    */   }
/*    */ 
/*    */   public void setMessages(Vector a_vecMessages) {
/* 48 */     this.m_vecMessages = a_vecMessages;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.form.StatusForm
 * JD-Core Version:    0.6.0
 */