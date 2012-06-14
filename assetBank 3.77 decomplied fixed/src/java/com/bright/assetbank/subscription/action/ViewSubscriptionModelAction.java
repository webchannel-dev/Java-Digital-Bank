/*     */ package com.bright.assetbank.subscription.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionModel;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionModelUpgrade;
/*     */ import com.bright.assetbank.subscription.form.SubscriptionModelsForm;
/*     */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*     */ import com.bright.assetbank.user.action.UserAction;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.service.LanguageManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewSubscriptionModelAction extends UserAction
/*     */ {
/*  52 */   private SubscriptionManager m_subscriptionManager = null;
/*     */ 
/* 162 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager)
/*     */   {
/*  55 */     this.m_subscriptionManager = a_subscriptionManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  79 */     ActionForward afForward = null;
/*  80 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  81 */     SubscriptionModelsForm form = (SubscriptionModelsForm)a_form;
/*     */ 
/*  84 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  86 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  87 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  90 */     long lId = getLongParameter(a_request, "id");
/*     */ 
/*  92 */     if (!form.getHasErrors())
/*     */     {
/*  94 */       if (lId > 0L)
/*     */       {
/*  96 */         SubscriptionModel model = this.m_subscriptionManager.getSubscriptionModel(a_dbTransaction, lId);
/*  97 */         form.setModel(model);
/*     */       }
/*     */       else
/*     */       {
/* 102 */         this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getModel());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 111 */     Vector vecGroups = getUserManager().getAllGroups();
/* 112 */     form.setGroups(vecGroups);
/*     */ 
/* 115 */     Vector vecAllSMs = this.m_subscriptionManager.getSubscriptionModels(a_dbTransaction, false);
/* 116 */     form.setModels(vecAllSMs);
/*     */ 
/* 119 */     if (!form.getHasErrors())
/*     */     {
/* 122 */       HashMap hmUpgradeSelected = new HashMap();
/* 123 */       HashMap hmUpgradePrices = new HashMap();
/*     */ 
/* 126 */       Iterator itModels = vecAllSMs.iterator();
/* 127 */       while (itModels.hasNext())
/*     */       {
/* 130 */         SubscriptionModel newModel = (SubscriptionModel)itModels.next();
/* 131 */         long lNewModelId = newModel.getId();
/*     */ 
/* 134 */         boolean bModelHasUpgrade = false;
/* 135 */         String sModelUpgradePrice = "";
/*     */ 
/* 137 */         Iterator itUpgrades = form.getModel().getUpgrades().iterator();
/* 138 */         while (itUpgrades.hasNext())
/*     */         {
/* 140 */           SubscriptionModelUpgrade upgrade = (SubscriptionModelUpgrade)itUpgrades.next();
/*     */ 
/* 142 */           if (upgrade.getToModelId() == lNewModelId)
/*     */           {
/* 144 */             bModelHasUpgrade = true;
/* 145 */             sModelUpgradePrice = upgrade.getPrice().getFormAmount();
/*     */           }
/*     */         }
/*     */ 
/* 149 */         hmUpgradeSelected.put(new Long(lNewModelId), new Boolean(bModelHasUpgrade));
/* 150 */         hmUpgradePrices.put(new Long(lNewModelId), sModelUpgradePrice);
/*     */       }
/*     */ 
/* 153 */       form.setUpgradeSelected(hmUpgradeSelected);
/* 154 */       form.setUpgradePrices(hmUpgradePrices);
/*     */     }
/*     */ 
/* 158 */     afForward = a_mapping.findForward("Success");
/* 159 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 165 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.action.ViewSubscriptionModelAction
 * JD-Core Version:    0.6.0
 */