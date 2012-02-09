/*    */ package com.bright.framework.user.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ListUsersForm extends Bn2Form
/*    */ {
/* 30 */   private Vector m_vecUsers = null;
/*    */ 
/*    */   public void setUsers(Vector a_vecUsers)
/*    */   {
/* 34 */     this.m_vecUsers = a_vecUsers;
/*    */   }
/*    */ 
/*    */   public Vector getUsers()
/*    */   {
/* 39 */     return this.m_vecUsers;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.form.ListUsersForm
 * JD-Core Version:    0.6.0
 */