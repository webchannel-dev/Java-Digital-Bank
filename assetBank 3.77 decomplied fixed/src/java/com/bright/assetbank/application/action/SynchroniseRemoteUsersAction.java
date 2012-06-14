/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.user.service.UserManager;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class SynchroniseRemoteUsersAction extends Bn2Action
/*    */ {
/* 42 */   protected UserManager m_userManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 66 */     ActionForward afForward = null;
/* 67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 70 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 72 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 73 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 77 */     this.m_userManager.synchroniseRemoteUsers();
/*    */ 
/* 79 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 81 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setUserManager(UserManager a_userManager)
/*    */   {
/* 87 */     this.m_userManager = a_userManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.SynchroniseRemoteUsersAction
 * JD-Core Version:    0.6.0
 */