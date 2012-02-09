/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.user.constant.UserSettings;
/*    */ import com.bright.framework.user.sso.SSOPlugin;
/*    */ import com.bright.framework.user.sso.SSOPluginFactory;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class SSOAuthenticateAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 47 */     ActionForward afForward = null;
/*    */ 
/* 50 */     String sSSOPluginClassname = UserSettings.getSSOPluginClass();
/* 51 */     SSOPlugin ssoPlugin = SSOPluginFactory.getSSOPluginInstance(sSSOPluginClassname);
/*    */ 
/* 53 */     String sSSOMode = ssoPlugin.getSSOMode();
/*    */ 
/* 56 */     afForward = a_mapping.findForward(sSSOMode);
/*    */ 
/* 58 */     return afForward;
/*    */   }
/*    */ 
/*    */   public boolean doPreprocessing()
/*    */   {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 68 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.SSOAuthenticateAction
 * JD-Core Version:    0.6.0
 */