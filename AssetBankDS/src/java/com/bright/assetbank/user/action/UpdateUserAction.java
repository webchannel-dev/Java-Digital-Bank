/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.customfield.util.CustomFieldValidationUtil;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnitMetadata;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class UpdateUserAction extends UserAction
/*     */   implements FrameworkConstants, MessageConstants, AssetBankConstants
/*     */ {
/*  81 */   protected ListManager m_listManager = null;
/*  82 */   protected EmailManager m_emailManager = null;
/*  83 */   protected CustomFieldManager m_fieldManager = null;
/*  84 */   protected OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 106 */     ActionForward afForward = null;
/* 107 */     UserForm form = (UserForm)a_form;
/* 108 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 111 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/* 113 */       this.m_logger.error("UpdateUserAction.execute : User does not have admin permission : " + userProfile);
/* 114 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 118 */     if (form.getUser().getIsDeleted())
/*     */     {
/* 120 */       if (!getUserManager().deleteUserOrRemoveFromOU(a_dbTransaction, form.getUser().getId(), userProfile))
/*     */       {
/* 122 */         this.m_logger.error("DeleteUserAction.execute : User not admin for user: id=" + form.getUser().getId());
/* 123 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/* 126 */       if (form.getNotifyUser())
/*     */       {
/* 128 */         sendChangeConfirmationEmail(form, form.getUser(), "user_deleted_admin", (ABUser)userProfile.getUser());
/*     */       }
/*     */ 
/* 131 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 135 */     Vector vecGroupIds = getGroupIds(a_request);
/* 136 */     form.setGroupIds(vecGroupIds);
/*     */ 
/* 139 */     validateMandatoryFields(form, a_request);
/* 140 */     validateFieldLengths(form, a_request);
/* 141 */     form.validate(a_request, false, userProfile, a_dbTransaction, this.m_listManager);
/* 142 */     Vector vecOrgUnitGroups = null;
/*     */ 
/* 144 */     if (userProfile.getIsOrgUnitAdmin())
/*     */     {
/* 147 */       vecOrgUnitGroups = new Vector();
/* 148 */       for (int i = 0; i < ((ABUser)userProfile.getUser()).getOrgUnits().size(); i++)
/*     */       {
/* 150 */         long lOrgUnitId = ((OrgUnitMetadata)((ABUser)userProfile.getUser()).getOrgUnits().elementAt(i)).getId();
/* 151 */         vecOrgUnitGroups.addAll(this.m_orgUnitManager.getOrgUnitGroups(a_dbTransaction, lOrgUnitId));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 156 */     if ((!userProfile.getIsAdmin()) && (!AssetBankSettings.getOrgUnitAdminCanAccessAllUsers()) && (userProfile.getIsOrgUnitAdmin()) && (!existsGroupOtherThanDefault(vecGroupIds)))
/*     */     {
/* 158 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationMustAddGroup", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 162 */     long lPublicGroupId = getUserManager().getPublicGroup(a_dbTransaction).getId();
/*     */ 
/* 164 */     if (form.getUser().getIsAdmin())
/*     */     {
/* 166 */       for (int i = 0; i < vecGroupIds.size(); i++)
/*     */       {
/* 168 */         Long longGroupId = (Long)vecGroupIds.get(i);
/*     */ 
/* 170 */         if ((longGroupId.longValue() == lPublicGroupId) || (longGroupId.longValue() == 1L)) {
/*     */           continue;
/*     */         }
/* 173 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "adminUserCannotBeInGroups", userProfile.getCurrentLanguage()).getBody());
/* 174 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 182 */     CustomFieldValidationUtil.validateFields(a_request, this.m_fieldManager, a_dbTransaction, form, 1L, form.getUser().getId(), -1L);
/*     */ 
/* 184 */     if (form.getHasErrors())
/*     */     {
/* 186 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 191 */       ABUser managedUser = (ABUser)getUserManager().getUser(a_dbTransaction, form.getUser().getId());
/*     */ 
/* 194 */       if ((form.getOldUsername() != null) && (!form.getOldUsername().equals(form.getUser().getUsername())))
/*     */       {
/* 197 */         if (getUserManager().isUsernameInUse(a_dbTransaction, form.getUser().getUsername()))
/*     */         {
/* 199 */           this.m_logger.error("UpdateUserAction.execute : Unable to add user, username already in use");
/* 200 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationUsernameExists", userProfile.getCurrentLanguage()).getBody());
/* 201 */           a_request.setAttribute("id", new Long(form.getUser().getId()));
/* 202 */           CustomFieldValidationUtil.setValuesForRedirect(a_request, this.m_fieldManager, a_dbTransaction, 1L, form.getUser().getId());
/* 203 */           return a_mapping.findForward("Failure");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 208 */       updateUserWithCommonFields(managedUser, form);
/*     */ 
/* 211 */       managedUser.setUsername(form.getUser().getUsername());
/* 212 */       managedUser.setNotApproved(form.getUser().getNotApproved());
/* 213 */       managedUser.setCanBeCategoryAdmin(form.getUser().getCanBeCategoryAdmin());
/* 214 */       managedUser.setIsAdmin(form.getUser().getIsAdmin());
/* 215 */       managedUser.setExpiryDate(form.getUser().getExpiryDate());
/* 216 */       managedUser.setRequiresUpdate(form.getUser().getRequiresUpdate());
/* 217 */       managedUser.setRequestedOrgUnitId(form.getUser().getRequestedOrgUnitId());
/* 218 */       managedUser.setReceiveAlerts(form.getUser().getReceiveAlerts());
/* 219 */       managedUser.setHidden(form.getUser().getHidden());
/*     */ 
/* 221 */       if (form.getSelectedLanguage() > 0L)
/*     */       {
/* 223 */         managedUser.setLanguage(new Language(form.getSelectedLanguage()));
/*     */       }
/*     */       else
/*     */       {
/* 227 */         managedUser.setLanguage(form.getUser().getLanguage());
/*     */       }
/*     */ 
/* 231 */       managedUser.setLastModifiedBy(userProfile.getUser().getId());
/*     */ 
/* 234 */       if (AssetBankSettings.getSpecifyRemoteUsername())
/*     */       {
/* 236 */         managedUser.setRemoteUsername(form.getUser().getRemoteUsername());
/* 237 */         managedUser.setRemoteUser(StringUtils.isNotEmpty(managedUser.getRemoteUsername()));
/*     */       }
/*     */ 
/* 241 */       form.setUser(managedUser);
/*     */ 
/* 244 */       if (!userProfile.getIsAdmin())
/*     */       {
/* 246 */         addInvisibleGroups(managedUser, vecGroupIds, vecOrgUnitGroups);
/*     */       }
/*     */ 
/* 250 */       getUserManager().saveUser(a_dbTransaction, managedUser);
/*     */ 
/* 253 */       getUserManager().addUserToGroups(a_dbTransaction, form.getUser().getId(), vecGroupIds);
/*     */ 
/* 258 */       CustomFieldUtil.getAndSaveFieldValues(a_request, this.m_fieldManager, a_dbTransaction, 1L, managedUser.getId());
/*     */ 
/* 261 */       if ((form.getNotifyUser()) && (!form.getApproved()))
/*     */       {
/* 263 */         sendChangeConfirmationEmail(form, managedUser, "user_updated_admin", (ABUser)userProfile.getUser());
/*     */       }
/*     */ 
/* 266 */       String sQueryString = StringUtil.makeQueryString(a_request.getParameterMap());
/* 267 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */ 
/* 270 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected void sendChangeConfirmationEmail(UserForm a_form, ABUser a_managedUser, String a_sTemplate, ABUser a_adminUser) throws Bn2Exception
/*     */   {
/* 275 */     UserForm form = a_form;
/* 276 */     ABUser managedUser = a_managedUser;
/*     */ 
/* 279 */     if ((!form.getHasErrors()) && (form.getNotifyUser()))
/*     */     {
/* 281 */       HashMap hmParams = new HashMap();
/* 282 */       hmParams.put("template", a_sTemplate);
/* 283 */       hmParams.put("recipients", managedUser.getEmailAddress());
/* 284 */       hmParams.put("greetname", managedUser.getForename());
/* 285 */       hmParams.put("username", managedUser.getUsername());
/*     */ 
/* 289 */       hmParams.put("password", managedUser.getPassword());
/*     */ 
/* 292 */       String sAdminName = a_adminUser.getFullName();
/* 293 */       if (!StringUtil.stringIsPopulated(sAdminName))
/*     */       {
/* 295 */         sAdminName = a_adminUser.getUsername();
/*     */       }
/* 297 */       hmParams.put("admin-name", sAdminName);
/*     */ 
/* 299 */       if ((form.getMessage() != null) && (form.getMessage().length() > 0))
/*     */       {
/* 301 */         hmParams.put("message", form.getMessage());
/*     */       }
/*     */       else
/*     */       {
/* 305 */         hmParams.put("message", " ");
/*     */       }
/*     */ 
/* 308 */       this.m_emailManager.sendTemplatedEmail(hmParams, managedUser.getLanguage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addInvisibleGroups(ABUser a_user, Vector a_vecGroupIds, Vector a_vecOrgUnitGroupIds)
/*     */   {
/* 322 */     Vector vecOldGroups = a_user.getGroups();
/*     */ 
/* 324 */     Iterator itOld = vecOldGroups.iterator();
/* 325 */     while (itOld.hasNext())
/*     */     {
/* 327 */       Group oldGroup = (Group)itOld.next();
/*     */ 
/* 330 */       boolean bInList = false;
/* 331 */       Iterator itNew = a_vecGroupIds.iterator();
/* 332 */       while (itNew.hasNext())
/*     */       {
/* 334 */         Long newGroupId = (Long)itNew.next();
/*     */ 
/* 336 */         if (oldGroup.getId() == newGroupId.longValue())
/*     */         {
/* 339 */           bInList = true;
/* 340 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 344 */       if (a_vecOrgUnitGroupIds != null)
/*     */       {
/* 346 */         for (int i = 0; i < a_vecOrgUnitGroupIds.size(); i++)
/*     */         {
/* 348 */           Group group = (Group)a_vecOrgUnitGroupIds.elementAt(i);
/* 349 */           if (group.getId() != oldGroup.getId())
/*     */             continue;
/* 351 */           bInList = true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 356 */       if (!bInList)
/*     */       {
/* 359 */         a_vecGroupIds.add(new Long(oldGroup.getId()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public EmailManager getEmailManager()
/*     */   {
/* 368 */     return this.m_emailManager;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_sEmailManager)
/*     */   {
/* 373 */     this.m_emailManager = a_sEmailManager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager) {
/* 377 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 382 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/* 387 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.UpdateUserAction
 * JD-Core Version:    0.6.0
 */