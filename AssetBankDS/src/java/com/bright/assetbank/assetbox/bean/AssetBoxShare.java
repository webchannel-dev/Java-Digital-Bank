/*    */ package com.bright.assetbank.assetbox.bean;
/*    */ 
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ 
/*    */ public class AssetBoxShare
/*    */ {
/*    */   private ABUser m_user;
/*    */   private boolean m_bHasEditPermission;
/*    */ 
/*    */   public boolean getHasEditPermission()
/*    */   {
/* 29 */     return this.m_bHasEditPermission;
/*    */   }
/*    */ 
/*    */   public void setHasEditPermission(boolean a_bHasEditPermission)
/*    */   {
/* 34 */     this.m_bHasEditPermission = a_bHasEditPermission;
/*    */   }
/*    */ 
/*    */   public ABUser getUser()
/*    */   {
/* 39 */     return this.m_user;
/*    */   }
/*    */ 
/*    */   public void setUser(ABUser user)
/*    */   {
/* 44 */     this.m_user = user;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.bean.AssetBoxShare
 * JD-Core Version:    0.6.0
 */