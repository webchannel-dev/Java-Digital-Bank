/*     */ package com.bright.framework.mail.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.bean.EmailTemplate;
/*     */ import com.bright.framework.mail.form.EmailTemplateForm;
/*     */ import com.bright.framework.mail.service.EmailTemplateManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveEmailTemplatesAction extends BTransactionAction
/*     */ {
/*  48 */   private EmailTemplateManager m_emailTemplateManager = null;
/*  49 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     ActionForward afForward = null;
/*  69 */     EmailTemplateForm form = (EmailTemplateForm)a_form;
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  75 */       this.m_logger.error("SaveEmailTemplateAction.execute : User must be admin.");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  79 */     long lTypeId = getLongParameter(a_request, "typeId");
/*     */ 
/*  81 */     validateMandatoryFields(form, a_request);
/*     */ 
/*  84 */     if (form.isAddingTemplate())
/*     */     {
/*  86 */       String sId = ((EmailTemplate)form.getEmailTemplates().get(0)).getCode();
/*  87 */       sId = sId.toLowerCase().replaceAll("[ \\.,'\"]+", "_");
/*  88 */       String sConstructedId = sId;
/*  89 */       int iCount = 1;
/*  90 */       while (this.m_emailTemplateManager.getEmailItem(a_dbTransaction, sConstructedId, lTypeId, LanguageConstants.k_defaultLanguage) != null)
/*     */       {
/*  92 */         sConstructedId = sId + iCount++;
/*     */       }
/*  94 */       ((EmailTemplate)form.getEmailTemplates().get(0)).setTextId(sConstructedId);
/*     */     }
/*     */ 
/*  98 */     for (int i = 0; i < form.getEmailTemplates().size(); i++)
/*     */     {
/* 100 */       EmailTemplate template = (EmailTemplate)form.getEmailTemplates().get(i);
/* 101 */       if ((template.getLanguage().getId() <= 0L) || (template.getLanguage().equals(LanguageConstants.k_defaultLanguage)))
/*     */         continue;
/* 103 */       if (((!StringUtils.isEmpty(template.getAddrBody())) || (!StringUtils.isNotEmpty(template.getAddrSubject()))) && ((!StringUtils.isNotEmpty(template.getAddrBody())) || (!StringUtils.isEmpty(template.getAddrSubject())))) {
/*     */         continue;
/*     */       }
/* 106 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationSubjectBody", userProfile.getCurrentLanguage()).getBody());
/* 107 */       break;
/*     */     }
/*     */ 
/* 112 */     if (form.getHasErrors())
/*     */     {
/* 114 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 119 */       EmailTemplate defaultLanguageTemplate = null;
/* 120 */       for (int i = 0; i < form.getEmailTemplates().size(); i++)
/*     */       {
/* 122 */         EmailTemplate template = (EmailTemplate)form.getEmailTemplates().get(i);
/* 123 */         if (template.getLanguage().getId() <= 0L)
/*     */           continue;
/* 125 */         if (template.getLanguage().equals(LanguageConstants.k_defaultLanguage))
/*     */         {
/* 127 */           defaultLanguageTemplate = template;
/*     */         }
/*     */         else
/*     */         {
/* 131 */           template.setTextId(defaultLanguageTemplate.getTextId());
/* 132 */           template.setCode(defaultLanguageTemplate.getCode());
/* 133 */           template.setAddrFrom(defaultLanguageTemplate.getAddrFrom());
/* 134 */           template.setAddrTo(defaultLanguageTemplate.getAddrTo());
/* 135 */           template.setAddrCc(defaultLanguageTemplate.getAddrCc());
/* 136 */           template.setAddrBcc(defaultLanguageTemplate.getAddrBcc());
/*     */         }
/* 138 */         this.m_emailTemplateManager.saveEmailItem(a_dbTransaction, template, lTypeId);
/*     */       }
/*     */ 
/* 142 */       afForward = createRedirectingForward("typeId=" + lTypeId + "&" + "languageId" + "=" + 1L, a_mapping, "Success");
/*     */     }
/*     */ 
/* 146 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateManager(EmailTemplateManager a_emailTemplateManager)
/*     */   {
/* 151 */     this.m_emailTemplateManager = a_emailTemplateManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 156 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.SaveEmailTemplatesAction
 * JD-Core Version:    0.6.0
 */