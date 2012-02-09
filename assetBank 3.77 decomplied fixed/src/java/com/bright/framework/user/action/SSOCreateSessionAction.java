/*     */ package com.bright.framework.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.activedirectory.constant.ActiveDirectorySettings;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import com.bright.framework.user.sso.SSOPlugin;
/*     */ import com.bright.framework.user.sso.SSOPluginFactory;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SSOCreateSessionAction extends LoginAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  63 */     LoginForm form = (LoginForm)a_form;
/*  64 */     ActionForward afForward = null;
/*     */ 
/*  66 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  69 */     String sSSOPluginClassname = UserSettings.getSSOPluginClass();
/*  70 */     SSOPlugin ssoPlugin = SSOPluginFactory.getSSOPluginInstance(sSSOPluginClassname);
/*     */     User remoteUser;
/*     */     //User remoteUser;
/*  77 */     if (form.getUser() == null)
/*     */     {
/*  80 */       remoteUser = ssoPlugin.getRemoteUser(a_request);
/*     */     }
/*     */     else
/*     */     {
/*  84 */       remoteUser = form.getUser();
/*     */     }
/*     */ 
/*  87 */     if (remoteUser != null)
/*     */     {
/*  90 */       form.setForwardUrl(ssoPlugin.getForwardUrl(a_request));
/*     */ 
/*  93 */       long lId = lookupRemoteUser(remoteUser, a_dbTransaction, this.m_userManager);
/*     */ 
/*  96 */       boolean bImportSSOUsers = (FrameworkSettings.getImportRemoteUsersOnTheFly()) && (ActiveDirectorySettings.getSuspendADAuthentication());
/*     */ 
/*  99 */       User localUser = getLocalUser(lId, remoteUser, a_dbTransaction, this.m_userManager, bImportSSOUsers, userProfile.getCurrentLanguage().getId());
/*     */ 
/* 101 */       if (localUser == null)
/*     */       {
/* 104 */         form.addError("You haven't been set up as a user for this application - please contact an administrator.");
/* 105 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 109 */       String sLocalUsername = localUser.getUsername();
/*     */ 
/* 111 */       afForward = doLogin(a_dbTransaction, a_mapping, form, a_request, a_response, sLocalUsername, "", true);
/*     */     }
/*     */     else
/*     */     {
/* 122 */       form.setLoginFailed(true);
/* 123 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/* 125 */     return afForward;
/*     */   }
/*     */ 
/*     */   public boolean doPreprocessing()
/*     */   {
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */   public static long lookupRemoteUser(User a_remoteUser, DBTransaction a_dbTransaction, UserManager a_userManager)
/*     */     throws Bn2Exception
/*     */   {
/* 148 */     long lId = 0L;
/*     */ 
/* 151 */     String sRemoteId = a_remoteUser.getRemoteUsername();
/*     */ 
/* 154 */     lId = a_userManager.getUserIdForRemoteUsername(a_dbTransaction, sRemoteId);
/*     */ 
/* 157 */     if ((lId <= 0L) && (FrameworkSettings.getImportRemoteUsersOnTheFly()))
/*     */     {
/* 159 */       lId = a_userManager.getUserIdForLocalUsername(a_dbTransaction, sRemoteId);
/*     */     }
/*     */ 
/* 162 */     return lId;
/*     */   }
/*     */ 
/*     */   public static User getLocalUser(long lId, User remoteUser, DBTransaction a_dbTransaction, UserManager a_userManager, boolean bImportSSOUsers, long a_lLanguageId)
/*     */     throws Bn2Exception
/*     */   {
/* 181 */     User localUser = null;
/*     */ 
/* 183 */     if (lId <= 0L)
/*     */     {
/* 185 */       if (!bImportSSOUsers)
/*     */       {
/* 187 */         return null;
/*     */       }
/*     */ 
/* 191 */       GlobalApplication.getInstance().getLogger().debug("SSOCreateSessionAction.createOrUpdateUser: Creating new remote user");
/*     */ 
/* 193 */       localUser = a_userManager.createUser();
/* 194 */       localUser.setRemoteUser(true);
/* 195 */       localUser.setUsername(remoteUser.getUsername());
/* 196 */       localUser.setRemoteUsername(remoteUser.getRemoteUsername());
/* 197 */       localUser.setDisplayName(remoteUser.getDisplayName());
/* 198 */       localUser.setForename(remoteUser.getForename());
/* 199 */       localUser.setSurname(remoteUser.getSurname());
/* 200 */       localUser.setEmailAddress(remoteUser.getEmailAddress());
/*     */ 
/* 203 */       localUser.setLanguage(new Language(a_lLanguageId));
/*     */ 
/* 205 */       localUser = a_userManager.saveUser(a_dbTransaction, localUser);
/*     */ 
/* 208 */       if (remoteUser.getGroups() != null)
/*     */       {
/* 210 */         a_userManager.addUserToGroups(a_dbTransaction, localUser.getId(), remoteUser.getGroups());
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 216 */       localUser = a_userManager.getUser(a_dbTransaction, lId);
/*     */ 
/* 219 */       if ((bImportSSOUsers) && (FrameworkSettings.getUpdateUserOnSSOLogin()))
/*     */       {
/* 221 */         boolean bChanged = false;
/* 222 */         if ((!localUser.getUsername().equals(remoteUser.getUsername())) && (StringUtil.stringIsPopulated(remoteUser.getUsername())))
/*     */         {
/* 224 */           localUser.setUsername(remoteUser.getUsername());
/* 225 */           bChanged = true;
/*     */         }
/* 227 */         if ((localUser.getEmailAddress() == null) || ((!localUser.getEmailAddress().equals(remoteUser.getEmailAddress())) && (StringUtil.stringIsPopulated(remoteUser.getEmailAddress()))))
/*     */         {
/* 229 */           localUser.setEmailAddress(remoteUser.getEmailAddress());
/* 230 */           bChanged = true;
/*     */         }
/* 232 */         if ((localUser.getForename() == null) || ((!localUser.getForename().equals(remoteUser.getForename())) && (StringUtil.stringIsPopulated(remoteUser.getForename()))))
/*     */         {
/* 234 */           localUser.setForename(remoteUser.getForename());
/* 235 */           bChanged = true;
/*     */         }
/* 237 */         if ((localUser.getSurname() == null) || ((!localUser.getSurname().equals(remoteUser.getSurname())) && (StringUtil.stringIsPopulated(remoteUser.getSurname()))))
/*     */         {
/* 239 */           localUser.setSurname(remoteUser.getSurname());
/* 240 */           bChanged = true;
/*     */         }
/* 242 */         if ((localUser.getDisplayName() != null) && (StringUtil.stringIsPopulated(remoteUser.getDisplayName())) && (!localUser.getDisplayName().equals(remoteUser.getDisplayName())))
/*     */         {
/* 244 */           localUser.setDisplayName(remoteUser.getDisplayName());
/* 245 */           bChanged = true;
/*     */         }
/* 247 */         if (bChanged)
/*     */         {
/* 249 */           localUser = a_userManager.saveUser(a_dbTransaction, localUser);
/*     */         }
/*     */ 
/* 253 */         if (remoteUser.getGroups() != null)
/*     */         {
/* 256 */           Vector vecGroupsToAdd = remoteUser.getGroups();
/*     */ 
/* 259 */           vecGroupsToAdd.add(Long.valueOf(1L));
/*     */ 
/* 262 */           if (localUser.getGroups() != null)
/*     */           {
/* 264 */             vecGroupsToAdd.removeAll(localUser.getGroups());
/*     */           }
/*     */ 
/* 268 */           if (vecGroupsToAdd != null)
/*     */           {
/* 271 */             a_userManager.addUserToGroups(a_dbTransaction, localUser.getId(), remoteUser.getGroups());
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 279 */     return localUser;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.SSOCreateSessionAction
 * JD-Core Version:    0.6.0
 */