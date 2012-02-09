/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.action.InitialisationErrorAction;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ReinitializeApplicationAction extends InitialisationErrorAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 62 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 65 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 67 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 68 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 74 */       GlobalApplication.getInstance().reinitialize();
/*    */     }
/*    */     catch (Bn2Exception bn2e)
/*    */     {
/* 79 */       return a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 82 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ReinitializeApplicationAction
 * JD-Core Version:    0.6.0
 */