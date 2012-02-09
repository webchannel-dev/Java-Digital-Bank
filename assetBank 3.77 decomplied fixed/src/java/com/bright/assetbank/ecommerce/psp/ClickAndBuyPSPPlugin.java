/*     */ package com.bright.assetbank.ecommerce.psp;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.ClickAndBuyPSPSettings;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceConstants;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.clickandbuy.TransactionManager.ClickAndBuyTransactionBDRProcessingState;
/*     */ import com.clickandbuy.TransactionManager.TransactionManagerServicesLocator;
/*     */ import com.clickandbuy.TransactionManager.TransactionManagerStatus;
/*     */ import com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ClickAndBuyPSPPlugin extends BasePspPlugin
/*     */   implements PSPPlugin, EcommerceConstants
/*     */ {
/*  46 */   private ClickAndBuyPSPSettings m_settings = new ClickAndBuyPSPSettings("ClickAndBuyPSPSettings");
/*     */ 
/*     */   public HashMap<String, String> createPurchaseForm(Purchase a_purchase, String a_sKey)
/*     */   {
/*  50 */     HashMap hmForm = new HashMap();
/*  51 */     return hmForm;
/*     */   }
/*     */ 
/*     */   public String createSignatureCallback(Purchase a_purchase, String a_sKey, HttpServletRequest a_request)
/*     */   {
/*  57 */     return null;
/*     */   }
/*     */ 
/*     */   public String createSignatureOut(Purchase a_purchase, String a_sKey)
/*     */   {
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */   public String getPSPUrl(Purchase a_purchase, String a_sKey)
/*     */   {
/*  69 */     String sUrl = this.m_settings.getClickAndBuyTransactionLink();
/*  70 */     sUrl = sUrl + "?" + this.m_settings.getClickAndBuyKeyPrice() + "=" + clickAndBuyAmount(a_purchase.getChargedAmount().getAmount());
/*  71 */     sUrl = sUrl + "&" + this.m_settings.getClickAndBuyKeyCurrencyCode() + "=" + this.m_settings.getClickAndBuyCurrencyCode();
/*     */ 
/*  74 */     sUrl = sUrl + "&" + this.m_settings.getClickAndBuyKeyExternalTransactionId() + "=" + a_sKey;
/*  75 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public PspPaymentReturn processCallback(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     Log logger = GlobalApplication.getInstance().getLogger();
/*  88 */     logger.debug("HEADER");
/*  89 */     PspPaymentReturn info = new PspPaymentReturn();
/*  90 */     Enumeration headers = a_request.getHeaderNames();
/*  91 */     while (headers.hasMoreElements())
/*     */     {
/*  93 */       Object header_name = headers.nextElement();
/*  94 */       logger.debug(header_name + ":" + a_request.getHeader(header_name.toString()));
/*     */     }
/*     */ 
/*  97 */     logger.debug("PARAMS");
/*  98 */     Enumeration params = a_request.getParameterNames();
/*  99 */     while (params.hasMoreElements())
/*     */     {
/* 101 */       Object params_name = params.nextElement();
/* 102 */       logger.debug(params_name + ":");
/* 103 */       String[] params_vals = a_request.getParameterValues(params_name.toString());
/* 104 */       for (int i = 0; i < params_vals.length; i++)
/*     */       {
/* 106 */         logger.debug(params_vals[i]);
/*     */       }
/*     */     }
/*     */ 
/* 110 */     String sHttpUserId = a_request.getHeader(this.m_settings.getClickAndBuyKeyCallbackUserId());
/* 111 */     String sHttpPrice = a_request.getHeader(this.m_settings.getClickAndBuyKeyCallbackPrice());
/* 112 */     String sHttpCurrency = a_request.getHeader(this.m_settings.getClickAndBuyKeyCallbackCurrency());
/* 113 */     String sHttpTransactionId = a_request.getHeader(this.m_settings.getClickAndBuyKeyCallbackTransactionId());
/* 114 */     String sHttpContentId = a_request.getHeader(this.m_settings.getClickAndBuyKeyCallbackContentId());
/* 115 */     String sHttpUserIp = a_request.getHeader(this.m_settings.getClickAndBuyKeyCallbackUserIp());
/* 116 */     String sPurchaseId = a_request.getParameter(this.m_settings.getClickAndBuyKeyExternalTransactionId());
/* 117 */     String sForwardedFor = a_request.getHeader(this.m_settings.getClickAndBuyKeyCallbackForwardedFor());
/*     */ 
/* 120 */     if ((sHttpUserId == null) || (sHttpPrice == null) || (sHttpCurrency == null) || (sHttpTransactionId == null) || (sHttpContentId == null) || (sHttpUserIp == null) || (sPurchaseId == null))
/*     */     {
/* 124 */       logger.debug("ClickAndBuy Callback Error: All exepcted header parameters were not present: " + this.m_settings.getClickAndBuyKeyCallbackUserId() + "=" + sHttpUserId + " " + this.m_settings.getClickAndBuyKeyCallbackPrice() + "=" + sHttpPrice + " " + this.m_settings.getClickAndBuyKeyCallbackCurrency() + "=" + sHttpCurrency + " " + this.m_settings.getClickAndBuyKeyCallbackTransactionId() + "=" + sHttpTransactionId + " " + this.m_settings.getClickAndBuyKeyCallbackContentId() + "=" + sHttpContentId + " " + this.m_settings.getClickAndBuyKeyCallbackUserIp() + "=" + sHttpUserIp + " " + this.m_settings.getClickAndBuyKeyExternalTransactionId() + "=" + sPurchaseId);
/*     */ 
/* 132 */       info.setValid(false);
/*     */     }
/*     */     else
/*     */     {
/* 138 */       String sRemoteIp = a_request.getRemoteAddr();
/* 139 */       String sCBIP = this.m_settings.getClickAndBuyCallbackRemoteIp();
/* 140 */       if ((!sRemoteIp.startsWith(sCBIP)) && (!sForwardedFor.contains(sCBIP)))
/*     */       {
/* 142 */         logger.debug("ClickAndBuy Callback Error: Remote IP " + sRemoteIp + " not as expected " + this.m_settings.getClickAndBuyCallbackRemoteIp());
/* 143 */         info.setValid(false);
/*     */       }
/*     */ 
/* 147 */       if (!sHttpCurrency.equals(this.m_settings.getClickAndBuyCurrencyCode()))
/*     */       {
/* 149 */         logger.debug("ClickAndBuy Callback Error: Currency Code " + sHttpCurrency + " not as expected " + this.m_settings.getClickAndBuyCurrencyCode());
/* 150 */         info.setValid(false);
/*     */       }
/*     */ 
/* 155 */       info.setChargedAmount(new BrightMoney(Long.parseLong(sHttpPrice) / 1000L));
/* 156 */       info.getChargedAmount().processFormData();
/* 157 */       info.setTransactionId(sPurchaseId);
/* 158 */       info.setPspTransactionId(sHttpTransactionId);
/* 159 */       info.setPspUserId(sHttpUserId);
/* 160 */       info.setValid(true);
/*     */     }
/*     */ 
/* 165 */     info.setCancelled(false);
/* 166 */     return info;
/*     */   }
/*     */ 
/*     */   public boolean requiresRedirectFromCallback()
/*     */   {
/* 183 */     return true;
/*     */   }
/*     */ 
/*     */   public String generateSuccessRedirect(PspPaymentReturn a_info)
/*     */   {
/* 201 */     String sReponse = this.m_settings.getClickAndBuySuccessReturnUrl();
/* 202 */     sReponse = sReponse + "?" + this.m_settings.getClickAndBuyKeyResult() + "=" + this.m_settings.getClickAndBuyResultSuccess();
/* 203 */     sReponse = sReponse + "&trans_id=" + a_info.getTransactionId();
/* 204 */     return sReponse;
/*     */   }
/*     */ 
/*     */   public String generateFailureRedirect(PspPaymentReturn a_info)
/*     */   {
/* 221 */     String sReponse = this.m_settings.getClickAndBuyFailureReturnUrl();
/* 222 */     sReponse = sReponse + "?" + this.m_settings.getClickAndBuyKeyResult() + "=" + this.m_settings.getClickAndBuyResultError();
/* 223 */     return sReponse;
/*     */   }
/*     */ 
/*     */   public boolean registerSuccessOnCallback()
/*     */   {
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean registerSuccessOnReturn()
/*     */   {
/* 261 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean processReturn(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */   {
/* 291 */     Log logger = GlobalApplication.getInstance().getLogger();
/* 292 */     boolean bTransactionComitted = false;
/*     */     try
/*     */     {
/* 296 */       TransactionManagerServicesLocator serviceLocator = new TransactionManagerServicesLocator();
/* 297 */       TransactionManagerStatus transactionManagerStatus = serviceLocator.getTransactionManagerStatusServicesPort();
/* 298 */       ClickAndBuyTransactionBDRProcessingState processState = transactionManagerStatus.isExternalBDRIDCommitted(Long.parseLong(this.m_settings.getClickAndBuyMerchantId()), this.m_settings.getClickAndBuyTransactionManagerPassword(), Long.parseLong("0"), getKeyFromReturnRequest(a_request));
/* 299 */       bTransactionComitted = processState.isIsCommitted();
/*     */     }
/*     */     catch (TransactionManagerStatusStatusException e)
/*     */     {
/* 303 */       logger.debug("TransactionManagerStatusStatus Exception while checking isExternalBDRIDCommitted: " + e + " - " + e.getMessage1() + " Transaction cannot be confirmed - approval will not be granted.");
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/* 307 */       logger.debug("Number Format Exception while checking isExternalBDRIDCommitted: " + e + " Transaction cannot be confirmed - approval will not be granted.");
/*     */     }
/*     */     catch (RemoteException e)
/*     */     {
/* 311 */       logger.debug("Remote Exception while checking isExternalBDRIDCommitted: " + e + " Transaction cannot be confirmed - approval will not be granted.");
/*     */     }
/*     */     catch (ServiceException e)
/*     */     {
/* 315 */       logger.debug("Service Exception while checking isExternalBDRIDCommitted: " + e + " Transaction cannot be confirmed - approval will not be granted.");
/*     */     }
/*     */ 
/* 319 */     return bTransactionComitted;
/*     */   }
/*     */ 
/*     */   private String clickAndBuyAmount(long l_amount)
/*     */   {
/* 325 */     String sAmount = Long.toString(l_amount);
/* 326 */     while (sAmount.length() < 3)
/*     */     {
/* 328 */       sAmount = "0" + sAmount;
/*     */     }
/*     */ 
/* 331 */     return sAmount;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.ClickAndBuyPSPPlugin
 * JD-Core Version:    0.6.0
 */