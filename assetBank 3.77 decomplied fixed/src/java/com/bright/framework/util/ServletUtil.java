/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ServletUtil
/*    */ {
/*    */   public static String getApplicationUrl(HttpServletRequest a_request)
/*    */   {
/* 40 */     String path = FrameworkSettings.getApplicationUrl();
/* 41 */     if ((path == null) || (path.equals("")))
/*    */     {
/* 43 */       String requestUrl = a_request.getRequestURL().toString();
/* 44 */       String contextPath = a_request.getContextPath();
/*    */ 
/* 46 */       path = requestUrl.substring(0, requestUrl.indexOf(contextPath)) + contextPath;
/*    */     }
/* 48 */     return path;
/*    */   }
/*    */ 
/*    */   public static String getTomcatReloadUrl(HttpServletRequest a_request)
/*    */   {
/* 67 */     return "http://" + a_request.getServerName() + ":" + a_request.getServerPort() + "/manager/reload?path=" + a_request.getContextPath();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.ServletUtil
 * JD-Core Version:    0.6.0
 */