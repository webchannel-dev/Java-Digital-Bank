/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewDownloadProgressAction extends Bn2Action
/*     */   implements AssetBoxConstants, AssetBankConstants
/*     */ {
/*  46 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  54 */     ActionForward afForward = null;
/*     */ 
/*  56 */     String sFileName = a_request.getParameter("downloadFilename");
/*  57 */     String sFilePath = a_request.getParameter("filePath");
/*     */ 
/*  59 */     a_request.setAttribute("compressFile", Boolean.valueOf(a_request.getParameter("compressAsset")));
/*  60 */     a_request.setAttribute("downloadFilename", sFileName);
/*  61 */     a_request.setAttribute("emailAsset", Boolean.valueOf(a_request.getParameter("emailAsset")));
/*  62 */     a_request.setAttribute("repurposeAsset", Boolean.valueOf(a_request.getParameter("repurposeAsset")));
/*     */ 
/*  65 */     File file = new File(this.m_fileStoreManager.getAbsolutePath(sFilePath) + ".ready");
/*     */ 
/*  67 */     if (file.exists())
/*     */     {
/*  69 */       if (Boolean.valueOf(a_request.getParameter("emailAsset")).booleanValue())
/*     */       {
/*  71 */         a_request.setAttribute("downloadFile", sFilePath);
/*  72 */         afForward = a_mapping.findForward("DownloadEmailSuccess");
/*     */       }
/*  74 */       else if (Boolean.valueOf(a_request.getParameter("repurposeAsset")).booleanValue())
/*     */       {
/*  76 */         a_request.setAttribute("downloadFile", sFilePath);
/*  77 */         afForward = a_mapping.findForward("DownloadRepurposeSuccess");
/*     */       }
/*     */       else
/*     */       {
/*  81 */         a_request.setAttribute("downloadFile", FileUtil.encryptFilepath(sFilePath));
/*  82 */         afForward = a_mapping.findForward("DownloadSuccess");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  87 */       int iRetries = getIntParameter(a_request, "downloadAttempts");
/*     */ 
/*  89 */       if (iRetries > AssetBankSettings.getDownloadImageMaxAttempts())
/*     */       {
/*  91 */         afForward = a_mapping.findForward("Failure");
/*     */       }
/*     */       else
/*     */       {
/*  95 */         a_request.setAttribute("downloadAttempts", Integer.valueOf(iRetries + 1));
/*  96 */         a_request.setAttribute("filePath", sFilePath);
/*  97 */         afForward = a_mapping.findForward("InProgress");
/*     */       }
/*     */     }
/*     */ 
/* 101 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_manager)
/*     */   {
/* 106 */     this.m_fileStoreManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewDownloadProgressAction
 * JD-Core Version:    0.6.0
 */