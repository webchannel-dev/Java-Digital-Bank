/*     */ package com.bright.framework.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.form.ChangePasswordForm;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ChangePasswordAction extends BTransactionAction
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  47 */   private UserManager m_userManager = null;
/*     */ 
/*  49 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     ActionForward afForward = null;
/*  75 */     ChangePasswordForm passwordForm = (ChangePasswordForm)a_form;
/*  76 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  78 */     boolean bError = false;
/*     */ 
/*  81 */     String sCancel = a_request.getParameter("Cancel");
/*     */ 
/*  83 */     if ((sCancel != null) && (sCancel.length() > 0))
/*     */     {
/*  86 */       return a_mapping.findForward("Cancel");
/*     */     }
/*     */ 
/*  89 */     long lUserId = 0L;
/*     */ 
/*  91 */     if (passwordForm.getUserId() > 0L)
/*     */     {
/*  93 */       lUserId = passwordForm.getUserId();
/*     */     }
/*     */     else
/*     */     {
/*  97 */       lUserId = userProfile.getUser().getId();
/*     */     }
/*     */ 
/* 101 */     if ((passwordForm.getOldPassword() == null) || (passwordForm.getOldPassword().trim().length() == 0) || (passwordForm.getNewPassword() == null) || (passwordForm.getNewPassword().trim().length() == 0) || (passwordForm.getConfirmNewPassword() == null) || (passwordForm.getConfirmNewPassword().trim().length() == 0))
/*     */     {
/* 103 */       passwordForm.addError(this.m_listManager.getListItem(a_transaction, "failedValidationPasswords", userProfile.getCurrentLanguage()).getBody());
/* 104 */       bError = true;
/*     */     }
/* 106 */     else if (!passwordForm.getNewPassword().trim().equals(passwordForm.getConfirmNewPassword().trim()))
/*     */     {
/* 108 */       passwordForm.addError(this.m_listManager.getListItem(a_transaction, "failedValidationPasswordsDontMatch", userProfile.getCurrentLanguage()).getBody());
/* 109 */       bError = true;
/*     */     }
/*     */ 
/* 112 */     if (!bError)
/*     */     {
/*     */       try
/*     */       {
/* 117 */         bError = !this.m_userManager.changePassword(a_transaction, lUserId, passwordForm.getOldPassword().trim(), passwordForm.getNewPassword().trim(), true);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 125 */         passwordForm.addError(this.m_listManager.getListItem(a_transaction, "userErrorInvalidOldPassword", userProfile.getCurrentLanguage()).getBody());
/* 126 */         bError = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 131 */     if (bError)
/*     */     {
/* 133 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 137 */       afForward = a_mapping.findForward("Success");
/*     */     }
/* 139 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setUserManager(UserManager a_userManager)
/*     */   {
/* 150 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 156 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.ChangePasswordAction
 * JD-Core Version:    0.6.0
 */