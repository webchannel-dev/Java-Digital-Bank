/*     */ package com.bright.assetbank.search.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.util.ABImageMagick;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.bean.SavedSearch;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderQuery;
/*     */ import com.bright.assetbank.search.exception.MaxSearchesReachedException;
/*     */ import com.bright.assetbank.search.form.SavedSearchForm;
/*     */ import com.bright.assetbank.search.service.SavedSearchManager;
/*     */ import com.bright.assetbank.usage.bean.ColorSpace;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class SaveSearchAction extends BTransactionAction
/*     */ {
/*  62 */   private SavedSearchManager m_savedSearchManager = null;
/*  63 */   private ListManager m_listManager = null;
/*  64 */   private FileStoreManager m_fileStoreManager = null;
/*  65 */   protected UsageManager m_usageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     ActionForward afForward = null;
/*  75 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     SavedSearchForm form = (SavedSearchForm)a_form;
/*     */ 
/*  79 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  81 */       this.m_logger.debug("SaveSearchAction.execute() : A user must be logged in to save a search.");
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  85 */     if (StringUtils.isEmpty(form.getSavedSearch().getName()))
/*     */     {
/*  87 */       form.addError(this.m_listManager.getListItem(a_transaction, "failedValidationNoName", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  90 */     String sOldName = a_request.getParameter("oldName");
/*     */ 
/*  92 */     form.getSavedSearch().setBuilderSearch(userProfile.getSearchCriteria() instanceof SearchBuilderQuery);
/*     */ 
/*  94 */     if (!StringUtil.stringIsPopulated(sOldName))
/*     */     {
/*  97 */       form.getSavedSearch().setCriteria((BaseSearchQuery)userProfile.getSearchCriteria());
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 103 */       if ((form.getRemoveImage()) || ((form.getImage() != null) && (form.getImage().getFileSize() > 0) && (StringUtil.stringIsPopulated(form.getSavedSearch().getImage()))))
/*     */       {
/* 106 */         File fToDelete = new File(AssetBankSettings.getApplicationPath() + "/" + form.getSavedSearch().getImage());
/* 107 */         fToDelete.delete();
/* 108 */         FileUtil.logFileDeletion(fToDelete);
/* 109 */         form.getSavedSearch().setImage(null);
/*     */       }
/*     */ 
/* 113 */       if ((form.getImage() != null) && (form.getImage().getFileSize() > 0))
/*     */       {
/* 116 */         InputStream ins = null;
/* 117 */         String sImageUrl = null;
/* 118 */         String sDestination = null;
/*     */         try
/*     */         {
/* 122 */           ins = form.getImage().getInputStream();
/* 123 */           sImageUrl = this.m_fileStoreManager.addFile(ins, form.getImage().getFileName(), StoredFileType.PREVIEW_OR_THUMBNAIL);
/*     */ 
/* 127 */           sDestination = AssetBankSettings.getSavedSearchImagesDir() + "/" + FileUtil.getSafeFilename(new StringBuilder().append(form.getSavedSearch().getName()).append(userProfile.getUser().getId()).append(".").append("jpg").toString(), true);
/*     */ 
/* 132 */           String sRgbColorProfile = this.m_usageManager.getColorSpace(null, 1).getFileLocation();
/* 133 */           String sCmykColorProfile = this.m_usageManager.getColorSpace(null, 2).getFileLocation();
/*     */ 
/* 135 */           ABImageMagick.convertToCategoryImage(this.m_fileStoreManager.getAbsolutePath(sImageUrl), AssetBankSettings.getApplicationPath() + "/" + sDestination, sRgbColorProfile, sCmykColorProfile);
/*     */         }
/*     */         catch (Exception bn2e)
/*     */         {
/* 139 */           this.m_logger.error("SaveSearchAction.execute() : Exception whilst storing image for saved search", bn2e);
/* 140 */           form.addError(this.m_listManager.getListItem(a_transaction, "imageFileError", userProfile.getCurrentLanguage()).getBody());
/*     */         }
/*     */         finally
/*     */         {
/*     */           try
/*     */           {
/* 146 */             ins.close();
/*     */           }
/*     */           catch (IOException ioe) {
/*     */           }
/* 150 */           if (sImageUrl != null)
/*     */           {
/* 152 */             this.m_fileStoreManager.deleteFile(sImageUrl);
/*     */           }
/*     */         }
/* 155 */         form.getSavedSearch().setImage(sDestination);
/*     */       }
/*     */ 
/* 158 */       if (form.getHasErrors())
/*     */       {
/* 160 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 164 */       this.m_savedSearchManager.saveSearch(a_transaction, form.getSavedSearch(), userProfile, sOldName, false);
/*     */ 
/* 166 */       if (form.isRecent())
/*     */       {
/* 168 */         afForward = a_mapping.findForward("SuccessRecent");
/*     */       }
/*     */       else
/*     */       {
/* 172 */         afForward = a_mapping.findForward("SuccessCurrent");
/*     */       }
/*     */     }
/*     */     catch (MaxSearchesReachedException e)
/*     */     {
/* 177 */       form.addError(this.m_listManager.getListItem(a_transaction, "maxSavedSearchesReached", userProfile.getCurrentLanguage()).getBody());
/* 178 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 182 */       this.m_logger.error("SaveSearchAction.execute: IOError: " + e.getMessage());
/* 183 */       form.addError(this.m_listManager.getListItem(a_transaction, "failedValidationFile", userProfile.getCurrentLanguage()).getBody());
/* 184 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 187 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setSavedSearchManager(SavedSearchManager savedSearchManager)
/*     */   {
/* 192 */     this.m_savedSearchManager = savedSearchManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 197 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 202 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 207 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.SaveSearchAction
 * JD-Core Version:    0.6.0
 */