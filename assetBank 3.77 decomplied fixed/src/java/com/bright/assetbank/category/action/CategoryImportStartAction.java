/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.batch.update.form.MetadataImportForm;
/*     */ import com.bright.assetbank.category.bean.CategoryImportBatch;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
         import com.bright.framework.file.DefaultBeanReader;
/*     */ import com.bright.framework.file.DefaultBeanReader.TooManyColumnsException;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.io.InputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class CategoryImportStartAction extends BTransactionAction
/*     */ {
/*  57 */   private AssetCategoryManager m_categoryManager = null;
/*  58 */   private FileStoreManager m_fileStoreManager = null;
/*  59 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     MetadataImportForm form = (MetadataImportForm)a_form;
/*     */ 
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  88 */     if (this.m_categoryManager.isBatchInProgress(userProfile.getSessionId()))
/*     */     {
/*  91 */       return a_mapping.findForward("InProgress");
/*     */     }
/*     */ 
/*  94 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  96 */       this.m_logger.error("CategoryImportStartAction.execute : User does not have permission.");
/*     */ 
/*  98 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 102 */     long lTreeId = getLongParameter(a_request, "treeId");
/*     */ 
/* 105 */     FormFile formFile = form.getFile();
/*     */ 
/* 108 */     if (formFile.getFileSize() == 0)
/*     */     {
/* 110 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationFile", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 119 */         InputStream in = formFile.getInputStream();
/* 120 */         String sFilePath = this.m_fileStoreManager.addFile(in, formFile.getFileName(), StoredFileType.TEMP);
/*     */ 
/* 122 */         in.close();
/*     */ 
/* 125 */         CategoryImportBatch batch = new CategoryImportBatch();
/* 126 */         batch.setUserProfile(userProfile);
/* 127 */         batch.setCategoryTypeId(lTreeId);
/* 128 */         batch.setImportFileLocation(sFilePath);
/* 129 */         batch.setJobId(userProfile.getSessionId());
/* 130 */         this.m_categoryManager.queueItem(batch);
/*     */ 
/* 132 */         return a_mapping.findForward("Success");
/*     */       }
/*     */       catch (DefaultBeanReader.TooManyColumnsException e)
/*     */       {
/* 137 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationFile", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 144 */         this.m_logger.error("Exception in CategoryImportStartAction: " + e.getMessage());
/*     */ 
/* 146 */         throw new Bn2Exception("Exception in CategoryImportStartAction: " + e.getMessage(), e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 160 */     RequestUtil.addAllParametersAsAttributes(a_request);
/* 161 */     return a_mapping.findForward("Failure");
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/* 166 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_manager)
/*     */   {
/* 171 */     this.m_categoryManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 176 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.CategoryImportStartAction
 * JD-Core Version:    0.6.0
 */