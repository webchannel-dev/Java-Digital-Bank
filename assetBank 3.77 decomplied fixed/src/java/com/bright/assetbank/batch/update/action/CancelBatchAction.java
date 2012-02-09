/*    */ package com.bright.assetbank.batch.update.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class CancelBatchAction extends BTransactionAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 55 */     BatchUpdateController controller = userProfile.getBatchUpdateController();
/*    */ 
/* 57 */     if (controller != null)
/*    */     {
/* 60 */       controller.cancelCurrentBatchUpdate();
/* 61 */       userProfile.setBatchUpdateController(null);
/*    */     }
/*    */ 
/* 65 */     if (StringUtil.stringIsPopulated(controller.getCancelUrl()))
/*    */     {
/* 67 */       return createRedirectingForward(controller.getCancelUrl());
/*    */     }
/*    */ 
/* 70 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.CancelBatchAction
 * JD-Core Version:    0.6.0
 */