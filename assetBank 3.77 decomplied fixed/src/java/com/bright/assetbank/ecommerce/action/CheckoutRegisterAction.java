/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.ecommerce.form.CheckoutForm;
/*     */ import com.bright.assetbank.user.action.UserAction;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CheckoutRegisterAction extends UserAction
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  48 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*  54 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/*  51 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  57 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     ActionForward afForward = null;
/*  68 */     CheckoutForm form = (CheckoutForm)a_form;
/*  69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  72 */     form.resetErrors();
/*  73 */     validateMandatoryFields(form, a_request);
/*  74 */     validateFieldLengths(form, a_request);
/*     */ 
/*  77 */     ABUser user = form.getUser();
/*     */ 
/*  80 */     user.setUsername(form.getUser().getEmailAddress());
/*     */ 
/*  83 */     long lExistingUserId = getUserManager().getUserIdForLocalUsername(a_dbTransaction, user.getUsername());
/*     */ 
/*  85 */     if (lExistingUserId > 0L)
/*     */     {
/*  87 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDuplicateUsername", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  90 */     if (form.getHasErrors())
/*     */     {
/*  92 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/*  99 */       this.m_assetBoxManager.refreshAssetBoxTaxForUser(a_dbTransaction, userProfile, user);
/*     */ 
/* 102 */       form.setIsProfileEntered(true);
/*     */ 
/* 104 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 107 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.CheckoutRegisterAction
 * JD-Core Version:    0.6.0
 */