/*     */ package com.bright.assetbank.ecommerce.psp;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.constant.TNSCommsXLSettings;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class TNSCommsXLPlugin extends BasePspPlugin
/*     */   implements PSPPlugin
/*     */ {
/*  48 */   private TNSCommsXLSettings m_settings = new TNSCommsXLSettings("TNSCommsXLSettings");
/*     */ 
/*     */   public String createSignatureOut(Purchase a_purchase, String a_sKey)
/*     */   {
/*  62 */     String sSignature = null;
/*     */     try
/*     */     {
/*  66 */       if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */       {
/*  70 */         String sSource = EcommerceSettings.getPspSecret() + this.m_settings.getVPCAccessCode() + String.valueOf(a_purchase.getChargedAmount().getAmount()) + this.m_settings.getVPCCommand() + this.m_settings.getVPCLocale() + a_sKey + this.m_settings.getMerchantId() + a_sKey + EcommerceSettings.getPspCallbackUrl() + this.m_settings.getVPCVersion();
/*     */ 
/*  81 */         sSignature = StringUtil.hexDigestMD5(sSource);
/*  82 */         sSignature = sSignature.toUpperCase();
/*     */       }
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/*  87 */       GlobalApplication.getInstance().getLogger().error(getClass().getSimpleName() + ".createSignatureOut: Error creating secure MD5 hash: ", e);
/*     */     }
/*     */ 
/*  90 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public String createSignatureCallback(Purchase a_purchase, String a_sKey, HttpServletRequest a_request)
/*     */   {
/*  96 */     String sSignature = null;
/*     */     try
/*     */     {
/* 100 */       if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */       {
/* 102 */         String sSource = EcommerceSettings.getPspSecret();
/*     */ 
/* 105 */         Enumeration params = a_request.getParameterNames();
/* 106 */         ArrayList<String> alParams = new ArrayList();
/*     */ 
/* 108 */         while (params.hasMoreElements())
/*     */         {
/* 110 */           String sName = (String)params.nextElement();
/* 111 */           alParams.add(sName);
/*     */         }
/*     */ 
/* 115 */         Collections.sort(alParams);
/* 116 */         for (String sName : alParams)
/*     */         {
/* 118 */           if (!sName.equals(this.m_settings.getParamSecureHash()))
/*     */           {
/* 120 */             sSource = sSource + a_request.getParameter(sName);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 125 */         sSignature = StringUtil.hexDigestMD5(sSource);
/* 126 */         sSignature = sSignature.toUpperCase();
/*     */       }
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 131 */       GlobalApplication.getInstance().getLogger().error(getClass().getSimpleName() + ".createSignatureCallback: Error creating secure MD5 hash: ", e);
/*     */     }
/*     */ 
/* 134 */     return sSignature;
/*     */   }
/*     */ 
/*     */   public HashMap<String, String> createPurchaseForm(Purchase a_purchase, String a_sKey)
/*     */   {
/* 149 */     HashMap hmForm = new HashMap();
/* 150 */     hmForm.put(this.m_settings.getParamTransactionRef(), a_sKey);
/*     */ 
/* 152 */     hmForm.put(this.m_settings.getParamAmount(), String.valueOf(a_purchase.getChargedAmount().getAmount()));
/* 153 */     hmForm.put(this.m_settings.getParamMerchant(), this.m_settings.getMerchantId());
/* 154 */     hmForm.put(this.m_settings.getParamReturnURL(), EcommerceSettings.getPspCallbackUrl());
/* 155 */     hmForm.put(this.m_settings.getParamOrderInfo(), a_sKey);
/* 156 */     hmForm.put(this.m_settings.getParamVersion(), this.m_settings.getVPCVersion());
/* 157 */     hmForm.put(this.m_settings.getParamCommand(), this.m_settings.getVPCCommand());
/* 158 */     hmForm.put(this.m_settings.getParamAccessCode(), this.m_settings.getVPCAccessCode());
/* 159 */     hmForm.put(this.m_settings.getParamLocale(), this.m_settings.getVPCLocale());
/*     */ 
/* 165 */     if (StringUtil.stringIsPopulated(EcommerceSettings.getPspSecret()))
/*     */     {
/* 167 */       String sSignature = createSignatureOut(a_purchase, a_sKey);
/* 168 */       hmForm.put(this.m_settings.getParamSecureHash(), sSignature);
/*     */     }
/*     */ 
/* 171 */     return hmForm;
/*     */   }
/*     */ 
/*     */   public String getPSPUrl(Purchase a_purchase, String a_sKey)
/*     */   {
/* 183 */     String sUrl = this.m_settings.getPaymentUrl();
/* 184 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public PspPaymentReturn processCallback(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 214 */     PspPaymentReturn info = new PspPaymentReturn();
/*     */ 
/* 217 */     String sAmount = a_request.getParameter(this.m_settings.getParamAmount());
/* 218 */     String sValid = a_request.getParameter(this.m_settings.getParamTransactionResponse());
/* 219 */     String sTransactionId = a_request.getParameter(this.m_settings.getParamTransactionRef());
/* 220 */     String sPSPTransId = a_request.getParameter(this.m_settings.getParamTransactionNo());
/* 221 */     String sSignature = a_request.getParameter(this.m_settings.getParamSecureHash());
/*     */ 
/* 224 */     boolean bValid = (sValid != null) && (sValid.equals(this.m_settings.getReturnCode_Success()));
/*     */ 
/* 227 */     boolean bCancelled = (sValid != null) && (sValid.equals(this.m_settings.getReturnCode_Cancel()));
/*     */ 
/* 229 */     info.setValid(bValid);
/* 230 */     info.setCancelled(bCancelled);
/*     */ 
/* 233 */     if (bValid)
/*     */     {
/* 235 */       info.setTransactionId(sTransactionId);
/* 236 */       info.getChargedAmount().setAmount(Integer.parseInt(sAmount));
/* 237 */       info.setPspTransactionId(sPSPTransId);
/* 238 */       info.setSignature(sSignature);
/*     */     }
/*     */ 
/* 241 */     return info;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.TNSCommsXLPlugin
 * JD-Core Version:    0.6.0
 */