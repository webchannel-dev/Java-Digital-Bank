/*     */ package com.bright.framework.mail.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.bean.EmailTemplate;
/*     */ import com.bright.framework.mail.form.EmailTemplateForm;
/*     */ import com.bright.framework.mail.service.EmailTemplateManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveFromEmailAddressAction extends BTransactionAction
/*     */ {
/*  46 */   private EmailTemplateManager m_emailTemplateManager = null;
/*  47 */   protected ListManager m_listManager = null;
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
/*  83 */       this.m_logger.error("EditEmailTemplateAction.execute : User must be an admin.");
/*  84 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  88 */     if (!StringUtil.isValidEmailAddress(form.getEmailTemplate().getAddrFrom()))
/*     */     {
/*  90 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationEmailAddress", userProfile.getCurrentLanguage()).getBody());
/*  91 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  94 */     if (!form.getHasErrors())
/*     */     {
/*  97 */       this.m_emailTemplateManager.saveFromEmailAddress(a_dbTransaction, form.getEmailTemplate());
/*     */     }
/*     */ 
/* 100 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 102 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateManager(EmailTemplateManager a_emailTemplateManager)
/*     */   {
/* 107 */     this.m_emailTemplateManager = a_emailTemplateManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 112 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.SaveFromEmailAddressAction
 * JD-Core Version:    0.6.0
 */