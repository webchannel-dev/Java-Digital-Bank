/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.DownloadAssetBoxBatchManager;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.bean.FileBean;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewDownloadProgressAction extends Bn2Action
/*     */   implements AssetBoxConstants, AssetBankConstants
/*     */ {
/*  60 */   private DownloadAssetBoxBatchManager m_downloadBatchManager = null;
/*     */ 
/*  66 */   private StorageDeviceManager m_storageDeviceManager = null;
/*     */ 
/*     */   public void setDownloadBatchManager(DownloadAssetBoxBatchManager a_downloadBatchManager)
/*     */   {
/*  63 */     this.m_downloadBatchManager = a_downloadBatchManager;
/*     */   }
/*     */ 
/*     */   public void setStorageDeviceManager(StorageDeviceManager a_manager)
/*     */   {
/*  69 */     this.m_storageDeviceManager = a_manager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     ActionForward afForward = null;
/*     */ 
/*  80 */     ABUserProfile user = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  82 */     long lIdForSession = UserProfile.getLongIdForSession(a_request.getSession());
/*     */ 
/*  84 */     List downloadFiles = (List)a_request.getSession().getAttribute("assetboxDownloadFiles");
/*     */ 
/*  87 */     Vector vMessages = this.m_downloadBatchManager.getMessages(lIdForSession);
/*  88 */     a_request.setAttribute("messages", vMessages);
/*     */ 
/*  90 */     if (downloadFiles == null)
/*     */     {
/*  92 */       int iRetries = getIntParameter(a_request, "downloadAttempts");
/*     */ 
/*  94 */       if (iRetries > AssetBankSettings.getDownloadAssetBoxMaxAttempts())
/*     */       {
/*  96 */         afForward = a_mapping.findForward("Failure");
/*     */       }
/*     */       else
/*     */       {
/* 100 */         a_request.setAttribute("downloadAttempts", Integer.valueOf(iRetries + 1));
/* 101 */         afForward = a_mapping.findForward("InProgress");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 106 */       String sFilePath = ((FileBean)downloadFiles.get(0)).getFilePath();
/*     */ 
/* 108 */       if (Boolean.TRUE.equals(a_request.getSession().getAttribute("emailAssetBox")))
/*     */       {
/* 110 */         a_request.setAttribute("downloadFile", sFilePath);
/* 111 */         afForward = a_mapping.findForward("DownloadEmailSuccess");
/*     */       }
/*     */       else
/*     */       {
/* 116 */         File file = new File(this.m_storageDeviceManager.getFullPathForRelativePath(sFilePath));
/*     */ 
/* 118 */         if ((AssetBankSettings.getLightboxMaxDownloadSize() > 0) && (file.length() > AssetBankSettings.getLightboxMaxDownloadSize()) && (!user.getIsAdmin()))
/*     */         {
/* 121 */           a_request.setAttribute("fileTooLarge", Boolean.valueOf(true));
/*     */         }
/*     */ 
/* 124 */         afForward = a_mapping.findForward("DownloadSuccess");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 129 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ViewDownloadProgressAction
 * JD-Core Version:    0.6.0
 */