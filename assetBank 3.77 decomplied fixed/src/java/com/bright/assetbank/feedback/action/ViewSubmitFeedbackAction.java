/*    */ package com.bright.assetbank.feedback.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.feedback.bean.AssetFeedback;
/*    */ import com.bright.assetbank.feedback.form.FeedbackForm;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewSubmitFeedbackAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 62 */     ActionForward afForward = null;
/* 63 */     FeedbackForm form = (FeedbackForm)a_form;
/*    */ 
/* 66 */     long lAssetId = getLongParameter(a_request, "id");
/* 67 */     form.getFeedback().setAssetId(lAssetId);
/*    */ 
/* 69 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 71 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.feedback.action.ViewSubmitFeedbackAction
 * JD-Core Version:    0.6.0
 */