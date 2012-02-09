/*     */ package com.bright.framework.mail.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.mail.form.EmailTemplateForm;
/*     */ import com.bright.framework.mail.service.EmailTemplateManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ManageEmailTemplateAction extends BTransactionAction
/*     */ {
/*  47 */   protected EmailTemplateManager m_emailTemplateManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ActionForward afForward = null;
/*  77 */     EmailTemplateForm form = (EmailTemplateForm)a_form;
/*  78 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  81 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  83 */       this.m_logger.error("ManageEmailTemplateAction.execute : User must be an admin.");
/*  84 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  87 */     long lTypeId = getLongParameter(a_request, "typeId");
/*  88 */     long lLanguageId = getLongParameter(a_request, "languageId");
/*     */ 
/*  90 */     int iTemplatesToGet = getIsHidden(form);
/*     */ 
/*  93 */     if (lLanguageId > 0L)
/*     */     {
/*  95 */       form.setEmailTemplates(this.m_emailTemplateManager.getEmailItems(a_dbTransaction, lTypeId, new Language(lLanguageId), iTemplatesToGet));
/*     */     }
/*     */     else
/*     */     {
/*  99 */       form.setEmailTemplates(this.m_emailTemplateManager.getEmailItems(a_dbTransaction, lTypeId, iTemplatesToGet));
/*     */     }
/*     */ 
/* 102 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 104 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected int getIsHidden(EmailTemplateForm a_form)
/*     */   {
/* 110 */     int iTemplatesToGet = 1;
/* 111 */     a_form.setHidden(false);
/*     */ 
/* 113 */     return iTemplatesToGet;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateManager(EmailTemplateManager a_emailTemplateManager)
/*     */   {
/* 118 */     this.m_emailTemplateManager = a_emailTemplateManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.ManageEmailTemplateAction
 * JD-Core Version:    0.6.0
 */