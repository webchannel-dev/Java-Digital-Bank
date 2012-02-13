/*    */ package com.bright.assetbank.approval.form;
/*    */ 
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ 
/*    */ public class AssetApprovalForm extends AssetApprovalListForm
/*    */ {
/*    */   private ABUser m_sUser;
/*    */ 
/*    */   public ABUser getUser()
/*    */   {
/* 45 */     return this.m_sUser;
/*    */   }
/*    */ 
/*    */   public void setUser(ABUser a_sUser)
/*    */   {
/* 54 */     this.m_sUser = a_sUser;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.form.AssetApprovalForm
 * JD-Core Version:    0.6.0
 */