/*    */ package com.bright.assetbank.application.filter;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.RequestDispatcher;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class UpdateInProgressFilter
/*    */   implements Filter
/*    */ {
/*    */   public void doFilter(ServletRequest a_request, ServletResponse a_response, FilterChain a_chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 49 */     if (AssetBankSettings.isApplicationUpdateInProgress())
/*    */     {
/* 51 */       if (isUpdaterRequest((HttpServletRequest)a_request))
/*    */       {
/* 54 */         String sActionName = getActionName((HttpServletRequest)a_request);
/* 55 */         ((HttpServletRequest)a_request).getRequestDispatcher(sActionName).forward(a_request, a_response);
/*    */       }
/*    */       else
/*    */       {
/* 60 */         ((HttpServletRequest)a_request).getRequestDispatcher("viewApplicationUpdateAdmin").forward(a_request, a_response);
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 65 */       a_chain.doFilter(a_request, a_response);
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean isUpdaterRequest(HttpServletRequest a_request)
/*    */   {
/* 76 */     String sActionName = getActionName(a_request);
/*    */ 
/* 78 */     if (sActionName != null)
/*    */     {
/* 80 */       return (sActionName.equals("viewApplicationUpdateAdmin")) || (sActionName.equals("viewApplicationUpdateDetails")) || (sActionName.equals("startApplicationUpdate")) || (sActionName.equals("viewApplicationUpdateProgress"));
/*    */     }
/*    */ 
/* 85 */     return false;
/*    */   }
/*    */ 
/*    */   private String getActionName(HttpServletRequest a_request)
/*    */   {
/* 91 */     String sActionName = null;
/*    */ 
/* 93 */     String sPathInfo = a_request.getPathInfo();
/* 94 */     if ((StringUtils.isNotEmpty(sPathInfo)) && (sPathInfo.charAt(0) == '/'))
/*    */     {
/* 96 */       sActionName = a_request.getPathInfo().substring(1);
/*    */     }
/*    */ 
/* 99 */     return sActionName;
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig arg0)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.filter.UpdateInProgressFilter
 * JD-Core Version:    0.6.0
 */