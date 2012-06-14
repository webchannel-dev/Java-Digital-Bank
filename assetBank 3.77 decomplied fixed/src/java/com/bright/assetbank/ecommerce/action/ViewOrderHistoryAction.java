/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
import com.bright.assetbank.usage.bean.ReportEntity;
/*     */ import com.bright.assetbank.usage.bean.ReportEntity.CountComparatorAsc;
/*     */ import com.bright.assetbank.usage.bean.ReportEntity.CountComparatorDesc;
/*     */ import com.bright.assetbank.usage.form.ReportForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewOrderHistoryAction extends BTransactionAction
/*     */ {
/*  49 */   private OrderManager m_orderManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_Manager) {
/*  52 */     this.m_orderManager = a_Manager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/*  83 */       ReportForm form = (ReportForm)a_form;
/*  84 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  87 */       if (!userProfile.getIsLoggedIn())
/*     */       {
/*  89 */         this.m_logger.debug("This user does not have permission to view the order history page");
/*  90 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*  93 */       form.processReportDates();
/*  94 */       Date dtStartDate = form.getStartDate();
/*  95 */       Date dtEndDate = form.getEndDate();
/*     */ 
/*  98 */       Vector vResults = this.m_orderManager.getOrderHistory(a_dbTransaction, dtStartDate, dtEndDate, userProfile.getUser().getId());
/*     */ 
/* 104 */       if (vResults != null)
/*     */       {
/* 106 */         if (form.getSortAscending())
/*     */         {
/* 108 */           Collections.sort(vResults, new ReportEntity.CountComparatorAsc());
/*     */         }
/*     */         else
/*     */         {
/* 112 */           Collections.sort(vResults, new ReportEntity.CountComparatorDesc());
/*     */         }
/*     */       }
/*     */ 
/* 116 */       form.setDetails(vResults);
/*     */     }
/*     */     catch (Bn2Exception bn2)
/*     */     {
/* 120 */       this.m_logger.error("Exception in ViewOrderHistory: " + bn2.getMessage());
/* 121 */       throw bn2;
/*     */     }
/*     */ 
/* 124 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewOrderHistoryAction
 * JD-Core Version:    0.6.0
 */