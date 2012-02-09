/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public abstract class ToggleUserProfileSettingAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 35 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 38 */     int iSwitch = getIntParameter(a_request, "switch");
/* 39 */     if (iSwitch > 0)
/*    */     {
/* 41 */       updateSetting(userProfile, true);
/*    */     }
/*    */     else
/*    */     {
/* 45 */       updateSetting(userProfile, false);
/*    */     }
/*    */ 
/* 49 */     ActionForward afForward = createRedirectingForward(a_request.getParameter("returnUrl"));
/* 50 */     return afForward;
/*    */   }
/*    */ 
/*    */   protected abstract void updateSetting(UserProfile paramUserProfile, boolean paramBoolean);
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.ToggleUserProfileSettingAction
 * JD-Core Version:    0.6.0
 */