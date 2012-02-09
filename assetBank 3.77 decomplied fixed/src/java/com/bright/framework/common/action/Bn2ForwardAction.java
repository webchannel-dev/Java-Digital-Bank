/*    */ package com.bright.framework.common.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class Bn2ForwardAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 55 */     ActionForward afForward = null;
/*    */ 
/* 58 */     String sForward = a_request.getParameter("bforward");
/*    */ 
/* 60 */     if (sForward != null)
/*    */     {
/* 63 */       boolean bRedirect = false;
/* 64 */       String sRedirect = a_request.getParameter("redirect");
/* 65 */       if ((sRedirect != null) && (sRedirect.equals("true")))
/*    */       {
/* 67 */         bRedirect = true;
/*    */       }
/*    */ 
/* 71 */       afForward = new ActionForward(sForward);
/* 72 */       afForward.setRedirect(bRedirect);
/*    */     }
/*    */     else
/*    */     {
/* 78 */       afForward = a_mapping.findForward("Success");
/*    */     }
/*    */ 
/* 83 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.action.Bn2ForwardAction
 * JD-Core Version:    0.6.0
 */