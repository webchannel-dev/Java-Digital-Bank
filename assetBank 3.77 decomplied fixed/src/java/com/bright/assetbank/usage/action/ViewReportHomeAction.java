/*    */ package com.bright.assetbank.usage.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bright.assetbank.usage.constant.UsageConstants;
/*    */ import com.bright.assetbank.usage.form.ReportForm;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewReportHomeAction extends Bn2Action
/*    */   implements UsageConstants, FrameworkConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Exception
/*    */   {
/* 67 */     ActionForward afForward = null;
/* 68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 70 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*    */     {
/* 72 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 73 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 76 */     ReportForm form = (ReportForm)a_form;
/*    */ 
/* 79 */     if (a_request.getParameter("reportType") != null)
/*    */     {
/* 81 */       int reportType = Integer.parseInt(a_request.getParameter("reportType"));
/* 82 */       form.setReportType(reportType);
/*    */     }
/*    */     else
/*    */     {
/* 86 */       form.setReportType(14);
/*    */     }
/*    */ 
/* 90 */     form.setStartDateString(a_request.getParameter("startDateString"));
/* 91 */     form.setEndDateString(a_request.getParameter("endDateString"));
/*    */ 
/* 93 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 95 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewReportHomeAction
 * JD-Core Version:    0.6.0
 */