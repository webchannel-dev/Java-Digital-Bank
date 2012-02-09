/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.customfield.util.CustomFieldValidationUtil;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.exception.UsernameInUseException;
/*     */ import com.bright.framework.user.util.PasswordUtil;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import com.bright.framework.util.DateUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddUserAction extends UserAction
/*     */   implements FrameworkConstants, UserConstants, MessageConstants, AssetBankConstants
/*     */ {
/*  73 */   protected EmailManager m_emailManager = null;
/*  74 */   protected CustomFieldManager m_fieldManager = null;
/*     */ 
/* 232 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  95 */     ActionForward afForward = null;
/*  96 */     UserForm form = (UserForm)a_form;
/*  97 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 100 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/* 102 */       this.m_logger.error("AddUserAction.execute : User does not have admin permission : " + userProfile);
/* 103 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 110 */     Vector vecGroupIds = getGroupIds(a_request);
/* 111 */     form.setGroupIds(vecGroupIds);
/*     */ 
/* 114 */     validateMandatoryFields(form, a_request);
/* 115 */     validateFieldLengths(form, a_request);
/*     */ 
/* 117 */     boolean bValidatePwd = (!AssetBankSettings.getForceRemoteAuthentication()) && (!AssetBankSettings.getSpecifyRemoteUsername());
/*     */ 
/* 119 */     form.validate(a_request, bValidatePwd, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/* 122 */     CustomFieldValidationUtil.validateFields(a_request, this.m_fieldManager, a_dbTransaction, form, 1L, form.getUser().getId(), -1L);
/*     */ 
/* 125 */     if ((!form.getNotifyUser()) && (StringUtils.isEmpty(form.getUser().getPassword())) && (StringUtils.isEmpty(form.getConfirmPassword())))
/*     */     {
/* 127 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationCannotGeneratePassword", userProfile.getCurrentLanguage()).getBody());
/* 128 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 132 */     if ((userProfile.getIsOrgUnitAdmin()) && (!existsGroupOtherThanDefault(vecGroupIds)))
/*     */     {
/* 134 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationMustAddGroup", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 137 */     if (form.getHasErrors())
/*     */     {
/* 139 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 144 */       ABUser managedUser = form.getUser();
/*     */ 
/* 147 */       managedUser.setLastModifiedBy(userProfile.getUser().getId());
/*     */ 
/* 150 */       if ((managedUser.getPassword() == null) || (managedUser.getPassword().length() < 1))
/*     */       {
/* 152 */         managedUser.setPassword(PasswordUtil.generateRandomPassword());
/* 153 */         form.setNotifyUser(true);
/*     */       }
/*     */ 
/* 157 */       if ((AssetBankSettings.getSpecifyRemoteUsername()) && (StringUtils.isNotEmpty(managedUser.getRemoteUsername())))
/*     */       {
/* 159 */         managedUser.setRemoteUser(true);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 164 */         getUserManager().saveUser(a_dbTransaction, managedUser);
/*     */       }
/*     */       catch (UsernameInUseException uiue)
/*     */       {
/* 168 */         this.m_logger.error("AddUserAction.execute : Unable to add user, username already in use");
/* 169 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationUsernameExists", userProfile.getCurrentLanguage()).getBody());
/* 170 */         CustomFieldValidationUtil.setValuesForRedirect(a_request, this.m_fieldManager, a_dbTransaction, 1L, form.getUser().getId());
/* 171 */         return a_mapping.findForward("ValidationFailure");
/*     */       }
/*     */ 
/* 174 */       getUserManager().addUserToGroups(a_dbTransaction, managedUser.getId(), vecGroupIds);
/*     */ 
/* 179 */       CustomFieldUtil.getAndSaveFieldValues(a_request, this.m_fieldManager, a_dbTransaction, 1L, managedUser.getId());
/*     */ 
/* 181 */       if (form.getNotifyUser())
/*     */       {
/* 184 */         HashMap hmParams = new HashMap();
/*     */ 
/* 186 */         String sName = managedUser.getFullName();
/* 187 */         if (!StringUtil.stringIsPopulated(sName))
/*     */         {
/* 189 */           sName = managedUser.getUsername();
/*     */         }
/*     */ 
/* 192 */         if (form.getUser().getExpiryDate() != null)
/*     */         {
/* 194 */           String expiryDateString = DateUtil.getStandardDateFormat().format(form.getUser().getExpiryDate());
/* 195 */           String[] userExpirySnippetReplacements = { expiryDateString };
/* 196 */           String userExpirySnippet = this.m_listManager.getListItem(a_dbTransaction, "snippet-user-expiry-date", userProfile.getCurrentLanguage(), userExpirySnippetReplacements);
/*     */ 
/* 200 */           hmParams.put("userExpiryMessage", userExpirySnippet);
/*     */         }
/*     */ 
/* 203 */         hmParams.put("template", "user_added_by_admin");
/* 204 */         hmParams.put("email", managedUser.getEmailAddress());
/* 205 */         hmParams.put("name", managedUser.getFullName());
/* 206 */         hmParams.put("username", managedUser.getUsername());
/* 207 */         hmParams.put("password", managedUser.getPassword());
/*     */         try
/*     */         {
/* 212 */           this.m_emailManager.sendTemplatedEmail(hmParams, managedUser.getLanguage());
/*     */         }
/*     */         catch (Bn2Exception be)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 221 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 224 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_sEmailManager)
/*     */   {
/* 229 */     this.m_emailManager = a_sEmailManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 235 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/* 240 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.AddUserAction
 * JD-Core Version:    0.6.0
 */