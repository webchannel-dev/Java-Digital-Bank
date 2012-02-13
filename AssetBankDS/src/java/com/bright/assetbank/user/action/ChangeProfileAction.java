/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.customfield.util.CustomFieldValidationUtil;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.ChangeProfileForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.exception.UsernameInUseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ChangeProfileAction extends UserAction
/*     */   implements ImageConstants, AssetBankConstants, MessageConstants
/*     */ {
/*  71 */   private AssetBoxManager m_assetBoxManager = null;
/*  72 */   private MarketingGroupManager m_marketingGroupManager = null;
/*  73 */   private LanguageManager m_languageManager = null;
/*  74 */   protected ListManager m_listManager = null;
/*  75 */   protected CustomFieldManager m_fieldManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  94 */     ActionForward afForward = null;
/*  95 */     ChangeProfileForm form = (ChangeProfileForm)a_form;
/*     */ 
/*  98 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  99 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 101 */       this.m_logger.debug("This user does not have permission to view the admin page");
/* 102 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 105 */     if (StringUtils.isNotEmpty(a_request.getParameter("reloadPage")))
/*     */     {
/* 107 */       return a_mapping.findForward("Reload");
/*     */     }
/*     */ 
/* 111 */     validateMandatoryFields(form, a_request);
/* 112 */     validateFieldLengths(form, a_request);
/* 113 */     form.validate(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/* 116 */     ABUser user = (ABUser)getUserManager().getUser(a_dbTransaction, form.getUser().getId());
/* 117 */     updateUserWithCommonFields(user, form);
/*     */ 
/* 120 */     if (AssetBankSettings.getUsernameIsEmailAddress())
/*     */     {
/* 122 */       user.setUsername(form.getUser().getEmailAddress());
/*     */     }
/*     */ 
/* 126 */     user.setLastModifiedBy(userProfile.getUser().getId());
/*     */ 
/* 129 */     CustomFieldValidationUtil.validateFields(a_request, this.m_fieldManager, a_dbTransaction, form, 1L, user.getId(), -1L);
/*     */ 
/* 132 */     boolean bValid = !form.getHasErrors();
/*     */ 
/* 134 */     if (bValid)
/*     */     {
/* 137 */       CustomFieldUtil.getAndSaveFieldValues(a_request, this.m_fieldManager, a_dbTransaction, 1L, user.getId());
/*     */ 
/* 140 */       if (user.getLanguage().getId() != userProfile.getCurrentLanguage().getId())
/*     */       {
/* 142 */         Language language = this.m_languageManager.getLanguage(a_dbTransaction, user.getLanguage().getId());
/* 143 */         userProfile.setCurrentLanguage(language);
/* 144 */         a_request.getSession().setAttribute("currentLanguage", language);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 149 */         getUserManager().saveUser(a_dbTransaction, user);
/*     */ 
/* 152 */         userProfile.setUser(user);
/*     */ 
/* 155 */         if (AssetBankSettings.ecommerce())
/*     */         {
/* 157 */           this.m_assetBoxManager.refreshAssetBoxTax(a_dbTransaction, userProfile);
/*     */         }
/*     */ 
/* 161 */         ArrayList marketingGroupIds = new ArrayList();
/* 162 */         Collections.addAll(marketingGroupIds, (Object[])form.getMarketingGroupIds());
/* 163 */         this.m_marketingGroupManager.setMarketingGroupIdsForUser(a_dbTransaction, user.getId(), marketingGroupIds);
/*     */       }
/*     */       catch (UsernameInUseException uiue)
/*     */       {
/* 168 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationUsernameExists", userProfile.getCurrentLanguage()).getBody());
/*     */ 
/* 170 */         bValid = false;
/*     */       }
/*     */     }
/*     */ 
/* 174 */     if (!bValid)
/*     */     {
/* 176 */       a_request.setAttribute("user", user);
/* 177 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 181 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 184 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 189 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupManager(MarketingGroupManager marketingGroupManager)
/*     */   {
/* 194 */     this.m_marketingGroupManager = marketingGroupManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 199 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 205 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*     */   {
/* 210 */     this.m_fieldManager = a_fieldManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ChangeProfileAction
 * JD-Core Version:    0.6.0
 */