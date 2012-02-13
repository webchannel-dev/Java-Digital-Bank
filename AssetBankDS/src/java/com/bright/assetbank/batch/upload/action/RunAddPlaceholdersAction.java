/*     */ package com.bright.assetbank.batch.upload.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.UploadUtil;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.attribute.util.BulkUploadMandatoryPredicate;
/*     */ import com.bright.assetbank.batch.upload.bean.BatchBulkUploadInfo;
/*     */ import com.bright.assetbank.batch.upload.form.ImportForm;
/*     */ import com.bright.assetbank.batch.upload.service.ImportManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*     */ import com.bright.framework.category.form.CategorySelectionForm;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RunAddPlaceholdersAction extends BTransactionAction
/*     */ {
/*  59 */   private IAssetManager m_assetManager = null;
/*  60 */   private ImportManager m_importManager = null;
/*  61 */   protected TaxonomyManager m_taxonomyManager = null;
/*  62 */   protected ListManager m_listManager = null;
/*  63 */   private AssetEntityManager m_assetEntityManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     ActionForward afForward = null;
/*  75 */     ImportForm form = (ImportForm)a_form;
/*     */ 
/*  78 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  79 */     if ((!userProfile.getIsLoggedIn()) || (!AssetBankSettings.getAssetEntitiesEnabled()) || (!userProfile.getUserCanAddEmptyAssets()))
/*     */     {
/*  81 */       this.m_logger.debug("RunAddPlaceholdersAction: Only logged in users can perform bulk uploads");
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  86 */     form.validate(a_request);
/*  87 */     form.validatePrice(a_request, this.m_listManager, userProfile, a_dbTransaction);
/*  88 */     form.validateNumberPlaceholdersToAdd(a_request, this.m_listManager, userProfile, a_dbTransaction);
/*  89 */     boolean bDateError = form.validateDates(a_request, this.m_listManager, userProfile, a_dbTransaction);
/*  90 */     validateMandatoryFields(form, a_request);
/*     */ 
/*  93 */     Vector attIds = AttributeUtil.getWritableRelevantAttributeIds(a_dbTransaction, this.m_assetEntityManager, form.getAsset(), userProfile, true);
/*     */ 
/*  97 */     Vector allAttributes = this.m_assetManager.getAssetAttributes(null, attIds);
/*     */ 
/*  99 */     AttributeUtil.populateAttributeValuesFromRequest(a_request, form, form.getAsset().getAttributeValues(), allAttributes, bDateError, new BulkUploadMandatoryPredicate(), false, a_dbTransaction, this.m_taxonomyManager, this.m_listManager, LanguageConstants.k_defaultLanguage, false);
/*     */ 
/* 111 */     AttributeUtil.validateInputMasks(a_dbTransaction, form.getAsset().getAttributeValues(), form, this.m_listManager, userProfile);
/*     */ 
/* 113 */     if (!form.getHasErrors())
/*     */     {
/* 115 */       Asset metaData = form.getAsset();
/* 116 */       if (form.getParentId() > 0L)
/*     */       {
/* 118 */         metaData.setParentAssetIdsAsString(String.valueOf(form.getParentId()));
/*     */       }
/*     */ 
/* 121 */       if (form.getSelectedAssetEntityId() > 0L)
/*     */       {
/* 123 */         metaData.setEntity(new AssetEntity(form.getSelectedAssetEntityId()));
/*     */       }
/*     */ 
/* 127 */       WorkflowUpdate update = WorkflowUtil.getWorkflowUpdateFromSubmitOptions(a_dbTransaction, userProfile, form.getSelectedSubmitOption(), a_request, form.getPermissionCategoryForm().getCategoryIds());
/*     */ 
/* 130 */       BatchBulkUploadInfo batchInfo = UploadUtil.prepPlaceholderBatchInfo(metaData, (int)form.getNumAssetsToAdd().getNumber(), update, userProfile, form.getLinkAssets(), allAttributes, -1L);
/*     */ 
/* 133 */       this.m_importManager.queueImport(batchInfo);
/*     */ 
/* 135 */       String sParam = "";
/* 136 */       if (form.getParentId() > 0L)
/*     */       {
/* 138 */         sParam = sParam + (StringUtils.isEmpty(sParam) ? "" : "&");
/* 139 */         sParam = sParam + "parentId=" + form.getParentId();
/*     */       }
/*     */ 
/* 143 */       long lTimestamp = metaData.getBulkUploadTimestamp().getTime();
/* 144 */       userProfile.setBatchImportTimestamp(lTimestamp);
/*     */ 
/* 146 */       boolean bIsUnsubmitted = form.getSelectedSubmitOption() == 2;
/* 147 */       userProfile.setBatchImportUnsubmitted(bIsUnsubmitted);
/*     */ 
/* 150 */       afForward = createRedirectingForward(sParam, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 156 */       form.setAssetAttributes(allAttributes);
/* 157 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 160 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 166 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setImportManager(ImportManager a_importManager)
/*     */   {
/* 171 */     this.m_importManager = a_importManager;
/*     */   }
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*     */   {
/* 176 */     this.m_taxonomyManager = a_taxonomyManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 181 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 186 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.action.RunAddPlaceholdersAction
 * JD-Core Version:    0.6.0
 */