/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.message.constant.MessageConstants;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteUserAction extends UserAction
/*    */   implements MessageConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 79 */     ActionForward afForward = null;
/* 80 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 83 */     long lUserId = getIntParameter(a_request, "id");
/*    */ 
/* 85 */     if (!getUserManager().deleteUserOrRemoveFromOU(a_dbTransaction, lUserId, userProfile))
/*    */     {
/* 87 */       this.m_logger.error("DeleteUserAction.execute : User does not have admin permission : " + userProfile);
/* 88 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 92 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 94 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.DeleteUserAction
 * JD-Core Version:    0.6.0
 */