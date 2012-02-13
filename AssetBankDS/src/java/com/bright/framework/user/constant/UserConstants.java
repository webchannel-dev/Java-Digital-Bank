package com.bright.framework.user.constant;

public abstract interface UserConstants
{
  public static final String SECURITY_VIOLATION_KEY = "SecurityViolation";
  public static final long k_lUserNotLoggedInId = 0L;
  public static final String k_sSubmitParamName = "submit";
  public static final String k_sCancelParam = "Cancel";
  public static final String CANCEL_KEY = "Cancel";
  public static final String k_sPostLoginRedirectParam = "redirecturl";
  public static final String k_sParamName_MessageCode = "message";
  public static final String k_sParamLogin = "login";
  public static final String k_sParamRegister = "register";
  public static final String k_sParamForgotten = "forgotten";
  public static final String k_sParamName_ForwardUrl = "forwardUrl";
  public static final String k_sParamName_Local = "local";
  public static final String k_sForward_LoginWithSSO = "LoginWithSSO";
  public static final String k_sForward_PasswordReminderSuccess = "PasswordReminder";
  public static final String k_sForward_ForcePasswordChangeSuccess = "ForcePasswordChange";
  public static final String k_sForward_LoginForward = "LoginForward";
  public static final String k_sAttribute_Username = "username";
  public static final String k_sAttribute_IgnorePassword = "ignorePassword";
  public static final String k_sAttribute_OriginalUserId = "requestUserId";
  public static final String k_sParamName_Switch = "switch";
  public static final String k_sCookieName_UsernameEncrypted = "AssetBankUserAuth";
  public static final String k_sCookieName_EncryptedUserDetails = "AssetBankUserDetails";
  public static final String k_sCookieName_Token = "AssetBankToken";
  public static final String k_sCookieTokenSecret = "rty67pk$";
  public static final String k_sAttribute_OriginalRequestUrl = "OriginalRequestUrl";
  public static final String k_sAttribute_LoggedOut = "LoggedOut";
  public static final String k_sAttribute_AccountSuspended = "AccountSuspended";
  public static final String k_sAttribute_AccountExpired = "AccountExpired";
  public static final String k_sAttribute_LoginFailed = "LoginFailed";
  public static final String k_sAttribute_UsernameInUse = "UsernameInUse";
  public static final String k_sActionName_SSOCreateSession = "ssoCreateSession";
  public static final String k_sActionName_SSODestroySession = "ssoDestroySession";
  public static final String k_sActionName_SSOLoginForm = "viewSSOLogin";
  public static final String k_sActionName_LoginForm = "viewLogin";
  public static final int k_iCookieExpiryTime = 315360000;
  public static final String k_sUserDetailsCookieEncryption = "AES/CBC/PKCS5Padding";
  public static final String k_sAttribute_OpenIdPrefix = "openIdPrefix";
  public static final String k_sMethod_GetPassword = "getPassword";
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.constant.UserConstants
 * JD-Core Version:    0.6.0
 */