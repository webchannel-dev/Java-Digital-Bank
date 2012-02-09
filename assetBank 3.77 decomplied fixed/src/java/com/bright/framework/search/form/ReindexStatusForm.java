/*    */ package com.bright.framework.search.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ReindexStatusForm extends Bn2Form
/*    */ {
/* 31 */   private boolean m_bReindexInProgress = false;
/* 32 */   private Vector m_vecMessages = null;
/*    */ 
/*    */   public void setReindexInProgress(boolean a_bReindexInProgress)
/*    */   {
/* 36 */     this.m_bReindexInProgress = a_bReindexInProgress;
/*    */   }
/*    */ 
/*    */   public boolean getReindexInProgress()
/*    */   {
/* 41 */     return this.m_bReindexInProgress;
/*    */   }
/*    */ 
/*    */   public void setMessages(Vector a_vecMessages)
/*    */   {
/* 46 */     this.m_vecMessages = a_vecMessages;
/*    */   }
/*    */ 
/*    */   public Vector getMessages()
/*    */   {
/* 51 */     return this.m_vecMessages;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.form.ReindexStatusForm
 * JD-Core Version:    0.6.0
 */