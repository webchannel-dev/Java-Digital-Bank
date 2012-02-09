/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceConstants;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPlugin;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPluginFactory;
/*     */ import com.bright.assetbank.ecommerce.service.EcommerceManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.exception.UsernameInUseException;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class PaymentCallbackAction extends BTransactionAction
/*     */   implements EcommerceConstants
/*     */ {
/*  55 */   private EcommerceManager m_ecommerceManager = null;
/*     */ 
/*     */   public void setEcommerceManager(EcommerceManager a_ecommerceManager) {
/*  58 */     this.m_ecommerceManager = a_ecommerceManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  81 */     ActionForward afForward = null;
/*  82 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  84 */     this.m_logger.debug("PaymentCallbackAction: processing callback");
/*     */ 
/*  87 */     String sPSPPluginClassname = EcommerceSettings.getPspPluginClass();
/*  88 */     PSPPlugin pspPlugin = PSPPluginFactory.getPSPPluginInstance(sPSPPluginClassname);
/*  89 */     PspPaymentReturn info = pspPlugin.processCallback(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*     */ 
/*  92 */     String sKey = info.getTransactionId();
/*     */ 
/*  95 */     if (info.getCancelled())
/*     */     {
/*  97 */       this.m_logger.error("PaymentCallbackAction: Cancel response from PSP: " + sKey);
/*  98 */       if (pspPlugin.requiresRedirectFromCallback())
/*     */       {
/* 100 */         return new ActionForward(pspPlugin.generateFailureRedirect(info), true);
/*     */       }
/*     */ 
/* 103 */       return createForward("", a_mapping, "PSPCancelResponse");
/*     */     }
/*     */ 
/* 107 */     if (!info.getValid())
/*     */     {
/* 109 */       this.m_logger.error("PaymentCallbackAction: Invalid payment response from PSP: " + sKey);
/* 110 */       if (pspPlugin.requiresRedirectFromCallback())
/*     */       {
/* 112 */         return new ActionForward(pspPlugin.generateFailureRedirect(info), true);
/*     */       }
/*     */ 
/* 115 */       return createForward("", a_mapping, "PSPInvalidResponse");
/*     */     }
/*     */ 
/* 120 */     if (!this.m_ecommerceManager.containsPurchase(sKey))
/*     */     {
/* 122 */       this.m_logger.error("PaymentCallbackAction: Cannot match purchase to given key: " + sKey);
/* 123 */       if (pspPlugin.requiresRedirectFromCallback())
/*     */       {
/* 125 */         return new ActionForward(pspPlugin.generateFailureRedirect(info), true);
/*     */       }
/*     */ 
/* 128 */       return createForward("", a_mapping, "Failure");
/*     */     }
/*     */ 
/* 132 */     Purchase purchase = this.m_ecommerceManager.getPurchase(sKey);
/* 133 */     purchase.setHasPaid(true);
/* 134 */     purchase.setPaymentReturn(info);
/*     */ 
/* 137 */     if (purchase.getChargedAmount().getAmount() != purchase.getPaymentReturn().getChargedAmount().getAmount())
/*     */     {
/* 139 */       this.m_logger.error("PaymentCallbackAction: Payment amounts do not match: " + purchase.getChargedAmount().getAmount() + " vs " + purchase.getPaymentReturn().getChargedAmount().getAmount());
/* 140 */       if (pspPlugin.requiresRedirectFromCallback())
/*     */       {
/* 142 */         return new ActionForward(pspPlugin.generateFailureRedirect(info), true);
/*     */       }
/*     */ 
/* 145 */       return createForward("", a_mapping, "PSPAmountCheckFailure");
/*     */     }
/*     */ 
/* 150 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/* 152 */       String sSignature = pspPlugin.createSignatureCallback(purchase, sKey, a_request);
/*     */ 
/* 155 */       String sReturnedSignature = purchase.getPaymentReturn().getSignature();
/* 156 */       if ((!StringUtil.stringIsPopulated(sReturnedSignature)) || (sReturnedSignature.compareToIgnoreCase(sSignature) != 0))
/*     */       {
/* 158 */         this.m_logger.error("PaymentCallbackAction: Payment signatures do not match: " + sSignature + " vs " + sReturnedSignature);
/* 159 */         if (pspPlugin.requiresRedirectFromCallback())
/*     */         {
/* 161 */           return new ActionForward(pspPlugin.generateFailureRedirect(info), true);
/*     */         }
/*     */ 
/* 164 */         return createForward("", a_mapping, "PSPAmountCheckFailure");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 170 */     String sQueryString = "trans_id=" + sKey + "&" + "transId" + "=" + info.getPspTransactionId();
/*     */ 
/* 173 */     this.m_logger.debug("PaymentCallbackAction: successful order, id=" + sKey);
/*     */ 
/* 175 */     if (pspPlugin.registerSuccessOnCallback())
/*     */     {
/*     */       try
/*     */       {
/* 180 */         purchase.registerPaymentSuccess(a_dbTransaction, userProfile.getCurrentLanguage());
/* 181 */         afForward = createForward(sQueryString, a_mapping, "Success");
/*     */       }
/*     */       catch (UsernameInUseException userException)
/*     */       {
/* 185 */         this.m_logger.error("PaymentCallbackAction: Cannot create a valid account for the registering user: " + purchase.getEmailAddress());
/* 186 */         afForward = createForward("", a_mapping, "Failure");
/*     */       }
/*     */     }
/*     */ 
/* 190 */     if (pspPlugin.requiresRedirectFromCallback())
/*     */     {
/* 192 */       return new ActionForward(pspPlugin.generateSuccessRedirect(info), true);
/*     */     }
/*     */ 
/* 196 */     return afForward;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 202 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.PaymentCallbackAction
 * JD-Core Version:    0.6.0
 */