/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.UploadFileForm;
/*     */ import com.bright.assetbank.application.service.FileUploadManager;
/*     */ import com.bright.assetbank.application.util.UploadUtil;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public abstract class UploadFileAction extends BTransactionAction
/*     */ {
/*  43 */   protected OrgUnitManager m_orgUnitManager = null;
/*  44 */   protected ListManager m_listManager = null;
/*  45 */   protected FileStoreManager m_fileStoreManager = null;
/*     */   private FileUploadManager m_fileUploadManager;
/*     */ 
/*     */   public File getUploadedFileAndValidate(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, HttpSession a_session, UploadFileForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/*  55 */     File uploadedFile = UploadUtil.getUploadedFile(a_userProfile, a_session);
/*     */ 
/*  57 */     if (((a_form.getFile() == null) || (a_form.getFile().getFileSize() == 0)) && ((uploadedFile == null) || (uploadedFile.length() == 0L)))
/*     */     {
/*  59 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationFile", a_userProfile.getCurrentLanguage()).getBody());
/*  60 */       a_form.setNoUploadFileSpecified(true);
/*  61 */       return uploadedFile;
/*     */     }
/*     */ 
/*  65 */     if (((a_form.getFile() != null) && (FileUtil.getSuffix(a_form.getFile().getFileName()) == null)) || ((uploadedFile != null) && (FileUtil.getSuffix(uploadedFile.getName()) == null)))
/*     */     {
/*  67 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationFileExtension", a_userProfile.getCurrentLanguage()).getBody());
/*  68 */       a_form.setNoUploadFileSpecified(true);
/*  69 */       return uploadedFile;
/*     */     }
/*     */ 
/*  72 */     long lFileSize = (a_form.getFile() != null) && (a_form.getFile().getFileSize() > 0) ? a_form.getFile().getFileSize() : uploadedFile.length();
/*     */ 
/*  75 */     if ((AssetBankSettings.getOrgUnitUse()) && (!a_userProfile.getIsAdmin()) && (this.m_orgUnitManager.isDiskQuotaExceeded(a_dbTransaction, a_userProfile.getUser().getId(), lFileSize)))
/*     */     {
/*  79 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationInsufficientQuota", a_userProfile.getCurrentLanguage()).getBody());
/*  80 */       a_form.setNoUploadFileSpecified(true);
/*  81 */       return uploadedFile;
/*     */     }
/*     */ 
/*  84 */     return uploadedFile;
/*     */   }
/*     */ 
/*     */   public OrgUnitManager getOrgUnitManager()
/*     */   {
/*  89 */     return this.m_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  94 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ListManager getListManager()
/*     */   {
/*  99 */     return this.m_listManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 104 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ 
/*     */   public FileStoreManager getFileStoreManager()
/*     */   {
/* 109 */     return this.m_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 114 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public FileUploadManager getFileUploadManager()
/*     */   {
/* 119 */     return this.m_fileUploadManager;
/*     */   }
/*     */ 
/*     */   public void setFileUploadManager(FileUploadManager a_fileUploadManager)
/*     */   {
/* 124 */     this.m_fileUploadManager = a_fileUploadManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.UploadFileAction
 * JD-Core Version:    0.6.0
 */