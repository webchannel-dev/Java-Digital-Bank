/*    */ package com.bright.assetbank.batch.update.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class CancelBatchAndForwardAction extends BTransactionAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 49 */     ActionForward afForward = null;
/*    */ 
/* 51 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 54 */     BatchUpdateController controller = userProfile.getBatchUpdateController();
/*    */ 
/* 56 */     if (controller == null)
/*    */     {
/* 58 */       afForward = a_mapping.findForward("Failure");
/*    */     }
/*    */     else
/*    */     {
/* 63 */       if (controller.getCancelUrl() != null)
/*    */       {
/* 65 */         afForward = createRedirectingForward(controller.getCancelUrl());
/*    */       }
/*    */ 
/* 69 */       controller.cancelCurrentBatchUpdate();
/* 70 */       userProfile.setBatchUpdateController(null);
/*    */     }
/*    */ 
/* 73 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.CancelBatchAndForwardAction
 * JD-Core Version:    0.6.0
 */