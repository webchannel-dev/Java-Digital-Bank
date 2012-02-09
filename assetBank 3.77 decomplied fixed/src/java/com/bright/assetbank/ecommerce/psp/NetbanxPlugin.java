/*     */ package com.bright.assetbank.ecommerce.psp;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.constant.NetbanxSettings;
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
/*     */ public class NetbanxPlugin extends BasePspPlugin
/*     */   implements PSPPlugin
/*     */ {
/*  39 */   private NetbanxSettings m_settings = new NetbanxSettings("NetbanxPSPSettings");
/*     */ 
/*     */   public String createSignatureOut(Purchase a_purchase, String a_sKey)
/*     */   {
/*  53 */     String sSignature = null;
/*     */ 
/*  56 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/*  58 */       String sSource = getAmount(a_purchase) + this.m_settings.getCurrencyCodeValue() + a_sKey + EcommerceSettings.getPspSecret();
/*     */       try
/*     */       {
/*  64 */         sSignature = StringUtil.hexDigestSHA1(sSource);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/*  68 */         GlobalApplication.getInstance().getLogger().error("NetbanxPlugin: Could not run SHA1 encryption");
/*     */       }
/*     */     }
/*     */ 
/*  72 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public String createSignatureCallback(Purchase a_purchase, String a_sKey, HttpServletRequest a_request)
/*     */   {
/*  78 */     String sSignature = null;
/*     */ 
/*  81 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/*  83 */       String sSource = getAmount(a_purchase) + this.m_settings.getCurrencyCodeValue() + a_sKey + a_purchase.getPaymentReturn().getPspTransactionId() + EcommerceSettings.getPspSecret();
/*     */       try
/*     */       {
/*  90 */         sSignature = StringUtil.hexDigestSHA1(sSource);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/*  94 */         GlobalApplication.getInstance().getLogger().error("NetbanxPlugin: Could not run SHA1 encryption");
/*     */       }
/*     */     }
/*  97 */     GlobalApplication.getInstance().getLogger().info("NetbanxPlugin.createSignatureCallback: " + sSignature);
/*     */ 
/*  99 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public HashMap<String, String> createPurchaseForm(Purchase a_purchase, String a_sKey)
/*     */   {
/* 114 */     HashMap hmForm = new HashMap();
/*     */ 
/* 117 */     hmForm.put(this.m_settings.getCurrencyCodeKey(), this.m_settings.getCurrencyCodeValue());
/* 118 */     hmForm.put(this.m_settings.getTransactionIdKey(), a_sKey);
/* 119 */     hmForm.put(this.m_settings.getAmountKey(), String.valueOf(getAmount(a_purchase)));
/* 120 */     hmForm.put(this.m_settings.getUserEmailKey(), a_purchase.getEmailAddress());
/*     */ 
/* 123 */     hmForm.put(this.m_settings.getPassthruTransactionIdKey(), a_sKey);
/*     */ 
/* 126 */     hmForm.put(this.m_settings.getCallbackSuccessUrlKey(), this.m_settings.getCallbackSuccessUrlValue());
/* 127 */     hmForm.put(this.m_settings.getCallbackFailureUrlKey(), this.m_settings.getCallbackFailureUrlValue());
/* 128 */     hmForm.put(this.m_settings.getReturnUrlKey(), this.m_settings.getReturnUrlValue());
/*     */ 
/* 135 */     hmForm.put(this.m_settings.getSynchronousCallbackKey(), this.m_settings.getSynchronousCallbackValue());
/*     */ 
/* 139 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/* 141 */       String sSignature = createSignatureOut(a_purchase, a_sKey);
/* 142 */       hmForm.put(this.m_settings.getChecksumKey(), sSignature);
/*     */     }
/*     */ 
/* 145 */     return hmForm;
/*     */   }
/*     */ 
/*     */   public String getPSPUrl(Purchase a_purchase, String a_sKey)
/*     */   {
/* 157 */     String sUrl = this.m_settings.getNetbanxGateway() + this.m_settings.getMerchantValue();
/* 158 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public PspPaymentReturn processCallback(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 183 */     PspPaymentReturn info = new PspPaymentReturn();
/*     */ 
/* 186 */     String sAmountKey = this.m_settings.getCallbackAmountKey();
/* 187 */     String sValidKey = this.m_settings.getCallbackStatusKey();
/* 188 */     String sTransIdKey = this.m_settings.getCallbackNetbanxTransidKey();
/* 189 */     String sChecksumKey = this.m_settings.getCallbackChecksumKey();
/* 190 */     String sPassthruTransIdKey = this.m_settings.getPassthruTransactionIdKey();
/*     */ 
/* 193 */     String sAmount = a_request.getParameter(sAmountKey);
/* 194 */     String sValid = a_request.getParameter(sValidKey);
/* 195 */     String sNetbanxTransId = a_request.getParameter(sTransIdKey);
/* 196 */     String sChecksum = a_request.getParameter(sChecksumKey);
/*     */ 
/* 199 */     String sTransactionId = a_request.getParameter(sPassthruTransIdKey);
/*     */ 
/* 202 */     String sDebug = sAmountKey + "=" + sAmount + "&" + sValidKey + "=" + sValid + "&" + sTransIdKey + "=" + sNetbanxTransId + "&" + sChecksumKey + "=" + sChecksum + "&" + sPassthruTransIdKey + "=" + sTransactionId;
/*     */ 
/* 207 */     GlobalApplication.getInstance().getLogger().info("NetbanxPlugin.processCallback: " + sDebug);
/*     */ 
/* 210 */     boolean bValid = (sValid != null) && (sValid.compareToIgnoreCase(this.m_settings.getCallbackStatusSuccessValue()) == 0);
/*     */ 
/* 212 */     info.setValid(bValid);
/*     */ 
/* 216 */     long lAmount = 0L;
/* 217 */     if (StringUtil.stringIsPopulated(sAmount))
/*     */     {
/* 219 */       lAmount = Long.valueOf(sAmount).longValue();
/*     */     }
/*     */ 
/* 222 */     if (bValid)
/*     */     {
/* 224 */       info.setTransactionId(sTransactionId);
/* 225 */       info.getChargedAmount().setAmount(lAmount);
/* 226 */       info.setPspTransactionId(sNetbanxTransId);
/* 227 */       info.setSignature(sChecksum);
/*     */     }
/*     */ 
/* 230 */     return info;
/*     */   }
/*     */ 
/*     */   private long getAmount(Purchase a_purchase)
/*     */   {
/* 236 */     long lAmount = a_purchase.getChargedAmount().getAmount();
/* 237 */     return lAmount;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.NetbanxPlugin
 * JD-Core Version:    0.6.0
 */