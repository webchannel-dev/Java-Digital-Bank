/*    */ package com.bright.assetbank.orgunit.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.bean.Group;
/*    */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ViewOrgUnitDetailsAction extends ViewOrgUnitAndUsersAction
/*    */ {
/* 35 */   private ABUserManager m_userManager = null;
/*    */ 
/*    */   public void setUserManager(ABUserManager a_userManager) {
/* 38 */     this.m_userManager = a_userManager;
/*    */   }
/*    */ 
/*    */   protected Vector<ABUser> getUsers(OrgUnit a_ou)
/*    */     throws Bn2Exception
/*    */   {
/* 48 */     UserSearchCriteria search = new UserSearchCriteria();
/* 49 */     search.setGroupId(a_ou.getAdminGroup().getId());
/* 50 */     Vector vecAdminUsers = this.m_userManager.findUsers(search, 0);
/* 51 */     return vecAdminUsers;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.ViewOrgUnitDetailsAction
 * JD-Core Version:    0.6.0
 */