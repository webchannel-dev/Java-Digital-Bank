/*     */ package com.bright.assetbank.application.filter;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.exception.AccountExpiredException;
/*     */ import com.bright.framework.user.exception.AccountSuspendedException;
/*     */ import com.bright.framework.user.exception.UsernameInUseException;
/*     */ import com.bright.framework.user.service.RemoteUserManager;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class CrowdAuthenticationFilter
/*     */   implements Filter
/*     */ {
/*     */   private static final String k_sAction_Logout = "/logout";
/*     */   private static final String k_sAction_ViewLogin = "/viewLogin";
/*     */   private static final String k_sAction_Login = "/login";
/*     */   private static final String k_sAttribute_FilterRun = "AuthenticationFilterProxyRun";
/*  62 */   private RemoteUserManager m_remoteUserManager = null;
/*  63 */   private UserManager m_userManager = null;
/*  64 */   private AtomicBoolean m_bLoadImplementationCheck = new AtomicBoolean(false);
/*     */ 
/*     */   public void doFilter(ServletRequest a_request, ServletResponse a_response, FilterChain a_chain)
/*     */     throws IOException, ServletException
/*     */   {
/*  73 */     HttpServletRequest request = (HttpServletRequest)a_request;
/*  74 */     HttpServletResponse response = (HttpServletResponse)a_response;
/*     */ 
/*  77 */     loadImplementation();
/*     */ 
/*  79 */     boolean bFilterRun = Boolean.TRUE.equals(request.getAttribute("AuthenticationFilterProxyRun"));
/*     */     try
/*     */     {
/*  83 */       if ((!bFilterRun) && (this.m_remoteUserManager != null))
/*     */       {
/*  85 */         UserProfile userProfile = UserProfile.getUserProfile(request.getSession());
/*     */ 
/*  87 */         boolean bIsLoginRequest = (request.getPathInfo().equals("/login")) || (request.getPathInfo().equals("/viewLogin")) || (request.getPathInfo().equals("/logout")) || (request.getPathInfo().equals("/ssoCreateSession"));
/*     */ 
/*  93 */         boolean bReauthenticate = AssetBankSettings.getAuthenticateEveryRequest();
/*     */ 
/*  96 */         if ((!bIsLoginRequest) && ((!userProfile.getIsLoggedIn()) || (bReauthenticate)))
/*     */         {
/*  98 */           if (userProfile.getIsLoggedIn())
/*     */           {
/* 101 */             if (((userProfile.getUser().isRemoteUser()) || (AssetBankSettings.getForceRemoteAuthentication())) && (bReauthenticate))
/*     */             {
/* 103 */               boolean bAuthenticated = this.m_remoteUserManager.isUserAuthenticated(request, response);
/*     */ 
/* 105 */               if (!bAuthenticated)
/*     */               {
/* 107 */                 this.m_userManager.logout(request, response);
/* 108 */                 request.getSession().setAttribute("LoggedOut", Boolean.TRUE);
/* 109 */                 setOriginalRequestUrl(request);
/* 110 */                 response.sendRedirect(request.getContextPath() + request.getServletPath() + "/viewLogin");
/* 111 */                 return;
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 118 */             String sUsername = this.m_remoteUserManager.getRemoteUserIdentifier(request);
/*     */ 
/* 120 */             if ((StringUtils.isNotEmpty(sUsername)) && (this.m_remoteUserManager.isUserAuthenticated(request, response)))
/*     */             {
/* 122 */               if (!doLocalLogin(request, response, userProfile))
/*     */               {
/* 124 */                 setOriginalRequestUrl(request);
/* 125 */                 response.sendRedirect(request.getContextPath() + request.getServletPath() + "/viewLogin");
/* 126 */                 return;
/*     */               }
/*     */             }
/* 129 */             else if (AssetBankSettings.getForceRemoteAuthentication())
/*     */             {
/* 131 */               setOriginalRequestUrl(request);
/* 132 */               response.sendRedirect(request.getContextPath() + request.getServletPath() + "/viewLogin");
/* 133 */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 139 */       continueChain(request, response, a_chain);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 143 */       GlobalApplication.getInstance().getLogger().error("Could not authenticate user due to unexpected exception : " + e.getMessage(), e);
/* 144 */       request.getSession().setAttribute("LoginFailed", Boolean.TRUE);
/* 145 */       response.sendRedirect(request.getContextPath() + request.getServletPath() + "/viewLogin");
/* 146 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setOriginalRequestUrl(HttpServletRequest a_request)
/*     */   {
/* 152 */     a_request.getSession().setAttribute("OriginalRequestUrl", a_request.getRequestURL() + (StringUtils.isNotEmpty(a_request.getQueryString()) ? "?" + a_request.getQueryString() : ""));
/*     */   }
/*     */ 
/*     */   private boolean doLocalLogin(HttpServletRequest a_request, HttpServletResponse a_response, UserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 158 */     String sUsername = this.m_remoteUserManager.getRemoteUserIdentifier(a_request);
/*     */ 
/* 161 */     if ((!a_userProfile.getIsLoggedIn()) && (StringUtils.isNotEmpty(sUsername)))
/*     */     {
/*     */       try
/*     */       {
/* 165 */         long lId = this.m_userManager.getUserIdForRemoteUsername(null, sUsername);
/*     */ 
/* 168 */         if (lId > 0L)
/*     */         {
/* 170 */           User user = this.m_userManager.getUser(null, lId);
/* 171 */           this.m_userManager.login(null, a_request, a_response, user.getUsername(), null, true);
/* 172 */           return true;
/*     */         }
/*     */ 
/* 175 */         if (AssetBankSettings.getImportRemoteUsersOnTheFly())
/*     */         {
/* 177 */           User user = this.m_userManager.createUser();
/*     */ 
/* 179 */           this.m_remoteUserManager.populateUser(a_request, user);
/*     */ 
/* 181 */           user.setRemoteUser(true);
/*     */ 
/* 183 */           if (StringUtils.isEmpty(user.getUsername()))
/*     */           {
/* 185 */             user.setUsername(AssetBankSettings.getRemoteUserDirectoryImportedUsernamePrefix().trim() + user.getRemoteUsername());
/*     */           }
/*     */ 
/* 188 */           user = this.m_userManager.saveUser(null, user);
/*     */ 
/* 191 */           if ((user.getGroups() != null) && (user.getGroups().size() > 0))
/*     */           {
/* 193 */             String[] asGroupNames = new String[user.getGroups().size()];
/* 194 */             user.getGroups().copyInto(asGroupNames);
/* 195 */             user.setGroups(null);
/* 196 */             this.m_remoteUserManager.updateUserInGroups(null, this.m_userManager, this.m_userManager.getMappedGroups(), user, asGroupNames);
/*     */           }
/*     */ 
/* 199 */           this.m_userManager.login(null, a_request, a_response, user.getUsername(), null, true);
/* 200 */           return true;
/*     */         }
/*     */       }
/*     */       catch (AccountSuspendedException e)
/*     */       {
/* 205 */         a_request.getSession().setAttribute("AccountSuspended", Boolean.TRUE);
/*     */       }
/*     */       catch (AccountExpiredException e)
/*     */       {
/* 209 */         a_request.getSession().setAttribute("AccountExpired", Boolean.TRUE);
/*     */       }
/*     */       catch (UsernameInUseException e)
/*     */       {
/* 213 */         a_request.getSession().setAttribute("UsernameInUse", Boolean.TRUE);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 217 */         a_request.getSession().setAttribute("LoginFailed", Boolean.TRUE);
/*     */       }
/*     */     }
/*     */ 
/* 221 */     return false;
/*     */   }
/*     */ 
/*     */   private void continueChain(HttpServletRequest a_request, HttpServletResponse a_response, FilterChain a_chain)
/*     */     throws ServletException, IOException
/*     */   {
/* 227 */     a_request.setAttribute("AuthenticationFilterProxyRun", Boolean.TRUE);
/* 228 */     a_chain.doFilter(a_request, a_response);
/* 229 */     a_request.getSession().removeAttribute("OriginalRequestUrl");
/* 230 */     a_request.getSession().removeAttribute("LoggedOut");
/* 231 */     a_request.getSession().removeAttribute("AccountSuspended");
/* 232 */     a_request.getSession().removeAttribute("AccountExpired");
/* 233 */     a_request.getSession().removeAttribute("LoginFailed");
/* 234 */     a_request.getSession().removeAttribute("UsernameInUse");
/*     */   }
/*     */ 
/*     */   public void loadImplementation()
/*     */     throws ServletException
/*     */   {
/* 244 */     if ((this.m_userManager == null) && (AssetBankSettings.getInstance().hasBeenInitialised()) && (!this.m_bLoadImplementationCheck.getAndSet(true)))
/*     */     {
/* 246 */       synchronized (this.m_bLoadImplementationCheck)
/*     */       {
/* 248 */         if (this.m_userManager == null)
/*     */         {
/*     */           try
/*     */           {
/* 252 */             this.m_userManager = ((UserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager"));
/*     */           }
/*     */           catch (ComponentException e)
/*     */           {
/* 256 */             GlobalApplication.getInstance().getLogger().error("AuthenticationFilterProxy.loadImplementation() : ComponentException while getting UserMAnager : " + e);
/* 257 */             throw new ServletException("AuthenticationFilterProxy.loadImplementation() : ComponentException while getting UserMAnager : " + e, e);
/*     */           }
/*     */         }
/*     */ 
/* 261 */         String sRemoteUserManagerClassName = AssetBankSettings.getRemoteUserDirectoryManagerClassName();
/*     */ 
/* 263 */         if (StringUtils.isNotEmpty(sRemoteUserManagerClassName))
/*     */         {
/*     */           try
/*     */           {
/* 267 */             Object manager = Class.forName(sRemoteUserManagerClassName).newInstance();
/*     */ 
/* 269 */             this.m_remoteUserManager = ((RemoteUserManager)manager);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 273 */             GlobalApplication.getInstance().getLogger().error("AuthenticationFilterProxy.loadImplementation() : Could not instantiate Remote User Manager " + sRemoteUserManagerClassName + " : " + e);
/* 274 */             throw new ServletException("AuthenticationFilterProxy.loadImplementation() : Could not instantiate Remote User Manager " + sRemoteUserManagerClassName + " : " + e, e);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init(FilterConfig a_filterConfig)
/*     */     throws ServletException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.filter.CrowdAuthenticationFilter
 * JD-Core Version:    0.6.0
 */