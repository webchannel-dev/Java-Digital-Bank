/*    */ package com.bright.assetbank.category.action;
/*    */ 
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.action.ToggleUserProfileSettingAction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ 
/*    */ public class ToggleListViewAction extends ToggleUserProfileSettingAction
/*    */ {
/*    */   protected void updateSetting(UserProfile a_userProfile, boolean a_bValue)
/*    */   {
/* 24 */     ABUserProfile userProfile = (ABUserProfile)a_userProfile;
/* 25 */     userProfile.setListView(a_bValue);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.ToggleListViewAction
 * JD-Core Version:    0.6.0
 */