/*     */ package com.bright.framework.user.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Brand;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import com.bright.framework.user.sso.SSOPlugin;
/*     */ import com.bright.framework.user.sso.SSOPluginFactory;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class LogoutAction extends Bn2Action
/*     */   implements CommonConstants
/*     */ {
/*  49 */   private UserManager m_userManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     this.m_logger.debug("In LogoutAction.execute");
/*  69 */     ActionForward afForward = null;
/*     */ 
/*  71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request);
/*     */ 
/*  74 */     String sBrand = userProfile.getBrand().getCode();
/*     */ 
/*  77 */     if (UserSettings.getAllowAutoLoginUsingCookie())
/*     */     {
/*  79 */       this.m_userManager.removeUsernameCookie(a_response);
/*     */ 
/*  82 */       userProfile.setCheckedCookies(true);
/*     */     }
/*     */ 
/*  86 */     this.m_userManager.logout(a_request, a_response);
/*     */ 
/*  90 */     if (UserSettings.getSSOIsEnabled())
/*     */     {
/*  92 */       String sSSOPluginClassname = UserSettings.getSSOPluginClass();
/*  93 */       SSOPlugin ssoPlugin = SSOPluginFactory.getSSOPluginInstance(sSSOPluginClassname);
/*     */ 
/*  96 */       ssoPlugin.doPreLogoutActions(a_response);
/*     */ 
/*  99 */       String sUrl = ssoPlugin.getLogoutUrl(a_request, a_response);
/*     */ 
/* 101 */       if (sUrl != null)
/*     */       {
/* 104 */         afForward = createRedirectingForward(sUrl);
/* 105 */         return afForward;
/*     */       }
/*     */     }
/*     */ 
/* 109 */     if (StringUtil.stringIsPopulated(sBrand))
/*     */     {
/* 112 */       afForward = createRedirectingForward(AssetBankSettings.getBrandParameter() + "=" + sBrand, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 116 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 119 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setUserManager(UserManager a_userManager)
/*     */   {
/* 130 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.LogoutAction
 * JD-Core Version:    0.6.0
 */