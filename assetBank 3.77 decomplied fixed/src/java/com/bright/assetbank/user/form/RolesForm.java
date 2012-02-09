/*    */ package com.bright.assetbank.user.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.user.bean.Group;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class RolesForm extends Bn2Form
/*    */ {
/* 31 */   private Group m_group = null;
/* 32 */   private Vector m_vecRoles = null;
/*    */ 
/*    */   public void setGroup(Group a_group)
/*    */   {
/* 36 */     this.m_group = a_group;
/*    */   }
/*    */ 
/*    */   public Group getGroup()
/*    */   {
/* 41 */     return this.m_group;
/*    */   }
/*    */ 
/*    */   public void setRoles(Vector a_vecRoles)
/*    */   {
/* 46 */     this.m_vecRoles = a_vecRoles;
/*    */   }
/*    */ 
/*    */   public Vector getRoles()
/*    */   {
/* 51 */     return this.m_vecRoles;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.RolesForm
 * JD-Core Version:    0.6.0
 */