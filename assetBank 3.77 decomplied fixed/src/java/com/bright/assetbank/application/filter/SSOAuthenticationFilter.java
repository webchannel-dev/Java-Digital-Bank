/*    */ package com.bright.assetbank.application.filter;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.user.constant.UserSettings;
/*    */ import com.bright.framework.util.RequestUtil;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class SSOAuthenticationFilter
/*    */   implements Filter, AssetBankConstants
/*    */ {
/*    */   private static final String k_sAction_SSOAuthenticate = "/ssoAuthenticate";
/*    */ 
/*    */   public void doFilter(ServletRequest a_request, ServletResponse a_response, FilterChain a_chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 49 */     HttpServletRequest request = (HttpServletRequest)a_request;
/* 50 */     HttpServletResponse response = (HttpServletResponse)a_response;
/*    */ 
/* 53 */     boolean bFilterRun = Boolean.TRUE.equals(request.getAttribute("AuthenticationComplete"));
/*    */ 
/* 56 */     boolean bSSOEnabled = UserSettings.getSSOIsEnabled();
/*    */ 
/* 58 */     if ((!bFilterRun) && (bSSOEnabled))
/*    */     {
/* 60 */       UserProfile userProfile = UserProfile.getUserProfile(request.getSession());
/*    */ 
/* 62 */       String sAction = request.getPathInfo();
/* 63 */       boolean bIsLoginRequest = RequestUtil.getIsUrlLoginAction(sAction);
/*    */ 
/* 70 */       if ((!bIsLoginRequest) && (!userProfile.getIsLoggedIn()))
/*    */       {
/* 73 */         RequestUtil.setOriginalRequestUrl(request);
/* 74 */         response.sendRedirect(request.getContextPath() + request.getServletPath() + "/ssoAuthenticate");
/* 75 */         return;
/*    */       }
/*    */     }
/*    */ 
/* 79 */     continueChain(request, response, a_chain);
/*    */   }
/*    */ 
/*    */   private void continueChain(HttpServletRequest a_request, HttpServletResponse a_response, FilterChain a_chain)
/*    */     throws ServletException, IOException
/*    */   {
/* 86 */     a_request.setAttribute("AuthenticationComplete", Boolean.TRUE);
/* 87 */     a_chain.doFilter(a_request, a_response);
/* 88 */     a_request.getSession().removeAttribute("OriginalRequestUrl");
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig a_filterConfig)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.filter.SSOAuthenticationFilter
 * JD-Core Version:    0.6.0
 */