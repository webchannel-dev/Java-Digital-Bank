/*     */ package com.bright.assetbank.ecommerce.psp;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.BankpassSettings;
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
/*     */ public class BankpassPlugin extends BasePspPlugin
/*     */   implements PSPPlugin
/*     */ {
/*  45 */   private BankpassSettings m_settings = new BankpassSettings("SSBBankpassSettings");
/*     */ 
/*     */   public String createSignatureOut(Purchase a_purchase, String a_sKey)
/*     */   {
/*  64 */     String sSignature = null;
/*     */ 
/*  66 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/*  68 */       String sSource = this.m_settings.getOrderidKey() + "=" + getOrderIdFromKeyId(a_sKey) + "&" + this.m_settings.getMerchantKey() + "=" + this.m_settings.getMerchantValue() + "&" + this.m_settings.getPriceKey() + "=" + getPrice(a_purchase) + "&" + this.m_settings.getCurrencyKey() + "=" + this.m_settings.getCurrencyValue() + "&" + this.m_settings.getTypecalcKey() + "=" + this.m_settings.getTypecalcValue() + "&" + this.m_settings.getTypeauthKey() + "=" + this.m_settings.getTypeauthValue() + "&" + this.m_settings.getOptionsKey() + "=" + this.m_settings.getOptionsValue() + "&" + EcommerceSettings.getPspSecret();
/*     */ 
/*  77 */       sSignature = StringUtil.hexDigest(sSource);
/*     */     }
/*     */ 
/*  80 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public String createSignatureCallback(Purchase a_purchase, String a_sKey, HttpServletRequest a_request)
/*     */   {
/* 100 */     String sSignature = null;
/*     */ 
/* 102 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecretCallback()))
/*     */     {
/* 104 */       String sSource = this.m_settings.getOrderidKey() + "=" + getOrderIdFromKeyId(a_sKey) + "&" + this.m_settings.getMerchantKey() + "=" + this.m_settings.getMerchantValue() + "&" + this.m_settings.getCallbackAuthnumberKey() + "=" + a_purchase.getPaymentReturn().getAuthNumber() + "&" + this.m_settings.getPriceKey() + "=" + getPrice(a_purchase) + "&" + this.m_settings.getCurrencyKey() + "=" + this.m_settings.getCurrencyValue() + "&" + this.m_settings.getCallbackIdtransKey() + "=" + a_purchase.getPaymentReturn().getPspTransactionId() + "&" + this.m_settings.getTypecalcKey() + "=" + this.m_settings.getTypecalcValue() + "&" + this.m_settings.getTypeauthKey() + "=" + this.m_settings.getTypeauthValue() + "&" + this.m_settings.getCallbackResultKey() + "=" + "00" + "&" + EcommerceSettings.getPspSecretCallback();
/*     */ 
/* 115 */       sSignature = StringUtil.hexDigest(sSource);
/*     */     }
/*     */ 
/* 118 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public HashMap<String, String> createPurchaseForm(Purchase a_purchase, String a_sKey)
/*     */   {
/* 138 */     HashMap hmForm = new HashMap();
/*     */ 
/* 140 */     hmForm.put("PAGE", "MASTER");
/* 141 */     hmForm.put(this.m_settings.getUrlbackKey(), this.m_settings.getUrlbackValue());
/* 142 */     hmForm.put(this.m_settings.getUrldoneKey(), this.m_settings.getUrldoneValue());
/* 143 */     hmForm.put(this.m_settings.getUrlmsKey(), this.m_settings.getUrlmsValue());
/* 144 */     hmForm.put(this.m_settings.getCurrencyKey(), this.m_settings.getCurrencyValue());
/* 145 */     hmForm.put(this.m_settings.getMerchantKey(), this.m_settings.getMerchantValue());
/* 146 */     hmForm.put(this.m_settings.getTypecalcKey(), this.m_settings.getTypecalcValue());
/* 147 */     hmForm.put(this.m_settings.getTypeauthKey(), this.m_settings.getTypeauthValue());
/* 148 */     hmForm.put(this.m_settings.getLanguageKey(), this.m_settings.getLanguageValue());
/* 149 */     hmForm.put(this.m_settings.getOptionsKey(), this.m_settings.getOptionsValue());
/*     */ 
/* 152 */     hmForm.put(this.m_settings.getEmailresultKey(), this.m_settings.getEmailresultValue());
/*     */ 
/* 155 */     hmForm.put(this.m_settings.getEmailKey(), a_purchase.getEmailAddress());
/*     */ 
/* 158 */     hmForm.put(this.m_settings.getPriceKey(), getPrice(a_purchase));
/*     */ 
/* 161 */     String sOrderId = getOrderIdFromKeyId(a_sKey);
/* 162 */     hmForm.put(this.m_settings.getOrderidKey(), sOrderId);
/*     */ 
/* 166 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/* 168 */       String sSignature = createSignatureOut(a_purchase, a_sKey);
/* 169 */       hmForm.put(this.m_settings.getSignatureKey(), sSignature);
/*     */     }
/*     */ 
/* 173 */     return hmForm;
/*     */   }
/*     */ 
/*     */   public String getPSPUrl(Purchase a_purchase, String a_sKey)
/*     */   {
/* 186 */     String sUrl = this.m_settings.getBankpassGateway();
/* 187 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public PspPaymentReturn processCallback(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 216 */     PspPaymentReturn info = new PspPaymentReturn();
/*     */ 
/* 219 */     String sOrderidKey = this.m_settings.getOrderidKey();
/* 220 */     String sAmountKey = this.m_settings.getPriceKey();
/* 221 */     String sValidKey = this.m_settings.getCallbackResultKey();
/* 222 */     String sBankpassTransIdKey = this.m_settings.getCallbackIdtransKey();
/* 223 */     String sSignatureKey = this.m_settings.getSignatureKey();
/* 224 */     String sAuthNumberKey = this.m_settings.getCallbackAuthnumberKey();
/*     */ 
/* 227 */     String sAmount = a_request.getParameter(sAmountKey);
/* 228 */     String sValid = a_request.getParameter(sValidKey);
/* 229 */     String sOrderId = a_request.getParameter(sOrderidKey);
/* 230 */     String sBankpassTransId = a_request.getParameter(sBankpassTransIdKey);
/* 231 */     String sSignature = a_request.getParameter(sSignatureKey);
/* 232 */     String sAuthNumber = a_request.getParameter(sAuthNumberKey);
/*     */ 
/* 235 */     String sTransactionId = getKeyIdFromOrderId(sOrderId);
/*     */ 
/* 238 */     boolean bValid = (sValid != null) && (sValid.compareToIgnoreCase("00") == 0);
/* 239 */     info.setValid(bValid);
/*     */ 
/* 242 */     info.setCancelled(false);
/*     */ 
/* 245 */     if (bValid)
/*     */     {
/* 247 */       info.setTransactionId(sTransactionId);
/* 248 */       info.setPspTransactionId(sBankpassTransId);
/* 249 */       info.setSignature(sSignature);
/* 250 */       info.setAuthNumber(sAuthNumber);
/*     */ 
/* 253 */       long lAmount = new Long(sAmount).longValue();
/* 254 */       info.getChargedAmount().setAmount(lAmount);
/*     */     }
/*     */ 
/* 257 */     return info;
/*     */   }
/*     */ 
/*     */   public String getKeyFromReturnRequest(HttpServletRequest a_request)
/*     */   {
/* 277 */     String sOrderidKey = this.m_settings.getOrderidKey();
/* 278 */     String sOrderId = a_request.getParameter(sOrderidKey);
/* 279 */     String sKey = getKeyIdFromOrderId(sOrderId);
/*     */ 
/* 281 */     return sKey;
/*     */   }
/*     */ 
/*     */   private String getOrderIdFromKeyId(String a_sKey)
/*     */   {
/* 295 */     String sOrderId = this.m_settings.getOrderidStem() + a_sKey;
/* 296 */     return sOrderId;
/*     */   }
/*     */ 
/*     */   private String getKeyIdFromOrderId(String a_sOrderId)
/*     */   {
/* 307 */     String sKeyId = a_sOrderId.replace(this.m_settings.getOrderidStem(), "");
/* 308 */     return sKeyId;
/*     */   }
/*     */ 
/*     */   private String getPrice(Purchase a_purchase)
/*     */   {
/* 313 */     String sPrice = Long.toString(a_purchase.getChargedAmount().getAmount());
/* 314 */     return sPrice;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.BankpassPlugin
 * JD-Core Version:    0.6.0
 */