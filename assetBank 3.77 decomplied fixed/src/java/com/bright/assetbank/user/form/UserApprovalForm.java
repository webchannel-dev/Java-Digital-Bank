/*    */ package com.bright.assetbank.user.form;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class UserApprovalForm extends UserForm
/*    */ {
/*    */   private Vector m_usersRequiringUpdates;
/*    */ 
/*    */   public Vector getUsersRequiringUpdates()
/*    */   {
/* 34 */     return this.m_usersRequiringUpdates;
/*    */   }
/*    */ 
/*    */   public void setUsersRequiringUpdates(Vector a_sUsersRequiringUpdates) {
/* 38 */     this.m_usersRequiringUpdates = a_sUsersRequiringUpdates;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.UserApprovalForm
 * JD-Core Version:    0.6.0
 */