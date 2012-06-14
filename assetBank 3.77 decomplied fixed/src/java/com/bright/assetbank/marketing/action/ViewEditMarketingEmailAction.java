/*     */ package com.bright.assetbank.marketing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.marketing.constant.MarketingConstants;
/*     */ import com.bright.assetbank.marketing.form.SendMarketingEmailForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.bean.EmailContent;
/*     */ import com.bright.framework.mail.bean.EmailTemplate;
/*     */ import com.bright.framework.mail.constant.MailConstants;
/*     */ import com.bright.framework.mail.service.EmailTemplateManager;
/*     */ import com.bright.framework.mail.util.MailUtils;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEditMarketingEmailAction extends BTransactionAction
/*     */   implements AssetBoxConstants, AssetBankConstants, FrameworkConstants, MarketingConstants, MailConstants
/*     */ {
/*  56 */   private EmailTemplateManager m_emailTemplateManager = null;
/*  57 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     ActionForward afForward = null;
/*  67 */     SendMarketingEmailForm form = (SendMarketingEmailForm)a_form;
/*  68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  69 */     String[] asUserIds = a_request.getParameterValues("selectedUsers");
/*     */ 
/*  72 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  74 */       this.m_logger.debug("The user must be admin to access " + getClass().getSimpleName());
/*  75 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  78 */     if (form.getAssetBoxId() > 0L)
/*     */     {
/*  80 */       form.setAssetLink(ServletUtil.getApplicationUrl(a_request) + "/action/" + "viewEmailAssets" + "?" + "emailId" + "=" + "#" + "emailId" + "#");
/*     */     }
/*     */ 
/*  84 */     if ((asUserIds != null) && (asUserIds.length > 0))
/*     */     {
/*  86 */       if ((!form.getHasErrors()) && (a_request.getParameter("back") == null))
/*     */       {
/*  89 */         EmailContent email = form.getEmailContent();
/*  90 */         this.m_languageManager.createEmptyTranslations(a_dbTransaction, email);
/*     */ 
/*  93 */         if (StringUtils.isNotEmpty(form.getEmailTemplateId()))
/*     */         {
/*  95 */           Vector templates = this.m_emailTemplateManager.getEmailItems(a_dbTransaction, form.getEmailTemplateId(), 2L, 1);
/*  96 */           email = form.getEmailContent();
/*  97 */           MailUtils.populateEmailFromTemplates(email, templates);
/*     */ 
/* 100 */           if ((templates != null) && (templates.size() > 0))
/*     */           {
/* 102 */             EmailTemplate template = (EmailTemplate)templates.get(0);
/* 103 */             form.setFromAddress(template.getAddrFrom());
/* 104 */             form.setToAddress(template.getAddrTo());
/* 105 */             form.setCcAddress(template.getAddrCc());
/* 106 */             form.setBccAddress(template.getAddrBcc());
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 111 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     else
/*     */     {
/* 115 */       form.addError("Please select some users.");
/* 116 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 119 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateManager(EmailTemplateManager emailTemplateManager)
/*     */   {
/* 124 */     this.m_emailTemplateManager = emailTemplateManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 129 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.ViewEditMarketingEmailAction
 * JD-Core Version:    0.6.0
 */