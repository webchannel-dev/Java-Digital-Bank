/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.constant.UserSettings;
/*    */ import com.bright.framework.user.form.LoginForm;
/*    */ import com.bright.framework.user.sso.SSOPlugin;
/*    */ import com.bright.framework.user.sso.SSOPluginFactory;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class SSOLoginAction extends BTransactionAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ActionForward afForward = null;
/* 52 */     LoginForm form = (LoginForm)a_form;
/*    */ 
/* 55 */     String sSSOPluginClassname = UserSettings.getSSOPluginClass();
/* 56 */     SSOPlugin ssoPlugin = SSOPluginFactory.getSSOPluginInstance(sSSOPluginClassname);
/*    */ 
/* 58 */     if (!ssoPlugin.validateForm(form))
/*    */     {
/* 60 */       form.setLoginFailed(true);
/* 61 */       afForward = a_mapping.findForward("Failure");
/*    */     }
/*    */     else
/*    */     {
/* 65 */       String sUrl = ssoPlugin.getLoginUrlForRedirect(a_dbTransaction, a_request, form);
/*    */ 
/* 67 */       if (sUrl != null)
/*    */       {
/* 70 */         afForward = createRedirectingForward(sUrl);
/*    */       }
/*    */       else
/*    */       {
/* 74 */         form.setLoginFailed(true);
/* 75 */         afForward = a_mapping.findForward("Failure");
/*    */       }
/*    */     }
/*    */ 
/* 79 */     return afForward;
/*    */   }
/*    */ 
/*    */   public boolean doPreprocessing()
/*    */   {
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 89 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.SSOLoginAction
 * JD-Core Version:    0.6.0
 */