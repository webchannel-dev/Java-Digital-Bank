/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.bean.UploadedFileInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddAssetFromPreviewAction extends ViewUpdateAssetAction
/*     */   implements AssetBankConstants
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  64 */     AssetFileSource afs = null;
/*     */ 
/*  66 */     DownloadForm downloadForm = (DownloadForm)a_form;
/*     */ 
/*  68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  71 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getCanUpdateAssets()) && (!userProfile.getCanUploadWithApproval())))
/*     */     {
/*  73 */       this.m_logger.debug("This user does not have permission to add an asset from the preview page");
/*  74 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  78 */     String sFileLocation = downloadForm.getPathToAddExisting();
/*     */     try
/*     */     {
/*  82 */       afs = new AssetFileSource(new File(this.m_fileStoreManager.getAbsolutePath(sFileLocation)));
/*     */ 
/*  84 */       UploadedFileInfo ufi = getAssetManager().storeTempUploadedFile(afs, StoredFileType.ASSET);
/*  85 */       downloadForm.setTempFileLocation(ufi.getFileLocation());
/*     */ 
/*  89 */       afs.close();
/*     */     }
/*     */     catch (FileNotFoundException fnfe)
/*     */     {
/*  94 */       this.m_logger.error("FileNotFoundException in AddAssetFromPreviewAction:", fnfe);
/*  95 */       throw new Bn2Exception("FileNotFoundException in AddAssetFromPreviewAction ", fnfe);
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*  99 */       this.m_logger.error("IOException in AddAssetFromPreviewAction:", ioe);
/* 100 */       throw new Bn2Exception("IOException in AddAssetFromPreviewAction ", ioe);
/*     */     }
/*     */ 
/* 103 */     return createRedirectingForward("path=" + downloadForm.getTempFileLocation(), a_mapping, "AddAsset");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.AddAssetFromPreviewAction
 * JD-Core Version:    0.6.0
 */