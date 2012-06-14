/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.customfield.util.CustomFieldValidationUtil;
/*     */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Brand;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.form.RegisterForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.common.bean.RefDataItem;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.util.PasswordUtil;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import nl.captcha.Captcha;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RegisterUserAction extends UserAction
/*     */   implements FrameworkConstants, UserConstants, MessageConstants, AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "RegisterUserAction";
/* 102 */   protected EmailManager m_emailManager = null;
/* 103 */   private MarketingGroupManager m_marketingGroupManager = null;
/* 104 */   private OrgUnitManager m_orgUnitManager = null;
/* 105 */   protected ListManager m_listManager = null;
/* 106 */   protected CustomFieldManager m_fieldManager = null;
/* 107 */   private HashMap<String, String> m_hmUserCustomFields = null;
/* 108 */   protected AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 130 */     ActionForward afForward = null;
/* 131 */     RegisterForm form = (RegisterForm)a_form;
/*     */ 
/* 133 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 135 */     if (StringUtils.isNotEmpty(a_request.getParameter("reloadPage")))
/*     */     {
/* 137 */       return a_mapping.findForward("Reload");
/*     */     }
/*     */ 
/* 141 */     checkCaptcha(a_request, form, a_dbTransaction);
/* 142 */     stripHtml(form.getUser());
/* 143 */     validateMandatoryFields(form, a_request);
/* 144 */     validateFieldLengths(form, a_request);
/*     */ 
/* 148 */     if ((AssetBankSettings.getShowConditionsOnRegister()) && (!form.getConditionsAccepted()))
/*     */     {
/* 151 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationConditions", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 155 */     if ((AssetBankSettings.getUseBrands()) && (!AssetBankSettings.getAutomaticBrandRegistration()) && (form.getRequestedGroupId() <= 0L))
/*     */     {
/* 157 */       this.m_listManager.getListItem(a_dbTransaction, "failedValidationGroupNotGiven", userProfile.getCurrentLanguage());
/* 158 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationGroupNotGiven", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 162 */     ABUser user = form.getUser();
/*     */ 
/* 165 */     user.setRegisterDate(new Date());
/*     */ 
/* 168 */     user.setPasswordChangedDate(new Date());
/*     */ 
/* 171 */     if (AssetBankSettings.getAdditionalUserApprovalEnabled())
/*     */     {
/* 174 */       if ((!StringUtil.stringIsPopulated(form.getVerifyName())) || (!StringUtil.isValidEmailAddress(form.getVerifyEmail())))
/*     */       {
/* 177 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoUserDetailsSupplied", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/* 182 */       else if (!form.getVerifyEmail().contains(AssetBankSettings.getAdditionalApprovalDomain()))
/*     */       {
/* 184 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationEmailNotValidForUser", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 190 */     String sEmailAddress = user.getEmailAddress();
/*     */ 
/* 193 */     if ((StringUtils.isNotEmpty(AssetBankSettings.getLocalUserEmailDomain())) && (!Boolean.parseBoolean(a_request.getParameter("externalUser"))))
/*     */     {
/* 195 */       sEmailAddress = sEmailAddress + "@" + AssetBankSettings.getLocalUserEmailDomain();
/*     */     }
/*     */ 
/* 199 */     if ((!StringUtil.stringIsPopulated(user.getEmailAddress())) || (!StringUtil.isValidEmailAddress(sEmailAddress)))
/*     */     {
/* 201 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationEmailAddress", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 206 */     if ((AssetBankSettings.getUsernameIsEmailAddress()) || (user.getUsername() == null))
/*     */     {
/* 208 */       user.setUsername(sEmailAddress);
/*     */     }
/*     */ 
/* 212 */     String sOrgUnitName = null;
/* 213 */     String sOrgUnitNameForEmail = "";
/* 214 */     long lOrgUnitId = form.getSelectedOrgUnit();
/* 215 */     if (lOrgUnitId > 0L)
/*     */     {
/* 218 */       OrgUnit ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, lOrgUnitId);
/*     */ 
/* 220 */       sOrgUnitName = ou.getCategory().getName();
/* 221 */       sOrgUnitNameForEmail = this.m_listManager.getListItem(a_dbTransaction, "label-org-unit", userProfile.getCurrentLanguage()).getBody() + " " + sOrgUnitName;
/*     */ 
/* 224 */       if (StringUtils.isEmpty(user.getOrganisation()))
/*     */       {
/* 226 */         user.setOrganisation(sOrgUnitName);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 231 */     long lExistingUserId = getUserManager().getUserIdForLocalUsername(a_dbTransaction, user.getUsername());
/*     */ 
/* 233 */     if (lExistingUserId > 0L)
/*     */     {
/* 236 */       ABUser existingUser = (ABUser)getUserManager().getUser(a_dbTransaction, lExistingUserId);
/*     */ 
/* 238 */       if (!existingUser.isRemoteUser())
/*     */       {
/* 240 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDuplicateUsername", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/* 244 */       else if (existingUser.getHidden())
/*     */       {
/* 247 */         existingUser.setHidden(false);
/* 248 */         user = existingUser;
/*     */ 
/* 251 */         user.setAddress(form.getUser().getAddress());
/*     */ 
/* 254 */         user.setEmailAddress(form.getUser().getEmailAddress());
/*     */ 
/* 257 */         user.setForename(form.getUser().getForename());
/* 258 */         user.setSurname(form.getUser().getSurname());
/*     */ 
/* 261 */         user.setRegisterDate(new Date());
/*     */       }
/*     */       else
/*     */       {
/* 266 */         this.m_logger.debug("RegisterUserAction -  username is already in use by a registered Active Directory user");
/* 267 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDuplicateUsername", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 274 */       String sExclusionPattern = AssetBankSettings.getADUsernameExclusionPattern();
/* 275 */       if (StringUtil.stringIsPopulated(sExclusionPattern))
/*     */       {
/* 277 */         String sProposedUsername = user.getUsername();
/* 278 */         if (sProposedUsername.matches(sExclusionPattern))
/*     */         {
/* 281 */           this.m_logger.debug("RegisterUserAction -  username matches the configured Active Directory exclusion pattern.");
/* 282 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationActiveDirectoryUsernamePatternMatch", userProfile.getCurrentLanguage()).getBody());
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 288 */     CustomFieldValidationUtil.validateFields(a_request, this.m_fieldManager, a_dbTransaction, form, 1L, user.getId(), form.getSelectedOrgUnit());
/*     */ 
/* 291 */     ArrayList marketingGroupIds = new ArrayList();
/* 292 */     Collections.addAll(marketingGroupIds, (Object[])form.getMarketingGroupIds());
/*     */ 
/* 294 */     if (AssetBankSettings.getMarketingGroupsMandatory())
/*     */     {
/* 296 */       if (marketingGroupIds.size() <= 0)
/*     */       {
/* 298 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationMarketingGroups", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */ 
/* 302 */     if (form.getHasErrors())
/*     */     {
/* 304 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */ 
/* 307 */       if (form.getSelectedOrgUnit() > 0L)
/*     */       {
/* 309 */         ActionForward afForwardPlusOrg = new ActionForward();
/* 310 */         String sPath = afForward.getPath() + "?" + "orgUnitId" + "=" + form.getSelectedOrgUnit();
/* 311 */         afForwardPlusOrg.setPath(sPath);
/* 312 */         afForward = afForwardPlusOrg;
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 318 */       user.setNotApproved(true);
/*     */ 
/* 323 */       user.setEmailAddress(sEmailAddress);
/*     */       boolean bUserCanRegisterWithoutApproval;
/*     */      // boolean bUserCanRegisterWithoutApproval;
/* 325 */       if (user.getIsLocalUser())
/*     */       {
/* 327 */         bUserCanRegisterWithoutApproval = AssetBankSettings.getUsersCanRegisterWithoutApproval();
/*     */       }
/*     */       else
/*     */       {
/* 331 */         bUserCanRegisterWithoutApproval = AssetBankSettings.getExternalUsersCanRegisterWithoutApproval();
/*     */       }
/*     */ 
/* 335 */       if (form.getRegisteringForPurchase())
/*     */       {
/* 337 */         bUserCanRegisterWithoutApproval = true;
/*     */       }
/*     */ 
/* 341 */       if (bUserCanRegisterWithoutApproval)
/*     */       {
/* 343 */         user.setNotApproved(false);
/*     */ 
/* 346 */         if (!user.isRemoteUser())
/*     */         {
/* 348 */           String sPassword = PasswordUtil.generateRandomPassword();
/* 349 */           user.setPassword(sPassword);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 354 */       if (user.getRegistrationInfo() == null)
/*     */       {
/* 359 */         String sRegistrationInfo = "";
/*     */ 
/* 362 */         Enumeration e = a_request.getParameterNames();
/* 363 */         while (e.hasMoreElements())
/*     */         {
/* 366 */           String sParamName = (String)e.nextElement();
/*     */ 
/* 369 */           if ((sParamName != null) && (sParamName.startsWith("regInfo_")))
/*     */           {
/* 371 */             String sValue = a_request.getParameter(sParamName);
/*     */ 
/* 374 */             sRegistrationInfo = sRegistrationInfo + sValue + "\n";
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 379 */         user.setRegistrationInfo(sRegistrationInfo);
/*     */       }
/*     */ 
/* 383 */       if (AssetBankSettings.getAdditionalUserApprovalEnabled())
/*     */       {
/* 385 */         user.setHidden(true);
/* 386 */         user.setAdditionalApproverDetails(form.getVerifyName() + " (" + form.getVerifyEmail() + ")");
/*     */       }
/*     */ 
/* 390 */       getUserManager().saveUser(a_dbTransaction, user);
/*     */ 
/* 394 */       this.m_assetBoxManager.synchroniseAssetBox(a_dbTransaction, userProfile, user.getId(), user.getNewAssetBoxId());
/*     */ 
/* 397 */       if ((AssetBankSettings.getAutomaticBrandRegistration()) && (userProfile.getBrand() != null) && (userProfile.getBrand().getId() > 0L))
/*     */       {
/* 399 */         Vector<Group> vecGroups = getUserManager().getAutomaticRegistrationGroupsForBrand(a_dbTransaction, userProfile.getBrand().getId());
/* 400 */         if ((vecGroups != null) && (vecGroups.size() > 0))
/*     */         {
/* 402 */           Vector vecGroupIds = new Vector(vecGroups.size());
/* 403 */           for (Group group : vecGroups)
/*     */           {
/* 405 */             vecGroupIds.add(Long.valueOf(group.getId()));
/*     */           }
/* 407 */           getUserManager().addUserToGroups(a_dbTransaction, user.getId(), vecGroupIds);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 412 */       if (AssetBankSettings.getAdditionalUserApprovalEnabled())
/*     */       {
/* 415 */         HashMap hmParams = new HashMap();
/*     */ 
/* 417 */         hmParams.put("template", "additional_user_approval");
/* 418 */         hmParams.put("recipients", form.getVerifyEmail());
/* 419 */         hmParams.put("name", form.getVerifyName());
/*     */ 
/* 421 */         addUserElementsToEmail(a_dbTransaction, a_request, hmParams, user);
/*     */ 
/* 424 */         String sAcceptUrl = ServletUtil.getApplicationUrl(a_request) + "/action/additionalUserApproval?approve=1&id=" + user.getId();
/* 425 */         String sRejectUrl = ServletUtil.getApplicationUrl(a_request) + "/action/additionalUserApproval?approve=0&id=" + user.getId();
/*     */ 
/* 427 */         hmParams.put("approve", sAcceptUrl);
/* 428 */         hmParams.put("reject", sRejectUrl);
/*     */         try
/*     */         {
/* 432 */           String sEmailAddresses = getUserManager().getAdminEmailAddresses();
/*     */ 
/* 434 */           if (StringUtil.stringIsPopulated(sEmailAddresses))
/*     */           {
/* 436 */             hmParams.put("adminEmailAddresses", sEmailAddresses);
/*     */           }
/*     */ 
/* 439 */           this.m_emailManager.sendTemplatedEmail(hmParams, LanguageConstants.k_defaultLanguage);
/*     */         }
/*     */         catch (Bn2Exception be)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 450 */       CustomFieldUtil.getAndSaveFieldValues(a_request, this.m_fieldManager, a_dbTransaction, 1L, user.getId());
/*     */ 
/* 453 */       if (lOrgUnitId > 0L)
/*     */       {
/* 455 */         this.m_orgUnitManager.addUserToOrgUnit(a_dbTransaction, lOrgUnitId, user.getId());
/*     */       }
/*     */ 
/* 459 */       String sRequestedGroup = "";
/* 460 */       if (form.getRequestedGroupId() > 0L)
/*     */       {
/* 462 */         Vector vecGroupId = new Vector();
/* 463 */         vecGroupId.add(new Long(form.getRequestedGroupId()));
/* 464 */         getUserManager().addGroupsToUser(a_dbTransaction, user.getId(), vecGroupId);
/*     */ 
/* 466 */         Group group = getUserManager().getGroup(form.getRequestedGroupId());
/* 467 */         sRequestedGroup = group.getName();
/*     */       }
/*     */ 
/* 472 */       this.m_marketingGroupManager.setMarketingGroupIdsForUser(a_dbTransaction, user.getId(), marketingGroupIds);
/*     */ 
/* 476 */       commitTransaction(a_dbTransaction);
/*     */ 
/* 480 */       HashMap hmParams = new HashMap();
/*     */ 
/* 482 */       if (bUserCanRegisterWithoutApproval)
/*     */       {
/* 485 */         String sName = user.getFullName();
/* 486 */         if (!StringUtil.stringIsPopulated(sName))
/*     */         {
/* 488 */           sName = user.getUsername();
/*     */         }
/*     */ 
/* 491 */         hmParams.put("template", "user_approved");
/* 492 */         hmParams.put("name", sName);
/* 493 */         hmParams.put("requestedgroupname", sRequestedGroup);
/* 494 */         addUserElementsToEmail(null, a_request, hmParams, user);
/*     */         try
/*     */         {
/* 498 */           this.m_emailManager.sendTemplatedEmail(hmParams, user.getLanguage());
/*     */         }
/*     */         catch (Bn2Exception be)
/*     */         {
/*     */         }
/*     */ 
/* 506 */         if (AssetBankSettings.getSendAdminUserRegEmails())
/*     */         {
/* 508 */           hmParams.put("template", "user_registered_admin");
/* 509 */           hmParams.put("adminEmailAddresses", getUserManager().getAdminEmailAddresses());
/* 510 */           hmParams.put("name", sName);
/* 511 */           addUserElementsToEmail(null, a_request, hmParams, user);
/* 512 */           hmParams.put("id", String.valueOf(user.getId()));
/*     */           try
/*     */           {
/* 517 */             this.m_emailManager.sendTemplatedEmail(hmParams, user.getLanguage());
/*     */           }
/*     */           catch (Bn2Exception be)
/*     */           {
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/* 526 */       else if (!AssetBankSettings.getAdditionalUserApprovalEnabled())
/*     */       {
/* 530 */         hmParams.put("template", "user_registered");
/* 531 */         hmParams.put("name", user.getFullName());
/* 532 */         hmParams.put("orgunitname", sOrgUnitNameForEmail);
/* 533 */         hmParams.put("requestedgroupname", sRequestedGroup);
/*     */ 
/* 535 */         hmParams.put("establishment", user.getOrganisation());
/*     */ 
/* 538 */         DBTransaction dbEmailTransaction = null;
/*     */ 
/* 541 */         addUserElementsToEmail(dbEmailTransaction, a_request, hmParams, user);
/*     */         try
/*     */         {
/* 547 */           String sEmailAddresses = "";
/* 548 */           if (lOrgUnitId > 0L)
/*     */           {
/* 551 */             dbEmailTransaction = getNewTransaction();
/* 552 */             sEmailAddresses = this.m_orgUnitManager.getAdminEmailAddresses(dbEmailTransaction, lOrgUnitId);
/*     */           }
/*     */ 
/* 555 */           if (!StringUtil.stringIsPopulated(sEmailAddresses))
/*     */           {
/* 558 */             sEmailAddresses = getUserManager().getAdminEmailAddressesInDivision(form.getUser().getRefDivision().getId(), true);
/*     */           }
/*     */ 
/* 562 */           if (!StringUtil.stringIsPopulated(sEmailAddresses))
/*     */           {
/* 565 */             sEmailAddresses = getUserManager().getAdminEmailAddresses();
/*     */           }
/*     */ 
/* 569 */           if (StringUtil.stringIsPopulated(sEmailAddresses))
/*     */           {
/* 571 */             hmParams.put("adminEmailAddresses", sEmailAddresses);
/*     */           }
/*     */ 
/* 574 */           this.m_emailManager.sendTemplatedEmail(hmParams, LanguageConstants.k_defaultLanguage);
/*     */         }
/*     */         catch (Bn2Exception be)
/*     */         {
/* 579 */           this.m_logger.error("RegisterUserAction.execute : Bn2Exception : " + be);
/* 580 */           throw new Bn2Exception("RegisterUserAction.execute : Bn2Exception : " + be, be);
/*     */         }
/*     */         finally
/*     */         {
/* 585 */           if (dbEmailTransaction != null)
/*     */           {
/*     */             try
/*     */             {
/* 589 */               dbEmailTransaction.commit();
/*     */             }
/*     */             catch (SQLException sqle)
/*     */             {
/* 593 */               this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 601 */       afForward = createRedirectingForward("externalUser=" + (!user.getIsLocalUser()), a_mapping, "Success");
/*     */     }
/*     */ 
/* 604 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void addUserElementsToEmail(DBTransaction a_dbTransaction, HttpServletRequest a_request, HashMap<String, String> a_hmParams, ABUser a_user)
/*     */     throws Bn2Exception
/*     */   {
/* 611 */     a_hmParams.put("forename", a_user.getForename());
/* 612 */     a_hmParams.put("surname", a_user.getSurname());
/* 613 */     a_hmParams.put("email", a_user.getEmailAddress());
/* 614 */     a_hmParams.put("username", a_user.getUsername());
/* 615 */     a_hmParams.put("organisation", a_user.getOrganisation());
/* 616 */     a_hmParams.put("password", a_user.getPassword());
/*     */ 
/* 619 */     if (this.m_hmUserCustomFields == null)
/*     */     {
/* 621 */       this.m_hmUserCustomFields = CustomFieldUtil.getEmailTemplateParams(a_request, this.m_fieldManager, a_dbTransaction, 1L, a_user.getId());
/*     */     }
/* 623 */     a_hmParams.putAll(this.m_hmUserCustomFields);
/*     */   }
/*     */ 
/*     */   public boolean checkCaptcha(HttpServletRequest a_request, Bn2Form a_form, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 634 */     boolean bPassed = true;
/* 635 */     if (AssetBankSettings.getShowCaptchaOnRegistration())
/*     */     {
/* 637 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 638 */       Captcha captcha = (Captcha)a_request.getSession().getAttribute("simpleCaptcha");
/* 639 */       String sAnswer = a_request.getParameter("capAnswer");
/* 640 */       if (!captcha.isCorrect(sAnswer))
/*     */       {
/* 642 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationCaptcha", userProfile.getCurrentLanguage()).getBody());
/* 643 */         bPassed = false;
/*     */       }
/*     */     }
/* 646 */     return bPassed;
/*     */   }
/*     */ 
/*     */   public static void stripHtml(ABUser a_user)
/*     */   {
/* 656 */     if (a_user != null)
/*     */     {
/* 658 */       a_user.setAddress(StringUtil.xssProcess(a_user.getAddress()));
/* 659 */       a_user.setEmailAddress(StringUtil.xssProcess(a_user.getEmailAddress()));
/* 660 */       a_user.setForename(StringUtil.xssProcess(a_user.getForename()));
/* 661 */       a_user.setSurname(StringUtil.xssProcess(a_user.getSurname()));
/* 662 */       a_user.setJobTitle(StringUtil.xssProcess(a_user.getJobTitle()));
/* 663 */       a_user.setOrganisation(StringUtil.xssProcess(a_user.getOrganisation()));
/* 664 */       a_user.setRegistrationInfo(StringUtil.xssProcess(a_user.getRegistrationInfo()));
/* 665 */       a_user.setTitle(StringUtil.xssProcess(a_user.getTitle()));
/* 666 */       a_user.setUsername(StringUtil.xssProcess(a_user.getUsername()));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 687 */     return true;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_sEmailManager)
/*     */   {
/* 692 */     this.m_emailManager = a_sEmailManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/* 697 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupManager(MarketingGroupManager marketingGroupManager)
/*     */   {
/* 702 */     this.m_marketingGroupManager = marketingGroupManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 707 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/* 712 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 717 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.RegisterUserAction
 * JD-Core Version:    0.6.0
 */