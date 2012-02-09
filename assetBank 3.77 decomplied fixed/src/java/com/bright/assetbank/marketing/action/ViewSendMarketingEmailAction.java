/*     */ package com.bright.assetbank.marketing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.marketing.constant.MarketingConstants;
/*     */ import com.bright.assetbank.marketing.form.SendMarketingEmailForm;
/*     */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.service.AddressManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.constant.MailConstants;
/*     */ import com.bright.framework.mail.service.EmailTemplateManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewSendMarketingEmailAction extends MarketingGroupAction
/*     */   implements MailConstants, MarketingConstants
/*     */ {
/*  57 */   private LanguageManager m_languageManager = null;
/*  58 */   private ABUserManager m_userManager = null;
/*  59 */   private AddressManager m_addressManager = null;
/*  60 */   private EmailTemplateManager m_emailTemplateManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     SendMarketingEmailForm form = (SendMarketingEmailForm)a_form;
/*  81 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  84 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  86 */       this.m_logger.error("ViewAddMarketingGroupAction.execute : User must be an administrator.");
/*  87 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  91 */     long lAssetBoxId = getLongParameter(a_request, "assetBoxId");
/*     */ 
/*  93 */     if (lAssetBoxId > 0L)
/*     */     {
/*  95 */       form.setAssetBoxId(lAssetBoxId);
/*  96 */       form.setAssetLink(ServletUtil.getApplicationUrl(a_request) + "/action/" + "viewEmailAssets" + "?" + "id" + "=" + "#" + "emailId" + "#");
/*     */     }
/*     */ 
/* 102 */     Vector vGroupIds = null;
/*     */     Vector vecGroups;
/* 104 */     if ((userProfile.getIsAdmin()) || (!AssetBankSettings.getShareLightboxGroupOnly()))
/*     */     {
/* 106 */       vecGroups = this.m_userManager.getAllGroups();
/*     */ 
/* 109 */       form.setMarketingGroups(getMarketingGroupManager().getMarketingGroups(a_dbTransaction));
/*     */     }
/*     */     else
/*     */     {
/* 113 */       vGroupIds = (Vector)userProfile.getGroupIds().clone();
/* 114 */       vGroupIds.remove(Long.valueOf(1L));
/* 115 */       vGroupIds.remove(Long.valueOf(2L));
/* 116 */       vecGroups = this.m_userManager.getGroups(vGroupIds);
/*     */     }
/* 118 */     form.setGroups(vecGroups);
/*     */ 
/* 120 */     if (AssetBankSettings.getUsersHaveStructuredAddress())
/*     */     {
/* 122 */       form.setCountries(this.m_addressManager.getCountryList());
/*     */     }
/*     */ 
/* 125 */     UserSearchCriteria criteria = form.getSearchCriteria();
/* 126 */     if (!criteria.isEmpty())
/*     */     {
/* 129 */       criteria.setNotApproved(Boolean.FALSE);
/* 130 */       criteria.setExcludedUserId(userProfile.getUser().getId());
/* 131 */       criteria.setGroupIds(vGroupIds);
/*     */ 
/* 133 */       List users = this.m_userManager.findUsers(criteria, 0);
/* 134 */       form.setUsers(users);
/*     */     }
/*     */ 
/* 137 */     if (AssetBankSettings.isMarketingEnabled())
/*     */     {
/* 140 */       form.setEmailTemplates(this.m_emailTemplateManager.getEmailItems(a_dbTransaction, 2L, LanguageConstants.k_defaultLanguage, 1));
/*     */     }
/*     */ 
/* 144 */     if (AssetBankSettings.getSupportMultiLanguage())
/*     */     {
/* 146 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getEmailContent());
/*     */     }
/*     */ 
/* 149 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 154 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ 
/*     */   public void setAddressManager(AddressManager addressManager)
/*     */   {
/* 159 */     this.m_addressManager = addressManager;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateManager(EmailTemplateManager emailTemplateManager)
/*     */   {
/* 164 */     this.m_emailTemplateManager = emailTemplateManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager userManager)
/*     */   {
/* 169 */     this.m_userManager = userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.ViewSendMarketingEmailAction
 * JD-Core Version:    0.6.0
 */