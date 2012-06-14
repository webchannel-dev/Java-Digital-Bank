/*     */ package com.bright.assetbank.subscription.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.subscription.bean.Subscription;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionModel;
/*     */ import com.bright.assetbank.subscription.form.SubscriptionForm;
/*     */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewSubscriptionAction extends BTransactionAction
/*     */ {
/*  52 */   private SubscriptionManager m_subscriptionManager = null;
/*     */ 
/*     */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager) {
/*  55 */     this.m_subscriptionManager = a_subscriptionManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  79 */     ActionForward afForward = null;
/*  80 */     SubscriptionForm form = (SubscriptionForm)a_form;
/*  81 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  84 */     ABUser user = null;
/*  85 */     if (userProfile.getIsLoggedIn())
/*     */     {
/*  87 */       user = (ABUser)userProfile.getUser();
/*     */     }
/*     */     else
/*     */     {
/*  91 */       user = form.getUser();
/*     */     }
/*     */ 
/*  95 */     long lUserId = user.getId();
/*  96 */     form.getSubscription().getRefUser().setId(lUserId);
/*     */ 
/*  99 */     Vector vecModels = this.m_subscriptionManager.getSubscriptionModels(a_dbTransaction, true);
/* 100 */     LanguageUtils.setLanguageOnAll(vecModels, userProfile.getCurrentLanguage());
/* 101 */     form.setModels(vecModels);
/*     */ 
/* 104 */     if (lUserId > 0L)
/*     */     {
/* 106 */       Iterator itModels = vecModels.iterator();
/* 107 */       while (itModels.hasNext())
/*     */       {
/* 109 */         SubscriptionModel model = (SubscriptionModel)itModels.next();
/* 110 */         long lModelId = model.getId();
/* 111 */         BrightMoney bestPrice = this.m_subscriptionManager.getBestPriceOfModelForUser(a_dbTransaction, lModelId, lUserId);
/* 112 */         model.setBestPrice(bestPrice);
/*     */ 
/* 114 */         if (bestPrice.getAmount() > 0L)
/*     */         {
/* 116 */           form.setUpgradePricesAvailable(true);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 121 */     afForward = a_mapping.findForward("Success");
/* 122 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.action.ViewSubscriptionAction
 * JD-Core Version:    0.6.0
 */