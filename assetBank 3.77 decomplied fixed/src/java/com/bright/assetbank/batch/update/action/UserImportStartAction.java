/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.batch.update.form.MetadataImportForm;
/*     */ import com.bright.assetbank.synchronise.service.UserImportManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.InputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class UserImportStartAction extends BTransactionAction
/*     */ {
/*  51 */   private UserImportManager m_importManager = null;
/*  52 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/* 130 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     ActionForward forward = null;
/*     */     try
/*     */     {
/*  80 */       MetadataImportForm form = (MetadataImportForm)a_form;
/*     */ 
/*  83 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  85 */       if (!userProfile.getIsAdmin())
/*     */       {
/*  87 */         this.m_logger.error("UserImportStartAction.execute : User does not have permission.");
/*  88 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*  91 */       FormFile formFile = form.getFile();
/*     */ 
/*  94 */       if (formFile.getFileSize() == 0)
/*     */       {
/*  96 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationFile", userProfile.getCurrentLanguage()).getBody());
/*  97 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 101 */       InputStream in = formFile.getInputStream();
/* 102 */       String sUrl = this.m_fileStoreManager.addFile(in, formFile.getFileName(), StoredFileType.TEMP);
/*     */ 
/* 106 */       in.close();
/*     */ 
/* 108 */       int iAdded = this.m_importManager.importUserData(sUrl, userProfile.getUser().getId());
/*     */ 
/* 110 */       forward = createRedirectingForward("numUsersAdded=" + iAdded, a_mapping, "Success");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 114 */       this.m_logger.error("Exception in UserImportStartAction: " + e.getMessage());
/* 115 */       throw new Bn2Exception("Exception in UserImportStartAction: " + e.getMessage(), e);
/*     */     }
/* 117 */     return forward;
/*     */   }
/*     */ 
/*     */   public void setImportManager(UserImportManager a_sImportManager)
/*     */   {
/* 122 */     this.m_importManager = a_sImportManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/* 127 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 133 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.UserImportStartAction
 * JD-Core Version:    0.6.0
 */