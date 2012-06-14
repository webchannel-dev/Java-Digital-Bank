/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.ecommerce.bean.UploadersReportRecord;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceConstants;
/*     */ import com.bright.assetbank.ecommerce.form.ReportForm;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
import com.bright.assetbank.usage.bean.ReportEntity;
/*     */ import com.bright.assetbank.usage.bean.ReportEntity.CountComparatorAsc;
/*     */ import com.bright.assetbank.usage.bean.ReportEntity.CountComparatorDesc;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
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
/*     */ public class ViewReportAction extends BTransactionAction
/*     */   implements EcommerceConstants, FrameworkConstants
/*     */ {
/*  59 */   private OrderManager m_orderManager = null;
/*     */ 
/*  65 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_Manager)
/*     */   {
/*  62 */     this.m_orderManager = a_Manager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  68 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  95 */     ActionForward afForward = null;
/*  96 */     ReportForm form = (ReportForm)a_form;
/*  97 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 100 */     if (!userProfile.getIsAdmin())
/*     */     {
/* 102 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 103 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 106 */     form.validate(a_request, userProfile, a_transaction, this.m_listManager);
/*     */ 
/* 108 */     if (!form.getHasErrors())
/*     */     {
/* 110 */       form.processReportDates();
/* 111 */       Date dtStartDate = form.getStartDate();
/* 112 */       Date dtEndDate = form.getEndDate();
/*     */ 
/* 116 */       Vector vResults = null;
/*     */ 
/* 118 */       switch (form.getReportType())
/*     */       {
/*     */       case 1:
/* 122 */         vResults = this.m_orderManager.getOrderHistory(a_transaction, dtStartDate, dtEndDate, -1L);
/*     */ 
/* 126 */         afForward = a_mapping.findForward("OrderDetailReport");
/*     */ 
/* 129 */         if (vResults == null)
/*     */           break;
/* 131 */         if (form.getSortAscending())
/*     */         {
/* 133 */           Collections.sort(vResults, new ReportEntity.CountComparatorAsc());
/*     */         }
/*     */         else
/*     */         {
/* 137 */           Collections.sort(vResults, new ReportEntity.CountComparatorDesc()); } break;
/*     */       case 2:
/* 147 */         vResults = this.m_orderManager.getUploadersReportLines(a_transaction, dtStartDate, dtEndDate);
/*     */ 
/* 151 */         BrightMoney total = new BrightMoney(0L);
/*     */ 
/* 153 */         if (vResults != null)
/*     */         {
/* 156 */           for (int i = 0; i < vResults.size(); i++)
/*     */           {
/* 158 */             UploadersReportRecord temp = (UploadersReportRecord)vResults.elementAt(i);
/* 159 */             total.setAmount(total.getAmount() + temp.getTotalIncome().getAmount());
/*     */           }
/*     */         }
/*     */ 
/* 163 */         form.setTotal(total);
/* 164 */         afForward = a_mapping.findForward("UploadersReport");
/* 165 */         break;
/*     */       case 3:
/* 170 */         vResults = this.m_orderManager.getSubscriptionReportLines(a_transaction, dtStartDate, dtEndDate, form.getShowActiveSubscriptions(), form.getShowActiveSubscriptionModels());
/*     */ 
/* 177 */         afForward = a_mapping.findForward("SubscriptionReport");
/* 178 */         break;
/*     */       case 4:
/* 183 */         vResults = this.m_orderManager.getUserSubscriptionReportLines(a_transaction, form.getUsername());
/*     */ 
/* 185 */         afForward = a_mapping.findForward("UserSubscriptionReport");
/*     */       }
/*     */ 
/* 191 */       form.setDetails(vResults);
/*     */     }
/*     */     else
/*     */     {
/* 195 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 198 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewReportAction
 * JD-Core Version:    0.6.0
 */