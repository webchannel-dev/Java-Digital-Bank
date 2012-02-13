/*    */ package com.bright.assetbank.autocomplete.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ACReindexStatusForm extends Bn2Form
/*    */ {
/* 29 */   private boolean m_bReindexInProgress = false;
/* 30 */   private List m_messages = null;
/*    */ 
/*    */   public void setReindexInProgress(boolean a_bReindexInProgress)
/*    */   {
/* 34 */     this.m_bReindexInProgress = a_bReindexInProgress;
/*    */   }
/*    */ 
/*    */   public boolean getReindexInProgress()
/*    */   {
/* 39 */     return this.m_bReindexInProgress;
/*    */   }
/*    */ 
/*    */   public void setMessages(List a_vecMessages)
/*    */   {
/* 44 */     this.m_messages = a_vecMessages;
/*    */   }
/*    */ 
/*    */   public List getMessages()
/*    */   {
/* 49 */     return this.m_messages;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.form.ACReindexStatusForm
 * JD-Core Version:    0.6.0
 */