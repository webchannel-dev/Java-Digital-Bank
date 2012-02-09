/*    */ package com.bright.assetbank.user.bean;
/*    */ 
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.user.bean.UserProfileFactory;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class ABUserProfileFactory extends UserProfileFactory
/*    */ {
/*    */   public UserProfile createUserProfile(HttpSession a_session)
/*    */   {
/* 46 */     ABUserProfile profile = new ABUserProfile();
/*    */ 
/* 49 */     if (a_session.getAttribute("currentLanguage") != null)
/*    */     {
/* 51 */       profile.setCurrentLanguage((Language)a_session.getAttribute("currentLanguage"));
/*    */     }
/*    */ 
/* 54 */     return profile;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.ABUserProfileFactory
 * JD-Core Version:    0.6.0
 */