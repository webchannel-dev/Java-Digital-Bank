/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteDivisionAction extends BTransactionAction
/*    */ {
/* 41 */   private ABUserManager m_userManager = null;
/*    */ 
/*    */   public void setUserManager(ABUserManager a_userManager) {
/* 44 */     this.m_userManager = a_userManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 72 */     ActionForward afForward = null;
/* 73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 76 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 78 */       this.m_logger.error("ListDivisionsAction.execute : User must be an admin.");
/* 79 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 83 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 86 */     this.m_userManager.deleteDivision(a_dbTransaction, lId);
/*    */ 
/* 88 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 90 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.DeleteDivisionAction
 * JD-Core Version:    0.6.0
 */