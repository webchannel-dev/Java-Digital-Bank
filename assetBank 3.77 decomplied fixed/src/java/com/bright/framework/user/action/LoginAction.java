/*     */ package com.bright.framework.user.action;
/*     */ 
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.activedirectory.constant.ActiveDirectorySettings;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.constant.UserConstants;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.exception.AccountExpiredException;
/*     */ import com.bright.framework.user.exception.AccountSuspendedException;
/*     */ import com.bright.framework.user.exception.InvalidLoginException;
/*     */ import com.bright.framework.user.exception.LoginException;
/*     */ import com.bright.framework.user.exception.PasswordExpiredException;
/*     */ import com.bright.framework.user.exception.PasswordReminderException;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class LoginAction extends BTransactionAction
/*     */   implements CommonConstants, UserConstants, AssetBankConstants
/*     */ {
/*  73 */   protected UserManager m_userManager = null;
/*     */ 
/*  79 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setUserManager(UserManager a_userManager)
/*     */   {
/*  76 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  82 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 104 */     this.m_logger.debug("In LoginAction.execute");
/*     */ 
/* 106 */     ActionForward afForward = null;
/* 107 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/* 108 */     LoginForm loginForm = (LoginForm)a_form;
/*     */ 
/* 111 */     String sSubmitButton = a_request.getParameter("submit");
/* 112 */     if ((sSubmitButton != null) && (sSubmitButton.equals("Cancel")))
/*     */     {
/* 114 */       afForward = a_mapping.findForward("Cancel");
/*     */     }
/* 119 */     else if ((AssetBankSettings.getShowConditionsOnLogin()) && (!loginForm.isConditionsAccepted()))
/*     */     {
/* 122 */       loginForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationConditions", userProfile.getCurrentLanguage()).getBody());
/* 123 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 127 */       afForward = processLoginForm(a_dbTransaction, a_mapping, loginForm, a_request, a_response);
/*     */ 
/* 134 */       userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 137 */       if ((UserSettings.getAllowAutoLoginUsingCookie()) && (loginForm.getSetCookieForAutoLogin()))
/*     */       {
/* 141 */         if (userProfile.getIsLoggedIn())
/*     */         {
/* 143 */           this.m_userManager.saveUsernameCookie(a_response, userProfile);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 149 */       if ((userProfile.getIsLoggedIn()) && (UserSettings.getUserDetailsCookieEnabled()))
/*     */       {
/* 151 */         this.m_userManager.saveUserDetailsCookie(a_response, userProfile);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 157 */     if ((UserSettings.getSSOIsEnabled()) && (UserSettings.getSSOLoginIsDefault()))
/*     */     {
/* 159 */       a_request.setAttribute("local", new Boolean(true));
/*     */     }
/* 161 */     return afForward;
/*     */   }
/*     */ 
/*     */   public boolean doPreprocessing()
/*     */   {
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   public ActionForward processLoginForm(DBTransaction a_dbTransaction, ActionMapping a_mapping, LoginForm loginForm, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/* 216 */     String ksMethodName = "processLoginForm";
/*     */ 
/* 218 */     ActionForward afForward = null;
/*     */ 
/* 221 */     String sForgotten = a_request.getParameter("forgotten");
/* 222 */     if (StringUtil.stringIsPopulated(sForgotten))
/*     */     {
/*     */       try
/*     */       {
/* 227 */         if (ActiveDirectorySettings.getSuspendADAuthentication())
/*     */         {
/* 229 */           this.m_userManager.sendPasswordReminder(a_dbTransaction, loginForm.getForgottenUsername());
/* 230 */           loginForm.setPasswordReminderSuccess(true);
/* 231 */           afForward = a_mapping.findForward("PasswordReminder");
/*     */         }
/*     */         else
/*     */         {
/* 236 */           long[] laIds = null;
/*     */ 
/* 239 */           long lUserId = this.m_userManager.getUserIdForLocalUsername(a_dbTransaction, loginForm.getForgottenUsername());
/*     */ 
/* 241 */           if (lUserId > 0L)
/*     */           {
/* 243 */             laIds = new long[1];
/* 244 */             laIds[0] = lUserId;
/*     */           }
/*     */           else
/*     */           {
/* 249 */             laIds = this.m_userManager.getUserIdsForEmailAddress(a_dbTransaction, loginForm.getForgottenUsername());
/*     */ 
/* 252 */             if (laIds == null)
/*     */             {
/* 254 */               this.m_logger.debug("processLoginForm: No user found for user or email address: " + loginForm.getForgottenUsername());
/* 255 */               throw new PasswordReminderException("No user found for user or email address: " + loginForm.getForgottenUsername());
/*     */             }
/*     */           }
/*     */ 
/* 259 */           boolean bAllUsersAreLdap = true;
/*     */ 
/* 261 */           for (int i = 0; i < laIds.length; i++)
/*     */           {
/* 263 */             User user = this.m_userManager.getUser(a_dbTransaction, laIds[i]);
/*     */ 
/* 265 */             if (user.isRemoteUser())
/*     */               continue;
/* 267 */             bAllUsersAreLdap = false;
/*     */           }
/*     */ 
/* 272 */           if (bAllUsersAreLdap)
/*     */           {
/* 274 */             loginForm.setPasswordReminderFailedLdapUser(true);
/* 275 */             afForward = a_mapping.findForward("Failure");
/*     */           }
/*     */           else
/*     */           {
/* 280 */             this.m_userManager.sendPasswordReminder(a_dbTransaction, loginForm.getForgottenUsername());
/* 281 */             loginForm.setPasswordReminderSuccess(true);
/* 282 */             afForward = a_mapping.findForward("PasswordReminder");
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (PasswordReminderException e)
/*     */       {
/* 290 */         loginForm.setPasswordReminderFailed(true);
/* 291 */         afForward = a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 297 */       boolean a_bIgnorePassword = false;
/*     */ 
/* 299 */       if (a_request.getAttribute("ignorePassword") != null)
/*     */       {
/* 303 */         a_bIgnorePassword = ((Boolean)a_request.getAttribute("ignorePassword")).booleanValue();
/*     */       }
/*     */ 
/* 306 */       afForward = doLogin(a_dbTransaction, a_mapping, loginForm, a_request, a_response, getUsername(loginForm, a_request), loginForm.getPassword(), a_bIgnorePassword);
/*     */ 
/* 316 */       UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/* 317 */       userProfile.setOriginalUserId(getOriginalUserId(a_request));
/*     */     }
/*     */ 
/* 320 */     return afForward;
/*     */   }
/*     */ 
/*     */   private String getUsername(LoginForm a_form, HttpServletRequest a_request)
/*     */   {
/* 327 */     if (a_request.getAttribute("username") != null)
/*     */     {
/* 329 */       return (String)a_request.getAttribute("username");
/*     */     }
/*     */ 
/* 333 */     return a_form.getUsername();
/*     */   }
/*     */ 
/*     */   private long getOriginalUserId(HttpServletRequest a_request)
/*     */   {
/* 339 */     if (a_request.getAttribute("requestUserId") != null)
/*     */     {
/* 341 */       return ((Long)a_request.getAttribute("requestUserId")).longValue();
/*     */     }
/*     */ 
/* 344 */     return -1L;
/*     */   }
/*     */ 
/*     */   public ActionForward doLogin(DBTransaction a_dbTransaction, ActionMapping a_mapping, LoginForm loginForm, HttpServletRequest a_request, HttpServletResponse a_response, String a_sUsername, String a_sPassword, boolean a_bIgnorePassword)
/*     */     throws Bn2Exception
/*     */   {
/* 368 */     ActionForward afForward = null;
/* 369 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */     try
/*     */     {
/* 374 */       this.m_userManager.login(a_dbTransaction, a_request, a_response, a_sUsername, a_sPassword, a_bIgnorePassword);
/*     */ 
/* 381 */       if ((loginForm.getForwardUrl() == null) || (loginForm.getForwardUrl().length() == 0))
/*     */       {
/* 384 */         afForward = createRedirectingForward("", a_mapping, "Success");
/*     */       }
/*     */       else
/*     */       {
/* 388 */         afForward = createRedirectingForward(loginForm.getForwardUrl());
/*     */       }
/*     */     }
/*     */     catch (AccountSuspendedException ase)
/*     */     {
/* 393 */       loginForm.setAccountSuspended(true);
/* 394 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     catch (InvalidLoginException ile)
/*     */     {
/* 398 */       loginForm.setLoginFailed(true);
/* 399 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     catch (AccountExpiredException ase)
/*     */     {
/* 403 */       loginForm.setAccountSuspended(true);
/* 404 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     catch (PasswordExpiredException pee)
/*     */     {
/* 408 */       userProfile.setIsPasswordExpired(true);
/* 409 */       afForward = a_mapping.findForward("ForcePasswordChange");
/*     */     }
/*     */     catch (LoginException le)
/*     */     {
/* 414 */       loginForm.setLoginFailed(true);
/* 415 */       loginForm.addError(le.getMessage());
/* 416 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 420 */       this.m_logger.error("Error in LoginAction : " + e.getMessage());
/* 421 */       throw e;
/*     */     }
/* 423 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.LoginAction
 * JD-Core Version:    0.6.0
 */