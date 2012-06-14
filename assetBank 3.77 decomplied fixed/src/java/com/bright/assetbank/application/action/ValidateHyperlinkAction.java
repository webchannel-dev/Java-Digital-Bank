/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.form.ValidateHyperlinkForm;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.bean.NameValueBean;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ValidateHyperlinkAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 56 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 58 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getCanUpdateAssets()) && (!userProfile.getCanUploadWithApproval())))
/*    */     {
/* 60 */       this.m_logger.debug("This user does not have permission to upload files");
/* 61 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 64 */     ValidateHyperlinkForm form = (ValidateHyperlinkForm)a_form;
/*    */ 
/* 67 */     String sHyperlink = a_request.getParameter("hyperlink");
/*    */ 
/* 69 */     NameValueBean[] pairs = StringUtil.getNameValuePairs(sHyperlink, "\\|\\|", "==", true);
/*    */ 
/* 71 */     form.setHyperlinks(pairs);
/*    */ 
/* 74 */     String sBaseUrl = a_request.getParameter("baseurl");
/* 75 */     form.setBaseUrl(sBaseUrl);
/*    */ 
/* 78 */     String sLinkName = a_request.getParameter("linkname");
/* 79 */     form.setLinkName(sLinkName);
/*    */ 
/* 81 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ValidateHyperlinkAction
 * JD-Core Version:    0.6.0
 */