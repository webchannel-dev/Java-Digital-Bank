/*    */ package com.bright.framework.common.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class LoggedInForwardAction extends Bn2ForwardAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 41 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 43 */     if ((userProfile != null) && (userProfile.getIsLoggedIn()))
/*    */     {
/* 45 */       return super.execute(a_mapping, a_form, a_request, a_response);
/*    */     }
/*    */ 
/* 48 */     return a_mapping.findForward("NoPermission");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.action.LoggedInForwardAction
 * JD-Core Version:    0.6.0
 */