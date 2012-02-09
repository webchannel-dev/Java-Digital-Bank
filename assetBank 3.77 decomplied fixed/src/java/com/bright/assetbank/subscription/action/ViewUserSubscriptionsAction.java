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
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUserSubscriptionsAction extends BTransactionAction
/*     */ {
/*  50 */   private SubscriptionManager m_subscriptionManager = null;
/*     */ 
/*     */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager) {
/*  53 */     this.m_subscriptionManager = a_subscriptionManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     ActionForward afForward = null;
/*  78 */     SubscriptionForm form = (SubscriptionForm)a_form;
/*  79 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  82 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  84 */       this.m_logger.debug("This user is not logged in.");
/*  85 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  87 */     ABUser user = (ABUser)userProfile.getUser();
/*  88 */     long lUserId = user.getId();
/*     */ 
/*  91 */     Vector vecSubs = this.m_subscriptionManager.getSubscriptions(a_dbTransaction, lUserId, false);
/*  92 */     if (!LanguageConstants.k_defaultLanguage.equals(userProfile.getCurrentLanguage()))
/*     */     {
/*  94 */       for (int i = 0; i < vecSubs.size(); i++)
/*     */       {
/*  96 */         ((Subscription)vecSubs.get(i)).getModel().setLanguage(userProfile.getCurrentLanguage());
/*     */       }
/*     */     }
/*  99 */     form.setSubscriptions(vecSubs);
/*     */ 
/* 102 */     form.resetSubscription();
/*     */ 
/* 105 */     String sStart = a_request.getParameter("start");
/* 106 */     if (StringUtil.stringIsPopulated(sStart))
/*     */     {
/* 108 */       form.resetAll();
/*     */     }
/*     */ 
/* 113 */     long lDownloadsLeft = this.m_subscriptionManager.getRemainingDownloadsTodayForUser(a_dbTransaction, lUserId);
/* 114 */     userProfile.setDownloadsLeft(lDownloadsLeft);
/*     */ 
/* 117 */     boolean bHasValidSubscription = this.m_subscriptionManager.getUserHasValidSubscription(a_dbTransaction, user.getId());
/* 118 */     userProfile.setUserHasValidSubscription(bHasValidSubscription);
/*     */ 
/* 120 */     afForward = a_mapping.findForward("Success");
/* 121 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.action.ViewUserSubscriptionsAction
 * JD-Core Version:    0.6.0
 */