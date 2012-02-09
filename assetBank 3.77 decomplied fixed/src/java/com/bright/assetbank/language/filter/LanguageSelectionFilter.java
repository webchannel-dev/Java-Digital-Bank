/*     */ package com.bright.assetbank.language.filter;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.service.LanguageManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class LanguageSelectionFilter
/*     */   implements Filter
/*     */ {
/*  62 */   private Log m_logger = null;
/*     */ 
/*     */   public void doFilter(ServletRequest a_request, ServletResponse a_response, FilterChain a_chain)
/*     */     throws IOException, ServletException
/*     */   {
/*  70 */     HttpServletRequest httpRequest = (HttpServletRequest)a_request;
/*  71 */     HttpSession session = httpRequest.getSession();
/*     */ 
/*  73 */     if (!AssetBankSettings.isApplicationUpdateInProgress())
/*     */     {
/*  75 */       LanguageManager languageManager = null;
/*  76 */       List lsAvailableLanguages = null;
/*     */ 
/*  79 */       if (AssetBankSettings.getDatabaseSupportsUTF8())
/*     */       {
/*  81 */         a_request.setCharacterEncoding("utf-8");
/*     */       }
/*     */ 
/*  85 */       if (AssetBankSettings.getAuditLogEnabled())
/*     */       {
/*  87 */         if (session.getAttribute("ipAddress") == null)
/*     */         {
/*  89 */           session.setAttribute("ipAddress", a_request.getRemoteAddr());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  95 */       if (session.getAttribute("currentLanguage") == null)
/*     */       {
/*  97 */         String sLanguage = a_request.getLocale().getLanguage().toLowerCase();
/*     */ 
/* 100 */         int iIndexOfHyphen = sLanguage.indexOf('-');
/* 101 */         if (iIndexOfHyphen > 0)
/*     */         {
/* 103 */           sLanguage = sLanguage.substring(0, iIndexOfHyphen);
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 108 */           languageManager = (LanguageManager)GlobalApplication.getInstance().getComponentManager().lookup("LanguageManager");
/*     */ 
/* 110 */           Language foundLanguage = null;
/*     */ 
/* 113 */           if (AssetBankSettings.getSupportMultiLanguage())
/*     */           {
/* 116 */             lsAvailableLanguages = languageManager.getLanguages(null, true);
/*     */ 
/* 119 */             Iterator itLanguages = lsAvailableLanguages.iterator();
/*     */ 
/* 121 */             while (itLanguages.hasNext())
/*     */             {
/* 123 */               Language language = (Language)itLanguages.next();
/*     */ 
/* 125 */               if ((language.getCode().equalsIgnoreCase(sLanguage)) && (!language.isSuspended()))
/*     */               {
/* 127 */                 foundLanguage = language;
/* 128 */                 break;
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 134 */           if (foundLanguage == null)
/*     */           {
/* 136 */             foundLanguage = (Language)LanguageConstants.k_defaultLanguage.clone();
/*     */           }
/*     */ 
/* 140 */           session.setAttribute("currentLanguage", foundLanguage);
/*     */ 
/* 143 */           UserProfile userProfile = UserProfile.getUserProfile(httpRequest);
/* 144 */           if (userProfile.getCurrentLanguage() == null)
/*     */           {
/* 146 */             userProfile.setCurrentLanguage(foundLanguage);
/*     */           }
/*     */         }
/*     */         catch (ComponentException ce)
/*     */         {
/* 151 */           this.m_logger.error("ComponentException whilst loading language manager: " + ce.getMessage(), ce);
/*     */         }
/*     */         catch (Bn2Exception bn2)
/*     */         {
/* 155 */           this.m_logger.error("Exception whilst obtaining default language: " + bn2.getMessage(), bn2);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 161 */         UserProfile userProfile = UserProfile.getUserProfile(httpRequest);
/* 162 */         if ((userProfile != null) && (!userProfile.getCurrentLanguage().equals(session.getAttribute("currentLanguage"))))
/*     */         {
/* 164 */           session.setAttribute("currentLanguage", userProfile.getCurrentLanguage());
/*     */         }
/*     */       }
/*     */     }
/* 168 */     else if (session.getAttribute("currentLanguage") == null)
/*     */     {
/* 171 */       session.setAttribute("currentLanguage", LanguageConstants.k_defaultLanguage.clone());
/*     */     }
/*     */ 
/* 174 */     a_chain.doFilter(a_request, a_response);
/*     */   }
/*     */ 
/*     */   public void init(FilterConfig arg0) throws ServletException
/*     */   {
/* 179 */     this.m_logger = GlobalApplication.getInstance().getLogger();
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.language.filter.LanguageSelectionFilter
 * JD-Core Version:    0.6.0
 */