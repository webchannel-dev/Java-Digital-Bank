/*     */ package com.bright.assetbank.application.filter;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.assetbank.user.service.RoleManager;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AuthenticationFilter extends UserControlFilter
/*     */ {
/*  48 */   private RoleManager m_roleManager = null;
/*     */ 
/*     */   protected RoleManager getRoleManager() {
/*  51 */     if (this.m_roleManager == null)
/*     */     {
/*     */       try
/*     */       {
/*  55 */         this.m_roleManager = ((RoleManager)GlobalApplication.getInstance().getComponentManager().lookup("RoleManager"));
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/*  59 */         GlobalApplication.getInstance().getLogger().error("ComponentException whilst getting user manager component : " + ce);
/*     */       }
/*     */     }
/*     */ 
/*  63 */     return this.m_roleManager;
/*     */   }
/*     */ 
/*     */   public void doFilter(ServletRequest a_request, ServletResponse a_response, FilterChain a_chain)
/*     */     throws IOException, ServletException
/*     */   {
/*  71 */     HttpServletRequest request = (HttpServletRequest)a_request;
/*  72 */     String sKey = null;
/*     */ 
/*  75 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(request.getSession());
/*     */ 
/*  78 */     if (!RequestUtil.getIsUrlLoginAction(request.getPathInfo()))
/*     */     {
/*  81 */       if (!AssetBankSettings.getGuestUserCanViewUncategorisedAssets())
/*     */       {
/*  85 */         if (!userProfile.getIsLoggedIn())
/*     */         {
/*  87 */           if ((userProfile.getPermissionCategoryIds(1) == null) || (userProfile.getPermissionCategoryIds(1).size() == 0))
/*     */           {
/*  89 */             sKey = "NoPermission";
/*     */           }
/*     */           else
/*     */           {
/*  95 */             request.setAttribute("AuthenticationComplete", Boolean.TRUE);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 101 */       if ((userProfile.isPasswordExpired()) && (!RequestUtil.getIsUrlChangePasswordAction(request.getPathInfo())))
/*     */       {
/* 103 */         sKey = "ForcePasswordChange";
/*     */       }
/*     */ 
/* 106 */       if ((!userProfile.isIpPermitted()) && (StringUtil.stringIsPopulated(getIPMapping(request))) && (!userProfile.getIsAdmin()))
/*     */       {
/* 109 */         boolean bPermitted = false;
/*     */         try
/*     */         {
/* 112 */           bPermitted = getUserManager().getPublicGroupIPCheck(getIPMapping(request));
/* 113 */           userProfile.setIpPermitted(bPermitted);
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/* 118 */           GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception in getPublicGroupIPCheck.", bn2e);
/*     */         }
/*     */ 
/* 121 */         if (!bPermitted)
/*     */         {
/* 123 */           GlobalApplication.getInstance().getLogger().info("preProcessActionPerform: IP blocked: " + getIPMapping(request));
/*     */ 
/* 125 */           String sQueryString = "message=failedLoginIPBlock";
/*     */ 
/* 127 */           sKey = "LoginFailure";
/* 128 */           a_request.setAttribute("redirecting", new Boolean(true));
/* 129 */           a_request.setAttribute("queryString", sQueryString);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 141 */     if (AssetBankSettings.getUseGroupRoles())
/*     */     {
/* 144 */       if (!userProfile.getIsAdmin())
/*     */       {
/* 146 */         String sUrlToCheck = request.getRequestURI();
/*     */ 
/* 148 */         sUrlToCheck = sUrlToCheck.substring(sUrlToCheck.lastIndexOf("/") + 1, sUrlToCheck.length());
/* 149 */         int iUserRoleStatus = getRoleManager().getUserRoleStatusForAction(userProfile, sUrlToCheck);
/*     */ 
/* 152 */         if (iUserRoleStatus != -1)
/*     */         {
/* 155 */           if (iUserRoleStatus == 1)
/*     */           {
/* 159 */             userProfile.setAllowedByRole(true);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 166 */     a_request.setAttribute("forwardKey", sKey);
/* 167 */     a_chain.doFilter(a_request, a_response);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.filter.AuthenticationFilter
 * JD-Core Version:    0.6.0
 */