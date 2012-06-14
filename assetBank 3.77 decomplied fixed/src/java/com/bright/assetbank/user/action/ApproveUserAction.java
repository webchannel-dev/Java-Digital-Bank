/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
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
/*     */ public class ApproveUserAction extends UpdateUserAction
/*     */   implements FrameworkConstants, AssetBankConstants
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     ActionForward afForward = null;
/*  88 */     UserForm form = (UserForm)a_form;
/*  89 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  92 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin())))
/*     */     {
/*  94 */       this.m_logger.error("ApproveUserAction.execute : User does not have admin permission : " + userProfile);
/*  95 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  98 */     ABUser user = form.getUser();
/*  99 */     String sPassword = "";
/*     */ 
/* 102 */     Vector vecGroupIds = getGroupIds(a_request);
/* 103 */     form.setGroupIds(vecGroupIds);
/*     */ 
/* 107 */     if ((form.getApproved()) && (!form.getNotifyUser()) && (StringUtils.isEmpty(form.getUser().getPassword())))
/*     */     {
/* 109 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationCannotGeneratePassword", userProfile.getCurrentLanguage()).getBody());
/* 110 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 114 */     if (form.getApproved())
/*     */     {
/* 117 */       user.setNotApproved(false);
/*     */ 
/* 120 */       afForward = super.execute(a_mapping, form, a_request, a_response, a_dbTransaction);
/*     */ 
/* 127 */       if (!form.getHasErrors())
/*     */       {
/* 130 */         if (!user.isRemoteUser())
/*     */         {
/* 133 */           if ((user.getPassword() != null) && (user.getPassword().length() > 0))
/*     */           {
/* 135 */             sPassword = user.getPassword();
/*     */           }
/*     */           else
/*     */           {
/* 140 */             sPassword = PasswordUtil.generateRandomPassword();
/*     */           }
/*     */ 
/* 144 */           getUserManager().changePassword(a_dbTransaction, user.getId(), sPassword);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 153 */       getUserManager().deleteUser(a_dbTransaction, form.getUser().getId());
/*     */ 
/* 155 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 159 */     if ((!form.getHasErrors()) && (form.getNotifyUser()))
/*     */     {
/* 161 */       sendNotificationEmail(form, user, sPassword, a_dbTransaction, userProfile);
/*     */     }
/*     */ 
/* 164 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void sendNotificationEmail(UserForm form, ABUser user, String sPassword, DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 183 */     HashMap hmParams = new HashMap();
/*     */ 
/* 186 */     String sName = user.getFullName();
/* 187 */     if (!StringUtil.stringIsPopulated(sName))
/*     */     {
/* 189 */       sName = user.getUsername();
/*     */     }
/*     */ 
/* 192 */     hmParams.put("email", user.getEmailAddress());
/* 193 */     hmParams.put("name", sName);
/*     */ 
/* 196 */     if ((form.getMessage() != null) && (form.getMessage().length() > 0))
/*     */     {
/* 198 */       hmParams.put("adminMessage", form.getMessage());
/*     */     }
/*     */     else
/*     */     {
/* 202 */       hmParams.put("adminMessage", " ");
/*     */     }
/*     */ 
/* 205 */     if (form.getUser().getExpiryDate() != null)
/*     */     {
/* 207 */       String expiryDateString = DateUtil.getStandardDateFormat().format(form.getUser().getExpiryDate());
/* 208 */       String[] userExpirySnippetReplacements = { expiryDateString };
/* 209 */       String userExpirySnippet = this.m_listManager.getListItem(a_dbTransaction, "snippet-user-expiry-date", a_userProfile.getCurrentLanguage(), userExpirySnippetReplacements);
/*     */ 
/* 213 */       hmParams.put("userExpiryMessage", userExpirySnippet);
/*     */     }
/*     */ 
/* 216 */     if (form.getApproved())
/*     */     {
/* 219 */       if (!user.isRemoteUser())
/*     */       {
/* 221 */         hmParams.put("template", "user_approved");
/*     */       }
/*     */       else
/*     */       {
/* 226 */         hmParams.put("template", "ad_user_approved");
/*     */       }
/*     */ 
/* 230 */       hmParams.put("username", user.getUsername());
/* 231 */       hmParams.put("password", sPassword);
/*     */     }
/*     */     else
/*     */     {
/* 235 */       hmParams.put("template", "user_rejected");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 240 */       this.m_emailManager.sendTemplatedEmail(hmParams, user.getLanguage());
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void sendChangeConfirmationEmail(UserForm form, ABUser managedUser)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ApproveUserAction
 * JD-Core Version:    0.6.0
 */