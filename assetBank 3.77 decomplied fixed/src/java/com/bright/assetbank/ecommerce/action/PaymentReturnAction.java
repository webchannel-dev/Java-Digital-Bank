/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceConstants;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPlugin;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPluginFactory;
/*     */ import com.bright.assetbank.ecommerce.service.EcommerceManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.action.LoginAction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class PaymentReturnAction extends LoginAction
/*     */   implements EcommerceConstants
/*     */ {
/*  54 */   private EcommerceManager m_ecommerceManager = null;
/*     */ 
/*  59 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*     */   public void setEcommerceManager(EcommerceManager a_ecommerceManager)
/*     */   {
/*  56 */     this.m_ecommerceManager = a_ecommerceManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_assetBoxManager)
/*     */   {
/*  61 */     this.m_assetBoxManager = a_assetBoxManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  84 */     ActionForward afForward = null;
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  86 */     LoginForm form = (LoginForm)a_form;
/*     */ 
/*  89 */     String sPSPPluginClassname = EcommerceSettings.getPspPluginClass();
/*  90 */     PSPPlugin pspPlugin = PSPPluginFactory.getPSPPluginInstance(sPSPPluginClassname);
/*  91 */     String sKey = pspPlugin.getKeyFromReturnRequest(a_request);
/*     */ 
/*  93 */     this.m_logger.debug("PaymentReturnAction: Trying to get purchase with key=" + sKey);
/*     */ 
/*  95 */     if (!this.m_ecommerceManager.containsPurchase(sKey))
/*     */     {
/*  99 */       afForward = a_mapping.findForward("ValidationFailure");
/* 100 */       return afForward;
/*     */     }
/* 102 */     Purchase purchase = this.m_ecommerceManager.getPurchase(sKey);
/*     */ 
/* 104 */     if (pspPlugin.registerSuccessOnReturn())
/*     */     {
/* 106 */       if (pspPlugin.processReturn(a_mapping, a_form, a_request, a_response, a_dbTransaction))
/*     */       {
/* 108 */         purchase.registerPaymentSuccess(a_dbTransaction, userProfile.getCurrentLanguage());
/*     */       }
/*     */       else
/*     */       {
/* 112 */         afForward = a_mapping.findForward("NotAuthorisedFailure");
/* 113 */         return afForward;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 121 */       afForward = doLogin(a_dbTransaction, a_mapping, form, a_request, a_response, purchase.getLoginUser(), purchase.getLoginPassword(), false);
/*     */     }
/*     */     else
/*     */     {
/* 133 */       this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/* 134 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 138 */     if (purchase.getClass().getName().equals("com.bright.assetbank.subscription.bean.SubscriptionPurchase"))
/*     */     {
/* 140 */       afForward = a_mapping.findForward("Subscription");
/*     */     }
/*     */ 
/* 143 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.PaymentReturnAction
 * JD-Core Version:    0.6.0
 */