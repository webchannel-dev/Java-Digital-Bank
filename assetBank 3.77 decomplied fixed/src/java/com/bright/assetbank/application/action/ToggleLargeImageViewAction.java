/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.action.ToggleUserProfileSettingAction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ 
/*    */ public class ToggleLargeImageViewAction extends ToggleUserProfileSettingAction
/*    */ {
/*    */   protected void updateSetting(UserProfile a_userProfile, boolean a_bValue)
/*    */   {
/* 20 */     ABUserProfile userProfile = (ABUserProfile)a_userProfile;
/* 21 */     userProfile.setLargeImagesOnView(a_bValue);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ToggleLargeImageViewAction
 * JD-Core Version:    0.6.0
 */