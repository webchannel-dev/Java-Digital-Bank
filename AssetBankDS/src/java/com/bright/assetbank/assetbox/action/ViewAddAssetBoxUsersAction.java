/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.form.ShareAssetBoxForm;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.service.AddressManager;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.service.EmailTemplateManager;
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
/*     */ public class ViewAddAssetBoxUsersAction extends BTransactionAction
/*     */   implements AssetBoxConstants, FrameworkConstants
/*     */ {
/*  57 */   private ABUserManager m_userManager = null;
/*  58 */   private MarketingGroupManager m_marketingGroupManager = null;
/*  59 */   private AddressManager m_addressManager = null;
/*  60 */   private EmailTemplateManager m_emailTemplateManager = null;
/*  61 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     ActionForward afForward = null;
/*  71 */     ShareAssetBoxForm form = (ShareAssetBoxForm)a_form;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  77 */       this.m_logger.debug("The user must be logged in to access " + getClass().getSimpleName());
/*  78 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  81 */     if (!AssetBankSettings.isSharedLightboxesEnabled())
/*     */     {
/*  83 */       throw new Bn2Exception("ViewShareAssetBoxAction : Lightbox sharing is not enabled in the application settings");
/*     */     }
/*     */ 
/*  87 */     long lId = getLongParameter(a_request, "assetBoxId");
/*     */ 
/*  89 */     if (lId > 0L)
/*     */     {
/*  91 */       form.setAssetBoxId(lId);
/*     */     }
/*     */ 
/*  96 */     Vector vGroupIds = null;
/*     */     Vector vecGroups;
/*  97 */     if ((userProfile.getIsAdmin()) || (!AssetBankSettings.getShareLightboxGroupOnly()))
/*     */     {
/*  99 */        vecGroups = this.m_userManager.getAllGroups();
/*     */ 
/* 102 */       form.setMarketingGroups(this.m_marketingGroupManager.getMarketingGroups(a_dbTransaction));
/*     */     }
/*     */     else
/*     */     {
/* 106 */       vGroupIds = (Vector)userProfile.getGroupIds().clone();
/* 107 */       vGroupIds.remove(Long.valueOf(1L));
/* 108 */       vGroupIds.remove(Long.valueOf(2L));
/* 109 */       vecGroups = this.m_userManager.getGroups(vGroupIds);
/*     */     }
/* 111 */     form.setGroups(vecGroups);
/*     */ 
/* 113 */     if (AssetBankSettings.getUsersHaveStructuredAddress())
/*     */     {
/* 115 */       form.setCountries(this.m_addressManager.getCountryList());
/*     */     }
/*     */ 
/* 118 */     UserSearchCriteria criteria = form.getSearchCriteria();
/* 119 */     if (!criteria.isEmpty())
/*     */     {
/* 122 */       criteria.setNotApproved(Boolean.FALSE);
/* 123 */       criteria.setExcludedUserId(userProfile.getUser().getId());
/* 124 */       criteria.setGroupIds(vGroupIds);
/*     */ 
/* 126 */       List users = this.m_userManager.findUsers(criteria, 0);
/* 127 */       form.setUsers(users);
/*     */     }
/*     */ 
/* 130 */     form.setName(userProfile.getAssetBox().getName());
/*     */ 
/* 132 */     if (AssetBankSettings.isMarketingEnabled())
/*     */     {
/* 135 */       form.setEmailTemplates(this.m_emailTemplateManager.getEmailItems(a_dbTransaction, 2L, LanguageConstants.k_defaultLanguage, 1));
/*     */     }
/*     */ 
/* 139 */     if (AssetBankSettings.getSupportMultiLanguage())
/*     */     {
/* 141 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getEmailContent());
/*     */     }
/*     */ 
/* 144 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 146 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 151 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupManager(MarketingGroupManager marketingGroupManager)
/*     */   {
/* 156 */     this.m_marketingGroupManager = marketingGroupManager;
/*     */   }
/*     */ 
/*     */   public void setAddressManager(AddressManager addressManager)
/*     */   {
/* 161 */     this.m_addressManager = addressManager;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateManager(EmailTemplateManager emailTemplateManager)
/*     */   {
/* 166 */     this.m_emailTemplateManager = emailTemplateManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 171 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ViewAddAssetBoxUsersAction
 * JD-Core Version:    0.6.0
 */