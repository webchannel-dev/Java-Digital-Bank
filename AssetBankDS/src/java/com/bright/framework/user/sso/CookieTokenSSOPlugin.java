/*     */ package com.bright.framework.user.sso;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.codec.binary.Hex;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CookieTokenSSOPlugin extends BaseSSOPlugin
/*     */   implements SSOPlugin
/*     */ {
/*     */   private static final String k_sClassName = "CookieTokenSSOPlugin";
/*  45 */   private CookieTokenSSOSettings m_settings = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     super.startup();
/*     */ 
/*  59 */     if (UserSettings.getCookieTokenEnabled())
/*     */     {
/*  61 */       this.m_settings = new CookieTokenSSOSettings("CookieTokenSSOSettings");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSSOMode()
/*     */   {
/*  67 */     return "HTTPRequestToken";
/*     */   }
/*     */ 
/*     */   public String getLogoutUrl(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */   {
/*  81 */     String sUrl = this.m_settings.getLogoutUrl();
/*  82 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public String getSessionCheckUrl(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 100 */     String sUrl = getScriptUrl();
/*     */ 
/* 103 */     String sForwardUrl = a_form.getForwardUrl();
/* 104 */     if (StringUtil.stringIsPopulated(sForwardUrl))
/*     */     {
/* 106 */       String sReturnUrl = RequestUtil.getActionPart(sForwardUrl);
/*     */ 
/* 109 */       String sPassThrough = RequestUtil.getQueryStringPart(sForwardUrl);
/* 110 */       sPassThrough = new String(Hex.encodeHex(sPassThrough.getBytes()));
/*     */ 
/* 112 */       sUrl = sUrl + "?" + getReturnUrlParam() + "=" + sReturnUrl + "&" + getPassThruParam() + "=" + sPassThrough;
/*     */     }
/*     */ 
/* 115 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public User getRemoteUser(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 131 */     User user = null;
/*     */ 
/* 134 */     String sCookieString = getUsernameFromCookie(a_request, getCookieName(), getSalt());
/*     */ 
/* 137 */     if (StringUtil.stringIsPopulated(sCookieString))
/*     */     {
/* 139 */       String sEmail = null;
/* 140 */       String sFullName = null;
/* 141 */       String sUsername = null;
/*     */ 
/* 144 */       if (sCookieString.indexOf("&") > 0)
/*     */       {
/* 146 */         String[] asDetails = sCookieString.split("&");
/*     */ 
/* 148 */         for (String sParam : asDetails)
/*     */         {
/* 150 */           if (sParam.indexOf("=") <= 0)
/*     */             continue;
/* 152 */           String[] asParts = sParam.split("=");
/* 153 */           if (((asParts != null ? 1 : 0) & (asParts.length > 1 ? 1 : 0)) == 0)
/*     */             continue;
/* 155 */           String sName = asParts[0];
/* 156 */           String sValue = asParts[1];
/*     */ 
/* 158 */           if (sName.equalsIgnoreCase(this.m_settings.getUsernameParam()))
/*     */           {
/* 160 */             sUsername = sValue;
/*     */           }
/* 162 */           if (sName.equalsIgnoreCase(this.m_settings.getFullNameParam()))
/*     */           {
/* 164 */             sFullName = sValue;
/*     */           }
/* 166 */           if (!sName.equalsIgnoreCase(this.m_settings.getEmailParam()))
/*     */             continue;
/* 168 */           sEmail = sValue;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 177 */         sUsername = sCookieString;
/*     */       }
/*     */ 
/* 180 */       user = new User();
/*     */ 
/* 182 */       user.setRemoteUsername(sUsername);
/* 183 */       user.setUsername(sUsername);
/*     */ 
/* 185 */       if (StringUtil.stringIsPopulated(sFullName))
/*     */       {
/* 187 */         String[] asNames = BaseSSOPlugin.parseFullUsername(sFullName);
/* 188 */         user.setForename(asNames[0]);
/* 189 */         user.setSurname(asNames[1]);
/* 190 */         user.setDisplayName(sFullName);
/*     */       }
/*     */ 
/* 193 */       user.setEmailAddress(sEmail);
/*     */     }
/*     */ 
/* 196 */     return user;
/*     */   }
/*     */ 
/*     */   public static String getUsernameFromCookie(HttpServletRequest a_request, String a_sCookieName, String a_sSecret)
/*     */   {
/* 211 */     String sUsername = null;
/* 212 */     String sToken = null;
/*     */ 
/* 215 */     Cookie[] aCookies = a_request.getCookies();
/* 216 */     for (int i = 0; (aCookies != null) && (i < aCookies.length); i++)
/*     */     {
/* 219 */       if ((aCookies[i].getName() == null) || (!aCookies[i].getName().equals(a_sCookieName)))
/*     */         continue;
/* 221 */       sToken = aCookies[i].getValue();
/* 222 */       break;
/*     */     }
/*     */ 
/* 227 */     if (sToken != null)
/*     */     {
/* 229 */       int iPos = sToken.indexOf('|');
/*     */ 
/* 231 */       if (iPos <= 0)
/*     */       {
/* 233 */         GlobalApplication.getInstance().getLogger().error("CookieTokenSSOPlugin: could not find delimiter:" + sToken);
/*     */       }
/*     */       else
/*     */       {
/* 237 */         sUsername = sToken.substring(0, iPos);
/* 238 */         String sKey = sToken.substring(iPos + 1);
/*     */ 
/* 241 */         sUsername = sUsername.replaceAll("\\+", " ");
/*     */ 
/* 244 */         String sInput = sUsername + a_sSecret;
/* 245 */         String sKeyTest = StringUtil.hexDigest(sInput);
/*     */ 
/* 247 */         GlobalApplication.getInstance().getLogger().debug("CookieTokenSSOPlugin: user=" + sUsername + ", key=" + sKey + ", keytest=" + sKeyTest);
/*     */ 
/* 249 */         if ((sKey == null) || (sKeyTest == null) || (!sKey.equalsIgnoreCase(sKeyTest)))
/*     */         {
/* 251 */           sUsername = null;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 256 */     return sUsername;
/*     */   }
/*     */ 
/*     */   private String getScriptUrl()
/*     */   {
/* 262 */     String sUrl = this.m_settings.getScriptUrl();
/* 263 */     return sUrl;
/*     */   }
/*     */ 
/*     */   private String getReturnUrlParam()
/*     */   {
/* 268 */     String sUrl = this.m_settings.getReturnUrlParameter();
/* 269 */     return sUrl;
/*     */   }
/*     */ 
/*     */   private String getPassThruParam()
/*     */   {
/* 274 */     String sUrl = this.m_settings.getPassThruParameter();
/* 275 */     return sUrl;
/*     */   }
/*     */ 
/*     */   private String getCookieName()
/*     */   {
/* 280 */     String sUrl = this.m_settings.getCookieName();
/* 281 */     return sUrl;
/*     */   }
/*     */ 
/*     */   private String getSalt()
/*     */   {
/* 286 */     String sUrl = this.m_settings.getSalt();
/* 287 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public void resetAfterLogin(HttpServletResponse a_response)
/*     */   {
/* 304 */     removeCookie(a_response, getCookieName());
/*     */   }
/*     */ 
/*     */   private void removeCookie(HttpServletResponse a_response, String a_sName)
/*     */   {
/* 314 */     setUsernameCookie(a_response, "void", 0, a_sName);
/*     */   }
/*     */ 
/*     */   private void setUsernameCookie(HttpServletResponse a_response, String sValue, int a_iExpiry, String a_sName)
/*     */   {
/* 331 */     Cookie cookie = new Cookie(a_sName, sValue);
/* 332 */     cookie.setMaxAge(a_iExpiry);
/* 333 */     a_response.addCookie(cookie);
/*     */   }
/*     */ 
/*     */   public void doDestroyAction(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getForwardUrl(HttpServletRequest a_request)
/*     */   {
/* 346 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLoginBaseUrl()
/*     */   {
/* 351 */     return null;
/*     */   }
/*     */ 
/*     */   public HashMap getLoginFormForPost(HttpServletRequest a_request)
/*     */   {
/* 356 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLoginJsp()
/*     */   {
/* 361 */     return null;
/*     */   }
/*     */ 
/*     */   public String getLoginUrlForRedirect(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 367 */     return null;
/*     */   }
/*     */ 
/*     */   public void processRequest(HttpServletRequest a_request, LoginForm a_form)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean validateForm(LoginForm a_form)
/*     */   {
/* 376 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.CookieTokenSSOPlugin
 * JD-Core Version:    0.6.0
 */