/*     */ package com.bright.framework.mail.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.constant.MailConstants;
/*     */ import com.bright.framework.mail.form.SendEmailForm;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.mail.util.MailUtils;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.mail.HtmlEmail;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class EmailThisPageAction extends BTransactionAction
/*     */   implements MailConstants, FrameworkConstants
/*     */ {
/*  48 */   protected EmailManager m_emailManager = null;
/*  49 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     ActionForward afForward = null;
/*     */ 
/*  75 */     SendEmailForm form = (SendEmailForm)a_form;
/*  76 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*  77 */     form.validate(a_dbTransaction, this.m_listManager, userProfile);
/*     */ 
/*  79 */     if (!form.getHasErrors())
/*     */     {
/*     */       try
/*     */       {
/*  84 */         HtmlEmail htmlEmail = MailUtils.populateHtmlMessage(form.getSubject(), form.getBody(), form.getFromAddress(), form.getToAddress(), null, null, null);
/*     */ 
/*  92 */         this.m_emailManager.sendEmail(htmlEmail);
/*     */       }
/*     */       catch (MessagingException e)
/*     */       {
/*  96 */         this.m_logger.error("ViewEmailThisPageAction.execute: Error: " + e.getMessage());
/*  97 */         throw new Bn2Exception(e.getMessage(), e);
/*     */       }
/*     */ 
/* 101 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     else
/*     */     {
/* 105 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/* 107 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/* 112 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 117 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.EmailThisPageAction
 * JD-Core Version:    0.6.0
 */