/*    */ package com.bright.assetbank.usage.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.usage.constant.UsageConstants;
/*    */ import com.bright.assetbank.usage.form.ReportForm;
/*    */ import com.bright.assetbank.usage.service.UsageReportManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.util.Date;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DownloadReportAction extends Bn2Action
/*    */   implements UsageConstants, FrameworkConstants
/*    */ {
/* 50 */   private UsageReportManager m_usageReportManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 68 */     ReportForm form = (ReportForm)a_form;
/* 69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 70 */     String sFilename = null;
/*    */ 
/* 73 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*    */     {
/* 75 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 76 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 79 */     form.processReportDates();
/*    */ 
/* 81 */     Date dtStartDate = form.getStartDate();
/* 82 */     Date dtEndDate = form.getEndDate();
/*    */ 
/* 85 */     sFilename = this.m_usageReportManager.createReport(form.getReportType(), dtStartDate, dtEndDate, form.getSortAscending(), form.getUsername());
/*    */ 
/* 87 */     a_request.setAttribute("downloadFile", FileUtil.encryptFilepath(sFilename));
/* 88 */     a_request.setAttribute("downloadFilename", "usage_report.xls");
/*    */ 
/* 90 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setUsageReportManager(UsageReportManager a_usageReportManager)
/*    */   {
/* 95 */     this.m_usageReportManager = a_usageReportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.DownloadReportAction
 * JD-Core Version:    0.6.0
 */