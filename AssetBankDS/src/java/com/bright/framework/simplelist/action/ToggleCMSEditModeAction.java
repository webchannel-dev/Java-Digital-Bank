/*    */ package com.bright.framework.simplelist.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.simplelist.constant.ListConstants;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.ServletUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ToggleCMSEditModeAction extends Bn2Action
/*    */   implements ListConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 63 */     String sEditMode = a_request.getParameter("editMode");
/* 64 */     boolean bEditMode = Boolean.parseBoolean(sEditMode);
/* 65 */     ActionForward forward = null;
/*    */ 
/* 67 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/* 68 */     userProfile.setCMSEditMode(!bEditMode);
/*    */ 
/* 70 */     int iRedirect = getIntParameter(a_request, "redirect");
/* 71 */     if (iRedirect > 0)
/*    */     {
/* 73 */       String sRedirectUrl = (String)a_request.getSession().getAttribute("lastGetRequestUri");
/* 74 */       sRedirectUrl = ServletUtil.getApplicationUrl(a_request) + sRedirectUrl;
/* 75 */       forward = new ActionForward(sRedirectUrl);
/* 76 */       forward.setRedirect(true);
/*    */     }
/*    */     else
/*    */     {
/* 80 */       forward = a_mapping.findForward("Success");
/*    */     }
/*    */ 
/* 83 */     return forward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.action.ToggleCMSEditModeAction
 * JD-Core Version:    0.6.0
 */