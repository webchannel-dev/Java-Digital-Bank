/*    */ package com.bright.assetbank.usage.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.usage.constant.UsageConstants;
/*    */ import com.bright.assetbank.usage.form.ReportForm;
/*    */ import com.bright.assetbank.usage.service.UsageReportManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteScheduledReportAction extends BTransactionAction
/*    */   implements UsageConstants, FrameworkConstants
/*    */ {
/* 44 */   private UsageReportManager m_usageReportManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 68 */     ActionForward afForward = null;
/* 69 */     ReportForm form = (ReportForm)a_form;
/*    */ 
/* 72 */     long lReportId = Long.parseLong(a_request.getParameter("reportId").trim());
/*    */ 
/* 75 */     this.m_usageReportManager.deleteScheduledReport(a_dbTransaction, lReportId);
/*    */ 
/* 79 */     form.setScheduledReports(this.m_usageReportManager.getScheduledReports(a_dbTransaction));
/*    */ 
/* 81 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 83 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setUsageReportManager(UsageReportManager a_usageReportManager)
/*    */   {
/* 88 */     this.m_usageReportManager = a_usageReportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.DeleteScheduledReportAction
 * JD-Core Version:    0.6.0
 */