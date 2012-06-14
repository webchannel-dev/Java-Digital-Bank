/*    */ package com.bright.assetbank.usage.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.usage.constant.UsageConstants;
/*    */ import com.bright.assetbank.usage.service.UsageReportManager;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class TestScheduledReportAction extends Bn2Action
/*    */   implements UsageConstants, FrameworkConstants
/*    */ {
/* 44 */   private UsageReportManager m_usageReportManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 68 */     ActionForward afForward = null;
/*    */ 
/* 70 */     int iForce = getIntParameter(a_request, "force");
/* 71 */     boolean bForce = false;
/* 72 */     if (iForce > 0)
/*    */     {
/* 74 */       bForce = true;
/*    */     }
/*    */ 
/* 78 */     this.m_usageReportManager.processScheduledReports(bForce);
/*    */ 
/* 80 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 82 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setUsageReportManager(UsageReportManager a_usageReportManager)
/*    */   {
/* 87 */     this.m_usageReportManager = a_usageReportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.TestScheduledReportAction
 * JD-Core Version:    0.6.0
 */