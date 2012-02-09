/*     */ package com.bright.framework.user.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.customfield.service.CustomFieldManager;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewLoginAction extends Bn2Action
/*     */ {
/*  51 */   private CustomFieldManager m_customFieldManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  60 */     ActionForward afForward = null;
/*     */ 
/*  63 */     String sElectLocal = a_request.getParameter("local");
/*  64 */     boolean bElectLocal = (StringUtil.stringIsPopulated(sElectLocal)) && (sElectLocal.equals("true"));
/*     */ 
/*  66 */     if ((!bElectLocal) && (a_request.getAttribute("local") != null))
/*     */     {
/*     */       try
/*     */       {
/*  70 */         bElectLocal = ((Boolean)a_request.getAttribute("local")).booleanValue();
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*     */     }
/*  75 */     if ((UserSettings.getSSOIsEnabled()) && ((FrameworkSettings.getForceRemoteAuthentication()) || ((UserSettings.getSSOLoginIsDefault()) && (!bElectLocal))))
/*     */     {
/*  77 */       afForward = a_mapping.findForward("LoginWithSSO");
/*  78 */       return afForward;
/*     */     }
/*     */ 
/*  81 */     LoginForm loginForm = (LoginForm)a_form;
/*     */ 
/*  83 */     populateLoginFormForwardUrl(loginForm, a_request);
/*     */ 
/*  86 */     loginForm.setCanHaveExternalUsers((this.m_customFieldManager.subtypeFieldsExist(null, 1L)) || (StringUtils.isNotEmpty(AssetBankSettings.getLocalUserEmailDomain())));
/*     */ 
/*  90 */     afForward = a_mapping.findForward("Success");
/*  91 */     return afForward;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static void populateLoginFormFlags(LoginForm loginForm, HttpServletRequest a_request)
/*     */   {
/* 108 */     if (a_request.getSession().getAttribute("LoggedOut") != null)
/*     */     {
/* 110 */       loginForm.setLoggedOut(Boolean.TRUE.equals(a_request.getSession().getAttribute("LoggedOut")));
/*     */     }
/* 112 */     if (a_request.getSession().getAttribute("AccountSuspended") != null)
/*     */     {
/* 114 */       loginForm.setAccountSuspended(Boolean.TRUE.equals(a_request.getSession().getAttribute("AccountSuspended")));
/*     */     }
/* 116 */     if (a_request.getSession().getAttribute("AccountExpired") != null)
/*     */     {
/* 118 */       loginForm.setAccountSuspended(Boolean.TRUE.equals(a_request.getSession().getAttribute("AccountExpired")));
/*     */     }
/* 120 */     if (a_request.getSession().getAttribute("LoginFailed") != null)
/*     */     {
/* 122 */       loginForm.setLoginFailed(Boolean.TRUE.equals(a_request.getSession().getAttribute("LoginFailed")));
/*     */     }
/* 124 */     if (a_request.getSession().getAttribute("UsernameInUse") != null)
/*     */     {
/* 126 */       loginForm.setUsernameInUse(Boolean.TRUE.equals(a_request.getSession().getAttribute("UsernameInUse")));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void populateLoginFormForwardUrl(LoginForm loginForm, HttpServletRequest a_request)
/*     */   {
/* 141 */     String sRedirectUrl = a_request.getParameter("redirecturl");
/*     */ 
/* 144 */     if (sRedirectUrl == null)
/*     */     {
/* 146 */       sRedirectUrl = (String)a_request.getSession().getAttribute("OriginalRequestUrl");
/*     */     }
/*     */ 
/* 150 */     if (sRedirectUrl == null)
/*     */     {
/* 152 */       sRedirectUrl = RequestUtil.getOriginalRequestUrl(a_request);
/*     */     }
/*     */ 
/* 156 */     if (RequestUtil.getIsUrlPostLoginRedirectException(sRedirectUrl))
/*     */     {
/* 159 */       sRedirectUrl = null;
/*     */     }
/*     */ 
/* 162 */     loginForm.setForwardUrl(sRedirectUrl);
/*     */ 
/* 164 */     if ((StringUtil.stringIsPopulated(sRedirectUrl)) && (!RequestUtil.getIsHomepageAction(sRedirectUrl)))
/*     */     {
/* 166 */       loginForm.setShowAccessMessage(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean doPreprocessing()
/*     */   {
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/* 185 */     this.m_customFieldManager = a_fieldManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.ViewLoginAction
 * JD-Core Version:    0.6.0
 */