/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
/*     */ import com.bright.assetbank.ecommerce.bean.CommercialOptionPurchase;
/*     */ import com.bright.assetbank.ecommerce.form.CommercialOrderForm;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUserCommercialOptionPurchaseAction extends BTransactionAction
/*     */ {
/*  45 */   private OrderManager m_orderManager = null;
/*     */ 
/*  52 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_orderManager)
/*     */   {
/*  49 */     this.m_orderManager = a_orderManager;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/*  56 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  86 */     ActionForward afForward = null;
/*  87 */     CommercialOrderForm form = (CommercialOrderForm)a_form;
/*     */ 
/*  89 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  92 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  94 */       this.m_logger.debug("This user does not have permission to view the order history page");
/*  95 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  97 */     Vector commercialOptionList = this.m_coManager.getCommercialOptionList(a_dbTransaction);
/*  98 */     form.setCommercialOptionsList(commercialOptionList);
/*     */ 
/* 100 */     long lOrderId = getLongParameter(a_request, "orderId");
/* 101 */     long lAssetId = getLongParameter(a_request, "assetId");
/* 102 */     long lPriceBandId = getLongParameter(a_request, "priceBandId");
/* 103 */     CommercialOptionPurchase commOptPur = this.m_orderManager.getCommercialOptionPurchase(a_dbTransaction, lPriceBandId, lOrderId, lAssetId);
/* 104 */     commOptPur.getCommercialOption().setLanguage(userProfile.getCurrentLanguage());
/* 105 */     form.setCommercialOptionPurchase(commOptPur);
/*     */ 
/* 107 */     afForward = a_mapping.findForward("Success");
/* 108 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewUserCommercialOptionPurchaseAction
 * JD-Core Version:    0.6.0
 */