/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DownloadAssetFileAction extends Bn2Action
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 42 */     ActionForward afForward = a_mapping.findForward("Success");
/*    */ 
/* 44 */     String sFileName = a_request.getParameter("downloadFilename");
/* 45 */     String sFilePath = a_request.getParameter("downloadFile");
/* 46 */     sFilePath = FileUtil.decryptFilepath(sFilePath);
/* 47 */     Boolean bCompress = Boolean.valueOf(a_request.getParameter("compressAsset"));
/*    */ 
/* 49 */     a_request.setAttribute("compressFile", bCompress);
/* 50 */     a_request.setAttribute("downloadFile", sFilePath);
/* 51 */     a_request.setAttribute("downloadFilename", sFileName);
/*    */ 
/* 53 */     a_request.setAttribute("downloadFaliureUrl", a_mapping.findForward("DownloadFailure").getPath());
/*    */ 
/* 55 */     return afForward;
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 60 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DownloadAssetFileAction
 * JD-Core Version:    0.6.0
 */