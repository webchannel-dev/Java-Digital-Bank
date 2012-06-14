/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.user.constant.UserSettings;
/*    */ import com.bright.framework.user.form.LoginForm;
/*    */ import com.bright.framework.user.sso.SSOPlugin;
/*    */ import com.bright.framework.user.sso.SSOPluginFactory;
/*    */ import java.util.HashMap;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewSSOLoginAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ActionForward afForward = null;
/* 52 */     LoginForm loginForm = (LoginForm)a_form;
/*    */ 
/* 55 */     String sSSOPluginClassname = UserSettings.getSSOPluginClass();
/* 56 */     SSOPlugin ssoPlugin = SSOPluginFactory.getSSOPluginInstance(sSSOPluginClassname);
/*    */ 
/* 60 */     ssoPlugin.processRequest(a_request, loginForm);
/*    */ 
/* 62 */     if (!loginForm.getHasErrors())
/*    */     {
/* 66 */       ViewLoginAction.populateLoginFormForwardUrl(loginForm, a_request);
/*    */     }
/*    */ 
/* 70 */     String sBaseUrl = ssoPlugin.getLoginBaseUrl();
/* 71 */     loginForm.setSsoBaseUrl(sBaseUrl);
/* 72 */     HashMap hmSSOForm = ssoPlugin.getLoginFormForPost(a_request);
/* 73 */     loginForm.setSsoForm(hmSSOForm);
/* 74 */     if (hmSSOForm != null)
/*    */     {
/* 76 */       loginForm.setSsoFormKeys(hmSSOForm.keySet());
/*    */     }
/*    */ 
/* 81 */     String sJspForward = ssoPlugin.getLoginJsp();
/* 82 */     afForward = new ActionForward(sJspForward);
/*    */ 
/* 84 */     return afForward;
/*    */   }
/*    */ 
/*    */   public boolean doPreprocessing()
/*    */   {
/* 89 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 94 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.ViewSSOLoginAction
 * JD-Core Version:    0.6.0
 */