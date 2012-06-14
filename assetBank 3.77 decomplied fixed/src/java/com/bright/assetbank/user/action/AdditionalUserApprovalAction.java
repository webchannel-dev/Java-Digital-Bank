/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AdditionalUserApprovalAction extends RegisterUserAction
/*     */   implements AssetBankConstants
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     ActionForward afForward = null;
/*  59 */     UserForm form = (UserForm)a_form;
/*     */ 
/*  62 */     if (!AssetBankSettings.getAdditionalUserApprovalEnabled())
/*     */     {
/*  64 */       afForward = a_mapping.findForward("SystemFailure");
/*     */     }
/*     */     else
/*     */     {
/*  69 */       int iApprove = getIntParameter(a_request, "approve");
/*     */ 
/*  72 */       long lUserIdToApprove = getIntParameter(a_request, "id");
/*     */ 
/*  75 */       if (iApprove > 0)
/*     */       {
/*  77 */         form.setApproved(true);
/*     */ 
/*  79 */         ABUser existingUser = (ABUser)getUserManager().getUser(a_dbTransaction, lUserIdToApprove);
/*  80 */         existingUser.setHidden(false);
/*  81 */         getUserManager().saveUser(a_dbTransaction, existingUser);
/*     */ 
/*  87 */         commitTransaction(a_dbTransaction);
/*     */ 
/*  89 */         a_dbTransaction = getNewTransaction();
/*  90 */         HashMap hmParams = new HashMap();
/*     */ 
/*  92 */         hmParams.put("template", "user_registered");
/*  93 */         hmParams.put("email", existingUser.getEmailAddress());
/*  94 */         hmParams.put("name", existingUser.getFullName());
/*  95 */         hmParams.put("orgunitname", "");
/*     */         try
/*     */         {
/*  99 */           String sEmailAddresses = getUserManager().getAdminEmailAddresses();
/*     */ 
/* 101 */           if (StringUtil.stringIsPopulated(sEmailAddresses))
/*     */           {
/* 103 */             hmParams.put("adminEmailAddresses", sEmailAddresses);
/*     */           }
/*     */ 
/* 106 */           this.m_emailManager.sendTemplatedEmail(hmParams, LanguageConstants.k_defaultLanguage);
/*     */         }
/*     */         catch (Bn2Exception be)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 119 */         ABUser existingUser = (ABUser)getUserManager().getUser(a_dbTransaction, lUserIdToApprove);
/*     */ 
/* 121 */         if (existingUser.getHidden())
/*     */         {
/* 123 */           getUserManager().deleteUser(a_dbTransaction, lUserIdToApprove);
/*     */ 
/* 126 */           HashMap hmParams = new HashMap();
/*     */ 
/* 128 */           hmParams.put("template", "user_rejected");
/* 129 */           hmParams.put("email", existingUser.getEmailAddress());
/* 130 */           hmParams.put("name", existingUser.getFullName());
/* 131 */           hmParams.put("adminMessage", "");
/*     */           try
/*     */           {
/* 135 */             this.m_emailManager.sendTemplatedEmail(hmParams, LanguageConstants.k_defaultLanguage);
/*     */           }
/*     */           catch (Bn2Exception be)
/*     */           {
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 146 */           return a_mapping.findForward("SystemFailure");
/*     */         }
/*     */       }
/*     */ 
/* 150 */       commitTransaction(a_dbTransaction);
/*     */ 
/* 152 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 156 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.AdditionalUserApprovalAction
 * JD-Core Version:    0.6.0
 */