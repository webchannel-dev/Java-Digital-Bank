/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.bean.Order;
/*     */ import com.bright.assetbank.ecommerce.bean.OrderStatus;
/*     */ import com.bright.assetbank.ecommerce.form.OrderForm;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class UpdateOrderStatusAction extends BTransactionAction
/*     */ {
/*  40 */   private OrderManager m_orderManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_orderManager)
/*     */   {
/*  44 */     this.m_orderManager = a_orderManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  75 */     OrderForm form = (OrderForm)a_form;
/*     */ 
/*  78 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  80 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  81 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  84 */     long lOrderId = getLongParameter(a_request, "orderId");
/*  85 */     if (lOrderId > 0L)
/*     */     {
/*  90 */       long lOrderStatus = form.getOrder().getStatus().getId();
/*  91 */       if (lOrderStatus > 0L)
/*     */       {
/*  93 */         this.m_orderManager.updateOrderStatus(a_dbTransaction, lOrderId, lOrderStatus);
/*     */ 
/*  95 */         switch ((int)lOrderStatus)
/*     */         {
/*     */         case 1:
/*  98 */           this.m_orderManager.sendConfirmOrderShipmentEmail(a_dbTransaction, lOrderId);
/*  99 */           break;
/*     */         case 4:
/* 101 */           this.m_orderManager.sendApprovedPayOnline(a_dbTransaction, lOrderId);
/* 102 */           break;
/*     */         case 5:
/* 104 */           this.m_orderManager.sendApprovedPayOffline(a_dbTransaction, lOrderId);
/* 105 */           break;
/*     */         case 7:
/* 107 */           this.m_orderManager.approveOrderAssetsForUser(a_dbTransaction, lOrderId);
/* 108 */           this.m_orderManager.sendConfirmOrderPurchaseEmail(a_dbTransaction, lOrderId, userProfile.getCurrentLanguage(), null);
/* 109 */           break;
/*     */         case 8:
/* 111 */           this.m_orderManager.sendOrderDeclined(a_dbTransaction, lOrderId);
/* 112 */           this.m_orderManager.rejectOrderAssetsForUser(a_dbTransaction, lOrderId);
/* 113 */           break;
/*     */         case 2:
/*     */         case 3:
/*     */         case 6:
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 121 */     String sQueryString = "orderId=" + lOrderId;
/* 122 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 124 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.UpdateOrderStatusAction
 * JD-Core Version:    0.6.0
 */