/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
/*     */ import com.bright.assetbank.ecommerce.bean.Order;
/*     */ import com.bright.assetbank.ecommerce.bean.OrderStatus;
/*     */ import com.bright.assetbank.ecommerce.form.OrderForm;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.StringDataBean;
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
/*     */ public class ViewOrderDetailsAction extends BTransactionAction
/*     */ {
/*  50 */   private OrderManager m_orderManager = null;
/*     */ 
/*  57 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_orderManager)
/*     */   {
/*  54 */     this.m_orderManager = a_orderManager;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/*  60 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  90 */     ActionForward afForward = null;
/*  91 */     OrderForm form = (OrderForm)a_form;
/*  92 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  95 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  97 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  98 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 102 */     Vector commercialOptions = this.m_coManager.getCommercialOptionList(a_dbTransaction);
/*     */ 
/* 104 */     if (!commercialOptions.isEmpty())
/*     */     {
/* 106 */       form.setCommercialOptionsExist(true);
/*     */     }
/*     */ 
/* 109 */     long lOrderId = getLongParameter(a_request, "orderId");
/* 110 */     if (lOrderId > 0L)
/*     */     {
/* 112 */       if (!form.getHasErrors())
/*     */       {
/* 114 */         Order order = this.m_orderManager.getOrder(a_dbTransaction, lOrderId);
/* 115 */         form.setOrder(order);
/*     */ 
/* 117 */         Vector vecOrderStatus = this.m_orderManager.getOrderStatusList(a_dbTransaction, order.getStatus().getOrderWorkflow().getId(), true);
/*     */ 
/* 120 */         form.setOrderStatusList(vecOrderStatus);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 132 */     afForward = a_mapping.findForward("Success");
/* 133 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewOrderDetailsAction
 * JD-Core Version:    0.6.0
 */