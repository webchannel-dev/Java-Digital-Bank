/*     */ package com.bright.assetbank.filetransfer.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.action.UploadFileAction;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.FileUploadManager;
/*     */ import com.bright.assetbank.filetransfer.form.FileTransferForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
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
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class FileTransferUploadAction extends UploadFileAction
/*     */   implements AssetBankConstants
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     FileTransferForm form = (FileTransferForm)a_form;
/*     */ 
/*  60 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  63 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  65 */       this.m_logger.debug("This user does not have permission to view the file transfer page");
/*  66 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  69 */     File uploadedFile = getUploadedFileAndValidate(a_dbTransaction, userProfile, a_request.getSession(), form);
/*  70 */     if (form.getHasErrors())
/*     */     {
/*  72 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  75 */     if (!form.getConditionsAccepted())
/*     */     {
/*  77 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationConditions", userProfile.getCurrentLanguage()).getBody());
/*  78 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  82 */     String maxFileUploadSizeError = getFileUploadManager().validateMaxFileUploadSize(a_dbTransaction, userProfile, form.getFile());
/*  83 */     if (maxFileUploadSizeError != null)
/*     */     {
/*  85 */       form.addError(maxFileUploadSizeError);
/*  86 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */     try
/*     */     {
/*     */       AssetFileSource source;
/*  94 */       if (form.getFile() != null)
/*     */       {
/*  96 */         source = new AssetFileSource(form.getFile());
/*  97 */         form.setFilename(form.getFile().getFileName());
/*     */       }
/*     */       else
/*     */       {
/* 101 */         source = new AssetFileSource(uploadedFile);
/* 102 */         form.setFilename(uploadedFile.getName());
/*     */       }
/*     */ 
/* 106 */       String sFileLocation = this.m_fileStoreManager.addFile(source.getInputStream(), source.getFilename(), StoredFileType.TEMP);
/*     */ 
/* 110 */       form.setFileLocation(sFileLocation);
/*     */ 
/* 113 */       source.close();
/*     */ 
/* 115 */       return a_mapping.findForward("Success");
/*     */     }
/*     */     catch (FileNotFoundException fnfe)
/*     */     {
/* 119 */       this.m_logger.error("FileNotFoundException in UploadAssetFileAction:", fnfe);
/* 120 */       throw new Bn2Exception("FileNotFoundException in UploadAssetFileAction ", fnfe);
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 124 */       this.m_logger.error("IOException in UploadAssetFileAction:", ioe);
/* 125 */     throw new Bn2Exception("IOException in UploadAssetFileAction ", ioe);}
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.filetransfer.action.FileTransferUploadAction
 * JD-Core Version:    0.6.0
 */