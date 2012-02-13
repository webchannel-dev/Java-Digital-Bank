/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.user.form.LoginForm;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class LoginFailureAction extends BTransactionAction
/*    */ {
/* 84 */   protected ListManager m_listManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 53 */     ActionForward afForward = null;
/* 54 */     LoginForm loginForm = (LoginForm)a_form;
/*    */ 
/* 56 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 59 */     String sErrorCode = a_request.getParameter("message");
/* 60 */     if (StringUtil.stringIsPopulated(sErrorCode))
/*    */     {
/* 63 */       String sErrorMsg = this.m_listManager.getListItem(a_dbTransaction, sErrorCode, userProfile.getCurrentLanguage()).getBody();
/*    */ 
/* 65 */       loginForm.addError(sErrorMsg);
/*    */     }
/*    */ 
/* 68 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 70 */     return afForward;
/*    */   }
/*    */ 
/*    */   public boolean doPreprocessing()
/*    */   {
/* 81 */     return false;
/*    */   }
/*    */ 
/*    */   public void setListManager(ListManager listManager)
/*    */   {
/* 87 */     this.m_listManager = listManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.LoginFailureAction
 * JD-Core Version:    0.6.0
 */