/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ReactivateAccountAction extends UserAction
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     int iUserId = getIntParameter(a_request, "id");
/*    */ 
/* 54 */     getUserManager().updateDateLastLoggedIn(a_dbTransaction, iUserId);
/*    */ 
/* 57 */     ABUser user = (ABUser)getUserManager().getUser(a_dbTransaction, iUserId);
/*    */ 
/* 60 */     user.setReactivationPending(false);
/*    */ 
/* 63 */     getUserManager().saveUser(a_dbTransaction, user);
/*    */ 
/* 65 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ReactivateAccountAction
 * JD-Core Version:    0.6.0
 */