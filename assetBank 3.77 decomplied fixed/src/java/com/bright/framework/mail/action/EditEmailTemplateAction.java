/*     */ package com.bright.framework.mail.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.service.LanguageManager;
/*     */ import com.bright.framework.mail.bean.EmailTemplate;
/*     */ import com.bright.framework.mail.form.EmailTemplateForm;
/*     */ import com.bright.framework.mail.service.EmailTemplateManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class EditEmailTemplateAction extends BTransactionAction
/*     */ {
/*  56 */   private EmailTemplateManager m_emailTemplateManager = null;
/*  57 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  86 */     ActionForward afForward = null;
/*  87 */     EmailTemplateForm form = (EmailTemplateForm)a_form;
/*  88 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  91 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  93 */       this.m_logger.error("EditEmailTemplateAction.execute : User must be an admin.");
/*  94 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  97 */     long lTypeId = getLongParameter(a_request, "typeId");
/*     */ 
/*  99 */     if (!form.getHasErrors())
/*     */     {
/* 101 */       long lLanguageId = getLongParameter(a_request, "languageId");
/* 102 */       String sTextId = a_request.getParameter("textId");
/*     */ 
/* 105 */       if (StringUtils.isNotEmpty(sTextId))
/*     */       {
/* 108 */         if (lLanguageId > 0L)
/*     */         {
/* 111 */           form.setEmailTemplate(this.m_emailTemplateManager.getEmailItem(a_dbTransaction, sTextId, lTypeId, new Language(lLanguageId)));
/*     */         }
/*     */         else
/*     */         {
/* 117 */           form.setEmailTemplates(this.m_emailTemplateManager.getEmailItems(a_dbTransaction, sTextId, lTypeId, 1));
/*     */         }
/*     */ 
/*     */       }
/* 121 */       else if (lLanguageId <= 0L)
/*     */       {
/* 123 */         List languages = this.m_languageManager.getLanguages(a_dbTransaction, true, true);
/* 124 */         Vector vTemplates = new Vector();
/*     */ 
/* 126 */         for (Iterator it = languages.iterator(); it.hasNext(); )
/*     */         {
/* 128 */           Language language = (Language)it.next();
/* 129 */           EmailTemplate template = new EmailTemplate();
/* 130 */           template.setLanguage(language);
/* 131 */           vTemplates.add(template);
/*     */         }
/*     */ 
/* 134 */         form.setEmailTemplates(vTemplates);
/* 135 */         form.setAddingTemplate(true);
/*     */       }
/*     */     }
/*     */ 
/* 139 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 141 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateManager(EmailTemplateManager a_emailTemplateManager)
/*     */   {
/* 146 */     this.m_emailTemplateManager = a_emailTemplateManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 151 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.EditEmailTemplateAction
 * JD-Core Version:    0.6.0
 */