/*    */ package com.bright.assetbank.synchronise.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DownloadExportFileAction extends Bn2Action
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ActionForward afForward = a_mapping.findForward("Success");
/* 52 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 54 */     if ((!userProfile.getIsAdmin()) && ((!AssetBankSettings.getOrgUnitAdminsCanExport()) || (!userProfile.getIsOrgUnitAdmin())))
/*    */     {
/* 56 */       this.m_logger.error("ExportFromSearchAction.execute : User does not have admin permission : " + userProfile);
/* 57 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 60 */     a_request.setAttribute("downloadFile", FileUtil.encryptFilepath(a_request.getParameter("file")));
/*    */ 
/* 64 */     a_request.setAttribute("downloadFilename", a_request.getParameter("filename"));
/*    */ 
/* 68 */     return afForward;
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 73 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.DownloadExportFileAction
 * JD-Core Version:    0.6.0
 */