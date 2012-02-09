/*    */ package com.bright.framework.struts;
/*    */ 
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ import org.apache.struts.action.ExceptionHandler;
/*    */ import org.apache.struts.config.ExceptionConfig;
/*    */ 
/*    */ public class BrightExceptionHandler extends ExceptionHandler
/*    */ {
/* 39 */   private static Log m_logger = GlobalApplication.getInstance().getLogger();
/*    */ 
/*    */   public ActionForward execute(Exception a_ex, ExceptionConfig a_ae, ActionMapping a_mapping, ActionForm a_formInstance, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws ServletException
/*    */   {
/* 64 */     String sURLInformation = "";
/*    */     try
/*    */     {
/* 67 */       StringBuffer sbURL = a_request.getRequestURL();
/* 68 */       String sQueryString = a_request.getQueryString();
/* 69 */       if ((sQueryString != null) && (sbURL != null))
/*    */       {
/* 71 */         sbURL.append("?").append(sQueryString);
/*    */       }
/* 73 */       sURLInformation = " Requested URL = " + sbURL;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 77 */       m_logger.error("Error determining URL", e);
/*    */     }
/*    */ 
/* 80 */     m_logger.error("BrightExceptionHandler invoked - about to display error page." + sURLInformation, a_ex);
/*    */ 
/* 82 */     return super.execute(a_ex, a_ae, a_mapping, a_formInstance, a_request, a_response);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.struts.BrightExceptionHandler
 * JD-Core Version:    0.6.0
 */