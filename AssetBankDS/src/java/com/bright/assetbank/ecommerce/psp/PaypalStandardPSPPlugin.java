/*     */ package com.bright.assetbank.ecommerce.psp;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceConstants;
/*     */ import com.bright.assetbank.ecommerce.constant.PaypalStandardPSPSettings;
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
/*     */ public class PaypalStandardPSPPlugin extends BasePspPlugin
/*     */   implements PSPPlugin, EcommerceConstants
/*     */ {
/*  45 */   private PaypalStandardPSPSettings m_settings = new PaypalStandardPSPSettings("PaypalStandardPSPSettings");
/*     */ 
/*     */   public HashMap<String, String> createPurchaseForm(Purchase a_purchase, String a_sKey)
/*     */   {
/*  59 */     HashMap hmForm = new HashMap();
/*     */ 
/*  61 */     hmForm.put(this.m_settings.getPaypalAccountKey(), this.m_settings.getPaypalAccount());
/*  62 */     hmForm.put(this.m_settings.getPaypalItemNameKey(), a_purchase.getDescription());
/*  63 */     hmForm.put(this.m_settings.getPaypalCurrencyCodeKey(), this.m_settings.getPaypalCurrencyCode());
/*  64 */     hmForm.put(this.m_settings.getPaypalItemNumberKey(), a_sKey);
/*  65 */     hmForm.put(this.m_settings.getPaypalAmountKey(), a_purchase.getChargedAmount().getFormAmount());
/*  66 */     hmForm.put(this.m_settings.getPaypalReturnUrlKey(), this.m_settings.getPaypalReturnUrl() + "?" + this.m_settings.getPaypalItemNumberKey() + "=" + a_sKey);
/*  67 */     hmForm.put(this.m_settings.getPaypalCancelReturnUrlKey(), this.m_settings.getPaypalCancelReturnUrl());
/*  68 */     hmForm.put(this.m_settings.getPaypalCommandKey(), this.m_settings.getPaypalCommand());
/*     */ 
/*  70 */     return hmForm;
/*     */   }
/*     */ 
/*     */   public String createSignatureOut(Purchase a_purchase, String a_sKey)
/*     */   {
/*  85 */     String sSignature = null;
/*  86 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public String createSignatureCallback(Purchase a_purchase, String a_sKey, HttpServletRequest a_request)
/*     */   {
/*  92 */     String sSignature = null;
/*  93 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public String getKeyFromReturnRequest(HttpServletRequest a_request)
/*     */   {
/*  99 */     return a_request.getParameter(this.m_settings.getPaypalItemNumberKey());
/*     */   }
/*     */ 
/*     */   public String getPSPUrl(Purchase a_purchase, String a_sKey)
/*     */   {
/* 111 */     String sUrl = this.m_settings.getPaypalUrl();
/* 112 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public PspPaymentReturn processCallback(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 136 */     PspPaymentReturn info = new PspPaymentReturn();
/*     */ 
/* 139 */     String sItemNo = a_request.getParameter(this.m_settings.getPaypalItemNumberKey());
/* 140 */     boolean bValid = false;
/*     */ 
/* 142 */     if (StringUtil.stringIsPopulated(sItemNo))
/*     */     {
/* 145 */       String sPaymentStatus = a_request.getParameter(this.m_settings.getPaypalPaymentStatusKey());
/*     */ 
/* 147 */       if (StringUtil.stringIsPopulated(sPaymentStatus))
/*     */       {
/* 149 */         String sSharedSecret = a_request.getParameter(this.m_settings.getSharedSecretParameter());
/* 150 */         String sStoredSharedSecret = this.m_settings.getSharedSecret();
/*     */ 
/* 152 */         if (sSharedSecret.equals(sStoredSharedSecret))
/*     */         {
/* 154 */           if (this.m_settings.getPaypalPaymentStatusSuccess().indexOf(sPaymentStatus) >= 0)
/*     */           {
/* 157 */             bValid = true;
/*     */           }
/*     */ 
/* 161 */           if (bValid)
/*     */           {
/* 164 */             String sAmount = a_request.getParameter(this.m_settings.getPaypalReturnAmountKey());
/* 165 */             String sPspTransId = a_request.getParameter(this.m_settings.getPaypalTransactionIdKey());
/*     */ 
/* 167 */             GlobalApplication.getInstance().getLogger().debug("Paypal callback amount = " + sAmount);
/* 168 */             GlobalApplication.getInstance().getLogger().debug("Paypal callback transaction id = " + sPspTransId);
/*     */ 
/* 170 */             info.setTransactionId(sItemNo);
/* 171 */             info.getChargedAmount().setFormAmount(sAmount);
/* 172 */             info.getChargedAmount().processFormData();
/* 173 */             info.setPspTransactionId(sPspTransId);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 179 */     info.setValid(bValid);
/* 180 */     info.setCancelled(false);
/*     */ 
/* 182 */     return info;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.PaypalStandardPSPPlugin
 * JD-Core Version:    0.6.0
 */