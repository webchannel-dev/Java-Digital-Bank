/*     */ package com.bright.framework.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.activedirectory.constant.ActiveDirectorySettings;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import com.bright.framework.user.sso.SSOPlugin;
/*     */ import com.bright.framework.user.sso.SSOPluginFactory;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SSOHTTPRequestTokenAction extends LoginAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     ActionForward afForward = null;
/*  60 */     LoginForm form = (LoginForm)a_form;
/*     */ 
/*  62 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  66 */     String sRedirectUrl = (String)a_request.getSession().getAttribute("OriginalRequestUrl");
/*  67 */     form.setForwardUrl(sRedirectUrl);
/*     */ 
/*  71 */     String sSSOPluginClassname = UserSettings.getSSOPluginClass();
/*  72 */     SSOPlugin ssoPlugin = SSOPluginFactory.getSSOPluginInstance(sSSOPluginClassname);
/*  73 */     User remoteUser = ssoPlugin.getRemoteUser(a_request);
/*     */ 
/*  75 */     if (remoteUser != null)
/*     */     {
/*  78 */       String sTokenUsername = remoteUser.getRemoteUsername();
/*  79 */       long lId = this.m_userManager.getUserIdForLocalUsername(a_dbTransaction, sTokenUsername);
/*     */ 
/*  82 */       boolean bImportSSOUsers = (FrameworkSettings.getImportRemoteUsersOnTheFly()) && (ActiveDirectorySettings.getSuspendADAuthentication());
/*     */ 
/*  85 */       User localUser = SSOCreateSessionAction.getLocalUser(lId, remoteUser, a_dbTransaction, this.m_userManager, bImportSSOUsers, userProfile.getCurrentLanguage().getId());
/*     */ 
/*  87 */       if (localUser == null)
/*     */       {
/*  90 */         form.addError("You haven't been set up as a user for this application - please contact an administrator.");
/*  91 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/*  95 */       String sLocalUsername = localUser.getUsername();
/*     */ 
/*  97 */       afForward = doLogin(a_dbTransaction, a_mapping, form, a_request, a_response, sLocalUsername, "", true);
/*     */     }
/*     */     else
/*     */     {
/* 108 */       String sFailureUrl = ssoPlugin.getSessionCheckUrl(a_dbTransaction, a_request, form);
/* 109 */       if (sFailureUrl != null)
/*     */       {
/* 111 */         afForward = createRedirectingForward(sFailureUrl);
/*     */       }
/*     */       else
/*     */       {
/* 115 */         afForward = a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 120 */     return afForward;
/*     */   }
/*     */ 
/*     */   public boolean doPreprocessing()
/*     */   {
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 130 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.SSOHTTPRequestTokenAction
 * JD-Core Version:    0.6.0
 */