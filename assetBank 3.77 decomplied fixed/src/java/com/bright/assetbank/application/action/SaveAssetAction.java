/*      */ package com.bright.assetbank.application.action;
/*      */ 
/*      */ import com.bn2web.common.action.Bn2Action;
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bright.assetbank.actiononasset.action.ActionOnAsset;
/*      */ import com.bright.assetbank.actiononasset.action.RestrictAssetAction;
/*      */ import com.bright.assetbank.actiononasset.service.ActionOnAssetManager;
/*      */ import com.bright.assetbank.agreements.bean.Agreement;
/*      */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*      */ import com.bright.assetbank.application.bean.AssetFileSource;
/*      */ import com.bright.assetbank.application.bean.FileFormat;
/*      */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*      */ import com.bright.assetbank.application.bean.MediaAssetConversionInfo;
/*      */ import com.bright.assetbank.application.bean.VideoConversionInfo;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.exception.AssetFileReadException;
/*      */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*      */ import com.bright.assetbank.application.exception.UploadedFileNotFoundException;
/*      */ import com.bright.assetbank.application.form.AssetForm;
/*      */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*      */ import com.bright.assetbank.application.service.AssetLogManager;
/*      */ import com.bright.assetbank.application.service.AssetManager;
/*      */ import com.bright.assetbank.application.service.IAssetManager;
/*      */ import com.bright.assetbank.application.util.UploadUtil;
/*      */ import com.bright.assetbank.application.util.VideoUtil;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.bean.DataLookupRequest;
/*      */ import com.bright.assetbank.attribute.service.AttributeDateRuleManager;
/*      */ import com.bright.assetbank.attribute.service.AttributeManager;
/*      */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*      */ import com.bright.assetbank.brandtemplate.service.BrandTemplateManager;
/*      */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*      */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*      */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*      */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*      */ import com.bright.assetbank.plugin.service.PluginManager;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*      */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*      */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.form.CategorySelectionForm;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.category.util.CategoryUtil;
/*      */ import com.bright.framework.common.bean.BrightDateTime;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.bean.DataBean;
/*      */ import com.bright.framework.database.service.DBTransactionCallback;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.image.constant.ImageConstants;
/*      */ import com.bright.framework.message.constant.MessageConstants;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.util.CollectionUtil;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DateFormat;
/*      */ import java.text.ParseException;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.upload.FormFile;
/*      */ 
/*      */ public class SaveAssetAction extends Bn2Action
/*      */   implements ImageConstants, AssetBankConstants, MessageConstants
/*      */ {
/*  167 */   protected IAssetManager m_assetManager = null;
/*  168 */   protected DBTransactionManager m_transactionManager = null;
/*  169 */   protected AssetCategoryManager m_categoryManager = null;
/*  170 */   protected MultiLanguageSearchManager m_searchManager = null;
/*  171 */   protected AssetEntityManager m_assetEntityManager = null;
/*  172 */   protected TaxonomyManager m_taxonomyManager = null;
/*  173 */   protected FileStoreManager m_fileStoreManager = null;
/*  174 */   private AttributeManager m_attributeManager = null;
/*  175 */   private AttributeDateRuleManager m_attributeDateRuleManager = null;
/*  176 */   protected ListManager m_listManager = null;
/*  177 */   protected AssetLogManager m_assetLogManager = null;
/*  178 */   private OrgUnitManager m_orgUnitManager = null;
/*  179 */   private ActionOnAssetManager m_actionOnAssetManager = null;
/*  180 */   private AgreementsManager m_agreementsManager = null;
/*  181 */   protected AssetWorkflowManager m_assetWorkflowManager = null;
/*  182 */   protected CategoryCountCacheManager m_categoryCountCacheManager = null;
/*  183 */   private BrandTemplateManager m_brandTemplateManager = null;
/*      */   private PluginManager m_pluginManager;
/*      */ 
/*      */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*      */     throws Bn2Exception
/*      */   {
/*  192 */     ActionForward afForward = null;
/*  193 */     final AssetForm form = (AssetForm)a_form;
/*  194 */     Asset originalAsset = null;
/*      */ 
/*  197 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*      */ 
/*  199 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getCanUpdateAssets()) && (!userProfile.getCanUploadWithApproval())))
/*      */     {
/*  201 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  202 */       return a_mapping.findForward("NoPermission");
/*      */     }
/*      */ 
/*  206 */     if ((form.getAsset().getCurrentVersionId() > 0L) && (!AssetBankSettings.getCanEditPreviousAssetVersions()))
/*      */     {
/*  208 */       this.m_logger.debug("SaveAssetACtion.execute() : Cannot edit previous asset versions");
/*  209 */       return a_mapping.findForward("Failure");
/*      */     }
/*      */ 
/*  212 */     validateFile(null, form, userProfile);
/*      */ 
/*  214 */     validateSubstituteFile(form, null, userProfile);
/*      */ 
/*  216 */     validatePrice(form, null, userProfile);
/*      */ 
/*  219 */     long lId = form.getAsset().getId();
/*  220 */     boolean bDateError = populateAndValidateDates(null, form, userProfile);
/*      */     try
/*      */     {
/*  226 */       validateBrandTemplate(userProfile, a_request, form);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  230 */       throw new Bn2Exception("IOException validating brand template", e);
/*      */     }
/*      */ 
/*  234 */     boolean bValidateMandatoryFields = doValidateMandatoryFields(form);
/*  235 */     if (bValidateMandatoryFields)
/*      */     {
/*  237 */       validateMandatoryFields(form, a_request);
/*      */     }
/*      */ 
/*  241 */     Vector attIds = AttributeUtil.getWritableRelevantAttributeIds(null, this.m_assetEntityManager, form.getAsset(), userProfile, true);
/*      */ 
/*  246 */     Vector allAttributes = this.m_assetManager.getAssetAttributes(null, attIds);
/*      */ 
/*  249 */     DBTransaction dbTransaction = null;
/*  250 */     DBTransaction dbValidationTransaction = this.m_transactionManager.getNewTransaction();
/*      */     try
/*      */     {
/*  254 */       Set visibleCats = userProfile.getPermissionCategoryIds(3);
/*  255 */       boolean bNoAccessLevelsSelected = validateAccessLevels(null, dbValidationTransaction, form, a_request, userProfile, bValidateMandatoryFields, visibleCats);
/*      */ 
/*  258 */       AttributeUtil.populateAttributeValuesFromRequest(a_request, form, form.getAsset().getAttributeValues(), allAttributes, bDateError, bValidateMandatoryFields, false, dbValidationTransaction, this.m_taxonomyManager, this.m_listManager, userProfile.getCurrentLanguage(), false);
/*      */ 
/*  270 */       AttributeUtil.validateInputMasks(dbValidationTransaction, form.getAsset().getAttributeValues(), form, this.m_listManager, userProfile);
/*      */ 
/*  275 */       validateFieldLengths(form, a_request);
/*      */ 
/*  278 */       if (form.getAsset().getIsSensitive())
/*      */       {
/*  280 */         AttributeValue notes = form.getAsset().getAttributeValue(301L);
/*      */ 
/*  282 */         if (StringUtils.isEmpty(notes.getValue()))
/*      */         {
/*  284 */           form.addError(this.m_listManager.getListItem(dbValidationTransaction, "failedValidationSensitivityNotesRequired", userProfile.getCurrentLanguage()).getBody());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  290 */         AttributeUtil.nullifyAttributeValue(form.getAsset().getAttributeValue(301L));
/*      */       }
/*      */ 
/*  295 */       form.validateSubmitOptions(dbValidationTransaction, userProfile, a_request, this.m_categoryManager, this.m_listManager, false);
/*      */ 
/*  298 */       WorkflowUpdate update = WorkflowUtil.getWorkflowUpdateFromSubmitOptions(dbValidationTransaction, userProfile, form.getSelectedSubmitOption(), a_request, form.getPermissionCategoryForm().getCategoryIds());
/*      */ 
/*  303 */       final boolean bAdding = form.getAsset().getId() <= 0L;
/*  304 */       validateExtensionData(dbValidationTransaction, userProfile, bAdding, form.getAsset(), form);
/*      */ 
/*  306 */       dbValidationTransaction.commit();
/*      */ 
/*  309 */       DataLookupRequest dlr = AttributeUtil.checkForDataLookup(a_request);
/*  310 */       if ((!form.getHasErrors()) && (dlr == null))
/*      */       {
/*  318 */         Asset asset = null;
/*  319 */         AssetFileSource source = null;
/*      */ 
/*  322 */         AssetConversionInfo conversionInfo = null;
/*      */ 
/*  324 */         if (form.getSelectedFrame() >= 0)
/*      */         {
/*  327 */           VideoConversionInfo vidConvert = new VideoConversionInfo();
/*  328 */           vidConvert.setPreviewStartFrame(form.getSelectedFrame());
/*  329 */           conversionInfo = vidConvert;
/*      */         }
/*      */ 
/*  333 */         if ((form.getRotationAngle() != 0) || (form.getAsset().getAutoRotate() > 0))
/*      */         {
/*  336 */           ImageConversionInfo imageConversionInfo = null;
/*      */ 
/*  338 */           if (conversionInfo == null)
/*      */           {
/*  340 */             imageConversionInfo = new ImageConversionInfo();
/*      */           }
/*      */           else
/*      */           {
/*  344 */             imageConversionInfo = (ImageConversionInfo)conversionInfo;
/*      */           }
/*  346 */           imageConversionInfo.setRotationAngle(form.getRotationAngle() + form.getAsset().getAutoRotate());
/*  347 */           conversionInfo = imageConversionInfo;
/*      */         }
/*      */ 
/*  351 */         if (form.isEmptyAsset())
/*      */         {
/*  353 */           if (conversionInfo == null)
/*      */           {
/*  355 */             conversionInfo = new MediaAssetConversionInfo();
/*      */           }
/*  357 */           conversionInfo.setSupressWatermark(true);
/*      */         }
/*      */ 
/*  361 */         source = openAssetFileSource(userProfile, a_request, form);
/*      */ 
/*  364 */         AssetFileSource substituteFileSource = new AssetFileSource(form.getSubstituteFile());
/*      */ 
/*  367 */         if (form.getRemoveSubstitute())
/*      */         {
/*  369 */           substituteFileSource.setRemove(true);
/*      */         }
/*      */ 
/*  373 */         form.getAsset().setDateLastModified(new Date());
/*  374 */         form.getAsset().setLastModifiedByUser((ABUser)userProfile.getUser());
/*      */ 
/*  377 */         Vector vec = arrayToVector(form.getBrandSelectedList());
/*  378 */         form.getAsset().setFeaturedInBrandsList(vec);
/*      */ 
/*  381 */         if (AssetBankSettings.getUseBrands())
/*      */         {
/*  383 */           if (vec.size() > 0)
/*      */           {
/*  385 */             form.getAsset().setIsFeatured(true);
/*      */           }
/*      */           else
/*      */           {
/*  389 */             form.getAsset().setIsFeatured(false);
/*      */           }
/*      */         }
/*      */ 
/*  393 */         boolean bUserHasDiskQuota = false;
/*  394 */         Vector<OrgUnit> vOrgUnits = null;
/*      */ 
/*  397 */         if (AssetBankSettings.getOrgUnitUse())
/*      */         {
/*  399 */           DBTransaction dbOrgUnitTransaction = this.m_transactionManager.getNewTransaction();
/*  400 */           vOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(dbOrgUnitTransaction, userProfile.getUser().getId());
/*  401 */           dbOrgUnitTransaction.commit();
/*      */ 
/*  403 */           for (OrgUnit orgUnit : vOrgUnits)
/*      */           {
/*  405 */             if (orgUnit.getDiskQuotaMb() > 0)
/*      */             {
/*  407 */               bUserHasDiskQuota = true;
/*  408 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */         DBTransaction dbAssetLogTransaction;
/*  414 */         if ((form.getAsset().getId() > 0L) && ((AssetBankSettings.getAuditLogEnabled()) || ((form.getFile() != null) && (bUserHasDiskQuota)) || (AttributeUtil.hasActionableAttributeValue(form.getAsset().getAttributeValues())) || (AssetBankSettings.getRestrictChildAssetsWithParent())))
/*      */         {
/*  420 */           dbAssetLogTransaction = this.m_transactionManager.getNewTransaction();
/*  421 */           originalAsset = this.m_assetManager.getAsset(dbAssetLogTransaction, form.getAsset().getId(), userProfile.getWriteableAttributeIds(), false, false);
/*  422 */           dbAssetLogTransaction.commit();
/*      */         }
/*      */ 
/*  426 */         if ((bUserHasDiskQuota) && (!userProfile.getIsAdmin()))
/*      */         {
/*  428 */           validateOrgUnitDiskUsage(form, originalAsset, userProfile, source, vOrgUnits);
/*      */ 
/*  430 */           if (form.getHasErrors())
/*      */           {
/*  432 */             form.setNoUploadFileSpecified(true);
/*  433 */             form.setAssetAttributes(allAttributes);
/*  434 */             dbAssetLogTransaction = a_mapping.findForward("Failure"); 
                //jsr 1623;
                        return ;
/*      */           }
/*      */         }
/*      */ 
/*  438 */         this.m_agreementsManager.processAgreementsBeforeSave(null, form.getAsset());
/*      */ 
/*  441 */         if (!userProfile.getIsAdmin())
/*      */         {
/*  444 */           addInvisibleCategoryIds(this.m_categoryManager.getCategoriesForItem(null, 2L, form.getAsset().getId()), userProfile.getUpdateableOrUpdateableWithApprovalCategoryIds(), form.getAsset().getPermissionCategories(), false);
/*      */ 
/*  450 */           if (userProfile.getRootDescriptiveCategoryIds().size() > 0)
/*      */           {
/*  452 */             addInvisibleCategoryIds(this.m_categoryManager.getCategoriesForItem(null, 1L, form.getAsset().getId()), userProfile.getRootDescriptiveCategoryIds(), form.getAsset().getDescriptiveCategories(), true);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  461 */         Vector vActions = performPreSaveActions(null, form.getAsset(), originalAsset);
/*      */ 
/*  464 */         Set previousWorkflows = null;
/*  465 */         if (form.getAsset().getId() > 0L)
/*      */         {
/*  467 */           Vector vecCategories = this.m_categoryManager.getCategoriesForItem(null, 2L, form.getAsset().getId());
/*  468 */           previousWorkflows = WorkflowUtil.getWorkflowNamesFromCategories(vecCategories);
/*      */         }
/*      */ 
/*  472 */         prepExtensionAsset(form);
/*      */ 
/*  475 */         this.m_transactionManager.execute(new DBTransactionCallback()
/*      */         {
/*      */           public Object doInTransaction(DBTransaction a_transaction) throws SQLException, Bn2Exception
/*      */           {
/*  479 */             SaveAssetAction.this.extractExtensionDataFromForm(a_transaction, bAdding, form.getAsset(), form);
/*  480 */             return null;
/*      */           }
/*      */         });
/*  485 */         asset = this.m_assetManager.saveAsset(null, form.getAsset(), source, userProfile.getUser().getId(), conversionInfo, substituteFileSource, form.getChangedFrame(), form.getSaveTypeId(), form.getAsset().getFormat().getId(), update);
/*      */ 
/*  495 */         form.getAsset().setId(asset.getId());
/*      */ 
/*  498 */         source.close();
/*  499 */         substituteFileSource.close();
/*      */ 
/*  502 */         if (StringUtils.isNotEmpty(form.getUploadedFilename()))
/*      */         {
/*  504 */           File uploadedFile = UploadUtil.getUploadedFile(userProfile, a_request.getSession());
/*      */ 
/*  506 */           if (uploadedFile.exists())
/*      */           {
/*  508 */             UploadUtil.getUploadedFile(userProfile, a_request.getSession()).delete();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  513 */         performPostSaveActionsOnAsset(null, asset.getId(), userProfile.getUser().getId(), vActions);
/*      */ 
/*  516 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/*  519 */         Set newWorkflows = WorkflowUtil.getWorkflowNamesFromCategories(asset.getPermissionCategories());
/*  520 */         boolean bRemovedWorkflows = !CollectionUtil.isSubSetOf(newWorkflows, previousWorkflows);
/*      */ 
/*  523 */         dbTransaction.commit();
/*  524 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/*  527 */         asset = this.m_assetManager.getAsset(dbTransaction, asset.getId(), null, true, true);
/*      */ 
/*  529 */         if (AssetBankSettings.getAgreementsEnabled())
/*      */         {
/*  532 */           asset.getAgreement().setId(form.getAsset().getAgreement().getId());
/*  533 */           this.m_agreementsManager.processAgreementAfterSave(dbTransaction, asset, userProfile.getUser() != null ? userProfile.getUser().getId() : 0L);
/*      */         }
/*  535 */         else if (asset.getIsRestricted())
/*      */         {
/*  537 */           new RestrictAssetAction().performOnAssetAfterSave(dbTransaction, asset.getId(), userProfile.getUser() != null ? userProfile.getUser().getId() : 0L);
/*      */         }
/*      */ 
/*  541 */         if ((asset.getIsVideo()) && (StringUtil.stringIsPopulated(form.getTempDirName())))
/*      */         {
/*  543 */           VideoUtil.clearThumbnailDirectory(this.m_fileStoreManager.getAbsolutePath(form.getTempDirName()));
/*      */         }
/*      */ 
/*  547 */         String sQueryString = "id=" + asset.getId() + "&" + "wfRemoved" + "=" + bRemovedWorkflows;
/*      */ 
/*  549 */         if (StringUtils.isNotEmpty(form.getReturnUrl()))
/*      */         {
/*  551 */           afForward = createRedirectingForward(form.getReturnUrl() + "&" + "expectedAssetId" + "=" + form.getExpectedAssetId());
/*      */         }
/*      */         else
/*      */         {
/*  555 */           if (asset.getIsUnsubmitted())
/*      */           {
/*  557 */             sQueryString = sQueryString + "&unsubmitted=true";
/*      */           }
/*  559 */           afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*      */         }
/*      */ 
/*  565 */         boolean bPopFromChild = AttributeUtil.getPopulatingFromChild(asset);
/*  566 */         if ((StringUtil.stringIsPopulated(asset.getParentAssetIdsAsString())) && ((bPopFromChild) || (AssetBankSettings.getUseFirstChildAssetAsSurrogate())))
/*      */         {
/*  568 */           reindexRelatedAssets(dbTransaction, asset, asset.getParentAssetIdsAsString(), false);
/*      */         }
/*      */ 
/*  573 */         if ((StringUtils.isNotEmpty(asset.getChildAssetIdsAsString())) && ((AssetBankSettings.getRestrictChildAssetsWithParent()) || (AssetBankSettings.getIncludeParentMetadataForSearch())))
/*      */         {
/*  577 */           reindexRelatedAssets(dbTransaction, asset, asset.getChildAssetIdsAsString(), false);
/*      */         }
/*      */ 
/*  581 */         if ((AssetBankSettings.getAllowPublishing()) && (AssetBankSettings.getIncludeParentMetadataForExport()))
/*      */         {
/*  583 */           if (StringUtil.stringIsPopulated(asset.getChildAssetIdsAsString()))
/*      */           {
/*  585 */             this.m_assetManager.updateDateLastModifiedForAssets(dbTransaction, asset.getDateLastModified(), asset.getChildAssetIdsAsString());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  591 */         dbTransaction.commit();
/*      */ 
/*  595 */         if ((!asset.getIsUnsubmitted()) && (update.getUpdateType() != 3))
/*      */         {
/*  599 */           Set workflows = null;
/*  600 */           if (visibleCats != null)
/*      */           {
/*  602 */             Vector vecIds = new Vector(visibleCats);
/*  603 */             Vector vecVisibileCats = this.m_categoryManager.getCategories(null, 2L, vecIds);
/*  604 */             workflows = WorkflowUtil.getWorkflowNamesFromCategories(vecVisibileCats);
/*      */           }
/*      */ 
/*  607 */           if (update.getLeavingAllWorkflowsInCurrentState(workflows))
/*      */           {
/*  609 */             afForward = a_mapping.findForward("LeaveInCurrentState");
/*      */           }
/*  611 */           else if ((update.getUpdateType() == 2) || (update.getUnnapprovingAllWorkflows(workflows)))
/*      */           {
/*  614 */             afForward = a_mapping.findForward("SubmittedForApproval");
/*      */           }
/*  616 */           else if (bNoAccessLevelsSelected)
/*      */           {
/*  618 */             userProfile.setRecentImagesMinId(0L);
/*  619 */             afForward = a_mapping.findForward("RemovedFromAccessLevels");
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  625 */           form.setAsset(asset);
/*      */         }
/*      */ 
/*  630 */         if ((lId > 0L) && (!asset.getIsUnsubmitted()))
/*      */         {
/*  632 */           Vector vecMissingWorkflows = asset.getMissingWorkflows();
/*  633 */           if ((vecMissingWorkflows != null) && (vecMissingWorkflows.size() > 0))
/*      */           {
/*  638 */             afForward = createRedirectingForward(sQueryString, a_mapping, "CorrectWorkflows");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */         try
/*      */         {
/*  646 */           dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/*  649 */           afForward = overrideWithExtensionAssetForward(dbTransaction, userProfile, a_request, form, a_mapping, afForward);
/*      */ 
/*  652 */           if (AssetBankSettings.getAuditLogEnabled())
/*      */           {
/*  655 */             if (lId > 0L)
/*      */             {
/*  657 */               this.m_assetLogManager.generateChangeLogEntry(originalAsset, asset, dbTransaction, (ABUser)userProfile.getUser(), userProfile.getSessionId());
/*      */             }
/*  660 */             else if (lId <= 0L)
/*      */             {
/*  662 */               this.m_assetLogManager.saveLog(asset.getId(), asset.getFileName(), dbTransaction, userProfile.getUser().getId(), new Date(), 2L, userProfile.getSessionId(), asset.getVersionNumber());
/*      */             }
/*      */           }
/*      */ 
/*  666 */           BrightDateTime dtNow = BrightDateTime.now();
/*  667 */           this.m_attributeDateRuleManager.scheduleNextSendEmailRulesTime(dbTransaction, dtNow);
/*  668 */           this.m_attributeDateRuleManager.scheduleNextChangeAttributeValueRulesTime(dbTransaction, dtNow);
/*      */         }
/*      */         catch (Bn2Exception bn2e)
/*      */         {
/*  672 */           this.m_logger.error("Exception when resheduling attribute rules in AddAssetAction:", bn2e);
/*      */ 
/*  674 */           if (dbTransaction != null)
/*      */           {
/*      */             try
/*      */             {
/*  678 */               dbTransaction.rollback();
/*      */             }
/*      */             catch (SQLException se)
/*      */             {
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */         finally
/*      */         {
/*  688 */           dbTransaction.commit();
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  694 */         form.setAssetAttributes(allAttributes);
/*  695 */         form.setWorkflowUpdate(update);
/*      */ 
/*  697 */         if (dlr != null)
/*      */         {
/*      */           try
/*      */           {
/*  702 */             Vector vecValues = this.m_attributeManager.dataLookup(null, dlr);
/*  703 */             form.setDataLookupValues(vecValues);
/*      */ 
/*  706 */             form.addInfo("The data lookup has been done and the fields populated");
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  710 */             this.m_logger.error("SaveAssetAction.execute: Error with external data lookup: " + e.getMessage());
/*  711 */             form.addError(this.m_listManager.getListItem(null, "failedDataLookup", userProfile.getCurrentLanguage()).getBody() + e.getMessage());
/*      */           }
/*      */         }
/*      */ 
/*  715 */         afForward = a_mapping.findForward("Failure");
/*      */       }
/*      */     }
/*      */     catch (Bn2Exception bn2e)
/*      */     {
/*  720 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  724 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (SQLException se)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  732 */       if (dbValidationTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  736 */           dbValidationTransaction.rollback();
/*      */         }
/*      */         catch (SQLException se)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  744 */       if ((bn2e instanceof AssetFileReadException))
/*      */       {
/*  746 */         form.addError(this.m_listManager.getListItem(dbTransaction, "fileError", userProfile.getCurrentLanguage()).getBody());
/*  747 */         form.setAssetAttributes(allAttributes);
/*      */ 
/*  749 */         this.m_logger.error("AssetFileReadException in AddAssetAction:", bn2e);
/*      */ 
/*  751 */         afForward = a_mapping.findForward("Failure");
/*      */       }
/*  753 */       if ((bn2e instanceof UploadedFileNotFoundException))
/*      */       {
/*  755 */         form.addError(this.m_listManager.getListItem(dbTransaction, "uploadedFileNotFoundError", userProfile.getCurrentLanguage()).getBody());
/*  756 */         form.setAssetAttributes(allAttributes);
/*      */ 
/*  758 */         this.m_logger.error("UploadedFileNotFoundException in AddAssetAction:", bn2e);
/*      */ 
/*  760 */         afForward = a_mapping.findForward("Failure");
/*      */       }
/*      */       else
/*      */       {
/*  764 */         this.m_logger.error("Bn2Exception in AddAssetAction:", bn2e);
/*  765 */         throw bn2e;
/*      */       }
/*      */     }
/*      */     catch (FileNotFoundException fnfe)
/*      */     {
/*  770 */       this.m_logger.error("FileNotFoundException in UpdateAssetAction:", fnfe);
/*  771 */       throw new Bn2Exception("FileNotFoundException in UpdateAssetAction ", fnfe);
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*  775 */       this.m_logger.error("IOException in UpdateAssetAction:", ioe);
/*  776 */       throw new Bn2Exception("IOException in UpdateAssetAction ", ioe);
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*  780 */       this.m_logger.error("SQLException in UpdateAssetAction:", se);
/*  781 */       throw new Bn2Exception("SQLException in UpdateAssetAction ", se);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  788 */         if (dbTransaction != null)
/*      */         {
/*  790 */           dbTransaction.commit();
/*      */         }
/*      */ 
/*  793 */         if (dbValidationTransaction != null)
/*      */         {
/*  795 */           dbValidationTransaction.commit();
/*      */         }
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  800 */         this.m_logger.error("Exception commiting transaction in UpdateAssetAction:", sqle);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  805 */     return afForward;
/*      */   }
/*      */ 
/*      */   private void validateFile(DBTransaction a_dbValidationTransaction, AssetForm a_form, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  815 */     if (a_form.getFile() != null)
/*      */     {
/*  817 */       if ((StringUtil.stringIsPopulated(a_form.getFile().getFileName())) && (a_form.getFile().getFileSize() == 0))
/*      */       {
/*  819 */         a_form.addError(this.m_listManager.getListItem(a_dbValidationTransaction, "failedValidationFile", a_userProfile.getCurrentLanguage()).getBody());
/*  820 */         a_form.setNoUploadFileSpecified(true);
/*      */       }
/*  822 */       else if ((StringUtil.stringIsPopulated(a_form.getFile().getFileName())) && (FileUtil.getSuffix(a_form.getFile().getFileName()) == null))
/*      */       {
/*  824 */         a_form.addError(this.m_listManager.getListItem(a_dbValidationTransaction, "failedValidationFileExtension", a_userProfile.getCurrentLanguage()).getBody());
/*  825 */         a_form.setNoUploadFileSpecified(true);
/*      */       }
/*  827 */       else if ((StringUtil.stringIsPopulated(a_form.getFile().getFileName())) && (a_form.getAsset().getFormat().getAssetTypeId() > 0L) && (this.m_assetManager.getFileFormatForFile(null, a_form.getFile().getFileName()).getAssetTypeId() != a_form.getAsset().getFormat().getAssetTypeId()))
/*      */       {
/*  831 */         a_form.addError(this.m_listManager.getListItem(a_dbValidationTransaction, "failedValidationFileTypeChange", a_userProfile.getCurrentLanguage()).getBody());
/*  832 */         a_form.setNoUploadFileSpecified(true);
/*      */       }
/*  834 */       else if ((StringUtil.stringIsPopulated(a_form.getFile().getFileName())) && (a_form.getAsset().getEntity().getId() > 0L))
/*      */       {
/*  836 */         FileFormat format = this.m_assetManager.getFileFormatForFile(null, a_form.getFile().getFileName());
/*  837 */         AssetEntity entity = this.m_assetEntityManager.getEntity(a_dbValidationTransaction, a_form.getAsset().getEntity().getId(), false);
/*  838 */         if (!entity.getAllowAssetType(format.getAssetTypeId()))
/*      */         {
/*  840 */           a_form.addError(this.m_listManager.getListItem(a_dbValidationTransaction, "failedValidationWrongType", a_userProfile.getCurrentLanguage(), new String[] { entity.getName() }));
/*  841 */           a_form.setNoUploadFileSpecified(true);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void validateSubstituteFile(AssetForm a_form, DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  853 */     if ((a_form.getSubstituteFile() != null) && (a_form.getSubstituteFile().getFileName() != null) && (a_form.getSubstituteFile().getFileName().length() > 0))
/*      */     {
/*  857 */       boolean bImageFormat = false;
/*  858 */       String sExt = FileUtil.getSuffix(a_form.getSubstituteFile().getFileName());
/*      */ 
/*  861 */       if (sExt != null)
/*      */       {
/*  863 */         FileFormat thumbFileFormat = this.m_assetManager.getFileFormatForExtension(null, sExt);
/*  864 */         if ((thumbFileFormat.getIsConvertable()) && (thumbFileFormat.getAssetTypeId() == 2L))
/*      */         {
/*  867 */           bImageFormat = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  872 */       if (!bImageFormat)
/*      */       {
/*  874 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "thumbnailFileNotImage", a_userProfile.getCurrentLanguage()).getBody());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void validatePrice(AssetForm a_form, DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  882 */     BrightMoney price = a_form.getAsset().getPrice();
/*  883 */     if (price.getIsFormAmountEntered())
/*      */     {
/*  885 */       if (!price.processFormData())
/*      */       {
/*  887 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationPrice", a_userProfile.getCurrentLanguage()).getBody());
/*      */       }
/*  891 */       else if ((!AssetBankSettings.getUsePriceBands()) && (price.getAmount() < 0L))
/*      */       {
/*  893 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationPrice", a_userProfile.getCurrentLanguage()).getBody());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean populateAndValidateDates(DBTransaction a_dbTransaction, AssetForm a_form, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*  902 */     boolean bDateError = false;
/*      */ 
/*  905 */     DateFormat dfDateFormat = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/*  909 */       if (StringUtil.stringIsPopulated(a_form.getDateAdded()))
/*      */       {
/*  911 */         a_form.getAsset().setDateAdded(k_dfFullTimestampDateFormat.parse(a_form.getDateAdded()));
/*      */       }
/*  913 */       if (StringUtil.stringIsPopulated(a_form.getExpiryDate()))
/*      */       {
/*  915 */         a_form.getAsset().setExpiryDate(dfDateFormat.parse(a_form.getExpiryDate()));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (ParseException pe)
/*      */     {
/*  921 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", a_userProfile.getCurrentLanguage()).getBody());
/*  922 */       bDateError = true;
/*      */     }
/*  924 */     return bDateError;
/*      */   }
/*      */ 
/*      */   private boolean validateAccessLevels(DBTransaction a_dbTransaction, DBTransaction a_dbValidationTransaction, AssetForm a_form, HttpServletRequest a_request, ABUserProfile a_userProfile, boolean a_bValidateMandatoryFields, Set a_visibleCats)
/*      */     throws Bn2Exception
/*      */   {
/*  930 */     boolean bNoAccessLevelsSelected = false;
/*      */ 
/*  934 */     if ((AssetBankSettings.getShowSingleAccessLevel()) && (a_bValidateMandatoryFields))
/*      */     {
/*  936 */       if (!StringUtil.stringIsPopulated(a_request.getParameter("permissionCategoryForm.selectedCategories")))
/*      */       {
/*  939 */         a_form.getPermissionCategoryForm().setCategoryIds("");
/*  940 */         a_form.getPermissionCategoryForm().setSelectedCategories(null);
/*  941 */         bNoAccessLevelsSelected = true;
/*      */       }
/*      */ 
/*  944 */       if (bNoAccessLevelsSelected)
/*      */       {
/*  946 */         boolean bValid = false;
/*  947 */         Vector vecCats = this.m_categoryManager.getCategoriesForItem(a_dbValidationTransaction, 2L, a_form.getAsset().getId());
/*      */ 
/*  950 */         boolean bUserHasAllAccessLevels = AssetManager.userHasCategoryPermissions(a_visibleCats, vecCats, true);
/*  951 */         if (!bUserHasAllAccessLevels)
/*      */         {
/*  953 */           bValid = true;
/*      */         }
/*      */ 
/*  956 */         if (!bValid)
/*      */         {
/*  958 */           a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationAccessLevel", a_userProfile.getCurrentLanguage()).getBody());
/*      */         }
/*      */       }
/*      */     }
/*  962 */     return bNoAccessLevelsSelected;
/*      */   }
/*      */ 
/*      */   private void validateExtensionData(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, boolean a_bAdding, Asset a_asset, AssetForm a_form)
/*      */     throws Bn2Exception
/*      */   {
/*  973 */     if (a_bAdding)
/*      */     {
/*  975 */       getPluginManager().validateAdd(a_dbTransaction, a_userProfile, "asset", a_asset, a_form);
/*      */     }
/*      */     else
/*      */     {
/*  979 */       getPluginManager().validateEdit(a_dbTransaction, a_userProfile, "asset", a_asset, a_form);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void extractExtensionDataFromForm(DBTransaction a_transaction, boolean a_bAdding, Asset a_asset, AssetForm a_form) throws Bn2Exception
/*      */   {
/*  985 */     if (a_bAdding)
/*      */     {
/*  987 */       getPluginManager().extractAddDataFromForm(a_transaction, "asset", a_asset, a_form);
/*      */     }
/*      */     else
/*      */     {
/*  991 */       getPluginManager().extractEditDataFromForm(a_transaction, "asset", a_asset, a_form);
/*      */     }
/*      */   }
/*      */ 
/*      */   private AssetFileSource openAssetFileSource(ABUserProfile a_userProfile, HttpServletRequest a_request, AssetForm a_form)
/*      */     throws Bn2Exception, IOException
/*      */   {
/*      */     AssetFileSource source;
/* 1014 */     if ((a_form.getTempFileLocation() != null) && (a_form.getTempFileLocation().length() > 0))
/*      */     {
/* 1017 */      source = new AssetFileSource();
/* 1018 */       source.setStoredFileLocation(a_form.getTempFileLocation());
/*      */ 
/* 1020 */       File tmpFile = new File(this.m_fileStoreManager.getAbsolutePath(a_form.getTempFileLocation()));
/*      */ 
/* 1022 */       if (tmpFile.exists())
/*      */       {
/* 1024 */         source.setFileSize(tmpFile.length());
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*      */      // AssetFileSource source;
/* 1027 */       if (StringUtils.isNotEmpty(a_form.getUploadedFilename()))
/*      */       {
/* 1029 */         source = new AssetFileSource(UploadUtil.getUploadedFile(a_userProfile, a_request.getSession()));
/*      */       }
/*      */       else
/*      */       {
/* 1034 */         source = new AssetFileSource(a_form.getFile());
/*      */       }
/*      */     }
/* 1037 */     if ((a_form.getFile() != null) && (StringUtils.isNotEmpty(a_form.getFile().getFileName())))
/*      */     {
/* 1039 */       source.setOriginalFilename(a_form.getFile().getFileName());
/*      */     }
/* 1041 */     else if (StringUtils.isNotEmpty(a_form.getUploadedFilename()))
/*      */     {
/* 1043 */       source.setOriginalFilename(a_form.getUploadedFilename());
/*      */     }
/*      */     else
/*      */     {
/* 1047 */       source.setOriginalFilename(a_form.getOriginalFilename());
/*      */     }
/* 1049 */     return source;
/*      */   }
/*      */ 
/*      */   private void performPostSaveActionsOnAsset(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId, Vector<ActionOnAsset> a_vActions)
/*      */     throws Bn2Exception
/*      */   {
/* 1063 */     if (a_vActions != null)
/*      */     {
/* 1065 */       for (int i = 0; i < a_vActions.size(); i++)
/*      */       {
/* 1067 */         ((ActionOnAsset)a_vActions.get(i)).performOnAssetAfterSave(a_dbTransaction, a_lAssetId, a_lUserId);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Vector<ActionOnAsset> performPreSaveActions(DBTransaction a_dbTransaction, Asset a_asset, Asset a_originalAsset)
/*      */     throws Bn2Exception
/*      */   {
/* 1083 */     Vector vActions = new Vector();
/*      */ 
/* 1086 */     if (a_originalAsset != null)
/*      */     {
/* 1088 */       Vector vAttributeValues = a_asset.getAttributeValues();
/* 1089 */       for (int i = 0; i < vAttributeValues.size(); i++)
/*      */       {
/* 1091 */         AttributeValue value = (AttributeValue)vAttributeValues.get(i);
/*      */ 
/* 1094 */         if (value.getActionOnAssetId() <= 0L)
/*      */           continue;
/* 1096 */         AttributeValue originalValue = a_originalAsset.getAttributeValue(value.getAttribute().getId());
/*      */ 
/* 1099 */         if ((originalValue != null) && (originalValue.getValue() != null) && ((originalValue.getValue().equals(value.getValue())) || (value.getId() == originalValue.getId()))) {
/*      */           continue;
/*      */         }
/* 1102 */         ActionOnAsset action = this.m_actionOnAssetManager.getAction(a_dbTransaction, value.getActionOnAssetId());
/* 1103 */         action.performOnAssetBeforeSave(a_dbTransaction, a_asset);
/* 1104 */         vActions.add(action);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1109 */     return vActions;
/*      */   }
/*      */ 
/*      */   private void reindexRelatedAssets(DBTransaction a_dbTransaction, Asset a_asset, String a_sRelatedAssetIds, boolean a_bSurrogate)
/*      */     throws AssetNotFoundException, Bn2Exception
/*      */   {
/* 1122 */     String[] sIds = a_sRelatedAssetIds.split("[ ,]+");
/*      */ 
/* 1124 */     for (int i = 0; i < sIds.length; i++)
/*      */     {
/*      */       try
/*      */       {
/* 1128 */         if (StringUtils.isNotEmpty(sIds[i]))
/*      */         {
/* 1130 */           Asset asset = this.m_assetManager.getAsset(a_dbTransaction, Long.parseLong(sIds[i]), null, true, true);
/* 1131 */           if ((!a_bSurrogate) || (asset.getSurrogateAssetId() == a_asset.getId()))
/*      */           {
/* 1133 */             this.m_searchManager.indexDocument(asset, true);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (NumberFormatException e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void validateOrgUnitDiskUsage(AssetForm a_form, Asset a_originalAsset, ABUserProfile a_userProfile, AssetFileSource a_source, Vector a_vOrgUnits)
/*      */     throws Bn2Exception
/*      */   {
/* 1163 */     long lSizeRequired = 0L;
/* 1164 */     long lSizeOfReplacedFile = 0L;
/* 1165 */     HashSet hsOldAccessLevelIds = null;
/*      */ 
/* 1168 */     if (a_originalAsset != null)
/*      */     {
/* 1170 */       hsOldAccessLevelIds = new HashSet();
/* 1171 */       List vOldAccessLevels = a_originalAsset.getPermissionCategories();
/* 1172 */       for (int i = 0; i < vOldAccessLevels.size(); i++)
/*      */       {
/* 1174 */         hsOldAccessLevelIds.add(Long.valueOf(((Category)vOldAccessLevels.get(i)).getId()));
/*      */       }
/* 1176 */       this.m_categoryManager.addAncestorsOfCategories(vOldAccessLevels, hsOldAccessLevelIds, 2L);
/*      */     }
/*      */ 
/* 1180 */     if (a_source.isValid())
/*      */     {
/* 1182 */       lSizeRequired = a_source.getFileSize();
/*      */ 
/* 1184 */       if (a_originalAsset != null)
/*      */       {
/* 1186 */         lSizeOfReplacedFile = a_originalAsset.getFileSizeInBytes();
/*      */       }
/*      */ 
/*      */     }
/* 1190 */     else if (a_originalAsset != null)
/*      */     {
/* 1192 */       lSizeRequired = a_originalAsset.getFileSizeInBytes();
/*      */     }
/*      */ 
/* 1196 */     List accessLevelIds = StringUtil.convertToListOfLongs(a_form.getPermissionCategoryIds(), ",");
/* 1197 */     HashSet hsAccessLevelIds = new HashSet(accessLevelIds);
/* 1198 */     this.m_categoryManager.addAncestorsOfCategoryIds(accessLevelIds, hsAccessLevelIds, 2L);
/*      */ 
/* 1200 */     if (lSizeRequired > 0L)
/*      */     {
/* 1203 */       for (int i = 0; i < a_vOrgUnits.size(); i++)
/*      */       {
/* 1205 */         OrgUnit orgUnit = (OrgUnit)a_vOrgUnits.get(i);
/* 1206 */         long lSizeOfStorageToTest = lSizeRequired;
/*      */ 
/* 1209 */         if ((orgUnit.getDiskQuotaMb() <= 0) || (!hsAccessLevelIds.contains(Long.valueOf(orgUnit.getCategory().getId())))) {
/*      */           continue;
/*      */         }
/* 1212 */         if ((hsOldAccessLevelIds != null) && (hsOldAccessLevelIds.contains(Long.valueOf(orgUnit.getCategory().getId()))))
/*      */         {
/* 1216 */           if (a_source.isValid())
/*      */           {
/* 1218 */             lSizeOfStorageToTest -= lSizeOfReplacedFile;
/*      */           }
/*      */           else
/*      */           {
/* 1224 */             lSizeOfStorageToTest = 0L;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1229 */         if ((lSizeOfStorageToTest <= 0L) || (!this.m_orgUnitManager.isDiskQuotaExceeded(null, orgUnit, lSizeOfStorageToTest)))
/*      */           continue;
/* 1231 */         String[] asParams = { orgUnit.getCategory().getName(a_userProfile.getCurrentLanguage()) };
/* 1232 */         a_form.addError(this.m_listManager.getListItem(null, "failedValidationInsufficientOrgUnitQuota", a_userProfile.getCurrentLanguage(), asParams));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void validateBrandTemplate(ABUserProfile a_userProfile, HttpServletRequest a_request, AssetForm a_form)
/*      */     throws Bn2Exception, IOException
/*      */   {
/* 1253 */     if (a_form.getAsset().getIsBrandTemplate())
/*      */     {
/* 1255 */       AssetFileSource source = null;
/*      */       try
/*      */       {
/* 1260 */         source = openAssetFileSource(a_userProfile, a_request, a_form);
/*      */         boolean valid;
/*      */        // boolean valid;
/* 1262 */         if (source.isValid())
/*      */         {
/* 1265 */           String tempFileLocation = source.getStoredFileLocation();
/*      */           //boolean valid;
/* 1266 */           if (tempFileLocation != null)
/*      */           {
/* 1268 */             valid = this.m_brandTemplateManager.isValidBrandTemplate(tempFileLocation);
/*      */           }
/*      */           else
/*      */           {
/* 1272 */             valid = this.m_brandTemplateManager.isValidBrandTemplate(source.getFilename(), source.getInputStream());
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1278 */           valid = this.m_brandTemplateManager.isValidBrandTemplate(a_form.getAsset().getFileLocation());
/*      */         }
/*      */ 
/* 1281 */         if (!valid)
/*      */         {
/* 1283 */           a_form.addError(this.m_listManager.getListItem(null, "failedValidationBrandTemplate", a_userProfile.getCurrentLanguage()).getBody());
/*      */         }
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/* 1289 */         if (source != null)
/*      */         {
/* 1291 */           source.close();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addInvisibleCategoryIds(Vector<Category> a_vExistingCategoriesInAsset, Set<Long> a_visIds, Vector<Category> a_vecNewCategoriesInAsset, boolean a_bImplySameForAllDescendants)
/*      */     throws Bn2Exception
/*      */   {
/* 1309 */     CategoryUtil.addInvisibleCategoryIds(a_vExistingCategoriesInAsset, a_visIds, a_vecNewCategoriesInAsset, a_bImplySameForAllDescendants);
/*      */   }
/*      */ 
/*      */   private Vector<DataBean> arrayToVector(long[] a_al)
/*      */   {
/* 1327 */     Vector vec = new Vector();
/* 1328 */     for (int i = 0; i < a_al.length; i++)
/*      */     {
/* 1330 */       DataBean brand = new DataBean();
/* 1331 */       brand.setId(a_al[i]);
/* 1332 */       vec.add(brand);
/*      */     }
/* 1334 */     return vec;
/*      */   }
/*      */ 
/*      */   protected void prepExtensionAsset(AssetForm a_form)
/*      */     throws Bn2Exception
/*      */   {
/* 1354 */     if (a_form.getAsset().getExtendsCategory().getId() <= 0L)
/*      */     {
/* 1356 */       a_form.getAsset().getExtendsCategory().setId(a_form.getCatExtensionCatId());
/* 1357 */       a_form.getAsset().getExtendsCategory().setCategoryTypeId(a_form.getCatExtensionTreeId());
/* 1358 */       a_form.getAsset().getExtendsCategory().setParentId(a_form.getCatExtensionParentId());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected ActionForward overrideWithExtensionAssetForward(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, HttpServletRequest a_request, AssetForm a_form, ActionMapping a_mapping, ActionForward a_forward)
/*      */     throws Bn2Exception
/*      */   {
/* 1372 */     ActionForward forward = null;
/* 1373 */     if (a_form.getAsset().getExtendsCategory().getId() > 0L)
/*      */     {
/* 1377 */       Category cat = this.m_categoryManager.getCategory(a_dbTransaction, a_form.getAsset().getExtendsCategory().getCategoryTypeId(), a_form.getAsset().getExtendsCategory().getId());
/* 1378 */       if (!cat.getName().equals(a_form.getAsset().getName()))
/*      */       {
/* 1380 */         a_request.setAttribute("ExtensionError", this.m_listManager.getListItem(a_dbTransaction, "categoryExtensionNameError", a_userProfile.getCurrentLanguage()).getBody());
/*      */       }
/*      */ 
/* 1385 */       if (a_form.getAsset().getExtendsCategory().getParentId() > 0L)
/*      */       {
/* 1387 */         a_request.setAttribute("categoryId", Long.valueOf(a_form.getAsset().getExtendsCategory().getParentId()));
/*      */       }
/*      */ 
/* 1392 */       if (StringUtil.stringIsPopulated(a_form.getCatExtensionReturnLocation()))
/*      */       {
/* 1395 */         String sQueryString = "categoryId=" + a_form.getAsset().getExtendsCategory().getParentId() + "&" + "categoryTypeId" + "=" + a_form.getAsset().getExtendsCategory().getCategoryTypeId();
/* 1396 */         String sName = "BrowseCategory";
/* 1397 */         if (a_form.getCatExtensionReturnLocation().equals("explorer"))
/*      */         {
/* 1399 */           sName = "BrowseExplorer";
/*      */         }
/* 1401 */         forward = createRedirectingForward(sQueryString, a_mapping, sName);
/* 1402 */         forward.setName(sName);
/*      */       }
/* 1406 */       else if (a_form.getAsset().getExtendsCategory().getCategoryTypeId() == 2L)
/*      */       {
/* 1408 */         forward = a_mapping.findForward("AddExtensionAssetAL");
/*      */       }
/*      */       else
/*      */       {
/* 1412 */         forward = a_mapping.findForward("AddExtensionAsset");
/*      */       }
/*      */ 
/* 1418 */       if (forward == null)
/*      */       {
/* 1420 */         String sUrl = a_request.getParameter("url");
/* 1421 */         if (StringUtil.stringIsPopulated(sUrl))
/*      */         {
/* 1423 */           forward = new ActionForward(sUrl);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1429 */     if (forward == null)
/*      */     {
/* 1431 */       forward = a_forward;
/*      */     }
/* 1433 */     return forward;
/*      */   }
/*      */ 
/*      */   protected boolean doValidateMandatoryFields(AssetForm a_form)
/*      */   {
/* 1445 */     return true;
/*      */   }
/*      */ 
/*      */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/* 1453 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public MultiLanguageSearchManager getSearchManager()
/*      */   {
/* 1458 */     return this.m_searchManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/* 1463 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public IAssetManager getAssetManager()
/*      */   {
/* 1469 */     return this.m_assetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(IAssetManager a_sAssetManager)
/*      */   {
/* 1475 */     this.m_assetManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public CategoryManager getCategoryManager()
/*      */   {
/* 1481 */     return this.m_categoryManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(AssetCategoryManager a_sCategoryManager)
/*      */   {
/* 1487 */     this.m_categoryManager = a_sCategoryManager;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*      */   {
/* 1492 */     this.m_assetEntityManager = assetEntityManager;
/*      */   }
/*      */ 
/*      */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*      */   {
/* 1497 */     this.m_taxonomyManager = a_taxonomyManager;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*      */   {
/* 1502 */     this.m_fileStoreManager = a_fileStoreManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager a_attributeManager)
/*      */   {
/* 1507 */     this.m_attributeManager = a_attributeManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeDateRuleManager(AttributeDateRuleManager a_attributeDateRuleManager)
/*      */   {
/* 1512 */     this.m_attributeDateRuleManager = a_attributeDateRuleManager;
/*      */   }
/*      */ 
/*      */   public void setListManager(ListManager listManager)
/*      */   {
/* 1517 */     this.m_listManager = listManager;
/*      */   }
/*      */ 
/*      */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*      */   {
/* 1522 */     this.m_assetLogManager = a_assetLogManager;
/*      */   }
/*      */ 
/*      */   public void setOrgUnitManager(OrgUnitManager orgUnitManager)
/*      */   {
/* 1531 */     this.m_orgUnitManager = orgUnitManager;
/*      */   }
/*      */ 
/*      */   public void setActionOnAssetManager(ActionOnAssetManager a_manager)
/*      */   {
/* 1536 */     this.m_actionOnAssetManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*      */   {
/* 1542 */     this.m_agreementsManager = a_agreementsManager;
/*      */   }
/*      */ 
/*      */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWorkflowManager)
/*      */   {
/* 1547 */     this.m_assetWorkflowManager = a_assetWorkflowManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_countCacheManager)
/*      */   {
/* 1552 */     this.m_categoryCountCacheManager = a_countCacheManager;
/*      */   }
/*      */ 
/*      */   public BrandTemplateManager getBrandTemplateManager()
/*      */   {
/* 1557 */     return this.m_brandTemplateManager;
/*      */   }
/*      */ 
/*      */   public void setBrandTemplateManager(BrandTemplateManager a_brandTemplateManager)
/*      */   {
/* 1562 */     this.m_brandTemplateManager = a_brandTemplateManager;
/*      */   }
/*      */ 
/*      */   public PluginManager getPluginManager()
/*      */   {
/* 1567 */     return this.m_pluginManager;
/*      */   }
/*      */ 
/*      */   public void setPluginManager(PluginManager a_pluginManager)
/*      */   {
/* 1572 */     this.m_pluginManager = a_pluginManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.SaveAssetAction
 * JD-Core Version:    0.6.0
 */