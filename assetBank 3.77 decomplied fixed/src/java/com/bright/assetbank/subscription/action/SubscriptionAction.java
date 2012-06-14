/*     */ package com.bright.assetbank.subscription.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPlugin;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPluginFactory;
/*     */ import com.bright.assetbank.ecommerce.service.EcommerceManager;
/*     */ import com.bright.assetbank.subscription.bean.Subscription;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionModel;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionPurchase;
/*     */ import com.bright.assetbank.subscription.form.SubscriptionForm;
/*     */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*     */ import com.bright.assetbank.tax.bean.TaxValue;
/*     */ import com.bright.assetbank.tax.bean.TaxablePrice;
/*     */ import com.bright.assetbank.tax.service.TaxManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.Address;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.Country;
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SubscriptionAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  65 */   private EcommerceManager m_ecommerceManager = null;
/*     */ 
/*  71 */   private SubscriptionManager m_subscriptionManager = null;
/*     */ 
/*  77 */   private TaxManager m_taxManager = null;
/*     */ 
/*  83 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setEcommerceManager(EcommerceManager a_ecommerceManager)
/*     */   {
/*  68 */     this.m_ecommerceManager = a_ecommerceManager;
/*     */   }
/*     */ 
/*     */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager)
/*     */   {
/*  74 */     this.m_subscriptionManager = a_subscriptionManager;
/*     */   }
/*     */ 
/*     */   public void setTaxManager(TaxManager a_taxManager)
/*     */   {
/*  80 */     this.m_taxManager = a_taxManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  86 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 110 */     ActionForward afForward = null;
/* 111 */     SubscriptionForm form = (SubscriptionForm)a_form;
/* 112 */     Subscription sub = form.getSubscription();
/* 113 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 116 */     ABUser user = null;
/* 117 */     if (userProfile.getIsLoggedIn())
/*     */     {
/* 119 */       user = (ABUser)userProfile.getUser();
/*     */     }
/*     */     else
/*     */     {
/* 123 */       user = form.getUser();
/*     */     }
/*     */ 
/* 127 */     form.resetErrors();
/* 128 */     form.validateSubscription(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/* 131 */     validateUser(form, user, a_request, a_dbTransaction);
/*     */ 
/* 134 */     form.setEmailAddress(user.getEmailAddress());
/*     */ 
/* 136 */     if (!form.getHasErrors())
/*     */     {
/* 139 */       SubscriptionModel model = this.m_subscriptionManager.getSubscriptionModel(a_dbTransaction, sub.getModel().getId());
/* 140 */       model.setLanguage(userProfile.getCurrentLanguage());
/* 141 */       sub.setModel(model);
/*     */ 
/* 144 */       if (user.getId() > 0L)
/*     */       {
/* 146 */         BrightMoney bestPrice = this.m_subscriptionManager.getBestPriceOfModelForUser(a_dbTransaction, sub.getModel().getId(), user.getId());
/* 147 */         model.setBestPrice(bestPrice);
/*     */       }
/*     */ 
/* 151 */       long lSubtotal = 0L;
/* 152 */       if (model.getBestPrice().getAmount() > 0L)
/*     */       {
/* 154 */         lSubtotal = model.getBestPrice().getAmount();
/*     */       }
/*     */       else
/*     */       {
/* 158 */         lSubtotal = model.getPrice().getAmount();
/*     */       }
/*     */ 
/* 163 */       long lCountryId = user.getHomeAddress().getCountry().getId();
/* 164 */       long lTaxTypeId = 1L;
/* 165 */       TaxablePrice price = this.m_taxManager.getTaxablePriceForCountry(a_dbTransaction, lSubtotal, lCountryId, lTaxTypeId);
/*     */ 
/* 170 */       price.getTax().getTaxType().setLanguage(userProfile.getCurrentLanguage());
/* 171 */       price.getTax().getTaxRegion().setLanguage(userProfile.getCurrentLanguage());
/*     */ 
/* 174 */       String sUserTaxNumber = user.getTaxNumber();
/* 175 */       price.setTaxNumber(sUserTaxNumber);
/*     */ 
/* 178 */       form.setPrice(price);
/*     */ 
/* 181 */       if (price.getTax().getTaxType().getId() <= 0L)
/*     */       {
/* 183 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "subInvalidTaxLookup", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */ 
/* 187 */     if (form.getHasErrors())
/*     */     {
/* 189 */       afForward = a_mapping.findForward("Failure");
/* 190 */       return afForward;
/*     */     }
/*     */ 
/* 195 */     String sKey = "";
/* 196 */     Purchase purchase = populatePurchase(form, user);
/*     */ 
/* 198 */     if (purchase != null)
/*     */     {
/* 201 */       sKey = this.m_ecommerceManager.getNextPurchaseId(a_dbTransaction);
/* 202 */       purchase.setPurchaseId(sKey);
/*     */ 
/* 205 */       this.m_ecommerceManager.addPurchase(purchase, sKey);
/*     */ 
/* 208 */       String sPSPPluginClassname = EcommerceSettings.getPspPluginClass();
/* 209 */       PSPPlugin pspPlugin = PSPPluginFactory.getPSPPluginInstance(sPSPPluginClassname);
/* 210 */       HashMap hmPSPForm = pspPlugin.createPurchaseForm(purchase, sKey);
/* 211 */       form.setPurchaseForm(hmPSPForm);
/* 212 */       form.setPurchaseFormKeys(hmPSPForm.keySet());
/*     */ 
/* 214 */       String sPostUrl = pspPlugin.getPSPUrl(purchase, sKey);
/* 215 */       form.setPspUrl(sPostUrl);
/*     */ 
/* 218 */       if (purchase.getChargedAmount().getAmount() <= 0L)
/*     */       {
/* 221 */         purchase.registerPaymentSuccess(a_dbTransaction, userProfile.getCurrentLanguage());
/* 222 */         afForward = createRedirectingForward("", a_mapping, "ZeroPrice");
/* 223 */         return afForward;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 228 */     String sQueryString = "trans_id=" + sKey;
/* 229 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 230 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void validateUser(SubscriptionForm a_form, ABUser a_user, HttpServletRequest a_request, DBTransaction a_dbtransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 247 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 251 */     String sEmailAddress = a_user.getEmailAddress();
/* 252 */     if ((!StringUtil.stringIsPopulated(sEmailAddress)) || (!StringUtil.isValidEmailAddress(sEmailAddress)))
/*     */     {
/* 254 */       a_form.addError(this.m_listManager.getListItem(a_dbtransaction, "subInvalidEmailAddress", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 257 */     long lCountryId = a_user.getHomeAddress().getCountry().getId();
/* 258 */     if (lCountryId <= 0L)
/*     */     {
/* 260 */       a_form.addError(this.m_listManager.getListItem(a_dbtransaction, "subInvalidCountry", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   private Purchase populatePurchase(SubscriptionForm form, ABUser user)
/*     */   {
/* 280 */     SubscriptionPurchase purchase = new SubscriptionPurchase();
/*     */ 
/* 283 */     purchase.setUserId(user.getId());
/* 284 */     purchase.setLoginUser(user.getUsername());
/* 285 */     purchase.setLoginPassword(user.getPassword());
/* 286 */     purchase.setEmailAddress(user.getEmailAddress());
/*     */ 
/* 288 */     if (user.getId() == 0L)
/*     */     {
/* 291 */       purchase.setHasRegistered(true);
/* 292 */       purchase.setRegisterUser(user);
/*     */     }
/*     */ 
/* 296 */     Subscription sub = form.getSubscription();
/* 297 */     purchase.setSubscription(sub);
/*     */ 
/* 300 */     BrightMoney totalAmount = form.getPrice().getTotalAmount();
/* 301 */     purchase.setChargedAmount(totalAmount);
/*     */ 
/* 304 */     sub.setPricePaid(totalAmount);
/*     */ 
/* 307 */     purchase.setRefPaymentProcessor(this.m_subscriptionManager);
/*     */ 
/* 309 */     return purchase;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.action.SubscriptionAction
 * JD-Core Version:    0.6.0
 */