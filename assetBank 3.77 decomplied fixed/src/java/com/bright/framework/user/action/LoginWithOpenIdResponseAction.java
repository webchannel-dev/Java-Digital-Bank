/*     */ package com.bright.framework.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import com.bright.framework.user.service.OpenIdManager;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class LoginWithOpenIdResponseAction extends LoginAction
/*     */ {
/*  44 */   private OpenIdManager m_openIdManager = null;
/*  45 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  54 */     ActionForward afForward = null;
/*     */ 
/*  56 */     LoginForm form = (LoginForm)a_form;
/*     */ 
/*  58 */     User remoteUser = this.m_openIdManager.getVerifiedUser(a_request);
/*     */ 
/*  60 */     String sQueryString = a_request.getQueryString();
/*     */ 
/*  62 */     if (StringUtils.isNotEmpty(sQueryString))
/*     */     {
/*  64 */       if (sQueryString.matches("forwardUrl=[^&].+"))
/*     */       {
/*  66 */         sQueryString = sQueryString.replaceAll("&amp;", "&");
/*  67 */         form.setForwardUrl(sQueryString.substring("forwardUrl".length() + 1));
/*     */       }
/*     */     }
/*     */ 
/*  71 */     if (remoteUser != null)
/*     */     {
/*  73 */       String sPrefix = this.m_listManager.getListItem(a_dbTransaction, "technical-open-id-prefix", LanguageConstants.k_defaultLanguage, null);
/*     */ 
/*  75 */       String sRemoteId = remoteUser.getRemoteUsername().substring(sPrefix.length());
/*     */ 
/*  77 */       if (sRemoteId.endsWith("/"))
/*     */       {
/*  79 */         sRemoteId = sRemoteId.substring(0, sRemoteId.length() - 1);
/*     */       }
/*     */ 
/*  82 */       if (sRemoteId.lastIndexOf("#") > 0)
/*     */       {
/*  84 */         sRemoteId = sRemoteId.substring(0, sRemoteId.lastIndexOf("#"));
/*     */       }
/*     */ 
/*  88 */       long lId = this.m_userManager.getUserIdForRemoteUsername(a_dbTransaction, sRemoteId);
/*     */ 
/*  91 */       if ((lId <= 0L) && (FrameworkSettings.getImportRemoteUsersOnTheFly()))
/*     */       {
/*  93 */         lId = this.m_userManager.getUserIdForLocalUsername(a_dbTransaction, sRemoteId);
/*     */       }
/*     */ 
/*  96 */       if ((!FrameworkSettings.getImportRemoteUsersOnTheFly()) && (lId <= 0L))
/*     */       {
/*  98 */         form.addError("You haven't been set up as a user for this application - please contact an administrator.");
/*  99 */         afForward = a_mapping.findForward("Failure");
/*     */       }
/*     */       else
/*     */       {
/* 103 */         User localUser = null;
/*     */ 
/* 106 */         if ((FrameworkSettings.getImportRemoteUsersOnTheFly()) && (lId <= 0L))
/*     */         {
/* 108 */           localUser = this.m_userManager.createUser();
/* 109 */           localUser.setRemoteUser(true);
/* 110 */           localUser.setUsername(sRemoteId);
/* 111 */           localUser.setRemoteUsername(sRemoteId);
/* 112 */           localUser.setForename(remoteUser.getForename());
/* 113 */           localUser.setSurname(remoteUser.getSurname());
/* 114 */           localUser.setEmailAddress(remoteUser.getEmailAddress());
/* 115 */           localUser = this.m_userManager.saveUser(a_dbTransaction, localUser);
/*     */         }
/*     */         else
/*     */         {
/* 119 */           localUser = this.m_userManager.getUser(a_dbTransaction, lId);
/*     */         }
/*     */ 
/* 123 */         afForward = doLogin(a_dbTransaction, 
/* 124 */           a_mapping, 
/* 125 */           form, 
/* 126 */           a_request, 
/* 127 */           a_response, 
/* 128 */           localUser.getUsername(), 
/* 129 */           "", 
/* 130 */           true);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 135 */       form.setLoginFailed(true);
/* 136 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 139 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 144 */     this.m_listManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setOpenIdManager(OpenIdManager a_openIdManager)
/*     */   {
/* 149 */     this.m_openIdManager = a_openIdManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.LoginWithOpenIdResponseAction
 * JD-Core Version:    0.6.0
 */