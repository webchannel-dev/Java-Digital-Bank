/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBasket;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
/*     */ import com.bright.assetbank.ecommerce.form.CheckoutForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collection;
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
/*     */ public class ViewReviewPurchaseCommercialOptionsAction extends BTransactionAction
/*     */ {
/*  49 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/*  55 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/*  52 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/*  58 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     ActionForward afForward = null;
/*  88 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  91 */     this.m_assetBoxManager.refreshAssetBoxTax(a_dbTransaction, userProfile);
/*     */ 
/*  94 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  96 */       this.m_logger.debug("This user does not have permission to view the review commercial options page");
/*  97 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 101 */     CheckoutForm form = (CheckoutForm)a_form;
/* 102 */     form.getErrors().clear();
/*     */ 
/* 105 */     for (Iterator itCommOpt = userProfile.getBasket().getAssetPriceCommercialOptions().values().iterator(); itCommOpt.hasNext(); )
/*     */     {
/* 107 */       CommercialOption commOptInBox = (CommercialOption)itCommOpt.next();
/*     */ 
/* 109 */       CommercialOption commOptComplete = this.m_coManager.getCommercialOption(a_dbTransaction, commOptInBox.getId());
/* 110 */       commOptInBox.copyFrom(commOptComplete);
/* 111 */       commOptInBox.setLanguage(userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 114 */     form.setCommercialOptionsList(this.m_coManager.getCommercialOptionList(a_dbTransaction));
/*     */ 
/* 117 */     userProfile.getBasket().calculateCommercialCost();
/*     */ 
/* 119 */     afForward = a_mapping.findForward("Success");
/* 120 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewReviewPurchaseCommercialOptionsAction
 * JD-Core Version:    0.6.0
 */