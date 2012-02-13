/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceConstants;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.form.CheckoutForm;
/*     */ import com.bright.assetbank.ecommerce.service.EcommerceManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class PurchaseAction extends BTransactionAction
/*     */   implements EcommerceConstants, AssetBoxConstants, MessageConstants
/*     */ {
/*  53 */   private EcommerceManager m_ecommerceManager = null;
/*     */ 
/*     */   public void setEcommerceManager(EcommerceManager a_ecommerceManager) {
/*  56 */     this.m_ecommerceManager = a_ecommerceManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     ActionForward afForward = null;
/*  68 */     CheckoutForm form = (CheckoutForm)a_form;
/*  69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  71 */     String sQueryString = "";
/*     */ 
/*  74 */     if (EcommerceSettings.getEcommerceOfflineOption())
/*     */     {
/*  76 */       if ((form.getSelectedUse() != null) && (form.getSelectedUse().compareToIgnoreCase("offline") == 0))
/*     */       {
/*  78 */         if (userProfile.getIsLoggedIn())
/*     */         {
/*  81 */           afForward = createRedirectingForward(sQueryString, a_mapping, "DoOffline");
/*     */         }
/*     */         else
/*     */         {
/*  86 */           userProfile.setRegisterEmailAddress(form.getRegisterEmailAddress());
/*  87 */           afForward = createRedirectingForward(sQueryString, a_mapping, "OfflineRegister");
/*     */         }
/*  89 */         return afForward;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  94 */     String sTransidKey = "trans_id";
/*  95 */     String sTransactionId = a_request.getParameter(sTransidKey);
/*     */ 
/*  98 */     if ((!StringUtil.stringIsPopulated(sTransactionId)) || (!this.m_ecommerceManager.containsPurchase(sTransactionId)))
/*     */     {
/* 100 */       sQueryString = "checkout=checkout";
/* 101 */       afForward = createForward(sQueryString, a_mapping, "ValidationFailure");
/* 102 */       return afForward;
/*     */     }
/* 104 */     Purchase purchase = this.m_ecommerceManager.getPurchase(sTransactionId);
/*     */ 
/* 107 */     sQueryString = "trans_id=" + sTransactionId;
/*     */ 
/* 111 */     if (purchase.getChargedAmount().getAmount() <= 0L)
/*     */     {
/* 113 */       purchase.registerPaymentSuccess(a_dbTransaction, userProfile.getCurrentLanguage());
/* 114 */       afForward = createRedirectingForward(sQueryString, a_mapping, "ZeroPrice");
/* 115 */       return afForward;
/*     */     }
/*     */ 
/* 119 */     sQueryString = "checkout=checkout";
/* 120 */     afForward = createForward(sQueryString, a_mapping, "ValidationFailure");
/* 121 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.PurchaseAction
 * JD-Core Version:    0.6.0
 */