/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.bean.OrderSearchCriteria;
/*     */ import com.bright.assetbank.ecommerce.form.OrderForm;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewOrderOverviewAction extends BTransactionAction
/*     */ {
/*  51 */   private OrderManager m_orderManager = null;
/*     */ 
/*  58 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_orderManager)
/*     */   {
/*  55 */     this.m_orderManager = a_orderManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  61 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     ActionForward afForward = null;
/*  92 */     OrderForm form = (OrderForm)a_form;
/*  93 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  96 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  98 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  99 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 102 */     OrderSearchCriteria searchFilter = null;
/* 103 */     String cacheParam = a_request.getParameter("orderSearchCache");
/*     */ 
/* 105 */     if ((cacheParam != null) && (cacheParam.compareTo("true") == 0) && (userProfile.getOrderSearchCriteria() != null))
/*     */     {
/* 108 */       searchFilter = userProfile.getOrderSearchCriteria();
/* 109 */       form.setSearchFields(searchFilter);
/*     */     }
/*     */     else
/*     */     {
/* 114 */       searchFilter = form.getSearchCriteria(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */     }
/*     */ 
/* 118 */     userProfile.setOrderSearchCriteria(searchFilter);
/*     */ 
/* 123 */     Vector vecOrderStatusList = this.m_orderManager.getOrderStatusList(a_dbTransaction, searchFilter.getOrderWorkflow(), false);
/*     */ 
/* 126 */     form.setOrderStatusList(vecOrderStatusList);
/*     */ 
/* 129 */     Vector vecPriceBandTypeSearchList = this.m_orderManager.getPriceBandTypeSearchList(searchFilter.getOrderWorkflow());
/* 130 */     form.setOrderPriceBandTypeSearchList(vecPriceBandTypeSearchList);
/*     */ 
/* 132 */     if (!form.getHasErrors())
/*     */     {
/* 135 */       Vector vecOrderList = this.m_orderManager.getOrders(a_dbTransaction, searchFilter);
/* 136 */       form.setOrderList(vecOrderList);
/*     */     }
/* 138 */     afForward = a_mapping.findForward("Success");
/* 139 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewOrderOverviewAction
 * JD-Core Version:    0.6.0
 */