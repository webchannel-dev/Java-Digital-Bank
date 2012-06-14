/*     */ package com.bright.assetbank.batch.upload.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.attribute.util.BulkUploadMandatoryPredicate;
/*     */ import com.bright.assetbank.batch.upload.bean.BatchBulkUploadInfo;
/*     */ import com.bright.assetbank.batch.upload.form.ImportForm;
/*     */ import com.bright.assetbank.batch.upload.service.ImportManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*     */ import com.bright.framework.category.form.CategorySelectionForm;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class StartImportAction extends BTransactionAction
/*     */   implements ImageConstants, AssetBankConstants, MessageConstants
/*     */ {
/*  85 */   private IAssetManager m_assetManager = null;
/*  86 */   private ImportManager m_importManager = null;
/*  87 */   protected TaxonomyManager m_taxonomyManager = null;
/*  88 */   protected ListManager m_listManager = null;
/*  89 */   private AssetEntityManager m_assetEntityManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  99 */     ActionForward afForward = null;
/* 100 */     ImportForm form = (ImportForm)a_form;
/*     */ 
/* 103 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 105 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 107 */       this.m_logger.debug("Only logged in users can perform bulk uploads");
/* 108 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 112 */     if (form.getImportFilesToExistingAssets())
/*     */     {
/* 114 */       BatchBulkUploadInfo batchInfo = new BatchBulkUploadInfo();
/* 115 */       batchInfo.setUser((ABUser)userProfile.getUser());
/* 116 */       batchInfo.setImportFilesToExistingAssets(true);
/* 117 */       batchInfo.setAssetEntityId(form.getSelectedAssetEntityId());
/*     */ 
/* 119 */       this.m_importManager.queueImport(batchInfo);
/*     */ 
/* 121 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 125 */     form.validate(a_request);
/* 126 */     form.validatePrice(a_request, this.m_listManager, userProfile, a_dbTransaction);
/* 127 */     boolean bDateError = form.validateDates(a_request, this.m_listManager, userProfile, a_dbTransaction);
/* 128 */     validateMandatoryFields(form, a_request);
/*     */ 
/* 131 */     Vector attIds = AttributeUtil.getWritableRelevantAttributeIds(a_dbTransaction, this.m_assetEntityManager, form.getAsset(), userProfile, true);
/*     */ 
/* 135 */     Vector allAttributes = null;
/* 136 */     allAttributes = this.m_assetManager.getAssetAttributes(null, attIds);
/*     */ 
/* 138 */     AttributeUtil.populateAttributeValuesFromRequest(a_request, form, form.getAsset().getAttributeValues(), allAttributes, bDateError, new BulkUploadMandatoryPredicate(), false, a_dbTransaction, this.m_taxonomyManager, this.m_listManager, LanguageConstants.k_defaultLanguage, false);
/*     */ 
/* 150 */     AttributeUtil.validateInputMasks(a_dbTransaction, form.getAsset().getAttributeValues(), form, this.m_listManager, userProfile);
/*     */ 
/* 152 */     if (!form.getHasErrors())
/*     */     {
/* 154 */       Asset metaData = form.getAsset();
/*     */ 
/* 156 */       if (form.getParentId() > 0L)
/*     */       {
/* 158 */         metaData.setParentAssetIdsAsString(String.valueOf(form.getParentId()));
/*     */       }
/*     */ 
/* 162 */       GregorianCalendar now = new GregorianCalendar();
/* 163 */       BrightDateTime bdtDate = new BrightDateTime();
/* 164 */       bdtDate.setDate(now.getTime());
/* 165 */       bdtDate.zeroMillis();
/* 166 */       metaData.setBulkUploadTimestamp(bdtDate.getDate());
/*     */ 
/* 169 */       WorkflowUpdate update = WorkflowUtil.getWorkflowUpdateFromSubmitOptions(a_dbTransaction, userProfile, form.getSelectedSubmitOption(), a_request, form.getPermissionCategoryForm().getCategoryIds());
/*     */ 
/* 172 */       BatchBulkUploadInfo batchInfo = new BatchBulkUploadInfo();
/* 173 */       batchInfo.setUser((ABUser)userProfile.getUser());
/* 174 */       batchInfo.setUserProfile(userProfile);
/* 175 */       batchInfo.setAssetMetadata(metaData);
/* 176 */       batchInfo.setLinkAssets(form.getLinkAssets());
/* 177 */       batchInfo.setAssetAttributes(allAttributes);
/* 178 */       batchInfo.setPopulateNameFromFilename(form.getUseFilenameAsTitle());
/*     */ 
/* 180 */       batchInfo.setDeferThumbnailGeneration(form.getDeferThumbnailGeneration());
/* 181 */       batchInfo.setChosenFileFormat(form.getChosenFileFormat());
/* 182 */       batchInfo.setWorkflowUpdate(update);
/* 183 */       batchInfo.setImportChildAssets(form.getImportChildAssets());
/*     */ 
/* 185 */       if (AssetBankSettings.getRemoveIdFromUploadFilenames() != null)
/*     */       {
/* 187 */         batchInfo.setRemoveIdFromFilename(AssetBankSettings.getRemoveIdFromUploadFilenames().booleanValue());
/*     */       }
/*     */       else
/*     */       {
/* 191 */         batchInfo.setRemoveIdFromFilename(form.isRemoveIdFromFilename());
/*     */       }
/*     */ 
/* 194 */       if (form.getSelectedAssetEntityId() > 0L)
/*     */       {
/* 196 */         metaData.setEntity(new AssetEntity(form.getSelectedAssetEntityId()));
/*     */       }
/*     */ 
/* 200 */       if (form.getImportFileOption().equals("all_files"))
/*     */       {
/* 202 */         batchInfo.setFromZip(false);
/* 203 */         batchInfo.setDirectoryOrFileName("");
/* 204 */         batchInfo.setProcessZipsAsAssets(true);
/*     */       }
/* 206 */       else if ((form.getZipPath() != null) && (form.getZipPath().length() > 0))
/*     */       {
/* 208 */         batchInfo.setFromZip(true);
/* 209 */         batchInfo.setDirectoryOrFileName(form.getZipPath());
/*     */       }
/*     */       else
/*     */       {
/* 213 */         batchInfo.setFromZip(false);
/*     */ 
/* 216 */         if (form.getDirectoryPath().equals("#toplevel#"))
/*     */         {
/* 219 */           batchInfo.setDirectoryOrFileName("");
/* 220 */           batchInfo.setProcessZipsAsAssets(true);
/*     */         }
/*     */         else
/*     */         {
/* 224 */           batchInfo.setDirectoryOrFileName(form.getDirectoryPath());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 229 */       this.m_importManager.queueImport(batchInfo);
/*     */ 
/* 231 */       String sParam = "";
/* 232 */       if (form.getParentId() > 0L)
/*     */       {
/* 234 */         sParam = sParam + (StringUtils.isEmpty(sParam) ? "" : "&");
/* 235 */         sParam = sParam + "parentId=" + form.getParentId();
/*     */       }
/*     */ 
/* 239 */       long lTimestamp = metaData.getBulkUploadTimestamp().getTime();
/* 240 */       userProfile.setBatchImportTimestamp(lTimestamp);
/*     */ 
/* 242 */       boolean bIsUnsubmitted = form.getSelectedSubmitOption() == 2;
/* 243 */       userProfile.setBatchImportUnsubmitted(bIsUnsubmitted);
/*     */ 
/* 246 */       afForward = createRedirectingForward(sParam, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 255 */       form.setAssetAttributes(allAttributes);
/* 256 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 259 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 265 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setImportManager(ImportManager a_importManager)
/*     */   {
/* 270 */     this.m_importManager = a_importManager;
/*     */   }
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*     */   {
/* 275 */     this.m_taxonomyManager = a_taxonomyManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 280 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 285 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.action.StartImportAction
 * JD-Core Version:    0.6.0
 */