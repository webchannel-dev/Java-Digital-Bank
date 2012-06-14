/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.batch.update.bean.MetadataImportInfo;
/*     */ import com.bright.assetbank.batch.update.form.MetadataImportForm;
/*     */ import com.bright.assetbank.synchronise.service.AssetImportManager;
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
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class MetadataImportUploadAction extends BTransactionAction
/*     */ {
/*  52 */   private AssetImportManager m_importManager = null;
/*  53 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/* 164 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     ActionForward afForward = null;
/*     */     try
/*     */     {
/*  82 */       MetadataImportForm form = (MetadataImportForm)a_form;
/*     */ 
/*  85 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  87 */       if (!userProfile.getIsAdmin())
/*     */       {
/*  89 */         this.m_logger.error("MetadataImportCheckAction.execute : User does not have permission.");
/*  90 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*  93 */       if (this.m_importManager.isBatchInProgress(userProfile.getUser().getId()))
/*     */       {
/*  95 */         return a_mapping.findForward("BatchInProgress");
/*     */       }
/*     */ 
/*  99 */       FormFile formFile = form.getFile();
/*     */ 
/* 102 */       if (formFile.getFileSize() == 0)
/*     */       {
/* 104 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationFile", userProfile.getCurrentLanguage()).getBody());
/* 105 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 109 */       InputStream in = formFile.getInputStream();
/* 110 */       String sUrl = this.m_fileStoreManager.addFile(in, formFile.getFileName(), StoredFileType.TEMP);
/*     */ 
/* 115 */       in.close();
/*     */ 
/* 117 */       a_request.getSession().setAttribute("url", sUrl);
/* 118 */       a_request.getSession().setAttribute("addCats", Boolean.valueOf(form.getAddMissingCategories()));
/* 119 */       a_request.getSession().setAttribute("addAssets", Boolean.valueOf(form.getAddMissingAssets()));
/* 120 */       a_request.getSession().setAttribute("removeExistingCats", Boolean.valueOf(form.getRemoveFromExistingCategories()));
/*     */ 
/* 123 */       if (form.getSkipCheck())
/*     */       {
/* 126 */         afForward = a_mapping.findForward("StartImport");
/*     */       }
/*     */       else
/*     */       {
/* 132 */         MetadataImportInfo importInfo = new MetadataImportInfo();
/* 133 */         importInfo.setFileUrl(sUrl);
/* 134 */         importInfo.setUser(userProfile.getUser());
/* 135 */         importInfo.setAddMissingCategories(form.getAddMissingCategories());
/* 136 */         importInfo.setAddMissingAssets(form.getAddMissingAssets());
/* 137 */         importInfo.setCheckOnly(true);
/* 138 */         importInfo.setRemoveFromExistingCategories(form.getRemoveFromExistingCategories());
/*     */ 
/* 141 */         this.m_importManager.queueCheck(importInfo);
/* 142 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 148 */       this.m_logger.error("Exception in DataImportCheckAction: ", e);
/* 149 */       throw new Bn2Exception("Exception in DataImportCheckAction: " + e.getMessage(), e);
/*     */     }
/* 151 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/* 156 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setImportManager(AssetImportManager a_sImportManager)
/*     */   {
/* 161 */     this.m_importManager = a_sImportManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 167 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.MetadataImportUploadAction
 * JD-Core Version:    0.6.0
 */