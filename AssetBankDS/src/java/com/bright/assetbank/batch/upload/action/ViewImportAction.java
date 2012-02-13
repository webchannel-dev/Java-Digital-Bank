/*     */ package com.bright.assetbank.batch.upload.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.batch.upload.constant.UploadConstants;
/*     */ import com.bright.assetbank.batch.upload.form.ImportForm;
/*     */ import com.bright.assetbank.batch.upload.service.ImportManager;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewImportAction extends BTransactionAction
/*     */   implements ImageConstants, UploadConstants, AssetBankConstants, MessageConstants
/*     */ {
/*  56 */   private ImportManager m_importManager = null;
/*  57 */   private AssetEntityManager m_assetEntityManager = null;
/*  58 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  75 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  79 */     this.m_logger.debug("In ViewImportAction.execute");
/*  80 */     ImportForm importForm = (ImportForm)a_form;
/*     */ 
/*  83 */     importForm.setUploadToolOption("browser");
/*     */ 
/*  86 */     importForm.setEntitiesWithMatchAttributeExist(this.m_assetEntityManager.getEntitiesHaveMatchAttribute(a_dbTransaction));
/*     */ 
/*  89 */     importForm.setChildEntitiesWithMatchAttributeExist(this.m_assetEntityManager.getChildEntitiesHaveMatchAttribute(a_dbTransaction));
/*     */ 
/*  92 */     if (this.m_importManager.getMessages(userProfile.getUser().getId()) != null)
/*     */     {
/*  94 */       importForm.setMessages((Vector)this.m_importManager.getMessages(userProfile.getUser().getId()).clone());
/*     */ 
/*  97 */       if ((AssetBankSettings.getAllowSearchByCompleteness()) && (userProfile.getBatchImportTimestamp() > 0L))
/*     */       {
/*  99 */         SearchCriteria searchCriteria = new SearchCriteria();
/* 100 */         searchCriteria.setBulkUpload(userProfile.getBatchImportTimestamp());
/* 101 */         searchCriteria.setIsComplete(Boolean.FALSE);
/* 102 */         SearchResults searchResults = this.m_searchManager.search(searchCriteria, userProfile.getCurrentLanguage().getCode());
/* 103 */         importForm.setNumIncompleteAssets(searchResults.getNumResults());
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 110 */       importForm.setMessages(null);
/*     */     }
/*     */ 
/* 114 */     if (this.m_importManager.isImportInProgress(userProfile.getUser().getId()))
/*     */     {
/* 116 */       importForm.setIsImportInProgress(true);
/*     */     }
/*     */     else
/*     */     {
/* 120 */       importForm.setIsImportInProgress(false);
/*     */     }
/*     */ 
/* 123 */     if ((AssetBankSettings.getAssetEntitiesEnabled()) && (importForm.getParentId() <= 0L) && (getLongParameter(a_request, "parentId") <= 0L))
/*     */     {
/* 125 */       Vector entities = this.m_assetEntityManager.getAllUploadEntities(a_dbTransaction);
/*     */ 
/* 128 */       if (entities.size() == 0)
/*     */       {
/* 130 */         importForm.setNoUploadableTypes(true);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 135 */     ActionForward afForward = a_mapping.findForward("Success");
/*     */ 
/* 137 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setImportManager(ImportManager a_importManager)
/*     */   {
/* 142 */     this.m_importManager = a_importManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager a_entityManager)
/*     */   {
/* 147 */     this.m_assetEntityManager = a_entityManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 152 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.action.ViewImportAction
 * JD-Core Version:    0.6.0
 */