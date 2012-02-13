/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.RequestUserUpdateForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RequestUserUpdateAction extends UserAction
/*     */   implements MessageConstants
/*     */ {
/*  51 */   protected EmailManager m_emailManager = null;
/*     */ 
/*  57 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*     */   public void setEmailManager(EmailManager a_sEmailManager)
/*     */   {
/*  54 */     this.m_emailManager = a_sEmailManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  60 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  84 */     ActionForward afForward = null;
/*  85 */     RequestUserUpdateForm form = (RequestUserUpdateForm)a_form;
/*     */ 
/*  88 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  89 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  91 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  92 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  95 */     long lUserId = userProfile.getUser().getId();
/*  96 */     long lOrgUnitId = form.getSelectedOrgUnit();
/*  97 */     String sNotes = form.getRegInfo();
/*     */ 
/* 103 */     ABUser user = (ABUser)getUserManager().getUser(a_dbTransaction, lUserId);
/* 104 */     user.setRegistrationInfo(sNotes);
/* 105 */     user.setRequiresUpdate(true);
/* 106 */     user.setRequestedOrgUnitId(lOrgUnitId);
/* 107 */     getUserManager().saveUser(a_dbTransaction, user);
/*     */ 
/* 111 */     String sOrgUnitName = "None";
/* 112 */     if (lOrgUnitId > 0L)
/*     */     {
/* 114 */       OrgUnit ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, lOrgUnitId);
/* 115 */       sOrgUnitName = ou.getCategory().getName();
/*     */     }
/*     */ 
/* 121 */     commitTransaction(a_dbTransaction);
/*     */ 
/* 124 */     HashMap hmParams = new HashMap();
/*     */ 
/* 126 */     hmParams.put("template", "admin_alert_update_request");
/* 127 */     hmParams.put("email", user.getEmailAddress());
/* 128 */     hmParams.put("name", user.getFullName());
/* 129 */     hmParams.put("orgunitname", sOrgUnitName);
/* 130 */     hmParams.put("usernotes", sNotes);
/*     */ 
/* 134 */     String sEmailAddresses = "";
/* 135 */     if (lOrgUnitId > 0L)
/*     */     {
/* 138 */       a_dbTransaction = getNewTransaction();
/* 139 */       sEmailAddresses = this.m_orgUnitManager.getAdminEmailAddresses(a_dbTransaction, lOrgUnitId);
/*     */     }
/* 141 */     if (!StringUtil.stringIsPopulated(sEmailAddresses))
/*     */     {
/* 143 */       sEmailAddresses = getUserManager().getAdminEmailAddresses();
/*     */     }
/*     */ 
/* 146 */     if (StringUtil.stringIsPopulated(sEmailAddresses))
/*     */     {
/* 148 */       hmParams.put("adminEmailAddresses", sEmailAddresses);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 154 */       this.m_emailManager.sendTemplatedEmail(hmParams, LanguageConstants.k_defaultLanguage);
/*     */     }
/*     */     catch (Bn2Exception be)
/*     */     {
/*     */     }
/*     */ 
/* 162 */     afForward = a_mapping.findForward("Success");
/* 163 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.RequestUserUpdateAction
 * JD-Core Version:    0.6.0
 */