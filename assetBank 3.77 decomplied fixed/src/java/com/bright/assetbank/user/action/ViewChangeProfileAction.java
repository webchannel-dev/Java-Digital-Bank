/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.form.ChangeProfileForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
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
/*     */ public class ViewChangeProfileAction extends UserAction
/*     */   implements ImageConstants, UserConstants, CommonConstants, AssetBankConstants
/*     */ {
/*  64 */   private MarketingGroupManager m_marketingGroupManager = null;
/*  65 */   private LanguageManager m_languageManager = null;
/*  66 */   private CustomFieldManager m_fieldManager = null;
/*  67 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  85 */     ChangeProfileForm form = (ChangeProfileForm)a_form;
/*     */ 
/*  88 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  90 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  92 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  93 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  97 */     ABUser user = null;
/*     */ 
/*  99 */     if (a_request.getParameter("reloadPage") == null)
/*     */     {
/* 101 */       if (a_request.getAttribute("user") != null)
/*     */       {
/* 104 */         user = (ABUser)a_request.getAttribute("user");
/*     */       }
/*     */       else
/*     */       {
/* 108 */         user = (ABUser)getUserManager().getUser(a_dbTransaction, userProfile.getUser().getId());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 113 */       user = form.getUser();
/*     */     }
/*     */ 
/* 116 */     User userUpdated = null;
/*     */ 
/* 118 */     if (user.getLastModifiedBy() > 0L)
/*     */     {
/* 120 */       userUpdated = getUserManager().getUser(a_dbTransaction, user.getLastModifiedBy());
/*     */     }
/*     */ 
/* 123 */     form.setLastUpdatedBy(userUpdated);
/*     */ 
/* 125 */     if (user.getInvitedByUserId() > 0L)
/*     */     {
/* 127 */       form.setInvitedByUser(getUserManager().getUser(a_dbTransaction, user.getInvitedByUserId()));
/*     */     }
/*     */ 
/* 130 */     form.setUser(user);
/*     */ 
/* 133 */     Vector vecDivisions = getUserManager().getAllDivisions(a_dbTransaction);
/* 134 */     form.setDivisionList(vecDivisions);
/*     */ 
/* 137 */     List marketingGroups = this.m_marketingGroupManager.getMarketingGroups(a_dbTransaction, user.getLanguage());
/* 138 */     LanguageUtils.setLanguageOnAll(marketingGroups, userProfile.getCurrentLanguage());
/* 139 */     form.setMarketingGroups(marketingGroups);
/*     */ 
/* 141 */     if (!form.isPopulatedviaReload())
/*     */     {
/* 143 */       List marketingGroupIds = this.m_marketingGroupManager.getMarketingGroupIdsForUser(a_dbTransaction, user.getId());
/* 144 */       form.setMarketingGroupIds((String[])(String[])marketingGroupIds.toArray(new String[marketingGroups.size()]));
/*     */     }
/*     */ 
/* 148 */     List languages = this.m_languageManager.getLanguages(a_dbTransaction, true, false);
/* 149 */     form.setLanguages(languages);
/*     */ 
/* 152 */     Vector vecOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, user.getId());
/* 153 */     user.setOrgUnits(vecOrgUnits);
/*     */ 
/* 155 */     CustomFieldUtil.prepCustomFields(a_request, form, form.getUser(), this.m_fieldManager, a_dbTransaction, 1L, form.getUser());
/*     */ 
/* 157 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupManager(MarketingGroupManager marketingGroupManager)
/*     */   {
/* 162 */     this.m_marketingGroupManager = marketingGroupManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 167 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/* 172 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/* 177 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewChangeProfileAction
 * JD-Core Version:    0.6.0
 */