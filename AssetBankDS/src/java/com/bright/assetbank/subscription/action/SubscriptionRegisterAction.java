/*     */ package com.bright.assetbank.subscription.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.subscription.form.SubscriptionForm;
/*     */ import com.bright.assetbank.user.action.UserAction;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.util.PasswordUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SubscriptionRegisterAction extends UserAction
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/* 118 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*  72 */     SubscriptionForm form = (SubscriptionForm)a_form;
/*     */ 
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     form.resetErrors();
/*  78 */     form.resetSubscription();
/*  79 */     validateMandatoryFields(form, a_request);
/*  80 */     validateFieldLengths(form, a_request);
/*     */ 
/*  83 */     ABUser user = form.getUser();
/*     */ 
/*  86 */     if (AssetBankSettings.getUsernameIsEmailAddress())
/*     */     {
/*  88 */       user.setUsername(form.getUser().getEmailAddress());
/*     */     }
/*     */ 
/*  92 */     long lExistingUserId = getUserManager().getUserIdForLocalUsername(a_dbTransaction, user.getUsername());
/*     */ 
/*  94 */     if (lExistingUserId > 0L)
/*     */     {
/*  96 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDuplicateUsername", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 100 */     if (form.getHasErrors())
/*     */     {
/* 102 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 109 */       String sPassword = PasswordUtil.generateRandomPassword();
/* 110 */       user.setPassword(sPassword);
/*     */ 
/* 112 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 115 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 121 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.action.SubscriptionRegisterAction
 * JD-Core Version:    0.6.0
 */