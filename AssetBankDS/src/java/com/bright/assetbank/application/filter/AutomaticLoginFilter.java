/*    */ package com.bright.assetbank.application.filter;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.user.constant.UserSettings;
/*    */ import com.bright.framework.util.RequestUtil;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class AutomaticLoginFilter extends UserControlFilter
/*    */ {
/*    */   public void doFilter(ServletRequest a_request, ServletResponse a_response, FilterChain a_chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 45 */     HttpServletRequest request = (HttpServletRequest)a_request;
/* 46 */     HttpServletResponse response = (HttpServletResponse)a_response;
/* 47 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(request.getSession());
/*    */ 
/* 49 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 52 */       if ((UserSettings.getAllowAutoLoginUsingCookie()) && (!userProfile.getCheckedCookies()) && (!RequestUtil.getIsUrlLoginAction(request.getPathInfo())))
/*    */       {
/*    */         try
/*    */         {
/* 58 */           getUserManager().loginUsingCookie(request, response);
/*    */         }
/*    */         catch (Bn2Exception bn2e)
/*    */         {
/* 62 */           GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst trying to auto-log in user: " + bn2e.getMessage());
/*    */         }
/*    */ 
/* 66 */         userProfile.setCheckedCookies(true);
/*    */       }
/*    */     }
/* 69 */     a_chain.doFilter(a_request, a_response);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.filter.AutomaticLoginFilter
 * JD-Core Version:    0.6.0
 */