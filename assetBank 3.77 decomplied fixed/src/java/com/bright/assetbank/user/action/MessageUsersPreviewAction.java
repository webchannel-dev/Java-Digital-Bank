/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.MessageUsersForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.mail.HtmlEmail;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MessageUsersPreviewAction extends UserAction
/*     */ {
/*  50 */   private EmailManager m_emailManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     MessageUsersForm form = (MessageUsersForm)a_form;
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  79 */     validateMandatoryFields(form, a_request);
/*     */ 
/*  81 */     if (form.getHasErrors())
/*     */     {
/*  83 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  87 */     Vector vecGroupIds = getGroupIds(a_request);
/*  88 */     form.setGroupIds(vecGroupIds);
/*     */ 
/*  91 */     int iCount = 0;
/*  92 */     if ((vecGroupIds != null) && (vecGroupIds.size() > 0))
/*     */     {
/*  94 */       iCount = getUserManager().getUserCountForGroups(a_dbTransaction, vecGroupIds);
/*     */     }
/*     */ 
/*  97 */     if (StringUtil.stringIsPopulated(form.getToUser()))
/*     */     {
/*  99 */       String[] aExtras = form.getToUser().split(",");
/* 100 */       iCount += aExtras.length;
/*     */     }
/* 102 */     form.setUserCount(iCount);
/*     */ 
/* 105 */     HashMap hmParams = new HashMap();
/* 106 */     hmParams.put("template", "user_message");
/* 107 */     hmParams.put("body", form.getMessageBody());
/* 108 */     HtmlEmail email = null;
/*     */     try
/*     */     {
/* 111 */       email = this.m_emailManager.buildHtmlEmail(hmParams, null, 1L, userProfile.getCurrentLanguage());
/*     */     }
/*     */     catch (MessagingException e)
/*     */     {
/* 115 */       this.m_logger.error("MessageUsersPreview.execute: Error: " + e.getMessage());
/* 116 */       throw new Bn2Exception("MessageUsersPreview.execute: Error: " + e.getMessage(), e);
/*     */     }
/* 118 */     if (email != null)
/*     */     {
/* 120 */       form.setCompleteBody(email.getHtmlMsg());
/*     */     }
/* 122 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/* 128 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.MessageUsersPreviewAction
 * JD-Core Version:    0.6.0
 */