/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.usage.constant.UsageConstants;
/*     */ import com.bright.assetbank.usage.form.ReportForm;
/*     */ import com.bright.assetbank.usage.service.UsageReportManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveScheduledReportAction extends BTransactionAction
/*     */   implements UsageConstants, FrameworkConstants
/*     */ {
/*  46 */   private UsageReportManager m_usageReportManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ReportForm form = (ReportForm)a_form;
/*  75 */     long[] lUserGroupsSelected = null;
/*  76 */     long lNewId = 0L;
/*     */ 
/*  79 */     if (a_request.getParameter("Cancel") != null)
/*     */     {
/*  81 */       form.setScheduledReports(this.m_usageReportManager.getScheduledReports(a_dbTransaction));
/*     */ 
/*  83 */       afForward = a_mapping.findForward("Cancel");
/*     */     }
/*  88 */     else if (a_request.getParameter("reportType") != null)
/*     */     {
/*  91 */       String sReportFrequency = a_request.getParameter("reportFrequency");
/*     */ 
/*  93 */       String sReportType = a_request.getParameter("reportType");
/*  94 */       String sReportName = a_request.getParameter("reportName");
/*     */ 
/*  97 */       if (!StringUtil.stringIsPopulated(sReportName))
/*     */       {
/*  99 */         form.addError("A report name must be specified");
/* 100 */         afForward = a_mapping.findForward("Failure");
/*     */       }
/*     */       else
/*     */       {
/* 105 */         lNewId = this.m_usageReportManager.createScheduledReport(a_dbTransaction, this.m_usageReportManager.getNextSendDate(sReportFrequency), sReportFrequency, sReportType, sReportName);
/*     */ 
/* 110 */         lUserGroupsSelected = form.getGroupSelectedList();
/* 111 */         if (lUserGroupsSelected != null)
/*     */         {
/* 113 */           for (int x = 0; x < lUserGroupsSelected.length; x++)
/*     */           {
/* 115 */             this.m_usageReportManager.createScheduledReportGroup(a_dbTransaction, lNewId, lUserGroupsSelected[x]);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 121 */         form.setScheduledReports(this.m_usageReportManager.getScheduledReports(a_dbTransaction));
/*     */ 
/* 124 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 130 */       form.setScheduledReports(this.m_usageReportManager.getScheduledReports(a_dbTransaction));
/*     */ 
/* 132 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 135 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setUsageReportManager(UsageReportManager a_usageReportManager)
/*     */   {
/* 140 */     this.m_usageReportManager = a_usageReportManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.SaveScheduledReportAction
 * JD-Core Version:    0.6.0
 */