/*     */ package com.bright.framework.mail.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.constant.MailConstants;
/*     */ import com.bright.framework.mail.form.SendEmailForm;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.mail.HtmlEmail;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEmailThisPageAction extends BTransactionAction
/*     */   implements MailConstants, FrameworkConstants
/*     */ {
/*  53 */   protected EmailManager m_emailManager = null;
/*  54 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     ActionForward afForward = null;
/*  78 */     SendEmailForm form = (SendEmailForm)a_form;
/*  79 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  81 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  83 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  89 */       String sRedirectUrl = (String)a_request.getSession().getAttribute("lastGetRequestUri");
/*  90 */       sRedirectUrl = ServletUtil.getApplicationUrl(a_request) + sRedirectUrl;
/*  91 */       form.setBackUrl(sRedirectUrl);
/*     */ 
/*  93 */       if (FrameworkSettings.getEmailThisPageIntegrated())
/*     */       {
/*  96 */         String sAction = sRedirectUrl.substring(sRedirectUrl.lastIndexOf("/") + 1, sRedirectUrl.length());
/*  97 */         String sServer = ServletUtil.getApplicationUrl(a_request).substring(0, ServletUtil.getApplicationUrl(a_request).lastIndexOf("/"));
/*  98 */         sRedirectUrl = sServer + "/" + FrameworkSettings.getWIAUrl() + URLEncoder.encode(sAction, "UTF-8");
/*     */       }
/*     */ 
/* 101 */       form.setRedirectUrl(sRedirectUrl);
/* 102 */       String sLinkName = a_request.getParameter("page");
/* 103 */       if (!StringUtil.stringIsPopulated(sLinkName))
/*     */       {
/* 105 */         sLinkName = this.m_listManager.getListItem(a_dbTransaction, "link-visit-the-page", userProfile.getCurrentLanguage(), null);
/*     */       }
/*     */ 
/* 108 */       String sName = this.m_listManager.getListItem(a_dbTransaction, "snippet-your-name", userProfile.getCurrentLanguage(), null);
/* 109 */       if ((userProfile.getUser() != null) && (StringUtil.stringIsPopulated(userProfile.getUser().getForename())) && (StringUtil.stringIsPopulated(userProfile.getUser().getSurname())))
/*     */       {
/* 113 */         sName = userProfile.getUser().getForename() + " " + userProfile.getUser().getSurname();
/*     */       }
/*     */ 
/* 116 */       if ((userProfile.getUser() != null) && (StringUtil.isValidEmailAddress(userProfile.getUser().getEmailAddress())))
/*     */       {
/* 118 */         form.setFromAddress(userProfile.getUser().getEmailAddress());
/*     */       }
/*     */       else
/*     */       {
/* 122 */         form.setFromAddress("noreply@noreply.com");
/*     */       }
/*     */ 
/* 126 */       HashMap hmParams = new HashMap();
/* 127 */       hmParams.put("template", "email_this_page");
/* 128 */       hmParams.put("url", sRedirectUrl);
/* 129 */       hmParams.put("link", sLinkName);
/* 130 */       hmParams.put("name", sName);
/* 131 */       HtmlEmail email = this.m_emailManager.buildHtmlEmail(hmParams, null, 1L, userProfile.getCurrentLanguage());
/* 132 */       form.setEmail(email);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 136 */       this.m_logger.error("ViewEmailThisPageAction.execute: Error: " + e.getMessage());
/* 137 */       throw new Bn2Exception(e.getMessage(), e);
/*     */     }
/*     */ 
/* 140 */     afForward = a_mapping.findForward("Success");
/* 141 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/* 146 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 151 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.ViewEmailThisPageAction
 * JD-Core Version:    0.6.0
 */