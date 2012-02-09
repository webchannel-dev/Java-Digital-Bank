/*     */ package com.bright.framework.user.sso;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class BaseSSOPlugin extends Bn2Manager
/*     */ {
/*     */   public void resetAfterLogin(HttpServletResponse a_response)
/*     */   {
/*     */   }
/*     */ 
/*     */   public User initialSessionCheck(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     return null;
/*     */   }
/*     */ 
/*     */   public void doPreLogoutActions(HttpServletResponse a_response)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static StringBuffer getSSOCreateSessionActionUrl(HttpServletRequest a_request)
/*     */   {
/*  78 */     StringBuffer returnURL = new StringBuffer(ServletUtil.getApplicationUrl(a_request));
/*  79 */     returnURL.append("/action/").append("ssoCreateSession");
/*     */ 
/*  81 */     return returnURL;
/*     */   }
/*     */ 
/*     */   public static StringBuffer getSSODestroySessionActionUrl(HttpServletRequest a_request)
/*     */   {
/*  92 */     StringBuffer returnURL = new StringBuffer(ServletUtil.getApplicationUrl(a_request));
/*  93 */     returnURL.append("/action/").append("ssoDestroySession");
/*     */ 
/*  95 */     return returnURL;
/*     */   }
/*     */ 
/*     */   public static StringBuffer getSSOLoginFormUrl(HttpServletRequest a_request)
/*     */   {
/* 106 */     StringBuffer returnURL = new StringBuffer(ServletUtil.getApplicationUrl(a_request));
/* 107 */     returnURL.append("/action/").append("viewSSOLogin");
/*     */ 
/* 109 */     return returnURL;
/*     */   }
/*     */ 
/*     */   public static StringBuffer getLoginFormUrl(HttpServletRequest a_request)
/*     */   {
/* 120 */     StringBuffer returnURL = new StringBuffer(ServletUtil.getApplicationUrl(a_request));
/* 121 */     returnURL.append("/action/").append("viewLogin");
/*     */ 
/* 123 */     return returnURL;
/*     */   }
/*     */ 
/*     */   public static String[] parseFullUsername(String sFullName)
/*     */   {
/* 135 */     String[] asNames = new String[2];
/* 136 */     asNames[0] = "";
/* 137 */     asNames[1] = "";
/*     */ 
/* 139 */     if (StringUtils.isNotEmpty(sFullName))
/*     */     {
/* 141 */       sFullName = sFullName.trim();
/* 142 */       asNames[0] = sFullName.substring(0, Math.max(0, sFullName.lastIndexOf(" ")));
/* 143 */       asNames[1] = sFullName.substring(sFullName.lastIndexOf(" ") + 1);
/*     */     }
/*     */ 
/* 146 */     return asNames;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.BaseSSOPlugin
 * JD-Core Version:    0.6.0
 */