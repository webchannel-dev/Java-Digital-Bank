/*    */ package com.bright.framework.mail.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class LoggedInSendTemplatedEmailAction extends SendTemplatedEmailAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 43 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 45 */     if ((userProfile != null) && (userProfile.getIsLoggedIn()))
/*    */     {
/* 47 */       return super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*    */     }
/*    */ 
/* 50 */     return a_mapping.findForward("NoPermission");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.LoggedInSendTemplatedEmailAction
 * JD-Core Version:    0.6.0
 */