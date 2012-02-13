/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.framework.common.bean.FileBean;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DownloadAssetBoxFileAction extends Bn2Action
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     ActionForward afForward = a_mapping.findForward("Success");
/*    */ 
/* 54 */     List downloadFiles = (List)a_request.getSession().getAttribute("assetboxDownloadFiles");
/*    */ 
/* 57 */     if (downloadFiles == null)
/*    */     {
/* 59 */       afForward = a_mapping.findForward("DownloadFailure");
/*    */     }
/*    */     else
/*    */     {
/* 63 */       String sZipFileLocation = a_request.getParameter("file");
/* 64 */       String sZipFileName = a_request.getParameter("filename");
/*    */ 
/* 67 */       boolean bValidFile = false;
/* 68 */       for (int i = 0; i < downloadFiles.size(); i++)
/*    */       {
/* 70 */         FileBean file = (FileBean)downloadFiles.get(i);
/* 71 */         if (!file.getFilePath().equalsIgnoreCase(sZipFileLocation))
/*    */           continue;
/* 73 */         bValidFile = true;
/*    */       }
/*    */ 
/* 77 */       if (bValidFile)
/*    */       {
/* 79 */         a_request.setAttribute("downloadFile", sZipFileLocation);
/* 80 */         a_request.setAttribute("downloadFilename", sZipFileName);
/* 81 */         a_request.setAttribute("deleteFileAfterUse", Boolean.FALSE);
/* 82 */         a_request.setAttribute("downloadFaliureUrl", a_mapping.findForward("DownloadFailure").getPath());
/*    */ 
/* 84 */         afForward = a_mapping.findForward("Success");
/*    */       }
/*    */       else
/*    */       {
/* 88 */         a_mapping.findForward("NoPermission");
/*    */       }
/*    */     }
/*    */ 
/* 92 */     return afForward;
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 97 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.DownloadAssetBoxFileAction
 * JD-Core Version:    0.6.0
 */