/*     */ package com.bright.assetbank.application.filter;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class InitialiseProfileFilter extends UserControlFilter
/*     */ {
/*  44 */   private FilterManager m_filterManager = null;
/*     */ 
/*     */   public FilterManager getFilterManager() {
/*  47 */     if (this.m_filterManager == null)
/*     */     {
/*     */       try
/*     */       {
/*  51 */         this.m_filterManager = ((FilterManager)GlobalApplication.getInstance().getComponentManager().lookup("FilterManager"));
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/*  55 */         GlobalApplication.getInstance().getLogger().error("ComponentException whilst getting filter manager component : " + ce, ce);
/*     */       }
/*     */     }
/*     */ 
/*  59 */     return this.m_filterManager;
/*     */   }
/*     */ 
/*     */   public void doFilter(ServletRequest a_request, ServletResponse a_response, FilterChain a_chain)
/*     */     throws IOException, ServletException
/*     */   {
/*  65 */     HttpServletRequest request = (HttpServletRequest)a_request;
/*  66 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(request.getSession());
/*     */ 
/*  69 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  72 */       if ((userProfile.getPermissionCategoryIds(1) == null) || (getURLGroupMapping(request) != null))
/*     */       {
/*  76 */         userProfile.resetPermissionsKey();
/*     */         try
/*     */         {
/*  80 */           getUserManager().setCategoryPermissions(null, userProfile, getIPMapping(request), getURLGroupMapping(request));
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/*  84 */           GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst getting default categories.", bn2e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  89 */       if ((userProfile.getVisibleAttributeIds() == null) || (getURLGroupMapping(request) != null))
/*     */       {
/*     */         try
/*     */         {
/*  93 */           userProfile.setVisibleAttributeIds(getUserManager().getAttributeIdsForUser(null, getIPMapping(request), getURLGroupMapping(request)));
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/*  97 */           GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst getting default attributes.", bn2e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 102 */       if ((userProfile.getAttributeExclusions() == null) || (getURLGroupMapping(request) != null))
/*     */       {
/*     */         try
/*     */         {
/* 106 */           userProfile.setAttributeExclusions(getUserManager().getAttributeExclusionsForUser(null, getIPMapping(request), getURLGroupMapping(request)));
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/* 110 */           GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst getting default attribute exclusions.", bn2e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 115 */       if ((userProfile.getUsageExclusions() == null) || (getURLGroupMapping(request) != null))
/*     */       {
/*     */         try
/*     */         {
/* 119 */           userProfile.setUsageExclusions(getUserManager().getUsageExclusionsForUser(null, getIPMapping(request), getURLGroupMapping(request)));
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/* 123 */           GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst getting default usage exclusions.", bn2e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 128 */       if ((userProfile.getFilterExclusions() == null) || (getURLGroupMapping(request) != null))
/*     */       {
/*     */         try
/*     */         {
/* 132 */           userProfile.setFilterExclusions(getUserManager().getFilterExclusionsForUser(null, getIPMapping(request), getURLGroupMapping(request)));
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/* 136 */           GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst getting default filter exclusions.", bn2e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 143 */     if (!userProfile.getHaveSetDefaultFilter())
/*     */     {
/*     */       try
/*     */       {
/* 147 */         Filter defaultFilter = getFilterManager().getDefaultFilter(null);
/*     */ 
/* 149 */         if (defaultFilter != null)
/*     */         {
/* 151 */           userProfile.addSelectedFilter(defaultFilter);
/*     */         }
/*     */ 
/* 154 */         userProfile.setHaveSetDefaultFilter(true);
/*     */       }
/*     */       catch (Bn2Exception bn2e)
/*     */       {
/* 158 */         GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst trying to set default filter: " + bn2e.getMessage(), bn2e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 163 */     if (userProfile.getUserOS() == null)
/*     */     {
/* 165 */       userProfile.setUserOS(RequestUtil.getUserOS(request));
/*     */     }
/*     */ 
/* 168 */     a_chain.doFilter(a_request, a_response);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.filter.InitialiseProfileFilter
 * JD-Core Version:    0.6.0
 */