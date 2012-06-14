/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class LoginAsUserAction extends LoginWithAttributeAction
/*    */ {
/*    */   protected boolean checkPermissions(UserProfile a_userProfile)
/*    */   {
/* 36 */     return a_userProfile.getIsAdmin();
/*    */   }
/*    */ 
/*    */   protected void setupOtherAttributes(HttpServletRequest a_request, UserProfile a_userProfile)
/*    */   {
/* 43 */     a_request.setAttribute("requestUserId", new Long(a_userProfile.getUser().getId()));
/*    */   }
/*    */ 
/*    */   protected long getUserId(HttpServletRequest a_request, UserProfile a_userProfile)
/*    */   {
/* 48 */     return getLongParameter(a_request, "id");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.LoginAsUserAction
 * JD-Core Version:    0.6.0
 */