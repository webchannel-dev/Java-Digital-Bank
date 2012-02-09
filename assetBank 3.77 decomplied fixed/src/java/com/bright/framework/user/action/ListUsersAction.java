/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.constant.CommonConstants;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.constant.UserConstants;
/*    */ import com.bright.framework.user.form.ListUsersForm;
/*    */ import com.bright.framework.user.service.UserManager;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ListUsersAction extends BTransactionAction
/*    */   implements CommonConstants, UserConstants
/*    */ {
/* 43 */   protected UserManager m_userManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 67 */     this.m_logger.debug("In ListUsersAction.execute");
/*    */ 
/* 69 */     ActionForward afForward = null;
/*    */ 
/* 72 */     ListUsersForm usersForm = (ListUsersForm)a_form;
/*    */ 
/* 74 */     usersForm.setUsers(this.m_userManager.getAllUsers(a_dbTransaction));
/*    */ 
/* 77 */     afForward = a_mapping.findForward("Success");
/* 78 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setUserManager(UserManager a_userManager)
/*    */   {
/* 89 */     this.m_userManager = a_userManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.ListUsersAction
 * JD-Core Version:    0.6.0
 */