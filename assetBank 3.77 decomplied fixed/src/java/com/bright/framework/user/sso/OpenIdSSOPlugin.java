/*     */ package com.bright.framework.user.sso;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class OpenIdSSOPlugin extends BaseSSOPlugin
/*     */   implements SSOPlugin
/*     */ {
/*  44 */   private OpenIdSSOSettings m_settings = null;
/*     */ 
/*  46 */   private OpenIdManager m_openIdManager = null;
/*     */ 
/*  54 */   User m_remoteUser = null;
/*     */ 
/*     */   public void setOpenIdManager(OpenIdManager a_openIdManager)
/*     */   {
/*  49 */     this.m_openIdManager = a_openIdManager;
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     super.startup();
/*     */ 
/*  68 */     if (UserSettings.getOpenIdEnabled())
/*     */     {
/*  70 */       this.m_settings = new OpenIdSSOSettings("OpenIdSSOSettings");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSSOMode()
/*     */   {
/*  84 */     return "Identify";
/*     */   }
/*     */ 
/*     */   public String getLoginJsp()
/*     */   {
/*  92 */     String sJsp = this.m_settings.getLoginJsp();
/*  93 */     return sJsp;
/*     */   }
/*     */ 
/*     */   public void processRequest(HttpServletRequest a_request, LoginForm a_form)
/*     */   {
/* 108 */     String sOpenIdPrefix = this.m_settings.getOpenIdPrefix();
/* 109 */     a_request.setAttribute("openIdPrefix", sOpenIdPrefix);
/*     */   }
/*     */ 
/*     */   public String getLoginBaseUrl()
/*     */   {
/* 121 */     return "";
/*     */   }
/*     */ 
/*     */   public HashMap getLoginFormForPost(HttpServletRequest a_request)
/*     */   {
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLoginUrlForRedirect(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 144 */     String sPrefix = this.m_settings.getOpenIdPrefix();
/* 145 */     String sUrl = this.m_openIdManager.getAuthenticationUrl(sPrefix + a_form.getUsername(), a_request);
/* 146 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public String getSessionCheckUrl(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean validateForm(LoginForm a_form)
/*     */   {
/* 158 */     boolean bValid = StringUtil.stringIsPopulated(a_form.getUsername());
/* 159 */     return bValid;
/*     */   }
/*     */ 
/*     */   public String getForwardUrl(HttpServletRequest a_request)
/*     */   {
/* 175 */     String sQueryString = a_request.getQueryString();
/*     */ 
/* 177 */     if (StringUtil.stringIsPopulated(sQueryString))
/*     */     {
/* 179 */       if (sQueryString.matches("forwardUrl=[^&].+"))
/*     */       {
/* 181 */         sQueryString = sQueryString.replaceAll("&amp;", "&");
/* 182 */         return sQueryString.substring("forwardUrl".length() + 1);
/*     */       }
/*     */     }
/* 185 */     return null;
/*     */   }
/*     */ 
/*     */   public User getRemoteUser(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 201 */     String sPrefix = this.m_settings.getOpenIdPrefix();
/* 202 */     User user = this.m_openIdManager.getVerifiedUser(a_request, sPrefix);
/* 203 */     return user;
/*     */   }
/*     */ 
/*     */   public String getLogoutUrl(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */   {
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   public void doDestroyAction(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.OpenIdSSOPlugin
 * JD-Core Version:    0.6.0
 */