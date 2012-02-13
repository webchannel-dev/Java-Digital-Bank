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
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class WIASSOPlugin extends BaseSSOPlugin
/*     */   implements SSOPlugin
/*     */ {
/*     */   private static final String k_sClassName = "WIASSOPlugin";
/*  48 */   private WIASSOSettings m_settings = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  60 */     super.startup();
/*     */ 
/*  62 */     if (UserSettings.getWindowsIntegratedAuthenticationEnabled())
/*     */     {
/*  64 */       this.m_settings = new WIASSOSettings("WIASSOSettings");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSSOMode()
/*     */   {
/*  72 */     return "HTTPRequestToken";
/*     */   }
/*     */ 
/*     */   public User getRemoteUser(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/*  89 */     String sUsername = null;
/*     */ 
/*  92 */     String sAuthString = a_request.getRemoteUser();
/*     */ 
/*  94 */     this.m_logger.debug("WIASSOPlugin.getRemoteUser: got auth string: " + sAuthString);
/*     */ 
/*  97 */     if ((sAuthString != null) && (sAuthString.length() > 2))
/*     */     {
/*  99 */       int iPos = sAuthString.indexOf('\\');
/* 100 */       if ((iPos > 0) && (sAuthString.length() > iPos + 1))
/*     */       {
/* 102 */         sUsername = sAuthString.substring(iPos + 1);
/*     */       }
/*     */     }
/*     */ 
/* 106 */     this.m_logger.debug("WIASSOPlugin: user: " + sUsername);
/*     */ 
/* 109 */     User user = null;
/*     */ 
/* 111 */     if (StringUtil.stringIsPopulated(sUsername))
/*     */     {
/* 113 */       user = new User();
/*     */ 
/* 115 */       user.setRemoteUsername(sUsername);
/* 116 */       user.setUsername(sUsername);
/*     */     }
/*     */ 
/* 120 */     return user;
/*     */   }
/*     */ 
/*     */   public String getLogoutUrl(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */   {
/* 126 */     return BaseSSOPlugin.getLoginFormUrl(a_request).toString();
/*     */   }
/*     */ 
/*     */   public void doDestroyAction(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getForwardUrl(HttpServletRequest a_request)
/*     */   {
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLoginBaseUrl()
/*     */   {
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   public HashMap getLoginFormForPost(HttpServletRequest a_request)
/*     */   {
/* 149 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLoginJsp()
/*     */   {
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLoginUrlForRedirect(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   public String getSessionCheckUrl(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 166 */     return null;
/*     */   }
/*     */ 
/*     */   public void processRequest(HttpServletRequest a_request, LoginForm a_form)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean validateForm(LoginForm a_form)
/*     */   {
/* 175 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.WIASSOPlugin
 * JD-Core Version:    0.6.0
 */