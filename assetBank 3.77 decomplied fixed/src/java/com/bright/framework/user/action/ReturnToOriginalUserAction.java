/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ReturnToOriginalUserAction extends LoginWithAttributeAction
/*    */ {
/*    */   protected boolean checkPermissions(UserProfile a_userProfile)
/*    */   {
/* 36 */     return a_userProfile.getOriginalUserId() > 0L;
/*    */   }
/*    */ 
/*    */   protected void setupOtherAttributes(HttpServletRequest a_request, UserProfile a_userProfile)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected long getUserId(HttpServletRequest a_request, UserProfile a_userProfile)
/*    */   {
/* 48 */     return a_userProfile.getOriginalUserId();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.ReturnToOriginalUserAction
 * JD-Core Version:    0.6.0
 */