/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.ChangePasswordForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.exception.PasswordReminderException;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ChangePasswordAction extends UserAction
/*     */   implements ImageConstants, MessageConstants, AssetBankConstants
/*     */ {
/*  60 */   private EmailManager m_emailManager = null;
/*     */ 
/*  66 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  71 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/*  63 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  68 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  74 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  96 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  98 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 100 */       this.m_logger.debug("This user does not have permission to view the admin page");
/* 101 */       return a_mapping.findForward("NoPermission");
/*     */     }
/* 103 */     ABUser adminUser = (ABUser)userProfile.getUser();
/*     */ 
/* 105 */     ActionForward afForward = null;
/* 106 */     ChangePasswordForm passwordForm = (ChangePasswordForm)a_form;
/*     */ 
/* 109 */     if ((passwordForm.getNewPassword() == null) || (passwordForm.getNewPassword().trim().length() == 0))
/*     */     {
/* 111 */       passwordForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationPasswords", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 114 */     if (!passwordForm.getHasErrors())
/*     */     {
/* 117 */       if (passwordForm.getUser().getId() > 0L)
/*     */       {
/* 119 */         Vector vecOUs = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, passwordForm.getUser().getId());
/*     */ 
/* 121 */         if ((!userProfile.getIsAdmin()) && ((!userProfile.getIsOrgUnitAdmin()) || (!adminUser.getIsAdminOfAtLeastOneOrgUnit(vecOUs))))
/*     */         {
/* 123 */           this.m_logger.debug("User does not have permission to change password for user.");
/* 124 */           return a_mapping.findForward("NoPermission");
/*     */         }
/*     */ 
/* 128 */         if (passwordForm.getNotifyUser())
/*     */         {
/*     */           try
/*     */           {
/* 132 */             this.m_emailManager.sendTemplatedEmailFromRequest(a_request);
/*     */           }
/*     */           catch (Bn2Exception bn2e)
/*     */           {
/* 136 */             this.m_logger.error("ChangePasswordAction.execute : Unable to update password and notify");
/* 137 */             passwordForm.addError(this.m_listManager.getListItem(a_dbTransaction, "userErrorPasswordNotificationFailed", userProfile.getCurrentLanguage()).getBody());
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 143 */         if (!passwordForm.getHasErrors())
/*     */         {
/* 145 */           getUserManager().setUserPassword(passwordForm.getUser().getId(), passwordForm.getNewPassword().trim());
/*     */         }
/*     */ 
/*     */       }
/* 152 */       else if ((passwordForm.getOldPassword() == null) || (passwordForm.getOldPassword().trim().length() == 0))
/*     */       {
/* 154 */         passwordForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationExistingPassword", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/* 156 */       else if (!passwordForm.getNewPassword().trim().equals(passwordForm.getConfirmNewPassword().trim()))
/*     */       {
/* 158 */         passwordForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationPasswordsDontMatch", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/* 162 */       else if ((AssetBankSettings.isStrongValidationEnabled()) && (!StringUtil.isValidPassword(passwordForm.getNewPassword())))
/*     */       {
/* 164 */         passwordForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationSecurePassword", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/* 169 */       else if (!getUserManager().changePassword(a_dbTransaction, userProfile.getUser().getId(), passwordForm.getOldPassword().trim(), passwordForm.getNewPassword().trim(), true))
/*     */       {
/* 175 */         passwordForm.addError(this.m_listManager.getListItem(a_dbTransaction, "userErrorInvalidOldPassword", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */       else
/*     */       {
/* 180 */         userProfile.setIsPasswordExpired(false);
/* 181 */         userProfile.getUser().setPasswordExpired(false);
/*     */ 
/* 184 */         if ((passwordForm.getOldPassword() == null) || (passwordForm.getOldPassword().trim().length() == 0))
/*     */         {
/* 186 */           ABUser user = (ABUser)userProfile.getUser();
/* 187 */           String sName = user.getFullName();
/* 188 */           if (!StringUtil.stringIsPopulated(sName))
/*     */           {
/* 190 */             sName = user.getUsername();
/*     */           }
/*     */ 
/* 193 */           HashMap params = new HashMap();
/* 194 */           params.put("template", "password_change");
/* 195 */           params.put("name", sName);
/* 196 */           params.put("username", user.getUsername());
/* 197 */           params.put("email", user.getEmailAddress());
/* 198 */           params.put("newPassword", passwordForm.getNewPassword().trim());
/*     */           try
/*     */           {
/* 202 */             this.m_emailManager.sendTemplatedEmail(params, user.getLanguage());
/*     */           }
/*     */           catch (Bn2Exception e)
/*     */           {
/* 207 */             this.m_logger.debug("The password change email was not successfully sent to the user: " + user.getEmailAddress() + ": " + e.getMessage());
/* 208 */             throw new PasswordReminderException("Email could not be sent to user: " + user.getEmailAddress() + ": " + e.getMessage());
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 217 */     if (passwordForm.getHasErrors())
/*     */     {
/* 219 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 224 */       String sQueryString = StringUtil.makeQueryString(a_request.getParameterMap());
/* 225 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */ 
/* 228 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ChangePasswordAction
 * JD-Core Version:    0.6.0
 */