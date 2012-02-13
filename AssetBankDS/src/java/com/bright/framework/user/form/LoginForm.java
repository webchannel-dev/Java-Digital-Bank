/*     */ package com.bright.framework.user.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class LoginForm extends Bn2Form
/*     */ {
/*  37 */   private String m_sUsername = null;
/*  38 */   private String m_sPassword = null;
/*  39 */   private boolean m_bLoginFailed = false;
/*  40 */   private boolean m_bAccountSuspended = false;
/*  41 */   private String m_sForwardUrl = null;
/*  42 */   private boolean m_bIgnorePasswordIfAdmin = false;
/*  43 */   private boolean m_bSetCookieForAutoLogin = false;
/*  44 */   private boolean m_bConditionsAccepted = false;
/*  45 */   private boolean m_bLoggedOut = false;
/*  46 */   private boolean m_bUsernameInUse = false;
/*  47 */   private boolean m_bShowAccessMessage = false;
/*     */ 
/*  50 */   private HashMap m_hmSSOForm = null;
/*  51 */   private Collection m_colSSOFormKeys = null;
/*  52 */   private String m_sSSOBaseUrl = null;
/*  53 */   private User m_user = null;
/*     */ 
/*  55 */   private boolean m_bCanHaveExternalUsers = false;
/*     */   private String m_forgottenUsername;
/*     */   private boolean m_passwordReminderFailed;
/*     */   private boolean m_passwordReminderFailedLdapUser;
/*     */   private boolean m_passwordReminderSuccess;
/*     */ 
/*     */   public void resetFlags()
/*     */   {
/*  60 */     this.m_bLoginFailed = false;
/*  61 */     this.m_bAccountSuspended = false;
/*  62 */     this.m_passwordReminderFailed = false;
/*  63 */     this.m_passwordReminderFailedLdapUser = false;
/*  64 */     this.m_passwordReminderSuccess = false;
/*  65 */     this.m_bShowAccessMessage = false;
/*  66 */     this.m_bIgnorePasswordIfAdmin = false;
/*  67 */     this.m_bSetCookieForAutoLogin = false;
/*  68 */     this.m_bConditionsAccepted = false;
/*  69 */     this.m_bUsernameInUse = false;
/*     */   }
/*     */ 
/*     */   public void resetLoginForm()
/*     */   {
/*  75 */     this.m_sUsername = "";
/*  76 */     this.m_sPassword = "";
/*  77 */     this.m_forgottenUsername = "";
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  92 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername)
/*     */   {
/* 107 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/* 122 */     return this.m_sPassword;
/*     */   }
/*     */ 
/*     */   public void setPassword(String a_sPassword)
/*     */   {
/* 137 */     this.m_sPassword = a_sPassword;
/*     */   }
/*     */ 
/*     */   public void setLoginFailed(boolean a_bLoginFailed)
/*     */   {
/* 142 */     this.m_bLoginFailed = a_bLoginFailed;
/*     */   }
/*     */ 
/*     */   public boolean getLoginFailed()
/*     */   {
/* 147 */     return this.m_bLoginFailed;
/*     */   }
/*     */ 
/*     */   public void setAccountSuspended(boolean a_bAccountSuspended)
/*     */   {
/* 152 */     this.m_bAccountSuspended = a_bAccountSuspended;
/*     */   }
/*     */ 
/*     */   public boolean getAccountSuspended()
/*     */   {
/* 157 */     return this.m_bAccountSuspended;
/*     */   }
/*     */ 
/*     */   public void setForwardUrl(String a_sForwardUrl)
/*     */   {
/* 162 */     this.m_sForwardUrl = a_sForwardUrl;
/*     */   }
/*     */ 
/*     */   public String getForwardUrl()
/*     */   {
/* 167 */     return this.m_sForwardUrl;
/*     */   }
/*     */ 
/*     */   public boolean getIgnorePasswordIfAdmin()
/*     */   {
/* 172 */     return this.m_bIgnorePasswordIfAdmin;
/*     */   }
/*     */ 
/*     */   public void setIgnorePasswordIfAdmin(boolean a_bIgnorePasswordIfAdmin)
/*     */   {
/* 177 */     this.m_bIgnorePasswordIfAdmin = a_bIgnorePasswordIfAdmin;
/*     */   }
/*     */ 
/*     */   public String getForgottenUsername()
/*     */   {
/* 190 */     return this.m_forgottenUsername;
/*     */   }
/*     */ 
/*     */   public void setForgottenUsername(String a_sForgottenUsername)
/*     */   {
/* 198 */     this.m_forgottenUsername = a_sForgottenUsername;
/*     */   }
/*     */ 
/*     */   public boolean getPasswordReminderFailed()
/*     */   {
/* 205 */     return this.m_passwordReminderFailed;
/*     */   }
/*     */ 
/*     */   public void setPasswordReminderFailed(boolean a_sPasswordReminderFailed)
/*     */   {
/* 210 */     this.m_passwordReminderFailed = a_sPasswordReminderFailed;
/*     */   }
/*     */ 
/*     */   public boolean getPasswordReminderFailedLdapUser()
/*     */   {
/* 217 */     return this.m_passwordReminderFailedLdapUser;
/*     */   }
/*     */ 
/*     */   public void setPasswordReminderFailedLdapUser(boolean a_sPasswordReminderFailedLdapUser)
/*     */   {
/* 222 */     this.m_passwordReminderFailedLdapUser = a_sPasswordReminderFailedLdapUser;
/*     */   }
/*     */ 
/*     */   public boolean getPasswordReminderSuccess()
/*     */   {
/* 229 */     return this.m_passwordReminderSuccess;
/*     */   }
/*     */ 
/*     */   public void setPasswordReminderSuccess(boolean a_sPasswordReminderSuccess)
/*     */   {
/* 234 */     this.m_passwordReminderSuccess = a_sPasswordReminderSuccess;
/*     */   }
/*     */ 
/*     */   public boolean getSetCookieForAutoLogin()
/*     */   {
/* 239 */     return this.m_bSetCookieForAutoLogin;
/*     */   }
/*     */ 
/*     */   public void setSetCookieForAutoLogin(boolean a_sSetCookieForAutoLogin)
/*     */   {
/* 244 */     this.m_bSetCookieForAutoLogin = a_sSetCookieForAutoLogin;
/*     */   }
/*     */ 
/*     */   public boolean isConditionsAccepted()
/*     */   {
/* 249 */     return this.m_bConditionsAccepted;
/*     */   }
/*     */ 
/*     */   public void setConditionsAccepted(boolean a_sConditionsAccepted)
/*     */   {
/* 254 */     this.m_bConditionsAccepted = a_sConditionsAccepted;
/*     */   }
/*     */ 
/*     */   public boolean isLoggedOut()
/*     */   {
/* 259 */     return this.m_bLoggedOut;
/*     */   }
/*     */ 
/*     */   public void setLoggedOut(boolean loggedOut)
/*     */   {
/* 264 */     this.m_bLoggedOut = loggedOut;
/*     */   }
/*     */ 
/*     */   public boolean isUsernameInUse()
/*     */   {
/* 269 */     return this.m_bUsernameInUse;
/*     */   }
/*     */ 
/*     */   public void setUsernameInUse(boolean usernameInUse)
/*     */   {
/* 274 */     this.m_bUsernameInUse = usernameInUse;
/*     */   }
/*     */ 
/*     */   public boolean getShowAccessMessage()
/*     */   {
/* 279 */     return this.m_bShowAccessMessage;
/*     */   }
/*     */ 
/*     */   public void setShowAccessMessage(boolean a_sShowAccessMessage)
/*     */   {
/* 284 */     this.m_bShowAccessMessage = a_sShowAccessMessage;
/*     */   }
/*     */ 
/*     */   public HashMap getSsoForm()
/*     */   {
/* 289 */     return this.m_hmSSOForm;
/*     */   }
/*     */ 
/*     */   public void setSsoForm(HashMap a_sHmSSOForm)
/*     */   {
/* 294 */     this.m_hmSSOForm = a_sHmSSOForm;
/*     */   }
/*     */ 
/*     */   public String getSsoBaseUrl()
/*     */   {
/* 299 */     return this.m_sSSOBaseUrl;
/*     */   }
/*     */ 
/*     */   public void setSsoBaseUrl(String a_sBaseUrl)
/*     */   {
/* 304 */     this.m_sSSOBaseUrl = a_sBaseUrl;
/*     */   }
/*     */ 
/*     */   public Collection getSsoFormKeys()
/*     */   {
/* 309 */     return this.m_colSSOFormKeys;
/*     */   }
/*     */ 
/*     */   public void setSsoFormKeys(Collection a_sColSSOFormKeys)
/*     */   {
/* 314 */     this.m_colSSOFormKeys = a_sColSSOFormKeys;
/*     */   }
/*     */ 
/*     */   public boolean getCanHaveExternalUsers()
/*     */   {
/* 319 */     return this.m_bCanHaveExternalUsers;
/*     */   }
/*     */ 
/*     */   public void setCanHaveExternalUsers(boolean canHaveExternalUsers)
/*     */   {
/* 324 */     this.m_bCanHaveExternalUsers = canHaveExternalUsers;
/*     */   }
/*     */ 
/*     */   public User getUser()
/*     */   {
/* 329 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public void setUser(User a_user)
/*     */   {
/* 334 */     this.m_user = a_user;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.form.LoginForm
 * JD-Core Version:    0.6.0
 */