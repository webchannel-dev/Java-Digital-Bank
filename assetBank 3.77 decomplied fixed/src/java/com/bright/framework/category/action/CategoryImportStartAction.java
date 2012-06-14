/*     */ package com.bright.framework.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.batch.update.form.MetadataImportForm;
import com.bright.assetbank.category.bean.CategoryImportBatch;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
          import com.bright.framework.file.DefaultBeanReader;
/*     */ import com.bright.framework.file.DefaultBeanReader.TooManyColumnsException;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
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
/*     */ public class CategoryImportStartAction extends BTransactionAction
/*     */ {
/*  51 */   private CategoryManager m_categoryManager = null;
/*  52 */   private FileStoreManager m_fileStoreManager = null;
/*  53 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ActionForward forward = null;
/*     */ 
/*  78 */     MetadataImportForm form = (MetadataImportForm)a_form;
/*     */ 
/*  81 */     ABUserProfile userProfile = (ABUserProfile)
/*  82 */       UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  84 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  86 */       this.m_logger
/*  87 */         .error("CategoryImportStartAction.execute : User does not have permission.");
/*  88 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  91 */     FormFile formFile = form.getFile();
/*     */ 
/*  94 */     if (formFile.getFileSize() == 0)
/*     */     {
/*  96 */       form.addError(
/*  98 */         this.m_listManager.getListItem(
/*  98 */         a_dbTransaction, "failedValidationFile", userProfile.getCurrentLanguage()).getBody());
/*     */ 
/*  99 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ //.k_iStoredFileType_TEMP
/*     */     try
/*     */     {
/* 105 */       InputStream in = formFile.getInputStream();
/* 106 */       String sFilePath = this.m_fileStoreManager.addFile(in, 
/* 107 */         formFile.getFileName(), FileStoreManager.k_iStoredFileType_TEMP);
/*     */ 
/* 109 */       in.close();
/*     */       CategoryImportBatch cib = new CategoryImportBatch();
                cib.setCategoryTypeId(1L);
                cib.setImportFileLocation(sFilePath);
/* 111 */       int iAdded =((AssetCategoryManager) this.m_categoryManager).importCategories(a_dbTransaction, 
/* 112 */         cib);
/*     */ 
/* 114 */       forward = createRedirectingForward("numCatsAdded=" + iAdded, 
/* 115 */         a_mapping, "Success");
/*     */     }
/*     */     catch (DefaultBeanReader.TooManyColumnsException e)
/*     */     {
/* 119 */       form.addError(
/* 121 */         this.m_listManager.getListItem(
/* 121 */         a_dbTransaction, "failedValidationFile", userProfile.getCurrentLanguage()).getBody());
/*     */ 
/* 122 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 127 */       this.m_logger.error("Exception in UserImportStartAction: " + 
/* 128 */         e.getMessage());
/* 129 */       throw new Bn2Exception("Exception in UserImportStartAction: " + 
/* 130 */         e.getMessage(), e);
/*     */     }
/* 132 */     return forward;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/* 137 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_manager)
/*     */   {
/* 142 */     this.m_categoryManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 147 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.action.CategoryImportStartAction
 * JD-Core Version:    0.6.0
 */