/*     */ package com.bright.framework.util;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class RequestUtil
/*     */ {
/*     */   public static final String k_sOS_Unknown = "Unknown";
/*     */ 
/*     */   public static String getOriginalRequestUrl(HttpServletRequest a_request)
/*     */   {
/*  56 */     return getOriginalRequestUrl(a_request, false);
/*     */   }
/*     */ 
/*     */   public static String getOriginalRequestUrl(HttpServletRequest a_request, boolean a_bEncode)
/*     */   {
/*  73 */     String sUrl = (String)a_request.getAttribute("javax.servlet.forward.request_uri");
/*     */ 
/*  75 */     if (sUrl != null)
/*     */     {
/*  77 */       String sQueryString = (String)a_request.getAttribute("javax.servlet.forward.query_string");
/*     */ 
/*  80 */       sUrl = sUrl.substring(a_request.getContextPath().length()) + (StringUtils.isNotEmpty(sQueryString) ? "?" + sQueryString : "");
/*     */     }
/*     */ 
/*  85 */     if (a_bEncode)
/*     */     {
/*     */       try
/*     */       {
/*  89 */         sUrl = URLEncoder.encode(sUrl, "UTF-8");
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  93 */         GlobalApplication.getInstance().getLogger().error("RequestUtil.getOriginalRequestUrl: Unable to encode url: " + sUrl);
/*     */       }
/*     */     }
/*     */ 
/*  97 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public static String getActionPart(String a_sUrl)
/*     */   {
/* 109 */     return a_sUrl.split("\\?")[0];
/*     */   }
/*     */ 
/*     */   public static String getActionName(String a_sUrl)
/*     */   {
/* 120 */     String sActionPart = getActionPart(a_sUrl);
/*     */ 
/* 122 */     String sActionName = sActionPart;
/* 123 */     if (sActionPart != null)
/*     */     {
/* 125 */       int iPos = sActionPart.lastIndexOf('/');
/* 126 */       if (iPos > 0)
/*     */       {
/* 128 */         sActionName = sActionPart.substring(iPos);
/*     */       }
/*     */     }
/*     */ 
/* 132 */     return sActionName;
/*     */   }
/*     */ 
/*     */   public static String getQueryStringPart(String a_sUrl)
/*     */   {
/* 144 */     String sQueryString = "";
/*     */ 
/* 146 */     String[] asParts = a_sUrl.split("\\?");
/* 147 */     if (asParts.length > 1)
/*     */     {
/* 149 */       sQueryString = asParts[1];
/*     */     }
/*     */ 
/* 152 */     return sQueryString;
/*     */   }
/*     */ 
/*     */   public static boolean getIsUrlPostLoginRedirectException(String a_sUrl)
/*     */   {
/* 164 */     boolean bIsException = false;
/*     */ 
/* 166 */     if (a_sUrl != null)
/*     */     {
/* 169 */       String sActionPart = getActionName(a_sUrl);
/*     */ 
/* 172 */       if (UserSettings.getPostLoginRedirectExceptions().contains(sActionPart))
/*     */       {
/* 174 */         bIsException = true;
/*     */       }
/*     */     }
/*     */ 
/* 178 */     return bIsException;
/*     */   }
/*     */ 
/*     */   public static boolean getIsUrlLoginAction(String a_sUrl)
/*     */   {
/* 183 */     return getDoActionsContainUrl(a_sUrl, UserSettings.getLoginActions());
/*     */   }
/*     */ 
/*     */   public static boolean getIsUrlChangePasswordAction(String a_sUrl)
/*     */   {
/* 188 */     return getDoActionsContainUrl(a_sUrl, UserSettings.getChangePasswordAction());
/*     */   }
/*     */ 
/*     */   private static boolean getDoActionsContainUrl(String a_sUrl, String a_sActions)
/*     */   {
/* 193 */     boolean bIsLogin = false;
/*     */ 
/* 195 */     if (a_sUrl != null)
/*     */     {
/* 198 */       String sActionPart = getActionName(a_sUrl);
/*     */ 
/* 201 */       if (a_sActions.contains(sActionPart))
/*     */       {
/* 203 */         bIsLogin = true;
/*     */       }
/*     */     }
/*     */ 
/* 207 */     return bIsLogin;
/*     */   }
/*     */ 
/*     */   public static boolean getIsHomepageAction(String a_sUrl)
/*     */   {
/* 219 */     boolean bIsHome = false;
/*     */ 
/* 221 */     if (a_sUrl != null)
/*     */     {
/* 224 */       String sActionPart = getActionName(a_sUrl);
/*     */ 
/* 227 */       if ("viewHome".contains(sActionPart))
/*     */       {
/* 229 */         bIsHome = true;
/*     */       }
/*     */     }
/*     */ 
/* 233 */     return bIsHome;
/*     */   }
/*     */ 
/*     */   public static void setOriginalRequestUrl(HttpServletRequest a_request)
/*     */   {
/* 245 */     String sRequestUrl = a_request.getRequestURL().toString();
/* 246 */     String sAppUrl = ServletUtil.getApplicationUrl(a_request);
/* 247 */     String sWebappName = a_request.getContextPath();
/* 248 */     String sRemainder = sRequestUrl.substring(sRequestUrl.indexOf(sWebappName) + sWebappName.length());
/*     */ 
/* 250 */     String sActualUrl = sAppUrl + sRemainder;
/*     */ 
/* 252 */     String sForwardUrl = sActualUrl + (StringUtils.isNotEmpty(a_request.getQueryString()) ? "?" + a_request.getQueryString() : "");
/* 253 */     a_request.getSession().setAttribute("OriginalRequestUrl", sForwardUrl);
/*     */   }
/*     */ 
/*     */   public static Map<String, String> getRequestParametersAsMap(final HttpServletRequest a_request, String a_namePrefix, boolean a_includeEmpty)
/*     */   {
/* 266 */     NameValuePairSource nvpSource = new NameValuePairSource()
/*     */     {
/*     */       public Enumeration getNameEnumeration()
/*     */       {
/* 273 */         return a_request.getParameterNames();
/*     */       }
/*     */ 
/*     */       public String getValue(String a_name)
/*     */       {
/* 278 */         return a_request.getParameter(a_name);
/*     */       }
/*     */     };
/* 282 */     return getNameValuePairsByPrefix(nvpSource, a_namePrefix, a_includeEmpty);
/*     */   }
/*     */ 
/*     */   public static Map<String, Object> getRequestAttributesAsMap(final HttpServletRequest a_request, String a_namePrefix, boolean a_includeEmptyStrings)
/*     */   {
/* 295 */     NameValuePairSource nvpSource = new NameValuePairSource()
/*     */     {
/*     */       public Enumeration getNameEnumeration()
/*     */       {
/* 302 */         return a_request.getAttributeNames();
/*     */       }
/*     */ 
/*     */       public Object getValue(String a_name)
/*     */       {
/* 307 */         return a_request.getAttribute(a_name);
/*     */       }
/*     */     };
/* 311 */     return getNameValuePairsByPrefix(nvpSource, a_namePrefix, a_includeEmptyStrings);
/*     */   }
/*     */ 
/*     */   private static <T> Map<String, T> getNameValuePairsByPrefix(NameValuePairSource<T> a_nvpSource, String a_namePrefix, boolean a_includeEmptyStrings)
/*     */   {
/* 316 */     Map map = new HashMap();
/*     */ 
/* 322 */     Enumeration en = a_nvpSource.getNameEnumeration();
/* 323 */     while (en.hasMoreElements())
/*     */     {
/* 325 */       String nameWithPrefix = (String)en.nextElement();
/* 326 */       if (nameWithPrefix.startsWith(a_namePrefix))
/*     */       {
/* 328 */         Object value = a_nvpSource.getValue(nameWithPrefix);
/* 329 */         if ((a_includeEmptyStrings) || (!(value instanceof String)) || (((String)value).length() > 0))
/*     */         {
/* 333 */           String nameWithoutPrefix = nameWithPrefix.substring(a_namePrefix.length());
/* 334 */           map.put(nameWithoutPrefix, value);
/*     */         }
/*     */       }
/*     */     }
/* 338 */     return map;
/*     */   }
/*     */ 
/*     */   public static void addAllParametersAsAttributes(HttpServletRequest a_request)
/*     */   {
/* 360 */     Enumeration en = a_request.getParameterNames();
/* 361 */     while (en.hasMoreElements())
/*     */     {
/* 363 */       String name = (String)en.nextElement();
/* 364 */       String value = a_request.getParameter(name);
/* 365 */       a_request.setAttribute(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void addParameterAsAttribute(HttpServletRequest a_request, String a_sName)
/*     */   {
/* 376 */     a_request.setAttribute(a_sName, a_request.getParameter(a_sName));
/*     */   }
/*     */ 
/*     */   public static String getParameterOrAttribute(ServletRequest a_request, String a_sName)
/*     */   {
/* 386 */     String sValue = a_request.getParameter(a_sName);
/* 387 */     if (sValue == null)
/*     */     {
/* 389 */       Object oValue = a_request.getAttribute(a_sName);
/* 390 */       if ((oValue != null) && ((oValue instanceof String)))
/*     */       {
/* 392 */         sValue = (String)oValue;
/*     */       }
/*     */     }
/* 395 */     return sValue;
/*     */   }
/*     */ 
/*     */   public static long getLongParameterOrAttribute(ServletRequest a_request, String a_sName)
/*     */   {
/* 405 */     String sValue = getParameterOrAttribute(a_request, a_sName);
/*     */ 
/* 407 */     return longFromStringParameter(sValue);
/*     */   }
/*     */ 
/*     */   public static long longFromStringParameter(String a_sId)
/*     */   {
/* 416 */     long lId = -1L;
/*     */ 
/* 419 */     if ((a_sId == null) || (a_sId.length() == 0))
/*     */     {
/* 421 */       return -1L;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 426 */       lId = Long.parseLong(a_sId);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/* 430 */       GlobalApplication.getInstance().getLogger().error("Couldn't convert value \"" + a_sId + "\" to long", nfe);
/*     */     }
/*     */ 
/* 433 */     return lId;
/*     */   }
/*     */ 
/*     */   public static int intFromStringParameter(String a_sId)
/*     */   {
/* 442 */     int id = -1;
/*     */ 
/* 445 */     if ((a_sId == null) || (a_sId.length() == 0))
/*     */     {
/* 447 */       return -1;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 452 */       id = Integer.parseInt(a_sId);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/* 456 */       GlobalApplication.getInstance().getLogger().error("Couldn't convert value \"" + a_sId + "\" to int", nfe);
/*     */     }
/*     */ 
/* 459 */     return id;
/*     */   }
/*     */ 
/*     */   public static boolean getBooleanParameter(ServletRequest a_request, String a_sName)
/*     */   {
/* 469 */     String sValue = a_request.getParameter(a_sName);
/* 470 */     return Boolean.parseBoolean(sValue);
/*     */   }
/*     */ 
/*     */   public static String getUserOS(HttpServletRequest a_request)
/*     */   {
/* 486 */     String sOS = null;
/*     */ 
/* 488 */     String sUserAgent = a_request.getHeader("User-Agent");
/*     */ 
/* 490 */     if (sUserAgent != null)
/*     */     {
/* 493 */       if (sUserAgent.split("Win16").length > 1)
/*     */       {
/* 495 */         sOS = "Windows 3.11";
/*     */       }
/* 497 */       else if (sUserAgent.split("(Windows 95)|(Win95)|(Windows_95)").length > 1)
/*     */       {
/* 499 */         sOS = "Windows 95";
/*     */       }
/* 501 */       else if (sUserAgent.split("(Windows 98)|(Win98)").length > 1)
/*     */       {
/* 503 */         sOS = "Windows 98";
/*     */       }
/* 505 */       else if (sUserAgent.split("(Windows NT 5.0)|(Windows 2000)").length > 1)
/*     */       {
/* 507 */         sOS = "Windows 2000";
/*     */       }
/* 509 */       else if (sUserAgent.split("(Windows NT 5.1)|(Windows XP)").length > 1)
/*     */       {
/* 511 */         sOS = "Windows XP";
/*     */       }
/* 513 */       else if (sUserAgent.split("(Windows NT 5.2)").length > 1)
/*     */       {
/* 515 */         sOS = "Windows Server 2003";
/*     */       }
/* 517 */       else if (sUserAgent.split("Windows NT 6\\.0").length > 1)
/*     */       {
/* 519 */         sOS = "Windows Vista";
/*     */       }
/* 521 */       else if (sUserAgent.split("(Windows NT 6.1)").length > 1)
/*     */       {
/* 523 */         sOS = "Windows 7";
/*     */       }
/* 525 */       else if (sUserAgent.split("(Windows NT 4.0)|(WinNT4.0)|(WinNT)|(Windows NT)").length > 1)
/*     */       {
/* 527 */         sOS = "Windows NT 4.0";
/*     */       }
/* 529 */       else if (sUserAgent.split("Windows ME").length > 1)
/*     */       {
/* 531 */         sOS = "Windows ME";
/*     */       }
/* 533 */       else if (sUserAgent.split("OpenBSD").length > 1)
/*     */       {
/* 535 */         sOS = "Open BSD";
/*     */       }
/* 537 */       else if (sUserAgent.split("SunOS").length > 1)
/*     */       {
/* 539 */         sOS = "Sun OS";
/*     */       }
/* 541 */       else if (sUserAgent.split("(Linux)|(X11)").length > 1)
/*     */       {
/* 543 */         sOS = "Linux";
/*     */       }
/* 545 */       else if (sUserAgent.split("(Mac_PowerPC)|(Macintosh)").length > 1)
/*     */       {
/* 547 */         sOS = "Mac OS";
/*     */       }
/* 549 */       else if (sUserAgent.split("QNX").length > 1)
/*     */       {
/* 551 */         sOS = "QNX";
/*     */       }
/* 553 */       else if (sUserAgent.split("BeOS").length > 1)
/*     */       {
/* 555 */         sOS = "BeOS";
/*     */       }
/* 557 */       else if (sUserAgent.split("OS/2").length > 1)
/*     */       {
/* 559 */         sOS = "OS/2";
/*     */       }
/*     */     }
/*     */ 
/* 563 */     if (sOS == null)
/*     */     {
/* 565 */       sOS = "Unknown";
/*     */     }
/* 567 */     return sOS;
/*     */   }
/*     */ 
/*     */   public static String getAsQueryParameter(HttpServletRequest a_request, String a_sParamName)
/*     */   {
/* 578 */     String sParamString = a_sParamName + "=";
/* 579 */     if (StringUtils.isNotEmpty(a_request.getParameter(a_sParamName)))
/*     */     {
/* 581 */       sParamString = sParamString + a_request.getParameter(a_sParamName);
/*     */     }
/* 583 */     return sParamString;
/*     */   }
/*     */ 
/*     */   public static Cookie getCookieByName(HttpServletRequest a_request, String a_sCookieName)
/*     */   {
/* 591 */     Cookie[] aCookies = a_request.getCookies();
/* 592 */     for (Cookie cookie : aCookies)
/*     */     {
/* 595 */       if ((cookie.getName() != null) && (cookie.getName().equals(a_sCookieName)))
/*     */       {
/* 597 */         return cookie;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 602 */     return null;
/*     */   }
/*     */ 
/*     */   private static abstract interface NameValuePairSource<T>
/*     */   {
/*     */     public abstract Enumeration getNameEnumeration();
/*     */ 
/*     */     public abstract T getValue(String paramString);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.RequestUtil
 * JD-Core Version:    0.6.0
 */