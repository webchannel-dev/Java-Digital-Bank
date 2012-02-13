/*     */ package com.bright.assetbank.subscription.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionModel;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class SubscriptionModelsForm extends UserForm
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  51 */   private SubscriptionModel m_model = null;
/*  52 */   private Vector m_vecModels = null;
/*     */ 
/*  55 */   private HashMap m_hmUpgradeSelected = null;
/*  56 */   private HashMap m_hmUpgradePrices = null;
/*     */ 
/*     */   public SubscriptionModelsForm()
/*     */   {
/*  61 */     this.m_model = new SubscriptionModel();
/*     */ 
/*  64 */     if (FrameworkSettings.getSupportMultiLanguage())
/*     */     {
/*  66 */       LanguageUtils.createEmptyTranslations(this.m_model, 20);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     BrightMoney price = this.m_model.getPrice();
/*  83 */     if ((!price.getIsFormAmountEntered()) || (!price.processFormData()))
/*     */     {
/*  85 */       addError(a_listManager.getListItem(a_dbTransaction, "subModelInvalidPrice", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  88 */     BrightNaturalNumber nNumDownloads = this.m_model.getNoOfDownloads();
/*  89 */     if ((!nNumDownloads.getIsFormNumberEntered()) || (!nNumDownloads.processFormData()))
/*     */     {
/*  91 */       addError(a_listManager.getListItem(a_dbTransaction, "subModelInvalidNumDownloads", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  94 */     BrightNaturalNumber nDuration = this.m_model.getDuration();
/*  95 */     if ((!nDuration.getIsFormNumberEntered()) || (!nDuration.processFormData()))
/*     */     {
/*  97 */       addError(a_listManager.getListItem(a_dbTransaction, "subModelInvalidDuration", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 101 */     boolean bUpgradePriceFormatError = false;
/* 102 */     Iterator it = this.m_hmUpgradePrices.values().iterator();
/* 103 */     while (it.hasNext())
/*     */     {
/* 105 */       String sPrice = (String)it.next();
/* 106 */       BrightMoney upgradePrice = new BrightMoney();
/* 107 */       upgradePrice.setFormAmount(sPrice);
/* 108 */       if (!upgradePrice.processFormData())
/*     */       {
/* 110 */         bUpgradePriceFormatError = true;
/*     */       }
/*     */     }
/* 113 */     if (bUpgradePriceFormatError)
/*     */     {
/* 115 */       addError(a_listManager.getListItem(a_dbTransaction, "subModelInvalidPriceUpgrade", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setModels(Vector a_vecModels)
/*     */   {
/* 125 */     this.m_vecModels = a_vecModels;
/*     */   }
/*     */ 
/*     */   public Vector getModels() {
/* 129 */     return this.m_vecModels;
/*     */   }
/*     */ 
/*     */   public SubscriptionModel getModel()
/*     */   {
/* 134 */     return this.m_model;
/*     */   }
/*     */ 
/*     */   public void setModel(SubscriptionModel a_sModel) {
/* 138 */     this.m_model = a_sModel;
/*     */   }
/*     */ 
/*     */   public HashMap getUpgradePrices()
/*     */   {
/* 143 */     return this.m_hmUpgradePrices;
/*     */   }
/*     */ 
/*     */   public void setUpgradePrices(HashMap a_sHmUpgradePrices) {
/* 147 */     this.m_hmUpgradePrices = a_sHmUpgradePrices;
/*     */   }
/*     */ 
/*     */   public HashMap getUpgradeSelected() {
/* 151 */     return this.m_hmUpgradeSelected;
/*     */   }
/*     */ 
/*     */   public void setUpgradeSelected(HashMap a_sHmUpgradeSelected) {
/* 155 */     this.m_hmUpgradeSelected = a_sHmUpgradeSelected;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.form.SubscriptionModelsForm
 * JD-Core Version:    0.6.0
 */