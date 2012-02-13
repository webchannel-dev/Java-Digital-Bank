/*     */ package com.bright.assetbank.ecommerce.psp;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.constant.TwoCheckoutSettings;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class TwoCheckoutPlugin extends BasePspPlugin
/*     */   implements PSPPlugin
/*     */ {
/*  45 */   private TwoCheckoutSettings m_settings = new TwoCheckoutSettings("TwoCheckoutSettings");
/*     */ 
/*     */   public HashMap<String, String> createPurchaseForm(Purchase a_purchase, String a_sKey)
/*     */   {
/*  57 */     HashMap hmForm = new HashMap();
/*     */ 
/*  60 */     hmForm.put(this.m_settings.get2CheckoutIdKey(), this.m_settings.get2CheckoutId());
/*     */ 
/*  63 */     hmForm.put(this.m_settings.get2CheckoutDemoKey(), this.m_settings.get2CheckoutDemo());
/*     */ 
/*  66 */     hmForm.put(this.m_settings.get2CheckoutLanguageKey(), this.m_settings.get2CheckoutLanguage());
/*     */ 
/*  69 */     hmForm.put(this.m_settings.get2CheckoutPayMethodKey(), this.m_settings.get2CheckoutPayMethod());
/*     */ 
/*  72 */     hmForm.put(this.m_settings.get2CheckoutCartIdKey(), a_sKey);
/*     */ 
/*  75 */     hmForm.put(this.m_settings.get2CheckoutTotalKey(), a_purchase.getChargedAmount().getFormAmountWithoutSeperator());
/*     */ 
/*  78 */     hmForm.put(this.m_settings.get2CheckoutCallbackKey(), EcommerceSettings.getPspCallbackUrl());
/*     */ 
/*  80 */     return hmForm;
/*     */   }
/*     */ 
/*     */   public PspPaymentReturn processCallback(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 100 */     boolean bValid = false;
/*     */ 
/* 103 */     String sAmount = a_request.getParameter(this.m_settings.get2CheckoutTotalKey());
/* 104 */     String sTransactionId = a_request.getParameter(this.m_settings.get2CheckoutCartIdKey());
/* 105 */     String sKey = a_request.getParameter(this.m_settings.get2CheckoutKey());
/* 106 */     String sOrderNumber = a_request.getParameter(this.m_settings.get2CheckoutOrderNumber());
/*     */ 
/* 108 */     GlobalApplication.getInstance().getLogger().debug("Processing transaction for amount: " + sAmount + " Trasaction ID: " + sTransactionId + " Key: " + sKey + " Order no: " + sOrderNumber);
/*     */ 
/* 117 */     String sToHash = this.m_settings.get2CheckoutSecretWord() + this.m_settings.get2CheckoutId() + sOrderNumber + sAmount;
/*     */ 
/* 123 */     String sExpectedResult = StringUtil.hexDigestMD5(sToHash);
/*     */ 
/* 125 */     GlobalApplication.getInstance().getLogger().debug("Expected response: " + sExpectedResult);
/*     */ 
/* 128 */     if (sExpectedResult.equalsIgnoreCase(sKey))
/*     */     {
/* 131 */       bValid = true;
/*     */     }
/*     */ 
/* 134 */     PspPaymentReturn info = new PspPaymentReturn();
/* 135 */     info.setValid(bValid);
/*     */ 
/* 137 */     if (bValid)
/*     */     {
/* 139 */       info.setTransactionId(sTransactionId);
/* 140 */       info.getChargedAmount().setFormAmount(sAmount);
/* 141 */       info.getChargedAmount().processFormData();
/* 142 */       info.setPspTransactionId(sOrderNumber);
/*     */     }
/*     */     else
/*     */     {
/* 146 */       GlobalApplication.getInstance().getLogger().error("Payment validation failed");
/*     */     }
/*     */ 
/* 149 */     return info;
/*     */   }
/*     */ 
/*     */   public String getPSPUrl(Purchase a_purchase, String a_sKey)
/*     */   {
/* 158 */     String sUrl = this.m_settings.get2CheckoutGateway();
/* 159 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public String createSignatureOut(Purchase a_purchase, String a_sKey)
/*     */   {
/* 167 */     String sSignature = null;
/* 168 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public String createSignatureCallback(Purchase a_purchase, String a_sKey, HttpServletRequest a_request)
/*     */   {
/* 176 */     String sSignature = null;
/* 177 */     return sSignature;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.TwoCheckoutPlugin
 * JD-Core Version:    0.6.0
 */