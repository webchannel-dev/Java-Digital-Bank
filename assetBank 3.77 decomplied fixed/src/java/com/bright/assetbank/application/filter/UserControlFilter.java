/*     */ package com.bright.assetbank.application.filter;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class UserControlFilter
/*     */   implements Filter, AssetBankConstants, UserConstants
/*     */ {
/*  48 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   protected ABUserManager getUserManager() {
/*  51 */     if (this.m_userManager == null)
/*     */     {
/*     */       try
/*     */       {
/*  55 */         this.m_userManager = ((ABUserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager"));
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/*  59 */         GlobalApplication.getInstance().getLogger().error("ComponentException whilst getting user manager component : " + ce);
/*     */       }
/*     */     }
/*     */ 
/*  63 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   protected String getIPMapping(HttpServletRequest a_request)
/*     */   {
/*  69 */     if (AssetBankSettings.getUseIPMappings())
/*     */     {
/*  72 */       String sIPMapping = a_request.getHeader("X-Forwarded-For");
/*     */ 
/*  74 */       if (StringUtil.stringIsPopulated(sIPMapping))
/*     */       {
/*  76 */         GlobalApplication.getInstance().getLogger().info("preProcessActionPerform: read from X-Forwarded-For header: " + sIPMapping);
/*     */       }
/*     */       else
/*     */       {
/*  81 */         sIPMapping = a_request.getRemoteAddr();
/*  82 */         GlobalApplication.getInstance().getLogger().info("preProcessActionPerform: read from getRemoteAddr: " + sIPMapping);
/*     */       }
/*     */ 
/*  85 */       return sIPMapping;
/*     */     }
/*     */ 
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   protected String getURLGroupMapping(HttpServletRequest a_request)
/*     */   {
/*  94 */     String sURLGroupMapping = null;
/*     */ 
/*  96 */     if (AssetBankSettings.getUseURLMappings())
/*     */     {
/*  99 */       String sURLMappingParam = AssetBankSettings.getURLMappingParameter();
/* 100 */       sURLGroupMapping = a_request.getParameter(sURLMappingParam);
/*     */     }
/*     */ 
/* 104 */     return sURLGroupMapping;
/*     */   }
/*     */ 
/*     */   public abstract void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
/*     */     throws IOException, ServletException;
/*     */ 
/*     */   public void init(FilterConfig a_filterConfig)
/*     */     throws ServletException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.filter.UserControlFilter
 * JD-Core Version:    0.6.0
 */