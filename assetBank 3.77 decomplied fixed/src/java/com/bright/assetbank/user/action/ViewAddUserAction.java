/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
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
/*     */ public class ViewAddUserAction extends UserAction
/*     */   implements UserConstants, AssetBankConstants
/*     */ {
/*  55 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  60 */   private LanguageManager m_languageManager = null;
/*     */ 
/*  66 */   private CustomFieldManager m_fieldManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  57 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/*  63 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/*  69 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     ActionForward afForward = null;
/*  92 */     UserForm form = (UserForm)a_form;
/*  93 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  96 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  98 */       this.m_logger.error("ViewAddUserAction.execute : User does not have admin permission : " + userProfile);
/*  99 */       return a_mapping.findForward("NoPermission");
/*     */     }
/* 101 */     ABUser user = (ABUser)userProfile.getUser();
/*     */     Vector vecGroups;
/*     */     //Vector vecGroups;
/* 105 */     if (userProfile.getIsAdmin())
/*     */     {
/* 107 */       vecGroups = getUserManager().getAllGroups();
/*     */     }
/*     */     else
/*     */     {
/* 112 */       vecGroups = this.m_orgUnitManager.getOrgUnitAdminUserManagedGroups(a_dbTransaction, user.getId());
/*     */     }
/* 114 */     form.setGroups(vecGroups);
/*     */ 
/* 117 */     Vector vecDivisions = getUserManager().getAllDivisions(a_dbTransaction);
/* 118 */     form.setDivisionList(vecDivisions);
/*     */ 
/* 121 */     List languages = this.m_languageManager.getLanguages(a_dbTransaction, true, false);
/* 122 */     form.setLanguages(languages);
/*     */ 
/* 125 */     int expiryDays = AssetBankSettings.getUserDefaultExpiryDays();
/* 126 */     if (expiryDays > 0) {
/* 127 */       BrightDate date = BrightDate.todayPlusOffsetDays(expiryDays);
/* 128 */       form.setExpiryDate(date.getDisplayDate());
/*     */     }
/*     */ 
/* 132 */     form.setSelectedLanguage(this.m_languageManager.getDefaultLanguage(a_dbTransaction).getId());
/* 133 */     CustomFieldUtil.prepCustomFields(a_request, form, form.getUser(), this.m_fieldManager, a_dbTransaction, 1L, user);
/*     */ 
/* 135 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 137 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewAddUserAction
 * JD-Core Version:    0.6.0
 */