/*    */ package com.bright.assetbank.simplelist.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.simplelist.constant.SimpleListConstants;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ToggleCMSEditModeAction extends Bn2Action
/*    */   implements SimpleListConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 62 */     String sEditMode = a_request.getParameter("editMode");
/* 63 */     boolean bEditMode = Boolean.parseBoolean(sEditMode);
/*    */ 
/* 65 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 66 */     userProfile.setCMSEditMode(!bEditMode);
/*    */ 
/* 68 */     ActionForward forward = a_mapping.findForward("Success");
/* 69 */     return forward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.simplelist.action.ToggleCMSEditModeAction
 * JD-Core Version:    0.6.0
 */