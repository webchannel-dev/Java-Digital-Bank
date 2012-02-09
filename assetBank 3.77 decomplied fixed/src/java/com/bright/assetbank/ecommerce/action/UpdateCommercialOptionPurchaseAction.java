/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.ecommerce.bean.CommercialOptionPurchase;
/*     */ import com.bright.assetbank.ecommerce.bean.Order;
/*     */ import com.bright.assetbank.ecommerce.form.CommercialOrderForm;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class UpdateCommercialOptionPurchaseAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  47 */   private OrderManager m_orderManager = null;
/*     */ 
/*  54 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_orderManager)
/*     */   {
/*  51 */     this.m_orderManager = a_orderManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  57 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  86 */     ActionForward afForward = null;
/*  87 */     CommercialOrderForm form = (CommercialOrderForm)a_form;
/*  88 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  91 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  93 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  94 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  98 */     BrightMoney price = form.getCommercialOptionPurchase().getPrice();
/*  99 */     if ((!price.getIsFormAmountEntered()) || (!price.processFormData()))
/*     */     {
/* 101 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "commercialOptionInvalidPrice", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 105 */     if (!form.getHasErrors())
/*     */     {
/* 107 */       long lOrderId = getLongParameter(a_request, "orderId");
/* 108 */       long lAssetId = getLongParameter(a_request, "assetId");
/* 109 */       long lPriceBandId = getLongParameter(a_request, "priceBandId");
/*     */ 
/* 111 */       CommercialOptionPurchase newCommOptPurchase = form.getCommercialOptionPurchase();
/* 112 */       CommercialOptionPurchase oldCommOptPurchase = this.m_orderManager.getCommercialOptionPurchase(a_dbTransaction, lPriceBandId, lOrderId, lAssetId);
/*     */ 
/* 114 */       this.m_orderManager.updateCommercialOptionPurchase(a_dbTransaction, newCommOptPurchase, lPriceBandId, lOrderId, lAssetId);
/*     */ 
/* 117 */       if ((oldCommOptPurchase != null) && (newCommOptPurchase.getCommercialOption().getId() != oldCommOptPurchase.getCommercialOption().getId()))
/*     */       {
/* 119 */         this.m_orderManager.sendCommercialOptionChangedNotification(a_dbTransaction, oldCommOptPurchase, lOrderId, lAssetId, lPriceBandId);
/*     */       }
/*     */ 
/* 122 */       Order order = this.m_orderManager.getOrder(a_dbTransaction, lOrderId);
/* 123 */       order.recalculateCommercialCost();
/* 124 */       this.m_orderManager.updateOrderTotals(a_dbTransaction, order);
/*     */ 
/* 127 */       this.m_orderManager.updateAssetPurchaseLog(a_dbTransaction, lAssetId, lOrderId, price);
/*     */ 
/* 130 */       String sQueryString = "orderId=" + lOrderId;
/* 131 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 135 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 138 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.UpdateCommercialOptionPurchaseAction
 * JD-Core Version:    0.6.0
 */