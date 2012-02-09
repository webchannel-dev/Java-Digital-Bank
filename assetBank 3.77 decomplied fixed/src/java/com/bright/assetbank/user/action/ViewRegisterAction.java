/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnitSearchCriteria;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.RegisterForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewRegisterAction extends UserAction
/*     */   implements OrgUnitConstants
/*     */ {
/*  61 */   private OrgUnitManager m_orgUnitManager = null;
/*  62 */   private MarketingGroupManager m_marketingGroupManager = null;
/*  63 */   private LanguageManager m_languageManager = null;
/*  64 */   private CustomFieldManager m_fieldManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     ActionForward afForward = null;
/*  83 */     RegisterForm form = (RegisterForm)a_form;
/*  84 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  87 */     RegisterUserAction.stripHtml(form.getUser());
/*     */ 
/*  90 */     long lInvitedBy = getLongParameter(a_request, "invitedBy");
/*  91 */     form.getUser().setInvitedByUserId(lInvitedBy);
/*     */ 
/*  94 */     OrgUnitSearchCriteria search = new OrgUnitSearchCriteria();
/*  95 */     Vector vecOrgUnits = this.m_orgUnitManager.getOrgUnitList(a_dbTransaction, search, false);
/*  96 */     form.setOrgUnitList(vecOrgUnits);
/*     */ 
/*  99 */     Vector vecDivisions = getUserManager().getAllDivisions(a_dbTransaction);
/* 100 */     form.setDivisionList(vecDivisions);
/*     */ 
/* 103 */     Vector vecGroups = getUserManager().getBrandGroups(a_dbTransaction);
/* 104 */     form.setRegisterGroupList(vecGroups);
/*     */ 
/* 107 */     List marketingGroups = this.m_marketingGroupManager.getMarketingGroups(a_dbTransaction, form.getUser().getLanguage());
/* 108 */     LanguageUtils.setLanguageOnAll(marketingGroups, userProfile.getCurrentLanguage());
/* 109 */     form.setMarketingGroups(marketingGroups);
/*     */ 
/* 112 */     List languages = this.m_languageManager.getLanguages(a_dbTransaction, true, false);
/* 113 */     form.setLanguages(languages);
/*     */ 
/* 116 */     Language language = userProfile.getCurrentLanguage();
/*     */ 
/* 118 */     form.setSelectedLanguage(language.getId());
/*     */ 
/* 120 */     if ((userProfile.getUser() != null) && (!form.isPopulatedviaReload()))
/*     */     {
/* 122 */       List marketingGroupIds = this.m_marketingGroupManager.getMarketingGroupIdsForUser(a_dbTransaction, userProfile.getUser().getId());
/* 123 */       form.setMarketingGroupIds((String[])(String[])marketingGroupIds.toArray(new String[marketingGroups.size()]));
/*     */     }
/*     */ 
/* 127 */     CustomFieldUtil.prepCustomFields(a_request, form, form.getUser(), this.m_fieldManager, a_dbTransaction, 1L, null);
/* 128 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 130 */     return afForward;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/* 150 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupManager(MarketingGroupManager marketingGroupManager)
/*     */   {
/* 155 */     this.m_marketingGroupManager = marketingGroupManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 160 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/* 165 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewRegisterAction
 * JD-Core Version:    0.6.0
 */