/*    */ package com.bright.assetbank.user.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ 
/*    */ public class InviteUsersForm extends Bn2Form
/*    */ {
/* 31 */   private String m_sMessageBody = null;
/* 32 */   private String m_sCompleteBody = null;
/* 33 */   private String m_sTo = null;
/*    */ 
/* 35 */   private boolean m_bInvitesSent = false;
/*    */ 
/*    */   public String getMessageBody()
/*    */   {
/* 39 */     return this.m_sMessageBody;
/*    */   }
/*    */ 
/*    */   public void setMessageBody(String a_sMessageBody)
/*    */   {
/* 44 */     this.m_sMessageBody = a_sMessageBody;
/*    */   }
/*    */ 
/*    */   public void setCompleteBody(String a_sCompleteBody)
/*    */   {
/* 49 */     this.m_sCompleteBody = a_sCompleteBody;
/*    */   }
/*    */ 
/*    */   public String getCompleteBody()
/*    */   {
/* 54 */     return this.m_sCompleteBody;
/*    */   }
/*    */ 
/*    */   public String getTo()
/*    */   {
/* 59 */     return this.m_sTo;
/*    */   }
/*    */ 
/*    */   public void setTo(String a_sTo)
/*    */   {
/* 64 */     this.m_sTo = a_sTo;
/*    */   }
/*    */ 
/*    */   public boolean getInvitesSent()
/*    */   {
/* 69 */     return this.m_bInvitesSent;
/*    */   }
/*    */ 
/*    */   public void setInvitesSent(boolean a_bInvitesSent)
/*    */   {
/* 74 */     this.m_bInvitesSent = a_bInvitesSent;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.InviteUsersForm
 * JD-Core Version:    0.6.0
 */