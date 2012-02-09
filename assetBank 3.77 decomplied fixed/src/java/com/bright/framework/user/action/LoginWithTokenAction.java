/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.form.LoginForm;
/*    */ import com.bright.framework.user.sso.CookieTokenSSOPlugin;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ /** @deprecated */
/*    */ public class LoginWithTokenAction extends LoginAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 60 */     ActionForward afForward = null;
/*    */ 
/* 62 */     LoginForm loginForm = (LoginForm)a_form;
/*    */ 
/* 65 */     String sUsername = CookieTokenSSOPlugin.getUsernameFromCookie(a_request, "AssetBankToken", "rty67pk$");
/* 66 */     if (sUsername != null)
/*    */     {
/* 68 */       afForward = doLogin(a_dbTransaction, a_mapping, loginForm, a_request, a_response, sUsername, "", true);
/*    */     }
/*    */     else
/*    */     {
/* 79 */       this.m_logger.error("LoginWithTokenAction: user failed to log in " + sUsername);
/* 80 */       afForward = a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 84 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.LoginWithTokenAction
 * JD-Core Version:    0.6.0
 */