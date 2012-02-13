/*     */ package com.bright.assetbank.marketing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.marketing.constant.MarketingConstants;
/*     */ import com.bright.assetbank.marketing.form.SendMarketingEmailForm;
/*     */ import com.bright.assetbank.marketing.util.MarketingUtils;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.mail.bean.EmailContent;
/*     */ import com.bright.framework.mail.util.MailUtils;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
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
/*     */ public class PreviewMarketingEmailAction extends BTransactionAction
/*     */   implements AssetBoxConstants, AssetBankConstants, FrameworkConstants, MarketingConstants
/*     */ {
/*  56 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  65 */     ActionForward afForward = null;
/*  66 */     SendMarketingEmailForm form = (SendMarketingEmailForm)a_form;
/*  67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  70 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  72 */       this.m_logger.debug("The user must be admin to access " + getClass().getSimpleName());
/*  73 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  77 */     if ((!form.getHasErrors()) && (!validateMandatoryFields(form, a_request)))
/*     */     {
/*  79 */       return a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/*  82 */     List langs = this.m_languageManager.getLanguages(a_dbTransaction, true, false);
/*  83 */     form.setLanguages(langs);
/*     */ 
/*  85 */     this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getMarketingEmail());
/*     */ 
/*  87 */     EmailContent content = form.getEmailContent();
/*     */ 
/*  89 */     String sToAddress = form.getToAddress();
/*  90 */     String sCcAddress = form.getCcAddress();
/*  91 */     String sBccAddress = form.getBccAddress();
/*     */ 
/*  93 */     sToAddress = sToAddress.replaceAll("#recipients#", "johnsmith@example-address.com");
/*  94 */     sCcAddress = sCcAddress.replaceAll("#recipients#", "johnsmith@example-address.com");
/*  95 */     sBccAddress = sBccAddress.replaceAll("#recipients#", "johnsmith@example-address.com");
/*     */ 
/*  97 */     Vector previewEmails = new Vector(langs.size());
/*  98 */     for (int i = 0; i < langs.size(); i++)
/*     */     {
/* 100 */       HtmlEmail email = null;
/* 101 */       Language lang = (Language)langs.get(i);
/*     */ 
/* 103 */       String sBody = content.getBody(lang);
/*     */ 
/* 105 */       sBody = MarketingUtils.replaceEmailVariables(sBody, "jsmith", "Mr", "John", "Smith");
/*     */       try
/*     */       {
/* 109 */         email = MailUtils.populateHtmlMessage(content.getSubject(lang), sBody, form.getFromAddress(), sToAddress, sCcAddress, sBccAddress, null);
/*     */       }
/*     */       catch (MessagingException e)
/*     */       {
/* 113 */         this.m_logger.error("PreviewMarketingEmailAction.execute() : MessagingException whilst populating email for preview : " + e.getLocalizedMessage());
/* 114 */         throw new Bn2Exception("PreviewMarketingEmailAction.execute() : MessagingException whilst populating email for preview.", e);
/*     */       }
/*     */ 
/* 117 */       previewEmails.add(email);
/*     */     }
/*     */ 
/* 120 */     form.setPreviewEmails(previewEmails);
/*     */ 
/* 122 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 124 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 129 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.PreviewMarketingEmailAction
 * JD-Core Version:    0.6.0
 */