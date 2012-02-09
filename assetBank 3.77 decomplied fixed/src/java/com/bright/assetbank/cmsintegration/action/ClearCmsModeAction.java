/*    */ package com.bright.assetbank.cmsintegration.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.cmsintegration.bean.CmsInfo;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ClearCmsModeAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request);
/*    */ 
/* 55 */     CmsInfo cmsInfo = null;
/*    */ 
/* 58 */     userProfile.setCmsInfo(cmsInfo);
/*    */ 
/* 60 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.action.ClearCmsModeAction
 * JD-Core Version:    0.6.0
 */