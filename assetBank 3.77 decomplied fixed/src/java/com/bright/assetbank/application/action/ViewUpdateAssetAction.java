/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.agreements.bean.Agreement;
/*     */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.SubmitOptions;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.UploadUtil;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.plugin.service.PluginManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.CategorySelectionForm;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.category.util.CategoryUtil;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.workflow.service.WorkflowManager;
/*     */ import java.io.File;
/*     */ import java.text.DateFormat;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUpdateAssetAction extends BTransactionAction
/*     */   implements FrameworkConstants, AssetBankConstants, CategoryConstants
/*     */ {
/* 100 */   protected IAssetManager m_assetManager = null;
/* 101 */   protected CategoryManager m_categoryManager = null;
/* 102 */   protected FileStoreManager m_fileStoreManager = null;
/* 103 */   protected AssetEntityManager m_assetEntityManager = null;
/* 104 */   protected ABUserManager m_userManager = null;
/* 105 */   protected AgreementsManager m_agreementsManager = null;
/* 106 */   private OrgUnitManager m_orgUnitManager = null;
/* 107 */   protected AssetWorkflowManager m_assetWorkflowManager = null;
/* 108 */   protected WorkflowManager m_workflowManager = null;
/*     */   private PluginManager m_pluginManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 119 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 120 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/* 122 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 124 */       this.m_logger.debug("This user does not have permission to view the admin page");
/* 125 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 128 */     long lAssetId = getAssetId(form, a_request);
/*     */ 
/* 131 */     populateForm(a_dbTransaction, lAssetId, form, a_request);
/*     */ 
/* 134 */     SubmitOptions options = ViewAddAssetAction.getSubmitOptions(userProfile, false, form.getAsset().getIsUnsubmitted(), form.getAsset().getHasWorkflow(), true, form.getAsset().getExtendsCategory().getId() > 0L);
/* 135 */     form.setSubmitOptions(options);
/*     */ 
/* 137 */     if (form.getAsset().getExtendsCategory().getId() <= 0L)
/*     */     {
/* 139 */       Vector vecWorkflowOptions = WorkflowUtil.getWorkflowSubmitOptions(a_dbTransaction, userProfile, form.getAsset(), this.m_assetManager, this.m_categoryManager, form, this.m_workflowManager);
/* 140 */       form.setWorkflowOptions(vecWorkflowOptions);
/*     */     }
/*     */ 
/* 144 */     if ((!form.getHasErrors()) && (!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanUpdateAsset(userProfile, form.getAsset())))
/*     */     {
/* 147 */       this.m_logger.debug("This user does not have permission to update asset id=" + lAssetId);
/* 148 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 151 */     if (!form.getHasErrors())
/*     */     {
/* 153 */       UploadUtil.clearSingleUploadDir(userProfile, a_request.getSession());
/*     */     }
/*     */ 
/* 156 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   protected void populateForm(DBTransaction a_dbTransaction, long a_lAssetId, AssetForm a_form, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 168 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 172 */     Set updCIds = userProfile.getUpdateableOrUpdateableWithApprovalCategoryIds();
/*     */     Asset asset;
/* 174 */     if (!a_form.getHasErrors())
/*     */     {
/*     */       //Asset asset;
/*     */       //Asset asset;
/* 177 */       if (userProfile.getIsAdmin())
/*     */       {
/* 179 */         asset = this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, null, false, LanguageConstants.k_defaultLanguage, false);
/*     */       }
/*     */       else
/*     */       {
/* 183 */         asset = this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, userProfile.getVisibleAttributeIds(), false, LanguageConstants.k_defaultLanguage, false);
/*     */       }
/*     */ 
/* 193 */       a_form.setAsset(asset);
/*     */ 
/* 195 */       DateFormat format = AssetBankSettings.getStandardDateFormat();
/*     */ 
/* 198 */       if (asset.getDateAdded() != null)
/*     */       {
/* 200 */         a_form.setDateAdded(k_dfFullTimestampDateFormat.format(asset.getDateAdded()));
/*     */       }
/*     */ 
/* 203 */       if (asset.getExpiryDate() != null)
/*     */       {
/* 205 */         a_form.setExpiryDate(format.format(asset.getExpiryDate()));
/*     */       }
/*     */ 
/* 211 */       Vector attIds = AttributeUtil.getWritableRelevantAttributeIds(a_dbTransaction, this.m_assetEntityManager, a_form.getAsset(), userProfile, true);
/*     */ 
/* 214 */       Vector attributes = this.m_assetManager.getAssetAttributes(a_dbTransaction, attIds);
/* 215 */       a_form.setAssetAttributes(attributes);
/* 216 */       LanguageUtils.setLanguageOnAll(attributes, userProfile.getCurrentLanguage());
/*     */ 
/* 220 */       Vector vAttributes = a_form.getAssetAttributes();
/*     */ 
/* 222 */       for (int i = 0; i < vAttributes.size(); i++)
/*     */       {
/* 224 */         Attribute att = (Attribute)vAttributes.get(i);
/*     */ 
/* 226 */         Vector vAssetAttValues = asset.getAttributeValues();
/*     */ 
/* 229 */         for (int j = 0; j < vAssetAttValues.size(); j++)
/*     */         {
/* 231 */           AttributeValue attAssetVal = (AttributeValue)vAssetAttValues.get(j);
/*     */ 
/* 234 */           if (att.getId() != attAssetVal.getAttribute().getId()) {
/*     */             continue;
/*     */           }
/* 237 */           if ((att.getIsCheckList()) || (att.getIsOptionList()))
/*     */           {
/* 240 */             for (int iValIndex = 0; iValIndex < att.getListOptionValues().size(); iValIndex++)
/*     */             {
/* 242 */               AttributeValue valToCheck = (AttributeValue)att.getListOptionValues().get(iValIndex);
/*     */ 
/* 245 */               if (attAssetVal.getId() != valToCheck.getId())
/*     */                 continue;
/* 247 */               valToCheck.setIsSelected(true);
/* 248 */               break;
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 255 */             att.setValue(attAssetVal);
/*     */           }
/*     */ 
/* 260 */           if ((att.getIsTextarea()) || (att.getIsTextfield()))
/*     */             continue;
/* 262 */           att.setLanguage(userProfile.getCurrentLanguage());
/* 263 */           attAssetVal.setLanguage(userProfile.getCurrentLanguage());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 270 */       if (asset.getTypeId() == 2L)
/*     */       {
/* 272 */         a_form.setHeight(((ImageAsset)asset).getHeight());
/* 273 */         a_form.setWidth(((ImageAsset)asset).getWidth());
/* 274 */         a_form.setNumLayers(((ImageAsset)asset).getNumPages());
/*     */       }
/* 276 */       a_form.setFileSizeInBytes(asset.getFileSizeInBytes());
/*     */ 
/* 279 */       a_form.setBrandSelectedList(ViewAssetAction.getIdsAsArray(asset.getFeaturedInBrandsList()));
/*     */ 
/* 282 */       if (asset.getIsVideo())
/*     */       {
/* 284 */         if (!StringUtil.stringIsPopulated(a_form.getTempDirName()))
/*     */         {
/* 287 */           String sDirName = this.m_fileStoreManager.getUniqueFilepath("vidthumbs", StoredFileType.TEMP);
/* 288 */           String sDirectory = this.m_fileStoreManager.getAbsolutePath(sDirName);
/*     */ 
/* 291 */           File f = new File(sDirectory);
/*     */ 
/* 293 */           if (!f.exists())
/*     */           {
/* 295 */             f.mkdir();
/*     */           }
/*     */ 
/* 298 */           a_form.setTempDirName(sDirName);
/*     */         }
/*     */       }
/*     */ 
/* 302 */       getPluginManager().populateEditExistingForm(a_dbTransaction, "asset", asset, a_request, a_form);
/*     */     }
/*     */     else
/*     */     {
/* 312 */       asset = a_form.getAsset();
/*     */ 
/* 316 */       Vector vCats = CategoryUtil.getCategoriesFromIds(getCategoryManager(), 1L, a_form.getDescriptiveCategoryForm().getCategoryIds());
/*     */ 
/* 320 */       a_form.getAsset().setDescriptiveCategories(vCats);
/*     */ 
/* 324 */       Vector vPermissionCatIds = StringUtil.convertToVectorOfLongs(a_form.getPermissionCategoryForm().getCategoryIds(), ",");
/* 325 */       Vector vecCatObjs = this.m_categoryManager.getPopulatedCategories(a_dbTransaction, a_form.getAsset(), 2L, vPermissionCatIds);
/* 326 */       a_form.getAsset().setPermissionCategories(vecCatObjs);
/*     */ 
/* 328 */       a_form.getAsset().setWorkflows(this.m_workflowManager.getWorkflowsForEntity(a_dbTransaction, asset.getId()));
/*     */     }
/*     */ 
/* 332 */     FlatCategoryList flatCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 1L, userProfile.getCurrentLanguage());
/*     */     Vector vecTopLevelCats;
/* 336 */     if (userProfile.getHasRootCategoriesSet())
/*     */     {
/* 339 */       vecTopLevelCats = this.m_categoryManager.getCategories(a_dbTransaction, 1L, userProfile.getRootDescriptiveCategoryIds(), userProfile.getCurrentLanguage());
/*     */ 
/* 342 */       vecTopLevelCats = CategoryUtil.ignoreSingleCategories(this.m_categoryManager, a_dbTransaction, userProfile, vecTopLevelCats);
/*     */ 
/* 344 */       flatCategoryList = CategoryUtil.filterFlatCategoryListByAllowedIds(new HashSet(userProfile.getRootDescriptiveCategoryIds()), flatCategoryList, userProfile.getRootDescriptiveCategoryIds().size() > 1);
/*     */ 
/* 348 */       if ((a_form.getAsset().getDescriptiveCategories() != null) && (a_form.getAsset().getDescriptiveCategories().size() > 0))
/*     */       {
/* 350 */         Vector vecFilteredCats = new Vector();
/*     */ 
/* 352 */         for (Category cat : a_form.getAsset().getDescriptiveCategories())
/*     */         {
/* 355 */           if (CategoryUtil.categoryOrAncestorIsInIdSet(cat, userProfile.getRootDescriptiveCategoryIds(), true))
/*     */           {
/* 358 */             vecFilteredCats.add(cat);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 363 */         a_form.getAsset().setDescriptiveCategories(vecFilteredCats);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 369 */       vecTopLevelCats = this.m_categoryManager.getCategories(a_dbTransaction, 1L, -1L, userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 372 */     a_form.getDescriptiveCategoryForm().setTopLevelCategories(vecTopLevelCats);
/* 373 */     a_form.getDescriptiveCategoryForm().setMaxNumOfSubCats(flatCategoryList.getDepth() - 1);
/* 374 */     a_form.getDescriptiveCategoryForm().setFlatCategoryList(flatCategoryList.getCategories());
/*     */ 
/* 378 */     FlatCategoryList flatPermissionCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L, userProfile.getCurrentLanguage());
/* 379 */     Vector vecTopLevelPermissionCats = this.m_categoryManager.getCategories(a_dbTransaction, 2L, -1L, userProfile.getCurrentLanguage());
/*     */ 
/* 381 */     if (!userProfile.getIsAdmin())
/*     */     {
/* 384 */       flatPermissionCategoryList = CategoryUtil.filterFlatCategoryListByCategoryIds(updCIds, flatPermissionCategoryList);
/* 385 */       vecTopLevelPermissionCats = CategoryUtil.filterCategoryVectorByCategoryIds(updCIds, vecTopLevelPermissionCats, true);
/*     */     }
/*     */ 
/* 388 */     a_form.getPermissionCategoryForm().setTopLevelCategories(vecTopLevelPermissionCats);
/* 389 */     a_form.getPermissionCategoryForm().setMaxNumOfSubCats(flatPermissionCategoryList.getDepth() - 1);
/* 390 */     a_form.getPermissionCategoryForm().setFlatCategoryList(flatPermissionCategoryList.getCategories());
/*     */ 
/* 394 */     a_form.setBrandList(this.m_userManager.getAllBrands(a_dbTransaction));
/*     */ 
/* 397 */     a_form.setEncryptedFilePath(FileUtil.encryptFilepath(asset.getFileLocation()));
/* 398 */     a_form.setEncryptedOriginalFilePath(FileUtil.encryptFilepath(asset.getOriginalFileLocation()));
/*     */ 
/* 401 */     if (AssetBankSettings.getAgreementsEnabled())
/*     */     {
/* 404 */       Vector vOrgUnits = null;
/* 405 */       vOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, userProfile.getUser().getId());
/*     */ 
/* 408 */       a_form.setAgreements(this.m_agreementsManager.getAgreements(a_dbTransaction, true, vOrgUnits, -1L, true));
/*     */ 
/* 411 */       if (a_form.getAsset().getAgreementTypeId() == 2L)
/*     */       {
/* 413 */         Agreement agreement = a_form.getAsset().getAgreement();
/* 414 */         if (agreement != null)
/*     */         {
/* 417 */           if (a_form.getHasErrors())
/*     */           {
/* 419 */             agreement = this.m_agreementsManager.getAgreement(a_dbTransaction, agreement.getId());
/*     */           }
/*     */ 
/* 423 */           if (!a_form.getAgreements().contains(agreement))
/*     */           {
/* 425 */             a_form.getAgreements().add(agreement);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 432 */     getPluginManager().populateViewEditExistingRequest(a_dbTransaction, "asset", a_request, a_form);
/*     */   }
/*     */ 
/*     */   private long getAssetId(AssetForm a_form, HttpServletRequest a_request)
/*     */   {
/* 450 */     long lId = 0L;
/*     */ 
/* 452 */     if (!a_form.getHasErrors())
/*     */     {
/* 455 */       lId = getLongParameter(a_request, "id");
/*     */     }
/*     */ 
/* 458 */     if (lId <= 0L)
/*     */     {
/* 461 */       lId = a_form.getAsset().getId();
/*     */     }
/*     */ 
/* 464 */     return lId;
/*     */   }
/*     */ 
/*     */   public CategoryManager getCategoryManager()
/*     */   {
/* 473 */     return this.m_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_sCategoryManager)
/*     */   {
/* 479 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 485 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 491 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/* 496 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager) {
/* 500 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 505 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 510 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ 
/*     */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*     */   {
/* 515 */     this.m_agreementsManager = a_agreementsManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager orgUnitManager)
/*     */   {
/* 520 */     this.m_orgUnitManager = orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_assetWorkflowManager)
/*     */   {
/* 525 */     this.m_assetWorkflowManager = a_assetWorkflowManager;
/*     */   }
/*     */ 
/*     */   public void setWorkflowManager(WorkflowManager a_workflowManager)
/*     */   {
/* 530 */     this.m_workflowManager = a_workflowManager;
/*     */   }
/*     */ 
/*     */   public PluginManager getPluginManager()
/*     */   {
/* 535 */     return this.m_pluginManager;
/*     */   }
/*     */ 
/*     */   public void setPluginManager(PluginManager a_pluginManager)
/*     */   {
/* 540 */     this.m_pluginManager = a_pluginManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewUpdateAssetAction
 * JD-Core Version:    0.6.0
 */