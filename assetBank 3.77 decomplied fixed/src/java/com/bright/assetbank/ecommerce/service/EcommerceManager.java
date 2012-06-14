/*     */ package com.bright.assetbank.ecommerce.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.approval.constant.AssetApprovalConstants;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.ecommerce.bean.AssetInOrder;
/*     */ import com.bright.assetbank.ecommerce.bean.AssetPurchase;
/*     */ import com.bright.assetbank.ecommerce.bean.AssetPurchasePriceBand;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceConstants;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.common.service.RefDataManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.user.util.PasswordUtil;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class EcommerceManager extends Bn2Manager
/*     */   implements PaymentCallbackProcessor, EcommerceConstants, AssetApprovalConstants
/*     */ {
/*     */   private static final String c_ksClassName = "EcommerceManager";
/*  70 */   private AssetApprovalManager m_approvalManager = null;
/*     */ 
/*  76 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*  82 */   private EmailManager m_emailManager = null;
/*     */ 
/*  88 */   private ABUserManager m_userManager = null;
/*     */ 
/*  94 */   private RefDataManager m_refDataManager = null;
/*     */ 
/* 100 */   private OrderManager m_orderManager = null;
/*     */ 
/* 108 */   private HashMap m_hmPurchases = null;
/*     */   private int m_iMinsExpiry;
/*     */   private Object m_oKeyLock;
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/*  73 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_manager)
/*     */   {
/*  79 */     this.m_assetBoxManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/*  85 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  91 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setRefDataManager(RefDataManager a_refDataManager)
/*     */   {
/*  97 */     this.m_refDataManager = a_refDataManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(OrderManager a_sManager)
/*     */   {
/* 103 */     this.m_orderManager = a_sManager;
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/* 128 */     this.m_hmPurchases = new HashMap(50);
/* 129 */     this.m_iMinsExpiry = EcommerceSettings.getPurchaseExpiryPeriod();
/*     */ 
/* 132 */     this.m_oKeyLock = new Object();
/*     */   }
/*     */ 
/*     */   public synchronized void addPurchase(Purchase a_purchase, String a_sKey)
/*     */   {
/* 143 */     clearOldPurchases();
/* 144 */     this.m_hmPurchases.put(a_sKey, a_purchase);
/*     */ 
/* 146 */     this.m_logger.info("EcommerceManager: Adding purchase to memory store: " + a_sKey);
/* 147 */     this.m_logger.info("EcommerceManager: Number of purchases in memory store: " + this.m_hmPurchases.size());
/*     */   }
/*     */ 
/*     */   public boolean containsPurchase(String a_sKey)
/*     */   {
/* 158 */     return this.m_hmPurchases.containsKey(a_sKey);
/*     */   }
/*     */ 
/*     */   public Purchase getPurchase(String a_sKey)
/*     */   {
/* 169 */     return (Purchase)this.m_hmPurchases.get(a_sKey);
/*     */   }
/*     */ 
/*     */   public void registerPaymentSuccess(DBTransaction a_dbTransaction, Purchase a_purchase, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 188 */     AssetPurchase purchase = (AssetPurchase)a_purchase;
/*     */ 
/* 191 */     if (purchase.getHasRegistered())
/*     */     {
/* 193 */       ABUser user = createNewAccount(a_dbTransaction, purchase, a_language);
/*     */ 
/* 196 */       purchase.setLoginUser(user.getUsername());
/* 197 */       purchase.setLoginPassword(user.getPassword());
/* 198 */       purchase.setUserId(user.getId());
/*     */     }
/*     */ 
/* 202 */     long lUserId = purchase.getUserId();
/*     */ 
/* 205 */     if (AssetBankSettings.getUsePriceBands())
/*     */     {
/* 207 */       updateAssetBoxAfterPayment(a_dbTransaction, a_purchase);
/*     */     }
/*     */ 
/* 212 */     Vector items = purchase.getAssetList();
/* 213 */     for (int i = 0; i < items.size(); i++)
/*     */     {
/* 215 */       AssetInOrder aio = (AssetInOrder)items.get(i);
/* 216 */       long lAssetId = aio.getAssetId();
/*     */ 
/* 218 */       if (AssetBankSettings.getUsePriceBands())
/*     */       {
/* 221 */         Vector vecPriceBands = aio.getPriceBands();
/* 222 */         for (int j = 0; j < vecPriceBands.size(); j++)
/*     */         {
/* 224 */           AssetPurchasePriceBand appb = (AssetPurchasePriceBand)vecPriceBands.get(j);
/*     */ 
/* 226 */           if (appb.getPriceBandType().getId() != 1L)
/*     */             continue;
/* 228 */           this.m_approvalManager.submitAndApproveAssetForUser(a_dbTransaction, lAssetId, lUserId);
/* 229 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 237 */         this.m_approvalManager.submitAndApproveAssetForUser(a_dbTransaction, lAssetId, lUserId);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 242 */     long lOrderId = this.m_orderManager.logOrder(a_dbTransaction, purchase);
/*     */ 
/* 245 */     this.m_orderManager.sendConfirmOrderPurchaseEmail(a_dbTransaction, lOrderId, a_language, purchase);
/*     */   }
/*     */ 
/*     */   public void updateAssetBoxAfterPayment(DBTransaction a_dbTransaction, Purchase a_purchase)
/*     */     throws Bn2Exception
/*     */   {
/* 266 */     AssetPurchase purchase = (AssetPurchase)a_purchase;
/*     */ 
/* 269 */     long lUserId = purchase.getUserId();
/*     */ 
/* 273 */     Vector items = purchase.getAssetList();
/* 274 */     for (int i = 0; i < items.size(); i++)
/*     */     {
/* 276 */       AssetInOrder aio = (AssetInOrder)items.get(i);
/* 277 */       long lAssetId = aio.getAssetId();
/*     */ 
/* 279 */       if (purchase.getHasRegistered())
/*     */       {
/* 282 */         this.m_assetBoxManager.addAssetToDBForUser(a_dbTransaction, lUserId, lAssetId);
/*     */       }
/*     */       else
/*     */       {
/* 287 */         this.m_assetBoxManager.removeAssetPricesFromDBForUser(a_dbTransaction, lUserId, lAssetId);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private ABUser createNewAccount(DBTransaction a_dbTransaction, Purchase a_purchase, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 308 */     ABUser user = a_purchase.getRegisterUser();
/* 309 */     user.setEmailAddress(a_purchase.getEmailAddress());
/* 310 */     user.setUsername(a_purchase.getEmailAddress());
/* 311 */     user.setNotApproved(false);
/* 312 */     user.setLanguage(a_language);
/*     */ 
/* 315 */     String sPassword = PasswordUtil.generateRandomPassword();
/* 316 */     user.setPassword(sPassword);
/* 317 */     this.m_userManager.saveUser(a_dbTransaction, user);
/*     */ 
/* 319 */     return user;
/*     */   }
/*     */ 
/*     */   private synchronized void clearOldPurchases()
/*     */   {
/* 332 */     Calendar cal = Calendar.getInstance();
/* 333 */     cal.setTime(new Date());
/* 334 */     cal.add(12, -this.m_iMinsExpiry);
/* 335 */     Date dateExpiry = cal.getTime();
/*     */ 
/* 338 */     Collection colEntries = this.m_hmPurchases.entrySet();
/* 339 */     Iterator it = colEntries.iterator();
/* 340 */     while (it.hasNext())
/*     */     {
/* 342 */       Map.Entry e = (Map.Entry)it.next();
/* 343 */       Purchase p = (Purchase)e.getValue();
/*     */ 
/* 345 */       if (p.getDateCreated().before(dateExpiry))
/*     */       {
/* 347 */         it.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getNextPurchaseId(DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 367 */     synchronized (this.m_oKeyLock)
/*     */     {
/* 370 */       long lPurchaseId = this.m_refDataManager.getSystemSettingAsLong("PurchaseId");
/* 371 */       lPurchaseId += 1L;
/* 372 */       this.m_refDataManager.updateSystemSettingAsLong(a_dbTransaction, "PurchaseId", lPurchaseId);
/*     */ 
/* 374 */       return new Long(lPurchaseId).toString();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.service.EcommerceManager
 * JD-Core Version:    0.6.0
 */