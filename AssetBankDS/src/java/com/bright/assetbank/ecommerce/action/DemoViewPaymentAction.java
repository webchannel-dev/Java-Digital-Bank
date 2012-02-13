/*    */ package com.bright.assetbank.ecommerce.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.common.bean.BrightMoney;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DemoViewPaymentAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 46 */     ActionForward afForward = null;
/*    */ 
/* 49 */     String sAmount = a_request.getParameter("amount");
/* 50 */     String sDisplayAmount = "";
/* 51 */     if (StringUtil.stringIsPopulated(sAmount))
/*    */     {
/* 53 */       BrightMoney amount = new BrightMoney();
/* 54 */       amount.setFormAmount(sAmount);
/* 55 */       amount.processFormData();
/* 56 */       sDisplayAmount = amount.getDisplayAmount();
/*    */     }
/*    */ 
/* 59 */     a_request.setAttribute("displayamount", sDisplayAmount);
/*    */ 
/* 61 */     afForward = a_mapping.findForward("Success");
/* 62 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.DemoViewPaymentAction
 * JD-Core Version:    0.6.0
 */