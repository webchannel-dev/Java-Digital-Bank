/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.constant.UserSettings;
/*    */ import com.bright.framework.user.form.LoginForm;
/*    */ import com.bright.framework.user.sso.SSOPlugin;
/*    */ import com.bright.framework.user.sso.SSOPluginFactory;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class SSOSessionCheckAction extends BTransactionAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 46 */     ActionForward afForward = null;
/* 47 */     LoginForm form = (LoginForm)a_form;
/*    */ 
/* 50 */     ViewLoginAction.populateLoginFormForwardUrl(form, a_request);
/*    */ 
/* 53 */     String sSSOPluginClassname = UserSettings.getSSOPluginClass();
/* 54 */     SSOPlugin ssoPlugin = SSOPluginFactory.getSSOPluginInstance(sSSOPluginClassname);
/*    */ 
/* 58 */     User remoteUser = ssoPlugin.initialSessionCheck(a_request, a_response);
/*    */ 
/* 61 */     if ((remoteUser != null) && (StringUtil.stringIsPopulated(remoteUser.getRemoteUsername())))
/*    */     {
/* 63 */       form.setUser(remoteUser);
/* 64 */       return a_mapping.findForward("Login");
/*    */     }
/*    */ 
/* 67 */     String sUrl = ssoPlugin.getSessionCheckUrl(a_dbTransaction, a_request, form);
/*    */ 
/* 69 */     if (sUrl != null)
/*    */     {
/* 72 */       afForward = createRedirectingForward(sUrl);
/*    */     }
/*    */     else
/*    */     {
/* 76 */       form.setLoginFailed(true);
/* 77 */       afForward = a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 80 */     return afForward;
/*    */   }
/*    */ 
/*    */   public boolean doPreprocessing()
/*    */   {
/* 85 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 90 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.SSOSessionCheckAction
 * JD-Core Version:    0.6.0
 */