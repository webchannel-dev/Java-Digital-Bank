/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.form.DownloadSharedAssetForm;
/*    */ import com.bright.framework.common.action.Bn2ForwardAction;
/*    */ import com.bright.framework.service.FileStoreManager;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewDownloadSharedAssetAction extends Bn2ForwardAction
/*    */ {
/* 36 */   private FileStoreManager m_fileStoreManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 60 */     DownloadSharedAssetForm form = (DownloadSharedAssetForm)a_form;
/*    */ 
/* 63 */     String sFilename = a_request.getParameter("downloadFile");
/*    */ 
/* 65 */     if ((sFilename == null) || (sFilename.length() == 0))
/*    */     {
/* 67 */       this.m_logger.error("ViewDownloadSharedAssetAction: no value passed for parameter 'downloadFile'");
/* 68 */       throw new Bn2Exception("Invalid parameter passed to download file page.");
/*    */     }
/*    */ 
/* 71 */     String sFile = this.m_fileStoreManager.getAbsolutePath(FileUtil.decryptFilepath(sFilename));
/*    */ 
/* 74 */     File fFile = new File(sFile);
/*    */ 
/* 76 */     if (!fFile.exists())
/*    */     {
/* 78 */       form.setFileExpired(true);
/*    */     }
/*    */ 
/* 81 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 86 */     return true;
/*    */   }
/*    */ 
/*    */   public void setFileStoreManager(FileStoreManager fileStoreManager)
/*    */   {
/* 91 */     this.m_fileStoreManager = fileStoreManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewDownloadSharedAssetAction
 * JD-Core Version:    0.6.0
 */