/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.agreements.bean.Agreement;
/*     */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.SubmitOptions;
/*     */ import com.bright.assetbank.application.bean.UploadedFileInfo;
/*     */ import com.bright.assetbank.application.bean.UploadedImageInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.ABImageMagickOptionList;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.plugin.service.PluginManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.CategoryImpl;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.CategorySelectionForm;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.category.util.CategoryUtil;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.image.util.ImageMagick;
/*     */ import com.bright.framework.image.util.ImageUtil;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAddAssetAction extends BTransactionAction
/*     */   implements ImageConstants, CategoryConstants, AssetBankConstants
/*     */ {
/* 106 */   protected IAssetManager m_assetManager = null;
/* 107 */   private CategoryManager m_categoryManager = null;
/* 108 */   protected AttributeManager m_attributeManager = null;
/* 109 */   protected FileStoreManager m_fileStoreManager = null;
/* 110 */   private ABUserManager m_userManager = null;
/* 111 */   protected AssetEntityManager m_assetEntityManager = null;
/* 112 */   private OrgUnitManager m_orgUnitManager = null;
/* 113 */   private AgreementsManager m_agreementsManager = null;
/*     */   private PluginManager m_pluginManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 128 */     ActionForward afForward = null;
/* 129 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 130 */     AssetForm form = (AssetForm)a_form;
/* 131 */     boolean bUserTemplate = false;
/*     */ 
/* 133 */     if (form.getTemplate() != null)
/*     */     {
/* 135 */       bUserTemplate = true;
/* 136 */       form.getTemplate().getId();
/*     */     }
/*     */ 
/* 140 */     if ((form.getTempFileLocation() == null) && (a_request.getParameter("path") != null))
/*     */     {
/* 142 */       form.setTempFileLocation(a_request.getParameter("path"));
/*     */     }
/*     */ 
/* 146 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getCanUpdateAssets()) && (!userProfile.getCanUploadWithApproval())))
/*     */     {
/* 148 */       this.m_logger.debug("This user does not have permission to view the add image page");
/* 149 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 153 */     form.setDateAdded(k_dfFullTimestampDateFormat.format(new Date()));
/*     */ 
/* 156 */     form.setBrandList(this.m_userManager.getAllBrands(a_dbTransaction));
/*     */ 
/* 160 */     if (form.getAssetAttributes() == null)
/*     */     {
/* 164 */       Vector attIds = AttributeUtil.getWritableRelevantAttributeIds(a_dbTransaction, this.m_assetEntityManager, form.getAsset(), userProfile, true);
/*     */ 
/* 166 */       form.setAssetAttributes(this.m_assetManager.getAssetAttributes(a_dbTransaction, attIds));
/*     */     }
/*     */ 
/* 170 */     LanguageUtils.setLanguageOnAll(form.getAssetAttributes(), userProfile.getCurrentLanguage());
/*     */ 
/* 180 */     AttributeUtil.markRelevantToEntity(form.getAssetAttributes(), a_dbTransaction, this.m_assetEntityManager, form.getAsset().getEntity().getId());
/*     */ 
/* 183 */     SubmitOptions options = getSubmitOptions(userProfile, true, false, false, getHideSingleOptions(), form.getCatExtensionCatId() > 0L);
/* 184 */     form.setSubmitOptions(options);
/*     */ 
/* 187 */     if (form.getHasErrors())
/*     */     {
/* 191 */       Vector vCatIds = StringUtil.convertToVector(form.getDescriptiveCategoryForm().getCategoryIds(), ",");
/* 192 */       Vector vCats = CategoryUtil.createSelectedCategoryVector(a_dbTransaction, vCatIds, this.m_categoryManager, 1L);
/* 193 */       form.getAsset().setDescriptiveCategories(vCats);
/*     */ 
/* 195 */       Vector vPermissionCatIds = StringUtil.convertToVector(form.getPermissionCategoryForm().getCategoryIds(), ",");
/* 196 */       Vector vPermissionCats = CategoryUtil.createSelectedCategoryVector(a_dbTransaction, vPermissionCatIds, this.m_categoryManager, 2L);
/* 197 */       form.getAsset().setPermissionCategories(vPermissionCats);
/*     */     }
/*     */     else
/*     */     {
/* 202 */       form.setSelectedSubmitOption(calculateSelectedSubmitOption(options, userProfile));
/*     */ 
/* 205 */       if (bUserTemplate)
/*     */       {
/* 207 */         Vector vCatIds = StringUtil.convertToVector(form.getTemplate().getCategoryIds(), ",");
/* 208 */         Vector vCats = CategoryUtil.createSelectedCategoryVector(a_dbTransaction, vCatIds, this.m_categoryManager, 1L);
/* 209 */         form.getAsset().setDescriptiveCategories(vCats);
/*     */ 
/* 211 */         Vector vPermissionCatIds = StringUtil.convertToVector(form.getTemplate().getAccessLevelIds(), ",");
/* 212 */         Vector vPermissionCats = CategoryUtil.createSelectedCategoryVector(a_dbTransaction, vPermissionCatIds, this.m_categoryManager, 2L);
/* 213 */         form.getAsset().setPermissionCategories(vPermissionCats);
/*     */       }
/*     */       else
/*     */       {
/* 217 */         populatePreSelectedCategories(a_dbTransaction, a_request, userProfile, form);
/*     */       }
/*     */ 
/* 221 */       populateMetadataDefaults(form);
/*     */     }
/*     */ 
/* 225 */     if (StringUtils.isNotEmpty(form.getTempFileLocation()))
/*     */     {
/* 227 */       UploadedFileInfo uploadedFileInfo = getAssetManager().getUploadedFileInfo(form.getTempFileLocation(), form.getAsset().getFormat().getId(), false);
/*     */ 
/* 229 */       if (form.getPreviewImageFile() == null)
/*     */       {
/* 231 */         form.setPreviewImageFile(uploadedFileInfo.getPreviewImage());
/*     */       }
/*     */ 
/* 235 */       form.getAsset().setTypeId(uploadedFileInfo.getAssetTypeId());
/*     */ 
/* 238 */       if (uploadedFileInfo.getAssetTypeId() == 2L)
/*     */       {
/* 240 */         ImageAsset image = new ImageAsset(form.getAsset());
/* 241 */         form.setHeight(((UploadedImageInfo)uploadedFileInfo).getImageHeight());
/* 242 */         form.setWidth(((UploadedImageInfo)uploadedFileInfo).getImageWidth());
/* 243 */         image.setHeight(form.getHeight());
/* 244 */         image.setWidth(form.getWidth());
/* 245 */         image.setNumPages(((UploadedImageInfo)uploadedFileInfo).getNumLayers());
/* 246 */         form.setNumLayers(((UploadedImageInfo)uploadedFileInfo).getNumLayers());
/*     */ 
/* 248 */         form.setAsset(image);
/*     */       }
/*     */ 
/* 252 */       if ((!form.getHasErrors()) && (ImageUtil.canReadEmbeddedData(FileUtil.getSuffix(form.getTempFileLocation()))))
/*     */       {
/* 257 */         String sImageFullPath = this.m_fileStoreManager.getAbsolutePath(form.getTempFileLocation());
/*     */ 
/* 260 */         this.m_attributeManager.populateEmbeddedMetadataValues(sImageFullPath, form.getAsset(), form.getAssetAttributes(), true);
/*     */       }
/*     */ 
/* 267 */       if (form.getAsset().getAutoRotate() > 0)
/*     */       {
/* 269 */         ABImageMagickOptionList imOptions = new ABImageMagickOptionList();
/* 270 */         imOptions.addInputFilename(this.m_fileStoreManager.getAbsolutePath(uploadedFileInfo.getPreviewImage().getPath()));
/* 271 */         imOptions.addRotateClockwise(form.getAsset().getAutoRotate());
/* 272 */         imOptions.addOutputFilename(this.m_fileStoreManager.getAbsolutePath(uploadedFileInfo.getPreviewImage().getPath()));
/* 273 */         ImageMagick.convert(imOptions);
/*     */       }
/*     */ 
/* 276 */       form.setFileSizeInBytes(uploadedFileInfo.getFileSizeInBytes());
/*     */     }
/*     */ 
/* 282 */     FlatCategoryList flatCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 1L, userProfile.getCurrentLanguage());
/*     */     Vector vecTopLevelCats;
/* 286 */     if (userProfile.getHasRootCategoriesSet())
/*     */     {
/* 288 */       vecTopLevelCats = this.m_categoryManager.getCategories(a_dbTransaction, 1L, userProfile.getRootDescriptiveCategoryIds(), userProfile.getCurrentLanguage());
/*     */ 
/* 291 */       vecTopLevelCats = CategoryUtil.ignoreSingleCategories(this.m_categoryManager, a_dbTransaction, userProfile, vecTopLevelCats);
/* 292 */       flatCategoryList = CategoryUtil.filterFlatCategoryListByAllowedIds(new HashSet(userProfile.getRootDescriptiveCategoryIds()), flatCategoryList, userProfile.getRootDescriptiveCategoryIds().size() > 1);
/*     */     }
/*     */     else
/*     */     {
/* 297 */       vecTopLevelCats = this.m_categoryManager.getCategories(a_dbTransaction, 1L, -1L, userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 300 */     form.getDescriptiveCategoryForm().setTopLevelCategories(vecTopLevelCats);
/* 301 */     form.getDescriptiveCategoryForm().setMaxNumOfSubCats(flatCategoryList.getDepth() - 1);
/* 302 */     form.getDescriptiveCategoryForm().setFlatCategoryList(flatCategoryList.getCategories());
/*     */ 
/* 306 */     FlatCategoryList flatPermissionCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L, userProfile.getCurrentLanguage());
/* 307 */     Vector vecTopLevelPermissionCats = this.m_categoryManager.getCategories(a_dbTransaction, 2L, -1L, userProfile.getCurrentLanguage());
/*     */ 
/* 309 */     if (!userProfile.getIsAdmin())
/*     */     {
/* 312 */       Set updCIds = userProfile.getUpdateableOrUpdateableWithApprovalCategoryIds();
/*     */ 
/* 315 */       flatPermissionCategoryList = CategoryUtil.filterFlatCategoryListByCategoryIds(updCIds, flatPermissionCategoryList);
/* 316 */       vecTopLevelPermissionCats = CategoryUtil.filterCategoryVectorByCategoryIds(updCIds, vecTopLevelPermissionCats, true);
/*     */     }
/*     */ 
/* 319 */     form.getPermissionCategoryForm().setTopLevelCategories(vecTopLevelPermissionCats);
/* 320 */     form.getPermissionCategoryForm().setMaxNumOfSubCats(flatPermissionCategoryList.getDepth() - 1);
/* 321 */     form.getPermissionCategoryForm().setFlatCategoryList(flatPermissionCategoryList.getCategories());
/*     */ 
/* 324 */     if (AssetBankSettings.getAgreementsEnabled())
/*     */     {
/* 327 */       Vector vOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, userProfile.getUser().getId());
/*     */ 
/* 330 */       form.setAgreements(this.m_agreementsManager.getAgreements(a_dbTransaction, true, vOrgUnits, 0L, true));
/*     */ 
/* 332 */       if ((form.getHasErrors()) && (form.getAsset() != null) && (form.getAsset().getAgreement() != null) && (form.getAsset().getAgreement().getId() > 0L))
/*     */       {
/* 335 */         form.getAsset().setAgreement(this.m_agreementsManager.getAgreement(a_dbTransaction, form.getAsset().getAgreement().getId()));
/* 336 */         form.getAgreements().add(form.getAsset().getAgreement());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 341 */     getPluginManager().populateAddForm(a_dbTransaction, "asset", a_request, form);
/*     */ 
/* 344 */     getPluginManager().populateViewAddRequest(a_dbTransaction, "asset", a_request, form);
/*     */ 
/* 349 */     afForward = a_mapping.findForward("Success");
/* 350 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void populatePreSelectedCategories(DBTransaction a_transaction, HttpServletRequest a_request, ABUserProfile a_userProfile, AssetForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 372 */     HashMap hmPreSelects = new HashMap();
/* 373 */     hmPreSelects.put(Long.valueOf(2L), new ArrayList());
/* 374 */     hmPreSelects.put(Long.valueOf(1L), new ArrayList());
/*     */ 
/* 376 */     if (a_form.getAsset().getEntity().getId() > 0L)
/*     */     {
/* 378 */       AssetEntity entity = this.m_assetEntityManager.getEntity(a_transaction, a_form.getAsset().getEntity().getId());
/* 379 */       if (entity.getDefaultCategoryId() > 0L)
/*     */       {
/* 382 */         ((ArrayList)hmPreSelects.get(Long.valueOf(AssetBankSettings.getEntityDefaultCategoryIdsDescriptive() ? 1L : 2L))).add(Long.valueOf(entity.getDefaultCategoryId()));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 388 */     if ((a_form.getAddingFromBrowseCatId() > 0L) && (a_form.getAddingFromBrowseTreeId() > 0L))
/*     */     {
/* 390 */       ((ArrayList)hmPreSelects.get(Long.valueOf(a_form.getAddingFromBrowseTreeId()))).add(Long.valueOf(a_form.getAddingFromBrowseCatId()));
/*     */     }
/*     */ 
/* 393 */     Set longKeys = hmPreSelects.keySet();
        long lKey;
/* 394 */     for (Iterator i$ = longKeys.iterator(); i$.hasNext(); ) { lKey = ((Long)i$.next()).longValue();
/*     */ 
/* 396 */       for (i$ = ((ArrayList)hmPreSelects.get(Long.valueOf(lKey))).iterator(); i$.hasNext(); ) { long lCatId = ((Long)i$.next()).longValue();
/*     */ 
/* 398 */         Category cat = new CategoryImpl(lCatId);
/*     */ 
/* 400 */         if (lKey == 1L)
/*     */         {
/* 405 */           if ((!a_userProfile.getHasRootCategoriesSet()) || ((a_userProfile.getRootDescriptiveCategoryIds() != null) && (a_userProfile.getRootDescriptiveCategoryIds().contains(Long.valueOf(lCatId)))) || (CategoryUtil.getIsDescendantOfCategories(lCatId, 1L, a_userProfile.getRootDescriptiveCategoryIds())))
/*     */           {
/* 409 */             a_form.getAsset().addDescriptiveCategories(cat);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 414 */           a_form.getAsset().addPermissionCategory(cat);
/*     */         }
/*     */       }
/*     */     }
/*     */     //long lKey;
/*     */    // Iterator i$;
/*     */   }
/*     */ 
/*     */   public static int calculateSelectedSubmitOption(SubmitOptions a_options, ABUserProfile a_userProfile)
/*     */   {
/* 432 */     int iSelected = 0;
/*     */ 
/* 436 */     iSelected = a_options.getFirstOption();
/*     */ 
/* 438 */     return iSelected;
/*     */   }
/*     */ 
/*     */   public static SubmitOptions getSubmitOptions(ABUserProfile a_userProfile, boolean a_bIsUpload, boolean a_bIsUnsubmitted, boolean a_bHasWorkflow, boolean a_bHideSingleOptions, boolean a_bCategoryExtensionAsset)
/*     */   {
/* 457 */     SubmitOptions options = new SubmitOptions();
/*     */ 
/* 460 */     if (a_bCategoryExtensionAsset)
/*     */     {
/* 462 */       options.addHiddenOption(0);
/* 463 */       return options;
/*     */     }
/*     */ 
/* 467 */     boolean bUsersWithUpdateCanAddAssetsToWorkflow = AssetBankSettings.getWorkflowUsersWithUpdateCanAddAssetsToWorkflow();
/*     */ 
/* 470 */     boolean bUnsubmittedAssetsEnabled = AssetBankSettings.getEnableUnsubmittedAssets();
/*     */ 
/* 473 */     options.setHasMixedPermissions((!a_userProfile.getIsAdmin()) && (a_userProfile.getCanUpdateAssets()) && (a_userProfile.getCanUploadWithApproval()));
/*     */ 
/* 476 */     if (!a_bHasWorkflow)
/*     */     {
/* 479 */       if (a_bIsUpload)
/*     */       {
/* 481 */         if (a_userProfile.getCanUpdateAssets())
/*     */         {
/* 483 */           options.addOption(0);
/*     */         }
/*     */ 
/* 487 */         if (((a_userProfile.getCanUploadWithApproval()) && (!a_userProfile.getCanUpdateAssets())) || (bUsersWithUpdateCanAddAssetsToWorkflow) || ((a_userProfile.getIsAdmin()) && (!a_bIsUpload)))
/*     */         {
/* 489 */           options.addOption(1);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 494 */       if ((bUnsubmittedAssetsEnabled) && ((a_bIsUpload) || (a_bIsUnsubmitted)))
/*     */       {
/* 496 */         options.addOption(2);
/*     */       }
/*     */     }
/*     */ 
/* 500 */     if ((a_bIsUpload) && (a_bHideSingleOptions) && (options.getOnlyContainsSingleOption()))
/*     */     {
/* 503 */       int iOption = options.getFirstOption();
/* 504 */       options.removeOption(iOption);
/* 505 */       options.addHiddenOption(iOption);
/*     */     }
/*     */ 
/* 508 */     return options;
/*     */   }
/*     */ 
/*     */   protected boolean getHideSingleOptions()
/*     */   {
/* 513 */     return true;
/*     */   }
/*     */ 
/*     */   protected void populateMetadataDefaults(AssetForm a_assetForm)
/*     */     throws Bn2Exception
/*     */   {
/* 519 */     this.m_attributeManager.populateMetadataDefaults(a_assetForm.getAsset(), a_assetForm.getAssetAttributes(), true);
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 528 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 534 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public CategoryManager getCategoryManager()
/*     */   {
/* 540 */     return this.m_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_sCategoryManager)
/*     */   {
/* 546 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_sAttributeManager)
/*     */   {
/* 551 */     this.m_attributeManager = a_sAttributeManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 556 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 561 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 566 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager orgUnitManager)
/*     */   {
/* 571 */     this.m_orgUnitManager = orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*     */   {
/* 576 */     this.m_agreementsManager = a_agreementsManager;
/*     */   }
/*     */ 
/*     */   public PluginManager getPluginManager()
/*     */   {
/* 581 */     return this.m_pluginManager;
/*     */   }
/*     */ 
/*     */   public void setPluginManager(PluginManager a_pluginManager)
/*     */   {
/* 586 */     this.m_pluginManager = a_pluginManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewAddAssetAction
 * JD-Core Version:    0.6.0
 */