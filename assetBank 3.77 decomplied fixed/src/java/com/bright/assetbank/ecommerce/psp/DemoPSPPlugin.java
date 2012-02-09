/*     */ package com.bright.assetbank.ecommerce.psp;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.DemoPSPSettings;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DemoPSPPlugin extends BasePspPlugin
/*     */   implements PSPPlugin
/*     */ {
/*  45 */   private DemoPSPSettings m_settings = new DemoPSPSettings("DemoPSPSettings");
/*     */ 
/*     */   public HashMap<String, String> createPurchaseForm(Purchase a_purchase, String a_sKey)
/*     */   {
/*  59 */     HashMap hmForm = new HashMap();
/*     */ 
/*  61 */     hmForm.put(this.m_settings.getPspDemoCallbackKey(), EcommerceSettings.getPspCallbackUrl());
/*  62 */     hmForm.put(this.m_settings.getPspDemoMerchantKey(), this.m_settings.getPspDemoMerchantValue());
/*  63 */     hmForm.put(this.m_settings.getPspDemoTransidKey(), a_sKey);
/*  64 */     hmForm.put(this.m_settings.getPspDemoAmountKey(), a_purchase.getChargedAmount().getFormAmount());
/*  65 */     hmForm.put(this.m_settings.getPspDemoDescKey(), a_purchase.getDescription());
/*     */ 
/*  69 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/*  71 */       String sSignature = createSignatureOut(a_purchase, a_sKey);
/*  72 */       hmForm.put(this.m_settings.getPspDemoSignatureKey(), sSignature);
/*  73 */       hmForm.put(this.m_settings.getPspDemoPassSignatureKey(), sSignature);
/*     */     }
/*     */ 
/*  76 */     return hmForm;
/*     */   }
/*     */ 
/*     */   public String createSignatureOut(Purchase a_purchase, String a_sKey)
/*     */   {
/*  96 */     String sSignature = null;
/*     */ 
/*  98 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/* 100 */       String sSource = EcommerceSettings.getPspSecret() + ":" + a_purchase.getChargedAmount().getFormAmount() + ":" + a_sKey;
/* 101 */       sSignature = StringUtil.hexDigest(sSource);
/*     */     }
/*     */ 
/* 104 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public String createSignatureCallback(Purchase a_purchase, String a_sKey, HttpServletRequest a_request)
/*     */   {
/* 115 */     String sSignature = null;
/*     */ 
/* 117 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/* 119 */       String sSource = EcommerceSettings.getPspSecret() + ":" + a_purchase.getChargedAmount().getFormAmount() + ":" + a_sKey;
/* 120 */       sSignature = StringUtil.hexDigest(sSource);
/*     */     }
/*     */ 
/* 123 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public String getPSPUrl(Purchase a_purchase, String a_sKey)
/*     */   {
/* 135 */     String sUrl = this.m_settings.getPspDemoGateway();
/* 136 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public PspPaymentReturn processCallback(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 165 */     PspPaymentReturn info = new PspPaymentReturn();
/*     */ 
/* 168 */     String sTransidKey = this.m_settings.getPspDemoCallbackTransidKey();
/* 169 */     String sAmountKey = this.m_settings.getPspDemoCallbackAmountKey();
/* 170 */     String sValidKey = this.m_settings.getPspDemoCallbackValidKey();
/* 171 */     String sValidTrue = this.m_settings.getPspDemoCallbackValidTrue();
/* 172 */     String sCancelled = this.m_settings.getPspDemoCallbackValidCancel();
/* 173 */     String sSignatureKey = this.m_settings.getPspDemoPassSignatureKey();
/* 174 */     String sPspTransIdKey = this.m_settings.getPspDemoCallbackPspTransidKey();
/*     */ 
/* 177 */     String sAmount = a_request.getParameter(sAmountKey);
/* 178 */     String sValid = a_request.getParameter(sValidKey);
/* 179 */     String sTransactionId = a_request.getParameter(sTransidKey);
/* 180 */     String sSignature = a_request.getParameter(sSignatureKey);
/* 181 */     String sPspTransId = a_request.getParameter(sPspTransIdKey);
/*     */ 
/* 184 */     boolean bValid = (sValid == null) || (sValid.compareToIgnoreCase(sValidTrue) == 0);
/*     */ 
/* 187 */     boolean bCancelled = (sValid == null) || (sValid.compareToIgnoreCase(sCancelled) == 0);
/*     */ 
/* 189 */     info.setValid(bValid);
/* 190 */     info.setCancelled(bCancelled);
/*     */ 
/* 193 */     if (bValid)
/*     */     {
/* 195 */       info.setTransactionId(sTransactionId);
/* 196 */       info.getChargedAmount().setFormAmount(sAmount);
/* 197 */       info.getChargedAmount().processFormData();
/* 198 */       info.setSignature(sSignature);
/* 199 */       info.setPspTransactionId(sPspTransId);
/*     */     }
/*     */ 
/* 202 */     return info;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.DemoPSPPlugin
 * JD-Core Version:    0.6.0
 */