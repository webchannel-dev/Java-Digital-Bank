/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.framework.service.FileStoreManager;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class CompressDownloadAction extends Bn2Action
/*    */   implements AssetBankConstants
/*    */ {
/* 43 */   private FileStoreManager m_fileStoreManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     boolean bCompress = false;
/*    */ 
/* 53 */     if (a_request.getAttribute("compressFile") != null)
/*    */     {
/* 55 */       bCompress = ((Boolean)a_request.getAttribute("compressFile")).booleanValue();
/*    */     }
/*    */ 
/* 59 */     if (bCompress)
/*    */     {
/* 62 */       String sFile = (String)a_request.getAttribute("downloadFile");
/*    */ 
/* 65 */       String sFilename = (String)a_request.getAttribute("downloadFilename");
/*    */ 
/* 67 */       Vector vecSourceFilePaths = new Vector();
/* 68 */       vecSourceFilePaths.add(sFile);
/* 69 */       Vector vecDestFilePaths = new Vector();
/* 70 */       vecDestFilePaths.add(sFilename);
/*    */ 
/* 73 */       sFile = this.m_fileStoreManager.createZipFileForDownload(sFilename, vecSourceFilePaths, vecDestFilePaths);
/*    */ 
/* 79 */       sFilename = FileUtil.getFilepathWithoutSuffix(sFilename) + ".zip";
/*    */ 
/* 81 */       a_request.setAttribute("downloadFile", sFile);
/* 82 */       a_request.setAttribute("downloadFilename", sFilename);
/*    */     }
/*    */ 
/* 86 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*    */   {
/* 91 */     this.m_fileStoreManager = a_sFileStoreManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.CompressDownloadAction
 * JD-Core Version:    0.6.0
 */