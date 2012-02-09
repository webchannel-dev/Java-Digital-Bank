/*     */ package com.bright.framework.mail.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.bean.EmailTemplate;
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
/*     */ public class SaveEmailTemplateAction extends BTransactionAction
/*     */ {
/*  47 */   private EmailTemplateManager m_emailTemplateManager = null;
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
/*  83 */       this.m_logger.error("SaveEmailTemplateAction.execute : User must be admin.");
/*  84 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  87 */     long lTypeId = getLongParameter(a_request, "typeId");
/*     */ 
/*  89 */     EmailTemplate emailTemplate = form.getEmailTemplate();
/*     */ 
/*  91 */     validateMandatoryFields(form, a_request);
/*  92 */     form.validate(a_request);
/*     */ 
/*  94 */     if (form.getHasErrors())
/*     */     {
/*  96 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 100 */       this.m_emailTemplateManager.saveEmailItem(a_dbTransaction, emailTemplate, lTypeId);
/* 101 */       afForward = createRedirectingForward("typeId=" + lTypeId, a_mapping, "Success");
/*     */     }
/*     */ 
/* 104 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateManager(EmailTemplateManager a_emailTemplateManager)
/*     */   {
/* 110 */     this.m_emailTemplateManager = a_emailTemplateManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.SaveEmailTemplateAction
 * JD-Core Version:    0.6.0
 */