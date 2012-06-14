/*     */ package com.bright.assetbank.subscription.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.subscription.bean.Subscription;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionModel;
/*     */ import com.bright.assetbank.tax.bean.TaxablePrice;
/*     */ import com.bright.assetbank.user.form.UpdateUserForm;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class SubscriptionForm extends UpdateUserForm
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  46 */   private Subscription m_subscription = null;
/*  47 */   private Vector m_subscriptions = null;
/*  48 */   private Vector m_models = null;
/*  49 */   private boolean m_bTsandcs = false;
/*  50 */   private boolean m_bUpgradePricesAvailable = false;
/*     */   private HashMap m_purchaseForm;
/*     */   private Collection m_purchaseFormKeys;
/*     */   private String m_sPspUrl;
/*     */   private String m_sEmailAddress;
/*  57 */   private TaxablePrice m_price = null;
/*     */ 
/*     */   public SubscriptionForm()
/*     */   {
/*  65 */     this.m_subscription = new Subscription();
/*     */   }
/*     */ 
/*     */   public void resetErrors()
/*     */   {
/*  74 */     getErrors().clear();
/*     */   }
/*     */ 
/*     */   public void resetSubscription()
/*     */   {
/*  83 */     this.m_bTsandcs = false;
/*  84 */     this.m_subscription = new Subscription();
/*     */   }
/*     */ 
/*     */   public void resetAll()
/*     */   {
/*  95 */     resetErrors();
/*  96 */     resetSubscription();
/*  97 */     resetUser();
/*     */   }
/*     */ 
/*     */   public void validateSubscription(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/* 108 */     BrightDate date = this.m_subscription.getStartDate();
/* 109 */     if ((!date.getIsFormDateEntered()) || (!date.processFormData()) || (date.getIsDayInPast()))
/*     */     {
/* 111 */       addError(a_listManager.getListItem(a_dbTransaction, "subInvalidDate", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 115 */     long lSubscriptionId = this.m_subscription.getModel().getId();
/* 116 */     if (lSubscriptionId <= 0L)
/*     */     {
/* 118 */       addError(a_listManager.getListItem(a_dbTransaction, "subInvalidModel", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 122 */     if (!getTsandcs())
/*     */     {
/* 124 */       addError(a_listManager.getListItem(a_dbTransaction, "subInvalidTsandcs", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Subscription getSubscription()
/*     */   {
/* 131 */     return this.m_subscription;
/*     */   }
/*     */ 
/*     */   public void setSubscription(Subscription a_sSubscription) {
/* 135 */     this.m_subscription = a_sSubscription;
/*     */   }
/*     */ 
/*     */   public Vector getSubscriptions() {
/* 139 */     return this.m_subscriptions;
/*     */   }
/*     */ 
/*     */   public void setSubscriptions(Vector a_sSubscriptionList) {
/* 143 */     this.m_subscriptions = a_sSubscriptionList;
/*     */   }
/*     */ 
/*     */   public Vector getModels()
/*     */   {
/* 148 */     return this.m_models;
/*     */   }
/*     */ 
/*     */   public void setModels(Vector a_sModels) {
/* 152 */     this.m_models = a_sModels;
/*     */   }
/*     */ 
/*     */   public boolean getTsandcs()
/*     */   {
/* 157 */     return this.m_bTsandcs;
/*     */   }
/*     */ 
/*     */   public void setTsandcs(boolean a_sTsandcs) {
/* 161 */     this.m_bTsandcs = a_sTsandcs;
/*     */   }
/*     */ 
/*     */   public HashMap getPurchaseForm()
/*     */   {
/* 166 */     return this.m_purchaseForm;
/*     */   }
/*     */ 
/*     */   public void setPurchaseForm(HashMap a_sPurchaseForm) {
/* 170 */     this.m_purchaseForm = a_sPurchaseForm;
/*     */   }
/*     */ 
/*     */   public Collection getPurchaseFormKeys()
/*     */   {
/* 175 */     return this.m_purchaseFormKeys;
/*     */   }
/*     */ 
/*     */   public void setPurchaseFormKeys(Collection a_sPurchaseFormKeys) {
/* 179 */     this.m_purchaseFormKeys = a_sPurchaseFormKeys;
/*     */   }
/*     */ 
/*     */   public String getPspUrl()
/*     */   {
/* 184 */     return this.m_sPspUrl;
/*     */   }
/*     */ 
/*     */   public void setPspUrl(String a_sPspUrl) {
/* 188 */     this.m_sPspUrl = a_sPspUrl;
/*     */   }
/*     */ 
/*     */   public String getEmailAddress()
/*     */   {
/* 193 */     return this.m_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public void setEmailAddress(String a_sEmailAddress) {
/* 197 */     this.m_sEmailAddress = a_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public TaxablePrice getPrice()
/*     */   {
/* 202 */     return this.m_price;
/*     */   }
/*     */ 
/*     */   public void setPrice(TaxablePrice a_sPrice) {
/* 206 */     this.m_price = a_sPrice;
/*     */   }
/*     */ 
/*     */   public boolean getUpgradePricesAvailable()
/*     */   {
/* 211 */     return this.m_bUpgradePricesAvailable;
/*     */   }
/*     */ 
/*     */   public void setUpgradePricesAvailable(boolean a_sUpgradePricesAvailable) {
/* 215 */     this.m_bUpgradePricesAvailable = a_sUpgradePricesAvailable;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.form.SubscriptionForm
 * JD-Core Version:    0.6.0
 */