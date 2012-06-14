/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.agreements.bean.Agreement;
/*     */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.DownloadUtil;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*     */ import com.bright.assetbank.entity.relationship.util.RelationshipUtil;
/*     */ import com.bright.assetbank.plugin.service.PluginManager;
/*     */ import com.bright.assetbank.priceband.service.PriceBandManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.bean.FileInStorageDevice;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.workflow.service.WorkflowManager;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAssetAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/* 104 */   private IAssetManager m_assetManager = null;
/* 105 */   private ABUserManager m_userManager = null;
/* 106 */   private CategoryManager m_categoryManager = null;
/* 107 */   private UsageManager m_usageManager = null;
/* 108 */   private PriceBandManager m_pbManager = null;
/* 109 */   private AssetEntityRelationshipManager m_assetEntityRelationshipManager = null;
/* 110 */   protected FileStoreManager m_fileStoreManager = null;
/* 111 */   protected AgreementsManager m_agreementsManager = null;
/* 112 */   private WorkflowManager m_workflowManager = null;
/*     */   private PluginManager m_pluginManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 122 */     ActionForward afForward = null;
/* 123 */     AssetForm form = (AssetForm)a_form;
/* 124 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 127 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/* 130 */     if (StringUtil.stringIsPopulated(a_request.getParameter("noDownloadPermission")))
/*     */     {
/* 132 */       form.setRedirectedFromDownloadPage(true);
/*     */     }
/*     */ 
/* 136 */     String sAssetState = a_request.getParameter("assetState");
/* 137 */     if (StringUtil.stringIsPopulated(sAssetState))
/*     */     {
/* 139 */       String[] aAssetStates = sAssetState.split(",");
/* 140 */       Vector vecAssets = new Vector();
/* 141 */       for (int i = 0; i < aAssetStates.length; i++)
/*     */       {
/* 143 */         int iAssetState = Integer.parseInt(aAssetStates[i]);
/*     */ 
/* 145 */         if (iAssetState <= 0)
/*     */           continue;
/* 147 */         Collection assets = userProfile.getAssetBox().getAssetsInState(iAssetState);
/* 148 */         vecAssets.addAll(assets);
/*     */       }
/*     */ 
/* 151 */       int iIndex = getIntParameter(a_request, "index");
/* 152 */       if (iIndex < vecAssets.size())
/*     */       {
/* 154 */         AssetInList asset = (AssetInList)vecAssets.elementAt(iIndex);
/* 155 */         lAssetId = asset.getId();
/*     */       }
/*     */ 
/*     */     }
/* 161 */     else if (lAssetId <= 0L)
/*     */     {
/* 163 */       lAssetId = form.getAsset().getId();
/*     */     }
/*     */ 
/* 167 */     Asset asset = null;
/*     */     try
/*     */     {
/* 172 */       asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, userProfile.getVisibleAttributeIds(), false, userProfile.getCurrentLanguage(), true);
/*     */     }
/*     */     catch (AssetNotFoundException anfe)
/*     */     {
/* 176 */       return a_mapping.findForward("AssetNotFound");
/*     */     }
/*     */ 
/* 180 */     if (AssetBankSettings.getAgreementsEnabled())
/*     */     {
/* 182 */       Agreement agreement = null;
/*     */ 
/* 184 */       if (asset.getAgreementTypeId() == 2L)
/*     */       {
/* 186 */         agreement = this.m_agreementsManager.getCurrentAgreementForAsset(a_dbTransaction, asset.getId());
/*     */       }
/* 188 */       else if ((asset.getAgreementTypeId() == 1L) && (asset.getEntity().getUnrestrictedAgreementId() > 0L))
/*     */       {
/* 190 */         agreement = this.m_agreementsManager.getAgreement(a_dbTransaction, asset.getEntity().getUnrestrictedAgreementId());
/*     */       }
/* 192 */       else if ((asset.getAgreementTypeId() == 3L) && (asset.getEntity().getRestrictedAgreementId() > 0L))
/*     */       {
/* 194 */         agreement = this.m_agreementsManager.getAgreement(a_dbTransaction, asset.getEntity().getRestrictedAgreementId());
/*     */       }
/*     */ 
/* 197 */       if (agreement != null)
/*     */       {
/* 199 */         asset.setAgreement(agreement);
/*     */       }
/*     */ 
/* 202 */       if (userProfile.getIsAdmin())
/*     */       {
/* 204 */         asset.setPreviousAgreements(this.m_agreementsManager.getAgreements(a_dbTransaction, false, null, lAssetId, false));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 209 */     form.setDirectDownloadFileType(AssetBankSettings.getIsDirectDownloadFileType(FileUtil.getSuffix(asset.getFileName())));
/*     */ 
/* 212 */     if (AssetBankSettings.getGetRelatedAssets())
/*     */     {
/* 214 */       RelationshipUtil.populateRelatedAssets(a_dbTransaction, userProfile, asset, form);
/*     */     }
/*     */ 
/* 218 */     setPermissionsInAssetForm(form, asset, userProfile, this.m_assetManager);
/*     */ 
/* 221 */     if ((!userProfile.getIsAdmin()) && (!form.getUserCanUpdateAsset()) && (!this.m_assetManager.userCanViewAsset(userProfile, asset)))
/*     */     {
/* 223 */       this.m_logger.debug("This user does not have permission to view asset id=" + lAssetId);
/* 224 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 229 */     Vector vecDescriptiveCategories = this.m_categoryManager.getCategoriesForItem(a_dbTransaction, 1L, lAssetId, userProfile.getCurrentLanguage());
/*     */ 
/* 231 */     asset.setDescriptiveCategories(vecDescriptiveCategories);
/*     */ 
/* 234 */     Vector vecAllCategories = this.m_categoryManager.getCategories(a_dbTransaction, 1L, CategoryUtil.getDescriptiveBrowseTopLevelCatId());
/*     */ 
/* 238 */     if ((vecAllCategories == null) || (vecAllCategories.isEmpty()))
/*     */     {
/* 240 */       form.setHideCategoriesBecauseEmpty(true);
/*     */     }
/*     */ 
/* 246 */     Vector vecPermissionCategories = this.m_categoryManager.getCategoriesForItem(a_dbTransaction, 2L, lAssetId, userProfile.getCurrentLanguage());
/* 247 */     if (!userProfile.getIsAdmin())
/*     */     {
/* 249 */       vecPermissionCategories = CategoryUtil.filterCategoryVectorByCategoryIds(userProfile.getPermissionCategoryIds(1), vecPermissionCategories, false);
/*     */     }
/*     */ 
/* 254 */     asset.setPermissionCategories(vecPermissionCategories);
/*     */ 
/* 257 */     form.setAsset(asset);
/*     */ 
/* 259 */     AssetBox assetBox = userProfile.getAssetBox();
/* 260 */     boolean bInAssetBox = assetBox.containsAsset(lAssetId);
/*     */ 
/* 263 */     form.setAssetInAssetBox(bInAssetBox);
/*     */ 
/* 265 */     long lUserId = userProfile.getUser() != null ? userProfile.getUser().getId() : 0L;
/*     */ 
/* 267 */     form.setCanRemoveAssetFromAssetBox((bInAssetBox) && ((!assetBox.isShared()) || (assetBox.getAsset(lAssetId).getAddedToAssetBoxByUserId() == lUserId)));
/*     */ 
/* 270 */     if ((AssetBankSettings.getDownloadFromFilesystem()) && (userProfile.getUserCanSeeSourcePath()))
/*     */     {
/* 273 */       String sFileLocation = asset.getOriginalFileLocation();
/*     */ 
/* 275 */       if (StringUtils.isEmpty(sFileLocation))
/*     */       {
/* 277 */         sFileLocation = asset.getFileLocation();
/*     */       }
/*     */ 
/* 280 */       if (StringUtils.isNotEmpty(sFileLocation))
/*     */       {
/* 282 */         FileInStorageDevice fileInSD = new FileInStorageDevice(this.m_fileStoreManager.getAbsolutePath(sFileLocation), userProfile.getUserOsIsWindows());
/* 283 */         form.setFileInStorageDevice(fileInSD);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 289 */     form.setEncryptedFilePath(FileUtil.encryptFilepath(asset.getFileLocation()));
/* 290 */     form.setEncryptedOriginalFilePath(FileUtil.encryptFilepath(asset.getOriginalFileLocation()));
/*     */ 
/* 293 */     if (AssetBankSettings.getUsePriceBands())
/*     */     {
/* 295 */       Vector priceBands = this.m_pbManager.getPriceBandList(a_dbTransaction, asset.getTypeId());
/* 296 */       LanguageUtils.setLanguageOnAll(priceBands, userProfile.getCurrentLanguage());
/* 297 */       form.setPriceBands(priceBands);
/*     */     }
/*     */ 
/* 301 */     form.setBrandList(this.m_userManager.getAllBrands(a_dbTransaction));
/* 302 */     form.setBrandSelectedList(getIdsAsArray(asset.getFeaturedInBrandsList()));
/* 303 */     form.setWorkflows(this.m_workflowManager.getWorkflows());
/* 304 */     form.setEntityCanHaveParents(this.m_assetEntityRelationshipManager.isChildEntity(a_dbTransaction, asset.getEntity().getId()));
/*     */ 
/* 309 */     if (!asset.getIsUnsubmitted())
/*     */     {
/* 311 */       this.m_usageManager.logAssetViewAsynchronously(lAssetId, lUserId, userProfile.getSessionId());
/*     */     }
/*     */ 
/* 315 */     if (Boolean.parseBoolean(a_request.getParameter("showSensitive")))
/*     */     {
/* 317 */       userProfile.getClickedSensitiveImageIds().add(Long.valueOf(asset.getId()));
/*     */     }
/*     */ 
/* 320 */     if (asset.getFormat().getId() > 0L)
/*     */     {
/* 322 */       form.setDownloadAsDocument(DownloadUtil.treatFileAsDocumentForDownload(asset.getFormat().getFileExtension()));
/*     */     }
/*     */ 
/* 326 */     form.setCanBeRated(asset.getCanBeRated());
/*     */ 
/* 328 */     getPluginManager().populateViewRequest(a_dbTransaction, "asset", asset, a_request);
/*     */ 
/* 334 */     if (asset.getTypeId() == 2L)
/*     */     {
/* 336 */       afForward = a_mapping.findForward("SuccessImage");
/*     */     }
/* 338 */     else if (asset.getTypeId() == 3L)
/*     */     {
/* 340 */       afForward = a_mapping.findForward("SuccessVideo");
/*     */     }
/* 342 */     else if (asset.getTypeId() == 4L)
/*     */     {
/* 344 */       afForward = a_mapping.findForward("SuccessAudio");
/*     */     }
/*     */     else
/*     */     {
/* 348 */       afForward = a_mapping.findForward("SuccessFile");
/*     */     }
/*     */ 
/* 351 */     return afForward;
/*     */   }
/*     */ 
/*     */   public static long[] getIdsAsArray(Vector<DataBean> a_vecListValues)
/*     */   {
/* 357 */     if (a_vecListValues == null)
/*     */     {
/* 359 */       return new long[0];
/*     */     }
/*     */ 
/* 362 */     int iVectorSize = a_vecListValues.size();
/*     */ 
/* 364 */     long[] alIds = new long[iVectorSize];
/*     */ 
/* 366 */     for (int i = 0; i < iVectorSize; i++)
/*     */     {
/* 369 */       alIds[i] = ((DataBean)a_vecListValues.get(i)).getId();
/*     */     }
/*     */ 
/* 372 */     return alIds;
/*     */   }
/*     */ 
/*     */   public static void setPermissionsInAssetForm(AssetForm form, Asset asset, ABUserProfile userProfile, IAssetManager a_assetManager)
/*     */     throws Bn2Exception
/*     */   {
/* 387 */     if (userProfile.getIsAdmin())
/*     */     {
/* 389 */       form.setUserCanUpdateAsset(true);
/* 390 */       form.setUserCanDownloadAsset(true);
/* 391 */       form.setUserCanDeleteAsset(true);
/*     */     }
/*     */     else
/*     */     {
/* 396 */       form.setUserCanUpdateAsset(a_assetManager.userCanUpdateAsset(userProfile, asset));
/*     */ 
/* 399 */       form.setUserCanDeleteAsset(a_assetManager.userCanDeleteAsset(userProfile, asset));
/*     */ 
/* 404 */       if (a_assetManager.userCanDownloadAssetNow(userProfile, asset))
/*     */       {
/* 407 */         form.setUserCanDownloadAsset(true);
/*     */       }
/* 413 */       else if (a_assetManager.userCanDownloadWithApprovalAsset(userProfile, asset))
/*     */       {
/* 415 */         form.setUserCanDownloadAssetWithApproval(true);
/*     */       }
/*     */ 
/* 420 */       if (userProfile.getIsLoggedIn())
/*     */       {
/* 422 */         form.setUserMustRequestApprovalForHighRes(a_assetManager.userMustRequestApprovalForHighRes(userProfile, asset));
/* 423 */         form.setUserHasApprovalForHighRes(a_assetManager.userApprovedForHighResAsset(userProfile, asset));
/*     */       }
/*     */ 
/* 428 */       form.setUserCanReviewAsset(a_assetManager.userCanReviewAsset(userProfile, asset));
/* 429 */       form.setUserCanViewRestrictedAsset(a_assetManager.userCanViewRestrictedAsset(userProfile, asset));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static long getLongParameter(HttpServletRequest a_request, String a_sParam)
/*     */   {
/* 443 */     String sId = a_request.getParameter(a_sParam);
/* 444 */     long lId = -1L;
/*     */ 
/* 446 */     if ((sId == null) || (sId.length() == 0))
/*     */     {
/* 448 */       return lId;
/*     */     }
/*     */ 
/* 451 */     if (sId.indexOf('#') > 0)
/*     */     {
/* 453 */       sId = sId.substring(0, sId.indexOf('#'));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 458 */       lId = Long.parseLong(sId);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/* 462 */       GlobalApplication.getInstance().getLogger().error("Long format exception for parameter: " + a_sParam + " : " + nfe);
/*     */     }
/*     */ 
/* 465 */     return lId;
/*     */   }
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/* 471 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 477 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 483 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 489 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public CategoryManager getCategoryManager()
/*     */   {
/* 495 */     return this.m_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_sCategoryManager)
/*     */   {
/* 501 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ 
/*     */   public UsageManager getUsageManager()
/*     */   {
/* 507 */     return this.m_usageManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 513 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public void setPriceBandManager(PriceBandManager a_sManager)
/*     */   {
/* 518 */     this.m_pbManager = a_sManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityRelationshipManager(AssetEntityRelationshipManager a_assetEntityRelationshipManager)
/*     */   {
/* 523 */     this.m_assetEntityRelationshipManager = a_assetEntityRelationshipManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 528 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*     */   {
/* 533 */     this.m_agreementsManager = a_agreementsManager;
/*     */   }
/*     */ 
/*     */   public void setWorkflowManager(WorkflowManager a_workflowManager)
/*     */   {
/* 538 */     this.m_workflowManager = a_workflowManager;
/*     */   }
/*     */ 
/*     */   public PluginManager getPluginManager()
/*     */   {
/* 543 */     return this.m_pluginManager;
/*     */   }
/*     */ 
/*     */   public void setPluginManager(PluginManager a_pluginManager)
/*     */   {
/* 548 */     this.m_pluginManager = a_pluginManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewAssetAction
 * JD-Core Version:    0.6.0
 */