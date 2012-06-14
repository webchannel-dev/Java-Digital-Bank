/*    */ package com.bright.assetbank.application.filter;
/*    */ 
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class StaticResourceFilter
/*    */   implements Filter
/*    */ {
/* 46 */   private static String k_sStaticResourcePathInfo = null;
/*    */ 
/*    */   public void doFilter(ServletRequest a_request, ServletResponse a_response, FilterChain a_chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 51 */     HttpServletRequest httpRequest = (HttpServletRequest)a_request;
/* 52 */     if ((StringUtils.isNotEmpty(getStaticResourcePathInfo())) && (StringUtils.isNotEmpty(httpRequest.getPathInfo())) && (httpRequest.getPathInfo().startsWith(getStaticResourcePathInfo())))
/*    */     {
/* 55 */       HttpServletResponse httpResponse = (HttpServletResponse)a_response;
/* 56 */       String path = httpRequest.getSession().getServletContext().getRealPath("/files/static/" + httpRequest.getPathInfo().substring(getStaticResourcePathInfo().length()));
/*    */ 
/* 59 */       if (!new File(path).exists())
/*    */       {
/* 61 */         GlobalApplication.getInstance().getLogger().debug("Static resource " + path + " does not exist - returning error code 404.");
/* 62 */         httpResponse.sendError(404);
/* 63 */         return;
/*    */       }
/*    */ 
/* 66 */       httpRequest.getRequestDispatcher("/servlet/display?file=" + FileUtil.encryptFilepath(path)).forward(httpRequest, httpResponse);
/*    */     }
/*    */     else
/*    */     {
/* 70 */       a_chain.doFilter(a_request, a_response);
/*    */     }
/*    */   }
/*    */ 
/*    */   private static String getStaticResourcePathInfo()
/*    */   {
/* 76 */     if (k_sStaticResourcePathInfo == null)
/*    */     {
/* 78 */       k_sStaticResourcePathInfo = "/" + AssetBankSettings.getStaticResourcePathSuffix() + "/";
/*    */     }
/*    */ 
/* 81 */     return k_sStaticResourcePathInfo;
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig filterconfig)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.filter.StaticResourceFilter
 * JD-Core Version:    0.6.0
 */