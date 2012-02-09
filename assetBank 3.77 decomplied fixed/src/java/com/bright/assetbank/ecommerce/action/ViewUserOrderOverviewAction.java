/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.bean.OrderSearchCriteria;
/*     */ import com.bright.assetbank.ecommerce.form.OrderForm;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUserOrderOverviewAction extends BTransactionAction
/*     */ {
/*  54 */   private OrderManager m_orderManager = null;
/*     */ 
/*  61 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_orderManager)
/*     */   {
/*  58 */     this.m_orderManager = a_orderManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  64 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  93 */     ActionForward afForward = null;
/*  94 */     OrderForm form = (OrderForm)a_form;
/*     */ 
/*  96 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  99 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 101 */       this.m_logger.debug("This user does not have permission to view the order history page");
/* 102 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 105 */     OrderSearchCriteria searchFilter = null;
/* 106 */     String cacheParam = a_request.getParameter("orderSearchCache");
/*     */ 
/* 108 */     if ((cacheParam != null) && (cacheParam.compareTo("true") == 0) && (userProfile.getOrderSearchCriteria() != null))
/*     */     {
/* 111 */       searchFilter = userProfile.getOrderSearchCriteria();
/* 112 */       form.setSearchFields(searchFilter);
/*     */     }
/*     */     else
/*     */     {
/* 117 */       searchFilter = form.getSearchCriteria(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */     }
/*     */ 
/* 120 */     searchFilter.setUserId(userProfile.getUser().getId());
/* 121 */     userProfile.setOrderSearchCriteria(searchFilter);
/*     */ 
/* 124 */     Vector vecOrderStatusList = this.m_orderManager.getOrderStatusList(a_dbTransaction, searchFilter.getOrderWorkflow(), false);
/*     */ 
/* 128 */     if (!userProfile.getCurrentLanguage().equals(LanguageConstants.k_defaultLanguage))
/*     */     {
/* 130 */       LanguageUtils.setLanguageOnAll(vecOrderStatusList, userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 133 */     form.setOrderStatusList(vecOrderStatusList);
/*     */ 
/* 135 */     if (!form.getHasErrors())
/*     */     {
/* 138 */       Vector vecOrderList = this.m_orderManager.getOrders(a_dbTransaction, searchFilter, userProfile.getCurrentLanguage());
/* 139 */       form.setOrderList(vecOrderList);
/*     */     }
/*     */ 
/* 142 */     afForward = a_mapping.findForward("Success");
/* 143 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewUserOrderOverviewAction
 * JD-Core Version:    0.6.0
 */