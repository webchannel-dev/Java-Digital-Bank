/*     */ package com.bright.framework.mail.action;
/*     */ 
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.constant.MailConstants;
/*     */ import com.bright.framework.mail.form.BasicEmailForm;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SendTemplatedEmailAction extends BTransactionAction
/*     */   implements CommonConstants, MailConstants, AssetBankConstants
/*     */ {
/*  63 */   private EmailManager m_emailManager = null;
/*  64 */   private ABUserManager m_userManager = null;
/*  65 */   private CustomFieldManager m_fieldManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  92 */     ActionForward sForward = null;
/*     */ 
/*  94 */     BasicEmailForm form = (BasicEmailForm)a_form;
/*     */ 
/*  96 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  98 */     ABUser user = (ABUser)this.m_userManager.getUser(a_dbTransaction, userProfile.getUser().getId());
/*     */ 
/* 101 */     Enumeration e = a_request.getParameterNames();
/* 102 */     HashMap params = new HashMap();
/*     */ 
/* 104 */     while (e.hasMoreElements())
/*     */     {
/* 107 */       String sParamName = (String)e.nextElement();
/* 108 */       String sParam = a_request.getParameter(sParamName);
/*     */ 
/* 110 */       if ((sParam != null) && (sParam.length() > 4096))
/*     */       {
/* 112 */         sParam = sParam.substring(0, 4096);
/*     */       }
/*     */ 
/* 115 */       params.put(sParamName, sParam);
/*     */     }
/*     */ 
/* 119 */     params.putAll(CustomFieldUtil.getEmailTemplateParams(a_request, this.m_fieldManager, a_dbTransaction, 1L, user.getId()));
/*     */ 
/* 122 */     if (a_form != null)
/*     */     {
/* 124 */       validateMandatoryFields(form, a_request);
/*     */ 
/* 126 */       if (form.getHasErrors())
/*     */       {
/* 129 */         form.setParams(params);
/* 130 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 135 */     this.m_logger.debug("In SendTemplatedEmailAction.execute");
/*     */     try
/*     */     {
/* 140 */       this.m_emailManager.sendTemplatedEmail(params, LanguageConstants.k_defaultLanguage);
/*     */ 
/* 143 */       String sSuccess = (String)params.get("REDIRECT_SUCCESS");
/*     */ 
/* 146 */       if (sSuccess != null)
/*     */       {
/* 148 */         sForward = new ActionForward(sSuccess);
/*     */       }
/*     */       else
/*     */       {
/* 152 */         sForward = a_mapping.findForward("Success");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Bn2Exception bne)
/*     */     {
/* 158 */       String sFailure = (String)params.get("REDIRECT_FAILURE");
/*     */ 
/* 161 */       if (sFailure != null)
/*     */       {
/* 163 */         sForward = new ActionForward(sFailure);
/*     */       }
/*     */       else
/*     */       {
/* 167 */         sForward = a_mapping.findForward("Failure");
/*     */       }
/*     */     }
/*     */ 
/* 171 */     return sForward;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/* 187 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 192 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/* 197 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.SendTemplatedEmailAction
 * JD-Core Version:    0.6.0
 */