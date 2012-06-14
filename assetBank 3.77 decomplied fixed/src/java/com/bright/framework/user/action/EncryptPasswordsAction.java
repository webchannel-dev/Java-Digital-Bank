/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.util.UserUtil;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.user.constant.UserSettings;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class EncryptPasswordsAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 58 */     ActionForward afForward = null;
/* 59 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 61 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 63 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 67 */     if (!UserSettings.getEncryptPasswords())
/*    */     {
/* 69 */       UserUtil.encryptPasswords();
/*    */     }
/*    */ 
/* 72 */     afForward = a_mapping.findForward("Success");
/* 73 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.EncryptPasswordsAction
 * JD-Core Version:    0.6.0
 */