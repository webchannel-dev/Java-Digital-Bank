/*     */ package com.bright.assetbank.ecommerce.psp;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.constant.WorldpaySettings;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class WorldpayPlugin extends BasePspPlugin
/*     */   implements PSPPlugin
/*     */ {
/*  45 */   private WorldpaySettings m_settings = new WorldpaySettings("WorldpaySettings");
/*     */ 
/*     */   public String createSignatureOut(Purchase a_purchase, String a_sKey)
/*     */   {
/*  64 */     String sSignature = null;
/*     */ 
/*  66 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/*  68 */       String sSource = EcommerceSettings.getPspSecret() + ":" + a_purchase.getChargedAmount().getFormAmount() + ":" + a_sKey;
/*  69 */       sSignature = StringUtil.hexDigest(sSource);
/*     */     }
/*     */ 
/*  72 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public String createSignatureCallback(Purchase a_purchase, String a_sKey, HttpServletRequest a_request)
/*     */   {
/*  83 */     String sSignature = null;
/*     */ 
/*  85 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/*  87 */       String sSource = EcommerceSettings.getPspSecret() + ":" + a_purchase.getChargedAmount().getFormAmount() + ":" + a_sKey;
/*  88 */       sSignature = StringUtil.hexDigest(sSource);
/*     */     }
/*     */ 
/*  91 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public HashMap<String, String> createPurchaseForm(Purchase a_purchase, String a_sKey)
/*     */   {
/* 106 */     HashMap hmForm = new HashMap();
/*     */ 
/* 108 */     hmForm.put(this.m_settings.getWorldpayMerchantKey(), this.m_settings.getWorldpayMerchantValue());
/* 109 */     hmForm.put(this.m_settings.getWorldpayCurrencyKey(), this.m_settings.getWorldpayCurrencyValue());
/* 110 */     hmForm.put(this.m_settings.getWorldpayTestModeKey(), this.m_settings.getWorldpayTestModeValue());
/* 111 */     hmForm.put(this.m_settings.getWorldpayTransidKey(), a_sKey);
/* 112 */     hmForm.put(this.m_settings.getWorldpayAmountKey(), a_purchase.getChargedAmount().getFormAmountWithoutSeperator());
/* 113 */     hmForm.put(this.m_settings.getWorldpayDescKey(), a_purchase.getDescription());
/*     */ 
/* 117 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/* 119 */       String sSignature = createSignatureOut(a_purchase, a_sKey);
/* 120 */       hmForm.put(this.m_settings.getWorldpaySignatureKey(), sSignature);
/* 121 */       hmForm.put(this.m_settings.getWorldpayPassSignatureKey(), sSignature);
/*     */ 
/* 124 */       hmForm.put(this.m_settings.getWorldpaySignatureFieldsKey(), this.m_settings.getWorldpaySignatureFields());
/*     */     }
/*     */ 
/* 127 */     return hmForm;
/*     */   }
/*     */ 
/*     */   public String getPSPUrl(Purchase a_purchase, String a_sKey)
/*     */   {
/* 139 */     String sUrl = this.m_settings.getWorldpayGateway();
/* 140 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public PspPaymentReturn processCallback(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 170 */     PspPaymentReturn info = new PspPaymentReturn();
/*     */ 
/* 173 */     String sTransidKey = this.m_settings.getWorldpayCallbackTransidKey();
/* 174 */     String sAmountKey = this.m_settings.getWorldpayCallbackAmountKey();
/* 175 */     String sValidKey = this.m_settings.getWorldpayCallbackValidKey();
/* 176 */     String sValidTrue = this.m_settings.getWorldpayCallbackValidTrue();
/* 177 */     String sWorldpayTransIdKey = this.m_settings.getWorldpayCallbackWorldpayTransidKey();
/* 178 */     String sCancelled = this.m_settings.getWorldpayCallbackValidCancel();
/* 179 */     String sSignatureKey = this.m_settings.getWorldpayPassSignatureKey();
/*     */ 
/* 182 */     String sAmount = a_request.getParameter(sAmountKey);
/* 183 */     String sValid = a_request.getParameter(sValidKey);
/* 184 */     String sTransactionId = a_request.getParameter(sTransidKey);
/* 185 */     String sWorldpayTransId = a_request.getParameter(sWorldpayTransIdKey);
/* 186 */     String sSignature = a_request.getParameter(sSignatureKey);
/*     */ 
/* 189 */     boolean bValid = (sValid != null) && (sValid.compareToIgnoreCase(sValidTrue) == 0);
/*     */ 
/* 192 */     boolean bCancelled = (sValid == null) || (sValid.compareToIgnoreCase(sCancelled) == 0);
/*     */ 
/* 194 */     info.setValid(bValid);
/* 195 */     info.setCancelled(bCancelled);
/*     */ 
/* 198 */     if (bValid)
/*     */     {
/* 200 */       info.setTransactionId(sTransactionId);
/* 201 */       info.getChargedAmount().setFormAmount(sAmount);
/* 202 */       info.getChargedAmount().processFormData();
/* 203 */       info.setPspTransactionId(sWorldpayTransId);
/* 204 */       info.setSignature(sSignature);
/*     */     }
/*     */ 
/* 207 */     return info;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.WorldpayPlugin
 * JD-Core Version:    0.6.0
 */