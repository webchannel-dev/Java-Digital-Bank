/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUpdateUserAction extends UserAction
/*     */   implements FrameworkConstants, UserConstants, AssetBankConstants
/*     */ {
/*  65 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  69 */   private LanguageManager m_languageManager = null;
/*     */ 
/*  74 */   private CustomFieldManager m_fieldManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  67 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/*  72 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/*  77 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  99 */     ActionForward afForward = null;
/* 100 */     UserForm form = (UserForm)a_form;
/* 101 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 104 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/* 106 */       this.m_logger.error("ViewUpdateUserAction.execute : User does not have admin permission : " + userProfile);
/* 107 */       return a_mapping.findForward("NoPermission");
/*     */     }
/* 109 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/* 112 */     long lUserId = getIntParameter(a_request, "id");
/*     */ 
/* 114 */     if (lUserId > 0L)
/*     */     {
/*     */       Vector vecGroups;
/*     */      // Vector vecGroups;
/* 118 */       if (userProfile.getIsAdmin())
/*     */       {
/* 120 */         vecGroups = getUserManager().getAllGroups();
/*     */       }
/*     */       else
/*     */       {
/* 125 */         vecGroups = this.m_orgUnitManager.getOrgUnitAdminUserManagedGroups(a_dbTransaction, user.getId());
/*     */       }
/* 127 */       form.setGroups(vecGroups);
/*     */ 
/* 130 */       Vector vecDivisions = getUserManager().getAllDivisions(a_dbTransaction);
/* 131 */       form.setDivisionList(vecDivisions);
/*     */ 
/* 134 */       if (!form.getHasErrors())
/*     */       {
/* 136 */         ABUser managedUser = (ABUser)getUserManager().getUser(a_dbTransaction, lUserId);
/* 137 */         form.setOldUsername(managedUser.getUsername());
/*     */ 
/* 139 */         User userUpdated = null;
/* 140 */         if (managedUser.getLastModifiedBy() > 0L)
/*     */         {
/* 142 */           userUpdated = getUserManager().getUser(a_dbTransaction, managedUser.getLastModifiedBy());
/*     */         }
/* 144 */         form.setLastUpdatedBy(userUpdated);
/*     */ 
/* 146 */         if (managedUser.getInvitedByUserId() > 0L)
/*     */         {
/* 148 */           form.setInvitedByUser(getUserManager().getUser(a_dbTransaction, managedUser.getInvitedByUserId()));
/*     */         }
/*     */ 
/* 152 */         form.populate(managedUser);
/*     */ 
/* 155 */         if (AssetBankSettings.getSpecifyRemoteUsername())
/*     */         {
/* 157 */           form.getUser().setRemoteUser(false);
/*     */         }
/*     */ 
/* 161 */         long lRequestedOrgUnitId = managedUser.getRequestedOrgUnitId();
/* 162 */         if (lRequestedOrgUnitId > 0L)
/*     */         {
/* 164 */           OrgUnit ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, lRequestedOrgUnitId);
/* 165 */           form.setRequestedOrgUnit(ou.getCategory().getName());
/*     */         }
/*     */ 
/* 169 */         form.setNotifyUser(AssetBankSettings.getNotifyUserOnUpdateDefault());
/*     */ 
/* 172 */         form.setSelectedLanguage(managedUser.getLanguage().getId());
/*     */ 
/* 175 */         int expiryDays = AssetBankSettings.getUserDefaultExpiryDays();
/* 176 */         if ((expiryDays > 0) && (managedUser.getNotApproved()))
/*     */         {
/* 178 */           BrightDate date = BrightDate.todayPlusOffsetDays(expiryDays);
/* 179 */           form.setExpiryDate(date.getDisplayDate());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 184 */       List languages = this.m_languageManager.getLanguages(a_dbTransaction, true, false);
/* 185 */       form.setLanguages(languages);
/* 186 */       CustomFieldUtil.prepCustomFields(a_request, form, form.getUser(), this.m_fieldManager, a_dbTransaction, 1L, user);
/*     */     }
/*     */ 
/* 189 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 191 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewUpdateUserAction
 * JD-Core Version:    0.6.0
 */